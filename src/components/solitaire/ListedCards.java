package components.solitaire;

import java.io.IOException;

public class ListedCards extends CardStack {

	public ListedCards() throws IOException  {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		this.removeAll();
		int y = (this.cards.size()-1)*GameController.getInstance().getListVertical();
		for (int i=this.cards.size()-1;i>=0;i--) {
			Card card=this.cards.get(i);
			card.setLocation(0, y);
			this.add(card);
			y -= GameController.getInstance().getListVertical();
		}
	}

	@Override
	public void onClickCard(Card card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDblClickCard(Card card) throws IOException {
		// TODO Auto-generated method stub
		if (this.cards.isEmpty()) return;
		if (this.cards.getLast().equals(card)) {
			CardSuit suit=card.getCardSuit();
			ResolvedCards stack=GameController.getInstance().getResolvedStack(suit);
			if (stack.cards.isEmpty() && card.getCardNumber()==1 || 
					stack.cards.getLast().getCardNumber()==card.getCardNumber()-1) {
				stack.cards.add(card);
				this.cards.removeLast();
				if (!this.cards.isEmpty())
					this.cards.getLast().setFaceUp(true);
				stack.repaint();
				this.repaint();
			}
		}
	}

	/*@Override
	public void onDrop() {
		// TODO Auto-generated method stub
		// allow floating stack to dropped if color is different and the number is successive
		boolean allowDrop=false;
		Card dragCard=GameController.getInstance().getDragInfo().dragStack.cards.getFirst();
		if (this.cards.isEmpty()) {
			
			if (dragCard.getCardNumber()==13) allowDrop=true;
		} else {
			Card targetCard=this.cards.getLast();
			if (targetCard.getColor()!=dragCard.getColor() && targetCard.getCardNumber()-1==dragCard.getCardNumber())
				allowDrop=true;
		}
		if (allowDrop)
			super.onDrop();
	}*/

	/*@Override
	public void onDrag(Card card) {
		// TODO Auto-generated method stub
		// allow all faceup cards to be dragged.
		
		try {
			CardStack dragStack;
			dragStack = new ListedCards();
			int idx=this.cards.indexOf(card);
			for (int i=idx;i<this.cards.size();i++)
				dragStack.appendCard(this.cards.get(i));
			GameController.getInstance().setDragInfo(new DragInfo(this,dragStack));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}
