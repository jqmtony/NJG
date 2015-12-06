package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectDynamicCostFactory
{
    private ProjectDynamicCostFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("75CD9A79") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("75CD9A79") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("75CD9A79"));
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("75CD9A79"));
    }
}