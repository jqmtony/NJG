package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.Context;
import com.kingdee.bos.IBOSObject;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosCollection;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.IContractExecInfos;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillException;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctFacadeFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetConstants;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCPaymentBillHelper;
import com.kingdee.eas.fdc.finance.FDCPaymentBillInfo;
import com.kingdee.eas.fdc.finance.PayRequestSplitFactory;
import com.kingdee.eas.fdc.finance.ShowDeductOfPartABillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.eas.fdc.schedule.FDCSCHUtils;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.UrgentDegreeEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.ma.budget.BudgetCtrlFacadeFactory;
import com.kingdee.eas.ma.budget.IBudgetCtrlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

public class PayRequestBillControllerBean extends AbstractPayRequestBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.PayRequestBillControllerBean");
    
    private BOSUuid payBillId;
    
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		super._save(ctx, pk, model);
		
		//R101207-373 �������뵥�޸��ύʱ����Ҫ���¼���������뵥�ġ���ͬ�ڹ����ۼ����롱��ֵ  by zhiyuan_tang 2010/12/20
		recountReqAmt(ctx, ((PayRequestBillInfo)model).getContractId());
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException
	{
		IObjectPK pk = super._save(ctx, model);
		
		//R101207-373 �������뵥�޸��ύʱ����Ҫ���¼���������뵥�ġ���ͬ�ڹ����ۼ����롱��ֵ  by zhiyuan_tang 2010/12/20
		recountReqAmt(ctx, ((PayRequestBillInfo)model).getContractId());
		
		if(pk!=null){
			
		}
		return pk;
		
	}

	private void setPropsForBill(Context ctx, PayRequestBillInfo info) {
		
		FullOrgUnitInfo orgUnit = ContextUtil.getCurrentCostUnit(ctx).castToFullOrgUnitInfo();;
		info.setOrgUnit(orgUnit);
		CtrlUnitInfo currentCtrlUnit = ContextUtil.getCurrentCtrlUnit(ctx);
		info.setCU(currentCtrlUnit);
	}

	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
//		setPropsForBill(ctx, (PayRequestBillInfo)model);
//		checkBill(ctx, model);
		super._addnew(ctx, pk, model);
		//�ۿ��뽱��Ĭ������,����Ĭ�ϲ�����
		Object flag=ctx.get("fromwebservice");
		if(flag==null){
			addDeductBill((PayRequestBillInfo)model, ctx);
			addGuerdonBill((PayRequestBillInfo)model, ctx);
			addCompensationBill((PayRequestBillInfo)model, ctx);
		}
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
//		setPropsForBill(ctx, (PayRequestBillInfo)model);
//		checkBill(ctx, model);
		//��¼info����Դ
		PayRequestBillInfo info  =(PayRequestBillInfo)model;
		if(info.getSource()==null && info.getContractId()!=null){
			info.setSource(BOSUuid.read(info.getContractId()).getType().toString());
		}
		if(info.getContractId()!=null){
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(new FilterInfo());
			//EntityViewInfo���ܻᵼ��info.getContractId()�Զ�����NVarchar��ת������»�ȡ�������ݣ�����toString()ȷ��������ȷ��
			view.getFilter().appendFilterItem("contractId", info.getContractId().toString());
			view.getSelector().add("id");
			ContractBaseDataCollection c=ContractBaseDataFactory.getLocalInstance(ctx).getContractBaseDataCollection(view);
			if(c!=null&&c.size()==1){
				info.setContractBase(c.get(0));
			}
		}
		IObjectPK objectPK = super._addnew(ctx, model);
		
		if(objectPK!=null){
			/*****
			 * ���¹����ļ׹�����ȷ�ϵ����ۼ�������
			 */
			if(info!=null&&info.getConfirmEntry()!=null){
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.setPrepareStatementSql("update T_PAM_MaterialConfirmBill set FToDateReqAmt=(select sum(FReqAmount) from T_CON_PayReqConfirmEntry where FConfirmBillID=?) where FID=? ");
				builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
				boolean hasSQL = false;
				for(int i=0;i<info.getConfirmEntry().size();i++){
					if(info.getConfirmEntry().get(i).getConfirmBill()!=null){
						builder.addParam(info.getConfirmEntry().get(i).getConfirmBill().getId().toString());
						builder.addParam(info.getConfirmEntry().get(i).getConfirmBill().getId().toString());
						builder.addBatch();
						hasSQL = true;
					}
				}
				if(hasSQL){
					builder.executeBatch();
				}
				builder = null;
			}
		}
		
		Object flag=ctx.get("fromwebservice");
		if(flag==null){
			addDeductBill((PayRequestBillInfo)model, ctx);
			addGuerdonBill((PayRequestBillInfo)model, ctx);
			addCompensationBill((PayRequestBillInfo)model, ctx);
		}
		
		return objectPK;
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//�ύǰ���
		boolean hasUsed=FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_ACCTBUDGET);
		checkBillForSubmit( ctx,model);
		final IObjectPK pk = super._submit(ctx, model);
		
		//R101207-373 �������뵥�޸��ύʱ����Ҫ���¼���������뵥�ġ���ͬ�ڹ����ۼ����롱��ֵ  by zhiyuan_tang 2010/12/20
		recountReqAmt(ctx, ((PayRequestBillInfo)model).getContractId());
		
		if(hasUsed){
			PayRequestSplitFactory.getLocalInstance(ctx).autoSplit(pk.toString());
		}
		return pk;
	}


	protected void _audit(Context ctx, List idList) throws BOSException, EASBizException {
		if(idList==null) return;
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();			
			audit(ctx, BOSUuid.read(id));
		}	
	}

	/**
	 * 
	 * ������������
	 * @author:jelon
	 * @see com.kingdee.eas.fdc.contract.app.AbstractContractBillControllerBean#_unAudit(com.kingdee.bos.Context, java.util.List)
	 */
	protected void _unAudit(Context ctx, List idList) throws BOSException, EASBizException {
		if(idList==null) return;
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			unAudit(ctx, BOSUuid.read(id));
		}	
	}
	
	private String getZbhetId(Context ctx, String proId) throws BOSException, SQLException{
		String id = null;
		FDCSQLBuilder fdc = new FDCSQLBuilder(ctx);
		fdc.clear();
		fdc.appendSql("select contract.fid from T_CON_ContractBill contract left outer join T_FDC_ContractType ctype on " );
		fdc.appendSql("contract.FCONTRACTTYPEID=ctype.FID where contract.FCURPROJECTID=? and ctype.fname_l2='[����]' ");
		fdc.appendSql("and contract.fcontrolunitid=? and contract.FcontractPropert='DIRECT'");
		fdc.addParam(proId);
		fdc.addParam(ContextUtil.getCurrentCtrlUnit(ctx).getId().toString());
		IRowSet sets = fdc.executeQuery();
		if(sets.next()){
			id = sets.getString(1);
		}
		return id;
	}
	
	/** ��ȡ����������ɵı��� 
     * @throws UuidException 
     * @throws BOSException 
     * @throws EASBizException */
	public String getAutoCode(Context ctx ,IObjectValue objValue) throws EASBizException, BOSException, UuidException {
		    String NumberCode = "";
		    String companyId = "";
			com.kingdee.eas.base.codingrule.ICodingRuleManager codeRuleMgr = null;
			if(ctx == null){
				companyId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
				codeRuleMgr = CodingRuleManagerFactory.getRemoteInstance();
			}else{
				companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
				codeRuleMgr = CodingRuleManagerFactory.getLocalInstance(ctx);
			}

			if (codeRuleMgr.isExist(objValue, companyId)) {
				if (codeRuleMgr.isUseIntermitNumber(objValue, companyId)) {
					NumberCode = codeRuleMgr.readNumber(objValue, companyId);
				} else {
					NumberCode = codeRuleMgr.getNumber(objValue, companyId);
				}
			}
		return NumberCode;
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		PayRequestBillInfo payReqInfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(billId));
		BOSObjectType bosType = BOSUuid.read(payReqInfo.getContractId()).getType();
		if(bosType != null && bosType.equals(new ContractBillInfo().getBOSType())) {
			ContractBillInfo astContract = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(payReqInfo.getContractId()));
			String typeName = ContractTypeFactory.getLocalInstance(ctx).getContractTypeInfo(new ObjectUuidPK(astContract.getContractType().getId())).getName();
			//throw new EASBizException(new NumericExceptionSubItem("100", "�ܰ���ͬ�������뵥��ϵͳ�Զ����ɣ��û����ܽ�����˲�����"));
			if("[ʩ��]".equals(typeName) || "[����]".equals(typeName) || "[�ְ�]".equals(typeName)) {
				realAudit(ctx, billId);
				//���ܰ�������һ���������뵥
				String zbhtId = null;
				try {
					zbhtId = getZbhetId(ctx, payReqInfo.getCurProject().getId().toString());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				//main contract
				ContractBillInfo mainContract = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(zbhtId));
				payReqInfo.setHasClosed(false);
				//auto code number for pay request
				payReqInfo.setNumber(getAutoCode(ctx, payReqInfo));
				//SupplierInfo supplier = SupplierFactory.getLocalInstance(ctx).getSupplierInfo(new ObjectUuidPK(mainContract.getPartB().getId()));
				payReqInfo.setRealSupplier(mainContract.getPartB());
				payReqInfo.setSupplier(mainContract.getPartB());
				payReqInfo.setSourceBillId(payReqInfo.getId().toString());
				String astContractNum = astContract.getNumber();
				//�ְ���ͬ�е��ҷ��������Ǹ������뵥���տλ�����������뵥���տλ�����޸ģ���������߲�һ�������
				//�ҵĴ����ǰ��շְ���ͬ�е��ҷ���Ȼ�󽫺�ͬ�����д+�ҷ�������д�ڱ�ע��
				//SupplierInfo supplier = SupplierFactory.getLocalInstance(ctx).getSupplierInfo(new ObjectUuidPK(astContract.getPartB().getId()));
				if("[ʩ��]".equals(typeName))
					payReqInfo.setMoneyDesc(astContractNum.substring(astContractNum.length()-5)+payReqInfo.getProcess());
				else
					payReqInfo.setMoneyDesc(astContractNum.substring(astContractNum.length()-5));
				payReqInfo.setContractBase(mainContract.getContractBaseData());
				payReqInfo.setContractId(mainContract.getId().toString());
				payReqInfo.setContractName(mainContract.getName());
				payReqInfo.setContractNo(mainContract.getNumber());
				payReqInfo.setContractPrice(mainContract.getAmount());
				payReqInfo.setState(FDCBillStateEnum.SUBMITTED);
				payReqInfo.setId(BOSUuid.create(payReqInfo.getBOSType()));
				PayRequestBillEntryCollection entrys = payReqInfo.getEntrys();
				PayRequestBillEntryInfo tempEntryInfo = null;
				for(int i=0; i<entrys.size(); i++){
					tempEntryInfo = entrys.get(i);
					tempEntryInfo.setId(BOSUuid.create(tempEntryInfo.getBOSType()));
					tempEntryInfo.setPaymentBill(null);
					tempEntryInfo.setParent(payReqInfo);
				}
				payReqInfo.setLatestPrice(mainContract.getAmount());
				PayRequestBillFactory.getLocalInstance(ctx).addnew(payReqInfo);
				//�����ɵ����뵥���ɸ��
				realAudit(ctx, payReqInfo.getId());
			}
			else {
				realAudit(ctx, billId);
			}
		}else {
			realAudit(ctx, billId);
		}
