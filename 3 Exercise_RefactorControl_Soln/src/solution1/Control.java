package solution1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public enum Control {
	INSTANCE;
	private UI ui;
	public void setUI(UI ui) {
		this.ui = ui;
	}
	private Printer printer = new Printer();
	private class PrintHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			printer.print(ui.getTextField().getText());
			ui.getMessageBar().setText("Data printed to console");
		}
	}
	public PrintHandler getPrintHandler() {
		return new PrintHandler();
	}
	
}
