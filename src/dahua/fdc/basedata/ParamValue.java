package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

import com.kingdee.util.PropertyContainer;

/**
 * ������������˴��ݲ���
 * @author xiaohong_shi
 *
 */
public class ParamValue extends PropertyContainer implements Serializable{
	public ParamValue() {
		super();
	}
	protected ParamValue(boolean init) {
	    super(init);
	}
	
	public ParamValue(int initialCapacity) {
		super(initialCapacity);
	}
}
