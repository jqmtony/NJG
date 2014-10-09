package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractsettlementFactory
{
    private ContractsettlementFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlement getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlement)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0173D879") ,com.kingdee.eas.bpmdemo.IContractsettlement.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IContractsettlement getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlement)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0173D879") ,com.kingdee.eas.bpmdemo.IContractsettlement.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlement getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlement)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0173D879"));
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlement getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlement)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0173D879"));
    }
}