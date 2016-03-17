package com.kingdee.eas.fdc.gcftbiaoa;

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

public interface ISwb extends ICoreBillBase
{
    public SwbCollection getSwbCollection() throws BOSException;
    public SwbCollection getSwbCollection(EntityViewInfo view) throws BOSException;
    public SwbCollection getSwbCollection(String oql) throws BOSException;
    public SwbInfo getSwbInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SwbInfo getSwbInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SwbInfo getSwbInfo(String oql) throws BOSException, EASBizException;
    public void aduit(SwbInfo model) throws BOSException, EASBizException;
    public void unAudit(SwbInfo model) throws BOSException, EASBizException;
}