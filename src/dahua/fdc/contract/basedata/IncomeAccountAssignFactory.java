package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IncomeAccountAssignFactory
{
    private IncomeAccountAssignFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IIncomeAccountAssign getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccountAssign)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("78CBFA21") ,com.kingdee.eas.fdc.basedata.IIncomeAccountAssign.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IIncomeAccountAssign getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccountAssign)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("78CBFA21") ,com.kingdee.eas.fdc.basedata.IIncomeAccountAssign.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IIncomeAccountAssign getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccountAssign)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("78CBFA21"));
    }
    public static com.kingdee.eas.fdc.basedata.IIncomeAccountAssign getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIncomeAccountAssign)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("78CBFA21"));
    }
}