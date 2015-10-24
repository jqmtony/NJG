package com.kingdee.eas.fdc.photomanager;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PhotoAuditFactory
{
    private PhotoAuditFactory()
    {
    }
    public static com.kingdee.eas.fdc.photomanager.IPhotoAudit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoAudit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("17269CFB") ,com.kingdee.eas.fdc.photomanager.IPhotoAudit.class);
    }
    
    public static com.kingdee.eas.fdc.photomanager.IPhotoAudit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoAudit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("17269CFB") ,com.kingdee.eas.fdc.photomanager.IPhotoAudit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.photomanager.IPhotoAudit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoAudit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("17269CFB"));
    }
    public static com.kingdee.eas.fdc.photomanager.IPhotoAudit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoAudit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("17269CFB"));
    }
}