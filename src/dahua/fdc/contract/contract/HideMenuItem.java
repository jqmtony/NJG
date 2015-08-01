package com.kingdee.eas.fdc.contract;

import java.awt.event.ActionEvent;

import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.ItemAction;

public class HideMenuItem extends KDMenuItem {

	private String keyStroke = null;

	public HideMenuItem(String keyStroke) {
		this.keyStroke = keyStroke;
		this.setAction(new ActionChangeSource());
		this.setVisible(false);
	}

	protected class ActionChangeSource extends ItemAction {
		public ActionChangeSource() {
			this(null);
		}

		public ActionChangeSource(IUIObject uiObject) {
			super(uiObject);
			this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke
					.getKeyStroke(keyStroke));

		}

		public void actionPerformed(ActionEvent e) {
			action_actionPerformed();
		}
	}

	public void action_actionPerformed() {

	}

}
