package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.IFDCAction;

/**
 * 从服务端获取合同数据,避免数据权限影响
 * R111020-0297
 * @author tim_gao
 *
 */
public class ContractBillGetter implements IFDCAction {
	private String oql ;
	public ContractBillGetter(String oql){
		this.oql = oql;
	}

	public Object actionPerformed(Context ctx) throws Exception {
		return ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(oql);
	}

}
