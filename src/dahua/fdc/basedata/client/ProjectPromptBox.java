package com.kingdee.eas.fdc.basedata.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import common.Logger;
/**
 * 描述:房地产项目F7
 * @author jackwang  date:2007-6-7 <p>
 * @version EAS5.3
 */
public class ProjectPromptBox extends KDCommonPromptDialog {

	protected IUIWindow classDlg = null;

	protected IUIObject owner;

	protected boolean isOnlyLeaf = false;// 是否只选叶结点

	protected boolean isMultiSelect = false;// 是否多选

	protected boolean isOrgFilter = false;// 是否组织隔离

	protected SelectorItemCollection userSeletor = null;
	
	private static Logger logger = Logger.getLogger(ProjectPromptBox.class);

	public ProjectPromptBox() {
	}

	public ProjectPromptBox(IUIObject owner) {
		this.owner = owner;
	}

	public ProjectPromptBox(IUIObject owner, boolean isOnlyLeaf, boolean isMultiSelect, boolean isOrgFilter, SelectorItemCollection userSelector) {
		this.owner = owner;
		this.isOnlyLeaf = isOnlyLeaf;
		this.isMultiSelect = isMultiSelect;
		this.isOrgFilter = isOrgFilter;
		this.userSeletor = userSelector;
	}

	public String getUITitle() {
		String resFullName = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource.Project";
		String returnValue = EASResource.getString(resFullName);
		return returnValue;
	}

	public void show() {
		Map map = new HashMap();
//		map.put("Owner", ComponentUtil.getRootComponent((Component) owner));
//		map.put("isF7", Boolean.valueOf(true));
//		map.put("isOrgFilter", new Boolean(this.isOrgFilter));
//		map.put("isOnlyLeaf", new Boolean(this.isOnlyLeaf));
//		map.put("userSelector", this.userSeletor);
//		map.put("isMultiSelect", new Boolean(this.isMultiSelect));
		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			classDlg = uiFactory.create("com.kingdee.eas.fdc.basedata.client.ProjectPromptUI", map);
			classDlg.show();
		} catch (BOSException ex) {
			// @AbortException
			ExceptionHandler.handle(this, ex);
			return;
		}
	}

	public boolean isCanceled() {
		if (classDlg != null) {
			return ((ProjectPromptUI) classDlg.getUIObject()).isCanceled;
			// return false;
		} else {
			return true;
		}
		// return true;
	}

	public Object getData() {
		ProjectPromptUI ui = (ProjectPromptUI) classDlg.getUIObject();
		CurProjectInfo returnInfo = null;
		int len = 1;
		try {
			returnInfo = ui.getReturnValues();
		} catch (Exception err) {
			// @AbortException
			logger.error(err.getMessage(), err);
			return null;
		}
		Object[] returnValue = new Object[len];
		returnValue[0] = returnInfo;
		return returnValue;
	}
}
