package upenn.cis550.groupf.client;

import upenn.cis550.groupf.shared.Board;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;


public class BoardWidgetPanel extends VerticalPanel {
	
	//TODO later implements View
	
	public BoardWidgetPanel(Board board) {
		setSize("150px", "150px");
		
		Label boardLabel = new Label(board.getBoardName());
		Image boardImage = new Image("/images/boardImage.png");
		boardImage.setSize("120px", "120px");
		
		add(boardImage);
		add(boardLabel);
	}

}
