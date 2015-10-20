package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractWebServiceFacadeController extends BizController
{
    public void setContract(Context ctx, ContractBillInfo contractBill) throws BOSException, EASBizException, RemoteException;
    public boolean validate(Context ctx, ContractBillInfo contractBill) throws BOSException, EASBizException, RemoteException;
}