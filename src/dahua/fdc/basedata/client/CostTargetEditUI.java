/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.CostTargetFactory;
import com.kingdee.eas.fdc.basedata.CostTargetInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.IChangeType;
import com.kingdee.eas.fdc.basedata.ICostTarget;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/** 
 * 描述:造价指标编辑
 * @author jackwang  date:2006-11-13 <p>
 * @version EAS5.2
 */
public class CostTargetEditUI extends AbstractCostTargetEditUI {
	private static final Logger logger = CoreUIObject.getLogger(CostTargetEditUI.class);

	/**
	 * output class constructor
	 */
	public CostTargetEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.COSTTARGET));
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("CU.id"));
		return sic;
	}

	protected IObjectValue createNewData() {
		CostTargetInfo costTargetInfo = new CostTargetInfo();
		costTargetInfo.setIsEnabled(isEnabled);
		return costTargetInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CostTargetFactory.getRemoteInstance();
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return bizName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		//启用禁用
		this.actionCancelCancel.setVisible(false);
		super.actionRemove_actionPerformed(e);
		
	}
}