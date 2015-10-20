package com.kingdee.eas.fdc.aimcost.costkf;

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

public interface ICQGS extends ICoreBillBase
{
    public CQGSCollection getCQGSCollection() throws BOSException;
    public CQGSCollection getCQGSCollection(EntityViewInfo view) throws BOSException;
    public CQGSCollection getCQGSCollection(String oql) throws BOSException;
    public CQGSInfo getCQGSInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CQGSInfo getCQGSInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CQGSInfo getCQGSInfo(String oql) throws BOSException, EASBizException;
    public void aduit(CQGSInfo model) throws BOSException, EASBizException;
    public void unAudit(CQGSInfo model) throws BOSException, EASBizException;
}