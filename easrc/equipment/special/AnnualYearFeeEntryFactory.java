package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AnnualYearFeeEntryFactory
{
    private AnnualYearFeeEntryFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearFeeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearFeeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("22C9A152") ,com.kingdee.eas.port.equipment.special.IAnnualYearFeeEntry.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IAnnualYearFeeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearFeeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("22C9A152") ,com.kingdee.eas.port.equipment.special.IAnnualYearFeeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearFeeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearFeeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("22C9A152"));
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearFeeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearFeeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("22C9A152"));
    }
}