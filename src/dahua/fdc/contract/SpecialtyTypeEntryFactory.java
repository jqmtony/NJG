package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SpecialtyTypeEntryFactory
{
    private SpecialtyTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ISpecialtyTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISpecialtyTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0E915F75") ,com.kingdee.eas.fdc.contract.ISpecialtyTypeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ISpecialtyTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISpecialtyTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0E915F75") ,com.kingdee.eas.fdc.contract.ISpecialtyTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ISpecialtyTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISpecialtyTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0E915F75"));
    }
    public static com.kingdee.eas.fdc.contract.ISpecialtyTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISpecialtyTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0E915F75"));
    }
}