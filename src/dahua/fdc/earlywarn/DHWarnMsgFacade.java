package com.kingdee.eas.fdc.earlywarn;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.earlywarn.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public class DHWarnMsgFacade extends AbstractBizCtrl implements IDHWarnMsgFacade
{
    public DHWarnMsgFacade()
    {
        super();
        registerInterface(IDHWarnMsgFacade.class, this);
    }
    public DHWarnMsgFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IDHWarnMsgFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("90D6137E");
    }
    private DHWarnMsgFacadeController getController() throws BOSException
    {
        return (DHWarnMsgFacadeController)getBizController();
    }
    /**
     *合约规划预警-User defined method
     */
    public void programmingWarnMsg() throws BOSException
    {
        try {
            getController().programmingWarnMsg(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *进度项预警-User defined method
     */
    public void dhScheduleWarnMsg() throws BOSException
    {
        try {
            getController().dhScheduleWarnMsg(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *合约跟踪单预警-User defined method
     *@param billId 单据ID
     *@param day 提前预警天数
     */
    public void programmingGZWarnMsg(String billId, int day) throws BOSException
    {
        try {
            getController().programmingGZWarnMsg(getContext(), billId, day);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *结算申报单预警-User defined method
     */
    public void settleDeclarationWarnMsg() throws BOSException
    {
        try {
            getController().settleDeclarationWarnMsg(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *动态成本差异率预警-User defined method
     */
    public void aimCostDiffWarnMsg() throws BOSException
    {
        try {
            getController().aimCostDiffWarnMsg(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}