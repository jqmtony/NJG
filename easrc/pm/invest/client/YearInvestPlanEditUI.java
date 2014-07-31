/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.event.AncestorEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.core.fm.ClientVerifyHelper;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.port.pm.base.BuildTypeInfo;
import com.kingdee.eas.port.pm.base.InvestYearFactory;
import com.kingdee.eas.port.pm.base.InvestYearInfo;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YearInvestPlanCollection;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.port.pm.invest.investplan.IProgramming;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingCollection;
import com.kingdee.eas.port.pm.invest.investplan.client.ProgrammingEditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.helper.PersonXRHelper;

/**
 * output class name
 */
public class YearInvestPlanEditUI extends AbstractYearInvestPlanEditUI {
	private static final Logger logger = CoreUIObject.getLogger(YearInvestPlanEditUI.class);

	/**
	 * output class constructor
	 */
	public YearInvestPlanEditUI() throws Exception {
		super();

	}
	ProgrammingEditUI programmingEditUI = null;
	public void onLoad() throws Exception {
		super.onLoad();
		actionRemoveLine.setVisible(false);
		actionAddLine.setVisible(false);
		actionInsertLine.setVisible(false);
		actionCopy.setVisible(false);
		contCU.setVisible(true);
		prmtCU.setVisible(true);
		contrequestOrg.setEnabled(false);
		kdtEntry.getColumn("seq").getStyleAttributes().setHided(true);
		kdtE3.setEditable(false);
		kdtE3_detailPanel.getAddNewLineButton().setVisible(false);
		kdtE3_detailPanel.getInsertLineButton().setVisible(false);
		kdtE3_detailPanel.getRemoveLinesButton().setVisible(false);
		this.txtBIMUDF0027.setMaxLength(2000);
		this.txtanalyse.setMaxLength(2000);
		this.txtscheme.setMaxLength(2000);
		this.kDContainer1.removeAll();
		kDContainer1.setVisible(false);
		this.txtinvestAmount.setEnabled(false);
		this.continvestAmount.setEnabled(false);
		this.txtamount.setEnabled(false);
		this.txtbalance.setEnabled(false);
		contcostTemp.setVisible(false);
		prmtcostTemp.setVisible(false);
		prmtcostTemp.setEnabled(false);
		if (prmtbuildType.getValue() != null) 
		{
			BuildTypeInfo bdtinfo = (BuildTypeInfo) prmtbuildType.getValue();
			if (bdtinfo.getNumber().equals("002")) 
			{
				prmtportProject.setVisible(true);
				contportProject.setVisible(true);
			} 
			else 
			{
				prmtportProject.setVisible(false);
				prmtportProject.setValue(null);
				contportProject.setVisible(false);
				prmtyear.setEnabled(true);
				contyear.setEnabled(true);
				txtaddInvestAmount.setValue(null);
			}
		} 
		else 
		{
			prmtportProject.setVisible(false);
			prmtportProject.setValue(null);
			contportProject.setVisible(false);
		}

		if (getOprtState().equals(OprtState.ADDNEW))
		{
			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
			if (user.getPerson() != null) 
			{//申请人带出申请单位
				PersonInfo person = user.getPerson();
				prmtrequestPerson.setValue(person);
				AdminOrgUnitCollection orgColl = PersonXRHelper.getDepartmentByUserCollection(person);
				AdminOrgUnitInfo adminInfo = orgColl.get(0);
				prmtrequestOrg.setValue(adminInfo);
				prmtCU.setValue(SysContext.getSysContext().getCurrentCtrlUnit());
			}
		}
		else if (getOprtState().equals(OprtState.VIEW))
		{
		} 
		else if (getOprtState().equals(OprtState.EDIT)) 
		{
		}
		this.kDContainer1.getContentPane().setVisible(false);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("NJGprojectType.name","基本建设",com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		this.prmtportProject.setEntityViewInfo(view);
	}
	
	public void actionInvestPlan_actionPerformed(ActionEvent e)throws Exception {
	}
	/**
	 * 申报人带出组织
	 */
	protected void prmtrequestPerson_dataChanged(DataChangeEvent e)throws Exception {
		if (prmtrequestPerson.getValue() != null)
		{
			PersonInfo prinfo = (PersonInfo) prmtrequestPerson.getValue();
			AdminOrgUnitCollection orgColl = PersonXRHelper.getDepartmentByUserCollection(prinfo);
			AdminOrgUnitInfo adminInfo = orgColl.get(0);
			prmtrequestOrg.setValue(adminInfo);
		}
	}
	/**
	 * 费用模板直接带出费用类型，费用名称
	 */
	protected void prmtcostTemp_dataChanged(DataChangeEvent e) throws Exception {
	}
	/**
	 * 建设性质为续建项目时显示出项目信息字段
	 */
	protected void prmtbuildType_dataChanged(DataChangeEvent e)throws Exception {
		super.prmtbuildType_dataChanged(e);
		if (prmtbuildType.getValue() != null) 
		{
			BuildTypeInfo bdtinfo = (BuildTypeInfo) prmtbuildType.getValue();
			if (bdtinfo.getNumber().equals("002")) 
			{
				prmtportProject.setVisible(true);
				contportProject.setVisible(true);
			} 
			else 
			{
				prmtportProject.setVisible(false);
				prmtportProject.setValue(null);
				contportProject.setVisible(false);
				prmtyear.setEnabled(true);
				contyear.setEnabled(true);
				txtaddInvestAmount.setValue(null);
			}
		} 
		else 
		{
			prmtportProject.setVisible(false);
			prmtportProject.setValue(null);
			contportProject.setVisible(false);
		}
	}
	/**
	 * EditUI界面点击复制并新增时，单据状态变新增
	 */
	protected void setFieldsNull(AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("status",null);
		arg0.put("objectState",ObjectStateEnum.save);
	}
	protected void prmtyear_dataChanged(DataChangeEvent e)throws Exception{
		super.prmtyear_dataChanged(e);
	}

	protected void objectState_itemStateChanged(ItemEvent e) throws Exception {
		super.objectState_itemStateChanged(e);
	}
	
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntry_editStopped(e);
	}
	/**
	 * 校验信息
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		BigDecimal a = UIRuleUtil.getBigDecimal((this.txtinvestAmount.getValue()));
		BigDecimal c = UIRuleUtil.getBigDecimal((this.txtamount.getValue()));
		if(a.compareTo(c)<0)
		{
			MsgBox.showWarning("项目投资总金额不能小于本年计划投资金额");abort();
		}
    	ClientVerifyHelper.verifyEmpty(this, this.pkplanStartDate);
    	ClientVerifyHelper.verifyEmpty(this, this.pkplanEndDate);
    	ClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
    	ClientVerifyHelper.verifyEmpty(this, this.prmtyear);
    	
    	SimpleDateFormat Formatter = new SimpleDateFormat("yyyy-MM-dd");
    	if(!Formatter.format(this.pkplanStartDate.getSqlDate()).equals(Formatter.format(this.pkBizDate.getSqlDate())))
    	{	
    		if(this.pkplanStartDate.getSqlDate().before(this.pkBizDate.getSqlDate()))
    		{
    	    	MsgBox.showWarning("计划开工日期不能早于计划申报日期！");abort();
    	    }
    	}	
    	if(!Formatter.format(this.pkplanStartDate.getSqlDate()).equals(Formatter.format(this.pkplanEndDate.getSqlDate())))
    	{	
    		if(this.pkplanEndDate.getSqlDate().before(this.pkplanStartDate.getSqlDate())){
    	    	MsgBox.showWarning("计划完工日期不能早于计划开工日期！");abort();
    	    }
    	}	
		super.verifyInput(e);
	}

	/**
	 * 续建时，项目信息选择已立项项目
	 */
	protected void prmtportProject_dataChanged(DataChangeEvent e)throws Exception {
		super.prmtportProject_dataChanged(e);
		if(!BizCollUtil.isF7ValueChanged(e)||e.getNewValue()==null){
			return;
		}
		verifySave(null);
		setPortProject(null);
	}
	
