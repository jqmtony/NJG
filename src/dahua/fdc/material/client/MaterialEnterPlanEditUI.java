/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.material.IMaterialPlan;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.master.material.MaterialPlanFactory;
import com.kingdee.eas.basedata.master.material.MaterialPlanInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.MaterialPromptSelector;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.MaterialBillStateEnum;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillFactory;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillInfo;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryCollection;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryInfo;
import com.kingdee.eas.fdc.material.PartAMaterialEntryCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryFactory;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 材料进场计划 编辑界面
 */
public class MaterialEnterPlanEditUI extends AbstractMaterialEnterPlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialEnterPlanEditUI.class);
    
	private int unitPrecision = 2;
    
    /**
     * output class constructor
     */
    public MaterialEnterPlanEditUI() throws Exception
    {
        super();
    }
    
	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MaterialEnterPlanBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	protected IObjectValue createNewData() {
		MaterialEnterPlanBillInfo matEnterPlanBillInfo = new MaterialEnterPlanBillInfo();
		matEnterPlanBillInfo.setBillState(MaterialBillStateEnum.NONE);
		
		// 选择材料进场计划项目子节点，新增进场计划界面中“所属项目”字段自动带入，应不可编辑
		if (this.getUIContext().get("curProject") != null){
			matEnterPlanBillInfo.setCurProject((CurProjectInfo) this.getUIContext().get("curProject"));
		}
		else if (this.editData.getCurProject()!=null){
			matEnterPlanBillInfo.setCurProject(editData.getCurProject());
		}
		return matEnterPlanBillInfo;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		MaterialEnterPlanEntryInfo object = new MaterialEnterPlanEntryInfo();
		return object;
	}
	public void onLoad() throws Exception {
		super.onLoad();
		
		if(STATUS_EDIT.equals(this.getOprtState()))
		{
			if(this.editData!=null&&FDCBillStateEnum.SUBMITTED.equals(this.editData.getState()))
			{
				this.actionSave.setEnabled(false);
			}
		}else if(STATUS_VIEW.equals(this.getOprtState()))
		{
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionCopyLine.setEnabled(false);
		}
		
		if(editData!=null&&FDCBillStateEnum.AUDITTED.equals(editData.getState())){
			this.actionEdit.setEnabled(false);
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			
		}
		
		resetPrmtCurProject();
		resetPrmtContractBill();
		
		if(editData!=null&&editData.getContractBill()!=null)
		this.prmtSupplier.setValue(editData.getContractBill().getPartB());		
		
		resetMaterialF7();
		
		/**
		 * 设置计量单位为F7控件
		 */
		IColumn colUnit = this.kdtEntrys.getColumn("unit");
		colUnit.setRequired(true);
		
		colUnit.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		KDBizPromptBox f7MeasureUnit = new KDBizPromptBox();
		
		f7MeasureUnit.setDisplayFormat("$number$");
		f7MeasureUnit.setEditFormat("$number$");
		f7MeasureUnit.setCommitFormat("$number$");
		f7MeasureUnit.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
		
		KDTDefaultCellEditor f7EditorUnit = new KDTDefaultCellEditor(f7MeasureUnit);
		colUnit.setEditor(f7EditorUnit);
		
		KDFormattedTextField amountField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		
		amountField.setPrecision(2);
		amountField.setNegatived(false);
		amountField.setSupportedEmpty(true);
		
		KDTDefaultCellEditor qtyDecimalEditor = new KDTDefaultCellEditor(amountField);
		
		this.kdtEntrys.getColumn("quantity").setEditor(qtyDecimalEditor);
		this.kdtEntrys.getColumn("quantity").setRequired(true);
		this.kdtEntrys.getColumn("quantity").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.kdtEntrys.getColumn("quantity").getStyleAttributes().setNumberFormat("#,###.00");
		
		this.kdtEntrys.getColumn("auditQuantity").setEditor(qtyDecimalEditor);
		this.kdtEntrys.getColumn("auditQuantity").setRequired(true);
		this.kdtEntrys.getColumn("auditQuantity").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.kdtEntrys.getColumn("auditQuantity").getStyleAttributes().setNumberFormat("#,##0.00");
		/**
		 * 设置进场日期
		 */
		IColumn inputDateCol = this.kdtEntrys.getColumn("enterTime");
		
		KDDatePicker inputDateField = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(inputDateField);
		inputDateCol.setEditor(dateEditor);
		
		FDCHelper.formatTableDate(kdtEntrys, "enterTime");
		
		/**
		 * 设置规格型号
		 */
		KDTextField txtSize = new KDTextField();
		txtSize.setMaxLength(80);
		KDTDefaultCellEditor sizeCellEditor = new KDTDefaultCellEditor(txtSize);
		this.kdtEntrys.getColumn("model").setEditor(sizeCellEditor);
//		this.kdtEntrys.getColumn("brand").setEditor(sizeCellEditor);
//		this.kdtEntrys.getColumn("factory").setEditor(sizeCellEditor);
//		this.kdtEntrys.getColumn("use").setEditor(sizeCellEditor);
		
//		KDComboBox box = new KDComboBox();
//		box.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.InstallTypeEnum").toArray());
//		KDTDefaultCellEditor boxCellEditor = new KDTDefaultCellEditor(box);
//		this.kdtEntrys.getColumn("install").setEditor(boxCellEditor);
		/**
		 * 设置备注cell长度
		 */
		KDTextField txtDescription = new KDTextField();
		txtDescription.setMaxLength(100);
		KDTDefaultCellEditor descriptionCellEditor = new KDTDefaultCellEditor(txtDescription);
		
		this.kdtEntrys.getColumn("remark").setEditor(descriptionCellEditor);
		
		
		this.actionAuditResult.setVisible(false);
		
		this.btnAddLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		
		// 启用print和printPreview按钮  Added by owen_wen 2010-09-13
		actionPrint.setVisible(true);
		actionPrint.setEnabled(true);
		actionPrintPreview.setVisible(true);
		actionPrintPreview.setEnabled(true);
		
		if(STATUS_ADDNEW.equals(getOprtState()))
			combBillState.setSelectedItem(MaterialBillStateEnum.NONE);
		combBillState.setEnabled(false);
		if (editData != null && editData.getBillState() != null) {
			this.combBillState.setSelectedItem(editData.getBillState());
		}
		setEntriesPrecision();
		this.kdtEntrys.getColumn("produceLeadTime").getStyleAttributes().setLocked(false);
		this.kdtEntrys.getColumn("meterialCon").getStyleAttributes().setLocked(false);
		this.kdtEntrys.getColumn("supplier").getStyleAttributes().setLocked(false);
		//收货可提前天数
		formattedNumberOnly();
	}

	/**
	 * 设置物料编码为F7控件，并设置其相应的属性
	 * @throws SQLException 
	 * @throws BOSException 
	 */
	private void resetMaterialF7() throws BOSException, SQLException {
		resetMaterialF7(null);
	}
	
	private void resetMaterialF7(ContractBillInfo contractbill) throws BOSException, SQLException {
		IColumn matNumCol = this.kdtEntrys.getColumn("material.number");
		matNumCol.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		
		matNumCol.setRequired(true);
		KDBizPromptBox f7MatNum = new KDBizPromptBox();
		
		ObjectValueRender segNum = new ObjectValueRender();
		segNum.setFormat(new BizDataFormat("$number$"));
		matNumCol.setRenderer(segNum);
		
		f7MatNum.setDisplayFormat("$number$");
		f7MatNum.setEditFormat("$number$");
		f7MatNum.setCommitFormat("$number$");
		
		FilterInfo filter = new FilterInfo();
		
		Set idSet = getMaterialIDsFromCon(contractbill!=null?contractbill:editData.getContractBill());
		idSet.add("nullnull");
		if (idSet.size()>0)
			filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		
		MaterialPromptSelector matPrmtSel = new MaterialPromptSelector(filter);		
		f7MatNum.setSelector(matPrmtSel);  // 使用自定义左树右表的F7 Added by owen_wen 2010-8-27
		
//		f7MatNum.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialQuery");
//		f7MatNum.getQueryAgent().getRuntimeEntityView().getFilter().mergeFilter(filter, "and");
		f7MatNum.setEnabledMultiSelection(true);
		KDTDefaultCellEditor f7EditorMatNum = new KDTDefaultCellEditor(f7MatNum);
		matNumCol.setEditor(f7EditorMatNum);
		
		SelectorItemCollection sic2 = new SelectorItemCollection();
		sic2.add( new SelectorItemInfo("number"));
		sic2.add( new SelectorItemInfo("name"));
		sic2.add( new SelectorItemInfo("model"));
		sic2.add( new SelectorItemInfo("baseUnit.*"));
		sic2.add( new SelectorItemInfo("*"));
		
		f7MatNum.setSelectorCollection(sic2);
	}
	/**
	 * 重新设置工程项目prmtCurProject F7属性
	 * @throws BOSException
	 * @author owen_wen 2010-8-29
	 */
	private void resetPrmtCurProject() throws BOSException{
		Object curProject = this.getUIContext().get("curProject");
		if(curProject != null){
			this.prmtCurProject.setValue(curProject);
		}
		this.prmtCurProject.setDisplayFormat("$number$ $name$");
		this.prmtCurProject.setEditFormat("$name$");
		this.prmtCurProject.setCommitFormat("$name$");
		this.prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		
		FilterInfo curProjectFilter = new FilterInfo();
		curProjectFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString(), CompareType.EQUALS));
		curProjectFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		curProjectFilter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		
		this.prmtCurProject.getQueryAgent().getRuntimeEntityView().getFilter().mergeFilter(curProjectFilter, "and");

		this.prmtCurProject.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				CurProjectInfo project = (CurProjectInfo) eventObj.getNewValue();
				if(project != null){
					CurProjectInfo oldProject = (CurProjectInfo) eventObj.getOldValue();
					if(oldProject != null && project.getId().toString().equals(oldProject.getId().toString())){
						return;
					}
					if(project.getCostCenter() == null){
						prmtCurProject.setValue(eventObj.getOldValue());
						FDCMsgBox.showError("您选择的工程项目没有对应到成本中心");
					}
				}
			}
			
		});
	}
	
	
	/**
	 * 重新设置施工合同prmtContractBill F7属性
	 * @author owen_wen 2010-8-29
	 */
	private void resetPrmtContractBill(){
		this.prmtContractBill.setDisplayFormat("$number$");
		this.prmtContractBill.setEditFormat("$number$");
		this.prmtContractBill.setCommitFormat("$number$");
		this.prmtContractBill.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7SimpleQuery");
		
		EntityViewInfo contractViewInfo = new EntityViewInfo();
		if(curProject==null&&editData!=null)
			curProject = editData.getCurProject();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",curProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("hasSettled", Boolean.FALSE));

		contractViewInfo.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("partB.id");
		sic.add("partB.number");
		sic.add("partB.name");
		prmtContractBill.setSelectorCollection(sic);
		prmtContractBill.setEntityViewInfo(contractViewInfo);
	}
	
	/**
	 * 根据施工合同，从已审批的最高版本的材料明细表中获取物料ID集
	 * @return 物料的ID集
	 * @author owen_wen 2010-8-29
	 * @throws BOSException 
	 * @throws SQLException 
	 */
	private Set getMaterialIDsFromCon(ContractBillInfo contractBill) throws BOSException, SQLException{
		String contractID = "";	
		
		if (contractBill != null){
			contractID = contractBill.getId().toString();
		}
		
		// 获取材料明细单状态为已审批，且版本号是最新的
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql("select FMaterialId from T_PAM_PartAMaterialEntry pamEntry ");
		fdcBuilder.appendSql("inner join T_PAM_PartAMaterial pam on  pam.fid = pamEntry.FparentID ");
		fdcBuilder.appendSql("where pamEntry.FMainContractBillId = '" + contractID + "' ");
		fdcBuilder.appendSql("and pam.fstate='4AUDITTED' ");
		
		// 杨志桥添加了是否最新版本字段，这里改为直接引用，不过SQL脚本要处理老数据情况。added by owen_wen 2010-09-27
		fdcBuilder.appendSql("and pam.FISLATESTVER = 1 "); 
		
		logger.debug(fdcBuilder.getTestSql());
		
		IRowSet rs = fdcBuilder.executeQuery();
		Set rsSet = new HashSet();
		while(rs.next()){
			rsSet.add(rs.getString("FMaterialId"));
		}
		return rsSet;
	}
	
	protected void initWorkButton() {
		
		super.initWorkButton();
		initUI();
	}
	private void initUI() {
    	JButton btnAddLine = this.kDContainer1.add(actionAddLine);
    	JButton btnInsertLine = this.kDContainer1.add(actionInsertLine);
		JButton btnDelLine = this.kDContainer1.add(actionRemoveLine);
		btnAddLine.setIcon(EASResource.getIcon("imgTbtn_addline"));		
		btnAddLine.setSize(22, 19);
		btnInsertLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
		btnInsertLine.setSize(22, 19);
		btnDelLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelLine.setSize(22, 19);
		
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);	
		
		this.actionNextPerson.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionWorkflowList.setVisible(false);
		
    }
