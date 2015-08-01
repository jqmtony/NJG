package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AcctAccreditAcctsFactory
{
    private AcctAccreditAcctsFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditAccts getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditAccts)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E98F27E0") ,com.kingdee.eas.fdc.basedata.IAcctAccreditAccts.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditAccts getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditAccts)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E98F27E0") ,com.kingdee.eas.fdc.basedata.IAcctAccreditAccts.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditAccts getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditAccts)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E98F27E0"));
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditAccts getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditAccts)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E98F27E0"));
    }
}