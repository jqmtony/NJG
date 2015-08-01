package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.Rectangle;
import java.awt.event.FocusListener;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.tree.TreeCellEditor;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeCellEditor;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeCellRenderer;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
public class MyTreeCellEditor extends DefaultKingdeeTreeCellEditor{

	public MyTreeCellEditor(JTree tree, DefaultKingdeeTreeCellRenderer renderer) {
		super(tree, renderer);
	}
	
	public void requestFocus() {
		getRealEditor().requestFocus();
	}
	
	public synchronized void addFocusListener(FocusListener focuslistener) {
		getRealEditor().addFocusListener(focuslistener);
	}

	private DefaultTextField getRealEditor() {
		DefaultCellEditor editor=(DefaultCellEditor)realEditor;
		return (DefaultTextField)editor.getComponent();
	}
	
	public boolean stopCellEditing() {
		if (realEditor.stopCellEditing()) {
			return true;
		} else {
			return false;
		}
	}

	public void cancelCellEditing() {
		realEditor.cancelCellEditing();
	}
	
	protected TreeCellEditor createTreeCellEditor() {
		Border aBorder = UIManager.getBorder("Tree.editorBorder");

		DefaultTextField textField = new DefaultTextField(aBorder);
		textField.setBounds(new Rectangle(1, 10, 100, 1000));
		DefaultCellEditor editor = new DefaultCellEditor(textField) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3961767050811504387L;

			public boolean shouldSelectCell(EventObject event) {
				boolean retValue = super.shouldSelectCell(event);
				return retValue;
			}
		};

		// One click to edit.
		//editor.setClickCountToStart(1);
		return editor;
	}
	public Object getCellEditorValue() {
		Object cellEditorValue = super.getCellEditorValue();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) tree.getLastSelectedPathComponent();
		if (null != node && cellEditorValue != null) {
			node.setText(cellEditorValue.toString());
		}

		return cellEditorValue;
	}
}


