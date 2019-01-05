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
		} else if (this.cards.size() < 3) {
			n = this.cards.size();
		} else
			n = 3;

		int x = 0;
		for (int i = 0; i < n; i++) {
			Card card = this.cards.get(this.cards.size() - i);
			card.setLocation(x, 0);
			card.draw();
			this.add(card);
			x += GameController.getInstance().getListHorizontal();
		}
	}

	@Override
	public void onClickCard(Card card) throws IOException {

	}

	@Override
	public void onDblClickCard(Card card) {
		// TODO Auto-generated method stub
		if (this.cards.getLast().equals(card)) {
			CardSuit suit = card.getCardSuit();
			ResolvedCards stack = GameController.getInstance().getResolvedStack(suit);
			if (stack.cards.getLast().getCardNumber() == card.getCardNumber() - 1) {
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
