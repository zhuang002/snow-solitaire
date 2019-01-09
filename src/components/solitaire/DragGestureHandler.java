package components.solitaire;

import java.awt.Cursor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.IOException;

public class DragGestureHandler implements DragGestureListener, DragSourceListener {
	CardStack sourceStack;
	CardStack dragStack;
	Card sourceCard;
	
	public DragGestureHandler(Card card) {
		this.sourceCard=card;
		
	}
	@Override
	public void dragEnter(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DragSourceEvent dse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		// TODO Auto-generated method stub
		// If the drop was not successful, we need to
        // return the component back to it's previous
        // parent
        if (!dsde.getDropSuccess()) {
        	this.sourceStack.cards.addAll(this.dragStack.cards);
        	this.sourceStack.invalidate();
        	this.sourceStack.repaint();
        } else {
        	if (!this.sourceStack.cards.isEmpty()) {
        		this.sourceStack.cards.getLast().setFaceUp(true);
        		this.sourceStack.invalidate();
            	this.sourceStack.repaint();
        	}
        }
        GameController.getInstance().clearDragInfo();
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		// TODO Auto-generated method stub
		
		try {
			this.sourceStack=(CardStack)this.sourceCard.getParent();
			this.dragStack=new ListedCards();
			int idx=this.sourceStack.cards.indexOf(this.sourceCard);
			int n=0;
			for (int i=idx;i<this.sourceStack.cards.size();i++) {
				this.dragStack.cards.add(this.sourceStack.cards.get(i));
				n++;
			}
			for (int i=0;i<n;i++) {
				this.sourceStack.cards.removeLast();
			}
			this.sourceStack.invalidate();
			this.sourceStack.repaint();
			
			// Start the "drag" process...
			GameController.getInstance().setDragInfo(new DragInfo(this.sourceStack,this.dragStack));
			Transferable transferable = new DataTransferable(this.dragStack);
	        DragSource ds = dge.getDragSource();
	        ds.startDrag(dge, Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR), transferable, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
