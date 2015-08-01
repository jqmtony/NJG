package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LicenceManagerFactory
{
    private LicenceManagerFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ILicenceManager getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILicenceManager)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("16421F8E") ,com.kingdee.eas.fdc.basedata.ILicenceManager.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ILicenceManager getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILicenceManager)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("16421F8E") ,com.kingdee.eas.fdc.basedata.ILicenceManager.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ILicenceManager getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILicenceManager)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("16421F8E"));
    }
    public static com.kingdee.eas.fdc.basedata.ILicenceManager getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILicenceManager)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("16421F8E"));
    }
}