package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBailFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractCostSplit;
import com.kingdee.eas.fdc.contract.IContractSettlementBill;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.util.ContractCodingUtil;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacadeFactory;
import com.kingdee.eas.fdc.finance.IContractPayPlan;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fdc.invite.app.ContractBillAcceptanceLetterHelper;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.UpdateHistoryTypeEnum;
import com.kingdee.eas.ma.budget.BgBudgetFacadeFactory;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.IBgBudgetFacade;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 
 * 描述:合同单据
 * @author liupd  date:2006-10-13 <p>
 * @version EAS5.1.3
 */
public class ContractBillControllerBean extends
		AbstractContractBillControllerBean {
	
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillControllerBean");

	/**
	 * 合同编码规则的标识属性
	 */
	private static final String BINDING_PROPERTY = "codeType.number";
	
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk,ContractBillInfo.getDefaultSelector());
    }
    protected void trimName(FDCBillInfo billInfo) {
		if(billInfo.getName() != null) {
			billInfo.setName(billInfo.getName().trim());
		}
	}
    public IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	if(((ContractBillInfo)model).getBail()!=null){
    		ContractBailFactory.getLocalInstance(ctx).save(((ContractBillInfo)model).getBail());
    	}
    	
    	ContractBillInfo info = (ContractBillInfo)model;
    	
    	if(info.getProgrammingContract()!=null){
    		updateProgrammingContract(ctx, info.getProgrammingContract().getId().toString(), 1);
    	}
    	
    	// 因为利用BOTP关联生成合同时，在调用_save前就有了id，调用_exists判断库中是否已经有了数据。Added by Owen_wen 2012-06-14
		if (info.getId() != null && this._exists(ctx, new ObjectUuidPK(info.getId()))) {
    		ContractBillInfo oldInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(info.getId()));
			if (!oldInfo.getState().equals(FDCBillStateEnum.SAVED)) {
				if (!FDCUtils.isRunningWorkflow(ctx, info.getId().toString())) {
					if (!FDCBillStateEnum.SUBMITTED.equals(oldInfo.getState()) && !FDCBillStateEnum.BACK.equals(oldInfo.getState())
							&& !FDCBillStateEnum.INVALID.equals(oldInfo.getState())) {
						throw new ContractException(ContractException.WITHMSG, new Object[] { "\n当前单据最新状态为" + oldInfo.getState()
								+ ",可能是被 其他用户所更改，不能保存！" });
					}
				}
			}
    		info.setState(oldInfo.getState());
    	}
    	info.setCodingNumber(info.getNumber());
    	
    	/**
		 * 如果设置了不允许断号，但是在取号以后，由于名称重复而报错<br>
		 * 断号不会回滚，导致再次提交时，就断号了。<br>
		 * 所以在保存前先校验Name <br>
		 * add by emanon
		 */
		checkNameDup(ctx, info);
		
		IObjectPK pk = super._save(ctx, info);

		// 处理编码类型字段，防止回收编码时出现异常
		dealWithCodeType(ctx, info, true);
    	
		return pk;
    }
    
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ContractBillInfo contractBillInfo = ((ContractBillInfo) model);
		
		if(contractBillInfo.getProgrammingContract()!=null){
    		updateProgrammingContract(ctx, contractBillInfo.getProgrammingContract().getId().toString(), 1);
    	}
		//checkBillForSubmit( ctx, FDCBillInfo);
		if(contractBillInfo.getId()!=null){
			boolean canSubMit = _checkCanSubmit(ctx, contractBillInfo.getId().toString());
			if(!canSubMit){
				String msg = "单据状态已经在审核中或者已审核，不能再提交";
				throw new ContractException(ContractException.WITHMSG,new Object[]{msg});
			}
		}
		if(contractBillInfo.getBail()!=null){
			ContractBailFactory.getLocalInstance(ctx).submit(contractBillInfo.getBail());
		}
		contractBillInfo.setCodingNumber(contractBillInfo.getNumber());

		/**
		 * 如果设置了不允许断号，但是在取号以后，由于名称重复而报错<br>
		 * 断号不会回滚，导致再次提交时，就断号了。<br>
		 * 所以在保存前先校验Name <br>
		 * add by emanon
		 */ 
		//checkNameDup(ctx, contractBillInfo);
		
		
		// modified by zhaoqin for 项目资金计划 on 2013/08/14 start
		// return super._submit(ctx, contractBillInfo);
		IObjectPK pk = super._submit(ctx, contractBillInfo);

		// 处理编码类型字段，防止回收编码时出现异常
		dealWithCodeType(ctx, contractBillInfo, true);

		String costCenterId = contractBillInfo.getOrgUnit().getId().toString();//合同肯定是在成本中心下做的
		// 合同完成审批前需要完全拆分
		boolean splitBeforeAudit = FDCUtils.getBooleanValue4FDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_SPLITBFAUDIT);
		// 是否自动拆分
		//boolean isAutoSplit = FDCUtils.getBooleanValue4FDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_ISAUTOSPLIT);
		/**
		 * 是否自动拆分:
		 *  0: 合同自动拆分
		 *  1: 全部自动拆分
		 *  2: 不自动拆分
		 */
		String autoSplit = FDCUtils.getFDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_ISAUTOSPLIT);
		if (splitBeforeAudit && null != autoSplit && ("0".equals(autoSplit) || "1".equals(autoSplit)))
			autoSplit(ctx, contractBillInfo);
		
		return pk;
		// modified by zhaoqin for 项目资金计划 on 2013/08/14 end
	}

	/**
	 * 项目资金计划：自动拆分 - 合同拆分自动根据关联的合约规划的科目及比例自动拆分
	 * 	
	 * @param contractBillInfo
	 * @Author：zhaoqin
	 * @CreateTime：2013-8-14
	 */
	private void autoSplit(Context ctx, ContractBillInfo contractBillInfo) throws BOSException, EASBizException {
		String contractId = contractBillInfo.getId().toString();
		// '合同拆分'根据关联的合约规划的科目及比例自动生成
		if (contractBillInfo.isIsCoseSplit()) { // 成本拆分
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			IContractCostSplit iconCostSp = ContractCostSplitFactory.getLocalInstance(ctx);
			if (!iconCostSp.exists(filter)) { // 是否已经拆分
				ContractCostSplitInfo objectValue = new ContractCostSplitInfo();
				objectValue.setContractBill(contractBillInfo);
				objectValue.setCreator(ContextUtil.getCurrentUserInfo(ctx));
				objectValue.setCurProject(contractBillInfo.getCurProject());
				objectValue.setIsConfirm(true);
				objectValue.setIsInvalid(false);
				objectValue.setState(FDCBillStateEnum.SAVED);
				objectValue.setOrgUnit(contractBillInfo.getOrgUnit());
				objectValue.setCU(contractBillInfo.getCU());
				
				// 是否关联了框架合约
				String progConId = iconCostSp.checkIsExistProg(contractId); // return T_CON_ProgrammingContract.FID
				// or check 'contractBillInfo.getProgrammingContract()' is not null
				if (!StringUtils.isEmpty(progConId)) {
					importProgramForContract(ctx, contractBillInfo, progConId, objectValue);
				}
				iconCostSp.addnew(objectValue);
				// 更新合同的拆分状态
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("splitState");
				_updatePartial(ctx, contractBillInfo, selector);
			}
		}
	}

	/**
	 * 描述：根据关联的合约规划的科目及比例自动拆分
	 * 参考: ContractCostSplitEditUI.actionProgrAcctSelect_actionPerformed
	 * @param ctx
	 * @param contractBillInfo
	 * @param ProConId 框架合约ID
	 * @Author：zhaoqin
	 * @CreateTime：2013-8-15
	 */
	private void importProgramForContract(Context ctx, ContractBillInfo contractBillInfo, String progConId,
			ContractCostSplitInfo objectValue)
			throws BOSException, EASBizException {
		BigDecimal amount = contractBillInfo.getAmount(); // 合同造价
		// 合同造价是否为0
		if (amount.compareTo(FDCHelper.ZERO) == 0) {
			/* modified by zhaoqin for BT869127 on 2015/02/02 */
			objectValue.setSplitState(CostSplitStateEnum.ALLSPLIT);
			contractBillInfo.setSplitState(CostSplitStateEnum.ALLSPLIT);
			return;
		}
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("costEntries.*");
		sic.add("costEntries.costAccount.*");
		sic.add("costEntries.costAccount.curProject.*");
		ProgrammingContractInfo programmingContractInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(
				new ObjectUuidPK(BOSUuid.read(progConId)), sic);
		ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
		if (costEntries.size() == 0) {
			/* modified by zhaoqin for BT869127 on 2015/02/02 */
			objectValue.setSplitState(CostSplitStateEnum.PARTSPLIT);
			contractBillInfo.setSplitState(CostSplitStateEnum.PARTSPLIT);
			return;
		}
		ProgrammingContracCostInfo progConCost = null;
		ContractCostSplitEntryCollection entryColl = objectValue.getEntrys();
		
		//总的已分配金额(合约规划的规划金额)
		BigDecimal allAssigned = FDCHelper.ZERO;
		List cachList = new ArrayList();
		for (Iterator iter = costEntries.iterator(); iter.hasNext();) {
			progConCost = (ProgrammingContracCostInfo) iter.next();
			if (cachList.contains(progConCost.getCostAccount())) {
				continue;
			} else {
				cachList.add(progConCost.getCostAccount());
			}
			allAssigned = allAssigned.add((progConCost.getContractAssign() == null ? FDCHelper.ZERO : progConCost.getContractAssign()));
		}
		if (allAssigned.compareTo(FDCHelper.ZERO) == 0) {
			/* modified by zhaoqin for BT869127 on 2015/02/02 */
			objectValue.setSplitState(CostSplitStateEnum.PARTSPLIT);
			contractBillInfo.setSplitState(CostSplitStateEnum.PARTSPLIT);
			return;
		}
		
		int index = 0; // 分录序号
		BigDecimal tempAll = FDCHelper.ZERO;
		BigDecimal splitAmount = FDCHelper.ZERO; // 已拆分金额
		cachList = new ArrayList();
		for (Iterator iter = costEntries.iterator(); iter.hasNext();) {
			progConCost = (ProgrammingContracCostInfo) iter.next();
			if (cachList.contains(progConCost.getCostAccount())) {
				continue;
			} else {
				cachList.add(progConCost.getCostAccount());
			}
			ContractCostSplitEntryInfo entry = new ContractCostSplitEntryInfo();
			entry.setId(BOSUuid.create(entry.getBOSType()));
			entry.setLevel(0);
			entry.setIsLeaf(true);
			entry.setCostAccount(progConCost.getCostAccount());
			entry.getCostAccount().setCurProject(progConCost.getCostAccount().getCurProject());
			entry.setIndex(index);
			entry.setSeq(index + 1);
			entryColl.add(entry);
			index++;
			// 本合约分配金额是否为0
			if (progConCost.getContractAssign().compareTo(FDCHelper.ZERO) == 0) {
				entry.setSplitScale(FDCHelper.ZERO);
				entry.setAmount(FDCHelper.ZERO);
				continue;
			}
			if (index == costEntries.size()) { // 最后一项
				entry.setSplitScale(FDCHelper.subtract(FDCHelper.ONE_HUNDRED, tempAll));
				entry.setAmount(FDCHelper.divide(amount.multiply(entry.getSplitScale()), FDCHelper.ONE_HUNDRED, 10, BigDecimal.ROUND_HALF_DOWN));
			} else { 
				entry.setSplitScale(FDCHelper.divide(progConCost.getContractAssign(), allAssigned, 10, BigDecimal.ROUND_HALF_DOWN));
				entry.setAmount(amount.multiply(entry.getSplitScale())); // 合同拆分.归属金额
				entry.setSplitScale(entry.getSplitScale().multiply(FDCHelper.ONE_HUNDRED));
				tempAll = FDCHelper.add(tempAll, entry.getSplitScale());
			}
			splitAmount = FDCHelper.add(entry.getAmount(), splitAmount);
		}
		objectValue.setAmount(splitAmount);
		objectValue.setOriginalAmount(contractBillInfo.getOriginalAmount());
		if (amount.compareTo(splitAmount) == 0) {
			objectValue.setSplitState(CostSplitStateEnum.ALLSPLIT);
			contractBillInfo.setSplitState(CostSplitStateEnum.ALLSPLIT);
		} else {
			objectValue.setSplitState(CostSplitStateEnum.PARTSPLIT);
			contractBillInfo.setSplitState(CostSplitStateEnum.PARTSPLIT);
		}
	}

	/**
	 * 设置制单人制单时的职位信息 处理提单R101021-327
	 * 
	 * 制单人对应的是用户ID 用户(t_pm_user)可以找到对应的职员（t_bd_person）
	 * 职员可以找到对应的职员职业信息（T_HR_PersonPosition），这张表就是用户的主要职位
	 * 职业信息（T_HR_PersonPosition） 主要职位可以找到 t_org_position(职位)
	 * 
	 * @author owen_wen 201-07-20
	 * @param contractBillInfo
	 */
	private void setCreatorPosition(Context ctx, ContractBillInfo contractBillInfo) throws BOSException {
		String creatorId = ctx.getCaller().toString();
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select p.fid from t_org_position p \n");
		builder.appendSql("inner join T_HR_PersonPosition pp on pp.FPrimaryPositionID = p.fid \n");
		builder.appendSql("inner join T_bd_person ps on ps.fid = pp.FPersonID \n");
		builder.appendSql("inner join t_pm_user u on u.FPersonId = ps.fid \n");
		builder.appendSql("where u.fid = ");
		builder.appendParam(creatorId);
		
		String positionId = "";
		try {
			IRowSet rs = builder.executeQuery(ctx);
			if (rs.size() > 0) {
				rs.next();
				positionId = rs.getString("fid");
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			throw new BOSException(e);
		}
		
		// 如果制单人没有绑定职位信息，直接返回 Added By Owen_wen 2011-07-28
		if (StringUtils.isEmpty(positionId))
			return;
		
		PositionInfo positionInfo = new PositionInfo();
		positionInfo.setId(BOSUuid.read(positionId));
		contractBillInfo.setCreatorPosition(positionInfo);
	}

    
    private SelectorItemCollection getSic(){
		// 此过滤为详细信息定义
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("splitState"));        
        sic.add(new SelectorItemInfo("isCoseSplit"));
        sic.add(new SelectorItemInfo("isAmtWithoutCost"));
        sic.add(new SelectorItemInfo("period.id"));
        sic.add(new SelectorItemInfo("period.beginDate"));	
        sic.add(new SelectorItemInfo("orgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));	
        sic.add(new SelectorItemInfo("conChargeType.id"));
        sic.add(new SelectorItemInfo("contractPropert"));
     // Dean_zhu 2011-1-5
		sic.add(new SelectorItemInfo("hasSettled"));
		sic.add(new SelectorItemInfo("programmingContract.*"));
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("splitEntry.id"));
    	sic.add(new SelectorItemInfo("splitEntry.amount"));
    	sic.add(new SelectorItemInfo("splitEntry.product.id"));
//		sic.add(new SelectorItemInfo("splitEntry.product.name"));
//    	sic.add(new SelectorItemInfo("splitEntry.product.number"));
    	sic.add(new SelectorItemInfo("splitEntry.costAccount.curProject.*"));
    	sic.add(new SelectorItemInfo("splitEntry.costAccount.*"));
    	sic.add(new SelectorItemInfo("splitEntry.level"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionType.name"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionValue"));
    	sic.add(new SelectorItemInfo("splitEntry.directAmount"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionValueTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.directAmountTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.otherRatioTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.splitType"));
    	sic.add(new SelectorItemInfo("splitEntry.workLoad"));
    	sic.add(new SelectorItemInfo("splitEntry.price"));
    	sic.add(new SelectorItemInfo("splitEntry.splitScale"));
    	sic.add(new SelectorItemInfo("splitEntry.isLeaf"));
    	sic.add(new SelectorItemInfo("splitEntry.programmings.id"));
//		sic.add(new SelectorItemInfo("splitEntry.programmings.name"));
//    	sic.add(new SelectorItemInfo("splitEntry.programmings.number"));
        return sic;
    }
    
	private SelectorItemCollection getSettleSic() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("contractBill.*"));

		return sic;
	}

	private SelectorItemCollection getSeletor() {
		// 此过滤为详细信息定义
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("suppEntry.*"));

		return sic;
	}
    
	//提交时校验单据期间不能在工程项目的当前期间之前
	protected void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		
		//不能落于当前成本期间之前
		ContractBillInfo contractBill = (ContractBillInfo)model;
		//        if(model==null || contractBill.getCurProject()==null ||contractBill.getCurProject().getFullOrgUnit()==null){
		//        	model= this.getContractBillInfo(ctx,new ObjectUuidPK(contractBill.toString()),getSic());
		//        }
		//是否启用财务一体化
		String comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		//因为月结只检查了成本类的，所以此处不控制财务类的
		if(isInCore&&contractBill.isIsCoseSplit()){
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractBill.getCurProject().getId().toString(),true);
			if(contractBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				//单据期间不能在工程项目的当前期间之前 CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
	}
	
	//
	protected void checkBillForAudit(Context ctx, BOSUuid billId)throws BOSException, EASBizException {

        ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
		//检查功能是否已经结束初始化
		String comId = contractBillInfo.getCurProject().getFullOrgUnit().getId().toString();
			
		String costCenterId = contractBillInfo.getOrgUnit().getId().toString();//合同肯定是在成本中心下做的
		boolean splitBeforeAudit = FDCUtils.getBooleanValue4FDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_SPLITBFAUDIT);
		//该合同已经还没有拆分，不能审核
		if(splitBeforeAudit && contractBillInfo.isIsCoseSplit() &&  
				!contractBillInfo.isIsAmtWithoutCost() && !CostSplitStateEnum.ALLSPLIT.equals(contractBillInfo.getSplitState())){
//			MsgBox.showWarning(this, "该合同已经进行了拆分，不能进行修改");
//			SysUtil.abort();
			throw new ContractException(ContractException.NOSPLITFORAUDIT);
		}
		
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = contractBillInfo.getCurProject().getId().toString();
			//没有结束初始化
			if(!ProjectPeriodStatusUtil._isClosed(ctx,curProject)){
				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.ISNOT_INIT,new Object[]{contractBillInfo.getCurProject().getDisplayName()});
			}			
			//不再当前期间，不能审核
		}
		//单据审批时预算控制
		//根据参数是否显示合同费用项目
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx,null,FDCConstants.FDC_PARAM_CHARGETYPE);
        if (isShowCharge && contractBillInfo.getConChargeType() != null) {
           invokeAuditBudgetCtrl(ctx,contractBillInfo);
        }
	}
	
	protected void checkBillForUnAudit(Context ctx, BOSUuid billId)throws BOSException, EASBizException {
    	// 此过滤为详细信息定义
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));
        sic.add(new SelectorItemInfo("period.beginDate"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
        sic.add(new SelectorItemInfo("conChargeType.id"));
        
        ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,new ObjectUuidPK(billId.toString()),sic);

        //检查是否已经拆分
		String costCenterId = contractBillInfo.getOrgUnit().getId().toString(); //合同肯定是在成本中心下做的
		boolean splitBeforeAudit = FDCUtils.getBooleanValue4FDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_SPLITBFAUDIT);
    	if(!splitBeforeAudit){
    		FilterInfo filterSett = new FilterInfo();
    		filterSett.getFilterItems().add(
    				new FilterItemInfo("contractBill.id", billId.toString()));
    		filterSett.getFilterItems().add(
    				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
    						CompareType.NOTEQUALS));
    		boolean hasSettleSplit = false;
    		if (ContractCostSplitFactory.getLocalInstance(ctx).exists(filterSett)
    				|| ConNoCostSplitFactory.getLocalInstance(ctx).exists(filterSett)) {
    			hasSettleSplit = true;
    		}
    		if (hasSettleSplit) {
    			throw new  ContractException(ContractException.HASSPLIT);
    		}
    	}
    	
    	//R110603-0148:如果存在变更签证申请，则不允许反审批
    	ContractUtil.checkHasChangeAuditBill(ctx, contractBillInfo.getId());
    	
    	//R110603-0148如果存在变更签证确认，则不允许反审批
    	ContractUtil.checkHasContractChangeBill(ctx, contractBillInfo.getId());
    	
    	//R120313-0742如果存在工程量确认单，则不允许反审批
		ContractUtil.checkhasWorkLoadConfirmBill(ctx, contractBillInfo.getId());
    	
        //检查功能是否已经结束初始化
		String comId = contractBillInfo.getCurProject().getFullOrgUnit().getId().toString();
			
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = contractBillInfo.getCurProject().getId().toString();
			
