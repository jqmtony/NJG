package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquIdTechnologyParFactory
{
    private EquIdTechnologyParFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.record.IEquIdTechnologyPar getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdTechnologyPar)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("62370453") ,com.kingdee.eas.port.equipment.record.IEquIdTechnologyPar.class);
    }
    
    public static com.kingdee.eas.port.equipment.record.IEquIdTechnologyPar getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdTechnologyPar)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("62370453") ,com.kingdee.eas.port.equipment.record.IEquIdTechnologyPar.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.record.IEquIdTechnologyPar getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdTechnologyPar)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("62370453"));
    }
    public static com.kingdee.eas.port.equipment.record.IEquIdTechnologyPar getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdTechnologyPar)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("62370453"));
    }
}