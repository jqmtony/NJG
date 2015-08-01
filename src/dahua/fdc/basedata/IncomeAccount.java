package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.TreeBase;

public class IncomeAccount extends TreeBase implements IIncomeAccount
{
    public IncomeAccount()
    {
        super();
        registerInterface(IIncomeAccount.class, this);
    }
    public IncomeAccount(Context ctx)
    {
        super(ctx);
        registerInterface(IIncomeAccount.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B6424292");
    }
    private IncomeAccountController getController() throws BOSException
    {
        return (IncomeAccountController)getBizController();
    }
    /**
     *存在-System defined method
     *@param pk 存在
     *@return
     */
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *存在-System defined method
     *@param filter 存在
     *@return
     */
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *存在-System defined method
     *@param oql 存在
     *@return
     */
    public boolean exists(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public IncomeAccountInfo getIncomeAccountInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getIncomeAccountInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public IncomeAccountInfo getIncomeAccountInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getIncomeAccountInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public IncomeAccountInfo getIncomeAccountInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getIncomeAccountInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增-System defined method
     *@param model 新增
     *@return
     */
    public IObjectPK addnew(IncomeAccountInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增-System defined method
     *@param pk 新增
     *@param model 新增
     */
    public void addnew(IObjectPK pk, IncomeAccountInfo model) throws BOSException, EASBizException
    {
        try {
            getController().addnew(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修改-System defined method
     *@param pk 更新
     *@param model 更新
     */
    public void update(IObjectPK pk, IncomeAccountInfo model) throws BOSException, EASBizException
    {
        try {
            getController().update(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *局部更新-System defined method
     *@param model 局部更新
     *@param selector 局部更新
     */
    public void updatePartial(IncomeAccountInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            getController().updatePartial(getContext(), model, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新大对象-System defined method
     *@param pk 更新大对象
     *@param model 更新大对象
     */
    public void updateBigObject(IObjectPK pk, IncomeAccountInfo model) throws BOSException
    {
        try {
            getController().updateBigObject(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-System defined method
     *@param pk 删除
     */
    public void delete(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取主键-System defined method
     *@return
     */
    public IObjectPK[] getPKList() throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取主键-System defined method
     *@param oql 取主键
     *@return
     */
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取主键-System defined method
     *@param filter 取主键
     *@param sorter 取主键
     *@return
     */
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), filter, sorter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public IncomeAccountCollection getIncomeAccountCollection() throws BOSException
    {
        try {
            return getController().getIncomeAccountCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public IncomeAccountCollection getIncomeAccountCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getIncomeAccountCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public IncomeAccountCollection getIncomeAccountCollection(String oql) throws BOSException
    {
        try {
            return getController().getIncomeAccountCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-System defined method
     *@param filter 删除
     *@return
     */
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-System defined method
     *@param oql 删除
     *@return
     */
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-System defined method
     *@param arrayPK 删除
     */
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), arrayPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *分配收入科目-User defined method
     *@param pk pk
     *@return
     */
    public boolean assign(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().assign(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反分配收入科目-User defined method
     *@param pk pk
     *@return
     */
    public boolean disassign(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().disassign(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *启用收入科目-User defined method
     *@param pk pk
     *@return
     */
    public boolean enable(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().enable(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *禁用收入科目-User defined method
     *@param pk pk
     *@return
     */
    public boolean disable(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().disable(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *科目引入-User defined method
     *@param cac 成本科目集合
     *@param addressId 引入目的地工程项目id
     */
    public void importDatas(IncomeAccountCollection cac, BOSUuid addressId) throws BOSException, EASBizException
    {
        try {
            getController().importDatas(getContext(), cac, addressId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *导入收入科目系统模板-User defined method
     *@param cac 成本科目集合
     *@return
     */
    public ArrayList importTemplateDatas(IncomeAccountCollection cac) throws BOSException, EASBizException
    {
        try {
            return getController().importTemplateDatas(getContext(), cac);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}