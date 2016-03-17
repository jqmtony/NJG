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

public interface IIndoorEngineering extends ICoreBillBase
{
    public IndoorEngineeringCollection getIndoorEngineeringCollection() throws BOSException;
    public IndoorEngineeringCollection getIndoorEngineeringCollection(EntityViewInfo view) throws BOSException;
    public IndoorEngineeringCollection getIndoorEngineeringCollection(String oql) throws BOSException;
    public IndoorEngineeringInfo getIndoorEngineeringInfo(IObjectPK pk) throws BOSException, EASBizException;
    public IndoorEngineeringInfo getIndoorEngineeringInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public IndoorEngineeringInfo getIndoorEngineeringInfo(String oql) throws BOSException, EASBizException;
    public void aduit(IndoorEngineeringInfo model) throws BOSException, EASBizException;
    public void unAudit(IndoorEngineeringInfo model) throws BOSException, EASBizException;
}