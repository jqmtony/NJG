package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class FdcRptBaseFacade extends CommRptBase implements IFdcRptBaseFacade
{
    public FdcRptBaseFacade()
    {
        super();
        registerInterface(IFdcRptBaseFacade.class, this);
    }
    public FdcRptBaseFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFdcRptBaseFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3500A0EE");
    }
    private FdcRptBaseFacadeController getController() throws BOSException
    {
        return (FdcRptBaseFacadeController)getBizController();
    }
    /**
     *获取期间设置-User defined method
     *@param costCenterId 成本中心
     *@param isProcess 系统是否是进度
     *@return
     */
    public Map getPeriodRange(String costCenterId, boolean isProcess) throws BOSException, EASBizException
    {
        try {
            return getController().getPeriodRange(getContext(), costCenterId, isProcess);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}