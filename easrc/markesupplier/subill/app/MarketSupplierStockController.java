package com.kingdee.eas.port.markesupplier.subill.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MarketSupplierStockController extends CoreBillBaseController
{
    public MarketSupplierStockCollection getMarketSupplierStockCollection(Context ctx) throws BOSException, RemoteException;
    public MarketSupplierStockCollection getMarketSupplierStockCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MarketSupplierStockCollection getMarketSupplierStockCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public MarketSupplierStockInfo getMarketSupplierStockInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierStockInfo getMarketSupplierStockInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierStockInfo getMarketSupplierStockInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, MarketSupplierStockInfo model) throws BOSException, RemoteException;
    public void unAudit(Context ctx, MarketSupplierStockInfo model) throws BOSException, RemoteException;
    public void addToSysSupplier(Context ctx, IObjectValue objectValue) throws BOSException, EASBizException, RemoteException;
}