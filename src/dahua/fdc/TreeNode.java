package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.gcftbiaoa.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class TreeNode extends DataBase implements ITreeNode
{
    public TreeNode()
    {
        super();
        registerInterface(ITreeNode.class, this);
    }
    public TreeNode(Context ctx)
    {
        super(ctx);
        registerInterface(ITreeNode.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("47DFAA47");
    }
    private TreeNodeController getController() throws BOSException
    {
        return (TreeNodeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public TreeNodeInfo getTreeNodeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTreeNodeInfo(getContext(), pk);
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
    public TreeNodeInfo getTreeNodeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTreeNodeInfo(getContext(), pk, selector);
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
    public TreeNodeInfo getTreeNodeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTreeNodeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public TreeNodeCollection getTreeNodeCollection() throws BOSException
    {
        try {
            return getController().getTreeNodeCollection(getContext());
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
    public TreeNodeCollection getTreeNodeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTreeNodeCollection(getContext(), view);
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
    public TreeNodeCollection getTreeNodeCollection(String oql) throws BOSException
    {
        try {
            return getController().getTreeNodeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}