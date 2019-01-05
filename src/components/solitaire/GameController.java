package components.solitaire;

import java.io.IOException;
import java.security.InvalidParameterException;

public class GameController {
	static GameController instance=null;
	int level=1;
	int listHorizontal=20;
	int listVertical=20;
	int listStacksSize=10;
	int resolvedStacksSize=4;
	
	int scale=1;
	OpenedCards openedStack=null;
	EnclosedCards closeStack=null;
	ListedCards[] listStacks=null;
	ResolvedCards[] resolvedStacks=null;
	
	
	public GameController()  {
		/*openedStack=new OpenedCards();
		closeStack  = new EnclosedCards();
		for (int i=0;i<listStacks.length;i++)
			listStacks[i]=new ListedCards();
		for (int i=0;i<resolvedStacks.length;i++)
			resolvedStacks[i]=new ResolvedCards();*/
	}
	
	static public GameController getInstance()  {
		if (instance==null) {
			instance=new GameController();
		}
		return instance;
			
	}

	public CardStack getCardStackFromCard(Card card) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLevel() {
		// TODO Auto-generated method stub
		return this.level;
	}

	
	
	public int getListHorizontal() {
		return this.listHorizontal*this.scale;
	}
	
	public int getListVertical() {
		return this.listVertical*this.scale;
	}

	public ResolvedCards getResolvedStack(CardSuit suit) {
		// TODO Auto-generated method stub
		switch (suit) {
			case Spade:
				return this.resolvedStacks[0];
			case Heart:
				return this.resolvedStacks[1];
			case Diamond:
				return this.resolvedStacks[2];
			case Club:
				return this.resolvedStacks[3];
			default:
					throw new InvalidParameterException();
		}
	}

	public void setClosedStack(EnclosedCards enclosedStack) {
		// TODO Auto-generated method stub
		this.closeStack=enclosedStack;
	}
	
	public CardStack getClosedStack() throws IOException  {
		if (this.closeStack==null)
			this.closeStack=new EnclosedCards();
		return this.closeStack;
	}

	public void setOpenedStack(OpenedCards openedStack) {
		// TODO Auto-generated method stub
		this.openedStack=openedStack;
	}
	
	public CardStack getOpenedStack() throws IOException {
		// TODO Auto-generated method stub
		if (this.openedStack==null)
			this.openedStack=new OpenedCards();
		return this.openedStack;
	}

	public void setResolvedStacks(ResolvedCards[] stacks) {
		// TODO Auto-generated method stub
		this.resolvedStacks=stacks;
	}
	
	public CardStack[] getResolvedStacks() throws IOException {
		if (this.resolvedStacks==null) {
			this.resolvedStacks=new ResolvedCards[this.resolvedStacksSize];
			for (int i=0;i<this.resolvedStacksSize;i++)
				this.resolvedStacks[i]=new ResolvedCards();
		}
		return this.resolvedStacks;
	}

	public void setListedStacks(ListedCards[] stacks) {
		// TODO Auto-generated method stub
		this.listStacks=stacks;
	}
	
	public CardStack[] getListedStacks() throws IOException {
		if (this.listStacks==null) {
			this.listStacks=new ListedCards[this.listStacksSize];
			for (int i=0;i<this.listStacksSize;i++)
				this.listStacks[i]=new ListedCards();
		}
		return this.listStacks;
	}

	public int getHorizontalEdge() {
		// TODO Auto-generated method stub
		return 10;
	}
	
	public int getVerticalEdge() {
		// TODO Auto-generated method stub
		return 10;
	}

	public void setLevel(int lev) {
		// TODO Auto-generated method stub
		this.level=lev;
	}

}
