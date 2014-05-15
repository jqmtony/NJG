package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JudgesTreeFactory
{
    private JudgesTreeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IJudgesTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IJudgesTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F7BA10FB") ,com.kingdee.eas.port.pm.base.IJudgesTree.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IJudgesTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IJudgesTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F7BA10FB") ,com.kingdee.eas.port.pm.base.IJudgesTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IJudgesTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IJudgesTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F7BA10FB"));
    }
    public static com.kingdee.eas.port.pm.base.IJudgesTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IJudgesTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F7BA10FB"));
    }
}