package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EumUseRecordFactory
{
    private EumUseRecordFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.operate.IEumUseRecord getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEumUseRecord)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3ABA977A") ,com.kingdee.eas.port.equipment.operate.IEumUseRecord.class);
    }
    
    public static com.kingdee.eas.port.equipment.operate.IEumUseRecord getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEumUseRecord)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3ABA977A") ,com.kingdee.eas.port.equipment.operate.IEumUseRecord.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.operate.IEumUseRecord getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEumUseRecord)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3ABA977A"));
    }
    public static com.kingdee.eas.port.equipment.operate.IEumUseRecord getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEumUseRecord)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3ABA977A"));
    }
}