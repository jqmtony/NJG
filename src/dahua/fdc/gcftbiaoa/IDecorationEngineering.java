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

public interface IDecorationEngineering extends ICoreBillBase
{
    public DecorationEngineeringCollection getDecorationEngineeringCollection() throws BOSException;
    public DecorationEngineeringCollection getDecorationEngineeringCollection(EntityViewInfo view) throws BOSException;
    public DecorationEngineeringCollection getDecorationEngineeringCollection(String oql) throws BOSException;
    public DecorationEngineeringInfo getDecorationEngineeringInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DecorationEngineeringInfo getDecorationEngineeringInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DecorationEngineeringInfo getDecorationEngineeringInfo(String oql) throws BOSException, EASBizException;
    public void aduit(DecorationEngineeringInfo model) throws BOSException, EASBizException;
    public void unAudit(DecorationEngineeringInfo model) throws BOSException, EASBizException;
}