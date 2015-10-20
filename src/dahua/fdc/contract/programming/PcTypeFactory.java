package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PcTypeFactory
{
    private PcTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IPcType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPcType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B37DC0FD") ,com.kingdee.eas.fdc.contract.programming.IPcType.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IPcType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPcType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B37DC0FD") ,com.kingdee.eas.fdc.contract.programming.IPcType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IPcType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPcType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B37DC0FD"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IPcType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPcType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B37DC0FD"));
    }
}