//			if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
//				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{contractBillInfo.getCurProject().getDisplayName()});
//			}		
			
			//单据期间在工程项目当前期间之前，不能反审核
			PeriodInfo costPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
			if(contractBillInfo.getPeriod().getBeginDate().before(costPeriod.getBeginDate())){
				throw new  ContractException(ContractException.CNTPERIODBEFORE);
			}			
		}
		//2009-3-4 如果合同对应的核算项目基础资料已经被凭证引用，则不允许反审批合同
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId", billId.toString()));
		boolean exist = ContractBaseDataFactory.getLocalInstance(ctx).exists(filter);
		if(exist){
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("state");
			view.getSelector().add("id");
			view.setFilter(filter);
			ContractBaseDataCollection contractBaseDataCollection = 
				ContractBaseDataFactory.getLocalInstance(ctx).getContractBaseDataCollection(view);
			if(contractBaseDataCollection.size() == 1) {
				ContractBaseDataInfo info = contractBaseDataCollection.get(0);
				String baseId = info.getId().toString();
				String sql = " select ar.fid from T_GL_VoucherAssistRecord ar  \r\n" +
						" inner join T_BD_AssistantHG hg  on ar.FAssGrpID = hg.fid   \r\n" +
						" where hg.FContractBaseDataID = ?     \r\n";
				IRowSet rs = DbUtil.executeQuery(ctx,sql,new Object[]{baseId});
				try {
					if(rs != null && rs.next()){
						throw new ContractException(ContractException.HASVOUCHER);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new BOSException(e);
				}
			}
		}
	
		//单据反审批时预算控制
		//根据参数是否显示合同费用项目
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx,null,FDCConstants.FDC_PARAM_CHARGETYPE);
        if (isShowCharge && contractBillInfo.getConChargeType() != null) {
           invokeUnAuditBudgetCtrl(ctx,contractBillInfo);
        }
	}
	
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
	
		
		removeDetailEntries(ctx, pk);
		
		if(model.get("number")==null)
			this.handleIntermitNumber(ctx,(FDCBillInfo)model);
		super._update(ctx, pk, model);
		
		//同步到合同基本资料，用于合同做核算项目
		ContractBaseDataHelper.synToContractBaseData(ctx, false, pk.toString());
		
		/**
		 * 导入合同付款计划  modify by yxl 20151027  根据选择的合约框架自动带出了付款计划，不需要再合同保存时进行导入
		 */
