package components.solitaire;

import java.io.IOException;

public class EnclosedCards extends CardStack {

	public EnclosedCards() throws IOException  {
		super();
		// TODO Auto-generated constructor stub
		
		
	}

	@Override
	public void draw() {
		this.removeAll();
		if (!this.cards.isEmpty()) {
			Card card=this.cards.getLast();
			card.setLocation(0,0);
			this.add(card);
		}
	}

	@Override
	public void onClickCard(Card card) throws IOException {
		// TODO Auto-generated method stub
		if (GameController.getInstance().isFreezed) return;
		int n=0;
		if (GameController.getInstance().getLevel()==0) {
			n=1;
		} else if (this.cards.size()>=3) {
			n=3;
		} else {
			n=this.cards.size();
		}
		CardStack openStack=GameController.getInstance().getOpenedStack();
		for (int i=0;i<n;i++) {
			Card c=this.cards.getLast();
			c.setFaceUp(true);
			openStack.cards.add(c);
			this.cards.removeLast();
		}
		openStack.repaint();
		this.repaint();
	}


	@Override
	public void onDblClick() throws IOException {
		// TODO Auto-generated method stub
		if (GameController.getInstance().isFreezed) return;
		if (this.cards.isEmpty()) {
			CardStack openedStack=GameController.getInstance().getOpenedStack();
			for (Card card:openedStack.cards) {
				card.setFaceUp(false);
				this.cards.add(card);
			}
			openedStack.cards.clear();
			openedStack.repaint();
			this.repaint();
		}
	}

	@Override
	public void onClick() throws IOException {
		// TODO Auto-generated method stub
		onDblClick();
	}

}
