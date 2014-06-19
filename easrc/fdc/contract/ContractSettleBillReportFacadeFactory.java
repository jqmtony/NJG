package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractSettleBillReportFacadeFactory
{
    private ContractSettleBillReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractSettleBillReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettleBillReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B5C22CAD") ,com.kingdee.eas.fdc.contract.IContractSettleBillReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractSettleBillReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettleBillReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B5C22CAD") ,com.kingdee.eas.fdc.contract.IContractSettleBillReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractSettleBillReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettleBillReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B5C22CAD"));
    }
    public static com.kingdee.eas.fdc.contract.IContractSettleBillReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettleBillReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B5C22CAD"));
    }
}