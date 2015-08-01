package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.ObjectBaseController;
import com.kingdee.eas.fdc.basedata.EASLogTimeCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.EASLogTimeInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface EASLogTimeController extends ObjectBaseController
{
    public boolean startSucess(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean logTime(Context ctx, String ip, String port, String dbName, String userName, String password, String tableName, String field) throws BOSException, EASBizException, RemoteException;
}