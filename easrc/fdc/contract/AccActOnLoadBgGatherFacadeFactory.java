package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AccActOnLoadBgGatherFacadeFactory
{
    private AccActOnLoadBgGatherFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IAccActOnLoadBgGatherFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IAccActOnLoadBgGatherFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6750168B") ,com.kingdee.eas.fdc.contract.IAccActOnLoadBgGatherFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IAccActOnLoadBgGatherFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IAccActOnLoadBgGatherFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6750168B") ,com.kingdee.eas.fdc.contract.IAccActOnLoadBgGatherFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IAccActOnLoadBgGatherFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IAccActOnLoadBgGatherFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6750168B"));
    }
    public static com.kingdee.eas.fdc.contract.IAccActOnLoadBgGatherFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IAccActOnLoadBgGatherFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6750168B"));
    }
}