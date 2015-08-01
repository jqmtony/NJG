package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostAccountFixDataFacadeFactory
{
    private CostAccountFixDataFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountFixDataFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountFixDataFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5C70EA0B") ,com.kingdee.eas.fdc.basedata.ICostAccountFixDataFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICostAccountFixDataFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountFixDataFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5C70EA0B") ,com.kingdee.eas.fdc.basedata.ICostAccountFixDataFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountFixDataFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountFixDataFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5C70EA0B"));
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountFixDataFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountFixDataFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5C70EA0B"));
    }
}