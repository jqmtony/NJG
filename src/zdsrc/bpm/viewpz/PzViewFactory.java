package com.kingdee.eas.bpm.viewpz;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PzViewFactory
{
    private PzViewFactory()
    {
    }
    public static com.kingdee.eas.bpm.viewpz.IPzView getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpm.viewpz.IPzView)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A174A7C7") ,com.kingdee.eas.bpm.viewpz.IPzView.class);
    }
    
    public static com.kingdee.eas.bpm.viewpz.IPzView getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpm.viewpz.IPzView)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A174A7C7") ,com.kingdee.eas.bpm.viewpz.IPzView.class, objectCtx);
    }
    public static com.kingdee.eas.bpm.viewpz.IPzView getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpm.viewpz.IPzView)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A174A7C7"));
    }
    public static com.kingdee.eas.bpm.viewpz.IPzView getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpm.viewpz.IPzView)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A174A7C7"));
    }
}