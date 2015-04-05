package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProProgressReportE1Factory
{
    private ProProgressReportE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProProgressReportE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProProgressReportE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("87154BDD") ,com.kingdee.eas.port.pm.invest.IProProgressReportE1.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProProgressReportE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProProgressReportE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("87154BDD") ,com.kingdee.eas.port.pm.invest.IProProgressReportE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProProgressReportE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProProgressReportE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("87154BDD"));
    }
    public static com.kingdee.eas.port.pm.invest.IProProgressReportE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProProgressReportE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("87154BDD"));
    }
}