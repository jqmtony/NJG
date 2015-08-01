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
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import java.util.Set;

public class ContractExecFacade extends AbstractBizCtrl implements IContractExecFacade
{
    public ContractExecFacade()
    {
        super();
        registerInterface(IContractExecFacade.class, this);
    }
    public ContractExecFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IContractExecFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4014ACB8");
    }
    private ContractExecFacadeController getController() throws BOSException
    {
        return (ContractExecFacadeController)getBizController();
    }
    /**
     *����Ҫ���ں�ִͬ����������һ�к�����һ�С��ۼ����깤��������-User defined method
     *@param fullOrgUnitID ʵ�������֯ID
     *@param idSet ��ͬID����
     *@return
     */
    public Map _getCompleteAmt(String fullOrgUnitID, Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController()._getCompleteAmt(getContext(), fullOrgUnitID, idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ҫ����"��ִͬ�������"����ʾ"�ۼ����깤������"-User defined method
     *@param idSet ��ͬID����
     *@return
     */
    public Map _getCompletePrjAmtForNoTextContract(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController()._getCompletePrjAmtForNoTextContract(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ۼ����깤������ȡ������-User defined method
     *@param orgId2ContractIdSet keyΪ��֯id�� valueΪ��ͬid�ļ���
     *@return
     */
    public Map getCompleteAmt(Map orgId2ContractIdSet) throws BOSException, EASBizException
    {
        try {
            return getController().getCompleteAmt(getContext(), orgId2ContractIdSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ���ͬ�µ��ۼ�Ʊ����-User defined method
     *@param idSet ��ͬID����
     *@return
     */
    public Map getAllInvoiceAmt(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getAllInvoiceAmt(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ���ͬ�µ��ۼ�Ʊ���ԭ��-User defined method
     *@param idSet ��ͬid
     *@return
     */
    public Map getAllInvoiceOriAmt(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getAllInvoiceOriAmt(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��õ������-User defined method
     *@param idSet ��ͬID
     *@return
     */
    public Map getAdjustSumMap(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getAdjustSumMap(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����Ʊ����-User defined method
     *@param idSet ID
     *@return
     */
    public Map getInvoiceAmt(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceAmt(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����Ʊ���ԭ��-User defined method
     *@param idSet ID
     *@return
     */
    public Map getInvoiceOriAmt(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceOriAmt(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}