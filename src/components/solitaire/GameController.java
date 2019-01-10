package components.solitaire;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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
	LinkedList<Card> enclosedCardsBK=null;
	ListedCards[] listStacks=null;
	LinkedList<Card>[] listStacksBK=null;
	ResolvedCards[] resolvedStacks=null;
	Random rand=new Random();
	DragInfo dragInfo=null;
	boolean isFreezed=false;
	HistoryQueue historyQueue=new HistoryQueue();
	int moves=0;
	UICallback uiCallback=null;
	
	public GameController()  {
	}
	
	static public GameController getInstance()  {
		if (instance==null) {
			instance=new GameController();
		}
		return instance;
			
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
		if (this.closeStack==null) {
			this.closeStack=new EnclosedCards();
			this.enclosedCardsBK=new LinkedList<Card>();
		}
		return this.closeStack;
	}

	public void setOpenedStack(OpenedCards openedStack) {
		// TODO Auto-generated method stub
		this.openedStack=openedStack;
	}
	
	public CardStack getOpenedStack() throws IOException {
		// TODO Auto-generated method stub
		if (this.openedStack==null) {
			this.openedStack=new OpenedCards();
		}
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
			this.listStacksBK=new LinkedList[this.listStacksSize];
			for (int i=0;i<this.listStacksSize;i++) {
				this.listStacks[i]=new ListedCards();
				this.listStacksBK[i]=new LinkedList<Card>();
			}
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
		
		
		this.historyQueue.clear();
		this.historyQueue.addHistory();
		this.moves=0;
		this.uiCallback.notifyMove();
	}
	
	
	public void restoreStacks() {
		History history=this.historyQueue.rewind();
		this.copyFromHistory(history);
		this.moves=0;
		this.uiCallback.notifyMove();
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

	public void freeze() {
		// TODO Auto-generated method stub
		this.isFreezed=true;
	}

	public void unfreeze() {
		// TODO Auto-generated method stub
		this.isFreezed=false;
	}
	
	public boolean IsFreezed() {
		return this.isFreezed;
	}

	public boolean undo() {
		// TODO Auto-generated method stub
		History history=this.historyQueue.stepBack();
		if (history==null) return false;
		this.copyFromHistory(history);
		this.moves--;
		this.uiCallback.notifyMove();
		return !this.historyQueue.isHead();
	}

	public boolean redo() {
		// TODO Auto-generated method stub
		History history=this.historyQueue.stepForward();
		if (history==null) return false;
		this.copyFromHistory(history);
		this.moves++;
		this.uiCallback.notifyMove();
		return !this.historyQueue.isTail();
	}

	

	private void copyFromHistory(History history) {
		// TODO Auto-generated method stub
		this.openedStack.cards=history.cloneCards(history.getOpenedCards());
		this.closeStack.cards=history.cloneCards(history.getEnclosedCards());
		for (int i=0;i<this.resolvedStacksSize;i++) {
			this.resolvedStacks[i].cards=history.cloneCards(history.getResolvedStackCards(i));
		}
		for (int i=0;i<this.listStacksSize;i++) {
			this.listStacks[i].cards=history.cloneCards(history.getListStackCards(i));
		}
	}

	private void copyToHistory(History history) {
		// TODO Auto-generated method stub
		history.cloneOpenedCards(this.openedStack.cards);
		history.cloneEnclosedCards(this.closeStack.cards);
		for (int i=0;i<this.resolvedStacksSize;i++) {
			history.cloneResolvedStackCards(i,this.resolvedStacks[i].cards);
		}
		for (int i=0;i<this.listStacksSize;i++) {
			history.cloneListStackCards(i,this.listStacks[i].cards);
		}
	}
	
	public int getScore() {
		int score=0;
		for (int i=0;i<this.resolvedStacksSize;i++) {
			score+=this.resolvedStacks[i].cards.size();
		}
		return score;
	}

	private class History {
		LinkedList<Card> closedCards;
		LinkedList<Card> openedCards;
		ArrayList<LinkedList<Card>> resolvedStacksCards=new ArrayList<LinkedList<Card>>();
		ArrayList<LinkedList<Card>> listStacksCards=new ArrayList<LinkedList<Card>>();
		
		public LinkedList<Card> getOpenedCards() {
			// TODO Auto-generated method stub
			return this.openedCards;
		}

		public void cloneListStackCards(int i, LinkedList<components.solitaire.Card> cards) {
			// TODO Auto-generated method stub
			for (int j=listStacksCards.size();j<=i;j++) {
				this.listStacksCards.add(null);
			}
			this.listStacksCards.set(i, cloneCards(cards));
			
		}

		private LinkedList<Card> cloneCards(LinkedList<Card> cards) {
			// TODO Auto-generated method stub
			if (cards==null) return null;
			LinkedList<Card> ret=new LinkedList<Card>();
			for (int i=0;i<cards.size();i++) {
				ret.add(cards.get(i).clone());
			}
			return ret;
		}

		public void cloneResolvedStackCards(int i, LinkedList<components.solitaire.Card> cards) {
			// TODO Auto-generated method stub
			for (int j=resolvedStacksCards.size();j<=i;j++) {
				this.resolvedStacksCards.add(null);
			}
			this.resolvedStacksCards.set(i, cloneCards(cards));
		}

		public void cloneEnclosedCards(LinkedList<components.solitaire.Card> cards) {
			// TODO Auto-generated method stub
			this.closedCards=cloneCards(cards);
		}

		public void cloneOpenedCards(LinkedList<components.solitaire.Card> cards) {
			// TODO Auto-generated method stub
			this.openedCards=cloneCards(cards);
		}

		public LinkedList<Card> getListStackCards(int i) {
			// TODO Auto-generated method stub
			return this.listStacksCards.get(i);
		}

		public LinkedList<Card> getResolvedStackCards(int i) {
			// TODO Auto-generated method stub
			return this.resolvedStacksCards.get(i);
		}

		public LinkedList<Card> getEnclosedCards() {
			// TODO Auto-generated method stub
			return this.closedCards;
		}
		
		
	}
	
	public class HistoryQueue {
		ArrayList<History> queue=new ArrayList<History>();
		int current=-1;

		public History stepForward() {
			// TODO Auto-generated method stub
			if (isTail()) return null;
			current++;
			return queue.get(current);

		}

		public void clear() {
			// TODO Auto-generated method stub
			current=-1;
			this.queue=new ArrayList<History>();
		}

		public History rewind() {
			// TODO Auto-generated method stub
			if (this.queue.isEmpty()) {
				current=-1;
				return null;
			}
			current=0;
			return this.queue.get(0);
		}

		public void addHistory() {
			// TODO Auto-generated method stub
			History history=new History();
			GameController.getInstance().copyToHistory(history);
			
			if (!this.queue.isEmpty()) {
				int size=this.queue.size();
				for (int i=current+1;i<size;i++) {
					this.queue.remove(current+1);
				}
			}
			this.queue.add(history);
			current++;
		}

		public boolean isTail() {
			// TODO Auto-generated method stub
			if (this.queue.isEmpty()) {
				current=-1;
				return true;
			}
			return current>=this.queue.size()-1;
		}

		public boolean isHead() {
			// TODO Auto-generated method stub
			if (this.queue.isEmpty()) {
				current=-1;
				return true;
			}
			return current<=0;
		}

		public History stepBack() {
			// TODO Auto-generated method stub
			if (isHead()) return null;
			current--;
			return queue.get(current);
		}
	}

	public void notifyMove() {
		// TODO Auto-generated method stub
		this.historyQueue.addHistory();
		this.moves++;
		this.uiCallback.notifyMove();
		
	}

	public int getMoves() {
		// TODO Auto-generated method stub
		return this.moves;
	}
	
	public HistoryQueue getHistoryQueue() {
		return this.historyQueue;
	}

	public void setNotifyMoveCallback(UICallback myCallback) {
		// TODO Auto-generated method stub
		this.uiCallback=myCallback;
	}
	

}
