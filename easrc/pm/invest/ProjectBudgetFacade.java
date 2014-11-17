package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.port.pm.invest.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public class ProjectBudgetFacade extends AbstractBizCtrl implements IProjectBudgetFacade
{
    public ProjectBudgetFacade()
    {
        super();
        registerInterface(IProjectBudgetFacade.class, this);
    }
    public ProjectBudgetFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectBudgetFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1FC99E48");
    }
    private ProjectBudgetFacadeController getController() throws BOSException
    {
        return (ProjectBudgetFacadeController)getBizController();
    }
    /**
     *�ۼ�Ԥ��-User defined method
     *@param projectNumber ��Ŀ����
     *@param year ���
     *@param budgetNumber Ԥ�����
     *@param amount ���
     *@param stag ���б��걨���бꡢ��ͬ��������㣩
     *@param isBack �Ƿ񷵻���ʡԤ��
     *@param backAmount ��ʡ/�������
     *@return
     */
    public String[] subBudgetAmount(String projectNumber, String year, String budgetNumber, String amount, String stag, boolean isBack, String backAmount) throws BOSException
    {
        try {
            return getController().subBudgetAmount(getContext(), projectNumber, year, budgetNumber, amount, stag, isBack, backAmount);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ԥ��-User defined method
     *@param projectNumber ��Ŀ����
     *@param year ���
     *@param budgetNumber Ԥ�����
     *@param amount ���
     *@param stag ���б��걨���бꡢ��ͬ��������㣩
     *@param isBack �Ƿ񷵻���ʡԤ��
     *@param backAmount ��ʡ/�������
     *@return
     */
    public String[] backBudgetAmount(String projectNumber, String year, String budgetNumber, String amount, String stag, boolean isBack, String backAmount) throws BOSException
    {
        try {
            return getController().backBudgetAmount(getContext(), projectNumber, year, budgetNumber, amount, stag, isBack, backAmount);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}