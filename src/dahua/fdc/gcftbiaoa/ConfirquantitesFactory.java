package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConfirquantitesFactory
{
    private ConfirquantitesFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IConfirquantites getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IConfirquantites)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("77AF44C8") ,com.kingdee.eas.fdc.gcftbiaoa.IConfirquantites.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IConfirquantites getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IConfirquantites)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("77AF44C8") ,com.kingdee.eas.fdc.gcftbiaoa.IConfirquantites.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IConfirquantites getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IConfirquantites)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("77AF44C8"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IConfirquantites getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IConfirquantites)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("77AF44C8"));
    }
}