//		ContractBillInfo info = (ContractBillInfo) model;
//		if(ContractPropertyEnum.DIRECT.equals(info.getContractPropert())){
//			ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(pk.toString(),false);
//		}else if(ContractPropertyEnum.SUPPLY.equals(info.getContractPropert())){
//			if(info.isIsAmtWithoutCost()){
//				ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(pk.toString(),false);
//			}else{
//				ContractBillEntryCollection entrys = info.getEntrys();
//				for(int i=0;i<entrys.size();i++){
//					ContractBillEntryInfo contractBillEntryInfo = entrys.get(i);
//					if("nu".equals(contractBillEntryInfo.getRowKey())){
//						ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(contractBillEntryInfo.getContent(),false);
//						break;
//					}
//				}
//			}
//		}
	}

	/*
	 * 填充编码类型字段,用于编码规则
	 */
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {
		super.setPropsForBill(ctx, fDCBillInfo);
		
		// 处理编码类型字段，防止回收编码时出现异常
		dealWithCodeType(ctx, fDCBillInfo, false);
	}

	/**
	 * 描述：处理编码类型字段，防止回收编码时出现异常
	 * <p>
	 * 如果在调用编码规则定义（ContractBillControllerBean.handleIntermitNumber1）的时候，<br>
	 * 没有获取到编码，则编码类型字段可能为空，这个时候，就需要设置一个默认值
	 * 
	 * @param ctx
	 * @param fdcBillInfo
	 * @param isUpdate
	 * @throws EASBizException
	 * @throws BOSException
	 * @author：skyiter_wang
	 * @createDate：2013-10-14
	 */
	protected void dealWithCodeType(Context ctx, FDCBillInfo fdcBillInfo, boolean isUpdate) throws EASBizException,
			BOSException {
		ContractBillInfo contractBillInfo = (ContractBillInfo) fdcBillInfo;

		if (null == contractBillInfo.getCodeType()) {
			ContractTypeInfo contractType = contractBillInfo.getContractType();
			ContractPropertyEnum contractPropertyEnum = contractBillInfo.getContractPropert();
			ContractThirdTypeEnum contractThirdTypeEnum = ContractThirdTypeEnum.NEW;
			// 获取合同编码类型对象集合（精确，模糊，递归）
			ContractCodingTypeCollection codingTypeList = (ContractCodingTypeCollection) _getContractCodingType(ctx,
					contractType, contractPropertyEnum.getValue(), contractThirdTypeEnum.getValue());

			if (FdcObjectCollectionUtil.isNotEmpty(codingTypeList)) {
				// 第一个合同编码类型（最接近，最匹配）
				ContractCodingTypeInfo firstCodingTypeInfo = codingTypeList.get(0);
				// 设置合同编码类型
				contractBillInfo.setCodeType(firstCodingTypeInfo);
			}

			if (null != contractBillInfo.getCodeType() && isUpdate && null != contractBillInfo.getId()
					&& this._exists(ctx, new ObjectUuidPK(contractBillInfo.getId()))) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("codeType");

				super._updatePartial(ctx, contractBillInfo, selector);
			}
		}

		// 打印调试信息
		logger.info("合同，contractBillInfo：" + contractBillInfo);
		logger.info("合同编码关联属性，contractBillInfo.codeType：" + contractBillInfo.getCodeType());
	}

	private void removeDetailEntries(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", pk.toString()));
		
		ContractBillEntryFactory.getLocalInstance(ctx).delete(filter);
	}
	protected boolean _contractBillStore(Context ctx, IObjectPK ctPK, String storeNumber) throws BOSException, EASBizException {
		boolean flag = false;
//		ContractBillInfo cbi = this.getContractBillInfo(ctx,ctPK);
//		 String sql =
//		        "select fid from T_CON_ContractBill where FNumber = ? and FOrgUnitID = ? ";
//		    Object[] params = new Object[]{storeNumber,cbi.getOrgUnit().getId().toString()};
//
//		    RowSet rowset = DbUtil.executeQuery(ctx, sql, params);
//		    try {
//				if(rowset.next()){
//					if(!rowset.getString("FID").equalsIgnoreCase(ctPK.toString())){
//						throw (new ContractException(ContractException.NUMBER_DUP));
//					}else{
//						sql = "update T_CON_ContractBill set  FIsArchived = 1 ,FNumber = ?  where FID = ? ";
//					    params = new Object[]{storeNumber,ctPK.toString()};
//					    DbUtil.execute(ctx,sql,params);
//					    flag = true;
//					}
//				}else{
//					sql = "update T_CON_ContractBill set  FIsArchived = 1 ,FNumber = ?  where FID = ? ";
//				    params = new Object[]{storeNumber,ctPK.toString()};
//				    DbUtil.execute(ctx,sql,params);
//				    flag = true;
//				}
//			} catch (SQLException e) {			
//			}
//			
//			//如果归档成功,该合同关联的变更签证单状态改变为已审批
//			if(flag){
//				sql = "update T_CON_ContractChangeBill set  FState = '4AUDITTED' where FContractBillID = ? ";
//				params = new Object[]{ctPK.toString()};
//			    DbUtil.execute(ctx,sql,params);				
//			}
//	    
		return flag;
	}
	
	/**
	 * 如果是非独立结算的补充合同,金额累加到主合同
	 * @param ctx
	 * @param billId
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void supplyAdd(Context ctx, BOSUuid billId,boolean audit) throws BOSException, EASBizException{
		if(audit){
			//如果是非独立结算的补充合同,金额累加到
			String sql1 = " select entry.fcontent as amount,parent.fsplitState splitState,parent.fid mainId,parent.FAmount mainAmount,parent.ForiginalAmount oriAmount 			\r\n" +
					",parent.FexRate mainRate,parent.FgrtRate grtRate,parent.FGrtAmount grtAmount,parent.FStampTaxRate stampRate,parent.FStampTaxAmt stampAmt                             \r\n"+
					"from T_CON_ContractbillEntry entry 																							\r\n" +
					"inner join T_CON_Contractbill con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY' 	\r\n" +
					"inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and parent.fcurprojectid=con.fcurprojectid												\r\n" +
					"where con.fid=? and entry.FRowkey='am' 																				\r\n";
			IRowSet rs1 = DbUtil.executeQuery(ctx,sql1,new Object[]{billId.toString()});
			try {
				if(rs1!=null && rs1.next()){
					//更新主合同金额
					String splitState = rs1.getString("splitState");
					String mainId = rs1.getString("mainId");
					//补充合同金额
					BigDecimal supAmount = rs1.getBigDecimal("amount");
					//主合同本位币金额
					BigDecimal mainAmount = rs1.getBigDecimal("mainAmount");
//					BigDecimal localAmount = rs1.getBigDecimal("mainLocalAmount");
					//主合同原币金额
					BigDecimal oriAmount = rs1.getBigDecimal("oriAmount");
					//主合同汇率
					BigDecimal mainRate = rs1.getBigDecimal("mainRate");
					//主合同保修比例
					BigDecimal grtRate = rs1.getBigDecimal("grtRate");
					//主合同保修金额
					BigDecimal grtAmount = rs1.getBigDecimal("grtAmount");
					//主合同印花税率
					BigDecimal stampRate = rs1.getBigDecimal("stampRate");
					//主合同印花税金额
					BigDecimal stampAmt = rs1.getBigDecimal("stampAmt");
					//更新主合同原币和本位币金额
					String updateSql = "update T_CON_Contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
					
			    	//原币金额
			    	if(supAmount==null){
			    		supAmount = FDCConstants.ZERO;
			    	}
			    	if(mainAmount==null){
			    		mainAmount = FDCConstants.ZERO;
			    	}
			    	if(oriAmount==null){
			    		oriAmount = FDCConstants.ZERO;
			    	}
			    	BigDecimal revAmount = FDCConstants.ZERO;
			    	if(mainRate.compareTo(FDCConstants.ZERO)==1){
			    		revAmount = supAmount.multiply(mainRate);
			    	}
			    	//更新保修金额
			    	BigDecimal newGrtAmount = FDCConstants.ZERO;
			    	if(grtRate.compareTo(FDCConstants.ZERO)==1){
			    		newGrtAmount = (supAmount.add(oriAmount)).multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED,4,2);
			    	}
			    	//更新印花税金额
			    	BigDecimal newStampAmt = FDCConstants.ZERO;
			    	if(stampRate.compareTo(FDCConstants.ZERO)==1){
			    		newStampAmt = (supAmount.add(oriAmount)).multiply(mainRate).multiply(stampRate).divide(FDCConstants.ONE_HUNDRED,4,2);
			    	}
					DbUtil.execute(ctx,updateSql,new Object[]{supAmount.add(oriAmount),revAmount.add(mainAmount),newGrtAmount,newStampAmt,mainId});
							    		
					//判断主合同的金额 和 合同拆分的已拆分金额 ，是否相等。如果不等，把主合同和合同拆分的拆分状态改为部分拆分
//					String updateState1 = "update T_CON_Contractbill set FSplitState=? where Fid=?";
//					String updateState2 = "update T_CON_ContractCostSplit set FSplitState=? where Fid=?";
					EntityViewInfo viewInfo = new EntityViewInfo();
//					viewInfo.getSelector().add("amount");
//					viewInfo.getSelector().add("splitState");
					viewInfo.getSelector().add("*");
					viewInfo.getSelector().add("curProject.costCenter");
					viewInfo.getSelector().add("contractBill.isCoseSplit");
					FilterInfo filterInfo = new FilterInfo();
					viewInfo.setFilter(filterInfo);
					filterInfo.getFilterItems().add(new FilterItemInfo("contractBill.id",mainId));
					
					//R110228-280：录入非单独结算的补充合同之后主合同显示为全部拆分(之前没有考虑合同不进入动态成本的情况)
					ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
					if (contractbill.isIsCoseSplit()) {
						
						//成本类：非自动按比例拆分时部分拆分(以前逻辑)，否则(启用参数)自动拆分并且状态为全部拆分 by hpw 2010-06-25
						ContractCostSplitCollection col = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(viewInfo);
						boolean isSplitBaseOnProp = false;
						if (col != null && col.size() > 0) {
							ContractCostSplitInfo info = col.get(0);
							if (info != null && info.getContractBill() != null && info.getContractBill().isIsCoseSplit() && info.getCurProject() != null && info.getCurProject().getCostCenter() != null
									&& info.getCurProject().getCostCenter().getId() != null) {
								isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, info.getCurProject().getCostCenter().getId().toString(), FDCConstants.FDC_PARAM_SPLITBASEONPROP);
							}
						}
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						for(int i=0;i < col.size();i++){
							ContractCostSplitInfo info = col.get(i);
							if(info!=null){
								if (!isSplitBaseOnProp) {
									//如果补充合同+主合同金额大于主合同已拆分金额 则变更主合同拆分状态为部分拆分
									//R110608-0383: 录入金额为0的主合同的拆分金额存在为0的情况
									BigDecimal splitAmount = info.getBigDecimal("amount");
									if (splitAmount == null) {
										splitAmount = FDCHelper.ZERO;
									}
									if((mainAmount.add(revAmount)).compareTo(splitAmount)!=0
											&& splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
										builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
										builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
										builder.addParam(mainId.toString());
										builder.getSql();
										builder.execute();
										builder.clear();//不用再New
										builder.appendSql("update T_CON_ContractCostSplit set fsplitState=? where fcontractbillid=?");
										builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
										builder.addParam(mainId.toString());
										builder.execute();
										builder.clear();
									}
								}
							}
						}
						
						if (isSplitBaseOnProp) {
							ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(mainId));

							// 审核时，如果为自动拆分，那么就将状态改为”全部拆分“
							builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
							builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
							builder.addParam(mainId.toString());
							builder.getSql();
							builder.execute();
							builder.clear();
							
							builder.appendSql("update T_CON_ContractCostSplit set fsplitState=? where fcontractbillid=?");
							builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
							builder.addParam(mainId.toString());
							builder.execute();
							builder.clear();
						}
					} else {
						//非成本类：更新状态为部分拆分
						ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						for(int i=0;i < col.size();i++){
							ConNoCostSplitInfo info = col.get(i);
							if(info!=null){
								//如果补充合同+主合同金额大于主合同已拆分金额 则变更主合同拆分状态为部分拆分
								BigDecimal splitAmount = info.getBigDecimal("amount");
								if (splitAmount == null) {
									splitAmount = FDCHelper.ZERO;
								}
								if((mainAmount.add(revAmount)).compareTo(splitAmount)!=0
										&& splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
									builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.getSql();
									builder.execute();
									builder.clear();//不用再New
									builder.appendSql("update T_CON_ConNoCostSplit set fsplitState=? where fcontractbillid=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.execute();
									builder.clear();
								}
							}
						}
					}
					
			    	//更新合同付款计划    					
					revAmount = revAmount.add(FDCUtils.getContractLastPrice (ctx,mainId,false));
					
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add(new SelectorItemInfo("payProportion"));
					FilterInfo filter = new FilterInfo();
					view.setFilter(filter);
					filter.getFilterItems().add(new FilterItemInfo("contractId.id", mainId));
					
					IContractPayPlan iContractPayPlan =  ContractPayPlanFactory.getLocalInstance(ctx);
					ContractPayPlanCollection payPlans =iContractPayPlan.getContractPayPlanCollection(view);
					for (int i = 0; i < payPlans.size(); i++) {
						ContractPayPlanInfo info = payPlans.get(i);
						
						info.setPayAmount(revAmount.multiply(info.getPayProportion()).divide(new BigDecimal(100), 2,
								BigDecimal.ROUND_HALF_UP));					
						iContractPayPlan.update(new ObjectUuidPK(info.getId().toString()),info);
					}
				/*****更新主合同的合同执行情况表合同成本金额 -by neo****/
				ContractExecInfosFactory.getLocalInstance(ctx).
						updateSuppliedContract("audit", mainId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}else{
			//如果是非独立结算的补充合同,金额累加到
			String sql = " select entry.fcontent as amount,parent.fsplitState splitState,parent.fid mainId,parent.FAmount mainAmount,parent.ForiginalAmount oriAmount 			\r\n" +
					",parent.FexRate mainRate,parent.FgrtRate grtRate,parent.FGrtAmount grtAmount,parent.FStampTaxRate stampRate,parent.FStampTaxAmt stampAmt    \r\n"+
					" ,parent.FHasSettled    from T_CON_ContractbillEntry entry 																	\r\n" +
					"inner join T_CON_Contractbill con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY' 	\r\n" +
					"inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber 	and parent.fcurprojectid=con.fcurprojectid					\r\n" +
					"where   con.fid=? 	and  entry.FRowkey='am' 	 																							\r\n";
			IRowSet rs = DbUtil.executeQuery(ctx,sql,new Object[]{billId.toString()});
			try {
				if(rs!=null && rs.next()){
					//
					boolean hasSettle = rs.getBoolean("FHasSettled");
					if(hasSettle){
						throw new ContractException(ContractException.MAINCONHASSETTLED);
					}
					String splitState = rs.getString("splitState");	
					//如果主合同已完全拆分 则不允许反审批补充合同
					if(splitState!=null &&splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
						throw new ContractException(ContractException.HASALLAPLIT);
					}
					//更新主合同金额
					String mainId = rs.getString("mainId");
					//补充合同金额
					BigDecimal supAmount = rs.getBigDecimal("amount");
					BigDecimal mainAmount = rs.getBigDecimal("mainAmount");
//					BigDecimal localAmount = rs.getBigDecimal("mainLocalAmount");
					//主合同原币金额
					BigDecimal oriAmount = rs.getBigDecimal("oriAmount");
					//主合同汇率
					BigDecimal mainRate = rs.getBigDecimal("mainRate");
					//主合同保修比例
					BigDecimal grtRate = rs.getBigDecimal("grtRate");
					//主合同保修金额
					BigDecimal grtAmount = rs.getBigDecimal("grtAmount");
					//主合同印花税率
					BigDecimal stampRate = rs.getBigDecimal("stampRate");
					//主合同印花税金额
					BigDecimal stampAmt = rs.getBigDecimal("stampAmt");
					
					String updateSql = "update T_CON_Contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
//					String updateSql = "update T_CON_Contractbill set FAmount=?,FlocalAmount=? where Fid=?";
					//DbUtil.execute(ctx,updateSql,new Object[]{mainAmount.subtract(supAmount),localAmount.subtract(supAmount),mainId});
					
					//R101227-311合同反审批时报错， 先进行空指针处理
			    	//补充合同金额 本位币
					if(supAmount==null){
						supAmount = FDCConstants.ZERO;
					}
			    	BigDecimal revAmount = supAmount.multiply(mainRate);
			    	//如果主合同已拆分金额大于其自身金额 则补充合同不能反审批
			    	String splitSql = "select famount from T_CON_ContractCostSplit where fcontractBillId=?";
			    	IRowSet ir = DbUtil.executeQuery(ctx,splitSql,new Object[]{mainId.toString()});
			    	if(ir!=null && ir.next()){
			    		BigDecimal splitAmount = ir.getBigDecimal("famount");
			    		if (splitAmount == null) {
			    			splitAmount = FDCHelper.ZERO;
			    		}
			    		if(splitAmount.compareTo(mainAmount.subtract(revAmount))==1){
			    			throw new ContractException(ContractException.HASSPLIT);
			    		}
			    	}
					//如果付款申请单的累计申请金额已经大于revAmount
			    	if(mainAmount==null){
			    		mainAmount = FDCConstants.ZERO;
			    	}
			    	if(oriAmount==null){
			    		oriAmount = FDCConstants.ZERO;
			    	}
			    	BigDecimal temp = FDCConstants.ZERO;
			    	if(mainRate.compareTo(FDCConstants.ZERO)==1){
			    		temp = supAmount.multiply(mainRate);
			    	}
			    	//更新保修金额
			    	BigDecimal newGrtAmount = FDCConstants.ZERO;
			    	if(grtRate.compareTo(FDCConstants.ZERO)==1){
			    		newGrtAmount = (oriAmount.subtract(supAmount)).multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED,4,2);
			    	}
			    	//更新印花税金额
			    	BigDecimal newStampAmt = FDCConstants.ZERO;
			    	if(stampRate.compareTo(FDCConstants.ZERO)==1){
			    		newStampAmt = (oriAmount.subtract(supAmount)).multiply(mainRate).multiply(stampRate).divide(FDCConstants.ONE_HUNDRED,4,2);
			    	}
			    	DbUtil.execute(ctx,updateSql,new Object[]{oriAmount.subtract(supAmount),mainAmount.subtract(temp),newGrtAmount,newStampAmt,mainId});
					EntityViewInfo viewInfo = new EntityViewInfo();
					viewInfo.getSelector().add("*");
					viewInfo.getSelector().add("curProject.costCenter");
					viewInfo.getSelector().add("contractBill.isCoseSplit");
					FilterInfo filterInfo = new FilterInfo();
					viewInfo.setFilter(filterInfo);
					filterInfo.getFilterItems().add(new FilterItemInfo("contractBill.id",mainId));
					
					//R110228-280：录入非单独结算的补充合同之后主合同显示为全部拆分(之前没有考虑合同不进入动态成本的情况)
					ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
					if (contractbill.isIsCoseSplit()) {
						
						//成本类：非自动按比例拆分时部分拆分，否则自动拆分并且状态为全部拆分 by hpw 2010-06-25
						ContractCostSplitCollection coll = ContractCostSplitFactory
						.getLocalInstance(ctx).getContractCostSplitCollection(viewInfo);
						boolean isSplitBaseOnProp = false;
						if (coll != null && coll.size() > 0) {
							ContractCostSplitInfo info = coll.get(0);
							if (info != null && info.getContractBill() != null && info.getContractBill().isIsCoseSplit() && info.getCurProject() != null && info.getCurProject().getCostCenter() != null
									&& info.getCurProject().getCostCenter().getId() != null) {
								isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, info.getCurProject().getCostCenter().getId().toString(), FDCConstants.FDC_PARAM_SPLITBASEONPROP);
							}
						}
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						for(int j=0;j < coll.size();j++){
							ContractCostSplitInfo splitInfo = coll.get(j);
							if(splitInfo!=null){
								if(!isSplitBaseOnProp){
									//如果主合同原金额等于主合同已拆分金额 则变更主合同拆分状态为完全拆分
									BigDecimal splitAmount = splitInfo.getBigDecimal("amount");
									if (splitAmount == null) {
										splitAmount = FDCHelper.ZERO;
									}
									if((mainAmount.subtract(revAmount)).compareTo(splitAmount)==0 
											&& splitState.equals(CostSplitStateEnum.PARTSPLIT_VALUE)){
										builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
										builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
										builder.addParam(mainId.toString());
										builder.getSql();
										builder.execute();
										//								DbUtil.execute(ctx,updateState1,new Object[]{CostSplitStateEnum.PARTSPLIT_VALUE,mainId.toString()});
										//								FDCSQLBuilder builder2=new FDCSQLBuilder(ctx);
										builder.clear();
										builder.appendSql("update T_CON_ContractCostSplit set fsplitState=? where fcontractbillid=?");
										builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
										builder.addParam(mainId.toString());
										builder.execute();
										builder.clear();
									}
								}
							}
						}
						
						if(isSplitBaseOnProp){
							ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(mainId));
						}
					} else {
						
						//非成本类：更新状态为部分拆分
						ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						for(int i=0;i < col.size();i++){
							ConNoCostSplitInfo info = col.get(i);
							if(info!=null){
								//如果主合同原金额等于主合同已拆分金额 则变更主合同拆分状态为完全拆分
								BigDecimal splitAmount = info.getBigDecimal("amount");
								if (splitAmount == null) {
									splitAmount = FDCHelper.ZERO;
								}
								if((mainAmount.add(revAmount)).compareTo(splitAmount)==0
										&& splitState.equals(CostSplitStateEnum.PARTSPLIT_VALUE)){
									builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
									builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.getSql();
									builder.execute();
									builder.clear();
									builder.appendSql("update T_CON_ConNoCostSplit set fsplitState=? where fcontractbillid=?");
									builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.execute();
									builder.clear();
								}
							}
						}
					}
					
			    	//更新合同付款计划    	
			    	revAmount = revAmount.add(FDCUtils.getContractLastPrice (ctx,mainId,false));
			    	
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add(new SelectorItemInfo("payProportion"));
					FilterInfo filter = new FilterInfo();
					view.setFilter(filter);
					filter.getFilterItems().add(new FilterItemInfo("contractId.id", mainId));
					
					IContractPayPlan iContractPayPlan =  ContractPayPlanFactory.getLocalInstance(ctx);
					ContractPayPlanCollection payPlans =iContractPayPlan.getContractPayPlanCollection(view);
					for (int i = 0; i < payPlans.size(); i++) {
						ContractPayPlanInfo info = payPlans.get(i);
						
						info.setPayAmount(revAmount.multiply(info.getPayProportion()).divide(new BigDecimal(100), 2,
								BigDecimal.ROUND_HALF_UP));					
						iContractPayPlan.update(new ObjectUuidPK(info.getId().toString()),info);
					}
				/*****更新主合同的合同执行情况表合同成本金额 -by neo****/
				ContractExecInfosFactory.getLocalInstance(ctx).
						updateSuppliedContract("audit", mainId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		//ContractBillInfo contractBill = (ContractBillInfo)this.getValue(ctx,new ObjectUuidPK(billId.toString()),seletor);
	}
	
	private String getMainSignAmount(Context ctx, BOSUuid billId) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rowSet = null;
		builder
				.appendSql(" select con.famount amount from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
		builder
				.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
		builder.appendSql(" where  entry.FRowkey='am' and ");
		builder.appendParam(" con.fid", billId.toString());
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			return rowSet.getBigDecimal("amount").toString();
		}
		return null;
	}
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		
		checkBillForAudit( ctx,billId);
		
		/* modified by zhaoqin for R140402-0042 on 2014/05/29 */
		checkContractProgramming(ctx, billId.toString());
		
		super._audit(ctx, billId);
		
		// 在金额累计之前取主合同的签约金额
		String mainSignAmount = null;
		try {
			mainSignAmount = getMainSignAmount(ctx, billId);
			synUpdateBillByRelation(ctx, billId, true, mainSignAmount);
		} catch (SQLException e1) {
			logger.error(e1);
			throw new BOSException(e1);
		}	
		
		//如果是非独立结算的补充合同,金额累加到
		supplyAdd(ctx, billId,true);
	
		//同步到执行状态：已签署
		ContractStateHelper.synToExecState(ctx, billId.toString(), ContractExecStateEnum.SIGNED);

		//自动审批拆分单(完全拆分状态的)
		//成本拆分
