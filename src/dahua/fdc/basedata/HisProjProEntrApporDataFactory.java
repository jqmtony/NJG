package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HisProjProEntrApporDataFactory
{
    private HisProjProEntrApporDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IHisProjProEntrApporData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjProEntrApporData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("324293B7") ,com.kingdee.eas.fdc.basedata.IHisProjProEntrApporData.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IHisProjProEntrApporData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjProEntrApporData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("324293B7") ,com.kingdee.eas.fdc.basedata.IHisProjProEntrApporData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IHisProjProEntrApporData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjProEntrApporData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("324293B7"));
    }
    public static com.kingdee.eas.fdc.basedata.IHisProjProEntrApporData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IHisProjProEntrApporData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("324293B7"));
    }
}