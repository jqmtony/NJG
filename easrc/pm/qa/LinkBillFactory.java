package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LinkBillFactory
{
    private LinkBillFactory()
    {
    }
    public static com.kingdee.eas.port.pm.qa.ILinkBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.ILinkBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4084C343") ,com.kingdee.eas.port.pm.qa.ILinkBill.class);
    }
    
    public static com.kingdee.eas.port.pm.qa.ILinkBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.ILinkBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4084C343") ,com.kingdee.eas.port.pm.qa.ILinkBill.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.qa.ILinkBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.ILinkBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4084C343"));
    }
    public static com.kingdee.eas.port.pm.qa.ILinkBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.ILinkBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4084C343"));
    }
}