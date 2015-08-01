package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCPDMFacadeFactory
{
    private FDCCPDMFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IFDCCPDMFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCCPDMFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("27C33563") ,com.kingdee.eas.fdc.basedata.IFDCCPDMFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IFDCCPDMFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCCPDMFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("27C33563") ,com.kingdee.eas.fdc.basedata.IFDCCPDMFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IFDCCPDMFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCCPDMFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("27C33563"));
    }
    public static com.kingdee.eas.fdc.basedata.IFDCCPDMFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCCPDMFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("27C33563"));
    }
}