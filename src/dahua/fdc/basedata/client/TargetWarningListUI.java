/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.TargetWarningFactory;
import com.kingdee.eas.fdc.basedata.TargetWarningInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TargetWarningListUI extends AbstractTargetWarningListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TargetWarningListUI.class);
    
    /**
     * output class constructor
     */
    public TargetWarningListUI() throws Exception
    {
        super();
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new TargetWarningInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TargetWarningFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return TargetWarningEditUI.class.getName();
	}
	
	/**
	 * 覆写以使一个启用时其余被禁用
	 */
	protected void setIsEnabled(boolean flag) throws Exception {
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		// 不允许操作系统预设数据
		if (!flag && !FDCBaseDataEditUI.canCancel)
			if (isSystemDefaultData(activeRowIndex)) {
				MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
				return;
			}
		String id = tblMain.getRow(activeRowIndex).getCell("id").getValue().toString().trim();
		FDCDataBaseInfo info = getBaseDataInfo();
		info.setId(BOSUuid.read(id));
		info.setIsEnabled(flag);
		//便于启用、禁用时得到编码及名称
		String number = tblMain.getRow(activeRowIndex).getCell("number").getValue().toString().trim();
		String name = tblMain.getRow(activeRowIndex).getCell("name").getValue().toString().trim();
		info.setNumber(number);
		info.setName(name);
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			TargetWarningFactory.getRemoteInstance().enable(new ObjectUuidPK(id));
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		}
		// ContractCostPropertyFactory.getRemoteInstance().enabled(new
		// ObjectUuidPK(id), info);
		else {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		// ContractCostPropertyFactory.getRemoteInstance().disEnabled(new
		// ObjectUuidPK(id), info);

		setMessageText(message);
		showMessage();
		tblMain.refresh();
		loadFields();
		actionCancel.setEnabled(false);
		actionCancelCancel.setEnabled(false);
	}
}