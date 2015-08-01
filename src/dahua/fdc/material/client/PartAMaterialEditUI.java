/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.MaterialPromptSelector;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.invite.InviteProjectEntryCollection;
import com.kingdee.eas.fdc.invite.InviteProjectEntryInfo;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillCollection;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillFactory;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillInfo;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryInfo;
import com.kingdee.eas.fdc.material.MaterialException;
import com.kingdee.eas.fdc.material.PartAMaterialEntryCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.fdc.material.PartAMaterialFactory;
import com.kingdee.eas.fdc.material.PartAMaterialInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 材料明细单 编辑界面
 */
public class PartAMaterialEditUI extends AbstractPartAMaterialEditUI {
	private static final String DESCRIPTION = "description";
	private static final long serialVersionUID = 1L;
	private static final Logger logger = CoreUIObject.getLogger(PartAMaterialEditUI.class);
	private int unitPrecision = 2;
	private int pricePrecision = 2;
	
	/**
	 * 修订前物料ID和Name集
	 */
	private Map materialIDNameMap = new HashMap();
	
	
	public PartAMaterialEditUI() throws Exception {
		super();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		setEntries();
		actionSave.setEnabled(true);
		actionSubmit.setEnabled(true);
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		setEntries();
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		setEntries();
	}

	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		// super.actionTraceDown_actionPerformed(e);
	}

	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		// super.actionTraceUp_actionPerformed(e);
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PartAMaterialFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		FDCTableHelper.disableDelete(kdtEntrys);
		super.onLoad();
		this.numberTxtRate.setPrecision(FDCClientHelper.getExRatePrecOfCurrency(editData.getContractBill().getCurrency().getId().toString()));
		initContractNoF7();
		initMaterialNoF7();
		initCellFormat();

		afterPressDeleteButton();
		setEntries();
		
		kdtEntrys.addKDTEditListener(new KDTEditListener(){

			public void editCanceled(KDTEditEvent e) {}

			public void editStarted(KDTEditEvent e) {}

			public void editStarting(KDTEditEvent e) {}

			public void editStopped(KDTEditEvent e) {	
				KDTableHelper.autoFitRowHeight(kdtEntrys,e.getRowIndex(),5);
			}

			public void editStopping(KDTEditEvent e) {}

			public void editValueChanged(KDTEditEvent e) {}			
		});
		
		materialIDNameMap = getMaterial_ID_Name();
		
		if(this.getUIContext().containsKey("isFromWorkflow")){
			this.txtNumber.setEnabled(false);
		}
		
		//提交状态的单据，编辑界面灰显“保存”按钮
		if(FDCBillStateEnum.SUBMITTED.equals(editData.getState()))
			actionSave.setEnabled(false);
		
		
		if (editData!= null && editData.getContractBill() != null)
			contractBill = editData.getContractBill();
	}
	
	protected void disablePrintFunc() {
		
	}
	
	// 隐藏工作流，审批按钮
	protected void initWorkButton() {
//		this.btnWorkFlowG.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
//		this.btnMultiapprove.setVisible(false);
		this.btnNextPerson.setVisible(false);
		this.btnTraceUp.setVisible(false);
		this.btnTraceDown.setVisible(false);
		this.btnAudit.setVisible(false);

//		this.actionSubmit.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuView.setVisible(false);
		this.menuSubmitOption.setVisible(false);

		this.contAuditor.setVisible(false);
		this.contAuditTime.setVisible(false);
		
		this.menuItemCopyLine.setVisible(false);

		super.initWorkButton();		
		
		this.menuItemImportFromTemplate.setIcon(EASResource.getIcon("imgTbtn_input"));
	}

	private void initCellFormat() {

		this.actionAddNew.setEnabled(false);

		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(new KDDatePicker());
		getDetailTable().getColumn(PartAMaterialContants.ARRIVE_DATE).setEditor(editor);
		FDCHelper.formatTableDate(getDetailTable(), PartAMaterialContants.ARRIVE_DATE);

		KDFormattedTextField originalPriceTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		originalPriceTextField.setPrecision(2);
		originalPriceTextField.setSupportedEmpty(false);
		originalPriceTextField.setNegatived(false);
		originalPriceTextField.setMinimumValue(FDCHelper.ZERO);
		originalPriceTextField.setMaximumValue(new BigDecimal("99999999999"));
		originalPriceTextField.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		ICellEditor originalPriceEditor = new KDTDefaultCellEditor(originalPriceTextField);
		//this.kdtEntrys.getColumn(PartAMaterialContants.ORIGINAL_PRICE).setEditor(originalPriceEditor);

		KDFormattedTextField quantityTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		quantityTextField.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		quantityTextField.setSupportedEmpty(false);
		quantityTextField.setNegatived(false);
		// quantityTextField.setMaximumValue(FDCHelper.MAX_VALUE);
		quantityTextField.setMinimumValue(FDCHelper.ZERO);
		quantityTextField.setMaximumValue(new BigDecimal("99999999999"));
		quantityTextField.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		ICellEditor quantityEditor = new KDTDefaultCellEditor(quantityTextField);
		this.kdtEntrys.getColumn(PartAMaterialContants.QUANTITY).setEditor(quantityEditor);
		this.kdtEntrys.getColumn(PartAMaterialContants.AMOUNT).setEditor(quantityEditor);

		FDCHelper.formatTableNumber(kdtEntrys, PartAMaterialContants.ORIGINAL_PRICE);
		getDetailTable().getColumn(PartAMaterialContants.ORIGINAL_PRICE).getStyleAttributes().setNumberFormat("##.00");
		getDetailTable().getColumn(PartAMaterialContants.ORIGINAL_PRICE).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getDetailTable().getColumn(PartAMaterialContants.PRICE).getStyleAttributes().setNumberFormat("##.00");
		getDetailTable().getColumn(PartAMaterialContants.PRICE).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getDetailTable().getColumn(PartAMaterialContants.AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		getDetailTable().getColumn(PartAMaterialContants.AMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getDetailTable().getColumn(PartAMaterialContants.QUANTITY).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getDetailTable().getColumn(PartAMaterialContants.QUANTITY).getStyleAttributes().setNumberFormat("#,###.00");

		getDetailTable().getColumn(PartAMaterialContants.CONTRACT_NAME).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		getDetailTable().getColumn(PartAMaterialContants.MATERIAL_NAME).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		getDetailTable().getColumn(PartAMaterialContants.MODEL).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		getDetailTable().getColumn(PartAMaterialContants.UNIT).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		getDetailTable().getColumn(PartAMaterialContants.PRICE).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		getDetailTable().getColumn(PartAMaterialContants.AMOUNT).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);

	}

	/**
	 * 材料合同F7
	 * @throws Exception 
	 */
	private void initMaterialNoF7() throws Exception  {
		KDBizPromptBox kdtEntrys_materialNumber_PromptBox = new KDBizPromptBox();
		
		// 使用自定义的左树右表的物料F7， Added by Owen_wen 2010-9-2
		 kdtEntrys_materialNumber_PromptBox.setSelector(new MaterialPromptSelector(this));  
//		kdtEntrys_materialNumber_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		kdtEntrys_materialNumber_PromptBox.setEntityViewInfo(view);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("model");
		sic.add("baseUnit.*");
		sic.add("*");
		kdtEntrys_materialNumber_PromptBox.setSelectorCollection(sic);
		kdtEntrys_materialNumber_PromptBox.setVisible(true);
		kdtEntrys_materialNumber_PromptBox.setEditable(true);
		kdtEntrys_materialNumber_PromptBox.setDisplayFormat("$number$");
		kdtEntrys_materialNumber_PromptBox.setEditFormat("$number$");
		kdtEntrys_materialNumber_PromptBox.setCommitFormat("$number$");
		kdtEntrys_materialNumber_PromptBox.setEnabledMultiSelection(true);
		KDTDefaultCellEditor kdtEntrys_materialNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_materialNumber_PromptBox);
		this.kdtEntrys.getColumn(PartAMaterialContants.MATERIAL_NUMBER).setEditor(kdtEntrys_materialNumber_CellEditor);
		ObjectValueRender kdtEntrys_materialNumber_OVR = new ObjectValueRender();
		kdtEntrys_materialNumber_OVR.setFormat(new BizDataFormat("$number$"));
		this.kdtEntrys.getColumn(PartAMaterialContants.MATERIAL_NUMBER).setRenderer(kdtEntrys_materialNumber_OVR);
	}

	/**
	 * 主合同F7
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void initContractNoF7() throws EASBizException, BOSException {
		String contractBillId = editData.getContractBill().getId().toString();
		boolean isPartAMaterialCon = editData.getContractBill().isIsPartAMaterialCon();
		boolean isCostSplit = editData.getContractBill().isIsCoseSplit();
		CtrlUnitInfo CU = SysContext.getSysContext().getCurrentCtrlUnit();

		Set authorizedOrgs = new HashSet();
		Map orgs = null;
		if (orgs == null) {
			orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter,
					null, null, null);
		}
		if (orgs != null) {
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while (it.hasNext()) {
				authorizedOrgs.add(it.next());
			}
		}

		KDBizPromptBox prmt = new KDBizPromptBox();
		prmt.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQuery");
		prmt.setVisible(true);
		prmt.setEditable(true);
		prmt.setDisplayFormat("$number$");
		prmt.setEditFormat("$number$");
		prmt.setCommitFormat("$number$");

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		// 所有组织都可F7
		if (!CU.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			filter.getFilterItems().add(
					new FilterItemInfo("CU.id", CU.getId().toString()));
		}
		// f7弹出的时候，甲供材合同只能选已审批状态的非甲供合同，而非甲供的只能选自身(包括save,submit,audited状态)
		if (isPartAMaterialCon) {
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(
					new FilterItemInfo("isPartAMaterialCon", Boolean.valueOf(isPartAMaterialCon),
							CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs, CompareType.INCLUDE));
			FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.longNumber", orgUnit.getLongNumber() + "%", CompareType.LIKE));
		}

		if (isCostSplit) {
			FilterInfo filterMySelf = new FilterInfo();
			filterMySelf.getFilterItems().add(new FilterItemInfo("id", contractBillId));
			filter.mergeFilter(filterMySelf, "or");
		}

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("partB.name"));

		view.setFilter(filter);
		prmt.setEntityViewInfo(view);
		prmt.setSelectorCollection(sic);

		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(prmt);
		this.kdtEntrys.getColumn(PartAMaterialContants.CONTRACT_NUMBER)
				.setEditor(cellEditor);
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new BizDataFormat("$number$"));
		this.kdtEntrys.getColumn(PartAMaterialContants.CONTRACT_NUMBER)
				.setRenderer(render);

	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();
	}

	public void loadFields() {
		super.loadFields();
		addEmptyRows();		// 默认显示20行,

		/**
		 * 在此调用，防止保存后被关联的分录编辑解锁
		 */
		try {
			isRefered();
		} catch (MaterialException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
	}

	/**
	 * 计算总金额，并载入到控件上。
	 * 
	 * @author owen_wen 2011-05-09
	 */
	private void loadTotalAmt() {
		BigDecimal totalAmt = FDCHelper.ZERO;

		for (int i = 0; i < kdtEntrys.getRowCount3(); i++) {
			if (kdtEntrys.getRow(i).getCell(PartAMaterialContants.AMOUNT).getValue() != null) {
				totalAmt = totalAmt.add((BigDecimal) kdtEntrys.getRow(i).getCell(PartAMaterialContants.AMOUNT).getValue());
			}
		}

		this.txtTotalAmt.setValue(totalAmt);
	}

	/**
	 * 保存时先去掉空行，再补足空行，保证至少有20行分录<p>
	 * 因为storeFields()会改变editData的值，从而会导致在序时薄界面点编辑进入，不修改退出会提示已修改是否保存，
	 * 所以先去空行再补上空行。
	 * @author owen_wen 2010-12-21
	 */
	public void storeFields() {
		removeEmptyRows();
		super.storeFields();
		addEmptyRows();
	}

	/**
	 * 添加空行
	 * @author owen_wen 2010-12-21
	 */
	private void addEmptyRows() {
		int rowCount = this.getDetailTable().getRowCount();
		int count = 20;
		if (rowCount < count) {
			for (int i = 0; i < (count - rowCount); i++) {
				getDetailTable().addRow();
			}
		}
	}

	/**
	 * 去除空行
	 * @author owen_wen 2010-12-21
	 */
	private void removeEmptyRows() {
		for (int i = 0; i < getDetailTable().getRowCount(); i++){
			IRow row = getDetailTable().getRow(i);
			if (isEmptyRow(row)){
				getDetailTable().removeRow(i);
				i--;
			}			
		}
	}

	/**
	 * 是否是空行
	 * @param row 要判断的行
	 * @return true 是空行，false不是空行
	 * @author owen_wen 2010-12-21
	 */
	private boolean isEmptyRow(IRow row) {
		return row.getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue() == null
				&& row.getCell(PartAMaterialContants.MATERIAL_NUMBER).getValue() == null
				&& row.getCell(PartAMaterialContants.ORIGINAL_PRICE).getValue() == null
				&& row.getCell(PartAMaterialContants.QUANTITY).getValue() == null
				&& row.getCell(PartAMaterialContants.ARRIVE_DATE).getValue() == null
				&& row.getCell(PartAMaterialContants.DESCRIPTION).getValue() == null;
	}

	/**
	 * 描述：子类必须实现的父类方法，并初始化部分表头数据
	 */
	protected IObjectValue createNewData() {

		PartAMaterialInfo objectValue = new PartAMaterialInfo();
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		// objectValue.setCreateTime(new Timestamp(new Date().getTime()));
		try {
			objectValue.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			handUIException(e);
		}
		
		// 如果是修订单据，先保存单据再提交后会报中断，contractBill为null，
		// 因此加上如下的判断,处理此种特殊情况  added By owen_wen 
		if (this.getUIContext().get("willRevise") != null){
			if (editData!= null && editData.getContractBill() != null)
				contractBill = editData.getContractBill();
		}
		ContractBillInfo contractBillInfo = this.contractBill;
		String contractBillNumber = (String) getUIContext().get("contractBillNumber");
		contractBillInfo.setNumber(contractBillNumber);
		objectValue.setContractBill(contractBillInfo);
		if (contractBillInfo.getName() != null)
			txtContractName.setText(contractBillInfo.getName());
		if (contractBillInfo.getNumber() != null)
			txtContractNumber.setText(contractBillInfo.getNumber());
		if (contractBillInfo.getCurrency() != null)
			txtCurrency.setText(contractBillInfo.getCurrency().toString());

		return objectValue;
	}

	/**
	 * @return 分录selectors
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		// 补充父类的sic
		sic.add(new SelectorItemInfo("entrys.material.baseUnit.*"));
		sic.add(new SelectorItemInfo("entrys.material.*"));
		sic.add(new SelectorItemInfo("state"));
		//Add by zhiyuan_tang 2010/11/02  增加合同的isPartAMaterialCon、isCoseSplit属性
		sic.add(new SelectorItemInfo("contractBill.isPartAMaterialCon"));
		sic.add(new SelectorItemInfo("contractBill.isCoseSplit"));
	
		return sic;
	}

	/**
	 * @return 分录表
	 */
	protected KDTable getDetailTable() {
		return kdtEntrys;
	}

	/**
	 * 描述：编辑结束事件,选择合同与材料编码后自动带出其它信息
	 */
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		//如果是“领用合同编码”编辑结束，
		if (e.getColIndex() == getDetailTable().getColumn(PartAMaterialContants.CONTRACT_NUMBER).getColumnIndex()) {
			if (e.getValue() != null) {
				if (e.getOldValue() != null) {
					FDCBillInfo oldInfo = (FDCBillInfo) e.getOldValue();
					if (oldInfo != null
							&& oldInfo.getId().toString().equals(((ContractBillInfo) e.getValue()).getId().toString())) {
						return;
					}
				}
				ICell contractCell = getDetailTable().getCell(e.getRowIndex(),
						PartAMaterialContants.CONTRACT_NAME);
				ICell contractIdCell = getDetailTable().getCell(
						e.getRowIndex(), PartAMaterialContants.CONTRACT_ID);
				if (e.getValue() instanceof ContractBillInfo) {
					contractCell.setValue(((ContractBillInfo) e.getValue()).getName());
					contractIdCell.setValue(((ContractBillInfo) e.getValue()).getId());
					getDetailTable().getCell(e.getRowIndex(), PartAMaterialContants.ARRIVE_DATE).setValue(
							new Date());
					if (kdtEntrys.getRowCount() > 1) {
						if (MsgBox.YES == FDCMsgBox.showConfirm2(this, "是否批量选定该领用合同?")) {
						for (int i = e.getRowIndex(); i < getDetailTable().getRowCount(); i++) {
							getDetailTable().getCell(i, PartAMaterialContants.CONTRACT_NUMBER).setValue(
									((ContractBillInfo) e.getValue()));
							getDetailTable().getCell(i, PartAMaterialContants.CONTRACT_NAME).setValue(
									((ContractBillInfo) e.getValue()).getName());
							getDetailTable().getCell(i, PartAMaterialContants.CONTRACT_ID).setValue(
									((ContractBillInfo) e.getValue()).getId());
							getDetailTable().getCell(i, PartAMaterialContants.ARRIVE_DATE).setValue(
									new Date());
						}
						}					
					}
				} else {
					contractCell.setValue(((ContractWithoutTextInfo) e
							.getValue()).getName());
					contractIdCell.setValue(((ContractWithoutTextInfo) e
							.getValue()).getId());
				}
			} else {
				ICell nameCell = getDetailTable().getCell(e.getRowIndex(),
						PartAMaterialContants.CONTRACT_NAME);
				nameCell.setValue(null);
			}
		}
		
		if (getDetailTable().getCell(e.getRowIndex(), PartAMaterialContants.ORIGINAL_PRICE).getValue() != null
				&& e.getValue() != null) {
			calculateAmount(e);
			FDCTableHelper.apendFootRow(kdtEntrys, new String[] { "amount" });
			loadTotalAmt();
		}
		
		//如果是"物料编码"编辑结束，
		if (e.getColIndex() == getDetailTable().getColumn(PartAMaterialContants.MATERIAL_NUMBER).getColumnIndex()) {
             
			if (e.getValue() != null ) {
				
				if(e.getOldValue() != null && e.getValue().equals(e.getOldValue())){
					abort();
				}								
				Object[] materials = filterMaterial(e);
				insertDataToRow(e.getRowIndex(), materials);		
			}
		}
	}

	/**
	 * 往分录行中插入数据
	 * @param rowIndex 光标所在行
	 * @param materials 待插入的物料数组，若其中某个为null，表示有重复不需要插入
	 */
	private void insertDataToRow(int rowIndex, Object[] materials) {
	
		//获取活动行的合同编码和合同名称
		int activeRow = kdtEntrys.getSelectManager().getActiveRowIndex();
		String contractName = "";
		ContractBillInfo contract = null;
		if (getDetailTable().getCell(activeRow, PartAMaterialContants.CONTRACT_NUMBER).getValue() != null){
			contract = ((ContractBillInfo) getDetailTable().getCell(activeRow, PartAMaterialContants.CONTRACT_NUMBER).getValue());
			contractName = contract.getName();
		}
		KDTable table  = getDetailTable();
		IRow row = table.getRow(rowIndex);
		for(int i = 0; i < materials.length; i++){
			MaterialInfo material = null;
			if(materials[i] instanceof MaterialInfo)
				material = (MaterialInfo)materials[i];
			
			if(i == 0 && material == null)
			{
				table.removeRow(rowIndex);
				continue;
			}
			
			//如果是多选(即i>0时)，则要判断是否要addLine（即新增一行分录）
			if(i > 0){
				if(material != null)
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
			
			// 当 i==0, and material != null 的情况，直接将material放入到当前行中
			row.getCell(PartAMaterialContants.MATERIAL_NUMBER).setValue(material); 
//			row.getCell(PartAMaterialContants.MATERIAL_NUMBER).setValue(material.getNumber());  这种写法有问题，第一次编辑时，如果不录合同时，保存后会自动消失
			row.getCell(PartAMaterialContants.MATERIAL_NAME).setValue(material.getName());
			row.getCell(PartAMaterialContants.MODEL).setValue(material.getModel());
			if(material.getBaseUnit() != null){
				row.getCell(PartAMaterialContants.UNIT).setValue(material.getBaseUnit().getName());
			}
			row.getCell(PartAMaterialContants.MATERIAL_ID).setValue(material.getId());
			
			//复制活动行合同编码和名称到row中
			row.getCell(PartAMaterialContants.CONTRACT_NUMBER).setValue(contract);
			if (contract != null){
				row.getCell(PartAMaterialContants.CONTRACT_ID).setValue(contract.getId());
				row.getCell(PartAMaterialContants.CONTRACT_NAME).setValue(contractName);
			}
			KDFormattedTextField qty = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
			KDFormattedTextField price = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
			
			if(material.getBaseUnit() != null){
				unitPrecision = material.getBaseUnit().getQtyPrecision();
			}
			pricePrecision = material.getPricePrecision();
			
			qty.setPrecision(unitPrecision);
			price.setPrecision(pricePrecision);
			
			KDTDefaultCellEditor qtyEditor = new KDTDefaultCellEditor(qty);
			KDTDefaultCellEditor priceCellEditor = new KDTDefaultCellEditor(price);
			
			row.getCell("quantity").setEditor(qtyEditor);
			row.getCell("originalPrice").setEditor(priceCellEditor);		
			row.getCell("price").setEditor(priceCellEditor);			
			row.getCell("amount").setEditor(priceCellEditor);
		
			row.getCell("quantity").getStyleAttributes().setNumberFormat(precisionFormat(unitPrecision));
			row.getCell("originalPrice").getStyleAttributes().setNumberFormat(precisionFormat(pricePrecision));
			row.getCell("price").getStyleAttributes().setNumberFormat(precisionFormat(pricePrecision));
			row.getCell("amount").getStyleAttributes().setNumberFormat(precisionFormat(pricePrecision));
			row.getCell(PartAMaterialContants.ARRIVE_DATE).setValue(new Date());
			
			if(row.getUserObject() != null && 
					row.getUserObject() instanceof PartAMaterialEntryInfo)
			{
				PartAMaterialEntryInfo entry = (PartAMaterialEntryInfo)row.getUserObject();
				entry.setMaterial(material);
			}
		}	
	}
	
	/**
	 * 统计（施工）合同对应的进场计划所包含的物料id集
	 * @param contractID
	 * @return
	 * @throws BOSException 
	 */
	private Set calMaterialEnterPlan(String contractID) throws BOSException{
		Set resultSet = new HashSet();
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("entrys.material.id"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill", contractID, CompareType.EQUALS));
		view.setSelector(sic);
		view.setFilter(filter);
		
		MaterialEnterPlanBillCollection mepbc = MaterialEnterPlanBillFactory.getRemoteInstance().getMaterialEnterPlanBillCollection(view);
		for (Iterator it = mepbc.iterator(); it.hasNext();){
			MaterialEnterPlanBillInfo info = (MaterialEnterPlanBillInfo) it.next();
			for (Iterator it2 = info.getEntrys().iterator(); it2.hasNext();){
				MaterialEnterPlanEntryInfo entryInfo = (MaterialEnterPlanEntryInfo) it2.next();
				resultSet.add(entryInfo.getMaterial().getId().toString());
			}
		}
		
		return resultSet;
	}
	
	/**
	 * 获取修订前editData中的物料ID和Name，并放到Map中
	 * @author owen_wen
	 * @return
	 */
	private Map getMaterial_ID_Name(){
		Map materialIDNameMap = new HashMap();
		
		if (editData == null || editData.getId() == null) 
			return materialIDNameMap;
		
		for (Iterator it = editData.getEntrys().iterator(); it.hasNext();){
			PartAMaterialEntryInfo info = (PartAMaterialEntryInfo)it.next();
			materialIDNameMap.put(info.getMaterial().getId().toString(), info.getMaterial().getName());
		}		
		return materialIDNameMap;
	}
	
	
	/**
	 * 检查分录中是否删除了被进场计划引用的物料
	 * @author owen_wen
	 * @throws BOSException 
	 */
	private void checkIfDeleteMaterialByEnterPlan(Map matIDs_contractIds) throws BOSException {
		String msg = "已做进场计划不能删除此物料！";
		StringBuffer sb = new StringBuffer();
				
		for(Iterator it = matIDs_contractIds.keySet().iterator(); it.hasNext();){
			// 判断该物料是否在材料进场计划中被引用
			String materialID = it.next().toString();
			if ( matIDs_contractIds.get(materialID) instanceof String) { //放入的确实是合同id，而不是true对象
				String contractID = matIDs_contractIds.get(materialID).toString();				
				if ( calMaterialEnterPlan(contractID).contains(materialID) ){// 被进场计划引用
					sb.append( materialIDNameMap.get(materialID) + ", "); 
				}
			}
		}
		
		int length = sb.length();
		if (length > 0){
			String tempS = sb.toString().substring(0, length -2);
			FDCMsgBox.showConfirm2(tempS + " " + msg);
			SysUtil.abort();
		}
	}

	/**
	 * 如果有相同合同编码和相同物料编码的行则过滤掉
	 * @param e KDTEditEvent KDTable编辑事件 
	 */
	private Object[] filterMaterial(KDTEditEvent e){
		KDTable table = getDetailTable();
		if( (e.getValue() != null) 
				&& (e.getValue() instanceof Object[])){
			Object[] materials = (Object[]) e.getValue();
							
			int activeRow = table.getSelectManager().getActiveRowIndex();
			String activeRowContractNumber = "";
			if (table.getRow(activeRow).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue() instanceof ContractBillInfo) 
				activeRowContractNumber = ((ContractBillInfo)table.getRow(activeRow).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue()).getNumber();
			else
				if(table.getRow(activeRow).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue() != null)
					activeRowContractNumber = table.getRow(activeRow).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue().toString();
			
			//过滤物料重复，且合同重复的物料
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
						if(table.getRow(j).getCell(PartAMaterialContants.MATERIAL_NUMBER).getValue() == null)
							continue;   //碰到没有物料的行，跳过
												
						String preMateralNumber = ""; //获取第j行的物料编码
						
						//有可能是刚新增未保存的物料行，getValue得到的还不是MaterialInfo对象，因此要判断处理
						if (table.getRow(j).getCell(PartAMaterialContants.MATERIAL_NUMBER).getValue() instanceof MaterialInfo) 
							preMateralNumber = ((MaterialInfo)table.getRow(j).getCell(PartAMaterialContants.MATERIAL_NUMBER).getValue()).getNumber();
						else
							preMateralNumber = table.getRow(j).getCell(PartAMaterialContants.MATERIAL_NUMBER).getValue().toString();
						
						//碰到没有合同的行
						if (table.getRow(j).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue() == null){
							if (activeRowContractNumber.equals("")){ //如果当前合同也为空
								if(material.getNumber().equals(preMateralNumber)){ //物料重复,且合同为空
									materials[i] = null; //将第i个物料，标记为空
								}
							}
							continue; // 直接跳出本次循环，对table中的下一行物料进行判断
						}
							
						String preContractNumber = "";  //获取当第j行的合同编码
						
						if (table.getRow(j).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue() instanceof ContractBillInfo) 
							preContractNumber = ((ContractBillInfo)table.getRow(j).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue()).getNumber();
						else
							preContractNumber = table.getRow(j).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue().toString();
												
						if(material.getNumber().equals(preMateralNumber) && preContractNumber.equals(activeRowContractNumber)){
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
	
	// 计算金额
	private void calculateAmount(
			com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e)
			throws Exception {
		Object objRate = numberTxtRate.getBigDecimalValue();
		Object objOPrice = getDetailTable().getCell(e.getRowIndex(),
				PartAMaterialContants.ORIGINAL_PRICE).getValue();
		BigDecimal oPrice = new BigDecimal(objOPrice.toString());
		BigDecimal rate = (BigDecimal) objRate;
		BigDecimal price = FDCHelper.ZERO;

		if (objOPrice != null && objRate != null) {
			// 本币单价=原币单价*rate
			price = oPrice.multiply(rate);
			getDetailTable().getCell(e.getRowIndex(),
					PartAMaterialContants.PRICE).setValue(price);
		}
		Object objNum = getDetailTable().getCell(e.getRowIndex(),
				PartAMaterialContants.QUANTITY).getValue();
		if (objNum != null) {
			BigDecimal num = new BigDecimal(objNum.toString());
			BigDecimal amount = price.multiply(num);
			getDetailTable().getCell(e.getRowIndex(),
					PartAMaterialContants.AMOUNT).setValue(amount);
			if (PartAUtils.getAmtSize(amount.toString()) > 11) {
				MsgBox.showWarning(PartAUtils.getRes("outOfAmount"));
				getDetailTable().getCell(e.getRowIndex(), MaterialConfirmContants.QUANTITY).setValue(
						FDCHelper.ZERO);
				getDetailTable().getCell(e.getRowIndex(), MaterialConfirmContants.ORIGINAL_PRICE).setValue(
						FDCHelper.ZERO);
				getDetailTable().getCell(e.getRowIndex(), MaterialConfirmContants.PRICE).setValue(FDCHelper.ZERO);
				getDetailTable().getCell(e.getRowIndex(), MaterialConfirmContants.AMOUNT)
						.setValue(FDCHelper.ZERO);
			}
			// getDetailTable().getCell(e.getRowIndex(),PartAMaterialContants.
			// AMOUNT).setValue(amount);
		}
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return new PartAMaterialEntryInfo();
	}
	protected void verifyInputForSubmint() throws Exception {
		if (this.editData != null && checkIsEmptyEntry()){
			MsgBox.showWarning(this, FDCClientUtils.getRes("atLeastOneEntry"));
			SysUtil.abort();
		}
		verifyInputForSave();
		
		// 材料的总金额超过了合同的总金额，要给出提示
		if (FDCHelper.compareTo(txtTotalAmt.getBigDecimalValue(), contractBill.getAmount()) > 0) {
			int result = FDCMsgBox.showConfirm2(this, "材料的总金额超过了合同的总金额，是否确定提交?");
			if (result == FDCMsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
	}
	
	/**
	 * 检查是否是空分录
	 * @return true没有分录，false有分录
	 */
	private boolean checkIsEmptyEntry(){
//		PartAMaterialEntryCollection entrys = editData.getEntrys(); 
//		if (entrys.size()<1)
//			return true;
//		
//		for (int i= 0; i< entrys.size(); i++){
//			if (entrys.get(i).getMaterial() != null)
//				return false;
//		}

		for (int i = 0; i < getDetailTable().getRowCount(); i++){
			if (!isEmptyRow(getDetailTable().getRow(i)))
				return false;
		}
		
		return true;
	}
	
	// 保存前校验空值和重复行
	protected void verifyInputForSave() throws Exception {
		if(getNumberCtrl().isEnabled()){
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
				
		Set set = new HashSet();
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			if (isEmptyRow(row)) {
				continue;
			}
			
			verifyInput(this, row, PartAMaterialContants.MATERIAL_NUMBER);
			verifyInput(this, row, PartAMaterialContants.ORIGINAL_PRICE);
			verifyInput(this, row, PartAMaterialContants.QUANTITY);
			String materialStr = "";
			if (row.getCell("materialId").getValue()!=null) {
				materialStr = row.getCell("materialId").getValue().toString();
			}else{
				continue;
			}
			String contractStr = "null";
			if (row.getCell("contractId").getValue() != null)
				contractStr = row.getCell("contractId").getValue().toString();
			if (set.contains(contractStr + materialStr)) {
				String info = PartAUtils.getRes("repeatData");
				String[] args = new String[] { (i+1) + "" };
				MsgBox.showWarning(this, FDCClientHelper.formatMessage(info, args));
				SysUtil.abort();
			} else {
				set.add(contractStr + materialStr);
			}
		}
	
	}

	/**
	 * 重写父类方法，校验行单元值是否为空
	 * 
	 * @param ui
	 * @param row
	 * @param key
	 */
	public void verifyInput(CoreUIObject ui, IRow row, String key) {
		int colIndex = getDetailTable().getColumnIndex(key);
		int rowIndex = row.getRowIndex();
		if (FDCHelper.isEmpty(row.getCell(key).getValue())) {
			getDetailTable().getEditManager().editCellAt(rowIndex, colIndex);
			rowIndex++;
			String headValue = (String) getDetailTable().getHeadRow(0).getCell(
					key).getValue();
			String info = PartAUtils.getRes("argsCanNotBeNull");
			String[] args = new String[] { rowIndex + "", headValue };
			MsgBox.showWarning(ui, FDCClientHelper.formatMessage(info, args));

			SysUtil.abort();
		}
	}

	public void onShow() throws Exception {
		super.onShow();

		actionAudit.setVisible(false);
		actionAuditResult.setVisible(false);
				
		this.actionNextPerson.setVisible(true);
		this.actionAuditResult.setVisible(true);

		if (getOprtState().equals(STATUS_EDIT)) {
			actionAddLine.setEnabled(true);
			actionInsertLine.setEnabled(true);
			actionRemoveLine.setEnabled(true);
			setUITitle(getRes("partATitleEdit"));
		} else if (getOprtState().equals(STATUS_ADDNEW)) {
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);

			actionSave.setEnabled(true);
			actionSubmit.setEnabled(true);
			actionAddLine.setEnabled(true);
			actionRemoveLine.setEnabled(true);
			actionInsertLine.setEnabled(true);
			setUITitle(getRes("partATitleAddNew"));
		} else {
			actionEdit.setEnabled(true);
			actionRemove.setEnabled(false);
			actionSave.setEnabled(false);

			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			setUITitle(getRes("partATitleView"));
			if (getOprtState().equals(STATUS_FINDVIEW)) {
				txtNumber.setEditable(false);
				txtNumber.setEnabled(false);
			}
		}
		/**
		 * 材料明细单，当已审批状态时，查看界面的修改按钮不可用。 --jiadong
		 */
		if(STATUS_VIEW.equals(this.getOprtState())){
			if(editData!=null&&FDCBillStateEnum.AUDITTED.equals(editData.getState())){
				this.actionEdit.setEnabled(false);
			}
		}
	}

	protected void insertLine(KDTable table) {
		super.insertLine(table);
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			rowIndex = table.getRowCount() - 1;
		}
		table.getCell(rowIndex, PartAMaterialContants.QUANTITY).setValue(null);
	}

	protected void addLine(KDTable table) {
		super.addLine(table);
		if (table.getRowCount() == 0) {
			return;
		} else {
			int rowIndex = table.getRowCount();
			table.getCell(rowIndex - 1, PartAMaterialContants.QUANTITY).setValue(null);
		}
	}
	
	/**
	 * 当选中Cell按Delete键时
	 * <p>
	 * 1.删除当前单元格数据与之关联的数据也做删除; 2.非编辑状态的不能删除；
	 * <p>
	 * 
	 * @author pengwei_hou Date 2008-9-24
	 */
	private void afterPressDeleteButton() {
		kdtEntrys.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					Set set = new HashSet();
					set.add(PartAMaterialContants.CONTRACT_NAME);
					set.add(PartAMaterialContants.MATERIAL_NAME);
					set.add(PartAMaterialContants.UNIT);
					set.add(PartAMaterialContants.MODEL);
					set.add(PartAMaterialContants.PRICE);
					set.add(PartAMaterialContants.AMOUNT);

					for (int i = 0; i < kdtEntrys.getSelectManager().size(); i++) {
						KDTSelectBlock block = kdtEntrys.getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++) {
							for (int colIndex = block.getBeginCol(); colIndex <= block.getEndCol(); colIndex++) {
								String colKey = getDetailTable().getColumnKey(colIndex);
								if (!set.contains(colKey)) {

									KDTEditEvent event = new KDTEditEvent(kdtEntrys, null, null, rowIndex,
											colIndex, true, 1);
									getDetailTable().getCell(rowIndex, colIndex).setValue(null);
									try {
										kdtEntrys_editStopped(event);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		});
	}

	/**
	 * 分录被引用不能删除
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		verifyIfRefByEnterPlanBill();
		verifyIfRefByConfirmBill();
		super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * 检查是否被进场计划引用
	 * 
	 * @throws BOSException
	 */
	private void verifyIfRefByEnterPlanBill() throws BOSException {
		int[] selectRowIdxs = KDTableUtil.getSelectedRows(kdtEntrys);
		Map matIDs_contractIds = new HashMap();

		for (int i = 0; i < selectRowIdxs.length; i++) {
			IRow row = this.getDetailTable().getRow(selectRowIdxs[i]);
			
			if (row.getCell("materialId").getValue() == null) {
				continue;
			}

			String materialId = row.getCell("materialId").getValue().toString();

			if (row.getCell("contractId").getValue() != null) {
				matIDs_contractIds.put(materialId, row.getCell("contractId").getValue().toString());
			} else {
				matIDs_contractIds.put(materialId, Boolean.TRUE);
			}
		}
		
		checkIfDeleteMaterialByEnterPlan(matIDs_contractIds);
	}

	/**
	 * 检查是否被材料确认单引用
	 * 
	 * @throws BOSException
	 * @throws MaterialException
	 */
	private void verifyIfRefByConfirmBill() throws BOSException, MaterialException {
		String[] pk = new String[editData.getEntrys().size()];
		int index = 0;
		for (int i = 0; i < getDetailTable().getSelectManager().size(); i++) {
			KDTSelectBlock block = getDetailTable().getSelectManager().get(i);
			for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++) {
				for (int colIndex = block.getBeginCol(); colIndex <= block.getEndCol(); colIndex++) {

					KDTEditEvent event = new KDTEditEvent(kdtEntrys, null,null, rowIndex, colIndex, true, 1);
					Object obj = getDetailTable().getCell(event.getRowIndex(),PartAMaterialContants.ID).getValue();
					if (obj != null) {
						pk[index] = obj.toString();
						++index;
					}
				}
			}
		}

		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FPartAMaterialEntryId from T_PAM_MaterialConfirmBillEntry");

		IRowSet set = builder.executeQuery();
		Set confmSet = new HashSet();
		try {
			while (set.next()) {
				String fid = set.getString("FPartAMaterialEntryId");
				confmSet.add(fid);
			}
		} catch (SQLException e2) {
			handUIException(e2);
		}
		for (int i = 0; i < pk.length; i++) {
			if (pk[i]!= null&&confmSet.contains(pk[i])) {
				throw new MaterialException(MaterialException.REF_NOT_DELETE);
			}
		}
	}

	/**
	 * 分录被引用时不允许修改
	 * 
	 * @author pengwei_hou Date: 2008-09-24
	 * @throws BOSException
	 * @throws MaterialException
	 */
	private void isRefered() throws BOSException, MaterialException {
		ArrayList pkList = new ArrayList();
		for (int i = 0; i < editData.getEntrys().size(); i++) {
			if (editData.getEntrys().get(i) != null 
					&& editData.getEntrys().get(i).getId()!=null){
				pkList.add(editData.getEntrys().get(i).getId().toString());
			}
		}

		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FPartAMaterialEntryId from T_PAM_MaterialConfirmBillEntry");

		IRowSet set = builder.executeQuery();
		Set confmSet = new HashSet();
		try {
			while (set.next()) {
				String fid = set.getString("FPartAMaterialEntryId");
				confmSet.add(fid);
			}
		} catch (SQLException e) {
			handUIException(e);
		}
		
		// MsgBox.showWarning(this,"分录被确认单关联，不允许修改！");
		for (int i = 0; i < pkList.size(); i++){
			if (confmSet.contains(pkList.get(i))) {
				getDetailTable().getRow(i).getStyleAttributes().setLocked(true);
				getDetailTable().getRow(i).getStyleAttributes().setBackground(FDCColorConstants.lockColor);
			}
		}
	}

	private String getRes(String resName) {
		return PartAUtils.getRes(resName);
	}

	/*
	 * 从关联的中标通知书获取物料信息
	 * 
	 * */
	
	public void actionImportMaterial_actionPerformed(ActionEvent e)
			throws Exception {

		if (this.kdtEntrys.getRowCount() >= 0) {

			int nResult = MsgBox.showConfirm2New(null,"引入材料设备明细将覆盖表中的数据，是否继续");
			if (nResult != JOptionPane.NO_OPTION) {
			     kdtEntrys.removeRows();
				 BOSUuid contractId = this.editData.getContractBill().getId();
				 if(contractId == null){
					 return ;
				 }
			     Map map = PartAMaterialFactory.getRemoteInstance().getMaterialFromInviteProject(contractId);
			     if(map != null && map.get("cols") != null){
			    	 InviteProjectEntryCollection cols = (InviteProjectEntryCollection) map.get("cols");
			    	 if(cols.size()<=0){
			    		 FDCMsgBox.showInfo(this, "由于缺乏源头数据导致无法自动引入材料设备明细，请手工新增。");
			    		 abort();
			    	 }
			    	 
			    	 IRow row = null;
			    	 InviteProjectEntryInfo info = null;
			    	 PartAMaterialEntryInfo entry = null;
			    	 KDFormattedTextField txt =  null;
			    	 KDTDefaultCellEditor editor = null;
			    	for(int i=0;i<cols.size();i++){
			    		row = kdtEntrys.addRow();
			    		entry = (PartAMaterialEntryInfo) createNewDetailData(kdtEntrys);
			    		info = cols.get(i);
			    		setCellEditorWhenImportMaterial(row, info);
			    		
			    		row.getCell("materialName").setValue(info.getMaterial().getName());
			    		row.getCell("materialNumber").setValue(info.getMaterial());
			    		row.getCell("model").setValue(info.getSize());
			    		row.getCell("unit").setValue(info.getMeasureUnit().getName());
			    		row.getCell(DESCRIPTION).setValue(info.getDescription());
			    		row.getCell("arriveDate").setValue(info.getInputDate());
			    		row.getCell("unit").setValue(info.getMeasureUnit().getName());
			    		row.getCell("quantity").setValue(info.getBigDecimal("amount"));
			    		row.getCell("materialId").setValue(info.getMaterial().getId());
			    	}
			     }else{
			    	 FDCMsgBox.showInfo(this, "由于缺乏源头数据导致无法自动引入材料设备明细，请手工新增。");
		    		 abort();
			     }
			}else{
				abort();
			}
		}

	}

	/**
	 * 
	 * 从关联的中标通知书获取物料信息时，设置Cell的Editor
	 * 从actionImportMaterial_actionPerformed()中抽取的方法，重构了一下
	 * @Author：owen_wen
	 * @CreateTime：2012-9-24
	 */
	private void setCellEditorWhenImportMaterial(IRow row, InviteProjectEntryInfo info) {
		KDFormattedTextField txt;
		KDTDefaultCellEditor editor;
		row.getCell("quantity").getStyleAttributes().setNumberFormat(precisionFormat(info.getMeasureUnit().getQtyPrecision()));
		txt = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		txt.setPrecision(info.getMeasureUnit().getQtyPrecision());
		editor = new KDTDefaultCellEditor(txt);
		row.getCell("quantity").setEditor(editor);
		
		row.getCell("originalPrice").getStyleAttributes().setNumberFormat(precisionFormat(info.getMaterial().getPricePrecision()));
		row.getCell("price").getStyleAttributes().setNumberFormat(precisionFormat(info.getMaterial().getPricePrecision()));
		row.getCell("amount").getStyleAttributes().setNumberFormat(precisionFormat(info.getMaterial().getPricePrecision()));
		
		txt = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		txt.setPrecision(info.getMaterial().getPricePrecision());
		editor = new KDTDefaultCellEditor(txt);
		row.getCell("originalPrice").setEditor(editor);
		row.getCell("price").setEditor(editor);
		row.getCell("amount").setEditor(editor);
	}
	
	private void setEntries(){
		if(this.editData.getEntrys() != null){
			PartAMaterialEntryCollection cols = editData.getEntrys();
			if(cols != null && cols.size() > 0){
				KDFormattedTextField qty = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
				KDFormattedTextField price = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
				KDTDefaultCellEditor editor = null;
				
				for(int i=0;i<cols.size();i++){
					MaterialInfo info = cols.get(i).getMaterial();
					if(info != null){
						qty.setPrecision(info.getBaseUnit().getQtyPrecision());
						price.setPrecision(info.getPricePrecision());
					
						editor = new KDTDefaultCellEditor(qty);
						this.kdtEntrys.getCell(i, "quantity").setEditor(editor);
						
						editor = null;
						
						editor = new KDTDefaultCellEditor(price);
						this.kdtEntrys.getCell(i, "originalPrice").setEditor(editor);
						this.kdtEntrys.getCell(i, "price").setEditor(editor);
						this.kdtEntrys.getCell(i, "amount").setEditor(editor);
						
						this.kdtEntrys.getCell(i, "quantity").getStyleAttributes().setNumberFormat(precisionFormat(info.getBaseUnit().getQtyPrecision()));
						this.kdtEntrys.getCell(i, "originalPrice").getStyleAttributes().setNumberFormat(precisionFormat(info.getPricePrecision()));
						this.kdtEntrys.getCell(i, "price").getStyleAttributes().setNumberFormat(precisionFormat(info.getPricePrecision()));
						this.kdtEntrys.getCell(i, "amount").getStyleAttributes().setNumberFormat(precisionFormat(info.getPricePrecision()));
						
					}
				}
			}
		}
		
		loadTotalAmt();
		
		FDCTableHelper.autoFitRowHeight(this.getDetailTable(), DESCRIPTION, 255);
		FDCTableHelper.apendFootRow(kdtEntrys, new String[] { "amount", });
	}
	
	private String precisionFormat(int precision){
		
	   StringBuffer sb = new StringBuffer();
	   sb.append("#######.00");
	   for(int i=2;i<precision;i++){
		   sb.append("0");
	   }
	   return sb.toString();
	}
	
	/**
	 * 打印
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		MaterialPrintDataProvider data = new MaterialPrintDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * 打印预览
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		logger.info("打印预览");
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		MaterialPrintDataProvider data = new MaterialPrintDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	// 获得无文本合同套打对应的目录
	protected String getTDFileName() {
		return "/bim/fdc/material/PartAmaterial";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.material.app.PartAMaterialQuery");
	}
	
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
		FDCTableHelper.autoFitRowHeight(this.getDetailTable(), DESCRIPTION, 255);
	}
	
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionInsertLine_actionPerformed(e);
		FDCTableHelper.autoFitRowHeight(this.getDetailTable(), DESCRIPTION, 255);
	}
	
	
	private ArrayList getImportParam()
    {		
		DatataskParameter datataskParameter = new DatataskParameter();
        datataskParameter.solutionName = getSolutionName();      
        datataskParameter.alias = getDatataskAlias();
        ArrayList paramList = new ArrayList();
        datataskParameter.putContextParam("parentBill", editData);
        paramList.add(datataskParameter);
        return paramList;
    }
	
	private String getSolutionName(){
    	return "eas.fdc.PartAMaterial.PartAMaterialEntryImp";
    }
    
    private String getDatataskAlias(){
    	return "材料明细分录";
    } 
    
    /**
     * 材料明细Excel引入
     * @author owen_wen 2010-12-20
     */
    public void actionImportFromTemplate_actionPerformed(ActionEvent e) throws Exception {
    	if (editData == null || editData.getId() == null) {
    		FDCMsgBox.showInfo("请先保存单据。");
    		SysUtil.abort();
    	}
    	
    	DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        if (getImportParam() != null)
        {
            task.invoke(getImportParam(), DatataskMode.ImpMode,true,true);
        }
        
        onLoad();
    }
}