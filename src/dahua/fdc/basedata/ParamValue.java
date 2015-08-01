package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

import com.kingdee.util.PropertyContainer;

/**
 * 用于向服务器端传递参数
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
