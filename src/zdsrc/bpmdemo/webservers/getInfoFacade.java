package com.kingdee.eas.bpmdemo.webservers;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.bpmdemo.webservers.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.Date;

public class getInfoFacade extends AbstractBizCtrl implements IgetInfoFacade
{
    public getInfoFacade()
    {
        super();
        registerInterface(IgetInfoFacade.class, this);
    }
    public getInfoFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IgetInfoFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2969D79D");
    }
    private getInfoFacadeController getController() throws BOSException
    {
        return (getInfoFacadeController)getBizController();
    }
    /**
     *返回与业务对象对应的所有业务JOSN格式数据-User defined method
     *@param strBSID 业务系统类型ID
     *@param strBOID 业务实例ID
     *@return
     */
    public String[] GetbillInfo(String strBSID, String strBOID) throws BOSException
    {
        try {
            return getController().GetbillInfo(getContext(), strBSID, strBOID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *查看单据关联信息-User defined method
     *@param strBTID 单据类型（业务表单ID）
     *@param strBOID 单据ID（业务对象ID）
     *@param strRelatedCode 关联页签编码（如：SEL_Contract结算单_合同信息；）
     *@return
     */
    public String[] GetrRelatedBillInfo(String strBTID, String strBOID, String strRelatedCode) throws BOSException
    {
        try {
            return getController().GetrRelatedBillInfo(getContext(), strBTID, strBOID, strRelatedCode);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *提交结果-User defined method
     *@param strBSID 业务系统ID
     *@param strBOID 单据ID
     *@param bSuccess 创建流程实例是否成功
     *@param iProcInstID 该业务对象对应的创建的流程实例ID。如果创建失败，则该值无效，置0
     *@param procURL K2 返回的BPM流程URL
     *@param strMessage 为K2接口提供的信息反馈
     *@return
     */
    public String[] SubmitResult(String strBSID, String strBOID, boolean bSuccess, int iProcInstID, String procURL, String strMessage) throws BOSException
    {
        try {
            return getController().SubmitResult(getContext(), strBSID, strBOID, bSuccess, iProcInstID, procURL, strMessage);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *K2在流程审批结束后（包括审批通过，不同意或被删除），调用ERP的Close接口,向业务系统汇报审批结果-User defined method
     *@param strBSID 业务系统ID
     *@param strBOID 业务对象ID
     *@param iProcInstID 流程实例ID
     *@param eProcessInstanceResult 流程审批结果
     *@param strComment 为K2接口提供的信息反馈
     *@param dtTime 审批时间
     *@return
     */
    public String[] ApproveClose(String strBSID, String strBOID, int iProcInstID, String eProcessInstanceResult, String strComment, Date dtTime) throws BOSException
    {
        try {
            return getController().ApproveClose(getContext(), strBSID, strBOID, iProcInstID, eProcessInstanceResult, strComment, dtTime);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *BPM审批反写-User defined method
     *@param strBTID 单据类型
     *@param strBOID 单据ID
     *@param strXML 单据相关XML
     *@return
     */
    public String[] ApproveBack(String strBTID, String strBOID, String strXML) throws BOSException
    {
        try {
            return getController().ApproveBack(getContext(), strBTID, strBOID, strXML);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取工程项目-User defined method
     *@return
     */
    public String[] GetcurProject() throws BOSException
    {
        try {
            return getController().GetcurProject(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取会议系统模板信息-User defined method
     *@return
     */
    public String[] GetDemo() throws BOSException
    {
        try {
            return getController().GetDemo(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Getpoint-User defined method
     *@param pointID 工程项目编号
     *@return
     */
    public String[] Getpoint(String pointID) throws BOSException
    {
        try {
            return getController().Getpoint(getContext(), pointID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *读取BPM穿入的数据-填入ERP数据中-User defined method
     *@param Domxml XML数据
     */
    public void GetProgressReport(String Domxml) throws BOSException
    {
        try {
            getController().GetProgressReport(getContext(), Domxml);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取XML反馈信息-填入ERP-User defined method
     *@param Domxml XML数据额
     */
    public void GetTaskEvalation(String Domxml) throws BOSException
    {
        try {
            getController().GetTaskEvalation(getContext(), Domxml);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}