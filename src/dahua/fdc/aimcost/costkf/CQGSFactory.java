package com.kingdee.eas.fdc.aimcost.costkf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CQGSFactory
{
    private CQGSFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.ICQGS getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICQGS)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D1BD4D83") ,com.kingdee.eas.fdc.aimcost.costkf.ICQGS.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.costkf.ICQGS getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICQGS)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D1BD4D83") ,com.kingdee.eas.fdc.aimcost.costkf.ICQGS.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.ICQGS getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICQGS)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D1BD4D83"));
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.ICQGS getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICQGS)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D1BD4D83"));
    }
}