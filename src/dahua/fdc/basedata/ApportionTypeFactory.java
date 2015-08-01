package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ApportionTypeFactory
{
    private ApportionTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IApportionType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IApportionType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("35B46E80") ,com.kingdee.eas.fdc.basedata.IApportionType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IApportionType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IApportionType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("35B46E80") ,com.kingdee.eas.fdc.basedata.IApportionType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IApportionType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IApportionType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("35B46E80"));
    }
    public static com.kingdee.eas.fdc.basedata.IApportionType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IApportionType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("35B46E80"));
    }
}