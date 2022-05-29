import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;

class Shape extends JFrame{
	Point oldCoordinates,newCoordinates;
	private Color color;
	private String name;



	Shape(Point oldCoordinates, Point newCoordinates, Color color) {
		this.oldCoordinates = new Point(oldCoordinates.x,oldCoordinates.y);
		this.newCoordinates = new Point(newCoordinates.x,newCoordinates.y);
		this.setColor(color);
	}

	Color getColor() {
		return color;
	}
	void setColor(Color color) {
		this.color = color;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

}


    