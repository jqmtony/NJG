package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IndoorEngineeringFactory
{
    private IndoorEngineeringFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineering getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineering)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("38843319") ,com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineering.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineering getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineering)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("38843319") ,com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineering.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineering getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineering)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("38843319"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineering getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineering)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("38843319"));
    }
}