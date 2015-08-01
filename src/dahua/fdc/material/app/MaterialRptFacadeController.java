package com.kingdee.eas.fdc.material.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.RetValue;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MaterialRptFacadeController extends BizController
{
    public RetValue getMaterialContractValues(Context ctx, RetValue param) throws BOSException, EASBizException, RemoteException;
    public RetValue getPartAMaterialValues(Context ctx, RetValue param) throws BOSException, EASBizException, RemoteException;
    public RetValue getMaterialContractMonthValues(Context ctx, RetValue param) throws BOSException, EASBizException, RemoteException;
}