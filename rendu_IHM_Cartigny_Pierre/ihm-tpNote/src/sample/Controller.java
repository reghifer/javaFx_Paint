package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

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
				model.ChangeSelectedShapeColor(colorDrawing.getValue());
			}
		});
		buttonDelete.setOnAction(event ->{
			if(model.getSelectedShape() != null) {
				DrawingPane.getChildren().remove(model.getSelectedShape());
			}
		});
		buttonDelete.setOnAction(event ->{
			if(model.getSelectedShape() != null) {
				DrawingPane.getChildren().remove(model.getSelectedShape());
			}
		});
		buttonClone.setOnAction(event->{
			if(model.getSelectedShape() != null) {
				Drawer.clone(model, DrawingPane);
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

}
