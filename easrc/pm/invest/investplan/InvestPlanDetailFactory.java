package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvestPlanDetailFactory
{
    private InvestPlanDetailFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9613BABC") ,com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetail.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9613BABC") ,com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetail.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9613BABC"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IInvestPlanDetail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9613BABC"));
    }
}