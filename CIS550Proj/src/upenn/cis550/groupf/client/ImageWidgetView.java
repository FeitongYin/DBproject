package upenn.cis550.groupf.client;

import java.io.InputStream;

import javax.imageio.ImageIO;

import upenn.cis550.groupf.shared.Content;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class ImageWidgetView extends VerticalPanel {
	
	private static final String IMAGE_WIDTH = "550px";
	private static final String IMAGE_HEIGHT = "400px";

	public ImageWidgetView(Content content) {
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setStyleName("ImageWidgetView");
		HorizontalPanel notePanel = new HorizontalPanel();
		VerticalPanel infoPanel = new VerticalPanel();
		HorizontalPanel pinPanel = new HorizontalPanel();
		HorizontalPanel ratePanel = new HorizontalPanel();
		Label pinLabel = new Label("PIN IT");
		Label rateLabel = new Label("RATE IT");
		rateLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		Image image = new Image(content.getContentKey());
//		Image image = null;
//		
//		if (content.isCached()) {
//			//TODO temporarily set null. need to fire an event and get the file back from mongoDB
//			InputStream imageFile = null;
//			image = ImageIO.read(imageFile);
//		}
//		else {
//			image = new Image(content.getContentKey());
//		}
		image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
		add(image);
		add(notePanel);
		
		infoPanel.add(new Label("Pinned " + content.getFrequency() + ((content.getFrequency() > 1) ? " times" : " time")));
		infoPanel.add(new Label("Description: " + content.getDescription()));
		
		notePanel.add(infoPanel);
		notePanel.add(pinPanel);
		notePanel.add(ratePanel);
		
		pinPanel.add(pinLabel);
		pinPanel.setCellVerticalAlignment(pinLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		pinPanel.setCellHorizontalAlignment(pinLabel, HasHorizontalAlignment.ALIGN_CENTER);
		pinLabel.setSize("100px", "35px");
		ratePanel.add(rateLabel);
		rateLabel.setSize("100px", "35px");
		ratePanel.setCellHorizontalAlignment(rateLabel, HasHorizontalAlignment.ALIGN_CENTER);
		ratePanel.setCellVerticalAlignment(rateLabel, HasVerticalAlignment.ALIGN_MIDDLE);
	}
}
