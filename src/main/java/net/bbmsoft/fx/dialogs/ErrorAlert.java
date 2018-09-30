package net.bbmsoft.fx.dialogs;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ErrorAlert extends Alert {

	public ErrorAlert(String title, String message, String contentText) {
		this(title, message, contentText, null);
	}
	
	public ErrorAlert(String title, String message, String contentText, Throwable e) {

		super(AlertType.ERROR);

		setTitle(title);
		setHeaderText(message);
		setContentText(contentText);

		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String exceptionText = sw.toString();
			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);
			textArea.setWrapText(true);
			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);
			this.getDialogPane().setExpandableContent(textArea);
		}
	}

	public ErrorAlert(String title, String message, Throwable e) {
		this(title != null ? title : "Error", message,
				e != null ? (e.getMessage() != null ? e.getMessage() : "Stack trace:") : null, e);
	}


	public ErrorAlert(String message, Throwable e) {
		this(null, message, e);
	}
}
