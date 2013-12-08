package upenn.cis550.groupf.client;

import java.util.ArrayList;
import java.util.List;

import upenn.cis550.groupf.shared.Board;
import upenn.cis550.groupf.shared.Content;
import upenn.cis550.groupf.client.event.TagEvent;
import upenn.cis550.groupf.client.event.PinEvent;
import upenn.cis550.groupf.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;

public class ImageWidgetView extends VerticalPanel implements PinEvent.Handler,
		TagEvent.Handler {

	private final GreetingServiceAsync pennterestService = GWT
			.create(GreetingService.class);
	
	EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);

	private static final String IMAGE_WIDTH = "550px";
	private static final String IMAGE_HEIGHT = "400px";

	HorizontalPanel notePanel = new HorizontalPanel();
	VerticalPanel infoPanel = new VerticalPanel();
	HorizontalPanel pinPanel = new HorizontalPanel();
	HorizontalPanel ratePanel = new HorizontalPanel();
	Button pinButton = new Button("PIN IT");
	// tags in the pin dialog
	HorizontalPanel tagPanel = new HorizontalPanel();
	List<Label> tagLabels = new ArrayList<Label>();
	TextBox newTagField = new TextBox();
	TextBox commentField = new TextBox();
	
	ListBox boardCombo = new ListBox();
	Button rateButton = new Button("RATE IT");

	DialogBox pinDialogBox = new DialogBox();
	Button pinOpButton = new Button("Pin");

	User viewer;
	List<Board> viewersBoards;
	Content content;
	// tags associating with this content
	List<String> tags;
	
	Label tagLabel = new Label();

	public ImageWidgetView(User viewer, List<Board> viewersBoards,
			Content content) {
		this.viewer = viewer;
		this.viewersBoards = viewersBoards;
		this.content = content;
		
		// empty list of tags until the get tag event returns those tags
		//this.tags = new ArrayList<String>();

		EVENT_BUS.addHandler(PinEvent.TYPE, this);
		EVENT_BUS.addHandler(TagEvent.TYPE, this);

		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setStyleName("ImageWidgetView");

		Image image = new Image(content.getContentKey());
		// Image image = null;
		//
		// if (content.isCached()) {
		// //TODO temporarily set null. need to fire an event and get the file
		// back from mongoDB
		// InputStream imageFile = null;
		// image = ImageIO.read(imageFile);
		// }
		// else {
		// image = new Image(content.getContentKey());
		// }
		image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
		add(image);
		add(notePanel);

		for (Board board : viewersBoards) {
			boardCombo.addItem(board.getBoardName());
		}

		infoPanel.add(new Label("Pinned " + content.getFrequency()
				+ ((content.getFrequency() > 1) ? " times" : " time")));
		infoPanel.add(new Label("Description: " + content.getDescription()));
		
		infoPanel.add(tagLabel);

		
		// fire TagEvent and wait for the response. When they are back, put them
		// up in doWork()
		EVENT_BUS.fireEvent(new TagEvent(content.getContentID(),
				new AsyncCallback<List<String>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<String> result) {
						tags = result;
						doWork();
					}

				}));

		notePanel.add(infoPanel);
		notePanel.add(pinPanel);
		notePanel.add(ratePanel);

		pinPanel.add(pinButton);
		pinPanel.setCellVerticalAlignment(pinButton,
				HasVerticalAlignment.ALIGN_MIDDLE);
		pinPanel.setCellHorizontalAlignment(pinButton,
				HasHorizontalAlignment.ALIGN_CENTER);
		pinButton.getElement().setId("Pin" + content.getContentID());
		pinButton.setSize("100px", "35px");
		pinOpButton.getElement().setId("PinOp" + content.getContentID());

		ClickHandler handler1 = new ShowBoardHandler();
		pinButton.addClickHandler(handler1);
		ClickHandler handler2 = new PinHandler();
		pinOpButton.addClickHandler(handler2);

		ratePanel.add(rateButton);
		rateButton.setSize("100px", "35px");
		ratePanel.setCellHorizontalAlignment(rateButton,
				HasHorizontalAlignment.ALIGN_CENTER);
		ratePanel.setCellVerticalAlignment(rateButton,
				HasVerticalAlignment.ALIGN_MIDDLE);
	}
	
	// refreshes the content after TagEvent is handled
	public void doWork() {
		String tagString = "Tags: ";
		if (tags.size() == 0) {
			tagString = "No tag yet. Be the first to add tag";
		}
		else {
			for (String tag : tags) {
				tagString += tag + ", ";
			}
		}
		
		System.out.println(tagString);
		tagLabel.setText(tagString);
	}


	class ShowBoardHandler implements ClickHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		@Override
		public void onClick(ClickEvent event) {
			System.out.println("label clicked.");

			pinDialogBox.setText("Login Failed");
			pinDialogBox.setAnimationEnabled(true);

			// We can set the id of a widget by accessing its Element
			VerticalPanel dialogVPanel = new VerticalPanel();
			dialogVPanel.addStyleName("dialogVPanel");

			dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
			dialogVPanel.add(boardCombo);
			// tagPanel
			dialogVPanel.add(tagPanel);
			for (String tag : tags) {
				tagPanel.add(new Label(tag));
			}
			
			dialogVPanel.add(newTagField);
			dialogVPanel.add(commentField);
			dialogVPanel.add(pinOpButton);
			pinDialogBox.setWidget(dialogVPanel);

			// Show the RPC error message to the user
			pinDialogBox.setText("Choose board to pin and add tag");
			pinDialogBox.center();

		}
	}

	class PinHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			// pinButton.setEnabled(false);
			pinDialogBox.hide();
			int clickedIndex = boardCombo.getSelectedIndex();
			// TODO get the real comment, get fake comment for now
			String comment = "Good Pic";
			pinContent(viewer, viewersBoards.get(clickedIndex), content,
					comment);

			System.out.println("PinOp button hit");
		}

		private void pinContent(User viewer, Board board, Content content,
				String comment) {
			EVENT_BUS.fireEvent(new PinEvent(viewer, board, content,
					comment, new AsyncCallback<Boolean>() {

						// On error, call the error dialog routine
						public void onFailure(Throwable caught) {
							// TODO add failure response

						}

						@Override
						public void onSuccess(Boolean result) {
							
						}
					}));
		}
	}

	@Override
	public void pin(String userName, String boardID, String srcGroup, int contentID, String comment,
			AsyncCallback<Boolean> callback) {
		pennterestService.pin(userName, boardID, srcGroup, contentID, comment, callback);
	}

	@Override
	public void getTags(int contentID, AsyncCallback<List<String>> callback) {
		pennterestService.getTags(contentID, callback);
	}
}
