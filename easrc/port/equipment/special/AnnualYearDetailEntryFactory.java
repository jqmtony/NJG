package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AnnualYearDetailEntryFactory
{
    private AnnualYearDetailEntryFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1022BC1B") ,com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1022BC1B") ,com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1022BC1B"));
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1022BC1B"));
    }
}