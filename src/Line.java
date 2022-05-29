import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

class Line extends Shape {

	Line(Point oldCoordinates, Point newCoordinates, Color color) {
		super(oldCoordinates, newCoordinates, color);
		
		setName("Line");
	}
	
	public void paint(Graphics graphics) {
	    super.paint(graphics);
			
	    int x1 = oldCoordinates.x;
	    int y1 = oldCoordinates.y;
	    int x2 = newCoordinates.x;
	    int y2 = newCoordinates.y;

        graphics.setColor(this.getColor());
        graphics.drawLine(x1+8, y1+66, x2+8, y2+66);
	}

}
