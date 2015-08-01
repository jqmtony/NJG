package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

public class ContractWebServiceFacadeControllerBean extends AbstractContractWebServiceFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractWebServiceFacadeControllerBean");
    protected void _setContract(Context ctx, IObjectValue contractBill)throws BOSException, EASBizException
    {
    }
    protected boolean _validate(Context ctx, IObjectValue contractBill)throws BOSException, EASBizException
    {
    	if(contractBill!=null){
    		return true;
    	}
    	return false;
    }
}