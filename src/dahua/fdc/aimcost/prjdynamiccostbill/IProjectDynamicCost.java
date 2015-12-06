package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

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

public interface IProjectDynamicCost extends ICoreBillBase
{
    public ProjectDynamicCostCollection getProjectDynamicCostCollection() throws BOSException;
    public ProjectDynamicCostCollection getProjectDynamicCostCollection(EntityViewInfo view) throws BOSException;
    public ProjectDynamicCostCollection getProjectDynamicCostCollection(String oql) throws BOSException;
    public ProjectDynamicCostInfo getProjectDynamicCostInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectDynamicCostInfo getProjectDynamicCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectDynamicCostInfo getProjectDynamicCostInfo(String oql) throws BOSException, EASBizException;
    public void audit(ProjectDynamicCostInfo model) throws BOSException;
    public void unAudit(ProjectDynamicCostInfo model) throws BOSException;
}