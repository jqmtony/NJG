package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractSourceFactory
{
    private ContractSourceFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IContractSource getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractSource)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9AB1D73F") ,com.kingdee.eas.fdc.basedata.IContractSource.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IContractSource getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractSource)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9AB1D73F") ,com.kingdee.eas.fdc.basedata.IContractSource.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IContractSource getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractSource)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9AB1D73F"));
    }
    public static com.kingdee.eas.fdc.basedata.IContractSource getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractSource)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9AB1D73F"));
    }
}