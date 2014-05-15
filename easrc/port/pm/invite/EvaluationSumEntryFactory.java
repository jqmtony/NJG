package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationSumEntryFactory
{
    private EvaluationSumEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationSumEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationSumEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("50F2494C") ,com.kingdee.eas.port.pm.invite.IEvaluationSumEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IEvaluationSumEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationSumEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("50F2494C") ,com.kingdee.eas.port.pm.invite.IEvaluationSumEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationSumEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationSumEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("50F2494C"));
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationSumEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationSumEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("50F2494C"));
    }
}