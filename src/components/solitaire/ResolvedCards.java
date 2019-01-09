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

	
}
