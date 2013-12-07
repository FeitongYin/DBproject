package upenn.cis550.groupf.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.HashMap;
import java.util.Map;

public class Board implements IsSerializable {
	private int ownerID;
	private int boardID;
	private String boardName;
	
	private Map<Integer, Content> contentMap;
	
	public Board() {
		
	}
	
	public Board(int ownerID, int boardID, String boardName) {
		setOwnerID(ownerID);
		setBoardID(boardID);
		setBoardName(boardName);
		// stuff with empty content
		setContentMap(new HashMap<Integer, Content>());
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public int getBoardID() {
		return boardID;
	}

	public void setBoardID(int boardID) {
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
