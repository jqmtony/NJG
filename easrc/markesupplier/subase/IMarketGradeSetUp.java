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

public interface IMarketGradeSetUp extends IDataBase
{
    public MarketGradeSetUpInfo getMarketGradeSetUpInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketGradeSetUpInfo getMarketGradeSetUpInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketGradeSetUpInfo getMarketGradeSetUpInfo(String oql) throws BOSException, EASBizException;
    public MarketGradeSetUpCollection getMarketGradeSetUpCollection() throws BOSException;
    public MarketGradeSetUpCollection getMarketGradeSetUpCollection(EntityViewInfo view) throws BOSException;
    public MarketGradeSetUpCollection getMarketGradeSetUpCollection(String oql) throws BOSException;
}