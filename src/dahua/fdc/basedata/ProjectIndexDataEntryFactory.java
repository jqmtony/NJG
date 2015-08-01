package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectIndexDataEntryFactory
{
    private ProjectIndexDataEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IProjectIndexDataEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexDataEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3DCF312B") ,com.kingdee.eas.fdc.basedata.IProjectIndexDataEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IProjectIndexDataEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexDataEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3DCF312B") ,com.kingdee.eas.fdc.basedata.IProjectIndexDataEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IProjectIndexDataEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexDataEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3DCF312B"));
    }
    public static com.kingdee.eas.fdc.basedata.IProjectIndexDataEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexDataEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3DCF312B"));
    }
}