package com.kingdee.eas.fdc.contract.report.monthcontract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;
import com.kingdee.eas.fdc.contract.report.monthcontract.app.*;

public class MonthContractReportFacade extends CommRptBase implements IMonthContractReportFacade
{
    public MonthContractReportFacade()
    {
        super();
        registerInterface(IMonthContractReportFacade.class, this);
    }
    public MonthContractReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMonthContractReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("52C9666B");
    }
    private MonthContractReportFacadeController getController() throws BOSException
    {
        return (MonthContractReportFacadeController)getBizController();
    }
}