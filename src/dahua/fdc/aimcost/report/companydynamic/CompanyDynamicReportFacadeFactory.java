package com.kingdee.eas.fdc.aimcost.report.companydynamic;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompanyDynamicReportFacadeFactory
{
    private CompanyDynamicReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.report.companydynamic.ICompanyDynamicReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.companydynamic.ICompanyDynamicReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A2AE2F39") ,com.kingdee.eas.fdc.aimcost.report.companydynamic.ICompanyDynamicReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.report.companydynamic.ICompanyDynamicReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.companydynamic.ICompanyDynamicReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A2AE2F39") ,com.kingdee.eas.fdc.aimcost.report.companydynamic.ICompanyDynamicReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.report.companydynamic.ICompanyDynamicReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.companydynamic.ICompanyDynamicReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A2AE2F39"));
    }
    public static com.kingdee.eas.fdc.aimcost.report.companydynamic.ICompanyDynamicReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.companydynamic.ICompanyDynamicReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A2AE2F39"));
    }
}