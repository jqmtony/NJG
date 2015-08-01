package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AcctAccreditSchemeFactory
{
    private AcctAccreditSchemeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditScheme getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditScheme)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("670F1C85") ,com.kingdee.eas.fdc.basedata.IAcctAccreditScheme.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditScheme getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditScheme)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("670F1C85") ,com.kingdee.eas.fdc.basedata.IAcctAccreditScheme.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditScheme getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditScheme)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("670F1C85"));
    }
    public static com.kingdee.eas.fdc.basedata.IAcctAccreditScheme getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAcctAccreditScheme)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("670F1C85"));
    }
}