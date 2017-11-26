
package sql_injection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import connect.ConnectManagerMySql;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SSNLookupGUI extends Application {
	static final String userInput = "' or '1'='1"; //delete outer quotes when entering in gui
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sql Injection Example");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("SSN Lookup");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("First Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        //userTextField.setPrefColumnCount(10);
        //userTextField.setPrefWidth(30);
        grid.add(userTextField, 1, 1);

        Label pw = new Label("SSN for this Customer:");
        grid.add(pw, 0, 2);
        //grid.setGridLinesVisible(true) ;

        TextArea pwBox = new TextArea();
        grid.add(pwBox, 1, 2);

        Button notbtn = new Button("Not Prepared");
        Button btn = new Button("Prepared");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(notbtn);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        notbtn.setOnAction(evt -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Request submitted"); 
            String firstName = userTextField.getText();
            //sql stuff
            String query = "SELECT ssn FROM Customer WHERE fname = '%s'";
    		Connection conn = null;
    		try {
    			conn = ConnectManagerMySql.getConnection(ConnectManagerMySql.DB.ACCT);
    			Statement stmt = conn.createStatement();
    			query = String.format(query, firstName);
    			System.out.println(query);
    			ResultSet rs = stmt.executeQuery(query);
    			//System.out.println("\nResult from Database:\n");
    			StringBuilder sb = new StringBuilder();
    			while(rs.next()) {
    				//System.out.println(rs.getString("ssn"));
    				sb.append(rs.getString("ssn") + "\n");
    				
    			}
    			pwBox.setText(sb.toString());
    		} catch(SQLException e) {
    			e.printStackTrace();
    		} finally {
    			try {
    				if(conn != null && !conn.isClosed()) {
    					conn.close();
    				}
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
            
            //return
            
        });
        btn.setOnAction(evt -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Request submitted"); 
            String firstName = userTextField.getText();
            //sql stuff
            Connection con = null;
    		String query = "SELECT ssn FROM customer WHERE fname = ?";
    		try {
    			con = ConnectManagerMySql.getConnection(ConnectManagerMySql.DB.ACCT);
    			PreparedStatement stmt = con.prepareStatement(query);
    			stmt.setString(1, firstName);
    			System.out.println(stmt.toString());
    			ResultSet rs = stmt.executeQuery();
    			//System.out.println("\nResult from Database:\n");
    			StringBuilder sb = new StringBuilder();
    			while(rs.next()) {
    				sb.append(rs.getString("ssn"));
    			}
    			String output = sb.toString();
    			if(output.isEmpty()) pwBox.setText("not found");
    			else pwBox.setText(output);
    		} catch(SQLException e) {
    			e.printStackTrace();
    		} finally {
    			try {
    				if(con != null && !con.isClosed()) {
    					con.close();
    				}
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
            
            
            //return
            
        });

        //Scene scene = new Scene(grid, 300, 200);
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}