//		realAudit(ctx, billId);
	}
	//modify by yxl 20151215 ��ԭ��������߼��ó���������֮ǰ�Ը������뵥�޸ģ�֮ǰ�Ƿ���beanEx�
	private void realAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException, PayRequestBillException {
		checkBillForAudit( ctx,billId,null);
		
		PayRequestBillInfo billInfo = new PayRequestBillInfo();
		billInfo.setId(billId);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		// �������
		billInfo.setAuditTime(DateTimeUtils.truncateDate(new Date()));
		// ״̬
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		_updatePartial(ctx, billInfo, selector);
		//����ۿ���,��Ϊ��������ѡ���ʱ��ʹ���
		selector = new SelectorItemCollection();
		selector.add("contractId");
		selector.add("*");
		selector.add("curProject.id");
		//ȡ��������Ŀ�Ĳ�����֯�����ɸ��ʱ�á� by sxhong 2009-06-04 18:28:23
		selector.add("curProject.fullOrgUnit.id");
		selector.add("orgUnit.id");
		selector.add("realSupplier.number");
		selector.add("realSupplier.name");
		selector.add("supplier.number");
		selector.add("supplier.name");
		selector.add("CU.name");
		selector.add("CU.number");
		//�ڼ�
		selector.add("period.number");
		selector.add("period.beginDate");
		selector.add("period.endDate");
		// by tim_gao ���� 
		selector.add("currency.*");
		
		PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getLocalInstance(ctx)
			.getPayRequestBillInfo(new ObjectUuidPK(billId),selector);
		//payRequestBillInfo.getRealSupplier().getName();
		
		//��ֹ�����ۼ�ʵ���ڸ������뵥����������ύ��ʱ��ȡ���Ǻ�ͬ�ϵ�prjPriceInConPaid������ֻ�б�������ύ��ʱ��ŻὫ��ͬ�ϵ�prjPriceInConPaid
		//ͬ�����浽�������뵥�ϵ�lstPrjAllPaidAmt�ֶ���ȥ.ϵͳ��˴��������³������������:B�������뵥��A�������뵥û�и���֮ǰ���ύ�ˣ���ΪA��û�и���
		//��ͬ�ϵ� prjPriceInConPaidΪ�գ���ôB�ڱ����ύ��ʱ��ͬ����B�������뵥��ֵ��Ϊ�գ���ʱ��A���Ȼ������B���ֽ�ֹ�����ۼ�ʵ������Ϊ�ա����
		//��ʱ��B�������뵥�������ύһ�¾ͺ���.
		//Ϊ�˷�ֹ����������������ǿ�����������ʱ�������·�ʽ��ʽ����һ��                  by cassiel_peng 2010-1-5
		
		FDCSQLBuilder _builder=new FDCSQLBuilder(ctx);
		_builder.appendSql("select fprjPriceInConPaid as amount from T_CON_ContractBill where fid=?");
		_builder.addParam(payRequestBillInfo.getContractId());
		IRowSet rowSet = _builder.executeQuery();
		if(rowSet!=null&&rowSet.size()==1){
			try {
				rowSet.next();
				billInfo.setLstPrjAllPaidAmt(rowSet.getBigDecimal("amount"));
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
				
			}
		}
		SelectorItemCollection _selector = new  SelectorItemCollection();
		_selector.add("lstPrjAllPaidAmt");
		_updatePartial(ctx, billInfo, _selector); 
		
		boolean hasConPlan = FDCUtils.getBooleanValue4FDCParamByKey(ctx, payRequestBillInfo.getOrgUnit().getId().toString(),
				FDCConstants.FDC_PARAM_CONPAYPLAN);
		 // ���ı�û�и���ƻ�..�����ı���ͬ�������Զ����������ĸ������뵥����
		if (hasConPlan && FDCUtils.isContractBill(ctx, payRequestBillInfo.getContractId())) {
			//R140610-0199	modified by ken_liu...��У��ƻ������Ƿ�Ϊ0������ҪУ���Ƿ�ѡ���˼ƻ���
			/*if (payRequestBillInfo.getCurPlannedPayment() == null || FDCConstants.ZERO.compareTo(payRequestBillInfo.getCurPlannedPayment()) == 0) {
				throw new PayRequestBillException(PayRequestBillException.MUSTCONPAYPLAN);
			}*/
			if (payRequestBillInfo.getPlanHasCon() == null && payRequestBillInfo.getPlanUnCon()==null) {
				throw new PayRequestBillException(PayRequestBillException.MUSTCONPAYPLAN);
			}
		}
		
		//��ԷҪ�� ���ݵ�ǰʱ�����ö�Ӧ�ڼ�
		String companyID = payRequestBillInfo.getCurProject().getFullOrgUnit().getId().toString();
		boolean isInCore = FDCUtils.IsInCorporation(ctx, companyID);
		if(isInCore){//ȥ�������������½�ͳһ�������߼�����
			String prjId = payRequestBillInfo.getCurProject().getId().toString();
			// �����ڼ�
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, false);
			PeriodInfo billPeriod = payRequestBillInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//���������ڼ�
			Date bookedDate = DateTimeUtils.truncateDate(payRequestBillInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","��������Ӧ����֯û�е�ǰʱ����ڼ䣬�������ã�"));
			}
			/***************
			 * ��1�����������뵥�ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱���ڡ��������ڡ���������Ŀ�ɱ����񡰵�ǰ�ڼ䡱��ʱ������ġ������ڼ䡱�͡��������ڡ�ȡ�������뵥�ġ�ҵ���ڼ䡱�͡�ҵ�����ڡ�
			 * ��2�����������뵥�ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱С�ڵ��ڡ��������ڡ���������Ŀ�ɱ����񡰵�ǰ�ڼ䡱��ʱ������ġ������ڼ䡱�͡��������ڡ�ȡ��������Ŀ�ɱ����񡰵�ǰ�ڼ䡱�����������ڣ��ҽ��������ڷ�д�ظ������뵥�ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱��
			 *	
			 *	ԭ�����ֱ���ʱ��ͬ���ڼ��ϳ�����
			 */
			if(billPeriod!=null&&billPeriod.getNumber()>finPeriod.getNumber()){
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				shouldPeriod = billPeriod;
			}else if(finPeriod!=null){
				if (bookedDate.before(finPeriod.getBeginDate())) {
					bookedDate = finPeriod.getBeginDate();
				} else if (bookedDate.after(finPeriod.getEndDate())) {
					bookedDate = finPeriod.getEndDate();
				}
				shouldPeriod = finPeriod;
			}
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_CON_PayRequestBill set FPeriodId = ?,FBookedDate = ? where fId=? ");
			builder.addParam(shouldPeriod.getId().toString());
			builder.addParam(bookedDate);
			builder.addParam(billId.toString());
			builder.execute();
			//�����ڼ������
			payRequestBillInfo.setBookedDate(bookedDate);
		}
		PaymentBillInfo payBillInfo = null;
		try {
			payBillInfo = createPayment(ctx,payRequestBillInfo);
		} catch (Exception e) {
			throw new EASBizException(new NumericExceptionSubItem("100","���ɸ��ʱ����:"+e.getMessage()),e);//new BOSException(e.getMessage());//new PayRequestBillException(PayRequestBillException.CHECKTEXTLENGTH1);
		}
			
		if(payBillInfo != null){
			payBillId = payBillInfo.getId();
			
			try {
				updateFDCPaymentBillinvoice(ctx, payRequestBillInfo, payBillInfo);
			} catch (Exception e) {
				throw new EASBizException(new NumericExceptionSubItem("100","���·��ز�����м��ʱ����:"+e.getMessage()),e);
			}
		}
		updateContractExecInfos(ctx, payRequestBillInfo.getContractId(), billId.toString(), true);
	}

	/**
	 * ����������������ʱ���º�ִͬ����Ϣ�����깤������δ�깤������
	 * @param contractId
	 * @param string 
	 * @param b true ����, false ������
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author��keyan_zhao
	 * @CreateTime��2012-6-14
	 */
	private void updateContractExecInfos(Context ctx, String contractId, String string, boolean b) throws EASBizException, BOSException {
		if (BOSUuid.read(contractId).getType().equals(new ContractBillInfo().getBOSType())) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("settleAmt");

			ContractBillInfo conBill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(
					new ObjectUuidPK(BOSUuid.read(contractId)), selector);
			IContractExecInfos ice = ContractExecInfosFactory.getLocalInstance(ctx);
			EntityViewInfo view = new EntityViewInfo();
			selector = new SelectorItemCollection();
			selector.add("completedAmt");
			selector.add("notCompletedAmt");
			selector.add("costAmt");

			view.setSelector(selector);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId));
			view.setFilter(filter);
			ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
			ContractExecInfosInfo info = null;
			if (coll != null && coll.size() == 1) {
				info = coll.get(0);
			}
			if (info == null) {
				return;
			}

			/**
			 * ������㲢�Ѹ������ �����깤Ϊ��ͬ����ۡ�δ�깤Ϊ0 
			 * δ���㸶����� �����깤Ϊ�����Ѹ����ȿ����깤֮�͡�δ�깤=�����-���깤
			 * ��δ���� �����깤Ϊ�����Ѹ�����ȿ����깤֮�� δ�깤 = ��ͬ��� + ������ - ���깤
			 */

			if (conBill.isHasSettled()) {
				info.setCompletedAmt(getProcessAmt(ctx, contractId, string, b));
				info.setNotCompletedAmt(FDCHelper.subtract(conBill.getSettleAmt(), info.getCompletedAmt()));
			} else if (!conBill.isHasSettled()) {
				info.setCompletedAmt(getProcessAmt(ctx, contractId, string, b));
				info.setNotCompletedAmt(FDCHelper.subtract(info.getCostAmt(), info.getCompletedAmt()));
			}
			ice.updatePartial(info, selector);
		}
	}

	/**
	 * ��ú�ͬ��������Ѹ���ĸ�������Ϊ���ȿ�����깤�ϼ�
	 * @param ctx ������
	 * @param contractId ��ͬid
	 * @param b 
	 * @param string 
	 * @param paymentType ��������id 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected BigDecimal getProcessAmt(Context ctx, String contractId, String string, boolean b) throws BOSException, EASBizException {
		if (contractId.equals("")) {
			return null;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		BigDecimal temp = null;

		/****�ж��Ƿ����ù��������������������ò�������ֱ��ȡ�������嵥��ֵ -by neo***/
		if (FDCUtils.getDefaultFDCParamByKey(ctx, companyID, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)) {
			temp = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(contractId);
		} else {
			builder.appendSql(" select a.fcontractid as contractId, sum(a.FCompletePrjAmt) as amount "
					+ " from t_con_payrequestbill as a  ");
			builder.appendSql("inner join T_FDC_PaymentType type on type.fid = a.FPaymentType");
			builder.appendParam("where (type.FPayTypeID", PaymentTypeInfo.progressID);
			/**
			 * ����������ʵ�ֳɱ���������������ΪԤ����Ĳ��
			 */
			builder.appendSql(" and type.fname_l2 !='Ԥ����' \n ");
			
			builder.appendSql(" and ");
			builder.appendParam("a.FContractId", contractId);
			if (b) {
				builder.appendSql(" ) or a.fid = '" + string + "' ");
			} else {
				builder.appendSql(" ) and a.fid != '" + string + "' ");
			}
			builder.appendSql("  group by a.FContractId");
			IRowSet rs = builder.executeQuery();

			try {
				while (rs.next()) {
					temp = rs.getBigDecimal("amount");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		return temp;
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
	{
		
		checkBillForUnAudit( ctx,billId,null);
		
		
		BOSObjectType  contractType=new ContractBillInfo().getBOSType();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("contractId");
		selector.add("number");
		selector.add("curProject.id");
		selector.add("orgUnit.id");
		selector.add("amount");
		//��������
		selector.add("paymentType.payType.number");

		PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(billId),selector);
		
		String contractID= payRequestBillInfo.getContractId();
		
		//���ı���ͬ�ķ����������ı���ά��
		if(contractID!=null&&!BOSUuid.read(contractID).getType().equals(contractType)){
			throw new PayRequestBillException(PayRequestBillException.WITHOUTCONTRACTEXEC);
		}else{
			
			PaymentTypeInfo type = (PaymentTypeInfo) payRequestBillInfo.getPaymentType();
			if (type.getPayType().getId().toString().equals(PaymentTypeInfo.progressID)) {
				selector = new SelectorItemCollection();
				selector.add("hasSettled");
				
				ContractBillInfo contractBillInfo =ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractID),selector);
				if(contractBillInfo.isHasSettled()){
					throw new PayRequestBillException(PayRequestBillException.PROCNTUNAUDIT,new Object[]{payRequestBillInfo.getNumber()});
				}
			}
		}
		
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",billId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id",null,CompareType.NOTEQUALS));
		if(PayRequestBillEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new PayRequestBillException(PayRequestBillException.CANNT_UNAUDIT);
		}
		PayRequestBillInfo billInfo = new PayRequestBillInfo();
		
		billInfo.setId(billId);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		
		selector = new SelectorItemCollection();
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		_updatePartial(ctx, billInfo, selector);	
		
		
		//����ۿ���
		if(contractID!=null&&BOSUuid.read(contractID).getType().equals(contractType)){
			EntityViewInfo view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.payRequestBill.id",billId.toString()));
			view.setFilter(filter);
			DeductOfPayReqBillEntryCollection c = DeductOfPayReqBillEntryFactory.getLocalInstance(ctx).getDeductOfPayReqBillEntryCollection(view);
			selector=new SelectorItemCollection();
			selector.add("hasApplied");
			DeductBillEntryInfo dinfo=new DeductBillEntryInfo();
			for (Iterator iterator = c.iterator(); iterator.hasNext();)
			{
				DeductOfPayReqBillEntryInfo info = (DeductOfPayReqBillEntryInfo) iterator.next();
				dinfo.setId(info.getDeductBillEntry().getId());
				dinfo.setHasApplied(false);
				DeductBillEntryFactory.getLocalInstance(ctx).updatePartial(dinfo, selector);
								
			}
		}
	
        // 20050511 ��־���ṩԤ��ӿ�
        boolean isMbgCtrl = FDCUtils.getBooleanValue4FDCParamByKey(ctx, payRequestBillInfo.getOrgUnit().getId().toString(),
				FDCConstants.FDC_PARAM_STARTMG);
        if (isMbgCtrl ) {
        	/* modified by zhaoqin for R140729-0038 on 2014/12/31 */
            //IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(ctx);
            //iBgCtrl.cancelRequestBudget(payRequestBillInfo.getId().toString());
        	returnBudgetLocal(ctx, new ObjectUuidPK(billId));
        }
        
        updateContractExecInfos(ctx, contractID, billId.toString(), false);
	}


	protected void _setAuditing(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		PayRequestBillInfo billInfo = new PayRequestBillInfo();
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);					
	}

	protected void _setAudited(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		_audit(ctx, billId);
/*		PayRequestBillInfo billInfo = new PayRequestBillInfo();
		billInfo.setId(billId);			
		// �����
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		// �������
		billInfo.setAuditTime(DateTimeUtils.truncateDate(new Date()));
		// ״̬
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		_updatePartial(ctx, billInfo, selector);	*/		
	}
	
	/**
	 * 
	 * ��������������ظ�
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd ����ʱ�䣺2006-8-24
	 *               <p>
	 */
	private void checkNumberDup(Context ctx, PayRequestBillInfo billInfo)
			throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));
		filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
							.getId()));
		

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		PayRequestBillInfo info = ((PayRequestBillInfo) model);
		checkNumberDup(ctx, info);
		checkPaymentType(ctx,info);

	}


	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		checkBill(ctx, model);
		super._update(ctx, pk, model);
	}

	
	private void addDeductBill(PayRequestBillInfo editData,Context ctx){
		if(editData==null) return;
		String contractId=editData.getContractId();
		if(contractId==null||editData.getId()==null) return;
		
		String payReqId=editData.getId().toString();
		
		HashMap map=new HashMap();
		EntityViewInfo view;
		FilterInfo filter;
		SelectorItemCollection selector;
		/*
		 * �õ���ۿ����Ͷ�Ӧ��deductOfPayReqBillInfo��map
		 */
		view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("id", DeductTypeInfo.partAMaterialType, CompareType.NOTEQUALS));
		view.setFilter(filter);
		final SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("id");
		
		try
		{//by tim_gao ΪʲôҪ��ôд,��������update һ�������ύ����ô
			DeductTypeInfo info = null;
			DeductTypeCollection c = DeductTypeFactory.getLocalInstance(ctx).getDeductTypeCollection(view);
			for (int i = 0; i < c.size(); i++)
			{
				info = c.get(i);
				DeductOfPayReqBillInfo deductOfPayReqBillInfo = new DeductOfPayReqBillInfo();
				deductOfPayReqBillInfo.setPayRequestBill(editData);
				deductOfPayReqBillInfo.setDeductType(info);
				deductOfPayReqBillInfo.setAmount(FDCHelper.ZERO);
				deductOfPayReqBillInfo.setOriginalAmount(FDCHelper.ZERO);
				IObjectPK pk=DeductOfPayReqBillFactory.getLocalInstance(ctx).addnew(deductOfPayReqBillInfo);
				deductOfPayReqBillInfo.setId(BOSUuid.read(pk.toString()));
				map.put(info.getId().toString(), deductOfPayReqBillInfo);
			}
		} catch (Exception e)
		{
			logger.info(e);
			SysUtil.abort();
		}
		
		if(map.size()<=0) {
			return;
		}
		
