package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaxManagerFactory
{
    private TaxManagerFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ITaxManager getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITaxManager)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("50ED91F4") ,com.kingdee.eas.fdc.basedata.ITaxManager.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ITaxManager getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITaxManager)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("50ED91F4") ,com.kingdee.eas.fdc.basedata.ITaxManager.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ITaxManager getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITaxManager)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("50ED91F4"));
    }
    public static com.kingdee.eas.fdc.basedata.ITaxManager getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITaxManager)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("50ED91F4"));
    }
}