package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildPriceIndexEindexDataFactory
{
    private BuildPriceIndexEindexDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8D2BA1E8") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexData.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8D2BA1E8") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8D2BA1E8"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8D2BA1E8"));
    }
}