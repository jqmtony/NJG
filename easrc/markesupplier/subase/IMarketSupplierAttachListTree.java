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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMarketSupplierAttachListTree extends ITreeBase
{
    public MarketSupplierAttachListTreeInfo getMarketSupplierAttachListTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierAttachListTreeInfo getMarketSupplierAttachListTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierAttachListTreeInfo getMarketSupplierAttachListTreeInfo(String oql) throws BOSException, EASBizException;
    public MarketSupplierAttachListTreeCollection getMarketSupplierAttachListTreeCollection() throws BOSException;
    public MarketSupplierAttachListTreeCollection getMarketSupplierAttachListTreeCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierAttachListTreeCollection getMarketSupplierAttachListTreeCollection(String oql) throws BOSException;
}