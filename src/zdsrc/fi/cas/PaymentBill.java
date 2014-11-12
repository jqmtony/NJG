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
     *取值-System defined method
     *@param pk 取值
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
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
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
     *取值-System defined method
     *@param oql 取值
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
     *取集合-System defined method
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
     *取集合-System defined method
     *@param view 取集合
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
     *取集合-System defined method
     *@param oql 取集合
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
     *删除多条付款单方法 -User defined method
     *@param idSet 单据id集合
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
     *@param idSet 单据id集合
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
     *isWfInVoked(是否为工作流调用)，用来防止重复进行预算控制-User defined method
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
     *isWfInVoked(是否为工作流调用)，用来防止重复进行预算控制-User defined method
     *@param pk pk
     *@param isWfInVoked 是否为工作流调用
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
     *审批未通过-User defined method
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
     *由于在工作流中连续配置两个自动节点,有可能导致WF挂起,所以讲两个功能集中到一个节点中-User defined method
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
     *反审核-User defined method
     *@param idSet 单据ID集
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
     *反审核-User defined method
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
     *付款-User defined method
     *@param idSet 单据id
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
     *付款-User defined method
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
     *取消付款-User defined method
     *@param idSet 单据id集合
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
     *取消付款-User defined method
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
     *提交结算中心-User defined method
     *@param idSet 单据id集合
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
     *反提交结算中心-User defined method
     *@param idSet id集合
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
     *取消提交结算中心-User defined method
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
     *提交结算中心-User defined method
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
     *如果初始单据发生过任何后续业务，则不允许修改、删除： 判断的依据可以简化为：if 未锁定金额==历史未核销金额 then 未发生过 -User defined method
     *@param id 单据id
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
     *开票-User defined method
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
     *房地产审批-User defined method
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
     *房地产反审批-User defined method
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
     *房地产置审批中-User defined method
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
     *房地产置已审批状态-User defined method
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
     *房地产反审批-User defined method
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
     *提交银企互联，生成银行付款单-User defined method
     *@param idSet id集合
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
     *工作流中置已审批之后被打回的单据，需要将状态更改为已提交。-User defined method
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
     *付款-User defined method
     *@param idSet 单据id
     *@param commitToBe 银行提交成功标志
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
     *是否等日记账的付款-User defined method
     *@param idSet id集合
     *@param ifBookPayable 是否等日记账
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
     *是否等日记账的付款-User defined method
     *@param idSet id集合
     *@param ifBookPayable 是否等日记账
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
     *是否需要提交结算中心-User defined method
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
     *根据单据ID获取单据状态-User defined method
     *@param id 单据ID
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
     *批量提交-User defined method
     *@param idSet 单据idSet集合
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
     *取消提交银企互联-User defined method
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
     *审核-User defined method
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
     *反审核-User defined method
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
     *审核-User defined method
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
     *反审核-User defined method
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
     *是否能生成的判断-User defined method
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
     *批量保存付款单-User defined method
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
     *提交银企互联包装方法 -User defined method
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
     *提交银企的反写方法-User defined method
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
     *批量保存付款单-User defined method
     *@param paymentBillCollection 付款单
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
     *@param paymentBillCollection 付款单
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
     *合并付款-User defined method
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
     *工作流节点专用-User defined method
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
     *自动提交银企-User defined method
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
     *付款单工作流自动付款专用-User defined method
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
     *能否提交结算中心-User defined method
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
     *@param acctColl 银行账号集合
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
     *单个结算单下载生成收款单-User defined method
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
     *批量下载结算单-User defined method
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
     *支票填开提交-User defined method
     *@param newChequeNumber 新填开的支票号
     *@param paymentBillID 付款单ID
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
     *修改凭证-User defined method
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
     *调用框架的update方法，防止在调用本地update方法的时候出现死循环-User defined method
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
     *校验银企信息-User defined method
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
     *校验不抛出异常-User defined method
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
     *校验不抛出异常-User defined method
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
     *单据提交（批量框架用）-User defined method
     *@param pk 单据PK
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
     *序时簿界面取消付款，为销售信用接口单独增加-User defined method
     *@param idSet 单据ID集合
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
     *判断期间是否为调整期-User defined method
     *@param idList 单据ID
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
     *返回包含两种类型的Map的Set，一种为公对私的付款单，一种为非公对私的付款单-User defined method
     *@param idSet 付款单id集合
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
     *批量提交公对私付款单-User defined method
     *@param payList 付款单集合
     *@param accountBank 银行账号
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