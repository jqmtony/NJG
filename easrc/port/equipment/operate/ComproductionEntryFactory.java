package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ComproductionEntryFactory
{
    private ComproductionEntryFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.operate.IComproductionEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IComproductionEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("62F96457") ,com.kingdee.eas.port.equipment.operate.IComproductionEntry.class);
    }
    
    public static com.kingdee.eas.port.equipment.operate.IComproductionEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IComproductionEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("62F96457") ,com.kingdee.eas.port.equipment.operate.IComproductionEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.operate.IComproductionEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IComproductionEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("62F96457"));
    }
    public static com.kingdee.eas.port.equipment.operate.IComproductionEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IComproductionEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("62F96457"));
    }
}