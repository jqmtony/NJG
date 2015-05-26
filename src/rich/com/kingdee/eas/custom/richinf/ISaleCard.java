package com.kingdee.eas.custom.richinf;

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

public interface ISaleCard extends ICoreBillBase
{
    public SaleCardCollection getSaleCardCollection() throws BOSException;
    public SaleCardCollection getSaleCardCollection(EntityViewInfo view) throws BOSException;
    public SaleCardCollection getSaleCardCollection(String oql) throws BOSException;
    public SaleCardInfo getSaleCardInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SaleCardInfo getSaleCardInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SaleCardInfo getSaleCardInfo(String oql) throws BOSException, EASBizException;
}