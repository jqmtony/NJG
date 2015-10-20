package com.kingdee.eas.fdc.contract.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RealDateRelFactory
{
    private RealDateRelFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.basedata.IRealDateRel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IRealDateRel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FF80291B") ,com.kingdee.eas.fdc.contract.basedata.IRealDateRel.class);
    }
    
    public static com.kingdee.eas.fdc.contract.basedata.IRealDateRel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IRealDateRel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FF80291B") ,com.kingdee.eas.fdc.contract.basedata.IRealDateRel.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.basedata.IRealDateRel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IRealDateRel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FF80291B"));
    }
    public static com.kingdee.eas.fdc.contract.basedata.IRealDateRel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IRealDateRel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FF80291B"));
    }
}