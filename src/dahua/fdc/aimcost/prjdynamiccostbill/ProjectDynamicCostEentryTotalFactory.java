package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectDynamicCostEentryTotalFactory
{
    private ProjectDynamicCostEentryTotalFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEentryTotal getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEentryTotal)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0467D7DE") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEentryTotal.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEentryTotal getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEentryTotal)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0467D7DE") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEentryTotal.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEentryTotal getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEentryTotal)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0467D7DE"));
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEentryTotal getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEentryTotal)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0467D7DE"));
    }
}