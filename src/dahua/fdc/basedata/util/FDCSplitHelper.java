package com.kingdee.eas.fdc.basedata.util;

import java.math.BigDecimal;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;


/**
 * FDC拆分Helper类
 * @Modified By owen_wen 2010-12-30 将添加的4个方法移至FDCSplitClientHelper中。
 */
public class FDCSplitHelper {

	//	protected static final FDCCostSplit fdcCostSplit=new FDCCostSplit(null);
		/**
		 * 金额修正入口
		 * @param allEntrys	拆分分录集合
		 * @param curEntry	当前分录
		 * @param prop		要修正的分录属性，如：amount，payedAmt等
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
	 * 描述：修正非明星项目的金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
	private static void adjustAmountProject(IObjectCollection allEntrys, int index,String prop){
		FDCSplitBillEntryInfo curEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(index);		
		if(curEntry.isIsLeaf()){
			return;
		}		
		//父级
		BigDecimal curAmount=curEntry.getBigDecimal(prop);	
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);						
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();				
		//直接下级工程
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
					//修正项为金额最大的项
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
	 * 描述：修正非明细科目的金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
	private static void adjustAmountAccount(IObjectCollection allEntrys, int index,String prop){
		FDCSplitBillEntryInfo curEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(index);	
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//父级
		BigDecimal curAmount=curEntry.getBigDecimal(prop);
		
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);			
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		
	
		
		//直接下级工程
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
	 * 描述：汇总分摊发票金额（汇总生成非明细工程项目中附加科目的成本，其值为下级工程项目相同科目的成本之和）
	 * @return
	 * @param allEntrys
	 * @param curIndex
	 * @param prop	要做汇总的属性名 
	 * 
	 * 从付款拆分中拆出 by sxhong 2009-07-20 11:25:09 
	 */
	public static void totAmountAddlAcct(IObjectCollection allEntrys,int curIndex,String prop){
	 	if(curIndex>=allEntrys.size()){
			return;
		}
		
		FDCSplitBillEntryInfo topEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(curIndex);
		if(topEntry==null||topEntry.getCostAccount()==null||topEntry.getCostAccount().getCurProject()==null||topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//明细工程的科目		
			return;			
		}
	
		//当前工程项目的下级成本科目
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
		
		//下级工程项目的相同成本科目
		int topLevel=topEntry.getLevel();
		
		BigDecimal amount=null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()>topLevel){
				if(entry.getLevel()==topLevel+1){
					if(FDCCostSplit.isChildProjSameAcct(entry,topEntry)){
						totAmountAddlAcct(allEntrys,i,prop);				
						
						//取属性
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
		
		//topEntry.isIsAddlAccount() 条件增加 by sxhong 这段代码应该是有问题的
		//进行累加
		if(topLevel!=0&&topEntry.isIsAddlAccount()){
			//设置值
			topEntry.setBigDecimal(prop, amountTotal);
		}
		
	}
}
