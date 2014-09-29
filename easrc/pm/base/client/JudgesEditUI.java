/**
 * output package name
 */
package com.kingdee.eas.port.pm.base.client;

import java.awt.event.ActionEvent;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.hraux.DiplomaFactory;
import com.kingdee.eas.basedata.hraux.DiplomaInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.org.PositionMemberInfo;
import com.kingdee.eas.basedata.org.client.f7.AdminF7;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.hr.base.TechnicalPostInfo;
import com.kingdee.eas.hr.emp.PersonContactMethodCollection;
import com.kingdee.eas.hr.emp.PersonContactMethodFactory;
import com.kingdee.eas.hr.emp.PersonContactMethodInfo;
import com.kingdee.eas.hr.emp.PersonTechnicalPostCollection;
import com.kingdee.eas.hr.emp.PersonTechnicalPostFactory;
import com.kingdee.eas.hr.emp.PersonTechnicalPostInfo;
import com.kingdee.eas.hr.emp.client.EmployeeMultiF7PromptBox;
import com.kingdee.eas.port.pm.base.JudgesTreeInfo;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.xr.helper.PersonXRHelper;

/**
 * output class name
 */
public class JudgesEditUI extends AbstractJudgesEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(JudgesEditUI.class);
    
    /**
     * output class constructor
     */
    public JudgesEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    @Override
    public void onLoad() throws Exception {
    	//根据组织长编码过滤人员
    	AdminOrgUnitInfo admiInfo = SysContext.getSysContext().getCurrentAdminUnit();
//    	String sql = "select a.fid from t_bd_person a " +
//    				 " left join T_ORG_PositionMember b " +
//    			     " on b.FPersonID=a.fid and b.FIsPrimary='1'" +
//    			     " left join T_ORG_Position c on c.fid=b.FPositionID" +
//    			     " where c.FAdminOrgUnitID in (select fid from t_org_admin where flongnumber like '" + admiInfo.getLongNumber() + "%')";
//    	FilterInfo filter = new FilterInfo();
//        filter.getFilterItems().add(new FilterItemInfo("id", sql, CompareType.INNER));
		EmployeeMultiF7PromptBox person = new EmployeeMultiF7PromptBox();
		person.setIsSingleSelect(false);
		person.showNoPositionPerson(false);
		person.setIsShowAllAdmin(false);
//		person.setNopositionPersonFilter(filter);
		if(OrgConstants.DEF_CU_ID.equals(admiInfo.getId().toString()))
			person.setIsShowAllAdmin(true);
		this.prmtjuName.setSelector(person);
		
    	prmtjudgeType.setEnabled(false);
    	btnNext.setVisible(false);
    	btnPre.setVisible(false);
    	btnFirst.setVisible(false);
    	btnLast.setVisible(false);
    	super.onLoad();
    	kDLabelContainer2.setVisible(false);
    	kDLabelContainer3.setVisible(false);
    	kDLabelContainer4.setVisible(false);
    	contjuName.setVisible(true);
    	txtpersonName.setVisible(false);
    	chkisUse.setVisible(false);
    	txtNumber.setRequired(true);
    	btnCancel.setEnabled(true);
    	btnCancelCancel.setEnabled(true);
    	
    	if(chkisOuter.isSelected()) {
    		contjuName.setVisible(false);
        	txtpersonName.setVisible(true);
    	}
    	else {
    		contjuName.setVisible(true);
        	txtpersonName.setVisible(false);
    	}
    	
    	AdminF7 f7 = new AdminF7(this);
  		f7.showCheckBoxOfShowingAllOUs();
  		f7.setIsCUFilter(false);
  		f7.setRootUnitID(SysContext.getSysContext().getCurrentAdminUnit().getId().toString());
  		this.prmtcurDep.setSelector(f7);
    }
    
    
	protected void prmtjuName_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtjuName_dataChanged(e);
		PersonInfo person = (PersonInfo)prmtjuName.getValue();
		if(person!=null){
			person = PersonFactory.getRemoteInstance().getPersonInfo(new ObjectUuidPK(person.getId()));
			String personid = person.getId().toString();
			DiplomaInfo diplomaInfo = person.getHighestDegree();
			diplomaInfo = DiplomaFactory.getRemoteInstance().getDiplomaInfo(new ObjectUuidPK(diplomaInfo.getId()));
			sex.setSelectedItem(person.getGender());
			pkbirthday.setValue(person.getBirthday());
			prmteducation.setValue(diplomaInfo);
			txtprofession.setText(getPositionInfo(personid).getName());
			txttechLevel.setText(getPersonTechnicalPostInfo(personid).getName());
			AdminOrgUnitCollection orgColl = PersonXRHelper.getDepartmentByUserCollection(person);
			AdminOrgUnitInfo adminInfo = orgColl.get(0);
			prmtcurDep.setValue(adminInfo);
			PersonContactMethodInfo info = getPersonContactMethodInfo(personid);
			txttelephone.setText(info.getOfficePhone());
			txtmobile.setText(info.getMobile());
		}
	}
	PositionInfo getPositionInfo(String personid){
		PositionMemberInfo info = new PositionMemberInfo();
		PositionInfo position = new PositionInfo();
		try {
			PositionMemberCollection coll = PositionMemberFactory.getRemoteInstance().getPositionMemberCollection("select Position.name where person.id='"+personid+"'");
			info = coll.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(info.getPosition()!=null)
			position = info.getPosition();
		return position;
	}
	
	TechnicalPostInfo getPersonTechnicalPostInfo(String personid){
		PersonTechnicalPostInfo info = new PersonTechnicalPostInfo();
		TechnicalPostInfo technical = new TechnicalPostInfo();
		try {
			PersonTechnicalPostCollection coll = PersonTechnicalPostFactory.getRemoteInstance().getPersonTechnicalPostCollection("select TechnicalPost.name where person.id='"+personid+"'");
			info = coll.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(info.getTechnicalPost()!=null)
			technical = info.getTechnicalPost();
		return technical;
	}
	PersonContactMethodInfo getPersonContactMethodInfo(String personid){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("person.id", personid));
		view.setFilter(filter);
		PersonContactMethodCollection pcmCol = null;
		try {
			pcmCol = PersonContactMethodFactory.getRemoteInstance().getPersonContactMethodCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return pcmCol.get(0);
	}
	protected void chkisOuter_stateChanged(ChangeEvent e) throws Exception {
    	super.chkisOuter_stateChanged(e);
    	if(chkisOuter.isSelected()) {
    		contjuName.setVisible(false);
        	txtpersonName.setVisible(true);
    	}
    	else {
    		contjuName.setVisible(true);
        	txtpersonName.setVisible(false);
    	}
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!chkisUse.isSelected()) {
    		MsgBox.showWarning("未启用基础资料无需禁用");
    		SysUtil.abort();
    	}
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	if(chkisUse.isSelected()) {
    		MsgBox.showWarning("已启用基础资料无需启用");
    		SysUtil.abort();
    	}
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(chkisUse.isSelected()) {
    		MsgBox.showWarning("已启用基础资料无法修改");
    		SysUtil.abort();
    	}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(chkisUse.isSelected()) {
    		MsgBox.showWarning("已启用基础资料无法删除");
    		SysUtil.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.base.JudgesFactory.getRemoteInstance();
    }

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject) 
    {
        super.setDataObject(dataObject);
        if(STATUS_ADDNEW.equals(getOprtState())) {
            editData.put("treeid",(com.kingdee.eas.port.pm.base.JudgesTreeInfo)getUIContext().get(UIContext.PARENTNODE));
        }
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.base.JudgesInfo objectValue = new com.kingdee.eas.port.pm.base.JudgesInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		if(getUIContext().get("treeInfo") != null){
			JudgesTreeInfo info = (JudgesTreeInfo) getUIContext().get("treeInfo");
			objectValue.setJudgeType(info);
		}
        return objectValue;
    }

}