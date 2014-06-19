package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AccActOnLoadBgFacadeFactory
{
    private AccActOnLoadBgFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IAccActOnLoadBgFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IAccActOnLoadBgFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F6EABD30") ,com.kingdee.eas.fdc.contract.IAccActOnLoadBgFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IAccActOnLoadBgFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IAccActOnLoadBgFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F6EABD30") ,com.kingdee.eas.fdc.contract.IAccActOnLoadBgFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IAccActOnLoadBgFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IAccActOnLoadBgFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F6EABD30"));
    }
    public static com.kingdee.eas.fdc.contract.IAccActOnLoadBgFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IAccActOnLoadBgFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F6EABD30"));
    }
}