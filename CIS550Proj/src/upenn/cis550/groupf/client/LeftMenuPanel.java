package upenn.cis550.groupf.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.Board;

public class LeftMenuPanel extends VerticalPanel {
	private static final String LEFT_PANEL_WIDTH = "250px";
	final Label boardLabel = new Label("");
	
	public LeftMenuPanel(User currectUser, List<Board> boards, List<User> friends) {
		setBorderWidth(3);
		setSpacing(10);
		Label label = null;
		
		Label boardLabel = new Label("Boards you own");
		Panel boardPanel = new VerticalPanel();
		Label friendLabel = new Label("Friends");
		Panel friendPanel = new VerticalPanel();
		
		add(boardLabel);
		boardLabel.setSize(LEFT_PANEL_WIDTH, "20px");
		add(boardPanel);
		boardPanel.setSize(LEFT_PANEL_WIDTH, "100px");
		add(friendLabel);
		friendLabel.setSize(LEFT_PANEL_WIDTH, "20px");
		add(friendPanel);
		friendPanel.setSize(LEFT_PANEL_WIDTH, "100px");
		
		for (Board board : boards) {
			boardLabel.setText(board.getBoardName());
			boardPanel.add(boardLabel);
			boardLabel.addClickHandler(new BoardClickHandler());
		}
		
		for (User friend : friends) {
			label = new Label(friend.getName());
			friendPanel.add(label);
		}
	}
	
	class BoardClickHandler implements ClickHandler {

		/**
		 * Fired when a board label is clicked
		 */
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			Window.Location.assign("http://www.google.com");
			
		}
		
		/**
		 * Send the name of board owner and board to the server and wait for a response
		 * maybe should also send the name of viewer(check later)
		 */
		private void sendBoardToServer() {
			
		}
		
	}
	
	public void doWork() {
		// TODO 
	}
}
