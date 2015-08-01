/**
 * 
 */
package com.kingdee.eas.fdc.finance;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;

/**
 * 
 * @author sxhong Date 2007-3-22
 */
public class AutoSplitSortor implements Comparator {
	private Collator comparator = Collator.getInstance(Locale.getDefault());
	private static AutoSplitSortor sortComparator=null;
	private static Comparator getSortComparator(){
		if(sortComparator==null){
			sortComparator=new AutoSplitSortor();
		}
		return sortComparator;
	}
	public int compare(Object o1, Object o2) {
		if (o1 instanceof FDCSplitBillEntryInfo
				&& o2 instanceof FDCSplitBillEntryInfo) {
			return compare((FDCSplitBillEntryInfo) o1,
					(FDCSplitBillEntryInfo) o2);
		}
		return 0;
	}

	/**
	 * �������0����Ŀ�ı��룭>��Ŀ����˳��>������������ȣ���>��Ŀ����˳��>��ʾ˳��
	 * @param info1
	 * @param info2
	 * @return
	 */
	private int compare(FDCSplitBillEntryInfo info1, FDCSplitBillEntryInfo info2) {
		int compareValue = 0;
		compareValue = comparePrj(info1.getCostAccount().getCurProject(), info2.getCostAccount().getCurProject());
		if (compareValue == 0) {
			compareValue = compareByDistributeAmt(info1, info2);			
			if (compareValue == 0) {
				String accountNumber1 = info1.getCostAccount().getLongNumber();
				String accountNumber2 = info2.getCostAccount().getLongNumber();
				compareValue = compareNumber(accountNumber1, accountNumber2);
				if (compareValue == 0) {
					int seq1 = info1.getSeq();
					int seq2 = info2.getSeq();
					compareValue = seq1 - seq2;
				}
			}
		}
		return compareValue;
		
	}
	
	/**
	 * ��������������Ƚϣ����������Ҫ��ǰ��ʾ
	 * @Author��owen_wen
	 * @CreateTime��2011-12-15
	 */
	private int compareByDistributeAmt(FDCSplitBillEntryInfo info1, FDCSplitBillEntryInfo info2) {
		if (FDCHelper.compareTo(info1.get("distributeamt"), FDCNumberHelper.ZERO) < 0
				&& FDCHelper.compareTo(info2.get("distributeamt"), FDCNumberHelper.ZERO) > 0) {
			return -1;
		}
		if (FDCHelper.compareTo(info1.get("distributeamt"), FDCNumberHelper.ZERO) > 0
				&& FDCHelper.compareTo(info2.get("distributeamt"), FDCNumberHelper.ZERO) < 0) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * ������Ŀ����
	 * @return
	 */
	private int comparePrj(CurProjectInfo info1,CurProjectInfo info2){
		String curNumber1 = info1.getLongNumber();
		String curNumber2 = info2.getLongNumber();
		if(FDCHelper.isEmpty(curNumber1)){
			return -1;
		}
		if(FDCHelper.isEmpty(curNumber2)){
			return 1;
		}
		if(curNumber1.indexOf('!')<0){
			curNumber1=curNumber1+"!";
		}
		if(curNumber2.indexOf('!')<0){
			curNumber2=curNumber2+"!";
		}
		//R110708-0056����������ʵ�������뷢Ʊ������Զ�ƥ��˳��һ��
		//�Ƚ��ϼ�������Ŀ�Ƿ���ͬ�������ͬ��ȽϿ���˳��
//		String topCurNumber1=curNumber1.substring(0,curNumber1.indexOf('!'));
//		String topCurNumber2=curNumber2.substring(0,curNumber2.indexOf('!'));
		String topCurNumber1=curNumber1.substring(0,curNumber1.lastIndexOf('!'));
		String topCurNumber2=curNumber2.substring(0,curNumber2.lastIndexOf('!'));
		int value=compareNumber(topCurNumber1, topCurNumber2);
		if(value==0){
			value=info1.getSortNo()-info2.getSortNo();
		}
		if(value==0){
			value=compareNumber(curNumber1, curNumber2);
		}
		return value;
		
	}
	private int compareNumber(String number1, String number2) {
		if(FDCHelper.isEmpty(number1)){
			return -1;
		}
		if(FDCHelper.isEmpty(number2)){
			return 1;
		}
		String nums1[] = number1.split("!");
		String nums2[] = number2.split("!");
		int value = 0;
		for (int i = 0; i < nums1.length && i < nums2.length; i++) {
			// value=nums1[i].compareTo(nums2[i]);
			value = comparator.compare(nums1[i],nums2[i]);
			if (value != 0) {
				return value;
			}
		}
		// number1,number2���̲�һ����ǰ�벿����һ����
		value = nums2.length - nums1.length;

		return value;
	}
	/**
	 * ����ϸ�д���ϸ�л�����ȥ������ͨ��ĳ�ּ��㷽��ֱ�Ӽ������
	 * by cassiel_peng 2010-01-23 
	 */
	public static List filterExceptProductSplit(AbstractObjectCollection collection) {
		List list = new ArrayList();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			FDCSplitBillEntryInfo info = (FDCSplitBillEntryInfo) iter.next();
			//��Ʒ��ֵĳ���,��Ϊ��������ϸ������Ҫ����ϸ�л�����ȥ�ģ���������в�Ʒ��֣��ڵ㸶���ֱ༭����� "���������"ʱ�����¹�ʽ��Ҫ��ѭ��
			//�����ɱ����=������ϸ��Ŀ�еĹ����ɱ����(�ɱ���ֽ��/������ϸ��Ŀ�еĳɱ���ֽ��)  
			//����������=������ϸ��Ŀ�еĹ���������(�ɱ���ֽ��/������ϸ��Ŀ�еĳɱ���ֽ��)  
			//������Ʊ���=������ϸ��Ŀ�еĹ�����Ʊ���(�ɱ���ֽ��/������ϸ��Ŀ�еĳɱ���ֽ��)  
			//��������ڹ��˷���ϸ�����ݵ�ʱ�򽫸ò�Ʒ��ּ�¼Ҳ���˳�ȥ�Ļ����ͼ��㲻����Ʒ��ֵ�����ϸ��¼����Ӧ�����ɱ������������������Ʊ�����
			if ((info.getLevel() < 0 || !info.isIsLeaf()) && !CostSplitTypeEnum.PRODSPLIT.equals(info.getSplitType())) {
				continue;
			}
			list.add(info);
		}
		return list;
	}
	public static List sort(AbstractObjectCollection collection) {
		List list = new ArrayList();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			FDCSplitBillEntryInfo info = (FDCSplitBillEntryInfo) iter.next();

			if (info.getLevel() < 0 || !info.isIsLeaf())
				continue;

			list.add(info);
		}
		Collections.sort(list, getSortComparator());
		
		return list;
	}
}
