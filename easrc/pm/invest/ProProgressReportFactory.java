package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProProgressReportFactory
{
    private ProProgressReportFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProProgressReport getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProProgressReport)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("677FE391") ,com.kingdee.eas.port.pm.invest.IProProgressReport.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProProgressReport getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProProgressReport)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("677FE391") ,com.kingdee.eas.port.pm.invest.IProProgressReport.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProProgressReport getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProProgressReport)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("677FE391"));
    }
    public static com.kingdee.eas.port.pm.invest.IProProgressReport getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProProgressReport)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("677FE391"));
    }
}