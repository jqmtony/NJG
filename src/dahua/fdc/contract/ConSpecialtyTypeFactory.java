package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConSpecialtyTypeFactory
{
    private ConSpecialtyTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IConSpecialtyType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConSpecialtyType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8563B731") ,com.kingdee.eas.fdc.contract.IConSpecialtyType.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IConSpecialtyType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConSpecialtyType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8563B731") ,com.kingdee.eas.fdc.contract.IConSpecialtyType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IConSpecialtyType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConSpecialtyType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8563B731"));
    }
    public static com.kingdee.eas.fdc.contract.IConSpecialtyType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConSpecialtyType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8563B731"));
    }
}