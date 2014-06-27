package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvestPlanDetailEntryFactory
{
    private InvestPlanDetailEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetailEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetailEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E7FFB316") ,com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetailEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetailEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetailEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E7FFB316") ,com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetailEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetailEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetailEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E7FFB316"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetailEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetailEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E7FFB316"));
    }
}