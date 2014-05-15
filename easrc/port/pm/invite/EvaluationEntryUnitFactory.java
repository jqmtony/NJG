package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationEntryUnitFactory
{
    private EvaluationEntryUnitFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9A132151") ,com.kingdee.eas.port.pm.invite.IEvaluationEntryUnit.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9A132151") ,com.kingdee.eas.port.pm.invite.IEvaluationEntryUnit.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9A132151"));
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9A132151"));
    }
}