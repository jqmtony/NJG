package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AcctAccreditUserFactory
{
    private AcctAccreditUserFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditUser getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditUser)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("83710AAB") ,com.kingdee.eas.fdc.basedata.IAcctAccreditUser.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditUser getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditUser)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("83710AAB") ,com.kingdee.eas.fdc.basedata.IAcctAccreditUser.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditUser getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditUser)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("83710AAB"));
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditUser getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditUser)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("83710AAB"));
    }
}