package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BeforeAccountViewFactory
{
    private BeforeAccountViewFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IBeforeAccountView getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBeforeAccountView)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("02DED8C1") ,com.kingdee.eas.fdc.basedata.IBeforeAccountView.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IBeforeAccountView getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBeforeAccountView)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("02DED8C1") ,com.kingdee.eas.fdc.basedata.IBeforeAccountView.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IBeforeAccountView getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBeforeAccountView)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("02DED8C1"));
    }
    public static com.kingdee.eas.fdc.basedata.IBeforeAccountView getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBeforeAccountView)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("02DED8C1"));
    }
}