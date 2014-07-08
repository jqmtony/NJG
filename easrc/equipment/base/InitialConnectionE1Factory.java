package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InitialConnectionE1Factory
{
    private InitialConnectionE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IInitialConnectionE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInitialConnectionE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("082ACFCC") ,com.kingdee.eas.port.equipment.base.IInitialConnectionE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IInitialConnectionE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInitialConnectionE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("082ACFCC") ,com.kingdee.eas.port.equipment.base.IInitialConnectionE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IInitialConnectionE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInitialConnectionE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("082ACFCC"));
    }
    public static com.kingdee.eas.port.equipment.base.IInitialConnectionE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInitialConnectionE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("082ACFCC"));
    }
}