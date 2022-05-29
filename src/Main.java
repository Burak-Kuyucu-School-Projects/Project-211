import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

class Main extends JFrame implements ActionListener{
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 700;

	static final ArrayList<Shape> Shapes = new ArrayList<>();
	private static final ArrayList<MyThread> threads = new ArrayList<>();

	private JList<Shape> list;
	private JComboBox<String> box;

	private Color currentColor = Color.BLACK;
	private String currentPolygon = "Nothing";



	public static void main (String[] args) {
		new Main();
	}
	private Main(){
		initialize(this);
		addButtons(this);
		drawingPlace(this);

		readSaving();
		setVisible(true);
	}

	private void initialize(JFrame frame) {
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle("Draw");
		frame.setResizable(true);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);


		WindowListener exitListener = new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				int confirm = JOptionPane.showOptionDialog(
						null, "Are You Sure to Close Application?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);

				if (confirm ==  0) {
					writeSaving();
					System.exit(0);
				}
			}
		};

		frame.addWindowListener(exitListener);
	}
	private void addButtons(JFrame frame) {
		JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new GridLayout(1,3));
        
		JPanel panel = new JPanel();
		panel.setBackground(Color.gray);

		String[] str = {"Blue", "Yellow", "Black", "Green", "Red"};
		box =  new JComboBox<>(str);
		box.addActionListener(this);
		panel.add(box);
		
		
		JPanel panel2 = new JPanel();
		ButtonGroup buttonGroup = new ButtonGroup();

		JRadioButton b1,b2,b3,b4;
		b1 = new JRadioButton("Rectangle");
		b2 = new JRadioButton("Circle");
		b3 = new JRadioButton("Line");
		b4 = new JRadioButton("Pointer");

		buttonGroup.add(b1);
		buttonGroup.add(b2);
		buttonGroup.add(b3);
		buttonGroup.add(b4);

		panel2.add(b1);
		panel2.add(b2);
		panel2.add(b3);
		panel2.add(b4);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);

		
		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.gray);

		JButton buttonUndo =  new JButton("Undo");
		buttonUndo.addActionListener(this);
		panel3.add(buttonUndo);

		JButton buttonClear =  new JButton("Clear");
		buttonClear.setBackground(Color.BLACK);
		buttonClear.setForeground(Color.WHITE);
		buttonClear.addActionListener(this);
		panel3.add(buttonClear);
		
		
        bigPanel.add(panel);
        bigPanel.add(panel2);
        bigPanel.add(panel3);
		
		frame.add(bigPanel, BorderLayout.NORTH);

	}
	private void drawingPlace(JFrame frame) {
		JPanel ustPanel = new JPanel();
		ustPanel.setBackground(Color.BLACK);

		JLabel label = new JLabel("Shapes");
		label.setForeground(Color.WHITE);
		ustPanel.add(label);
		
		
		list = new JList<>();
		list.setBackground(Color.CYAN);

		JScrollPane scrollPane = new JScrollPane(list);
		JPanel altPanel = new JPanel();
		altPanel.setBackground(Color.orange);

		
		JButton animate = new JButton("Animate");
		altPanel.add(animate);
		animate.addActionListener(this);

		JButton stopAnimate = new JButton("Stop");
		altPanel.add(stopAnimate);
		stopAnimate.addActionListener(this);

		JButton delete = new JButton("Delete");
		altPanel.add(delete);
		delete.addActionListener(this);
		
		
        JPanel objectPanel = new JPanel();
        objectPanel.setLayout(new BorderLayout());
        objectPanel.setPreferredSize(new Dimension(250,100));
        objectPanel.add(ustPanel, BorderLayout.NORTH);
        objectPanel.add(scrollPane);
        objectPanel.add(altPanel,BorderLayout.SOUTH);

		frame.add(objectPanel,BorderLayout.EAST);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frame.add(panel,BorderLayout.CENTER);

		
		MouseAdapter mouseAdapter = new CanvasPanelMouseAdapter();
		panel.addMouseListener(mouseAdapter);
		panel.addMouseMotionListener(mouseAdapter);
	}
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		switch (actionCommand) {
			case "Rectangle":
				currentPolygon = "Rectangle";
				break;
			case "Circle":
				currentPolygon = "Circle";
				break;
			case "Line":
				currentPolygon = "Line";
				break;
			case "Pointer":
				currentPolygon = "Pointer";
				break;
			case "Undo": {
				if (Shapes.size() > 0) {
					int i = threads.size() - 1;

					threads.get(i).setInThread(false);
					threads.get(i).setIsDeath();
					threads.remove(i);

					Shapes.remove(i);
				}

				Shape[] array = addToJList();
				list.setListData(array);

				repaint();
				break;
			}
			case "Clear": {
				for (int i = 0; i < Shapes.size(); i++) {
					threads.get(i).setInThread(false);
					threads.get(i).setIsDeath();
				}

				threads.clear();
				Shapes.clear();

				Shape[] array = addToJList();
				list.setListData(array);

				repaint();

				break;
			}
			case "Animate":
				if (Shapes.size() > 0) {
					int a = list.getSelectedIndex();

					if (!threads.get(a).getInThread()) {
						threads.get(a).setInThread(true);
						Thread thread = new Thread(threads.get(a));
						thread.start();
					}
				}
				break;
			case "Stop":
				if (Shapes.size() > 0) {
					int i = list.getSelectedIndex();

					threads.get(i).setInThread(false);
					repaint();
				}
				break;
			case "Delete": {
				if (list.getSelectedIndex() >= 0) {
					int i = list.getSelectedIndex();
					threads.get(i).setInThread(false);
					threads.get(i).setIsDeath();
					threads.remove(i);

					if (Shapes.size() > 0) {
						Shapes.remove(i);
					}
				}

				for (int i = 0; i < threads.size(); i++) {
					threads.get(i).setShapeIndex(i);
				}

				Shape[] array = addToJList();
				list.setListData(array);

				repaint();
				break;
			}
		}

	}
	public void paint(Graphics g) {
		super.paint(g);

		for (Shape shape : Shapes) {
			choseCurrentColor();
			shape.paint(g);
		}
	}

	private void choseCurrentColor() {
		String color = (String) box.getSelectedItem();

		if (color == null) {
			currentColor = Color.BLUE;
			return;
		}

		switch (color) {
			case "Blue":
				currentColor = Color.BLUE;
				break;
			case "Yellow":
				currentColor = Color.YELLOW;
				break;
			case "Black":
				currentColor = Color.BLACK;
				break;
			case "Green":
				currentColor = Color.GREEN;
				break;
			case "Red":
				currentColor = Color.RED;
				break;
		}
	}
	private Shape[] addToJList() {
		Shape[] array = new Shape[Shapes.size()];

		for(int i = 0; i< Shapes.size(); i++) {
            array[i] =  Shapes.get(i);
		}

		return array;
	}
    private void writeSaving() {
		//  Format: Rectangle&123,232&234,345&BLUE&false

		File file = new File("saving.txt");

		try
		{
			if (!file.exists() && !file.createNewFile())
				    return;

			FileWriter fileWriter = new FileWriter(file, false);
			BufferedWriter bWriter = new BufferedWriter(fileWriter);

			for(int i = 0; i< Shapes.size(); i++) {
				Shape temp =  Shapes.get(i);

				Color color = threads.get(i).getColor();
				String tempColor = "";

				boolean inThread = false;

				if(color.equals(Color.BLUE))
					tempColor = "BLUE";
				else if(color.equals(Color.YELLOW))
					tempColor = "YELLOW";
				else if(color.equals(Color.BLACK))
					tempColor = "BLACK";
				else if(color.equals(Color.GREEN))
					tempColor = "GREEN";
				else if(color.equals(Color.RED))
					tempColor = "RED";

				if(threads.get(i).getInThread())
					inThread = true;

				bWriter.write(temp.getName()+"&" + temp.oldCoordinates.x
						+","+ temp.oldCoordinates.y
						+"&"+ temp.newCoordinates.x
						+","+ temp.newCoordinates.y+"&" + tempColor
						+"&"+ inThread);
				bWriter.newLine();
			}

			bWriter.close();

		} catch (Exception exception) {
			System.out.println("Error: "+ exception.getMessage());
		}
	}
	private void readSaving() {
		try {
			File file = new File("saving.txt");
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);

			boolean finished = true;
			while(finished) {
				String s = bReader.readLine();

				if(s == null)
					finished = false;
				else {
					String[] row = s.split("&");

					Point point1 = new Point();
					String[] oldCoordinates = row[1].split(",");
					point1.x = Integer.parseInt(oldCoordinates[0]);
					point1.y = Integer.parseInt(oldCoordinates[1]);

					Point point2 = new Point();
					String[] newCoordinates = row[2].split(",");
					point2.x = Integer.parseInt(newCoordinates[0]);
					point2.y = Integer.parseInt(newCoordinates[1]);

					Color color = Color.BLACK;
					switch (row[3]) {
						case "BLUE":
							color = Color.BLUE;
							break;
						case "YELLOW":
							color = Color.YELLOW;
							break;
						case "GREEN":
							color = Color.GREEN;
							break;
						case "RED":
							color = Color.RED;
							break;
					}


					switch (row[0]) {
						case "Rectangle": {
							Shapes.add(new Rectangle(point1, point2, color));
							MyThread myThread = new MyThread(Shapes.size() - 1, Main.this, color);
							threads.add(myThread);
							break;
						}
						case "Circle": {
							Shapes.add(new Circle(point1, point2, color));
							MyThread myThread = new MyThread(Shapes.size() - 1, Main.this, color);
							threads.add(myThread);
							break;
						}
						case "Line": {
							Shapes.add(new Line(point1, point2, color));
							MyThread myThread = new MyThread(Shapes.size() - 1, Main.this, color);
							threads.add(myThread);
							break;
						}
					}

					boolean inThread = false;
					if(row[4].equals("true"))
						inThread = true;

					if(inThread) {
						int a =  Shapes.size()-1;

						threads.get(a).setInThread(true);
						Thread thread = new Thread(threads.get(a));
						thread.start();
					}

					Shape[]array = addToJList();
					list.setListData(array);

					repaint();
				}
			}

			bReader.close();
		} catch (Exception exception) {
			System.out.println("Error: "+ exception.getMessage());
		}
	}



	class CanvasPanelMouseAdapter extends MouseAdapter implements MouseListener{

		private Point oldCoordinates,newCoordinates;
		private boolean isInDragged = false;

		public void mousePressed(MouseEvent e)
		{
			oldCoordinates = new Point(e.getPoint());
		}
		public void mouseDragged(MouseEvent e) {
			newCoordinates = new Point(e.getPoint());

			if(isInDragged && Shapes.size()>0 && !currentPolygon.equals("Pointer")) {
				Shapes.remove(Shapes.size()-1);
				threads.remove(threads.size()-1);
			}


			isInDragged = true;

			switch (currentPolygon) {
				case "Rectangle": {
					Shapes.add(new Rectangle(oldCoordinates, newCoordinates, currentColor));
					MyThread myThread = new MyThread(Shapes.size() - 1, Main.this, currentColor);
					threads.add(myThread);
					break;
				}
				case "Circle": {
					Shapes.add(new Circle(oldCoordinates, newCoordinates, currentColor));
					MyThread myThread = new MyThread(Shapes.size() - 1, Main.this, currentColor);
					threads.add(myThread);
					break;
				}
				case "Line": {
					Shapes.add(new Line(oldCoordinates, newCoordinates, currentColor));
					MyThread myThread = new MyThread(Shapes.size() - 1, Main.this, currentColor);
					threads.add(myThread);
					break;
				}
				default:
					isInDragged = false;
					break;
			}

			repaint();
		}
		public void mouseReleased(MouseEvent e) {
			newCoordinates = new Point(e.getPoint());

			if(!oldCoordinates.equals(newCoordinates) && isInDragged) {
				if(Shapes.size()>0 && !currentPolygon.equals("Pointer")) {
					Shapes.remove(Shapes.size()-1);
					threads.remove(threads.size()-1);
				}

				switch (currentPolygon) {
					case "Rectangle": {
						Shapes.add(new Rectangle(oldCoordinates, newCoordinates, currentColor));
						MyThread myThread = new MyThread(Shapes.size() - 1, Main.this, currentColor);
						threads.add(myThread);
						break;
					}
					case "Circle": {
						Shapes.add(new Circle(oldCoordinates, newCoordinates, currentColor));
						MyThread myThread = new MyThread(Shapes.size() - 1, Main.this, currentColor);
						threads.add(myThread);
						break;
					}
					case "Line": {
						Shapes.add(new Line(oldCoordinates, newCoordinates, currentColor));
						MyThread myThread = new MyThread(Shapes.size() - 1, Main.this, currentColor);
						threads.add(myThread);
						break;
					}
				}

				Shape[]array = addToJList();
				list.setListData(array);

				repaint();
				isInDragged = false;
			}
		}

	}

}