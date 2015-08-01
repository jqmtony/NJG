package com.kingdee.eas.fdc.material;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.material.app.*;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public class MaterialEnterSum extends FDCBill implements IMaterialEnterSum
{
    public MaterialEnterSum()
    {
        super();
        registerInterface(IMaterialEnterSum.class, this);
    }
    public MaterialEnterSum(Context ctx)
    {
        super(ctx);
        registerInterface(IMaterialEnterSum.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("842589E0");
    }
    private MaterialEnterSumController getController() throws BOSException
    {
        return (MaterialEnterSumController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public MaterialEnterSumCollection getMaterialEnterSumCollection() throws BOSException
    {
        try {
            return getController().getMaterialEnterSumCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public MaterialEnterSumCollection getMaterialEnterSumCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMaterialEnterSumCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public MaterialEnterSumCollection getMaterialEnterSumCollection(String oql) throws BOSException
    {
        try {
            return getController().getMaterialEnterSumCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public MaterialEnterSumInfo getMaterialEnterSumInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialEnterSumInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public MaterialEnterSumInfo getMaterialEnterSumInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialEnterSumInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public MaterialEnterSumInfo getMaterialEnterSumInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialEnterSumInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��д���Ͻ����ƻ��ѻ�������-User defined method
     *@param refList refList
     *@param state ���״̬
     *@return
     */
    public boolean refSetPlanSumQty(List refList, FDCBillStateEnum state) throws BOSException
    {
        try {
            return getController().refSetPlanSumQty(getContext(), refList, state);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}