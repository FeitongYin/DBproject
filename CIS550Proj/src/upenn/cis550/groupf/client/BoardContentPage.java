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

import upenn.cis550.groupf.client.event.ViewBoardEvent;
import upenn.cis550.groupf.client.event.ViewEvent;
import upenn.cis550.groupf.shared.User;
import upenn.cis550.groupf.shared.Board;
import upenn.cis550.groupf.shared.ViewResult;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Grid;

public class BoardContentPage{
	
	//System.out.println("welcome to baord content page!");
	public EventBus EVENT_BUS;
	
	
	
	public BoardContentPage(ViewResult result) {
		//this.EVENT_BUS = bus;
		//EVENT_BUS.addHandler(ViewBoardEvent.TYPE, this);
		
		// get and clear root panel
		
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
		
		Grid contentGrid = new Grid(4, 4);
		Image testImg = new Image("/images/pic.jpg");
		testImg.setSize("50px", "50px");
		contentGrid.setWidget(0, 0, testImg);
		contentGrid.setWidget(0, 1, testImg);
		mainPanel.add(contentGrid);
		
		
		
		
	}






}
