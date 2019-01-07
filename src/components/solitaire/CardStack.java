package components.solitaire;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;


public abstract class CardStack extends JPanel {

	protected LinkedList<Card> cards=new LinkedList<Card>();
	protected Boolean dropable = false;
	protected Boolean dragable = false;
	protected BufferedImage emptyImg = null;

	public CardStack() throws IOException {
		emptyImg =  ImageIO.read(CardStack.class.getClassLoader().getResource("empty.png"));
		
		//int hEdge=GameController.getInstance().getHorizontalEdge();
		//int vEdge=GameController.getInstance().getVerticalEdge();
		setPreferredSize(new Dimension(emptyImg.getWidth(),emptyImg.getHeight()));
		//setBorder(new EmptyBorder(vEdge,vEdge,hEdge,hEdge));
		
		this.addMouseMotionListener(new CardStackMouseListener());
		this.addMouseListener(new CardStackMouseListener());
	}

	
	
	public void appendCard(Card card) {
		this.cards.addLast(card);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // paint the background image and scale it to fill the entire space
		g.drawImage(emptyImg, 0, 0, emptyImg.getWidth(), emptyImg.getHeight(), this);
	}

	abstract public void onClickCard(Card card) throws IOException;

	abstract public void onDblClickCard(Card card) throws IOException;

	abstract public void onDrop();
	
	abstract public void draw() throws IOException;
	
	abstract public void onDrag(Card card);
	
	abstract public void onDblClick() throws IOException;
	
	abstract public void onClick() throws IOException;



	protected boolean containsCard(Card card) {
		return this.cards.contains(card);
	}
	
	private class CardStackMouseListener extends MouseInputAdapter {
		public void mouseClicked(MouseEvent e) {
			try {
				if (e.isConsumed()) return;
				CardStack stack=(CardStack)e.getComponent();
				if (e.getClickCount()==2)
					stack.onDblClick();
				else 
					stack.onClick();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}
}
