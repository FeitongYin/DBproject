package upenn.cis550.groupf.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import upenn.cis550.groupf.shared.Content;

public class ContentConvertor {
	/**
	 * Converts the next tuple in a ResultSet into an User object
	 * 
	 * @param rs
	 *            input ResultSet
	 * @return corresponding User object
	 * @throws SQLException
	 */
	public static Content getContentFrom(ResultSet contentRs) {
		Content content = null;
		try {
			content = new Content(contentRs.getInt("CONTENTID"),
					contentRs.getInt("FREQUENCY"),
					contentRs.getString("CONTENTKEY"),
					contentRs.getString("DESCRIPTION"),
					isCached(contentRs.getString("ISCACHED")));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return content;
	}
	

	private static boolean isCached(String isCached) {
		return isCached.equals("Y");
	}

	/**
	 * reads a ResultSet stream and returns a list of User objects
	 * 
	 * @param rs
	 *            input ResultSet
	 * @return list of corresponding Users
	 * @throws SQLException
	 */
	public static List<Content> getContentListFrom(ResultSet rs)
			throws SQLException {
		List<Content> ret = new ArrayList<Content>();
		while (rs.next()) {
			ret.add(getContentFrom(rs));
		}
		return ret;
	}
}
