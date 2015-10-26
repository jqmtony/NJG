package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildPriceIndexEindexDataEbaseUnitFactory
{
    private BuildPriceIndexEindexDataEbaseUnitFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbaseUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbaseUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("35BE7252") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbaseUnit.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbaseUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbaseUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("35BE7252") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbaseUnit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbaseUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbaseUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("35BE7252"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbaseUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEindexDataEbaseUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("35BE7252"));
    }
}