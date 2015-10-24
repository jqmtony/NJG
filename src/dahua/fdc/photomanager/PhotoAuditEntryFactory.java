package com.kingdee.eas.fdc.photomanager;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PhotoAuditEntryFactory
{
    private PhotoAuditEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.photomanager.IPhotoAuditEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoAuditEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("54EB6037") ,com.kingdee.eas.fdc.photomanager.IPhotoAuditEntry.class);
    }
    
    public static com.kingdee.eas.fdc.photomanager.IPhotoAuditEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoAuditEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("54EB6037") ,com.kingdee.eas.fdc.photomanager.IPhotoAuditEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.photomanager.IPhotoAuditEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoAuditEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("54EB6037"));
    }
    public static com.kingdee.eas.fdc.photomanager.IPhotoAuditEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoAuditEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("54EB6037"));
    }
}