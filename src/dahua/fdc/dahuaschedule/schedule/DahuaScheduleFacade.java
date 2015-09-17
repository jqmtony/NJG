package com.kingdee.eas.fdc.dahuaschedule.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.dahuaschedule.schedule.app.*;

public class DahuaScheduleFacade extends AbstractBizCtrl implements IDahuaScheduleFacade
{
    public DahuaScheduleFacade()
    {
        super();
        registerInterface(IDahuaScheduleFacade.class, this);
    }
    public DahuaScheduleFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IDahuaScheduleFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C5579C32");
    }
    private DahuaScheduleFacadeController getController() throws BOSException
    {
        return (DahuaScheduleFacadeController)getBizController();
    }
    /**
     *生成进度中间表数-User defined method
     *@param xml xml
     *@return
     */
    public String[] createTempData(String xml) throws BOSException
    {
        try {
            return getController().createTempData(getContext(), xml);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *生成进度信息，发布webservice不要发布出来的-User defined method
     *@return
     */
    public String[] createScheduleBill() throws BOSException
    {
        try {
            return getController().createScheduleBill(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}