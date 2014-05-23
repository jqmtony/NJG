package com.kingdee.eas.port.pm.contract.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractTypeFactory
{
    private ContractTypeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("56F37ED9") ,com.kingdee.eas.port.pm.contract.basedata.IContractType.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.basedata.IContractType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("56F37ED9") ,com.kingdee.eas.port.pm.contract.basedata.IContractType.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("56F37ED9"));
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("56F37ED9"));
    }
}