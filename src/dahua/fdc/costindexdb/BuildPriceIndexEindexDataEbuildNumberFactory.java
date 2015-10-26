package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildPriceIndexEindexDataEbuildNumberFactory
{
    private BuildPriceIndexEindexDataEbuildNumberFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbuildNumber getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbuildNumber)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("433C1BDA") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbuildNumber.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbuildNumber getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbuildNumber)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("433C1BDA") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbuildNumber.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbuildNumber getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbuildNumber)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("433C1BDA"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbuildNumber getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbuildNumber)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("433C1BDA"));
    }
}