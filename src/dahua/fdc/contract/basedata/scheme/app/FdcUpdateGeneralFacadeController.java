package com.kingdee.eas.fdc.basedata.scheme.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FdcUpdateGeneralFacadeController extends BizController
{
    public Map updateData(Context ctx, Map param) throws BOSException, RemoteException;
    public Map getData(Context ctx, Map param) throws BOSException, RemoteException;
}