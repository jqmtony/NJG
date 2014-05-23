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

public interface ISupplierType extends IDataBase
{
    public SupplierTypeInfo getSupplierTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierTypeInfo getSupplierTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierTypeInfo getSupplierTypeInfo(String oql) throws BOSException, EASBizException;
    public SupplierTypeCollection getSupplierTypeCollection() throws BOSException;
    public SupplierTypeCollection getSupplierTypeCollection(EntityViewInfo view) throws BOSException;
    public SupplierTypeCollection getSupplierTypeCollection(String oql) throws BOSException;
}