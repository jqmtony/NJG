package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCBillWFFacadeFactory
{
    private FDCBillWFFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBillWFFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillWFFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("06611023") ,com.kingdee.eas.fdc.basedata.IFDCBillWFFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IFDCBillWFFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillWFFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("06611023") ,com.kingdee.eas.fdc.basedata.IFDCBillWFFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBillWFFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillWFFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("06611023"));
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBillWFFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillWFFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("06611023"));
    }
}