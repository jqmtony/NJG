package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingTemplateEntirePteCostFactory
{
    private ProgrammingTemplateEntirePteCostFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E1B3DD7") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteCost.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E1B3DD7") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteCost.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E1B3DD7"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E1B3DD7"));
    }
}