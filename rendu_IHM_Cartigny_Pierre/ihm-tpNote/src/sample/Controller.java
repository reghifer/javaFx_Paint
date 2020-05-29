package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

public class Controller {
	@FXML
	private RadioButton radioSelectMove;
	@FXML
	private RadioButton radioEllipse;
	@FXML
	private RadioButton radioRectangle;
	@FXML
	private RadioButton radioLine;
	@FXML
	private Button buttonDelete;
	@FXML
	private Button buttonClone;
	@FXML
	private ColorPicker colorDrawing;
	@FXML
	private Pane DrawingPane;
	@FXML
	private BorderPane applicationPane;
	private Model model;
	private boolean firstClick = false;
	private double firstClickX = -1.0;
	private double firstClickY = -1.0;
	private Shape tempoparyDraw = null;
	
	@FXML
    public void initialize(){
		radioSelectMove.setSelected(true);
		model = new Model(CurrentState.select_Move);
		radioSelectMove.setOnMousePressed(event ->{
			changeSelectedRadioButton(radioSelectMove);
		});
		radioEllipse.setOnMousePressed(event ->{
			changeSelectedRadioButton(radioEllipse);
		});
		radioRectangle.setOnMousePressed(event ->{
			changeSelectedRadioButton(radioRectangle);
		});
		radioLine.setOnMousePressed(event ->{
			changeSelectedRadioButton(radioLine);
		});
		DrawingPane.setOnMouseClicked(event->{
			clickDrawingPane(event.getX(),event.getY());
		});
		DrawingPane.setOnMouseMoved(event->{
			showDrawing(event.getX(),event.getY());
		});
		
		colorDrawing.setOnAction(event ->{
			if(model.getSelectedShape() != null) {
				model.addActionDone(Action.changeColor, model.getSelectedShape(), model.getSelectedShape().getFill().toString());
				model.ChangeSelectedShapeColor(colorDrawing.getValue());
			}
		});
		
		buttonDelete.setOnAction(event ->{
			if(model.getSelectedShape() != null) {
				model.addActionDone(Action.add, model.getSelectedShape(), "");
				DrawingPane.getChildren().remove(model.getSelectedShape());
				
			}
		});
		buttonClone.setOnAction(event->{
			if(model.getSelectedShape() != null) {
				Drawer.clone(model, DrawingPane);
			}
		});
		
		applicationPane.setOnKeyPressed(event->{
			switch(event.getText()) {
			case"z":
				deleteAction();
				break;
			case"r":
				CanceldeleteAction();
				break;
			case"d":
				drawMeASheep();
				break;
			case"h":
				Helmsdeep();
				break;
			case"i":
				Hello();
				break;
			}
		});
		
	}
	//set the Current State depending of wich radio button is selected
	private void changeSelectedRadioButton(RadioButton radio) {
		String idRadio = radio.getId();
		switch(idRadio){
		case "radioSelectMove" :
			radioEllipse.setSelected(false);
			radioRectangle.setSelected(false);
			radioLine.setSelected(false);
			model.setCs(CurrentState.select_Move);
			break;
		case "radioEllipse" :
			radioSelectMove.setSelected(false);
			radioRectangle.setSelected(false);
			radioLine.setSelected(false);
			model.setCs(CurrentState.Ellipse);
			Drawer.deselect(model);
			break;
		case "radioRectangle" :
			radioSelectMove.setSelected(false);
			radioEllipse.setSelected(false);
			radioLine.setSelected(false);
			model.setCs(CurrentState.Rectangle);
			Drawer.deselect(model);
			break;
		case "radioLine" :
			radioSelectMove.setSelected(false);
			radioEllipse.setSelected(false);
			radioRectangle.setSelected(false);
			model.setCs(CurrentState.Line);
			Drawer.deselect(model);
			break;
			default :
				break;
		}	
	}
	
	//function called when a click is done on the drawing zone
	private void clickDrawingPane(double mouseX,double mouseY) {
		if(!firstClick) {
			if(model.getCs() != CurrentState.select_Move) {
				firstClick = true;
				firstClickX = mouseX;
				firstClickY = mouseY;
			}
		}else {
			DrawingPane.getChildren().remove(tempoparyDraw);
			tempoparyDraw = null;
			firstClick = false;
			switch (model.getCs()){
			case select_Move :
				break;
			case Ellipse :
				Drawer.drawEllipse(firstClickX, firstClickY, mouseX, mouseY, colorDrawing.getValue(), model, DrawingPane);
				break;
			case Rectangle :
				Drawer.drawRectangle(firstClickX, firstClickY, mouseX, mouseY, colorDrawing.getValue(), model, DrawingPane);
				break;
			case Line :
				Drawer.drawLine(firstClickX, firstClickY, mouseX, mouseY, colorDrawing.getValue(), model, DrawingPane);
				break;
			}
		}
	}
	
