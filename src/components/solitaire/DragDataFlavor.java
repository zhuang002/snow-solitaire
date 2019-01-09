package components.solitaire;

import java.awt.datatransfer.DataFlavor;

public class DragDataFlavor extends DataFlavor {
	public static final DragDataFlavor SHARED_INSTANCE = new DragDataFlavor();
	public DragDataFlavor() {
		super(ListedCards.class,null);
	}
}
