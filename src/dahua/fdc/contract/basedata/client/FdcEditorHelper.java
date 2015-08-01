/**
 * @(#)FdcEditorHelper.java 1.0 2014-6-3
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.client;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;

import com.kingdee.bos.ctrl.extendcontrols.ExtendParser;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.util.FdcArrayUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.framework.client.CoreUI;

/**
 * ���������ز��༭������
 * <p>
 * ���ñ��Ԫ��༭��ʽ
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-6-3
 * @version 1.0, 2014-6-3
 * @see
 * @since JDK1.4
 */
public class FdcEditorHelper {

	/**
	 * ��Ԫ���ı��ؼ�
	 * 
	 * @param precession
	 * @return
	 */
	public static ICellEditor getTextEditor(int length) {

		KDTextField textField = new KDTextField();
		textField.setHorizontalAlignment(JTextField.LEFT);
		textField.setMaxLength(length);

		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(textField);
		numberEditor.setClickCountToStart(1);

		return numberEditor;
	}

	/**
	 * ��Ԫ���ı��ؼ�
	 * 
	 * @param precession
	 * @return
	 */
	public static ICellEditor getAreaTextEditor(int maxLenth) {

		KDTextArea textField = new KDTextArea();
		textField.setWrapStyleWord(true);
		textField.setName("indexValue_TextField");
		textField.setVisible(true);
		textField.setEditable(true);
		textField.setMaxLength(500);
		textField.setColumns(10);
		textField.setWrapStyleWord(true);
		textField.setLineWrap(true);
		textField.setAutoscrolls(true);
		textField.setMaxLength(maxLenth);

		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(textField);
		numberEditor.setClickCountToStart(1);

		return numberEditor;
	}

	/**
	 * ��Ԫ���ı��ؼ�
	 * 
	 * @param precession
	 * @return
	 */
	public static ICellEditor getDetailedAreaTextEditor(KDDetailedArea textField, int maxLength) {
		if (textField == null) {
			textField = new KDDetailedArea();
		}
		textField.setName("indexValue_TextField");
		textField.setVisible(true);
		textField.setEditable(true);
		textField.setMaxLength(maxLength);
		textField.setAutoscrolls(true);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(textField);
		numberEditor.setClickCountToStart(1);
		return numberEditor;
	}

	/**
	 * ��Ԫ�����ֿؼ�
	 * 
	 * @param precession
	 * @return
	 */
	public static ICellEditor getNumberEditor(int precession) {

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setHorizontalAlignment(JTextField.RIGHT);
		formattedTextField.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMaximumValue(FDCConstants.MAX_DECIMAL);
		formattedTextField.setMinimumValue(FDCConstants.MIN_DECIMAL);
		formattedTextField.setPrecision(precession);

		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		numberEditor.setClickCountToStart(1);

		return numberEditor;
	}

	/**
	 * ��Ԫ�����ֿؼ�
	 * 
	 * @param precession
	 * @return
	 */
	public static ICellEditor getNumberEditor(int precession, boolean negtive) {

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setHorizontalAlignment(JTextField.RIGHT);
		formattedTextField.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMaximumValue(FDCConstants.MAX_DECIMAL);
		// �Ǹ�����СֵΪ0
		if (!negtive) {
			formattedTextField.setMinimumValue(FDCConstants.ZERO);
		} else {
			formattedTextField.setMinimumValue(FDCConstants.MIN_DECIMAL);
		}
		formattedTextField.setPrecision(precession);
		formattedTextField.setNegatived(negtive);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		numberEditor.setClickCountToStart(1);

		return numberEditor;
	}

	// �����༭��
	public static ICellEditor getIntegerEditor(boolean negtive) {

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		formattedTextField.setHorizontalAlignment(JTextField.RIGHT);
		formattedTextField.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMaximumValue(FDCConstants.MAX_DECIMAL);
		formattedTextField.setMinimumValue(FDCConstants.ZERO);
		formattedTextField.setPrecision(0);
		formattedTextField.setNegatived(negtive);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		numberEditor.setClickCountToStart(1);

		return numberEditor;
	}

