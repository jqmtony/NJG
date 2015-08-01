package com.kingdee.eas.fdc.basedata.util;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;

public class IncomeAccountHelper {

	/**
	 * 从incomeAcctMap 内找到longNumber对应科目的明细科目
	 * 逻辑如下：
	 * 1.如果incomeAcctMap 内有longnumber对应的科目且是明细科目的话则直接返回
	 * 2.如果incomeAcctMap 内有longnumber对应的科目但不是明细科目的话则找到他的第一个明细科目
	 * 2.如果incomeAcctMap 内没有longnumber对应的科目则找longnumber的上级对应的科目的明细科目
	 * @param incomeAcctMap 以longnumber为健的TreeMap
	 * @param longnumber
	 * @return
	 */
	public static IncomeAccountInfo getIncomeAccount(Map incomeAcctMap,String longnumber){
		TreeMap treeMap=null;
		if(!(incomeAcctMap instanceof TreeMap)){
			treeMap=new TreeMap(incomeAcctMap);
		}else{
			treeMap=(TreeMap)incomeAcctMap;
		}
		IncomeAccountInfo acct=null;
		for(String number=longnumber;number!=null;number=number.substring(0,number.lastIndexOf('!'))){
			acct=(IncomeAccountInfo)incomeAcctMap.get(number);
			//找到科目返回
			if(acct!=null){
				break;
			}
			//最上级返回
			if(number.lastIndexOf('!')<0){
				break;
			}
		}
		
		if(acct==null){
			//not find
			return null;
		}
		
		if(!acct.isIsLeaf()){
			//不是明细则明细在后面的Map里面，如下方法在后面的Map里面找到明细
			SortedMap tailMap = treeMap.tailMap(acct.getLongNumber());
			for(Iterator iter=tailMap.values().iterator();iter.hasNext();){
				IncomeAccountInfo temp=(IncomeAccountInfo)iter.next();
				if(temp.isIsLeaf()){
					acct=temp;
					break;
				}
			}
			
		}
		
		
		return acct;
	}
	
}
