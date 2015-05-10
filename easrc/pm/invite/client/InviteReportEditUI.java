/**
 * output package name
 */
package com.kingdee.eas.port.pm.invite.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kds.expans.model.data.ParameterImpl;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.client.f7.AdminF7;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.hr.emp.client.EmployeeMultiF7PromptBox;
import com.kingdee.eas.port.equipment.uitl.ToolHelp;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo;
import com.kingdee.eas.port.markesupplier.subill.client.MarketSupplierStockEditUI;
import com.kingdee.eas.port.pm.base.EvaluationIndicatorsFactory;
import com.kingdee.eas.port.pm.base.EvaluationIndicatorsInfo;
import com.kingdee.eas.port.pm.base.EvaluationIndicatorsTreeFactory;
import com.kingdee.eas.port.pm.base.EvaluationIndicatorsTreeInfo;
import com.kingdee.eas.port.pm.base.EvaluationTemplateEntryInfo;
import com.kingdee.eas.port.pm.base.EvaluationTemplateFactory;
import com.kingdee.eas.port.pm.base.EvaluationTemplateInfo;
import com.kingdee.eas.port.pm.base.IEvaluationIndicators;
import com.kingdee.eas.port.pm.base.IEvaluationIndicatorsTree;
import com.kingdee.eas.port.pm.base.InviteTypeFactory;
import com.kingdee.eas.port.pm.base.InviteTypeInfo;
import com.kingdee.eas.port.pm.invest.IProjectStartRequest;
import com.kingdee.eas.port.pm.invest.ProjectStartRequestCollection;
import com.kingdee.eas.port.pm.invest.ProjectStartRequestFactory;
import com.kingdee.eas.port.pm.invest.client.ProjectStartRequestEditUI;
import com.kingdee.eas.port.pm.invite.InvitePlanInfo;
import com.kingdee.eas.port.pm.invite.WinInviteReportInfo;
import com.kingdee.eas.port.pm.invite.judgeSolution;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.rpts.ctrlreport.client.ExtReportRunUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.xr.helper.PersonXRHelper;
import com.kingdee.eas.xr.helper.SysPlatformXRHelper;

/**
 * output class name
 */
