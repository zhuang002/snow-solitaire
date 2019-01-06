package components.solitaire;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class Card extends JPanel  {
	int ID;
	boolean faceup = false;
	BufferedImage img = null;
	BufferedImage foreImg=null;
	static BufferedImage backImg = null;

	public Card(CardSuit s, int n, boolean f, Dimension size) throws IOException {
		Initialize(getId(s,n),f,size);
	}
	
	private void Initialize(int id, boolean f, Dimension size) throws IOException {
		// TODO Auto-generated method stub
		this.ID=id;
		
		this.faceup = f;
		this.setSize(size);
		foreImg = ImageIO.read(CardStack.class.getClassLoader().getResource(this.getImagePath()));
		backImg = ImageIO.read(CardStack.class.getClassLoader().getResource("b.gif"));
		this.addMouseMotionListener(new CardMouseListener());
		this.addMouseListener(new CardMouseListener());
	}

	public Card(int id, boolean f, Dimension size) throws IOException {
		Initialize(id,f,size);
	}


	private int getId(CardSuit s, int n) {
		// TODO Auto-generated method stub
		int id=0;
		switch (s) {
			case Spade:
				id=0;
				break;
			case Heart:
				id=13;
				break;
			case Diamond:
				id=26;
				break;
			case Club:
				id=39;
				break;
			default:
				throw new InvalidParameterException();
		}
		id+=n-1;
		return id;
	}

	public CardSuit getCardSuit() {
		switch (this.ID/13) {
			case 0:
				return CardSuit.Club;
			case 1:
				return CardSuit.Diamond;
			case 2:
				return CardSuit.Heart;
			case 3:
				return CardSuit.Spade;
			default:
				throw new InvalidParameterException();
		}
	}
	
	public int getCardNumber() {
		return this.ID%13+1;
	}
	private String getImagePath() {
		// TODO Auto-generated method stub
		return Integer.toString(this.getCardNumber())+Character.toLowerCase(this.getCardSuit().toString().charAt(0))+".gif";
	}

	public void draw() {
		this.img=this.faceup?this.foreImg:backImg;
	}

	public CardColor getColor() {
		CardSuit suit=this.getCardSuit();
		if (suit == CardSuit.Diamond || suit == CardSuit.Heart)
			return CardColor.Red;
		return CardColor.Black;
	}

	public void onClick() throws IOException {
		CardStack cardStack = GameController.getInstance().getCardStackFromCard(this);
		cardStack.onClickCard(this);
	}

	public void onDblClick() throws IOException {
		CardStack cardStack = GameController.getInstance().getCardStackFromCard(this);
		cardStack.onDblClickCard(this);
	}

	public void onDrag() throws IOException {
		CardStack cardStack = GameController.getInstance().getCardStackFromCard(this);
		cardStack.onDrag(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // paint the background image and scale it to fill the entire space
		if (this.img != null) {
			g.drawImage(this.img, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	public void setFaceUp(boolean b) {
		// TODO Auto-generated method stub
		this.faceup=b;
	}

	private class CardMouseListener extends MouseInputAdapter {
		public void mouseClicked(MouseEvent e) {
			try {
				if (e.isConsumed()) return;
				Card card=(Card)e.getComponent();
				if (e.getClickCount()==2)
					card.onDblClick();
				else 
					card.onClick();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
