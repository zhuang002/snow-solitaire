package components.solitaire;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
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
	protected DropListener dropListener;

	public CardStack() throws IOException {
		emptyImg = ImageIO.read(CardStack.class.getClassLoader().getResource("empty.png"));

		setPreferredSize(new Dimension(emptyImg.getWidth(), emptyImg.getHeight()));

		this.addMouseMotionListener(new CardStackMouseListener());
		this.addMouseListener(new CardStackMouseListener());

		this.dropListener = new DropListener(this);
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


	protected void draw() throws IOException {

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

	}

	private class DropListener implements DropTargetListener {
		CardStack targetStack = null;
		DropTarget dropTarget = null;

		public DropListener(CardStack cardStack) {
			// TODO Auto-generated constructor stub
			this.targetStack = cardStack;
			this.dropTarget = new DropTarget(cardStack, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);
		}

		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dragExit(DropTargetEvent dtde) {
			// TODO Auto-generated method stub

		}

		@Override
		public void drop(DropTargetDropEvent dtde) {
			// TODO Auto-generated method stub
			if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
				// Accept the drop and get the transfer data
				CardStack dragStack = null;
				try {
					Transferable transferable = dtde.getTransferable();
					DataFlavor flavor = getFlavor(dtde);
					dragStack = getDataFromTransferable(transferable, flavor);
				} catch (Exception e) {

				}
				if (dragStack == null) {
					if (!GameController.getInstance().isInDragging()) {
						dtde.rejectDrop();
						return;
					}
					dragStack = GameController.getInstance().getDragInfo().dragStack;
				}

				if (dragStack == null) {
					dtde.rejectDrop();
					return;
				}
				if (!allowDrop(dragStack, this.targetStack)) {
					dtde.rejectDrop();
					return;
				}
				dtde.acceptDrop(dtde.getDropAction());
				try {
					boolean result = dropData(dragStack);
					dtde.dropComplete(result);
				} catch (Exception e) {
					dtde.dropComplete(false);
				}
			} else {
				dtde.rejectDrop();
			}

		}

		private boolean allowDrop(CardStack src, CardStack des) {
			// TODO Auto-generated method stub

			try {
				if (des == GameController.getInstance().getClosedStack())
					return false;
				if (des == GameController.getInstance().getOpenedStack())
					return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

			if (des.cards.isEmpty()) {
				if (src.cards.getFirst().getCardNumber() == 13)
					return true;
				else
					return false;
			}

			Card srcCard = src.cards.getFirst();
			Card desCard = des.cards.getLast();
			if (srcCard.getColor() != desCard.getColor() && srcCard.getCardNumber() + 1 == desCard.getCardNumber())
				return true;
			return false;
		}

		private CardStack getDataFromTransferable(Transferable transferable, DataFlavor flavor) {
			// TODO Auto-generated method stub

			Object data;
			try {
				data = transferable.getTransferData(flavor);
				if (data instanceof CardStack)
					return (CardStack) data;
				else
					return null;
			} catch (UnsupportedFlavorException | IOException e) {
				// TODO Auto-generated catch block
				return null;
			}
		}

		private DataFlavor getFlavor(DropTargetDropEvent dtde) {
			// TODO Auto-generated method stub
			DataFlavor[] fl = dtde.getCurrentDataFlavors();
			for (int i = 0; i < fl.length; i++) {
				Class dataClass = fl[i].getRepresentationClass();

				if (Component.class.isAssignableFrom(dataClass)) {
					// This flavor returns a Component - accept it.
					return fl[i];
				}
			}
			return null;
		}

		private boolean dropData(CardStack data) throws IOException {
			this.targetStack.cards.addAll(data.cards);
			this.targetStack.invalidate();
			this.targetStack.repaint();
			return true;
		}

	}

}
