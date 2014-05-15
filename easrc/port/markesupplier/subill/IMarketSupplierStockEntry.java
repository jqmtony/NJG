package com.kingdee.eas.port.markesupplier.subill;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IMarketSupplierStockEntry extends ICoreBillEntryBase
{
    public MarketSupplierStockEntryInfo getMarketSupplierStockEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierStockEntryInfo getMarketSupplierStockEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierStockEntryInfo getMarketSupplierStockEntryInfo(String oql) throws BOSException, EASBizException;
    public MarketSupplierStockEntryCollection getMarketSupplierStockEntryCollection() throws BOSException;
    public MarketSupplierStockEntryCollection getMarketSupplierStockEntryCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierStockEntryCollection getMarketSupplierStockEntryCollection(String oql) throws BOSException;
}