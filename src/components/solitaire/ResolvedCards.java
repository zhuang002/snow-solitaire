package components.solitaire;

import java.io.IOException;

public class ResolvedCards extends CardStack {

	public ResolvedCards() throws IOException  {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		this.removeAll();
		if (this.cards.isEmpty()) return;
		Card card=this.cards.getLast();
		card.setLocation(0,0);
		this.add(card);
	}

	@Override
	public void onClickCard(Card card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDblClickCard(Card card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrop() {
		// TODO Auto-generated method stub
		DragInfo info=GameController.getInstance().getDragInfo();
		if (info.dragStack.cards.size()>1) return; // only one card is allowed to drop to this stack.
		
		Card sourceCard=info.dragStack.cards.getFirst();
		if (GameController.getInstance().getResolvedStackSuit(this)!=sourceCard.getCardSuit())
			return; // must be same suit
		
		if (this.cards.isEmpty()) {
			if (sourceCard.getCardNumber()!=1)
				return; // an empty target only allow A to drop.
			else { // allow drop A
				super.onDrop();
				return;
			}
		}
		Card targetCard=this.cards.getLast();
		if (targetCard.getCardSuit()==sourceCard.getCardSuit()
				&& targetCard.getCardNumber()==sourceCard.getCardNumber()-1)
			super.onDrop();
	}

	@Override
	public void onDrag(Card card) {
		// TODO Auto-generated method stub
		// only allow last card to be dragged.
		if (this.cards.getLast()==card) {
			try {
				CardStack dragStack;
				dragStack = new ResolvedCards();
				dragStack.appendCard(card);
				GameController.getInstance().setDragInfo(new DragInfo(this,dragStack));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
