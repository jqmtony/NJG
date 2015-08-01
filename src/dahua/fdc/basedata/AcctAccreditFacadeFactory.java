package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AcctAccreditFacadeFactory
{
    private AcctAccreditFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("50C1977A") ,com.kingdee.eas.fdc.basedata.IAcctAccreditFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("50C1977A") ,com.kingdee.eas.fdc.basedata.IAcctAccreditFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("50C1977A"));
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("50C1977A"));
    }
}