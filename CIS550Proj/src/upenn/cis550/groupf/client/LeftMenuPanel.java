package upenn.cis550.groupf.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
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
import upenn.cis550.groupf.client.event.ViewFriendEvent;
import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.Board;
import upenn.cis550.groupf.shared.ViewResult;

public class LeftMenuPanel extends VerticalPanel implements ViewBoardEvent.Handler {
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);	
	private static final String LEFT_PANEL_WIDTH = "250px";
	
	
	private Label boardLabelSelected = new Label("");
	private String boardIDSelected = null;
	
	final Label friendLabelSelected = new Label("");
	private String friendNameSelected = null;
	
	public LeftMenuPanel(User currectUser, List<Board> boards, List<User> friends) {
		Pennterest.EVENT_BUS.addHandler(ViewBoardEvent.TYPE, this);
		setBorderWidth(3);
		setSpacing(10);
		//Label label = null;
		
		Label boardLabel = new Label("Boards you own");
		Label boardNameLabel = null;
		Panel boardPanel = new VerticalPanel();
		
		
		Label friendLabel = new Label("Friends");
		Label friendNameLabel = null;
		Panel friendPanel = new VerticalPanel();
		
		add(boardLabel);
		boardLabel.setSize("100px", "21px");
		add(boardPanel);
		boardPanel.setSize("100px", "100px");
		
		
		add(friendLabel);
		friendLabel.setSize("100px", "20px");
		add(friendPanel);
		friendPanel.setSize("100px", "100px");
		
		for (Board board : boards) {
			//boardLabel.setText(board.getBoardName());
			boardNameLabel = new Label(board.getBoardName());
			boardNameLabel.setTitle(board.getBoardID()); //get boardId set as title
			boardPanel.add(boardNameLabel);
			boardNameLabel.addClickHandler(new BoardClickHandler());
		}
		
		for (User friend : friends) {
			friendNameLabel = new Label(friend.getName()); // get [nickname] as label to display
			friendNameLabel.setTitle(friend.getUserName()); // get [username(p.k)],set as title
			friendPanel.add(friendNameLabel);
			// TODO add handler
		}
	}
	
	class BoardClickHandler implements ClickHandler {

		/**
		 * Fired when a board label is clicked
		 */
		public void onClick(ClickEvent event) {
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++on lick++++++++++");
			boardLabelSelected = (Label) event.getSource();
			boardIDSelected = boardLabelSelected.getTitle(); //boardID as title, string to int
			System.out.println("sending to server.....");
			sendBoardToServer();
			
		}
		
		/**
		 * Send the name of board owner and board to the server and wait for a response
		 * maybe should also send the name of viewer(check later)
		 */
		private void sendBoardToServer() {
			final String boardID = boardIDSelected;
			//final String boardName = boardLabel.getText();
			//test purpose, print out board name 
			System.out.println("getting board id");
			System.out.println(boardID);
			
			// trigger a query via event bus
			Pennterest.EVENT_BUS.fireEvent(new ViewBoardEvent(boardID,
					new AsyncCallback<ViewResult>() {
				
				// On error, call the error dialog routine
				public void onFailure(Throwable caught) {
					postErrorDialog();
				}

				private void postErrorDialog() {
					System.out.println("failed.....");
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
						System.out.println("success!!");
						BoardContentPage boardPage = new BoardContentPage(result);
					}
				}
			}));
			
		}
		
	}
	
	class FriendClickHandler implements ClickHandler {

		/**
		 * Fired when a board label is clicked
		 */
		public void onClick(ClickEvent event) {
			// test onlick, direct to a new test page
			// succeed
			//Window.Location.assign("http://www.google.com");
			System.out.println("sending friend to server.....");
			sendFriendToServer();
			
		}
		
		/**
		 * Send the name of board owner and board to the server and wait for a response
		 * maybe should also send the name of viewer(check later)
		 */
		private void sendFriendToServer() {
			final String friendName = friendNameSelected;
			
			
			// trigger a query via event bus
			Pennterest.EVENT_BUS.fireEvent(new ViewFriendEvent(friendName,
					new AsyncCallback<ViewResult>() {
				
				// On error, call the error dialog routine
				public void onFailure(Throwable caught) {
					postErrorDialog();
				}

				private void postErrorDialog() {
					System.out.println("failed:)");
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
						System.out.println("success!!");
						FriendBoardPage friendBoard = new FriendBoardPage(result);
					}
				}
			}));
			
		}
		
	}
	
	public void doWork() {
		// TODO 
	}

	@Override
	public void processGetBoard(String boardID, AsyncCallback<ViewResult> callback) {		

			greetingService.getBoardContent(boardID, callback);
			
		}
	
	// add another override, e.g. processGetFriendBoard
	
	@Override
	public void processViewFriend(String friendName, AsyncCallback<ViewResult> callback) {		

			greetingService.viewBoard(friendName, callback);
			
		}

		
	}

