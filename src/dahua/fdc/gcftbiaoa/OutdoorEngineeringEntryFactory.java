package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OutdoorEngineeringEntryFactory
{
    private OutdoorEngineeringEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineeringEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineeringEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("094E92D4") ,com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineeringEntry.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineeringEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineeringEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("094E92D4") ,com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineeringEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineeringEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineeringEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("094E92D4"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineeringEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IOutdoorEngineeringEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("094E92D4"));
    }
}