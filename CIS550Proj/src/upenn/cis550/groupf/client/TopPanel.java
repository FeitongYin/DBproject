package upenn.cis550.groupf.client;

import upenn.cis550.groupf.shared.User;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class TopPanel extends HorizontalPanel {

	final Label nameLabel;
	
	public TopPanel(User user) {
		nameLabel = new Label(user.getName());
		nameLabel.setHorizontalAlignment(ALIGN_RIGHT);
		add(nameLabel);
	}
	
}
