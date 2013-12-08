package upenn.cis550.groupf.server.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

	/**
	 * @author benwu
	 * @param rs the resultset that needs to be closed
	 */
	public static void safeCloseRs(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
