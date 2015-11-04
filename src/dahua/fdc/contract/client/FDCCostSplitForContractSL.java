/**
 * 
 */
package com.kingdee.eas.fdc.contract.client;



import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.contract.ContractBillSplitEntryInfo;
import com.kingdee.util.StringUtils;

/**
 * 此类包含房地产单据拆分的拆分逻辑及相关方法
 * Date 2006-12-7
 */
public class FDCCostSplitForContractSL
{
    private static final Logger logger = CoreUIObject.getLogger(FDCCostSplitForContractSL.class);
    private Context ctx=null;
    
    public FDCCostSplitForContractSL(Context ctx){
    	this.ctx=ctx;
    }

	/**
	 * 描述：成本分摊计算
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */    
    public void apptAmount(IObjectCollection allEntrys,ContractBillSplitEntryInfo curEntry){
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
					ContractBillSplitEntryInfo entry = (ContractBillSplitEntryInfo)allEntrys.getObject(i);
					if(entry.getLevel()>level){
						entry.setAmount(FDCHelper.ZERO);
					}else{
						break;    						
					}
				}
				
				return;
			}
    		
		}
    	
    	//出现了costAccount的longNumber为空的情况，故先对costAccount进行一下处理
    	ICostAccount icostAccount =null;
    	try{
	    	if(ctx!=null){
	    		icostAccount = CostAccountFactory.getLocalInstance(ctx);
	    	}else{
	    		icostAccount = CostAccountFactory.getRemoteInstance();
	    	}

	    	SelectorItemCollection sic=new SelectorItemCollection();
	        sic.add(new SelectorItemInfo("*"));
	        sic.add(new SelectorItemInfo("curProject.*"));
	        if(icostAccount==null) return;
			for(int i=curIndex+1; i<allEntrys.size(); i++){
				ContractBillSplitEntryInfo entry = (ContractBillSplitEntryInfo)allEntrys.getObject(i);
	    		if(entry.getCostAccount()!=null&&StringUtils.isEmpty(entry.getCostAccount().getLongNumber())){
	    			CostAccountInfo costAccount=icostAccount.getCostAccountInfo(new ObjectUuidPK(entry.getCostAccount().getId()), sic);
	    			entry.setCostAccount(costAccount);
	    		}
			}
    	}catch (Exception e){
    		e.printStackTrace();
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
		    	
		


		//修正金额，保证上下级的汇总关系
		adjustAmount(allEntrys,curEntry);
		/*金额修正对存在产品拆分的数据会有影响,因修正前的拆分数据与修正后不一致,
		 *修正的结果与按比例拆分的结果会存在精度上的差别,一般为0.01
		 *在修正后进行一次产品拆分
		*/
		
		
		//汇总生成非明细工程项目中附加科目的成本		jelon 12/29/2006
		totAmountAddlAcct(allEntrys,curIndex);
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
		amountTotal=amountTotal.setScale(10);	
		
		
		
		//分摊值汇总、直接费用汇总
		BigDecimal apportionValue=FDCHelper.ZERO;//null;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		
		BigDecimal directAmount=FDCHelper.ZERO;//null;	
		BigDecimal directTotal=FDCHelper.ZERO;
		
		//金额	
		BigDecimal ratio=FDCHelper.ZERO;
		
		ContractBillSplitEntryInfo curEntry=(ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);		
    	
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

    	/*
    	 * modified by zhaoqin for R131101-0367 on 2013/11/05
    	 * 更改计算方式:
    	 *  amount = amountTotal * (directAmount/directTotal)	指定公摊
    	 *  or
    	 *  amount = amountTotal * (apportionValue/apportionTotal)	非指定公摊
    	 */
    	// amount=amountTotal.subtract(directTotal);

    	if(apportionTotal.compareTo(FDCHelper.ZERO)==0){
    		if(directTotal.compareTo(FDCHelper.ZERO) == 0)
    			ratio = FDCHelper.ZERO;
    		else
    			ratio=amountTotal.divide(directTotal,10,BigDecimal.ROUND_HALF_EVEN);
    	}else{
    		//比例值，非直接显示值，需要保证小数位数
    		ratio=amountTotal.divide(apportionTotal,10,BigDecimal.ROUND_HALF_EVEN);
    	}
    	
    	//calcApportionAmountBotUp(curIndex,ratio,FDCHelper.ZERO,FDCHelper.ZERO);

		for(int i=curIndex+1; i<allEntrys.size(); i++){
			ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==topLevel+1){
				apptAmountBotUp(allEntrys,i,ratio,FDCHelper.ZERO,FDCHelper.ZERO);

			}else if(entry.getLevel()<=topLevel){
				break;
			}
		}
    }
	/**
	 * 修正金额前进行四舍五入,修正后对二次拆分(产品拆分,附加科目拆分进行再次拆分) by sxhong 2007年6月21日 
	 * 描述：修正金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void adjustAmount(IObjectCollection allEntrys,ContractBillSplitEntryInfo curEntry){
    	ContractBillSplitEntryInfo entry=curEntry;    	
		int index=allEntrys.indexOf(curEntry);
		if(index==-1){
			return;
		}
		//由于向上累加时存在误差，所以四舍五入在分摊完成后再进行处理	jelon 12/26/2006
		//修正金额前进行四舍五入 by sxhong 2007年6月21日
		int level=curEntry.getLevel();
		BigDecimal amount=null;
		for(int i=index+1; i<allEntrys.size(); i++){				
			ContractBillSplitEntryInfo tempEntry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
			
			if(tempEntry.getLevel()>level){
				amount=tempEntry.getAmount();
				
				boolean isDirectAmt=false;
				if(tempEntry.getDirectAmount()!=null
				   &&tempEntry.getDirectAmount().compareTo(FDCHelper.ZERO)>0
						&&(tempEntry.getApportionValue()==null
						   ||tempEntry.getApportionValue().compareTo(FDCHelper.ZERO)==0)){
					//直接金额大于0且没有分摊值
					isDirectAmt=true;
				}
				if(amount!=null&&!isDirectAmt){//直接金额不做处理
					amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
					tempEntry.setAmount(amount);
				}
							
			}else{
				break;
			}
		}
    	
		CostSplitTypeEnum splitType = curEntry.getSplitType();
		if(splitType!=null&&splitType==CostSplitTypeEnum.BOTUPSPLIT){
			adjustBotupAmt(allEntrys, curEntry, index, level);
		}else{
	    	//其它的拆分修正
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
	    		adjustAmountProject(allEntrys, index);
	    	}else{
	    		adjustAmountAccount(allEntrys, index);    		
	    	}
		}
    	//对修正后的数据进行产品拆分
    	for(int i=index+1;i<allEntrys.size();i++){
    		ContractBillSplitEntryInfo topEntry=(ContractBillSplitEntryInfo) allEntrys.getObject(i);
    		if(topEntry.getLevel()<=level){
    			break;
    		}
    		if(topEntry.getCostAccount().getCurProject().isIsLeaf()){		  	    	
    			//其他下级科目需按附加科目分摊或按产品分摊
        		if(isProdSplitParent(topEntry)){
        			//产品拆分
    	    		apptAmountProduct(allEntrys, i);
    	    		adjustAmount(allEntrys,topEntry);
        		}else if(!topEntry.getCostAccount().isIsLeaf()){
        			//附加科目
        			//TODO 暂时不处理精度,产品拆分及附加科目的拆分应该在拆分内部进行精度调整
//        			apptAmountAddlAcct(allEntrys, i);	    			
        		}
    			
    		}
    	}
    	
    }
    
	/**
	 * 描述：修正金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void adjustAmountProject(IObjectCollection allEntrys, int index){
		ContractBillSplitEntryInfo curEntry =(ContractBillSplitEntryInfo)allEntrys.getObject(index);		
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//父级
		BigDecimal curAmount=curEntry.getAmount();
		
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);			
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//直接下级工程
		IRow row;
		ContractBillSplitEntryInfo entry=null;

		int idx=0;
		BigDecimal amountMax=FDCHelper.ZERO;
		
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
					amount=entry.getAmount();
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
			entry=(ContractBillSplitEntryInfo)allEntrys.getObject(idx);

			amount=entry.getAmount();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setAmount(amount);

		}


		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
					
					if(!entry.isIsLeaf()){
						adjustAmountAccount(allEntrys,i);
					}
					
					if(!entry.getCostAccount().getCurProject().isIsLeaf()){
						adjustAmountProject(allEntrys,i);
						
					}
					
				}else{
					
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
    }
    
	/**
	 * 描述：修正金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void adjustAmountAccount(IObjectCollection allEntrys, int index){
		ContractBillSplitEntryInfo curEntry =(ContractBillSplitEntryInfo)allEntrys.getObject(index);	
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//父级
		BigDecimal curAmount=curEntry.getAmount();
		
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		//BigDeciaml 使用的时候，一定要重新赋值
		amountTotal = amountTotal.setScale(10);			
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//直接下级工程
		IRow row=null;
		ContractBillSplitEntryInfo entry=null;

		int idx=0;
		
		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);	
						
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
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
			entry=(ContractBillSplitEntryInfo)allEntrys.getObject(idx);	

			amount=entry.getAmount();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setAmount(amount);
		}
		

		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(ContractBillSplitEntryInfo)allEntrys.getObject(idx);	
			
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
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
	 *末级拆分修正 by sxhong //2008-02-49 09:09:36 
	 * @param allEntrys
	 * @param curEntry
	 * @param index
	 * @param level
	 */
	private void adjustBotupAmt(IObjectCollection allEntrys,
			ContractBillSplitEntryInfo curEntry, int index, int level) {
		//末级拆分修正
		BigDecimal total=FDCHelper.ZERO;
		BigDecimal maxAmount=FDCHelper.ZERO;
		int max=index;
		for(int i=index+1; i<allEntrys.size(); i++){		
			ContractBillSplitEntryInfo myEntry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
			if(myEntry.getLevel()<=level){
				break;
			}
			if(myEntry.getCostAccount().getCurProject().isIsLeaf() 
					&&!myEntry.isIsAddlAccount() 
					&& !isProdSplitLeaf(myEntry)){						
				//明细科目修正
				BigDecimal amt = FDCHelper.toBigDecimal(myEntry.getAmount());
				total=total.add(amt);
				if(amt.compareTo(maxAmount)>0){
					maxAmount=amt;
					max=i;
				}
			}
		}
		if(max>index){
			ContractBillSplitEntryInfo myEntry=(ContractBillSplitEntryInfo)allEntrys.getObject(max);
			BigDecimal amt=FDCHelper.toBigDecimal(myEntry.getAmount());
			amt=FDCHelper.toBigDecimal(curEntry.getAmount()).subtract(total).add(amt);
			myEntry.setAmount(amt);
			//汇总到非明细
			for(int i=index+1; i<allEntrys.size(); i++){		
				ContractBillSplitEntryInfo myEntry1=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
				if(myEntry1.getLevel()<=level){
					break;
				}
				if(!myEntry1.getCostAccount().getCurProject().isIsLeaf()){
					BigDecimal sum=FDCHelper.ZERO;
					for(int j=i+1;j<allEntrys.size();j++){
						ContractBillSplitEntryInfo myEntry2=(ContractBillSplitEntryInfo)allEntrys.getObject(j);
						if(myEntry2.getLevel()<=myEntry1.getLevel()){
							break;
						}
						if(myEntry2.getLevel()==myEntry1.getLevel()+1){
							sum=sum.add(FDCHelper.toBigDecimal(myEntry2.getAmount()));
						}
					}
					myEntry1.setAmount(sum);
				}
			}
		}
	}
    
    /**
	 * 描述：成本分摊计算（末级拆分，递归拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void apptAmountBotUp(IObjectCollection allEntrys,int curIndex, BigDecimal ratio, BigDecimal parApportionTotal, 
    		BigDecimal parDirectAmount){
    	if(curIndex>=allEntrys.size()){
    		return;
    	}
    	if(parApportionTotal==null){
			parApportionTotal=FDCHelper.ZERO;
		}
		if(parDirectAmount==null){
			parDirectAmount=FDCHelper.ZERO;
		}
		
		ContractBillSplitEntryInfo topEntry =(ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);
		
    	int topLevel=topEntry.getLevel();
    	int projLevel=topEntry.getCostAccount().getCurProject().getLevel();
		
		//BigDecimal apportionValue=FDCHelper.ZERO;
		//BigDecimal apportionTotal=FDCHelper.ZERO;
		//BigDecimal directAmount=FDCHelper.ZERO;
  
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

		// modified by zhaoqin for R131101-0367 on 2013/11/05
		//BigDecimal directTotal=FDCHelper.ZERO;
		    	
		BigDecimal amount=FDCHelper.ZERO;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		
		//分摊直接费用（目前只有明细工程明细科目才有直接费用，直接费用实际不用分摊）
		if(parApportionTotal.compareTo(FDCHelper.ZERO)!=0){
			/*directAmount=directAmount.add(
					parDirectAmount.multiply(apportionValue).divide(parApportionTotal,2,BigDecimal.ROUND_HALF_EVEN));*/	
			// modified by zhaoqin for R131101-0367 on 2013/11/05 start
			//directAmount=directAmount.add(
					//parDirectAmount.multiply(apportionValue).divide(parApportionTotal,10,BigDecimal.ROUND_HALF_EVEN));
			directAmount=directAmount.add(parDirectAmount);
			// modified by zhaoqin for R131101-0367 on 2013/11/05 end
		}				
		
		
		if(topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//明细工程的科目直接分摊
			/*amount=apportionValue.multiply(ratio).add(directAmount);
			amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
			amountTotal=amount;*/
			
			//第一级科目进行末级分摊
	    	if(!topEntry.isIsAddlAccount() && !isProdSplitLeaf(topEntry)){
	    		// modified by zhaoqin for R131101-0367 on 2013/11/05 start
	    		// amount=apportionValue.multiply(ratio).add(directAmount);
	    		if(apportionValue.compareTo(FDCHelper.ZERO)==0){
	    			amount=directAmount.multiply(ratio);
	    		} else {
	    			amount=apportionValue.multiply(ratio);
	    		}
	    		// modified by zhaoqin for R131101-0367 on 2013/11/05 end
	    		
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
    		}else if(!topEntry.getCostAccount().isIsLeaf()){
    			//附加科目
    			apptAmountAddlAcct(allEntrys, curIndex);	    			
    		}
		}else{		
			//非明细工程的各科目的成本从下级工程相同科目累加
			String topAcctNo=topEntry.getCostAccount().getLongNumber().replace('.','!');
			for(int i=curIndex+1; i<allEntrys.size(); i++){				
				ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
				
				if(entry.getLevel()>topLevel){
					/*if(entry.getCostAccount().getCurProject().getLevel()==(projLevel+1)
							&& entry.getCostAccount().getLongNumber().replace('.','!').equals(topAcctNo)
							&& !isProdSplitLeaf(entry)){*/
					if(isChildProjSameAcct(entry,topEntry)){
						//if(!entry.isIsAddlAccount()){

						apptAmountBotUp(allEntrys,i,ratio,apportionTotal,directAmount);
							
						amountTotal=amountTotal.add(entry.getAmount());			
						//}					
					}
				}else if(entry.getLevel()<=topLevel){
					break;
				}
			}
			topEntry.setAmount(amountTotal);
		}
	}
 
    
    /**
	 * 描述：汇总分摊金额（汇总生成非明细工程项目中附加科目的成本，其值为下级工程项目相同科目的成本之和）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-29 <p>
	 */
    public void totAmountAddlAcct(IObjectCollection allEntrys,int curIndex){
    	if(curIndex>=allEntrys.size()){
    		return;
    	}
		
    	ContractBillSplitEntryInfo topEntry =(ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);
    	if(topEntry==null||topEntry.getCostAccount()==null||topEntry.getCostAccount().getCurProject()==null||topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//明细工程的科目		
			return;			
		}
		

		//当前工程项目的下级成本科目
    	int acctLevel=topEntry.getCostAccount().getLevel();
    	
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);

			if(entry.getCostAccount().getCurProject().getId().equals(topEntry.getCostAccount().getCurProject().getId())){
				if(entry.getCostAccount().getLevel()>acctLevel){
					if(entry.getCostAccount().getLevel()==acctLevel+1){
						totAmountAddlAcct(allEntrys,i);										
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
    	int projLevel=topEntry.getCostAccount().getCurProject().getLevel();
    	
		BigDecimal amount=null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()>topLevel){
				if(entry.getLevel()==topLevel+1){
					if(isChildProjSameAcct(entry,topEntry)){
						totAmountAddlAcct(allEntrys,i);				
						
						amount=entry.getAmount();
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
		if(topLevel!=0&&topEntry.isIsAddlAccount()){
			topEntry.setAmount(amountTotal);
		}
    		
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
    	ContractBillSplitEntryInfo curEntry = (ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);		
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
		int projLevel=curEntry.getCostAccount().getCurProject().getLevel();
		
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
		
		//分摊到子级
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);;
			
			
			if(entry.getLevel()<=level){
				break;
			}
			
			
			//直接下级
			if(entry.getLevel()==(level+1) 
					&& entry.getCostAccount().getCurProject().getLevel() == (projLevel + 1)) {
				
				//分摊
				apportionValue=entry.getApportionValue();
				if(apportionValue==null){
					apportionValue=FDCHelper.ZERO;
				}
				directAmount=entry.getDirectAmount();
				if(directAmount==null){
					directAmount=FDCHelper.ZERO;
				}
				
				// modified by zhaoqin for R131101-0367 on 2013/11/06
				//if(apportionValue.compareTo(FDCHelper.ZERO)==0 && apportionTotal.compareTo(FDCHelper.ZERO)==0){
				if(apportionTotal.compareTo(FDCHelper.ZERO) == 0){
					if(directTotal.compareTo(FDCHelper.ZERO) == 0)
						amount = FDCHelper.ZERO;
					else
						amount=amountTotal.multiply(directAmount.divide(directTotal, 10, BigDecimal.ROUND_HALF_UP));
				}else{
					//amount=(apportionValue/approtionTotal) * (amountTotal-directTotal) + directAmount
					
					
					/*
			    	 * modified by zhaoqin for R131101-0367 on 2013/11/05
			    	 * 更改计算方式:
			    	 *  amount = amountTotal * (directAmount/directTotal)	指定公摊
			    	 *  or
			    	 *  amount = amountTotal * (apportionValue/apportionTotal)	非指定公摊
			    	 */
					amount = (amountTotal.multiply(apportionValue)).divide(apportionTotal, 10, BigDecimal.ROUND_HALF_UP);
					/*
					amount=apportionValue.multiply(amountTotal.subtract(directTotal));
					//amount=amount.divide(apportionTotal,2,BigDecimal.ROUND_HALF_EVEN);
					amount=amount.divide(apportionTotal,10,BigDecimal.ROUND_HALF_EVEN);
					amount=amount.add(directAmount);
					*/
				}
				
				entry.setAmount(amount);					

				//处理包含的附加科目和产品拆分	jelon 12/22/2006	
	    		if(isProdSplitParent(entry)){
	    			//产品拆分
		    		apptAmountProduct(allEntrys,i);
	    			
	    		}else if(!entry.getCostAccount().isIsLeaf()){
	    			//附加科目
	    			apptAmountAddlAcct(allEntrys,i);	    			
	    		}
		    	
		    	//下级工程项目的拆分
		    	if(!entry.getCostAccount().getCurProject().isIsLeaf()){
		    		apptAmountTopDown(allEntrys,i);
		    	}
		    	
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		
		
		if(!curEntry.isIsAddlAccount()){
			//calcApportionAmountAddlAcct(rowIndex);
		}
    }
    
	/**
	 * 描述：成本分摊计算（附加科目）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void apptAmountAddlAcct(IObjectCollection allEntrys,int curIndex){		
    	ContractBillSplitEntryInfo curEntry =(ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);		
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//产品拆分
		if(curEntry.getSplitType()!=null && curEntry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
			apptAmountProduct(allEntrys,curIndex);
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
		int acctLevel=curEntry.getCostAccount().getLevel();
		BOSUuid projId=curEntry.getCostAccount().getCurProject().getId();
		
		amountTotal=curEntry.getAmount();
		if(amountTotal==null){
			amountTotal=FDCHelper.ZERO;
		}					
		
		//汇总	
		apportionTotal=curEntry.getOtherRatioTotal();
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=curEntry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
		
		/*
		//明细工程项目分摊金额，非明细工程项目，累加下级工程项目的相同科目金额
		if(entry.getCostAccount().getCurProject().isIsLeaf()){
			
		}
		*/
		
		
		//（没有设置分摊比例）检测是否为唯一的子科目
		int count=0;		
		if(apportionTotal.compareTo(FDCHelper.ZERO)==0){
			for(int i=curIndex+1; i<allEntrys.size(); i++){				
				ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
				//直接下级
				if(entry.getCostAccount().getCurProject().getId().equals(projId)){
					if(entry.getCostAccount().getLevel()==(acctLevel+1)){						
						count++;
						//idx=i;
					}
				}else if(entry.getLevel()<=level){
					break;
				}
			}    				
		}
		
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
			
			//直接下级
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(projId)){
					if(entry.getCostAccount().getLevel()==(acctLevel+1)){
						if(count==1){
							amount=amountTotal;
							
						}else{
							apportionValue=entry.getApportionValue();
							if(apportionValue==null){
								apportionValue=FDCHelper.ZERO;
							}
							directAmount=entry.getDirectAmount();
							if(directAmount==null){
								directAmount=FDCHelper.ZERO;
							}
							
							// modified by zhaoqin for R131101-0367 on 2013/11/05						
							// if(apportionValue.compareTo(FDCHelper.ZERO)==0|| apportionTotal.compareTo(FDCHelper.ZERO)==0){
							if(apportionTotal.compareTo(FDCHelper.ZERO) == 0){
								if(directTotal.compareTo(FDCHelper.ZERO) == 0)
									amount = FDCHelper.ZERO;
								else
									amount=amountTotal.multiply(directAmount).divide(directTotal, 10, BigDecimal.ROUND_HALF_UP);
							}else{	
//								存在分摊
								//TODO 可能要处理精度
								
								/*
						    	 * modified by zhaoqin for R131101-0367 on 2013/11/05
						    	 * 更改计算方式:
						    	 *  amount = amountTotal * (directAmount/directTotal)	指定公摊
						    	 *  or
						    	 *  amount = amountTotal * (apportionValue/apportionTotal)	非指定公摊
						    	 */
								amount = (amountTotal.multiply(apportionValue)).divide(apportionTotal, 10, BigDecimal.ROUND_HALF_UP);
								/*
								amount=apportionValue.multiply(amountTotal.subtract(directTotal));
								//amount=amount.divide(apportionTotal,2,BigDecimal.ROUND_HALF_EVEN);
								amount=amount.divide(apportionTotal,10,BigDecimal.ROUND_HALF_EVEN);
								amount=amount.add(directAmount);
								*/
							}						
						}					
						entry.setAmount(amount);					
						apptAmountAddlAcct(allEntrys,i);		
						
					}
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
    	ContractBillSplitEntryInfo curEntry = (ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);		
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
		int projLevel=curEntry.getCostAccount().getCurProject().getLevel();
		
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
//		amountTotal=amountTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
		//子级
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			ContractBillSplitEntryInfo entry =(ContractBillSplitEntryInfo)allEntrys.getObject(i);
			
			//直接下级
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getId().equals(curEntry.getCostAccount().getId())){
					apportionValue=entry.getApportionValue();
					if(apportionValue==null){
						apportionValue=FDCHelper.ZERO;
					}
					directAmount=entry.getDirectAmount();
					if(directAmount==null){
						directAmount=FDCHelper.ZERO;
					}
					
					//if(apportionValue.compareTo(FDCHelper.ZERO)==0|| apportionTotal.compareTo(FDCHelper.ZERO)==0){
					if(apportionTotal.compareTo(FDCHelper.ZERO)==0){
					//if(directAmount.compareTo(FDCHelper.ZERO)>0){
						//直接金额
						// modified by zhaoqin for R131101-0367 on 2013/11/4 start
						// 按建发要求:产品拆分的"直接费用"改成"指定分摊",计算方式改成按比例计算
						// amount=directAmount;
						if(directTotal.compareTo(FDCHelper.ZERO) == 0)
							amount = FDCHelper.ZERO;
						else
							amount = (directAmount.multiply(amountTotal)).divide(directTotal, 10, BigDecimal.ROUND_HALF_UP);
					} else {
						//存在分摊
						/*
						amount=apportionValue.multiply(amountTotal.subtract(directTotal));
						//amount=amount.divide(apportionTotal,2,BigDecimal.ROUND_HALF_EVEN);
						amount=amount.divide(apportionTotal,10,BigDecimal.ROUND_HALF_EVEN);
						amount=amount.add(directAmount);
						*/
						amount = (apportionValue.multiply(amountTotal)).divide(apportionTotal, 10, BigDecimal.ROUND_HALF_UP);
						// modified by zhaoqin for R131101-0367 on 2013/11/4 end
					}
					entry.setAmount(amount);
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    			

    }
    
	/**
	 * 描述：汇总分摊指标值
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-11-07 <p>
	 */
    public void totApptValue(IObjectCollection allEntrys,ContractBillSplitEntryInfo curEntry){
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
	 * 描述：汇总分摊指标值（产品拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void totApptValueProduct(IObjectCollection allEntrys,int curIndex){
    	ContractBillSplitEntryInfo curEntry = (ContractBillSplitEntryInfo)allEntrys.getObject(curIndex); 
    	if(curEntry.isIsLeaf()){
    		return;
    	}
    	
    	int level=curEntry.getLevel();
    	int projLevel=curEntry.getCostAccount().getCurProject().getLevel();
    	BOSUuid acctId=curEntry.getCostAccount().getId();

		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;

		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			ContractBillSplitEntryInfo entry = (ContractBillSplitEntryInfo)allEntrys.getObject(i);
			
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
	 * 描述：是否产品拆分明细
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-11-30 <p>
	 */
	public static boolean isProdSplitLeaf(ContractBillSplitEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    		isTrue=true;
    	}
    	
    	return isTrue;
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
    	
    	
    	ContractBillSplitEntryInfo topEntry = (ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);    
		
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
			ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
			if(entry.getLevel()>0){			
				
				if(entry.getCostAccount().getCurProject().isIsLeaf()
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
	 * 描述：是否产品拆分父级
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-11-30 <p>
	 */
    public boolean isProdSplitParent(ContractBillSplitEntryInfo entry){		
		boolean isTrue=false;
		
		if(!entry.isIsLeaf()
				&& entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
			isTrue=true;
		}
		
		return isTrue;
	}

	
	/**
	 * 描述：是否下级工程项目的相同科目（长编码相同）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-28 <p>
	 */
    public static boolean isChildProjSameAcct(ContractBillSplitEntryInfo entry, ContractBillSplitEntryInfo parent){		
		boolean isTrue=false;

		String topAcctNo=parent.getCostAccount().getLongNumber().replace('.','!');

		//if(entry.getCostAccount().getCurProject().getLevel()==(parent.getCostAccount().getCurProject().getLevel()+1)
		if (entry.getLevel() == parent.getLevel() + 1
				&& entry.getCostAccount().getCurProject().getLevel() == parent.getCostAccount().getCurProject().getLevel() + 1
				&& entry.getCostAccount().getCurProject().getParent() != null
				&& entry.getCostAccount().getCurProject().getParent().getId().equals(parent.getCostAccount().getCurProject().getId())
				&& entry.getCostAccount().getLongNumber().replace('.','!').equals(topAcctNo)
				&& !isProdSplitLeaf(entry)){
			isTrue=true;
		}
		
		return isTrue;
	}
    
	/**
	 * 描述：汇总分摊指标值（附加科目）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void totApptValueAddlAcct(IObjectCollection allEntrys,int curIndex){
    	ContractBillSplitEntryInfo curEntry = (ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);    
    	if(curEntry.isIsLeaf()){
    		return;
    	}    	
    	
    	/*if(curEntry.getSplitType()!=null && curEntry.getSplitType().equals(CostSplitType.PRODSPLIT)){
    		return;
    	}*/
    	
		//产品拆分
		if(curEntry.getSplitType()!=null && curEntry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
			totApptValueProduct(allEntrys,curIndex);			
    		return;
		}
    	
    	int level=curEntry.getLevel();
    	int atcctLevel=curEntry.getCostAccount().getLevel();
    	BOSUuid projId=curEntry.getCostAccount().getCurProject().getId();

		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;

		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
			
			if(entry.getCostAccount().getCurProject().getId().equals(projId)){
				if(entry.getCostAccount().getLevel()==atcctLevel+1 
						&& entry.getLevel()==level+1){
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
									
					totApptValueAddlAcct(allEntrys,i);
				}
			}
			else if(entry.getLevel()<=level){
				break;
			}			
		}	

		curEntry.setOtherRatioTotal(apportionTotal);
	}
    
	/**
	 * 描述：汇总分摊指标值（自动拆分，同时适用其中包含的产品拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void totApptValueTopDown(IObjectCollection allEntrys,int topIndex){
    	ContractBillSplitEntryInfo topEntry =(ContractBillSplitEntryInfo)allEntrys.getObject(topIndex);  
    	
    	if(topEntry.isIsLeaf()){
    		return;
    	}
    	
    	int topLevel=topEntry.getLevel();
    	//BOSUuid acctId=topEntry.getCostAccount().getId();

		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;

		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
		
		
		ContractBillSplitEntryInfo entry=null;		

		if(topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//明细工程的科目
			
    		if(isProdSplitParent(topEntry)){
    			totApptValueProduct(allEntrys,topIndex);				    			
    		}else{
    			totApptValueAddlAcct(allEntrys,topIndex);	    			
    		}	
			
			
		}else{		
			//非明细工程的各科目的成本从下级工程相同科目累加
			
			for(int i=topIndex+1; i<allEntrys.size(); i++){				
				entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
				
				if(entry.getLevel()>topLevel){
					if(isChildProjSameAcct(entry,topEntry)){	
						//递归调用
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
									
						if(CostSplitTypeEnum.PRODSPLIT==entry.getSplitType()){
							totApptValueProduct(allEntrys, i);
						}else{
							totApptValueTopDown(allEntrys,i);
						}
					}
				}else if(entry.getLevel()<=topLevel){
					break;
				}
			}
			
			topEntry.setApportionValueTotal(apportionTotal);
			topEntry.setDirectAmountTotal(directTotal);
		}
	}
	
}