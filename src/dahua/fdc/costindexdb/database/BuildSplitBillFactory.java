package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildSplitBillFactory
{
    private BuildSplitBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C672055A") ,com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBill.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C672055A") ,com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C672055A"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C672055A"));
    }
}