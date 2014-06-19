package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillInfo;
import com.kingdee.eas.fdc.contract.IContractEstimateChangeBill;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class ContractEstimateChangeBillControllerBean extends AbstractContractEstimateChangeBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractEstimateChangeBillControllerBean");
    
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
    		throws BOSException, EASBizException {
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	return super._submit(ctx, model);
    }
    
    protected IObjectPK _save(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	return super._save(ctx, model);
    }
    
    /**
     * 1����˺�Լ�滮��Ӧ��Ԥ����ͬ�䶯Ϊ�����µ�
     * 2���ۼ��滮�����´˺�Լ�滮��Ԥ�����
     * 3����Ԥ����ͬ�䶯Ϊ���µ�
     * 
     */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
    	IContractEstimateChangeBill iFaced =  ContractEstimateChangeBillFactory.getLocalInstance(ctx);
    	
    	//�ۼ��滮���
    	SelectorItemCollection sel = new SelectorItemCollection();
    	sel.add("programmingContract.*");
    	sel.add("estimateAmount");
    	ContractEstimateChangeBillInfo info = iFaced.getContractEstimateChangeBillInfo(new ObjectUuidPK(billId), sel);
    	
    	BigDecimal estimateAmount = info.getEstimateAmount();
    	BigDecimal balance = FDCHelper.ZERO;
    	ProgrammingContractInfo programmingInfo = info.getProgrammingContract();
    	if(programmingInfo!=null){
    		balance = programmingInfo.getBalance();
    	}
    	balance = FDCHelper.subtract(balance, estimateAmount);
    	//���º�Լ�滮��Ԥ�����
    	BigDecimal progEsAmount = FDCHelper.ZERO;
    	progEsAmount = FDCHelper.add(progEsAmount, estimateAmount);
    	
    	//20120222 wangxin ͬʱ����һ��Ԥ����ͬ������Ƿ����¸���Ϊflase
	   EntityViewInfo view = new EntityViewInfo();
	   FilterInfo zFilter = new FilterInfo();
	   zFilter.getFilterItems().add(new FilterItemInfo("isLastest",Boolean.TRUE));
	   zFilter.getFilterItems().add(new FilterItemInfo("programmingContract.id",info.getProgrammingContract().getId().toString()));
	   view.setFilter(zFilter);
	   ContractEstimateChangeBillCollection coll1 = iFaced.getContractEstimateChangeBillCollection(view);
	   if(coll1.size()>0){
		   ContractEstimateChangeBillInfo info1 = coll1.get(0);
		   estimateAmount = info1.getEstimateAmount();
		   //�ں�Լ�滮����з�����һ��(���µ�,δʹ�õ�)Ԥ����ͬ�䶯�Ľ�
		   balance = balance.add(estimateAmount);
		   progEsAmount = progEsAmount.subtract(estimateAmount);
		   info1.setIsLastest(false);
		   SelectorItemCollection sel1 = new SelectorItemCollection();
		   sel1.add("isLastest");
		   iFaced.updatePartial(info1, sel1);
	   }
	   SelectorItemCollection coll = new SelectorItemCollection();
   	   coll.add("balance");
   	   coll.add("estimateAmount");
   	   programmingInfo.setBalance(balance);
//	   programmingInfo.setEstimateAmount(progEsAmount);
	   programmingInfo.setEstimateAmount(info.getEstimateAmount());
   	   ProgrammingContractFactory.getLocalInstance(ctx).updatePartial(programmingInfo, coll);
   	
	   info.setIsLastest(true);
	   SelectorItemCollection sel2 = new SelectorItemCollection();
	   sel2.add("isLastest");
	   iFaced.updatePartial(info, sel2);
	   super._audit(ctx, billId);
    }
    
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
    	IContractEstimateChangeBill iFaced =  ContractEstimateChangeBillFactory.getLocalInstance(ctx);
    	//�����滮���
    	SelectorItemCollection sel = new SelectorItemCollection();
    	sel.add("programmingContract.*");
    	sel.add("estimateAmount");
    	sel.add("isLastest");
    	ContractEstimateChangeBillInfo info = iFaced.getContractEstimateChangeBillInfo(new ObjectUuidPK(billId), sel);
    	if(!info.isIsLastest()){
    		throw new EASBizException(new NumericExceptionSubItem("1", "�����°汾���ܷ�����"));
    	}
    	//������µģ�δ��ʹ�õľͷ������ 20120419
    	if(info.isIsLastest()){
    		BigDecimal estimateAmount = info.getEstimateAmount();
        	BigDecimal balance = FDCHelper.ZERO;
        	BigDecimal progEsAmount = FDCHelper.ZERO;
        	ProgrammingContractInfo programmingInfo = info.getProgrammingContract();
        	if(programmingInfo!=null){
        		balance = info.getProgrammingContract().getBalance();
        	}
        	balance = FDCHelper.add(balance, estimateAmount);
        	programmingInfo.setBalance(balance);
        	programmingInfo.setEstimateAmount(progEsAmount);
        	SelectorItemCollection coll = new SelectorItemCollection();
        	coll.add("balance");
        	coll.add("estimateAmount");
        	ProgrammingContractFactory.getLocalInstance(ctx).updatePartial(programmingInfo, coll);
           
        	info.setIsLastest(false);
     	   SelectorItemCollection sel2 = new SelectorItemCollection();
     	   sel2.add("isLastest");
     	   iFaced.updatePartial(info, sel2);
    	}
    	super._unAudit(ctx, billId);
    }
    
    protected void _audit(Context ctx, List idList) throws BOSException,
    		EASBizException {
    	super._audit(ctx, idList);
    }
    
    protected void _unAudit(Context ctx, List idList) throws BOSException,
    		EASBizException {
    	super._unAudit(ctx, idList);
    }
    protected void _isReferenced(Context arg0, IObjectPK arg1) throws BOSException, EASBizException {
    	ContractEstimateChangeBillInfo info=this.getContractEstimateChangeBillInfo(arg0, arg1);
    	if(info.getProgrammingContract()!=null){
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("programmingContract.id", info.getProgrammingContract().getId().toString()));
    		if(ContractBillFactory.getLocalInstance(arg0).exists(filter)){
    			throw new EASBizException(new NumericExceptionSubItem("100","��Ӧ��Լ�滮�Ѿ�����ͬ���ã����ܽ���ɾ��������"));
    		}
    	}
	}
}