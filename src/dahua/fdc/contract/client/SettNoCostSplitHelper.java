package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryInfo;

public class SettNoCostSplitHelper {

	static void adjustQuaAmount(IObjectCollection allEntrys,SettNoCostSplitEntryInfo curEntry){
		SettNoCostSplitEntryInfo entry=curEntry;    	
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
    		adjustQuaAmountProject(allEntrys, index);
    	}else{
    		adjustQuaAmountAccount(allEntrys, index);    		
    	}
    }
	
	/**
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private static void adjustQuaAmountProject(IObjectCollection allEntrys, int index){
    	SettNoCostSplitEntryInfo curEntry =(SettNoCostSplitEntryInfo)allEntrys.getObject(index);		
		if(curEntry.isIsLeaf()){
			return;
		}		
		//����
		BigDecimal curAmount=curEntry.getGrtSplitAmt();		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);						
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();				
		//ֱ���¼�����
		IRow row;
		SettNoCostSplitEntryInfo entry=null;
		int idx=0;
		BigDecimal amountMax=FDCHelper.ZERO;		
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(SettNoCostSplitEntryInfo)allEntrys.getObject(i);			
			if(entry.getLevel()==(level+1)){
//				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
//						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
					amount=entry.getGrtSplitAmt();
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
//				}else{
//					
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		if(idx>0 && curAmount!=null&&curAmount.compareTo(amountTotal)!=0){	
			entry=(SettNoCostSplitEntryInfo)allEntrys.getObject(idx);
			amount=entry.getGrtSplitAmt();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setGrtSplitAmt(amount);
		}
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(SettNoCostSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
//				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
//						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
//					
					if(!entry.isIsLeaf()){
						adjustQuaAmountAccount(allEntrys,i);
					}
					
//					if(!entry.getCostAccount().getCurProject().isIsLeaf()){
//						adjustQuaAmountProject(allEntrys,i);
//						
//					}
					
//				}else{
//					
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
    }
    
	
	
	/**
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private static void adjustQuaAmountAccount(IObjectCollection allEntrys, int index){
    	SettNoCostSplitEntryInfo curEntry =(SettNoCostSplitEntryInfo)allEntrys.getObject(index);	
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//����
		BigDecimal curAmount=curEntry.getGrtSplitAmt();
		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);			
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//ֱ���¼�����
		IRow row;
		SettNoCostSplitEntryInfo entry=null;

		int idx=0;
		
		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(SettNoCostSplitEntryInfo)allEntrys.getObject(i);	
						
			if(entry.getLevel()==(level+1)){
//				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					amount=entry.getGrtSplitAmt();
					if(amount==null){
						amount=FDCHelper.ZERO;
					}
					
					amountTotal=amountTotal.add(amount);
					
					if(amount.compareTo(FDCHelper.ZERO)!=0){
						idx=i;						
					}
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		

		if(idx>0 && curAmount.compareTo(amountTotal)!=0){			
			entry=(SettNoCostSplitEntryInfo)allEntrys.getObject(idx);	

			amount=entry.getGrtSplitAmt();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setGrtSplitAmt(amount);
		}
		

		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(SettNoCostSplitEntryInfo)allEntrys.getObject(idx);	
			
			
			if(entry.getLevel()==(level+1)){
//				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					if(!entry.isIsLeaf()){
						adjustQuaAmountAccount(allEntrys,i);
					}
//				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
    }
    
    /**
	 * ���������ܷ�̯���������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ�����ֵΪ�¼�������Ŀ��ͬ��Ŀ�ĳɱ�֮�ͣ�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-29 <p>
	 */
    public static void totAmountQuaAddlAcct(IObjectCollection allEntrys,int curIndex){  		
	}
}
