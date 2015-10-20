package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettlementCostSplitFactory
{
    private SettlementCostSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ISettlementCostSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettlementCostSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BE73CE09") ,com.kingdee.eas.fdc.contract.ISettlementCostSplit.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ISettlementCostSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettlementCostSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BE73CE09") ,com.kingdee.eas.fdc.contract.ISettlementCostSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ISettlementCostSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettlementCostSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BE73CE09"));
    }
    public static com.kingdee.eas.fdc.contract.ISettlementCostSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettlementCostSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BE73CE09"));
    }
}