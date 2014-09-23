package com.kingdee.eas.bpmdemo.webservers;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class getInfoFacadeFactory
{
    private getInfoFacadeFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2969D79D") ,com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade.class);
    }
    
    public static com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2969D79D") ,com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2969D79D"));
    }
    public static com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2969D79D"));
    }
}