package com.kingdee.eas.fdc.aimcost.costkf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CQGSEntryFactory
{
    private CQGSEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.ICQGSEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICQGSEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0515C4AF") ,com.kingdee.eas.fdc.aimcost.costkf.ICQGSEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.costkf.ICQGSEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICQGSEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0515C4AF") ,com.kingdee.eas.fdc.aimcost.costkf.ICQGSEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.ICQGSEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICQGSEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0515C4AF"));
    }
    public static com.kingdee.eas.fdc.aimcost.costkf.ICQGSEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.ICQGSEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0515C4AF"));
    }
}