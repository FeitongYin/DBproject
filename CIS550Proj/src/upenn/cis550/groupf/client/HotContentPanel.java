package upenn.cis550.groupf.client;

import java.util.List;

import upenn.cis550.groupf.shared.Board;
import upenn.cis550.groupf.shared.Content;
import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;

public class HotContentPanel extends VerticalPanel {

	public HotContentPanel(User viewer, List<Board> viewersBoards, List<Content> contents) {
		setBorderWidth(3);
		setSpacing(10);
		
		Grid grid = new Grid(contents.size(), 1);
		add(grid);
		setCellHorizontalAlignment(grid, HasHorizontalAlignment.ALIGN_CENTER);
		
		int rowNumber = 0;
		for (Content content : contents) {
			VerticalPanel panel = new VerticalPanel();
			grid.setWidget(rowNumber, 0, panel);
			panel.add(new ImageWidgetView(viewer, viewersBoards, content));
			++rowNumber;
		}
	}

}
