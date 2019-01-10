package solitaire;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import components.solitaire.CardStack;
import components.solitaire.GameController;
import components.solitaire.UICallback;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class Solitaire {

	private JFrame frame;
	private Timer timer=new Timer();
	private UpdateTimerTask timerTask=null;

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
		
		upperPanel.add(btnStart);
		
		JButton btnRestart = new JButton("Restart");
		btnRestart.setEnabled(false);
		
		upperPanel.add(btnRestart);
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (timerTask.isStarted()) {
					timerTask.pause();
					GameController.getInstance().freeze();
					btnPause.setText("Resume");
				} else {
					GameController.getInstance().unfreeze();
					timerTask.start();
					btnPause.setText("Pause");
				}
			}
		});
		upperPanel.add(btnPause);
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.setEnabled(false);
		upperPanel.add(btnUndo);
		
		JButton btnRedo = new JButton("Redo");
		btnRedo.setEnabled(false);
		upperPanel.add(btnRedo);
		
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
		
		Dimension lbDimension=new Dimension(150,16);
		JLabel lblMoves = new JLabel(" # Moves: 0");
		lblMoves.setPreferredSize(lbDimension);
		lowerPanel.add(lblMoves);
		
		JLabel lblTime = new JLabel("Time Elapsed: 0s");
		lblTime.setPreferredSize(lbDimension);
		lowerPanel.add(lblTime);
		
		JLabel lblScore = new JLabel("Score #: 0");
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
		
		timerTask=new UpdateTimerTask(lblTime);
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GameController.getInstance().start();
					btnRestart.setEnabled(true);
					timerTask.reset();
					timerTask.start();
					frame.repaint();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GameController.getInstance().restoreStacks();
					timerTask.reset();
					timerTask.start();
					frame.repaint();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUndo.setEnabled(GameController.getInstance().undo());
				btnRedo.setEnabled(true);
				frame.invalidate();
				frame.repaint();
			}
		});
		
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRedo.setEnabled(GameController.getInstance().redo());
				btnUndo.setEnabled(true);
				frame.invalidate();
				frame.repaint();
			}
		});
		
		frame.addMouseMotionListener(new CardStackMouseListener());
		frame.addMouseListener(new CardStackMouseListener());
		
		GameController.getInstance().setNotifyMoveCallback(new MyCallback(lblMoves, lblScore, btnRedo, btnUndo));
	}
	
	
	private class MyCallback implements UICallback {
		JLabel lblMoves;
		JLabel lblScore;
		JButton btnRedo;
		JButton btnUndo;
		
		public MyCallback(JLabel move, JLabel score, JButton redo, JButton undo) {
			this.lblMoves=move;
			this.lblScore=score;
			this.btnRedo=redo;
			this.btnUndo=undo;
		}
		@Override
		public void notifyMove() {
			// TODO Auto-generated method stub
			GameController ctrl=GameController.getInstance();
			lblMoves.setText("#Moves: "+ctrl.getMoves());
			btnRedo.setEnabled(!ctrl.getHistoryQueue().isTail());
			btnUndo.setEnabled(!ctrl.getHistoryQueue().isHead());
			lblScore.setText("Score #:"+ctrl.getScore());
		}
		
	}
	
	private class CardStackMouseListener extends MouseInputAdapter {
		public void mouseReleased(MouseEvent e) {
			if (!GameController.getInstance().isInDragging())
				return;
			GameController.getInstance().clearDragInfo();
		}
	}
	
	private class UpdateTimerTask extends TimerTask {
		private JLabel timerLabel=null;
		private int counter=0;
		private boolean isRunning=false;
		public UpdateTimerTask(JLabel label) {
			this.timerLabel=label;
		}
		public void reset() {
			// TODO Auto-generated method stub
			this.isRunning=false;
			this.counter=0;
		}
		public void start() {
			// TODO Auto-generated method stub
			this.isRunning=true;
		}
		public void pause() {
			// TODO Auto-generated method stub
			this.isRunning=false;
		}
		public boolean isStarted() {
			// TODO Auto-generated method stub
			return this.isRunning;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if (this.isRunning) {
				counter++;
				this.timerLabel.setText("Time Elapsed:"+counter+"s");
				this.timerLabel.repaint();
			}
		}
		
	}
	
	

}
