package com.kingdee.eas.fdc.aimcost.costkf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class UsesFactory
{
    private UsesFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.IUses getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.IUses)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D1C5FF95") ,com.kingdee.eas.fdc.aimcost.costkf.IUses.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.costkf.IUses getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.IUses)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D1C5FF95") ,com.kingdee.eas.fdc.aimcost.costkf.IUses.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.IUses getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.IUses)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D1C5FF95"));
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.IUses getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.IUses)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D1C5FF95"));
    }
}