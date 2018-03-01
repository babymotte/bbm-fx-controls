package net.bbmsoft.fx.controls;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import net.bbmsoft.fx.controls.skin.DirectorySelectorSkin;

public class DirectorySelector extends Control {

	public static final File INVALID_DIRECTORY = new File("");

	private File _selectedDirectory;
	private ObjectProperty<File> selectedDirectoryProperty;

	private String _title;
	private StringProperty titleProperty;

	private DirectoryChooser dirChooser;

	public DirectorySelector() {
		this(new DirectoryChooser());
	}

	public DirectorySelector(DirectoryChooser dirChooser) {
		this.dirChooser = dirChooser;
		this.addEventHandler(DragEvent.DRAG_OVER, this::dragOver);
		this.addEventHandler(DragEvent.DRAG_DROPPED, this::dragDropped);
	}

	public DirectoryChooser getDirChooser() {
		return dirChooser;
	}

	public void setDirChooser(DirectoryChooser dirChooser) {

		checkThread();

		Objects.requireNonNull(dirChooser);
		this.dirChooser = dirChooser;
	}

	public ObjectProperty<File> selectedDirectoryProperty() {

		checkThread();

		if (this.selectedDirectoryProperty == null) {
			this.selectedDirectoryProperty = new SimpleObjectProperty<File>(this._selectedDirectory);
		}
		return this.selectedDirectoryProperty;
	}

	public void setSelectedDirectory(File file) {

		checkThread();

		if (file != null && (file.exists() && !file.isDirectory())) {
			throw new IllegalArgumentException(file + " is not a directory!");
		}

		if (this.selectedDirectoryProperty == null) {
			this._selectedDirectory = file;
		} else {
			this.selectedDirectoryProperty.set(file);
		}

		this.dirChooser.setInitialDirectory(file);
	}

	public File getSelectedDirectory() {

		checkThread();

		File directory;

		if (this.selectedDirectoryProperty == null) {
			directory = this._selectedDirectory;
		} else {
			directory = this.selectedDirectoryProperty.get();
		}

		if (directory == INVALID_DIRECTORY) {
			throw new IllegalStateException("The selected path does not point to a directory!");
		}

		return directory;
	}

	public File browse() {
		return this.browse(null, null);
	}

	public File browse(String title) {
		return this.browse(title, null);
	}

	public File browse(File initialDirectory) {
		return this.browse(null, initialDirectory);
	}

	public File browse(String title, File initialDirectory) {

		Scene scene = this.getScene();

		if (scene == null) {
			throw new IllegalStateException("Directory selector is not part of a scene!");
		}

		Window window = scene.getWindow();

		if (window == null) {
			throw new IllegalStateException("Directory selector has no parent window!");
		}

		String actualTitle = title != null ? title : (this.getTitle() != null ? "Select " + this.getTitle() : null);

		this.dirChooser.setTitle(actualTitle);
		this.dirChooser.setInitialDirectory(initialDirectory);

		File dir = this.dirChooser.showDialog(window);

		if (dir != null) {
			this.setSelectedDirectory(dir);
		}

		return dir;
	}

	@Override
	protected Skin<DirectorySelector> createDefaultSkin() {
		return new DirectorySelectorSkin(this);
	}

	private void checkThread() {

		if (!Platform.isFxApplicationThread()) {
			throw new IllegalStateException(
					"Not on JavaFX Thread. Current thread is " + Thread.currentThread().getName());
		}
	}

	public void setTitle(String title) {

		checkThread();

		if (this.titleProperty == null) {
			this._title = title;
		} else {
			this.titleProperty.set(title);
		}
	}

	public String getTitle() {

		checkThread();

		if (this.titleProperty == null) {
			return this._title;
		} else {
			return this.titleProperty.get();
		}
	}

	public StringProperty titleProperty() {

		checkThread();

		if (this.titleProperty == null) {
			this.titleProperty = new SimpleStringProperty(this._title);
		}
		return this.titleProperty;
	}

	private void dragOver(DragEvent e) {

		Dragboard db = e.getDragboard();

		if (db.hasFiles()) {
			e.acceptTransferModes(TransferMode.COPY);
		}

		e.consume();
	}

	private void dragDropped(DragEvent e) {

		Dragboard db = e.getDragboard();

		boolean success = false;

		if (db.hasFiles()) {
			success = true;
			File dir = null;

			try {
				dir = getParent(db.getFiles());
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (dir != null) {
				this.setSelectedDirectory(dir);
			}
		}

		e.setDropCompleted(success);
		e.consume();
	}

	private File getParent(List<File> files) throws IOException {

		if (files == null || files.isEmpty()) {
			return null;
		}

		Iterator<File> it = files.iterator();
		String path = it.next().getCanonicalPath();

		while (it.hasNext()) {
			path = findCommonParentPath(path, it.next().getCanonicalPath());
		}

		File longestCommonParent = new File(path);
		while (!longestCommonParent.isDirectory()) {
			longestCommonParent = longestCommonParent.getParentFile();
		}

		return longestCommonParent;
	}

	private String findCommonParentPath(String pathA, String pathB) {

		int length = Math.min(pathA.length(), pathB.length());

		for (int i = 0; i < length; i++) {
			if (pathA.charAt(i) != pathB.charAt(i)) {
				return pathA.substring(0, i);
			}
		}

		return pathA.substring(0, length);
	}
}
