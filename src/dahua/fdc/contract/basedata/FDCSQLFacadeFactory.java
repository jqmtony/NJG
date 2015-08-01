package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSQLFacadeFactory
{
    private FDCSQLFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IFDCSQLFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCSQLFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B5889A15") ,com.kingdee.eas.fdc.basedata.IFDCSQLFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IFDCSQLFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCSQLFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B5889A15") ,com.kingdee.eas.fdc.basedata.IFDCSQLFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IFDCSQLFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCSQLFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B5889A15"));
    }
    public static com.kingdee.eas.fdc.basedata.IFDCSQLFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCSQLFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B5889A15"));
    }
}