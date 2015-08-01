package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWOTCodingTypeFactory
{
    private ContractWOTCodingTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IContractWOTCodingType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractWOTCodingType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9173D3DC") ,com.kingdee.eas.fdc.basedata.IContractWOTCodingType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IContractWOTCodingType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractWOTCodingType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9173D3DC") ,com.kingdee.eas.fdc.basedata.IContractWOTCodingType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IContractWOTCodingType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractWOTCodingType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9173D3DC"));
    }
    public static com.kingdee.eas.fdc.basedata.IContractWOTCodingType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractWOTCodingType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9173D3DC"));
    }
}