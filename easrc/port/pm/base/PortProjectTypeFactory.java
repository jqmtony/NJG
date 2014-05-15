package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PortProjectTypeFactory
{
    private PortProjectTypeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IPortProjectType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IPortProjectType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("41C50451") ,com.kingdee.eas.port.pm.base.IPortProjectType.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IPortProjectType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IPortProjectType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("41C50451") ,com.kingdee.eas.port.pm.base.IPortProjectType.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IPortProjectType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IPortProjectType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("41C50451"));
    }
    public static com.kingdee.eas.port.pm.base.IPortProjectType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IPortProjectType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("41C50451"));
    }
}