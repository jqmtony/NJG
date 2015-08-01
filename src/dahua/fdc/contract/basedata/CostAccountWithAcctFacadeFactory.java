package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostAccountWithAcctFacadeFactory
{
    private CostAccountWithAcctFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountWithAcctFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountWithAcctFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C4029B01") ,com.kingdee.eas.fdc.basedata.ICostAccountWithAcctFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICostAccountWithAcctFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountWithAcctFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C4029B01") ,com.kingdee.eas.fdc.basedata.ICostAccountWithAcctFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountWithAcctFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountWithAcctFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C4029B01"));
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountWithAcctFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountWithAcctFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C4029B01"));
    }
}