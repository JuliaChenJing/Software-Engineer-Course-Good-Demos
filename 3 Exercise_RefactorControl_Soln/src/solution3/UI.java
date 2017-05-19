package solution3;

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
 * In this solution, Control is a singleton
 * and is even more loosely coupled with UI. In this
 * solution, Control is not aware of UI at all; access
 * to UI is achieved through the Callback interface
 * (which the UI implements). 
 * 
 * @author pcorazza
 *
 */
public class UI extends Application implements Callback {

    public static void main(String[] args) {
        launch(args);
    }
    
    Label messageBar = new Label("");
    private TextField printField;
    public String getText() {
    	return printField.getText();
    }
    public void setText(String s) {
    	messageBar.setText(s);
    }
   
    @Override
    public void start(Stage primaryStage) {
    	Control.INSTANCE.setCallback(this);
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

        btn.setOnAction(Control.INSTANCE.getPrintHandler());
        
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
