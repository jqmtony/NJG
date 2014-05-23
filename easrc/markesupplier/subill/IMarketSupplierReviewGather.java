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
import com.kingdee.eas.framework.ICoreBillBase;

public interface IMarketSupplierReviewGather extends ICoreBillBase
{
    public MarketSupplierReviewGatherCollection getMarketSupplierReviewGatherCollection() throws BOSException;
    public MarketSupplierReviewGatherCollection getMarketSupplierReviewGatherCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierReviewGatherCollection getMarketSupplierReviewGatherCollection(String oql) throws BOSException;
    public MarketSupplierReviewGatherInfo getMarketSupplierReviewGatherInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierReviewGatherInfo getMarketSupplierReviewGatherInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierReviewGatherInfo getMarketSupplierReviewGatherInfo(String oql) throws BOSException, EASBizException;
    public void audit(MarketSupplierReviewGatherInfo model) throws BOSException;
    public void unAudit(MarketSupplierReviewGatherInfo model) throws BOSException;
}