	//allow to show the current drawing the user do
	private void showDrawing(double mouseX,double mouseY) {
		if(firstClick) {
			if(tempoparyDraw != null) {
				DrawingPane.getChildren().remove(tempoparyDraw);
			}
			switch (model.getCs()){
			case select_Move :
				tempoparyDraw = null;
				break;
			case Ellipse :
				tempoparyDraw = Drawer.temporaryDrawEllipse(firstClickX, firstClickY, mouseX, mouseY, colorDrawing.getValue(), DrawingPane);
				break;
			case Rectangle :
				tempoparyDraw = Drawer.temporarydrawRectangle(firstClickX, firstClickY, mouseX, mouseY, colorDrawing.getValue(), DrawingPane);
				break;
			case Line :
				tempoparyDraw = Drawer.temporaryDrawLine(firstClickX, firstClickY, mouseX, mouseY, colorDrawing.getValue(), DrawingPane);
				break;
			}
		}
	}
	
	//execute the delete of an action
	
	private void deleteAction() {
		Pair<Action,Pair<Shape,String>> actionToDo = model.deleteAction();
		if(actionToDo == null || actionToDo.getKey() == null || actionToDo.getValue() == null) {
			return;
		}
		translateAction(actionToDo.getKey(), actionToDo.getValue());
	}
	
	//cancel the delete of an action
	
	private void CanceldeleteAction() {
		Pair<Action,Pair<Shape,String>> actionToDo = model.CanceldeleteAction();
		if(actionToDo == null || actionToDo.getKey() == null || actionToDo.getValue() == null) {
			return;
		}
		translateAction(actionToDo.getKey(), actionToDo.getValue());
	}
	
	//apply the changes
	
	private void translateAction(Action ac ,Pair<Shape,String> data) {
		switch(ac) {
		case add :
			DrawingPane.getChildren().add(data.getKey());
			break;
		case delete :
			DrawingPane.getChildren().remove(data.getKey());
			break;
		case changeColor :
			Color c = Color.valueOf(data.getValue());
			data.getKey().setFill(c);
			break;
		case move :
			String[] positions= data.getValue().split(";");
			double x = Double.parseDouble(positions[0]);
			double y = Double.parseDouble(positions[1]);
			if(data.getKey() instanceof Line) {
				Double EndX = ((Line)data.getKey()).getStartX() - ((Line)data.getKey()).getEndX();
				Double EndY = ((Line)data.getKey()).getStartY() - ((Line)data.getKey()).getEndY();
				((Line)data.getKey()).setStartX(x);
				((Line)data.getKey()).setStartY(y);
				((Line)data.getKey()).setEndX(x + EndX);
				((Line)data.getKey()).setEndY(y+ EndY);
			}
			if(data.getKey() instanceof Rectangle) {
				((Rectangle)data.getKey()).setX(x);
				((Rectangle)data.getKey()).setY(y);
			}
			if(data.getKey() instanceof Ellipse) {
				((Ellipse)data.getKey()).setCenterX((x + ((Ellipse)data.getKey()).getRadiusX()));
				((Ellipse)data.getKey()).setCenterY((y + ((Ellipse)data.getKey()).getRadiusY()));
			}
		}
	}
	
	//test elements
	private void drawMeASheep(){
		Drawer.drawLine(100, 200, 100, 400, Color.BLACK, model, DrawingPane);
		Drawer.drawLine(100, 400, 500, 400, Color.BLACK, model, DrawingPane);
		Drawer.drawLine(500, 400, 500, 200, Color.BLACK, model, DrawingPane);
		Drawer.drawLine(500, 200, 100, 200, Color.BLACK, model, DrawingPane);
		Drawer.drawLine(100, 200, 150, 150, Color.BLACK, model, DrawingPane);
		Drawer.drawLine(500, 200, 550, 150, Color.BLACK, model, DrawingPane);
		Drawer.drawLine(500, 400, 550, 350, Color.BLACK, model, DrawingPane);
		Drawer.drawLine(150, 150, 550, 150, Color.BLACK, model, DrawingPane);
		Drawer.drawLine(550, 150, 550, 350, Color.BLACK, model, DrawingPane);
	}
	private void Helmsdeep(){
		Drawer.drawRectangle(100, 300, 500, 500,  Color.DARKGREY, model, DrawingPane);
		Drawer.drawRectangle(100, 250, 183, 300,  Color.DARKGREY, model, DrawingPane);
		Drawer.drawRectangle(266, 250, 349, 300,  Color.DARKGREY, model, DrawingPane);
		Drawer.drawRectangle(431, 250, 500, 300,  Color.DARKGREY, model, DrawingPane);
	}
	private void Hello(){
		Drawer.drawEllipse(100, 200, 500, 400, Color.WHITE, model, DrawingPane);
		Drawer.drawEllipse(200, 200, 400, 400, Color.DEEPSKYBLUE, model, DrawingPane);
		Drawer.drawEllipse(250, 250, 350, 350, Color.BLACK, model, DrawingPane);
	}

}
