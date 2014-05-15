package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RegulationsFactory
{
    private RegulationsFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IRegulations getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IRegulations)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("126D7B5F") ,com.kingdee.eas.port.equipment.special.IRegulations.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IRegulations getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IRegulations)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("126D7B5F") ,com.kingdee.eas.port.equipment.special.IRegulations.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IRegulations getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IRegulations)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("126D7B5F"));
    }
    public static com.kingdee.eas.port.equipment.special.IRegulations getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IRegulations)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("126D7B5F"));
    }
}