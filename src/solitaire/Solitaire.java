package solitaire;

import java.awt.EventQueue;
import java.io.IOException;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import components.solitaire.CardStack;
import components.solitaire.GameController;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class Solitaire {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Solitaire window = new Solitaire();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public Solitaire() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		JPanel upperPanel = new JPanel();
		frame.getContentPane().add(upperPanel, BorderLayout.NORTH);
		
		JButton btnStart = new JButton("New Game");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GameController.getInstance().start();
					frame.repaint();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		upperPanel.add(btnStart);
		
		JButton btnRestart = new JButton("Restart");
		upperPanel.add(btnRestart);
		
		JButton btnPause = new JButton("Pause");
		upperPanel.add(btnPause);
		
		JButton btnUndo = new JButton("Undo");
		upperPanel.add(btnUndo);
		
		JButton btnLevel = new JButton("Easy Level");
		btnLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int level=GameController.getInstance().getLevel();
				level = (level+1)%2;
				GameController.getInstance().setLevel(level);
				if (level==0) {
					btnLevel.setText("Hard Level");
				} else btnLevel.setText("Easy Level");
			}
		});
		upperPanel.add(btnLevel);

		JPanel lowerPanel = new JPanel();
		frame.getContentPane().add(lowerPanel, BorderLayout.SOUTH);
		
		Dimension lbDimension=new Dimension(120,16);
		JLabel lblMoves = new JLabel(" # Moves: ");
		lblMoves.setPreferredSize(lbDimension);
		lowerPanel.add(lblMoves);
		
		JLabel lblTime = new JLabel("Time Elapsed:");
		lblTime.setPreferredSize(lbDimension);
		lowerPanel.add(lblTime);
		
		JLabel lblScore = new JLabel("Score #: ");
		lblScore.setPreferredSize(lbDimension);
		lowerPanel.add(lblScore);

		JPanel midPanel = new JPanel();
		frame.getContentPane().add(midPanel, BorderLayout.CENTER);
		GridBagLayout gbl_midPanel = new GridBagLayout();
		//gbl_midPanel.columnWidths = new int[]{0, 0};
		//gbl_midPanel.rowHeights = new int[]{0, 0};
		//gbl_midPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		//gbl_midPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		midPanel.setLayout(gbl_midPanel);

		GameController controller = GameController.getInstance();
		GridBagConstraints c = new GridBagConstraints();
		
		
		CardStack enclosedStack = controller.getClosedStack();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor=GridBagConstraints.NORTH;
		c.gridwidth=2;
		c.ipady=0;
		c.gridheight=1;
		c.weightx=0.2;
		c.weighty=0.1;
		c.gridx = 0;
		c.gridy = 0;
		midPanel.add(enclosedStack,c);

		CardStack openStack = controller.getOpenedStack();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=3;
		c.gridheight=1;
		c.weightx=0.2;
		c.gridx = 2;
		c.gridy = 0;
		midPanel.add(openStack,c);
		

		CardStack[] resolvedStacks = controller.getResolvedStacks();
		for (int i=0;i<resolvedStacks.length;i++)
		{
			CardStack stack=resolvedStacks[i];
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth=1;
			c.gridheight=1;
			c.weightx=0.2;
			c.gridx = 5+i;
			c.gridy = 0;
			midPanel.add(stack,c);
		}

		CardStack[] listedStacks = controller.getListedStacks();
		for (int i=0;i<listedStacks.length;i++)
		{
			CardStack stack=listedStacks[i];
			c.anchor = GridBagConstraints.NORTH;
			c.fill = GridBagConstraints.BOTH;
			c.ipady=0;
			c.gridwidth=1;
			c.gridheight=6;
			c.weightx=0.1;
			c.weighty=1;
			c.gridx = i;
			c.gridy = 1;
			midPanel.add(stack,c);
		}
		
		frame.addMouseMotionListener(new CardStackMouseListener());
		frame.addMouseListener(new CardStackMouseListener());
	}
	
	private class CardStackMouseListener extends MouseInputAdapter {
		public void mouseReleased(MouseEvent e) {
			if (!GameController.getInstance().isInDragging())
				return;
			GameController.getInstance().clearDragInfo();
		}
	}

}
