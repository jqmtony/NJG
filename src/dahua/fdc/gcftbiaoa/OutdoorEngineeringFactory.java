package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OutdoorEngineeringFactory
{
    private OutdoorEngineeringFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineering getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineering)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F44B7C3E") ,com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineering.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineering getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineering)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F44B7C3E") ,com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineering.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineering getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineering)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F44B7C3E"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineering getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineering)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F44B7C3E"));
    }
}