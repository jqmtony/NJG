package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AnnualYearPlanEntryFactory
{
    private AnnualYearPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("485866C3") ,com.kingdee.eas.port.equipment.special.IAnnualYearPlanEntry.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IAnnualYearPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("485866C3") ,com.kingdee.eas.port.equipment.special.IAnnualYearPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("485866C3"));
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("485866C3"));
    }
}