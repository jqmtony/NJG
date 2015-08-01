package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.scheme.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;

public class FdcUpdateGeneralFacade extends AbstractBizCtrl implements IFdcUpdateGeneralFacade
{
    public FdcUpdateGeneralFacade()
    {
        super();
        registerInterface(IFdcUpdateGeneralFacade.class, this);
    }
    public FdcUpdateGeneralFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFdcUpdateGeneralFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2EEDB7A5");
    }
    private FdcUpdateGeneralFacadeController getController() throws BOSException
    {
        return (FdcUpdateGeneralFacadeController)getBizController();
    }
    /**
     *更新-User defined method
     *@param param param
     *@return
     */
    public Map updateData(Map param) throws BOSException
    {
        try {
            return getController().updateData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取数据-User defined method
     *@param param param
     *@return
     */
    public Map getData(Map param) throws BOSException
    {
        try {
            return getController().getData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}