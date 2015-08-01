package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RightManagerFactory
{
    private RightManagerFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IRightManager getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IRightManager)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B253C9E3") ,com.kingdee.eas.fdc.basedata.IRightManager.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IRightManager getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IRightManager)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B253C9E3") ,com.kingdee.eas.fdc.basedata.IRightManager.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IRightManager getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IRightManager)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B253C9E3"));
    }
    public static com.kingdee.eas.fdc.basedata.IRightManager getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IRightManager)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B253C9E3"));
    }
}