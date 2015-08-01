/**
 * 
 */
package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.BgCtrlParamInfo;
import com.kingdee.eas.ma.budget.IBgCtrlHandler;

/**
 * @author jinxp
 *
 */
public class BgCtrlPayRequestBillHandler implements IBgCtrlHandler {

	public static final String BGHANDLER = "com.kingdee.eas.fdc.contract.BgCtrlPayRequestBillHandler";
	/** 
	 * 预算控制类
	 * @see com.kingdee.eas.ma.budget.IBgCtrlHandler#getParameters(com.kingdee.bos.Context, java.lang.String)
	 */
	public BgCtrlParamCollection getParameters(Context ctx, String billId)
			throws BOSException, EASBizException {

		 BgCtrlParamCollection result = new BgCtrlParamCollection();
		 
		 IPayRequestBill iPayRequestBill = null;
	     if (ctx == null) {
	    	 iPayRequestBill = PayRequestBillFactory.getRemoteInstance(); 
	     }else{
	    	 iPayRequestBill = PayRequestBillFactory.getLocalInstance(ctx);
	     }
	     
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("number");
		selector.add("payDate");
		selector.add("currency.id");
		selector.add("currency.number");
		selector.add("orgUnit.id");
		selector.add("curProject.number");
		selector.add("curProject.name");
		selector.add("amount");
		selector.add("contractName");
		selector.add("contractNo");
			
	     PayRequestBillInfo info = iPayRequestBill.getPayRequestBillInfo(new ObjectUuidPK(billId),selector);
	     result.add(getCtrlInfo(info,null));
//	     for (int i = 0; i < info.getEntrys().size(); i++) {
//	    	 
//	    	 PayRequestBillEntryInfo entryInfo = info.getEntrys().get(i);
//	    	 result.add(getCtrlInfo(info,entryInfo));
//	     }
	     
		return result;
	}

	//预算控制项目
	public static BgCtrlParamInfo getCtrlInfo(PayRequestBillInfo info,PayRequestBillEntryInfo entryInfo){
		
		BgCtrlParamInfo ctrlInfo = new BgCtrlParamInfo();
		
        // 组织
        ctrlInfo.setOrgUnitId(info.getOrgUnit().getId().toString());
        // 业务日期
        ctrlInfo.setBizDate(info.getPayDate());
        // 源单ID
        if (info.getId() != null) {
            ctrlInfo.setSrcBillId(info.getId().toString());
            ctrlInfo.setSrcBillNumber(info.getNumber());
        }
        
        // 币别
        ctrlInfo.setCurrencyNumber(info.getCurrency().getNumber());
        ctrlInfo.setCurrencyId(info.getCurrency().getId().toString());
        
        // 项目组合
        String itemNumber = info.getContractNo();
        	//info.getCurProject().getNumber();
        String itemName = info.getContractName();
        
        ctrlInfo.setItemCombinNumber(itemNumber);
        ctrlInfo.setItemCombinName(itemName);
        // 单据金额
        ctrlInfo.setAmount(info.getAmount());
        
		return ctrlInfo;
	}
}
