package upenn.cis550.groupf.client.event;

import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.Board;
import upenn.cis550.groupf.shared.Content;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class PinEvent extends GwtEvent<PinEvent.Handler> {
	private User viewer;
	private Board board;
	private Content content;
	private String comment;

	private AsyncCallback<Boolean> callback;

	/**
	 * GwtEvents need to make clear what their type and handler type is
	 */
	public static Type<PinEvent.Handler> TYPE = new Type<PinEvent.Handler>();

	public PinEvent(User viewer, Board board, Content content, String comment,
			AsyncCallback<Boolean> callback) {
		this.viewer = viewer;
		this.board = board;
		this.content = content;
		this.comment = comment;
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
		handler.pin(viewer.getUserName(), board.getBoardID(),
				content.getSrcGroup(), content.getContentID(), comment,
				callback);
	}

	/**
	 * An event handler for the query request event
	 * 
	 * @author benwu
	 * 
	 */
	public static interface Handler extends EventHandler {
		public void pin(String userName, String boardID, String srcGroup,
				int contentID, String comment, AsyncCallback<Boolean> callback);

	}
}
