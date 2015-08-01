package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectTargetBillFactory
{
    private ProjectTargetBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BB67A7DD") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetBill.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BB67A7DD") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BB67A7DD"));
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BB67A7DD"));
    }
}