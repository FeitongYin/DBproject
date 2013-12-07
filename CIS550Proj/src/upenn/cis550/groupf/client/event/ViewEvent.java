package upenn.cis550.groupf.client.event;

import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ViewEvent extends GwtEvent<ViewEvent.Handler> {
	private int userID;
	
	// Whom to call when results are returned
	private AsyncCallback<ViewResult> callback;

	/**
	 * GwtEvents need to make clear what their type and handler type is
	 */
	public static Type<ViewEvent.Handler> TYPE = new Type<ViewEvent.Handler>();

	public ViewEvent(int userID, AsyncCallback<ViewResult> callback) {
		this.userID = userID;
		this.callback = callback;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * When an event is fired, we call this method
	 */
	@Override
	protected void dispatch(Handler handler) {
		handler.getUserBoardFriends(userID, callback);
	}

	/**
	 * An event handler for the query request event
	 * 
	 * @author benwu
	 * 
	 */
	public static interface Handler extends EventHandler {
		public void getUserBoardFriends(int userID,
				AsyncCallback<ViewResult> callback);

	}
}
