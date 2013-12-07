package upenn.cis550.groupf.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import upenn.cis550.groupf.client.event.ViewEvent;
import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.Board;
import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class PinPage implements ViewEvent.Handler {
	
	public EventBus EVENT_BUS;
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	final Label idLabel = new Label();
	final Label nameLabel = new Label();
	final Label genderLabel = new Label();
	final Label emailLabel = new Label();
	final Label phoneLabel = new Label();
	final Label affiliationLabel = new Label();
	
	List<Board> boards = null;
	List<User> friends = null;

	public PinPage(EventBus bus, ViewResult result) {
		this.EVENT_BUS = bus;
		EVENT_BUS.addHandler(ViewEvent.TYPE, this);
		
		/*
		EVENT_BUS.fireEvent(new ViewEvent(result.getUser().getId(),
				new AsyncCallback<ViewResult>() {
			
			// On error, call the error dialog routine
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ViewResult result) {
				boards = result.getBoards();
				friends = result.getFriends();
			}
		}));
		*/
		
		RootPanel rootPanel = RootPanel.get("ContentPanel");
		rootPanel.setSize("1024px", "768px");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		rootPanel.clear();
		
		HorizontalPanel mainPanel = new HorizontalPanel();
		mainPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		rootPanel.add(mainPanel, 10, 36);
		mainPanel.setSize("719px", "513px");
		
		VerticalPanel leftPanel = new LeftMenuPanel(result.getOwner(), result.getBoards(), result.getFriends());
		mainPanel.add(leftPanel);
		leftPanel.setSize("231px", "511px");
		
		VerticalPanel centerPanel = new HotContentPanel(result.getContents());
		centerPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		mainPanel.add(centerPanel);
		centerPanel.setSize("154px", "510px");
		
		HorizontalPanel topPanel = new TopPanel(result.getViewer());
		topPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		rootPanel.add(topPanel, 10, 10);
		topPanel.setSize("430px", "20px");
		
		HorizontalPanel bottomPanel = new HorizontalPanel();
		bottomPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		rootPanel.add(bottomPanel, 10, 555);
		bottomPanel.setSize("719px", "20px");
		
		VerticalPanel rightPanel = new VerticalPanel();
		rootPanel.add(rightPanel, 599, 36);
		rightPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		rightPanel.setSize("130px", "513px");
		
		/*
		// for test only
		idLabel.setText(Integer.toString(user.getId()));
		nameLabel.setText(user.getName());
		genderLabel.setText((user.isMale() ? "M" : "F"));
		emailLabel.setText(user.getEmail());
		phoneLabel.setText(user.getPhone());
		affiliationLabel.setText(user.getAffiliation());
		
		leftPanel.add(idLabel);
		leftPanel.add(nameLabel);
		leftPanel.add(genderLabel);
		leftPanel.add(emailLabel);
		leftPanel.add(phoneLabel);
		leftPanel.add(affiliationLabel);
		*/
		
	}
	


	public void doWork() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getUserBoardFriends(int userID,
			AsyncCallback<ViewResult> callback) {
		//greetingService.getUserBoardFriends(userID, callback);
	}
}