//		CostSplitBillAutoAuditor.autoAudit(ctx, new ObjectUuidPK(billId), "T_CON_ContractCostSplit", "FContractBillID");
		//财务拆分
//		CostSplitBillAutoAuditor.autoAudit(ctx, new ObjectUuidPK(billId), "T_CON_ConNoCostSplit", "FContractBillID");
		
		
		// modified by zhaoqin for 项目资金计划 on 2013/08/15 start
		// return super._submit(ctx, contractBillInfo);
		ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx, new ObjectUuidPK(billId.toString()), getSic());
//		String costCenterId = contractBillInfo.getOrgUnit().getId().toString();//合同肯定是在成本中心下做的
		// 是否自动拆分
		//boolean isAutoSplit = FDCUtils.getBooleanValue4FDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_ISAUTOSPLIT);
		/**
		 * 是否自动拆分:
		 *  0: 合同自动拆分
		 *  1: 全部自动拆分
		 *  2: 不自动拆分
		 */
//		String autoSplit = FDCUtils.getFDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_ISAUTOSPLIT);
//		if (null != autoSplit && ("0".equals(autoSplit) || "1".equals(autoSplit)))
//			autoSplit(ctx, contractBillInfo);
		// modified by zhaoqin for 项目资金计划 on 2013/08/14 end
		updateContractExecInfos(ctx, billId);		
		updatePeriod(ctx, billId);
		updateInviteContractHistory(ctx, billId);
		
		//判断拆分分录有无信息，有则保存至合同拆分
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filInfo = new FilterInfo();
//		filInfo.getFilterItems().add(new FilterItemInfo("parent.id",billId));
//		view.setFilter(filInfo);
//		view.setSelector()
//		ContractBillSplitEntryCollection cbscoll=ContractBillSplitEntryFactory.getLocalInstance(ctx).getContractBillSplitEntryCollection(view);
		ContractBillSplitEntryCollection cbscoll = contractBillInfo.getSplitEntry();
		if(cbscoll.size() > 0){
			ContractCostSplitInfo ccinfo = new ContractCostSplitInfo();
			ccinfo.setState(FDCBillStateEnum.SAVED);
			ccinfo.setHasInitIdx(true);
			ccinfo.setIslastVerThisPeriod(true);
			ccinfo.setContractBill(contractBillInfo);
			ccinfo.setCurProject(contractBillInfo.getCurProject());
			ccinfo.setAmount(contractBillInfo.getAmount());
		    ccinfo.setIsInvalid(false);
		    ccinfo.setIsConfirm(true); 
			ContractBillSplitEntryInfo cbseinfo = null;
			ContractCostSplitEntryInfo costentry = null;
			BigDecimal splitAmount = BigDecimal.ZERO;
			for (int i = 0; i < cbscoll.size(); i++) {
				cbseinfo = cbscoll.get(i);
				costentry = new ContractCostSplitEntryInfo();
				costentry.setSeq(cbseinfo.getSeq());
				costentry.setCostAccount(cbseinfo.getCostAccount());
				costentry.setProduct(cbseinfo.getProduct());
				if(cbseinfo.getAmount() != null && cbseinfo.getLevel() == 0)
					splitAmount = splitAmount.add(cbseinfo.getAmount());
				costentry.setAmount(cbseinfo.getAmount());
				costentry.setSplitType(cbseinfo.getSplitType());
				costentry.setLevel(cbseinfo.getLevel());
				costentry.setIsLeaf(cbseinfo.isIsLeaf());
				costentry.setApportionType(cbseinfo.getApportionType());
				costentry.setApportionValue(cbseinfo.getApportionValue());
				costentry.setApportionValueTotal(cbseinfo.getApportionValueTotal());
				costentry.setOtherRatio(cbseinfo.getOtherRatio());
				costentry.setOtherRatioTotal(cbseinfo.getOtherRatioTotal());
				costentry.setDirectAmount(cbseinfo.getDirectAmount());
				costentry.setDirectAmountTotal(cbseinfo.getDirectAmountTotal());
				costentry.setIsApportion(cbseinfo.isIsApportion());
				costentry.setIsAddlAccount(cbseinfo.isIsAddlAccount());
				costentry.setCostBillId(cbseinfo.getCostBillId());
				costentry.setIndex(cbseinfo.getIndex());
				costentry.setIdxApportionId(cbseinfo.getIdxApportionId());
				costentry.setSplitBillId(cbseinfo.getSplitBillId());
				costentry.setPrice(cbseinfo.getPrice());
				costentry.setWorkLoad(cbseinfo.getWorkLoad());
				costentry.setSplitScale(cbseinfo.getSplitScale());
				costentry.setProgrammings(cbseinfo.getProgrammings());
				ccinfo.getEntrys().add(costentry);
			}
			if(contractBillInfo.getAmount().compareTo(splitAmount) == 0)
				ccinfo.setSplitState(CostSplitStateEnum.ALLSPLIT);
			else
				ccinfo.setSplitState(CostSplitStateEnum.PARTSPLIT);
			ContractCostSplitFactory.getLocalInstance(ctx).save(ccinfo);
		}
		/**
		 * 导入合同付款计划
		 */