public class InviteReportEditUI extends AbstractInviteReportEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InviteReportEditUI.class);

	/**
	 * output class constructor
	 */
	public InviteReportEditUI() throws Exception {
		super();
	}

	public KDTable getkdtEntry5()
	{
		return this.kdtEntry5;
	}
	
	public void onLoad() throws Exception {
		txtrmhigh.setRequired(true);
		txtrmlow.setRequired(true);
		txtreduHigh.setRequired(true);
		txtreduLow.setRequired(true);
		txtbusinessScore.setRequired(true);
		txttechScore.setRequired(true);
		this.txtreportName.setRequired(true);
		this.prmtproName.setRequired(true);
		this.prmtuseOrg.setRequired(true);
		this.prmtinvitePlan.setRequired(true);
		this.prmtdevOrg.setEnabled(false);
		this.prmtinviteType.setRequired(true);
		this.prmtvalidTemplate.setRequired(true);
		this.kDContainer4.setVisible(false);
		this.contprojectNumber.setVisible(false);
		this.contdevOrg.setVisible(false);
		this.contproSite.setVisible(false);
		super.onLoad();
		
		if(OprtState.ADDNEW.equals(getOprtState())){
    		EvaluationTemplateInfo evaInfo = EvaluationTemplateFactory.getRemoteInstance().getEvaluationTemplateInfo(new ObjectUuidPK("6vYAAAAk6WljUt3X"));
    		prmtvalidTemplate.setValue(evaInfo);
    		EvaluationTemplateInfo evaInfo1 = EvaluationTemplateFactory.getRemoteInstance().getEvaluationTemplateInfo(new ObjectUuidPK("6vYAAAAnUW1jUt3X"));
    		prmtevaTemplate.setValue(evaInfo1);
    		txtrmhigh.setText("0");
			txtrmlow.setText("0");
			txtreduHigh.setText("0");
			txtreduLow.setText("0");
			txtbusinessScore.setText("0");
			txttechScore.setText("0"); 
			txtbusinessScore.setText("70");
			txttechScore.setText("30");
    	}
		this.btnCopyLine.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.contBizDate.setVisible(false);
		this.contCU.setVisible(false);
		this.contBizStatus.setVisible(false);
		this.contDescription.setVisible(false);
		this.prmtinvitePlan.setVisible(false);
		this.continvitePlan.setVisible(false);
		this.kDContainer6.getContentPane().add(kdtE6, BorderLayout.CENTER);
		this.kDContainer7.getContentPane().add(kdtE7, BorderLayout.CENTER);
		this.kdtE7.getColumn("weight").getStyleAttributes().setHided(true);
		this.kDButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (prmtproName.getValue() == null) {
					MsgBox.showWarning("请先选择项目名称!");
				} else {
					UIContext context = new UIContext(this);
					ProjectInfo info = (ProjectInfo) prmtproName.getValue();
					if(info.getNJGyearInvest()!=null)
					{
						try {
							IProjectStartRequest iProjectStartRequest = ProjectStartRequestFactory.getRemoteInstance();
							ProjectStartRequestCollection reqColl = iProjectStartRequest.getProjectStartRequestCollection("where projectName.id='"+info.getId()+"'");
							if(reqColl.size()>0){
								context.put("ID", reqColl.get(0).getId());
							}else{
								MsgBox.showWarning("该项目没有进行启动申请！");SysUtil.abort();
							}
						} catch (BOSException e2) {
							e2.printStackTrace();
						}
					}
					else
					{
						MsgBox.showWarning("没有对应的年度投资计划！");SysUtil.abort();
					}
					try {
						UIFactory.createUIFactory().create(ProjectStartRequestEditUI.class.getName(), context, null,OprtState.VIEW).show();
					} catch (UIException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		 EntityViewInfo view = new EntityViewInfo();
		 FilterInfo filInfo = new FilterInfo();
		 filInfo.getFilterItems().add(new
		 FilterItemInfo("status","4",CompareType.EQUALS));
		 view.setFilter(filInfo);
		 
		 this.prmtinvitePlan.setEntityViewInfo(view);
		 
		// 重构分录专家类别
		final KDBizPromptBox kdtE5_judgeType_PromptBox = new KDBizPromptBox();
		kdtE5_judgeType_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.invite.app.F7JudgesTreeQuery");
		kdtE5_judgeType_PromptBox.setVisible(true);
		kdtE5_judgeType_PromptBox.setEditable(true);
		kdtE5_judgeType_PromptBox.setDisplayFormat("$number$");
		kdtE5_judgeType_PromptBox.setEditFormat("$number$");
		kdtE5_judgeType_PromptBox.setCommitFormat("$number$");
		KDTDefaultCellEditor kdtE5_judgeType_CellEditor = new KDTDefaultCellEditor(kdtE5_judgeType_PromptBox);
		this.kdtEntry5.getColumn("judgeType").setEditor(kdtE5_judgeType_CellEditor);
		ObjectValueRender kdtE5_judgeType_OVR = new ObjectValueRender();
		kdtE5_judgeType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtEntry5.getColumn("judgeType").setRenderer(kdtE5_judgeType_OVR);

		final KDBizPromptBox kdtEntry4_name_PromptBox = new KDBizPromptBox();
        kdtEntry4_name_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.invest.investplan.app.ProgrammingCostEntryQuery");
        kdtEntry4_name_PromptBox.setVisible(true);
        kdtEntry4_name_PromptBox.setEditable(true);
        kdtEntry4_name_PromptBox.setDisplayFormat("$feeNumber$");
        kdtEntry4_name_PromptBox.setEditFormat("$feeNumber$");
        kdtEntry4_name_PromptBox.setCommitFormat("$feeNumber$");
        view = new EntityViewInfo();
		filInfo = new FilterInfo();
		if(prmtproName.getValue()!=null){
			ProjectInfo project = (ProjectInfo)prmtproName.getValue();
			filInfo.getFilterItems().add(new FilterItemInfo("number",project.getNumber()));
			filInfo.getFilterItems().add(new FilterItemInfo("isLast","1"));
			filInfo.getFilterItems().add(new FilterItemInfo("beizhu","最新"));
			view.setFilter(filInfo);
			kdtEntry4_name_PromptBox.setEntityViewInfo(view);
		}
        KDTDefaultCellEditor kdtEntry4_name_CellEditor = new KDTDefaultCellEditor(kdtEntry4_name_PromptBox);
        this.kdtEntry4.getColumn("budgetNumber").setEditor(kdtEntry4_name_CellEditor);
        ObjectValueRender kdtEntry4_name_OVR = new ObjectValueRender();
        kdtEntry4_name_OVR.setFormat(new BizDataFormat("$feeNumber$"));
        this.kdtEntry4.getColumn("budgetNumber").setRenderer(kdtEntry4_name_OVR);
        
		AdminOrgUnitInfo admin = SysContext.getSysContext().getCurrentAdminUnit();
		EmployeeMultiF7PromptBox person = new EmployeeMultiF7PromptBox();
		person.setIsSingleSelect(false);
		person.showNoPositionPerson(false);
		if (OrgConstants.DEF_CU_ID.equals(admin.getId().toString()))
			person.setIsShowAllAdmin(true);
		this.prmtapplicant.setSelector(person);
		initContainerButton(kDContainer1, kdtEntry2_detailPanel);
		initContainerButton(kDContainer2, kdtEntry5_detailPanel);
		initContainerButton(kDContainer3, kdtEntry3_detailPanel);
		initContainerButton(kDContainer4, kdtEntry1_detailPanel);
		initContainerButton(kDContainer5, kdtEntry4_detailPanel);
		initContainerButton(kDContainer6, kdtE6_detailPanel);
		initContainerButton(kDContainer7, kdtE7_detailPanel);

		AdminF7 f7 = new AdminF7(this);
		f7.showCheckBoxOfShowingAllOUs();
		f7.setIsCUFilter(false);
		f7.setRootUnitID(SysContext.getSysContext().getCurrentAdminUnit().getId().toString());
		this.prmtuseOrg.setSelector(f7);
		
		this.kdtE7.getColumn("EvaluationName").setRequired(true);
		this.kdtE6.getColumn("evaluationNameTex").setRequired(true);
		this.kdtE6.getColumn("weight").setRequired(true);
		this.kdtE6.getColumn("EvaluationType").getStyleAttributes().setLocked(false);
		
		setTableToSumField(kdtE6, new String []{"weight"});
		setTableToSumField(kdtE7, new String []{"weight"});
		
		this.kdtEntry2.getColumn("evaEnterprise").getStyleAttributes().setUnderline(true);
		this.kdtEntry2.getColumn("evaEnterprise").getStyleAttributes().setFontColor(Color.BLUE);
		this.kdtEntry2.getColumn("succTable").getStyleAttributes().setUnderline(true);
		this.kdtEntry2.getColumn("succTable").getStyleAttributes().setFontColor(Color.BLUE);
		
		if(OprtState.ADDNEW.equals(getOprtState())){
			this.prmtapplicant.setValue(SysContext.getSysContext().getCurrentUserInfo().getPerson());
			this.prmtuseOrg.setValue(SysContext.getSysContext().getCurrentAdminUnit());
			prmtinviteType.setValue(InviteTypeFactory.getRemoteInstance().getInviteTypeCollection("where number='ZBFS02'").get(0));
		}
		this.kdtEntry4.getColumn("seq").getStyleAttributes().setHided(true);
		this.kdtEntry4.getColumn("content").setWidth(360);
		this.kdtEntry4.getColumn("budgetName").setWidth(180);
		
		PersonPromptBox select = new PersonPromptBox(this);
//		select.setCU(OrgConstants.DEF_CU_ID);
		select.showAllAdmin();
        final KDBizPromptBox kdtEntry3_invitePerson_PromptBox = new KDBizPromptBox();
        kdtEntry3_invitePerson_PromptBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        kdtEntry3_invitePerson_PromptBox.setVisible(true);
        kdtEntry3_invitePerson_PromptBox.setEditable(true);
        kdtEntry3_invitePerson_PromptBox.setDisplayFormat("$name$");
        kdtEntry3_invitePerson_PromptBox.setEditFormat("$number$");
        kdtEntry3_invitePerson_PromptBox.setCommitFormat("$number$");
        kdtEntry3_invitePerson_PromptBox.setSelector(select);
        KDTDefaultCellEditor kdtEntry3_invitePerson_CellEditor = new KDTDefaultCellEditor(kdtEntry3_invitePerson_PromptBox);
        this.kdtEntry3.getColumn("invitePerson").setEditor(kdtEntry3_invitePerson_CellEditor);
        ObjectValueRender kdtEntry3_invitePerson_OVR = new ObjectValueRender();
        kdtEntry3_invitePerson_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry3.getColumn("invitePerson").setRenderer(kdtEntry3_invitePerson_OVR);
        
      
        
        pkaudDate.setValue(new Date());
	}

	// container设置分录按钮以及分录放置模式
	public static void initContainerButton(KDContainer kDContainer,DetailPanel detail) {
		kDContainer.getContentPane().add(detail.getEntryTable(),BorderLayout.CENTER);
		kDContainer.addButton(detail.getAddNewLineButton());
		kDContainer.addButton(detail.getInsertLineButton());
		kDContainer.addButton(detail.getRemoveLinesButton());
	}
	
	public void kdtEntry3_Changed(int rowIndex, int colIndex) throws Exception {
		 if(UIRuleUtil.isNull(kdtEntry3.getCell(rowIndex, "invitePerson").getValue())
				 ||!"invitePerson".equalsIgnoreCase(kdtEntry3.getColumn(colIndex).getKey())||rowIndex==-1)
			 return;
		 PersonInfo personInfo = (PersonInfo)kdtEntry3.getCell(rowIndex, "invitePerson").getValue();
		 kdtEntry3.getCell(rowIndex,"department").setValue(PersonXRHelper.getPosiMemByDeptUser(personInfo));
	}
	
    public void kdtEntry4_Changed(int rowIndex,int colIndex) throws Exception
    {
    	BigDecimal budgetAmount = UIRuleUtil.getBigDecimal(UIRuleUtil.getProperty((IObjectValue)kdtEntry4.getCell(rowIndex,"budgetNumber").getValue(),"contractAssign"));
    	BigDecimal reportInviteAmount = UIRuleUtil.getBigDecimal(UIRuleUtil.getProperty((IObjectValue)kdtEntry4.getCell(rowIndex,"budgetNumber").getValue(),"invitReportedAmount"));
    	BigDecimal InvitedAmount = UIRuleUtil.getBigDecimal(UIRuleUtil.getProperty((IObjectValue)kdtEntry4.getCell(rowIndex,"budgetNumber").getValue(),"invitedAmount"));
    	BigDecimal contractAmount = UIRuleUtil.getBigDecimal(UIRuleUtil.getProperty((IObjectValue)kdtEntry4.getCell(rowIndex,"budgetNumber").getValue(),"contractedAmount"));
    	BigDecimal nocontractAmount = UIRuleUtil.getBigDecimal(UIRuleUtil.getProperty((IObjectValue)kdtEntry4.getCell(rowIndex,"budgetNumber").getValue(),"noContractedAmount"));
    	BigDecimal noInviteContractAmount = UIRuleUtil.getBigDecimal(UIRuleUtil.getProperty((IObjectValue)kdtEntry4.getCell(rowIndex,"budgetNumber").getValue(),"noInviteContractAmount"));
    	BigDecimal balanceAmount = budgetAmount.subtract(reportInviteAmount).subtract(nocontractAmount).subtract(noInviteContractAmount);

    	if ("budgetNumber".equalsIgnoreCase(kdtEntry4.getColumn(colIndex).getKey())) {
        	kdtEntry4.getCell(rowIndex,"budgetName").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)kdtEntry4.getCell(rowIndex,"budgetNumber").getValue(),"feeName")));
        	kdtEntry4.getCell(rowIndex,"budgetAmount").setValue(UIRuleUtil.getBigDecimal(UIRuleUtil.getProperty((IObjectValue)kdtEntry4.getCell(rowIndex,"budgetNumber").getValue(),"contractAssign")));
        	kdtEntry4.getCell(rowIndex,"balance").setValue(balanceAmount);
        	kdtEntry4.getCell(rowIndex,"lastAmount").setValue(balanceAmount);
       }
        if ("amount".equalsIgnoreCase(kdtEntry4.getColumn(colIndex).getKey())) {
        	BigDecimal amount = balanceAmount.subtract(UIRuleUtil.getBigDecimal(kdtEntry4.getCell(rowIndex,"amount").getValue()));
        	if(amount.intValue()<0){
        		kdtEntry4.getCell(rowIndex,"amount").setValue("0");
        	}else
            	kdtEntry4.getCell(rowIndex,"lastAmount").setValue(amount);
       }
        BigDecimal amount = new BigDecimal(0.000);
       for (int i = 0; i < kdtEntry4.getRowCount(); i++) {
    	   amount = amount.add(UIRuleUtil.getBigDecimal(kdtEntry4.getCell(i,"amount").getValue()));
       }
       txtinviteBudget.setText(amount.toString());
    }

	protected void prmtinviteType_dataChanged(DataChangeEvent e)throws Exception {
		super.prmtinviteType_dataChanged(e); 
		if(this.prmtinviteType.getValue()!=null)
		{
			InviteTypeInfo Info = (InviteTypeInfo)this.prmtinviteType.getValue();
			if(Info.getName().equals("引用招标成果"))
			{
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filInfo = new FilterInfo();
				String sql = "select b.CFUnitNameID from CT_INV_WinInviteReport a left join CT_INV_WinInviteReportUnit b on b.fparentid=a.fid where a.fstatus='4' and b.CFWin='1'";
				filInfo.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
				view.setFilter(filInfo);
				
				KDBizPromptBox kdtEntry2_evaEnterprise_PromptBox = new KDBizPromptBox();
		        kdtEntry2_evaEnterprise_PromptBox.setQueryInfo("com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierStockQuery");
		        kdtEntry2_evaEnterprise_PromptBox.setVisible(true);
		        kdtEntry2_evaEnterprise_PromptBox.setEditable(true);
		        kdtEntry2_evaEnterprise_PromptBox.setDisplayFormat("$number$");
		        kdtEntry2_evaEnterprise_PromptBox.setEditFormat("$number$");
		        kdtEntry2_evaEnterprise_PromptBox.setCommitFormat("$number$");
		        kdtEntry2_evaEnterprise_PromptBox.setEntityViewInfo(view);
		        KDTDefaultCellEditor kdtEntry2_evaEnterprise_CellEditor = new KDTDefaultCellEditor(kdtEntry2_evaEnterprise_PromptBox);
		        this.kdtEntry2.getColumn("evaEnterprise").setEditor(kdtEntry2_evaEnterprise_CellEditor);
		        ObjectValueRender kdtEntry2_evaEnterprise_OVR = new ObjectValueRender();
		        kdtEntry2_evaEnterprise_OVR.setFormat(new BizDataFormat("$supplierName$"));
		        this.kdtEntry2.getColumn("evaEnterprise").setRenderer(kdtEntry2_evaEnterprise_OVR);
		        
			}
			
			if(Info.getName().equals("引用招标成果")){
				this.kdtEntry2.getColumn("succTable").getStyleAttributes().setHided(false);
				kDTabbedPane2.add("评标办法", kDPanel9);
			}else{
				this.kdtEntry2.getColumn("succTable").getStyleAttributes().setHided(true);
				kDTabbedPane2.remove(kDPanel9);
			}
			
			
			if(Info.getName().equals("公开招标")||Info.getName().equals("邀请招标")||Info.getName().equals("竞争性谈判")){
				kDTabbedPane2.add("评标办法", kDPanel4);
			}else{
				kDTabbedPane2.remove(kDPanel4);
			}
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,txtreportName, "标段名称");
		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,prmtproName, "项目名称");
		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,prmtuseOrg, "招标单位");
		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,prmtinviteType, "招标方式");
		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,txtinviteBudget, "招标预算");
		if(UIRuleUtil.getBigDecimal(txtinviteBudget.getText()).intValue()==0){
			MsgBox.showError("招标预算不能为0，请选择标段预算！\n 如果选择不到标段预算，请确认是否完成项目启动申请！");
			SysUtil.abort();
		}
			com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,prmtvalidTemplate, "符合性审查模板");
			if (this.judgeSolution.getSelectedItem().equals(
					com.kingdee.eas.port.pm.invite.judgeSolution.integrate)) {
				com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,
						prmtevaTemplate, "评标模板");
				com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,
						txtrmhigh, "去除几个最高");
				com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,
						txtrmlow, "去除几个最低");
				com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,
						txtreduHigh, "高%1扣");
				com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,
						txtreduLow, "低%1扣");
				com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,
						txtbusinessScore, "商务分");
				com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this,
						txttechScore, "技术分");
			BigDecimal business = new BigDecimal(txtbusinessScore.getText());
			if (business.add(new BigDecimal(txttechScore.getText())).compareTo(
					new BigDecimal(100)) != 0) {
				MsgBox.showWarning("商务分,技术分总和不等于100,请修改!!");
				SysUtil.abort();
			}
		}
		
		if(kdtEntry2.getRowCount()==0){
			MsgBox.showInfo("请填写投标单位分录!");
			SysUtil.abort();
		}
		
		for (int i = 0; i < kdtEntry2.getRowCount(); i++) {
			if(kdtEntry2.getCell(i, "evaEnterprise").getValue() == null){
				MsgBox.showInfo("请填写投标单位分录的投标单位!");
				SysUtil.abort();
			}
		}
		
		if(kdtEntry3.getRowCount()==0){
			MsgBox.showInfo("请填写招标人员信息的招标项目组分录!");
			SysUtil.abort();
		}
		
		for (int i = 0; i < kdtEntry3.getRowCount(); i++) {
			if(kdtEntry3.getCell(i, "invitePerson").getValue() == null){
				MsgBox.showInfo("请填写招标人员信息的招标项目组分录的招标成员!");
				SysUtil.abort();
			}
		}
		
		if(kdtEntry5.getRowCount()==0){
			MsgBox.showInfo("请填写招标人员信息的评标专家组分录!");
			SysUtil.abort();
		}
		
		for (int i = 0; i < kdtEntry5.getRowCount(); i++) {
			if(kdtEntry5.getCell(i, "judgeType").getValue() == null){
				MsgBox.showInfo("请填写招标人员信息的评标专家组分录的专家类别!");
				SysUtil.abort();
			}
			if(kdtEntry5.getCell(i, "amount").getValue() == null){
				MsgBox.showInfo("请填写招标人员信息的评标专家组分录的人数!");
				SysUtil.abort();
			}
		}
		
		if(judgeSolution.getSelectedItem() !=null  ){
			judgeSolution jus = (judgeSolution)this.judgeSolution.getSelectedItem();
			if (jus.equals(com.kingdee.eas.port.pm.invite.judgeSolution.integrate)){
			if(kdtE6.getRowCount()==0){
				MsgBox.showInfo("请填写评标办法的评审指标信息分录!");
				SysUtil.abort();
			}
			
			for (int i = 0; i < kdtE6.getRowCount(); i++) {
				if(kdtE6.getCell(i, "evaluationNameTex").getValue() == null){
					MsgBox.showInfo("请填写评标办法的评审指标信息分录的评审指标名称!");
					SysUtil.abort();
				}
				if(kdtE6.getCell(i, "weight").getValue() == null){
					MsgBox.showInfo("请填写评标办法的评审指标信息分录的权重!");
					SysUtil.abort();
				}
			}
		 }
		}
	
		if(kdtE7.getRowCount()==0){
			MsgBox.showInfo("请填写评标办法的符合性审查分录!");
			SysUtil.abort();
		}
		
		for (int i = 0; i < kdtE7.getRowCount(); i++) {
			if(kdtE7.getCell(i, "EvaluationName").getValue() == null){
				MsgBox.showInfo("请填写评标办法的符合性审查分录的评审指标名称!");
				SysUtil.abort();
			}
		}
		
