package solution3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public enum Control {
	INSTANCE;
	private Callback c;
	public void setCallback(Callback c) {
		this.c = c;
	}
	private Printer printer = new Printer();
	private class PrintHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			printer.print(c.getText());
			c.setText("Data printed to console");
		}
	}
	public PrintHandler getPrintHandler() {
		return new PrintHandler();
	}
	
}
