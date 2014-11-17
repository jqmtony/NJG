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
     *扣减预算-User defined method
     *@param projectNumber 项目编码
     *@param year 年度
     *@param budgetNumber 预算编码
     *@param amount 金额
     *@param stag （招标申报、中标、合同、付款、结算）
     *@param isBack 是否返还节省预算
     *@param backAmount 节省/超出金额
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
     *返还预算-User defined method
     *@param projectNumber 项目编码
     *@param year 年度
     *@param budgetNumber 预算编码
     *@param amount 金额
     *@param stag （招标申报、中标、合同、付款、结算）
     *@param isBack 是否返还节省预算
     *@param backAmount 节省/超出金额
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