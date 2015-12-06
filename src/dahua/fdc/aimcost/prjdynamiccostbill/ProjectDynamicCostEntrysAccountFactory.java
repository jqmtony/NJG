package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectDynamicCostEntrysAccountFactory
{
    private ProjectDynamicCostEntrysAccountFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrysAccount getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrysAccount)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FFF7E2B3") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrysAccount.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrysAccount getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrysAccount)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FFF7E2B3") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrysAccount.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrysAccount getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrysAccount)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FFF7E2B3"));
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrysAccount getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrysAccount)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FFF7E2B3"));
    }
}