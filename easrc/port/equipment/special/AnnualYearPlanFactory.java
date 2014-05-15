package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AnnualYearPlanFactory
{
    private AnnualYearPlanFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0AFAAEEF") ,com.kingdee.eas.port.equipment.special.IAnnualYearPlan.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IAnnualYearPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0AFAAEEF") ,com.kingdee.eas.port.equipment.special.IAnnualYearPlan.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0AFAAEEF"));
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0AFAAEEF"));
    }
}