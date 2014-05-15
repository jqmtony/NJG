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

public interface IMarketSupplierReviewGatherEntry extends ICoreBillEntryBase
{
    public MarketSupplierReviewGatherEntryInfo getMarketSupplierReviewGatherEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierReviewGatherEntryInfo getMarketSupplierReviewGatherEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierReviewGatherEntryInfo getMarketSupplierReviewGatherEntryInfo(String oql) throws BOSException, EASBizException;
    public MarketSupplierReviewGatherEntryCollection getMarketSupplierReviewGatherEntryCollection() throws BOSException;
    public MarketSupplierReviewGatherEntryCollection getMarketSupplierReviewGatherEntryCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierReviewGatherEntryCollection getMarketSupplierReviewGatherEntryCollection(String oql) throws BOSException;
}