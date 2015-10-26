package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildPriceIndexEindexDataEtextDataFactory
{
    private BuildPriceIndexEindexDataEtextDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEtextData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEtextData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("608038D4") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEtextData.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEtextData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEtextData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("608038D4") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEtextData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEtextData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEtextData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("608038D4"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEtextData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEtextData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("608038D4"));
    }
}