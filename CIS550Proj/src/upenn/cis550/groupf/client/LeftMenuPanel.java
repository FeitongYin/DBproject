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
import upenn.cis550.groupf.client.event.ViewOwnerEvent;
import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.Board;
import upenn.cis550.groupf.shared.ViewResult;

public class LeftMenuPanel extends VerticalPanel implements ViewBoardEvent.Handler, ViewOwnerEvent.Handler {
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);	
	private static final String LEFT_PANEL_WIDTH = "250px";
	
	
	private Label boardLabelSelected = new Label("");
	private String boardIDSelected = null;
	
	private Label friendLabelSelected = new Label("");
	private String friendNameSelected = null;
	
	public LeftMenuPanel(User currectUser, List<Board> boards, List<User> friends) {
		Pennterest.EVENT_BUS.addHandler(ViewBoardEvent.TYPE, this);
		Pennterest.EVENT_BUS.addHandler(ViewOwnerEvent.TYPE, this);
		
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
			friendNameLabel.addClickHandler(new FriendClickHandler());
		}
	}
	
	class BoardClickHandler implements ClickHandler {

		/**
		 * Fired when a board label is clicked
		 */
		public void onClick(ClickEvent event) {
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++on lick++++++++++");
			boardLabelSelected = (Label) event.getSource();
			boardIDSelected = boardLabelSelected.getTitle(); //boardID as title
			sendBoardToServer();
			
		}
		
		/**
		 * Send the name of board owner and board to the server and wait for a response
		 * maybe should also send the name of viewer(check later)
		 */
		private void sendBoardToServer() {
			final String boardID = boardIDSelected;
			
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
			
			System.out.println("================On click a friend, sending friend to server===============");
			friendLabelSelected = (Label) event.getSource();
			friendNameSelected = friendLabelSelected.getTitle();  //get userName, which is a p.k.
			sendFriendToServer();
			
		}
		
		/**
		 * Send the name of board owner and board to the server and wait for a response
		 * maybe should also send the name of viewer(check later)
		 */
		private void sendFriendToServer() {
			final String friendName = friendNameSelected;
			System.out.println("The userName of people you just clicked is............");
			System.out.println(friendName);
					
			// trigger a query via event bus
			Pennterest.EVENT_BUS.fireEvent(new ViewOwnerEvent(friendName,
					new AsyncCallback<ViewResult>() {
				
				// On error, call the error dialog routine
				public void onFailure(Throwable caught) {
					postErrorDialog();
				}

				private void postErrorDialog() {
					System.out.println("Oh, no...Viewing a people's board failed :( ");
					// TODO Auto-generated method stub
					
				}

				// On successful retrieval of actors, create an actors page
				public void onSuccess(ViewResult result) {
					if (result == null) {
						postErrorDialog();
					}
					else {
						System.out.println("****************Cogratulations!! Going to a friend's************");
						UserPage userPage = new UserPage(result);
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
	public void processViewOwner(String ownerName, AsyncCallback<ViewResult> callback) {		

			greetingService.getUser(ownerName, callback);
			
		}

	@Override
	public void processViewUser(String friendName,
			AsyncCallback<ViewResult> callback) {
		// TODO Auto-generated method stub
		
	}
		
	}

