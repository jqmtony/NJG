package com.kingdee.eas.fdc.basedata;

import java.math.BigDecimal;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;

/**
 * 此类包含房地产单据拆分的拆分逻辑及相关方法
 * Date 2006-12-7
 */
public class FDCNoCostSplit
{
    private static final Logger logger = CoreUIObject.getLogger(FDCNoCostSplit.class);
    private Context ctx=null;
    
    public FDCNoCostSplit(Context ctx){
    	this.ctx=ctx;
    }

	/**
	 * 描述：汇总分摊指标值
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-11-07 <p>
	 */
    public void totApptValue(IObjectCollection allEntrys,FDCNoCostSplitBillEntryInfo curEntry){
    	int curIndex=allEntrys.indexOf(curEntry);
    	if(curIndex==-1){
    		return;
    	}
    	CostSplitTypeEnum costSplitType=curEntry.getSplitType();
		//计算汇总数	
		if(CostSplitTypeEnum.PRODSPLIT==costSplitType){
			totApptValueProduct(allEntrys,curIndex);
		}else if(CostSplitTypeEnum.BOTUPSPLIT==costSplitType){
			totApptValueBotUp(allEntrys,curIndex);
		}else{
			totApptValueTopDown(allEntrys,curIndex);
		}	
    
    }
	
	/**
	 * 描述：是否产品拆分父级
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-11-30 <p>
	 */
    public boolean isProdSplitParent(FDCNoCostSplitBillEntryInfo entry){		
		boolean isTrue=false;
		
		if(!entry.isIsLeaf()
				&& entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
			isTrue=true;
		}
		
		return isTrue;
	}
	
