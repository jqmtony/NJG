package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingTemplateEntireFactory
{
    private ProgrammingTemplateEntireFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntire getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntire)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("96023477") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntire.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntire getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntire)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("96023477") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntire.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntire getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntire)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("96023477"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntire getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntire)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("96023477"));
    }
}