package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeductTypeFactory
{
    private DeductTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IDeductType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDeductType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8782970F") ,com.kingdee.eas.fdc.basedata.IDeductType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IDeductType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDeductType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8782970F") ,com.kingdee.eas.fdc.basedata.IDeductType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IDeductType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDeductType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8782970F"));
    }
    public static com.kingdee.eas.fdc.basedata.IDeductType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDeductType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8782970F"));
    }
}