/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class BeforeAccountViewListUI extends AbstractBeforeAccountViewListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BeforeAccountViewListUI.class);

	private String isImpedTemplate = null;
	String selectedValue = null;
	/**
	 * output class constructor
	 */
	public BeforeAccountViewListUI() throws Exception {
		super();
	}

	protected String getEditUIName() {
		return BeforeAccountViewEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BeforeAccountViewFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
		// return UIFactoryName.MODEL;
		// return UIFactoryName.NEWWIN;
		// 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		} else {
			return UIFactoryName.MODEL;
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		//FullOrgUnitCollection CU = new CompanyOrgSwitchUI().getUserOrgRangeOUsByOrgType(OrgTypeForOrgSwitchingEnum.CU);
		
		//当前是集团组织   by pu_zhang  2010-5-26 
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
			this.menuImpTemplate.setEnabled(false);
			actionImpTemplate.setEnabled(false);
			// this.menuItemCancel.setv(true)
			
		//管理单元可以编辑   
		//财务组织也可以新增，编辑,和管理单元一样 by zhiyuan_tang 2010/11/25
		} else if(SysContext.getSysContext().getCurrentOrgUnit().isIsCU() || SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()){
			
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
			this.menuImpTemplate.setEnabled(true);
			actionImpTemplate.setEnabled(true);
			
			this.btnEdit.setEnabled(true);
			isImpedTemplate = "true";
			
		//如果当前组织是非财务组织
		}else{
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.menuImpTemplate.setEnabled(false);
			actionImpTemplate.setEnabled(false);
		}
		
		
		menuImpTemplate.setText("引入模版");
	}

	protected String[] getLocateNames() {
		String[] locateNames = new String[1];
		locateNames[0] = "company.name";
		return locateNames;
	}
	
	public void actionImpTemplate_actionPerformed(ActionEvent e) throws Exception {
		FilterInfo filterExist = new FilterInfo();
		String company = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		filterExist.getFilterItems().add(
				new FilterItemInfo("company.id", company));
		if(getBizInterface().exists(filterExist)){
			String msg = "该公司已存在财务成本一体化科目设置，导入会覆盖，请确认是否覆盖？";
			int v = MsgBox.showConfirm2New(this, msg);
			if (v == MsgBox.YES) {
				BeforeAccountViewFactory.getRemoteInstance().impTemplate(company);
				refresh(e);
			}
		}else{
			BeforeAccountViewFactory.getRemoteInstance().impTemplate(company);
			refresh(e);
		}
		this.btnEdit.setEnabled(true);
	}
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
 		checkSelected();
 		selectedValue = getSelectedKeyValue();
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		String ctlUnitID = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select fid from T_FDC_BeforeAccountView where fcontrolunitid=?");
		sqlBuilder.addParam(ctlUnitID);
		try {
			IRowSet rowSet = sqlBuilder.executeQuery();
			if(rowSet.size()!=0){
				uiContext.put("isFirstAddNew", "true");
			}
		} catch (BOSException e1) {
			handUIExceptionAndAbort(e1);
		}
		uiContext.put("isImpedTemplate",isImpedTemplate);
		uiContext.put("selectedValue", selectedValue);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		//如果选择的不是当前公司或者组织的数据，就不能进入编辑
		String company = "";
		if(getSelectedKeyValue()!=null){
			selectedValue = getSelectedKeyValue();
		}
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select fcompanyid from T_FDC_BeforeAccountView where fid=?");
		sqlBuilder.addParam(selectedValue);
		IRowSet rowSet = sqlBuilder.executeQuery();
		if(rowSet.next()){
			 company = rowSet.getString("FCOMPANYID");
		}
		String orgUnitID = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		if(!company.equals(orgUnitID)){
			MsgBox.showWarning(this, "不允许修改其他组织的数据!");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}
	

	//该公司已存在财务成本一体化科目设置，请直接修改。 added by pu_zhang   2010-5-31
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
//		FilterInfo filterExist = new FilterInfo();
//		String company = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
//		filterExist.getFilterItems().add(
//				new FilterItemInfo("company.id", company));
//		if(getBizInterface().exists(filterExist)){
//			
//		}
		
		FilterInfo  filterExist = new FilterInfo();
		String company  = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		filterExist.getFilterItems().add(new FilterItemInfo("company.id",company));
		if(getBizInterface().exists(filterExist)){
			MsgBox.showWarning(this, "该公司已存在财务成本一体化科目设置，请直接修改。");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}
	
	
	
	 /* 
     * 实体没有继承基础资料的实体，导致界面取不到控制类型,将控制类型改成S1类基础资料
     * @see com.kingdee.eas.basedata.framework.client.DataBaseSIListUI#getControlType()
     */
    protected String getControlType() {
    	return FDCBaseDataClientCtrler.CONTROLTYPE_S1;
    }
    
    /**
     * 删除科目设置  added by pu_zhang  2010.5.25
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	String company="";
    	String orgUnitID =SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
    	if(getSelectedKeyValue()!=null){
    		selectedValue = getSelectedKeyValue();
    	}
    	FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
    	sqlBuilder.appendSql("select fcompanyid from T_FDC_BeforeAccountView where fid=?");
    	sqlBuilder.addParam(selectedValue);
    	IRowSet rowSet = sqlBuilder.executeQuery();
    	if(rowSet.next()){
    		company = rowSet.getString("FCOMPANYID");
    	}
    	if(!company.equalsIgnoreCase(orgUnitID)){
    		MsgBox.showWarning(this, "不允许删除其他组织的数据!");
			SysUtil.abort();
    	}else{
    		super.actionRemove_actionPerformed(e);
    	}
    }
   
    

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：是否支持EAS高级统计(EAS800新增的功能)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}