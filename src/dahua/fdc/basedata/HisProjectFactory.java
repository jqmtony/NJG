package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HisProjectFactory
{
    private HisProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IHisProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("34011799") ,com.kingdee.eas.fdc.basedata.IHisProject.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IHisProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("34011799") ,com.kingdee.eas.fdc.basedata.IHisProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IHisProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("34011799"));
    }
    public static com.kingdee.eas.fdc.basedata.IHisProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("34011799"));
    }
}