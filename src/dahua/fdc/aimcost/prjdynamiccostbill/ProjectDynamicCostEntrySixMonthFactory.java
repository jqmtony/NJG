package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectDynamicCostEntrySixMonthFactory
{
    private ProjectDynamicCostEntrySixMonthFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrySixMonth getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrySixMonth)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6A6B2F57") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrySixMonth.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrySixMonth getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrySixMonth)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6A6B2F57") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrySixMonth.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrySixMonth getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrySixMonth)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6A6B2F57"));
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrySixMonth getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCostEntrySixMonth)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6A6B2F57"));
    }
}