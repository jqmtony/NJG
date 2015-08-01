package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CurProjProEntrApporDataFactory
{
    private CurProjProEntrApporDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjProEntrApporData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjProEntrApporData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("00F65F65") ,com.kingdee.eas.fdc.basedata.ICurProjProEntrApporData.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICurProjProEntrApporData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjProEntrApporData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("00F65F65") ,com.kingdee.eas.fdc.basedata.ICurProjProEntrApporData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjProEntrApporData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjProEntrApporData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("00F65F65"));
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjProEntrApporData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjProEntrApporData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("00F65F65"));
    }
}