package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractChangeBillReportFacadeFactory
{
    private ContractChangeBillReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeBillReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeBillReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9956C512") ,com.kingdee.eas.fdc.contract.IContractChangeBillReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractChangeBillReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeBillReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9956C512") ,com.kingdee.eas.fdc.contract.IContractChangeBillReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeBillReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeBillReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9956C512"));
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeBillReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeBillReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9956C512"));
    }
}