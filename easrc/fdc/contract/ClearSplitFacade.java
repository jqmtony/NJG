package com.kingdee.eas.fdc.contract;

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
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import java.util.List;

public class ClearSplitFacade extends AbstractBizCtrl implements IClearSplitFacade
{
    public ClearSplitFacade()
    {
        super();
        registerInterface(IClearSplitFacade.class, this);
    }
    public ClearSplitFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IClearSplitFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E4771802");
    }
    private ClearSplitFacadeController getController() throws BOSException
    {
        return (ClearSplitFacadeController)getBizController();
    }
    /**
     *���ϲ��-User defined method
     *@param contractID ��ͬid
     *@param isContract �Ƿ����Ϻ�ͬ���
     */
    public void clearAllSplit(String contractID, boolean isContract) throws BOSException, EASBizException
    {
        try {
            getController().clearAllSplit(getContext(), contractID, isContract);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ϲ��-User defined method
     *@param contractID ��ͬid
     */
    public void clearSplitBill(String contractID) throws BOSException, EASBizException
    {
        try {
            getController().clearSplitBill(getContext(), contractID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������ı���֣��ṩ�����ˢ�µĽӿڣ�-User defined method
     *@param id ���ı�Id
     */
    public void clearNoTextSplit(String id) throws BOSException, EASBizException
    {
        try {
            getController().clearNoTextSplit(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���Ͻ������Լ������˴˽����ֵĸ�����-User defined method
     *@param contractId ��ͬid
     */
    public void clearSettle(String contractId) throws BOSException, EASBizException
    {
        try {
            getController().clearSettle(getContext(), contractId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ֱ���������ı���ͬ���-User defined method
     *@param id ���ı�Id
     */
    public void clearWithoutTextSplit(String id) throws BOSException, EASBizException
    {
        try {
            getController().clearWithoutTextSplit(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ϸ����֣����ĺ�ͬ״̬-User defined method
     *@param idList ��ͬID����
     */
    public void clearPaymentSplit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().clearPaymentSplit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���֮���ڼ�ĺ�ͬ��ص����в�֣�������ͬ��֣������֣������֣������֣����ı���ͬ�����֣�-User defined method
     *@param contractID ��ͬID
     *@param type ������ͣ���ͬ�����㡢����
     *@return
     */
    public String clearPeriodConSplit(String contractID, String type) throws BOSException, EASBizException
    {
        try {
            return getController().clearPeriodConSplit(getContext(), contractID, type);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ϱ����֣��Լ������Ľ����֣�������-User defined method
     *@param changeId ���ID
     */
    public void clearChangeSplit(String changeId) throws BOSException, EASBizException
    {
        try {
            getController().clearChangeSplit(getContext(), changeId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ�������Ŀ��Ӧ�����ϵ���û�����ϵĺ�ͬ-User defined method
     *@param idList ������ĿID
     *@return
     */
    public List getToInvalidContract(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().getToInvalidContract(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}