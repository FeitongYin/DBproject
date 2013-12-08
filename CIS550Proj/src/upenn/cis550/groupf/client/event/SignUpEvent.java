package upenn.cis550.groupf.client.event;

import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SignUpEvent extends GwtEvent<SignUpEvent.Handler> {
	private String username;
	private String password;
	private String email;
	private AsyncCallback<Boolean> callback;
	
	public SignUpEvent(String username, String password, String email, AsyncCallback<Boolean> asyncCallback) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.callback = asyncCallback;
	}
	
	public static Type<SignUpEvent.Handler> TYPE = 
			new Type<SignUpEvent.Handler>();
	
	public static interface Handler extends EventHandler {
		public void processValidation(String username, AsyncCallback<Boolean> callback);
		
		//public void processSignUp(String username, String password, String email,
		//		AsyncCallback<ViewResult> callback);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.processValidation(username, callback);
	}
}
