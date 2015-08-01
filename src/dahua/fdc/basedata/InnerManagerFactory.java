package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InnerManagerFactory
{
    private InnerManagerFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IInnerManager getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInnerManager)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("76FEE869") ,com.kingdee.eas.fdc.basedata.IInnerManager.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IInnerManager getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInnerManager)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("76FEE869") ,com.kingdee.eas.fdc.basedata.IInnerManager.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IInnerManager getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInnerManager)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("76FEE869"));
    }
    public static com.kingdee.eas.fdc.basedata.IInnerManager getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInnerManager)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("76FEE869"));
    }
}