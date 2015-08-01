package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CurProjCostEntriesFactory
{
    private CurProjCostEntriesFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjCostEntries getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjCostEntries)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A49C2078") ,com.kingdee.eas.fdc.basedata.ICurProjCostEntries.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICurProjCostEntries getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjCostEntries)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A49C2078") ,com.kingdee.eas.fdc.basedata.ICurProjCostEntries.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjCostEntries getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjCostEntries)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A49C2078"));
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjCostEntries getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjCostEntries)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A49C2078"));
    }
}