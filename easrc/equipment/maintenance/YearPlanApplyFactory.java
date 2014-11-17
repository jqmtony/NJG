package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class YearPlanApplyFactory
{
    private YearPlanApplyFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IYearPlanApply getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IYearPlanApply)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("22C36DB8") ,com.kingdee.eas.port.equipment.maintenance.IYearPlanApply.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IYearPlanApply getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IYearPlanApply)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("22C36DB8") ,com.kingdee.eas.port.equipment.maintenance.IYearPlanApply.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IYearPlanApply getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IYearPlanApply)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("22C36DB8"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IYearPlanApply getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IYearPlanApply)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("22C36DB8"));
    }
}