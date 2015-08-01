package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.HisProjectInfo;
import com.kingdee.eas.fdc.basedata.HisProjectCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectFacadeController extends BizController
{
    public HisProjectCollection getHisProjectCollection(Context ctx, String curProjectID) throws BOSException, EASBizException, RemoteException;
    public boolean updateHisProjectInfo(Context ctx, HisProjectInfo hisProjectInfo) throws BOSException, EASBizException, RemoteException;
    public boolean updateCurProjectInfo(Context ctx, CurProjectInfo curProjectInfo) throws BOSException, EASBizException, RemoteException;
    public Map canAddNew(Context ctx, String curProjectId) throws BOSException, EASBizException, RemoteException;
    public boolean checkBeforeModifyIsDevPrj(Context ctx, CurProjectInfo ObjectValue) throws BOSException, EASBizException, RemoteException;
}