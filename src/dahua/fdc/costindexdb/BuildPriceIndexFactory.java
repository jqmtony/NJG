package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildPriceIndexFactory
{
    private BuildPriceIndexFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4FFB9A31") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndex.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4FFB9A31") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndex.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4FFB9A31"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4FFB9A31"));
    }
}