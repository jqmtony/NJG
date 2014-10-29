package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompanyPropertyFactory
{
    private CompanyPropertyFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.ICompanyProperty getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanyProperty)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FE1D3711") ,com.kingdee.eas.port.pm.base.ICompanyProperty.class);
    }
    
    public static com.kingdee.eas.port.pm.base.ICompanyProperty getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanyProperty)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FE1D3711") ,com.kingdee.eas.port.pm.base.ICompanyProperty.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.ICompanyProperty getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanyProperty)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FE1D3711"));
    }
    public static com.kingdee.eas.port.pm.base.ICompanyProperty getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICompanyProperty)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FE1D3711"));
    }
}