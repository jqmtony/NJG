package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCBillFacadeFactory
{
    private FDCBillFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBillFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5138CB14") ,com.kingdee.eas.fdc.basedata.IFDCBillFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IFDCBillFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5138CB14") ,com.kingdee.eas.fdc.basedata.IFDCBillFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBillFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5138CB14"));
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBillFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBillFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5138CB14"));
    }
}