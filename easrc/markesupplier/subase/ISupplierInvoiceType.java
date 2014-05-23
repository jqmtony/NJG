package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ISupplierInvoiceType extends IDataBase
{
    public SupplierInvoiceTypeInfo getSupplierInvoiceTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierInvoiceTypeInfo getSupplierInvoiceTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierInvoiceTypeInfo getSupplierInvoiceTypeInfo(String oql) throws BOSException, EASBizException;
    public SupplierInvoiceTypeCollection getSupplierInvoiceTypeCollection() throws BOSException;
    public SupplierInvoiceTypeCollection getSupplierInvoiceTypeCollection(EntityViewInfo view) throws BOSException;
    public SupplierInvoiceTypeCollection getSupplierInvoiceTypeCollection(String oql) throws BOSException;
}