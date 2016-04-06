package com.kingdee.eas.fdc.aimcost.report.groupDynamic;

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
import com.kingdee.eas.fdc.aimcost.report.groupDynamic.app.*;

public class GroupDynamicReportFacade extends CommRptBase implements IGroupDynamicReportFacade
{
    public GroupDynamicReportFacade()
    {
        super();
        registerInterface(IGroupDynamicReportFacade.class, this);
    }
    public GroupDynamicReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IGroupDynamicReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BD2C6159");
    }
    private GroupDynamicReportFacadeController getController() throws BOSException
    {
        return (GroupDynamicReportFacadeController)getBizController();
    }
}