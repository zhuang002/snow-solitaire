package components.solitaire;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class DataTransferable implements Transferable {

	private DataFlavor[] flavors = new DataFlavor[]{DragDataFlavor.SHARED_INSTANCE};
	CardStack dragStack;
	
	public DataTransferable(CardStack stack) {
		this.dragStack=stack;
	}
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		// TODO Auto-generated method stub
		return this.flavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		// TODO Auto-generated method stub
		boolean supported = false;

        for (DataFlavor mine : getTransferDataFlavors()) {

            if (mine.equals(flavor)) {

                supported = true;
                break;

            }

        }

        return supported;
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		// TODO Auto-generated method stub
		Object data = null;
        if (isDataFlavorSupported(flavor)) {

            data = this.dragStack;

        } else {

            throw new UnsupportedFlavorException(flavor);

        }

        return data;
	}

}
