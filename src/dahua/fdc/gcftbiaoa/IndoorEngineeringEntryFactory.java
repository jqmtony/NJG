package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IndoorEngineeringEntryFactory
{
    private IndoorEngineeringEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineeringEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineeringEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AFCCECD9") ,com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineeringEntry.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineeringEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineeringEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AFCCECD9") ,com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineeringEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineeringEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineeringEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AFCCECD9"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineeringEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorEngineeringEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AFCCECD9"));
    }
}