package upenn.cis550.groupf.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.HashMap;
import java.util.Map;

public class Board implements IsSerializable {
	private String userName;
	private String boardID;
	private String boardName;
	
	private Map<Integer, Content> contentMap;
	
	public Board() {
		
	}
	
	public Board(String userName, String boardID, String boardName) {
		setUserName(userName);
		setBoardID(boardID);
		setBoardName(boardName);
		// stuff with empty content
		setContentMap(new HashMap<Integer, Content>());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBoardID() {
		return boardID;
	}

	public void setBoardID(String boardID) {
		this.boardID = boardID;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public Map<Integer, Content> getContentMap() {
		return contentMap;
	}

	public void setContentMap(Map<Integer, Content> contentMap) {
		this.contentMap = contentMap;
	}

	/**
	 * @param content
	 * @return whether the Content added contains contentID already found in the contents already in the map
	 */
	public boolean addContent(Content content) {
		boolean hasDup = false;
		if (contentMap.containsKey(content.getContentID())) {
			// contentID duplication is not allowed, temporarily print a log
			System.err.println("contentID duplication is not allowed! Old content gets overwritten");
			hasDup = true;
		}
		
		contentMap.put(content.getContentID(), content);
		
		return hasDup;
	}
}
