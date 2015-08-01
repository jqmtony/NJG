package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class UnitDataManagerFactory
{
    private UnitDataManagerFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IUnitDataManager getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IUnitDataManager)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8E92FF4D") ,com.kingdee.eas.fdc.basedata.IUnitDataManager.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IUnitDataManager getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IUnitDataManager)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8E92FF4D") ,com.kingdee.eas.fdc.basedata.IUnitDataManager.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IUnitDataManager getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IUnitDataManager)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8E92FF4D"));
    }
    public static com.kingdee.eas.fdc.basedata.IUnitDataManager getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IUnitDataManager)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8E92FF4D"));
    }
}