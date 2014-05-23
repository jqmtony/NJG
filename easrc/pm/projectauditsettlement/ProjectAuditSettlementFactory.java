package com.kingdee.eas.port.pm.projectauditsettlement;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectAuditSettlementFactory
{
    private ProjectAuditSettlementFactory()
    {
    }
    public static com.kingdee.eas.port.pm.projectauditsettlement.IProjectAuditSettlement getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.projectauditsettlement.IProjectAuditSettlement)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0016A052") ,com.kingdee.eas.port.pm.projectauditsettlement.IProjectAuditSettlement.class);
    }
    
    public static com.kingdee.eas.port.pm.projectauditsettlement.IProjectAuditSettlement getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.projectauditsettlement.IProjectAuditSettlement)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0016A052") ,com.kingdee.eas.port.pm.projectauditsettlement.IProjectAuditSettlement.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.projectauditsettlement.IProjectAuditSettlement getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.projectauditsettlement.IProjectAuditSettlement)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0016A052"));
    }
    public static com.kingdee.eas.port.pm.projectauditsettlement.IProjectAuditSettlement getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.projectauditsettlement.IProjectAuditSettlement)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0016A052"));
    }
}