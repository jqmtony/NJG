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
 * ����:��ͬ����
 * @author liupd  date:2006-10-13 <p>
 * @version EAS5.1.3
 */
public class ContractBillControllerBean extends
		AbstractContractBillControllerBean {
	
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillControllerBean");

	/**
	 * ��ͬ�������ı�ʶ����
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
    	
    	// ��Ϊ����BOTP�������ɺ�ͬʱ���ڵ���_saveǰ������id������_exists�жϿ����Ƿ��Ѿ��������ݡ�Added by Owen_wen 2012-06-14
		if (info.getId() != null && this._exists(ctx, new ObjectUuidPK(info.getId()))) {
    		ContractBillInfo oldInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(info.getId()));
			if (!oldInfo.getState().equals(FDCBillStateEnum.SAVED)) {
				if (!FDCUtils.isRunningWorkflow(ctx, info.getId().toString())) {
					if (!FDCBillStateEnum.SUBMITTED.equals(oldInfo.getState()) && !FDCBillStateEnum.BACK.equals(oldInfo.getState())
							&& !FDCBillStateEnum.INVALID.equals(oldInfo.getState())) {
						throw new ContractException(ContractException.WITHMSG, new Object[] { "\n��ǰ��������״̬Ϊ" + oldInfo.getState()
								+ ",�����Ǳ� �����û������ģ����ܱ��棡" });
					}
				}
			}
    		info.setState(oldInfo.getState());
    	}
    	info.setCodingNumber(info.getNumber());
    	
    	/**
		 * ��������˲�����Ϻţ�������ȡ���Ժ����������ظ�������<br>
		 * �ϺŲ���ع��������ٴ��ύʱ���ͶϺ��ˡ�<br>
		 * �����ڱ���ǰ��У��Name <br>
		 * add by emanon
		 */
		checkNameDup(ctx, info);
		
		IObjectPK pk = super._save(ctx, info);

		// ������������ֶΣ���ֹ���ձ���ʱ�����쳣
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
				String msg = "����״̬�Ѿ�������л�������ˣ��������ύ";
				throw new ContractException(ContractException.WITHMSG,new Object[]{msg});
			}
		}
		if(contractBillInfo.getBail()!=null){
			ContractBailFactory.getLocalInstance(ctx).submit(contractBillInfo.getBail());
		}
		contractBillInfo.setCodingNumber(contractBillInfo.getNumber());

		/**
		 * ��������˲�����Ϻţ�������ȡ���Ժ����������ظ�������<br>
		 * �ϺŲ���ع��������ٴ��ύʱ���ͶϺ��ˡ�<br>
		 * �����ڱ���ǰ��У��Name <br>
		 * add by emanon
		 */ 
		//checkNameDup(ctx, contractBillInfo);
		
		
		// modified by zhaoqin for ��Ŀ�ʽ�ƻ� on 2013/08/14 start
		// return super._submit(ctx, contractBillInfo);
		IObjectPK pk = super._submit(ctx, contractBillInfo);

		// ������������ֶΣ���ֹ���ձ���ʱ�����쳣
		dealWithCodeType(ctx, contractBillInfo, true);

		String costCenterId = contractBillInfo.getOrgUnit().getId().toString();//��ͬ�϶����ڳɱ�����������
		// ��ͬ�������ǰ��Ҫ��ȫ���
		boolean splitBeforeAudit = FDCUtils.getBooleanValue4FDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_SPLITBFAUDIT);
		// �Ƿ��Զ����
		//boolean isAutoSplit = FDCUtils.getBooleanValue4FDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_ISAUTOSPLIT);
		/**
		 * �Ƿ��Զ����:
		 *  0: ��ͬ�Զ����
		 *  1: ȫ���Զ����
		 *  2: ���Զ����
		 */
		String autoSplit = FDCUtils.getFDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_ISAUTOSPLIT);
		if (splitBeforeAudit && null != autoSplit && ("0".equals(autoSplit) || "1".equals(autoSplit)))
			autoSplit(ctx, contractBillInfo);
		
		return pk;
		// modified by zhaoqin for ��Ŀ�ʽ�ƻ� on 2013/08/14 end
	}

	/**
	 * ��Ŀ�ʽ�ƻ����Զ���� - ��ͬ����Զ����ݹ����ĺ�Լ�滮�Ŀ�Ŀ�������Զ����
	 * 	
	 * @param contractBillInfo
	 * @Author��zhaoqin
	 * @CreateTime��2013-8-14
	 */
	private void autoSplit(Context ctx, ContractBillInfo contractBillInfo) throws BOSException, EASBizException {
		String contractId = contractBillInfo.getId().toString();
		// '��ͬ���'���ݹ����ĺ�Լ�滮�Ŀ�Ŀ�������Զ�����
		if (contractBillInfo.isIsCoseSplit()) { // �ɱ����
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			IContractCostSplit iconCostSp = ContractCostSplitFactory.getLocalInstance(ctx);
			if (!iconCostSp.exists(filter)) { // �Ƿ��Ѿ����
				ContractCostSplitInfo objectValue = new ContractCostSplitInfo();
				objectValue.setContractBill(contractBillInfo);
				objectValue.setCreator(ContextUtil.getCurrentUserInfo(ctx));
				objectValue.setCurProject(contractBillInfo.getCurProject());
				objectValue.setIsConfirm(true);
				objectValue.setIsInvalid(false);
				objectValue.setState(FDCBillStateEnum.SAVED);
				objectValue.setOrgUnit(contractBillInfo.getOrgUnit());
				objectValue.setCU(contractBillInfo.getCU());
				
				// �Ƿ�����˿�ܺ�Լ
				String progConId = iconCostSp.checkIsExistProg(contractId); // return T_CON_ProgrammingContract.FID
				// or check 'contractBillInfo.getProgrammingContract()' is not null
				if (!StringUtils.isEmpty(progConId)) {
					importProgramForContract(ctx, contractBillInfo, progConId, objectValue);
				}
				iconCostSp.addnew(objectValue);
				// ���º�ͬ�Ĳ��״̬
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("splitState");
				_updatePartial(ctx, contractBillInfo, selector);
			}
		}
	}

	/**
	 * ���������ݹ����ĺ�Լ�滮�Ŀ�Ŀ�������Զ����
	 * �ο�: ContractCostSplitEditUI.actionProgrAcctSelect_actionPerformed
	 * @param ctx
	 * @param contractBillInfo
	 * @param ProConId ��ܺ�ԼID
	 * @Author��zhaoqin
	 * @CreateTime��2013-8-15
	 */
	private void importProgramForContract(Context ctx, ContractBillInfo contractBillInfo, String progConId,
			ContractCostSplitInfo objectValue)
			throws BOSException, EASBizException {
		BigDecimal amount = contractBillInfo.getAmount(); // ��ͬ���
		// ��ͬ����Ƿ�Ϊ0
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
		
		//�ܵ��ѷ�����(��Լ�滮�Ĺ滮���)
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
		
		int index = 0; // ��¼���
		BigDecimal tempAll = FDCHelper.ZERO;
		BigDecimal splitAmount = FDCHelper.ZERO; // �Ѳ�ֽ��
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
			// ����Լ�������Ƿ�Ϊ0
			if (progConCost.getContractAssign().compareTo(FDCHelper.ZERO) == 0) {
				entry.setSplitScale(FDCHelper.ZERO);
				entry.setAmount(FDCHelper.ZERO);
				continue;
			}
			if (index == costEntries.size()) { // ���һ��
				entry.setSplitScale(FDCHelper.subtract(FDCHelper.ONE_HUNDRED, tempAll));
				entry.setAmount(FDCHelper.divide(amount.multiply(entry.getSplitScale()), FDCHelper.ONE_HUNDRED, 10, BigDecimal.ROUND_HALF_DOWN));
			} else { 
				entry.setSplitScale(FDCHelper.divide(progConCost.getContractAssign(), allAssigned, 10, BigDecimal.ROUND_HALF_DOWN));
				entry.setAmount(amount.multiply(entry.getSplitScale())); // ��ͬ���.�������
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
	 * �����Ƶ����Ƶ�ʱ��ְλ��Ϣ �����ᵥR101021-327
	 * 
	 * �Ƶ��˶�Ӧ�����û�ID �û�(t_pm_user)�����ҵ���Ӧ��ְԱ��t_bd_person��
	 * ְԱ�����ҵ���Ӧ��ְԱְҵ��Ϣ��T_HR_PersonPosition�������ű�����û�����Ҫְλ
	 * ְҵ��Ϣ��T_HR_PersonPosition�� ��Ҫְλ�����ҵ� t_org_position(ְλ)
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
		
		// ����Ƶ���û�а�ְλ��Ϣ��ֱ�ӷ��� Added By Owen_wen 2011-07-28
		if (StringUtils.isEmpty(positionId))
			return;
		
		PositionInfo positionInfo = new PositionInfo();
		positionInfo.setId(BOSUuid.read(positionId));
		contractBillInfo.setCreatorPosition(positionInfo);
	}

    
    private SelectorItemCollection getSic(){
		// �˹���Ϊ��ϸ��Ϣ����
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
		// �˹���Ϊ��ϸ��Ϣ����
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("suppEntry.*"));

		return sic;
	}
    
	//�ύʱУ�鵥���ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ
	protected void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		
		//�������ڵ�ǰ�ɱ��ڼ�֮ǰ
		ContractBillInfo contractBill = (ContractBillInfo)model;
		//        if(model==null || contractBill.getCurProject()==null ||contractBill.getCurProject().getFullOrgUnit()==null){
		//        	model= this.getContractBillInfo(ctx,new ObjectUuidPK(contractBill.toString()),getSic());
		//        }
		//�Ƿ����ò���һ�廯
		String comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		//��Ϊ�½�ֻ����˳ɱ���ģ����Դ˴������Ʋ������
		if(isInCore&&contractBill.isIsCoseSplit()){
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractBill.getCurProject().getId().toString(),true);
			if(contractBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				//�����ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
	}
	
	//
	protected void checkBillForAudit(Context ctx, BOSUuid billId)throws BOSException, EASBizException {

        ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
		//��鹦���Ƿ��Ѿ�������ʼ��
		String comId = contractBillInfo.getCurProject().getFullOrgUnit().getId().toString();
			
		String costCenterId = contractBillInfo.getOrgUnit().getId().toString();//��ͬ�϶����ڳɱ�����������
		boolean splitBeforeAudit = FDCUtils.getBooleanValue4FDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_SPLITBFAUDIT);
		//�ú�ͬ�Ѿ���û�в�֣��������
		if(splitBeforeAudit && contractBillInfo.isIsCoseSplit() &&  
				!contractBillInfo.isIsAmtWithoutCost() && !CostSplitStateEnum.ALLSPLIT.equals(contractBillInfo.getSplitState())){
//			MsgBox.showWarning(this, "�ú�ͬ�Ѿ������˲�֣����ܽ����޸�");
//			SysUtil.abort();
			throw new ContractException(ContractException.NOSPLITFORAUDIT);
		}
		
		//�Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = contractBillInfo.getCurProject().getId().toString();
			//û�н�����ʼ��
			if(!ProjectPeriodStatusUtil._isClosed(ctx,curProject)){
				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.ISNOT_INIT,new Object[]{contractBillInfo.getCurProject().getDisplayName()});
			}			
			//���ٵ�ǰ�ڼ䣬�������
		}
		//��������ʱԤ�����
		//���ݲ����Ƿ���ʾ��ͬ������Ŀ
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx,null,FDCConstants.FDC_PARAM_CHARGETYPE);
        if (isShowCharge && contractBillInfo.getConChargeType() != null) {
           invokeAuditBudgetCtrl(ctx,contractBillInfo);
        }
	}
	
	protected void checkBillForUnAudit(Context ctx, BOSUuid billId)throws BOSException, EASBizException {
    	// �˹���Ϊ��ϸ��Ϣ����
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

        //����Ƿ��Ѿ����
		String costCenterId = contractBillInfo.getOrgUnit().getId().toString(); //��ͬ�϶����ڳɱ�����������
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
    	
    	//R110603-0148:������ڱ��ǩ֤���룬����������
    	ContractUtil.checkHasChangeAuditBill(ctx, contractBillInfo.getId());
    	
    	//R110603-0148������ڱ��ǩ֤ȷ�ϣ�����������
    	ContractUtil.checkHasContractChangeBill(ctx, contractBillInfo.getId());
    	
    	//R120313-0742������ڹ�����ȷ�ϵ�������������
		ContractUtil.checkhasWorkLoadConfirmBill(ctx, contractBillInfo.getId());
    	
        //��鹦���Ƿ��Ѿ�������ʼ��
		String comId = contractBillInfo.getCurProject().getFullOrgUnit().getId().toString();
			
		//�Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = contractBillInfo.getCurProject().getId().toString();
			
//			if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
//				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{contractBillInfo.getCurProject().getDisplayName()});
//			}		
			
			//�����ڼ��ڹ�����Ŀ��ǰ�ڼ�֮ǰ�����ܷ����
			PeriodInfo costPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
			if(contractBillInfo.getPeriod().getBeginDate().before(costPeriod.getBeginDate())){
				throw new  ContractException(ContractException.CNTPERIODBEFORE);
			}			
		}
		//2009-3-4 �����ͬ��Ӧ�ĺ�����Ŀ���������Ѿ���ƾ֤���ã�������������ͬ
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
	
		//���ݷ�����ʱԤ�����
		//���ݲ����Ƿ���ʾ��ͬ������Ŀ
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
		
		//ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
		ContractBaseDataHelper.synToContractBaseData(ctx, false, pk.toString());
		
		/**
		 * �����ͬ����ƻ�  modify by yxl 20151027  ����ѡ��ĺ�Լ����Զ������˸���ƻ�������Ҫ�ٺ�ͬ����ʱ���е���
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
	 * �����������ֶ�,���ڱ������
	 */
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {
		super.setPropsForBill(ctx, fDCBillInfo);
		
		// ������������ֶΣ���ֹ���ձ���ʱ�����쳣
		dealWithCodeType(ctx, fDCBillInfo, false);
	}

	/**
	 * ������������������ֶΣ���ֹ���ձ���ʱ�����쳣
	 * <p>
	 * ����ڵ��ñ�������壨ContractBillControllerBean.handleIntermitNumber1����ʱ��<br>
	 * û�л�ȡ�����룬����������ֶο���Ϊ�գ����ʱ�򣬾���Ҫ����һ��Ĭ��ֵ
	 * 
	 * @param ctx
	 * @param fdcBillInfo
	 * @param isUpdate
	 * @throws EASBizException
	 * @throws BOSException
	 * @author��skyiter_wang
	 * @createDate��2013-10-14
	 */
	protected void dealWithCodeType(Context ctx, FDCBillInfo fdcBillInfo, boolean isUpdate) throws EASBizException,
			BOSException {
		ContractBillInfo contractBillInfo = (ContractBillInfo) fdcBillInfo;

		if (null == contractBillInfo.getCodeType()) {
			ContractTypeInfo contractType = contractBillInfo.getContractType();
			ContractPropertyEnum contractPropertyEnum = contractBillInfo.getContractPropert();
			ContractThirdTypeEnum contractThirdTypeEnum = ContractThirdTypeEnum.NEW;
			// ��ȡ��ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
			ContractCodingTypeCollection codingTypeList = (ContractCodingTypeCollection) _getContractCodingType(ctx,
					contractType, contractPropertyEnum.getValue(), contractThirdTypeEnum.getValue());

			if (FdcObjectCollectionUtil.isNotEmpty(codingTypeList)) {
				// ��һ����ͬ�������ͣ���ӽ�����ƥ�䣩
				ContractCodingTypeInfo firstCodingTypeInfo = codingTypeList.get(0);
				// ���ú�ͬ��������
				contractBillInfo.setCodeType(firstCodingTypeInfo);
			}

			if (null != contractBillInfo.getCodeType() && isUpdate && null != contractBillInfo.getId()
					&& this._exists(ctx, new ObjectUuidPK(contractBillInfo.getId()))) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("codeType");

				super._updatePartial(ctx, contractBillInfo, selector);
			}
		}

		// ��ӡ������Ϣ
		logger.info("��ͬ��contractBillInfo��" + contractBillInfo);
		logger.info("��ͬ����������ԣ�contractBillInfo.codeType��" + contractBillInfo.getCodeType());
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
//			//����鵵�ɹ�,�ú�ͬ�����ı��ǩ֤��״̬�ı�Ϊ������
//			if(flag){
//				sql = "update T_CON_ContractChangeBill set  FState = '4AUDITTED' where FContractBillID = ? ";
//				params = new Object[]{ctPK.toString()};
//			    DbUtil.execute(ctx,sql,params);				
//			}
//	    
		return flag;
	}
	
	/**
	 * ����ǷǶ�������Ĳ����ͬ,����ۼӵ�����ͬ
	 * @param ctx
	 * @param billId
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void supplyAdd(Context ctx, BOSUuid billId,boolean audit) throws BOSException, EASBizException{
		if(audit){
			//����ǷǶ�������Ĳ����ͬ,����ۼӵ�
			String sql1 = " select entry.fcontent as amount,parent.fsplitState splitState,parent.fid mainId,parent.FAmount mainAmount,parent.ForiginalAmount oriAmount 			\r\n" +
					",parent.FexRate mainRate,parent.FgrtRate grtRate,parent.FGrtAmount grtAmount,parent.FStampTaxRate stampRate,parent.FStampTaxAmt stampAmt                             \r\n"+
					"from T_CON_ContractbillEntry entry 																							\r\n" +
					"inner join T_CON_Contractbill con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY' 	\r\n" +
					"inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and parent.fcurprojectid=con.fcurprojectid												\r\n" +
					"where con.fid=? and entry.FRowkey='am' 																				\r\n";
			IRowSet rs1 = DbUtil.executeQuery(ctx,sql1,new Object[]{billId.toString()});
			try {
				if(rs1!=null && rs1.next()){
					//��������ͬ���
					String splitState = rs1.getString("splitState");
					String mainId = rs1.getString("mainId");
					//�����ͬ���
					BigDecimal supAmount = rs1.getBigDecimal("amount");
					//����ͬ��λ�ҽ��
					BigDecimal mainAmount = rs1.getBigDecimal("mainAmount");
//					BigDecimal localAmount = rs1.getBigDecimal("mainLocalAmount");
					//����ͬԭ�ҽ��
					BigDecimal oriAmount = rs1.getBigDecimal("oriAmount");
					//����ͬ����
					BigDecimal mainRate = rs1.getBigDecimal("mainRate");
					//����ͬ���ޱ���
					BigDecimal grtRate = rs1.getBigDecimal("grtRate");
					//����ͬ���޽��
					BigDecimal grtAmount = rs1.getBigDecimal("grtAmount");
					//����ͬӡ��˰��
					BigDecimal stampRate = rs1.getBigDecimal("stampRate");
					//����ͬӡ��˰���
					BigDecimal stampAmt = rs1.getBigDecimal("stampAmt");
					//��������ͬԭ�Һͱ�λ�ҽ��
					String updateSql = "update T_CON_Contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
					
			    	//ԭ�ҽ��
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
			    	//���±��޽��
			    	BigDecimal newGrtAmount = FDCConstants.ZERO;
			    	if(grtRate.compareTo(FDCConstants.ZERO)==1){
			    		newGrtAmount = (supAmount.add(oriAmount)).multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED,4,2);
			    	}
			    	//����ӡ��˰���
			    	BigDecimal newStampAmt = FDCConstants.ZERO;
			    	if(stampRate.compareTo(FDCConstants.ZERO)==1){
			    		newStampAmt = (supAmount.add(oriAmount)).multiply(mainRate).multiply(stampRate).divide(FDCConstants.ONE_HUNDRED,4,2);
			    	}
					DbUtil.execute(ctx,updateSql,new Object[]{supAmount.add(oriAmount),revAmount.add(mainAmount),newGrtAmount,newStampAmt,mainId});
							    		
					//�ж�����ͬ�Ľ�� �� ��ͬ��ֵ��Ѳ�ֽ�� ���Ƿ���ȡ�������ȣ�������ͬ�ͺ�ͬ��ֵĲ��״̬��Ϊ���ֲ��
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
					
					//R110228-280��¼��ǵ�������Ĳ����֮ͬ������ͬ��ʾΪȫ�����(֮ǰû�п��Ǻ�ͬ�����붯̬�ɱ������)
					ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
					if (contractbill.isIsCoseSplit()) {
						
						//�ɱ��ࣺ���Զ����������ʱ���ֲ��(��ǰ�߼�)������(���ò���)�Զ���ֲ���״̬Ϊȫ����� by hpw 2010-06-25
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
									//��������ͬ+����ͬ����������ͬ�Ѳ�ֽ�� ��������ͬ���״̬Ϊ���ֲ��
									//R110608-0383: ¼����Ϊ0������ͬ�Ĳ�ֽ�����Ϊ0�����
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
										builder.clear();//������New
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

							// ���ʱ�����Ϊ�Զ���֣���ô�ͽ�״̬��Ϊ��ȫ����֡�
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
						//�ǳɱ��ࣺ����״̬Ϊ���ֲ��
						ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						for(int i=0;i < col.size();i++){
							ConNoCostSplitInfo info = col.get(i);
							if(info!=null){
								//��������ͬ+����ͬ����������ͬ�Ѳ�ֽ�� ��������ͬ���״̬Ϊ���ֲ��
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
									builder.clear();//������New
									builder.appendSql("update T_CON_ConNoCostSplit set fsplitState=? where fcontractbillid=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.execute();
									builder.clear();
								}
							}
						}
					}
					
			    	//���º�ͬ����ƻ�    					
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
				/*****��������ͬ�ĺ�ִͬ��������ͬ�ɱ���� -by neo****/
				ContractExecInfosFactory.getLocalInstance(ctx).
						updateSuppliedContract("audit", mainId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}else{
			//����ǷǶ�������Ĳ����ͬ,����ۼӵ�
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
					//�������ͬ����ȫ��� ���������������ͬ
					if(splitState!=null &&splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
						throw new ContractException(ContractException.HASALLAPLIT);
					}
					//��������ͬ���
					String mainId = rs.getString("mainId");
					//�����ͬ���
					BigDecimal supAmount = rs.getBigDecimal("amount");
					BigDecimal mainAmount = rs.getBigDecimal("mainAmount");
//					BigDecimal localAmount = rs.getBigDecimal("mainLocalAmount");
					//����ͬԭ�ҽ��
					BigDecimal oriAmount = rs.getBigDecimal("oriAmount");
					//����ͬ����
					BigDecimal mainRate = rs.getBigDecimal("mainRate");
					//����ͬ���ޱ���
					BigDecimal grtRate = rs.getBigDecimal("grtRate");
					//����ͬ���޽��
					BigDecimal grtAmount = rs.getBigDecimal("grtAmount");
					//����ͬӡ��˰��
					BigDecimal stampRate = rs.getBigDecimal("stampRate");
					//����ͬӡ��˰���
					BigDecimal stampAmt = rs.getBigDecimal("stampAmt");
					
					String updateSql = "update T_CON_Contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
//					String updateSql = "update T_CON_Contractbill set FAmount=?,FlocalAmount=? where Fid=?";
					//DbUtil.execute(ctx,updateSql,new Object[]{mainAmount.subtract(supAmount),localAmount.subtract(supAmount),mainId});
					
					//R101227-311��ͬ������ʱ���� �Ƚ��п�ָ�봦��
			    	//�����ͬ��� ��λ��
					if(supAmount==null){
						supAmount = FDCConstants.ZERO;
					}
			    	BigDecimal revAmount = supAmount.multiply(mainRate);
			    	//�������ͬ�Ѳ�ֽ������������� �򲹳��ͬ���ܷ�����
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
					//����������뵥���ۼ��������Ѿ�����revAmount
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
			    	//���±��޽��
			    	BigDecimal newGrtAmount = FDCConstants.ZERO;
			    	if(grtRate.compareTo(FDCConstants.ZERO)==1){
			    		newGrtAmount = (oriAmount.subtract(supAmount)).multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED,4,2);
			    	}
			    	//����ӡ��˰���
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
					
					//R110228-280��¼��ǵ�������Ĳ����֮ͬ������ͬ��ʾΪȫ�����(֮ǰû�п��Ǻ�ͬ�����붯̬�ɱ������)
					ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
					if (contractbill.isIsCoseSplit()) {
						
						//�ɱ��ࣺ���Զ����������ʱ���ֲ�֣������Զ���ֲ���״̬Ϊȫ����� by hpw 2010-06-25
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
									//�������ͬԭ����������ͬ�Ѳ�ֽ�� ��������ͬ���״̬Ϊ��ȫ���
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
						
						//�ǳɱ��ࣺ����״̬Ϊ���ֲ��
						ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						for(int i=0;i < col.size();i++){
							ConNoCostSplitInfo info = col.get(i);
							if(info!=null){
								//�������ͬԭ����������ͬ�Ѳ�ֽ�� ��������ͬ���״̬Ϊ��ȫ���
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
					
			    	//���º�ͬ����ƻ�    	
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
				/*****��������ͬ�ĺ�ִͬ��������ͬ�ɱ���� -by neo****/
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
		
		// �ڽ���ۼ�֮ǰȡ����ͬ��ǩԼ���
		String mainSignAmount = null;
		try {
			mainSignAmount = getMainSignAmount(ctx, billId);
			synUpdateBillByRelation(ctx, billId, true, mainSignAmount);
		} catch (SQLException e1) {
			logger.error(e1);
			throw new BOSException(e1);
		}	
		
		//����ǷǶ�������Ĳ����ͬ,����ۼӵ�
		supplyAdd(ctx, billId,true);
	
		//ͬ����ִ��״̬����ǩ��
		ContractStateHelper.synToExecState(ctx, billId.toString(), ContractExecStateEnum.SIGNED);

		//�Զ�������ֵ�(��ȫ���״̬��)
		//�ɱ����
//		CostSplitBillAutoAuditor.autoAudit(ctx, new ObjectUuidPK(billId), "T_CON_ContractCostSplit", "FContractBillID");
		//������
//		CostSplitBillAutoAuditor.autoAudit(ctx, new ObjectUuidPK(billId), "T_CON_ConNoCostSplit", "FContractBillID");
		
		
		// modified by zhaoqin for ��Ŀ�ʽ�ƻ� on 2013/08/15 start
		// return super._submit(ctx, contractBillInfo);
		ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx, new ObjectUuidPK(billId.toString()), getSic());
//		String costCenterId = contractBillInfo.getOrgUnit().getId().toString();//��ͬ�϶����ڳɱ�����������
		// �Ƿ��Զ����
		//boolean isAutoSplit = FDCUtils.getBooleanValue4FDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_ISAUTOSPLIT);
		/**
		 * �Ƿ��Զ����:
		 *  0: ��ͬ�Զ����
		 *  1: ȫ���Զ����
		 *  2: ���Զ����
		 */
//		String autoSplit = FDCUtils.getFDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_ISAUTOSPLIT);
//		if (null != autoSplit && ("0".equals(autoSplit) || "1".equals(autoSplit)))
//			autoSplit(ctx, contractBillInfo);
		// modified by zhaoqin for ��Ŀ�ʽ�ƻ� on 2013/08/14 end
		updateContractExecInfos(ctx, billId);		
		updatePeriod(ctx, billId);
		updateInviteContractHistory(ctx, billId);
		
		//�жϲ�ַ�¼������Ϣ�����򱣴�����ͬ���
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
		 * �����ͬ����ƻ�
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
	 * ����������ʱ���ݺ�ͬ�������Ӻ�ִͬ�б������Ϣ
	 * �жϣ����ҽ�����ͬ����Ϊ�����ͬ�Һ�ͬΪ���Ƴɱ����ʱ����¼��ִͬ�������
	 * @param billId
	 * @Author��owen_wen
	 * @CreateTime��2012-1-6
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
	 * ������������Ͷ������еĹ�Ӧ�̵�ǩԼ��ʷ���������߹�Ӧ�̵�number����ƥ��
	 * @param ctx
	 * @param billId ��ͬID
	 * @Author��owen_wen
	 * @CreateTime��2012-1-6
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
	 * 1 .����ͬδ����ʱ(�����ս�������ս���δ����)���滮���=�滮���-��ǩԼ���+��������������=���ƽ��-ǩԼ��� 2
	 * .����ͬ�ѽ���ʱ(���ս���������)���滮���=�滮���-������������=���ƽ��-������ 3
	 * .��дʱ���ں�ͬ��������ͨ��ʱ�����ǩ֤��������ͨ��ʱ�����ǩ֤ȷ�Ͻ���ʱ����ͬ��������ͨ��ʱ�� 4.
	 * ��ͬ�޶������󣬹滮���=�滮���-���޶����ǩԼ���+��������������=���ƽ��-�޶����ǩԼ��
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
		// �滮���
		BigDecimal balanceAmt = pcInfo.getBalance();
		// �������
		BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
		// ��ͬǩԼ���
		BigDecimal signAmount = contractBillInfo.getAmount();
		// if(mainSignAmount != null){
		// if(flag){
		// signAmount = FDCHelper.subtract(signAmount, mainSignAmount);
		// }else{
		// signAmount = FDCHelper.add(signAmount, mainSignAmount);
		// }
		// }
		// ��ܺ�ԼǩԼ���
		BigDecimal signUpAmount = pcInfo.getSignUpAmount();
		// ���
		BigDecimal otherSignUpAmount = FDCHelper.ZERO;
		if (flag) {
			if (relateAmt == null) {
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, signAmount));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, signAmount));
				pcInfo.setSignUpAmount(FDCHelper.add(signUpAmount, signAmount));
				// ά�����
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.subtract(balanceAmt, signAmount));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.subtract(controlBalanceAmt, signAmount));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, signAmount), signUpAmount);
			} else {
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, relateAmt));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, relateAmt));
				pcInfo.setSignUpAmount(FDCHelper.add(signUpAmount, relateAmt));
				// ά�����
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
				// ά�����
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.add(balanceAmt, signAmount));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.add(controlBalanceAmt, signAmount));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, signAmount), signUpAmount);
			} else {
				pcInfo.setBalance(FDCHelper.add(balanceAmt, relateAmt));
				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, relateAmt));
				pcInfo.setSignUpAmount(FDCHelper.subtract(signUpAmount, relateAmt));
				// ά�����
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
		// ���������ĺ�Լ�滮�汾���
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
		// ���ҵ��ú�ͬ���Ƿ������ս���ĵ��ݣ��У���˵���ú�ͬ�����ս�����
		// ��ͬ�ѽ�����һ�������ս���Ľ��㵥
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
	 * ���ݵ�ǰ��Ŀ�ɱ��ڼ�����ݻ����ݵ�ҵ�����ںͶ����ڼ�
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
			//�����½�ͳһ�������߼�����
			String prjId = billInfo.getCurProject().getId().toString();
			//�ɱ��ڼ�
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
			PeriodInfo billPeriod = billInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//���������ڼ�
			Date bookedDate = DateTimeUtils.truncateDate(billInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","��������Ӧ����֯û�е�ǰʱ����ڼ䣬�������ã�"));
			}
			/***************
			 * ��1����������ȷ�ϵ��ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱���ڹ�����Ŀ�ɱ����񡰵�ǰ�ڼ䡱ʱ����ҵ���ڼ䡱����
			 * ��2����������ȷ�ϵ��ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱С�ڵ��ڹ�����Ŀ�ɱ����񡰵�ǰ�ڼ䡱ʱ����ҵ���ڼ䡱����Ϊ������Ŀ�ɱ����񡰵�ǰ�ڼ䡱
			 *	
			 *	ԭ�����ֱ���ʱ��ͬ���ڼ��ϳ�����
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
			
			//���¸������뵥��ҵ�����ڣ������ڼ���ݻ�״̬
			
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
	
	//��������ʱ Ԥ�����
	private void invokeAuditBudgetCtrl(Context ctx,ContractBillInfo conBill) throws BOSException ,EASBizException{
		boolean useWorkflow = FDCUtils.isRunningWorkflow(ctx, conBill.getId().toString());
        if (!useWorkflow) {
            IBgControlFacade bgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
            IBgBudgetFacade iBgBudget = BgBudgetFacadeFactory.getLocalInstance(ctx);

            boolean isPass = false;
            isPass = iBgBudget.getAllowAccessNoWF(FDCConstants.ContractBill);
            if (isPass) {
                // 5.1.1��ʱ����
                bgControlFacade.bgAuditAllowAccess(conBill.getId(), FDCConstants.ContractBill, null);
            } else {
                isPass = bgControlFacade.bgAudit(conBill.getId().toString(),  FDCConstants.ContractBill, null);
            }
            // ����isPass�ж��Ƿ����쳣
            if (!isPass) {
                throw new ContractException(ContractException.BEFOREBGBAL);
            }
        }
	}
	//���ݷ�����ʱ Ԥ�����
	private void invokeUnAuditBudgetCtrl(Context ctx,ContractBillInfo conBill) throws BOSException ,EASBizException{
		
		IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(ctx);
		iBgCtrl.cancelRequestBudget(conBill.getId().toString());
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		
		checkBillForUnAudit(ctx, billId);
		
		FilterInfo filter = new FilterInfo();
		// ��ͬ
		filter.getFilterItems().add(new FilterItemInfo("id", billId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("isArchived", BooleanEnum.TRUE));
		
		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.HASACHIVED);
		}	
		//�����ͬ������ͬ��֣�����ʾ���ܽ��з����  getContractCostSplitCollection("select id where contractbill.id='"+billId.toString()+"'")
//		if(ContractCostSplitFactory.getLocalInstance(ctx).exists("where contractbill.id='"+billId.toString()+"'"))
//			throw new ContractException(new NumericExceptionSubItem("999","�ѹ�����ͬ��ֵ��ݣ����ܷ���ˣ�"));
		super._unAudit(ctx, billId);
		// �ڽ���ۼ�֮ǰȡ�����ͬ�Ĳ�����
		// �ڽ���ۼ�֮ǰȡ����ͬ��ǩԼ���
		String mainSignAmount = null;
		try {
			mainSignAmount = getMainSignAmount(ctx, billId);
			synUpdateBillByRelation(ctx, billId, false, mainSignAmount);
		} catch (SQLException e1) {
			logger.error(e1);
			throw new BOSException(e1);
		}
		//����ǷǶ�������Ĳ����ͬ������ͬ������
		supplyAdd(ctx, billId,false);
		//�Զ���������ֵ�
		//�ɱ����
		CostSplitBillAutoAuditor.autoUnAudit(ctx, new ObjectUuidPK(billId), "T_CON_ContractCostSplit", "FContractBillID");
		//������
		CostSplitBillAutoAuditor.autoUnAudit(ctx, new ObjectUuidPK(billId), "T_CON_ConNoCostSplit", "FContractBillID");
		
		//�Զ�ɾ�������½�����
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("period.id");

		ContractBillInfo info = (ContractBillInfo)this.getValue(ctx,new ObjectUuidPK(billId),selectors);
		if(info.getPeriod()!=null){
			CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(info.getId().toString(),
					info.getPeriod().getId().toString());
		}
		//��������ͬʱɾ����ִͬ�б��Ӧ�����Ϣ
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
		//ʹ�ñ������ȡ��
		String orgId = cbi.getOrgUnit().getId().toString();
		String bindingProperty = "codeType.number";
		// �޵�ǰ��֯�����ߵ�ǰ��֯û�����������ü��ŵ�
		if (orgId == null || orgId.trim().length() == 0) {			
			orgId = OrgConstants.DEF_CU_ID;
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		/* �Ƿ����ñ������ */
		if(cbi.getArchivedNumber() == null){//����Ѿ��й��鵵 ��ʹ����ǰ�Ĺ鵵��ͬ��� 
			if (cbi.getCodeType()!=null&&iCodingRuleManager.isExist(cbi, orgId, bindingProperty)) {
				flag = true;						
				if (iCodingRuleManager.isUseIntermitNumber(cbi, orgId, bindingProperty)) {		// ��������в�����Ϻ�					
					// ��������������ˡ��Ϻ�֧�֡����ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
					storeNumber = iCodingRuleManager.getNumber(cbi, orgId, bindingProperty, "");										
				} else if (iCodingRuleManager.isAddView(cbi, orgId, bindingProperty)){				
					// �ж��Ƿ��޸��˱���,�Ƿ�Ĵ���˳���
					if (iCodingRuleManager.isModifiable(cbi, orgId, bindingProperty)) {					
						iCodingRuleManager.checkModifiedNumber(cbi, orgId,storeNumber, bindingProperty);
					}				
				} else {
					// ʲô��ûѡ,��������ʾ,����Ϻ�,�ڴ�����number
//					if(cbi.getNumber()==null||cbi.getNumber().equals("")){
						storeNumber = iCodingRuleManager.getNumber(cbi,orgId, bindingProperty, "");						
//					}
				}	
			}
			//�����һ�ι鵵 ��ѹ鵵��ű��� ���¼��ֶ� FArchivedNumber
			String updateNumberSql = "update T_CON_ContractBill set FArchivedNumber=?  where FID = ? ";
			Object[] params = new Object[]{storeNumber,cbi.getId().toString()};
			DbUtil.execute(ctx,updateNumberSql,params);
		}	
					
		//���º�ͬ�鵵״̬�͹鵵����
		String updateNumberSql = "update T_CON_ContractBill set FArchivingNumber=fnumber  where FID = ? ";
		Object[] params = new Object[]{cbi.getId().toString()};
		DbUtil.execute(ctx,updateNumberSql,params);
		
		//���º�ͬ�鵵״̬�͹鵵����
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
				
