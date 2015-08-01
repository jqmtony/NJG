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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMarketInfoType extends ITreeBase
{
    public MarketInfoTypeInfo getMarketInfoTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketInfoTypeInfo getMarketInfoTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketInfoTypeInfo getMarketInfoTypeInfo(String oql) throws BOSException, EASBizException;
    public MarketInfoTypeCollection getMarketInfoTypeCollection(EntityViewInfo view) throws BOSException;
    public MarketInfoTypeCollection getMarketInfoTypeCollection() throws BOSException;
    public MarketInfoTypeCollection getMarketInfoTypeCollection(String oql) throws BOSException;
    public Object[] getRelateData(String id, String[] tables) throws BOSException;
    public boolean updateRelateData(String oldID, String newID, Object[] tables) throws BOSException;
}