package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectTargetValueBillFactory
{
    private ProjectTargetValueBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2AC17B02") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueBill.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2AC17B02") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2AC17B02"));
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2AC17B02"));
    }
}