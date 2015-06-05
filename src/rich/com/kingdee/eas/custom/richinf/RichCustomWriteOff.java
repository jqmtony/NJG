package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fi.ar.OtherBillCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.custom.richinf.app.*;

public class RichCustomWriteOff extends CoreBillBase implements IRichCustomWriteOff
{
    public RichCustomWriteOff()
    {
        super();
        registerInterface(IRichCustomWriteOff.class, this);
    }
    public RichCustomWriteOff(Context ctx)
    {
        super(ctx);
        registerInterface(IRichCustomWriteOff.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C6D7BD2B");
    }
    private RichCustomWriteOffController getController() throws BOSException
    {
        return (RichCustomWriteOffController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public RichCustomWriteOffCollection getRichCustomWriteOffCollection() throws BOSException
    {
        try {
            return getController().getRichCustomWriteOffCollection(getContext());
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
    public RichCustomWriteOffCollection getRichCustomWriteOffCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRichCustomWriteOffCollection(getContext(), view);
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
    public RichCustomWriteOffCollection getRichCustomWriteOffCollection(String oql) throws BOSException
    {
        try {
            return getController().getRichCustomWriteOffCollection(getContext(), oql);
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
    public RichCustomWriteOffInfo getRichCustomWriteOffInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCustomWriteOffInfo(getContext(), pk);
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
    public RichCustomWriteOffInfo getRichCustomWriteOffInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCustomWriteOffInfo(getContext(), pk, selector);
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
    public RichCustomWriteOffInfo getRichCustomWriteOffInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCustomWriteOffInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ͷ�����-User defined method
     *@param fpColl ��Ʊ����
     *@param richExamColl ���쵥����
     *@param hxType ��������
     *@param ov �ͻ�������
     */
    public void aboutHxAndFanHx(OtherBillCollection fpColl, RichExamedCollection richExamColl, int hxType, RichCustomWriteOffInfo ov) throws BOSException
    {
        try {
            getController().aboutHxAndFanHx(getContext(), fpColl, richExamColl, hxType, ov);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}