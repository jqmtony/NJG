package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractProgrammingEntryFactory
{
    private ContractProgrammingEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IContractProgrammingEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IContractProgrammingEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E3DF8B58") ,com.kingdee.eas.port.pm.invest.investplan.IContractProgrammingEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IContractProgrammingEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IContractProgrammingEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E3DF8B58") ,com.kingdee.eas.port.pm.invest.investplan.IContractProgrammingEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IContractProgrammingEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IContractProgrammingEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E3DF8B58"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IContractProgrammingEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IContractProgrammingEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E3DF8B58"));
    }
}