protected void verifyInput(ActionEvent e) throws Exception {
		
		super.verifyInput(e);
		editData.setName(editData.getNumber());
//		if(StringUtils.isEmpty(editData.getNumber()))
//		{
//			FDCMsgBox.showWarning(this,"单据编号不能为空");
//			abort();
//		}
		
		if(editData.getCurProject() == null)
		{
			FDCMsgBox.showWarning(this,"工程项目不能为空");
			abort();
		}
		if(editData.getContractBill() == null)
		{
			FDCMsgBox.showWarning(this,"施工合同不能为空");
			abort();
		}
		
		if(editData.getEntrys().size() == 0)
		{
			FDCMsgBox.showWarning(this,"请至少录入一行分录");
			abort();
		}
		
		
		//检测分录
		for(int i = 0; i < kdtEntrys.getRowCount(); ++i)
		{
			IRow row = kdtEntrys.getRow(i);
			Integer index = new Integer(i+1);
			String warning = "第" + index.toString() + "行、第" ;
			if(row.getCell("material.number").getValue() == null)
			{
				warning = warning + "1列物料不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			
			if(row.getCell("unit").getValue() == null)
			{
				warning = warning + "4列计量单位不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			
			if(row.getCell("quantity").getValue() == null)
			{
				warning = warning + "6列申报数量不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if(row.getCell("auditQuantity").getValue() == null)
			{
				warning = warning + "8列核定数量不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
		}
	}
	/**
	 * @see 限制收货可提前天数字段只能输入数字
	 * @author tim_gao
	 */
	public void formattedNumberOnly(){
		KDFormattedTextField produceLeadTime_CREDIT=new KDFormattedTextField();
		produceLeadTime_CREDIT.setDataType(KDFormattedTextField.INTEGER_TYPE);
		produceLeadTime_CREDIT.setSupportedEmpty(false);
		this. getDetailTable().getColumn("produceLeadTime").setEditor(new KDTDefaultCellEditor(produceLeadTime_CREDIT));
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("billState"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("address"));
		sic.add(new SelectorItemInfo("auditor.*"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("contractBill.id"));
		sic.add(new SelectorItemInfo("contractBill.number"));
		sic.add(new SelectorItemInfo("contractBill.name"));
		sic.add(new SelectorItemInfo("contractBill.partB.id"));
		sic.add(new SelectorItemInfo("contractBill.partB.number"));
		sic.add(new SelectorItemInfo("contractBill.partB.name"));
		sic.add(new SelectorItemInfo("entrys.model"));
		sic.add(new SelectorItemInfo("entrys.quantity"));
		sic.add(new SelectorItemInfo("entrys.enterTime"));
		sic.add(new SelectorItemInfo("entrys.auditQuantity"));
		sic.add(new SelectorItemInfo("entrys.remark"));
		sic.add(new SelectorItemInfo("entrys.materialCon"));
		sic.add(new SelectorItemInfo("entrys.supplier"));
		sic.add(new SelectorItemInfo("entrys.*"));
		//        sic.add(new SelectorItemInfo("entrys.number"));
		sic.add(new SelectorItemInfo("entrys.material.*"));
		//        sic.add(new SelectorItemInfo("entrys.material.number"));
		sic.add(new SelectorItemInfo("entrys.material.name"));
		sic.add(new SelectorItemInfo("entrys.unit.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("CU.*"));
		//        sic.add(new SelectorItemInfo("entrys.unit.number"));
		return sic;
    } 
	protected void prmtCurProject_dataChanged(DataChangeEvent e)
			throws Exception {
		Object newValue = e.getNewValue();
		if(!isFirstOnload()&&newValue!=null&&newValue instanceof CurProjectInfo){
			CurProjectInfo project = (CurProjectInfo) newValue;
			this.txtAddress.setText(project.getProjectAddress());
			editData.setOrgUnit(project.getFullOrgUnit());
		}
	}
	
	// 施工合同发生变化时
	protected void prmtContractBill_dataChanged(DataChangeEvent e) throws Exception {
		if(!this.isFirstOnload()&&(e.getNewValue()!=null)){
			ContractBillInfo newInfo = (ContractBillInfo)e.getNewValue(); 
			ContractBillInfo oldInfo = (ContractBillInfo)e.getOldValue();
			if(oldInfo==null||!newInfo.getId().toString().equals(oldInfo.getId().toString())){
				this.prmtSupplier.setValue(newInfo.getPartB());
				String contractId = newInfo.getId().toString();
				EntityViewInfo view = new EntityViewInfo();
				view.getSelector().add("*");
				view.getSelector().add("material.*");
				view.getSelector().add("material.baseUnit.*");
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("mainContractBill",contractId));
				filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.AUDITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("parent.isLatestVer", Boolean.TRUE));
				PartAMaterialEntryCollection entrys = PartAMaterialEntryFactory.getRemoteInstance().getPartAMaterialEntryCollection(view);
				if(entrys.size()>0 ){
					if( kdtEntrys.getRowCount() > 0){
					int  select = FDCMsgBox.showConfirm3(this, "自动带出关联的材料明细记录?\n\n是：清空分录，引入材料明细记录。\n否：不清空分录，引入材料明细记录。\n取消：不引入材料明细记录！");
					if(select == MsgBox.CANCEL)
						return;
					if(MsgBox.YES==select){
						this.kdtEntrys.removeRows();
					}}
					
					for(Iterator it = entrys.iterator();it.hasNext();){
						PartAMaterialEntryInfo entry = (PartAMaterialEntryInfo)it.next();
						MaterialEnterPlanEntryInfo objectEntry = (MaterialEnterPlanEntryInfo)createNewDetailData(this.kdtEntrys);
						IRow row = this.kdtEntrys.addRow();
						objectEntry.setMaterial(entry.getMaterial());
						objectEntry.setUnit(entry.getMaterial().getBaseUnit());
						objectEntry.setModel(entry.getMaterial().getModel());
						objectEntry.setParent(editData);
						editData.getEntrys().add(objectEntry);
						this.loadLineFields(this.kdtEntrys, row, objectEntry);
						
////////////////////////////////////////////////////////////
						// "材料合同"取值：与物料、施工合同对应的材料明细中的材料合同名称；
						// "供应商"取值:材料合同对应的签约乙方。 by cassiel 2010-08-26
						fillConAndPartB(entry.getMaterial().getId().toString(), row);
						
						//////////////////////////////////////////////////////////////
					}
				}else{
					this.kdtEntrys.removeRows();
				}
				resetMaterialF7(newInfo);
			}
			
		}
	}

	private void fillConAndPartB(String materialID, IRow row) {
		EntityViewInfo _view = new EntityViewInfo();
		SelectorItemCollection selector = new SelectorItemCollection();			
		selector.add(new SelectorItemInfo("parent.contractBill.id"));
		selector.add(new SelectorItemInfo("parent.contractBill.name"));//合同名称
		selector.add(new SelectorItemInfo("parent.contractBill.partB.name"));//供应商
		FilterInfo _filter = new FilterInfo();
		_filter.getFilterItems().add(new FilterItemInfo("material.id",materialID));
		String _contractId = ((ContractBillInfo)this.prmtContractBill.getValue()).getId().toString();
		_filter.getFilterItems().add(new FilterItemInfo("mainContractBill.id",_contractId));
		_view.setFilter(_filter);
		_view.setSelector(selector);
		PartAMaterialEntryCollection colls = null;
		try {
			colls = PartAMaterialEntryFactory.getRemoteInstance().getPartAMaterialEntryCollection(_view);
			row.getCell("meterialCon").setValue(null);
			row.getCell("supplier").setValue(null);

			PartAMaterialEntryInfo partAmterialEntry = colls.get(0);
			if (partAmterialEntry != null) {
				if (partAmterialEntry.getParent() != null) {
					if (partAmterialEntry.getParent().getContractBill() != null) {
						String contractName = partAmterialEntry.getParent().getContractBill().getName();
						row.getCell("meterialCon").setValue(contractName);
						if (partAmterialEntry.getParent().getContractBill().getPartB() != null) {
							String supplier = partAmterialEntry.getParent().getContractBill().getPartB().getName();
							row.getCell("supplier").setValue(supplier);
						}
					}
				}
			}
		} catch (BOSException _e) {
			_e.printStackTrace();
		} 		
	}

	/**
	 * 往分录行中插入数据
	 * @param rowIndex 光标所在行
	 * @param materials 待插入的物料数组，若其中某个为null，表示有重复不需要插入
	 * @author owen_wen 2010-6-23
	 */
	private void insertDataToRow(Object[] materials) {
	
		//获取活动行的行号
		int activeRow = kdtEntrys.getSelectManager().getActiveRowIndex();
		
		KDTable table  = getDetailTable();
		IRow row = table.getRow(activeRow);
		for(int i = 0; i < materials.length; i++){
			MaterialInfo materialInfo = null;
			if(materials[i] instanceof MaterialInfo)
				materialInfo = (MaterialInfo)materials[i];
			
			if(i == 0 && materialInfo == null)
			{
				table.removeRow(activeRow);
				continue;
			}
			
			//如果是多选(即i>0时)，则要判断是否要addLine（即新增一行分录）
			if(i > 0){
				if(materialInfo != null)
				{
					table.addRow(table.getSelectManager().getActiveRowIndex()); //在活动行处添加一行 
					row = table.getRow(table.getSelectManager().getActiveRowIndex()); 
					IObjectValue detailData = createNewDetailData(table);
					row.setUserObject(detailData);
				}
				else
				{
					continue;
				}
			}
			
			// 当 i==0, and material != null 的情况，直接将materialInfo放入到当前行中
			row.getCell("material.name").setValue(materialInfo.getName());
			row.getCell("material.number").setValue(materialInfo);
			row.getCell("model").setValue(materialInfo.getModel());
			if(materialInfo.getBaseUnit() != null){
				row.getCell("unit").setValue(materialInfo.getBaseUnit());
			}
			
			unitPrecision = materialInfo.getBaseUnit().getQtyPrecision();
			
			KDFormattedTextField unitEditor = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
			unitEditor.setDataType(BigDecimal.class);
			unitEditor.setPrecision(unitPrecision);
			KDTDefaultCellEditor editor = new KDTDefaultCellEditor(unitEditor);
			row.getCell("auditQuantity").setEditor(editor);
			row.getCell("auditQuantity").getStyleAttributes().setNumberFormat(precisonFormat(unitPrecision));
			row.getCell("quantity").setEditor(editor);
			row.getCell("quantity").getStyleAttributes().setNumberFormat(precisonFormat(unitPrecision));
	        //带出物料收货可提前天数 add by ywm
			int leadTime =getProduceLeadTime(materialInfo);
			row.getCell("produceLeadTime").setValue(String.valueOf(leadTime));
			
			// "材料合同"取值：与物料、施工合同对应的材料明细中的材料合同名称；
			// "供应商"取值:材料合同对应的签约乙方。 by cassiel 2010-08-26
			String materialID = materialInfo.getId().toString();
			fillConAndPartB(materialID, row);
			
			if(row.getUserObject() != null && 
					row.getUserObject() instanceof MaterialEnterPlanEntryInfo)
			{
				MaterialEnterPlanEntryInfo entry = (MaterialEnterPlanEntryInfo)row.getUserObject();
				entry.setMaterial(materialInfo);
			}
		}	
	}
	/*获取物料对应的计划资料的收货提前期*/
	protected int getProduceLeadTime(MaterialInfo materialInfo){
		
		try {
			IMaterialPlan iMatPlan = MaterialPlanFactory.getRemoteInstance();
			MaterialPlanInfo matPlanInfo = iMatPlan.getMaterialPlanInfo("material.id='"+materialInfo.getId().toString()+"'");
		    if(matPlanInfo!=null)
		    	return matPlanInfo.getRecevingLeadTime();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 对物料F7中多选的物料数组进行筛选过滤，如果表中已有某物料，将其赋为null<p>
	 * 过滤条件：如果表中有相同物料编码的行则过滤掉
	 * @param e KDTEditEvent KDTable编辑事件 
	 * @author owen_wen 2010-6-23
	 * @return 筛选后的物料数组
	 */
	private Object[] filterMaterial(KDTEditEvent e){
		KDTable table = getDetailTable();
		if( (e.getValue() != null) 
				&& (e.getValue() instanceof Object[])){
			Object[] materials = (Object[]) e.getValue();
							
			int activeRow = table.getSelectManager().getActiveRowIndex();
			
			//过滤重复物料
			for(int i = 0; i < materials.length; i++)
			{
				MaterialInfo material = null;
				if(materials[i] instanceof MaterialInfo)
					material = (MaterialInfo)materials[i];
				
				for(int j = 0; j < table.getRowCount(); j++)
				{
					//如果j移动到活动行上，不需要判断是否重复，因为该活动行有可能选择了多个物料
					if(j != activeRow)
					{
						if(table.getRow(j).getCell("material.number").getValue() == null)
							continue;   //碰到没有物料的行，跳过
												
						String preMateralNumber = ""; //获取第j行的物料编码
						
						//有可能是刚新增未保存的物料行，getValue得到的还不是MaterialInfo对象，因此要判断处理
						if (table.getRow(j).getCell("material.number").getValue() instanceof MaterialInfo) 
							preMateralNumber = ((MaterialInfo)table.getRow(j).getCell("material.number").getValue()).getNumber();
						else
							preMateralNumber = table.getRow(j).getCell("material.number").getValue().toString();
																		
						if(material.getNumber().equals(preMateralNumber)){
							materials[i] = null; //将第i个物料，标记为空
							break; 		//物料重复,且合同重复，直接跳出循环，对下一行物料进行判断
						}
					}
				}
			}
			
			return materials;
		}else {
			return null;
		}
	}
	 public String precisonFormat(int precision){
	    	
	    	StringBuffer sb = new StringBuffer();
	    	sb.append("#######.00");
	    	for(int i=2;i<precision;i++){
	    		sb.append("0");
	    	}
	    	
	    	return sb.toString();
	    }
	
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		int cIndex = e.getColIndex();
		if("material.number".equals(kdtEntrys.getColumnKey(cIndex))){
			if(e.getValue() != null){
				Object[] materials = filterMaterial(e);
				if (materials != null){
					insertDataToRow(materials);	
				}
				   
			}
		}
		else if("quantity".equals(kdtEntrys.getColumnKey(cIndex))){
			int rIndex = e.getRowIndex();
	    	IRow row = kdtEntrys.getRow(rIndex);
	    	ICell cell = row.getCell(cIndex);
	    	if(cell.getValue()!=null){
	    		row.getCell("auditQuantity").setValue(cell.getValue());
	    	}
		}
	}

	public void setEntriesPrecision(){
		if(this.editData.getEntrys() != null){
			MaterialEnterPlanEntryCollection cols = editData.getEntrys();
			KDFormattedTextField unitEditor = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);;
			if(cols != null && cols.size() > 0){
				for(int i=0;i<cols.size();i++){
					MaterialInfo info = cols.get(i).getMaterial();
					if (info != null){
						kdtEntrys.getCell(i, "auditQuantity").getStyleAttributes().setNumberFormat(precisonFormat(info.getBaseUnit().getQtyPrecision()));
						kdtEntrys.getCell(i, "quantity").getStyleAttributes().setNumberFormat(precisonFormat(info.getBaseUnit().getQtyPrecision()));
						unitEditor.setPrecision(info.getBaseUnit().getQtyPrecision());
					}
					KDTDefaultCellEditor editor = new KDTDefaultCellEditor(unitEditor);
					kdtEntrys.getCell(i, "quantity").setEditor(editor);
				  	kdtEntrys.getCell(i, "auditQuantity").setEditor(editor);				   
				}
			}
		}
	}
	
	/**
	 *  套打对应的Query
	 */
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.material.app.MaterialEnterPlanQuery");
	}
	
	/**
	 * 套打对应的目录
	 */
	protected String getTDFileName() {
		return "/bim/fdc/material/materialEnterPlan";
	}

	/**
	 * 套打前的检查，检查是否可以打印
	 * @author owen_wen 2010-09-14
	 */
	private void checkBeforePrint(){
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		checkBeforePrint();
		MaterialPrintDataProvider data = new MaterialPrintDataProvider(editData.getString("id"), getTDQueryPK());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
	}
	
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		checkBeforePrint();		
		MaterialPrintDataProvider data = new MaterialPrintDataProvider(editData.getString("id"), getTDQueryPK());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
	}
}