package upenn.cis550.groupf.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import upenn.cis550.groupf.server.util.DBUtil;

public class TagConvertor {

	public static String getTagFrom(ResultSet rs) {
		String tag = null;
		try {
			tag = rs.getString("TAG");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tag;
	}

	public static List<String> getTagsFrom(ResultSet rs) throws SQLException {
		List<String> ret = new ArrayList<String>();
		while (rs.next()) {
			ret.add(getTagFrom(rs));
		}
		
		DBUtil.safeCloseRs(rs);
		return ret;
	}
}
