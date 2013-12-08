package upenn.cis550.groupf.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ViewResult implements IsSerializable {
	// who is viewing the page, i.e. person who login
	private User viewer;
	// what page are we showing
	private User owner;
	private List<Board> boards;
	private List<User> friends;
	private List<Content> contents;
	
	public ViewResult() {
		
	}
	
	public ViewResult(User viewer, User owner, List<Board> boards, List<User> friends, List<Content> contents) {
		setViewer(viewer);
		setOwner(owner);
		setBoards(boards);
		setFriends(friends);
		setContents(contents);
	}
	
	public ViewResult(User viewer, User owner, List<Board> boards, List<User> friends) {
		setViewer(viewer);
		setOwner(owner);
		setBoards(boards);
		setFriends(friends);
	}
	
	public User getViewer() {
		return viewer;
	}

	public void setViewer(User viewer) {
		this.viewer = viewer;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User user) {
		this.owner = user;
	}

	public List<Board> getBoards() {
		return boards;
	}

	public void setBoards(List<Board> boards) {
		this.boards = boards;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}
	
}
