package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Drawer {
	
	//function called for the deselection of a shape
	public static void deselect(Model mod) {
		if(mod.getSelectedShape() != null) {
			mod.getSelectedShape().setStroke(mod.getSelectedShape().getFill());
			mod.setSelectedShape(null);
		}
	}
	
	//---permanant Drawings-----
	
	public static void drawLine(double x1, double y1, double x2, double y2,Color c,Model mod,Pane p) {
		Line line = new Line(x1,y1,x2,y2);
		line.setFill(c);
		
		line.setOnMouseClicked(event ->{
			if(mod.getCs() == CurrentState.select_Move) {
				Drawer.deselect(mod);
				mod.setSelectedShape(line);
				line.setStroke(Color.CYAN);
			}
		});
		
		line.setOnDragDetected(event->{
			double x = line.getStartX();
			double y = line.getStartY();
			String dataPosition = x + ";" + y;
			mod.addActionDone(Action.move, line, dataPosition);
		});
		
		line.setOnMouseDragged(event->{
			if(line.equals(mod.getSelectedShape())) {
				line.setStartX(event.getX());
				line.setStartY(event.getY());
				line.setEndX(event.getX() + (x2 - x1));
				line.setEndY(event.getY() + (y2 - y1));
			}
		});
		mod.addActionDone(Action.delete, line, "");
		p.getChildren().add(line);
	}
	
	public static void drawRectangle(double x1, double y1, double x2, double y2,Color c,Model mod,Pane p) {
		if(x2 < x1) {
			double mem = x2;
			x2 = x1;
			x1 = mem;
			
		}
		if(y2 < y1) {
			double mem = y2;
			y2 = y1;
			y1 = mem;
			
		}
		Rectangle rectangle = new Rectangle();
		rectangle.setX(x1);
		rectangle.setY(y1);
		rectangle.setWidth(x2 - x1);
		rectangle.setHeight(y2 - y1);
		rectangle.setFill(c);
		
		rectangle.setOnMouseClicked(event ->{
			if(mod.getCs() == CurrentState.select_Move) {
				Drawer.deselect(mod);
				mod.setSelectedShape(rectangle);
				rectangle.setStroke(Color.CYAN);
			}
		});
		
		rectangle.setOnDragDetected(event->{
			double x = rectangle.getX();
			double y = rectangle.getY();
			String dataPosition = x + ";" + y;
			mod.addActionDone(Action.move, rectangle, dataPosition);
		});
		
		rectangle.setOnMouseDragged(event->{
			if(rectangle.equals(mod.getSelectedShape())) {
				rectangle.setX(event.getX());
				rectangle.setY(event.getY());
			}
		});
		p.getChildren().add(rectangle);
		mod.addActionDone(Action.delete, rectangle, "");
	}
	
	public static void drawEllipse(double x1, double y1, double x2, double y2,Color c,Model mod,Pane p) {
		Ellipse ellipse = new Ellipse();
		if(x2 < x1) {
			double mem = x2;
			x2 = x1;
			x1 = mem;
			
		}
		if(y2 < y1) {
			double mem = y2;
			y2 = y1;
			y1 = mem;
			
		}
		double centerX = (x1 + x2)/2;
		double centerY = (y1 + y2)/2;
		double radiusX = x2 - centerX;
		double radiusY = y2 - centerY;
		ellipse.setCenterX(centerX);
		ellipse.setCenterY(centerY);
		ellipse.setRadiusX(radiusX);
		ellipse.setRadiusY(radiusY);
		ellipse.setFill(c);
		
		ellipse.setOnMouseClicked(event ->{
			if(mod.getCs() == CurrentState.select_Move) {
				Drawer.deselect(mod);
				mod.setSelectedShape(ellipse);
				ellipse.setStroke(Color.CYAN);
			}
		});
		
		ellipse.setOnDragDetected(event->{
			double x = ellipse.getCenterX() - ellipse.getRadiusX();
			double y = ellipse.getCenterY() - ellipse.getRadiusY();
			String dataPosition = x + ";" + y;
			mod.addActionDone(Action.move, ellipse, dataPosition);
		});
		
		ellipse.setOnMouseDragged(event->{
			if(ellipse.equals(mod.getSelectedShape())) {
				ellipse.setCenterX(event.getX());
				ellipse.setCenterY(event.getY());
			}
		});
		p.getChildren().add(ellipse);
		mod.addActionDone(Action.delete, ellipse, "");
	}
	
	public static void clone(Model mod, Pane p) {
		if(mod.getSelectedShape() instanceof Line) {
			Line clone = (Line) mod.getSelectedShape();
			Drawer.drawLine(clone.getStartX() + 10, clone.getStartY() +10 , clone.getStartX() + 10, clone.getStartY() + 10, (Color) clone.getFill(), mod, p);
		}
		if(mod.getSelectedShape() instanceof Rectangle) {
			Rectangle clone = (Rectangle) mod.getSelectedShape();
			Drawer.drawRectangle(clone.getX() + 10, clone.getY() +10 , clone.getX() + clone.getWidth() + 10, clone.getY() + clone.getHeight() + 10, (Color) clone.getFill(), mod, p);
		}
		if(mod.getSelectedShape() instanceof Ellipse) {
			Ellipse clone = (Ellipse) mod.getSelectedShape();
			clone.setCenterX(clone.getCenterX() + 10);
			Drawer.drawEllipse(clone.getCenterX() - clone.getRadiusX() + 10, clone.getCenterY() - clone.getRadiusY() + 10,clone.getCenterX() + clone.getRadiusX() + 10, clone.getCenterY() + clone.getRadiusY() + 10, (Color) clone.getFill(), mod, p);
		}
	}
	
	//---temporary Drawings-------
	
	public static Shape temporaryDrawLine(double x1, double y1, double x2, double y2,Color c,Pane p) {
		Line line = new Line(x1,y1,x2,y2);
		line.setFill(c);
		p.getChildren().add(line);
		return line;
	}
	public static Shape temporarydrawRectangle(double x1, double y1, double x2, double y2,Color c,Pane p) {
		if(x2 < x1) {
			double mem = x2;
			x2 = x1;
			x1 = mem;
			
		}
		if(y2 < y1) {
			double mem = y2;
			y2 = y1;
			y1 = mem;
			
		}
		Rectangle rectangle = new Rectangle();
		rectangle.setX(x1);
		rectangle.setY(y1);
		rectangle.setWidth(x2 - x1);
		rectangle.setHeight(y2 - y1);
		rectangle.setFill(c);
		p.getChildren().add(rectangle);
		return rectangle;
	}
	public static Shape temporaryDrawEllipse(double x1, double y1, double x2, double y2,Color c,Pane p) {
		Ellipse ellipse = new Ellipse();
		if(x2 < x1) {
			double mem = x2;
			x2 = x1;
			x1 = mem;
			
		}
		if(y2 < y1) {
			double mem = y2;
			y2 = y1;
			y1 = mem;
			
		}
		double centerX = (x1 + x2)/2;
		double centerY = (y1 + y2)/2;
		double radiusX = x2 - centerX;
		double radiusY = y2 - centerY;
		ellipse.setCenterX(centerX);
		ellipse.setCenterY(centerY);
		ellipse.setRadiusX(radiusX);
		ellipse.setRadiusY(radiusY);
		ellipse.setFill(c);
		p.getChildren().add(ellipse);
		return ellipse;
	}
}
