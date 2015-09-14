package com.kingdee.eas.fdc.dahuaschedule.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface DahuaScheduleFacadeController extends BizController
{
    public String[] createTempData(Context ctx, String xml) throws BOSException, RemoteException;
    public String[] createScheduleBill(Context ctx) throws BOSException, RemoteException;
}