	/**
	 * 描述：是否产品拆分明细
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-11-30 <p>
	 */
	public boolean isProdSplitLeaf(FDCNoCostSplitBillEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
    

	
	/**
	 * 描述：是否允许产品拆分
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    public boolean isProdSplitEnabled(FDCNoCostSplitBillEntryInfo entry){		
		boolean isTrue=false;
		
		if(entry.getCurProject().isIsLeaf()){
			
			if(entry.getSplitType()==null || entry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				isTrue=true;
			}else if(entry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT) || entry.getSplitType().equals(CostSplitTypeEnum.BOTUPSPLIT)){
				isTrue=true;
	    	}else if(entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
	    		if(!entry.isIsLeaf()){
	    			isTrue=true;
	    		}
	    	}			
		}
		
		return isTrue;
	}
	
       
    
    
    
    /**
     * 汇总分摊金额
     * @author sxhong  		Date 2006-12-11
     * @param curIndex
     */
    public BigDecimal totAmount(IObjectCollection allEntrys){
    	
    	

		BigDecimal amountTotal=FDCHelper.ZERO;
		BigDecimal amount=FDCHelper.ZERO;
		FDCNoCostSplitBillEntryInfo entry=null;
		//计算拆分总金额
		for(int i=0; i<allEntrys.size(); i++){
			entry =(FDCNoCostSplitBillEntryInfo)(allEntrys.getObject(i));
			
			if(entry.getLevel()==0){
				amount=entry.getAmount();
				if(amount!=null){
					amountTotal=amountTotal.add(amount);
				}
			}						
		}			
//		TODO 设置到汇总控件
//		txtSplitedAmount.setValue(amountTotal);
		
    	return amountTotal;
    }
	/**
	 * 描述：汇总分摊指标值（产品拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void totApptValueProduct(IObjectCollection allEntrys,int curIndex){
    	FDCNoCostSplitBillEntryInfo curEntry = (FDCNoCostSplitBillEntryInfo)allEntrys.getObject(curIndex); 
    	if(curEntry.isIsLeaf()){
    		return;
    	}
    	
    	int level=curEntry.getLevel();

		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;

		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCNoCostSplitBillEntryInfo entry = (FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==level+1){				
				//累计
				apportionValue=entry.getApportionValue();
				if(apportionValue==null){
					apportionValue=FDCHelper.ZERO;
				}
				directAmount=entry.getDirectAmount();
				if(directAmount==null){
					directAmount=FDCHelper.ZERO;
				}
				
				apportionTotal=apportionTotal.add(apportionValue);
				directTotal=directTotal.add(directAmount);	
								
				//calcApportionDataTopDown(i);
			}
			else if(entry.getLevel()<=level){
				break;
			}			
		}	

		curEntry.setApportionValueTotal(apportionTotal);
		curEntry.setDirectAmountTotal(directTotal);
    }
    
	/**
	 * 描述：汇总分摊指标值（自动拆分，同时适用其中包含的产品拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void totApptValueTopDown(IObjectCollection allEntrys,int topIndex){
    	FDCNoCostSplitBillEntryInfo topEntry =(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(topIndex);  
    	
    	if(topEntry.isIsLeaf()){
    		return;
    	}
    	
    	int topLevel=topEntry.getLevel();
    	BOSUuid topPrj = topEntry.getCurProject().getId();
		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;

		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
		
		FDCNoCostSplitBillEntryInfo entry=null;		

		if(topEntry.getCurProject().isIsLeaf()){		
			//明细工程的科目
    		if(isProdSplitParent(topEntry)){
    			totApptValueProduct(allEntrys,topIndex);				    			
    		}else{
    			totApptValueAddlAcct(allEntrys,topIndex);	    			
    		}	
		}else{		
			//非明细工程的各科目的成本从下级工程相同科目累加
			
			for(int i=topIndex+1; i<allEntrys.size(); i++){				
				entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
				
				if(entry.getLevel()==topLevel+1&&entry.getCurProject().getParent().getId().equals(topPrj)){
					//递归调用,处理直接下级的累加
					totApptValueTopDown(allEntrys, i);
					
					//累计
					apportionValue=entry.getApportionValue();
					if(apportionValue==null){
						apportionValue=FDCHelper.ZERO;
					}
					directAmount=entry.getDirectAmount();
					if(directAmount==null){
						directAmount=FDCHelper.ZERO;
					}
					
					apportionTotal=apportionTotal.add(apportionValue);
					directTotal=directTotal.add(directAmount);	
								
/*					if(CostSplitType.PRODSPLIT==entry.getSplitType()){
						totApptValueProduct(allEntrys, i);
					}else{
						totApptValueTopDown(allEntrys,i);
					}*/
				}else{
//					topEntry.setApportionValueTotal(apportionTotal);
//					topEntry.setDirectAmountTotal(directTotal);
//					apportionTotal=FDCHelper.ZERO;
//					directTotal=FDCHelper.ZERO;
//					totApptValueTopDown(allEntrys, i);
//					break;
//					isSameTree=false;
				}
			}
			
			topEntry.setApportionValueTotal(apportionTotal);
			topEntry.setDirectAmountTotal(directTotal);
		}
	}
	


    
	/**
	 * 描述：汇总分摊指标值（末级拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-25 <p>
	 */
    private void totApptValueBotUp(IObjectCollection allEntrys,int curIndex){
    	//先调用普通的分摊值汇总
    	totApptValueTopDown(allEntrys,curIndex);
    	FDCNoCostSplitBillEntryInfo topEntry = (FDCNoCostSplitBillEntryInfo)allEntrys.getObject(curIndex);    
		
    	//根结点
    	if(topEntry.getLevel()!=0){
    		return;
    	}
    	
    	int level=topEntry.getLevel();
		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;

		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
		
		int lastIndex=0;

		boolean isTrue=false;
		//直接费用
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
			if(entry.getLevel()>0){			
				//不包括附加科目
				//if(!entry.isIsAddlAccount()){
				if(entry.getCurProject().isIsLeaf()
						&& !entry.isIsAddlAccount() && !isProdSplitLeaf(entry)){
					directAmount=entry.getDirectAmount();
					if(directAmount==null){
						directAmount=FDCHelper.ZERO;
					}
					directTotal=directTotal.add(directAmount);
					apportionValue=entry.getApportionValue();
					if(apportionValue==null){
						apportionValue=FDCHelper.ZERO;
					}
					apportionTotal=apportionTotal.add(apportionValue);
				}
			}else{
				break;
			}
		}	
		topEntry.setApportionValueTotal(apportionTotal);
		topEntry.setDirectAmountTotal(directTotal);
	}
	
	/**
	 * 描述：汇总分摊指标值（附加科目）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void totApptValueAddlAcct(IObjectCollection allEntrys,int curIndex){
    	//非成本拆分无附加科目,暂时保留
    }
    

	/**
	 * 描述：成本分摊计算
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */    
    public void apptAmount(IObjectCollection allEntrys,FDCNoCostSplitBillEntryInfo curEntry){
		CostSplitTypeEnum costSplitType=curEntry.getSplitType();
		int curIndex=allEntrys.indexOf(curEntry);
		if(curIndex==-1){
			return;
		}
		
    	//如果归属金额为0不进行拆分，避免因直接费用而出现拆分金额为负数的情况	jelon 12/7/06
    	if(costSplitType==null || costSplitType.equals(CostSplitTypeEnum.MANUALSPLIT)){
    		//
    	}else{	    		
    		int level=curEntry.getLevel();    		
    			    			
			BigDecimal amount=curEntry.getAmount();
			if(amount==null || amount.compareTo(FDCHelper.ZERO)==0){
				
				for(int i=curIndex+1; i<allEntrys.size(); i++){
		    		FDCNoCostSplitBillEntryInfo entry = (FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
					if(entry.getLevel()>level){
						entry.setAmount(FDCHelper.ZERO);
					}else{
						break;    						
					}
				}
				
				return;
			}
    		
		}
   	
    	
    	//成本分摊
    	if(costSplitType==null || costSplitType.equals(CostSplitTypeEnum.MANUALSPLIT)){
    		//
    	}else if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){	
			apptAmountProduct(allEntrys,curIndex);
		}else if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){	
			apptAmountBotUp(allEntrys,curIndex);
		}else{	
			apptAmountTopDown(allEntrys,curIndex);
		};
		
		    	
