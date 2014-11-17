package com.kingdee.eas.port.pm.invest;

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

public interface ProjectBudgetFacadeController extends BizController
{
    public String[] subBudgetAmount(Context ctx, String projectNumber, String year, String budgetNumber, String amount, String stag, boolean isBack, String backAmount) throws BOSException, RemoteException;
    public String[] backBudgetAmount(Context ctx, String projectNumber, String year, String budgetNumber, String amount, String stag, boolean isBack, String backAmount) throws BOSException, RemoteException;
}