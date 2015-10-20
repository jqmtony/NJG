package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeAuditEntryFactory
{
    private ChangeAuditEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IChangeAuditEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("92476D62") ,com.kingdee.eas.fdc.contract.IChangeAuditEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IChangeAuditEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("92476D62") ,com.kingdee.eas.fdc.contract.IChangeAuditEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IChangeAuditEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("92476D62"));
    }
    public static com.kingdee.eas.fdc.contract.IChangeAuditEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeAuditEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("92476D62"));
    }
}