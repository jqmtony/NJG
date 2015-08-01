package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BITargetFactory
{
    private BITargetFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IBITarget getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBITarget)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7394758A") ,com.kingdee.eas.fdc.basedata.IBITarget.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IBITarget getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBITarget)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7394758A") ,com.kingdee.eas.fdc.basedata.IBITarget.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IBITarget getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBITarget)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7394758A"));
    }
    public static com.kingdee.eas.fdc.basedata.IBITarget getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBITarget)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7394758A"));
    }
}