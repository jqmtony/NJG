package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.ICoreBillBase;

public interface IDBAimCost extends ICoreBillBase
{
    public DBAimCostInfo getDBAimCostInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DBAimCostInfo getDBAimCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DBAimCostCollection getDBAimCostCollection(EntityViewInfo view) throws BOSException;
    public DBAimCostCollection getDBAimCostCollection() throws BOSException;
    public DBAimCostCollection getDBAimCostCollection(String oql) throws BOSException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void audit(List idList) throws BOSException, EASBizException;
    public void unaudit(BOSUuid billId) throws BOSException, EASBizException;
    public void unaudit(List idList) throws BOSException, EASBizException;
}