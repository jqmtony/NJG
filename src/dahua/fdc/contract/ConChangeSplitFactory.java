package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConChangeSplitFactory
{
    private ConChangeSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9134D170") ,com.kingdee.eas.fdc.contract.IConChangeSplit.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IConChangeSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9134D170") ,com.kingdee.eas.fdc.contract.IConChangeSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9134D170"));
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9134D170"));
    }
}