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
		Card card=this.cards.getLast();
		card.draw();
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
