package com.kingdee.eas.fi.cas;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.basedata.assistant.AccountBankCollection;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fm.be.BankPayingBillInfo;
import com.kingdee.eas.fm.nt.ChequeInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fi.cas.app.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public class PaymentBill extends RecPayBill implements IPaymentBill
{
    public PaymentBill()
    {
        super();
        registerInterface(IPaymentBill.class, this);
    }
    public PaymentBill(Context ctx)
    {
        super(ctx);
        registerInterface(IPaymentBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("40284E81");
    }
    private PaymentBillController getController() throws BOSException
    {
        return (PaymentBillController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PaymentBillInfo getPaymentBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentBillInfo(getContext(), pk);
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
    public PaymentBillInfo getPaymentBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentBillInfo(getContext(), pk, selector);
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
    public PaymentBillInfo getPaymentBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PaymentBillCollection getPaymentBillCollection() throws BOSException
    {
        try {
            return getController().getPaymentBillCollection(getContext());
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
    public PaymentBillCollection getPaymentBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPaymentBillCollection(getContext(), view);
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
    public PaymentBillCollection getPaymentBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getPaymentBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ������������� -User defined method
     *@param idSet ����id����
     *@return
     */
    public int batchRemove(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().batchRemove(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     * -User defined method
     *@param idSet ����id����
     */
    public void audit(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *isWfInVoked(�Ƿ�Ϊ����������)��������ֹ�ظ�����Ԥ�����-User defined method
     *@param pk pk
     */
    public void audit4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().audit4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *isWfInVoked(�Ƿ�Ϊ����������)��������ֹ�ظ�����Ԥ�����-User defined method
     *@param pk pk
     *@param isWfInVoked �Ƿ�Ϊ����������
     */
    public void audit4WF(IObjectPK pk, boolean isWfInVoked) throws BOSException, EASBizException
    {
        try {
            getController().audit4WF(getContext(), pk, isWfInVoked);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����δͨ��-User defined method
     *@param pk pk
     */
    public void auditUnapprove(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().auditUnapprove(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ڹ��������������������Զ��ڵ�,�п��ܵ���WF����,���Խ��������ܼ��е�һ���ڵ���-User defined method
     *@param pk pk
     *@return
     */
    public boolean auditAndCommitSettle4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().auditAndCommitSettle4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����-User defined method
     *@param idSet ����ID��
     */
    public void antiAudit(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().antiAudit(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����-User defined method
     *@param pk pk
     */
    public void antiAudit4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().antiAudit4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param idSet ����id
     */
    public void pay(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().pay(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param pk pk
     */
    public void pay4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().pay4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ������-User defined method
     *@param idSet ����id����
     */
    public void cancelPay(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().cancelPay(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ������-User defined method
     *@param pk pk
     */
    public void cancelPay4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().cancelPay4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ύ��������-User defined method
     *@param idSet ����id����
     */
    public void commitSettle(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().commitSettle(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ύ��������-User defined method
     *@param idSet id����
     */
    public void cancelCommitSettle(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().cancelCommitSettle(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ���ύ��������-User defined method
     *@param pk pk
     */
    public void cancelCommitST4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().cancelCommitST4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ύ��������-User defined method
     *@param pk pk
     */
    public void commitSettle4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().commitSettle4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ʼ���ݷ������κκ���ҵ���������޸ġ�ɾ���� �жϵ����ݿ��Լ�Ϊ��if δ�������==��ʷδ������� then δ������ -User defined method
     *@param id ����id
     */
    public void checkIsCanEditInit(String id) throws BOSException, EASBizException
    {
        try {
            getController().checkIsCanEditInit(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��Ʊ-User defined method
     *@param ids ids
     *@param cheque cheque
     */
    public void writeOff(Set ids, ChequeInfo cheque) throws BOSException, EASBizException
    {
        try {
            getController().writeOff(getContext(), ids, cheque);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ز�����-User defined method
     *@param idList idList
     *@return
     */
    public boolean audit4FDC(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().audit4FDC(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ز�������-User defined method
     *@param idList idList
     *@return
     */
    public boolean antiAudit4FDC(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().antiAudit4FDC(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ز���������-User defined method
     *@param billId billId
     */
    public void setAuditing4FDC(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setAuditing4FDC(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ز���������״̬-User defined method
     *@param billId billId
     */
    public void setAudited4FDC(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setAudited4FDC(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ز�������-User defined method
     *@param billId billId
     */
    public void antiAudit4FDC(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().antiAudit4FDC(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ύ���������������и��-User defined method
     *@param idSet id����
     *@return
     */
    public IObjectPK commitToBE(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().commitToBE(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������������֮�󱻴�صĵ��ݣ���Ҫ��״̬����Ϊ���ύ��-User defined method
     *@param billId billId
     */
    public void setSubmit4FDC(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setSubmit4FDC(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param idSet ����id
     *@param commitToBe �����ύ�ɹ���־
     */
    public void pay(Set idSet, boolean commitToBe) throws BOSException, EASBizException
    {
        try {
            getController().pay(getContext(), idSet, commitToBe);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ƿ���ռ��˵ĸ���-User defined method
     *@param idSet id����
     *@param ifBookPayable �Ƿ���ռ���
     */
    public void payForBook(Set idSet, boolean ifBookPayable) throws BOSException, EASBizException
    {
        try {
            getController().payForBook(getContext(), idSet, ifBookPayable);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ƿ���ռ��˵ĸ���-User defined method
     *@param idSet id����
     *@param ifBookPayable �Ƿ���ռ���
     *@param isInvokeWF isInvokeWF
     */
    public void payForBook(Set idSet, boolean ifBookPayable, boolean isInvokeWF) throws BOSException, EASBizException
    {
        try {
            getController().payForBook(getContext(), idSet, ifBookPayable, isInvokeWF);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ƿ���Ҫ�ύ��������-User defined method
     *@param id id
     *@return
     */
    public boolean isNeedCommitST(String id) throws BOSException, EASBizException
    {
        try {
            return getController().isNeedCommitST(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ݵ���ID��ȡ����״̬-User defined method
     *@param id ����ID
     *@return
     */
    public int getBillStatus(String id) throws BOSException, EASBizException
    {
        try {
            return getController().getBillStatus(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ύ-User defined method
     *@param idSet ����idSet����
     */
    public void submit(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().submit(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ���ύ������-User defined method
     *@param id id
     */
    public void cancelCommitToBE(String id) throws BOSException, EASBizException
    {
        try {
            getController().cancelCommitToBE(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���-User defined method
     *@param idSet idSet
     */
    public void approve(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().approve(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����-User defined method
     *@param idSet idSet
     */
    public void untiApprove(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().untiApprove(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���-User defined method
     *@param pk pk
     */
    public void approve4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().approve4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����-User defined method
     *@param pk pk
     */
    public void untiApprove4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().untiApprove4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ƿ������ɵ��ж�-User defined method
     *@param paramMap paramMap
     *@return
     */
    public Map canVoucherParam(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().canVoucherParam(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������渶�-User defined method
     *@param idList idList
     *@return
     */
    public Map saveBatch(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().saveBatch(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ύ��������װ���� -User defined method
     *@param idSet idSet
     *@param bankPayingbillId bankPayingbillId
     */
    public void commitToBeAll(Set idSet, IObjectPK bankPayingbillId) throws BOSException, EASBizException
    {
        try {
            getController().commitToBeAll(getContext(), idSet, bankPayingbillId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ύ����ķ�д����-User defined method
     *@param idSet idSet
     *@param bankPayingbillId bankPayingbillId
     */
    public void reserveForBE(Set idSet, String bankPayingbillId) throws BOSException, EASBizException
    {
        try {
            getController().reserveForBE(getContext(), idSet, bankPayingbillId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������渶�-User defined method
     *@param paymentBillCollection ���
     */
    public void saveBatch(PaymentBillCollection paymentBillCollection) throws BOSException, EASBizException
    {
        try {
            getController().saveBatch(getContext(), paymentBillCollection);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *submitBatch-User defined method
     *@param paymentBillCollection ���
     */
    public void submitBatch(PaymentBillCollection paymentBillCollection) throws BOSException, EASBizException
    {
        try {
            getController().submitBatch(getContext(), paymentBillCollection);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ϲ�����-User defined method
     *@param idSet ID
     */
    public void unitePay(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().unitePay(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������ڵ�ר��-User defined method
     *@param pk pk
     */
    public void commitToBE4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().commitToBE4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Զ��ύ����-User defined method
     *@param pk pk
     */
    public void autoCommitToBE4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().autoCommitToBE4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������Զ�����ר��-User defined method
     *@param pk pk
     */
    public void autoPay4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().autoPay4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ܷ��ύ��������-User defined method
     *@param pk pk
     *@return
     */
    public boolean canCommitToBE(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().canCommitToBE(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *recBeInfo-User defined method
     *@param OBJPKColl OBJPKColl
     *@param acctColl �����˺ż���
     *@return
     */
    public PaymentBillCollection recBeInfo(Set OBJPKColl, AccountBankCollection acctColl) throws BOSException, EASBizException
    {
        try {
            return getController().recBeInfo(getContext(), OBJPKColl, acctColl);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������㵥���������տ-User defined method
     *@param info info
     *@return
     */
    public Set dlFromSettle(IObjectValue info) throws BOSException, EASBizException
    {
        try {
            return getController().dlFromSettle(getContext(), info);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������ؽ��㵥-User defined method
     *@param idSet idSet
     *@return
     */
    public Map dlFromStBatch(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().dlFromStBatch(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *֧Ʊ��ύ-User defined method
     *@param newChequeNumber �����֧Ʊ��
     *@param paymentBillID ���ID
     */
    public void chequeReimburse(String newChequeNumber, String paymentBillID) throws BOSException, EASBizException
    {
        try {
            getController().chequeReimburse(getContext(), newChequeNumber, paymentBillID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�޸�ƾ֤-User defined method
     *@param billId billId
     */
    public void editVouch(String billId) throws BOSException, EASBizException
    {
        try {
            getController().editVouch(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ÿ�ܵ�update��������ֹ�ڵ��ñ���update������ʱ�������ѭ��-User defined method
     *@param pk pk
     *@param value value
     */
    public void getSuperUpdate(IObjectPK pk, IObjectValue value) throws BOSException, EASBizException
    {
        try {
            getController().getSuperUpdate(getContext(), pk, value);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *У��������Ϣ-User defined method
     *@param idSet idSet
     *@return
     */
    public List verifyBeInfo(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().verifyBeInfo(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *У�鲻�׳��쳣-User defined method
     *@param idSet idSet
     *@return
     */
    public Map paySilent(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().paySilent(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *У�鲻�׳��쳣-User defined method
     *@param idSet idSet
     *@return
     */
    public Map cancelPaySilent(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().cancelPaySilent(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updatePrintCount-User defined method
     *@param billMaps billMaps
     *@return
     */
    public boolean updatePrintCount(Map billMaps) throws BOSException, EASBizException
    {
        try {
            return getController().updatePrintCount(getContext(), billMaps);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ύ����������ã�-User defined method
     *@param pk ����PK
     */
    public void submitBill(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().submitBill(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ʱ������ȡ�����Ϊ�������ýӿڵ�������-User defined method
     *@param idSet ����ID����
     */
    public void cancelPayForCredit(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().cancelPayForCredit(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ж��ڼ��Ƿ�Ϊ������-User defined method
     *@param idList ����ID
     */
    public void checkIsAdjustPeriod(List idList) throws BOSException, EASBizException
    {
        try {
            getController().checkIsAdjustPeriod(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *commitToBENew-User defined method
     *@param paymentBill paymentBill
     *@param isNeedCommitToBank isNeedCommitToBank
     *@return
     */
    public IObjectPK commitToBENew(PaymentBillInfo paymentBill, boolean isNeedCommitToBank) throws BOSException, EASBizException
    {
        try {
            return getController().commitToBENew(getContext(), paymentBill, isNeedCommitToBank);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *commitToBENewBatch-User defined method
     *@param idSet idSet
     *@return
     */
    public ArrayList commitToBENewBatch(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().commitToBENewBatch(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *reserveForBeException-User defined method
     *@param bankPayingbillId bankPayingbillId
     */
    public void reserveForBeException(String bankPayingbillId) throws BOSException, EASBizException
    {
        try {
            getController().reserveForBeException(getContext(), bankPayingbillId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *reserveForOtherException-User defined method
     *@param info info
     *@param returnMsg returnMsg
     */
    public void reserveForOtherException(BankPayingBillInfo info, String returnMsg) throws BOSException, EASBizException
    {
        try {
            getController().reserveForOtherException(getContext(), info, returnMsg);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ذ����������͵�Map��Set��һ��Ϊ����˽�ĸ����һ��Ϊ�ǹ���˽�ĸ��-User defined method
     *@param idSet ���id����
     *@return
     */
    public Map divisionPayBillType(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().divisionPayBillType(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ύ����˽���-User defined method
     *@param payList �������
     *@param accountBank �����˺�
     */
    public void payPrivateToBE(List payList, AccountBankInfo accountBank) throws BOSException, EASBizException
    {
        try {
            getController().payPrivateToBE(getContext(), payList, accountBank);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}