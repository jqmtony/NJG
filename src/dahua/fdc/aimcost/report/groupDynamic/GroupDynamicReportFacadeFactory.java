package com.kingdee.eas.fdc.aimcost.report.groupDynamic;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GroupDynamicReportFacadeFactory
{
    private GroupDynamicReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.report.groupDynamic.IGroupDynamicReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.groupDynamic.IGroupDynamicReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BD2C6159") ,com.kingdee.eas.fdc.aimcost.report.groupDynamic.IGroupDynamicReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.report.groupDynamic.IGroupDynamicReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.groupDynamic.IGroupDynamicReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BD2C6159") ,com.kingdee.eas.fdc.aimcost.report.groupDynamic.IGroupDynamicReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.report.groupDynamic.IGroupDynamicReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.groupDynamic.IGroupDynamicReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BD2C6159"));
    }
    public static com.kingdee.eas.fdc.aimcost.report.groupDynamic.IGroupDynamicReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.groupDynamic.IGroupDynamicReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BD2C6159"));
    }
}