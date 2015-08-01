/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class MeasureStageEditUI extends AbstractMeasureStageEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasureStageEditUI.class);
    
    /**
     * output class constructor
     */
    public MeasureStageEditUI() throws Exception
    {
        super();
    }      
    
    
    /* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI#onLoad()
	 */
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		setTitle();
		
		//非集团不能操作
		if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.btnCancel.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
		
	}

    /**
     * 判断 阶段顺序 为数字
     */
	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI#verifyInput(java.awt.event.ActionEvent)
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		//清除空格并设置使保存 by hpw
		this.txtNumber.setText(FDCBaseDataClientUtils.clearEmpty(txtNumber.getText()));
		this.editData.setNumber(txtNumber.getText());
		this.bizName.setDefaultLangItemData(FDCBaseDataClientUtils.clearEmpty(this.bizName.getDefaultLangItemData().toString()));
		if(bizName.getDefaultLangItemData()!=null){
			this.editData.setName(bizName.getDefaultLangItemData().toString());
		}
		if (txtNumber.getText() != null || txtNumber.getText().trim().length() > 0)
		{
			String tempNumber = txtNumber.getText().trim();
			boolean IsDigit = true;
			for (int i=0;i<tempNumber.length();i++)
			{
				char tempChar = tempNumber.charAt(i);
				if(!Character.isDigit(tempChar))
				{
					IsDigit = false;
					break;
				}			
			}
			if (!IsDigit)  //不是数字则报异常
			{				
				txtNumber.requestFocus(true);
				throw new FDCBasedataException(FDCBasedataException.NUMBER_MUST_DIGIT);
			}
		}	
		
		super.verifyInput(e);
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.MEASURESTAGE));
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

	protected IObjectValue createNewData() {
		MeasureStageInfo measureStageInfo = new MeasureStageInfo();
		measureStageInfo.setIsEnabled(isEnabled);
		return measureStageInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MeasureStageFactory.getRemoteInstance();
	}
	public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = super.getSelectors();
        sic.add(new SelectorItemInfo("*"));
        return sic;
    }
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionRemove_actionPerformed(e);
	}
	protected void setIsEnable(boolean flag) throws Exception {
		//TODO 后续将数据操作方法移到服务端
		super.setIsEnable(flag);
		//记录日志用
		if(flag){
			MeasureStageFactory.getRemoteInstance().enabled(null);
		}else{
			MeasureStageFactory.getRemoteInstance().disEnabled(null);
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEdit();
		super.actionEdit_actionPerformed(e);
	}
	private void checkBeforeEdit() throws Exception{
		if(editData!=null&&editData.getId()!=null){
			List idList = new ArrayList();
			idList.add(editData.getId().toString());
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("measureStage.id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
			if(AimCostFactory.getRemoteInstance().exists(filter) || MeasureCostFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this, "测算阶段被目标成本测算或目标成本引用，不能进行修改操作!");
				this.abort();
			}
		}
	}
	private void checkBeforeDisEnabled() throws Exception{
		if(editData!=null&&editData.getId()!=null){
			List idList = new ArrayList();
			idList.add(editData.getId().toString());
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("measureStage.id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
			if(AimCostFactory.getRemoteInstance().exists(filter) || MeasureCostFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this, "测算阶段被目标成本测算或目标成本引用，不能禁用!");
				this.abort();
			}
		}
	}
}