package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractProgrammingFactory
{
    private ContractProgrammingFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IContractProgramming getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IContractProgramming)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("565A413A") ,com.kingdee.eas.port.pm.invest.investplan.IContractProgramming.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IContractProgramming getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IContractProgramming)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("565A413A") ,com.kingdee.eas.port.pm.invest.investplan.IContractProgramming.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IContractProgramming getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IContractProgramming)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("565A413A"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IContractProgramming getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IContractProgramming)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("565A413A"));
    }
}