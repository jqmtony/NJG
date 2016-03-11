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

public interface IOutdoorEngineering extends ICoreBillBase
{
    public OutdoorEngineeringCollection getOutdoorEngineeringCollection() throws BOSException;
    public OutdoorEngineeringCollection getOutdoorEngineeringCollection(EntityViewInfo view) throws BOSException;
    public OutdoorEngineeringCollection getOutdoorEngineeringCollection(String oql) throws BOSException;
    public OutdoorEngineeringInfo getOutdoorEngineeringInfo(IObjectPK pk) throws BOSException, EASBizException;
    public OutdoorEngineeringInfo getOutdoorEngineeringInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public OutdoorEngineeringInfo getOutdoorEngineeringInfo(String oql) throws BOSException, EASBizException;
    public void aduit(OutdoorEngineeringInfo model) throws BOSException, EASBizException;
    public void unAudit(OutdoorEngineeringInfo model) throws BOSException, EASBizException;
}