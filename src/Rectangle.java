import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

class Rectangle extends Shape {

	Rectangle(Point oldCoordinates, Point newCoordinates, Color color) {
		super(oldCoordinates, newCoordinates, color);

		setName("Rectangle");
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		int x1 = oldCoordinates.x;
    	int y1 = oldCoordinates.y;
    	int x2 = newCoordinates.x;
    	int y2 = newCoordinates.y;
    	int width = Math.abs(x1-x2);
    	int height = Math.abs(y1-y2);

    	if(x1>x2 && y1<y2)
    	{
    		int temp = oldCoordinates.x;
    		oldCoordinates.x = newCoordinates.x;
    		newCoordinates.x = temp;
    	}
    	
    	else if(x1>x2 && y1>y2)
    	{
    		Point temp = oldCoordinates;
    		oldCoordinates = newCoordinates;
    		newCoordinates = temp;
    	}
    	
    	else if(x1<x2 && y1>y2)
    	{
    		int temp = oldCoordinates.y;
    		oldCoordinates.y = newCoordinates.y;
    		newCoordinates.y = temp;
    	}
    	
    	int x11 = oldCoordinates.x;
    	int y11 = oldCoordinates.y;

        graphics.setColor(this.getColor());
        graphics.drawRect(x11+8, y11+66, width, height);
	}

}
