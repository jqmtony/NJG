package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AccountStageFactory
{
    private AccountStageFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IAccountStage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAccountStage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("476BD8C3") ,com.kingdee.eas.fdc.basedata.IAccountStage.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IAccountStage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAccountStage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("476BD8C3") ,com.kingdee.eas.fdc.basedata.IAccountStage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IAccountStage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAccountStage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("476BD8C3"));
    }
    public static com.kingdee.eas.fdc.basedata.IAccountStage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAccountStage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("476BD8C3"));
    }
}