package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EASLogTimeFactory
{
    private EASLogTimeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IEASLogTime getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IEASLogTime)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C54AD938") ,com.kingdee.eas.fdc.basedata.IEASLogTime.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IEASLogTime getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IEASLogTime)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C54AD938") ,com.kingdee.eas.fdc.basedata.IEASLogTime.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IEASLogTime getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IEASLogTime)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C54AD938"));
    }
    public static com.kingdee.eas.fdc.basedata.IEASLogTime getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IEASLogTime)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C54AD938"));
    }
}