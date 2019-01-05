package components.solitaire;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public abstract class CardStack extends JPanel {

	protected LinkedList<Card> cards;
	protected Boolean dropable = false;
	protected Boolean dragable = false;
	protected BufferedImage emptyImg = null;

	public CardStack() throws IOException {
		emptyImg =  ImageIO.read(CardStack.class.getClassLoader().getResource("b.gif"));
		
		int hEdge=GameController.getInstance().getHorizontalEdge();
		int vEdge=GameController.getInstance().getVerticalEdge();
		//setPreferredSize(new Dimension(emptyImg.getWidth()+hEdge,vEdge));
		setBorder(new EmptyBorder(vEdge,vEdge,hEdge,hEdge));
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
	

}