/*		Collection values = map.values();
		
		for(Iterator iter=values.iterator();iter.hasNext();){
			try
			{
				DeductOfPayReqBillInfo info = (DeductOfPayReqBillInfo)(iter.next());
				IObjectPK pk=DeductOfPayReqBillFactory.getLocalInstance(ctx).addnew(info);
				info.setId(BOSUuid.read(pk.toString()));
			} catch (BOSException e)
			{
				logger.info(e);
			}
		}*/
	
		/*
		 * ���˳�DeductOfPayReqEntryBill���Ѻ��еĿۿ
		 */
		Set set=new HashSet();
		FilterItemCollection items;
		view = new EntityViewInfo();
		filter=new FilterInfo();
		items = filter.getFilterItems();
		items.add(new FilterItemInfo("parent.PayRequestBill.contractId",contractId,CompareType.EQUALS));
		items.add(new FilterItemInfo("parent.payRequestBill.id",payReqId,CompareType.NOTEQUALS));
		view.setFilter(filter);
		selector=new SelectorItemCollection();
		selector.add("deductBillEntry.id");
		
		try
		{//�õ�Ҫ����id���Ϸ���set��
			DeductOfPayReqBillEntryCollection c;
			c = DeductOfPayReqBillEntryFactory.getLocalInstance(ctx).getDeductOfPayReqBillEntryCollection(view);
			DeductOfPayReqBillEntryInfo info;
			for(int i=0;i<c.size();i++){
				info = c.get(i);
				set.add(info.getDeductBillEntry().getId()); //��¼��ID
			}
		} catch (BOSException e)
		{
			logger.info(e);
			SysUtil.abort();
		}
		
		/*
		 * ��DeductBillEntryȡ���������������Ŀۿ
		 */
		view = new EntityViewInfo();
		filter=new FilterInfo();
		items = filter.getFilterItems();
		items.add(new FilterItemInfo("contractId",contractId,CompareType.EQUALS));
		items.add(new FilterItemInfo("hasApplied",Boolean.FALSE,CompareType.EQUALS));
		items.add(new FilterItemInfo("Parent.state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		if(set.size()>0){
			items.add(new FilterItemInfo("id",set,CompareType.NOTINCLUDE));
		}
		view.setFilter(filter);
		try{
			DeductBillEntryInfo info;
			DeductBillEntryCollection c = DeductBillEntryFactory.getLocalInstance(ctx).getDeductBillEntryCollection(view);
			DeductOfPayReqBillInfo deductOfPayReqBillInfo;
			DeductOfPayReqBillEntryInfo entryInfo;
			for(int i=0;i<c.size();i++){
				info=c.get(i);
				Object o= map.get(info.getDeductType().getId().toString());
				if(o!=null){
					deductOfPayReqBillInfo=(DeductOfPayReqBillInfo)o;
					deductOfPayReqBillInfo.setAmount(deductOfPayReqBillInfo.getAmount().add(info.getDeductAmt()));
					deductOfPayReqBillInfo.setOriginalAmount(deductOfPayReqBillInfo.getAmount().add(info.getDeductOriginalAmt()));
					entryInfo=new DeductOfPayReqBillEntryInfo();
					entryInfo.setParent(deductOfPayReqBillInfo);
					entryInfo.setDeductBillEntry(info);
					DeductOfPayReqBillEntryFactory.getLocalInstance(ctx).addnew(entryInfo);
				}
			}
			DeductOfPayReqBillFactory.getLocalInstance(ctx).reCalcAmount(editData.getId().toString());
		}catch(Exception e){
			e.printStackTrace();
			SysUtil.abort();
		}
		

	}
	protected void _addDeductBill(Context ctx, IObjectValue model) throws BOSException
	{
		addDeductBill((PayRequestBillInfo)model,ctx);
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException
	{
		if(pk==null){
			return ;
		}
		// ͬʱɾ�������Ŀۿ�б�
		_delete(ctx, new IObjectPK[]{pk});
	}
	protected IObjectPK[] _delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException
	{
		//�ݲ�֧�ֵĲ���
//		throw new UnsupportedOperationException();
		throw new EASBizException(PayRequestBillException.NOTSUPPORTOPRT);
//		return super._delete(ctx, filter);
	}
	
	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		//ɾ�������Ŀۿ���
		deleteOfPayRequest(ctx, new IObjectPK[]{pk});
		super._cancel(ctx,pk);
	}
	
	protected void deleteOfPayRequest(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException
	{

		//�������뵥��ͬID
		String contractBillId = getPayRequestBillInfo(ctx, arrayPK[0]).getContractId();
		
		Set arrayPKSet=new HashSet();
		for (int i = 0; i < arrayPK.length; i++)
		{
			arrayPKSet.add(arrayPK[i].getKeyValue("id").toString());
		}
		FilterInfo filter=new FilterInfo();
		FilterItemCollection items = filter.getFilterItems();
		if(arrayPKSet.size()>0){
			items.add(new FilterItemInfo("payRequestBill.id",arrayPKSet,CompareType.INCLUDE));
		}

		//ɾ�������Ŀۿ���		
		{
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("entrys.deductBillEntry.id");
			
			SelectorItemCollection updateSelector=new SelectorItemCollection();
			updateSelector.add("id");
			updateSelector.add("hasApplied");
			
			DeductOfPayReqBillCollection deductOfPayReqBillCollection = DeductOfPayReqBillFactory.getLocalInstance(ctx).getDeductOfPayReqBillCollection(view);
			for (int i = 0; i < deductOfPayReqBillCollection.size(); i++) {
				DeductOfPayReqBillInfo billInfo = deductOfPayReqBillCollection.get(i);
				
				for (int j = 0; j < billInfo.getEntrys().size(); j++) {
					DeductOfPayReqBillEntryInfo billEntryInfo = billInfo.getEntrys().get(j);
					
					DeductBillEntryInfo info=new DeductBillEntryInfo();
					info.setId(billEntryInfo.getDeductBillEntry().getId());
					info.setHasApplied(false);
					DeductBillEntryFactory.getLocalInstance(ctx).updatePartial(info,updateSelector);
				}
			}
			
			DeductOfPayReqBillFactory.getLocalInstance(ctx).delete(filter);
		}
		//ɾ�������Ľ�����,�����������ۿ���ͬ
		{
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("guerdon.id");
			
			SelectorItemCollection updateSelector=new SelectorItemCollection();
			updateSelector.add("id");
			updateSelector.add("isGuerdoned");
			GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection = GuerdonOfPayReqBillFactory.getLocalInstance(ctx).getGuerdonOfPayReqBillCollection(view);
			for (int i = 0; i < guerdonOfPayReqBillCollection.size(); i++) {
				GuerdonOfPayReqBillInfo item = guerdonOfPayReqBillCollection.get(i);
				GuerdonBillInfo info=new GuerdonBillInfo();
				info.setId(item.getGuerdon().getId());
				info.setIsGuerdoned(false);
				GuerdonBillFactory.getLocalInstance(ctx).updatePartial(info, updateSelector);
				
			}
			GuerdonOfPayReqBillFactory.getLocalInstance(ctx).delete(filter);
		}
		{
			//ɾ��������ΥԼ��,����������뽱����ͬ
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("compensation.id");
			
			SelectorItemCollection updateSelector=new SelectorItemCollection();
			updateSelector.add("id");
			updateSelector.add("isCompensated");
			CompensationOfPayReqBillCollection compensationOfPayReqBillCollection = CompensationOfPayReqBillFactory.getLocalInstance(ctx).getCompensationOfPayReqBillCollection(view);
			for (int i = 0; i < compensationOfPayReqBillCollection.size(); i++) {
				CompensationOfPayReqBillInfo item = compensationOfPayReqBillCollection.get(i);
				CompensationBillInfo info=new CompensationBillInfo();
				info.setId(item.getCompensation().getId());
				info.setIsCompensated(false);
				CompensationBillFactory.getLocalInstance(ctx).updatePartial(info, updateSelector);
				
			}
			CompensationOfPayReqBillFactory.getLocalInstance(ctx).delete(filter);
		}
//		boolean param = FDCUtils.getDefaultFDCParamByKey(ctx, ContextUtil
//				.getCurrentFIUnit(ctx).getId().toString(),
//				FDCConstants.FDC_PARAM_CREATEPARTADEDUCT);
		//
//		if(param){
			//����Ϊ��ʱ��ɾ�������ļ׹��ۿ
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("deductBill.id");
			view.getSelector().add("deductBill.entrys.id");
			view.getSelector().add("deductBill.entrys.contractId");
			
			SelectorItemCollection updateSelector = new SelectorItemCollection();
			updateSelector.add("entrys.hasApplied");
			
			PartAOfPayReqBillCollection partAOfPayReqBillCollection = PartAOfPayReqBillFactory.getLocalInstance(ctx).getPartAOfPayReqBillCollection(view);
			for (int i = 0; i < partAOfPayReqBillCollection.size(); i++) {
				PartAOfPayReqBillInfo item = partAOfPayReqBillCollection.get(i);
				int deductEntrySize = item.getDeductBill().getEntrys().size();
				if(deductEntrySize == 0){
					continue;
				}
				for(int j=0;j<deductEntrySize;j++){
					DeductBillEntryInfo entry = item.getDeductBill().getEntrys().get(j);
					if(entry.getContractId().equals(contractBillId)){
						entry.setHasApplied(false);
						DeductBillEntryFactory.getLocalInstance(ctx).updatePartial(entry, updateSelector);
					}
				}
			}
			PartAOfPayReqBillFactory.getLocalInstance(ctx).delete(filter);
			
			//by cassiel 
			//ɾ���׹��Ŀۿ��οۿ������ϸ
			FilterInfo _filter = new FilterInfo();
			_filter.getFilterItems().add(new FilterItemInfo("payReqID",arrayPKSet,CompareType.INCLUDE));
			ShowDeductOfPartABillFactory.getLocalInstance(ctx).delete(_filter);
			
//		}else{
			//����Ϊ��ʱ��ɾ�������ļ׹�ȷ�ϵ�
			view=new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("materialConfirmBill.id");
			
			updateSelector = new SelectorItemCollection();
			updateSelector.add("id");
			updateSelector.add("hasApplied");
			PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection = PartAConfmOfPayReqBillFactory.getLocalInstance(ctx).getPartAConfmOfPayReqBillCollection(view);
			for (int i = 0; i < partAConfmOfPayReqBillCollection.size(); i++) {
				PartAConfmOfPayReqBillInfo item = partAConfmOfPayReqBillCollection.get(i);
				MaterialConfirmBillInfo info = new MaterialConfirmBillInfo();
				info.setId(item.getMaterialConfirmBill().getId());
				info.setHasApplied(false);
				MaterialConfirmBillFactory.getLocalInstance(ctx).updatePartial(info, updateSelector);
			}
			PartAConfmOfPayReqBillFactory.getLocalInstance(ctx).delete(filter);
//		}
	}
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException
	{
		if(arrayPK.length<1) return;
		PayRequestBillInfo model = getPayRequestBillInfo(ctx, new ObjectUuidPK(
				arrayPK[0].toString()), getSic());
		String contractId=model.getContractId();//getPayRequestBillInfo(ctx, arrayPK[0]).getContractId();
		if(contractId==null) return;
		if(BOSUuid.read(contractId).getType().equals((new ContractWithoutTextInfo()).getBOSType())){
			throw new PayRequestBillException(PayRequestBillException.WITHOUTCONTRACTEXEC);
		}

		//ɾ�������Ŀۿ���	
		deleteOfPayRequest(ctx,arrayPK);
		
		PayRequestBillInfo payReq=new PayRequestBillInfo();
		payReq.put("arrayPK",arrayPK);
		payReq.put("contractId", contractId);//ɾ��ʱֻ��ɾ��һ����ͬ�Ķ�����뵥
		/**
		 * ɾ��ʱ�����ɵĴ�ǩ����ͬ��¼ɾ����������ԭ��ǩ����ͬ״̬��ʹ������ͬ���Թ��� by hpw 20010-01-04
		 * TODO ��ֻ֧�ֵ���ɾ��,���ŵ���ͬ�����ڼ�Ŀǰ��û������Ӧ�ó���
		 */
		FDCBudgetPeriodInfo period=FDCBudgetPeriodInfo.getPeriod(model.getPayDate(),false);
		payReq.put("period", period);
		FDCBudgetAcctFacadeFactory.getLocalInstance(ctx).invokeBudgetCtrl(payReq, FDCBudgetConstants.STATE_DELETE);
		
		/*****
		 * ���¹����ļ׹�����ȷ�ϵ����ۼ�������
		 */
		Set idSet = new HashSet();
		for(int i=0;i<arrayPK.length;i++){
			idSet.add(arrayPK[i].toString());
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent",idSet,CompareType.INCLUDE));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		PayRequestBillConfirmEntryCollection confirmEns = PayRequestBillConfirmEntryFactory.getLocalInstance(ctx).getPayRequestBillConfirmEntryCollection(view);
		if(confirmEns!=null&&confirmEns.size()>0){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.setPrepareStatementSql("update T_PAM_MaterialConfirmBill set FToDateReqAmt=FToDateReqAmt - (select sum(FReqAmount) from T_CON_PayReqConfirmEntry where FConfirmBillID=? and Fid!=?) where FID=? ");
			builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
			boolean hasSQL = false;
			for(int i=0;i<confirmEns.size();i++){
				if(confirmEns.get(i).getConfirmBill()!=null){
					builder.addParam(confirmEns.get(i).getConfirmBill().getId().toString());
					builder.addParam(confirmEns.get(i).getId().toString());
					builder.addParam(confirmEns.get(i).getConfirmBill().getId().toString());
					builder.addBatch();
					hasSQL = true;
				}
			}
			if(hasSQL){
				builder.executeBatch();
			}
			builder = null;
		}
		
		//modified by zhaoqin for R140729-0038 on 2014/12/31
		for(int i = 0; i < arrayPK.length; i++) {
			returnBudgetLocal(ctx, arrayPK[i]);
		}
		
		super._delete(ctx, arrayPK);
		
		//R101207-373 �ദ��Ҫ���ּ���������뵥�ġ���ͬ�ڹ����ۼ����롱��ֵ , ��ȡ��һ������ by zhiyuan_tang 2010/12/20
		recountReqAmt(ctx, contractId);

/*		//���ı���ɾ��ͳһ�����ı��ĵط�ά��
//		ɾ�����������ı���ͬ		
		if(contractId!=null){
			String sql = "delete from T_CON_ContractWithoutText where fid = ? ";
			Object []params = new Object[] {contractId};
			 DbUtil.execute(ctx, sql, params);
		}*/
		// ���½��ȹ���id
		String companyId = null;
		if (model.getCurProject().getFullOrgUnit() == null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(
					new ObjectUuidPK(model.getCurProject().getId().toString()), sic);
			companyId = curProject.getFullOrgUnit().getId().toString();
		} else {
			companyId = model.getCurProject().getFullOrgUnit().getId().toString();
		}
		
		boolean isFromFillBill = FDCUtils.getBooleanValue4FDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_PROJECTFILLBILL);
		boolean isLoadStrict = FDCUtils.getBooleanValue4FDCParamByKey(ctx, companyId, FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT); 
		FDCSCHUtils.updateTaskRef(ctx,isFromFillBill,isLoadStrict,true,contractId,arrayPK,null);
	}
	
	/**
	 * �����������ͬ��Ӧ�����и������뵥���ۼ������ۼ����������ۼ����룫���ڷ����
	 *      �ദ��Ҫ���ּ���������뵥�ġ���ͬ�ڹ����ۼ����롱��ֵ , ������ȡ��һ������
	 * @param ctx
	 * @param contractId
	 * @throws BOSException
	 * @throws EASBizException
	 * @author zhiyuan_tang 2010/12/20
	 */
	private void recountReqAmt(Context ctx, String contractId) throws BOSException, EASBizException {
		FilterInfo filter;
		EntityViewInfo view;
		if(contractId==null) return;
		view=new EntityViewInfo();
		filter=new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractId",contractId));
		SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("number");
		view.getSelector().add("createTime");
		view.getSelector().add("prjAllReqAmt");
		view.getSelector().add("addPrjAllReqAmt");
		view.getSelector().add("payPartAMatlAllReqAmt");
		
		view.getSelector().add("projectPriceInContract");
		view.getSelector().add("addProjectAmt");
		view.getSelector().add("payPartAMatlAmt");
		PayRequestBillCollection c = getPayRequestBillCollection(ctx, view);
		PayRequestBillInfo info=null;
		
		for(int i=0;i<c.size();i++){
			info=c.get(i);
			if(i==0){
				//����0
				info.setPrjAllReqAmt(info.getProjectPriceInContract());
				info.setAddPrjAllReqAmt(info.getAddProjectAmt());
				info.setPayPartAMatlAllReqAmt(info.getPayPartAMatlAmt());
			}else{
				//���������ۼƣ����ڷ���
				if(c.get(i-1).getPrjAllReqAmt() instanceof BigDecimal &&info.getProjectPriceInContract() instanceof BigDecimal){
					info.setPrjAllReqAmt(c.get(i-1).getPrjAllReqAmt().add(info.getProjectPriceInContract()));
				}
				if (c.get(i-1).getAddPrjAllReqAmt() instanceof BigDecimal && info.getAddProjectAmt() instanceof BigDecimal)
				{
					info.setAddPrjAllReqAmt(c.get(i-1).getAddPrjAllReqAmt().add(info.getAddProjectAmt()));
				}
				if(c.get(i-1).getPayPartAMatlAllReqAmt() instanceof BigDecimal &&info.getPayPartAMatlAmt() instanceof BigDecimal){
					info.setPayPartAMatlAllReqAmt(c.get(i-1).getPayPartAMatlAllReqAmt().add(info.getPayPartAMatlAmt()));
				}
			}
			_updatePartial(ctx, info, view.getSelector());
		}
	}
	
	protected IObjectPK[] _delete(Context ctx, String oql) throws BOSException, EASBizException
	{
		//�ݲ�֧�ֵĲ���
		throw new EASBizException(PayRequestBillException.NOTSUPPORTOPRT);
//		return super._delete(ctx, oql);
	}
	private void addCompensationBill(PayRequestBillInfo billInfo,Context ctx) throws BOSException, EASBizException{
		
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("isCompensated");
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contract.id", billInfo.getContractId()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isCompensated",String.valueOf(1),CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("amount");
		view.getSelector().add("originalamount");
		view.getSelector().add("isCompensated");
		CompensationBillCollection compensationBillCollection = CompensationBillFactory.getLocalInstance(ctx).getCompensationBillCollection(view);
		CompensationBillInfo info = null;
		CompensationOfPayReqBillInfo gpInfo = null;
		CoreBaseCollection cbcollection=new CoreBaseCollection();
		for(int i=0;i<compensationBillCollection.size();i++){
			info=compensationBillCollection.get(i);
			gpInfo=new CompensationOfPayReqBillInfo();
			gpInfo.setAmount(info.getAmount());
			gpInfo.setOriginalAmount(info.getOriginalAmount());
			gpInfo.setPayRequestBill(billInfo);
			gpInfo.setCompensation(info);
			cbcollection.add(gpInfo);
			
			info.setIsCompensated(true);
			CompensationBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
		}
		CompensationOfPayReqBillFactory.getLocalInstance(ctx).addnew(cbcollection);
		
	}
	private void addGuerdonBill(PayRequestBillInfo billInfo,Context ctx) throws BOSException,EASBizException{
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("isGuerdoned");
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("contract.id", billInfo.getContractId());
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isGuerdoned",String.valueOf(1),CompareType.NOTEQUALS));
/*		//�����뵥��ͬ���·�֮ǰ��
		Timestamp createTime = billInfo.getCreateTime();
		Calendar cal=Calendar.getInstance();
		cal.setTime(createTime);
		createTime.setDate(cal.getActualMaximum(Calendar.DATE));
		createTime.setHours(cal.getActualMaximum(Calendar.HOUR_OF_DAY)); //24Сʱ��
		createTime.setMinutes(cal.getActualMaximum(Calendar.MINUTE));
		createTime.setSeconds(cal.getActualMaximum(Calendar.SECOND));
		filter.getFilterItems().add(new FilterItemInfo("createTime",createTime,CompareType.LESS_EQUALS));
*/		
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("amount");
		view.getSelector().add("originalAmount");
		view.getSelector().add("isGuerdoned");
		GuerdonBillCollection guerdonBillCollection = GuerdonBillFactory.getLocalInstance(ctx).getGuerdonBillCollection(view);
		GuerdonBillInfo info=null;
		GuerdonOfPayReqBillInfo gpInfo=null;
		CoreBaseCollection cbcollection=new CoreBaseCollection();
		for(int i=0;i<guerdonBillCollection.size();i++){
			info=guerdonBillCollection.get(i);
			gpInfo=new GuerdonOfPayReqBillInfo();
			gpInfo.setAmount(info.getAmount());
			gpInfo.setOriginalAmount(info.getOriginalAmount());
			gpInfo.setPayRequestBill(billInfo);
			gpInfo.setGuerdon(info);
			cbcollection.add(gpInfo);
			
			info.setIsGuerdoned(true);
			GuerdonBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
		}
		GuerdonOfPayReqBillFactory.getLocalInstance(ctx).addnew(cbcollection);
	}
	
	//���ɸ��
	private PaymentBillInfo createPayment(Context ctx,PayRequestBillInfo payReqBill)throws Exception{
		//���������뵥���ɸ����Ĭ�ϻ�ر�
		//��������ɵĸ��ɾ���� ��Ҫ�ٴ����ɸ�� ����Ҫ���رմ˸������뵥
		if(payReqBill.isHasClosed()){
			return null;
		}
		BOSObjectType bosType = new PaymentBillInfo().getBOSType();

		IBTPManager iBTPManager = BTPManagerFactory.getLocalInstance(ctx);
		BTPTransformResult result = iBTPManager.transform(payReqBill, bosType.toString());

		IObjectCollection destBillColl = result.getBills();
		BOTRelationCollection botRelateColl = result.getBOTRelationCollection();

		PaymentBillInfo destBillInfo = null;

		//2009-1-12 ȡ������֯  
		//��ʵʵ�������֯�����ù�����Ŀ�ϵ�fullorgunit��modify  by sxhong 2009-06-04 18:31:24
		FullOrgUnitInfo org = payReqBill.getOrgUnit();
		CompanyOrgUnitInfo company=null;	
		if(payReqBill.getCurProject().getFullOrgUnit()!=null){
			String companyId=payReqBill.getCurProject().getFullOrgUnit().getId().toString();
			company=new CompanyOrgUnitInfo();
			company.setId(BOSUuid.read(companyId));
		}else{
			company=FDCHelper.getFIOrgUnit(ctx, org);
		}
		
		BOSObjectType  contractType=new ContractBillInfo().getBOSType();
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("isNeedPaid");
		selectors.add("account.id");
		
		//�������֮�󣬲������Դ���
		//�˴�����BOTPδ���õ����� 
		for (int i = 0, size = destBillColl.size(); i < size; i++) {

			destBillInfo = (PaymentBillInfo) destBillColl.getObject(i);
			if(destBillInfo.getCU()==null){
				destBillInfo.setCU(payReqBill.getCU());
			}
			if(destBillInfo.getCompany()==null){
				destBillInfo.setCompany(company);
			}
//			if(destBillInfo.getBizDate()==null){
//				destBillInfo.setBizDate(payReqBill.getBizDate());
				destBillInfo.setBizDate(payReqBill.getBookedDate());//���ز�ά��bookeddate��Ĭ�������뵥��ͬ
//			}
		  //����Ҫ��Ѹ������ڸĳ�����ʱ������
//			if(destBillInfo.getPayDate()==null){//botpδ���ø���ʱ��ʱȡ���뵥��������,����ʱȡ����ʱ������
//				destBillInfo.setPayDate(payReqBill.getPayDate());
//			}
			if(destBillInfo.getCurProject()==null){
				destBillInfo.setCurProject(payReqBill.getCurProject());
			}
			if(destBillInfo.getBillStatus()==null){
				destBillInfo.setBillStatus(BillStatusEnum.SAVE);
			}
			//by tim_gao �ұ𣬻��ʲ�����ζ�Ҫ�ͺ�ͬ��ͳһ��������ͬͳһ
			//��Ϊ����Ը������뵥�����Զ��1 �����ÿ���ұ𣬻��ʲ�ͬ���ڼ����ۼƷ�Ʊ���Լ������ۼ������ͷ����
			//����պ����������ģ�����������޸�
				destBillInfo.setCurrency(payReqBill.getCurrency());
				destBillInfo.setExchangeRate(payReqBill.getExchangeRate());
			
			if(destBillInfo.getAmount()==null){
				destBillInfo.setAmount(payReqBill.getOriginalAmount());
			}
			if(destBillInfo.getLocalAmt()==null){
				if (payReqBill.getAmount()==null && payReqBill.getOriginalAmount() != null && payReqBill.getExchangeRate() != null){
					destBillInfo.setLocalAmt(payReqBill.getOriginalAmount().multiply(payReqBill.getExchangeRate()));
				}else{
					destBillInfo.setLocalAmt(payReqBill.getAmount());
				}
			}
			if(destBillInfo.getFdcPayReqID()==null){
				destBillInfo.setFdcPayReqID(payReqBill.getId().toString());
			}
			if(destBillInfo.getFdcPayReqNumber()==null){
				destBillInfo.setFdcPayReqNumber(payReqBill.getNumber());
			}
			if(destBillInfo.getContractNo()==null){
				destBillInfo.setContractNo(payReqBill.getContractNo());
			}
			if(destBillInfo.getContractBillId()==null){
				destBillInfo.setContractBillId(payReqBill.getContractId());
			}
			if(destBillInfo.getActFdcPayeeName()==null){
				destBillInfo.setActFdcPayeeName(payReqBill.getRealSupplier());
			}
			if(destBillInfo.getFdcPayeeName()==null){
				destBillInfo.setFdcPayeeName(payReqBill.getSupplier());
			}
			if(destBillInfo.getCapitalAmount()==null){
				destBillInfo.setCapitalAmount(payReqBill.getCapitalAmount());
			}
			if(destBillInfo.getAddProjectAmt()==null){
				destBillInfo.setAddProjectAmt(payReqBill.getProjectPriceInContractOri());
			}
			if(destBillInfo.getLstPrjAllPaidAmt()==null){
				destBillInfo.setLstPrjAllPaidAmt(payReqBill.getLstPrjAllPaidAmt());
			}
			//�Ƿ��ύ����
			destBillInfo.setIsNeedPay(payReqBill.isIsPay());
			//��ע
			if(destBillInfo.getDescription()==null){
				destBillInfo.setDescription(payReqBill.getDescription());
			}
			//����˵��
			if(destBillInfo.getSummary()==null){
				destBillInfo.setSummary(payReqBill.getMoneyDesc());
			}
			//�����̶�
			if(destBillInfo.getUrgentDegree()==null){
				destBillInfo.setUrgentDegree(UrgentDegreeEnum.getEnum(String.valueOf(payReqBill.getUrgentDegree().getValue())));
			}
			//destBillInfo.setIsEmergency(IsMergencyEnum.getEnum(String.valueOf(payReqBill.getUrgentDegree().getValue())));
			//�����̶�
			if(destBillInfo.getIsEmergency()==null){
				if(destBillInfo.getUrgentDegree()!=null&&destBillInfo.getUrgentDegree().equals(UrgentDegreeEnum.URGENT)){
					destBillInfo.put("isEmergency", new Integer(1));
				}else{
					destBillInfo.put("isEmergency", new Integer(0));
				}
			}
			//�տ������˻�
			if(destBillInfo.getPayeeBank()==null){
				destBillInfo.setPayeeBank(payReqBill.getRecBank());
			}
			if(destBillInfo.getPayeeAccountBank()==null){
				destBillInfo.setPayeeAccountBank(payReqBill.getRecAccount());
			}
			
			//ͬ������Լ���;
			destBillInfo.setIsDifferPlace(payReqBill.getIsDifferPlace());
			if(destBillInfo.getUsage()==null){
				destBillInfo.setUsage(payReqBill.getUsage());
			}
			destBillInfo.setAccessoryAmt(payReqBill.getAttachment());
			
			//�տ���
			if(payReqBill.getSupplier()!=null){
				destBillInfo.setPayeeID(payReqBill.getSupplier().getId().toString());
				destBillInfo.setPayeeNumber(payReqBill.getSupplier().getNumber());
				destBillInfo.setPayeeName(payReqBill.getSupplier().getName());
			}
			//��������
			if(destBillInfo.getFdcPayType()==null){
				destBillInfo.setFdcPayType(payReqBill.getPaymentType());
			}
			
			/**���Ӳ��������ݲ������Ƹ������뵥���������Ժ����ɵĸ�����Ƶ��˵�ȡֵ��R090520-176������by neo **/
			boolean isCreator = FDCUtils.getDefaultFDCParamByKey(ctx, 
					ContextUtil.getCurrentFIUnit(ctx).getId().toString(),FDCConstants.FDC_PARAM_PAYMENTCREATOR);
			if(isCreator){
				destBillInfo.setCreator(payReqBill.getCreator());
			}
			
			/**��������ı���ͬ��
			 * �����ò���ɱ�һ�廯����ʱ������ѡ�����踶��������s��������Ŀ���ֶΣ�
			 * �����󣬸��ֶν���Զ����롰�����Ŀ������Ӧ�ĸ�����Զ����Ѹ��״̬��
			 * ��������һ�廯����������ѡ�����踶������ı���ͬ�����󣬶�Ӧ�ĸ���Զ���Ϊ���Ѹ����*/
			
			ContractWithoutTextInfo model = null;
			if(!BOSUuid.read(payReqBill.getContractId()).getType().equals(contractType)){
				model = (ContractWithoutTextInfo)ContractWithoutTextFactory.getLocalInstance(ctx).
						getContractWithoutTextInfo(new ObjectUuidPK(payReqBill.getContractId()),selectors);
				if(destBillInfo.getPayerAccount()==null){
					destBillInfo.setPayerAccount(model.getAccount());
				}
			}	
			
			//�ύ״̬
			final IObjectPK pk = iBTPManager.saveRelations(destBillInfo, botRelateColl);
			destBillInfo.setId(BOSUuid.read(pk.toString()));			
			
			boolean is = FDCUtils.IsFinacial(ctx,company.getId().toString());
			if(model!=null && model.isIsNeedPaid() ){
				List list = new ArrayList();
				list.add(pk.toString());
				if((is && model.getAccount()!=null ) || !is ){
					PaymentBillFactory.getLocalInstance(ctx).audit4FDC(list);
				}
			}else{
				if(!isCreator){
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("createTime");
					selector.add("creator");
					destBillInfo.setCreateTime(null);
					destBillInfo.setCreator(null);
					PaymentBillFactory.getLocalInstance(ctx).updatePartial(destBillInfo, selector);
				}
			}
		}
		
		return destBillInfo;
	}
	//by tim_gao �����������������,����˵,�������뵥�븶�Ӧ��ȥͬʱдһ��fdcpaymentbill ͬʱ��ʾҲ����һ��,
	//�����ǻ������ɸ��ȥ��д������ĺ��鷳,����һ�ѹ����ۼƵ�����updateFDCPaymentBillinvoice()
	
	private void updateFDCPaymentBillinvoice(Context ctx, PayRequestBillInfo payReqBill, PaymentBillInfo paymentBill) throws EASBizException, BOSException{
		//�������ɸ���м��ʱ�������������뵥�ķ�Ʊ���ֶ�
		FDCPaymentBillInfo fdcPayment = new FDCPaymentBillInfo();
		fdcPayment.setInvoiceNumber(payReqBill.getInvoiceNumber());
		fdcPayment.setInvoiceAmt(payReqBill.getInvoiceAmt());
		fdcPayment.setAllInvoiceAmt(payReqBill.getAllInvoiceAmt());
		fdcPayment.setInvoiceOriAmt(payReqBill.getInvoiceOriAmt());
		fdcPayment.setAllInvoiceOriAmt(payReqBill.getAllInvoiceOriAmt());
		fdcPayment.setInvoiceDate(payReqBill.getInvoiceDate());
		// ��Ҫ�����ϸ��byhpw
		fdcPayment.setPaymentBill(paymentBill);
		FDCPaymentBillHelper.handleFDCPaymentBillInvoice(ctx, fdcPayment, paymentBill);
	}
	
	/**
	 * �������뵥�ۼƶ���ں�ͬ����������ʱ���ѣ��ύ��������
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException 
	 */
	private void checkAmt(Context ctx,PayRequestBillInfo billInfo) throws BOSException,EASBizException {
		if(billInfo.getLatestPrice()==null){
			return;
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("contractId", billInfo.getContractId());
		view.getSelector().add("amount");
		PayRequestBillCollection c = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		BigDecimal total=FDCHelper.ZERO;
		for(int i=0;i<c.size();i++){
			total=total.add(FDCHelper.toBigDecimal(c.get(0).getAmount()));
		}
		if(total.compareTo(billInfo.getLatestPrice())>0){
			throw new PayRequestBillException(PayRequestBillException.CHECKLSTAMT);
		}
		
	}
	
	private void checkPaymentType(Context ctx,PayRequestBillInfo payReq) throws EASBizException,BOSException{

/*		String settlementID="Ga7RLQETEADgAAC/wKgOlOwp3Sw=";//�����
		String progressID="Ga7RLQETEADgAAC6wKgOlOwp3Sw=";//���ȿ�
		String keepID="Ga7RLQETEADgAADDwKgOlOwp3Sw=";//���޿�
*/		
//		if(payReq.getContractId()==null||payReq.getPaymentType()==null||payReq.getPaymentType().getPayType()==null){
//			return;
//		}
//		PaymentTypeInfo type=payReq.getPaymentType();
//		FilterInfo filter=new FilterInfo();
//		filter.appendFilterItem("paymentType.payType.id", PaymentTypeInfo.settlementID);
//		filter.appendFilterItem("contractId", payReq.getContractId());
//		if(payReq.getId()!=null){
//			filter.getFilterItems().add(new FilterItemInfo("id",payReq.getId(),CompareType.NOTEQUALS));
//		}
//		if(type.getPayType().getId().toString().equals(PaymentTypeInfo.keepID)){
//			String orgUnitId = FDCUtils.findCostCenterOrgId(ctx, payReq.getCurProject().getId().toString());
//			boolean isKeepBefore = FDCUtils.getDefaultFDCParamByKey(ctx, orgUnitId, FDCConstants.FDC_PARAM_KEEPBEFORESETTLEMENT);
//			if(!_exists(ctx,filter)){
//				if(!isKeepBefore){
//					throw new PayRequestBillException(PayRequestBillException.CANTSELECTKEEPAMT);
//				}
////				MsgBox.showError(prmtPayment,"�����������ܸ����޿�,�����ڸ������͵�����Ϊ�������ĸ������뵥ʱ����ѡ�񡰱��޿���͸�������");
//			}
//			//���޿��ܽ��ܴ��ڽ��㵥�ϵ��ʱ���
//			FilterInfo myfilter=new FilterInfo();
//			myfilter.appendFilterItem("contractBill.id", payReq.getContractId());
//			EntityViewInfo view=new EntityViewInfo();
//			view.setFilter(myfilter);
//			view.getSelector().add("qualityGuarante");
//			// by Cassiel_peng
//			SorterItemInfo sorterItem = new SorterItemInfo();
//			sorterItem.setPropertyName("qualityGuarante");
//			sorterItem.setSortType(SortType.DESCEND);
//			view.getSorter().add(sorterItem);
//			ContractSettlementBillCollection c = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(view);
//			BigDecimal amount=FDCHelper.ZERO;
//			// by Cassiel_peng
//			if(c!=null&&c.size()!=0){
//				amount=FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(c.get(0).getQualityGuarante()));
//			}
//			/**
//			 * ����ϵͳԭ��ֻ֧�ֺ�ͬ�ĵ��ʽ��㣬����ȡ�ۼƱ��޿����ʱ�������´������󣬵���֧�ֺ�ͬ�Ķ�ʽ���֮����Ȼ��������
//			 * �ͻᵼ�������ظ��ۼӣ�����Ҫ����ֻ��ȡ�ú�ͬ���ۼƱ��޿������ÿ�ʽ��㵥���ۼƱ��޿�֮��   by Cassiel_peng
//			 */
////			for(int i=0;i<c.size();i++){
////				 amount= amount.add(FDCHelper.toBigDecimal(c.get(i).getQualityGuarante()));
////			}
//			//�������뵥���ۼƱ��޽��
//			view=new EntityViewInfo();
//			filter=new FilterInfo();
//			filter.appendFilterItem("contractId",payReq.getContractId());
//			filter.appendFilterItem("paymentType.payType.id",PaymentTypeInfo.keepID);
//			view.setFilter(filter);
//			view.getSelector().add("amount");
//			PayRequestBillCollection payRequestBillC = getPayRequestBillCollection(ctx, view);
//			BigDecimal payReqAmount=FDCHelper.ZERO;
//			for(int i=0;i<payRequestBillC.size();i++){
//				PayRequestBillInfo info=payRequestBillC.get(i);
//				if(info.getId().equals(payReq.getId())){
//					continue;//�Թ���ǰ����
//				}
//				payReqAmount=payReqAmount.add(FDCHelper.toBigDecimal(info.getAmount()));
//			}
//			payReqAmount=payReqAmount.add(FDCHelper.toBigDecimal(payReq.getAmount()));
////			if(FDCHelper.toBigDecimal(payReq.getAmount()).compareTo(amount)>0){
//			if(FDCHelper.toBigDecimal(payReqAmount).compareTo(amount)>0){
//				throw new PayRequestBillException(PayRequestBillException.MORETHANQUALITYGUARANTE);
//			}
//		}
//		if(type.getPayType().getId().toString().equals(PaymentTypeInfo.progressID)){
//			if(_exists(ctx,filter)){
//				//֮ǰ����ʾ��Ϣ���� by zhiyuan_tang 2010/12/21
//				throw new PayRequestBillException(PayRequestBillException.CANTSELECTPROGRESSAMT);
////				MsgBox.showError(prmtPayment,"����������ܸ����ȿ�,�����ڸ������͵�����Ϊ�������ĸ������뵥ʱ����ѡ�񡰽��ȿ���͸�������");
//			}
//		}
//		if(type.getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)){
//			FilterInfo myfilter=new FilterInfo();
//			myfilter.appendFilterItem("id", payReq.getContractId());
//			myfilter.appendFilterItem("hasSettled", Boolean.TRUE);
//			if(!ContractBillFactory.getLocalInstance(ctx).exists(myfilter)){
//				throw new PayRequestBillException(PayRequestBillException.MUSTSETTLE);
////				MsgBox.showError(prmtPayment,"��ͬ�������֮���������������ĸ������뵥");
//			}
//		}
		if(payReq.getContractId() == null || payReq.getPaymentType() == null || payReq.getPaymentType().getPayType() == null)
	        return;
	    PaymentTypeInfo type = payReq.getPaymentType();
	    FilterInfo filter = new FilterInfo();
	    filter.appendFilterItem("paymentType.payType.id", "Ga7RLQETEADgAAC/wKgOlOwp3Sw=");
	    filter.appendFilterItem("sourceBillId", null); // add by wp  -- ��У��ӷְ���ͬ�����ĸ�������
	    filter.appendFilterItem("contractId", payReq.getContractId());
	    if(payReq.getId() != null)
	        filter.getFilterItems().add(new FilterItemInfo("id", payReq.getId(), CompareType.NOTEQUALS));
	    if(type.getPayType().getId().toString().equals("Ga7RLQETEADgAADDwKgOlOwp3Sw="))
	    {
	        String orgUnitId = FDCUtils.findCostCenterOrgId(ctx, payReq.getCurProject().getId().toString());
	        boolean isKeepBefore = FDCUtils.getDefaultFDCParamByKey(ctx, orgUnitId, "FDC600_KEEPBEFORESETTLEMENT");
	        if(!_exists(ctx, filter) && !isKeepBefore)
	            throw new PayRequestBillException(PayRequestBillException.CANTSELECTKEEPAMT);
	        FilterInfo myfilter = new FilterInfo();
	        myfilter.appendFilterItem("contractBill.id", payReq.getContractId());
	        EntityViewInfo view = new EntityViewInfo();
	        view.setFilter(myfilter);
	        view.getSelector().add("qualityGuarante");
	        SorterItemInfo sorterItem = new SorterItemInfo();
	        sorterItem.setPropertyName("qualityGuarante");
	        sorterItem.setSortType(SortType.DESCEND);
	        view.getSorter().add(sorterItem);
	        ContractSettlementBillCollection c = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(view);
	        BigDecimal amount = FDCHelper.ZERO;
	        if(c != null && c.size() != 0)
	            amount = FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(c.get(0).getQualityGuarante()));
	        view = new EntityViewInfo();
	        filter = new FilterInfo();
	        filter.appendFilterItem("contractId", payReq.getContractId());
	        filter.appendFilterItem("sourceBillId", null); // add by wp  -- ��У��ӷְ���ͬ�����ĸ�������
	        filter.appendFilterItem("paymentType.payType.id", "Ga7RLQETEADgAADDwKgOlOwp3Sw=");
	        view.setFilter(filter);
	        view.getSelector().add("amount");
	        PayRequestBillCollection payRequestBillC = getPayRequestBillCollection(ctx, view);
	        BigDecimal payReqAmount = FDCHelper.ZERO;
	        for(int i = 0; i < payRequestBillC.size(); i++)
	        {
	            PayRequestBillInfo info = payRequestBillC.get(i);
	            if(!info.getId().equals(payReq.getId()))
	                payReqAmount = payReqAmount.add(FDCHelper.toBigDecimal(info.getAmount()));
	        }
	
	        payReqAmount = payReqAmount.add(FDCHelper.toBigDecimal(payReq.getAmount()));
	        if(FDCHelper.toBigDecimal(payReqAmount).compareTo(amount) > 0)
	            throw new PayRequestBillException(PayRequestBillException.MORETHANQUALITYGUARANTE);
	    }
	    if(type.getPayType().getId().toString().equals("Ga7RLQETEADgAAC6wKgOlOwp3Sw=") && _exists(ctx, filter))
	        throw new PayRequestBillException(PayRequestBillException.CANTSELECTPROGRESSAMT);
	    if(type.getPayType().getId().toString().equals("Ga7RLQETEADgAAC/wKgOlOwp3Sw="))
	    {
	        FilterInfo myfilter = new FilterInfo();
	        myfilter.appendFilterItem("id", payReq.getContractId());
	        myfilter.appendFilterItem("hasSettled", Boolean.TRUE);
	        if(!ContractBillFactory.getLocalInstance(ctx).exists(myfilter))
	            throw new PayRequestBillException(PayRequestBillException.MUSTSETTLE);
	    }
	
	}
	
	//������ʱ���༭�����ʼ���ݴ����ȷ���
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		
		paramMap.put("isCost",Boolean.FALSE);
		Map initMap = super._fetchInitData(ctx,paramMap);		
		CompanyOrgUnitInfo currentFIUnit = ContextUtil.getCurrentFIUnit(ctx);

		String comId = null;
//		String reqPayId = null;
//		if(paramMap.get("reqPayId")!=null){
//			reqPayId = (String) paramMap.get("reqPayId");
//		}
	
		//��ͬ����
		String contractBillId = (String)paramMap.get("contractBillId");
		
		if(contractBillId==null){
			
			if(paramMap.get("ID")==null){
				return initMap;
			}
			
			String id = paramMap.get("ID").toString();
			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("contractId");	
			PayRequestBillInfo bill = (PayRequestBillInfo) this.getValue(ctx,new ObjectUuidPK(id),selector);
			contractBillId = bill.getContractId();
		}
		
		//initMap.put("payScale",FDCConstants.ZERO);
		
		if(contractBillId!=null){
			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("currency.*");
			selector.add("curProject.name");
			selector.add("curProject.number");
			selector.add("curProject.longNumber");
			selector.add("curProject.codingNumber");
			selector.add("curProject.displayName");
			selector.add("curProject.parent.id");
			selector.add("curProject.fullOrgUnit.name");
			selector.add("curProject.fullOrgUnit.code");
			selector.add("curProject.CU.name");
			selector.add("curProject.CU.number");
			selector.add("curProject.CU.code");
			selector.add("respDept.*");
			selector.add("partB.*");
			CurProjectInfo project = null;
			
			BOSObjectType  contractType=new ContractBillInfo().getBOSType();

			if(BOSUuid.read(contractBillId).getType().equals(contractType)){
				
				ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
						getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				//�������
				if (contractBill.isHasSettled()) {				
				
					FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
					builder.clear();
					builder.appendSql("select fqualityGuarante as amount from t_con_contractsettlementbill where fcontractbillid=");
					builder.appendParam(contractBillId);
					IRowSet rowSet = builder.executeQuery();
					BigDecimal amount = FDCHelper.ZERO;
					if (rowSet.size() == 1) {
						try {
							rowSet.next();
							amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
						} catch (SQLException e) {
							e.printStackTrace();
							throw new BOSException(e);
						}					
					}
					
					initMap.put("payScale",amount);
				}
				
				//��Ӧ��
				if (contractBill.getPartB()!=null){
					String supplierid= contractBill.getPartB().getId().toString();
					BOSUuid FIid = contractBill.getCurProject().getFullOrgUnit().getId();
					SupplierCompanyInfoInfo companyInfo = SupplierFactory.getLocalInstance(ctx).
							getCompanyInfo(new ObjectUuidPK(supplierid),new ObjectUuidPK(FIid));	
					initMap.put("supplierCompanyInfoInfo",companyInfo);
				}				
				
				project = contractBill.getCurProject();
				comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
				
				/*****
				 * 
				 * ��Ҫ����Ƿ�����ü׹��ĺ�ͬ��ص�������״̬���ֳ�ȷ�ϵ���
				 * ����У���
				 * 			���ֳ�ȷ�ϵ��ϵĽ��д����������ԭ���ֶΣ��н�������޸ģ�û����������������
				 * 			���ɸ������뵥���ֳ�ȷ�ϵ���¼
				 
				 */
				if(contractBill.isIsPartAMaterialCon()){
					BigDecimal confirmAmts = FDCHelper.ZERO;
					if(paramMap.get("Fw_ID")!=null){
						selector = new SelectorItemCollection();
						selector.add("confirmEntry.*");	
						PayRequestBillInfo bill = (PayRequestBillInfo) this.getValue(ctx,new ObjectUuidPK((String)paramMap.get("Fw_ID")),selector);
						for(int i=0;i<bill.getConfirmEntry().size();i++){
							confirmAmts = confirmAmts.add(FDCHelper.toBigDecimal(bill.getConfirmEntry().get(i).getReqAmount()));
						}
						initMap.put("confirmAmts", confirmAmts);
						initMap.put("confirmBillEntry", bill.getConfirmEntry());
					}
					else{
						EntityViewInfo view = new EntityViewInfo();
						selector = view.getSelector();
						selector.add("id");
						selector.add("confirmAmt");
						selector.add("toDateReqAmt");
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
						filter.getFilterItems().add(new FilterItemInfo("materialContractBill.id",contractBillId));
						view.setFilter(filter);
						MaterialConfirmBillCollection mcBills = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(view);
						confirmAmts = FDCHelper.ZERO;
						PayRequestBillConfirmEntryCollection confirmEntrys = new PayRequestBillConfirmEntryCollection();
						for(Iterator it=mcBills.iterator();it.hasNext();){
							MaterialConfirmBillInfo mcBill = (MaterialConfirmBillInfo)it.next();
							BigDecimal reqAmount = FDCHelper.toBigDecimal(mcBill.getConfirmAmt()).subtract(FDCHelper.toBigDecimal(mcBill.getToDateReqAmt()));
							if(reqAmount.compareTo(FDCHelper.ZERO)>0){
								PayRequestBillConfirmEntryInfo item = new PayRequestBillConfirmEntryInfo();
								item.setConfirmBill(mcBill);
								item.put("notIncludeThisReqAmount", mcBill.getToDateReqAmt());
								item.setReqAmount(reqAmount);
								confirmEntrys.add(item);
								confirmAmts = FDCHelper.add(reqAmount, confirmAmts);
							}
							
						}
						initMap.put("confirmAmts", confirmAmts);
						initMap.put("confirmBillEntry", confirmEntrys);
					}
				}
			}else{
				
				ContractWithoutTextInfo contractBill = ContractWithoutTextFactory.getLocalInstance(ctx).
				getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				//initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				project = contractBill.getCurProject();
				
				comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
			}

			
			//������Ŀ
			String projectId = (String) project.getId().toString();
			CurProjectInfo curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId));
			
			initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
			
			if(projectId!=null){
				initPeriod( ctx, projectId,curProjectInfo, comId, initMap ,false);
			}		
			
			//������Ŀ��Ӧ����֯
			String orgUnitId = null;
			if(curProjectInfo!=null &&  curProjectInfo.getCostCenter()!=null){
				orgUnitId = curProjectInfo.getCostCenter().getId().toString();
			}			
			//��õ�ǰ��֯		
			FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx)
				.getFullOrgUnitInfo(new ObjectUuidPK(orgUnitId));			
			initMap.put(FDCConstants.FDC_INIT_ORGUNIT,orgUnitInfo);
			
			//���»�ȡ������Ŀ��Ӧ�Ĳ�����֯   R101202-122 by zhiyuan_tang
			if (comId != null) {
				CompanyOrgUnitInfo company =GlUtils.getCurrentCompany(ctx, comId,null,false);	
				initMap.put(FDCConstants.FDC_INIT_COMPANY,company);
			}
			
			
			//���ø������Ϊ��ͬ�ĸ������ �Ӹ���й���
			EntityViewInfo view = new EntityViewInfo();
			selector = view.getSelector();
			selector.add("id");
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("contractBillId", contractBillId));
			filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED_VALUE+ ""));
			BillBaseCollection 	c = PaymentBillFactory.getLocalInstance(ctx).getBillBaseCollection(view);
			initMap.put("payTimes",new Integer(c.size()));
			
			//ContractChangeBillCollection
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractBillId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.VISA_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.ANNOUNCE_VALUE));
			filter.setMaskString("#0 and (#1 or #2 or #3)");
			view = new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("amount");
			view.getSelector().add("originalAmount");
			view.getSelector().add("balanceAmount");
			view.getSelector().add("oriBalanceAmount");
			view.getSelector().add("hasSettled");
			IObjectCollection collection = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
			initMap.put("ContractChangeBillCollection",collection);
			
			//PaymentBillCollection
		    view=new EntityViewInfo();
		    view.getSelector().add("contractBillId");
		    view.getSelector().add("billStatus");
		    view.getSelector().add("amount");
		    view.getSelector().add("fdcPayReqID");
		    filter=new FilterInfo();
		    view.setFilter(filter);
		    filter.getFilterItems().add(new FilterItemInfo("contractBillId",contractBillId));
		    filter.getFilterItems().add(new FilterItemInfo("billStatus",BillStatusEnum.PAYED_VALUE+""));
		    BillBaseCollection billBaseCollection = PaymentBillFactory.getLocalInstance(ctx).getBillBaseCollection(view);
		    initMap.put("PaymentBillCollection",billBaseCollection);
		        
		    //GuerdonOfPayReqBillCollection
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.appendFilterItem("payRequestBill.contractId", contractBillId);
			view.setFilter(filter);
			//TODO �ټ�һ��ʱ�����
			GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection = GuerdonOfPayReqBillFactory.getLocalInstance(ctx)
						.getGuerdonOfPayReqBillCollection(view);
			initMap.put("GuerdonOfPayReqBillCollection",guerdonOfPayReqBillCollection);
			
			//GuerdonBillCollection
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contract.id", contractBillId));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isGuerdoned",String.valueOf(1),CompareType.NOTEQUALS));
		
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("amount");
			view.getSelector().add("originalAmount");
			view.getSelector().add("isGuerdoned");
			GuerdonBillCollection guerdonBillCollection = GuerdonBillFactory.getLocalInstance(ctx).getGuerdonBillCollection(view);
			initMap.put("GuerdonBillCollection",guerdonBillCollection);
			
			//PartAOfPayReqBillCollection
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.appendFilterItem("payRequestBill.contractId", contractBillId);
			view.setFilter(filter);
			PartAOfPayReqBillCollection partAOfPayReqBillCollection = PartAOfPayReqBillFactory.getLocalInstance(ctx)
						.getPartAOfPayReqBillCollection(view);
			initMap.put("PartAOfPayReqBillCollection",partAOfPayReqBillCollection);
			
			//PartAConfmOfPayReqBillCollection
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.appendFilterItem("payRequestBill.contractId", contractBillId);
			view.setFilter(filter);
			PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection = PartAConfmOfPayReqBillFactory.getLocalInstance(ctx)
						.getPartAConfmOfPayReqBillCollection(view);
			initMap.put("PartAConfmOfPayReqBillCollection",partAConfmOfPayReqBillCollection);
			
			//MaterialConfirmBillCollection
			/*view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("mainContractBill.id", contractBillId));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("hasApplied",String.valueOf(1),CompareType.NOTEQUALS));
		
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("amount");
			view.getSelector().add("hasApplied");
			MaterialConfirmBillCollection materialConfirmBillCollection = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(view);
			initMap.put("MaterialConfirmBillCollection",materialConfirmBillCollection);*/
			
			//CompensationOfPayReqBillCollection
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.appendFilterItem("payRequestBill.contractId", contractBillId);
			view.setFilter(filter);
			//TODO �ټ�һ��ʱ�����
			CompensationOfPayReqBillCollection compensationOfPayReqBillCollection = CompensationOfPayReqBillFactory.getLocalInstance(ctx).getCompensationOfPayReqBillCollection(view);
			initMap.put("CompensationOfPayReqBillCollection",compensationOfPayReqBillCollection);
			
			//DeductTypeCollection
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			//���˼׹���
			filter.getFilterItems().add(new FilterItemInfo("id", DeductTypeInfo.partAMaterialType, CompareType.NOTEQUALS));
			view.setFilter(filter);
			final SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
			sorterItemInfo.setSortType(SortType.ASCEND);
			view.getSorter().add(sorterItemInfo);
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("amount");
			view.getSelector().add("allReqAmt");
			view.getSelector().add("allReqOriAmt");
			view.getSelector().add("allPaidAmt");
			view.getSelector().add("allPaidOriAmt");
			view.getSelector().add("originalAmount");
			DeductTypeCollection deductTypeCollection = DeductTypeFactory.getLocalInstance(ctx).getDeductTypeCollection(view);
			initMap.put("DeductTypeCollection",deductTypeCollection);
			
