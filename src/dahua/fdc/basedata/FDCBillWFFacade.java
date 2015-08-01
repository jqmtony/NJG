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
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import java.util.Set;

public class FDCBillWFFacade extends AbstractBizCtrl implements IFDCBillWFFacade
{
    public FDCBillWFFacade()
    {
        super();
        registerInterface(IFDCBillWFFacade.class, this);
    }
    public FDCBillWFFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCBillWFFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("06611023");
    }
    private FDCBillWFFacadeController getController() throws BOSException
    {
        return (FDCBillWFFacadeController)getBizController();
    }
    /**
     *设置工作流审批信息的某一个节点不打印-User defined method
     *@param billId 单据Id
     *@param auditorId 审批人Id
     */
    public void setWFAuditNotPrint(BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException
    {
        try {
            getController().setWFAuditNotPrint(getContext(), billId, auditorId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置工作流审批节点的公司信息-User defined method
     *@param billId 单据Id
     *@param auditorId 审批人Id
     *@param org 公司
     */
    public void setWFAuditOrgInfo(BOSUuid billId, BOSUuid auditorId, String org) throws BOSException, EASBizException
    {
        try {
            getController().setWFAuditOrgInfo(getContext(), billId, auditorId, org);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取工作流的需要打印的审批信息 (无历史信息)-User defined method
     *@param billId 单据Id
     *@return
     */
    public List getWFAuditResultForPrint(String billId) throws BOSException, EASBizException
    {
        try {
            return getController().getWFAuditResultForPrint(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取工作流的需要打印的审批信息-User defined method
     *@param billId 单据Id
     *@param showHistory 是否显示历史审批信息
     *@return
     */
    public List getWFAuditResultForPrint2(String billId, boolean showHistory) throws BOSException, EASBizException
    {
        try {
            return getController().getWFAuditResultForPrint2(getContext(), billId, showHistory);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取最新的审批人和审批时间-User defined method
     *@param billIds 单据Ids
     *@return
     */
    public Map getWFBillLastAuditorAndTime(Set billIds) throws BOSException, EASBizException
    {
        try {
            return getController().getWFBillLastAuditorAndTime(getContext(), billIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}