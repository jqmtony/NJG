package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;

import javax.swing.JTextField;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.migrate.StringUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ��ͬ��Լ��֤�𼰷������ִ�����
 * @author ling_peng
 *
 */
public class EcoItemHelper {
	public EcoItemHelper() {
		// TODO Auto-generated constructor stub
	}
	
	public static void initTable() {

	}
	/**
	 * ��������
	 */
	public static void setPayItemRowBackColorWhenInit(KDTable tblPayItem){
		for (int i = 0; i < tblPayItem.getRowCount(); i++) {
			tblPayItem.getRow(i).getCell("payRate").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			tblPayItem.getRow(i).getCell("payAmount").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			tblPayItem.getRow(i).getCell("payCondition").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			tblPayItem.getRow(i).getCell("payType").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
		}
	}
	public static void setPayItemRowBackColor(KDTable tblPayItem) {
		for (int i = 0; i < tblPayItem.getRowCount(); i++) {
			tblPayItem.getRow(tblPayItem.getRowCount()-1).getCell("payRate").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			tblPayItem.getRow(tblPayItem.getRowCount()-1).getCell("payAmount").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			tblPayItem.getRow(tblPayItem.getRowCount()-1).getCell("payCondition").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			tblPayItem.getRow(tblPayItem.getRowCount()-1).getCell("payType").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
		}
	}
	/**
	 * ��Լ��֤�𼰷�������
	 */
	public static void setBailRowBackColorWhenInit(KDTable tblBail,KDFormattedTextField txtBailOriAmount,KDFormattedTextField txtBailRate){
		for (int j = 0; j < tblBail.getRowCount(); j++) {
			txtBailOriAmount.setRequired(true);
			txtBailRate.setRequired(true);
			tblBail.getRow(j).getCell("bailRate").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			tblBail.getRow(j).getCell("bailAmount").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			// tblBail.getRow(j).getCell("bailDate").getStyleAttributes().
			// setBackground(FDCColorConstants.requiredColor);
		}
	}
	public static void setBailRowBackColor(KDTable tblBail,KDFormattedTextField txtBailOriAmount,KDFormattedTextField txtBailRate){
		for (int j = 0; j < tblBail.getRowCount(); j++) {
			txtBailOriAmount.setRequired(true);
			txtBailRate.setRequired(true);
			tblBail.getRow(tblBail.getRowCount()-1).getCell("bailRate").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			tblBail.getRow(tblBail.getRowCount()-1).getCell("bailAmount").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			// tblBail.getRow(tblBail.getRowCount()-1).getCell("bailDate").
			// getStyleAttributes
			// ().setBackground(FDCColorConstants.requiredColor);
		}
	}
	/**
	 * ��֪��������ɶ����ʹ�� ContractBillEditUI.ui �е� tblEconItem(�� tblPayItem)�� tblBail ����������󶨵��е��������ʹ���һ���ؼ���<p>
	 * ��󶨵��е��ֶε���������ΪBigDecimal����ôBOS�ͻ�Ϊ���д���һ��KDFormattedTextField�ؼ�����������Ӧ��Editro.������Ϊ�ڻ���AbstractContractBillEditUI<p>
	 * ���õ�ĳЩ����(��KDFormattedTextFieldΪ�������ֵ����Сֵ���о��ȵȵ�����)��������Ҫ�󣬹���д���ǻ����    by Cassiel_peng <p>
	 */
	public static void setEntryTableCtrl(KDTable tblPayItem, KDTable tblBail) {
		// by Cassiel_peng 2008-8-27
		KDFormattedTextField tblEconItem_payRate_TextField = new KDFormattedTextField();
		tblEconItem_payRate_TextField.setName("tblEconItem_payRate_TextField");
		tblEconItem_payRate_TextField.setRemoveingZeroInDispaly(false);
		tblEconItem_payRate_TextField.setVisible(true);
		tblEconItem_payRate_TextField.setEditable(true);
		tblEconItem_payRate_TextField.setNegatived(false);
		tblEconItem_payRate_TextField.setHorizontalAlignment(JTextField.RIGHT);
		tblEconItem_payRate_TextField.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		tblEconItem_payRate_TextField.setMinimumValue(FDCHelper.ZERO);
		tblEconItem_payRate_TextField.setMaximumValue(FDCHelper.ONE_HUNDRED);
		tblEconItem_payRate_TextField.setPrecision(2);
		KDTDefaultCellEditor tblEconItem_payRate_CellEditor = new KDTDefaultCellEditor(tblEconItem_payRate_TextField);
		tblPayItem.getColumn("payRate").setEditor(tblEconItem_payRate_CellEditor);
		KDFormattedTextField tblEconItem_payAmount_TextField = new KDFormattedTextField();
		tblEconItem_payAmount_TextField.setRemoveingZeroInDispaly(false);
		tblEconItem_payAmount_TextField.setName("tblEconItem_payAmount_TextField");
		tblEconItem_payAmount_TextField.setVisible(true);
		tblEconItem_payAmount_TextField.setEditable(true);
		tblEconItem_payAmount_TextField.setNegatived(false);
		tblEconItem_payAmount_TextField.setHorizontalAlignment(JTextField.RIGHT);
		tblEconItem_payAmount_TextField.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		tblEconItem_payAmount_TextField.setMinimumValue(FDCHelper.ZERO);
		tblEconItem_payAmount_TextField.setMaximumValue(FDCHelper.MAX_DECIMAL);
		tblEconItem_payAmount_TextField.setPrecision(2);
		KDTDefaultCellEditor tblEconItem_payAmount_CellEditor = new KDTDefaultCellEditor(tblEconItem_payAmount_TextField);
		tblPayItem.getColumn("payAmount").setEditor(tblEconItem_payAmount_CellEditor);

		KDFormattedTextField tblBail_bailRate_TextField = new KDFormattedTextField();
		tblBail_bailRate_TextField.setRemoveingZeroInDispaly(false);
		tblBail_bailRate_TextField.setName("tblBail_bailRate_TextField");
		tblBail_bailRate_TextField.setVisible(true);
		tblBail_bailRate_TextField.setEditable(true);
		tblBail_bailRate_TextField.setNegatived(false);
		tblBail_bailRate_TextField.setHorizontalAlignment(JTextField.RIGHT);
		tblBail_bailRate_TextField.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		tblBail_bailRate_TextField.setMinimumValue(FDCHelper.ZERO);
		tblBail_bailRate_TextField.setMaximumValue(FDCHelper.ONE_HUNDRED);
		tblBail_bailRate_TextField.setPrecision(2);
		KDTDefaultCellEditor tblBail_bailRate_CellEditor = new KDTDefaultCellEditor(tblBail_bailRate_TextField);
		tblBail.getColumn("bailRate").setEditor(tblBail_bailRate_CellEditor);
		KDFormattedTextField tblBail_bailAmount_TextField = new KDFormattedTextField();
		tblBail_bailAmount_TextField.setRemoveingZeroInDispaly(false);
		tblBail_bailAmount_TextField.setName("tblBail_bailAmount_TextField");
		tblBail_bailAmount_TextField.setVisible(true);
		tblBail_bailAmount_TextField.setEditable(true);
		tblBail_bailAmount_TextField.setNegatived(false);
		tblBail_bailAmount_TextField.setHorizontalAlignment(JTextField.RIGHT);
		tblBail_bailAmount_TextField.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		tblBail_bailAmount_TextField.setMinimumValue(FDCHelper.ZERO);
		tblBail_bailAmount_TextField.setMaximumValue(FDCHelper.MAX_DECIMAL);
		tblBail_bailAmount_TextField.setPrecision(2);
		KDTDefaultCellEditor tblBail_bailAmount_CellEditor = new KDTDefaultCellEditor(tblBail_bailAmount_TextField);
		tblBail.getColumn("bailAmount").setEditor(tblBail_bailAmount_CellEditor);

		KDDatePicker bailDatePicker1 = new KDDatePicker();
		bailDatePicker1.setDatePattern(FDCHelper.KDTABLE_DATE_FMT);
		ICellEditor bailDateEditor = new KDTDefaultCellEditor(bailDatePicker1);
		tblBail.getColumn("bailDate").setEditor(bailDateEditor);
		tblPayItem.getColumn("date").setEditor(bailDateEditor);
		
		// ��Ӹ�������F7
		KDBizPromptBox bizPayTypeBox = new KDBizPromptBox();
		bizPayTypeBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
		// modified by zhaoqin for ���� on 2013/10/10
		bizPayTypeBox.setCommitFormat("$number$");
	    bizPayTypeBox.setDisplayFormat("$name$");
	    bizPayTypeBox.setEditFormat("$number$");
	    bizPayTypeBox.setRequired(true);
		KDTDefaultCellEditor payTypeEditor = new KDTDefaultCellEditor(bizPayTypeBox);
		tblPayItem.getColumn("payType").setEditor(payTypeEditor);
	}
	public static void checkVeforeSubmit(KDTable tblPayItem,KDTable tblBail) {
		for (int i = 0; i < tblPayItem.getRowCount(); i++) {
			if(null==tblPayItem.getRow(i).getCell("payType").getValue()){
				MsgBox.showWarning("�������Ͳ���Ϊ��");
				SysUtil.abort();
			}
			if(StringUtils.isEmpty((String)tblPayItem.getRow(i).getCell("payCondition").getValue())){
				MsgBox.showWarning("������������Ϊ��");
				SysUtil.abort();
			}
			if(null==tblPayItem.getRow(i).getCell("payRate").getValue()){
				MsgBox.showWarning("�����������Ϊ��");
				SysUtil.abort();
			}
			if(null==tblPayItem.getRow(i).getCell("payAmount").getValue()){
				MsgBox.showWarning("�������Ϊ��");
				SysUtil.abort();
			}
			
		}
		for (int j = 0; j < tblBail.getRowCount(); j++) {
			// if(null==tblBail.getRow(j).getCell("bailDate").getValue()){
			// MsgBox.showWarning("�������ڲ���Ϊ��");
			// SysUtil.abort();
			// }
			if(null==tblBail.getRow(j).getCell("bailRate").getValue()){
				MsgBox.showWarning("������������Ϊ��");
				SysUtil.abort();
			}
			if(null==tblBail.getRow(j).getCell("bailAmount").getValue()){
				MsgBox.showWarning("��������Ϊ��");
				SysUtil.abort();
			}
		}
	}

