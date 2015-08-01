package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCBIRptBaseFacadeFactory
{
    private FDCBIRptBaseFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBIRptBaseFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBIRptBaseFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3C72D0C7") ,com.kingdee.eas.fdc.basedata.IFDCBIRptBaseFacade.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IFDCBIRptBaseFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBIRptBaseFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3C72D0C7") ,com.kingdee.eas.fdc.basedata.IFDCBIRptBaseFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBIRptBaseFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBIRptBaseFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3C72D0C7"));
    }
    public static com.kingdee.eas.fdc.basedata.IFDCBIRptBaseFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFDCBIRptBaseFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3C72D0C7"));
    }
}