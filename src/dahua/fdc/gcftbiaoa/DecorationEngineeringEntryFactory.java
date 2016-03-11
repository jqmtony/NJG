package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DecorationEngineeringEntryFactory
{
    private DecorationEngineeringEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineeringEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineeringEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("614539B6") ,com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineeringEntry.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineeringEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineeringEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("614539B6") ,com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineeringEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineeringEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineeringEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("614539B6"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineeringEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineeringEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("614539B6"));
    }
}