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
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
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
import com.kingdee.eas.port.pm.base.BuildTypeFactory;
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
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo;
import com.kingdee.eas.port.pm.invest.investplan.client.ProgrammingEditUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.ClientVerifyXRHelper;
import com.kingdee.eas.xr.helper.PersonXRHelper;
import com.kingdee.jdbc.rowset.IRowSet;

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
		if(getUIContext().get("projectInfo-Adjuest")!=null && !"".equals(getUIContext().get("projectInfo-Adjuest")))
			planType.setSelectedItem(PlanTypeEnum.adjust);
		if(getUIContext().get("projectInfo-Change")!=null && !"".equals(getUIContext().get("projectInfo-Change"))){
			planType.setSelectedItem(PlanTypeEnum.change);
		}
		init();
		String cuid = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		if (getOprtState().equals(OprtState.ADDNEW)){
			String number = txtNumber.getText();
			int s = number.indexOf("-");
			String number1 = number.substring(0, s);
			String number2 = number.substring(s+1, number.length());
			txtNumber.setText(number1+"-"+SysContext.getSysContext().getCurrentCtrlUnit().getNumber()+number2);
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
		if(getUIContext().get("project")!=null)
			prmtportProject.setValue((ProjectInfo)getUIContext().get("project"));
		if(getUIContext().get("projectInfo-Adjuest")!=null){
			objectState.setSelectedItem(ObjectStateEnum.save);
			comboStatus.setSelectedItem(XRBillStatusEnum.TEMPORARILYSAVED);
		}
		setPortProject(null);
		
	}
	
	public void actionInvestPlan_actionPerformed(ActionEvent e)throws Exception {
	}
	public void onShow() throws Exception {
		super.onShow();
		kDTabbedPane1.remove(kDPanel5);
		pkplanEndDate.setRequired(true);
		pkplanStartDate.setRequired(true);
		if(getUIContext().get("projectInfo-edit")!=null){
			btnSave.setText("确认修改并保存");
			btnAddNew.setVisible(false);
			btnEdit.setVisible(false);
			btnSubmit.setVisible(false);
			btnRemove.setVisible(false);
			btnAudit.setVisible(false);
			btnUnAudit.setVisible(false);
		}
		if(!(planType.getSelectedItem().equals(PlanTypeEnum.adjust)||planType.getSelectedItem().equals(PlanTypeEnum.change))){
			planType.removeItem((PlanTypeEnum.adjust));
			planType.removeItem(PlanTypeEnum.change);
		}
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		if(editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("projectName.id",editData.getId().toString()));
			try {
              IQueryExecutor exec = this.getQueryExecutor(this.queryYearPlanAuditInfoQueryPK, evi);
              exec.option().isAutoIgnoreZero = true;
          	  exec.option().isAutoTranslateBoolean = true;
          	  exec.option().isAutoTranslateEnum = true; 
              IRowSet rowSet = exec.executeQuery();
              kdtE3.removeRows();
              while(rowSet.next()){
            	  IRow row = kdtE3.addRow();
            	  row.getCell("accredType").setValue(rowSet.getObject("accredType"));
            	  row.getCell("accredDate").setValue(rowSet.getObject("accredDate"));
            	  row.getCell("auditTime").setValue(rowSet.getObject("auditTime"));
            	  row.getCell("E1.accredResu").setValue(rowSet.getObject("E1.accredResu"));
            	  row.getCell("E1.projectConclude").setValue(rowSet.getObject("E1.projectConclude"));
            	  row.getCell("accredPerson.name").setValue(rowSet.getObject("accredPerson.name"));
            	  row.getCell("accredDpart.name").setValue(rowSet.getObject("accredDpart.name"));
            	  row.getCell("E2.aduitTime").setValue(rowSet.getObject("E2.aduitTime"));
            	  row.getCell("E2.accreConclu").setValue(rowSet.getObject("E2.accreConclu"));
            	  row.getCell("E2.opinion").setValue(rowSet.getObject("E2.opinion"));
            	  row.getCell("E2.remark").setValue(rowSet.getObject("E2.remark"));
            	  row.getCell("projectName.id").setValue(rowSet.getObject("projectName.id"));
            	  row.getCell("projectName.projectName").setValue(rowSet.getObject("projectName.projectName"));
            	  row.getCell("id").setValue(rowSet.getObject("id"));
              }
          } catch (Exception ee) {
              handUIException(ee);
          }
			this.kdtE3.getMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
		}
		kdtE3.getColumn("projectName.id").getStyleAttributes().setHided(true);
		kdtE3.getColumn("projectName.projectName").getStyleAttributes().setHided(true);
		kdtE3.getColumn("id").getStyleAttributes().setHided(true);
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
			if (ptInfo.getName().equals("项目前期")) //基本建设前期
			{
				String projectName = txtprojectName.getText();
				if (prmtbuildType.getValue() != null) 
				{
					BuildTypeInfo bdtinfo = (BuildTypeInfo) prmtbuildType.getValue();
					if (bdtinfo.getNumber().equals("002")) //续建项目
					{
						prmtprojectType.setValue(null);
						MsgBox.showInfo("项目前期为新建项目，不能为续建项目！");
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
					if (ptInfo.getName().equals("项目前期")) //基本建设前期
					{
						prmtbuildType.setValue(null);
						MsgBox.showInfo("项目前期为新建项目，不能为续建项目！");
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
		if(prmtyear.getValue()!=null){
			InvestYearInfo info = (InvestYearInfo)prmtyear.getValue();
			programmingEditUI.year = info.getNumber();
		}
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
	 * 续建时，项目信息选择已立项项目
	 */
	protected void prmtportProject_dataChanged(DataChangeEvent e)throws Exception {
		super.prmtportProject_dataChanged(e);
		if(!(getUIContext().get("projectInfo-Change")!=null)){
			if(!BizCollUtil.isF7ValueChanged(e)||e.getNewValue()==null){
				return;
			}
			verifySave(null);
			setPortProject(null);
		}
	}
	
	/**
	 * 点击投资规划页签加载投资规划
	 */
	protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
		super.kDTabbedPane1_stateChanged(e);
	}
	
	
	private void verifySave(ChangeEvent e) throws EASBizException, BOSException{
		
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}
	/**
	 * 项目信息及投资规划界面加载封装
	 */
	private void setPortProject(ChangeEvent e) throws Exception
	{
		if(prmtyear.getValue()!=null){
			InvestYearInfo info = (InvestYearInfo)prmtyear.getValue();
			programmingEditUI.year = info.getNumber();
		}
		if(editData!=null){
			if(editData.getId()==null)
				editData.setId(BOSUuid.create(editData.getBOSType()));
			String isAdjust = "0";//是否被调整过
			if(getUIContext().get("projectInfo")!=null)
				isAdjust = "1";
			kDScrollPane1.setViewportView(null);
			String number = this.txtNumber.getText();
			UIContext uiContext = new UIContext(this);
			uiContext.put("programmingInfo", getUIContext().get("programmingInfo"));
			uiContext.put("proAmount", this.txtamount);
			uiContext.put("proInvestAmount", this.txtinvestAmount);
			uiContext.put("proAddAmount", this.txtaddInvestAmount);
			uiContext.put("proBalance", this.txtbalance);
			uiContext.put("txtchancedAmount", this.txtchancedAmount);
			uiContext.put("projectName", this.txtprojectName.getText());
			uiContext.put("number", number);
			uiContext.put("isAdjust", isAdjust);
			uiContext.put("UI", this);
			uiContext.put("YearInvestPlanInfo ", editData);
			uiContext.put("investYearInfo ", editData.getYear());
			uiContext.put("yearPlanId",editData.getId().toString());
			//加载投资规划编辑界面
	    	String oql = "where SourceBillId='"+editData.getId()+"'";
	    	if(ProgrammingFactory.getRemoteInstance().exists(oql))
	    	{
	    		uiContext.put("ID", ProgrammingFactory.getRemoteInstance().getProgrammingCollection(oql).get(0).getId());
	    	}
	        programmingEditUI = (ProgrammingEditUI) UIFactoryHelper.initUIObject(ProgrammingEditUI.class.getName(), uiContext, null,getOprtState());
	        kDScrollPane1.setViewportView(programmingEditUI);
	        kDScrollPane1.setKeyBoardControl(true);
	        kDScrollPane1.setEnabled(false);
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
	 * output actionSave_actionPerformed
	 */
	boolean isFirstSave = true;
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		saveProgramming(e);
	}
	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		saveProgramming(e);
	}
	
	void saveProgramming(ActionEvent e) throws Exception{
		if(isFirstSave ){
			if(programmingEditUI!=null){
				String projectName = txtprojectName.getText();
				String projectNumber = txtNumber.getText();
				programmingEditUI.txtprojectName.setText(projectName);
				if("".equals(programmingEditUI.txtprojectNumber.getText()))
					programmingEditUI.txtprojectNumber.setText(projectNumber);
				programmingEditUI.txtName.setText("年度投资计划");
				ProgrammingInfo info = programmingEditUI.editData;
				for(int i=0;i<info.getEntries().size();i++){
					ProgrammingEntryInfo entry = info.getEntries().get(i);
					entry.setDescription(projectName);
					entry.setSimpleName(projectNumber);
					for(int j=0;j<entry.getCostEntries().size();j++){
						ProgrammingEntryCostEntryInfo cost = entry.getCostEntries().get(j);
						cost.setProject(projectName);
						cost.setNumber(projectNumber);
					}
				}
				programmingEditUI.actionSave_actionPerformed(e);
			}
			if(isFirstSave)
				saveAuditingEditInfo(editData);
			isFirstSave = false;
		}
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
		return com.kingdee.eas.port.pm.invest.YearInvestPlanFactory.getRemoteInstance();
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
		YearInvestPlanInfo info = (YearInvestPlanInfo)getUIContext().get("projectInfo-Continue");
		if(info!=null){
			objectValue.setProjectName(info.getProjectName());
			objectValue.setProjectType(info.getProjectType());
			try {
				objectValue.setBuildType(BuildTypeFactory.getRemoteInstance().getBuildTypeInfo("where number='002'"));
			} catch (Exception e) {
				e.printStackTrace();
			} 
			objectValue.setPortProject(info.getProject());
			objectValue.setFundSource(info.getFundSource());
			objectValue.setPlanStartDate(info.getPlanStartDate());
			objectValue.setPlanEndDate(info.getPlanEndDate());
		}
		info = (YearInvestPlanInfo)getUIContext().get("projectInfo-Adjuest");
		if(info!=null){
			objectValue = info;
		}
		return objectValue;
	}
	/**
	 * 评审、批准中，直接修改
	 * 将修改前版本存入历史库
	 * */
	void saveAuditingEditInfo(YearInvestPlanInfo objectValue){
		YearInvestPlanInfo info = new YearInvestPlanInfo();
		ObjectStateEnum state = null;
		if(getUIContext().get("projectInfo-edit")!=null){
			info = (YearInvestPlanInfo)getUIContext().get("projectInfo-edit");
			state = ObjectStateEnum.adjusted;
		}
		if(getUIContext().get("projectInfo-Adjuest")!=null){
			info = (YearInvestPlanInfo)getUIContext().get("projectInfo-Adjuest");
			state = ObjectStateEnum.adjusted;
		}
		if(getUIContext().get("projectInfo-Change")!=null){
			info = (YearInvestPlanInfo)getUIContext().get("projectInfo-Change");
			state = ObjectStateEnum.change;
		}
		if(getUIContext().get("projectInfo-Continue")!=null){
			info = (YearInvestPlanInfo)getUIContext().get("projectInfo-Continue");
		}
		try {
			YearInvestPlanCollection coll = YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanCollection("where sourcebillid='"+info.getId()+"'");
			if(ObjectStateEnum.adjusted.equals(state)){
				info.setNumber(info.getNumber()+"_0"+(coll.size()+1));
				info.setId(BOSUuid.create(info.getBOSType()));
				info.setStatus(XRBillStatusEnum.AUDITED);
				info.setSourceBillId(objectValue.getId().toString());
				for(int i=0;i<objectValue.getE3().size();i++){
					info.getE3().get(i).setId(null);
				}
			}
			if(state!=null){
				info.setObjectState(state);
				YearInvestPlanFactory.getRemoteInstance().save(info);
			}
			
			ProgrammingInfo programmingInfo = (ProgrammingInfo)getUIContext().get("programmingInfo");
//			programmingInfo.setDescription(info)
			programmingInfo.setSourceBillId(info.getId().toString());
			programmingInfo.setProjectName(info.getProjectName());
			if(getUIContext().get("projectInfo-edit")!=null 
					|| getUIContext().get("projectInfo-Adjuest")!=null){
				ProgrammingFactory.getRemoteInstance().save(programmingInfo);
				ProgrammingCollection gramColl = ProgrammingFactory.getRemoteInstance().getProgrammingCollection("where sourcebillid='"+editData.getId()+"'");
				programmingInfo = gramColl.get(0);
				programmingInfo.setVersion(null);
				ProgrammingFactory.getRemoteInstance().save(programmingInfo);
				programmingInfo = ProgrammingFactory.getRemoteInstance().getProgrammingInfo(new ObjectUuidPK(programmingInfo.getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		void init(){
			this.prmtportProject.setEnabled(false);
			this.prmtproject.setEnabled(false);
			this.btnEdit.setVisible(false);
			this.txtseq.setRequired(true);
			this.actionRemoveLine.setVisible(false);
			this.actionAddLine.setVisible(false);
			this.actionInsertLine.setVisible(false);
			this.actionCopy.setVisible(false);
			this.contCU.setVisible(true);
			this.prmtCU.setVisible(true);
			this.contrequestOrg.setEnabled(false);
			this.kdtEntry.getColumn("seq").getStyleAttributes().setHided(true);
			this.kdtE3.setEditable(false);
			this.txtBIMUDF0027.setMaxLength(2000);
			this.txtanalyse.setMaxLength(2000);
			this.txtscheme.setMaxLength(2000);
			this.kDContainer1.removeAll();
			this.kDContainer1.setVisible(false);
			this.txtinvestAmount.setEnabled(false);
			this.continvestAmount.setEnabled(false);
			this.txtamount.setEnabled(false);
			this.txtbalance.setEnabled(false);
			this.contcostTemp.setVisible(false);
			this.prmtcostTemp.setVisible(false);
			this.prmtcostTemp.setEnabled(false);
			
			PlanTypeEnum plantype = (PlanTypeEnum)planType.getSelectedItem();
			BuildTypeInfo bdtinfo = (BuildTypeInfo) prmtbuildType.getValue()==null?new BuildTypeInfo():(BuildTypeInfo) prmtbuildType.getValue();
			if(PlanTypeEnum.change.equals(plantype)||PlanTypeEnum.adjust.equals(plantype)||"002".equals(bdtinfo.getNumber())){
				prmtportProject.setVisible(true);
				contportProject.setVisible(true);
			}else{
				prmtportProject.setVisible(false);
				contportProject.setVisible(false);
				prmtportProject.setValue(null);
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
		
}