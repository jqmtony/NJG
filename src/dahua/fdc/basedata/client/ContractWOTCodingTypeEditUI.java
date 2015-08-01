/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.BindingPropertyFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractWOTCodingTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractWOTCodingTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.IContractWOTCodingType;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ���������ı���ͬ��������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2013-11-22
 * @version 1.0, 2013-11-22
 * @see
 * @since JDK1.4
 */
public class ContractWOTCodingTypeEditUI extends AbstractContractWOTCodingTypeEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ContractWOTCodingTypeEditUI.class);

	/**
	 * output class constructor
	 */
	public ContractWOTCodingTypeEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		// ��ʼ���ؼ�����
		initCtrl();

		super.onLoad();

		setTitle();

		setBtnStatus();
	}

	/**
	 * ��������ʼ���ؼ�����
	 * 
	 * @throws BOSException
	 * @author��skyiter_wang
	 * @createDate��2013-11-20
	 */
	protected void initCtrl() throws Exception {
		// ���ñ�������
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		// R131012-0165�������ޣ�������2013-11-20��
		// 1�����ı���ͬ������������ġ���ͬ���͡�Ҫ��ѡ�����ϸ����
		// filter.getFilterItems().add(new FilterItemInfo("isleaf", Boolean.TRUE));

		// �Ѿ�����
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		evi.setFilter(filter);

		// ���ݡ������롱����
		evi.getSorter().add(new SorterItemInfo("longNumber"));

		ContractTypeCollection cols = ContractTypeFactory.getRemoteInstance().getContractTypeCollection(evi);
		prmtContractType.addItem("ȫ��");
		prmtContractType.addItems(cols.toArray());
	}

	public void loadFields() {
		super.loadFields();

		if (editData.getContractType() != null) {
			FDCClientHelper.setSelectObject(prmtContractType, this.editData.getContractType());
		} else {
			prmtContractType.setSelectedIndex(0);
		}
	}

	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {// ����״̬
			// this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
			// this.btnCancel.setVisible(false);//���ð�ť���ɼ�
		} else if (STATUS_EDIT.equals(getOprtState())) {// �޸�״̬
			// if (this.editData.isIsEnabled()) {//�����ǰΪ����״̬
			// this.btnCancel.setVisible(true);//���ð�ť����
			// this.btnCancel.setEnabled(true);//���ð�ť����
			// this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
			// } else {//�����ǰΪ����״̬
			// this.btnCancelCancel.setVisible(true);//���ð�ť�ɼ�
			// this.btnCancelCancel.setEnabled(true);//���ð�ť����
			// this.btnCancel.setEnabled(false);//���ð�ť���ɼ�
			// }
		} else if (STATUS_VIEW.equals(getOprtState())) {// �鿴״̬
			if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
				// if (this.editData.isIsEnabled()) {//�����ǰΪ����״̬
				// this.btnCancel.setVisible(true);//���ð�ť����
				// this.btnCancel.setEnabled(true);//���ð�ť����
				// this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
				// } else {//�����ǰΪ����״̬
				// this.btnCancelCancel.setVisible(true);//���ð�ť�ɼ�
				// this.btnCancelCancel.setEnabled(true);//���ð�ť����
				// this.btnCancel.setEnabled(false);//���ð�ť���ɼ�
				// }
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
				this.menuItemAddNew.setEnabled(true);
				this.menuItemEdit.setEnabled(true);
			} else {
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				// this.btnCancel.setVisible(false);
				// this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				// this.btnCancel.setVisible(false);
				// this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
		}
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("contractType.*"));
		sic.add(new SelectorItemInfo("thirdType"));
		// sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("CU.id"));
		return sic;
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
				FDCBaseDataClientUtils.CONTRACTCODINGTYPE));
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * ���������Ϣ
	 */
	/**
	 * У��ֵ����ĺϷ���
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, txtNumber);

		BOSUuid id = this.editData.getId();
		String idStr = (null != id) ? id.toString() : null;
		String number = this.txtNumber.getText();
		// ��ͬ����
		Object contractType = prmtContractType.getSelectedItem();
		String contractTypeId = null;
		if (contractType instanceof ContractTypeInfo) {
			contractTypeId = ((ContractTypeInfo) contractType).getId().toString();
		}
		String thirdTypeValue = ((ContractThirdTypeEnum) comboThirdType.getSelectedItem()).getValue();

		EntityViewInfo entityViewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		entityViewInfo.setFilter(filterInfo);
		FilterItemCollection filterItems = filterInfo.getFilterItems();

		IContractWOTCodingType icct = ContractWOTCodingTypeFactory.getRemoteInstance();
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			filterItems.clear();
			filterItems.add(new FilterItemInfo("number", number));
			if (icct.exists(filterInfo)) {
				throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EXIST);
			}

			filterItems.clear();
			filterItems.add(new FilterItemInfo("contractType.id", contractTypeId));
			filterItems.add(new FilterItemInfo("thirdType", thirdTypeValue));
			if (icct.exists(filterInfo)) {
				throw new FDCBasedataException(FDCBasedataException.CONTRACTCODINTTYPE_IS_EXIST);
			}
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			filterItems.clear();
			filterItems.add(new FilterItemInfo("number", number));
			filterItems.add(new FilterItemInfo("id", idStr, CompareType.NOTEQUALS));
			if (icct.exists(filterInfo)) {
				throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EXIST);
			}

			filterItems.clear();
			filterItems.add(new FilterItemInfo("contractType.id", contractTypeId));
			filterItems.add(new FilterItemInfo("thirdType", thirdTypeValue));
			filterItems.add(new FilterItemInfo("id", idStr, CompareType.NOTEQUALS));
			if (icct.exists(filterInfo)) {
				throw new FDCBasedataException(FDCBasedataException.CONTRACTCODINTTYPE_IS_EXIST);
			}
		}
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
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
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
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
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
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
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
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
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
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
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
		checkBeforeRemove();
		super.actionEdit_actionPerformed(e);
	}

	// 2009-1-14 ������ı���ͬ���������ѱ�����������ã��������޸Ļ�ɾ��
	private void checkBeforeRemove() throws BOSException, EASBizException {
		if (editData == null || editData.getId() == null || "".equals(editData.getId().toString()))
			return;
		String number = editData.getNumber();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("propertyName", "codeType.number"));
		filter.getFilterItems().add(new FilterItemInfo("propertyValue", number));
		if (BindingPropertyFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showError(this, "�����ı���ͬ�����ڱ���������ѱ����ã��������޸Ļ�ɾ����");
			SysUtil.abort();
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	protected IObjectValue createNewData() {
		//
		ContractWOTCodingTypeInfo ccti = new ContractWOTCodingTypeInfo();

		ccti.setThirdType(ContractThirdTypeEnum.NEW);

		this.comboThirdType.setSelectedIndex(0);
		return ccti;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO �Զ����ɷ������
		return ContractWOTCodingTypeFactory.getRemoteInstance();
	}

	/**
	 * output comboSecondType_itemStateChanged method
	 */
	protected void comboSecondType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception {
	}

	/**
	 * output comboThirdType_itemStateChanged method
	 */
	protected void comboThirdType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception {
		if (!ContractThirdTypeEnum.NEW_VALUE.equals(((ContractThirdTypeEnum) comboThirdType.getSelectedItem())
				.getValue())) {
		}
	}
}