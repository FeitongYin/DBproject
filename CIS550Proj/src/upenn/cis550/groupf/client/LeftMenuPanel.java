package upenn.cis550.groupf.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import upenn.cis550.groupf.client.event.LoginEvent;
import upenn.cis550.groupf.client.event.ViewBoardEvent;
import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.Board;
import upenn.cis550.groupf.shared.ViewResult;

public class LeftMenuPanel extends VerticalPanel {
	
	public EventBus EVENT_BUS;
	
	
	private static final String LEFT_PANEL_WIDTH = "250px";
	final Label boardLabel = new Label("");
	private int boardID;
	final Label friendLabel = new Label("");
	
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
			boardID = board.getBoardID();
			boardPanel.add(boardLabel);
			boardLabel.addClickHandler(new BoardClickHandler());
		}
		
		for (User friend : friends) {
			friendLabel.setText(friend.getName());
			friendPanel.add(friendLabel);
		}
	}
	
	public LeftMenuPanel(EventBus bus, User currectUser, List<Board> boards, List<User> friends) {
		this.EVENT_BUS = bus;
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
			boardID = board.getBoardID();
			boardPanel.add(boardLabel);
			boardLabel.addClickHandler(new BoardClickHandler());
		}
		
		for (User friend : friends) {
			friendLabel.setText(friend.getName());
			friendPanel.add(friendLabel);
		}
	}
	
	class BoardClickHandler implements ClickHandler {

		/**
		 * Fired when a board label is clicked
		 */
		public void onClick(ClickEvent event) {
			// test onlick, direct to a new test page
			// succeed
			//Window.Location.assign("http://www.google.com");
			sendBoardToServer();
			
		}
		
		/**
		 * Send the name of board owner and board to the server and wait for a response
		 * maybe should also send the name of viewer(check later)
		 */
		private void sendBoardToServer() {
			final int boardOwnerID = boardID;
			final String boardName = boardLabel.getText();
			//test purpose, print out board name 
			System.out.println(boardName);
			
			// trigger a query via event bus
			Pennterest.EVENT_BUS.fireEvent(new ViewBoardEvent(boardOwnerID,
					new AsyncCallback<ViewResult>() {
				
				// On error, call the error dialog routine
				public void onFailure(Throwable caught) {
					postErrorDialog();
				}

				private void postErrorDialog() {
					// TODO Auto-generated method stub
					
				}

				// On successful retrieval of actors, create an actors page
				public void onSuccess(ViewResult result) {
					if (result == null) {
						postErrorDialog();
					}
					else {
						//PinPage pinPage = new PinPage(EVENT_BUS, result);
						//pinPage.doWork();
						BoardContentPage boardPage = new BoardContentPage(EVENT_BUS, result);
					}
				}
			}));
			
		}
		
	}
	
	public void doWork() {
		// TODO 
	}
}
