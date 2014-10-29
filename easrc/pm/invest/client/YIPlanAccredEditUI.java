/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.rpc.RpcProxy;
import com.kingdee.bos.workflow.ActivityInstInfo;
import com.kingdee.bos.workflow.metas.AssignCollection;
import com.kingdee.bos.workflow.metas.AssignFactory;
import com.kingdee.bos.workflow.metas.AssignInfo;
import com.kingdee.bos.workflow.monitor.IWfUtil;
import com.kingdee.bos.workflow.monitor.client.WfUtils;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.hr.emp.client.EmployeeMultiF7PromptBox;
import com.kingdee.eas.port.pm.base.ReviewerE1Collection;
import com.kingdee.eas.port.pm.base.ReviewerE1Factory;
import com.kingdee.eas.port.pm.base.ReviewerE1Info;
import com.kingdee.eas.port.pm.invest.AccredTypeEnum;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1E2Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredFactory;
import com.kingdee.eas.port.pm.invest.YIPlanAccredInfo;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.helper.ClientVerifyXRHelper;
import com.kingdee.eas.xr.helper.DateXRHelper;
import com.kingdee.eas.xr.helper.PersonXRHelper;
import com.kingdee.eas.xr.helper.WorkflowXRHelper;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class YIPlanAccredEditUI extends AbstractYIPlanAccredEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(YIPlanAccredEditUI.class);
    
    /**
     * output class constructor
     */
    public YIPlanAccredEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
    	detachListeners();
        super.loadFields();
        attachListeners();
        kdtE1.getSelectManager().select(0, 3);
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    /**
     * output onLoad method
     */
	public void onLoad() throws Exception {
		this.setPreferredSize(new Dimension(1000,555));
		super.onLoad();
		this.actionAddNew.setVisible(false);
		contDescription.setVisible(false);
		txtDescription.setVisible(false);
		contBizDate.setVisible(false);
		pkBizDate.setVisible(false);
		contBizStatus.setVisible(false);
		comboBizStatus.setVisible(false);
		contaccredPerson.setVisible(false);
		prmtaccredPerson.setVisible(false);
		kdtE1.getColumn("seq").getStyleAttributes().setHided(true);
		kdtE2.getColumn("seq").getStyleAttributes().setHided(true);
		accredType.setEnabled(false);
		//当评审时，选定评审表类型
		if(OprtState.ADDNEW.equals(getOprtState()))
		{
			if(((this.editData.getDescription()!=null)?this.editData.getDescription():"").equals("初审"))
			{
				accredType.setSelectedItem(AccredTypeEnum.trial);
			}
			else if(((this.editData.getDescription()!=null)?this.editData.getDescription():"").equals("评审"))
			{
				accredType.setSelectedItem(AccredTypeEnum.accred);
			}
			else
			{
				accredType.setSelectedItem(AccredTypeEnum.approve);
			}
		}
		pkaccredDate.setEnabled(false);
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		Set set = new HashSet();
		ReviewerE1Collection revE1Coll = ReviewerE1Factory.getRemoteInstance().getReviewerE1Collection();
		for (int i = 0; i < revE1Coll.size(); i++) {
			ReviewerE1Info e1Info = revE1Coll.get(i);
			set.add(e1Info.getJudges().getId().toString());
		}
		this.kDContainer1.getContentPane().add(this.kdtE1,BorderLayout.CENTER);
		this.kDContainer2.getContentPane().add(this.kdtE2,BorderLayout.CENTER);
		initProWorkButton(this.kDContainer1, false);
		initWorkButton(this.kDContainer2, false);
		this.kDContainer1.setTitle("投资信息");
		this.kDContainer2.setTitle("评审信息");
	
		KDComboBox kdtE1_accredResu_ComboBox = new KDComboBox();
        kdtE1_accredResu_ComboBox.setName("kdtE1_accredResu_ComboBox");
        kdtE1_accredResu_ComboBox.setVisible(true);
        List list = new ArrayList();
        
        KDComboBox kdtE2_accreConclu_ComboBox = new KDComboBox();
        kdtE2_accreConclu_ComboBox.setName("kdtE2_accreConclu_ComboBox");
        kdtE2_accreConclu_ComboBox.setVisible(true);
        List listE2 = new ArrayList();
        
		if(accredType.getSelectedItem().equals(AccredTypeEnum.trial)){
			kDContainer2.getContentPane().removeAll();
			kDContainer2.getContentPane().add(kDLabelContainer1, BorderLayout.CENTER);
	        list.add(ObjectStateEnum.throughAudit);
	        list.add(ObjectStateEnum.complement);
	        list.add(ObjectStateEnum.veto);
		}
		//分录投资信息”评审结果“，分录评审信息”评审结论“枚举值设为“评审通过”，“补充完善”，“否决”
		else if(accredType.getSelectedItem().equals(AccredTypeEnum.accred))  {
			kDContainer2.getContentPane().removeAll();
			kDContainer2.getContentPane().add(kdtE2_detailPanel, BorderLayout.CENTER);
			this.kDContainer2.getContentPane().add(this.kdtE2,BorderLayout.CENTER);
			kdtE2.getColumn("seq").getStyleAttributes().setHided(true);
	        list.add(ObjectStateEnum.accredit);
	        list.add(ObjectStateEnum.complement);
	        list.add(ObjectStateEnum.veto);
	        listE2.add(ObjectStateEnum.accredit);
	        listE2.add(ObjectStateEnum.complement);
	        listE2.add(ObjectStateEnum.veto);
		}
		else {
			kDContainer2.getContentPane().removeAll();
			kDContainer2.getContentPane().add(kdtE2_detailPanel, BorderLayout.CENTER);
			this.kDContainer2.getContentPane().add(this.kdtE2,BorderLayout.CENTER);
			kdtE2.getColumn("seq").getStyleAttributes().setHided(true);
	        list.add(ObjectStateEnum.approval);
	        list.add(ObjectStateEnum.complement);
	        list.add(ObjectStateEnum.veto);
	        listE2.add(ObjectStateEnum.approval);
	        listE2.add(ObjectStateEnum.complement);
	        listE2.add(ObjectStateEnum.veto);
		}
		    kdtE1_accredResu_ComboBox.addItems(list.toArray());
	        KDTDefaultCellEditor kdtE1_accredResu_CellEditor = new KDTDefaultCellEditor(kdtE1_accredResu_ComboBox);
	        this.kdtE1.getColumn("accredResu").setEditor(kdtE1_accredResu_CellEditor);
	        
	        kdtE2_accreConclu_ComboBox.addItems(listE2.toArray());
	        KDTDefaultCellEditor kdtE2_accreConclu_CellEditor = new KDTDefaultCellEditor(kdtE2_accreConclu_ComboBox);
	        this.kdtE2.getColumn("accreConclu").setEditor(kdtE2_accreConclu_CellEditor);
	    actionSave.setEnabled(true);
	    btnSave.setEnabled(true);
	    YIPlanAccredFactory.getRemoteInstance().save(editData);
	}
	/**
	 * 分录“accredResu”列值改变时“projectConclude”列值默认带出“同意”
	 */
	protected void kdtE1_editStopped(KDTEditEvent e) throws Exception {
		super.kdtE1_editStopped(e);
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		String key = kdtE1.getColumnKey(colIndex);
		if(this.kdtE1.getCell(rowIndex, "accredResu").getValue()!=null)
		{
			if(key.equals("accredResu"))
			{
				if(UIRuleUtil.getObject(this.kdtE1.getCell(rowIndex, "accredResu").getValue()).
					equals(ObjectStateEnum.throughAudit)||UIRuleUtil.getObject(this.kdtE1.getCell(rowIndex, "accredResu").getValue()).
					equals(ObjectStateEnum.accredit)||UIRuleUtil.getObject(this.kdtE1.getCell(rowIndex, "accredResu").getValue()).
					equals(ObjectStateEnum.approval)){
					this.kdtE1.getCell(rowIndex,"projectConclude").setValue("同意");
		        }else{
		        	this.kdtE1.getCell(rowIndex,"projectConclude").setValue("");
		        }
			}
		}
	}
	protected void kdtE2_editStopped(KDTEditEvent e) throws Exception {
		super.kdtE2_editStopped(e);
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		String key = kdtE2.getColumnKey(colIndex);
		if(this.kdtE2.getCell(rowIndex, "accreConclu").getValue()!=null)
		{
			if(key.equals("accreConclu"))
			{
				if(UIRuleUtil.getObject(this.kdtE2.getCell(rowIndex, "accreConclu").getValue()).
						equals(ObjectStateEnum.throughAudit)||UIRuleUtil.getObject(this.kdtE2.getCell(rowIndex, "accreConclu").getValue()).
						equals(ObjectStateEnum.accredit)||UIRuleUtil.getObject(this.kdtE2.getCell(rowIndex, "accreConclu").getValue()).
						equals(ObjectStateEnum.approval)){
		        	this.kdtE2.getCell(rowIndex,"opinion").setValue("同意");
		        }else{
		        	this.kdtE2.getCell(rowIndex,"opinion").setValue("");
		        }
				this.kdtE2.getCell(rowIndex,"aduitTime").setValue(DateXRHelper.getServerDate());
			}
		}
		if(this.kdtE2.getCell(rowIndex, "accredPerson").getValue()!=null)
		{
			if(key.equals("accredPerson"))
			{
				PersonInfo prinfo = (PersonInfo) this.kdtE2.getCell(rowIndex, "accredPerson").getValue();
				AdminOrgUnitCollection orgColl = PersonXRHelper.getDepartmentByUserCollection(prinfo);
				AdminOrgUnitInfo adminInfo = orgColl.get(0);
				this.kdtE2.getCell(rowIndex,"accredDpart").setValue(adminInfo);
				setLockAuditTable();
			}
		}
	}

	/**
	 * 校验分录列"projectConclude"值不能为空
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		AccredTypeEnum auditType = (AccredTypeEnum)accredType.getSelectedItem();
		if(AccredTypeEnum.trial.equals(auditType)){
			for (int i = 0; i < this.kdtE1.getRowCount(); i++) 
			{
				if(UIRuleUtil.isNull(this.kdtE1.getCell(i, "projectConclude").getValue()))
				{
					MsgBox.showWarning("初审阶段，年度计划第{"+(i+1)+"}行项目,项目结论不能为空！");
					SysUtil.abort();
				}
			}
			ClientVerifyXRHelper.verifyNull(this, accredInformation, "评审信息");
		}
		if(editData.getId()!=null && (AccredTypeEnum.accred.equals(auditType)||AccredTypeEnum.approve.equals(auditType))){
			for (int i = 0; i < editData.getE1().size(); i++) {
				YIPlanAccredE1Info info = editData.getE1().get(i);
				if (info.getE2().size() < 1) {
					MsgBox.showWarning("第{"+(i+1)+"}行项目："+info.getProjectName().getProjectName()+",评审人员不能为空！");
					SysUtil.abort();
				}
				for (int j = 0; j < info.getE2().size(); j++) {
					if(info.getE2().get(j).getAccredPerson()==null){
						MsgBox.showWarning("第{"+(i+1)+"}行项目："+info.getProjectName().getProjectName()+",评审人员不能为空！");
						SysUtil.abort();
					}
				}
			}
			if(WorkflowXRHelper.isRunningWorkflow(editData.getId().toString())){
				String userid = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
				PersonInfo person = SysContext.getSysContext().getCurrentUserInfo().getPerson();
				if(person==null)
					return;
				if(editData.getCreator().getId().toString().equals(userid)){
					for (int i = 0; i < this.kdtE1.getRowCount(); i++) 
					{
						if(UIRuleUtil.isNull(this.kdtE1.getCell(i, "projectConclude").getValue()))
						{
							MsgBox.showWarning("投资信息第{"+(i+1)+"}行项目,项目结论不能为空！");
							SysUtil.abort();
						}
					}
				}
		    	for (int i = 0; i < this.kdtE2.getRowCount(); i++)
		    	{
		    		IRow row = this.kdtE2.getRow(i);
		    		if(UIRuleUtil.isNull(row.getCell("accredPerson").getValue()))
		    			continue;
					String kdpersonId = ((PersonInfo)row.getCell("accredPerson").getValue()).getId().toString();
					if(kdpersonId.equals(person.getId().toString())){
						if(row.getCell("accreConclu").getValue()==null){
							MsgBox.showWarning("评审信息明细,评审结论不能为空！");
							SysUtil.abort();
						}
					}
				}
			}
		}
	}
	
	/**
	 * 评审中 有权限直接修改项目
	 * 在直接修改界面保存时，将修改前版本存入历史库
	 * */
	protected void btnEditProject_actionPerformed(ActionEvent e) throws Exception {
		super.btnEditProject_actionPerformed(e);
		UIContext context = new UIContext(this);
		int rowIndex = kdtE1.getSelectManager().getActiveRowIndex();
		if(kdtE1.getRow(rowIndex)!=null){
			YearInvestPlanInfo info = (YearInvestPlanInfo)kdtE1.getRow(rowIndex).getCell("projectName").getValue();
			info = YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanInfo(new ObjectUuidPK(info.getId()));
			ProgrammingInfo programmingInfo = getProgrammingInfo(info);
			context.put("programmingInfo", programmingInfo);
			context.put("projectInfo-edit", info);
			context.put("ID", info.getId());
			UIFactory.createUIFactory().create(YearInvestPlanEditUI.class.getName(), context, null,OprtState.EDIT).show();
		}
	}
	/**
	 * 获取直接修改项目的投资规划
	 * */
	static ProgrammingInfo getProgrammingInfo(YearInvestPlanInfo investInfo){
		ProgrammingInfo info = new ProgrammingInfo();
		try {
			ProgrammingCollection coll = ProgrammingFactory.getRemoteInstance().getProgrammingCollection("where sourcebillid='"+investInfo.getId()+"'");
			if(coll.size()>0){
				info = coll.get(0);
				info = ProgrammingFactory.getRemoteInstance().getProgrammingInfo(new ObjectUuidPK(info.getId()),getSelector());
				info.setId(BOSUuid.create(info.getBOSType()));
				for(int i=0;i<info.getEntries().size();i++){
					ProgrammingEntryInfo entry = info.getEntries().get(i);
					for(int j=0;j<entry.getCostEntries().size();j++){
						entry.getCostEntries().get(j).setId(null);
					}
					info.getEntries().get(i).setId(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	protected void initProWorkButton(KDContainer container, boolean flse) {
		KDWorkButton btnApprove = new KDWorkButton();
		KDWorkButton btnperson = new KDWorkButton();
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
		btnAddRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		btnInsertRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		btnDeleteRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		
		btnEditProject.setSize(new Dimension(140, 19));
		btnEditProject.setIcon(EASResource.getIcon("imgTbtn_edit"));
		container.addButton(btnEditProject);
		
		btnperson.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		btnApprove.setEnabled((OprtState.EDIT.equals(getOprtState()))||OprtState.ADDNEW.equals(getOprtState()) ? true : false);
		
		btnperson.setIcon(EASResource.getIcon("imgTbtn_addline"));
		container.addButton(btnperson);
		btnperson.setText("评审人员");
		btnperson.setSize(new Dimension(140, 19));
		btnperson.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					EntityViewInfo evInfo = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					Set set = new HashSet();
					ReviewerE1Collection revE1Coll = ReviewerE1Factory.getRemoteInstance().getReviewerE1Collection();
					for (int i = 0; i < revE1Coll.size(); i++) {
						ReviewerE1Info e1Info = revE1Coll.get(i);
						set.add(e1Info.getJudges().getId().toString());
					}
					filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
//					evInfo.setFilter(filter);
					EmployeeMultiF7PromptBox person = new EmployeeMultiF7PromptBox();
					person.setIsSingleSelect(false);
					person.showNoPositionPerson(false);
					person.setIsShowAllAdmin(true);
					person.show();
					if(person.getData() instanceof Object[]&&((Object[]) person.getData()).length>0){
						Object[] obj = (Object[]) person.getData();
						for (int m = 0; m < kdtE1.getRowCount(); m++) 
						{
							setKdtE2Person(obj, m);
						}
						storeFields();
						loadFields();
						kdtE1.getSelectManager().select(0, 3);
					}
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		if(accredType.getSelectedItem().equals(AccredTypeEnum.trial)){
			btnperson.setVisible(false);
		}
		
		btnApprove.setIcon(EASResource.getIcon("imgTbtn_addline"));
		container.addButton(btnApprove);
		btnApprove.setText("批量批准");
		btnApprove.setSize(new Dimension(140, 19));
		btnApprove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				boolean flag = MsgBox.isYes(MsgBox.showConfirm2( "是否确认批量批准"));
				if(flag)
				for (int i = 0; i < kdtE1.getRowCount(); i++) {
					kdtE1.getCell(i, "accredResu").setValue(ObjectStateEnum.approval);
					kdtE1.getCell(i,"projectConclude").setValue("同意");
				}
			}
		});
		
		if(!accredType.getSelectedItem().equals(AccredTypeEnum.approve)){
			btnApprove.setVisible(false);
		}
		
		
		btnAddRowinfo.setIcon(EASResource.getIcon("imgTbtn_addline"));
		container.addButton(btnAddRowinfo);
		btnAddRowinfo.setText("新增分录");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		btnAddRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE1_detailPanel.actionAddnewLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnInsertRowinfo.setIcon(EASResource.getIcon("imgTbtn_insert"));
		container.addButton(btnInsertRowinfo);
		btnInsertRowinfo.setText("插入分录");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		btnInsertRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE1_detailPanel.actionInsertLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnDeleteRowinfo.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		container.addButton(btnDeleteRowinfo);
		btnDeleteRowinfo.setText("删除分录");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		btnDeleteRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE1_detailPanel.actionRemoveLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	/**
	 * 选到评审人员，加入到审批工作流
	 */
	void addAuditPersonInWF(PersonInfo personInfo){
		try
		{
			HashSet set = new HashSet();
			set.add("1");
			set.add("2");
			set.add("32");
			EntityViewInfo evInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			evInfo.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("BIZOBJID",editData.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("STATE",set,CompareType.INCLUDE));
			AssignCollection  coll = AssignFactory.getRemoteInstance().getCollection(evInfo);
			String actInstId = "";
			if(coll.size()>0){
				AssignInfo info = coll.get(0); 
				actInstId = info.getActInstID().toString();
			}
			ActivityInstInfo  currentActivityInstInfo = EnactmentServiceFactory.createRemoteEnactService().getActivityInstByActInstId(actInstId);
			String personId = personInfo.getId().toString();
			if(WfUtils.checkPersonHasUser(personId))
			{
				String personIdArray[] = {personId};
				IWfUtil util = (IWfUtil)RpcProxy.wrapRequired(com.kingdee.bos.workflow.monitor.IWfUtil.class, com.kingdee.bos.workflow.monitor.WfUtil.class.getName());
				util.addAssignToActivity(actInstId, personIdArray, "");
			} 
		}catch(Exception ex){   
		}
	}
	/**
	 * 第一分录评审人按钮，使第二分录选到评审人员信息
	 */
	private void setKdtE2Person(Object obj[],int rowIndex)
	{
		YIPlanAccredE1Info enrtry1= (YIPlanAccredE1Info) kdtE1.getRow(rowIndex).getUserObject();
		List<String> list = new ArrayList<String>();
		
		for (int k = 0; k < enrtry1.getE2().size(); k++) {
			if(enrtry1.getE2().get(k).getAccredPerson()!=null)
				list.add(enrtry1.getE2().get(k).getAccredPerson().getId().toString());
		}
		
		for (int h = 0; h < obj.length; h++) 
		{	
			PersonInfo personInfo=((PersonInfo)obj[h]);
			if(list.contains(personInfo.getId().toString())){continue;}
			AdminOrgUnitInfo info =PersonXRHelper.getPosiMemByDeptUser(personInfo);
			YIPlanAccredE1E2Info e2 = new YIPlanAccredE1E2Info();
			e2.setAccredPerson(personInfo);
			e2.setAccredDpart(info);
			
			try {
				if(editData.getId()!=null && WorkflowXRHelper.isRunningWorkflow(editData.getId().toString())){
					PersonInfo curPerson = SysContext.getSysContext().getCurrentUserInfo().getPerson();
					if(curPerson!=null)
						e2.setRemark("由"+curPerson.getName()+"加签----"+DateXRHelper.DateToTimeString(DateXRHelper.getServerDate()));
				}
				if(WfUtils.checkPersonHasUser(personInfo.getId().toString()))
					enrtry1.getE2().add(e2);
				else
				{
					MsgBox.showWarning(EASResource.getString("com.kingdee.bos.workflow.monitor.client.WorkFlowMonitorImageDescriptionResource", "msgPersonHasNoUser"));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        	
        	try {
				if(editData.getId()!=null && WorkflowXRHelper.isRunningWorkflow(editData.getId().toString())){
					addAuditPersonInWF(personInfo);
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
	     }
	}
	
	protected void initWorkButton(KDContainer container, boolean flse) {
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		btnAddRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		btnInsertRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		btnDeleteRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);

		btnAddRowinfo.setIcon(EASResource.getIcon("imgTbtn_addline"));
		container.addButton(btnAddRowinfo);
		btnAddRowinfo.setText("新增分录");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		btnAddRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE2_detailPanel.actionAddnewLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnInsertRowinfo.setIcon(EASResource.getIcon("imgTbtn_insert"));
		container.addButton(btnInsertRowinfo);
		btnInsertRowinfo.setText("插入分录");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		btnInsertRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE2_detailPanel.actionInsertLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDeleteRowinfo.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		container.addButton(btnDeleteRowinfo);
		btnDeleteRowinfo.setText("删除分录");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		btnDeleteRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE2_detailPanel.actionRemoveLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		if(accredType.getSelectedItem().equals(AccredTypeEnum.trial)){
			btnAddRowinfo.setVisible(false);
			btnInsertRowinfo.setVisible(false);
			btnDeleteRowinfo.setVisible(false);
		}
		/**新增时，清除审批结果*/
		ActionListener[] actions = kdtE2_detailPanel.getRemoveLinesButton().getActionListeners();
		if (actions != null && actions.length > 0)
			kdtE2_detailPanel.getRemoveLinesButton().removeActionListener(actions[0]);
		    kdtE2_detailPanel.getRemoveLinesButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
			});
		    kdtE2_detailPanel.getInsertLineButton().removeActionListener(actions[0]);
		    kdtE2_detailPanel.getInsertLineButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
			});
		    btnAddRowinfo.removeActionListener(actions[0]);
		    btnAddRowinfo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
			});
		  //分录新增设置默认值
		    kdtE2_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
                  public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
                          IObjectValue vo = event.getObjectValue();
                          vo.put("accreConclu",null);
                          //设置默认值
                  }
                  public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
                	  IObjectValue vo = event.getObjectValue();
                	  vo.put("accreConclu",null);
                  }
          });
		   //分录新增设置默认值,
		    kdtE2_detailPanel.addInsertListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
                  public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
                          //设置默认值
                  }
                  public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
                  }
          });
		
	}
	
	public void onShow() throws Exception {
		super.onShow();
		kdtE1.getColumn("company").getStyleAttributes().setLocked(true);
		kdtE1.getColumn("projectType").getStyleAttributes().setLocked(true);
		kdtE1.getColumn("projectName").getStyleAttributes().setLocked(true);
		kdtE1.getColumn("amount").getStyleAttributes().setLocked(true);
		kdtE1.getColumn("contentSReq").getStyleAttributes().setLocked(true);
		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
    	if(!userInfo.getId().equals(editData.getCreator().getId())){
    		kdtE1.getColumn("accredResu").getStyleAttributes().setLocked(true);
    		kdtE1.getColumn("projectConclude").getStyleAttributes().setLocked(true);
    	}
    	setLockAuditTable();
	}
	/**
     * output accredType_actionPerformed method
     */
	protected void accredType_actionPerformed(ActionEvent e) throws Exception {
		super.accredType_actionPerformed(e);
		
	}
    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
        
        
    }
    protected void prmtaccredPerson_dataChanged(DataChangeEvent e)throws Exception {
    	super.prmtaccredPerson_dataChanged(e);

    }
    /**
     * 锁定非本人评审信息
     */
    void setLockAuditTable(){
    	PersonInfo personInfo = SysContext.getSysContext().getCurrentUserInfo().getPerson();
    	if(personInfo==null||personInfo.getId()==null)
    		return;
    	String personId = personInfo.getId().toString();
    	for (int i = 0; i < this.kdtE2.getRowCount(); i++)
    	{
    		IRow row = this.kdtE2.getRow(i);
    		if(UIRuleUtil.isNull(row.getCell("accredPerson").getValue()))
    			continue;
			String kdpersonId = ((PersonInfo)row.getCell("accredPerson").getValue()).getId().toString();
			if(!kdpersonId.equals(personId))
				row.getStyleAttributes().setLocked(true);
			else
				row.getStyleAttributes().setLocked(false);
		}
    }
    protected void kdtE1_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kdtE1_tableClicked(e);
    	setLockAuditTable();
    	if(e.getClickCount()==2){
			UIContext context = new UIContext(this);
			int rowIndex = kdtE1.getSelectManager().getActiveRowIndex();
			if(kdtE1.getRow(rowIndex)!=null){
				YearInvestPlanInfo info = (YearInvestPlanInfo)kdtE1.getRow(rowIndex).getCell("projectName").getValue();
				context.put("ID", info.getId());
				UIFactory.createUIFactory().create(YearInvestPlanEditUI.class.getName(), context, null,OprtState.VIEW).show();
			}
		}
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
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	isControl = false;
        super.actionSubmit_actionPerformed(e);
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
//        super.actionPrint_actionPerformed(e);
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
    	isControl = false;
        super.actionEdit_actionPerformed(e);
        this.kDContainer1.removeAllButton();
        initProWorkButton(this.kDContainer1, false);
        this.kDContainer2.removeAllButton();
        initWorkButton(this.kDContainer2, false);
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
    	String id = this.editData.getId().toString();
    	YIPlanAccredInfo Info =YIPlanAccredFactory.getRemoteInstance().getYIPlanAccredInfo(new ObjectUuidPK(id));
    	AccredTypeEnum accredType = Info.getAccredType();
