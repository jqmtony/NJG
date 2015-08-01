package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasureIndexFactory
{
    private MeasureIndexFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IMeasureIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IMeasureIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EF787A06") ,com.kingdee.eas.fdc.basedata.IMeasureIndex.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IMeasureIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IMeasureIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EF787A06") ,com.kingdee.eas.fdc.basedata.IMeasureIndex.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IMeasureIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IMeasureIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EF787A06"));
    }
    public static com.kingdee.eas.fdc.basedata.IMeasureIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IMeasureIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EF787A06"));
    }
}