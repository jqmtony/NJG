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
     *��ȡ��ʷ�汾������Ŀ����-User defined method
     *@param curProjectID ��ǰ������Ŀid
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
     *������Ŀ�汾�༭-User defined method
     *@param hisProjectInfo ��ʷ�汾������Ŀ
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
     *���µ�ǰ������Ŀ-User defined method
     *@param curProjectInfo ��ǰ������Ŀ
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
     *�жϹ�����Ŀ�Ƿ��ܹ������¼�-User defined method
     *@param curProjectId ������ĿId
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
     *����Ƿ�����޸�һ��������Ŀ�Ƿ񿪷���Ŀ����-User defined method
     *@param ObjectValue ��ǰ������Ŀ
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