//			CostCenterOrgUnitInfo costCenterOrg = FDCHelper.getCostCenter(project,ctx);
//			
//			FullOrgUnitInfo orgInfo = null;
//			if(costCenterOrg!=null){
//				orgInfo = costCenterOrg.castToFullOrgUnitInfo();
//			}
			
			initMap.put("FullOrgUnitInfo",orgUnitInfo);
			
			try {
				Map resultMap = FDCUtils.getLastOriginalAmt_Batch(ctx, new String[]{contractBillId});
				if(resultMap!=null&&resultMap.containsKey(contractBillId)){
					initMap.put("lastestPriceOriginal",resultMap.get(contractBillId));
				}
				else{
					initMap.put("lastestPriceOriginal",FDCHelper.ZERO);
				}
			} catch (Exception e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
				SysUtil.abort();
			}
			
			
			
			
		}
		
		return initMap;
	}
	
    private SelectorItemCollection getSic(){
		// �˹���Ϊ��ϸ��Ϣ����
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("period.id"));
        sic.add(new SelectorItemInfo("period.beginDate"));	
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));	
        sic.add(new SelectorItemInfo("contractId"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("projectPriceInContract"));
        sic.add(new SelectorItemInfo("confirmEntry.*"));
        sic.add(new SelectorItemInfo("payDate"));
        sic.add(new SelectorItemInfo("paymentType.id"));
        sic.add(new SelectorItemInfo("paymentType.name"));
        return sic;
    }
    
	//�ύʱУ�鵥���ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ
    private void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		
		//�������ڵ�ǰ�ɱ��ڼ�֮ǰ
    	PayRequestBillInfo payReqBill = (PayRequestBillInfo)model;
		
		//�Ƿ����ò���һ�廯
		String comId = null;
		if( payReqBill.getCurProject().getFullOrgUnit()==null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(new ObjectUuidPK(payReqBill.getCurProject().getId().toString()),sic);
			
			comId = curProject.getFullOrgUnit().getId().toString();
		}else{
			comId = payReqBill.getCurProject().getFullOrgUnit().getId().toString();
		}
		//R110615-0438���������뵥�ύʱ����
		//checkConWorkLoad(ctx, payReqBill.getId(), model);
