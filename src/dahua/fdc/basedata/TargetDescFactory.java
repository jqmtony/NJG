package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TargetDescFactory
{
    private TargetDescFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ITargetDesc getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetDesc)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("48234434") ,com.kingdee.eas.fdc.basedata.ITargetDesc.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ITargetDesc getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetDesc)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("48234434") ,com.kingdee.eas.fdc.basedata.ITargetDesc.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ITargetDesc getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetDesc)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("48234434"));
    }
    public static com.kingdee.eas.fdc.basedata.ITargetDesc getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetDesc)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("48234434"));
    }
}