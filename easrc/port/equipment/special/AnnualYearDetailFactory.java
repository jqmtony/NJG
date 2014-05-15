package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AnnualYearDetailFactory
{
    private AnnualYearDetailFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearDetail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearDetail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("22366297") ,com.kingdee.eas.port.equipment.special.IAnnualYearDetail.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IAnnualYearDetail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearDetail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("22366297") ,com.kingdee.eas.port.equipment.special.IAnnualYearDetail.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearDetail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearDetail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("22366297"));
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearDetail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearDetail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("22366297"));
    }
}