//		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyKDTColumnNull(this, kdtEntry2, "evaEnterprise");
//		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyKDTColumnNull(this, kdtEntry3, "invitePerson");
//		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyKDTColumnNull(this, kdtEntry5, "judgeType");
//		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyKDTColumnNull(this, kdtEntry5, "amount");
//		
//		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyKDTColumnNull(this, kdtE6, "evaluationNameTex");
//		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyKDTColumnNull(this, kdtE6, "weight");
//		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyKDTColumnNull(this, kdtE7, "EvaluationName");

//		if(UIRuleUtil.sum(kdtE6, "weight")!=100)
//		{
//			MsgBox.showWarning("评标办法内评审指标信息权重比不等于100，不能保存、提交！");SysUtil.abort();
//		}
		
		judgeSolution jud = (judgeSolution)judgeSolution.getSelectedItem();
    	if(jud.getValue().equals("1")) {
    		//综合评分时评标系数必填
    		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this, txtcoefficient, "评标基准系数X");
    		BigDecimal coffient = new BigDecimal(txtcoefficient.getText());
    		if(coffient.compareTo(new BigDecimal(0.9)) < 0 || coffient.compareTo(new BigDecimal(1.0)) > 0) {
    			MsgBox.showWarning("评标基准系数X大于等于0.9小于等于1!!");
    			SysUtil.abort();
    		}
    	}
    	
		
		super.verifyInput(e);
	}

	protected void judgeSolution_actionPerformed(ActionEvent e)throws Exception {
		super.judgeSolution_actionPerformed(e);
		if(this.judgeSolution.getSelectedItem()==null)
			return;
		
		judgeSolution jus = (judgeSolution)this.judgeSolution.getSelectedItem();
		if (jus.equals(
				com.kingdee.eas.port.pm.invite.judgeSolution.lowest)) {
			txtrmhigh.setEnabled(false);
			txtrmlow.setEnabled(false);
			txtreduHigh.setEnabled(false);
			txtreduLow.setEnabled(false);
			txtbusinessScore.setEnabled(false);
			txttechScore.setEnabled(false);
			prmtevaTemplate.setEnabled(false);
			txtcoefficient.setEnabled(false);
			txtcoefficient.setValue(null);
			prmtevaTemplate.setValue(null);
			txtrmhigh.setText(null);
			txtrmlow.setText(null);
			txtreduHigh.setText(null);
			txtreduLow.setText(null);
			txtbusinessScore.setText(null);
			txttechScore.setText(null);
			this.kdtE6.removeRows();
		} else if (jus.equals(
				com.kingdee.eas.port.pm.invite.judgeSolution.integrate)) {
			txtcoefficient.setEnabled(true);
			prmtevaTemplate.setEnabled(true);
			txtrmhigh.setEnabled(true);
			txtrmlow.setEnabled(true);
			txtreduHigh.setEnabled(true);
			txtreduLow.setEnabled(true);
			txtbusinessScore.setEnabled(true);
			txttechScore.setEnabled(true);
			txtrmhigh.setRequired(true);
			txtrmlow.setRequired(true);
			txtreduHigh.setRequired(true);
			txtreduLow.setRequired(true);
			txtbusinessScore.setRequired(true);
			txttechScore.setRequired(true);
			txtcoefficient.setRequired(true);
		}
	}
	
	protected void kdtEntry2_tableClicked(KDTMouseEvent e) throws Exception {
		super.kdtEntry2_tableClicked(e);
		if(e.getRowIndex()==-1|| e.getColIndex()==-1)
			return;
		
		if(e.getColIndex()==kdtEntry2.getColumnIndex("succTable"))
		{
			if(UIRuleUtil.isNull(kdtEntry2.getCell(e.getRowIndex(), "evaEnterprise").getValue()))
			{
				MsgBox.showWarning("请先选择供应商！");
				kdtEntry2.getEditManager().editCellAt(e.getRowIndex(), kdtEntry2.getColumnIndex("evaEnterprise"));
				SysUtil.abort();
			}
			
			String id = ((MarketSupplierStockInfo)kdtEntry2.getCell(e.getRowIndex(), "evaEnterprise").getValue()).getId().toString();
//			MarketSupplierStockInfo maInfo = MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(id));
//			String name = maInfo.getSupplierName();
	       	EntityViewInfo evi = new EntityViewInfo();
	        FilterInfo filter = new FilterInfo();
	        filter.getFilterItems().add(new FilterItemInfo("winInviteUnit.id",id ,CompareType.EQUALS));
	        evi.setFilter(filter);
	        ((KDBizPromptBox)this.kdtEntry2.getColumn("succTable").getEditor().getComponent()).setEntityViewInfo(evi);
	        
		}
		
		
		if(e.getClickCount()==2&&e.getColIndex()==kdtEntry2.getColumnIndex("evaEnterprise"))
		{
			if(UIRuleUtil.isNull(kdtEntry2.getCell(e.getRowIndex(), e.getColIndex()).getValue()))
				return;
			BOSUuid id = ((MarketSupplierStockInfo)kdtEntry2.getCell(e.getRowIndex(), e.getColIndex()).getValue()).getId();
			UIContext context = new UIContext(this);
			context.put("ID", id);
			UIFactory.createUIFactory(UIFactoryName.NEWWIN).create(MarketSupplierStockEditUI.class.getName(), context, null,OprtState.VIEW).show();
		}
		
		if(e.getClickCount()==2&&e.getColIndex()==kdtEntry2.getColumnIndex("succTable"))
		{
			if(UIRuleUtil.isNull(kdtEntry2.getCell(e.getRowIndex(),"succTable").getValue()))
				return;
			BOSUuid id = ((WinInviteReportInfo)kdtEntry2.getCell(e.getRowIndex(),"succTable").getValue()).getId();
			UIContext context = new UIContext(this);
			context.put("ID", id);
			UIFactory.createUIFactory(UIFactoryName.NEWWIN).create(WinInviteReportEditUI.class.getName(), context, null,OprtState.VIEW).show();
		}
		
	}
	
	protected void kdtEntry2_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntry2_editStopped(e);
		
		if(e.getRowIndex()==-1||e.getColIndex()==-1||!kdtEntry2.getColumnKey(e.getColIndex()).equals("evaEnterprise"))
			return;
		
		Object oldValue = e.getOldValue(); 
		Object newValue = e.getValue();
		
		String oldStr = oldValue!=null?((MarketSupplierStockInfo)oldValue).getId().toString():"";	
		String newStr = newValue!=null?((MarketSupplierStockInfo)newValue).getId().toString():"";	
		
		if(!newStr.equals(oldStr))
			kdtEntry2.getCell(e.getRowIndex(), "succTable").setValue(null);
	}
	
	
	/**
	 * output loadFields method
	 */
	public void loadFields() {
		super.loadFields();
	}

	
	protected void prmtevaTemplate_dataChanged(DataChangeEvent e)throws Exception {
		super.prmtevaTemplate_dataChanged(e);
		
		 boolean isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged||e.getNewValue()==null)
        	return;
		
		EvaluationTemplateInfo Info = (EvaluationTemplateInfo)e.getNewValue();
		
		boolean isShowWarn=false;
        boolean isUpdate=false;
        if(this.kdtE6.getRowCount()>0 ){
        	isShowWarn=true;
        }
        if(isShowWarn){
        	if(MsgBox.showConfirm2(this, "评审模板改变会覆盖评标办法内评审指标信息，是否继续？")== JOptionPane.YES_OPTION){
        		isUpdate=true;
            }
        }else{
        	isUpdate=true;
        }
        
        if(isUpdate){
        	this.kdtE6.removeRows();
        	IEvaluationIndicators IEvaluationIndicators = EvaluationIndicatorsFactory.getRemoteInstance();
        	IEvaluationIndicatorsTree itree = EvaluationIndicatorsTreeFactory.getRemoteInstance();
        	for (int i = 0; i < Info.getEntry().size(); i++) 
        	{
        		EvaluationTemplateEntryInfo entryInfo = Info.getEntry().get(i);
        		
        		IRow row = this.kdtE6.addRow();
        		
        		if(entryInfo.getIndicatorType()!=null)
        		{
        			
        			EvaluationIndicatorsInfo EvaluationIndicatorsInfo = IEvaluationIndicators.getEvaluationIndicatorsInfo(new ObjectUuidPK(entryInfo.getIndicatorType().getId()));
        			
        			EvaluationIndicatorsTreeInfo treeInfo = itree.getEvaluationIndicatorsTreeInfo(new ObjectUuidPK(EvaluationIndicatorsInfo.getTreeid().getId()));
        			
        			row.getCell("evaluationNameTex").setValue(EvaluationIndicatorsInfo.getName());
        			row.getCell("weight").setValue(entryInfo.getWeight());
        			row.getCell("remake").setValue(entryInfo.getComment());
        			row.getCell("EvaluationType").setValue(treeInfo.getName());
        		}
			}
        }
        setTableToSumField(kdtE6, new String []{"weight"});
	}
	
	protected void prmtvalidTemplate_dataChanged(DataChangeEvent e)throws Exception {
		super.prmtvalidTemplate_dataChanged(e);
		
		 boolean isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged||e.getNewValue()==null)
        	return;
		
		EvaluationTemplateInfo Info = (EvaluationTemplateInfo)e.getNewValue();
		
		boolean isShowWarn=false;
        boolean isUpdate=false;
        if( this.kdtE7.getRowCount()>0){
        	isShowWarn=true;
        }
        if(isShowWarn){
        	if(MsgBox.showConfirm2(this, "评审模板改变会覆盖评标办法内符合性审查页签，是否继续？")== JOptionPane.YES_OPTION){
        		isUpdate=true;
            }
        }else{
        	isUpdate=true;
        }
        
        if(isUpdate){
        	this.kdtE7.removeRows();
        	IEvaluationIndicators IEvaluationIndicators = EvaluationIndicatorsFactory.getRemoteInstance();
        	IEvaluationIndicatorsTree itree = EvaluationIndicatorsTreeFactory.getRemoteInstance();
        	for (int i = 0; i < Info.getEntry().size(); i++) 
        	{
        		EvaluationTemplateEntryInfo entryInfo = Info.getEntry().get(i);
        		
        		IRow row1 = this.kdtE7.addRow();
        		
        		if(entryInfo.getIndicatorType()!=null)
        		{
        			
        			EvaluationIndicatorsInfo EvaluationIndicatorsInfo = IEvaluationIndicators.getEvaluationIndicatorsInfo(new ObjectUuidPK(entryInfo.getIndicatorType().getId()));
        			
        			EvaluationIndicatorsTreeInfo treeInfo = itree.getEvaluationIndicatorsTreeInfo(new ObjectUuidPK(EvaluationIndicatorsInfo.getTreeid().getId()));
        			
        			row1.getCell("EvaluationName").setValue(EvaluationIndicatorsInfo.getName());
        			row1.getCell("weight").setValue(entryInfo.getWeight());
        			row1.getCell("remake").setValue(entryInfo.getComment());
        			row1.getCell("EvaluationType").setValue(treeInfo.getName());
        		}
			}
        }
	
	}
	
	protected void prmtinvitePlan_dataChanged(DataChangeEvent e)throws Exception {
		super.prmtinvitePlan_dataChanged(e);
		boolean isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged||e.getNewValue()==null)
        	return;
		InvitePlanInfo Info = (InvitePlanInfo)e.getNewValue();
		
		if(Info.getInviteType()==null)
			return;
		this.prmtinviteType.setValue(InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(new ObjectUuidPK(Info.getInviteType().getId())));
	}
	
	public void kdtE6_Changed(int rowIndex, int colIndex) throws Exception {
		super.kdtE6_Changed(rowIndex, colIndex);
		
		setTableToSumField(kdtE6, new String []{"weight"});
		
	}
	
	protected void kdtE7_editStopped(KDTEditEvent e) throws Exception {
		super.kdtE7_editStopped(e);
		setTableToSumField(kdtE7, new String []{"weight"});
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
	public void actionCloudScreen_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCloudScreen_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
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
//		super.actionPrint_actionPerformed(e);
		openRptUI();
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
//		super.actionPrintPreview_actionPerformed(e);
		openRptUI();
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}
	
	private void openRptUI() throws UIException
	{
		//select * from T_BAS_DefineReport where fid ='6vYAAAAsYLc/8MJf'
		
		if(editData.getId()==null)
		{
			com.kingdee.eas.util.client.MsgBox.showWarning("当前单据未保存，不允许此操作！");
			SysUtil.abort();
		}
		
		String billId = editData.getId().toString();
		String reportId = "6vYAAAAyrxA/8MJf";
		
		SysPlatformXRHelper.openPrintForRpt(this,billId, reportId);
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
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

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
		return com.kingdee.eas.port.pm.invite.InviteReportFactory
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
	protected IObjectValue createNewData() {
		com.kingdee.eas.port.pm.invite.InviteReportInfo objectValue = new com.kingdee.eas.port.pm.invite.InviteReportInfo();
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		objectValue.setRmhigh("0");
		objectValue.setRmlow("0");
		objectValue.setCoefficient(new BigDecimal(1));
		ProjectInfo info = (ProjectInfo) getUIContext().get("treeInfo");
		System.out.println("hello " + info.toString());
		if (info != null ) {
			objectValue.setProName(info);
			objectValue.setReportName(info.getName());
			try {
				IProjectStartRequest iProjectStartRequest = ProjectStartRequestFactory.getRemoteInstance();
				ProjectStartRequestCollection reqColl = iProjectStartRequest.getProjectStartRequestCollection("where projectName.id='"+info.getId()+"'");
				if(reqColl.size()==0 && info.getNJGyearInvest()!=null){
					MsgBox.showWarning("项目没有启动,请到系统【投资管理-项目启动】进行项目启动申请！");
					SysUtil.abort();
				}
			} catch (BOSException e2) {
				e2.printStackTrace();
			}
		}
		return objectValue;
	}

	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return null;
	}

}