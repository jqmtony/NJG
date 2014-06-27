package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvestYearFactory
{
    private InvestYearFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IInvestYear getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IInvestYear)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F18E4CD3") ,com.kingdee.eas.port.pm.base.IInvestYear.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IInvestYear getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IInvestYear)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F18E4CD3") ,com.kingdee.eas.port.pm.base.IInvestYear.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IInvestYear getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IInvestYear)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F18E4CD3"));
    }
    public static com.kingdee.eas.port.pm.base.IInvestYear getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IInvestYear)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F18E4CD3"));
    }
}