//		if(true) return;
		//由于向上累加时存在误差，所以四舍五入在分摊完成后再进行处理	jelon 12/26/2006

		int level=curEntry.getLevel();
		BigDecimal amount=null;
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()>level){
				amount=entry.getAmount();
				
				boolean isDirectAmt=false;
				if(entry.getDirectAmount()!=null
				   &&entry.getDirectAmount().compareTo(FDCHelper.ZERO)>0
						&&(entry.getApportionValue()==null
						   ||entry.getApportionValue().compareTo(FDCHelper.ZERO)==0)){
					//直接金额大于0且没有分摊值
					isDirectAmt=true;
				}
				if(amount!=null&&!isDirectAmt){//直接金额不做处理
					amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
					entry.setAmount(amount);
				}
							
			}else{
				break;
			}
		}
		

		//修正金额，保证上下级的汇总关系
		adjustAmount(allEntrys,curEntry);
		
		
		//汇总生成非明细工程项目中附加科目的成本		jelon 12/29/2006
		totAmountAddlAcct(allEntrys,curIndex);
    }

    /**
	 * 描述：成本分摊计算（自动拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void apptAmountTopDown(IObjectCollection allEntrys,int curIndex){
    	if(curIndex>=allEntrys.size()){
    		return;
    	}
    	FDCNoCostSplitBillEntryInfo curEntry = (FDCNoCostSplitBillEntryInfo)allEntrys.getObject(curIndex);		
		/*
    	if(curEntry.isIsLeaf()){
			return;
		}
		 */  				
		//金额	
		BigDecimal amount=FDCHelper.ZERO;
		BigDecimal amountTotal=FDCHelper.ZERO;	
		
		//分摊值汇总、直接费用汇总
		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		
		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
			
					
		//父级
		int level=curEntry.getLevel();
		int projLevel=curEntry.getCurProject().getLevel();
		
		amountTotal=curEntry.getAmount();				
		if(amountTotal==null){
			amountTotal=FDCHelper.ZERO;
		}					
		
		//汇总
		apportionTotal=curEntry.getApportionValueTotal();				
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=curEntry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
		
		//子级
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);;
			
			
			if(entry.getLevel()<=level){
				break;
			}
			
			
			//直接下级
			if(entry.getLevel()==(level+1) 
					&& entry.getCurProject().getLevel()==(projLevel+1)){
				
				//分摊
				apportionValue=entry.getApportionValue();
				if(apportionValue==null){
					apportionValue=FDCHelper.ZERO;
				}
				directAmount=entry.getDirectAmount();
				if(directAmount==null){
					directAmount=FDCHelper.ZERO;
				}
				
				if(apportionValue.compareTo(FDCHelper.ZERO)==0 || apportionTotal.compareTo(FDCHelper.ZERO)==0){
					amount=directAmount;
				}else{				
					amount=apportionValue.multiply(amountTotal.subtract(directTotal));
					//amount=amount.divide(apportionTotal,2,BigDecimal.ROUND_HALF_EVEN);
					amount=amount.divide(apportionTotal,10,BigDecimal.ROUND_HALF_EVEN);
					amount=amount.add(directAmount);
				}
				
				entry.setAmount(amount);					

				//处理包含的附加科目和产品拆分	jelon 12/22/2006	
	    		if(isProdSplitParent(entry)){
	    			//产品拆分
		    		apptAmountProduct(allEntrys,i);
	    			
	    		}else if(!entry.getAccountView().isIsLeaf()){
	    			//附加科目
	    			apptAmountAddlAcct(allEntrys,i);	    			
	    		}
		    	
		    	//下级工程项目的拆分
		    	if(!entry.getCurProject().isIsLeaf()){
		    		apptAmountTopDown(allEntrys,i);
		    	}
		    	
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	

    }
    

	/**
	 * 描述：成本分摊计算（产品拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void apptAmountProduct(IObjectCollection allEntrys,int curIndex){
    	FDCNoCostSplitBillEntryInfo curEntry = (FDCNoCostSplitBillEntryInfo)allEntrys.getObject(curIndex);		
		if(curEntry.isIsLeaf()){
			return;
		}
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		//分摊值汇总、直接费用汇总
		BigDecimal apportionValue=FDCHelper.ZERO;//null;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		
		BigDecimal directAmount=FDCHelper.ZERO;//null;	
		BigDecimal directTotal=FDCHelper.ZERO;
			
		//父级
		int level=curEntry.getLevel();
		int projLevel=curEntry.getCurProject().getLevel();
		
		amountTotal=curEntry.getAmount();			
		if(amountTotal==null){
			amountTotal=FDCHelper.ZERO;
		}					
		
		//汇总
		apportionTotal=curEntry.getApportionValueTotal();				
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=curEntry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
		
		//子级
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCNoCostSplitBillEntryInfo entry =(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
			
			//直接下级
			if(entry.getLevel()==(level+1)){
				apportionValue=entry.getApportionValue();
				if(apportionValue==null){
					apportionValue=FDCHelper.ZERO;
				}
				directAmount=entry.getDirectAmount();
				if(directAmount==null){
					directAmount=FDCHelper.ZERO;
				}
				
				if(apportionValue.compareTo(FDCHelper.ZERO)==0|| apportionTotal.compareTo(FDCHelper.ZERO)==0){
					amount=directAmount;
				}else{
//						//存在分摊
					amount=apportionValue.multiply(amountTotal.subtract(directTotal));
					//amount=amount.divide(apportionTotal,2,BigDecimal.ROUND_HALF_EVEN);
					amount=amount.divide(apportionTotal,10,BigDecimal.ROUND_HALF_EVEN);
					amount=amount.add(directAmount);
				}
				entry.setAmount(amount);					
			}else if(entry.getLevel()<=level){
				break;
			}
		}    			

    }


	/**
	 * 描述：成本分摊计算（末级拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void apptAmountBotUp(IObjectCollection allEntrys,int curIndex){
    	int level;
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		//amountTotal.setScale(10);
		//update by renliang
		amountTotal = amountTotal.setScale(10);	
		
		
		
		//分摊值汇总、直接费用汇总
		BigDecimal apportionValue=FDCHelper.ZERO;//null;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		
		BigDecimal directAmount=FDCHelper.ZERO;//null;	
		BigDecimal directTotal=FDCHelper.ZERO;
		
		//金额	
		BigDecimal ratio=FDCHelper.ZERO;
		
    	FDCNoCostSplitBillEntryInfo curEntry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(curIndex);		
    	
    	//根结点
    	if(curEntry.getLevel()!=0){
    		return;
    	} 
    	int topLevel=curEntry.getLevel();
    	
    	amountTotal=curEntry.getAmount();
    	if(amountTotal==null){
    		amountTotal=FDCHelper.ZERO;
    	}
    	
    	apportionTotal=curEntry.getApportionValueTotal();
    	if(apportionTotal==null){
    		apportionTotal=FDCHelper.ZERO;
    	}
    	
    	directTotal=curEntry.getDirectAmountTotal();
    	if(directTotal==null){
    		directTotal=FDCHelper.ZERO;
    	}
    	    	
    	amount=amountTotal.subtract(directTotal);
    	if(apportionTotal.compareTo(FDCHelper.ZERO)==0){
    		ratio=FDCHelper.ZERO;
    	}else{
    		//比例值，非直接显示值，需要保证小数位数
    		ratio=amount.divide(apportionTotal,10,BigDecimal.ROUND_HALF_EVEN);	
    	}
    	
    	//calcApportionAmountBotUp(curIndex,ratio,FDCHelper.ZERO,FDCHelper.ZERO);

		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==topLevel+1){
				apptAmountBotUp(allEntrys,i,ratio,FDCHelper.ZERO,FDCHelper.ZERO);
							
			}else if(entry.getLevel()<=topLevel){
				break;
			}
		}
		
		
    	
    	
    	
    	/*    	
		if(entry.isIsLeaf()){
			return;
		}
		
		
			
		//父级
		level=entry.getLevel();
		amountTotal=entry.getAmount();
		
		//汇总
		apportionTotal=entry.getApportionValueTotal();				
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=entry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
		
		//子级
		for(int i=curIndex+1; i<kdtEntrys.getRowCount(); i++){				
			row=kdtEntrys.getRow(i);
			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
			
			//直接下级
			if(entry.getLevel()==(level+1)){				
				apportionValue=entry.getApportionValue();
				if(apportionValue==null){
					apportionValue=FDCHelper.ZERO;
				}
				directAmount=entry.getDirectAmount();
				if(directAmount==null){
					directAmount=FDCHelper.ZERO;
				}
				
				if(apportionValue.equals(FDCHelper.ZERO)){
					amount=directAmount;
				}
				else	//存在分摊
				{					
					amount=apportionValue.multiply(amountTotal.subtract(directTotal));
					amount=amount.divide(apportionTotal,2,BigDecimal.ROUND_HALF_EVEN);
					amount=amount.add(directAmount);
				}
				entry.setAmount(amount);					
				row.getCell("amount").setValue(amount);
				
				calcApportionAmountBotUp(i);
				
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	*/
    }
    

    /**
     * 描述：成本分摊计算（附加科目）
     * @author sxhong  		Date 2007-1-29
     * @param allEntrys
     * @param curIndex
     */
    private void apptAmountAddlAcct(IObjectCollection allEntrys,int curIndex){
    	//会计科目暂时不做考虑
    }

    /**
     * 描述：成本分摊计算（末级拆分，递归拆分）
     * @author sxhong  		Date 2007-1-29
     * @param allEntrys
     * @param curIndex
     * @param ratio
     * @param parApportionTotal
     * @param parDirectAmount
     */
    private void apptAmountBotUp(IObjectCollection allEntrys,int curIndex, BigDecimal ratio, BigDecimal parApportionTotal, BigDecimal parDirectAmount){
    	if(curIndex>=allEntrys.size()){
    		return;
    	}
    	if(parApportionTotal==null){
			parApportionTotal=FDCHelper.ZERO;
		}
		if(parDirectAmount==null){
			parDirectAmount=FDCHelper.ZERO;
		}
		
		FDCNoCostSplitBillEntryInfo topEntry =(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(curIndex);
    	int topLevel=topEntry.getLevel();
		BigDecimal apportionValue=topEntry.getApportionValue();
		if(apportionValue==null){
			apportionValue=FDCHelper.ZERO;
		}
		BigDecimal apportionTotal=topEntry.getApportionValueTotal();	
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}	
		
		BigDecimal directAmount=topEntry.getDirectAmount();
		if(directAmount==null){
			directAmount=FDCHelper.ZERO;
		}

		BigDecimal directTotal=FDCHelper.ZERO;
		    	
		BigDecimal amount=FDCHelper.ZERO;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		
		//分摊直接费用（目前只有明细工程明细科目才有直接费用，直接费用实际不用分摊）
		if(parApportionTotal.compareTo(FDCHelper.ZERO)!=0){
			/*directAmount=directAmount.add(
					parDirectAmount.multiply(apportionValue).divide(parApportionTotal,2,BigDecimal.ROUND_HALF_EVEN));*/	
			directAmount=directAmount.add(
					parDirectAmount.multiply(apportionValue).divide(parApportionTotal,10,BigDecimal.ROUND_HALF_EVEN));	
		}				
		
		
		if(topEntry.getCurProject().isIsLeaf()){		
			//明细工程的科目直接分摊
			
			/*amount=apportionValue.multiply(ratio).add(directAmount);
			amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
			amountTotal=amount;*/
			
			//第一级科目进行末级分摊
	    	if(!topEntry.isIsAddlAccount() && !isProdSplitLeaf(topEntry)){
	    		amount=apportionValue.multiply(ratio).add(directAmount);
				//amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
				//amountTotal=amount;	    		
	    	}
			topEntry.setAmount(amount);
			

			//处理包含的附加科目和产品拆分	jelon 12/22/2006				
	    	/*if(topEntry.isIsAddlAccount()){
	    		calcApportionAmountAddlAcct(rowIndex);
	    	}else if(isProdSplitParent(topEntry)){
	    		calcApportionAmountProduct(rowIndex);
	    	}	*/		
	    	/*if(!topEntry.getCostAccount().isIsLeaf()){
	    		if(isProdSplitParent(topEntry)){
	    			//产品拆分
		    		calcApportionAmountProduct(rowIndex);
	    		}else{
	    			//附加科目
	    			calcApportionAmountAddlAcct(rowIndex);
	    		}
	    	}*/
	    	
			//其他下级科目需按附加科目分摊或按产品分摊
    		if(isProdSplitParent(topEntry)){
    			//产品拆分
	    		apptAmountProduct(allEntrys, curIndex);
    			
    		}else if(!topEntry.getAccountView().isIsLeaf()){
    			//附加科目
    			apptAmountAddlAcct(allEntrys, curIndex);	    			
    		}
			
			
		}else{		
			//非明细工程的各科目的成本从下级工程相同科目累加
			for(int i=curIndex+1; i<allEntrys.size(); i++){				
				FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
				
				if(entry.getLevel()==topLevel+1){
					apptAmountBotUp(allEntrys,i,ratio,apportionTotal,directAmount);					
					amountTotal=amountTotal.add(entry.getAmount());			
				}else if(entry.getLevel()<=topLevel){
					break;
				}
			}
			
			topEntry.setAmount(amountTotal);
		}			
    		
	}
 

    /**
     * 面子刷新接口（用于合同，变更拆分，无文本合同付款拆分）
     * @author sxhong  		Date 2006-12-13
     * @param fdcSplitBillInfo 
     * @param iFDCNoCostSplitBillEntry 本地接口或者远程接口，null值则不更新到数据库
     * @throws BOSException
     * @throws EASBizException
     */
    public void refreshApportionAmount(FDCNoCostSplitBillInfo fdcSplitBillInfo,IFDCNoCostSplitBillEntry iFDCNoCostSplitBillEntry) throws BOSException,EASBizException{
    	
    	IObjectCollection allEntrys=(IObjectCollection) fdcSplitBillInfo.get("entrys");
    	FDCNoCostSplitBillEntryInfo entry=null;
    	for(int i=0;i<allEntrys.size();i++){
    		entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
    		if(entry.getLevel()==0&&entry.getSplitType()!=null&&entry.getSplitType()!=CostSplitTypeEnum.MANUALSPLIT){
    			CurProjectCollection curProjectCollection=getEntryCurProject(entry);
    			setApportionValue(allEntrys,i,curProjectCollection);
    			totApptValue(allEntrys,entry);
    			apptAmount(allEntrys,entry);
    		}
    	}
    	if(iFDCNoCostSplitBillEntry==null) return;
    	SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("amount");
    	selector.add("apportionValue");
    	selector.add("apportionValueTotal");
    	selector.add("otherRatioTotal");
    	for(Iterator iter=allEntrys.iterator();iter.hasNext();){
			iFDCNoCostSplitBillEntry.updatePartial((FDCNoCostSplitBillEntryInfo)iter.next(), selector);
    	}
    }
    


	/**
	 * 描述：设置分摊指标值
	 * @return
	 * @author: 
	 * 创建时间：2006-12-29 <p>
	 */
	private void setApportionValue(IObjectCollection entrys, int index,CurProjectCollection curProjectCollection) throws BOSException{
		
		FDCNoCostSplitBillEntryInfo topEntry=(FDCNoCostSplitBillEntryInfo)entrys.getObject(index);		
		if(topEntry==null||topEntry.isIsLeaf()){
			return;
		}
		int topIndex=index;
		int topLevel=topEntry.getLevel();		
		//分摊类型，根据父级的分摊类型设置直接下级的分摊指标数据
		ApportionTypeInfo apptType=topEntry.getApportionType();

		BigDecimal apptValue=null;		
		FDCNoCostSplitBillEntryInfo entry=null;

		if(topEntry.getCurProject().isIsLeaf()){		
			//明细工程的科目
    		if(isProdSplitParent(topEntry)){
				//产品拆分
    			ProductTypeInfo productType=null;  			
    			for(int i=topIndex+1; i<entrys.size(); i++){				
    				entry=(FDCNoCostSplitBillEntryInfo)entrys.getObject(i);
    				
    				if(entry.getLevel()==topLevel+1	&& isProdSplitLeaf(entry)){
    					productType=entry.getProduct();
						apptValue= ProjectHelper.getIndexValueByProjProdIdx(ctx, 
								entry.getCurProject().getId().toString(),
								productType.getId().toString(), 
								apptType.getId().toString(), ProjectStageEnum.DYNCOST);    						
    					
    					if(apptValue==null ){
    						apptValue=FDCHelper.ZERO;
    					}
						entry.setApportionValue(apptValue);
    					
    				}else{
    					break;
    				}
    			}
    			
    		}else{						
				//附加科目
    			for(int i=topIndex+1; i<entrys.size(); i++){				
    				entry=(FDCNoCostSplitBillEntryInfo)entrys.getObject(i);
    				if(entry.getLevel()>topLevel
    						&& entry.getCurProject().getId().equals(topEntry.getCurProject().getId())){
    					//递归调用（主要用于处理其中包含的产品拆分）
    					setApportionValue(entrys, i, curProjectCollection);
    				}
    			}	
    			
    			//附加科目不用处理分摊指标值    	
    			//附加科目指标值为分摊时指定的值，不能刷新处理
    		}	
			
			
		}else{		
			//非明细工程的各科目的成本从下级工程相同科目累加
			
			for(int i=topIndex+1; i<entrys.size(); i++){				
				entry=(FDCNoCostSplitBillEntryInfo)entrys.getObject(i);
				
				if(entry.getLevel()==topLevel+1){
						//递归调用
						setApportionValue(entrys, i, curProjectCollection);
						
						//工程项目分摊指标
						apptValue=null;

						//参与分摊，否则只有直接费用而不用设置分摊指标值
						if(entry.isIsApportion() && apptType!=null){
							for (Iterator iter2 = curProjectCollection.iterator(); iter2.hasNext();){
								CurProjectInfo curProj=(CurProjectInfo)iter2.next();
								
								if(curProj.getId()!=null 
										&& entry!=null && entry.getCurProject()!=null
										&& entry.getCurProject().getId()!=null
										&& curProj.getId().equals(entry.getCurProject().getId())){
									//工程项目分摊值集
									CurProjCostEntriesCollection projCosts=curProj.getCurProjCostEntries();
									
									for (Iterator iter3 = projCosts.iterator(); iter3.hasNext();){
										CurProjCostEntriesInfo projCost=(CurProjCostEntriesInfo)iter3.next();										
										if(projCost.getApportionType()!=null 
												&& projCost.getApportionType().getId().equals(apptType.getId())){
											apptValue=projCost.getValue();
											//entry.setApportionValue(apptValue);
//											logger.info("**********\nType:"+projCost.getApportionType()+"apportionValue:"+apptValue);
											break;
										}
									}
									
									break;						
								}					
							}							
						}
						
						if(apptValue==null){
							apptValue=FDCHelper.ZERO;
						}
						entry.setApportionValue(apptValue);				
					
				}else if(entry.getLevel()<=topLevel){
					break;
				}
			}

		}
	}
	
	/**
	 * 当前分录下的工程
	 * @author sxhong  		Date 2006-12-12
	 * @param topEntry
	 * @return
	 * @throws BOSException
	 */
	private CurProjectCollection getEntryCurProject(FDCNoCostSplitBillEntryInfo topEntry) throws BOSException{
//		if(topEntry.getLevel()!=0){
//			return;
//		}
		//工程项目
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("longNumber", 
    			topEntry.getCurProject().getLongNumber().replace('.','!')+"!%", CompareType.LIKE));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	view.setFilter(filter);
    	view.getSelector().add("id");
    	view.getSelector().add("name");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("parent");
    	view.getSelector().add("curProjCostEntries.apportionType.id");
    	view.getSelector().add("curProjCostEntries.value");
    	 	
    	
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	view.getSorter().add(new SorterItemInfo("sortNo"));
    	
		ICurProject iCurProj = null;
		if(ctx!=null){
			iCurProj=CurProjectFactory.getLocalInstance(ctx);
		}else{
			iCurProj=CurProjectFactory.getRemoteInstance();
			
		}
		CurProjectCollection curProjs = iCurProj.getCurProjectCollection(view);
		//apptObjIds=new String[apptObjs.size()];
		boolean debug=false;
		if(debug){
			for(Iterator iter=curProjs.iterator();iter.hasNext();){
				CurProjectInfo proj=(CurProjectInfo)iter.next();
				for(Iterator iter2=proj.getCurProjCostEntries().iterator();iter2.hasNext();){
					CurProjCostEntriesInfo entry=(CurProjCostEntriesInfo)iter2.next();
				}
			}
		}
		return curProjs;
	}
	
	/**
	 * 描述：修正金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void adjustAmount(IObjectCollection allEntrys){
    	FDCNoCostSplitBillEntryInfo entry=null;

		for(int i=0; i<allEntrys.size(); i++){		
			entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
			if(entry.getLevel()==0){				
				
				adjustAmountProject(allEntrys, i);
			}
		}    	
    }
    

	
	/**
	 * 描述：修正金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void adjustAmount(IObjectCollection allEntrys,FDCNoCostSplitBillEntryInfo curEntry){
    	FDCNoCostSplitBillEntryInfo entry=curEntry;    	
		int index=allEntrys.indexOf(curEntry);
		if(index==-1){
			return;
		}
		adjustAmountProject(allEntrys,index);
    }
    
    
    
   

	
	/**
	 * 描述：修正金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void adjustAmountProject(IObjectCollection allEntrys, int index){
		FDCNoCostSplitBillEntryInfo curEntry =(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(index);		
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//父级
		BigDecimal curAmount=curEntry.getAmount();
		
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		//amountTotal.setScale(10);
		//update by renliang
		amountTotal = amountTotal.setScale(10);
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//直接下级工程
		IRow row;
		FDCNoCostSplitBillEntryInfo entry=null;

		int idx=0;
		BigDecimal amountMax=FDCHelper.ZERO;
		boolean isDirectAmt=false;
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
				amount=entry.getAmount();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}
				
				amountTotal=amountTotal.add(amount);

				if(entry.getDirectAmount()!=null&&
						entry.getDirectAmount().compareTo(FDCHelper.ZERO)>0){
					if(entry.getApportionValue()!=null&&
							entry.getApportionValue().compareTo(FDCHelper.ZERO)>0){
						isDirectAmt=false;
					}else{
						isDirectAmt=true;
						
					}
				}
				//修正项为金额最大的项
				//if(amount.compareTo(FDCHelper.ZERO)!=0){
				if(amount.compareTo(amountMax)>=0&&!isDirectAmt){
					amountMax=amount;
					idx=i;						
				}

			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		

		if(idx>0 && curAmount.compareTo(amountTotal)!=0){	
			entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(idx);

			amount=entry.getAmount();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setAmount(amount);

		}


		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
				//递归调用
				if(!entry.isIsLeaf()){
					adjustAmountProject(allEntrys,i);
				}
/*				if(!entry.isIsLeaf()){
					adjustAmountAccount(allEntrys,i);
				}
				
				if(!entry.getCurProject().isIsLeaf()){
					adjustAmountProject(allEntrys,i);
					
				}*/
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
    }
    
	
	
	/**
	 * 描述：修正金额--附加科目,在财务科目拆分中不会被执行
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void adjustAmountAccount(IObjectCollection allEntrys, int index){
		FDCNoCostSplitBillEntryInfo curEntry =(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(index);	
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//父级
		BigDecimal curAmount=curEntry.getAmount();
		
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		//amountTotal.setScale(10); 
		//update by renliang			
		amountTotal  = amountTotal.setScale(10);
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//直接下级工程
		IRow row;
		FDCNoCostSplitBillEntryInfo entry=null;

		int idx=0;
		
		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);	
						
			if(entry.getLevel()==(level+1)){
/*				if(entry.getDirectAmount().compareTo(FDCHelper.ZERO)>0){
					continue;
				}*/
				if(entry.getCurProject().getId().equals(curEntry.getCurProject().getId())){				
					amount=entry.getAmount();
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
			entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(idx);	

			amount=entry.getAmount();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setAmount(amount);
		}
		

		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(idx);	
			
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCurProject().getId().equals(curEntry.getCurProject().getId())){				
					if(!entry.isIsLeaf()){
						adjustAmountAccount(allEntrys,i);
					}
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
    }
    

	
	/**
	 * 描述：设置拆分分录的Seq，以保证显示顺序
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-28 <p>
	 */
	public void setEntrySeq(FDCNoCostSplitBillEntryInfo entry){
		//index作为seq，actionSave_actionPerformed(ActionEvent e)设置index
		entry.setSeq(entry.getIndex());
		
		//index作为拆分组号，costSplit()设置index，并需要禁用actionSave_actionPerformed(ActionEvent e)中的设置
		//entry.setSeq(entry.getIndex()*100000 + entry.getSeq()%100000);	
	}

	/**
	 * 描述：汇总分摊金额（汇总生成非明细工程项目中附加科目的成本，其值为下级工程项目相同科目的成本之和）
     * @author sxhong  		Date 2007-1-29
     * @param allEntrys
     * @param curIndex
     */
    public void totAmountAddlAcct(IObjectCollection allEntrys,int curIndex){
    	//会计科目,不做处理
    }
    
    public SelectorItemCollection setSelectorsEntry(SelectorItemCollection sic, boolean isEntry) {
		String prefix="";
		if(!isEntry){
			prefix="entrys.";
		}
		

        sic.add(new SelectorItemInfo(prefix + "*"));
        sic.add(new SelectorItemInfo(prefix + "apportionType.id"));
        sic.add(new SelectorItemInfo(prefix + "apportionType.name"));
        sic.add(new SelectorItemInfo(prefix + "product.*"));

        sic.add(new SelectorItemInfo(prefix + "accountView"));
        sic.add(new SelectorItemInfo(prefix + "accountView.id"));
        sic.add(new SelectorItemInfo(prefix + "accountView.name"));
        sic.add(new SelectorItemInfo(prefix + "accountView.longNumber"));
        sic.add(new SelectorItemInfo(prefix + "accountView.number"));
        sic.add(new SelectorItemInfo(prefix + "accountView.displayName"));
        sic.add(new SelectorItemInfo(prefix + "accountView.isLeaf"));
        sic.add(new SelectorItemInfo(prefix + "accountView.level"));
        
        sic.add(new SelectorItemInfo(prefix + "curProject"));
        sic.add(new SelectorItemInfo(prefix + "curProject.id"));
        sic.add(new SelectorItemInfo(prefix + "curProject.name"));
        sic.add(new SelectorItemInfo(prefix + "curProject.displayName"));
        sic.add(new SelectorItemInfo(prefix + "curProject.longNumber"));
        sic.add(new SelectorItemInfo(prefix + "curProject.isLeaf"));
        sic.add(new SelectorItemInfo(prefix + "curProject.level"));
        sic.add(new SelectorItemInfo(prefix + "curProject.parent"));
        sic.add(new SelectorItemInfo(prefix + "curProject.parent.id"));
        sic.add(new SelectorItemInfo(prefix + "curProject.sortNo"));
        
        return sic;
	}
	
}
