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
 * ����������ز����ݲ�ֵĲ���߼�����ط���
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
	 * �������ɱ���̯����
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */    
    public void apptAmount(IObjectCollection allEntrys,ContractBillSplitEntryInfo curEntry){
		CostSplitTypeEnum costSplitType=curEntry.getSplitType();
		int curIndex=allEntrys.indexOf(curEntry);
		if(curIndex==-1){
			return;
		}
		
    	//����������Ϊ0�����в�֣�������ֱ�ӷ��ö����ֲ�ֽ��Ϊ���������	jelon 12/7/06
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
    	
    	//������costAccount��longNumberΪ�յ���������ȶ�costAccount����һ�´���
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
    	
    	
    	//�ɱ���̯
    	if(costSplitType==null || costSplitType.equals(CostSplitTypeEnum.MANUALSPLIT)){
    		//
    	}else if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){	
			apptAmountProduct(allEntrys,curIndex);
		}else if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){	
			apptAmountBotUp(allEntrys,curIndex);
		}else{	
			apptAmountTopDown(allEntrys,curIndex);
		};
		    	
		


		//��������֤���¼��Ļ��ܹ�ϵ
		adjustAmount(allEntrys,curEntry);
		/*��������Դ��ڲ�Ʒ��ֵ����ݻ���Ӱ��,������ǰ�Ĳ��������������һ��,
		 *�����Ľ���밴������ֵĽ������ھ����ϵĲ��,һ��Ϊ0.01
		 *�����������һ�β�Ʒ���
		*/
		
		
		//�������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ�		jelon 12/29/2006
		totAmountAddlAcct(allEntrys,curIndex);
    }
    
    
	/**
	 * �������ɱ���̯���㣨ĩ����֣�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */
    private void apptAmountBotUp(IObjectCollection allEntrys,int curIndex){
    	int level;
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal=amountTotal.setScale(10);	
		
		
		
		//��ֵ̯���ܡ�ֱ�ӷ��û���
		BigDecimal apportionValue=FDCHelper.ZERO;//null;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		
		BigDecimal directAmount=FDCHelper.ZERO;//null;	
		BigDecimal directTotal=FDCHelper.ZERO;
		
		//���	
		BigDecimal ratio=FDCHelper.ZERO;
		
		ContractBillSplitEntryInfo curEntry=(ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);		
    	
    	//�����
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
    	 * ���ļ��㷽ʽ:
    	 *  amount = amountTotal * (directAmount/directTotal)	ָ����̯
    	 *  or
    	 *  amount = amountTotal * (apportionValue/apportionTotal)	��ָ����̯
    	 */
    	// amount=amountTotal.subtract(directTotal);

    	if(apportionTotal.compareTo(FDCHelper.ZERO)==0){
    		if(directTotal.compareTo(FDCHelper.ZERO) == 0)
    			ratio = FDCHelper.ZERO;
    		else
    			ratio=amountTotal.divide(directTotal,10,BigDecimal.ROUND_HALF_EVEN);
    	}else{
    		//����ֵ����ֱ����ʾֵ����Ҫ��֤С��λ��
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
	 * �������ǰ������������,������Զ��β��(��Ʒ���,���ӿ�Ŀ��ֽ����ٴβ��) by sxhong 2007��6��21�� 
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private void adjustAmount(IObjectCollection allEntrys,ContractBillSplitEntryInfo curEntry){
    	ContractBillSplitEntryInfo entry=curEntry;    	
		int index=allEntrys.indexOf(curEntry);
		if(index==-1){
			return;
		}
		//���������ۼ�ʱ�������������������ڷ�̯��ɺ��ٽ��д���	jelon 12/26/2006
		//�������ǰ������������ by sxhong 2007��6��21��
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
					//ֱ�ӽ�����0��û�з�ֵ̯
					isDirectAmt=true;
				}
				if(amount!=null&&!isDirectAmt){//ֱ�ӽ�������
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
	    	//�����Ĳ������
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
    	//������������ݽ��в�Ʒ���
    	for(int i=index+1;i<allEntrys.size();i++){
    		ContractBillSplitEntryInfo topEntry=(ContractBillSplitEntryInfo) allEntrys.getObject(i);
    		if(topEntry.getLevel()<=level){
    			break;
    		}
    		if(topEntry.getCostAccount().getCurProject().isIsLeaf()){		  	    	
    			//�����¼���Ŀ�谴���ӿ�Ŀ��̯�򰴲�Ʒ��̯
        		if(isProdSplitParent(topEntry)){
        			//��Ʒ���
    	    		apptAmountProduct(allEntrys, i);
    	    		adjustAmount(allEntrys,topEntry);
        		}else if(!topEntry.getCostAccount().isIsLeaf()){
        			//���ӿ�Ŀ
        			//TODO ��ʱ��������,��Ʒ��ּ����ӿ�Ŀ�Ĳ��Ӧ���ڲ���ڲ����о��ȵ���
//        			apptAmountAddlAcct(allEntrys, i);	    			
        		}
    			
    		}
    	}
    	
    }
    
	/**
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private void adjustAmountProject(IObjectCollection allEntrys, int index){
		ContractBillSplitEntryInfo curEntry =(ContractBillSplitEntryInfo)allEntrys.getObject(index);		
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//����
		BigDecimal curAmount=curEntry.getAmount();
		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);			
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//ֱ���¼�����
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
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private void adjustAmountAccount(IObjectCollection allEntrys, int index){
		ContractBillSplitEntryInfo curEntry =(ContractBillSplitEntryInfo)allEntrys.getObject(index);	
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//����
		BigDecimal curAmount=curEntry.getAmount();
		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		//BigDeciaml ʹ�õ�ʱ��һ��Ҫ���¸�ֵ
		amountTotal = amountTotal.setScale(10);			
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//ֱ���¼�����
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
	 *ĩ��������� by sxhong //2008-02-49 09:09:36 
	 * @param allEntrys
	 * @param curEntry
	 * @param index
	 * @param level
	 */
	private void adjustBotupAmt(IObjectCollection allEntrys,
			ContractBillSplitEntryInfo curEntry, int index, int level) {
		//ĩ���������
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
				//��ϸ��Ŀ����
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
			//���ܵ�����ϸ
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
	 * �������ɱ���̯���㣨ĩ����֣��ݹ��֣�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
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
		
		
		//��ֱ̯�ӷ��ã�Ŀǰֻ����ϸ������ϸ��Ŀ����ֱ�ӷ��ã�ֱ�ӷ���ʵ�ʲ��÷�̯��
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
			//��ϸ���̵Ŀ�Ŀֱ�ӷ�̯
			/*amount=apportionValue.multiply(ratio).add(directAmount);
			amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
			amountTotal=amount;*/
			
			//��һ����Ŀ����ĩ����̯
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
			

			//��������ĸ��ӿ�Ŀ�Ͳ�Ʒ���	jelon 12/22/2006				
	    	/*if(topEntry.isIsAddlAccount()){
	    		calcApportionAmountAddlAcct(rowIndex);
	    	}else if(isProdSplitParent(topEntry)){
	    		calcApportionAmountProduct(rowIndex);
	    	}	*/		
	    	/*if(!topEntry.getCostAccount().isIsLeaf()){
	    		if(isProdSplitParent(topEntry)){
	    			//��Ʒ���
		    		calcApportionAmountProduct(rowIndex);
	    		}else{
	    			//���ӿ�Ŀ
	    			calcApportionAmountAddlAcct(rowIndex);
	    		}
	    	}*/
	    	
			//�����¼���Ŀ�谴���ӿ�Ŀ��̯�򰴲�Ʒ��̯
    		if(isProdSplitParent(topEntry)){
    			//��Ʒ���
	    		apptAmountProduct(allEntrys, curIndex);
    		}else if(!topEntry.getCostAccount().isIsLeaf()){
    			//���ӿ�Ŀ
    			apptAmountAddlAcct(allEntrys, curIndex);	    			
    		}
		}else{		
			//����ϸ���̵ĸ���Ŀ�ĳɱ����¼�������ͬ��Ŀ�ۼ�
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
	 * ���������ܷ�̯���������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ�����ֵΪ�¼�������Ŀ��ͬ��Ŀ�ĳɱ�֮�ͣ�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-29 <p>
	 */
    public void totAmountAddlAcct(IObjectCollection allEntrys,int curIndex){
    	if(curIndex>=allEntrys.size()){
    		return;
    	}
		
    	ContractBillSplitEntryInfo topEntry =(ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);
    	if(topEntry==null||topEntry.getCostAccount()==null||topEntry.getCostAccount().getCurProject()==null||topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//��ϸ���̵Ŀ�Ŀ		
			return;			
		}
		

		//��ǰ������Ŀ���¼��ɱ���Ŀ
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
		

		//�¼�������Ŀ����ͬ�ɱ���Ŀ
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
		
		//topEntry.isIsAddlAccount() �������� by sxhong ��δ���Ӧ�����������
		if(topLevel!=0&&topEntry.isIsAddlAccount()){
			topEntry.setAmount(amountTotal);
		}
    		
	}
    
    /**
	 * �������ɱ���̯���㣨�Զ���֣�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
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
		//���	
		BigDecimal amount=FDCHelper.ZERO;
		BigDecimal amountTotal=FDCHelper.ZERO;	
		
		//��ֵ̯���ܡ�ֱ�ӷ��û���
		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		
		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
			
					
		//����
		int level=curEntry.getLevel();
		int projLevel=curEntry.getCostAccount().getCurProject().getLevel();
		
		amountTotal=curEntry.getAmount();				
		if(amountTotal==null){
			amountTotal=FDCHelper.ZERO;
		}					
		
		//����
		apportionTotal=curEntry.getApportionValueTotal();				
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=curEntry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
		
		//��̯���Ӽ�
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);;
			
			
			if(entry.getLevel()<=level){
				break;
			}
			
			
			//ֱ���¼�
			if(entry.getLevel()==(level+1) 
					&& entry.getCostAccount().getCurProject().getLevel() == (projLevel + 1)) {
				
				//��̯
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
			    	 * ���ļ��㷽ʽ:
			    	 *  amount = amountTotal * (directAmount/directTotal)	ָ����̯
			    	 *  or
			    	 *  amount = amountTotal * (apportionValue/apportionTotal)	��ָ����̯
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

				//��������ĸ��ӿ�Ŀ�Ͳ�Ʒ���	jelon 12/22/2006	
	    		if(isProdSplitParent(entry)){
	    			//��Ʒ���
		    		apptAmountProduct(allEntrys,i);
	    			
	    		}else if(!entry.getCostAccount().isIsLeaf()){
	    			//���ӿ�Ŀ
	    			apptAmountAddlAcct(allEntrys,i);	    			
	    		}
		    	
		    	//�¼�������Ŀ�Ĳ��
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
	 * �������ɱ���̯���㣨���ӿ�Ŀ��
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */
    private void apptAmountAddlAcct(IObjectCollection allEntrys,int curIndex){		
    	ContractBillSplitEntryInfo curEntry =(ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);		
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//��Ʒ���
		if(curEntry.getSplitType()!=null && curEntry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
			apptAmountProduct(allEntrys,curIndex);
			return;
		}
		
    			
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;	
		
		//��ֵ̯���ܡ�ֱ�ӷ��û���
		BigDecimal apportionValue=FDCHelper.ZERO;//null;
		BigDecimal apportionTotal=FDCHelper.ZERO;	
		
		BigDecimal directAmount=FDCHelper.ZERO;//null;	
		BigDecimal directTotal=FDCHelper.ZERO;
			
		
			
		//����
		int level=curEntry.getLevel();
		int acctLevel=curEntry.getCostAccount().getLevel();
		BOSUuid projId=curEntry.getCostAccount().getCurProject().getId();
		
		amountTotal=curEntry.getAmount();
		if(amountTotal==null){
			amountTotal=FDCHelper.ZERO;
		}					
		
		//����	
		apportionTotal=curEntry.getOtherRatioTotal();
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=curEntry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
		
		/*
		//��ϸ������Ŀ��̯������ϸ������Ŀ���ۼ��¼�������Ŀ����ͬ��Ŀ���
		if(entry.getCostAccount().getCurProject().isIsLeaf()){
			
		}
		*/
		
		
		//��û�����÷�̯����������Ƿ�ΪΨһ���ӿ�Ŀ
		int count=0;		
		if(apportionTotal.compareTo(FDCHelper.ZERO)==0){
			for(int i=curIndex+1; i<allEntrys.size(); i++){				
				ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
				//ֱ���¼�
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
			
			//ֱ���¼�
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
//								���ڷ�̯
								//TODO ����Ҫ������
								
								/*
						    	 * modified by zhaoqin for R131101-0367 on 2013/11/05
						    	 * ���ļ��㷽ʽ:
						    	 *  amount = amountTotal * (directAmount/directTotal)	ָ����̯
						    	 *  or
						    	 *  amount = amountTotal * (apportionValue/apportionTotal)	��ָ����̯
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
	 * �������ɱ���̯���㣨��Ʒ��֣�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */
    private void apptAmountProduct(IObjectCollection allEntrys,int curIndex){
    	ContractBillSplitEntryInfo curEntry = (ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);		
		if(curEntry.isIsLeaf()){
			return;
		}
		    		
		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		//��ֵ̯���ܡ�ֱ�ӷ��û���
		BigDecimal apportionValue=FDCHelper.ZERO;//null;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		
		BigDecimal directAmount=FDCHelper.ZERO;//null;	
		BigDecimal directTotal=FDCHelper.ZERO;

		//����
		int level=curEntry.getLevel();
		int projLevel=curEntry.getCostAccount().getCurProject().getLevel();
		
		amountTotal=curEntry.getAmount();			
		if(amountTotal==null){
			amountTotal=FDCHelper.ZERO;
		}					
		
		//����
		apportionTotal=curEntry.getApportionValueTotal();				
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=curEntry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}
//		amountTotal=amountTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
		//�Ӽ�
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			ContractBillSplitEntryInfo entry =(ContractBillSplitEntryInfo)allEntrys.getObject(i);
			
			//ֱ���¼�
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
						//ֱ�ӽ��
						// modified by zhaoqin for R131101-0367 on 2013/11/4 start
						// ������Ҫ��:��Ʒ��ֵ�"ֱ�ӷ���"�ĳ�"ָ����̯",���㷽ʽ�ĳɰ���������
						// amount=directAmount;
						if(directTotal.compareTo(FDCHelper.ZERO) == 0)
							amount = FDCHelper.ZERO;
						else
							amount = (directAmount.multiply(amountTotal)).divide(directTotal, 10, BigDecimal.ROUND_HALF_UP);
					} else {
						//���ڷ�̯
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
	 * ���������ܷ�ָ̯��ֵ
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-11-07 <p>
	 */
    public void totApptValue(IObjectCollection allEntrys,ContractBillSplitEntryInfo curEntry){
    	int curIndex=allEntrys.indexOf(curEntry);
    	if(curIndex==-1){
    		return;
    	}
    	CostSplitTypeEnum costSplitType=curEntry.getSplitType();
		//���������	
		if(CostSplitTypeEnum.PRODSPLIT==costSplitType){
			totApptValueProduct(allEntrys,curIndex);
		}else if(CostSplitTypeEnum.BOTUPSPLIT==costSplitType){
			totApptValueBotUp(allEntrys,curIndex);
		}else{
			totApptValueTopDown(allEntrys,curIndex);
		}	
    }
    
	/**
	 * ���������ܷ�ָ̯��ֵ����Ʒ��֣�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
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
				//�ۼ�
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
	 * �������Ƿ��Ʒ�����ϸ
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-11-30 <p>
	 */
	public static boolean isProdSplitLeaf(ContractBillSplitEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
    
	/**
	 * ���������ܷ�ָ̯��ֵ��ĩ����֣�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-25 <p>
	 */
    private void totApptValueBotUp(IObjectCollection allEntrys,int curIndex){
    	//�ȵ�����ͨ�ķ�ֵ̯����
    	totApptValueTopDown(allEntrys,curIndex);
    	
    	
    	ContractBillSplitEntryInfo topEntry = (ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);    
		
    	//�����
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

		//ֱ�ӷ���
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
	 * �������Ƿ��Ʒ��ָ���
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-11-30 <p>
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
	 * �������Ƿ��¼�������Ŀ����ͬ��Ŀ����������ͬ��
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-28 <p>
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
	 * ���������ܷ�ָ̯��ֵ�����ӿ�Ŀ��
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */
    private void totApptValueAddlAcct(IObjectCollection allEntrys,int curIndex){
    	ContractBillSplitEntryInfo curEntry = (ContractBillSplitEntryInfo)allEntrys.getObject(curIndex);    
    	if(curEntry.isIsLeaf()){
    		return;
    	}    	
    	
    	/*if(curEntry.getSplitType()!=null && curEntry.getSplitType().equals(CostSplitType.PRODSPLIT)){
    		return;
    	}*/
    	
		//��Ʒ���
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
					//�ۼ�
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
	 * ���������ܷ�ָ̯��ֵ���Զ���֣�ͬʱ�������а����Ĳ�Ʒ��֣�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
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
			//��ϸ���̵Ŀ�Ŀ
			
    		if(isProdSplitParent(topEntry)){
    			totApptValueProduct(allEntrys,topIndex);				    			
    		}else{
    			totApptValueAddlAcct(allEntrys,topIndex);	    			
    		}	
			
			
		}else{		
			//����ϸ���̵ĸ���Ŀ�ĳɱ����¼�������ͬ��Ŀ�ۼ�
			
			for(int i=topIndex+1; i<allEntrys.size(); i++){				
				entry=(ContractBillSplitEntryInfo)allEntrys.getObject(i);
				
				if(entry.getLevel()>topLevel){
					if(isChildProjSameAcct(entry,topEntry)){	
						//�ݹ����
						totApptValueTopDown(allEntrys, i);
						
						//�ۼ�
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