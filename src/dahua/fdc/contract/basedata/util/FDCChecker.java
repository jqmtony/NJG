package com.kingdee.eas.fdc.basedata.util;

import java.util.ArrayList;
import java.util.List;


public class FDCChecker {
	private List itemList=new ArrayList();
	public void addItem(FDCCheckItem item){
		itemList.add(item);
	}
	public Object check(FDCCheckEvent event){
		for(int i=0;i<itemList.size();i++){
			FDCCheckItem item=(FDCCheckItem)itemList.get(i);
			item.check(event);
		}
		return event.getRetValue();
	}
}
