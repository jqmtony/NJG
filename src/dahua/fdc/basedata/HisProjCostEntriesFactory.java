package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HisProjCostEntriesFactory
{
    private HisProjCostEntriesFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IHisProjCostEntries getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjCostEntries)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("951100E6") ,com.kingdee.eas.fdc.basedata.IHisProjCostEntries.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IHisProjCostEntries getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjCostEntries)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("951100E6") ,com.kingdee.eas.fdc.basedata.IHisProjCostEntries.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IHisProjCostEntries getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjCostEntries)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("951100E6"));
    }
    public static com.kingdee.eas.fdc.basedata.IHisProjCostEntries getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjCostEntries)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("951100E6"));
    }
}