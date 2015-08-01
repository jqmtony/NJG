package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

import com.kingdee.util.PropertyContainer;

/**
 * 用于服务器端向客户端传递参数
 * @author xiaohong_shi
 *
 */
public class RetValue extends PropertyContainer implements Serializable{
	public RetValue() {
		super();
	}
	protected RetValue(boolean init) {
	    super(init);
	}
	
	public RetValue(int initialCapacity) {
		super(initialCapacity);
	}
	/**
	 * add by needle at 2004/3/12
	 */
	public void clear() {
		if(this.values!=null){
			this.values.clear();
		}
	}
	public void putAll(RetValue retValue){
		this.values.putAll(retValue.values);
	}
}
