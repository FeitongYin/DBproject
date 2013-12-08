package upenn.cis550.groupf.client.event;

import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This represents an event to be passed along the EventBus.
 * The event is passing the login parameters, 
 * i.e. username and password to server
 *  
 * @author benwu
 *
 */
public class LoginEvent extends GwtEvent<LoginEvent.Handler> {
	private String name;
	private String password;
	
	// Whom to call when results are returned
	private AsyncCallback<ViewResult> callback; 
	
	/**
	 * GwtEvents need to make clear what their type and handler type is
	 */
	public static Type<LoginEvent.Handler> TYPE = 
			new Type<LoginEvent.Handler>();
	
	public LoginEvent(String name, String password, AsyncCallback<ViewResult> asyncCallback) {
		this.name = name;
		this.password = password;
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
		handler.processLogin(name, password, callback);
	}
	
	/**
	 * An event handler for the query request event
	 * 
	 * @author zives
	 *
	 */
	public static interface Handler extends EventHandler {
		public void processLogin(String name, String password,
				AsyncCallback<ViewResult> callback);
	}
}
