package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class VisaTypeFactory
{
    private VisaTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IVisaType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IVisaType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("62CC3C0D") ,com.kingdee.eas.fdc.basedata.IVisaType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IVisaType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IVisaType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("62CC3C0D") ,com.kingdee.eas.fdc.basedata.IVisaType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IVisaType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IVisaType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("62CC3C0D"));
    }
    public static com.kingdee.eas.fdc.basedata.IVisaType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IVisaType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("62CC3C0D"));
    }
}