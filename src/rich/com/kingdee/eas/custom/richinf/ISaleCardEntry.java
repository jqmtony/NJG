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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface ISaleCardEntry extends ICoreBillEntryBase
{
    public SaleCardEntryInfo getSaleCardEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SaleCardEntryInfo getSaleCardEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SaleCardEntryInfo getSaleCardEntryInfo(String oql) throws BOSException, EASBizException;
    public SaleCardEntryCollection getSaleCardEntryCollection() throws BOSException;
    public SaleCardEntryCollection getSaleCardEntryCollection(EntityViewInfo view) throws BOSException;
    public SaleCardEntryCollection getSaleCardEntryCollection(String oql) throws BOSException;
}