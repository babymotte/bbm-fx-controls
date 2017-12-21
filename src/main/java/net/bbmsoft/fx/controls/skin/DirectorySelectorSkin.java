package net.bbmsoft.fx.controls.skin;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import net.bbmsoft.fx.controls.DirectorySelector;

public class DirectorySelectorSkin extends SkinBase<DirectorySelector> {

	private final HBox root;
	private final TextField pathField;
	private final Button browseButton;
	private final ChangeListener<File> selectedDirectoryListener;
	private final ChangeListener<String> titleListener;

	public DirectorySelectorSkin(DirectorySelector control) {

		super(control);

		this.root = new HBox();
		this.pathField = new TextField();
		this.browseButton = new Button("Browse");

		this.browseButton.setOnAction(e -> {
			File selectedDirectory = getCurrentFile();
			File existingParent = getExistingParent(selectedDirectory);
			control.browse(control.getDirChooser().getTitle(), existingParent);
		});

		this.pathField.setOnAction(e -> updateDirectory(control));

		this.pathField.focusedProperty().addListener((o, ov, nv) -> {
			if (!nv) {
				updateDirectory(control);
			}
		});

		this.selectedDirectoryListener = (o, ov, nv) -> updatePathField(nv);
		control.selectedDirectoryProperty().addListener(this.selectedDirectoryListener);
		updatePathField(control.getSelectedDirectory());

		this.titleListener = (o, ov, nv) -> this.pathField.setPromptText(nv != null && !nv.trim().isEmpty() ? nv + " path" : null);
		control.titleProperty().addListener(titleListener);
		this.pathField.setPromptText(control.getTitle());

		HBox.setHgrow(pathField, Priority.ALWAYS);
		this.root.setSpacing(8);
		this.root.getChildren().addAll(this.pathField, this.browseButton);

		this.getChildren().add(root);
	}

	private void updatePathField(File nv) {
		if (nv == null) {
			this.pathField.setText(null);
		} else if (nv != DirectorySelector.INVALID_DIRECTORY) {
			this.pathField.setText(nv.getAbsolutePath());
		}
	}

	private File getExistingParent(File selectedDirectory) {

		if (selectedDirectory == null) {
			return null;
		}

		File file = selectedDirectory;

		while (!file.isDirectory()) {
			file = file.getParentFile();
		}

		return file;
	}

	private void updateDirectory(DirectorySelector control) {

		File dir = getCurrentFile();

		if (dir != null && dir.exists() && !dir.isDirectory()) {
			control.setSelectedDirectory(DirectorySelector.INVALID_DIRECTORY);
		} else {
			control.setSelectedDirectory(dir);
		}
	}

	private File getCurrentFile() {

		String text = pathField.getText();
		File dir = text == null || text.trim().isEmpty() ? null : new File(text);

		return dir;
	}

	@Override
	public void dispose() {
		this.getSkinnable().selectedDirectoryProperty().removeListener(this.selectedDirectoryListener);
		super.dispose();
	}

}
