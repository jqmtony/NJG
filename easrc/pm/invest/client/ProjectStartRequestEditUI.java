/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.ProjectCollection;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.port.pm.base.CompanyPropertyFactory;
import com.kingdee.eas.port.pm.base.CompanyPropertyInfo;
import com.kingdee.eas.port.pm.base.CompanySetupEntryCollection;
import com.kingdee.eas.port.pm.base.CompanySetupEntryFactory;
import com.kingdee.eas.port.pm.base.CostTypeFactory;
import com.kingdee.eas.port.pm.base.ICostType;
import com.kingdee.eas.port.pm.base.InvestYearFactory;
import com.kingdee.eas.port.pm.base.InvestYearInfo;
import com.kingdee.eas.port.pm.base.ProjectTypeFactory;
import com.kingdee.eas.port.pm.base.ProjectTypeInfo;
import com.kingdee.eas.port.pm.invest.ProjectBudget2Collection;
import com.kingdee.eas.port.pm.invest.ProjectBudget2E1Collection;
import com.kingdee.eas.port.pm.invest.ProjectBudget2E1Info;
import com.kingdee.eas.port.pm.invest.ProjectBudget2Factory;
import com.kingdee.eas.port.pm.invest.ProjectBudget2Info;
import com.kingdee.eas.port.pm.invest.ProjectStartRequestFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.port.pm.invest.investplan.IProgramming;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo;
import com.kingdee.eas.port.pm.invest.investplan.client.ProgrammingEditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.helper.PersonXRHelper;

/**
 * output class name
 */
