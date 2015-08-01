package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NoticeInfoFactory
{
    private NoticeInfoFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.INoticeInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.INoticeInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DF7CD418") ,com.kingdee.eas.fdc.basedata.INoticeInfo.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.INoticeInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.INoticeInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DF7CD418") ,com.kingdee.eas.fdc.basedata.INoticeInfo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.INoticeInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.INoticeInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DF7CD418"));
    }
    public static com.kingdee.eas.fdc.basedata.INoticeInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.INoticeInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DF7CD418"));
    }
}