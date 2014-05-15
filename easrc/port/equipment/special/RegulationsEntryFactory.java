package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RegulationsEntryFactory
{
    private RegulationsEntryFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IRegulationsEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IRegulationsEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F3E3E053") ,com.kingdee.eas.port.equipment.special.IRegulationsEntry.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IRegulationsEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IRegulationsEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F3E3E053") ,com.kingdee.eas.port.equipment.special.IRegulationsEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IRegulationsEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IRegulationsEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F3E3E053"));
    }
    public static com.kingdee.eas.port.equipment.special.IRegulationsEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IRegulationsEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F3E3E053"));
    }
}