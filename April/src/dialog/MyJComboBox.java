package dialog;

import java.util.IdentityHashMap;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import database.DatabaseConnect;

public class MyJComboBox extends JComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8853257684281539320L;

	MyJComboBox(){
		super();
		DatabaseConnect database = new DatabaseConnect();
		IdentityHashMap result;
		
		result = database.findAllRecord();
		if(result == null){
			JOptionPane.showMessageDialog(null, "Ã»ÓÐ¼ÇÂ¼£¡", "¿Õ", JOptionPane.WARNING_MESSAGE);
			return;
		}
		else{
			Iterator iter = result.keySet().iterator();
			int key;
			while(iter.hasNext()){
				key = (int) iter.next();
				this.addItem(key);
			}
		}
		
	}
	
	
}
