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
     *������ҵ������Ӧ������ҵ��JOSN��ʽ����-User defined method
     *@param strBSID ҵ��ϵͳ����ID
     *@param strBOID ҵ��ʵ��ID
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
     *�鿴���ݹ�����Ϣ-User defined method
     *@param strBTID �������ͣ�ҵ���ID��
     *@param strBOID ����ID��ҵ�����ID��
     *@param strRelatedCode ����ҳǩ���루�磺SEL_Contract���㵥_��ͬ��Ϣ����
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
     *�ύ���-User defined method
     *@param strBSID ҵ��ϵͳID
     *@param strBOID ����ID
     *@param bSuccess ��������ʵ���Ƿ�ɹ�
     *@param iProcInstID ��ҵ������Ӧ�Ĵ���������ʵ��ID���������ʧ�ܣ����ֵ��Ч����0
     *@param procURL K2 ���ص�BPM����URL
     *@param strMessage ΪK2�ӿ��ṩ����Ϣ����
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
     *K2���������������󣨰�������ͨ������ͬ���ɾ����������ERP��Close�ӿ�,��ҵ��ϵͳ�㱨�������-User defined method
     *@param strBSID ҵ��ϵͳID
     *@param strBOID ҵ�����ID
     *@param iProcInstID ����ʵ��ID
     *@param eProcessInstanceResult �����������
     *@param strComment ΪK2�ӿ��ṩ����Ϣ����
     *@param dtTime ����ʱ��
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
     *BPM������д-User defined method
     *@param strBTID ��������
     *@param strBOID ����ID
     *@param strXML �������XML
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
     *��ȡ������Ŀ-User defined method
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
     *��ȡ����ϵͳģ����Ϣ-User defined method
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
     *@param pointID ������Ŀ���
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
     *��ȡBPM���������-����ERP������-User defined method
     *@param Domxml XML����
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
     *��ȡXML������Ϣ-����ERP-User defined method
     *@param Domxml XML���ݶ�
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