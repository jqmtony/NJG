package com.kingdee.eas.fdc.contract.report.monthcontract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthContractReportFacadeFactory
{
    private MonthContractReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.report.monthcontract.IMonthContractReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.monthcontract.IMonthContractReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("52C9666B") ,com.kingdee.eas.fdc.contract.report.monthcontract.IMonthContractReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.report.monthcontract.IMonthContractReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.monthcontract.IMonthContractReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("52C9666B") ,com.kingdee.eas.fdc.contract.report.monthcontract.IMonthContractReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.report.monthcontract.IMonthContractReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.monthcontract.IMonthContractReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("52C9666B"));
    }
    public static com.kingdee.eas.fdc.contract.report.monthcontract.IMonthContractReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.monthcontract.IMonthContractReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("52C9666B"));
    }
}