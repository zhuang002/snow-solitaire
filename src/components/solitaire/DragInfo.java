package components.solitaire;

public class DragInfo {
	CardStack sourceStack;
	CardStack dragStack;
	
	public DragInfo(CardStack s,CardStack ds) {
		this.sourceStack=s;
		this.dragStack=ds;
	}
}
