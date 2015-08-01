package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostAccountFacadeFactory
{
    private CostAccountFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2ACED1A8") ,com.kingdee.eas.fdc.basedata.ICostAccountFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICostAccountFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2ACED1A8") ,com.kingdee.eas.fdc.basedata.ICostAccountFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2ACED1A8"));
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2ACED1A8"));
    }
}