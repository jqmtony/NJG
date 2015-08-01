package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FdcPropertyFactory
{
    private FdcPropertyFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcProperty getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcProperty)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B5983B09") ,com.kingdee.eas.fdc.basedata.scheme.IFdcProperty.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcProperty getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcProperty)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B5983B09") ,com.kingdee.eas.fdc.basedata.scheme.IFdcProperty.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcProperty getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcProperty)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B5983B09"));
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcProperty getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcProperty)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B5983B09"));
    }
}