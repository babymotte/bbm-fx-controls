package net.bbmsoft.fx.controls;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;

public class ResizableCanvas extends Canvas {

	public final static double USE_COMPUTED_SIZE = (-1);
	public final static double USE_PREF_SIZE = Double.NEGATIVE_INFINITY;

	@Override
	public double prefWidth(double height) {

		double pref = this.getPrefWidth();

		if (pref == ResizableCanvas.USE_COMPUTED_SIZE) {
			return 0.0;
		}

		if (Double.isNaN(pref) || (pref < 0.0)) {
			return 0.0;
		}

		return pref;
	}

	@Override
	public double prefHeight(double width) {

		double pref = this.getPrefHeight();

		if (pref == ResizableCanvas.USE_COMPUTED_SIZE) {
			return 0.0;
		}

		if (Double.isNaN(pref) || (pref < 0.0)) {
			return 0.0;
		}

		return pref;
	}

	@Override
	public double maxWidth(double height) {

		final double pref = this.getMaxWidth();

		if (pref == ResizableCanvas.USE_COMPUTED_SIZE) {
			return super.maxWidth(height);
		}

		if (pref == ResizableCanvas.USE_PREF_SIZE) {
			return this.prefWidth(height);
		}

		if (Double.isNaN(pref) || (pref < 0)) {
			return 0.0;
		}

		return pref;
	}

	@Override
	public double maxHeight(double width) {

		final double pref = this.getMaxHeight();

		if (pref == ResizableCanvas.USE_COMPUTED_SIZE) {
			return super.maxHeight(width);
		}

		if (pref == ResizableCanvas.USE_PREF_SIZE) {
			return this.prefHeight(width);
		}

		if (Double.isNaN(pref) || (pref < 0)) {
			return 0.0;
		}

		return pref;
	}

	@Override
	public double minWidth(double height) {

		final double pref = this.getMinWidth();

		if (pref == ResizableCanvas.USE_COMPUTED_SIZE) {
			return super.minWidth(height);
		}

		if (pref == ResizableCanvas.USE_PREF_SIZE) {
			return this.prefWidth(height);
		}

		if (Double.isNaN(pref) || (pref < 0)) {
			return 0.0;
		}

		return pref;
	}

	@Override
	public double minHeight(double width) {

		final double pref = this.getMinHeight();

		if (pref == ResizableCanvas.USE_COMPUTED_SIZE) {
			return super.minHeight(width);
		}

		if (pref == ResizableCanvas.USE_PREF_SIZE) {
			return this.prefHeight(width);
		}

		if (Double.isNaN(pref) || (pref < 0)) {
			return 0.0;
		}

		return pref;
	}

	@Override
	public void resize(double newWidth, double newHeight) {
		this.setWidth(newWidth);
		this.setHeight(newHeight);
	}

	private DoubleProperty prefWidthProperty;
	private double _prefWidth = USE_COMPUTED_SIZE;

	public final DoubleProperty prefWidthProperty() {

		if (this.prefWidthProperty == null) {
			this.prefWidthProperty = new SimpleDoubleProperty(this, "prefWidth", this._prefWidth);
		}

		return this.prefWidthProperty;
	}

	public final double getPrefWidth() {

		if (this.prefWidthProperty != null) {
			return this.prefWidthProperty.get();
		} else {
			return this._prefWidth;
		}
	}

	public final void setPrefWidth(final double value) {

		if (this.prefWidthProperty != null) {
			this.prefWidthProperty.set(value);
		} else {
			this._prefWidth = value;
		}
	}

	private DoubleProperty prefHeightProperty;
	private double _prefHeight = USE_COMPUTED_SIZE;

	public final DoubleProperty prefHeightProperty() {

		if (this.prefHeightProperty == null) {
			this.prefHeightProperty = new SimpleDoubleProperty(this, "prefHeight", this._prefHeight);
		}

		return this.prefHeightProperty;
	}

