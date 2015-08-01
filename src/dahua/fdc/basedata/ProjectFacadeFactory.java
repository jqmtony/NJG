package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectFacadeFactory
{
    private ProjectFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IProjectFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7640B361") ,com.kingdee.eas.fdc.basedata.IProjectFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IProjectFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7640B361") ,com.kingdee.eas.fdc.basedata.IProjectFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IProjectFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7640B361"));
    }
    public static com.kingdee.eas.fdc.basedata.IProjectFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7640B361"));
    }
}