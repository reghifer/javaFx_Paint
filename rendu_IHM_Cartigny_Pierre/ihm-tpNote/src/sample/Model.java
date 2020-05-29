package sample;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Model {
	private CurrentState cs;
	private Shape SelectedShape = null;
	
	
	public Model(CurrentState cs) {
		this.cs = cs;
	}
	public CurrentState getCs() {
		return cs;
	}
	public void setCs(CurrentState cs) {
		this.cs = cs;
	}
	public Shape getSelectedShape() {
		return SelectedShape;
	}
	public void setSelectedShape(Shape selectedShape) {
		SelectedShape = selectedShape;
	}
	public void ChangeSelectedShapeColor(Color c) {
		SelectedShape.setFill(c);
	}
	
}
