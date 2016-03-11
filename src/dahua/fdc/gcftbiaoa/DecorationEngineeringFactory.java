package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DecorationEngineeringFactory
{
    private DecorationEngineeringFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineering getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineering)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D5DC901C") ,com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineering.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineering getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineering)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D5DC901C") ,com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineering.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineering getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineering)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D5DC901C"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineering getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IDecorationEngineering)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D5DC901C"));
    }
}