package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildPriceIndexEindexDataEdateDataFactory
{
    private BuildPriceIndexEindexDataEdateDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEdateData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEdateData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("072DBAD5") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEdateData.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEdateData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEdateData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("072DBAD5") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEdateData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEdateData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEdateData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("072DBAD5"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEdateData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEdateData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("072DBAD5"));
    }
}