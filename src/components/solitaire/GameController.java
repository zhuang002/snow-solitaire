package components.solitaire;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Random;

import javax.imageio.ImageIO;

public class GameController {
	static GameController instance=null;
	int level=1;
	int listHorizontal=20;
	int listVertical=20;
	int listStacksSize=8;
	int resolvedStacksSize=4;
	CardSuit[] resolvedStackSuits= {CardSuit.Spade,CardSuit.Heart,CardSuit.Diamond,CardSuit.Club};
	Dimension cardDimension=null;
	int scale=1;
	OpenedCards openedStack=null;
	EnclosedCards closeStack=null;
	ListedCards[] listStacks=null;
	ResolvedCards[] resolvedStacks=null;
	Random rand=new Random();
	DragInfo dragInfo=null;
	
	
	public GameController()  {
	}
	
	static public GameController getInstance()  {
		if (instance==null) {
			instance=new GameController();
		}
		return instance;
			
	}

	/*public CardStack getCardStackFromCard(Card card) {
		// TODO Auto-generated method stub
		if (this.closeStack.containsCard(card)) return this.closeStack;
		if (this.openedStack.containsCard(card)) return this.openedStack;
		for (CardStack stack:resolvedStacks) {
			if (stack.containsCard(card)) return stack;
		}
		for (CardStack stack:listStacks) {
			if (stack.containsCard(card)) return stack;
		}
		return null;
	}*/

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
		for (int i=0;i<this.resolvedStackSuits.length;i++) {
			if (this.resolvedStackSuits[i]==suit) {
				return this.resolvedStacks[i];
			}
		}
		return null;
	}
	
	public CardSuit getResolvedStackSuit(CardStack stack) {
		// TODO Auto-generated method stub
		for (int i=0;i<this.resolvedStackSuits.length;i++) {
			if (this.resolvedStacks[i]==stack) {
				return this.resolvedStackSuits[i];
			}
		}
		return CardSuit.None;
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
			for (int i=0;i<this.resolvedStacksSize;i++) {
				this.resolvedStacks[i]=new ResolvedCards();
			}
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
	
	public Dimension getCardDimension() throws IOException {
		if (this.cardDimension==null) {
			BufferedImage emptyImg =  ImageIO.read(CardStack.class.getClassLoader().getResource("empty.png"));
			this.cardDimension=new Dimension(emptyImg.getWidth(),emptyImg.getHeight());
		}
		return this.cardDimension;
	}


	public void setLevel(int lev) {
		// TODO Auto-generated method stub
		this.level=lev;
	}

	public void start() throws IOException {
		// TODO Auto-generated method stub
		int[] cards=suffleCards();
		
		this.getClosedStack();
		this.getOpenedStack();
		this.getResolvedStacks();
		this.getListedStacks();
		
		this.openedStack.cards.clear();
		this.closeStack.cards.clear();
		for (CardStack stack:this.resolvedStacks) 
			stack.cards.clear();
		for (CardStack stack:this.listStacks)
			stack.cards.clear();
		
		int idx=0;
		for (int i=0;i<this.listStacksSize;i++) {
			for (int j=i;j<this.listStacksSize;j++) {
				this.listStacks[j].cards.add(new Card(cards[idx],false,this.getCardDimension()));
				idx++;
			}
		}
		
		for (int i=0;i<this.listStacksSize;i++) {
			this.listStacks[i].cards.getLast().setFaceUp(true);
		}
		
		for (int i=51;i>=idx;i--) {
			this.closeStack.cards.add(new Card(cards[i],false,this.getCardDimension()));
		}

	}

	private int[] suffleCards() {
		// TODO Auto-generated method stub
		int[] cards=new int[52];
		for (int i=0;i<cards.length;i++) {
			cards[i]=i;
		}
		for (int i=0;i<cards.length;i++) {
			int idx=rand.nextInt(cards.length);
			int tmp=cards[idx];
			cards[idx]=cards[i];
			cards[i]=tmp;
		}
		return cards;
	}

	public void setDragInfo(DragInfo info) {
		// TODO Auto-generated method stub
		this.dragInfo=info;
	}
	
	public DragInfo getDragInfo() {
		return this.dragInfo;
	}

	public boolean isInDragging() {
		// TODO Auto-generated method stub
		return this.dragInfo!=null;
	}

	public void clearDragInfo() {
		// TODO Auto-generated method stub
		this.dragInfo=null;
	}

	

	
	

}
