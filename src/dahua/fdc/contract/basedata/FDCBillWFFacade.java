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
     *���ù�����������Ϣ��ĳһ���ڵ㲻��ӡ-User defined method
     *@param billId ����Id
     *@param auditorId ������Id
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
     *���ù����������ڵ�Ĺ�˾��Ϣ-User defined method
     *@param billId ����Id
     *@param auditorId ������Id
     *@param org ��˾
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
     *��ȡ����������Ҫ��ӡ��������Ϣ (����ʷ��Ϣ)-User defined method
     *@param billId ����Id
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
     *��ȡ����������Ҫ��ӡ��������Ϣ-User defined method
     *@param billId ����Id
     *@param showHistory �Ƿ���ʾ��ʷ������Ϣ
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
     *��ȡ���µ������˺�����ʱ��-User defined method
     *@param billIds ����Ids
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