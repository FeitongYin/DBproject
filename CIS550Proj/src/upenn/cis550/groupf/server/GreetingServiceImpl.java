package upenn.cis550.groupf.server;

import upenn.cis550.groupf.client.GreetingService;
import upenn.cis550.groupf.shared.FieldVerifier;
import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	static final String hostName = "cis550project.cwnynylpmg7r.us-west-2.rds.amazonaws.com";
	static final String user = "cis550project";
	static final String password = "cis550projectkey";
	static final String database = "PENNTR";

	Connection conn;
	Statement stat;

	public GreetingServiceImpl() {
		initDB();
	}

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	public void initDB() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@//"
					+ hostName + "/" + database, user, password);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public ViewResult login(String name, String pw) {
		ResultSet userRs = null;
		ResultSet boardRs = null;
		ResultSet friendRs = null;
		ResultSet pinRs = null;
		User owner = null;

		try {
			stat = conn.createStatement();

			System.out.println("Validating Users...");
			userRs = stat.executeQuery("SELECT * FROM Users where name = '"
					+ name + "' and password = '" + pw + "'");

			owner = UserConvertor.getUserFrom(userRs);
			if (owner == null) {
				System.out.println("Invalid username or password");
				return null;
			}

			stat = conn.createStatement();
			System.out.println("Fetching User " + owner.getId() + "'s Board");
			boardRs = stat.executeQuery("SELECT * FROM Boards where userID = "
					+ owner.getId());

			stat = conn.createStatement();
			System.out.println("Fetching User " + owner.getId() + "'s friends");
			friendRs = stat
					.executeQuery("select * from users where userID in "
							+ "(select friend1Id as friendID from Friends where friend2Id = "
							+ owner.getId()
							+ " union "
							+ "select friend2Id as friendID from Friends where friend1Id = "
							+ owner.getId() + ")");

			stat = conn.createStatement();
			System.out
					.println("Fetching most pinned content boards not belong to User "
							+ owner.getId());
			pinRs = stat
					.executeQuery("with hotcontent "
							+ "as "
							+ "(select contentID, count(contentID) as frequency from pin group by contentID) "
							+ "select C.contentID, frequency, contentKey, description, isCached from hotcontent H, content C "
							+ "where C.contentID = H.contentID "
							+ "order by frequency desc");

		} catch (SQLException se) {
			se.printStackTrace();
		}

		return ViewResultConvertor.getViewResultFrom(null, owner, boardRs,
				friendRs, pinRs);
	}

	@Override
	public User addUser(String firstName, String lastName, String email,
			String gender) {
		ResultSet rs = null;

		try {
			stat = conn.createStatement();

			System.out.println("Validating Users...");
			// rs = stat.executeQuery("insert into Users "
			// + "(userID, name, password, gender, email) values (" + name +
			// "' and password = '" + pw + "'");
		} catch (SQLException se) {
			se.printStackTrace();
		}

		return null;
	}

	@Override
	public ViewResult getBoardContent(int boardID) {
		
		System.out.println("proccessing query!!....................");
		// TODO Auto-generated method stub
		ResultSet userRs = null;
		ResultSet boardRs = null;
		ResultSet friendRs = null;
		ResultSet contentRs = null;
		User boardOwner = null;
		User viewer = null;
		
		try {
			//User
			stat = conn.createStatement();
			userRs = stat.executeQuery("SELECT U.* FROM Users U, Boards B where B.boardID = "
					+ boardID + " AND B.userID = U.userID");
			
			boardOwner = UserConvertor.getUserFrom(userRs);

			//Boards
			stat = conn.createStatement();
			boardRs = stat.executeQuery("SELECT * FROM Boards where userID = "
					+ boardOwner.getId());
			
			//Friends
			stat = conn.createStatement();
			friendRs = stat
					.executeQuery("select * from users where userID in "
							+ "(select friend1Id as friendID from Friends where friend2Id = "
							+ boardOwner.getId()
							+ " union "
							+ "select friend2Id as friendID from Friends where friend1Id = "
							+ boardOwner.getId() + ")");
			//Content
			stat = conn.createStatement();
			contentRs = stat
					.executeQuery("with hotcontent "
							+ "as "
							+ "(select contentID, count(contentID) as frequency from pin where destBoardID =" + boardID + "group by contentID) "
							+ "select C.contentID, frequency, contentKey, description, isCached from hotcontent H, content C "
							+ "where C.contentID = H.contentID ");
			// query tested in sql
			
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return ViewResultConvertor.getViewResultFrom(viewer, boardOwner, boardRs,
				friendRs, contentRs);
		

	}
}
