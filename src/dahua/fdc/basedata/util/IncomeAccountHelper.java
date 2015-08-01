package com.kingdee.eas.fdc.basedata.util;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;

public class IncomeAccountHelper {

	/**
	 * ��incomeAcctMap ���ҵ�longNumber��Ӧ��Ŀ����ϸ��Ŀ
	 * �߼����£�
	 * 1.���incomeAcctMap ����longnumber��Ӧ�Ŀ�Ŀ������ϸ��Ŀ�Ļ���ֱ�ӷ���
	 * 2.���incomeAcctMap ����longnumber��Ӧ�Ŀ�Ŀ��������ϸ��Ŀ�Ļ����ҵ����ĵ�һ����ϸ��Ŀ
	 * 2.���incomeAcctMap ��û��longnumber��Ӧ�Ŀ�Ŀ����longnumber���ϼ���Ӧ�Ŀ�Ŀ����ϸ��Ŀ
	 * @param incomeAcctMap ��longnumberΪ����TreeMap
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
			//�ҵ���Ŀ����
			if(acct!=null){
				break;
			}
			//���ϼ�����
			if(number.lastIndexOf('!')<0){
				break;
			}
		}
		
		if(acct==null){
			//not find
			return null;
		}
		
		if(!acct.isIsLeaf()){
			//������ϸ����ϸ�ں����Map���棬���·����ں����Map�����ҵ���ϸ
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
