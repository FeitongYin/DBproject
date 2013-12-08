package upenn.cis550.groupf.client;

import java.util.List;

import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {

	void login(String name, String password, AsyncCallback<ViewResult> callback);

	void addUser(String firstName, String lastName, String email,
			String gender, AsyncCallback<Boolean> callback);

	void isUsernameExist(String username, AsyncCallback<Boolean> callback);

	void getTags(int contentID, AsyncCallback<List<String>> callback);

	void pin(String userName, String boardID, String srcGroup, int contentID, String comment,
			AsyncCallback<Boolean> callback);

	void viewBoard(String username, AsyncCallback<ViewResult> callback);

	void isUsernameExist(String username, AsyncCallback<Boolean> callback);

	
}
