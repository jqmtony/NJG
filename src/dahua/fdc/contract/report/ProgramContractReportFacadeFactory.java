package com.kingdee.eas.fdc.contract.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgramContractReportFacadeFactory
{
    private ProgramContractReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.report.IProgramContractReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.IProgramContractReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7D2C5F0B") ,com.kingdee.eas.fdc.contract.report.IProgramContractReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.report.IProgramContractReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.IProgramContractReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7D2C5F0B") ,com.kingdee.eas.fdc.contract.report.IProgramContractReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.report.IProgramContractReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.IProgramContractReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7D2C5F0B"));
    }
    public static com.kingdee.eas.fdc.contract.report.IProgramContractReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.report.IProgramContractReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7D2C5F0B"));
    }
}