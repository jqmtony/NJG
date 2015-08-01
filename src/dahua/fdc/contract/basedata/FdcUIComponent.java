/**
 * @(#)EcUIComponent.java 1.0 2014-3-19
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.framework.ICoreBase;

/**
 * ���������ز�UI���
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcUIComponent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4615795649596975799L;
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * ҵ��ӿڶ��󣬹����ú�̨����ʹ��
	 */
	protected ICoreBase bizInterface;

	/**
	 * ҵ��ӿڶ�Ӧ��BOSType����
	 */
	protected BOSObjectType bizType;

	/**
	 * ҵ��ӿڶ�Ӧ��BOSType�ַ���
	 */
	protected String entityBOSType;

	/**
	 * �༭��������
	 */
	protected String editUIName;

	/**
	 * �༭����Ĭ�ϴ򿪷�ʽ
	 */
	protected String editUIModal;
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * �Ƿ��и�������
	 */
	protected boolean hasCopyTo = false;

	/**
	 * �Ƿ����޶�
	 */
	protected boolean hasRevise = false;

	/**
	 * �Ƿ���ǩ��
	 */
	protected boolean hasSignOption = false;

	/**
	 * �Ƿ����ڼ�
	 */
	protected boolean hasPeriod = false;

	/**
	 * �Ƿ��а汾��
	 * <p>
	 * ��"�Ƿ����ڼ�"��������"�Ƿ����޶�"��ͬ
	 */
	// protected boolean hasVersionNumber = false;
	/**
	 * �Ƿ���ִ��
	 * <p>
	 * ��"�Ƿ����ڼ�"��������"�Ƿ����޶�"��ͬ
	 */
	// protected boolean hasExecute = false;
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Ĭ�Ϲ�����
	 */
	public FdcUIComponent() {
	}

	/**
	 * ������
	 * 
	 * @param hasCopyTo
	 *            �Ƿ��и�������
	 * @param hasRevise
	 *            �Ƿ����޶�
	 * @param hasSignOption
	 *            �Ƿ���ǩ��
	 * @param hasPeriod
	 *            �Ƿ����ڼ�
	 */
	public FdcUIComponent(boolean hasCopyTo, boolean hasRevise, boolean hasSignOption, boolean hasPeriod) {
		super();
		this.hasCopyTo = hasCopyTo;
		this.hasRevise = hasRevise;
		this.hasSignOption = hasSignOption;
		this.hasPeriod = hasPeriod;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	public ICoreBase getBizInterface() {
		return bizInterface;
	}

	public void setBizInterface(ICoreBase bizInterface) {
		this.bizInterface = bizInterface;
	}

	public BOSObjectType getBizType() {
		return bizType;
	}

	public void setBizType(BOSObjectType bizType) {
		this.bizType = bizType;
	}

	public String getEntityBOSType() {
		return entityBOSType;
	}

	public void setEntityBOSType(String entityBOSType) {
		this.entityBOSType = entityBOSType;
	}

	public String getEditUIName() {
		return editUIName;
	}

	public void setEditUIName(String editUIName) {
		this.editUIName = editUIName;
	}

	public String getEditUIModal() {
		return editUIModal;
	}

	public void setEditUIModal(String editUIModal) {
		this.editUIModal = editUIModal;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * �Ƿ��и�������
	 * 
	 * @return
	 */
	public boolean isHasCopyTo() {
		return hasCopyTo;
	}

	/**
	 * ����"�Ƿ��и�������"
	 * 
	 * @param hasCopyTo
	 */
	public void setHasCopyTo(boolean hasCopyTo) {
		this.hasCopyTo = hasCopyTo;
	}

	/**
	 * �Ƿ����޶�
	 * 
	 * @return
	 */
	public boolean isHasRevise() {
		return hasRevise;
	}

	/**
	 * ����"�Ƿ����޶�"
	 * 
	 * @param hasRevise
	 */
	public void setHasRevise(boolean hasRevise) {
		this.hasRevise = hasRevise;
	}

	/**
	 * �Ƿ���ǩ��
	 * 
	 * @return
	 */
	public boolean isHasSignOption() {
		return hasSignOption;
	}

	/**
	 * ����"�Ƿ���ǩ��"
	 * 
	 * @param hasSignOption
	 */
	public void setHasSignOption(boolean hasSignOption) {
		this.hasSignOption = hasSignOption;
	}

	/**
	 * �Ƿ����ڼ�
	 * 
	 * @return
	 */
	public boolean isHasPeriod() {
		return hasPeriod;
	}

	/**
	 * ����"�Ƿ����ڼ�"
	 * 
	 * @param hasPeriod
	 */
	public void setHasPeriod(boolean hasPeriod) {
		this.hasPeriod = hasPeriod;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * �Ƿ��а汾��
	 * <p>
	 * ��"�Ƿ����ڼ�"��������"�Ƿ����޶�"��ͬ
	 * 
	 * @return
	 */
	public boolean isHasVersionNumber() {
		return !hasPeriod;
	}

	/**
	 * �Ƿ���ִ��
	 * <p>
	 * ��"�Ƿ����ڼ�"��������"�Ƿ����޶�"��ͬ
	 * 
	 * @return
	 */
	public boolean isHasExecute() {
		return !hasPeriod;
	}

}
