package com.kingdee.eas.fdc.aimcost.report.productdynamic;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProductDynamicReportFacadeFactory
{
    private ProductDynamicReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.report.productdynamic.IProductDynamicReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.productdynamic.IProductDynamicReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1C97C079") ,com.kingdee.eas.fdc.aimcost.report.productdynamic.IProductDynamicReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.report.productdynamic.IProductDynamicReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.productdynamic.IProductDynamicReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1C97C079") ,com.kingdee.eas.fdc.aimcost.report.productdynamic.IProductDynamicReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.report.productdynamic.IProductDynamicReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.productdynamic.IProductDynamicReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1C97C079"));
    }
    public static com.kingdee.eas.fdc.aimcost.report.productdynamic.IProductDynamicReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.productdynamic.IProductDynamicReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1C97C079"));
    }
}