/*		û���жϵı�Ҫ
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractBill.getCurProject().getId().toString(),false);
			if((bookedPeriod!=null && contractBill.getPeriod() !=null) && contractBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				//�����ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
*/
		/***
		 * ����ȷ�ϵ����ύ�ж�
		 */
		/***
		 * �������Ĳ���ȷ�ϵ����Ƿ�ᳬ��ȷ�Ͻ��
		 */
		if(payReqBill.getConfirmEntry()!=null&&payReqBill.getConfirmEntry().size()>0){
			Set idSet = new HashSet();
			Map confirmBillEntryMap = new HashMap();
			for(int i=0;i<payReqBill.getConfirmEntry().size();i++){
				if(payReqBill.getConfirmEntry().get(i)!=null&&payReqBill.getConfirmEntry().get(i).getConfirmBill()!=null){
					idSet.add(payReqBill.getConfirmEntry().get(i).getConfirmBill().getId().toString());
					confirmBillEntryMap.put(payReqBill.getConfirmEntry().get(i).getConfirmBill().getId().toString(), payReqBill.getConfirmEntry().get(i));
				}
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.getSelector().add("*");
			filter.getFilterItems().add(new FilterItemInfo("id", idSet,CompareType.INCLUDE));
			view.setFilter(filter);
			MaterialConfirmBillCollection mcBills = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(view);
			for(int i=0;i<mcBills.size();i++){
				String key = mcBills.get(i).getId().toString();
				PayRequestBillConfirmEntryInfo entryInfo = (PayRequestBillConfirmEntryInfo)confirmBillEntryMap.get(key);
				entryInfo.setConfirmBill(mcBills.get(i));
			}
			for(int i=0;i<payReqBill.getConfirmEntry().size();i++){
				PayRequestBillConfirmEntryInfo entryInfo = payReqBill.getConfirmEntry().get(i);
				BigDecimal reqAmt = FDCHelper.toBigDecimal(entryInfo.getReqAmount());
				BigDecimal paidAmt = FDCHelper.toBigDecimal(entryInfo.getPaidAmount());
				if(reqAmt.compareTo(paidAmt)>0){
					BigDecimal thisReqNotPaid = reqAmt.subtract(paidAmt);
					BigDecimal confirmAmt     = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getConfirmAmt());
					BigDecimal confirmPaidAmt = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getPaidAmt());
					if(confirmPaidAmt.add(thisReqNotPaid).compareTo(confirmAmt)>0){
						throw new EASBizException(new NumericExceptionSubItem("000","�����Ĳ���ȷ�ϵ�:"+entryInfo.getConfirmBill().getNumber()+"  ��ȷ�ϵ�ʵ����� + ȷ�ϵ�����������   ����  ����ȷ�ϵ�ȷ�Ͻ�������ִ�д˲�����"));
					}
				}
			}
		}
		
	}
	
	//���У��
	private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
    
		PayRequestBillInfo model = (PayRequestBillInfo)billInfo;

        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null||model.getPaymentType()==null){
        	model= this.getPayRequestBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
       //checkConWorkLoad(ctx, billId,model);
        //��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
/*			û���жϵı�Ҫ
		//�Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();	
//			//�ɱ��Ѿ��½�
//			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
//			if(model.getPeriod().getBeginDate().after(bookedPeriod.getEndDate())){
//				throw new  ContractException(ContractException.AUD_AFTERPERIOD,new Object[]{model.getNumber()});
//			}
			
			//����û���½ᣬ������˵���{0}
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,false);
			if(finPeriod!=null && model.getPeriod()!=null && model.getPeriod().getBeginDate().after(finPeriod.getEndDate())){
				throw new  ContractException(ContractException.AUD_FINNOTCLOSE,new Object[]{model.getNumber()});
			}	
		}
		*/
		
		//budget contrl
		FDCBudgetAcctFacadeFactory.getLocalInstance(ctx).invokeBudgetCtrl(model, FDCBudgetConstants.STATE_AUDIT);
		
		
		/***
		 * ����ȷ�ϵ����ύ�ж�
		 */
		/***
		 * �������Ĳ���ȷ�ϵ����Ƿ�ᳬ��ȷ�Ͻ��
		 */
		if(model.getConfirmEntry()!=null&&model.getConfirmEntry().size()>0){
			Set idSet = new HashSet();
			Map confirmBillEntryMap = new HashMap();
			for(int i=0;i<model.getConfirmEntry().size();i++){
				if(model.getConfirmEntry().get(i)!=null&&model.getConfirmEntry().get(i).getConfirmBill()!=null){
					idSet.add(model.getConfirmEntry().get(i).getConfirmBill().getId().toString());
					confirmBillEntryMap.put(model.getConfirmEntry().get(i).getConfirmBill().getId().toString(), model.getConfirmEntry().get(i));
				}
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.getSelector().add("*");
			filter.getFilterItems().add(new FilterItemInfo("id", idSet,CompareType.INCLUDE));
			view.setFilter(filter);
			MaterialConfirmBillCollection mcBills = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(view);
			for(int i=0;i<mcBills.size();i++){
				String key = mcBills.get(i).getId().toString();
				PayRequestBillConfirmEntryInfo entryInfo = (PayRequestBillConfirmEntryInfo)confirmBillEntryMap.get(key);
				entryInfo.setConfirmBill(mcBills.get(i));
			}
			for(int i=0;i<model.getConfirmEntry().size();i++){
				PayRequestBillConfirmEntryInfo entryInfo = model.getConfirmEntry().get(i);
				BigDecimal reqAmt = FDCHelper.toBigDecimal(entryInfo.getReqAmount());
				BigDecimal paidAmt = FDCHelper.toBigDecimal(entryInfo.getPaidAmount());
				if(reqAmt.compareTo(paidAmt)>0){
					BigDecimal thisReqNotPaid = reqAmt.subtract(paidAmt);
					BigDecimal confirmAmt     = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getConfirmAmt());
					BigDecimal confirmPaidAmt = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getPaidAmt());
					if(confirmPaidAmt.add(thisReqNotPaid).compareTo(confirmAmt)>0){
						throw new EASBizException(new NumericExceptionSubItem("000","�����Ĳ���ȷ�ϵ�:"+entryInfo.getConfirmBill().getNumber()+"  ��ȷ�ϵ�ʵ����� + ȷ�ϵ�����������   ����  ����ȷ�ϵ�ȷ�Ͻ�������ִ�д˲�����"));
					}
				}
			}
		}
		
	}
	//���У��
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
		PayRequestBillInfo model = (PayRequestBillInfo)billInfo;

        if(model==null  || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null ){
        	model= this.getPayRequestBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
		//��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
/*			û���жϵı�Ҫ
 *  by sxhong 2009-07-30 22:20:49
		//�Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();

			//�����ڼ��ڹ�����Ŀ��ǰ�ڼ�֮ǰ�����ܷ����
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,false);
			if(bookedPeriod!=null && model.getPeriod()!=null && model.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				throw new  ContractException(ContractException.CNTPERIODBEFORE);
			}	
			
//			if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
//				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{model.getCurProject().getDisplayName()});
//			}	
		}*/
		
		FDCBudgetAcctFacadeFactory.getLocalInstance(ctx).invokeBudgetCtrl(model, FDCBudgetConstants.STATE_UNAUDIT);
	}
	
	protected boolean isCost() {
		return false;
	}
	
	protected String _getContractTypeNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		BOSObjectType  contractType=new ContractBillInfo().getBOSType();
		
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("contractId"));
        PayRequestBillInfo model = this.getPayRequestBillInfo(ctx,new ObjectUuidPK(pk.toString()),sic);
        	
		String contractBillId = model.getContractId();
		if(BOSUuid.read(contractBillId).getType().equals(contractType)){
			
	        sic = new SelectorItemCollection();
	        sic.add(new SelectorItemInfo("contractType.number"));
	        sic.add(new SelectorItemInfo("contractType.longNumber"));
			ContractBillInfo contractBillInfo = 
				ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractBillId),sic);
			
			return contractBillInfo.getContractType().getLongNumber().replace('!','.');
		}else{
			return "none";	
		}
		
	}
	
	//�ر�
	protected void _close(Context ctx, IObjectPK pk) throws BOSException, EASBizException {

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasClosed");
		PayRequestBillInfo bill = (PayRequestBillInfo) this.getValue(ctx,pk,selector);
		
		bill.setHasClosed(true);

		
		_updatePartial(ctx,bill, selector);
		
	}

	protected void _unClose(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		/***
		 * �������Ĳ���ȷ�ϵ����Ƿ�ᳬ��ȷ�Ͻ��
		 */
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("*");
		view.getSelector().add("confirmBill.*");
		filter.getFilterItems().add(new FilterItemInfo("parent", pk.toString()));
		view.setFilter(filter);
		PayRequestBillConfirmEntryCollection mcBills = PayRequestBillConfirmEntryFactory.getLocalInstance(ctx).getPayRequestBillConfirmEntryCollection(view);
		for(int i=0;i<mcBills.size();i++){
			PayRequestBillConfirmEntryInfo entryInfo = mcBills.get(i);
			BigDecimal reqAmt = FDCHelper.toBigDecimal(entryInfo.getReqAmount());
			BigDecimal paidAmt = FDCHelper.toBigDecimal(entryInfo.getPaidAmount());
			if(reqAmt.compareTo(paidAmt)>0){
				BigDecimal thisReqNotPaid = reqAmt.subtract(paidAmt);
				BigDecimal confirmAmt     = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getConfirmAmt());
				BigDecimal confirmPaidAmt = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getPaidAmt());
				if(confirmPaidAmt.add(thisReqNotPaid).compareTo(confirmAmt)>0){
					throw new EASBizException(new NumericExceptionSubItem("000","�����Ĳ���ȷ�ϵ�:"+entryInfo.getConfirmBill().getNumber()+"  ��ȷ�ϵ�ʵ����� + ȷ�ϵ�����������δ�����   ����  ����ȷ�ϵ�ȷ�Ͻ�������ִ�д˲�����"));
				}
			}
		}
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasClosed");
		PayRequestBillInfo bill = (PayRequestBillInfo) this.getValue(ctx,pk,selector);
		
		bill.setHasClosed(false);

		
		_updatePartial(ctx,bill, selector);
	}
	
	protected void _adjustPayment(Context ctx, IObjectPK payRequestBillId, Map dataMap) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		
	}
	
	//�Ƿ񳬹����¸���ƻ�
	protected boolean _outPayPlan(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		BOSObjectType  contractType=new ContractBillInfo().getBOSType();
		
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("contractId"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("payDate"));
        PayRequestBillInfo model = this.getPayRequestBillInfo(ctx,new ObjectUuidPK(pk.toString()),sic);
        	
		String contractBillId = model.getContractId();
		if(BOSUuid.read(contractBillId).getType().equals(contractType)){
			Date payDate = model.getPayDate();
			int year = payDate.getYear()+1900;
			int month = payDate.getMonth()+1;
			int number = year*100+month;
			
			BigDecimal planAmount = null;
			BigDecimal payAmount = null;
			
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("payDate");
			view.getSelector().add("contractId");
			view.getSelector().add("payAmount");
			
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("contractId", contractBillId));
					
			ContractPayPlanCollection contractPayPlanCollection = ContractPayPlanFactory
					.getLocalInstance(ctx).getContractPayPlanCollection(view);
			
			for (int i = 0; i < contractPayPlanCollection.size(); i++) {
				ContractPayPlanInfo info = contractPayPlanCollection.get(i);
				Date planDate = info.getPayDate();
				Calendar cal = Calendar.getInstance();
				cal.setTime(planDate);
						
				int key = cal.get(Calendar.YEAR)*100+ cal.get(Calendar.MONTH)+1;
				if(number==key){
					if(planAmount == null){
						planAmount = info.getPayAmount();
					}else if(info.getPayAmount() == null){
						planAmount = planAmount.add(info.getPayAmount());
					}					
				}
			}
			
			view = new EntityViewInfo();
			view.getSelector().add("payDate");
			view.getSelector().add("contractId");
			view.getSelector().add("amount");
			
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("contractId", contractBillId));
			PayRequestBillCollection col = this.getPayRequestBillCollection(ctx,view);
			for (int i = 0; i < col.size(); i++) {
				PayRequestBillInfo info = col.get(i);
				Date planDate = info.getPayDate();
				Calendar cal = Calendar.getInstance();
				cal.setTime(planDate);
						
				int key = cal.get(Calendar.YEAR)*100+ cal.get(Calendar.MONTH)+1;
				if(number==key){
					if(payAmount == null){
						payAmount = info.getAmount();
					}else if(info.getAmount() != null){
						payAmount = payAmount.add(info.getAmount());
					}					
				}
			}
			
			//������������0
			if(payAmount!=null ){
				if(planAmount==null){
					return true;
				}else{
					return planAmount.compareTo(payAmount)<0;
				}
			}
		}
		
		return false;
	}
		//��������ظ�����ȡ����
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
		handleIntermitNumberForReset(ctx, info);
	}
	
	//��ͬ�����ı����������룬�Ѿ�����handleIntermitNumber
	protected void handleIntermitNumberForReset(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException {

		FilterInfo filter = null;
		int i = 0;
		do {
			//��������ظ�����ȡ����
			handleIntermitNumber1(ctx, info, i);

			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", info.getNumber()));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(), CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", info.getOrgUnit().getId()));
			if (info.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
			}
			i++;
		} while (_exists(ctx, filter) && i < 10);
	}
	
	protected void _deleteForContWithoutText(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		super._delete(ctx,arrayPK);
	}
	
	protected void _reverseSave(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
//		super._reverseSave(ctx, pk, model);
		//����Ҫ��д
	}
	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum billOperStateEnum,
			IObjectValue relationInfo) throws BOSException, EASBizException {
//		super._reverseSave(ctx, srcBillPK, srcBillVO, billOperStateEnum, relationInfo);
		//����Ҫ��д
	}
	
	
	/**
	 * �жϸ������뵥�Ƿ�����Ԥ����ҵ�������������ĸ��������κ�һ�����򷵻��ǣ�����Ԥ����ҵ�񣻷����򷵻ط񣬲�����Ԥ����ҵ��
	 * һ��	�����¶ȸ���ƻ��걨���У���ǩ����ͬ����û�б��ƻ����ȼƻ���Ϊ0ʱ ����ͬԤ����ҵ��
	 * ����	�����¶ȸ���ƻ��걨���У���ͬδǩ��������ǩ�������ֲ��Ǳ��е�δǩ����ͬ����Ҫ���ʱ����ͬԤ����ҵ��
	 * ����	�����¶ȸ���ƻ��걨���У���ǩ����ͬ�걨�˵������ƻ�������ʵ���������ƻ���ʱ����ͬԤ����ҵ��
	 * �ġ�	�����¶ȸ���ƻ��걨���У�δǩ����ͬ�걨�˵������ƻ�������ʵ�����ʱ�����˸�δǩ����ͬ�������ƻ�������ͬԤ����ҵ��
	 * 
	 * ֻУ�鱾�������뱾�ڼƻ� by hpw 2009-08-24
	 * 
	 * @throws BOSException,EASBizException
	 */
	protected boolean _isOutBudget(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
        SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("contractId"));
		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("payDate"));
		sic.add(new SelectorItemInfo("id"));
		PayRequestBillInfo model = this.getPayRequestBillInfo(ctx,
				new ObjectUuidPK(pk.toString()), sic);
		// �����������ۼ�
		BigDecimal allAmount = FDCHelper.ZERO;
		// ����Ԥ���Ŀ�����ۼ�
		BigDecimal allPlanAmt = null;
		Date payDate = model.getPayDate();//ʱС�� ȡ��������
    	
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("amount");
    	view.getSelector().add("state");
    	view.getSelector().add("payDate");
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractId", model.getContractId()));
//    	filter.getFilterItems().add(new FilterItemInfo("createTime", model.getCreateTime(), CompareType.LESS_EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE, CompareType.NOTEQUALS));
    	// ����������,���������浥����ȡ��ǰ������������,�Ա����������޸�ʱ���ݴ���
    	if(model.getId() != null){
    		filter.getFilterItems().add(new FilterItemInfo("id", model.getId().toString(), CompareType.NOTEQUALS));
    	}
    	view.setFilter(filter);
    	PayRequestBillCollection payReqColl = null;
		try {
			payReqColl = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		} catch (BOSException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
			SysUtil.abort();
		}
    	
    	if(payReqColl != null){
    		for(int i=0;i<payReqColl.size();i++){
    			PayRequestBillInfo info = payReqColl.get(i);
    			if(info.getPayDate().getYear()==payDate.getYear()&&info.getPayDate().getMonth()==payDate.getMonth()){
    				allAmount = allAmount.add(FDCHelper.toBigDecimal(info.getAmount()));
    			}
    		}
    	}
    	allAmount = FDCHelper.add(allAmount, model.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
    	
		FDCBudgetPeriodInfo period = FDCBudgetPeriodInfo.getPeriod(payDate,
				false);
		Map dataMap = FDCBudgetAcctFacadeFactory.getLocalInstance(ctx)
				.getAssociateAcctPay(pk.toString(), period);
		if (dataMap != null && dataMap.get("splitEntrys") != null) {
			ContractCostSplitEntryCollection splitEntrys = (ContractCostSplitEntryCollection) dataMap
					.get("splitEntrys");
			for (Iterator iter = splitEntrys.iterator(); iter.hasNext();) {
				ContractCostSplitEntryInfo entry = (ContractCostSplitEntryInfo) iter
						.next();
				BigDecimal planAmt = entry.getBigDecimal("planAmt");
				if (allPlanAmt == null) {
					allPlanAmt = planAmt;
				} else if (planAmt != null) {
					allPlanAmt = allPlanAmt.add(planAmt);
				}
			}
			if (allAmount.compareTo(FDCHelper.toBigDecimal(allPlanAmt, 2)) >0) {
				return true;
			}
		}
		return false;
	}
	protected BOSUuid _getPaymentBillId(Context ctx)
			throws BOSException {
		// TODO Auto-generated method stub
		return payBillId;
	}
	protected BOSUuid _auditAndOpenPayment(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
//		try {
			_audit(ctx, billId);
//		} catch (EASBizException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return payBillId;
	}
	protected void _setUnAudited2Auditing(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		IBOSObject object = BOSObjectFactory.createBOSObject(ctx,billId.getType());
		IFDCBill iBillBase = (IFDCBill) object;
		iBillBase.setAudittingStatus(billId);
		
		FDCBillInfo billInfo = new FDCBillInfo();
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		iBillBase.updatePartial(billInfo,selector);
	}
	
		/**
	 * ��ȡ�������Ѹ���
	 * @author owen_wen 
	 */
	protected BigDecimal _getActPaied(Context ctx, IObjectValue payRequestBill) throws BOSException, EASBizException {
		PayRequestBillInfo prb = getPayRequestBillForBg(ctx, payRequestBill);
		BigDecimal actPaied = FDCHelper.ZERO;
		Date firstDay = FDCDateHelper.getFirstDayOfMonth(prb.getBookedDate());
		Date lastDay = FDCDateHelper.getLastDayOfMonth(prb.getBookedDate());
		FDCSQLBuilder builderActPaied = new FDCSQLBuilder(ctx);
		builderActPaied.appendSql("select sum(FLocalAmount) as actPaied " + "from t_cas_paymentbill  as pay where pay.FBillStatus = 15 "
				+ "and pay.FBizDate >= ");
		builderActPaied.appendParam(firstDay);
		builderActPaied.appendSql(" and pay.FBizDate <= ");
		builderActPaied.appendParam(lastDay);
		builderActPaied.appendSql(" and pay.FCurProjectID = ");
		builderActPaied.appendParam(prb.getCurProject().getId().toString());
		builderActPaied.appendSql(" and pay.FContractBillID = ");
		builderActPaied.appendParam(prb.getContractId());
		// �鿴״̬����ǰ��
		if (prb.getLastUpdateTime() != null) {
			builderActPaied.appendSql(" and pay.FLastUpdateTime <= ");
			builderActPaied.appendSql("{ts '");
			builderActPaied.appendSql(FMConstants.FORMAT_TIME.format(prb.getLastUpdateTime()));
			builderActPaied.appendSql("' }");
		}
		IRowSet rowSetActPaied = builderActPaied.executeQuery();
		try {
			while (rowSetActPaied.next()) {
				if (rowSetActPaied.getString("actPaied") != null) {
					actPaied = rowSetActPaied.getBigDecimal("actPaied");
				}
			}
		} catch (SQLException e) {
			logger.info(e.getMessage(), e);
			e.printStackTrace();
			throw new BOSException(e);
		}
		return actPaied;
	}

	/**
	 * ΪԤ��׼���������뵥����Ҫ�ǲ��Ԥ�����õ���һЩ�ֶΣ���_getFloatFund()��_getLocalBudget()��_getActPaied()ʹ��
	 * @author��owen_wen
	 * @CreateTime��2012-7-13
	 */
	private PayRequestBillInfo getPayRequestBillForBg(Context ctx, IObjectValue payRequestBill) throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("id"));
		selector.add(new SelectorItemInfo("bookedDate"));
		selector.add(new SelectorItemInfo("curProject.id"));
		selector.add(new SelectorItemInfo("contractId"));
		selector.add(new SelectorItemInfo("lastUpdateTime"));
		selector.add(new SelectorItemInfo("planHasCon.id"));
		selector.add(new SelectorItemInfo("planUnCon.id"));

		PayRequestBillInfo prb = (PayRequestBillInfo) payRequestBill;
		return PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(prb.getId()), selector);
	}
	
	/**
	 * ��ȡ��������;��
	 * @author owen_wen
	 */
	protected BigDecimal _getFloatFund(Context ctx, IObjectValue payRequestBill) throws BOSException, EASBizException {
		PayRequestBillInfo prb = getPayRequestBillForBg(ctx, payRequestBill);
		BigDecimal floatFund = FDCHelper.ZERO;
		Date firstDay = FDCDateHelper.getFirstDayOfMonth(prb.getBookedDate());
		Date lastDay = FDCDateHelper.getLastDayOfMonth(prb.getBookedDate());
		FDCSQLBuilder builderFloatFund = new FDCSQLBuilder(ctx);
		builderFloatFund.appendSql(" select ");
		builderFloatFund.appendSql(" sum(case reqC when 1 then (case payS when 15 then 0 else payA end ) ");
		builderFloatFund.appendSql(" else (case payS when 15 then reqA-payA else reqA end) end) as floatFund ");
		builderFloatFund.appendSql(" from ( select  ");
		builderFloatFund.appendSql(" req.FAmount as reqA, ");
		builderFloatFund.appendSql(" req.FHasClosed as reqC, ");
		builderFloatFund.appendSql(" pay.FAmount as payA, ");
		builderFloatFund.appendSql(" pay.FBillStatus as payS ");
		builderFloatFund.appendSql(" from T_CON_PayRequestBill as req ");
		builderFloatFund.appendSql(" left join T_CAS_PaymentBill as pay ");
		builderFloatFund.appendSql(" on pay.FFdcPayReqID = req.FID ");
		builderFloatFund.appendSql(" where req.FState in ");
		Set payStates = new HashSet();
		payStates.add(FDCBillStateEnum.SUBMITTED_VALUE);
		payStates.add(FDCBillStateEnum.AUDITTING_VALUE);
		payStates.add(FDCBillStateEnum.AUDITTED_VALUE);
		builderFloatFund.appendSql(FMHelper.setTran2String(payStates));
		builderFloatFund.appendSql(" and req.FCurProjectID = ? ");
		builderFloatFund.addParam(prb.getCurProject().getId().toString());
		builderFloatFund.appendSql(" and req.FBookedDate >= ? ");
		builderFloatFund.addParam(firstDay);
		builderFloatFund.appendSql(" and req.FBookedDate <= ? ");
		builderFloatFund.addParam(lastDay);
		builderFloatFund.appendSql(" and req.FContractID = ? ");
		builderFloatFund.addParam(prb.getContractId());
		if (prb.getId() != null) {
			builderFloatFund.appendSql(" and req.FID != ? ");
			builderFloatFund.addParam(prb.getId().toString());
		}
		// �鿴״̬����ǰ��
		if (prb.getLastUpdateTime() != null) {
			builderFloatFund.appendSql(" and req.FLastUpdateTime <= ");
			builderFloatFund.appendSql("{ts '");
			builderFloatFund.appendSql(FMConstants.FORMAT_TIME.format(prb.getLastUpdateTime()));
			builderFloatFund.appendSql("' }");
		}
		builderFloatFund.appendSql(" ) as tmp ");
		IRowSet rowSetFloatFund = builderFloatFund.executeQuery();
		try {
			while (rowSetFloatFund.next()) {
				if (rowSetFloatFund.getString("floatFund") != null) {
					floatFund = rowSetFloatFund.getBigDecimal("floatFund");
				}
			}
		} catch (SQLException e) {
			logger.info(e.getMessage(), e);
			e.printStackTrace();
			throw new BOSException(e);
		}
		return floatFund;
	}

	/**
	 * ��ȡ������Ԥ���
	 * @author owen_wen
	 */
	protected BigDecimal _getLocalBudget(Context ctx, IObjectValue payRequestBill) throws BOSException, EASBizException {
		PayRequestBillInfo prb = getPayRequestBillForBg(ctx, payRequestBill);
		BigDecimal localBudget = FDCHelper.ZERO;
		Date firstDay = FDCDateHelper.getFirstDayOfMonth(prb.getBookedDate());
		Date lastDay = FDCDateHelper.getLastDayOfMonth(prb.getBookedDate());
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if (prb.getPlanHasCon() != null) {
			builder.appendSql("select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
			builder.appendSql("from T_FNC_FDCDepConPayPlanContract as con ");
			builder.appendSql("left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
			builder.appendSql("left join T_FNC_FDCDepConPayPlanCE as entry on entry.FParentC = con.FID ");
			builder.appendSql("left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
			builder.appendSql("where 1=1 ");
			builder.appendSql(" and entry.FMonth >= ");
			builder.appendParam(firstDay);
			builder.appendSql(" and entry.FMonth <= ");
			builder.appendParam(lastDay);
			builder.appendSql(" and con.FID = '");
			builder.appendSql(prb.getPlanHasCon().getId().toString()).appendSql("' ");
			builder.appendSql(" and con.FProjectID = ");
			builder.appendParam(prb.getCurProject().getId().toString());
			builder.appendSql("order by head.FYear desc,head.FMonth DESC");
			IRowSet rowSet = builder.executeQuery();
			try {
				while (rowSet.next()) {
					if (rowSet.getString("localBudget") != null) {
						localBudget = rowSet.getBigDecimal("localBudget");
					}
				}
			} catch (SQLException e) {
				logger.info(e.getMessage(), e);
				e.printStackTrace();
				throw new BOSException(e);
			}
		} 
		if (prb.getPlanUnCon() != null) {
			builder.appendSql(" select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
			builder.appendSql(" from T_FNC_FDCDepConPayPlanUC as con ");
			builder.appendSql(" left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FParentID ");
			builder.appendSql(" left join T_FNC_FDCDepConPayPlanUE as entry on entry.FParentID = con.FID ");
			builder.appendSql(" left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
			builder.appendSql(" where (head.FState = '4AUDITTED' or head.FState = '10PUBLISH') ");
			builder.appendSql(" and entry.FMonth >= ");
			builder.appendParam(firstDay);
			builder.appendSql(" and entry.FMonth <= ");
			builder.appendParam(lastDay);
			builder.appendSql(" and con.FID = '");
			builder.appendSql(prb.getPlanUnCon().getId().toString()).appendSql("' ");
			builder.appendSql(" and con.FProjectID = ");
			builder.appendParam(prb.getCurProject().getId().toString());
			builder.appendSql("order by head.FYear desc,head.FMonth DESC");
			IRowSet rowSet = builder.executeQuery();
			try {
				while (rowSet.next()) {
					if (rowSet.getString("localBudget") != null) {
						localBudget = rowSet.getBigDecimal("localBudget");
					}
				}
			} catch (SQLException e) {
				logger.info(e.getMessage(), e);
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		return localBudget;
	}

	/**
	 * ��ȡ�����ڿ���Ԥ�㡱
	 * @author owen_wen 
	 */
	protected BigDecimal _getBgBalance(Context ctx, IObjectValue payRequestBill) throws BOSException, EASBizException {
		return _getLocalBudget(ctx, payRequestBill).subtract(_getFloatFund(ctx, payRequestBill))
				.subtract(_getActPaied(ctx, payRequestBill));
	}
	
	/**
	 * ����Ԥ�� - R140729-0038
	 * 
	 * @author RD_zhaoqin
	 * @date 2014/12/31
	 */
	private void returnBudgetLocal(Context ctx, IObjectPK pk) throws EASBizException, BOSException {
		if(null == pk)
			return;
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("orgUnit.id");
		PayRequestBillInfo payRequestBillInfo = (PayRequestBillInfo)getValue(ctx, pk, selector);
		
		boolean isMbgCtrl = FDCUtils.getBooleanValue4FDCParamByKey(ctx, payRequestBillInfo.getOrgUnit().getId().toString(),
				FDCConstants.FDC_PARAM_STARTMG);
        if (isMbgCtrl ) {
        	IBudgetCtrlFacade iBgCtrl = BudgetCtrlFacadeFactory.getLocalInstance(ctx);
        	iBgCtrl.returnBudgetLocal(BOSUuid.read(pk.toString()));
        }
	}
}
