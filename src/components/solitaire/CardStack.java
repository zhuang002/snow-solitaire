package components.solitaire;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public abstract class CardStack extends JPanel {

	protected LinkedList<Card> cards = new LinkedList<Card>();
	protected BufferedImage emptyImg = null;
	
	

	public CardStack() throws IOException {
		emptyImg = ImageIO.read(CardStack.class.getClassLoader().getResource("empty.png"));

		setPreferredSize(new Dimension(emptyImg.getWidth(), emptyImg.getHeight()));

		this.addMouseMotionListener(new CardStackMouseListener());
		this.addMouseListener(new CardStackMouseListener());
	}
	
	

	public void appendCard(Card card) {
		this.cards.addLast(card);
	}

	@Override
	protected void paintComponent(Graphics g) {
		try {
			this.draw();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.paintComponent(g); // paint the background image and scale it to fill the entire space
		g.drawImage(emptyImg, 0, 0, emptyImg.getWidth(), emptyImg.getHeight(), this);
		
	}

	protected void onClickCard(Card card) throws IOException {
		
	}

	protected void onDblClickCard(Card card) throws IOException {
		
	}

	protected void onDrop() {
		DragInfo info = GameController.getInstance().getDragInfo();
		this.cards.addAll(info.dragStack.cards);
		info.sourceStack.cards.removeAll(info.dragStack.cards);
		if (!info.sourceStack.cards.isEmpty() && !info.sourceStack.cards.getLast().isFaceUp()) {
			info.sourceStack.cards.getLast().setFaceUp(true);
		}
		
		info.sourceStack.repaint();
		this.repaint();
	}

	protected void draw() throws IOException {
		
	}

	protected void onDrag(Card card) {
		
	}

	protected void onDblClick() throws IOException {
		
	}

	protected void onClick() throws IOException {
		
	}

	protected boolean containsCard(Card card) {
		return this.cards.contains(card);
	}

	private class CardStackMouseListener extends MouseInputAdapter {
		public void mouseClicked(MouseEvent e) {
			try {
				if (e.isConsumed())
					return;
				CardStack stack = (CardStack) e.getComponent();
				if (e.getClickCount() == 2)
					stack.onDblClick();
				else
					stack.onClick();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (!GameController.getInstance().isInDragging())
				return;

			CardStack targetStack = (CardStack) e.getComponent();
			targetStack.onDrop();
			GameController.getInstance().clearDragInfo();
		}
	}
	
}
