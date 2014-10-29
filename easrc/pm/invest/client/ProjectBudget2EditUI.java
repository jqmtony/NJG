/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
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
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.IMeasureUnit;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.port.pm.base.CostTypeFactory;
import com.kingdee.eas.port.pm.base.CostTypeTreeFactory;
import com.kingdee.eas.port.pm.base.ICostType;
import com.kingdee.eas.port.pm.base.ICostTypeTree;
import com.kingdee.eas.port.pm.base.InvestYearFactory;
import com.kingdee.eas.port.pm.base.InvestYearInfo;
import com.kingdee.eas.port.pm.base.ProjectTypeFactory;
import com.kingdee.eas.port.pm.base.ProjectTypeInfo;
import com.kingdee.eas.port.pm.invest.ProjectBudget2Factory;
import com.kingdee.eas.port.pm.invest.ProjectEstimateCollection;
import com.kingdee.eas.port.pm.invest.ProjectEstimateE1Collection;
import com.kingdee.eas.port.pm.invest.ProjectEstimateE1Info;
import com.kingdee.eas.port.pm.invest.ProjectEstimateFactory;
import com.kingdee.eas.port.pm.invest.ProjectEstimateInfo;
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
public class ProjectBudget2EditUI extends AbstractProjectBudget2EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectBudget2EditUI.class);
    ProgrammingEditUI programmingEditUI = null;
    /**
     * output class constructor
     */
    public ProjectBudget2EditUI() throws Exception
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
    public void onLoad() throws Exception {
    	super.onLoad();
    	kdtE1_detailPanel.setVisible(false);
    	contDescription.setVisible(false);
		txtDescription.setVisible(false);
		contBizStatus.setVisible(false);
		comboBizStatus.setVisible(false);
		kdtE1.getColumn("seq").getStyleAttributes().setHided(true);
		
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
//		filter.getFilterItems().add(new FilterItemInfo("NJGprojectType.name","基本建设",com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
		view = new EntityViewInfo();
		view.setFilter(filter);
		this.prmtprojectName.setEntityViewInfo(view);
		setPortProject(null);
		
    }
    public void storeFields()
    {
        super.storeFields();
    }
    protected void prmtperson_dataChanged(DataChangeEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.prmtperson_dataChanged(e);
    	if (prmtperson.getValue() != null) {
    		PersonInfo personInfo =(PersonInfo) prmtperson.getValue();
			AdminOrgUnitInfo adminInfo = PersonXRHelper.getPosiMemByDeptUser(personInfo); 
			prmtdeparment.setValue(adminInfo);// 带出当前登录职员主要部门
		}
    }
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	
    	if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty("NONE") && !"NONE".equalsIgnoreCase("NONE") && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("NONE"))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("NONE")).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    	super.verifyInput(actionevent);
    }
    
    protected void kdtE1_editStopped(KDTEditEvent e) throws Exception {
    	super.kdtE1_editStopped(e);
    	Object oldValue = e.getOldValue();
    	Object newValue = e.getValue();
    	int rowIndex = e.getRowIndex();
    	int colIndex = e.getColIndex();
    	String fieldName = kdtE1.getColumn(colIndex).getKey();
    	if(oldValue==null&&newValue==null){
    		return;
    	}
    	BigDecimal totalCount = new BigDecimal(UIRuleUtil.sum(this.kdtE1, "total"));
    	if("total".equalsIgnoreCase(fieldName)&&this.kdtE1.getCell(rowIndex, "total").getValue()!=null){
    		getProportion(totalCount);
    	}
    	if("constructCost".equalsIgnoreCase(fieldName)&&kdtE1.getCell(rowIndex,"constructCost").getValue()!=null){
    		getProportion(totalCount);
    	}
    	if("EPCost".equalsIgnoreCase(fieldName)&&kdtE1.getCell(rowIndex,"EPCost").getValue()!=null){
    		getProportion(totalCount);
    	}
    	if("installCost".equalsIgnoreCase(fieldName)&&kdtE1.getCell(rowIndex,"installCost").getValue()!=null){
    		getProportion(totalCount);
    	}
    	if("otherCost".equalsIgnoreCase(fieldName)&&kdtE1.getCell(rowIndex,"otherCost").getValue()!=null){
    		getProportion(totalCount);
    	}
    	this.txtestimateCost.setValue(totalCount);
    }
    
    protected void getProportion(BigDecimal totalCount){
    	BigDecimal sum=BigDecimal.ZERO;
    	for (int i = 0; i < kdtE1.getRowCount(); i++) {
        	BigDecimal total = UIRuleUtil.getBigDecimal((kdtE1.getCell(i,"total").getValue()));
        	BigDecimal constructCost = UIRuleUtil.getBigDecimal((kdtE1.getCell(i,"constructCost").getValue())).divide(new BigDecimal(1), 2, 4);
        	BigDecimal epCost = UIRuleUtil.getBigDecimal((kdtE1.getCell(i,"EPCost").getValue())).divide(new BigDecimal(1), 2, 4);
        	BigDecimal installCost = UIRuleUtil.getBigDecimal((kdtE1.getCell(i,"installCost").getValue())).divide(new BigDecimal(1), 2, 4);
        	BigDecimal otherCost = UIRuleUtil.getBigDecimal((kdtE1.getCell(i,"otherCost").getValue())).divide(new BigDecimal(1), 2, 4);
        	total=constructCost.add(epCost).add(installCost).add(otherCost);
        	kdtE1.getCell(i,"total").setValue(total);
        	if(total.compareTo(BigDecimal.ZERO)<0){
        		MsgBox.showWarning("数值不能为负！");
        		kdtE1.getCell(i,"total").setValue(BigDecimal.ZERO);
        	}
        	if(i==kdtE1.getRowCount()-1){
    			if(total.compareTo(BigDecimal.ZERO)==0||totalCount.compareTo(BigDecimal.ZERO)==0){
    			 kdtE1.getCell(i,"proportion").setValue(BigDecimal.ZERO);
    			}else{
        			kdtE1.getCell(i,"proportion").setValue(new BigDecimal(100).subtract(sum));
    			}
    		}else{
    			if(total.compareTo(BigDecimal.ZERO)==0||totalCount.compareTo(BigDecimal.ZERO)==0){
    				 kdtE1.getCell(i,"proportion").setValue(BigDecimal.ZERO);
    			}else{
    				BigDecimal proportion =UIRuleUtil.getBigDecimal((kdtE1.getCell(i, "total").getValue())).
            		multiply(new BigDecimal(100)).divide(totalCount, 2, 4);
        			sum=sum.add(proportion);
        			kdtE1.getCell(i,"proportion").setValue(proportion);
    			}
    		}
		}
    }
    
    /**
     * output prmtprojectName_dataChanged method
     */
    protected void prmtprojectName_dataChanged(DataChangeEvent e)throws Exception {
    	super.prmtprojectName_dataChanged(e);
    	
    	if(prmtprojectName.getValue()==null)
    		return;
    	ProjectInfo ptinfo = (ProjectInfo) prmtprojectName.getValue();
    	editData.setProjectName(ptinfo);
    	String oql = "";
    	if(editData.getId()==null)
    		oql= " select id where projectName.id = '"+ptinfo.getId().toString()+"'";
    	else
    		oql= " select id where projectName.id = '"+ptinfo.getId().toString()+"' and id<>'"+editData.getId().toString()+"'";
    	if(ProjectBudget2Factory.getRemoteInstance().exists(oql))
    	{
    		MsgBox.showWarning("当前项目已经有项目概算单据，请重新选择！");
    		prmtprojectName.setValue(null);
    		editData.setProjectName(null);
    		SysUtil.abort();
    	}
    	if(ptinfo.getNJGprojectType()!=null){
    		ProjectTypeInfo projectTypeInfo =ProjectTypeFactory.getRemoteInstance().getProjectTypeInfo(new ObjectUuidPK(ptinfo.getNJGprojectType().getId()));
    		prmtprojectType.setValue(projectTypeInfo);
    		editData.setProjectType(projectTypeInfo);
    		if(ptinfo.getNJGyearInvest()!=null){
    			YearInvestPlanInfo yearInvestPlanInfo =YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanInfo(new ObjectUuidPK(ptinfo.getNJGyearInvest().getId()));
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
			uiContext.put("UIName", "ProjectBudget2EditUI");
			uiContext.put("number",projectInfo.getNumber());
			uiContext.put("projectName", projectInfo.getName());
			uiContext.put("proInvestAmount", this.txtestimateCost);
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
			programmingEditUI.txtName.setText("项目概算");
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
        return com.kingdee.eas.port.pm.invest.ProjectBudget2Factory.getRemoteInstance();
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
        com.kingdee.eas.port.pm.invest.ProjectBudget2Info objectValue = new com.kingdee.eas.port.pm.invest.ProjectBudget2Info();
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