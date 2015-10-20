package com.kingdee.eas.fdc.contract.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.util.SysUtil;

public class SplitInvokeStrategyFactory {
	private SplitInvokeStrategyFactory(){}
	private static final BOSObjectType contractType=new ContractBillInfo().getBOSType();
	private static final BOSObjectType changeType=new ContractChangeBillInfo().getBOSType();
	private static final BOSObjectType settleType=new ContractSettlementBillInfo().getBOSType();
	private static final BOSObjectType paymentType=new PaymentBillInfo().getBOSType();
	public static AbstractSplitInvokeStrategy createSplitStrategy(String billId, CoreUIObject parentUI){
		if(billId==null){
			return null;
		}
		if(contractType.equals(BOSUuid.read(billId).getType())){
			return new ContractSplitInvokeStrategy(billId,parentUI);
		}
		if(changeType.equals(BOSUuid.read(billId).getType())){
			return new ConChangeSplitInvokeStrategy(billId,parentUI);
		}
		if(settleType.equals(BOSUuid.read(billId).getType())){
			return new ConSettleSplitInvokeStrategy(billId,parentUI);
		}
		if(paymentType.equals(BOSUuid.read(billId).getType())){
			return new PaymentSplitInvokeStrategy(billId,parentUI);
		}
		return AbstractSplitInvokeStrategy.getEmptySplitInvokeStrategy();
	}
	
	public static AbstractSplitInvokeStrategy createSplitStrategyByParam(String billId, CoreUIObject parentUI) throws EASBizException, BOSException{
		if(FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_SPLITAFTERAUDIT)){
			int retValue = FDCMsgBox.showConfirm2New(parentUI, "单据已审批请立即进行拆分！");
			if(retValue==FDCMsgBox.YES){
				return createSplitStrategy(billId, parentUI);
			}
		}
		return AbstractSplitInvokeStrategy.getEmptySplitInvokeStrategy();
	}
	
}
