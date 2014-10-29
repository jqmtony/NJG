package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompanySetupFactory
{
    private CompanySetupFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.ICompanySetup getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanySetup)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A0A9CAC1") ,com.kingdee.eas.port.pm.base.ICompanySetup.class);
    }
    
    public static com.kingdee.eas.port.pm.base.ICompanySetup getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanySetup)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A0A9CAC1") ,com.kingdee.eas.port.pm.base.ICompanySetup.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.ICompanySetup getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanySetup)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A0A9CAC1"));
    }
    public static com.kingdee.eas.port.pm.base.ICompanySetup getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanySetup)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A0A9CAC1"));
    }
}