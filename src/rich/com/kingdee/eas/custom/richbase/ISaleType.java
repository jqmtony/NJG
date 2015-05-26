package com.kingdee.eas.custom.richbase;

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

public interface ISaleType extends IDataBase
{
    public SaleTypeInfo getSaleTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SaleTypeInfo getSaleTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SaleTypeInfo getSaleTypeInfo(String oql) throws BOSException, EASBizException;
    public SaleTypeCollection getSaleTypeCollection() throws BOSException;
    public SaleTypeCollection getSaleTypeCollection(EntityViewInfo view) throws BOSException;
    public SaleTypeCollection getSaleTypeCollection(String oql) throws BOSException;
}