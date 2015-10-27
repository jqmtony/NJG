package com.kingdee.eas.fdc.aimcost.costkf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CqgsBaseFactory
{
    private CqgsBaseFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.ICqgsBase getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICqgsBase)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C4C9BB14") ,com.kingdee.eas.fdc.aimcost.costkf.ICqgsBase.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.costkf.ICqgsBase getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICqgsBase)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C4C9BB14") ,com.kingdee.eas.fdc.aimcost.costkf.ICqgsBase.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.ICqgsBase getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICqgsBase)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C4C9BB14"));
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.ICqgsBase getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICqgsBase)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C4C9BB14"));
    }
}