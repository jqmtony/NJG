package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingTemplateFactory
{
    private ProgrammingTemplateFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D383F146") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplate.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D383F146") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D383F146"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D383F146"));
    }
}