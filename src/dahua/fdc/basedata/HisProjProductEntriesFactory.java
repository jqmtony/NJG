package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HisProjProductEntriesFactory
{
    private HisProjProductEntriesFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IHisProjProductEntries getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjProductEntries)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("082AE0DE") ,com.kingdee.eas.fdc.basedata.IHisProjProductEntries.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IHisProjProductEntries getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjProductEntries)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("082AE0DE") ,com.kingdee.eas.fdc.basedata.IHisProjProductEntries.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IHisProjProductEntries getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjProductEntries)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("082AE0DE"));
    }
    public static com.kingdee.eas.fdc.basedata.IHisProjProductEntries getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjProductEntries)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("082AE0DE"));
    }
}