	/**
	 * ���㸶�������ԭ�ҽ����߱���
	 * @param tblEconItem
	 *            ��������table
	 * @param tblBail
	 *            ��Լ��֤�𼰷���table
	 * @param txtamount
	 *            ��ͬ����ͬ�޶�ʱΪ��ͬ�޶���
	 */
	public static void calPayItemAmt(KDTable tblEconItem, KDTable tblBail, KDFormattedTextField txtamount) {
		for (int i = 0; i < tblEconItem.getRowCount(); i++) {
			// ��������ͽ���Ϊ�գ��͸��ݺ�ͬ��������������
			if (tblEconItem.getRow(i).getCell("payRate").getValue() != null && txtamount.getNumberValue() != null) {
				BigDecimal payOriAmount = FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(FDCHelper.toBigDecimal(tblEconItem.getRow(i)
						.getCell("payRate").getValue(), 2), FDCHelper.toBigDecimal(txtamount.getBigDecimalValue())), FDCHelper.ONE_HUNDRED), 2);
				tblEconItem.getRow(i).getCell("payAmount").setValue(payOriAmount);
			}
			if (tblEconItem.getRow(i).getCell("payRate").getValue() == null && txtamount.getNumberValue() != null
					&& tblEconItem.getRow(i).getCell("payAmount").getValue() != null) {
				// �������
				BigDecimal payRate = FDCHelper.divide(FDCHelper.multiply(tblEconItem.getRow(i).getCell("payAmount").getValue(), FDCHelper.ONE),
						FDCHelper.toBigDecimal(txtamount.getNumberValue(), 2));
				tblEconItem.getRow(i).getCell("payRate").setValue(payRate);
				if (payRate.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
					MsgBox.showWarning("�����������100%");
					tblEconItem.getRow(i).getCell("payAmount").setValue(null);
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * ������Լ��֤�𼰷������ֵ�ԭ�ҽ����߱���������ͬ�����ı�ʱ��Ӧ������Լ��֤���*��������
	 * 
	 * @param tblBail
	 *            ��Լ��֤�𼰷���table
	 * @param txtamount
	 *            ��ͬ����ͬ�޶�ʱΪ��ͬ�޶���
	 * @param txtBailOriAmount
	 *            ��Լ��֤��ԭ��
	 * @param txtBailRate
	 *            ��Լ��֤�����
	 */
	public static void calBailAmt(KDTable tblBail, KDFormattedTextField txtamount, KDFormattedTextField txtBailOriAmount,
			KDFormattedTextField txtBailRate) {
		
		if (txtamount.getBigDecimalValue() != null && FDCHelper.toBigDecimal(txtamount.getBigDecimalValue(), 2).compareTo(FDCHelper.ZERO) != 0
				&& txtBailOriAmount.getBigDecimalValue() != null) {
			BigDecimal bailRate = FDCHelper.divide(FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(), 4), FDCHelper.toBigDecimal(txtamount
					.getBigDecimalValue(), 4), 4, BigDecimal.ROUND_HALF_UP);
			if (bailRate.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
				MsgBox.showWarning("��Լ��֤���������100%");
				txtBailOriAmount.setValue(null);
				SysUtil.abort();
			}
			txtBailRate.setValue(FDCHelper.toBigDecimal(FDCHelper.multiply(bailRate, FDCHelper.ONE_HUNDRED), 4));
		}
		if (txtBailOriAmount.getBigDecimalValue() == null && txtBailRate.getBigDecimalValue() != null && txtamount.getBigDecimalValue() != null) {
			BigDecimal bailOriAmt = FDCHelper.divide(FDCHelper.multiply(FDCHelper.toBigDecimal(txtBailRate.getBigDecimalValue(), 2), FDCHelper
					.toBigDecimal(txtamount.getBigDecimalValue(), 2)), FDCHelper.ONE_HUNDRED);
			txtBailOriAmount.setValue(bailOriAmt);
		}
		for (int i = 0; i < tblBail.getRowCount(); i++) {
			if (tblBail.getRow(i).getCell("bailRate").getValue() != null && txtamount.getNumberValue() != null) {
				BigDecimal bailAmount = FDCHelper.divide(FDCHelper.multiply(tblBail.getRow(i).getCell("bailRate").getValue(), FDCHelper.toBigDecimal(
						txtBailOriAmount.getBigDecimalValue(), 2)), FDCHelper.ONE_HUNDRED);
				tblBail.getRow(i).getCell("bailAmount").setValue(bailAmount);
			}
			if (tblBail.getRow(i).getCell("bailRate").getValue() == null && txtamount.getNumberValue() != null
					&& tblBail.getRow(i).getCell("bailAmount").getValue() != null) {
				// �������
				BigDecimal bailRate = FDCHelper.divide(FDCHelper.multiply(tblBail.getRow(i).getCell("bailAmount").getValue(), FDCHelper.ONE),
						FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(), 2));
				if (bailRate.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
					MsgBox.showWarning("���ر�������100%");
					tblBail.getRow(i).getCell("bailRate").setValue(null);
					SysUtil.abort();
				}
				tblBail.getRow(i).getCell("bailRate").setValue(bailRate);
			}
		}
	}
}
