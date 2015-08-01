package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectTargetShowBillFactory
{
    private ProjectTargetShowBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A44D557A") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowBill.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A44D557A") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A44D557A"));
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A44D557A"));
    }
}