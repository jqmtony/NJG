package com.kingdee.eas.custom.richfacade;

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
import java.util.Date;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface EASRichFacadeController extends BizController
{
    public String[] saveTempData(Context ctx, String String) throws BOSException, RemoteException;
    public String[] saveExamBill(Context ctx, Date date, String String) throws BOSException, RemoteException;
}