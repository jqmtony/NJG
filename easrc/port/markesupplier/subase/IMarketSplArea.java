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

public interface IMarketSplArea extends IDataBase
{
    public MarketSplAreaInfo getMarketSplAreaInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSplAreaInfo getMarketSplAreaInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSplAreaInfo getMarketSplAreaInfo(String oql) throws BOSException, EASBizException;
    public MarketSplAreaCollection getMarketSplAreaCollection() throws BOSException;
    public MarketSplAreaCollection getMarketSplAreaCollection(EntityViewInfo view) throws BOSException;
    public MarketSplAreaCollection getMarketSplAreaCollection(String oql) throws BOSException;
}