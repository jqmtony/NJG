package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AccidentNatureFactory
{
    private AccidentNatureFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IAccidentNature getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IAccidentNature)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D5EDFA58") ,com.kingdee.eas.port.equipment.base.IAccidentNature.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IAccidentNature getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IAccidentNature)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D5EDFA58") ,com.kingdee.eas.port.equipment.base.IAccidentNature.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IAccidentNature getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IAccidentNature)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D5EDFA58"));
    }
    public static com.kingdee.eas.port.equipment.base.IAccidentNature getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IAccidentNature)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D5EDFA58"));
    }
}