//		if(ContractPropertyEnum.DIRECT.equals(contractBillInfo.getContractPropert())){
//			ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(billId.toString(),false);
//		}else if(ContractPropertyEnum.SUPPLY.equals(contractBillInfo.getContractPropert())){
//			if(contractBillInfo.isIsAmtWithoutCost()){
//				ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(billId.toString(),false);
//			}else{
//				ContractBillEntryCollection entrys = contractBillInfo.getEntrys();
//				for(int i=0;i<entrys.size();i++){
//					ContractBillEntryInfo contractBillEntryInfo = entrys.get(i);
//					if("nu".equals(contractBillEntryInfo.getRowKey())){
//						ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(contractBillEntryInfo.getContent(),false);
//						break;
//					}
//				}
//			}
//		}
	}
	
	/**
	 * 描述：审批时根据合同数据增加合同执行表相关信息
	 * 判断：当且仅当合同性质为补充合同且合同为不计成本金额时不记录合同执行情况表
	 * @param billId
	 * @Author：owen_wen
	 * @CreateTime：2012-1-6
	 */
	private void updateContractExecInfos(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isAmtWithoutCost",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("contractPropert",ContractPropertyEnum.SUPPLY_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("id",billId));
		if (!ContractBillFactory.getLocalInstance(ctx).exists(filter)) {
			ContractExecInfosFactory.getLocalInstance(ctx).updateContract(ContractExecInfosInfo.EXECINFO_AUDIT, billId.toString());
		}
	}
	
	/**
	 * 描述：更新招投标管里中的供应商的签约历史，根据两边供应商的number进行匹配
	 * @param ctx
	 * @param billId 合同ID
	 * @Author：owen_wen
	 * @CreateTime：2012-1-6
	 */
	private void updateInviteContractHistory(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		IObjectPK pk= new ObjectUuidPK(billId.toString());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("partB.*"));
		selector.add(new SelectorItemInfo("partC.*"));
		ContractBillInfo billValue = (ContractBillInfo) _getValue(ctx, pk, selector );
		StringBuffer str = new StringBuffer();
		if(billValue.getPartB()!=null){
			str.append("'" + billValue.getPartB().getNumber().toString() + "'");
		}
		if (billValue.getPartC() != null) {
			str.append(",'" + billValue.getPartC().getNumber().toString() + "'");
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		Set partIds = new HashSet();
		if (str != null && !str.toString().equals("")) {
			builder.appendSql("select fid from t_fdc_supplierstock where fnumber in ("+ str.toString() + ")");
			IRowSet fids = builder.executeQuery();
			try {
				while (fids.next()) {
					partIds.add(fids.getString("fid").toString());
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		if(partIds.size()>0){
			Iterator it = partIds.iterator();
			while(it.hasNext()){
				IObjectPK pkId = new ObjectUuidPK(it.next().toString());
				SupplierStockFactory.getLocalInstance(ctx).updateHisCount(UpdateHistoryTypeEnum.CONSTRACTHIS, pkId, true);
			}
		}
	}
	
	private String getNextVersionProg(Context ctx, String nextProgId, FDCSQLBuilder builder, IRowSet rowSet) throws BOSException, SQLException {
		String tempId = null;
		builder.clear();
		builder.appendSql(" select fid from t_con_programmingContract where  ");
		builder.appendParam("FSrcId", nextProgId);
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			tempId = rowSet.getString("fid");
		}
		return tempId;
	}

	/**
	 * 1 .当合同未结算时(无最终结算或最终结算未审批)，规划余额=规划金额-（签约金额+变更金额），控制余额=控制金额-签约金额 2
	 * .当合同已结算时(最终结算已审批)，规划余额=规划金额-结算金额，控制余额=控制金额-结算金额 3
	 * .反写时点在合同单据审批通过时、变更签证申请审批通过时、变更签证确认结算时、合同结算审批通过时。 4.
	 * 合同修订审批后，规划余额=规划金额-（修订后的签约金额+变更金额），控制余额=控制金额-修订后的签约金额。
	 * 
	 * @param ctx
	 * @param billId
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLException
	 * @throws SQLException
	 */
	private void synUpdateBillByRelation(Context ctx, BOSUuid billId, boolean flag, String relateAmt) throws EASBizException, BOSException,
			SQLException {
		String billIdStr = billId.toString();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rowSet = null;
		builder
				.appendSql(" select parent.fid conId from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
		builder
				.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
		builder.appendSql(" where  entry.FRowkey='am' and ");
		builder.appendParam(" con.fid", billIdStr);
		rowSet = builder.executeQuery();
		if (rowSet.next()) {
			billIdStr = rowSet.getString("conId").toString();
		}
		ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx, new ObjectUuidPK(billIdStr), getSic());
		if (contractBillInfo.getProgrammingContract() == null)
			return;
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
		if (pcInfo == null)
			return;
		// 规划余额
		BigDecimal balanceAmt = pcInfo.getBalance();
		// 控制余额
		BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
		// 合同签约金额
		BigDecimal signAmount = contractBillInfo.getAmount();
		// if(mainSignAmount != null){
		// if(flag){
		// signAmount = FDCHelper.subtract(signAmount, mainSignAmount);
		// }else{
		// signAmount = FDCHelper.add(signAmount, mainSignAmount);
		// }
		// }
		// 框架合约签约金额
		BigDecimal signUpAmount = pcInfo.getSignUpAmount();
		// 差额
		BigDecimal otherSignUpAmount = FDCHelper.ZERO;
		if (flag) {
			if (relateAmt == null) {
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, signAmount));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, signAmount));
				pcInfo.setSignUpAmount(FDCHelper.add(signUpAmount, signAmount));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.subtract(balanceAmt, signAmount));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.subtract(controlBalanceAmt, signAmount));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, signAmount), signUpAmount);
			} else {
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, relateAmt));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, relateAmt));
				pcInfo.setSignUpAmount(FDCHelper.add(signUpAmount, relateAmt));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.subtract(balanceAmt, relateAmt));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.subtract(controlBalanceAmt, relateAmt));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, relateAmt), signUpAmount);
			}
		} else {
			if (relateAmt == null) {
				pcInfo.setBalance(FDCHelper.add(balanceAmt, signAmount));
				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, signAmount));
				pcInfo.setSignUpAmount(FDCHelper.subtract(signUpAmount, signAmount));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.add(balanceAmt, signAmount));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.add(controlBalanceAmt, signAmount));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, signAmount), signUpAmount);
			} else {
				pcInfo.setBalance(FDCHelper.add(balanceAmt, relateAmt));
				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, relateAmt));
				pcInfo.setSignUpAmount(FDCHelper.subtract(signUpAmount, relateAmt));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.add(balanceAmt, relateAmt));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.add(controlBalanceAmt, relateAmt));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, relateAmt), signUpAmount);
			}

		}
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("controlBalance");
		sict.add("signUpAmount");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("srcId");
		service.updatePartial(pcInfo, sict);
		// 更新其他的合约规划版本金额
		String progId = pcInfo.getId().toString();
		while (progId != null) {
			String nextVersionProgId = getNextVersionProg(ctx, progId, builder, rowSet);
			if (nextVersionProgId != null) {
				pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
				pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), otherSignUpAmount));
				pcInfo.setControlBalance(FDCHelper.subtract(pcInfo.getControlBalance(), otherSignUpAmount));
				pcInfo.setSignUpAmount(FDCHelper.add(pcInfo.getSignUpAmount(), otherSignUpAmount));
				service.updatePartial(pcInfo, sict);
				progId = pcInfo.getId().toString();
			} else {
				progId = null;
			}
		}
	}

	/**
	 * 
	 * @param billId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private BigDecimal getChangeAmountByContract(Context ctx, BOSUuid billId) throws BOSException, EASBizException {

		StringBuffer sql = new StringBuffer();

		sql.append(" Select CON.FID CON, ISNULL(SUM(CAB.FAmount),0) AMOUNT  ");
		sql.append(" from T_CON_ChangeAuditBill CAB ");
		sql.append(" right join T_CON_ChangeSupplierEntry CSE ON CSE.FParentID = CAB.FID  ");
		sql.append(" inner join T_CON_ContractBill CON ON  CSE.FContractBillID = CON.FID  ");
		sql.append(" WHERE  1 = 1 AND  ");
		sql.append(" CON.FID = '").append(billId.toString()).append("' AND	 ");
		sql.append(" CON.FState IN ('4AUDITTED')  ");
		sql.append(" GROUP BY CON.FID, CON.FAmount ");

		IRowSet rowSet = DbUtil.executeQuery(ctx, sql.toString());
		BigDecimal conTotal = new BigDecimal(0);
		try {
			if (rowSet.next()) {
				String conId = rowSet.getString("CON");
				conTotal = rowSet.getBigDecimal("AMOUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		return conTotal;
	}

	private BigDecimal getContractSettlementBillByContract(Context ctx, BOSUuid billId) throws BOSException, EASBizException {

		IContractSettlementBill settleSerivce = ContractSettlementBillFactory.getLocalInstance(ctx);
		// 你找到该合同下是否有最终结算的单据，有，就说明该合同是最终结算了
		// 合同已结算则一定有最终结算的结算单
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(this.getSettleSic());

		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("contractBill", billId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", Integer.valueOf("1")));

		view.setFilter(filter);

		ContractSettlementBillCollection settleColl = settleSerivce.getContractSettlementBillCollection(view);
		BigDecimal amt = new BigDecimal(0);

		if (settleColl != null) {
			if (settleColl.size() == 1) {
				ContractSettlementBillInfo billInfo = settleColl.get(0);
				amt = billInfo.getAmount();
			}
		}
		return amt;
	}
	
	/**
	 * 根据当前项目成本期间更新暂缓单据的业务日期和订立期间
	 * @param ctx
	 * @param billId
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void updatePeriod(Context ctx, BOSUuid billId) throws EASBizException, BOSException {
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("isRespite");
		selectors.add("curProject.id");
		selectors.add("curProject.fullOrgUnit.id");
		selectors.add("bookedDate");
		selectors.add("period.*");
		ContractBillInfo billInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(billId), selectors);
		
		String companyID = billInfo.getCurProject().getFullOrgUnit().getId().toString();
		boolean isInCore = FDCUtils.IsInCorporation(ctx, companyID); // FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INCORPORATION);
		if(isInCore){
			//启用月结统一按以下逻辑处理
			String prjId = billInfo.getCurProject().getId().toString();
			//成本期间
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
			PeriodInfo billPeriod = billInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//最终所在期间
			Date bookedDate = DateTimeUtils.truncateDate(billInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","单据所对应的组织没有当前时间的期间，请先设置！"));
			}
			/***************
			 * （1）当工程量确认单上的“业务日期”和“业务期间”大于工程项目成本财务“当前期间”时，“业务期间”不变
			 * （2）当工程量确认单上的“业务日期”和“业务期间”小于等于工程项目成本财务“当前期间”时，“业务期间”更新为工程项目成本财务“当前期间”
			 *	
			 *	原理与拆分保存时相同，期间老出问题
			 */
			if (billPeriod != null && billPeriod.getNumber() > finPeriod.getNumber()) {
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				shouldPeriod = billPeriod;
			} else if (finPeriod != null) {
				if (bookedDate.before(finPeriod.getBeginDate())) {
					bookedDate = finPeriod.getBeginDate();
				} else if (bookedDate.after(finPeriod.getEndDate())) {
					bookedDate = finPeriod.getEndDate();
				}
				shouldPeriod = finPeriod;
			}
			
			//更新付款申请单的业务日期，申请期间和暂缓状态
			
			selectors = new SelectorItemCollection();
			selectors.add("period");
			selectors.add("bookedDate");
			selectors.add("isRespite");
			billInfo.setBookedDate(bookedDate);
			billInfo.setPeriod(shouldPeriod);
			billInfo.setIsRespite(false);
			_updatePartial(ctx, billInfo, selectors);
		}
	}
	
	//单据审批时 预算控制
	private void invokeAuditBudgetCtrl(Context ctx,ContractBillInfo conBill) throws BOSException ,EASBizException{
		boolean useWorkflow = FDCUtils.isRunningWorkflow(ctx, conBill.getId().toString());
        if (!useWorkflow) {
            IBgControlFacade bgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
            IBgBudgetFacade iBgBudget = BgBudgetFacadeFactory.getLocalInstance(ctx);

            boolean isPass = false;
            isPass = iBgBudget.getAllowAccessNoWF(FDCConstants.ContractBill);
            if (isPass) {
                // 5.1.1暂时屏蔽
                bgControlFacade.bgAuditAllowAccess(conBill.getId(), FDCConstants.ContractBill, null);
            } else {
                isPass = bgControlFacade.bgAudit(conBill.getId().toString(),  FDCConstants.ContractBill, null);
            }
            // 根据isPass判断是否抛异常
            if (!isPass) {
                throw new ContractException(ContractException.BEFOREBGBAL);
            }
        }
	}
	//单据反审批时 预算控制
	private void invokeUnAuditBudgetCtrl(Context ctx,ContractBillInfo conBill) throws BOSException ,EASBizException{
		
		IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(ctx);
		iBgCtrl.cancelRequestBudget(conBill.getId().toString());
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		
		checkBillForUnAudit(ctx, billId);
		
		FilterInfo filter = new FilterInfo();
		// 合同
		filter.getFilterItems().add(new FilterItemInfo("id", billId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("isArchived", BooleanEnum.TRUE));
		
		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.HASACHIVED);
		}	
		//如果合同关联合同拆分，则提示不能进行反审核  getContractCostSplitCollection("select id where contractbill.id='"+billId.toString()+"'")
