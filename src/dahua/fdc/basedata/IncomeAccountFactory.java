package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IncomeAccountFactory
{
    private IncomeAccountFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IIncomeAccount getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccount)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B6424292") ,com.kingdee.eas.fdc.basedata.IIncomeAccount.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IIncomeAccount getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccount)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B6424292") ,com.kingdee.eas.fdc.basedata.IIncomeAccount.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IIncomeAccount getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccount)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B6424292"));
    }
    public static com.kingdee.eas.fdc.basedata.IIncomeAccount getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccount)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B6424292"));
    }
}