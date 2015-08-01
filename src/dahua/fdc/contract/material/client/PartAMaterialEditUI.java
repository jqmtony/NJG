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
 * ������ϸ�� �༭����
 */
public class PartAMaterialEditUI extends AbstractPartAMaterialEditUI {
	private static final String DESCRIPTION = "description";
	private static final long serialVersionUID = 1L;
	private static final Logger logger = CoreUIObject.getLogger(PartAMaterialEditUI.class);
	private int unitPrecision = 2;
	private int pricePrecision = 2;
	
	/**
	 * �޶�ǰ����ID��Name��
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
		
		//�ύ״̬�ĵ��ݣ��༭������ԡ����桱��ť
		if(FDCBillStateEnum.SUBMITTED.equals(editData.getState()))
			actionSave.setEnabled(false);
		
		
		if (editData!= null && editData.getContractBill() != null)
			contractBill = editData.getContractBill();
	}
	
	protected void disablePrintFunc() {
		
	}
	
	// ���ع�������������ť
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
	 * ���Ϻ�ͬF7
	 * @throws Exception 
	 */
	private void initMaterialNoF7() throws Exception  {
		KDBizPromptBox kdtEntrys_materialNumber_PromptBox = new KDBizPromptBox();
		
		// ʹ���Զ���������ұ������F7�� Added by Owen_wen 2010-9-2
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
	 * ����ͬF7
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

		// ������֯����F7
		if (!CU.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			filter.getFilterItems().add(
					new FilterItemInfo("CU.id", CU.getId().toString()));
		}
		// f7������ʱ�򣬼׹��ĺ�ֻͬ��ѡ������״̬�ķǼ׹���ͬ�����Ǽ׹���ֻ��ѡ����(����save,submit,audited״̬)
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
		addEmptyRows();		// Ĭ����ʾ20��,

		/**
		 * �ڴ˵��ã���ֹ����󱻹����ķ�¼�༭����
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
	 * �����ܽ������뵽�ؼ��ϡ�
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
	 * ����ʱ��ȥ�����У��ٲ�����У���֤������20�з�¼<p>
	 * ��ΪstoreFields()��ı�editData��ֵ���Ӷ��ᵼ������ʱ�������༭���룬���޸��˳�����ʾ���޸��Ƿ񱣴棬
	 * ������ȥ�����ٲ��Ͽ��С�
	 * @author owen_wen 2010-12-21
	 */
	public void storeFields() {
		removeEmptyRows();
		super.storeFields();
		addEmptyRows();
	}

	/**
	 * ��ӿ���
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
	 * ȥ������
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
	 * �Ƿ��ǿ���
	 * @param row Ҫ�жϵ���
	 * @return true �ǿ��У�false���ǿ���
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
	 * �������������ʵ�ֵĸ��෽��������ʼ�����ֱ�ͷ����
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
		
		// ������޶����ݣ��ȱ��浥�����ύ��ᱨ�жϣ�contractBillΪnull��
		// ��˼������µ��ж�,��������������  added By owen_wen 
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
	 * @return ��¼selectors
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		// ���丸���sic
		sic.add(new SelectorItemInfo("entrys.material.baseUnit.*"));
		sic.add(new SelectorItemInfo("entrys.material.*"));
		sic.add(new SelectorItemInfo("state"));
		//Add by zhiyuan_tang 2010/11/02  ���Ӻ�ͬ��isPartAMaterialCon��isCoseSplit����
		sic.add(new SelectorItemInfo("contractBill.isPartAMaterialCon"));
		sic.add(new SelectorItemInfo("contractBill.isCoseSplit"));
	
		return sic;
	}

	/**
	 * @return ��¼��
	 */
	protected KDTable getDetailTable() {
		return kdtEntrys;
	}

	/**
	 * �������༭�����¼�,ѡ���ͬ����ϱ�����Զ�����������Ϣ
	 */
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		//����ǡ����ú�ͬ���롱�༭������
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
						if (MsgBox.YES == FDCMsgBox.showConfirm2(this, "�Ƿ�����ѡ�������ú�ͬ?")) {
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
		
		//�����"���ϱ���"�༭������
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
	 * ����¼���в�������
	 * @param rowIndex ���������
	 * @param materials ��������������飬������ĳ��Ϊnull����ʾ���ظ�����Ҫ����
	 */
	private void insertDataToRow(int rowIndex, Object[] materials) {
	
		//��ȡ��еĺ�ͬ����ͺ�ͬ����
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
			
			//����Ƕ�ѡ(��i>0ʱ)����Ҫ�ж��Ƿ�ҪaddLine��������һ�з�¼��
			if(i > 0){
				if(material != null)
				{
					table.addRow(table.getSelectManager().getActiveRowIndex()); //�ڻ�д����һ�� 
					row = table.getRow(table.getSelectManager().getActiveRowIndex()); 
					IObjectValue detailData = createNewDetailData(table);
					row.setUserObject(detailData);
				}
				else
				{
					continue;
				}
			}
			
			// �� i==0, and material != null �������ֱ�ӽ�material���뵽��ǰ����
			row.getCell(PartAMaterialContants.MATERIAL_NUMBER).setValue(material); 
//			row.getCell(PartAMaterialContants.MATERIAL_NUMBER).setValue(material.getNumber());  ����д�������⣬��һ�α༭ʱ�������¼��ͬʱ���������Զ���ʧ
			row.getCell(PartAMaterialContants.MATERIAL_NAME).setValue(material.getName());
			row.getCell(PartAMaterialContants.MODEL).setValue(material.getModel());
			if(material.getBaseUnit() != null){
				row.getCell(PartAMaterialContants.UNIT).setValue(material.getBaseUnit().getName());
			}
			row.getCell(PartAMaterialContants.MATERIAL_ID).setValue(material.getId());
			
			//���ƻ�к�ͬ��������Ƶ�row��
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
	 * ͳ�ƣ�ʩ������ͬ��Ӧ�Ľ����ƻ�������������id��
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
	 * ��ȡ�޶�ǰeditData�е�����ID��Name�����ŵ�Map��
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
	 * ����¼���Ƿ�ɾ���˱������ƻ����õ�����
	 * @author owen_wen
	 * @throws BOSException 
	 */
	private void checkIfDeleteMaterialByEnterPlan(Map matIDs_contractIds) throws BOSException {
		String msg = "���������ƻ�����ɾ�������ϣ�";
		StringBuffer sb = new StringBuffer();
				
		for(Iterator it = matIDs_contractIds.keySet().iterator(); it.hasNext();){
			// �жϸ������Ƿ��ڲ��Ͻ����ƻ��б�����
			String materialID = it.next().toString();
			if ( matIDs_contractIds.get(materialID) instanceof String) { //�����ȷʵ�Ǻ�ͬid��������true����
				String contractID = matIDs_contractIds.get(materialID).toString();				
				if ( calMaterialEnterPlan(contractID).contains(materialID) ){// �������ƻ�����
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
	 * �������ͬ��ͬ�������ͬ���ϱ����������˵�
	 * @param e KDTEditEvent KDTable�༭�¼� 
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
			
			//���������ظ����Һ�ͬ�ظ�������
			for(int i = 0; i < materials.length; i++)
			{
				MaterialInfo material = null;
				if(materials[i] instanceof MaterialInfo)
					material = (MaterialInfo)materials[i];
				
				for(int j = 0; j < table.getRowCount(); j++)
				{
					//���j�ƶ�������ϣ�����Ҫ�ж��Ƿ��ظ�����Ϊ�û���п���ѡ���˶������
					if(j != activeRow)
					{
						if(table.getRow(j).getCell(PartAMaterialContants.MATERIAL_NUMBER).getValue() == null)
							continue;   //����û�����ϵ��У�����
												
						String preMateralNumber = ""; //��ȡ��j�е����ϱ���
						
						//�п����Ǹ�����δ����������У�getValue�õ��Ļ�����MaterialInfo�������Ҫ�жϴ���
						if (table.getRow(j).getCell(PartAMaterialContants.MATERIAL_NUMBER).getValue() instanceof MaterialInfo) 
							preMateralNumber = ((MaterialInfo)table.getRow(j).getCell(PartAMaterialContants.MATERIAL_NUMBER).getValue()).getNumber();
						else
							preMateralNumber = table.getRow(j).getCell(PartAMaterialContants.MATERIAL_NUMBER).getValue().toString();
						
						//����û�к�ͬ����
						if (table.getRow(j).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue() == null){
							if (activeRowContractNumber.equals("")){ //�����ǰ��ͬҲΪ��
								if(material.getNumber().equals(preMateralNumber)){ //�����ظ�,�Һ�ͬΪ��
									materials[i] = null; //����i�����ϣ����Ϊ��
								}
							}
							continue; // ֱ����������ѭ������table�е���һ�����Ͻ����ж�
						}
							
						String preContractNumber = "";  //��ȡ����j�еĺ�ͬ����
						
						if (table.getRow(j).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue() instanceof ContractBillInfo) 
							preContractNumber = ((ContractBillInfo)table.getRow(j).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue()).getNumber();
						else
							preContractNumber = table.getRow(j).getCell(PartAMaterialContants.CONTRACT_NUMBER).getValue().toString();
												
						if(material.getNumber().equals(preMateralNumber) && preContractNumber.equals(activeRowContractNumber)){
							materials[i] = null; //����i�����ϣ����Ϊ��
							break; 		//�����ظ�,�Һ�ͬ�ظ���ֱ������ѭ��������һ�����Ͻ����ж�
						}
					}
				}
			}						
			
			return materials;
		}else {
			return null;
		}
	}
	
	// ������
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
			// ���ҵ���=ԭ�ҵ���*rate
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
		
		// ���ϵ��ܽ����˺�ͬ���ܽ�Ҫ������ʾ
		if (FDCHelper.compareTo(txtTotalAmt.getBigDecimalValue(), contractBill.getAmount()) > 0) {
			int result = FDCMsgBox.showConfirm2(this, "���ϵ��ܽ����˺�ͬ���ܽ��Ƿ�ȷ���ύ?");
			if (result == FDCMsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
	}
	
	/**
	 * ����Ƿ��ǿշ�¼
	 * @return trueû�з�¼��false�з�¼
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
	
	// ����ǰУ���ֵ���ظ���
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
	 * ��д���෽����У���е�Ԫֵ�Ƿ�Ϊ��
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
		 * ������ϸ������������״̬ʱ���鿴������޸İ�ť�����á� --jiadong
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
	 * ��ѡ��Cell��Delete��ʱ
	 * <p>
	 * 1.ɾ����ǰ��Ԫ��������֮����������Ҳ��ɾ��; 2.�Ǳ༭״̬�Ĳ���ɾ����
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
	 * ��¼�����ò���ɾ��
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		verifyIfRefByEnterPlanBill();
		verifyIfRefByConfirmBill();
		super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * ����Ƿ񱻽����ƻ�����
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
	 * ����Ƿ񱻲���ȷ�ϵ�����
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
	 * ��¼������ʱ�������޸�
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
		
		// MsgBox.showWarning(this,"��¼��ȷ�ϵ��������������޸ģ�");
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
	 * �ӹ������б�֪ͨ���ȡ������Ϣ
	 * 
	 * */
	
	public void actionImportMaterial_actionPerformed(ActionEvent e)
			throws Exception {

		if (this.kdtEntrys.getRowCount() >= 0) {

			int nResult = MsgBox.showConfirm2New(null,"��������豸��ϸ�����Ǳ��е����ݣ��Ƿ����");
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
			    		 FDCMsgBox.showInfo(this, "����ȱ��Դͷ���ݵ����޷��Զ���������豸��ϸ�����ֹ�������");
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
			    	 FDCMsgBox.showInfo(this, "����ȱ��Դͷ���ݵ����޷��Զ���������豸��ϸ�����ֹ�������");
		    		 abort();
			     }
			}else{
				abort();
			}
		}

	}

	/**
	 * 
	 * �ӹ������б�֪ͨ���ȡ������Ϣʱ������Cell��Editor
	 * ��actionImportMaterial_actionPerformed()�г�ȡ�ķ������ع���һ��
	 * @Author��owen_wen
	 * @CreateTime��2012-9-24
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
	 * ��ӡ
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
	 * ��ӡԤ��
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		logger.info("��ӡԤ��");
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

	// ������ı���ͬ�״��Ӧ��Ŀ¼
	protected String getTDFileName() {
		return "/bim/fdc/material/PartAmaterial";
	}

	// ��Ӧ���״�Query
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
    	return "������ϸ��¼";
    } 
    
    /**
     * ������ϸExcel����
     * @author owen_wen 2010-12-20
     */
    public void actionImportFromTemplate_actionPerformed(ActionEvent e) throws Exception {
    	if (editData == null || editData.getId() == null) {
    		FDCMsgBox.showInfo("���ȱ��浥�ݡ�");
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