//		if(ContractCostSplitFactory.getLocalInstance(ctx).exists("where contractbill.id='"+billId.toString()+"'"))
//			throw new ContractException(new NumericExceptionSubItem("999","已关联合同拆分单据，不能反审核！"));
		super._unAudit(ctx, billId);
		// 在金额累计之前取补充合同的补充金额
		// 在金额累计之前取主合同的签约金额
		String mainSignAmount = null;
		try {
			mainSignAmount = getMainSignAmount(ctx, billId);
			synUpdateBillByRelation(ctx, billId, false, mainSignAmount);
		} catch (SQLException e1) {
			logger.error(e1);
			throw new BOSException(e1);
		}
		//如果是非独立结算的补充合同，主合同金额减少
		supplyAdd(ctx, billId,false);
		//自动反审批拆分单
		//成本拆分
		CostSplitBillAutoAuditor.autoUnAudit(ctx, new ObjectUuidPK(billId), "T_CON_ContractCostSplit", "FContractBillID");
		//财务拆分
		CostSplitBillAutoAuditor.autoUnAudit(ctx, new ObjectUuidPK(billId), "T_CON_ConNoCostSplit", "FContractBillID");
		
		//自动删除本期月结数据
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("period.id");

		ContractBillInfo info = (ContractBillInfo)this.getValue(ctx,new ObjectUuidPK(billId),selectors);
		if(info.getPeriod()!=null){
			CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(info.getId().toString(),
					info.getPeriod().getId().toString());
		}
		//反审批合同时删除合同执行表对应相关信息
		ContractExecInfosFactory.getLocalInstance(ctx)
			.updateContract(ContractExecInfosInfo.EXECINFO_UNAUDIT,billId.toString());
		IObjectPK pk= new ObjectUuidPK(billId.toString());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("partB.*"));
		selector.add(new SelectorItemInfo("partC.*"));
		ContractBillInfo billValue = (ContractBillInfo) _getValue(ctx, pk, selector );
		StringBuffer str = new StringBuffer();
		if(billValue.getPartB()!=null){
			str.append("'" + billValue.getPartB().getNumber().toString() + "'");
		}
		if (billValue.getPartC() != null) {
			str.append(",'" + billValue.getPartC().getNumber().toString() + "'");
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		Set partIds = new HashSet();
		if (str != null && !str.toString().equals("")) {
			builder.appendSql("select fid from t_fdc_supplierstock where fnumber in ("+ str.toString() + ")");
			IRowSet fids = builder.executeQuery();
			try {
				while (fids.next()) {
					partIds.add(fids.getString("fid").toString());
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		if(partIds.size()>0){
			Iterator it = partIds.iterator();
			while(it.hasNext()){
				IObjectPK pkId = new ObjectUuidPK(it.next().toString());
				SupplierStockFactory.getLocalInstance(ctx).updateHisCount(UpdateHistoryTypeEnum.CONSTRACTHIS, pkId, false);
			}
		}	
	}
	

	protected boolean _contractBillStore(Context ctx, IObjectValue cbInfo, String storeNumber) throws BOSException, EASBizException {
		boolean flag = false;
		ContractBillInfo cbi = (ContractBillInfo)cbInfo;
		//使用编码规则取号
		String orgId = cbi.getOrgUnit().getId().toString();
		String bindingProperty = "codeType.number";
		// 无当前组织，或者当前组织没定义编码规则，用集团的
		if (orgId == null || orgId.trim().length() == 0) {			
			orgId = OrgConstants.DEF_CU_ID;
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		/* 是否启用编码规则 */
		if(cbi.getArchivedNumber() == null){//如果已经有过归档 则使用以前的归档合同编号 
			if (cbi.getCodeType()!=null&&iCodingRuleManager.isExist(cbi, orgId, bindingProperty)) {
				flag = true;						
				if (iCodingRuleManager.isUseIntermitNumber(cbi, orgId, bindingProperty)) {		// 编码规则中不允许断号					
					// 编码规则中启用了“断号支持”功能，此时只是读取当前最新编码，真正的抢号在保存时
					storeNumber = iCodingRuleManager.getNumber(cbi, orgId, bindingProperty, "");										
				} else if (iCodingRuleManager.isAddView(cbi, orgId, bindingProperty)){				
					// 判断是否修改了编码,是否改大了顺序号
					if (iCodingRuleManager.isModifiable(cbi, orgId, bindingProperty)) {					
						iCodingRuleManager.checkModifiedNumber(cbi, orgId,storeNumber, bindingProperty);
					}				
				} else {
					// 什么都没选,新增不显示,允许断号,在此设置number
//					if(cbi.getNumber()==null||cbi.getNumber().equals("")){
						storeNumber = iCodingRuleManager.getNumber(cbi,orgId, bindingProperty, "");						
//					}
				}	
			}
			//如果第一次归档 则把归档编号保存 进新加字段 FArchivedNumber
			String updateNumberSql = "update T_CON_ContractBill set FArchivedNumber=?  where FID = ? ";
			Object[] params = new Object[]{storeNumber,cbi.getId().toString()};
			DbUtil.execute(ctx,updateNumberSql,params);
		}	
					
		//更新合同归档状态和归档编码
		String updateNumberSql = "update T_CON_ContractBill set FArchivingNumber=fnumber  where FID = ? ";
		Object[] params = new Object[]{cbi.getId().toString()};
		DbUtil.execute(ctx,updateNumberSql,params);
		
		//更新合同归档状态和归档编码
		String sql = "select fid from T_CON_ContractBill where FNumber = ? and FOrgUnitID = ? ";
		 
		params = new Object[]{storeNumber,cbi.getOrgUnit().getId().toString()};
		RowSet rowset = DbUtil.executeQuery(ctx, sql, params);
		 
		boolean hasCodeType =  cbi.getCodeType()!=null;
		if(hasCodeType){
			sql = "update T_CON_ContractBill set  FIsArchived = 1 ,FNumber = ? ,FCodeTypeID=? where FID = ? ";
			params = new Object[]{storeNumber,cbi.getCodeType().getId().toString(),cbi.getId().toString()};
		}else{
			sql = "update T_CON_ContractBill set  FIsArchived = 1 ,FNumber = ? where FID = ? ";
			params = new Object[]{storeNumber,cbi.getId().toString()};
		}
			
		try {
			if(rowset.next()){
				if(!rowset.getString("FID").equalsIgnoreCase(cbi.getId().toString())){
					throw (new ContractException(ContractException.NUMBER_DUP));
					
				}else{
					DbUtil.execute(ctx,sql,params);
					flag = true;
				}
			}else{
			    DbUtil.execute(ctx,sql,params);
			    flag = true;
			}
				
//				编码变化，同步到合同基本资料，用于合同做核算项目
			ContractBaseDataHelper.synToContractBaseData(ctx, false, cbi.getId().toString());

		} catch (SQLException e) {		
			throw new BOSException(e);
		}
			
			//如果归档成功,该合同关联的变更签证单状态改变为已审批
			if(flag){
				sql = "update T_CON_ContractChangeBill set  FState = '4AUDITTED' where FContractBillID = ? ";
				params = new Object[]{cbi.getId().toString()};
			    DbUtil.execute(ctx,sql,params);	
			    //如果归档成功，对应付款申请单上的合同号也应该做相应的调整 by cassiel_peng 2010-01-12
			    String _sql="update T_CON_PayRequestBill set FContractNo = ? where FContractId = ? ";
			    params = new Object[]{storeNumber,cbi.getId().toString()};
			    DbUtil.execute(ctx, _sql,params);
			}
		// 如果归档成功，更新补充合同的主编码
		if (flag && ContractPropertyEnum.DIRECT.equals(cbi.getContractPropert())) {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("update t_con_contractbill set fmainContractNumber = ? where  fid in (select DISTINCT fparentid from t_con_contractbillentry where fcontent = ? )");
			builder.addParam(storeNumber);
			builder.addParam(cbi.getId().toString());
			builder.execute();
		}
	    
		return flag;
	}
	/**
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		
	    SelectorItemCollection sic = new SelectorItemCollection();
	    sic.add(new SelectorItemInfo("*"));
	    sic.add(new SelectorItemInfo("codeType.number"));
	    sic.add(new SelectorItemInfo("curProject.id"));
	    sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
	    
		//合同编码规则，必须合同编码作为应用属性
		String bindingProperty = "codeType.number";
		ContractBillInfo info = (ContractBillInfo)_getValue(ctx, pk,sic);
		CostCenterOrgUnitInfo currentCostUnit = ContextUtil.getCurrentCostUnit(ctx);
		String curOrgId = currentCostUnit.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	    if ((info).getCodeType()!=null && iCodingRuleManager.isExist(info, curOrgId,bindingProperty)
	    		&& iCodingRuleManager.isUseIntermitNumber(info, curOrgId,bindingProperty)) {
	        iCodingRuleManager.recycleNumber(info, curOrgId, bindingProperty,"",info.getNumber());
	    }else {
	    	curOrgId = (info).getCurProject().getFullOrgUnit().getId().toString();
	    	
	        if ((info).getCodeType()!=null && iCodingRuleManager.isExist(info, curOrgId,bindingProperty)
	        		&& iCodingRuleManager.isUseIntermitNumber(info, curOrgId,bindingProperty)) {
	            iCodingRuleManager.recycleNumber(info, curOrgId, bindingProperty,"",info.getNumber());
	        }
	    	
	    }
	}*/

	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		logger.info("===============================================================================");
		logger.info("ContractBillControllerBean.recycleNumber(),start");
		logger.info("===============================================================================");

		SelectorItemCollection sic = getSeletorForRecycleNumber();
		ContractBillInfo info = (ContractBillInfo) _getValue(ctx, pk, sic);
		boolean isExistCodeType = (info.getCodeType() != null);
		String currentOrgId = getOrgUnitId(ctx, info);

		logger.info("sic:" + sic);
		logger.info("info:" + info);
		logger.info("info.number:" + info.getNumber());
		logger.info("info.getCodeType():" + info.getCodeType());
		logger.info("(info.getCodeType() != null):" + isExistCodeType);

		boolean isRecycleParentOrgNumber = isRecycleParentOrgNumber();
		logger.info("isRecycleParentOrgNumber:" + isRecycleParentOrgNumber);
		ArrayList orgIdList = new ArrayList();
		// 当前组织ID放在循环列表最前面
		orgIdList.add(currentOrgId);
		if (isRecycleParentOrgNumber) {
			ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
		}
		// 清除掉集合中的重复值和Null值
		FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
		logger.info("orgIdList:" + orgIdList);
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);

		for (int i = 0, size = orgIdList.size(); i < size; i++) {
			String curOrgId = orgIdList.get(i).toString();
			logger.info("回收组织为：" + curOrgId + "的编码规则。");

			boolean isExistCodingRule = false;
			boolean isUseIntermitNumber = false;
			boolean flag = false;

			if (isExistCodeType) {
				isExistCodingRule = iCodingRuleManager.isExist(info, curOrgId, BINDING_PROPERTY);
			}
			if (isExistCodingRule) {
				isUseIntermitNumber = iCodingRuleManager.isUseIntermitNumber(info, curOrgId, BINDING_PROPERTY);
			}

			flag = isExistCodeType && isExistCodingRule && isUseIntermitNumber;
			logger.info("iCodingRuleManager.isExist(info, curOrgId, BINDING_PROPERTY):" + isExistCodingRule);
			logger.info("iCodingRuleManager.isUseIntermitNumber(info, curOrgId, BINDING_PROPERTY):" + isUseIntermitNumber);
			logger
					.info("info.getCodeType() != null && iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId):"
							+ flag);

			if (flag) {
				//如果回收成功了就跳出循环
				if (iCodingRuleManager.recycleNumber(info, curOrgId, BINDING_PROPERTY, "", info.getNumber())) {
					break;
				}
			}
		}

		logger.info("===============================================================================");
		logger.info("ContractBillControllerBean.recycleNumber(),end");
		logger.info("===============================================================================");
	}
		
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
	
		FilterInfo filter = null;
		int i=0;
		// 获取最大循环次数(用于编码规则)
		int cycleMaxIndex = getCycleMaxIndex4HandleIntermitNumber();
		
		do {
			handleIntermitNumber1(ctx, info, i);
			
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", info.getNumber()));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
			filter.getFilterItems()
					.add(new FilterItemInfo("orgUnit.id", info.getOrgUnit().getId()));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		} while (_exists(ctx, filter) && i < cycleMaxIndex);
	}

	/**
	 * 获取最大循环次数(用于编码规则)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#getCycleMaxIndex4HandleIntermitNumber()
	 */
	protected int getCycleMaxIndex4HandleIntermitNumber() {
		return super.getCycleMaxIndex4HandleIntermitNumber();
	}

	/**
	 * 描述：取得合同编码类型对象集合（精确，模糊，递归）
	 * <p>
	 * 1、首先查询“合同类型”，“合同性质”都是指定值的情况 <br>
	 * 2、再查询“合同类型”为指定值，“合同性质”为“OLD(全部)”的情况 <br>
	 * 3、再查询父类“合同类型”为指定值的情况 <br>
	 * 4、再查询“合同类型”为“NULL(全部)”，“合同性质”为指定值的情况 <br>
	 * 5、再查询“合同类型”为“NULL(全部)”，“合同性质”为“OLD(全部)”的情况 <br>
	 * <p>
	 * 6.1、“合同类型”要支持递归。例如：首先要找当前级，如果没有找到，则要能找到上级，如果还没有找到，则要找上上级，依次类推 <br>
	 * 6.2、递归查找“合同类型”上级（“合同类型”是树形结构，通常只有3-4级，所以不会产生性能问题） <br>
	 * 
	 * @param ctx
	 *            应用上下文；非空
	 * @param contractType
	 *            合同类型；可空
	 * @param contractProperty
	 *            合同性质；非空
	 * @param thirdType
	 *            单据状态（新增或归档）；非空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-20
	 * @see com.kingdee.eas.fdc.contract.app.AbstractContractBillControllerBean#_getContractCodingType(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectValue, java.lang.String, java.lang.String)
	 */
	protected IObjectCollection _getContractCodingType(Context ctx, IObjectValue contractType, String contractProperty,
			String thirdType) throws BOSException, EASBizException {
		ContractTypeInfo contractTypeInfo = (ContractTypeInfo) contractType;

		// 取得合同编码类型对象集合（精确，模糊，递归）
		ContractCodingTypeCollection codingTypeCollection = ContractCodingUtil.getContractCodingTypeCollection(ctx,
				contractTypeInfo, contractProperty, thirdType);

		return codingTypeCollection;
	}

	/**
	 * 描述：编码规则获取要支持组织递归。例如：首先要找当前组织，如果没有找到，则要能找到上级组织，如果还没有找到，则要找上上级组织，依次类推
	 * 
	 * @param ctx
	 * @param info
	 * @param count
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-20
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#handleIntermitNumber1(com.kingdee.bos.Context,
	 *      com.kingdee.eas.fdc.basedata.FDCBillInfo, int)
	 */
	protected void handleIntermitNumber1(Context ctx, FDCBillInfo info, int count) throws BOSException,
			CodingRuleException, EASBizException {
		ContractBillInfo contractBillInfo = (ContractBillInfo) info;
		
		// 是否已归档
		if (!contractBillInfo.isIsArchived()) {
			ContractTypeInfo contractType = contractBillInfo.getContractType();
			ContractPropertyEnum contractPropertyEnum = contractBillInfo.getContractPropert();
			ContractThirdTypeEnum contractThirdTypeEnum = ContractThirdTypeEnum.NEW;
			// 获取合同编码类型对象集合（精确，模糊，递归）
			ContractCodingTypeCollection codingTypeList = (ContractCodingTypeCollection) _getContractCodingType(ctx,
					contractType, contractPropertyEnum.getValue(), contractThirdTypeEnum.getValue());

			if (FdcObjectCollectionUtil.isNotEmpty(codingTypeList)) {
				String currentOrgId = getOrgUnitId(ctx, info);

				boolean isRecycleParentOrgNumber = isRecycleParentOrgNumber();
				logger.info("isRecycleParentOrgNumber:" + isRecycleParentOrgNumber);
				ArrayList orgIdList = new ArrayList();
				// 当前组织ID放在循环列表最前面
				orgIdList.add(currentOrgId);
				if (isRecycleParentOrgNumber) {
					ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
				}
				// 清除掉集合中的重复值和Null值
				FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
				logger.info("orgIdList:" + orgIdList);

				// 第一个合同编码类型（最接近，最匹配）
				ContractCodingTypeInfo firstCodingTypeInfo = codingTypeList.get(0);

				ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
				for (int i = 0, size = codingTypeList.size(); i < size; i++) {
					ContractCodingTypeInfo tempCodingTypeInfo = codingTypeList.get(i);
					// 设置合同编码类型（临时设置，用于后面获取编码规则使用）
					contractBillInfo.setCodeType(tempCodingTypeInfo);

					// 编码规则获取要支持组织递归。例如：首先要找当前组织，如果没有找到，则要能找到上级组织，如果还没有找到，则要找上上级组织，依次类推
					for (int j = 0; j < orgIdList.size(); j++) {
						if (setNumber(ctx, contractBillInfo, orgIdList.get(j).toString(), BINDING_PROPERTY,
								iCodingRuleManager, count)) {
							// 如果成功匹配到编码规则，则终止循环，直接返回（合同编码类型为最后一次设置的临时值）
							return;
						}
					}
				}

				// 如果前面循环中始终没有匹配到编码规则，则重新设置合同编码类型（第一个合同编码类型（最接近，最匹配））
				contractBillInfo.setCodeType(firstCodingTypeInfo);
			}
		}
	}

	
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
		ContractBillInfo info = (ContractBillInfo) model;
		setCreatorPosition(ctx, (ContractBillInfo) model);
		
		IObjectPK objectPK = super._addnew(ctx, model);
		
		// 处理编码类型字段，防止回收编码时出现异常
		dealWithCodeType(ctx, info, true);
		
//		同步到合同基本资料，用于合同做核算项目
		ContractBaseDataHelper.synToContractBaseData(ctx, false, objectPK.toString());
		
    	/**添加反写ContractBaseDataID的代码 -by neo**/
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(
				"update t_con_contractbill set fcontractbasedataid =" +
				"(select fid from t_CON_contractbasedata where fcontractid = t_con_contractbill.fid) " +
				"where");
		builder.appendParam("fid", objectPK.toString());
		builder.executeUpdate(ctx);
		
		/**
		 * 导入合同付款计划
		 */
//		if(ContractPropertyEnum.DIRECT.equals(info.getContractPropert())){
//			ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(objectPK.toString(),false);
//		}else if(ContractPropertyEnum.SUPPLY.equals(info.getContractPropert())){
//			if(info.isIsAmtWithoutCost()){
//				ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(objectPK.toString(),false);
//			}else{
//				ContractBillEntryCollection entrys = info.getEntrys();
//				for(int i=0;i<entrys.size();i++){
//					ContractBillEntryInfo contractBillEntryInfo = entrys.get(i);
//					if("nu".equals(contractBillEntryInfo.getRowKey())){
//						ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(contractBillEntryInfo.getContent(),false);
//						break;
//					}
//				}
//			}
//		}
		return objectPK;
		/**添加反写ContractBaseDataID的代码**/
	}

	protected void _updatePartial(Context ctx, IObjectValue model, SelectorItemCollection selector) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		super._updatePartial(ctx, model, selector);
		
