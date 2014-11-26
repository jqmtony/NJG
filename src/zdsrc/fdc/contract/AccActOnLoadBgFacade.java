package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;
import com.kingdee.eas.fdc.contract.app.*;
import java.util.Set;

public class AccActOnLoadBgFacade extends CommRptBase implements IAccActOnLoadBgFacade
{
    public AccActOnLoadBgFacade()
    {
        super();
        registerInterface(IAccActOnLoadBgFacade.class, this);
    }
    public AccActOnLoadBgFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IAccActOnLoadBgFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F6EABD30");
    }
    private AccActOnLoadBgFacadeController getController() throws BOSException
    {
        return (AccActOnLoadBgFacadeController)getBizController();
    }
    /**
     *获取打印信息-User defined method
     *@param idSet id集合
     *@return
     */
    public IRowSet getPrintData(Set idSet) throws BOSException
    {
        try {
            return getController().getPrintData(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}