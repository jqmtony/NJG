package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaySplitUtilFacadeFactory
{
    private PaySplitUtilFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IPaySplitUtilFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPaySplitUtilFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8BFF02A0") ,com.kingdee.eas.fdc.basedata.IPaySplitUtilFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IPaySplitUtilFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPaySplitUtilFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8BFF02A0") ,com.kingdee.eas.fdc.basedata.IPaySplitUtilFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IPaySplitUtilFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPaySplitUtilFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8BFF02A0"));
    }
    public static com.kingdee.eas.fdc.basedata.IPaySplitUtilFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPaySplitUtilFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8BFF02A0"));
    }
}