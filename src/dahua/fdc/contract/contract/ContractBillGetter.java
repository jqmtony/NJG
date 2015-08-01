package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.IFDCAction;

/**
 * �ӷ���˻�ȡ��ͬ����,��������Ȩ��Ӱ��
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
