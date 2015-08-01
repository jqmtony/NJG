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

public class ProjectFacade extends AbstractBizCtrl implements IProjectFacade
{
    public ProjectFacade()
    {
        super();
        registerInterface(IProjectFacade.class, this);
    }
    public ProjectFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7640B361");
    }
    private ProjectFacadeController getController() throws BOSException
    {
        return (ProjectFacadeController)getBizController();
    }
    /**
     *获取历史版本工程项目集合-User defined method
     *@param curProjectID 当前工程项目id
     *@return
     */
    public HisProjectCollection getHisProjectCollection(String curProjectID) throws BOSException, EASBizException
    {
        try {
            return getController().getHisProjectCollection(getContext(), curProjectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *工程项目版本编辑-User defined method
     *@param hisProjectInfo 历史版本工程项目
     *@return
     */
    public boolean updateHisProjectInfo(HisProjectInfo hisProjectInfo) throws BOSException, EASBizException
    {
        try {
            return getController().updateHisProjectInfo(getContext(), hisProjectInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新当前工程项目-User defined method
     *@param curProjectInfo 当前工程项目
     *@return
     */
    public boolean updateCurProjectInfo(CurProjectInfo curProjectInfo) throws BOSException, EASBizException
    {
        try {
            return getController().updateCurProjectInfo(getContext(), curProjectInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *判断工程项目是否能够新增下级-User defined method
     *@param curProjectId 工程项目Id
     *@return
     */
    public Map canAddNew(String curProjectId) throws BOSException, EASBizException
    {
        try {
            return getController().canAddNew(getContext(), curProjectId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *检查是否可以修改一级工程项目是否开发项目属性-User defined method
     *@param ObjectValue 当前工程项目
     *@return
     */
    public boolean checkBeforeModifyIsDevPrj(CurProjectInfo ObjectValue) throws BOSException, EASBizException
    {
        try {
            return getController().checkBeforeModifyIsDevPrj(getContext(), ObjectValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}