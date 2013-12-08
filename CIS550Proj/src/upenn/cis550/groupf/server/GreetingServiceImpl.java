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
import java.util.List;

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
			userRs = stat.executeQuery("SELECT * FROM Users where username = '"
					+ name + "' and password = '" + pw + "'");

			owner = UserConvertor.getUserFrom(userRs);
			if (owner == null) {
				System.out.println("Invalid username or password");
				return null;
			}

			stat = conn.createStatement();
			System.out.println("Fetching User " + owner.getUserName()
					+ "'s Board");
			boardRs = stat
					.executeQuery("SELECT * FROM Boards where username = '"
							+ owner.getUserName() + "'");

			stat = conn.createStatement();
			System.out.println("Fetching User " + owner.getUserName()
					+ "'s friends");
			friendRs = stat
					.executeQuery("select * from users where username in "
							+ "(select friend1Id as friendID from Friends where friend2Id = '"
							+ owner.getUserName()
							+ "' union "
							+ "select friend2Id as friendID from Friends where friend1Id = '"
							+ owner.getUserName() + "')");

			stat = conn.createStatement();
			System.out
					.println("Fetching most pinned content boards not belong to User "
							+ owner.getUserName());
			pinRs = stat
					.executeQuery("with hotcontent "
							+ "as "
							+ "(select srcGroup, contentID, count(contentID) as frequency from pin group by srcGroup, contentID) "
							+ "select * from "
							+ "(select C.srcGroup, C.contentID, frequency, contentKey, description, isCached "
							+ "from hotcontent H, content C "
							+ "where C.contentID = H.contentID and C.srcGroup = H.srcGroup "
							+ "order by frequency desc) "
							+ "where ROWNUM <= 30");
			// .executeQuery("with hotcontent "
			// + "as "
			// +
			// "(select contentID, count(contentID) as frequency from pin group by contentID) "
			// +
			// "select C.contentID, frequency, contentKey, description, isCached "
			// + "from hotcontent H, content C "
			// + "where C.contentID = H.contentID and ROWNUM <= 25"
			// + "order by frequency desc");

		} catch (SQLException se) {
			se.printStackTrace();
		}

		return ViewResultConvertor.getViewResultFrom(null, owner, boardRs,
				friendRs, pinRs);
	}

	@Override
	public boolean addUser(String firstName, String lastName, String email,
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

		return true;
	}

	@Override
	public boolean isUsernameExist(String username) {
		// TODO Auto-generated method stub
		ResultSet rs = null;

		try {
			stat = conn.createStatement();

			rs = stat.executeQuery("SELECT * FROM Users where name = '"
					+ username + "'");
			if (rs.next() == false) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return false;
	}

	@Override
	public List<String> getTags(int contentID) {
		ResultSet rs = null;
		List<String> tags = null;

		System.out.println("entering getTags()...");

		try {
			stat = conn.createStatement();

			rs = stat.executeQuery("SELECT tag FROM Tags where contentID = "
					+ contentID);
			tags = TagConvertor.getTagsFrom(rs);
		} catch (SQLException se) {
			se.printStackTrace();
		}

		return tags;
	}

	@Override
	public boolean pin(String userName, String boardID, String srcGroup, int contentID,
			String comment) {
		System.out.println("in pin() username: " + userName + "\tboardID: "
				+ boardID + "\tsrcGroup: " + srcGroup + "\tcontentID: " + contentID + "\tcomment:"
				+ comment);
		return false;
	}
	
	/**
	 * @author FeitongYin
	 */

	@Override
	public ViewResult getBoardContent(String boardID) {
		
		System.out.println("------------------------process query : getting board with boardID being:-------------------");
		System.out.println(boardID);
		ResultSet userRs = null;
		ResultSet boardRs = null;
		ResultSet friendRs = null;
		ResultSet contentRs = null;
		User boardOwner = null;
		User viewer = null;
		
		try {
			//User
			stat = conn.createStatement();
			userRs = stat.executeQuery("SELECT U.* FROM Users U, Boards B where B.boardID = '"
					+ boardID + "' AND B.username = U.username");
			// user query ok
			boardOwner = UserConvertor.getUserFrom(userRs);
			System.out.println("successfully getting user...........and his username is:");
			System.out.println(boardOwner.getUserName());
			

			//Boards
			stat = conn.createStatement();
			boardRs = stat.executeQuery("SELECT * FROM Boards where username = '"
							+ boardOwner.getUserName() + "'"); //ok

			
			//Friends
			stat = conn.createStatement();
			System.out.println("Fetching User " + boardOwner.getUserName()
					+ "'s friends");
			friendRs = stat
					.executeQuery("select * from users where username in "
							+ "(select friend1Id as friendID from Friends where friend2Id = '"
							+ boardOwner.getUserName()
							+ "' union "
							+ "select friend2Id as friendID from Friends where friend1Id = '"
							+ boardOwner.getUserName() + "')"); //ok
			//Content
			stat = conn.createStatement();
			contentRs = stat
					.executeQuery("with hotcontent "
				+ "as "
				+ "(select srcGroup, contentID, count(contentID) as frequency from pin where destBoardID = '" + boardID + "' group by srcGroup, contentID) "
				+ "select * from "
				+ "(select C.srcGroup, C.contentID, frequency, contentKey, description, isCached "
				+ "from hotcontent H, content C "
				+ "where C.contentID = H.contentID and C.srcGroup = H.srcGroup)"); //ok
			// query tested in sql
			
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return ViewResultConvertor.getViewResultFrom(viewer, boardOwner, boardRs,
				friendRs, contentRs);
		

	}

	@Override
	public ViewResult viewBoard(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
