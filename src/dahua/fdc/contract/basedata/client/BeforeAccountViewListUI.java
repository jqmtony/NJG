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
		// 2006-4-29 ����Ҫ������������������ȡUI�򿪷�ʽ��
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
		
		//��ǰ�Ǽ�����֯   by pu_zhang  2010-5-26 
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
			
		//����Ԫ���Ա༭   
		//������֯Ҳ�����������༭,�͹���Ԫһ�� by zhiyuan_tang 2010/11/25
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
			
		//�����ǰ��֯�Ƿǲ�����֯
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
		
		
		menuImpTemplate.setText("����ģ��");
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
			String msg = "�ù�˾�Ѵ��ڲ���ɱ�һ�廯��Ŀ���ã�����Ḳ�ǣ���ȷ���Ƿ񸲸ǣ�";
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
		//���ѡ��Ĳ��ǵ�ǰ��˾������֯�����ݣ��Ͳ��ܽ���༭
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
			MsgBox.showWarning(this, "�������޸�������֯������!");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}
	

	//�ù�˾�Ѵ��ڲ���ɱ�һ�廯��Ŀ���ã���ֱ���޸ġ� added by pu_zhang   2010-5-31
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
			MsgBox.showWarning(this, "�ù�˾�Ѵ��ڲ���ɱ�һ�廯��Ŀ���ã���ֱ���޸ġ�");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}
	
	
	
	 /* 
     * ʵ��û�м̳л������ϵ�ʵ�壬���½���ȡ������������,���������͸ĳ�S1���������
     * @see com.kingdee.eas.basedata.framework.client.DataBaseSIListUI#getControlType()
     */
    protected String getControlType() {
    	return FDCBaseDataClientCtrler.CONTROLTYPE_S1;
    }
    
    /**
     * ɾ����Ŀ����  added by pu_zhang  2010.5.25
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
    		MsgBox.showWarning(this, "������ɾ��������֯������!");
			SysUtil.abort();
    	}else{
    		super.actionRemove_actionPerformed(e);
    	}
    }
   
    

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * �������Ƿ�֧��EAS�߼�ͳ��(EAS800�����Ĺ���)
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