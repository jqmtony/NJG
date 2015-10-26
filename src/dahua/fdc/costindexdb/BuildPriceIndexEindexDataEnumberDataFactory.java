package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildPriceIndexEindexDataEnumberDataFactory
{
    private BuildPriceIndexEindexDataEnumberDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEnumberData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEnumberData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D6303DB0") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEnumberData.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEnumberData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEnumberData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D6303DB0") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEnumberData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEnumberData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEnumberData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D6303DB0"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEnumberData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEnumberData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D6303DB0"));
    }
}