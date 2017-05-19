package control;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import ui.*;
import java.util.*;

import data.Data;
import data.Logins;

public enum Control {
	INSTANCE;
	Start start;
	Stage primaryStage;
	Login login;
	String username;
	Grades grades;
	Remarks remarks;
	private boolean isLoggedIn = false;
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean b) {
		isLoggedIn = b;
	}
	
	public void setStart(Start st) {
		this.start = st;
	}
	
	public void setStage(Stage ps) {
		primaryStage=ps;
	}
	public void backToStart(Stage stage) {
		stage.hide();
		start.setMessage("");
		primaryStage.show();
	}
	
	private class LogoutHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			username = null;
			isLoggedIn = false;
			start.setMessage("Logout successful");
		}
	}
	public LogoutHandler getLogoutHandler() {
		return new LogoutHandler();
	}
	
	private class RequestLoginHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			login = new Login();
			primaryStage.hide();
			login.show();
		}
	}
	public RequestLoginHandler getRequestLoginHandler() {
		return new RequestLoginHandler();
	}
	
	private class RequestGradesHandler implements EventHandler<ActionEvent>, Callback {
		public void performAction() {
			grades = new Grades();
			HashMap<String, String> gr 
				= Data.dataMap.get(username).getGrades();
			StringBuilder sb = new StringBuilder();
			for(String key : gr.keySet()) {
				sb.append(key + " : " + gr.get(key) + "\n");
			}
			grades.setGrades(sb.toString());
			grades.setTitle("Grades for " + username);
			grades.setHeading("Grades for " + username);
			grades.show();
		}
		public void handle(ActionEvent evt) {
			if(isLoggedIn) {
				performAction();
			} else {
				login = new Login(this);
				primaryStage.hide();
				login.show();
			}
		}
	}
	public RequestGradesHandler getRequestGradesHandler() {
		return new RequestGradesHandler();
	}
	
	private class RequestRemarksHandler implements EventHandler<ActionEvent>, Callback {
		public void performAction() {
			remarks = new Remarks();
			HashMap<String, String> rem 
				= Data.dataMap.get(username).getTeacherRemarks();
			StringBuilder sb = new StringBuilder();
			for(String key : rem.keySet()) {
				sb.append(key + " : " + rem.get(key) + "\n");
			}
			remarks.setRemarks(sb.toString());
			remarks.setTitle("Teacher Remarks for " + username);
			remarks.setHeading("Teacher Remarks for " + username);
			remarks.show();
		}
		public void handle(ActionEvent evt) {
			if(isLoggedIn) {
				performAction();
			} else {
				login = new Login(this);
				primaryStage.hide();
				login.show();
			}
		}
	}
	public RequestRemarksHandler getRequestRemarksHandler() {
		return new RequestRemarksHandler();
	}
	
	private class SubmitLoginHandler implements EventHandler<ActionEvent> {
		SubmitLoginHandler(Callback handler) {
			this.callbackHandler = handler;
		}
		private Callback callbackHandler;
		public void handle(ActionEvent evt) {
			String username = login.getUserName();
			String password = login.getPassword();
			if(!Logins.foundUserNamePwd(username, password)) {
				login.setMessage("Login failed.");
			} else {
				Control.this.username = username;
				Control.this.isLoggedIn = true;
				if(callbackHandler == null) { //no callback behavior needed
					login.setMessage("Login successful");
				} else { //invoke callbackHandler
					login.hide();
					callbackHandler.performAction();
				}
			}
		}
	}
	public SubmitLoginHandler getSubmitLoginHandler(Callback handler) {
		return new SubmitLoginHandler(handler);
	}
	
	
}
