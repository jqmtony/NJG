package com.kingdee.eas.fdc.material.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.fdc.material.MaterialIndexInfo;
import com.kingdee.eas.fdc.material.MaterialIndexCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MaterialIndexController extends FDCDataBaseController
{
    public MaterialIndexInfo getMaterialIndexInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MaterialIndexInfo getMaterialIndexInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MaterialIndexInfo getMaterialIndexInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public MaterialIndexCollection getMaterialIndexCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public MaterialIndexCollection getMaterialIndexCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MaterialIndexCollection getMaterialIndexCollection(Context ctx) throws BOSException, RemoteException;
    public List getSQLString(Context ctx, String sql) throws BOSException, EASBizException, RemoteException;
}