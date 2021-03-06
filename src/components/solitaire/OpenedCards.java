package components.solitaire;

import java.io.IOException;

public class OpenedCards extends CardStack {

	public OpenedCards() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		this.removeAll();
		int level = GameController.getInstance().getLevel();
		int n = 0;
		if (level == 0) {
			n = 1;
		} else 
			n=3;
		if (this.cards.size() < n) {
			n = this.cards.size();
		}

		int x = (n-1)*GameController.getInstance().getListHorizontal();
		for (int i = 0; i <n; i++) {
			Card card = this.cards.get(this.cards.size()-1-i);
			card.setLocation(x, 0);
			this.add(card);
			x -= GameController.getInstance().getListHorizontal();
		}
	}

	@Override
	public void onClickCard(Card card) throws IOException {
		
	}

	@Override
	public void onDblClickCard(Card card) {
		// TODO Auto-generated method stub
		if (GameController.getInstance().isFreezed) return;
		if (this.cards.getLast().equals(card)) {
			CardSuit suit = card.getCardSuit();
			ResolvedCards stack = GameController.getInstance().getResolvedStack(suit);
			if (stack.cards.isEmpty()) {
				if (card.getCardNumber()==1) {
					stack.cards.add(card);
					this.cards.removeLast();
				} else return;
			}
			else if (stack.cards.getLast().getCardNumber() == card.getCardNumber() - 1) {
				stack.cards.add(card);
				this.cards.removeLast();
			} else return;
			stack.repaint();
			this.repaint();
			GameController.getInstance().notifyMove();
		}
	}

	
}
