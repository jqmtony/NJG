package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.port.pm.contract.ContractBillFactory;

public class ContractSourceControllerBean extends AbstractContractSourceControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.ContractSourceControllerBean");

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractSourceId",pk));
		if(ContractBillFactory.getLocalInstance(ctx).exists(filter)
				){
			throw new FDCBasedataException(FDCBasedataException.DELETE_ISREFERENCE_FAIL);
		}
		
		super._delete(ctx, pk);
	}
}