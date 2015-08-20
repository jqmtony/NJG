package com.kingdee.eas.fdc.contract.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PcDepTypeFactory
{
    private PcDepTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.basedata.IPcDepType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IPcDepType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6E190484") ,com.kingdee.eas.fdc.contract.basedata.IPcDepType.class);
    }
    
    public static com.kingdee.eas.fdc.contract.basedata.IPcDepType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IPcDepType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6E190484") ,com.kingdee.eas.fdc.contract.basedata.IPcDepType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.basedata.IPcDepType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IPcDepType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6E190484"));
    }
    public static com.kingdee.eas.fdc.contract.basedata.IPcDepType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IPcDepType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6E190484"));
    }
}