//				����仯��ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
			ContractBaseDataHelper.synToContractBaseData(ctx, false, cbi.getId().toString());

		} catch (SQLException e) {		
			throw new BOSException(e);
		}
			
			//����鵵�ɹ�,�ú�ͬ�����ı��ǩ֤��״̬�ı�Ϊ������
			if(flag){
				sql = "update T_CON_ContractChangeBill set  FState = '4AUDITTED' where FContractBillID = ? ";
				params = new Object[]{cbi.getId().toString()};
			    DbUtil.execute(ctx,sql,params);	
			    //����鵵�ɹ�����Ӧ�������뵥�ϵĺ�ͬ��ҲӦ������Ӧ�ĵ��� by cassiel_peng 2010-01-12
			    String _sql="update T_CON_PayRequestBill set FContractNo = ? where FContractId = ? ";
			    params = new Object[]{storeNumber,cbi.getId().toString()};
			    DbUtil.execute(ctx, _sql,params);
			}
		// ����鵵�ɹ������²����ͬ��������
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
	    
		//��ͬ������򣬱����ͬ������ΪӦ������
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
		// ��ǰ��֯ID����ѭ���б���ǰ��
		orgIdList.add(currentOrgId);
		if (isRecycleParentOrgNumber) {
			ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
		}
		// ����������е��ظ�ֵ��Nullֵ
		FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
		logger.info("orgIdList:" + orgIdList);
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);

		for (int i = 0, size = orgIdList.size(); i < size; i++) {
			String curOrgId = orgIdList.get(i).toString();
			logger.info("������֯Ϊ��" + curOrgId + "�ı������");

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
				//������ճɹ��˾�����ѭ��
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
		// ��ȡ���ѭ������(���ڱ������)
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
	 * ��ȡ���ѭ������(���ڱ������)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#getCycleMaxIndex4HandleIntermitNumber()
	 */
	protected int getCycleMaxIndex4HandleIntermitNumber() {
		return super.getCycleMaxIndex4HandleIntermitNumber();
	}

	/**
	 * ������ȡ�ú�ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
	 * <p>
	 * 1�����Ȳ�ѯ����ͬ���͡�������ͬ���ʡ�����ָ��ֵ����� <br>
	 * 2���ٲ�ѯ����ͬ���͡�Ϊָ��ֵ������ͬ���ʡ�Ϊ��OLD(ȫ��)������� <br>
	 * 3���ٲ�ѯ���ࡰ��ͬ���͡�Ϊָ��ֵ����� <br>
	 * 4���ٲ�ѯ����ͬ���͡�Ϊ��NULL(ȫ��)��������ͬ���ʡ�Ϊָ��ֵ����� <br>
	 * 5���ٲ�ѯ����ͬ���͡�Ϊ��NULL(ȫ��)��������ͬ���ʡ�Ϊ��OLD(ȫ��)������� <br>
	 * <p>
	 * 6.1������ͬ���͡�Ҫ֧�ֵݹ顣���磺����Ҫ�ҵ�ǰ�������û���ҵ�����Ҫ���ҵ��ϼ��������û���ҵ�����Ҫ�����ϼ����������� <br>
	 * 6.2���ݹ���ҡ���ͬ���͡��ϼ�������ͬ���͡������νṹ��ͨ��ֻ��3-4�������Բ�������������⣩ <br>
	 * 
	 * @param ctx
	 *            Ӧ�������ģ��ǿ�
	 * @param contractType
	 *            ��ͬ���ͣ��ɿ�
	 * @param contractProperty
	 *            ��ͬ���ʣ��ǿ�
	 * @param thirdType
	 *            ����״̬��������鵵�����ǿ�
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

		// ȡ�ú�ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
		ContractCodingTypeCollection codingTypeCollection = ContractCodingUtil.getContractCodingTypeCollection(ctx,
				contractTypeInfo, contractProperty, thirdType);

		return codingTypeCollection;
	}

	/**
	 * ��������������ȡҪ֧����֯�ݹ顣���磺����Ҫ�ҵ�ǰ��֯�����û���ҵ�����Ҫ���ҵ��ϼ���֯�������û���ҵ�����Ҫ�����ϼ���֯����������
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
		
		// �Ƿ��ѹ鵵
		if (!contractBillInfo.isIsArchived()) {
			ContractTypeInfo contractType = contractBillInfo.getContractType();
			ContractPropertyEnum contractPropertyEnum = contractBillInfo.getContractPropert();
			ContractThirdTypeEnum contractThirdTypeEnum = ContractThirdTypeEnum.NEW;
			// ��ȡ��ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
			ContractCodingTypeCollection codingTypeList = (ContractCodingTypeCollection) _getContractCodingType(ctx,
					contractType, contractPropertyEnum.getValue(), contractThirdTypeEnum.getValue());

			if (FdcObjectCollectionUtil.isNotEmpty(codingTypeList)) {
				String currentOrgId = getOrgUnitId(ctx, info);

				boolean isRecycleParentOrgNumber = isRecycleParentOrgNumber();
				logger.info("isRecycleParentOrgNumber:" + isRecycleParentOrgNumber);
				ArrayList orgIdList = new ArrayList();
				// ��ǰ��֯ID����ѭ���б���ǰ��
				orgIdList.add(currentOrgId);
				if (isRecycleParentOrgNumber) {
					ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
				}
				// ����������е��ظ�ֵ��Nullֵ
				FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
				logger.info("orgIdList:" + orgIdList);

				// ��һ����ͬ�������ͣ���ӽ�����ƥ�䣩
				ContractCodingTypeInfo firstCodingTypeInfo = codingTypeList.get(0);

				ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
				for (int i = 0, size = codingTypeList.size(); i < size; i++) {
					ContractCodingTypeInfo tempCodingTypeInfo = codingTypeList.get(i);
					// ���ú�ͬ�������ͣ���ʱ���ã����ں����ȡ�������ʹ�ã�
					contractBillInfo.setCodeType(tempCodingTypeInfo);

					// ��������ȡҪ֧����֯�ݹ顣���磺����Ҫ�ҵ�ǰ��֯�����û���ҵ�����Ҫ���ҵ��ϼ���֯�������û���ҵ�����Ҫ�����ϼ���֯����������
					for (int j = 0; j < orgIdList.size(); j++) {
						if (setNumber(ctx, contractBillInfo, orgIdList.get(j).toString(), BINDING_PROPERTY,
								iCodingRuleManager, count)) {
							// ����ɹ�ƥ�䵽�����������ֹѭ����ֱ�ӷ��أ���ͬ��������Ϊ���һ�����õ���ʱֵ��
							return;
						}
					}
				}

				// ���ǰ��ѭ����ʼ��û��ƥ�䵽����������������ú�ͬ�������ͣ���һ����ͬ�������ͣ���ӽ�����ƥ�䣩��
				contractBillInfo.setCodeType(firstCodingTypeInfo);
			}
		}
	}

	
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
		ContractBillInfo info = (ContractBillInfo) model;
		setCreatorPosition(ctx, (ContractBillInfo) model);
		
		IObjectPK objectPK = super._addnew(ctx, model);
		
		// ������������ֶΣ���ֹ���ձ���ʱ�����쳣
		dealWithCodeType(ctx, info, true);
		
//		ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
		ContractBaseDataHelper.synToContractBaseData(ctx, false, objectPK.toString());
		
    	/**��ӷ�дContractBaseDataID�Ĵ��� -by neo**/
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(
				"update t_con_contractbill set fcontractbasedataid =" +
				"(select fid from t_CON_contractbasedata where fcontractid = t_con_contractbill.fid) " +
				"where");
		builder.appendParam("fid", objectPK.toString());
		builder.executeUpdate(ctx);
		
		/**
		 * �����ͬ����ƻ�
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
		/**��ӷ�дContractBaseDataID�Ĵ���**/
	}

	protected void _updatePartial(Context ctx, IObjectValue model, SelectorItemCollection selector) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		super._updatePartial(ctx, model, selector);
		
