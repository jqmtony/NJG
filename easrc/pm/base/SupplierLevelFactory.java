package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierLevelFactory
{
    private SupplierLevelFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.ISupplierLevel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupplierLevel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B2A55277") ,com.kingdee.eas.port.pm.base.ISupplierLevel.class);
    }
    
    public static com.kingdee.eas.port.pm.base.ISupplierLevel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupplierLevel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B2A55277") ,com.kingdee.eas.port.pm.base.ISupplierLevel.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.ISupplierLevel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupplierLevel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B2A55277"));
    }
    public static com.kingdee.eas.port.pm.base.ISupplierLevel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupplierLevel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B2A55277"));
    }
}