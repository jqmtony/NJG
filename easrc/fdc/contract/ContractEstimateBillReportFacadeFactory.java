package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractEstimateBillReportFacadeFactory
{
    private ContractEstimateBillReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractEstimateBillReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEstimateBillReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("54056F4A") ,com.kingdee.eas.fdc.contract.IContractEstimateBillReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractEstimateBillReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEstimateBillReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("54056F4A") ,com.kingdee.eas.fdc.contract.IContractEstimateBillReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractEstimateBillReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEstimateBillReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("54056F4A"));
    }
    public static com.kingdee.eas.fdc.contract.IContractEstimateBillReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEstimateBillReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("54056F4A"));
    }
}