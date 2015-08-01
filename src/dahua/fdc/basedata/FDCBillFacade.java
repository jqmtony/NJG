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
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class FDCBillFacade extends AbstractBizCtrl implements IFDCBillFacade
{
    public FDCBillFacade()
    {
        super();
        registerInterface(IFDCBillFacade.class, this);
    }
    public FDCBillFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCBillFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5138CB14");
    }
    private FDCBillFacadeController getController() throws BOSException
    {
        return (FDCBillFacadeController)getBizController();
    }
    /**
     *�Զ��������-User defined method
     *@param settleId ��ͬID
     *@param isAll �Ƿ�����ѱ������ĸ���
     */
    public void autoChangeSettle(String settleId, boolean isAll) throws BOSException, EASBizException
    {
        try {
            getController().autoChangeSettle(getContext(), settleId, isAll);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͨ��ִ�в�������-User defined method
     *@param param param
     *@return
     */
    public Object execAction(Map param) throws BOSException
    {
        try {
            return getController().execAction(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Զ��������-User defined method
     *@param type ����
     *@param dataMap ���ݼ���
     *@return
     */
    public FDCSplitBillInfo autoPropSplit(String type, Map dataMap) throws BOSException, EASBizException
    {
        try {
            return getController().autoPropSplit(getContext(), type, dataMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���õ���������-User defined method
     *@param billId ����Id
     */
    public void setBillAudited4wf(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setBillAudited4wf(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���õ���������ָ��������-User defined method
     *@param billId ����ID
     *@param auditorId ������
     */
    public void setBillAudited4wf2(BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException
    {
        try {
            getController().setBillAudited4wf2(getContext(), billId, auditorId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���õ���������ָ��������-User defined method
     *@param billId ����Id
     *@param auditorId ������
     */
    public void setBillAuditing4wf2(BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException
    {
        try {
            getController().setBillAuditing4wf2(getContext(), billId, auditorId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *dealSaveAboutConChange-User defined method
     *@param settleId ���㵥ID
     */
    public void dealSaveAboutConChange(String settleId) throws BOSException, EASBizException
    {
        try {
            getController().dealSaveAboutConChange(getContext(), settleId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}