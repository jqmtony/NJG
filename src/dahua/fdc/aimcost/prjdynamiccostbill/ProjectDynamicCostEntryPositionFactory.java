package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectDynamicCostEntryPositionFactory
{
    private ProjectDynamicCostEntryPositionFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntryPosition getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntryPosition)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("693195C2") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntryPosition.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntryPosition getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntryPosition)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("693195C2") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntryPosition.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntryPosition getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntryPosition)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("693195C2"));
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntryPosition getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntryPosition)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("693195C2"));
    }
}