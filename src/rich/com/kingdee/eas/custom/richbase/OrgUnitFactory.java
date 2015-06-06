package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OrgUnitFactory
{
    private OrgUnitFactory()
    {
    }
    public static com.kingdee.eas.custom.richbase.IOrgUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IOrgUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("257FF4D2") ,com.kingdee.eas.custom.richbase.IOrgUnit.class);
    }
    
    public static com.kingdee.eas.custom.richbase.IOrgUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IOrgUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("257FF4D2") ,com.kingdee.eas.custom.richbase.IOrgUnit.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richbase.IOrgUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IOrgUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("257FF4D2"));
    }
    public static com.kingdee.eas.custom.richbase.IOrgUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IOrgUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("257FF4D2"));
    }
}