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

public interface IMarketSplServiceType extends IDataBase
{
    public MarketSplServiceTypeInfo getMarketSplServiceTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSplServiceTypeInfo getMarketSplServiceTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSplServiceTypeInfo getMarketSplServiceTypeInfo(String oql) throws BOSException, EASBizException;
    public MarketSplServiceTypeCollection getMarketSplServiceTypeCollection() throws BOSException;
    public MarketSplServiceTypeCollection getMarketSplServiceTypeCollection(EntityViewInfo view) throws BOSException;
    public MarketSplServiceTypeCollection getMarketSplServiceTypeCollection(String oql) throws BOSException;
}