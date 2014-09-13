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
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.IMeasureUnit;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
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
        super.loadFields();
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
		filter.getFilterItems().add(new FilterItemInfo("NJGprojectType.name","基本建设",com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		this.prmtprojectName.setEntityViewInfo(view);
		
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
    	String oql = "";
    	if(editData.getId()==null)
    		oql= " select id where projectName.id = '"+ptinfo.getId().toString()+"'";
    	else
    		oql= " select id where projectName.id = '"+ptinfo.getId().toString()+"' and id<>'"+editData.getId().toString()+"'";
    	if(ProjectBudget2Factory.getRemoteInstance().exists(oql))
    	{
    		MsgBox.showWarning("当前项目已经有项目概算单据，请重新选择！");
    		prmtprojectName.setValue(null);SysUtil.abort();
    	}
    	if(ptinfo.getNJGprojectType()!=null){
    		ProjectTypeInfo projectTypeInfo =ProjectTypeFactory.getRemoteInstance().getProjectTypeInfo(new ObjectUuidPK(ptinfo.getNJGprojectType().getId()));
    		prmtprojectType.setValue(projectTypeInfo);
    		if(ptinfo.getNJGyearInvest()!=null){
    			YearInvestPlanInfo yearInvestPlanInfo =YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanInfo(new ObjectUuidPK(ptinfo.getNJGyearInvest().getId()));
    			if(yearInvestPlanInfo.getYear()!=null){
    				InvestYearInfo YearInfo=InvestYearFactory.getRemoteInstance().getInvestYearInfo(new ObjectUuidPK(yearInvestPlanInfo.getYear().getId()));
    				prmtyear.setValue(YearInfo);
    			}
    		
    		}
    	}
    	
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
	
	/**
	 * 项目信息及投资规划界面加载封装
	 */
	private void setPortProject(ChangeEvent e) throws Exception
	{
		if(editData!=null){
			kDScrollPane1.setViewportView(null);
			if(prmtprojectName.getValue()!=null){
				ProjectInfo projectInfo = (ProjectInfo)prmtprojectName.getValue();
				UIContext uiContext = new UIContext(this);
				uiContext.put("UIName", "ProjectBudget2EditUI");
				uiContext.put("proNumber",projectInfo.getNumber());
				uiContext.put("proname", projectInfo.getName());
				uiContext.put("SourceBillId",editData.getId().toString());
				BOSUuid yearID =((InvestYearInfo)prmtyear.getValue()).getId();
				InvestYearInfo  newYearInfo = InvestYearFactory.getRemoteInstance().getInvestYearInfo(new ObjectUuidPK(yearID));
				uiContext.put("year",newYearInfo);
				//加载投资规划编辑界面
				IProgramming iProgramming = ProgrammingFactory.getRemoteInstance();
		        String oql = "select id where projectNumber='"+projectInfo.getNumber()+"'" ;
		        oql = oql +" order by version desc ";
		        ProgrammingInfo pmInfo = iProgramming.getProgrammingCollection(oql).get(0);
				if(iProgramming.exists("where sourceBillId='"+editData.getId()+"'"))
				{
					ProgrammingInfo programming = iProgramming.getProgrammingInfo("where sourceBillId='"+editData.getId()+"'");
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
    
	private void verifySave(ChangeEvent e) throws EASBizException, BOSException{
		if(editData!=null)
		{
			boolean eidtBill = ProjectBudget2Factory.getRemoteInstance().exists("select id where id ='"+editData.getId()+"'");
			if(e!=null&&e.getSource()!=null)
			{
				KDTabbedPane kdtable = (KDTabbedPane)e.getSource();
				if(!kdtable.getSelectedComponent().getName().equals("kDPanel2"))
					return;
			}
			if(!eidtBill)
			{
				if(e==null)
				{
					MsgBox.showWarning("单据未保存，请先保存！");
					SysUtil.abort();
				}
				else 
				{
					KDTabbedPane kdtable = (KDTabbedPane)e.getSource();
					if(kdtable.getSelectedComponent().getName().equals("kDPanel2"))
					{
						MsgBox.showWarning("单据未保存，请先保存！");
						kDTabbedPane1.setSelectedComponent(kDPanel1);
						SysUtil.abort();
					}
					if(!kdtable.getSelectedComponent().getName().equals("kDPanel2"))
						return;
				}
			}
		}
	}
    
    
    /**
	 * 添加分录新增，插入，删除按钮点击事件
	 *  @author
	 * */
    protected void setEntryButtonAction() {
		ActionListener[] actions = kdtE1_detailPanel.getRemoveLinesButton().getActionListeners();
		if (actions != null && actions.length > 0)
			kdtE1_detailPanel.getRemoveLinesButton().removeActionListener(actions[0]);
	      	kdtE1_detailPanel.getRemoveLinesButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//分录删除按钮点击事件
						kdtE1.removeRow(kdtE1.getSelectManager().getActiveRowIndex());
						BigDecimal totalCount = new BigDecimal(UIRuleUtil.sum(kdtE1,"total"));
	                	 getProportion( totalCount);
					}
			});
	      	kdtE1_detailPanel.getInsertLineButton().removeActionListener(actions[0]);
	      	kdtE1_detailPanel.getInsertLineButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
			});
	      	kdtE1_detailPanel.getAddNewLineButton().removeActionListener(actions[0]);
	      	kdtE1_detailPanel.getAddNewLineButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						//分录新增按钮点击事件
					}
			});
		  //分录新增设置默认值
	      	kdtE1_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
                  public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
                          IObjectValue vo = event.getObjectValue();
                          vo.put("total",BigDecimal.ZERO);
                          vo.put("proportion",BigDecimal.ZERO);
                          
                          //设置默认值
                  }
                  public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
                	  IObjectValue vo = event.getObjectValue();
                  }
          });
		   //分录新增设置默认值,
	      	kdtE1_detailPanel.addInsertListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
                  public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
                          IObjectValue vo = event.getObjectValue();
                          vo.put("total",BigDecimal.ZERO);
                          vo.put("proportion",BigDecimal.ZERO);
                          //设置默认值
                  }
                  public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {

                  }
          });
	        //分录新增设置默认值,
	      	kdtE1_detailPanel.addRemoveListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
                  public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
                          IObjectValue vo = event.getObjectValue();
                          //设置默认值
                  }
                  public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
                	  BigDecimal totalCount = new BigDecimal(UIRuleUtil.sum(kdtE1,"total"));
                	  getProportion( totalCount);
                  }
          });
    }
    
    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
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
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		if(programmingEditUI!=null)
			programmingEditUI.actionSave_actionPerformed(e);
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
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
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
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
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
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
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
		
	}
	protected void detachListeners() {
		
	}
	protected KDTextField getNumberCtrl() {
		return null;
	}

}