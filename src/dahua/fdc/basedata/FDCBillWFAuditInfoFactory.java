package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCBillWFAuditInfoFactory
{
    private FDCBillWFAuditInfoFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBillWFAuditInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillWFAuditInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D4F99700") ,com.kingdee.eas.fdc.basedata.IFDCBillWFAuditInfo.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IFDCBillWFAuditInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillWFAuditInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D4F99700") ,com.kingdee.eas.fdc.basedata.IFDCBillWFAuditInfo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBillWFAuditInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillWFAuditInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D4F99700"));
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBillWFAuditInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillWFAuditInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D4F99700"));
    }
}