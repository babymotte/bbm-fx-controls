package net.bbmsoft.fx.helpers;

import java.util.Objects;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Window;

public class WindowHelper {

	public static void repositionAfterResize(Scene scene, Pos repositioningAnchor, Runnable resizeOperation) {

		Window window = scene.getWindow();
		if(window == null) {
			return;
		}
		
		repositionAfterResize(window, repositioningAnchor, resizeOperation);
	}
	
	public static void repositionAfterResize(Window window, Pos repositioningAnchor, Runnable resizeOperation) {

		Objects.requireNonNull(window);
		Objects.requireNonNull(repositioningAnchor);
		Objects.requireNonNull(resizeOperation);
		
		Bounds originalBounds = getBounds(window);
		try {
			resizeOperation.run();
		} finally {
			doRepositionAfterResize(window, repositioningAnchor, originalBounds, getBounds(window));
		}
	}

	private static void doRepositionAfterResize(Window window, Pos repositioningAnchor, Bounds originalBounds,
			Bounds newBounds) {

		if (originalBounds == null || newBounds == null) {
			return;
		}

		if (originalBounds.equals(newBounds)) {
			return;
		}

		double deltaWidth = newBounds.getWidth() - originalBounds.getWidth();
		double deltaHeight = newBounds.getHeight() - originalBounds.getHeight();

		double originalX = originalBounds.getMinX();
		double originalY = originalBounds.getMinY();

		double newX, newY;

		switch (repositioningAnchor) {
		case BOTTOM_CENTER:
			newX = originalX - deltaWidth / 2;
			newY = originalY - deltaHeight;
			break;
		case BOTTOM_LEFT:
			newX = originalX;
			newY = originalY - deltaHeight;
			break;
		case BOTTOM_RIGHT:
			newX = originalX - deltaWidth;
			newY = originalY - deltaHeight;
			break;
		case CENTER:
			newX = originalX - deltaWidth / 2;
			newY = originalY - deltaHeight / 2;
			break;
		case CENTER_LEFT:
			newX = originalX;
			newY = originalY - deltaHeight / 2;
			break;
		case CENTER_RIGHT:
			newX = originalX - deltaWidth;
			newY = originalY - deltaHeight / 2;
			break;
		case TOP_CENTER:
			newX = originalX - deltaWidth / 2;
			newY = originalY;
			break;
		case TOP_LEFT:
			newX = originalX;
			newY = originalY;
			break;
		case TOP_RIGHT:
			newX = originalX - deltaWidth;
			newY = originalY;
			break;
		case BASELINE_CENTER:
		case BASELINE_LEFT:
		case BASELINE_RIGHT:
			throw new IllegalArgumentException();
		default:
			throw new IllegalStateException("Unknow Pos: " + repositioningAnchor);
		}
		
		window.setX(newX);
		window.setY(newY);
	}

	private static Bounds getBounds(Window window) {
		return new BoundingBox(window.getX(), window.getY(), window.getWidth(), window.getHeight());
	}
}
