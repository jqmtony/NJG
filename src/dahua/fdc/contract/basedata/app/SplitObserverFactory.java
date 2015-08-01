package com.kingdee.eas.fdc.basedata.app;

import java.util.Observer;

public class SplitObserverFactory {

	public static final String PAYMENT_SPLIT = "paymentSplit";
	public static final String CON_SETTLE_SPLIT = "conSettleSplit";
	public static final String CON_CHANGE_SPLIT = "conChangeSplit";
	public static final String CONTRACT_SPLIT = "contractSplit";
	public static final String CLEAR_CON_SPLIT = "ConSplitClear";
	public static final String CLEAR_PAYMENT_SPLIT = "paymentsplitclear";

	public static Observer factory(String which) throws BadSplitObserverException{
		if(which.equals(CONTRACT_SPLIT)) {
			return new ContractSplitObserver();
		}
		else if(which.equals(CON_CHANGE_SPLIT)) {
			return new ConChangeSplitObserver();
		}
		else if(which.equals(CON_SETTLE_SPLIT)) {
			return new ConSettleSplitObserver();
		}
		else if(which.equals(PAYMENT_SPLIT)) {
			return new PaymentSplitObserver();
		}
		else if(which.equals(CLEAR_PAYMENT_SPLIT)) {
			return new PaymentSplitClearObserver();
		}
		else if(which.equals(CLEAR_CON_SPLIT)) {
			return new ConSplitClearObserver();
		}
		else {
			throw new BadSplitObserverException("Bad Split Observer");
		}
		
	}
}
