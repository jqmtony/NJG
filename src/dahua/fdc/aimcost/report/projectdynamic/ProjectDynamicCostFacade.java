package com.kingdee.eas.fdc.aimcost.report.projectdynamic;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.aimcost.report.projectdynamic.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class ProjectDynamicCostFacade extends CommRptBase implements IProjectDynamicCostFacade
{
    public ProjectDynamicCostFacade()
    {
        super();
        registerInterface(IProjectDynamicCostFacade.class, this);
    }
    public ProjectDynamicCostFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectDynamicCostFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5EEE7752");
    }
    private ProjectDynamicCostFacadeController getController() throws BOSException
    {
        return (ProjectDynamicCostFacadeController)getBizController();
    }
}