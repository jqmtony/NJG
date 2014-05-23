package com.kingdee.eas.port.markesupplier.subill;

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
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public interface IMarketSupplierStock extends ICoreBillBase
{
    public MarketSupplierStockCollection getMarketSupplierStockCollection() throws BOSException;
    public MarketSupplierStockCollection getMarketSupplierStockCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierStockCollection getMarketSupplierStockCollection(String oql) throws BOSException;
    public MarketSupplierStockInfo getMarketSupplierStockInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierStockInfo getMarketSupplierStockInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierStockInfo getMarketSupplierStockInfo(String oql) throws BOSException, EASBizException;
    public void audit(MarketSupplierStockInfo model) throws BOSException;
    public void unAudit(MarketSupplierStockInfo model) throws BOSException;
    public void addToSysSupplier(IObjectValue objectValue) throws BOSException, EASBizException;
}