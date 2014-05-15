package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AnnualYearFeeFactory
{
    private AnnualYearFeeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearFee getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearFee)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("29A4D700") ,com.kingdee.eas.port.equipment.special.IAnnualYearFee.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IAnnualYearFee getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearFee)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("29A4D700") ,com.kingdee.eas.port.equipment.special.IAnnualYearFee.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearFee getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearFee)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("29A4D700"));
    }
    public static com.kingdee.eas.port.equipment.special.IAnnualYearFee getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IAnnualYearFee)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("29A4D700"));
    }
}