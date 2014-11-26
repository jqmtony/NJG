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

public class AccActOnLoadBgGatherFacade extends CommRptBase implements IAccActOnLoadBgGatherFacade
{
    public AccActOnLoadBgGatherFacade()
    {
        super();
        registerInterface(IAccActOnLoadBgGatherFacade.class, this);
    }
    public AccActOnLoadBgGatherFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IAccActOnLoadBgGatherFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6750168B");
    }
    private AccActOnLoadBgGatherFacadeController getController() throws BOSException
    {
        return (AccActOnLoadBgGatherFacadeController)getBizController();
    }
    /**
     *��ȡ��ӡ��Ϣ-User defined method
     *@param idSet id����
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