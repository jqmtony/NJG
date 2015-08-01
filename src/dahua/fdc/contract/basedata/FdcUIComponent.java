/**
 * @(#)EcUIComponent.java 1.0 2014-3-19
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 描述：房地产UI组件
 * 
 * @author 王正
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
	 * 业务接口对象，供调用后台方法使用
	 */
	protected ICoreBase bizInterface;

	/**
	 * 业务接口对应的BOSType对象
	 */
	protected BOSObjectType bizType;

	/**
	 * 业务接口对应的BOSType字符串
	 */
	protected String entityBOSType;

	/**
	 * 编辑界面名称
	 */
	protected String editUIName;

	/**
	 * 编辑界面默认打开方式
	 */
	protected String editUIModal;
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 是否有复制生成
	 */
	protected boolean hasCopyTo = false;

	/**
	 * 是否有修订
	 */
	protected boolean hasRevise = false;

	/**
	 * 是否有签发
	 */
	protected boolean hasSignOption = false;

	/**
	 * 是否有期间
	 */
	protected boolean hasPeriod = false;

	/**
	 * 是否有版本号
	 * <p>
	 * 与"是否有期间"互斥且与"是否有修订"相同
	 */
	// protected boolean hasVersionNumber = false;
	/**
	 * 是否有执行
	 * <p>
	 * 与"是否有期间"互斥且与"是否有修订"相同
	 */
	// protected boolean hasExecute = false;
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 默认构造器
	 */
	public FdcUIComponent() {
	}

	/**
	 * 构造器
	 * 
	 * @param hasCopyTo
	 *            是否有复制生成
	 * @param hasRevise
	 *            是否有修订
	 * @param hasSignOption
	 *            是否有签发
	 * @param hasPeriod
	 *            是否有期间
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
	 * 是否有复制生成
	 * 
	 * @return
	 */
	public boolean isHasCopyTo() {
		return hasCopyTo;
	}

	/**
	 * 设置"是否有复制生成"
	 * 
	 * @param hasCopyTo
	 */
	public void setHasCopyTo(boolean hasCopyTo) {
		this.hasCopyTo = hasCopyTo;
	}

	/**
	 * 是否有修订
	 * 
	 * @return
	 */
	public boolean isHasRevise() {
		return hasRevise;
	}

	/**
	 * 设置"是否有修订"
	 * 
	 * @param hasRevise
	 */
	public void setHasRevise(boolean hasRevise) {
		this.hasRevise = hasRevise;
	}

	/**
	 * 是否有签发
	 * 
	 * @return
	 */
	public boolean isHasSignOption() {
		return hasSignOption;
	}

	/**
	 * 设置"是否有签发"
	 * 
	 * @param hasSignOption
	 */
	public void setHasSignOption(boolean hasSignOption) {
		this.hasSignOption = hasSignOption;
	}

	/**
	 * 是否有期间
	 * 
	 * @return
	 */
	public boolean isHasPeriod() {
		return hasPeriod;
	}

	/**
	 * 设置"是否有期间"
	 * 
	 * @param hasPeriod
	 */
	public void setHasPeriod(boolean hasPeriod) {
		this.hasPeriod = hasPeriod;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 是否有版本号
	 * <p>
	 * 与"是否有期间"互斥且与"是否有修订"相同
	 * 
	 * @return
	 */
	public boolean isHasVersionNumber() {
		return !hasPeriod;
	}

	/**
	 * 是否有执行
	 * <p>
	 * 与"是否有期间"互斥且与"是否有修订"相同
	 * 
	 * @return
	 */
	public boolean isHasExecute() {
		return !hasPeriod;
	}

}
