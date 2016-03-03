package com.kingdee.eas.fdc.contract.report.monthcontractchange;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonContractChangeReportFacadeFactory
{
    private MonContractChangeReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.report.monthcontractchange.IMonContractChangeReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.monthcontractchange.IMonContractChangeReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("10E03BF7") ,com.kingdee.eas.fdc.contract.report.monthcontractchange.IMonContractChangeReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.report.monthcontractchange.IMonContractChangeReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.monthcontractchange.IMonContractChangeReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("10E03BF7") ,com.kingdee.eas.fdc.contract.report.monthcontractchange.IMonContractChangeReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.report.monthcontractchange.IMonContractChangeReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.monthcontractchange.IMonContractChangeReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("10E03BF7"));
    }
    public static com.kingdee.eas.fdc.contract.report.monthcontractchange.IMonContractChangeReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.monthcontractchange.IMonContractChangeReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("10E03BF7"));
    }
}