package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectDynamicCostEntryFactory
{
    private ProjectDynamicCostEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BF1D2179") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BF1D2179") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BF1D2179"));
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BF1D2179"));
    }
}