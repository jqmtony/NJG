package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractCodingTypeFactory
{
    private ContractCodingTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IContractCodingType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractCodingType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BFCC7EE8") ,com.kingdee.eas.fdc.basedata.IContractCodingType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IContractCodingType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractCodingType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BFCC7EE8") ,com.kingdee.eas.fdc.basedata.IContractCodingType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IContractCodingType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractCodingType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BFCC7EE8"));
    }
    public static com.kingdee.eas.fdc.basedata.IContractCodingType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractCodingType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BFCC7EE8"));
    }
}