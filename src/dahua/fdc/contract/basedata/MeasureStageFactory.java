package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasureStageFactory
{
    private MeasureStageFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IMeasureStage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IMeasureStage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F0081432") ,com.kingdee.eas.fdc.basedata.IMeasureStage.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IMeasureStage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IMeasureStage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F0081432") ,com.kingdee.eas.fdc.basedata.IMeasureStage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IMeasureStage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IMeasureStage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F0081432"));
    }
    public static com.kingdee.eas.fdc.basedata.IMeasureStage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IMeasureStage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F0081432"));
    }
}