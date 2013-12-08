package upenn.cis550.groupf.client;

import upenn.cis550.groupf.client.event.LoginEvent;
import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class LoginPage {
	public EventBus EVENT_BUS;
	
	final Label nameLabel = new Label("Nick Name");
	final Label pswdLabel = new Label("Password");
	final TextBox nameField = new TextBox();
	final PasswordTextBox pswdField = new PasswordTextBox();
	final Button sendButton = new Button("Login");
	final Button testButton = new Button("Upload");
	
	final Grid table = new Grid(4, 3);
	
	final RootPanel loginPanel = RootPanel.get("ContentPanel");
	private final HorizontalPanel horizontalPanel = new HorizontalPanel();
	private final Label label = new Label("Pennterest");
	private final VerticalPanel verticalPanel = new VerticalPanel();
	
	public LoginPage(EventBus bus) {
		this.EVENT_BUS = bus;
		
		loginPanel.add(verticalPanel);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setStyleName("gwt-Logo");
		horizontalPanel.setSize("900px", "80px");
		horizontalPanel.add(label);
		label.setStyleName("gwt-LableLogo");
		label.setHeight("80px");
		verticalPanel.add(table);
		verticalPanel.setCellHorizontalAlignment(table, HasHorizontalAlignment.ALIGN_CENTER);
		table.setCellPadding(5);
		
		table.setWidget(0, 0, nameLabel);
		table.setWidget(0, 1, nameField);
		table.setWidget(1, 0, pswdLabel);
		table.setWidget(1, 1, pswdField);
		table.setWidget(2, 1, sendButton);
		table.setWidget(3, 1, testButton);
	}
	
	/* create a handler for clicks and keys */
	class TestHandler implements ClickHandler, KeyUpHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		public void onClick(ClickEvent event) {
			sendLoginToServer();
		}

		/**
		 * Fired when the user types in the nameField.
		 */
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				sendLoginToServer();
			}
		}

		/**
		 * Send the name from the nameField to the server and wait for a response.
		 * 
		 * Does this by firing an event on the event bus, and passing along a
		 * handler for the response.
		 */
		private void sendLoginToServer() {
			
			final String name = nameField.getText();
			final String password = pswdField.getText();

			// Then, we send the input to the server.
			sendButton.setEnabled(false);
			
			// trigger a query via the event bus (control goes back to MyApp)
			Pennterest.EVENT_BUS.fireEvent(new LoginEvent(name, password,
					new AsyncCallback<ViewResult>() {
				
				// On error, call the error dialog routine
				public void onFailure(Throwable caught) {
					postErrorDialog();
				}

				// On successful retrieval of actors, create an actors page
				public void onSuccess(ViewResult result) {
					//UserPage userPage = new UserPage(result.getOwner());
					//userPage.doWork();
				}
			}));
		}
		
	}
	
	class MyHandler implements ClickHandler, KeyUpHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		public void onClick(ClickEvent event) {
			sendLoginToServer();
		}

		/**
		 * Fired when the user types in the nameField.
		 */
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				sendLoginToServer();
			}
		}

		/**
		 * Send the name from the nameField to the server and wait for a response.
		 * 
		 * Does this by firing an event on the event bus, and passing along a
		 * handler for the response.
		 */
		private void sendLoginToServer() {
			
			final String name = nameField.getText();
			final String password = pswdField.getText();

			// Then, we send the input to the server.
			sendButton.setEnabled(false);
			
			// trigger a query via the event bus (control goes back to MyApp)
			Pennterest.EVENT_BUS.fireEvent(new LoginEvent(name, password,
					new AsyncCallback<ViewResult>() {
				
				// On error, call the error dialog routine
				public void onFailure(Throwable caught) {
					postErrorDialog();
				}

				// On successful retrieval of actors, create an actors page
				public void onSuccess(ViewResult result) {
					if (result == null) {
						postErrorDialog();
					}
					else {
						PinPage pinPage = new PinPage(EVENT_BUS, result);
						pinPage.doWork();
					}
				}
			}));
		}
		
	}
	
	public void postErrorDialog() {
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");

		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Show the RPC error message to the user
		dialogBox
				.setText("Remote Procedure Call - Failure");
		dialogBox.center();
		closeButton.setFocus(true);
	}

	public void doWork() {
		// Add a handler to send the login info to the server
		MyHandler handler = new MyHandler();
		TestHandler handler2 = new TestHandler();
		sendButton.addClickHandler(handler);
		testButton.addClickHandler(handler2);
	}
}
