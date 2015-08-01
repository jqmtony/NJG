package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractChargeTypeFactory
{
    private ContractChargeTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IContractChargeType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractChargeType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4A53E932") ,com.kingdee.eas.fdc.basedata.IContractChargeType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IContractChargeType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractChargeType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4A53E932") ,com.kingdee.eas.fdc.basedata.IContractChargeType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IContractChargeType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractChargeType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4A53E932"));
    }
    public static com.kingdee.eas.fdc.basedata.IContractChargeType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractChargeType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4A53E932"));
    }
}