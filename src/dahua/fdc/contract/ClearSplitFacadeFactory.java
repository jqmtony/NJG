package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ClearSplitFacadeFactory
{
    private ClearSplitFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IClearSplitFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IClearSplitFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E4771802") ,com.kingdee.eas.fdc.contract.IClearSplitFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IClearSplitFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IClearSplitFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E4771802") ,com.kingdee.eas.fdc.contract.IClearSplitFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IClearSplitFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IClearSplitFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E4771802"));
    }
    public static com.kingdee.eas.fdc.contract.IClearSplitFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IClearSplitFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E4771802"));
    }
}