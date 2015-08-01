package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TypeFactory
{
    private TypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EC29DD2C") ,com.kingdee.eas.fdc.basedata.IType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EC29DD2C") ,com.kingdee.eas.fdc.basedata.IType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EC29DD2C"));
    }
    public static com.kingdee.eas.fdc.basedata.IType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EC29DD2C"));
    }
}