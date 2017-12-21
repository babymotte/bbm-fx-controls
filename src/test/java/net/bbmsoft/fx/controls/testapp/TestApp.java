package net.bbmsoft.fx.controls.testapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.bbmsoft.fx.controls.DirectorySelector;

public class TestApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		DirectorySelector root = new DirectorySelector();
		root.selectedDirectoryProperty()
				.addListener((o, ov, nv) -> System.out.println("Property value: " + nv.getAbsolutePath()));
		root.selectedDirectoryProperty().addListener(
				(o, ov, nv) -> System.out.println("Directory: " + root.getSelectedDirectory().getAbsolutePath()));
		root.setTitle("test directory");

		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
