package upenn.cis550.groupf.client.event;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TagEvent  extends GwtEvent<TagEvent.Handler> {
	private int contentID;
	
	// Whom to call when results are returned
	private AsyncCallback<List<String>> callback; 
	
	/**
	 * GwtEvents need to make clear what their type and handler type is
	 */
	public static Type<TagEvent.Handler> TYPE = 
			new Type<TagEvent.Handler>();
	
	public TagEvent(int contentID, AsyncCallback<List<String>> asyncCallback) {
		this.contentID = contentID;
		this.callback = asyncCallback;
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
		handler.getTags(contentID, callback);
	}
	
	/**
	 * An event handler for the query request event
	 * 
	 * @author zives
	 *
	 */
	public static interface Handler extends EventHandler {
		public void getTags(int contentID,
				AsyncCallback<List<String>> callback);
	}
}