	/**
	 * �ٷֱȱ༭
	 * 
	 * @param precession
	 * @param negtive
	 * @return
	 */
	public static ICellEditor getPercentNumberEditor(int precession, boolean negtive) {

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setHorizontalAlignment(JTextField.RIGHT);
		formattedTextField.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMaximumValue(FDCConstants.ONE_HUNDRED);
		// �Ǹ�����СֵΪ0
		if (!negtive) {
			formattedTextField.setMinimumValue(FDCConstants.ZERO);
		} else {
			formattedTextField.setMinimumValue(FDCConstants._ONE_HUNDRED);
		}
		formattedTextField.setPrecision(precession);
		formattedTextField.setNegatived(negtive);

		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		numberEditor.setClickCountToStart(1);

		return numberEditor;
	}

	/**
	 * �����F7�ؼ�
	 * 
	 * @param bizPromptBox
	 * @return
	 */
	public static ICellEditor getBizPromptEditor(KDBizPromptBox bizPromptBox) {
		return getBizPromptEditor(bizPromptBox, false);
	}

	/**
	 * �����F7�ؼ�,����F7ѡ��Ҳ�����ֹ�¼���ַ�����
	 * 
	 * @param bizPromptBox
	 * @param useParse
	 *            True ������ֹ�¼������
	 * @return
	 */
	public static ICellEditor getBizPromptEditor(KDBizPromptBox bizPromptBox, boolean useParse) {
		KDTDefaultCellEditor bizPromptEditor = new KDTDefaultCellEditor(bizPromptBox);
		bizPromptEditor.setClickCountToStart(1);

		if (useParse) {
			ExtendParser parser = new ExtendParser(bizPromptBox);
			bizPromptBox.setCommitParser(parser);
		}

		return bizPromptEditor;
	}

	/**
	 * ������
	 * 
	 * @param comboBox
	 * @return
	 */
	public static ICellEditor getKDComboBoxEditor(KDComboBox comboBox) {
		KDTDefaultCellEditor bizPromptEditor = new KDTDefaultCellEditor(comboBox);
		bizPromptEditor.setClickCountToStart(1);

		return bizPromptEditor;
	}

	/**
	 * Boolean
	 * 
	 * @param comboBox
	 * @return
	 */
	public static ICellEditor getKDCheckBoxEditor(KDCheckBox comboBox) {
		KDTDefaultCellEditor bizPromptEditor = new KDTDefaultCellEditor(comboBox);
		bizPromptEditor.setClickCountToStart(1);

		return bizPromptEditor;
	}

	/**
	 * ���ڸ�ʽ
	 * 
	 * @param datePiker
	 * @return
	 */
	public static ICellEditor getKDDateEditor(KDDatePicker datePiker) {
		KDTDefaultCellEditor bizPromptEditor = new KDTDefaultCellEditor(datePiker);
		bizPromptEditor.setClickCountToStart(1);

		return bizPromptEditor;
	}

	/**
	 * ��������/�����������
	 * 
	 * @param txtNumber
	 * @param isNegative
	 * @param pre
	 * @param max
	 * @param min
	 */
	public static void setFormattedTextField(KDFormattedTextField txtNumber, boolean isNegative, int pre,
			BigDecimal max, BigDecimal min) {
		txtNumber.setDataType(KDFormattedTextField.DECIMAL);
		txtNumber.setNegatived(isNegative);
		txtNumber.setHorizontalAlignment(JTextField.RIGHT);
		txtNumber.setPrecision(pre);
		txtNumber.setMaximumValue(max);
		// �Ǹ�����Сֵ����С��0
		if (!isNegative && FDCNumberHelper.isLessThan(min, FDCConstants.ZERO)) {
			min = FDCConstants.ZERO;
		}
		txtNumber.setMinimumValue(min);

		// �ڿؼ��Ǳ༭״̬�£��������㴦��
		txtNumber.setRemoveingZeroInDispaly(FDCConstants.IS_REMOVEING_ZERO_IN_DISPALY);
	}

	/**
	 * ��������/�����������
	 * 
	 * @param txtNumber
	 * @param pre
	 */
	public static void setFormattedTextField(KDFormattedTextField txtNumber, int pre) {
		txtNumber.setDataType(KDFormattedTextField.DECIMAL);
		txtNumber.setHorizontalAlignment(JTextField.RIGHT);
		txtNumber.setPrecision(pre);
		txtNumber.setMaximumValue(FDCConstants.MAX_DECIMAL);
		txtNumber.setMinimumValue(FDCConstants.MIN_DECIMAL);

		// �ڿؼ��Ǳ༭״̬�£��������㴦��
		txtNumber.setRemoveingZeroInDispaly(FDCConstants.IS_REMOVEING_ZERO_IN_DISPALY);
	}

