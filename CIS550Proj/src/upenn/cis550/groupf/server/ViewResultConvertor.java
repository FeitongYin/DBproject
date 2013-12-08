package upenn.cis550.groupf.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import upenn.cis550.groupf.shared.Content;
import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.Board;
import upenn.cis550.groupf.shared.ViewResult;

public class ViewResultConvertor {

	public static ViewResult getViewResultFrom(User viewer, ResultSet ownerRs,
			ResultSet boardRs, ResultSet friendRs, ResultSet contentRs) {
		ViewResult result = null;
		try {
			User owner = UserConvertor.getUserFrom(ownerRs);
			List<Board> boards = BoardConvertor.getBoardListFrom(boardRs);
			List<User> friends = UserConvertor.getUserListFrom(friendRs);
			List<Content> contents = ContentConvertor
					.getContentListFrom(contentRs);
			if (viewer == null) {
				result = new ViewResult(owner, owner, boards, friends, contents);
			} else {
				result = new ViewResult(viewer, owner, boards, friends,
						contents);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// called from GreetingServiceImpl
	public static ViewResult getViewResultFrom(User viewer, User owner,
			ResultSet boardRs, ResultSet friendRs, ResultSet pinRs) {
		ViewResult result = null;
		try {
			List<Board> boards = BoardConvertor.getBoardListFrom(boardRs);
			List<User> friends = UserConvertor.getUserListFrom(friendRs);
			List<Content> hotContent = ContentConvertor
					.getContentListFrom(pinRs);
			if (viewer == null) {
				result = new ViewResult(owner, owner, boards, friends,
						hotContent);
			} else {
				result = new ViewResult(viewer, owner, boards, friends,
						hotContent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static ViewResult getViewResultFrom(User viewer, User owner,
			ResultSet boardRs, ResultSet friendRs) {
		ViewResult result = null;
		try {
			List<Board> boards = BoardConvertor.getBoardListFrom(boardRs);
			List<User> friends = UserConvertor.getUserListFrom(friendRs);

			if (viewer == null) {
				result = new ViewResult(owner, owner, boards, friends);
			} else {
				result = new ViewResult(viewer, owner, boards, friends);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
