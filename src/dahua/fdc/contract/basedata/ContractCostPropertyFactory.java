package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractCostPropertyFactory
{
    private ContractCostPropertyFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IContractCostProperty getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractCostProperty)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F4F758E6") ,com.kingdee.eas.fdc.basedata.IContractCostProperty.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IContractCostProperty getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractCostProperty)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F4F758E6") ,com.kingdee.eas.fdc.basedata.IContractCostProperty.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IContractCostProperty getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractCostProperty)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F4F758E6"));
    }
    public static com.kingdee.eas.fdc.basedata.IContractCostProperty getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractCostProperty)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F4F758E6"));
    }
}