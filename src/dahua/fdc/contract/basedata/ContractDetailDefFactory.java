package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractDetailDefFactory
{
    private ContractDetailDefFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IContractDetailDef getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractDetailDef)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("ED295450") ,com.kingdee.eas.fdc.basedata.IContractDetailDef.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IContractDetailDef getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractDetailDef)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("ED295450") ,com.kingdee.eas.fdc.basedata.IContractDetailDef.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IContractDetailDef getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractDetailDef)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("ED295450"));
    }
    public static com.kingdee.eas.fdc.basedata.IContractDetailDef getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractDetailDef)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("ED295450"));
    }
}