package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LandInfomationFactory
{
    private LandInfomationFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ILandInfomation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILandInfomation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F47B6C93") ,com.kingdee.eas.fdc.basedata.ILandInfomation.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ILandInfomation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILandInfomation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F47B6C93") ,com.kingdee.eas.fdc.basedata.ILandInfomation.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ILandInfomation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILandInfomation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F47B6C93"));
    }
    public static com.kingdee.eas.fdc.basedata.ILandInfomation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILandInfomation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F47B6C93"));
    }
}