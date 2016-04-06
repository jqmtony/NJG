package com.kingdee.eas.fdc.aimcost.report.productdynamic;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.aimcost.report.productdynamic.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class ProductDynamicReportFacade extends CommRptBase implements IProductDynamicReportFacade
{
    public ProductDynamicReportFacade()
    {
        super();
        registerInterface(IProductDynamicReportFacade.class, this);
    }
    public ProductDynamicReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProductDynamicReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1C97C079");
    }
    private ProductDynamicReportFacadeController getController() throws BOSException
    {
        return (ProductDynamicReportFacadeController)getBizController();
    }
}