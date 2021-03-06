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

public interface IMarketSupplierFileTyp extends IDataBase
{
    public MarketSupplierFileTypInfo getMarketSupplierFileTypInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierFileTypInfo getMarketSupplierFileTypInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierFileTypInfo getMarketSupplierFileTypInfo(String oql) throws BOSException, EASBizException;
    public MarketSupplierFileTypCollection getMarketSupplierFileTypCollection() throws BOSException;
    public MarketSupplierFileTypCollection getMarketSupplierFileTypCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierFileTypCollection getMarketSupplierFileTypCollection(String oql) throws BOSException;
}