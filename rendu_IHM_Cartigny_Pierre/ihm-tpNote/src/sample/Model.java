package sample;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

public class Model {
	private CurrentState cs;
	private Shape SelectedShape = null;
	private ArrayList<Pair<Action,Pair<Shape,String>>> actionDone = new ArrayList<Pair<Action,Pair<Shape,String>>>(); // the actions already done by the user
	private ArrayList<Pair<Action,Pair<Shape,String>>> actionCanceled = new ArrayList<Pair<Action,Pair<Shape,String>>>(); //the actions canceled by the user
	// private ArrayList<shape> listShape = new ArrayList<Shape>();
	
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
	public void addActionDone(Action a,Shape shape, String data) {
		actionDone.add(new Pair(a,new Pair(shape,data)));
	}
	
	//when a user wants to cancel is last modification (press Z)
	
	public Pair<Action,Pair<Shape,String>> deleteAction(){
		if(actionDone.isEmpty()) {
			return null;
		}
		Pair<Action,Pair<Shape,String>> actionToDo = actionDone.remove(actionDone.size() - 1);
		Pair<Action,Pair<Shape,String>> actionDeleted = new Pair<Action,Pair<Shape,String>>(null, null);
		Pair<Shape,String> values = actionToDo.getValue();
		switch(actionToDo.getKey()) {
		case add:
			actionDeleted = new Pair<Action,Pair<Shape,String>>(Action.delete, values);
			break;
		case delete:
			actionDeleted = new Pair<Action,Pair<Shape,String>>(Action.add, values);
			break;
		case move :
			double x = -1,y = -1;
			if(actionToDo.getValue().getKey() instanceof Line) {
				x = ((Line)actionToDo.getValue().getKey()).getStartX();
				y = ((Line)actionToDo.getValue().getKey()).getStartY();
			}
			if(actionToDo.getValue().getKey() instanceof Rectangle) {
				x = ((Rectangle)actionToDo.getValue().getKey()).getX();
				y = ((Rectangle)actionToDo.getValue().getKey()).getY();
			}
			if(actionToDo.getValue().getKey() instanceof Ellipse) {
				x = ((Ellipse)actionToDo.getValue().getKey()).getCenterX() - ((Ellipse)actionToDo.getValue().getKey()).getRadiusX();
				y = ((Ellipse)actionToDo.getValue().getKey()).getCenterY() - ((Ellipse)actionToDo.getValue().getKey()).getRadiusY();
			}
			
			String dataPosition = x + ";" + y;
			actionDeleted = new Pair<Action,Pair<Shape,String>>(Action.move, new Pair<Shape,String>(actionToDo.getValue().getKey(),dataPosition));
			break;
		case changeColor :
			String dataColor = actionToDo.getValue().getKey().getFill().toString();
			actionDeleted = new Pair<Action,Pair<Shape,String>>(Action.changeColor, new Pair<Shape,String>(actionToDo.getValue().getKey(), dataColor));
		}
		actionCanceled.add(actionDeleted);
		return actionToDo;
	}
	
	//when a user wants to cancel the delete action he did (press R)
	
	public Pair<Action,Pair<Shape,String>> CanceldeleteAction(){
		if(actionCanceled.isEmpty()) {
			return null;
		}
		Pair<Action,Pair<Shape,String>> actionToDo = actionCanceled.remove(actionCanceled.size() - 1);
		Pair<Action,Pair<Shape,String>> actionDeleted = new Pair<Action,Pair<Shape,String>>(null, null);
		Pair<Shape,String> values = actionToDo.getValue();
		switch(actionToDo.getKey()) {
		case add:
			actionDeleted = new Pair<Action,Pair<Shape,String>>(Action.delete, values);
			break;
		case delete:
			actionDeleted = new Pair<Action,Pair<Shape,String>>(Action.add, values);
			break;
		case move :
			double x = -1,y = -1;
			if(actionToDo.getValue().getKey() instanceof Line) {
				x = ((Line)actionToDo.getValue().getKey()).getStartX();
				y = ((Line)actionToDo.getValue().getKey()).getStartY();
			}
			if(actionToDo.getValue().getKey() instanceof Rectangle) {
				x = ((Rectangle)actionToDo.getValue().getKey()).getX();
				y = ((Rectangle)actionToDo.getValue().getKey()).getY();
			}
			if(actionToDo.getValue().getKey() instanceof Ellipse) {
				x = ((Ellipse)actionToDo.getValue().getKey()).getCenterX() - ((Ellipse)actionToDo.getValue().getKey()).getRadiusX();
				y = ((Ellipse)actionToDo.getValue().getKey()).getCenterY() - ((Ellipse)actionToDo.getValue().getKey()).getRadiusY();
			}
			
			String dataPosition = x + ";" + y;
			actionDeleted = new Pair<Action,Pair<Shape,String>>(Action.move, new Pair<Shape,String>(actionToDo.getValue().getKey(),dataPosition));
			break;
		case changeColor :
			String dataColor = actionToDo.getValue().getKey().getFill().toString();
			actionDeleted = new Pair<Action,Pair<Shape,String>>(Action.changeColor, new Pair<Shape,String>(actionToDo.getValue().getKey(), dataColor));
		}
		actionDone.add(actionDeleted);
		return actionToDo;
	}
	
}
