package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FdcUpdateGeneralFacadeFactory
{
    private FdcUpdateGeneralFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcUpdateGeneralFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcUpdateGeneralFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2EEDB7A5") ,com.kingdee.eas.fdc.basedata.scheme.IFdcUpdateGeneralFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcUpdateGeneralFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcUpdateGeneralFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2EEDB7A5") ,com.kingdee.eas.fdc.basedata.scheme.IFdcUpdateGeneralFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcUpdateGeneralFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcUpdateGeneralFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2EEDB7A5"));
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcUpdateGeneralFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcUpdateGeneralFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2EEDB7A5"));
    }
}