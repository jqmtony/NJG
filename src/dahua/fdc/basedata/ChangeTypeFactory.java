package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeTypeFactory
{
    private ChangeTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IChangeType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IChangeType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AC174F7C") ,com.kingdee.eas.fdc.basedata.IChangeType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IChangeType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IChangeType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AC174F7C") ,com.kingdee.eas.fdc.basedata.IChangeType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IChangeType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IChangeType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AC174F7C"));
    }
    public static com.kingdee.eas.fdc.basedata.IChangeType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IChangeType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AC174F7C"));
    }
}