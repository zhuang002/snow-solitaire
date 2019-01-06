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
		int y = 0;
		for (Card card:this.cards) {
			card.draw();
			card.setLocation(0, y);
			this.add(card);
			y += GameController.getInstance().getListVertical();
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
			if (stack.cards.getLast().getCardNumber()==card.getCardNumber()-1) {
				stack.cards.add(card);
				this.cards.removeLast();
				stack.draw();
				stack.repaint();
				this.draw();
				this.repaint();
			}
		}
	}

	@Override
	public void onDrop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrag(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDblClick() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
