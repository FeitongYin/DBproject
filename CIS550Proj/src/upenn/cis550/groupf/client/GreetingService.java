package upenn.cis550.groupf.client;

import java.util.List;

import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	
	ViewResult login(String name, String password);
	
	boolean isUsernameExist(String username);
	
	boolean addUser(String firstName, String lastName, String email, String gender);
	
	List<String> getTags(int contentID);
	
	boolean pin(String userName, String boardID, String srcGroup, int contentID, String comment);

	ViewResult getBoardContent(int boardID);
	
	ViewResult viewBoard(String username);

}
