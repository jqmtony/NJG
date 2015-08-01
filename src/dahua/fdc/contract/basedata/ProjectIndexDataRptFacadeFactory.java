package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectIndexDataRptFacadeFactory
{
    private ProjectIndexDataRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IProjectIndexDataRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexDataRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("00B6323B") ,com.kingdee.eas.fdc.basedata.IProjectIndexDataRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IProjectIndexDataRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexDataRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("00B6323B") ,com.kingdee.eas.fdc.basedata.IProjectIndexDataRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IProjectIndexDataRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexDataRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("00B6323B"));
    }
    public static com.kingdee.eas.fdc.basedata.IProjectIndexDataRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexDataRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("00B6323B"));
    }
}