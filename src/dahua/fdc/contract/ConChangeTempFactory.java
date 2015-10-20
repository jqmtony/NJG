package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConChangeTempFactory
{
    private ConChangeTempFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IConChangeTemp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeTemp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("452A8C6B") ,com.kingdee.eas.fdc.contract.IConChangeTemp.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IConChangeTemp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeTemp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("452A8C6B") ,com.kingdee.eas.fdc.contract.IConChangeTemp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IConChangeTemp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeTemp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("452A8C6B"));
    }
    public static com.kingdee.eas.fdc.contract.IConChangeTemp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeTemp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("452A8C6B"));
    }
}