package control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import data.*;

/* Note: INSTANCE is created immediately at start-up; it follows
 * that all windows (as empty shells) are also created immediately.
 */
public enum Control {
	INSTANCE;
	private static final Logger LOG
	   = Logger.getLogger(Start.class.getName());
	static {
		LOG.info("control started");
	}
	private static final int PUBLIC = 0;
	private static final int PRIVATE = 1;
	private static final int ADMIN = 2;
	Stage primaryStage;

	public void setStage(Stage st) {
		primaryStage = st;
	}

	public void setStart(Start start) {
		this.start = start;
		//added to allWindows after being initialized
		allWindows.add(start);
	}

	// windows managed by Control
	private Start start;
	private Login login = Login.INSTANCE;
	private AdminOnly adminOnly = AdminOnly.INSTANCE;
	private Private privateWindow = Private.INSTANCE;
	private Public1 publicWindow1 = Public1.INSTANCE;
	private Public2 publicWindow2 = Public2.INSTANCE;
	private HashMap<GenericWindow, Integer> accessRules = new HashMap<>();
	
	//object initialization block
	{
		accessRules.put(login, PUBLIC);
		accessRules.put(privateWindow, PRIVATE);
		accessRules.put(adminOnly, ADMIN);
		accessRules.put(publicWindow1, PUBLIC);
		accessRules.put(publicWindow2, PUBLIC);
	}
	private static class UserState {
		private static boolean isLoggedIn = false;
		private static boolean hasAdminAccess = false;
		private static GenericWindow currentTarget = null;
		private static void reset() {
			isLoggedIn = false;
			hasAdminAccess = false;
			currentTarget = null;
		}
	}
	
	@SuppressWarnings("serial")
	private List<GenericWindow> allWindows = new ArrayList<GenericWindow>() {
		{
	       add(login); add(adminOnly); add(privateWindow); add(publicWindow1); add(publicWindow2);
		}
	};
	public void hideAll() {
		for(GenericWindow w : allWindows) {
			w.clearMessages();
			w.hide();
		}
		primaryStage.hide();
	}
	//convenience methods
	private boolean requiresAdmin(GenericWindow window) {
		if(window == null) return false;
		return accessRules.get(window) !=null && accessRules.get(window) >= ADMIN;
	}

	private boolean requiresNoLogin(GenericWindow window) {
		if(window == null) return true;
		return accessRules.get(window) == null || accessRules.get(window) == PUBLIC;
	}

	public void backToStart() {
		hideAll();
		start.clearMessages();
		primaryStage.show();
	}
	
	private class LogoutHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			UserState.reset();
			start.displayInfo("Logout successful");
		}
	}
	
	public LogoutHandler getLogoutHandler() {
		return new LogoutHandler();
	}

	private class RequestLoginHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			LOG.info("login handler");
			if(!(login.initialized())) login.init();
			clearLoginWindow();
			hideAll();
			UserState.currentTarget = login;
			login.show();
		}
	}

	public RequestLoginHandler getRequestLoginHandler() {
		return new RequestLoginHandler();
	}

	private class SubmitLoginHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			login();
		}
		void login() {
			String username = login.getUserName();
			String password = login.getPassword();
			
			//Two steps:
			// 1. Update state
			// 2. Display results
			boolean alreadyLoggedIn = UserState.isLoggedIn;
			if (username.equals(PrivateLoginData.ADMIN_LOGIN) 
					&& password.equals(PrivateLoginData.ADMIN_PWD)) {
				UserState.isLoggedIn = true;
				UserState.hasAdminAccess = true;
			} else if (username.equals(PrivateLoginData.CUST_LOGIN) 
					&& password.equals(PrivateLoginData.CUST_PWD)) {
				UserState.isLoggedIn = true;
				UserState.hasAdminAccess = false;
			} else {
				UserState.isLoggedIn = false;
				UserState.hasAdminAccess = false;
			}
			//If user is trying to log in directly, need to show messages: 
			//    if already logged in, show message "already logged in"
			//    else if log in just now succeeded, "log in successful"
			//    else "login failed"'
			if(directLogin()) {
				handleDirectLogin(alreadyLoggedIn);
				return;
			} 
			//Make sure target has been initialized
			if(!UserState.currentTarget.initialized()) {
				UserState.currentTarget.init();
			}
			//Logic for handling accessibility
			if (requiresNoLogin(UserState.currentTarget)) {
				hideAll();
				UserState.currentTarget.show();
			} 
			
			else { //login required
				if(!UserState.isLoggedIn) {
					hideAll();
					login.show();
					login.displayError("Login failed");
				}
				else if (requiresAdmin(UserState.currentTarget)) {
					handleAdminAccess();
				}
				else {  //only ordinary login required; show target
					hideAll();
					UserState.currentTarget.show();
				}
			}
		}
		
		boolean directLogin() {
			return login == UserState.currentTarget;
		}
		//Assumes user has valid login. This method checks
		//whether user has admin access and responds with 
		//appropriate actions
		private void handleAdminAccess() {
			if (UserState.hasAdminAccess) {
				hideAll();
				UserState.currentTarget.show();
			} else {
				hideAll();
				login.show();
				login.displayError("Access not authorized.");
			}
		}
		void handleDirectLogin(boolean alreadyLoggedIn) {
			if(alreadyLoggedIn) login.displayInfo("You are already logged in");
			else if(UserState.isLoggedIn) login.displayInfo("Login successful"); 
			else login.displayError("Login failed");
		}
	}

	public SubmitLoginHandler getSubmitLoginHandler() {
		return new SubmitLoginHandler();
	}
	
	private class Public1Handler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent evt) {
			System.out.println("here");
			hideAll();
			if(!publicWindow1.initialized()) publicWindow1.init();
			publicWindow1.show();
		}
	}

	public Public1Handler getPublic1Handler() {
		return new Public1Handler();
	}

	private class Public2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			hideAll();
			if(!publicWindow2.initialized()) publicWindow2.init();
			publicWindow2.show();
		}
	}

	public Public2Handler getPublic2Handler() {
		return new Public2Handler();
	}

	private class PrivateHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			if(!privateWindow.initialized()) privateWindow.init();
			if (!UserState.isLoggedIn) {
				UserState.currentTarget = privateWindow;
				hideAll();
				if(!(login.initialized())) login.init();
				clearLoginWindow();
				login.show();
			} else {
				hideAll();
				privateWindow.show();
			}
		}
	}

	public PrivateHandler getPrivateHandler() {
		return new PrivateHandler();
	}

	private class AdminOnlyHandler implements EventHandler<ActionEvent> {
		AdminOnlyHandler(GenericWindow origin) {
			this.origin = origin;
		}
		private GenericWindow origin;
		public void handle(ActionEvent evt) {
			
			
			//if is already logged in but not an admin, "not authorized"
			if(!UserState.hasAdminAccess && UserState.isLoggedIn) {
				origin.displayError("Access not authorized");
			}
			//if not logged in, show login window
			else if(!UserState.isLoggedIn) {
				UserState.currentTarget = adminOnly;
				hideAll();
				if(!(login.initialized())) login.init();
				clearLoginWindow();
				login.show();				
			} 
			//has admin access, so show admin window
			else {
				if(!adminOnly.initialized()) adminOnly.init();
				hideAll();
				adminOnly.show();
			}
		}
	}

	public AdminOnlyHandler getAdminOnlyHandler(GenericWindow origin) {
		return new AdminOnlyHandler(origin);
	}
	
	private void clearLoginWindow() {
		if(login != null) {
			login.clearFields();
			login.clearMessage();
		}
	}
	

}
