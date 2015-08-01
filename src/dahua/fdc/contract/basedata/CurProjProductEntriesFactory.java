package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CurProjProductEntriesFactory
{
    private CurProjProductEntriesFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjProductEntries getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjProductEntries)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D719C00C") ,com.kingdee.eas.fdc.basedata.ICurProjProductEntries.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICurProjProductEntries getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjProductEntries)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D719C00C") ,com.kingdee.eas.fdc.basedata.ICurProjProductEntries.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjProductEntries getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjProductEntries)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D719C00C"));
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjProductEntries getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjProductEntries)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D719C00C"));
    }
}