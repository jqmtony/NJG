package com.kingdee.eas.fdc.contract.report;

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
import com.kingdee.eas.fdc.contract.report.app.*;

public class ProgramContractReportFacade extends CommRptBase implements IProgramContractReportFacade
{
    public ProgramContractReportFacade()
    {
        super();
        registerInterface(IProgramContractReportFacade.class, this);
    }
    public ProgramContractReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProgramContractReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7D2C5F0B");
    }
    private ProgramContractReportFacadeController getController() throws BOSException
    {
        return (ProgramContractReportFacadeController)getBizController();
    }
}