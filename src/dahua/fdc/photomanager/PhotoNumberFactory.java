package com.kingdee.eas.fdc.photomanager;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PhotoNumberFactory
{
    private PhotoNumberFactory()
    {
    }
    public static com.kingdee.eas.fdc.photomanager.IPhotoNumber getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoNumber)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E3DFFE29") ,com.kingdee.eas.fdc.photomanager.IPhotoNumber.class);
    }
    
    public static com.kingdee.eas.fdc.photomanager.IPhotoNumber getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoNumber)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E3DFFE29") ,com.kingdee.eas.fdc.photomanager.IPhotoNumber.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.photomanager.IPhotoNumber getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoNumber)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E3DFFE29"));
    }
    public static com.kingdee.eas.fdc.photomanager.IPhotoNumber getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.photomanager.IPhotoNumber)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E3DFFE29"));
    }
}