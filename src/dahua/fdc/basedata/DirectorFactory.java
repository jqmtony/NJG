package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DirectorFactory
{
    private DirectorFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IDirector getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDirector)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1381587E") ,com.kingdee.eas.fdc.basedata.IDirector.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IDirector getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDirector)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1381587E") ,com.kingdee.eas.fdc.basedata.IDirector.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IDirector getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDirector)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1381587E"));
    }
    public static com.kingdee.eas.fdc.basedata.IDirector getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDirector)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1381587E"));
    }
}