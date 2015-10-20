package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayReqPrjPayEntryFactory
{
    private PayReqPrjPayEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPayReqPrjPayEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayReqPrjPayEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("766F91E1") ,com.kingdee.eas.fdc.contract.IPayReqPrjPayEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPayReqPrjPayEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayReqPrjPayEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("766F91E1") ,com.kingdee.eas.fdc.contract.IPayReqPrjPayEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPayReqPrjPayEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayReqPrjPayEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("766F91E1"));
    }
    public static com.kingdee.eas.fdc.contract.IPayReqPrjPayEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayReqPrjPayEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("766F91E1"));
    }
}