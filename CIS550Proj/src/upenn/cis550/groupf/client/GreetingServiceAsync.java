package upenn.cis550.groupf.client;

import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {

	void login(String name, String password, AsyncCallback<ViewResult> callback);

	void addUser(String firstName, String lastName, String email, String sex,
			AsyncCallback<User> callback);
	
	void getBoardContent(int boardID, AsyncCallback<ViewResult> callback);

	void viewBoard(String username, AsyncCallback<ViewResult> callback);

	
}
