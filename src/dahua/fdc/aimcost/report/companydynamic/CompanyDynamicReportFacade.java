package com.kingdee.eas.fdc.aimcost.report.companydynamic;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.aimcost.report.companydynamic.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class CompanyDynamicReportFacade extends CommRptBase implements ICompanyDynamicReportFacade
{
    public CompanyDynamicReportFacade()
    {
        super();
        registerInterface(ICompanyDynamicReportFacade.class, this);
    }
    public CompanyDynamicReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICompanyDynamicReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A2AE2F39");
    }
    private CompanyDynamicReportFacadeController getController() throws BOSException
    {
        return (CompanyDynamicReportFacadeController)getBizController();
    }
}