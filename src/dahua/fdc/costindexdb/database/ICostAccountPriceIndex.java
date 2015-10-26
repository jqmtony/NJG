package com.kingdee.eas.fdc.costindexdb.database;

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

public interface ICostAccountPriceIndex extends IDataBase
{
    public CostAccountPriceIndexInfo getCostAccountPriceIndexInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CostAccountPriceIndexInfo getCostAccountPriceIndexInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CostAccountPriceIndexInfo getCostAccountPriceIndexInfo(String oql) throws BOSException, EASBizException;
    public CostAccountPriceIndexCollection getCostAccountPriceIndexCollection() throws BOSException;
    public CostAccountPriceIndexCollection getCostAccountPriceIndexCollection(EntityViewInfo view) throws BOSException;
    public CostAccountPriceIndexCollection getCostAccountPriceIndexCollection(String oql) throws BOSException;
}