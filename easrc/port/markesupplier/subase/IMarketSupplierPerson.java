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

public interface IMarketSupplierPerson extends IDataBase
{
    public MarketSupplierPersonInfo getMarketSupplierPersonInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierPersonInfo getMarketSupplierPersonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierPersonInfo getMarketSupplierPersonInfo(String oql) throws BOSException, EASBizException;
    public MarketSupplierPersonCollection getMarketSupplierPersonCollection() throws BOSException;
    public MarketSupplierPersonCollection getMarketSupplierPersonCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierPersonCollection getMarketSupplierPersonCollection(String oql) throws BOSException;
}