//		同步到合同基本资料，用于合同做核算项目
		String id = ((FDCBillInfo)model).getId().toString();
		ContractBaseDataHelper.synToContractBaseData(ctx, false, id);

	}
	
	/**
	 * 检查合同是否关联框架合约
	 * 
	 * @return
	 * @throws Exception
	 */
	private String checkReaPre(Context ctx, IObjectPK pk) throws Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		builder.clear();
		builder.appendSql("select fprogrammingContract from T_CON_CONTRACTBILL where 1=1 and ");
		builder.appendParam("fid", pk.toString());
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getString("fprogrammingContract") != null) {
				return rowSet.getString("fprogrammingContract").toString();
			}
		}
		return null;
	}

	/**
	 * 更新规划合约"是否被引用"字段
	 * 
	 * @param proContId
	 * @param isCiting
	 */
	private void updateProgrammingContract(Context ctx, String proContId, int isCiting) throws BOSException {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " ");
		buildSQL.appendSql("where FID = '" + proContId + "' ");
			buildSQL.executeUpdate();
	}

	/**
	 * 更新老框架合约是否被引用
	 * 
	 * @throws Exception
	 * @throws Exception
	 */
	private void updateOldProg(Context ctx, IObjectPK pk) throws Exception {
		String checkReaPre = checkReaPre(ctx, pk);
		while (checkReaPre != null) {
			int count = 0;// 关联合约数
			count = isCitingByProg(ctx, checkReaPre);
			boolean isCiting = preVersionProg(ctx, checkReaPre);
			if (count <= 1 && !isCiting) {
				updateProgrammingContract(ctx, checkReaPre, 0);
			}
			checkReaPre = isUpdateNextProgState(ctx, checkReaPre);
		}
	}

	/**
	 * 找出所关联的框架合约的记录数(无文本已经废除，不再查找)
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByProg(Context ctx, String proContId) throws BOSException {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql(" select count(1) count from T_INV_InviteProject ");
		buildSQL.appendSql(" where FProgrammingContractId = '" + proContId + "' ");
		buildSQL.appendSql(" union ");
		buildSQL.appendSql(" select count(1) count from T_CON_ContractBill ");
		buildSQL.appendSql(" where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			while (iRowSet.next()) {
				count += iRowSet.getInt("count");
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new BOSException(e);
		}
		return count;
	}

	private String isUpdateNextProgState(Context ctx, String progId) throws Exception {
		String flag = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select fid from t_con_programmingContract where ");
		builder.appendParam("fSrcId", progId);
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			flag = rowSet.getString("fid").toString();
		}
		return flag;
	}

	private boolean preVersionProg(Context ctx, String progId) throws BOSException, SQLException {
		boolean isCityingProg = false;
		int tempIsCiting = 0;
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql(" select t1.FIsCiting isCiting from t_con_programmingContract t1 where t1.fid = (");
		buildSQL.appendSql(" select t2.FSrcId from t_con_programmingContract t2 where t2.fid = '" + progId + "')");
		IRowSet rowSet = buildSQL.executeQuery();
		while (rowSet.next()) {
			tempIsCiting = rowSet.getInt("isCiting");
		}
		if (tempIsCiting > 0) {
			isCityingProg = true;
		}
		return isCityingProg;
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {

		//检查是否被变更签证申请引用
		this._isReferenced(ctx,pk);
		
		//关联生成合同状态变化及时更新中标通知书和招标预拆分
		ContractBillAcceptanceLetterHelper.acceptanceLetterDeleteHelper(ctx, pk);
		try {
			updateOldProg(ctx,pk);
		} catch (Exception e) {
			logger.error(e);
			throw new BOSException(e);
		}
		super._delete(ctx, pk);		
		
/*		//同步删除合同基本资料
		String sql = "delete t_con_contractbasedata where fcontractid = ?";
		
		DbUtil.execute(ctx, sql, new Object[]{pk.toString()});*/
		//ormap 删除的时候会检查引用关系
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("contractId", pk.toString());
		ContractBaseDataFactory.getLocalInstance(ctx).delete(filter);
	}

	
	
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		if(arrayPK != null && arrayPK.length > 0) {
			Set idSet=new HashSet(arrayPK.length);
			for (int i = 0; i < arrayPK.length; i++) {
				idSet.add(arrayPK[i].toString());
				
				delete(ctx, arrayPK[i]);
			}
			
/*			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("delete t_con_contractbasedata where ");
			builder.appendParam("fcontractid", ids);
			
			builder.executeUpdate(ctx);*/
			
			//ormap 删除的时候会检查引用关系
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId",idSet,CompareType.INCLUDE));
			ContractBaseDataFactory.getLocalInstance(ctx).delete(filter);
		}
	}

	//选择不计成本的金额为否,金额录在分录上,显示的时候要从分录上取
	protected Map _getAmtByAmtWithoutCost(Context ctx, Map idMap) throws BOSException, EASBizException {

		if(idMap==null){
			return null;
		}
		
		Map amountMap = new HashMap();
		String sql = "select FContent from T_CON_ContractBillEntry where fparentid = ? and FRowkey = ?";
		
		Set set = idMap.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String id = (String)it.next();
			

			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql(sql);
			builder.addParams(new Object[] { id,  "am" });
			ResultSet resultSet = builder.executeQuery();
			try {
				if (resultSet.next()) {
					String cont = resultSet.getString("FContent");
					if (cont != null && cont.length() > 0) {
						BigDecimal amt = new BigDecimal(cont);
						amountMap.put(id,amt)	;		
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		
		
		return amountMap;
	}
	
	//单据序时簿编辑界面初始数据粗粒度方法
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		Map initMap = super._fetchInitData(ctx,paramMap);
		return initMap;
	}
	
	//初始化工程项目
	protected void  initProject(Context ctx, Map paramMap,Map initMap) throws EASBizException, BOSException{
		String projectId = (String) paramMap.get("projectId");
		CurProjectInfo curProjectInfo = null;
		if(projectId == null && paramMap.get("contractBillId")!=null) {
			//合同单据
			String contractBillId = (String)paramMap.get("contractBillId");
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("curProject.name");
			selector.add("curProject.number");
			selector.add("curProject.longNumber");
			selector.add("curProject.codingNumber");
			selector.add("curProject.displayName");
			selector.add("curProject.parent.id");
			selector.add("curProject.parent.number");
			selector.add("curProject.parent.name");
			selector.add("curProject.fullOrgUnit.name");
			selector.add("curProject.fullOrgUnit.code");
			selector.add("curProject.CU.name");
			selector.add("curProject.CU.number");
			selector.add("curProject.CU.code");
			selector.add("curProject.landDeveloper.number");
			selector.add("curProject.landDeveloper.name");
			selector.add("curProject.costCenter.id");	// modified by zhaoqin for R130924-0167 on 2013/10/10
			selector.add("curProject.costCenter.number");
			selector.add("curProject.costCenter.name");
			selector.add("curProject.costCenter.code");
			selector.add("curProject.costCenter.displayName");
			selector.add("curProject.costCenter.longNumber");
			selector.add("respDept.number");
			selector.add("respDept.name");
			selector.add("partB.number");
			selector.add("partB.name");
			
			ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
			getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
			initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);

			//工程项目
			projectId = (String) contractBill.getCurProject().getId().toString();
			
		}
		if(projectId != null) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("landDeveloper.number");
			selector.add("landDeveloper.name");
			selector.add("name");
			selector.add("number");
			selector.add("longNumber");
			selector.add("codingNumber");
			selector.add("displayName");
			selector.add("parent.id");
			selector.add("fullOrgUnit.name");
			selector.add("costCenter.id");	// modified by zhaoqin for R130924-0167 on 2013/10/10
			selector.add("costCenter.number");
			selector.add("costCenter.name");
			selector.add("costCenter.displayName");
			selector.add("costCenter.longNumber");
			selector.add("CU.name");
			selector.add("CU.number");
			selector.add("CU.code");
			curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId),selector);
		}
		initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
		
