package solution2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Control {
	private UI ui;
	public Control(UI ui) {
		this.ui = ui;
	}
	private Printer printer = new Printer();
	private class PrintHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			String text = ui.getText();
			printer.print(text);
			ui.setMessage("Data printed to console");
			
		}
	}
	public PrintHandler getPrintHandler() {
		return new PrintHandler();
	}
}
