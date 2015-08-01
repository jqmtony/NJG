package com.kingdee.eas.fdc.basedata.client;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 责任部门数据改变Lisenter，用于校验只能输入最明细节点
 * @author liupd
 *
 */
public class RespDeptDataChangeLisenter implements DataChangeListener {

	private CoreUIObject ui;

	private boolean isMustSelectLeaf;

	public RespDeptDataChangeLisenter(CoreUIObject ui) {
		super();
		this.setUi(ui);
	}

	public RespDeptDataChangeLisenter(CoreUIObject ui, boolean isMustSelectLeaf) {
		super();
		this.setUi(ui);
		this.isMustSelectLeaf = isMustSelectLeaf;
	}

	public void dataChanged(DataChangeEvent eventObj) {
		KDBizPromptBox box = (KDBizPromptBox) eventObj.getSource();
        if (box.getData() instanceof TreeBaseInfo) {
			TreeBaseInfo info = (TreeBaseInfo) box.getData();        	
        	if(info != null) {
        		FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.FALSE));
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString()));
        		try {
        			if (isMustSelectLeaf && AdminOrgUnitFactory.getRemoteInstance().exists(filter)) { // 非叶子结点
        				filter = new FilterInfo(); // 清空过滤条件
						filter.getFilterItems().add(new FilterItemInfo("parent.id", info.getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("isSealUp", Boolean.FALSE));
						// 只要有一个未封存，非叶子节点就不可选。如果所有子组织都被封存，这个组织还是可以选的
						if (AdminOrgUnitFactory.getRemoteInstance().exists(filter)) {
							box.setUserObject(null);
							box.setText(null);
							// 责任部门 请选择最明细的记录
							MsgBox.showWarning(getUi(), EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource",
									"resptDept")
									+ FDCClientUtils.getRes("selectLeaf"));
						}
					}					
				} catch (Exception e) {
					// @AbortException
					ExceptionHandler.handle(e);
				}
        	}
        }
	}


	private void setUi(CoreUIObject ui) {
		this.ui = ui;
	}


	private CoreUIObject getUi() {
		return ui;
	}

}
