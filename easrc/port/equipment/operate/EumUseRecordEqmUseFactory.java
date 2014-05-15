package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EumUseRecordEqmUseFactory
{
    private EumUseRecordEqmUseFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.operate.IEumUseRecordEqmUse getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEumUseRecordEqmUse)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("460F30A0") ,com.kingdee.eas.port.equipment.operate.IEumUseRecordEqmUse.class);
    }
    
    public static com.kingdee.eas.port.equipment.operate.IEumUseRecordEqmUse getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEumUseRecordEqmUse)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("460F30A0") ,com.kingdee.eas.port.equipment.operate.IEumUseRecordEqmUse.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.operate.IEumUseRecordEqmUse getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEumUseRecordEqmUse)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("460F30A0"));
    }
    public static com.kingdee.eas.port.equipment.operate.IEumUseRecordEqmUse getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEumUseRecordEqmUse)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("460F30A0"));
    }
}