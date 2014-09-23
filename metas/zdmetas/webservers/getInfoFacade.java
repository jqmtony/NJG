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
}