	public final double getPrefHeight() {

		if (this.prefHeightProperty != null) {
			return this.prefHeightProperty.get();
		} else {
			return this._prefHeight;
		}
	}

	public final void setPrefHeight(final double value) {

		if (this.prefHeightProperty != null) {
			this.prefHeightProperty.set(value);
		} else {
			this._prefHeight = value;
		}
	}

	private DoubleProperty maxWidthProperty;
	private double _maxWidth = Double.MAX_VALUE;

	public final DoubleProperty maxWidthProperty() {

		if (this.maxWidthProperty == null) {
			this.maxWidthProperty = new SimpleDoubleProperty(this, "maxWidth", this._maxWidth);
		}

		return this.maxWidthProperty;
	}

	public final double getMaxWidth() {

		if (this.maxWidthProperty != null) {
			return this.maxWidthProperty.get();
		} else {
			return this._maxWidth;
		}
	}

	public final void setMaxWidth(final double value) {

		if (this.maxWidthProperty != null) {
			this.maxWidthProperty.set(value);
		} else {
			this._maxWidth = value;
		}
	}

	private DoubleProperty maxHeightProperty;
	private double _maxHeight = Double.MAX_VALUE;

	public final DoubleProperty maxHeightProperty() {

		if (this.maxHeightProperty == null) {
			this.maxHeightProperty = new SimpleDoubleProperty(this, "maxHeight", this._maxHeight);
		}

		return this.maxHeightProperty;
	}

	public final double getMaxHeight() {

		if (this.maxHeightProperty != null) {
			return this.maxHeightProperty.get();
		} else {
			return this._maxHeight;
		}
	}

	public final void setMaxHeight(final double value) {

		if (this.maxHeightProperty != null) {
			this.maxHeightProperty.set(value);
		} else {
			this._maxHeight = value;
		}
	}

	private DoubleProperty minWidthProperty;
	private double _minWidth = USE_COMPUTED_SIZE;

	public final DoubleProperty minWidthProperty() {

		if (this.minWidthProperty == null) {
			this.minWidthProperty = new SimpleDoubleProperty(this, "minWidth", this._minWidth);
		}

		return this.minWidthProperty;
	}

	public final double getMinWidth() {

		if (this.minWidthProperty != null) {
			return this.minWidthProperty.get();
		} else {
			return this._minWidth;
		}
	}

	public final void setMinWidth(final double value) {

		if (this.minWidthProperty != null) {
			this.minWidthProperty.set(value);
		} else {
			this._minWidth = value;
		}
	}

	private DoubleProperty minHeightProperty;
	private double _minHeight = USE_COMPUTED_SIZE;

	public final DoubleProperty minHeightProperty() {

		if (this.minHeightProperty == null) {
			this.minHeightProperty = new SimpleDoubleProperty(this, "minHeight", this._minHeight);
		}

		return this.minHeightProperty;
	}

	public final double getMinHeight() {

		if (this.minHeightProperty != null) {
			return this.minHeightProperty.get();
		} else {
			return this._minHeight;
		}
	}

	public final void setMinHeight(final double value) {

		if (this.minHeightProperty != null) {
			this.minHeightProperty.set(value);
		} else {
			this._minHeight = value;
		}
	}

	private BooleanProperty resizableProperty;
	private boolean _resizable = true;

	public final BooleanProperty resizableProperty() {

		if (this.resizableProperty == null) {
			this.resizableProperty = new javafx.beans.property.SimpleBooleanProperty(this, "resizable",
					this._resizable);
		}

		return this.resizableProperty;
	}

	public final boolean isResizable() {

		if (this.resizableProperty != null) {
			return this.resizableProperty.get();
		} else {
			return this._resizable;
		}
	}

	public final void setResizable(final boolean value) {

		if (this.resizableProperty != null) {
			this.resizableProperty.set(value);
		} else {
			this._resizable = value;
		}
	}

}
