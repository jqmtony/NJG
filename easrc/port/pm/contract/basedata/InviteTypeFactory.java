package com.kingdee.eas.port.pm.contract.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteTypeFactory
{
    private InviteTypeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IInviteType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IInviteType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7856C090") ,com.kingdee.eas.port.pm.contract.basedata.IInviteType.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.basedata.IInviteType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IInviteType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7856C090") ,com.kingdee.eas.port.pm.contract.basedata.IInviteType.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IInviteType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IInviteType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7856C090"));
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IInviteType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IInviteType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7856C090"));
    }
}