package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillReportFacadeFactory
{
    private ContractBillReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B59F1302") ,com.kingdee.eas.fdc.contract.IContractBillReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B59F1302") ,com.kingdee.eas.fdc.contract.IContractBillReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B59F1302"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B59F1302"));
    }
}