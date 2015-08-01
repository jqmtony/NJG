package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AllCostAcctWithAcctFactory
{
    private AllCostAcctWithAcctFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IAllCostAcctWithAcct getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAllCostAcctWithAcct)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D3160728") ,com.kingdee.eas.fdc.basedata.IAllCostAcctWithAcct.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IAllCostAcctWithAcct getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAllCostAcctWithAcct)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D3160728") ,com.kingdee.eas.fdc.basedata.IAllCostAcctWithAcct.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IAllCostAcctWithAcct getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAllCostAcctWithAcct)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D3160728"));
    }
    public static com.kingdee.eas.fdc.basedata.IAllCostAcctWithAcct getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAllCostAcctWithAcct)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D3160728"));
    }
}