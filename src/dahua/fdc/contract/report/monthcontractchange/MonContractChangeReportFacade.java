package com.kingdee.eas.fdc.contract.report.monthcontractchange;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.contract.report.monthcontractchange.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class MonContractChangeReportFacade extends CommRptBase implements IMonContractChangeReportFacade
{
    public MonContractChangeReportFacade()
    {
        super();
        registerInterface(IMonContractChangeReportFacade.class, this);
    }
    public MonContractChangeReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMonContractChangeReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("10E03BF7");
    }
    private MonContractChangeReportFacadeController getController() throws BOSException
    {
        return (MonContractChangeReportFacadeController)getBizController();
    }
}