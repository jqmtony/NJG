package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IncomeAccountFacadeFactory
{
    private IncomeAccountFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IIncomeAccountFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccountFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("804F29CC") ,com.kingdee.eas.fdc.basedata.IIncomeAccountFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IIncomeAccountFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccountFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("804F29CC") ,com.kingdee.eas.fdc.basedata.IIncomeAccountFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IIncomeAccountFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccountFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("804F29CC"));
    }
    public static com.kingdee.eas.fdc.basedata.IIncomeAccountFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccountFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("804F29CC"));
    }
}