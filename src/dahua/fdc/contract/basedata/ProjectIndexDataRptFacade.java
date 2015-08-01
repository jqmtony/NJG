package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.bos.framework.*;

public class ProjectIndexDataRptFacade extends FDCBIRptBaseFacade implements IProjectIndexDataRptFacade
{
    public ProjectIndexDataRptFacade()
    {
        super();
        registerInterface(IProjectIndexDataRptFacade.class, this);
    }
    public ProjectIndexDataRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectIndexDataRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("00B6323B");
    }
    private ProjectIndexDataRptFacadeController getController() throws BOSException
    {
        return (ProjectIndexDataRptFacadeController)getBizController();
    }
}