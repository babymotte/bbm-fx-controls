package net.bbmsoft.fx.controls.testapp;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.bbmsoft.fx.controls.ResizableCanvas;

public class ResiableCanvasTestApp extends Application {

	private ResizableCanvas resizableCanvas;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.resizableCanvas = new ResizableCanvas();
		resizableCanvas.widthProperty().addListener(this::repaint);
		resizableCanvas.heightProperty().addListener(this::repaint);
		
		primaryStage.setScene(new Scene(new StackPane(resizableCanvas)));
		primaryStage.show();
	}
	
	private void repaint(Observable o) {
		
		GraphicsContext g = this.resizableCanvas.getGraphicsContext2D();
		g.clearRect(0, 0, resizableCanvas.getWidth(), resizableCanvas.getHeight());
		g.setStroke(Color.RED);
		g.strokeLine(0, 0, resizableCanvas.getWidth(), resizableCanvas.getHeight());
		g.strokeLine(resizableCanvas.getWidth(), 0, 0, resizableCanvas.getHeight());
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
