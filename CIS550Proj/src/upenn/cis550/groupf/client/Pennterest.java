package upenn.cis550.groupf.client;

import upenn.cis550.groupf.client.event.LoginEvent;
import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * @author benwu
 */
public class Pennterest implements EntryPoint, LoginEvent.Handler {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync loginService = GWT
			.create(GreetingService.class);

	/**
	 * The "event bus" lets different modules in a GWT application communicate,
	 * typically to send event messages.  In our case we'll use it to trigger
	 * requests for Remote Procedure Calls.
	 */
	public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// load event handlers into eventbus
		EVENT_BUS.addHandler(LoginEvent.TYPE, this);
		
		LoginPage login = new LoginPage(EVENT_BUS);
		login.doWork();
	}

	@Override
	public void processLogin(String name, String password,
			AsyncCallback<ViewResult> callback) {
		loginService.login(name, password, callback);
	}
	
}
