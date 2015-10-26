package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildPriceIndexEindexDataEproductTypeFactory
{
    private BuildPriceIndexEindexDataEproductTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEproductType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEproductType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6C79594C") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEproductType.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEproductType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEproductType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6C79594C") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEproductType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEproductType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEproductType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6C79594C"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEproductType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEproductType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6C79594C"));
    }
}