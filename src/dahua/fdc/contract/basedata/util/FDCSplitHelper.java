package com.kingdee.eas.fdc.basedata.util;

import java.math.BigDecimal;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;


/**
 * FDC���Helper��
 * @Modified By owen_wen 2010-12-30 ����ӵ�4����������FDCSplitClientHelper�С�
 */
public class FDCSplitHelper {

	//	protected static final FDCCostSplit fdcCostSplit=new FDCCostSplit(null);
		/**
		 * ����������
		 * @param allEntrys	��ַ�¼����
		 * @param curEntry	��ǰ��¼
		 * @param prop		Ҫ�����ķ�¼���ԣ��磺amount��payedAmt��
		 */
		public static void adjustAmount(IObjectCollection allEntrys,FDCSplitBillEntryInfo curEntry,String prop){
	    	FDCSplitBillEntryInfo entry=curEntry;    	
			int index=allEntrys.indexOf(curEntry);
			if(index==-1){
				return;
			}
	    	
	    	boolean isProj=false;
	    	if(!entry.isIsAddlAccount()){
	    		if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
	    			if(!entry.isIsLeaf()){
	    				isProj=true;
	    			}
	    		}else{
					isProj=true;
	    		}
			}    	
	    	
	    	if(isProj){
	    		adjustAmountProject(allEntrys, index,prop);
	    	}else{
	    		adjustAmountAccount(allEntrys, index,prop);    		
	    	}
	    }

	/**
	 * ������������������Ŀ�Ľ��
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
	private static void adjustAmountProject(IObjectCollection allEntrys, int index,String prop){
		FDCSplitBillEntryInfo curEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(index);		
		if(curEntry.isIsLeaf()){
			return;
		}		
		//����
		BigDecimal curAmount=curEntry.getBigDecimal(prop);	
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);						
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();				
		//ֱ���¼�����
		FDCSplitBillEntryInfo entry=null;
		int idx=0;
		BigDecimal amountMax=FDCHelper.ZERO;		
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
					amount=entry.getBigDecimal(prop);
					if(amount==null){
						amount=FDCHelper.ZERO;
					}					
					amountTotal=amountTotal.add(amount);
					//������Ϊ���������
					//if(amount.compareTo(FDCHelper.ZERO)!=0){
					if(amount.compareTo(amountMax)>=0){
						amountMax=amount;
						idx=i;						
					}
				}else{
					
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		if(idx>0 && curAmount!=null&&curAmount.compareTo(amountTotal)!=0){	
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(idx);
			amount=entry.getBigDecimal(prop);
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setBigDecimal(prop, amount);
		}
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
					
					if(!entry.isIsLeaf()){
						adjustAmountAccount(allEntrys,i,prop);
					}
					
					if(!entry.getCostAccount().getCurProject().isIsLeaf()){
						adjustAmountProject(allEntrys,i,prop);
						
					}
					
				}else{
					
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
	}

	/**
	 * ��������������ϸ��Ŀ�Ľ��
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
	private static void adjustAmountAccount(IObjectCollection allEntrys, int index,String prop){
		FDCSplitBillEntryInfo curEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(index);	
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//����
		BigDecimal curAmount=curEntry.getBigDecimal(prop);
		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);			
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		
	
		
		//ֱ���¼�����
		FDCSplitBillEntryInfo entry=null;
	
		int idx=0;
		
		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);	
						
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					amount=entry.getBigDecimal(prop);
					if(amount==null){
						amount=FDCHelper.ZERO;
					}
					
					amountTotal=amountTotal.add(amount);
					
					if(amount.compareTo(FDCHelper.ZERO)!=0){
						idx=i;						
					}
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		
	
		if(idx>0 && curAmount.compareTo(amountTotal)!=0){			
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(idx);	
	
			amount=entry.getBigDecimal(prop);
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setBigDecimal(prop, amount);
		}
		
	
		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(idx);	
			
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					if(!entry.isIsLeaf()){
						adjustAmountAccount(allEntrys,i,prop);
					}
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
	}

	/**
	 * 
	 * ���������ܷ�̯��Ʊ���������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ�����ֵΪ�¼�������Ŀ��ͬ��Ŀ�ĳɱ�֮�ͣ�
	 * @return
	 * @param allEntrys
	 * @param curIndex
	 * @param prop	Ҫ�����ܵ������� 
	 * 
	 * �Ӹ������в�� by sxhong 2009-07-20 11:25:09 
	 */
	public static void totAmountAddlAcct(IObjectCollection allEntrys,int curIndex,String prop){
	 	if(curIndex>=allEntrys.size()){
			return;
		}
		
		FDCSplitBillEntryInfo topEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(curIndex);
		if(topEntry==null||topEntry.getCostAccount()==null||topEntry.getCostAccount().getCurProject()==null||topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//��ϸ���̵Ŀ�Ŀ		
			return;			
		}
	
		//��ǰ������Ŀ���¼��ɱ���Ŀ
		int acctLevel=topEntry.getCostAccount().getLevel();
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
	
			if(entry.getCostAccount().getCurProject().getId().equals(topEntry.getCostAccount().getCurProject().getId())){
				if(entry.getCostAccount().getLevel()>acctLevel){
					if(entry.getCostAccount().getLevel()==acctLevel+1){
						totAmountAddlAcct(allEntrys,i,prop);										
					}					
				}else{
					break;
				}
				
			}else{
				break;
			}
		}
		
		//�¼�������Ŀ����ͬ�ɱ���Ŀ
		int topLevel=topEntry.getLevel();
		
		BigDecimal amount=null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()>topLevel){
				if(entry.getLevel()==topLevel+1){
					if(FDCCostSplit.isChildProjSameAcct(entry,topEntry)){
						totAmountAddlAcct(allEntrys,i,prop);				
						
						//ȡ����
						amount=entry.getBigDecimal(prop);
						if(amount==null){
							amount=FDCHelper.ZERO;
						}
						amountTotal=amountTotal.add(amount);					
					}					
				}
				
			}else if(entry.getLevel()<topLevel){
				//break;
			}
		}
		
		//topEntry.isIsAddlAccount() �������� by sxhong ��δ���Ӧ�����������
		//�����ۼ�
		if(topLevel!=0&&topEntry.isIsAddlAccount()){
			//����ֵ
			topEntry.setBigDecimal(prop, amountTotal);
		}
		
	}
}
