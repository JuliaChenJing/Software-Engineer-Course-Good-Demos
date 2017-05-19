package solution2;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * In this solution, Control is not a singleton
 * and is loosely coupled with UI. Control obtains
 * the value of the UI textfield with a getter that
 * returns a String (not a UI component) and Control
 * sets the value of the messageBar in UI using
 * a String setter. This looser coupling makes it
 * possible to modify the UI (like changing the messageBar
 * from a Label to a Text) without affecting implementation
 * of Control.
 * 
 * @author pcorazza
 *
 */
public class UI extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private Control control;
    private Label messageBar = new Label("");
    private TextField printField;
    public String getText() {
    	return printField.getText();
    }
    public void setMessage(String msg) {
    	messageBar.setText(msg);
    }

    @Override
    public void start(Stage primaryStage) {
    	control = new Control(this);
    	messageBar.setTextFill(Color.DARKBLUE);
    	messageBar.setTextAlignment(TextAlignment.RIGHT);
        primaryStage.setTitle("Print App");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Enter Data");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(scenetitle, HPos.CENTER);
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("To Print:");
        grid.add(userName, 0, 2);

        printField = new TextField();
        grid.add(printField, 1, 2);

        Button btn = new Button("Print");
        Button clearBtn = new Button("Clear");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(btn);
        hbBtn.getChildren().add(clearBtn);
        grid.add(hbBtn, 1, 4);

        
        grid.add(messageBar, 1, 6);
        GridPane.setHalignment(messageBar, HPos.RIGHT);

        btn.setOnAction(control.getPrintHandler());
        
        clearBtn.setOnAction(evt -> {  
        	printField.clear();
            messageBar.setText("Data cleared");       
        });

        //Scene scene = new Scene(grid, 300, 200);
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
