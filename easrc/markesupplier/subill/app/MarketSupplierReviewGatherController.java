package com.kingdee.eas.port.markesupplier.subill.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MarketSupplierReviewGatherController extends CoreBillBaseController
{
    public MarketSupplierReviewGatherCollection getMarketSupplierReviewGatherCollection(Context ctx) throws BOSException, RemoteException;
    public MarketSupplierReviewGatherCollection getMarketSupplierReviewGatherCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MarketSupplierReviewGatherCollection getMarketSupplierReviewGatherCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public MarketSupplierReviewGatherInfo getMarketSupplierReviewGatherInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierReviewGatherInfo getMarketSupplierReviewGatherInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierReviewGatherInfo getMarketSupplierReviewGatherInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, MarketSupplierReviewGatherInfo model) throws BOSException, RemoteException;
    public void unAudit(Context ctx, MarketSupplierReviewGatherInfo model) throws BOSException, RemoteException;
}