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
 * ����������ز����ݲ�ֵĲ���߼�����ط���
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
	 * ���������ܷ�ָ̯��ֵ
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-11-07 <p>
	 */
    public void totApptValue(IObjectCollection allEntrys,FDCNoCostSplitBillEntryInfo curEntry){
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
	 * �������Ƿ��Ʒ��ָ���
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-11-30 <p>
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
	 * �������Ƿ��Ʒ�����ϸ
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-11-30 <p>
	 */
	public boolean isProdSplitLeaf(FDCNoCostSplitBillEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
    

	
	/**
	 * �������Ƿ������Ʒ���
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
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
     * ���ܷ�̯���
     * @author sxhong  		Date 2006-12-11
     * @param curIndex
     */
    public BigDecimal totAmount(IObjectCollection allEntrys){
    	
    	

		BigDecimal amountTotal=FDCHelper.ZERO;
		BigDecimal amount=FDCHelper.ZERO;
		FDCNoCostSplitBillEntryInfo entry=null;
		//�������ܽ��
		for(int i=0; i<allEntrys.size(); i++){
			entry =(FDCNoCostSplitBillEntryInfo)(allEntrys.getObject(i));
			
			if(entry.getLevel()==0){
				amount=entry.getAmount();
				if(amount!=null){
					amountTotal=amountTotal.add(amount);
				}
			}						
		}			
//		TODO ���õ����ܿؼ�
//		txtSplitedAmount.setValue(amountTotal);
		
    	return amountTotal;
    }
	/**
	 * ���������ܷ�ָ̯��ֵ����Ʒ��֣�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
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
	 * ���������ܷ�ָ̯��ֵ���Զ���֣�ͬʱ�������а����Ĳ�Ʒ��֣�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
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
			//��ϸ���̵Ŀ�Ŀ
    		if(isProdSplitParent(topEntry)){
    			totApptValueProduct(allEntrys,topIndex);				    			
    		}else{
    			totApptValueAddlAcct(allEntrys,topIndex);	    			
    		}	
		}else{		
			//����ϸ���̵ĸ���Ŀ�ĳɱ����¼�������ͬ��Ŀ�ۼ�
			
			for(int i=topIndex+1; i<allEntrys.size(); i++){				
				entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
				
				if(entry.getLevel()==topLevel+1&&entry.getCurProject().getParent().getId().equals(topPrj)){
					//�ݹ����,����ֱ���¼����ۼ�
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
	 * ���������ܷ�ָ̯��ֵ��ĩ����֣�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-25 <p>
	 */
    private void totApptValueBotUp(IObjectCollection allEntrys,int curIndex){
    	//�ȵ�����ͨ�ķ�ֵ̯����
    	totApptValueTopDown(allEntrys,curIndex);
    	FDCNoCostSplitBillEntryInfo topEntry = (FDCNoCostSplitBillEntryInfo)allEntrys.getObject(curIndex);    
		
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
			FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
			if(entry.getLevel()>0){			
				//���������ӿ�Ŀ
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
	 * ���������ܷ�ָ̯��ֵ�����ӿ�Ŀ��
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */
    private void totApptValueAddlAcct(IObjectCollection allEntrys,int curIndex){
    	//�ǳɱ�����޸��ӿ�Ŀ,��ʱ����
    }
    

	/**
	 * �������ɱ���̯����
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */    
    public void apptAmount(IObjectCollection allEntrys,FDCNoCostSplitBillEntryInfo curEntry){
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
		
		    	
//		if(true) return;
		//���������ۼ�ʱ�������������������ڷ�̯��ɺ��ٽ��д���	jelon 12/26/2006

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
					//ֱ�ӽ�����0��û�з�ֵ̯
					isDirectAmt=true;
				}
				if(amount!=null&&!isDirectAmt){//ֱ�ӽ�������
					amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
					entry.setAmount(amount);
				}
							
			}else{
				break;
			}
		}
		

		//��������֤���¼��Ļ��ܹ�ϵ
		adjustAmount(allEntrys,curEntry);
		
		
		//�������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ�		jelon 12/29/2006
		totAmountAddlAcct(allEntrys,curIndex);
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
    	FDCNoCostSplitBillEntryInfo curEntry = (FDCNoCostSplitBillEntryInfo)allEntrys.getObject(curIndex);		
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
		int projLevel=curEntry.getCurProject().getLevel();
		
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
		
		//�Ӽ�
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);;
			
			
			if(entry.getLevel()<=level){
				break;
			}
			
			
			//ֱ���¼�
			if(entry.getLevel()==(level+1) 
					&& entry.getCurProject().getLevel()==(projLevel+1)){
				
				//��̯
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

				//��������ĸ��ӿ�Ŀ�Ͳ�Ʒ���	jelon 12/22/2006	
	    		if(isProdSplitParent(entry)){
	    			//��Ʒ���
		    		apptAmountProduct(allEntrys,i);
	    			
	    		}else if(!entry.getAccountView().isIsLeaf()){
	    			//���ӿ�Ŀ
	    			apptAmountAddlAcct(allEntrys,i);	    			
	    		}
		    	
		    	//�¼�������Ŀ�Ĳ��
		    	if(!entry.getCurProject().isIsLeaf()){
		    		apptAmountTopDown(allEntrys,i);
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
    	FDCNoCostSplitBillEntryInfo curEntry = (FDCNoCostSplitBillEntryInfo)allEntrys.getObject(curIndex);		
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
		int projLevel=curEntry.getCurProject().getLevel();
		
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
		
		//�Ӽ�
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCNoCostSplitBillEntryInfo entry =(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(i);
			
			//ֱ���¼�
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
//						//���ڷ�̯
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
		//amountTotal.setScale(10);
		//update by renliang
		amountTotal = amountTotal.setScale(10);	
		
		
		
		//��ֵ̯���ܡ�ֱ�ӷ��û���
		BigDecimal apportionValue=FDCHelper.ZERO;//null;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		
		BigDecimal directAmount=FDCHelper.ZERO;//null;	
		BigDecimal directTotal=FDCHelper.ZERO;
		
		//���	
		BigDecimal ratio=FDCHelper.ZERO;
		
    	FDCNoCostSplitBillEntryInfo curEntry=(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(curIndex);		
    	
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
    	    	
    	amount=amountTotal.subtract(directTotal);
    	if(apportionTotal.compareTo(FDCHelper.ZERO)==0){
    		ratio=FDCHelper.ZERO;
    	}else{
    		//����ֵ����ֱ����ʾֵ����Ҫ��֤С��λ��
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
		
		
			
		//����
		level=entry.getLevel();
		amountTotal=entry.getAmount();
		
		//����
		apportionTotal=entry.getApportionValueTotal();				
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=entry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
		
		//�Ӽ�
		for(int i=curIndex+1; i<kdtEntrys.getRowCount(); i++){				
			row=kdtEntrys.getRow(i);
			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
			
			//ֱ���¼�
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
				else	//���ڷ�̯
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
     * �������ɱ���̯���㣨���ӿ�Ŀ��
     * @author sxhong  		Date 2007-1-29
     * @param allEntrys
     * @param curIndex
     */
    private void apptAmountAddlAcct(IObjectCollection allEntrys,int curIndex){
    	//��ƿ�Ŀ��ʱ��������
    }

    /**
     * �������ɱ���̯���㣨ĩ����֣��ݹ��֣�
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
		
		
		//��ֱ̯�ӷ��ã�Ŀǰֻ����ϸ������ϸ��Ŀ����ֱ�ӷ��ã�ֱ�ӷ���ʵ�ʲ��÷�̯��
		if(parApportionTotal.compareTo(FDCHelper.ZERO)!=0){
			/*directAmount=directAmount.add(
					parDirectAmount.multiply(apportionValue).divide(parApportionTotal,2,BigDecimal.ROUND_HALF_EVEN));*/	
			directAmount=directAmount.add(
					parDirectAmount.multiply(apportionValue).divide(parApportionTotal,10,BigDecimal.ROUND_HALF_EVEN));	
		}				
		
		
		if(topEntry.getCurProject().isIsLeaf()){		
			//��ϸ���̵Ŀ�Ŀֱ�ӷ�̯
			
			/*amount=apportionValue.multiply(ratio).add(directAmount);
			amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
			amountTotal=amount;*/
			
			//��һ����Ŀ����ĩ����̯
	    	if(!topEntry.isIsAddlAccount() && !isProdSplitLeaf(topEntry)){
	    		amount=apportionValue.multiply(ratio).add(directAmount);
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
    			
    		}else if(!topEntry.getAccountView().isIsLeaf()){
    			//���ӿ�Ŀ
    			apptAmountAddlAcct(allEntrys, curIndex);	    			
    		}
			
			
		}else{		
			//����ϸ���̵ĸ���Ŀ�ĳɱ����¼�������ͬ��Ŀ�ۼ�
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
     * ����ˢ�½ӿڣ����ں�ͬ�������֣����ı���ͬ�����֣�
     * @author sxhong  		Date 2006-12-13
     * @param fdcSplitBillInfo 
     * @param iFDCNoCostSplitBillEntry ���ؽӿڻ���Զ�̽ӿڣ�nullֵ�򲻸��µ����ݿ�
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
	 * ���������÷�ָ̯��ֵ
	 * @return
	 * @author: 
	 * ����ʱ�䣺2006-12-29 <p>
	 */
	private void setApportionValue(IObjectCollection entrys, int index,CurProjectCollection curProjectCollection) throws BOSException{
		
		FDCNoCostSplitBillEntryInfo topEntry=(FDCNoCostSplitBillEntryInfo)entrys.getObject(index);		
		if(topEntry==null||topEntry.isIsLeaf()){
			return;
		}
		int topIndex=index;
		int topLevel=topEntry.getLevel();		
		//��̯���ͣ����ݸ����ķ�̯��������ֱ���¼��ķ�ָ̯������
		ApportionTypeInfo apptType=topEntry.getApportionType();

		BigDecimal apptValue=null;		
		FDCNoCostSplitBillEntryInfo entry=null;

		if(topEntry.getCurProject().isIsLeaf()){		
			//��ϸ���̵Ŀ�Ŀ
    		if(isProdSplitParent(topEntry)){
				//��Ʒ���
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
				//���ӿ�Ŀ
    			for(int i=topIndex+1; i<entrys.size(); i++){				
    				entry=(FDCNoCostSplitBillEntryInfo)entrys.getObject(i);
    				if(entry.getLevel()>topLevel
    						&& entry.getCurProject().getId().equals(topEntry.getCurProject().getId())){
    					//�ݹ���ã���Ҫ���ڴ������а����Ĳ�Ʒ��֣�
    					setApportionValue(entrys, i, curProjectCollection);
    				}
    			}	
    			
    			//���ӿ�Ŀ���ô����ָ̯��ֵ    	
    			//���ӿ�Ŀָ��ֵΪ��̯ʱָ����ֵ������ˢ�´���
    		}	
			
			
		}else{		
			//����ϸ���̵ĸ���Ŀ�ĳɱ����¼�������ͬ��Ŀ�ۼ�
			
			for(int i=topIndex+1; i<entrys.size(); i++){				
				entry=(FDCNoCostSplitBillEntryInfo)entrys.getObject(i);
				
				if(entry.getLevel()==topLevel+1){
						//�ݹ����
						setApportionValue(entrys, i, curProjectCollection);
						
						//������Ŀ��ָ̯��
						apptValue=null;

						//�����̯������ֻ��ֱ�ӷ��ö��������÷�ָ̯��ֵ
						if(entry.isIsApportion() && apptType!=null){
							for (Iterator iter2 = curProjectCollection.iterator(); iter2.hasNext();){
								CurProjectInfo curProj=(CurProjectInfo)iter2.next();
								
								if(curProj.getId()!=null 
										&& entry!=null && entry.getCurProject()!=null
										&& entry.getCurProject().getId()!=null
										&& curProj.getId().equals(entry.getCurProject().getId())){
									//������Ŀ��ֵ̯��
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
	 * ��ǰ��¼�µĹ���
	 * @author sxhong  		Date 2006-12-12
	 * @param topEntry
	 * @return
	 * @throws BOSException
	 */
	private CurProjectCollection getEntryCurProject(FDCNoCostSplitBillEntryInfo topEntry) throws BOSException{
//		if(topEntry.getLevel()!=0){
//			return;
//		}
		//������Ŀ
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
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
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
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
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
	 * �������������
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private void adjustAmountProject(IObjectCollection allEntrys, int index){
		FDCNoCostSplitBillEntryInfo curEntry =(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(index);		
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//����
		BigDecimal curAmount=curEntry.getAmount();
		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		//amountTotal.setScale(10);
		//update by renliang
		amountTotal = amountTotal.setScale(10);
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//ֱ���¼�����
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
				//������Ϊ���������
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
				//�ݹ����
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
	 * �������������--���ӿ�Ŀ,�ڲ����Ŀ����в��ᱻִ��
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private void adjustAmountAccount(IObjectCollection allEntrys, int index){
		FDCNoCostSplitBillEntryInfo curEntry =(FDCNoCostSplitBillEntryInfo)allEntrys.getObject(index);	
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//����
		BigDecimal curAmount=curEntry.getAmount();
		
		//���	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		//amountTotal.setScale(10); 
		//update by renliang			
		amountTotal  = amountTotal.setScale(10);
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//ֱ���¼�����
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
	 * ���������ò�ַ�¼��Seq���Ա�֤��ʾ˳��
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-28 <p>
	 */
	public void setEntrySeq(FDCNoCostSplitBillEntryInfo entry){
		//index��Ϊseq��actionSave_actionPerformed(ActionEvent e)����index
		entry.setSeq(entry.getIndex());
		
		//index��Ϊ�����ţ�costSplit()����index������Ҫ����actionSave_actionPerformed(ActionEvent e)�е�����
		//entry.setSeq(entry.getIndex()*100000 + entry.getSeq()%100000);	
	}

	/**
	 * ���������ܷ�̯���������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ�����ֵΪ�¼�������Ŀ��ͬ��Ŀ�ĳɱ�֮�ͣ�
     * @author sxhong  		Date 2007-1-29
     * @param allEntrys
     * @param curIndex
     */
    public void totAmountAddlAcct(IObjectCollection allEntrys,int curIndex){
    	//��ƿ�Ŀ,��������
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
