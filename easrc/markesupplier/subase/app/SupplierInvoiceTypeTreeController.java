package com.kingdee.eas.port.markesupplier.subase.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SupplierInvoiceTypeTreeController extends TreeBaseController
{
    public SupplierInvoiceTypeTreeInfo getSupplierInvoiceTypeTreeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SupplierInvoiceTypeTreeInfo getSupplierInvoiceTypeTreeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SupplierInvoiceTypeTreeInfo getSupplierInvoiceTypeTreeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SupplierInvoiceTypeTreeCollection getSupplierInvoiceTypeTreeCollection(Context ctx) throws BOSException, RemoteException;
    public SupplierInvoiceTypeTreeCollection getSupplierInvoiceTypeTreeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SupplierInvoiceTypeTreeCollection getSupplierInvoiceTypeTreeCollection(Context ctx, String oql) throws BOSException, RemoteException;
}