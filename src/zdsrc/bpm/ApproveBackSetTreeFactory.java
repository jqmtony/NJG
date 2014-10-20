package com.kingdee.eas.bpm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ApproveBackSetTreeFactory
{
    private ApproveBackSetTreeFactory()
    {
    }
    public static com.kingdee.eas.bpm.IApproveBackSetTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSetTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D6D723AD") ,com.kingdee.eas.bpm.IApproveBackSetTree.class);
    }
    
    public static com.kingdee.eas.bpm.IApproveBackSetTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSetTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D6D723AD") ,com.kingdee.eas.bpm.IApproveBackSetTree.class, objectCtx);
    }
    public static com.kingdee.eas.bpm.IApproveBackSetTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSetTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D6D723AD"));
    }
    public static com.kingdee.eas.bpm.IApproveBackSetTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSetTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D6D723AD"));
    }
}