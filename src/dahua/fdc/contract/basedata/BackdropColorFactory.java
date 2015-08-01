package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BackdropColorFactory
{
    private BackdropColorFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IBackdropColor getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBackdropColor)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A153DEFB") ,com.kingdee.eas.fdc.basedata.IBackdropColor.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IBackdropColor getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBackdropColor)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A153DEFB") ,com.kingdee.eas.fdc.basedata.IBackdropColor.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IBackdropColor getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBackdropColor)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A153DEFB"));
    }
    public static com.kingdee.eas.fdc.basedata.IBackdropColor getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IBackdropColor)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A153DEFB"));
    }
}