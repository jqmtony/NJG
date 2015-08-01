package com.kingdee.eas.fdc.basedata.util;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

public class FDCCheckEvent extends EventObject {
	private final static String FDCCHECKEVENT_PATAM="FDCCheckEvent_Param";
	private final static String FDCCHECKEVENT_RETVALUE="FDCCheckEvent_RetValue";
	private Map innerMap =new HashMap();
	public FDCCheckEvent(Object source,Object checkParam) {
		super(source);
		innerMap.put(FDCCHECKEVENT_PATAM, checkParam);
	}
	
	public Object getCheckParam(){
		return innerMap.get(FDCCHECKEVENT_PATAM);
	}
	public Object getRetValue(){
		return innerMap.get(FDCCHECKEVENT_RETVALUE);
	}
	public void setRetValue(Object obj){
		innerMap.put(FDCCHECKEVENT_RETVALUE,obj);
	}
	
}
