import java.awt.Color;

import javax.swing.JFrame;

class MyThread implements Runnable{
	private int shapeIndex;
	private final JFrame jFrame;
	private final Color color;

	private boolean inThread=false;
	private boolean isDeath=false;



	MyThread(int shapeIndex, JFrame jFrame, Color color) {
		this.shapeIndex = shapeIndex;
		this.jFrame = jFrame;
		this.color = color;
	}
	public void run() {
        Color[] array = {Color.BLUE, Color.YELLOW, Color.BLACK, Color.GREEN, Color.RED};

		while(inThread) {
			for (Color color : array) {
				if (inThread) {
					Main.Shapes.get(shapeIndex).setColor(color);
					jFrame.repaint();

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if(!isDeath) {
			Main.Shapes.get(shapeIndex).setColor(color);
			jFrame.repaint();
		}
	}

    Color getColor()
    {
        return color;
    }
	boolean getInThread()
	{
		return inThread;
	}
	void setInThread(boolean inThread)
	{
		this.inThread = inThread;
	}
	void setIsDeath()
	{
		this.isDeath = true;
	}
    void setShapeIndex(int shapeIndex)
    {
        this.shapeIndex = shapeIndex;
    }

}
