package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeAuditBillSplitEntryFactory
{
    private ChangeAuditBillSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IChangeAuditBillSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditBillSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B837E9EF") ,com.kingdee.eas.fdc.contract.IChangeAuditBillSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IChangeAuditBillSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditBillSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B837E9EF") ,com.kingdee.eas.fdc.contract.IChangeAuditBillSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IChangeAuditBillSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditBillSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B837E9EF"));
    }
    public static com.kingdee.eas.fdc.contract.IChangeAuditBillSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditBillSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B837E9EF"));
    }
}