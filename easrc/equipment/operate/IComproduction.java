package com.kingdee.eas.port.equipment.operate;

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

public interface IComproduction extends ICoreBillBase
{
    public ComproductionCollection getComproductionCollection() throws BOSException;
    public ComproductionCollection getComproductionCollection(EntityViewInfo view) throws BOSException;
    public ComproductionCollection getComproductionCollection(String oql) throws BOSException;
    public ComproductionInfo getComproductionInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ComproductionInfo getComproductionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ComproductionInfo getComproductionInfo(String oql) throws BOSException, EASBizException;
    public void actionAudit(ComproductionInfo model) throws BOSException;
    public void actionUnAudit(ComproductionInfo model) throws BOSException;
}