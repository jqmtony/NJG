package com.kingdee.eas.fdc.basedata;

/**
 * ×Ö·û´®°ïÖúÀà
 * @author xiaohong_shi
 *
 */
public class FDCStrHelper {
	private FDCStrHelper(){};
	
	public static boolean isEmpty(Object obj){
		return obj==null?true:obj.toString().trim().length()==0;
	}
}
