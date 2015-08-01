package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FdcRptBaseFacadeFactory
{
    private FdcRptBaseFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3500A0EE") ,com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3500A0EE") ,com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3500A0EE"));
    }
    public static com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3500A0EE"));
    }
}