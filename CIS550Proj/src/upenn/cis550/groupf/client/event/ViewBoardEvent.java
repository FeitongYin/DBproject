package upenn.cis550.groupf.client.event;

import upenn.cis550.groupf.client.event.ViewBoardEvent.Handler;
import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This represents an event to be passed along the EventBus
 * @author FeitongYin
 *
 */
public class ViewBoardEvent extends GwtEvent<ViewBoardEvent.Handler> {
	private String boardID = null;
	
	// Whom to call when results are returned
	private AsyncCallback<ViewResult> callback;
	
	/**
	 * GwtEvents need to make clear what their type and handler type is
	 */
	public static Type<ViewBoardEvent.Handler> TYPE = new Type<ViewBoardEvent.Handler>();


	public ViewBoardEvent (String boardID, AsyncCallback<ViewResult> callback) {
		this.boardID = boardID;
		this.callback = callback;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
	
	/**
	 * when an event is fired, we call this method
	 * 
	 */
	@Override
	protected void dispatch(Handler handler) {
		handler.processGetBoard(boardID, callback);
	}
	
	 
	/**
	 * An event handler for the query request even
	 * 
	 * @author FeitongYin
	 *
	 */
	public static interface Handler extends EventHandler {
		public void processGetBoard(String boardID,
				AsyncCallback<ViewResult> callback);

		void processViewUser(String friendName,
				AsyncCallback<ViewResult> callback);
		

	}
		
	
}
