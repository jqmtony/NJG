package com.kingdee.eas.fdc.costdb;

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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMarketInfo extends IBillBase
{
    public MarketInfoInfo getMarketInfoInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketInfoInfo getMarketInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketInfoInfo getMarketInfoInfo(String oql) throws BOSException, EASBizException;
    public MarketInfoCollection getMarketInfoCollection() throws BOSException;
    public MarketInfoCollection getMarketInfoCollection(EntityViewInfo view) throws BOSException;
    public MarketInfoCollection getMarketInfoCollection(String oql) throws BOSException;
}