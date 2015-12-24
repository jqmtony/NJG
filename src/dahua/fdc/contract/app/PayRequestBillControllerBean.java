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
		
		//R101207-373 付款申请单修改提交时，需要重新计算后续申请单的“合同内工程累计申请”的值  by zhiyuan_tang 2010/12/20
		recountReqAmt(ctx, ((PayRequestBillInfo)model).getContractId());
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException
	{
		IObjectPK pk = super._save(ctx, model);
		
		//R101207-373 付款申请单修改提交时，需要重新计算后续申请单的“合同内工程累计申请”的值  by zhiyuan_tang 2010/12/20
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
		//扣款与奖励默认新增,索赔默认不新增
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
		//记录info的来源
		PayRequestBillInfo info  =(PayRequestBillInfo)model;
		if(info.getSource()==null && info.getContractId()!=null){
			info.setSource(BOSUuid.read(info.getContractId()).getType().toString());
		}
		if(info.getContractId()!=null){
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(new FilterInfo());
			//EntityViewInfo可能会导致info.getContractId()自动加上NVarchar的转义符导致获取不到数据，加上toString()确保数据正确。
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
			 * 更新关联的甲供材料确认单的累计申请金额
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
		//提交前检查
		boolean hasUsed=FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_ACCTBUDGET);
		checkBillForSubmit( ctx,model);
		final IObjectPK pk = super._submit(ctx, model);
		
		//R101207-373 付款申请单修改提交时，需要重新计算后续申请单的“合同内工程累计申请”的值  by zhiyuan_tang 2010/12/20
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
	 * 描述：反审批
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
		fdc.appendSql("contract.FCONTRACTTYPEID=ctype.FID where contract.FCURPROJECTID=? and ctype.fname_l2='[建安]' ");
		fdc.appendSql("and contract.fcontrolunitid=? and contract.FcontractPropert='DIRECT'");
		fdc.addParam(proId);
		fdc.addParam(ContextUtil.getCurrentCtrlUnit(ctx).getId().toString());
		IRowSet sets = fdc.executeQuery();
		if(sets.next()){
			id = sets.getString(1);
		}
		return id;
	}
	
	/** 获取编码规则生成的编码 
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
			//throw new EASBizException(new NumericExceptionSubItem("100", "总包合同付款申请单由系统自动生成，用户不能进行审核操作！"));
			if("[施工]".equals(typeName) || "[材料]".equals(typeName) || "[分包]".equals(typeName)) {
				realAudit(ctx, billId);
				//在总包下生成一个付款申请单
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
				//分包合同中的乙方理论上是付款申请单的收款单位，但付款申请单的收款单位可以修改，会出现两者不一样的情况
				//我的处理是按照分包合同中的乙方，然后将合同编码简写+乙方名称填写在备注里
				//SupplierInfo supplier = SupplierFactory.getLocalInstance(ctx).getSupplierInfo(new ObjectUuidPK(astContract.getPartB().getId()));
				if("[施工]".equals(typeName))
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
				//由生成的申请单生成付款单
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
	//modify by yxl 20151215 将原来的审核逻辑拿出来，方便之前对付款申请单修改（之前是放在beanEx里）
	private void realAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException, PayRequestBillException {
		checkBillForAudit( ctx,billId,null);
		
		PayRequestBillInfo billInfo = new PayRequestBillInfo();
		billInfo.setId(billId);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		// 审核日期
		billInfo.setAuditTime(DateTimeUtils.truncateDate(new Date()));
		// 状态
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		_updatePartial(ctx, billInfo, selector);
		//处理扣款项,改为调整款项选择的时候就处理
		selector = new SelectorItemCollection();
		selector.add("contractId");
		selector.add("*");
		selector.add("curProject.id");
		//取出工程项目的财务组织供生成付款单时用　 by sxhong 2009-06-04 18:28:23
		selector.add("curProject.fullOrgUnit.id");
		selector.add("orgUnit.id");
		selector.add("realSupplier.number");
		selector.add("realSupplier.name");
		selector.add("supplier.number");
		selector.add("supplier.name");
		selector.add("CU.name");
		selector.add("CU.number");
		//期间
		selector.add("period.number");
		selector.add("period.beginDate");
		selector.add("period.endDate");
		// by tim_gao 币种 
		selector.add("currency.*");
		
		PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getLocalInstance(ctx)
			.getPayRequestBillInfo(new ObjectUuidPK(billId),selector);
		//payRequestBillInfo.getRealSupplier().getName();
		
		//截止上期累计实付在付款申请单保存或者是提交的时候取得是合同上的prjPriceInConPaid，并且只有保存或者提交的时候才会将合同上的prjPriceInConPaid
		//同步保存到付款申请单上的lstPrjAllPaidAmt字段上去.系统如此处理在以下场景会存在问题:B付款申请单在A付款申请单没有付款之前就提交了，因为A还没有付款
		//合同上的 prjPriceInConPaid为空，那么B在保存提交的时候同步给B付款申请单的值就为空，此时将A付款，然后审批B发现截止上期累计实付还是为空。如果
		//这时候将B付款申请单再重新提交一下就好了.
		//为了防止这种问题产生，我们可以在审批的时候按照如下方式显式保存一下                  by cassiel_peng 2010-1-5
		
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
		 // 无文本没有付款计划..（无文本合同审批后自动生成审批的付款申请单。）
		if (hasConPlan && FDCUtils.isContractBill(ctx, payRequestBillInfo.getContractId())) {
			//R140610-0199	modified by ken_liu...不校验计划付款是否为0。但仍要校验是否选择了计划。
			/*if (payRequestBillInfo.getCurPlannedPayment() == null || FDCConstants.ZERO.compareTo(payRequestBillInfo.getCurPlannedPayment()) == 0) {
				throw new PayRequestBillException(PayRequestBillException.MUSTCONPAYPLAN);
			}*/
			if (payRequestBillInfo.getPlanHasCon() == null && payRequestBillInfo.getPlanUnCon()==null) {
				throw new PayRequestBillException(PayRequestBillException.MUSTCONPAYPLAN);
			}
		}
		
		//鑫苑要求 根据当前时间设置对应期间
		String companyID = payRequestBillInfo.getCurProject().getFullOrgUnit().getId().toString();
		boolean isInCore = FDCUtils.IsInCorporation(ctx, companyID);
		if(isInCore){//去掉参数，启用月结统一按以下逻辑处理
			String prjId = payRequestBillInfo.getCurProject().getId().toString();
			// 财务期间
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, false);
			PeriodInfo billPeriod = payRequestBillInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//最终所在期间
			Date bookedDate = DateTimeUtils.truncateDate(payRequestBillInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","单据所对应的组织没有当前时间的期间，请先设置！"));
			}
			/***************
			 * （1）当付款申请单上的“业务日期”和“业务期间”大于“审批日期”（工程项目成本财务“当前期间”）时，付款单的“申请期间”和“申请日期”取付款申请单的“业务期间”和“业务日期”
			 * （2）当付款申请单上的“业务日期”和“业务期间”小于等于“审批日期”（工程项目成本财务“当前期间”）时，付款单的“申请期间”和“申请日期”取（工程项目成本财务“当前期间”）和审批日期，且将审批日期返写回付款申请单上的“业务日期”和“业务期间”。
			 *	
			 *	原理与拆分保存时相同，期间老出问题
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
			//设置期间后新增
			payRequestBillInfo.setBookedDate(bookedDate);
		}
		PaymentBillInfo payBillInfo = null;
		try {
			payBillInfo = createPayment(ctx,payRequestBillInfo);
		} catch (Exception e) {
			throw new EASBizException(new NumericExceptionSubItem("100","生成付款单时出错:"+e.getMessage()),e);//new BOSException(e.getMessage());//new PayRequestBillException(PayRequestBillException.CHECKTEXTLENGTH1);
		}
			
		if(payBillInfo != null){
			payBillId = payBillInfo.getId();
			
			try {
				updateFDCPaymentBillinvoice(ctx, payRequestBillInfo, payBillInfo);
			} catch (Exception e) {
				throw new EASBizException(new NumericExceptionSubItem("100","更新房地产付款单中间表时出错:"+e.getMessage()),e);
			}
		}
		updateContractExecInfos(ctx, payRequestBillInfo.getContractId(), billId.toString(), true);
	}

	/**
	 * 描述：审批反审批时更新合同执行信息中已完工工程量未完工工程量
	 * @param contractId
	 * @param string 
	 * @param b true 审批, false 反审批
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author：keyan_zhao
	 * @CreateTime：2012-6-14
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
			 * 如果结算并已付结算款 则已完工为合同结算价、未完工为0 
			 * 未结算付结算款 则已完工为所有已付进度款已完工之和、未完工=结算价-已完工
			 * 若未结算 则已完工为所有已付款进度款已完工之和 未完工 = 合同金额 + 变更金额 - 已完工
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
	 * 获得合同付款单所有已付款的付款类型为进度款的已完工合计
	 * @param ctx 上下文
	 * @param contractId 合同id
	 * @param b 
	 * @param string 
	 * @param paymentType 付款类型id 
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

		/****判断是否启用工程量分离参数，如果启用参数，则直接取工程量清单的值 -by neo***/
		if (FDCUtils.getDefaultFDCParamByKey(ctx, companyID, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)) {
			temp = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(contractId);
		} else {
			builder.appendSql(" select a.fcontractid as contractId, sum(a.FCompletePrjAmt) as amount "
					+ " from t_con_payrequestbill as a  ");
			builder.appendSql("inner join T_FDC_PaymentType type on type.fid = a.FPaymentType");
			builder.appendParam("where (type.FPayTypeID", PaymentTypeInfo.progressID);
			/**
			 * 周鹏需求，已实现成本不包含付款类型为预付款的拆分
			 */
			builder.appendSql(" and type.fname_l2 !='预付款' \n ");
			
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
		//付款类型
		selector.add("paymentType.payType.number");

		PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(billId),selector);
		
		String contractID= payRequestBillInfo.getContractId();
		
		//无文本合同的反审批在无文本处维护
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
		
		
		//处理扣款项
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
	
        // 20050511 吴志敏提供预算接口
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
		// 审核人
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		// 审核日期
		billInfo.setAuditTime(DateTimeUtils.truncateDate(new Date()));
		// 状态
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		_updatePartial(ctx, billInfo, selector);	*/		
	}
	
	/**
	 * 
	 * 描述：检查名称重复
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd 创建时间：2006-8-24
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
		 * 得到与扣款类型对应的deductOfPayReqBillInfo的map
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
		{//by tim_gao 为什么要这么写,新增完在update 一次做好提交不行么
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
		 * 过滤出DeductOfPayReqEntryBill内已含有的扣款单
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
		{//得到要过滤id集合放入set内
			DeductOfPayReqBillEntryCollection c;
			c = DeductOfPayReqBillEntryFactory.getLocalInstance(ctx).getDeductOfPayReqBillEntryCollection(view);
			DeductOfPayReqBillEntryInfo info;
			for(int i=0;i<c.size();i++){
				info = c.get(i);
				set.add(info.getDeductBillEntry().getId()); //分录的ID
			}
		} catch (BOSException e)
		{
			logger.info(e);
			SysUtil.abort();
		}
		
		/*
		 * 从DeductBillEntry取出所有满足条件的扣款单
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
		// 同时删除关联的扣款单列表
		_delete(ctx, new IObjectPK[]{pk});
	}
	protected IObjectPK[] _delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException
	{
		//暂不支持的操作
//		throw new UnsupportedOperationException();
		throw new EASBizException(PayRequestBillException.NOTSUPPORTOPRT);
//		return super._delete(ctx, filter);
	}
	
	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		//删除关联的扣款项
		deleteOfPayRequest(ctx, new IObjectPK[]{pk});
		super._cancel(ctx,pk);
	}
	
	protected void deleteOfPayRequest(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException
	{

		//付款申请单合同ID
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

		//删除关联的扣款项		
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
		//删除关联的奖励单,其过滤条件与扣款项同
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
			//删除关联的违约单,其过滤条件与奖励项同
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
			//参数为是时，删除关联的甲供扣款单
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
			//删除甲供材扣款单多次扣款过程明细
			FilterInfo _filter = new FilterInfo();
			_filter.getFilterItems().add(new FilterItemInfo("payReqID",arrayPKSet,CompareType.INCLUDE));
			ShowDeductOfPartABillFactory.getLocalInstance(ctx).delete(_filter);
			
//		}else{
			//参数为是时，删除关联的甲供确认单
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

		//删除关联的扣款项	
		deleteOfPayRequest(ctx,arrayPK);
		
		PayRequestBillInfo payReq=new PayRequestBillInfo();
		payReq.put("arrayPK",arrayPK);
		payReq.put("contractId", contractId);//删除时只能删除一个合同的多个申请单
		/**
		 * 删除时，生成的待签定合同分录删除，并更新原待签定合同状态，使其它合同可以关联 by hpw 20010-01-04
		 * TODO 暂只支持单张删除,多张单不同付款期间目前还没有这种应用场景
		 */
		FDCBudgetPeriodInfo period=FDCBudgetPeriodInfo.getPeriod(model.getPayDate(),false);
		payReq.put("period", period);
		FDCBudgetAcctFacadeFactory.getLocalInstance(ctx).invokeBudgetCtrl(payReq, FDCBudgetConstants.STATE_DELETE);
		
		/*****
		 * 更新关联的甲供材料确认单的累计申请金额
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
		
		//R101207-373 多处需要重现计算后续申请单的“合同内工程累计申请”的值 , 提取成一个方法 by zhiyuan_tang 2010/12/20
		recountReqAmt(ctx, contractId);

/*		//无文本的删除统一在无文本的地方维护
//		删除关联的无文本合同		
		if(contractId!=null){
			String sql = "delete from T_CON_ContractWithoutText where fid = ? ";
			Object []params = new Object[] {contractId};
			 DbUtil.execute(ctx, sql, params);
		}*/
		// 更新进度关联id
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
	 * 描述：重算合同对应的所有付款申请单的累计申请额（累计申请额＝上期累计申请＋本期发生额）
	 *      多处需要重现计算后续申请单的“合同内工程累计申请”的值 , 所以提取成一个方法
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
				//等于0
				info.setPrjAllReqAmt(info.getProjectPriceInContract());
				info.setAddPrjAllReqAmt(info.getAddProjectAmt());
				info.setPayPartAMatlAllReqAmt(info.getPayPartAMatlAmt());
			}else{
				//等于上期累计＋本期发生
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
		//暂不支持的操作
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
/*		//与申请单相同的月份之前的
		Timestamp createTime = billInfo.getCreateTime();
		Calendar cal=Calendar.getInstance();
		cal.setTime(createTime);
		createTime.setDate(cal.getActualMaximum(Calendar.DATE));
		createTime.setHours(cal.getActualMaximum(Calendar.HOUR_OF_DAY)); //24小时制
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
	
	//生成付款单
	private PaymentBillInfo createPayment(Context ctx,PayRequestBillInfo payReqBill)throws Exception{
		//当付款申请单生成付款单后，默认会关闭
		//如果把生成的付款单删除后 需要再次生成付款单 则需要反关闭此付款申请单
		if(payReqBill.isHasClosed()){
			return null;
		}
		BOSObjectType bosType = new PaymentBillInfo().getBOSType();

		IBTPManager iBTPManager = BTPManagerFactory.getLocalInstance(ctx);
		BTPTransformResult result = iBTPManager.transform(payReqBill, bosType.toString());

		IObjectCollection destBillColl = result.getBills();
		BOTRelationCollection botRelateColl = result.getBOTRelationCollection();

		PaymentBillInfo destBillInfo = null;

		//2009-1-12 取财务组织  
		//其实实体财务组织可以用工程项目上的fullorgunit　modify  by sxhong 2009-06-04 18:31:24
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
		
		//付款单生成之后，部分属性处理
		//此处处理BOTP未设置的属性 
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
				destBillInfo.setBizDate(payReqBill.getBookedDate());//房地产维护bookeddate，默认与申请单相同
//			}
		  //需求要求把付款日期改成审批时的日期
//			if(destBillInfo.getPayDate()==null){//botp未设置付款时间时取申请单付款日期,付款时取付款时的日期
//				destBillInfo.setPayDate(payReqBill.getPayDate());
//			}
			if(destBillInfo.getCurProject()==null){
				destBillInfo.setCurProject(payReqBill.getCurProject());
			}
			if(destBillInfo.getBillStatus()==null){
				destBillInfo.setBillStatus(BillStatusEnum.SAVE);
			}
			//by tim_gao 币别，汇率不管如何都要和合同的统一，即跟合同统一
			//因为付款单对付款申请单，可以多对1 ，如果每个币别，汇率不同，在计算累计发票，以及其他累计问题就头大了
			//如果日后像做成灵活的，请分析好再修改
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
			//是否提交付款
			destBillInfo.setIsNeedPay(payReqBill.isIsPay());
			//备注
			if(destBillInfo.getDescription()==null){
				destBillInfo.setDescription(payReqBill.getDescription());
			}
			//款项说明
			if(destBillInfo.getSummary()==null){
				destBillInfo.setSummary(payReqBill.getMoneyDesc());
			}
			//紧急程度
			if(destBillInfo.getUrgentDegree()==null){
				destBillInfo.setUrgentDegree(UrgentDegreeEnum.getEnum(String.valueOf(payReqBill.getUrgentDegree().getValue())));
			}
			//destBillInfo.setIsEmergency(IsMergencyEnum.getEnum(String.valueOf(payReqBill.getUrgentDegree().getValue())));
			//紧急程度
			if(destBillInfo.getIsEmergency()==null){
				if(destBillInfo.getUrgentDegree()!=null&&destBillInfo.getUrgentDegree().equals(UrgentDegreeEnum.URGENT)){
					destBillInfo.put("isEmergency", new Integer(1));
				}else{
					destBillInfo.put("isEmergency", new Integer(0));
				}
			}
			//收款银行账户
			if(destBillInfo.getPayeeBank()==null){
				destBillInfo.setPayeeBank(payReqBill.getRecBank());
			}
			if(destBillInfo.getPayeeAccountBank()==null){
				destBillInfo.setPayeeAccountBank(payReqBill.getRecAccount());
			}
			
			//同城异地以及用途
			destBillInfo.setIsDifferPlace(payReqBill.getIsDifferPlace());
			if(destBillInfo.getUsage()==null){
				destBillInfo.setUsage(payReqBill.getUsage());
			}
			destBillInfo.setAccessoryAmt(payReqBill.getAttachment());
			
			//收款人
			if(payReqBill.getSupplier()!=null){
				destBillInfo.setPayeeID(payReqBill.getSupplier().getId().toString());
				destBillInfo.setPayeeNumber(payReqBill.getSupplier().getNumber());
				destBillInfo.setPayeeName(payReqBill.getSupplier().getName());
			}
			//付款类型
			if(destBillInfo.getFdcPayType()==null){
				destBillInfo.setFdcPayType(payReqBill.getPaymentType());
			}
			
			/**增加参数，根据参数控制付款申请单审批结束以后生成的付款单的制单人的取值（R090520-176）——by neo **/
			boolean isCreator = FDCUtils.getDefaultFDCParamByKey(ctx, 
					ContextUtil.getCurrentFIUnit(ctx).getId().toString(),FDCConstants.FDC_PARAM_PAYMENTCREATOR);
			if(isCreator){
				destBillInfo.setCreator(payReqBill.getCreator());
			}
			
			/**如果是无文本合同：
			 * 在启用财务成本一体化参数时，若勾选“无需付款”，则出来s“贷方科目”字段，
			 * 审批后，该字段金额自动填入“付款科目”，相应的付款单亦自动“已付款”状态；
			 * 若不启用一体化参数，若勾选“无需付款”，无文本合同审批后，对应的付款单自动变为“已付款”。*/
			
			ContractWithoutTextInfo model = null;
			if(!BOSUuid.read(payReqBill.getContractId()).getType().equals(contractType)){
				model = (ContractWithoutTextInfo)ContractWithoutTextFactory.getLocalInstance(ctx).
						getContractWithoutTextInfo(new ObjectUuidPK(payReqBill.getContractId()),selectors);
				if(destBillInfo.getPayerAccount()==null){
					destBillInfo.setPayerAccount(model.getAccount());
				}
			}	
			
			//提交状态
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
	//by tim_gao 这个方法做的有问题,按理说,付款申请单与付款单应该去同时写一个fdcpaymentbill 同时显示也调用一个,
	//而不是回来再由付款单去回写这样真的很麻烦,引出一堆关于累计的问题updateFDCPaymentBillinvoice()
	
	private void updateFDCPaymentBillinvoice(Context ctx, PayRequestBillInfo payReqBill, PaymentBillInfo paymentBill) throws EASBizException, BOSException{
		//审批生成付款单中间表时，带出付款申请单的发票的字段
		FDCPaymentBillInfo fdcPayment = new FDCPaymentBillInfo();
		fdcPayment.setInvoiceNumber(payReqBill.getInvoiceNumber());
		fdcPayment.setInvoiceAmt(payReqBill.getInvoiceAmt());
		fdcPayment.setAllInvoiceAmt(payReqBill.getAllInvoiceAmt());
		fdcPayment.setInvoiceOriAmt(payReqBill.getInvoiceOriAmt());
		fdcPayment.setAllInvoiceOriAmt(payReqBill.getAllInvoiceOriAmt());
		fdcPayment.setInvoiceDate(payReqBill.getInvoiceDate());
		// 需要关联上付款单byhpw
		fdcPayment.setPaymentBill(paymentBill);
		FDCPaymentBillHelper.handleFDCPaymentBillInvoice(ctx, fdcPayment, paymentBill);
	}
	
	/**
	 * 付款申请单累计额大于合同金额最新造价时提醒（提交，审批）
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

/*		String settlementID="Ga7RLQETEADgAAC/wKgOlOwp3Sw=";//结算款
		String progressID="Ga7RLQETEADgAAC6wKgOlOwp3Sw=";//进度款
		String keepID="Ga7RLQETEADgAADDwKgOlOwp3Sw=";//保修款
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
////				MsgBox.showError(prmtPayment,"付完结算款后才能付保修款,即存在付款类型的类型为“结算款”的付款申请单时才能选择“保修款”类型付款类型");
//			}
//			//保修款总金额不能大于结算单上的质保金
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
//			 * 由于系统原来只支持合同的单笔结算，所以取累计保修款金额的时候按照如下处理无误，但是支持合同的多笔结算之后仍然这样处理
//			 * 就会导致数据重复累加，我们要做的只是取该合同的累计保修款而不是每笔结算单的累计保修款之和   by Cassiel_peng
//			 */
////			for(int i=0;i<c.size();i++){
////				 amount= amount.add(FDCHelper.toBigDecimal(c.get(i).getQualityGuarante()));
////			}
//			//付款申请单的累计保修金额
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
//					continue;//略过当前单据
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
//				//之前的提示信息错误 by zhiyuan_tang 2010/12/21
//				throw new PayRequestBillException(PayRequestBillException.CANTSELECTPROGRESSAMT);
////				MsgBox.showError(prmtPayment,"付完结算款后不能付进度款,即存在付款类型的类型为“结算款”的付款申请单时不能选择“进度款”类型付款类型");
//			}
//		}
//		if(type.getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)){
//			FilterInfo myfilter=new FilterInfo();
//			myfilter.appendFilterItem("id", payReq.getContractId());
//			myfilter.appendFilterItem("hasSettled", Boolean.TRUE);
//			if(!ContractBillFactory.getLocalInstance(ctx).exists(myfilter)){
//				throw new PayRequestBillException(PayRequestBillException.MUSTSETTLE);
////				MsgBox.showError(prmtPayment,"合同必须结算之后才能做结算款类别的付款申请单");
//			}
//		}
		if(payReq.getContractId() == null || payReq.getPaymentType() == null || payReq.getPaymentType().getPayType() == null)
	        return;
	    PaymentTypeInfo type = payReq.getPaymentType();
	    FilterInfo filter = new FilterInfo();
	    filter.appendFilterItem("paymentType.payType.id", "Ga7RLQETEADgAAC/wKgOlOwp3Sw=");
	    filter.appendFilterItem("sourceBillId", null); // add by wp  -- 不校验从分包合同过来的付款申请
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
	        filter.appendFilterItem("sourceBillId", null); // add by wp  -- 不校验从分包合同过来的付款申请
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
	
	//单据序时簿编辑界面初始数据粗粒度方法
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		
		paramMap.put("isCost",Boolean.FALSE);
		Map initMap = super._fetchInitData(ctx,paramMap);		
		CompanyOrgUnitInfo currentFIUnit = ContextUtil.getCurrentFIUnit(ctx);

		String comId = null;
//		String reqPayId = null;
//		if(paramMap.get("reqPayId")!=null){
//			reqPayId = (String) paramMap.get("reqPayId");
//		}
	
		//合同单据
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
				
				//付款比例
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
				
				//供应商
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
				 * 需要检查是否有与该甲供材合同相关的已审批状态的现场确认单，
				 * 如果有，则
				 * 			将现场确认单上的金额写到本次申请原币字段（有金额则不能修改，没金额则可以新增金额），
				 * 			生成付款申请单的现场确认单分录
				 
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

			
			//工程项目
			String projectId = (String) project.getId().toString();
			CurProjectInfo curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId));
			
			initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
			
			if(projectId!=null){
				initPeriod( ctx, projectId,curProjectInfo, comId, initMap ,false);
			}		
			
			//工程项目对应的组织
			String orgUnitId = null;
			if(curProjectInfo!=null &&  curProjectInfo.getCostCenter()!=null){
				orgUnitId = curProjectInfo.getCostCenter().getId().toString();
			}			
			//获得当前组织		
			FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx)
				.getFullOrgUnitInfo(new ObjectUuidPK(orgUnitId));			
			initMap.put(FDCConstants.FDC_INIT_ORGUNIT,orgUnitInfo);
			
			//重新获取工程项目对应的财务组织   R101202-122 by zhiyuan_tang
			if (comId != null) {
				CompanyOrgUnitInfo company =GlUtils.getCurrentCompany(ctx, comId,null,false);	
				initMap.put(FDCConstants.FDC_INIT_COMPANY,company);
			}
			
			
			//设置付款次数为合同的付款次数 从付款单中过滤
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
			//TODO 再加一个时间过滤
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
			//TODO 再加一个时间过滤
			CompensationOfPayReqBillCollection compensationOfPayReqBillCollection = CompensationOfPayReqBillFactory.getLocalInstance(ctx).getCompensationOfPayReqBillCollection(view);
			initMap.put("CompensationOfPayReqBillCollection",compensationOfPayReqBillCollection);
			
			//DeductTypeCollection
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			//过滤甲供材
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
				// TODO 自动生成 catch 块
				e.printStackTrace();
				SysUtil.abort();
			}
			
			
			
			
		}
		
		return initMap;
	}
	
    private SelectorItemCollection getSic(){
		// 此过滤为详细信息定义
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
    
	//提交时校验单据期间不能在工程项目的当前期间之前
    private void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		
		//不能落于当前成本期间之前
    	PayRequestBillInfo payReqBill = (PayRequestBillInfo)model;
		
		//是否启用财务一体化
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
		//R110615-0438：付款申请单提交时报错
		//checkConWorkLoad(ctx, payReqBill.getId(), model);
/*		没有判断的必要
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractBill.getCurProject().getId().toString(),false);
			if((bookedPeriod!=null && contractBill.getPeriod() !=null) && contractBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				//单据期间不能在工程项目的当前期间之前 CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
*/
		/***
		 * 材料确认单，提交判断
		 */
		/***
		 * 检查关联的材料确认单，是否会超过确认金额
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
						throw new EASBizException(new NumericExceptionSubItem("000","关联的材料确认单:"+entryInfo.getConfirmBill().getNumber()+"  【确认单实付金额 + 确认单本次申请金额   大于  材料确认单确认金额】，不能执行此操作！"));
					}
				}
			}
		}
		
	}
	
	//审核校验
	private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
    
		PayRequestBillInfo model = (PayRequestBillInfo)billInfo;

        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null||model.getPaymentType()==null){
        	model= this.getPayRequestBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
       //checkConWorkLoad(ctx, billId,model);
        //检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
/*			没有判断的必要
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();	
//			//成本已经月结
//			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
//			if(model.getPeriod().getBeginDate().after(bookedPeriod.getEndDate())){
//				throw new  ContractException(ContractException.AUD_AFTERPERIOD,new Object[]{model.getNumber()});
//			}
			
			//财务还没有月结，不能审核单据{0}
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,false);
			if(finPeriod!=null && model.getPeriod()!=null && model.getPeriod().getBeginDate().after(finPeriod.getEndDate())){
				throw new  ContractException(ContractException.AUD_FINNOTCLOSE,new Object[]{model.getNumber()});
			}	
		}
		*/
		
		//budget contrl
		FDCBudgetAcctFacadeFactory.getLocalInstance(ctx).invokeBudgetCtrl(model, FDCBudgetConstants.STATE_AUDIT);
		
		
		/***
		 * 材料确认单，提交判断
		 */
		/***
		 * 检查关联的材料确认单，是否会超过确认金额
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
						throw new EASBizException(new NumericExceptionSubItem("000","关联的材料确认单:"+entryInfo.getConfirmBill().getNumber()+"  【确认单实付金额 + 确认单本次申请金额   大于  材料确认单确认金额】，不能执行此操作！"));
					}
				}
			}
		}
		
	}
	//审核校验
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
		PayRequestBillInfo model = (PayRequestBillInfo)billInfo;

        if(model==null  || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null ){
        	model= this.getPayRequestBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
		//检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
/*			没有判断的必要
 *  by sxhong 2009-07-30 22:20:49
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();

			//单据期间在工程项目当前期间之前，不能反审核
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
	
	//关闭
	protected void _close(Context ctx, IObjectPK pk) throws BOSException, EASBizException {

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasClosed");
		PayRequestBillInfo bill = (PayRequestBillInfo) this.getValue(ctx,pk,selector);
		
		bill.setHasClosed(true);

		
		_updatePartial(ctx,bill, selector);
		
	}

	protected void _unClose(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		/***
		 * 检查关联的材料确认单，是否会超过确认金额
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
					throw new EASBizException(new NumericExceptionSubItem("000","关联的材料确认单:"+entryInfo.getConfirmBill().getNumber()+"  【确认单实付金额 + 确认单本次已申请未付金额   大于  材料确认单确认金额】，不能执行此操作！"));
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
		// TODO 自动生成方法存根
		
	}
	
	//是否超过本月付款计划
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
			
			//如果付款金额不等于0
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
		//如果编码重复重新取编码
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
		handleIntermitNumberForReset(ctx, info);
	}
	
	//合同、无文本、付款申请，已经覆盖handleIntermitNumber
	protected void handleIntermitNumberForReset(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException {

		FilterInfo filter = null;
		int i = 0;
		do {
			//如果编码重复重新取编码
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
		// TODO 自动生成方法存根
		super._delete(ctx,arrayPK);
	}
	
	protected void _reverseSave(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
//		super._reverseSave(ctx, pk, model);
		//不需要反写
	}
	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum billOperStateEnum,
			IObjectValue relationInfo) throws BOSException, EASBizException {
//		super._reverseSave(ctx, srcBillPK, srcBillVO, billOperStateEnum, relationInfo);
		//不需要反写
	}
	
	
	/**
	 * 判断付款申请单是否属于预算外业务。若满足以下四个条件的任何一个，则返回是，属于预算外业务；否则，则返回否，不属于预算外业务。
	 * 一、	当本月度付款计划申报表中，已签订合同本月没有报计划，既计划数为0时 ，视同预算外业务。
	 * 二、	当本月度付款计划申报表中，合同未签订，当月签订，但又不是表中的未签订合同，需要请款时，视同预算外业务
	 * 三、	当本月度付款计划申报表中，已签订合同申报了当月请款计划数，当实际请款金额超过计划数时，视同预算外业务。
	 * 四、	当本月度付款计划申报表中，未签订合同申报了当月请款计划数，当实际请款时关联了改未签订合同，金额超过计划数，视同预算外业务。
	 * 
	 * 只校验本期申请与本期计划 by hpw 2009-08-24
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
		// 本期申请金额累计
		BigDecimal allAmount = FDCHelper.ZERO;
		// 本期预算科目付款累计
		BigDecimal allPlanAmt = null;
		Date payDate = model.getPayDate();//时小鸿 取付款日期
    	
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("amount");
    	view.getSelector().add("state");
    	view.getSelector().add("payDate");
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractId", model.getContractId()));
//    	filter.getFilterItems().add(new FilterItemInfo("createTime", model.getCreateTime(), CompareType.LESS_EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE, CompareType.NOTEQUALS));
    	// 不包括本次,本次在上面单独加取当前单据最新数据,以避免新增或修改时数据错误
    	if(model.getId() != null){
    		filter.getFilterItems().add(new FilterItemInfo("id", model.getId().toString(), CompareType.NOTEQUALS));
    	}
    	view.setFilter(filter);
    	PayRequestBillCollection payReqColl = null;
		try {
			payReqColl = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
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
	 * 获取“本期已付金额”
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
		// 查看状态查以前的
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
	 * 为预算准备付款申请单，主要是查出预算所用到的一些字段，供_getFloatFund()、_getLocalBudget()、_getActPaied()使用
	 * @author：owen_wen
	 * @CreateTime：2012-7-13
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
	 * 获取“本期在途金额”
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
		// 查看状态查以前的
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
	 * 获取“本期预算金额”
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
	 * 获取“本期可用预算”
	 * @author owen_wen 
	 */
	protected BigDecimal _getBgBalance(Context ctx, IObjectValue payRequestBill) throws BOSException, EASBizException {
		return _getLocalBudget(ctx, payRequestBill).subtract(_getFloatFund(ctx, payRequestBill))
				.subtract(_getActPaied(ctx, payRequestBill));
	}
	
	/**
	 * 返还预算 - R140729-0038
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
