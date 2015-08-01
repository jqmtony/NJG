package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JobTypeFactory
{
    private JobTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IJobType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IJobType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("78E70D65") ,com.kingdee.eas.fdc.basedata.IJobType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IJobType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IJobType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("78E70D65") ,com.kingdee.eas.fdc.basedata.IJobType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IJobType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IJobType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("78E70D65"));
    }
    public static com.kingdee.eas.fdc.basedata.IJobType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IJobType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("78E70D65"));
    }
}