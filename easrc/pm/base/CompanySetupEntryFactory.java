package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompanySetupEntryFactory
{
    private CompanySetupEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.ICompanySetupEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanySetupEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("613BDE31") ,com.kingdee.eas.port.pm.base.ICompanySetupEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.base.ICompanySetupEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanySetupEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("613BDE31") ,com.kingdee.eas.port.pm.base.ICompanySetupEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.ICompanySetupEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanySetupEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("613BDE31"));
    }
    public static com.kingdee.eas.port.pm.base.ICompanySetupEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanySetupEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("613BDE31"));
    }
}