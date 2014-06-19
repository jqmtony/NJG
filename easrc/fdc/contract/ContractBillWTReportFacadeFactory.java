package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillWTReportFacadeFactory
{
    private ContractBillWTReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillWTReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillWTReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6D6B8D5F") ,com.kingdee.eas.fdc.contract.IContractBillWTReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillWTReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillWTReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6D6B8D5F") ,com.kingdee.eas.fdc.contract.IContractBillWTReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillWTReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillWTReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6D6B8D5F"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillWTReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillWTReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6D6B8D5F"));
    }
}