public class ProjectStartRequestEditUI extends AbstractProjectStartRequestEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectStartRequestEditUI.class);
    ProgrammingEditUI programmingEditUI = null;
    /**
     * output class constructor
     */
    public ProjectStartRequestEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
    	this.detachListeners();
        super.loadFields();
        this.attachListeners();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	txtBIMUDF0022.setVisible(false);
    	contBIMUDF0022.setVisible(false);
    	KDDatePicker kdtE1_startDate_DatePicker = new KDDatePicker();
        kdtE1_startDate_DatePicker.setName("kdtE1_startDate_DatePicker");
        kdtE1_startDate_DatePicker.setVisible(true);
        kdtE1_startDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtE1_startDate_CellEditor = new KDTDefaultCellEditor(kdtE1_startDate_DatePicker);
        
        FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		String cuid = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		if (getOprtState().equals(OprtState.ADDNEW))
		{
			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
			if (user.getPerson() != null) 
			{//申请人带出申请单位
				PersonInfo person = user.getPerson();
				prmtperson.setValue(person);
				AdminOrgUnitCollection orgColl = PersonXRHelper.getDepartmentByUserCollection(person);
				AdminOrgUnitInfo adminInfo = orgColl.get(0);
				prmtdeparment.setValue(adminInfo);
			}
			EntityViewInfo evInfo = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("cu.id",cuid));
			evInfo.setFilter(filter);
			prmtperson.setEntityViewInfo(evInfo);
			prmtCU.setValue(SysContext.getSysContext().getCurrentCtrlUnit());
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("company.id",cuid));
		view = new EntityViewInfo();
		view.setFilter(filter);
		this.prmtprojectName.setEntityViewInfo(view);
		setPortProject(null);
    }
    
    protected void prmtperson_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtperson_dataChanged(e);
    	if (prmtperson.getValue() != null) {
    		PersonInfo personInfo =(PersonInfo) prmtperson.getValue();
			AdminOrgUnitInfo adminInfo = PersonXRHelper.getPosiMemByDeptUser(personInfo); 
			prmtdeparment.setValue(adminInfo);// 带出当前登录职员主要部门
		}
    }
    
    protected void prmtprojectName_dataChanged(DataChangeEvent e)throws Exception {
    	super.prmtprojectName_dataChanged(e);

    	if(prmtprojectName.getValue()==null)
    		return;
    	if(prmtprojectName.getValue()!=null)
    	{
    		ProjectInfo Info = (ProjectInfo) prmtprojectName.getValue();
    		editData.setProjectName(Info);
        	String oql = "";
        	if(editData.getId()==null)
        		oql= " select id where projectName.id = '"+Info.getId().toString()+"'";
        	else
        		oql= " select id where projectName.id = '"+Info.getId().toString()+"' and id<>'"+editData.getId().toString()+"'";
        	if(ProjectStartRequestFactory.getRemoteInstance().exists(oql))
        	{
        		MsgBox.showWarning("当前项目已经有项目启动申请单据，请重新选择！");
        		prmtprojectName.setValue(null);
        		editData.setProjectName(null);
        		SysUtil.abort();
        	}
    		if(Info.getNJGprojectType()!=null){
        		ProjectTypeInfo projectTypeInfo =ProjectTypeFactory.getRemoteInstance().getProjectTypeInfo(new ObjectUuidPK(Info.getNJGprojectType().getId()));
        		prmtprojectType.setValue(projectTypeInfo);
        		editData.setProjectType(projectTypeInfo);
        		if(Info.getNJGyearInvest()!=null){
        			YearInvestPlanInfo yearInvestPlanInfo =YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanInfo(new ObjectUuidPK(Info.getNJGyearInvest().getId()));
        			if(yearInvestPlanInfo.getYear()!=null){
        				InvestYearInfo YearInfo=InvestYearFactory.getRemoteInstance().getInvestYearInfo(new ObjectUuidPK(yearInvestPlanInfo.getYear().getId()));
        				prmtyear.setValue(YearInfo);
        				editData.setYear(YearInfo);
        			}
        		}else{
            		EntityViewInfo view = new EntityViewInfo();
            		FilterInfo filter = new FilterInfo();
            		view.setFilter(filter);
            		filter.getFilterItems().add(new FilterItemInfo("name","%"+Info.getName()+"%",CompareType.LIKE));
            		filter.getFilterItems().add(new FilterItemInfo("id",Info.getId(),CompareType.NOTEQUALS));
            		StringBuffer sb = new StringBuffer();
            		ProjectCollection coll = ProjectFactory.getRemoteInstance().getProjectCollection(view);
            		for (int i = 0; i < coll.size(); i++) {
            			sb.append(coll.get(i).getName());
            			if(i>0){
            				sb.append(" 或者  "+coll.get(i).getName());
            			}
    				}
            		MsgBox.showWarning("当前项目没有对应投资规划，可能你选择了父级项目！\n 你是否是要选择【"+sb.toString()+"】，请重新选择！");
            		prmtprojectName.setValue(null);
            		editData.setProjectName(null);
            		SysUtil.abort();
            	}
        	}
    	}
    	setPortProject(null);
    }
    /**
	 * 点击投资规划页签加载投资规划
	 */
	protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
		super.kDTabbedPane1_stateChanged(e);
		
	}

	/**
	 * 项目信息及投资规划界面加载封装
	 */
	private void setPortProject(ChangeEvent e) throws Exception
	{
		ProjectInfo projectInfo = (ProjectInfo)prmtprojectName.getValue();
		if(editData!=null && prmtprojectName.getValue()!=null){
			if(editData.getId()==null)
				editData.setId(BOSUuid.create(editData.getBOSType()));
			kDScrollPane1.setViewportView(null);
			UIContext uiContext = new UIContext(this);
			uiContext.put("UIName", "ProjectStartRequestEditUI");
			uiContext.put("number",projectInfo.getNumber());
			uiContext.put("projectName", projectInfo.getName());
			uiContext.put("proAmount", this.txtamount);
			uiContext.put("SourceBillId",editData.getId().toString());
			uiContext.put("investYearInfo ", editData.getYear());
			uiContext.put("programmingInfo", ProjectEstimateEditUI.getProgrammingInfo(projectInfo));
			//加载投资规划编辑界面
	    	String oql = "where SourceBillId='"+editData.getId()+"'";
	    	if(ProgrammingFactory.getRemoteInstance().exists(oql))
	    	{
	    		uiContext.put("ID", ProgrammingFactory.getRemoteInstance().getProgrammingCollection(oql).get(0).getId());
	    	}
	    	uiContext.put("yearPlanId",editData.getId().toString());
	        programmingEditUI = (ProgrammingEditUI) UIFactoryHelper.initUIObject(ProgrammingEditUI.class.getName(), uiContext, null,getOprtState());
	        programmingEditUI.kdtEntries.getColumn("name").setWidth(180);
	        programmingEditUI.kdtEntries.getColumn("workContent").setWidth(280);
	        programmingEditUI.kdtEntries.getColumn("amount").setWidth(120);
	        programmingEditUI.kdtEntries.getColumn("investAmount").setWidth(120);
	        programmingEditUI.kdtEntries.getColumn("cumulativeInvest").setWidth(120);
	        programmingEditUI.kdtEntries.getColumn("balance").setWidth(120);
	        programmingEditUI.kdtEntries.getColumn("investProportion").setWidth(80);
	        kDScrollPane1.setViewportView(programmingEditUI);
	        kDScrollPane1.setKeyBoardControl(true);
	        kDScrollPane1.setEnabled(false);
		}
	}
	/**
	 * output actionSave_actionPerformed
	 */
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
		if(programmingEditUI!=null){
			programmingEditUI.txtName.setText("项目启动申请");
			programmingEditUI.actionSave_actionPerformed(e);
		}
	}

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invest.ProjectStartRequestFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo objectValue = new com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		objectValue.setBizDate(new Date());
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setDec("项目后评估要基本能体现以下内容："+
				"\n1、预算执行情况对比分析（并附表说明）"+
				"\n2、主要经济、技术指标实现情况对比分析（并附表说明）"+
				"\n3、主要偏差、问题及原因分析（项目从申报、实施、竣工验收、试运营各阶段出现的偏差、问题及原因分析）"+
				"\n4、项目自评估报告（作为附件插入）");
        return objectValue;
    }
    protected void attachListeners() {
		addDataChangeListener(this.prmtprojectName);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtprojectName);
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if(!chkwuxu.isSelected()){
			if(kdtEntry.getRowCount()==0){
				MsgBox.showInfo("需要招标，请填写招标计划");
				SysUtil.abort();
			}
		}
		super.verifyInput(e);
	}
	
}