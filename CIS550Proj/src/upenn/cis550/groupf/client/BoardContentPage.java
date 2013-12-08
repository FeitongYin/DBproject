package upenn.cis550.groupf.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import upenn.cis550.groupf.client.LeftMenuPanel.BoardClickHandler;
import upenn.cis550.groupf.client.event.ViewBoardEvent;
import upenn.cis550.groupf.client.event.ViewEvent;
import upenn.cis550.groupf.shared.Content;
import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.Board;
import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Grid;

public class BoardContentPage{
	
	
	
	public BoardContentPage(ViewResult result) {

		String contentURL = null; 
		Image contentImg = null;
		
		System.out.println("welcome to Board!!!");

		RootPanel rootPanel = RootPanel.get("ContentPanel");
		rootPanel.setSize("900px", "768px");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		rootPanel.clear();
		
		HorizontalPanel mainPanel = new HorizontalPanel();
		mainPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		rootPanel.add(mainPanel, 10, 36);
		mainPanel.setSize("900px", "513px");
		
		VerticalPanel leftPanel = new LeftMenuPanel(result.getOwner(), result.getBoards(), result.getFriends());
		mainPanel.add(leftPanel);
		leftPanel.setSize("231px", "511px");
		
		VerticalPanel imagePanel = new VerticalPanel();
		mainPanel.add(imagePanel);
		//leftPanel.setSize("500px", "511px");
		
		
		for (Content content : result.getContents()) {
			System.out.println("-----------------------------This board has number of images to display------------------ ");
			System.out.println(result.getContents().size());
			contentURL = content.getContentKey();
			System.out.println(contentURL);
			contentImg = new Image(contentURL);
			contentImg.setSize("200px", "200px");
			imagePanel.add(contentImg);
			
		}
		
	
		/*
		Grid contentGrid = new Grid(4, 4);
		Image testImg = new Image("/images/pic.jpg");
		testImg.setSize("50px", "50px");
		contentGrid.setWidget(0, 0, testImg);
		contentGrid.setWidget(0, 1, testImg);
		mainPanel.add(contentGrid);
		*/
			
		
	}

}
