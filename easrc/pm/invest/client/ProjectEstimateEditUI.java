/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
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
import com.kingdee.eas.port.pm.base.InvestYearFactory;
import com.kingdee.eas.port.pm.base.InvestYearInfo;
import com.kingdee.eas.port.pm.base.ProjectTypeFactory;
import com.kingdee.eas.port.pm.base.ProjectTypeInfo;
import com.kingdee.eas.port.pm.invest.ProjectEstimateFactory;
import com.kingdee.eas.port.pm.invest.ProjectStartRequestFactory;
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
public class ProjectEstimateEditUI extends AbstractProjectEstimateEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectEstimateEditUI.class);
    
    /**
     * output class constructor
     */
    public ProjectEstimateEditUI() throws Exception
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

    ProgrammingEditUI programmingEditUI = null;
    /**
     * output onLoad method
     */
    public void onLoad() throws Exception {
    	super.onLoad();
    	kdtE1_detailPanel.setVisible(false);
    	contDescription.setVisible(false);
		txtDescription.setVisible(false);
		contBizStatus.setVisible(false);
		comboBizStatus.setVisible(false);
		
		kdtE1.getColumn("seq").getStyleAttributes().setHided(true);
		final KDBizPromptBox kdtE1_costType_PromptBox = new KDBizPromptBox();
		kdtE1_costType_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.base.app.CostTypeTreeQuery");
		kdtE1_costType_PromptBox.setVisible(true);
        kdtE1_costType_PromptBox.setEditable(true);
        kdtE1_costType_PromptBox.setDisplayFormat("$E1.costType.name$");
        kdtE1_costType_PromptBox.setEditFormat("$E1.costType.name$");
        kdtE1_costType_PromptBox.setCommitFormat("$E1.costType.name$");
        kdtE1_costType_PromptBox.setCommitFormat("$E1.costType.name$");
		KDTDefaultCellEditor kdtE1_costType_CellEditor =new KDTDefaultCellEditor(kdtE1_costType_PromptBox);
		this.kdtE1.getColumn("costType").setEditor(kdtE1_costType_CellEditor);
		ObjectValueRender kdtE1_costType_OVR = new ObjectValueRender();
		kdtE1_costType_OVR.setFormat(new BizDataFormat("$E1.costType.name$"));
		this.kdtE1.getColumn("costType").setRenderer(kdtE1_costType_OVR);
		
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
    
    public void storeFields()
    {
        super.storeFields();
    }
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	super.verifyInput(actionevent);
    	if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtprojectName.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"项目名称"});
		}
    }
    
    /**
     * output prmttempName_dataChanged method
     */
    protected void prmttempName_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmttempName_dataChanged(e);
	}
    
    /**
	 * 点击投资规划页签加载投资规划
	 */
	protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
		super.kDTabbedPane1_stateChanged(e);
	}
	   
    /**
	 * 人员
	 */
	   protected void prmtperson_dataChanged(DataChangeEvent e) throws Exception {
	    	super.prmtperson_dataChanged(e);
	    	if (prmtperson.getValue() != null) {
	    		PersonInfo personInfo =(PersonInfo) prmtperson.getValue();
				AdminOrgUnitInfo adminInfo = PersonXRHelper.getPosiMemByDeptUser(personInfo); 
				prmtdeparment.setValue(adminInfo);// 带出当前登录职员主要部门
			}
	    }
	   
	   /**
		 *项目名称
		 */  
    
    protected void prmtprojectName_dataChanged(DataChangeEvent e)throws Exception {
    	super.prmtprojectName_dataChanged(e);
    	if(prmtprojectName.getValue()==null)
    		return;
    	ProjectInfo projectInfo = (ProjectInfo) prmtprojectName.getValue();
    	editData.setProjectName(projectInfo);
    	String oql = "";
    	if(editData.getId()==null)
    		oql= " select id where projectName.id = '"+projectInfo.getId().toString()+"'";
    	else
    		oql= " select id where projectName.id = '"+projectInfo.getId().toString()+"' and id<>'"+editData.getId().toString()+"'";
    	if(ProjectEstimateFactory.getRemoteInstance().exists(oql))
    	{
    		MsgBox.showWarning("当前项目已经有项目估算单据，请重新选择！");
    		kDScrollPane1.setViewportView(programmingEditUI);
    		prmtprojectName.setValue(null);
    		editData.setProjectName(null);
    		SysUtil.abort();
    	}
    	if(projectInfo.getNJGprojectType()!=null){
    		ProjectTypeInfo projectTypeInfo =ProjectTypeFactory.getRemoteInstance().getProjectTypeInfo(new ObjectUuidPK(projectInfo.getNJGprojectType().getId()));
    		prmtprojectType.setValue(projectTypeInfo);
    		editData.setProjectType(projectTypeInfo);
    		if(projectInfo.getNJGyearInvest()!=null){
    			YearInvestPlanInfo yearInvestPlanInfo =YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanInfo(new ObjectUuidPK(projectInfo.getNJGyearInvest().getId()));
    			if(yearInvestPlanInfo.getYear()!=null){
    				InvestYearInfo YearInfo=InvestYearFactory.getRemoteInstance().getInvestYearInfo(new ObjectUuidPK(yearInvestPlanInfo.getYear().getId()));
    				prmtyear.setValue(YearInfo);
    				editData.setYear(YearInfo);
    			}
    		
    		}
    	}
    	setPortProject(null);
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
			uiContext.put("UIName", "ProjectEstimateEditUI");
			uiContext.put("number",projectInfo.getNumber());
			uiContext.put("projectName", projectInfo.getName());
			uiContext.put("proInvestAmount", this.txtestimateAmount);
			uiContext.put("SourceBillId",editData.getId().toString());
			uiContext.put("ProjectEstimateInfo ", editData);
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
	        programmingEditUI.kdtEntries.getColumn("investAmount").getStyleAttributes().setHided(true);
	        programmingEditUI.kdtEntries.getColumn("cumulativeInvest").getStyleAttributes().setHided(true);
	        programmingEditUI.kdtEntries.getColumn("balance").getStyleAttributes().setHided(true);
	        programmingEditUI.kdtEntries.getColumn("investProportion").getStyleAttributes().setHided(true);
	        programmingEditUI.kdtEntries.getColumn("name").setWidth(200);
	        programmingEditUI.kdtEntries.getColumn("workContent").setWidth(300);
	        programmingEditUI.kdtEntries.getColumn("amount").setWidth(200);
	        kDScrollPane1.setViewportView(programmingEditUI);
	        kDScrollPane1.setKeyBoardControl(true);
	        kDScrollPane1.setEnabled(false);
		}
	}
	/**
	 * 根据项目编码，找到最新版投资规划
	 * */
	static ProgrammingInfo getProgrammingInfo(ProjectInfo projectInfo ){
		ProgrammingInfo info = new ProgrammingInfo();
		try {
			ProgrammingCollection coll = ProgrammingFactory.getRemoteInstance().getProgrammingCollection("where projectnumber='"+projectInfo.getNumber()+"' order by version desc");
			if(coll.size()>0){
				info = coll.get(0);
				info = ProgrammingFactory.getRemoteInstance().getProgrammingInfo(new ObjectUuidPK(info.getId()),YIPlanAccredEditUI.getSelector());
				info.setId(BOSUuid.create(info.getBOSType()));
				for(int i=0;i<info.getEntries().size();i++){
					ProgrammingEntryInfo entry = info.getEntries().get(i);
					entry.setIsInvite(false);
					for(int j=0;j<entry.getCostEntries().size();j++){
						entry.getCostEntries().get(j).setId(null);
						entry.getCostEntries().get(j).setBeizhu("");
					}
					info.getEntries().get(i).setId(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
  

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		saveProgramming(e);
	}
	void saveProgramming(ActionEvent e) throws Exception{
		if(programmingEditUI!=null){
			programmingEditUI.txtName.setText("项目估算");
			programmingEditUI.actionSave_actionPerformed(e);
		}
		
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
        return com.kingdee.eas.port.pm.invest.ProjectEstimateFactory.getRemoteInstance();
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
        com.kingdee.eas.port.pm.invest.ProjectEstimateInfo objectValue = new com.kingdee.eas.port.pm.invest.ProjectEstimateInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		objectValue.setBizDate(new Date());
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
        return objectValue;
    }
	protected void attachListeners() {
		addDataChangeListener(this.prmtprojectName);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtprojectName);
	}
	protected KDTextField getNumberCtrl() {
		return null;
	}

}