//    	if(accredType.equals(AccredTypeEnum.accred)||accredType.equals(AccredTypeEnum.trial)){
//    		throw new EASBizException(new NumericExceptionSubItem("100",accredType.getAlias()+"不能反审核！"));
//    	}
    	super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invest.YIPlanAccredFactory.getRemoteInstance();
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
        com.kingdee.eas.port.pm.invest.YIPlanAccredInfo objectValue = new com.kingdee.eas.port.pm.invest.YIPlanAccredInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setAccredDate(new Date());
        return objectValue;
    }
	protected void attachListeners() {
		addDataChangeListener(this.prmtaccredPerson);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtaccredPerson);
	}
	protected KDTextField getNumberCtrl() {
		return null;
	}
public static SelectorItemCollection getSelector() {
		
		SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("projectNumber"));
		sic.add(new SelectorItemInfo("projectName"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("buildArea"));
		sic.add(new SelectorItemInfo("soldArea"));
		sic.add(new SelectorItemInfo("aimCost.*"));
		sic.add(new SelectorItemInfo("aimCost.measureStage.id"));
		
		sic.add(new SelectorItemInfo("project.name"));
		sic.add(new SelectorItemInfo("entries.longNumber"));
		sic.add(new SelectorItemInfo("entries.name"));
		sic.add(new SelectorItemInfo("entries.*"));
		sic.add(new SelectorItemInfo("entries.amount"));
		sic.add(new SelectorItemInfo("entries.controlAmount"));
		sic.add(new SelectorItemInfo("entries.parent.*"));
		sic.add(new SelectorItemInfo("entries.economyEntries.*"));
		sic.add(new SelectorItemInfo("entries.economyEntries.paymentType.*"));
		
		sic.add(new SelectorItemInfo("entries.costEntries.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costAccount.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costAccount.curProject.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costEntries.id"));
		sic.add(new SelectorItemInfo("entries.costEntries.project"));
		sic.add(new SelectorItemInfo("entries.costEntries.number"));
		sic.add(new SelectorItemInfo("entries.costEntries.investYear.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.goalCost"));
		sic.add(new SelectorItemInfo("entries.costEntries.assigned"));
		sic.add(new SelectorItemInfo("entries.costEntries.contractAssign"));
		sic.add(new SelectorItemInfo("entries.costEntries.assigning"));
		sic.add(new SelectorItemInfo("entries.costEntries.proportion"));
		sic.add(new SelectorItemInfo("entries.costEntries.description"));
		sic.add(new SelectorItemInfo("entries.costEntries.contract.*"));
		
		//取预估金额 20120420
		sic.add(new SelectorItemInfo("entries.estimateAmount"));
		sic.add(new SelectorItemInfo("entries.balance"));
		sic.add(new SelectorItemInfo("entries.controlBalance"));
		sic.add(new SelectorItemInfo("entries.signUpAmount"));
		sic.add(new SelectorItemInfo("entries.changeAmount"));
		sic.add(new SelectorItemInfo("entries.settleAmount"));
		sic.add(new SelectorItemInfo("entries.citeVersion"));
		sic.add(new SelectorItemInfo("entries.isCiting"));
		sic.add(new SelectorItemInfo("entries.attachment"));
		sic.add(new SelectorItemInfo("entries.description"));
		sic.add(new SelectorItemInfo("entries.id"));
		sic.add(new SelectorItemInfo("entries.number"));
		sic.add(new SelectorItemInfo("entries.level"));
		sic.add(new SelectorItemInfo("entries.parent.longNumber"));
		sic.add(new SelectorItemInfo("entries.sortNumber"));
		sic.add(new SelectorItemInfo("entries.displayName"));
		sic.add(new SelectorItemInfo("entries.workcontent"));
		sic.add(new SelectorItemInfo("entries.supMaterial"));
		sic.add(new SelectorItemInfo("entries.inviteWay"));
		sic.add(new SelectorItemInfo("entries.inviteOrg.*"));
		sic.add(new SelectorItemInfo("entries.buildPerSquare"));
		sic.add(new SelectorItemInfo("entries.soldPerSquare"));
		sic.add(new SelectorItemInfo("version"));
		sic.add(new SelectorItemInfo("versionGroup"));
	    sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		
		sic.add(new SelectorItemInfo("entries.quantities"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.unit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.unit.id"));
			sic.add(new SelectorItemInfo("entries.unit.name"));
        	sic.add(new SelectorItemInfo("entries.unit.number"));
		}
    	sic.add(new SelectorItemInfo("entries.price"));
		
		sic.add(new SelectorItemInfo("entries.contractType.*"));
		sic.add(new SelectorItemInfo("entries.programming.*"));
		
		sic.add(new SelectorItemInfo("isLatest"));
		sic.add(new SelectorItemInfo("entries.isInvite"));
		
		sic.add(new SelectorItemInfo("compareEntry.*"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("sourceBillId"));
		
		return sic;
	}   
}