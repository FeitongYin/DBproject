package upenn.cis550.groupf.server;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

import upenn.cis550.groupf.shared.Board;
import upenn.cis550.groupf.shared.Content;

public class BoardConvertor {
	/**
	 * Converts the next tuple in a ResultSet into an User object
	 * 
	 * @param rs
	 *            input ResultSet
	 * @return corresponding User object
	 * @throws SQLException
	 */
	public static Board getBoardFrom(ResultSet rs) {
		Board board = null;
		try {
			board = new Board(rs.getInt("USERID"), rs.getInt("BOARDID"),
					rs.getString("BOARDNAME"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return board;
	}

	/**
	 * reads a ResultSet stream and returns a list of User objects
	 * 
	 * @param rs
	 *            input ResultSet
	 * @return list of corresponding Users
	 * @throws SQLException
	 */
	public static List<Board> getBoardListFrom(ResultSet rs)
			throws SQLException {
		List<Board> ret = new ArrayList<Board>();
		while (rs.next()) {
			ret.add(getBoardFrom(rs));
		}
		return ret;
	}

	public static boolean addContentToBoard(Board board, ResultSet contentRs) {
		boolean hasDup = false;
		try {
			while (contentRs.next()) {
				hasDup = hasDup
						&& board.addContent(ContentConvertor.getContentFrom(contentRs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hasDup;
	}
}
