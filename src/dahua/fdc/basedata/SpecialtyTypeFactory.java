package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SpecialtyTypeFactory
{
    private SpecialtyTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ISpecialtyType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ISpecialtyType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DD5D9BA6") ,com.kingdee.eas.fdc.basedata.ISpecialtyType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ISpecialtyType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ISpecialtyType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DD5D9BA6") ,com.kingdee.eas.fdc.basedata.ISpecialtyType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ISpecialtyType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ISpecialtyType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DD5D9BA6"));
    }
    public static com.kingdee.eas.fdc.basedata.ISpecialtyType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ISpecialtyType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DD5D9BA6"));
    }
}