	/**
	 * ��������/�����������
	 * 
	 * @param coreUI
	 *            ����UI
	 * @param propertyName
	 *            ������
	 * @param isNegative
	 * @param pre
	 * @param max
	 * @param min
	 */
	public static void setFormattedTextField(CoreUI coreUI, String propertyName, boolean isNegative, int pre,
			BigDecimal max, BigDecimal min) {
		KDFormattedTextField txtNumber = FDCClientHelper.getKDFormattedTextField(coreUI, propertyName);
		if (null != txtNumber) {
			FdcEditorHelper.setFormattedTextField(txtNumber, isNegative, pre, max, min);
		}
	}

	/**
	 * ��������/�����������
	 * 
	 * @param coreUI
	 *            ����UI
	 * @param propertyName
	 *            ������
	 * @param pre
	 */
	public static void setFormattedTextField(CoreUI coreUI, String propertyName, int pre) {
		KDFormattedTextField txtNumber = FDCClientHelper.getKDFormattedTextField(coreUI, propertyName);
		if (null != txtNumber) {
			FdcEditorHelper.setFormattedTextField(txtNumber, pre);
		}
	}

	/**
	 * ��������/�����������
	 * 
	 * @param coreUI
	 *            ����UI
	 * @param propertyNameArr
	 *            ������ ����
	 * @param isNegative
	 * @param pre
	 * @param max
	 * @param min
	 */
	public static void setFormattedTextField(CoreUI coreUI, String[] propertyNameArr, boolean isNegative, int pre,
			BigDecimal max, BigDecimal min) {
		List txtNumberList = FDCClientHelper.getKDFormattedTextFieldList(coreUI, propertyNameArr);
		if (FdcCollectionUtil.isNotEmpty(txtNumberList)) {
			KDFormattedTextField txtNumber = null;
			for (Iterator iterator = txtNumberList.iterator(); iterator.hasNext();) {
				txtNumber = (KDFormattedTextField) iterator.next();
				FdcEditorHelper.setFormattedTextField(txtNumber, isNegative, pre, max, min);
			}
		}
	}

	/**
	 * ��������/�����������
	 * 
	 * @param coreUI
	 *            ����UI
	 * @param propertyNameArr
	 *            ������ ����
	 * @param pre
	 */
	public static void setFormattedTextField(CoreUI coreUI, String[] propertyNameArr, int pre) {
		List txtNumberList = FDCClientHelper.getKDFormattedTextFieldList(coreUI, propertyNameArr);
		if (FdcCollectionUtil.isNotEmpty(txtNumberList)) {
			KDFormattedTextField txtNumber = null;
			for (Iterator iterator = txtNumberList.iterator(); iterator.hasNext();) {
				txtNumber = (KDFormattedTextField) iterator.next();
				FdcEditorHelper.setFormattedTextField(txtNumber, pre);
			}
		}
	}

	/**
	 * �����ڿؼ��Ǳ༭״̬�£��������㴦��
	 * 
	 * @param txtNumberArr
	 *            ����/����������� ����
	 * @param isRemoveingZeroInDispaly
	 *            �Ƿ����㴦��
	 */
	public static void setRemoveingZeroInDispaly(KDFormattedTextField[] txtNumberArr, boolean isRemoveingZeroInDispaly) {
		if (FdcArrayUtil.isNotEmpty(txtNumberArr)) {
			KDFormattedTextField txtNumber = null;
			for (int i = 0, length = txtNumberArr.length; i < length; i++) {
				txtNumber = txtNumberArr[i];

				// �ڿؼ��Ǳ༭״̬�£��������㴦��
				txtNumber.setRemoveingZeroInDispaly(isRemoveingZeroInDispaly);
			}
		}
	}

	/**
	 * ����ǽ��ؼ�
	 * 
	 * @param precession
	 * @return
	 */
	public static ICellEditor getIntegerEditor() {

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		formattedTextField.setHorizontalAlignment(JTextField.RIGHT);
		formattedTextField.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMaximumValue(new Integer(99999999));
		formattedTextField.setMinimumValue(new Integer(0));

		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		numberEditor.setClickCountToStart(1);

		return numberEditor;
	}

	/**
	 * ��Ԫ�����ڿؼ�
	 * 
	 * @param precession
	 * @return
	 */
	public static ICellEditor getDateEditor() {
		KDDatePicker dateFiDatePicker = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(dateFiDatePicker);
		dateEditor.setClickCountToStart(1);

		return dateEditor;
	}
}