	/**
	 * 点击投资规划页签加载投资规划
	 */
	protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
		super.kDTabbedPane1_stateChanged(e);
		if(e!=null&&e.getSource()!=null){
			verifySave(e);
			setPortProject(e);
		}
		
	}
	
	
	private void verifySave(ChangeEvent e) throws EASBizException, BOSException{
		if(editData!=null)
		{
			boolean eidtBill = YearInvestPlanFactory.getRemoteInstance().exists("select id where id ='"+editData.getId()+"'");
			if(e!=null&&e.getSource()!=null)
			{
				KDTabbedPane kdtable = (KDTabbedPane)e.getSource();
				if(!kdtable.getSelectedComponent().getName().equals("kDScrollPane1"))
					return;
			}
			if(!eidtBill)
			{
				if(e==null)
				{
					MsgBox.showWarning("单据未保存，请先保存！");
					this.prmtportProject.setValue(null);
					SysUtil.abort();
				}
				else 
				{
					KDTabbedPane kdtable = (KDTabbedPane)e.getSource();
					if(kdtable.getSelectedComponent().getName().equals("kDScrollPane1"))
					{
						MsgBox.showWarning("单据未保存，请先保存！");
						kDTabbedPane1.setSelectedComponent(kDPanel1);
						SysUtil.abort();
					}
					if(!kdtable.getSelectedComponent().getName().equals("kDScrollPane1"))
						return;
				}
			}
		}
		
	}
	
	/**
	 * 项目信息及投资规划界面加载封装
	 */
	private void setPortProject(ChangeEvent e) throws Exception
	{
		if(editData!=null){
			kDScrollPane1.setViewportView(null);
			String number = this.txtNumber.getText();
			UIContext uiContext = new UIContext(this);
			uiContext.put("UIName", "YearInvestPlanEditUI");
			uiContext.put("proAmount", this.txtamount);
			uiContext.put("proInvestAmount", this.txtinvestAmount);
			uiContext.put("proAddAmount", this.txtaddInvestAmount);
			uiContext.put("proBalance", this.txtbalance);
			uiContext.put("proNumber",number);
			uiContext.put("proname", this.txtprojectName.getText());
			uiContext.put("modify", this.txtprojectName.getText());
			//新建项目
			if(prmtportProject.getValue()==null){
				//加载投资规划编辑界面
		    	String oql = "where SourceBillId='"+editData.getId()+"'";
		    	boolean flse = true;
		    	if(ProgrammingFactory.getRemoteInstance().exists(oql))
		    	{
		    		uiContext.put("ID", ProgrammingFactory.getRemoteInstance().getProgrammingCollection(oql).get(0).getId());
		    		flse = false;
		    	}
		    	else
		    	{
		    		uiContext.put("yearPlanId",editData.getId().toString());
		    	}
		        programmingEditUI = (ProgrammingEditUI) UIFactoryHelper.initUIObject(ProgrammingEditUI.class.getName(), uiContext, null, flse?"ADDNEW":getOprtState());
		        kDScrollPane1.setViewportView(programmingEditUI);
		        kDScrollPane1.setKeyBoardControl(true);
		        kDScrollPane1.setEnabled(false);
			}
			//续建项目
			else
			{
				verifyInput(null);
				IProgramming iProgramming = ProgrammingFactory.getRemoteInstance();
				ProjectInfo projectInfo = (ProjectInfo)prmtportProject.getValue();
				YearInvestPlanInfo yearInvestPlanInfo = (YearInvestPlanInfo)projectInfo.getNJGyearInvest();
				yearInvestPlanInfo = YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanInfo(new ObjectUuidPK(yearInvestPlanInfo.getId()));
		        String oql = "select number,year.id,year.number,year.name where (number='"+yearInvestPlanInfo.getNumber()+"' or portProject.number='"+
		        yearInvestPlanInfo.getNumber()+"')  and objectState='8'" ;
		        if(editData.getId()!=null)
		        	oql = oql + "and id<>'"+editData.getId()+"'";
		        oql = oql +" order by year.number desc ";
				YearInvestPlanCollection coll = YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanCollection(oql);
				YearInvestPlanInfo pro = coll.get(0);
				InvestYearInfo  newYearInfo = InvestYearFactory.getRemoteInstance().getInvestYearInfo("where name ='"+String.valueOf(Integer.parseInt(pro.getYear().getName())+1)+"'");
				if(!iProgramming.exists("where sourceBillId='"+pro.getId()+"'"))
				{
					MsgBox.showWarning("没有对应的项目!");
					this.prmtyear.setValue(null);
					kDScrollPane1.setViewportView(null);
					SysUtil.abort();
				}
				ProgrammingInfo pmInfo = iProgramming.getProgrammingCollection("where sourceBillId='"+pro.getId()+"'").get(0);
				uiContext.put("UIName", "YearInvestPlanEditUI");
		    	uiContext.put("ID", pmInfo.getId());
		    	uiContext.put("year",newYearInfo);
		    	uiContext.put("yearPlanId",editData.getId().toString());
		    	uiContext.put("proNumber",number);
				uiContext.put("proname", this.txtprojectName.getText());
		    	programmingEditUI = (ProgrammingEditUI) UIFactoryHelper.initUIObject(ProgrammingEditUI.class.getName(), uiContext, null, "EDIT");
		    	kDScrollPane1.setViewportView(programmingEditUI);
		    	kDScrollPane1.setKeyBoardControl(true);
		    	kDScrollPane1.setEnabled(false);
		        programmingEditUI.actionCopy_actionPerformed(null);
			}
			
		}
			
	}
	
	/**
	 * output loadFields method
	 */
	public void loadFields() {
		detachListeners();
		super.loadFields();
		attachListeners();
		this.getUITitle();
		if(programmingEditUI!=null)
			programmingEditUI.loadFields();
	}
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnAddLine_actionPerformed method
	 */
	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnAddLine_actionPerformed(e);
	}

	/**
	 * output menuItemEnterToNextRow_itemStateChanged method
	 */
	protected void menuItemEnterToNextRow_itemStateChanged(
			java.awt.event.ItemEvent e) throws Exception {
		super.menuItemEnterToNextRow_itemStateChanged(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionExportSave_actionPerformed
	 */
	public void actionExportSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSave_actionPerformed(e);
	}

	/**
	 * output actionExportSelectedSave_actionPerformed
	 */
	public void actionExportSelectedSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelectedSave_actionPerformed(e);
	}

	/**
	 * output actionKnowStore_actionPerformed
	 */
	public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception {
		super.actionKnowStore_actionPerformed(e);
	}

	/**
	 * output actionAnswer_actionPerformed
	 */
	public void actionAnswer_actionPerformed(ActionEvent e) throws Exception {
		super.actionAnswer_actionPerformed(e);
	}

	/**
	 * output actionRemoteAssist_actionPerformed
	 */
	public void actionRemoteAssist_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoteAssist_actionPerformed(e);
	}

	/**
	 * output actionPopupCopy_actionPerformed
	 */
	public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupCopy_actionPerformed(e);
	}

	/**
	 * output actionHTMLForMail_actionPerformed
	 */
	public void actionHTMLForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForMail_actionPerformed(e);
	}

	/**
	 * output actionExcelForMail_actionPerformed
	 */
	public void actionExcelForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForMail_actionPerformed(e);
	}

	/**
	 * output actionHTMLForRpt_actionPerformed
	 */
	public void actionHTMLForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForRpt_actionPerformed(e);
	}

	/**
	 * output actionExcelForRpt_actionPerformed
	 */
	public void actionExcelForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForRpt_actionPerformed(e);
	}

	/**
	 * output actionLinkForRpt_actionPerformed
	 */
	public void actionLinkForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionLinkForRpt_actionPerformed(e);
	}

	/**
	 * output actionPopupPaste_actionPerformed
	 */
	public void actionPopupPaste_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPopupPaste_actionPerformed(e);
	}

	/**
	 * output actionToolBarCustom_actionPerformed
	 */
	public void actionToolBarCustom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionToolBarCustom_actionPerformed(e);
	}

	/**
	 * output actionCloudFeed_actionPerformed
	 */
	public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception {
		super.actionCloudFeed_actionPerformed(e);
	}

	/**
	 * output actionCloudShare_actionPerformed
	 */
	public void actionCloudShare_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCloudShare_actionPerformed(e);
	}

	/**
	 * output actionCloudScreen_actionPerformed
	 */
	public void actionCloudScreen_actionPerformed(ActionEvent e)throws Exception {
		super.actionCloudScreen_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		setPortProject(null);
		if(programmingEditUI!=null)
			programmingEditUI.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		setPortProject(null);
		if(programmingEditUI!=null)
			programmingEditUI.actionSubmit_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		super.actionNext_actionPerformed(e);
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception {
		super.actionReset_actionPerformed(e);
	}

	/**
	 * output actionMsgFormat_actionPerformed
	 */
	public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception {
		super.actionMsgFormat_actionPerformed(e);
	}

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
	}

	/**
	 * output actionCopyLine_actionPerformed
	 */
	public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyLine_actionPerformed(e);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCreateFrom_actionPerformed(e);
	}

	/**
	 * output actionCopyFrom_actionPerformed
	 */
	public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyFrom_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionViewSubmitProccess_actionPerformed
	 */
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSubmitProccess_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionSendingMessage_actionPerformed
	 */
	public void actionSendingMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendingMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actionViewSignature_actionPerformed
	 */
	public void actionViewSignature_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSignature_actionPerformed(e);
	}

	/**
	 * output actionSendMail_actionPerformed
	 */
	public void actionSendMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMail_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionNumberSign_actionPerformed
	 */
	public void actionNumberSign_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNumberSign_actionPerformed(e);
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.port.pm.invest.YearInvestPlanFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return null;
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.port.pm.invest.YearInvestPlanInfo objectValue = new com.kingdee.eas.port.pm.invest.YearInvestPlanInfo();
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		objectValue.setObjectState(ObjectStateEnum.save);
		objectValue.setBizDate(new Date());
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		return objectValue;
	}

	protected void attachListeners() {
		addDataChangeListener(this.prmtcostTemp);
		addDataChangeListener(this.prmtbuildType);
		addDataChangeListener(this.prmtportProject);
	}

	protected void detachListeners() {
		removeDataChangeListener(this.prmtcostTemp);
		removeDataChangeListener(this.prmtbuildType);
		removeDataChangeListener(this.prmtportProject);
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public static void main(String[] args) {
	}
	
	/**
	 * 设置保存时焦点校对
	 */
	protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) {
			txtNumber.requestFocus(true);
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"单据编号"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtbuildType.getData())) {
			prmtbuildType.requestFocus(true);
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"建设性质"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtprojectName.getText())) {
			txtprojectName.requestFocus(true);
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"项目名称"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtfundSource.getData())) {
			prmtfundSource.requestFocus(true);
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"资金来源"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtrequestPerson.getData())) {
			prmtrequestPerson.requestFocus(true);
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"申报人"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtrequestOrg.getData())) {
			prmtrequestOrg.requestFocus(true);
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"申报部门"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtprojectType.getData())) {
			prmtprojectType.requestFocus(true);
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"项目类型"});
		}
//		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtamount.getValue())) {
//			txtamount.requestFocus(true);
//			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"计划投资金额"});
//		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(planType.getSelectedItem())) {
			planType.requestFocus(true);
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"计划类型"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtyear.getData())) {
			prmtyear.requestFocus(true);
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"投资年度"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(objectState.getSelectedItem())) {
			objectState.requestFocus(true);
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"项目状态"});
		}
			super.beforeStoreFields(arg0);
		}

}