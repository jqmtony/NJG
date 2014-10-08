/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
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
import com.kingdee.eas.port.pm.base.CompanyPropertyFactory;
import com.kingdee.eas.port.pm.base.CompanyPropertyInfo;
import com.kingdee.eas.port.pm.base.CompanySetupEntryCollection;
import com.kingdee.eas.port.pm.base.CompanySetupEntryFactory;
import com.kingdee.eas.port.pm.base.InvestYearInfo;
import com.kingdee.eas.port.pm.base.ProjectTypeInfo;
import com.kingdee.eas.port.pm.base.coms.PlanTypeEnum;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YearInvestPlanCollection;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.port.pm.invest.investplan.IProgramming;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo;
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
		String cuid = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
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
			}
			EntityViewInfo evInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("cu.id",cuid));
			evInfo.setFilter(filter);
			prmtrequestPerson.setEntityViewInfo(evInfo);
			prmtCU.setValue(SysContext.getSysContext().getCurrentCtrlUnit());
			CompanySetupEntryCollection coll = CompanySetupEntryFactory.getRemoteInstance().getCompanySetupEntryCollection(" where company.id ='"+cuid+"'");
			if(coll!=null && coll.size()>0){
				if( coll.get(0).getCompanyProperty()!=null){
					String id = coll.get(0).getCompanyProperty().getId().toString();
					CompanyPropertyInfo pro = CompanyPropertyFactory.getRemoteInstance().getCompanyPropertyInfo(new ObjectUuidPK(id));
					prmtcompanyProperty.setValue(pro);
				}else{
					MsgBox.showInfo("本公司未维护公司性质！");
				}
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
		filter.getFilterItems().add(new FilterItemInfo("company.id",cuid));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		this.prmtportProject.setEntityViewInfo(view);
		txtseq.setRequired(true);
	}
	
	public void actionInvestPlan_actionPerformed(ActionEvent e)throws Exception {
	}
	public void onShow() throws Exception {
		super.onShow();
		if(getUIContext().get("projectInfo")!=null){
			btnSave.setText("确认修改并保存");
			btnAddNew.setVisible(false);
			btnEdit.setVisible(false);
			btnSubmit.setVisible(false);
			btnRemove.setVisible(false);
			btnAudit.setVisible(false);
			btnUnAudit.setVisible(false);
		}
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
	 * 项目类型
	 */
	protected void prmtprojectType_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtprojectType_dataChanged(e);
		if (prmtprojectType.getValue() != null) 
		{
			ProjectTypeInfo ptInfo = (ProjectTypeInfo) prmtprojectType.getValue();
			if (ptInfo.getName().equals("基本建设前期")) //基本建设前期
			{
				String projectName = txtprojectName.getText();
				if (prmtbuildType.getValue() != null) 
				{
					BuildTypeInfo bdtinfo = (BuildTypeInfo) prmtbuildType.getValue();
					if (bdtinfo.getNumber().equals("002")) //续建项目
					{
						prmtprojectType.setValue(null);
						MsgBox.showInfo("基本建设前期为新建项目，不能为续建项目！");
					}
				}
			}else{
				prmtbuildType.setEnabled(true);
			} 
		}
	}
	/**
	 * 建设性质为续建项目时显示出项目信息字段
	 */
	protected void prmtbuildType_dataChanged(DataChangeEvent e)throws Exception {
		super.prmtbuildType_dataChanged(e);
		PlanTypeEnum plantype = (PlanTypeEnum)planType.getSelectedItem();
		BuildTypeInfo bdtinfo = (BuildTypeInfo) prmtbuildType.getValue();
		if (prmtbuildType.getValue() != null) 
		{
			if (bdtinfo.getNumber().equals("002")) //续建项目
			{
				prmtportProject.setVisible(true);
				contportProject.setVisible(true);
				if (prmtprojectType.getValue() != null) 
				{
					ProjectTypeInfo ptInfo = (ProjectTypeInfo) prmtprojectType.getValue();
					if (ptInfo.getName().equals("基本建设前期")) //基本建设前期
					{
						prmtbuildType.setValue(null);
						MsgBox.showInfo("基本建设前期为新建项目，不能为续建项目！");
					}
				}
			}else if(PlanTypeEnum.change.equals(plantype)||PlanTypeEnum.adjust.equals(plantype)){
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
	protected void planType_itemStateChanged(ItemEvent e) throws Exception {
		super.planType_itemStateChanged(e);
		PlanTypeEnum plantype = (PlanTypeEnum)planType.getSelectedItem();
		if(PlanTypeEnum.change.equals(plantype)||PlanTypeEnum.adjust.equals(plantype)){
			prmtportProject.setVisible(true);
			contportProject.setVisible(true);
		}else{
			prmtportProject.setVisible(false);
			contportProject.setVisible(false);
		}
	}
	/**
	 * 校验信息
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		PlanTypeEnum plantype = (PlanTypeEnum)planType.getSelectedItem();
		if(e!=null){
			KDWorkButton btn = (KDWorkButton)e.getSource();
			if(btn.getText().equals("提交")){
				if(PlanTypeEnum.change.equals(plantype)||PlanTypeEnum.adjust.equals(plantype)){
					ClientVerifyHelper.verifyEmpty(this, this.prmtportProject);
				}
			}
		}
		BigDecimal a = UIRuleUtil.getBigDecimal((this.txtinvestAmount.getValue()));
		BigDecimal c = UIRuleUtil.getBigDecimal((this.txtamount.getValue()));
		if(a.compareTo(c)<0)
		{
			MsgBox.showWarning("项目投资总金额不能小于本年计划投资金额");
			abort();
		}
		ClientVerifyHelper.verifyEmpty(this, this.txtseq);
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
    	String cuid = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
    	String yearid = ((InvestYearInfo)prmtyear.getValue()).getId().toString();
    	BigDecimal seq = (BigDecimal)txtseq.getValue();
		String oql = " where cu.id='"+cuid+"' and year.id='"+yearid+"' and seq='"+seq+"' and id<>'"+editData.getId()+"' " +
				" and planType not in ('"+ObjectStateEnum.VETO_VALUE+"','"+ObjectStateEnum.ADJUSTED_VALUE+"')" +
				" and sourcebillid is null ";
		if(getBizInterface().exists(oql) && !(PlanTypeEnum.change.equals(plantype)||PlanTypeEnum.adjust.equals(plantype))){
			MsgBox.showWarning("本年度该项目序号已经存在，请重新编号！");
			abort();
		}
		super.verifyInput(e);
		if(programmingEditUI!=null)
			programmingEditUI.verifyDataBySave();
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
			String isAdjust = "0";//是否被调整过
			if(getUIContext().get("projectInfo")!=null)
				isAdjust = "1";
			
			kDScrollPane1.setViewportView(null);
			String number = this.txtNumber.getText();
			UIContext uiContext = new UIContext(this);
			uiContext.put("proAmount", this.txtamount);
			uiContext.put("proInvestAmount", this.txtinvestAmount);
			uiContext.put("proAddAmount", this.txtaddInvestAmount);
			uiContext.put("proBalance", this.txtbalance);
			uiContext.put("txtchancedAmount", this.txtchancedAmount);
			uiContext.put("isAdjust", isAdjust);
			uiContext.put("UI", this);
			PlanTypeEnum plantype = (PlanTypeEnum)planType.getSelectedItem();
			//新建项目
			if(prmtportProject.getValue()==null 
						|| (prmtportProject.getValue()!=null && PlanTypeEnum.change.equals(plantype))){
				//加载投资规划编辑界面
		    	String oql = "where SourceBillId='"+editData.getId()+"'";
		    	boolean flse = true;
		    	if(ProgrammingFactory.getRemoteInstance().exists(oql))
		    	{
		    		uiContext.put("ID", ProgrammingFactory.getRemoteInstance().getProgrammingCollection(oql).get(0).getId());
		    		flse = false;
		    	}
		    	uiContext.put("yearPlanId",editData.getId().toString());
		        programmingEditUI = (ProgrammingEditUI) UIFactoryHelper.initUIObject(ProgrammingEditUI.class.getName(), uiContext, null, flse?"ADDNEW":getOprtState());
		        kDScrollPane1.setViewportView(programmingEditUI);
		        kDScrollPane1.setKeyBoardControl(true);
		        kDScrollPane1.setEnabled(false);
			}
			//续建项目
			if((prmtportProject.getValue()!=null && !PlanTypeEnum.change.equals(plantype)))
			{
				verifyInput(null);
				IProgramming iProgramming = ProgrammingFactory.getRemoteInstance();
				ProjectInfo projectInfo = (ProjectInfo)prmtportProject.getValue();
				YearInvestPlanInfo yearInvestPlanInfo = (YearInvestPlanInfo)projectInfo.getNJGyearInvest();
				yearInvestPlanInfo = YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanInfo(new ObjectUuidPK(yearInvestPlanInfo.getId()));
		        String oql = "select id where projectNumber='"+projectInfo.getNumber()+"'" ;
		        oql = oql +" order by version desc ";
		        ProgrammingInfo pmInfo = iProgramming.getProgrammingCollection(oql).get(0);
				if(iProgramming.exists("where sourceBillId='"+editData.getId()+"'"))
				{
					ProgrammingInfo programming = iProgramming.getProgrammingInfo("where sourceBillId='"+editData.getId().toString()+"'");
					uiContext.put("ID", programming.getId());
			    	uiContext.put("yearPlanId",editData.getId().toString());
			    	programmingEditUI = (ProgrammingEditUI) UIFactoryHelper.initUIObject(ProgrammingEditUI.class.getName(), uiContext, null, "EDIT");
			    	kDScrollPane1.setViewportView(programmingEditUI);
			    	kDScrollPane1.setKeyBoardControl(true);
			    	kDScrollPane1.setEnabled(false);
				}else{
			    	uiContext.put("ID", pmInfo.getId());
			    	uiContext.put("yearPlanId",editData.getId().toString());
			    	programmingEditUI = (ProgrammingEditUI) UIFactoryHelper.initUIObject(ProgrammingEditUI.class.getName(), uiContext, null, "EDIT");
			    	kDScrollPane1.setViewportView(programmingEditUI);
			    	kDScrollPane1.setKeyBoardControl(true);
			    	kDScrollPane1.setEnabled(false);
			        programmingEditUI.actionCopy_actionPerformed(null);
				}
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
		if(programmingEditUI!=null)
			programmingEditUI.actionSave_actionPerformed(e);
		
		saveAuditingEditInfo(editData);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if(programmingEditUI!=null)
			programmingEditUI.actionSubmit_actionPerformed(e);
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
		objectValue.setDesc("项目后评估要基本能体现以下内容："+
				"\n1、预算执行情况对比分析（并附表说明）"+
				"\n2、主要经济、技术指标实现情况对比分析（并附表说明）"+
				"\n3、主要偏差、问题及原因分析（项目从申报、实施、竣工验收、试运营各阶段出现的偏差、问题及原因分析）"+
				"\n4、项目自评估报告（作为附件插入）");
		return objectValue;
	}
	/**
	 * 评审、批准中，直接修改
	 * 将修改前版本存入历史库
	 * */
	void saveAuditingEditInfo(YearInvestPlanInfo objectValue){
		if(getUIContext().get("projectInfo")!=null){
			YearInvestPlanInfo info = (YearInvestPlanInfo)getUIContext().get("projectInfo");
			try {
				info.setSourceBillId(objectValue.getId().toString());
				for(int i=0;i<objectValue.getE3().size();i++){
					info.getE3().get(i).setId(null);
				}
				YearInvestPlanCollection coll = YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanCollection("where sourcebillid='"+info.getId()+"'");
				info.setNumber(info.getNumber()+"_0"+(coll.size()+1));
				info.setId(BOSUuid.create(info.getBOSType()));
				info.setObjectState(ObjectStateEnum.adjusted);
				YearInvestPlanFactory.getRemoteInstance().save(info);
				
				ProgrammingInfo programmingInfo = (ProgrammingInfo)getUIContext().get("programmingInfo");
				programmingInfo.setSourceBillId(info.getId().toString());
				programmingInfo.setProjectName(info.getProjectName());
				programmingInfo.setProjectNumber(info.getNumber());
				ProgrammingFactory.getRemoteInstance().save(programmingInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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