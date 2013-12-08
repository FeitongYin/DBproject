package upenn.cis550.groupf.server;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

import upenn.cis550.groupf.server.util.DBUtil;
import upenn.cis550.groupf.shared.User;

public class UserConvertor {
	/**
	 * This can only returns the first user in the set
	 * @param rs input ResultSet
	 * @return corresponding User object
	 * @throws SQLException
	 */
	public static User getUserFrom(ResultSet rs) {
		if (rs == null) {
			// invalid result set
			return null;
		}
		User user = null;
		try {
			if (rs.next()) {
				user = new User(rs.getString("username"), rs.getString("name"),
						rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("password"), isMale(rs.getString("gender")),
						rs.getDate("dob"),
						rs.getString("email"), rs.getString("phone"), 
						rs.getString("affiliation"));
			}
			else {
				System.out.println("UserConvertor: getUserFrom(): result set is none.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	// ATTENTION!! 
	// the only difference with getUserFrom is that this guy do not call rs.next().
	// it is called by getUserListFrom().
	// @author benwu
	public static User getUser(ResultSet rs) {
		User user = null;
		try {
			user = new User(rs.getString("username"), rs.getString("name"),
					rs.getString("firstname"), rs.getString("lastname"),
					rs.getString("password"), isMale(rs.getString("gender")),
					rs.getDate("dob"),
					rs.getString("email"), rs.getString("phone"), 
					rs.getString("affiliation"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	private static boolean isMale(String gender) {
		return (gender == null) ? false : gender.equals("M");
	}

	/**
	 * reads a ResultSet stream and returns a list of User objects
	 * @param rs input ResultSet
	 * @return list of corresponding Users
	 * @throws SQLException 
	 */
	public static List<User> getUserListFrom(ResultSet rs) throws SQLException {
		List<User> ret = new ArrayList<User>();
		while (rs.next()) {
			ret.add(getUser(rs));
		}
		DBUtil.safeCloseRs(rs);
		
		return ret;
	}
}