//		super.initProject(ctx, paramMap,initMap);
	}

	protected boolean _contractBillAntiStore(Context ctx, List idList) throws BOSException, EASBizException {
	
		String sql = "update T_CON_ContractBill set  FIsArchived = 0 where FID = ? ";
		Object[] params = null;
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			params = new Object[]{id};
			
			DbUtil.execute(ctx,sql,params);
			    
		}
		
		return true;
	}

	protected boolean _isContractSplit(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("splitState"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
		ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,pk,sic);
		
		boolean splitBeforeAudit = FDCUtils.getBooleanValue4FDCParamByKey(ctx, contractBillInfo.getOrgUnit().getId().toString(),
				FDCConstants.FDC_PARAM_SPLITBFAUDIT);
		if (!splitBeforeAudit) {
			return true;
		}
		
		return CostSplitStateEnum.ALLSPLIT.equals(contractBillInfo.getSplitState());
	}

	//工作流拆分驱动
	protected void _split(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
	}

	//自动删除拆分
	protected boolean _autoDelSplit(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		//审核的合同不能自动删除拆分
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("state"));
		ContractBillInfo contractBillInfo = 
			ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk,sic);
		if(FDCBillStateEnum.AUDITTED.equals(contractBillInfo.getState())){
			//throw new ContractException(ContractException.HASAUDIT);
			return false;
		}
		
		//成本拆分
		CostSplitBillAutoAuditor.autoDelete(ctx,pk, "T_CON_ContractCostSplit", "FContractBillID");
		//财务拆分
		CostSplitBillAutoAuditor.autoDelete(ctx,pk, "T_CON_ConNoCostSplit", "FContractBillID");
		String contractid=pk.toString();
		com.kingdee.eas.fdc.basedata.FDCSQLBuilder builder=new com.kingdee.eas.fdc.basedata.FDCSQLBuilder(ctx);
/*		builder.appendSql("delete from T_Con_ContractCostSplitEntry where fparentid in (select fid from T_Con_ContractCostSplit where fcontractbillid=?)");
		builder.addParam(contractid);
		builder.execute();
		builder.clear();
		builder.appendSql("delete from T_Con_ContractCostSplit where fcontractbillid=?)");
		builder.addParam(contractid);
		builder.execute();
		builder.clear();
		builder.appendSql("delete from T_CON_ConNoCostSplitEntry where fparentid in (select fid from T_CON_ConNoCostSplit where fcontractbillid=?)");
		builder.addParam(contractid);
		builder.execute();
		builder.clear();
		builder.appendSql("delete from T_CON_ConNoCostSplit where fcontractbillid=?)");
		builder.addParam(contractid);
		builder.execute();
		builder.clear();*/
		builder.appendSql("update T_Con_ContractBill set fsplitstate='1NOSPLIT' where fid=?");
		builder.addParam(contractid);
		builder.execute();
		return true;
	}

	//获取合同类型编码
	protected String _getContractTypeNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		//
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("contractType.number"));
        sic.add(new SelectorItemInfo("contractType.longNumber"));
		ContractBillInfo contractBillInfo = 
			ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk,sic);
		
		return contractBillInfo.getContractType().getLongNumber().replace('!','.');
	}
	
	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.contract.app.AbstractContractBillControllerBean#_getOtherInfo(com.kingdee.bos.Context, java.util.Set)
	 */
	protected Map _getOtherInfo(Context ctx, Set contractIds)
			throws BOSException, EASBizException {
		if(contractIds==null||contractIds.size()==0){
			return new HashMap();
		}
		TimeTools.getInstance().msValuePrintln("start");
		Map auditMap = FDCBillWFFacadeFactory.getLocalInstance(ctx).getWFBillLastAuditorAndTime(contractIds);
		TimeTools.getInstance().msValuePrintln("end auditMap");

		/**
		 * 填充正文列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
		 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
		 * 如果该合同有正文附件就进行标记，否则不标记
		 */
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcontractid from t_con_contractcontent where ");
		builder.appendParam("fcontractid", contractIds.toArray());
		IRowSet rowSet=builder.executeQuery();
		Map contentMap=new HashMap();
		Map attachMap=new HashMap();
		try {
			while (rowSet.next()) {
				contentMap.put(rowSet.getString("fcontractid"), Boolean.TRUE);
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		/**
		 * 由于肖飙彪在保存正文的时候想要整个房地产系统各的业务单据都可用便没有直接使用之前系统已有的contract这个字段为正文的业务单据父体而是新加了
		 * 字段parent，所以保存的时候依据不同，之后查看修改的时候可能就会产生很多问题。对于ID不同步的问题我是在启用或者禁用参数"房地产业务系统正文管理启用审批笔迹留痕功能"
		 * 解决的，也就是说如果没有手动启用或者是禁用参数的话我们的ID还是存在不同步的问题，而最直接的影响就是在合同录入序时簿和合同查询序时簿上的"正文"一列的信息显示不准确
		 * 由于目前咱们这个房地产系统就只有合同模块用了该功能，所以FParent其实就是合同的ID(FContractId)，那解决该问题就只以FParent为依据再找一遍咯！烦。。 by Cassiel_peng 2009-9-6
		 */
		builder.clear();
		builder.appendSql("select FParent from t_con_contractcontent where ");
		builder.appendParam("FParent", contractIds.toArray());
		IRowSet rowSet1=builder.executeQuery();
		try {
			while (rowSet1.next()) {
				contentMap.put(rowSet1.getString("FParent"), Boolean.TRUE);
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
					
		TimeTools.getInstance().msValuePrintln("end contentMap");
		/**
		 * 填充附件列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
		 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
		 * 如果该合同有业务相关附件就进行标记，否则不标记
		 */
		builder.clear();
		builder.appendSql("select fboid from t_bas_boattchasso where ");
		builder.appendParam("fboid", contractIds.toArray());
		rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				attachMap.put(rowSet.getString("fboid"),Boolean.TRUE);
			}	
		}catch(SQLException e){
			throw new BOSException(e);
		}
		TimeTools.getInstance().msValuePrintln("end attachMap");
		Map retMap=new HashMap();
		retMap.put("contentMap", contentMap);
		retMap.put("attachMap", attachMap);
		retMap.put("auditMap", auditMap);
		return retMap;
	}
	
	// 仅仅在同一个明细“工程项目”下才做合同名称唯一性检查，跨项目分期、登录跨组织的合同都不用检查合同名称唯一性。
	// R101129-229 Added by Owen_wen
	//修改前：由于设定了工程项目与成本中心对应关系，合同单据billInfo会通过getOrgUnit()归到某个组织下，该组织下的合同单据是不允许重名的
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {
		ContractBillInfo contractBill = (ContractBillInfo) billInfo;
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("name", contractBill.getName()));
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", contractBill.getCurProject().getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", contractBill.getId().toString(), CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NAME_DUP);
		}
	}

	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		String conId = pk.toString();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		ContractBillInfo billInfo = ContractBillFactory.getLocalInstance(ctx).
		getContractBillInfo(pk,selector);
		
		//如果存在补充合同，也作废
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	    builder.appendSql("select sup.fid from t_con_contractbill sup ");
	    builder.appendSql("inner join t_con_contractbillentry supe on supe.fparentid=sup.fid ");
	    builder.appendSql("inner join t_con_contractbill main on main.fid=supe.fcontent ");
	    builder.appendSql("where main.fid=?");
	    builder.addParam(conId);
	    IRowSet rs = builder.executeQuery();
	    try{
	    	while(rs.next()&&rs.size()==1){
	    		String supId = rs.getString("fid");
	    		
	    		ContractBillInfo supInfo= ContractBillFactory.getLocalInstance(ctx)
						.getContractBillInfo(new ObjectUuidPK(BOSUuid.read(supId)), selector);
	    		if(supInfo.getState()==FDCBillStateEnum.SUBMITTED || supInfo.getState()==FDCBillStateEnum.AUDITTING){
		    		// 清空成本拆分
		    		FilterInfo filter = new FilterInfo();
		    		filter.appendFilterItem("contractBill.id", supId);
		    		// 删除付款拆分
		    		PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
		    		// 删除结算拆分
		    		SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
		    		// 删除变更拆分
		    		ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);
		    		// 删除合同拆分
		    		ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
	    		}
	    		//合同终止操作目前暂时因建发R120114-0032提单改成终止状态
				//基类由于被售楼已使用广泛，目前不做改动。 added by andy_liu 2012-5-8
				//super._cancel(ctx,  new ObjectUuidPK(BOSUuid.read(supId)));
				setBillStatus(ctx, BOSUuid.read(pk.toString()), FDCBillStateEnum.CANCEL);
	    		
	    	}
	    }catch(Exception e){
	    	throw new BOSException(e);
	    }

	    if(billInfo.getState()==FDCBillStateEnum.SUBMITTED || billInfo.getState()==FDCBillStateEnum.AUDITTING){
    		// 清空成本拆分
    		FilterInfo filter = new FilterInfo();
    		filter.appendFilterItem("contractBill.id", conId);
    		// 删除付款拆分
    		PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
    		// 删除结算拆分
    		SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
    		// 删除变更拆分
    		ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);
    		// 删除合同拆分
    		ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
		}
		
		//合同终止操作目前暂时因建发R120114-0032提单改成终止状态
		//基类由于被售楼已使用广泛，目前不做改动。 added by andy_liu 2012-5-8
		setBillStatus(ctx, BOSUuid.read(pk.toString()), FDCBillStateEnum.CANCEL);
		//super._cancel(ctx, pk);
	}

	private void setBillStatus(Context ctx, BOSUuid billId, FDCBillStateEnum state) throws BOSException, EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		billInfo.setState(state);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");

		_updatePartial(ctx, billInfo, selector);
	}

	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
    //取编码，编码规则必须设置绑定属性 
	protected String getBindingProperty() {
		return BINDING_PROPERTY;
	}

	/**
	 * 描述：属性字段参与编码规则，如果值发生改变，则需要重新获取编码
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-10-10
	 */
	protected boolean isNeedReHandleIntermitNumberByProperty() {
		return true;
	}

	/**
	 * 描述：是否回收上级组织传递的编码规则
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-10-10
	 */
	protected boolean isRecycleParentOrgNumber() {
		return true;
	}

	/**
	 * 描述：取得回收编码Seletor
	 * @return
	 * @author：skyiter_wang
	 * @createDate：2013-10-16
	 */
	protected SelectorItemCollection getSeletorForRecycleNumber() {
		SelectorItemCollection sic = super.getSeletorForRecycleNumber();

		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("codeType.*"));
		sic.add(new SelectorItemInfo("curProject.*"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.*"));

		FdcObjectCollectionUtil.clearDuplicate(sic);

		return sic;
	}

	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	/**
	 * 描述: 在合同<<审批~之前>> 检查关联的框架合约是否为最新版本，如不是，则更新为最新版本
	 * 
	 * 情形：合同从提交到审批之间隔了好长时间，期间合约规划作了新版本，那就会出现问题
	 * 
	 * @param ctx
	 * @param billId
	 */
	public void checkContractProgramming(Context ctx, String billId) throws BOSException {
		if(StringUtils.isEmpty(billId))
			return;
		
		FDCSQLBuilder builder = null;
		if(null == ctx) {
			builder = new FDCSQLBuilder();
		} else {
			builder = new FDCSQLBuilder(ctx);
		}
		
		builder.appendSql("select FProgrammingContract from T_CON_ContractBill where FProgrammingContract is not null and fid = ?");
	    builder.addParam(billId);
	    IRowSet rs = builder.executeQuery();
	    String conProg = null;
	    boolean isNeedUpdate = false;
	    try {
	    	// 合同是否关联了规划合约
			if(rs.next()) {
				conProg = rs.getString("FProgrammingContract");
				builder.clear();
				builder.appendSql("select pc1.fid from T_CON_ProgrammingContract pc1 join T_CON_Programming p1 on p1.fid = pc1.fprogrammingid ");
				builder.appendSql("join (select p.FVersionGroup,pc.flongnumber from T_CON_ProgrammingContract pc join T_CON_Programming p ");
				builder.appendSql(" on p.fid = pc.fprogrammingid where pc.fid = ? ) t on (t.FVersionGroup = p1.FVersionGroup and t.flongnumber = pc1.flongnumber) ");
				builder.appendSql("where p1.FIsLatest = 1 and p1.FState = '4AUDITTED'");
				builder.addParam(conProg);
				rs = builder.executeQuery();
				// 判断合同关联框架合约是否为最新版本
				if(rs.next()){
					if(!conProg.equals(rs.getString("fid"))) {
						conProg = rs.getString("fid");
						isNeedUpdate = true;
					}
				} else {
					conProg = null;
					isNeedUpdate = true;
				}
				//如果不是最新版本，则更新为最新版本的规划合约
				if(isNeedUpdate) {
					builder.clear();
					if(null == conProg) {	//在新的版本中，之前关联的框架合约被删掉了
						builder.appendSql("update T_CON_ContractBill set FProgrammingContract = null where fid = ?");
						builder.addParam(billId);
					} else {
						builder.appendSql("update T_CON_ContractBill set FProgrammingContract = ? where fid = ?");
						builder.addParam(conProg);
						builder.addParam(billId);
					}
					if(null == ctx) {
						builder.executeUpdate();
					} else {
						builder.executeUpdate(ctx);
					}
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
	}
}