//		ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
		String id = ((FDCBillInfo)model).getId().toString();
		ContractBaseDataHelper.synToContractBaseData(ctx, false, id);

	}
	
	/**
	 * ����ͬ�Ƿ������ܺ�Լ
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
	 * ���¹滮��Լ"�Ƿ�����"�ֶ�
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
	 * �����Ͽ�ܺ�Լ�Ƿ�����
	 * 
	 * @throws Exception
	 * @throws Exception
	 */
	private void updateOldProg(Context ctx, IObjectPK pk) throws Exception {
		String checkReaPre = checkReaPre(ctx, pk);
		while (checkReaPre != null) {
			int count = 0;// ������Լ��
			count = isCitingByProg(ctx, checkReaPre);
			boolean isCiting = preVersionProg(ctx, checkReaPre);
			if (count <= 1 && !isCiting) {
				updateProgrammingContract(ctx, checkReaPre, 0);
			}
			checkReaPre = isUpdateNextProgState(ctx, checkReaPre);
		}
	}

	/**
	 * �ҳ��������Ŀ�ܺ�Լ�ļ�¼��(���ı��Ѿ��ϳ������ٲ���)
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

		//����Ƿ񱻱��ǩ֤��������
		this._isReferenced(ctx,pk);
		
		//�������ɺ�ͬ״̬�仯��ʱ�����б�֪ͨ����б�Ԥ���
		ContractBillAcceptanceLetterHelper.acceptanceLetterDeleteHelper(ctx, pk);
		try {
			updateOldProg(ctx,pk);
		} catch (Exception e) {
			logger.error(e);
			throw new BOSException(e);
		}
		super._delete(ctx, pk);		
		
/*		//ͬ��ɾ����ͬ��������
		String sql = "delete t_con_contractbasedata where fcontractid = ?";
		
		DbUtil.execute(ctx, sql, new Object[]{pk.toString()});*/
		//ormap ɾ����ʱ��������ù�ϵ
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
			
			//ormap ɾ����ʱ��������ù�ϵ
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId",idSet,CompareType.INCLUDE));
			ContractBaseDataFactory.getLocalInstance(ctx).delete(filter);
		}
	}

	//ѡ�񲻼Ƴɱ��Ľ��Ϊ��,���¼�ڷ�¼��,��ʾ��ʱ��Ҫ�ӷ�¼��ȡ
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
	
	//������ʱ���༭�����ʼ���ݴ����ȷ���
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		Map initMap = super._fetchInitData(ctx,paramMap);
		return initMap;
	}
	
	//��ʼ��������Ŀ
	protected void  initProject(Context ctx, Map paramMap,Map initMap) throws EASBizException, BOSException{
		String projectId = (String) paramMap.get("projectId");
		CurProjectInfo curProjectInfo = null;
		if(projectId == null && paramMap.get("contractBillId")!=null) {
			//��ͬ����
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

			//������Ŀ
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

	//�������������
	protected void _split(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
	}

	//�Զ�ɾ�����
	protected boolean _autoDelSplit(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		//��˵ĺ�ͬ�����Զ�ɾ�����
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("state"));
		ContractBillInfo contractBillInfo = 
			ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk,sic);
		if(FDCBillStateEnum.AUDITTED.equals(contractBillInfo.getState())){
			//throw new ContractException(ContractException.HASAUDIT);
			return false;
		}
		
		//�ɱ����
		CostSplitBillAutoAuditor.autoDelete(ctx,pk, "T_CON_ContractCostSplit", "FContractBillID");
		//������
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

	//��ȡ��ͬ���ͱ���
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
		 * ���������,���б��л�ȡ��ͬ��ID�����е�ID���Ǻ�ͬ�����ID��
		 * Ȼ��ͨ�����ù�����Ϣ��ʹ��Զ�̵��ã��鿴��ͬ���ĸ����������Ƿ��к�ͬ��ID.
		 * ����ú�ͬ�����ĸ����ͽ��б�ǣ����򲻱��
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
		 * ����Ф쭱��ڱ������ĵ�ʱ����Ҫ�������ز�ϵͳ����ҵ�񵥾ݶ����ñ�û��ֱ��ʹ��֮ǰϵͳ���е�contract����ֶ�Ϊ���ĵ�ҵ�񵥾ݸ�������¼���
		 * �ֶ�parent�����Ա����ʱ�����ݲ�ͬ��֮��鿴�޸ĵ�ʱ����ܾͻ�����ܶ����⡣����ID��ͬ�����������������û��߽��ò���"���ز�ҵ��ϵͳ���Ĺ������������ʼ����۹���"
		 * ����ģ�Ҳ����˵���û���ֶ����û����ǽ��ò����Ļ����ǵ�ID���Ǵ��ڲ�ͬ�������⣬����ֱ�ӵ�Ӱ������ں�ͬ¼����ʱ���ͺ�ͬ��ѯ��ʱ���ϵ�"����"һ�е���Ϣ��ʾ��׼ȷ
		 * ����Ŀǰ����������ز�ϵͳ��ֻ�к�ͬģ�����˸ù��ܣ�����FParent��ʵ���Ǻ�ͬ��ID(FContractId)���ǽ���������ֻ��FParentΪ��������һ�鿩�������� by Cassiel_peng 2009-9-6
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
		 * ��丽����,���б��л�ȡ��ͬ��ID�����е�ID���Ǻ�ͬ�����ID��
		 * Ȼ��ͨ�����ù�����Ϣ��ʹ��Զ�̵��ã��鿴��ͬ���ĸ����������Ƿ��к�ͬ��ID.
		 * ����ú�ͬ��ҵ����ظ����ͽ��б�ǣ����򲻱��
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
	
	// ������ͬһ����ϸ��������Ŀ���²�����ͬ����Ψһ�Լ�飬����Ŀ���ڡ���¼����֯�ĺ�ͬ�����ü���ͬ����Ψһ�ԡ�
	// R101129-229 Added by Owen_wen
	//�޸�ǰ�������趨�˹�����Ŀ��ɱ����Ķ�Ӧ��ϵ����ͬ����billInfo��ͨ��getOrgUnit()�鵽ĳ����֯�£�����֯�µĺ�ͬ�����ǲ�����������
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
		
		//������ڲ����ͬ��Ҳ����
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
		    		// ��ճɱ����
		    		FilterInfo filter = new FilterInfo();
		    		filter.appendFilterItem("contractBill.id", supId);
		    		// ɾ��������
		    		PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
		    		// ɾ��������
		    		SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
		    		// ɾ��������
		    		ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);
		    		// ɾ����ͬ���
		    		ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
	    		}
	    		//��ͬ��ֹ����Ŀǰ��ʱ�򽨷�R120114-0032�ᵥ�ĳ���ֹ״̬
				//�������ڱ���¥��ʹ�ù㷺��Ŀǰ�����Ķ��� added by andy_liu 2012-5-8
				//super._cancel(ctx,  new ObjectUuidPK(BOSUuid.read(supId)));
				setBillStatus(ctx, BOSUuid.read(pk.toString()), FDCBillStateEnum.CANCEL);
	    		
	    	}
	    }catch(Exception e){
	    	throw new BOSException(e);
	    }

	    if(billInfo.getState()==FDCBillStateEnum.SUBMITTED || billInfo.getState()==FDCBillStateEnum.AUDITTING){
    		// ��ճɱ����
    		FilterInfo filter = new FilterInfo();
    		filter.appendFilterItem("contractBill.id", conId);
    		// ɾ��������
    		PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
    		// ɾ��������
    		SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
    		// ɾ��������
    		ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);
    		// ɾ����ͬ���
    		ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
		}
		
		//��ͬ��ֹ����Ŀǰ��ʱ�򽨷�R120114-0032�ᵥ�ĳ���ֹ״̬
		//�������ڱ���¥��ʹ�ù㷺��Ŀǰ�����Ķ��� added by andy_liu 2012-5-8
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
	
    //ȡ���룬�������������ð����� 
	protected String getBindingProperty() {
		return BINDING_PROPERTY;
	}

	/**
	 * �����������ֶβ������������ֵ�����ı䣬����Ҫ���»�ȡ����
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected boolean isNeedReHandleIntermitNumberByProperty() {
		return true;
	}

	/**
	 * �������Ƿ�����ϼ���֯���ݵı������
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected boolean isRecycleParentOrgNumber() {
		return true;
	}

	/**
	 * ������ȡ�û��ձ���Seletor
	 * @return
	 * @author��skyiter_wang
	 * @createDate��2013-10-16
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
	 * ����: �ں�ͬ<<����~֮ǰ>> �������Ŀ�ܺ�Լ�Ƿ�Ϊ���°汾���粻�ǣ������Ϊ���°汾
	 * 
	 * ���Σ���ͬ���ύ������֮����˺ó�ʱ�䣬�ڼ��Լ�滮�����°汾���Ǿͻ��������
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
	    	// ��ͬ�Ƿ�����˹滮��Լ
			if(rs.next()) {
				conProg = rs.getString("FProgrammingContract");
				builder.clear();
				builder.appendSql("select pc1.fid from T_CON_ProgrammingContract pc1 join T_CON_Programming p1 on p1.fid = pc1.fprogrammingid ");
				builder.appendSql("join (select p.FVersionGroup,pc.flongnumber from T_CON_ProgrammingContract pc join T_CON_Programming p ");
				builder.appendSql(" on p.fid = pc.fprogrammingid where pc.fid = ? ) t on (t.FVersionGroup = p1.FVersionGroup and t.flongnumber = pc1.flongnumber) ");
				builder.appendSql("where p1.FIsLatest = 1 and p1.FState = '4AUDITTED'");
				builder.addParam(conProg);
				rs = builder.executeQuery();
				// �жϺ�ͬ������ܺ�Լ�Ƿ�Ϊ���°汾
				if(rs.next()){
					if(!conProg.equals(rs.getString("fid"))) {
						conProg = rs.getString("fid");
						isNeedUpdate = true;
					}
				} else {
					conProg = null;
					isNeedUpdate = true;
				}
				//����������°汾�������Ϊ���°汾�Ĺ滮��Լ
				if(isNeedUpdate) {
					builder.clear();
					if(null == conProg) {	//���µİ汾�У�֮ǰ�����Ŀ�ܺ�Լ��ɾ����
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