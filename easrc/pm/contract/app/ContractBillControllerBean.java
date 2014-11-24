package com.kingdee.eas.port.pm.contract.app;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBailFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractPayPlan;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.app.ContractBaseDataHelper;
import com.kingdee.eas.fdc.contract.app.ContractStateHelper;
import com.kingdee.eas.fdc.contract.app.CostSplitBillAutoAuditor;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacadeFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.ma.budget.BgBudgetFacadeFactory;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.IBgBudgetFacade;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.port.pm.contract.ContractBillBudgetEntryInfo;
import com.kingdee.eas.port.pm.contract.ContractBillEntryFactory;
import com.kingdee.eas.port.pm.contract.ContractBillFactory;
import com.kingdee.eas.port.pm.contract.ContractBillInfo;
import com.kingdee.eas.port.pm.contract.ContractChangeSettleBillCollection;
import com.kingdee.eas.port.pm.contract.ContractChangeSettleBillFactory;
import com.kingdee.eas.port.pm.contract.ContractSettlementBillCollection;
import com.kingdee.eas.port.pm.contract.ContractSettlementBillFactory;
import com.kingdee.eas.port.pm.contract.ContractSettlementBillInfo;
import com.kingdee.eas.port.pm.contract.ContractWithoutTextCollection;
import com.kingdee.eas.port.pm.contract.ContractWithoutTextFactory;
import com.kingdee.eas.port.pm.contract.IContractSettlementBill;
import com.kingdee.eas.port.pm.invest.ProjectBudgetFacadeFactory;
import com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo;
import com.kingdee.eas.port.pm.invest.uitls.CLUtil;
import com.kingdee.eas.port.pm.invite.WinInviteReportBudgetEntryInfo;
import com.kingdee.eas.port.pm.invite.WinInviteReportFactory;
import com.kingdee.eas.port.pm.invite.WinInviteReportInfo;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.scm.sm.pur.PurContractEntryInfo;
import com.kingdee.eas.scm.sm.pur.PurContractFactory;
import com.kingdee.eas.scm.sm.pur.PurContractInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.xr.app.XRBillException;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

public class ContractBillControllerBean extends AbstractContractBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.contract.app.ContractBillControllerBean");
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
    	if(info.getId() != null&&_exists(ctx, new ObjectUuidPK(info.getId()))){
    		ContractBillInfo oldInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(info.getId()));
    		if(!oldInfo.getState().equals(FDCBillStateEnum.SAVED)){
    			if (!FDCUtils.isRunningWorkflow(ctx, info.getId().toString())
    					|| !FDCBillStateEnum.SUBMITTED.equals(oldInfo.getState())) //���������ύ״̬Ҳ�����޸ı��� By Owen_wen 2011-03-28
    				throw new ContractException(ContractException.WITHMSG,new Object[]{"\n��ǰ��������״̬Ϊ"+oldInfo.getState()+",�����Ǳ� �����û������ģ����ܱ��棡"});
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
		info.setSrcAmount(info.getAmount());
		return super._save(ctx,info);
    }
    
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ContractBillInfo contractBillInfo = ((ContractBillInfo) model);
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
		checkNameDup(ctx, contractBillInfo);
		
		contractBillInfo.setSrcAmount(contractBillInfo.getAmount());
		return super._submit(ctx, contractBillInfo);
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
	private void setCreatorPosition(Context ctx, ContractBillInfo contractBillInfo) {
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
		} catch (BOSException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
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
     // Dean_zhu 2011-1-5
		sic.add(new SelectorItemInfo("hasSettled"));
		sic.add(new SelectorItemInfo("programmingContract.*"));
		sic.add(new SelectorItemInfo("*"));
		sic.add("contractPropert");
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
        if(model==null || contractBill.getCurProject()==null ||contractBill.getCurProject().getCompany()==null){
        	model= this.getContractBillInfo(ctx,new ObjectUuidPK(contractBill.toString()),getSic());
        }
		//�Ƿ����ò���һ�廯
		String comId = contractBill.getCurProject().getCompany().getId().toString();
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
        ProjectInfo curProjectInfo = ProjectFactory.getLocalInstance(ctx).getProjectInfo(new ObjectUuidPK(contractBillInfo.getCurProject().getId()));
		String comId = curProjectInfo.getCompany().getId().toString();
			
		HashMap param = FDCUtils.getDefaultFDCParam(ctx,contractBillInfo.getOrgUnit().getId().toString());		
		boolean splitBeforeAudit = false;
		if(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT)!=null){
			splitBeforeAudit =  Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString()).booleanValue();
		}
		
		//�Ƿ����ò���һ�廯
//		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
//		if(isInCore){
//			String curProject = contractBillInfo.getCurProject().getId().toString();
//			//û�н�����ʼ��
//			if(!ProjectPeriodStatusUtil._isClosed(ctx,curProject)){
//				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.ISNOT_INIT,new Object[]{contractBillInfo.getCurProject().getDisplayName()});
//			}			
//			//���ٵ�ǰ�ڼ䣬�������
//		}
		//��������ʱԤ�����
		//���ݲ����Ƿ���ʾ��ͬ������Ŀ
//		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx,null,FDCConstants.FDC_PARAM_CHARGETYPE);
//        if (isShowCharge && contractBillInfo.getConChargeType() != null) {
//           invokeAuditBudgetCtrl(ctx,contractBillInfo);
//        }
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
    	
    	//R110603-0148������ڱ��ָ�������������
    	if (ContractUtil.hasContractChangeBill(ctx, contractBillInfo.getId())) {
    		throw new  ContractException(ContractException.HASCONTRACTCHANGEBILL);
    	}
        
//        //��鹦���Ƿ��Ѿ�������ʼ��
//		String comId = contractBillInfo.getCurProject().getCompany().getId().toString();
//			
//		//�Ƿ����ò���һ�廯
//		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
//		if(isInCore){
//			String curProject = contractBillInfo.getCurProject().getId().toString();
//			//�����ڼ��ڹ�����Ŀ��ǰ�ڼ�֮ǰ�����ܷ����
//			PeriodInfo costPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
//			if(contractBillInfo.getPeriod().getBeginDate().before(costPeriod.getBeginDate())){
//				throw new  ContractException(ContractException.CNTPERIODBEFORE);
//			}			
//		}
		//2009-3-4 �����ͬ��Ӧ�ĺ�����Ŀ���������Ѿ���ƾ֤���ã�������������ͬ
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("contractId", billId.toString()));
//		boolean exist = ContractBaseDataFactory.getLocalInstance(ctx).exists(filter);
//		if(exist){
//			EntityViewInfo view = new EntityViewInfo();
//			view.getSelector().add("number");
//			view.getSelector().add("name");
//			view.getSelector().add("state");
//			view.getSelector().add("id");
//			view.setFilter(filter);
//			ContractBaseDataCollection contractBaseDataCollection = 
//				ContractBaseDataFactory.getLocalInstance(ctx).getContractBaseDataCollection(view);
//			if(contractBaseDataCollection.size() == 1) {
//				ContractBaseDataInfo info = contractBaseDataCollection.get(0);
//				String baseId = info.getId().toString();
//				String sql = " select ar.fid from T_GL_VoucherAssistRecord ar  \r\n" +
//						" inner join T_BD_AssistantHG hg  on ar.FAssGrpID = hg.fid   \r\n" +
//						" where hg.FContractBaseDataID = ?     \r\n";
//				IRowSet rs = DbUtil.executeQuery(ctx,sql,new Object[]{baseId});
//				try {
//					if(rs != null && rs.next()){
//						throw new ContractException(ContractException.HASVOUCHER);
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//					throw new BOSException(e);
//				}
//			}
//		}
//		//���ݷ�����ʱԤ�����
//		//���ݲ����Ƿ���ʾ��ͬ������Ŀ
//		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx,null,FDCConstants.FDC_PARAM_CHARGETYPE);
//        if (isShowCharge && contractBillInfo.getConChargeType() != null) {
//           invokeUnAuditBudgetCtrl(ctx,contractBillInfo);
//        }
	}
	
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
	
		
		removeDetailEntries(ctx, pk);
		
		if(model.get("number")==null)
			this.handleIntermitNumber(ctx,(FDCBillInfo)model);
		super._update(ctx, pk, model);
		
//		//ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
//		ContractBaseDataHelper.synToContractBaseData(ctx, false, pk.toString());
	}

	/*
	 * �����������ֶ�,���ڱ������
	 */
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {
	
		super.setPropsForBill(ctx, fDCBillInfo);
		
		ContractBillInfo contractBill = (ContractBillInfo)fDCBillInfo;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id", contractBill.getContractType().getId().toString()));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("secondType");
		
		// ��ͬ����ֻ�ܶ�Ӧһ����ͬ�������ͣ����contractCodingTypeCollectionֻ��Ҫget(0)
		ContractCodingTypeCollection contractCodingTypeCollection = ContractCodingTypeFactory.getLocalInstance(ctx).getContractCodingTypeCollection(view);
		if(contractCodingTypeCollection != null && contractCodingTypeCollection.size() > 0) {
			
			// ����Ƕ�"���к�ͬ"��Ч��ֱ�ӷ��أ������Ա���ԭ���ġ�
			if (ContractSecondTypeEnum.OLD_VALUE.equals(contractCodingTypeCollection.get(0).getSecondType().getValue()))
				return; 
			
			// �����ͬ�ġ���ͬ���ʡ��� ��ͬ�������͵ĺ�ͬ���� ��ͬ�����ͬ�ı���������Ϊ�ղ������
			if (contractBill.getContractPropert().getValue().equals(contractCodingTypeCollection.get(0).getSecondType().getValue())){
				contractBill.setCodeType(contractCodingTypeCollection.get(0));
			}
		}
		else {
			contractBill.setCodeType(null);
		}
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
//		        "select fid from  ct_con_contractbill where FNumber = ? and FOrgUnitID = ? ";
//		    Object[] params = new Object[]{storeNumber,cbi.getOrgUnit().getId().toString()};
//
//		    RowSet rowset = DbUtil.executeQuery(ctx, sql, params);
//		    try {
//				if(rowset.next()){
//					if(!rowset.getString("FID").equalsIgnoreCase(ctPK.toString())){
//						throw (new ContractException(ContractException.NUMBER_DUP));
//					}else{
//						sql = "update  ct_con_contractbill set  FIsArchived = 1 ,FNumber = ?  where FID = ? ";
//					    params = new Object[]{storeNumber,ctPK.toString()};
//					    DbUtil.execute(ctx,sql,params);
//					    flag = true;
//					}
//				}else{
//					sql = "update  ct_con_contractbill set  FIsArchived = 1 ,FNumber = ?  where FID = ? ";
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
					"from cT_CON_ContractbillEntry entry 																							\r\n" +
					"inner join cT_CON_Contractbill con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY' 	\r\n" +
					"inner join cT_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and parent.fcurprojectid=con.fcurprojectid												\r\n" +
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
					String updateSql = "update  ct_con_contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
					
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
//					String updateState1 = "update  ct_con_contractbill set FSplitState=? where Fid=?";
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
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}else{
			//����ǷǶ�������Ĳ����ͬ,����ۼӵ�
			String sql = " select entry.fcontent as amount,parent.fsplitState splitState,parent.fid mainId,parent.FAmount mainAmount,parent.ForiginalAmount oriAmount 			\r\n" +
					",parent.FexRate mainRate,parent.FgrtRate grtRate,parent.FGrtAmount grtAmount,parent.FStampTaxRate stampRate,parent.FStampTaxAmt stampAmt    \r\n"+
					" ,parent.FHasSettled    from  ct_con_contractbillentry entry 																	\r\n" +
					"inner join  ct_con_contractbill con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY' 	\r\n" +
					"inner join  ct_con_contractbill parent on parent.fnumber = con.fmainContractNumber 	and parent.fcurprojectid=con.fcurprojectid					\r\n" +
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
					
					String updateSql = "update  ct_con_contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
//					String updateSql = "update  ct_con_contractbill set FAmount=?,FlocalAmount=? where Fid=?";
					//DbUtil.execute(ctx,updateSql,new Object[]{mainAmount.subtract(supAmount),localAmount.subtract(supAmount),mainId});
					
					//R101227-311��ͬ������ʱ���� �Ƚ��п�ָ�봦��
			    	//�����ͬ��� ��λ��
					if(supAmount==null){
						supAmount = FDCConstants.ZERO;
					}
			    	BigDecimal revAmount = supAmount.multiply(mainRate);
			    	
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
				.appendSql(" select con.famount amount from  ct_con_contractbillentry entry inner join  ct_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
		builder
				.appendSql(" con.fcontractPropert='SUPPLY'  inner join  ct_con_contractbill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
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
		super._audit(ctx, billId);
		subBudgetAmount(ctx, new ObjectUuidPK(billId));
		ContractBillInfo info = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(billId));
		createPurContractInfo(ctx, info,BillBaseStatusEnum.AUDITED);
		// �ڽ���ۼ�֮ǰȡ����ͬ��ǩԼ���
		String mainSignAmount = null;
		try {
			mainSignAmount = getMainSignAmount(ctx, billId);
			synUpdateBillByRelation(ctx, billId, true, mainSignAmount);
		} catch (SQLException e1) {
			logger.error(e1);
		}	
		
		//����ǷǶ�������Ĳ����ͬ,����ۼӵ�
		supplyAdd(ctx, billId,true);
	
		//ͬ����ִ��״̬����ǩ��
//		ContractStateHelper.synToExecState(ctx, billId.toString(), ContractExecStateEnum.SIGNED);
		
		//��ͬ����ͬʱ��ͬ��Ӧ��Ԥ����ͬ�����Ҳ��Ӧ������ 20120222 wangxin 
	
//		 IContractEstimateChangeBill iFaced =  ContractEstimateChangeBillFactory.getLocalInstance(ctx);
//	   	SelectorItemCollection sel = new SelectorItemCollection();
//	   	sel.add("*");
//	   	sel.add("entrys.*");
//	   	ContractBillInfo contractInfo =  ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(billId), sel);
//	   if(ContractPropertyEnum.DIRECT.equals(contractInfo.getContractPropert())){//����ͬ �������������Ԥ����ͬ�䶯������
//		   EntityViewInfo view1 = new EntityViewInfo();
//		   FilterInfo Filter1 = new FilterInfo();
//		   Filter1.getFilterItems().add(new FilterItemInfo("isFromContract",Boolean.TRUE));
//		   Filter1.getFilterItems().add(new FilterItemInfo("contractbill.id",billId));
//		   view1.setFilter(Filter1);
//		   ContractEstimateChangeBillCollection coll1 = iFaced.getContractEstimateChangeBillCollection(view1);
//		   if(coll1.size()>0){
//			   ContractEstimateChangeBillInfo info1 = coll1.get(0);
//			   iFaced.audit(info1.getId());
//		   }
//	   }else if(ContractPropertyEnum.SUPPLY.equals(contractInfo.getContractPropert())){
		   //�����ô��  ��¼������ʹ�ú�Լ�滮������������Ԥ����ͬ�䲻�ù� 20120401
//		   ContractBillEntryCollection entryColl = contractInfo.getEntrys();
//		   ContractBillEntryInfo entryInfo= null;
//		  for(int i = 0 ; i < entryColl.size(); i++){
//			  entryInfo = entryColl.get(i);
//			  if("esId".equalsIgnoreCase(entryInfo.getRowKey()))break;
//		  }
//		   SelectorItemCollection esSel = new SelectorItemCollection();
//		   esSel.add("*");
//		   esSel.add("contractbill.programmingContract.*");
//		   EntityViewInfo view = new EntityViewInfo();
//		   view.setSelector(esSel);
//		   FilterInfo newfilter = new FilterInfo();
//		   view.setFilter(newfilter);
		   //��¼�����ĺ�ͬΪ��ʹ����
//		  ContractEstimateChangeBillInfo esInfo = iFaced.getContractEstimateChangeBillInfo(new ObjectUuidPK(entryInfo.getContent()),esSel);
//		  esInfo.setIsLastest(false);
//		  esInfo.setIsUsed(true);
//		  SelectorItemCollection sel1 = new SelectorItemCollection();
//		  sel1.add("isUsed");
//		  sel1.add("isLastest");
//		  iFaced.updatePartial(esInfo, sel1);
//		
//		  ProgrammingContractInfo programmingInfo =  esInfo.getContractbill().getProgrammingContract();
//		  BigDecimal balance = programmingInfo.getBalance();
//		  BigDecimal esAmount  = programmingInfo.getEstimateAmount();
//		  balance =  balance.add(esInfo.getEstimateAmount());
//		  esAmount = esAmount.subtract(esInfo.getEstimateAmount());
//		  programmingInfo.setBalance(balance);
//		  programmingInfo.setEstimateAmount(esAmount);
//		  SelectorItemCollection pSel = new SelectorItemCollection();
//		  pSel.add("balance");
//		  pSel.add("estimateAmount");
//		  ProgrammingContractFactory.getLocalInstance(ctx).updatePartial(programmingInfo, pSel);
		  
		  //�����ͬ ������������¼�������Ԥ����ͬ�䶯������
//		   EntityViewInfo esview = new EntityViewInfo();
//		   FilterInfo esfilter = new FilterInfo();
//		   esfilter.getFilterItems().add(new FilterItemInfo("suppyContractId",billId.toString()));
//		   esview.setFilter(esfilter);
//		   ContractEstimateChangeBillCollection  esColl =  iFaced.getContractEstimateChangeBillCollection(esview);
//		   if(esColl.size() > 0 ){
//			   ContractEstimateChangeBillInfo esInfo1 =  esColl.get(0);
//			   iFaced.audit(esInfo1.getId());
//		   }
//	   }
//		try {
//			synContractProgAmt(ctx,billId,true);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	
	void subBudgetAmount(Context ctx, IObjectPK pk)throws BOSException,EASBizException{
		ContractBillInfo info = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk);
		IProgrammingEntryCostEntry iProgrammingEntryCostEntry = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx);
		for (int i = 0; i <info.getBudgetEntry().size(); i++) {
			ContractBillBudgetEntryInfo entry = info.getBudgetEntry().get(i);
			ProgrammingEntryCostEntryInfo budgetInfo= iProgrammingEntryCostEntry.getProgrammingEntryCostEntryInfo(new ObjectUuidPK(entry.getBudgetNumber().getId()));
			String projectNumber = budgetInfo.getNumber();
			String budgetNumber = budgetInfo.getFeeNumber();
			String budgetName = budgetInfo.getFeeName();
			String year = budgetInfo.getYear();
			String backAmount = UIRuleUtil.getBigDecimal(entry.getDiffAmount()).toString();
			String staght = CLUtil.stag_ht;
			if(info.getWinInvitedBill()==null)
				staght = CLUtil.stag_wzbht;
			String[] str = ProjectBudgetFacadeFactory.getLocalInstance(ctx).subBudgetAmount(projectNumber,year,budgetNumber
																						,String.valueOf(entry.getAmount()),staght,true,backAmount);
			if("ʧ��".equals(str[0])){
				 throw new XRBillException(XRBillException.NOBUDGET, new Object[] {"Ԥ����룺"+budgetNumber+","+budgetName , str[1]});
			}
		}
	}
	
	void backBudgetAmount(Context ctx, IObjectPK pk)throws BOSException,EASBizException{
		ContractBillInfo info = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk);
		IProgrammingEntryCostEntry iProgrammingEntryCostEntry = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx);
		for (int i = 0; i <info.getBudgetEntry().size(); i++) {
			ContractBillBudgetEntryInfo entry = info.getBudgetEntry().get(i);
			ProgrammingEntryCostEntryInfo budgetInfo= iProgrammingEntryCostEntry.getProgrammingEntryCostEntryInfo(new ObjectUuidPK(entry.getBudgetNumber().getId()));
			String projectNumber = budgetInfo.getNumber();
			String budgetNumber = budgetInfo.getFeeNumber();
			String budgetName = budgetInfo.getFeeName();
			String year = budgetInfo.getYear();
			String backAmount = UIRuleUtil.getBigDecimal(entry.getDiffAmount()).toString();
			String staght = CLUtil.stag_ht;
			if(info.getWinInvitedBill()==null)
				staght = CLUtil.stag_wzbht;
			String[] str = ProjectBudgetFacadeFactory.getLocalInstance(ctx).backBudgetAmount(projectNumber,year,budgetNumber
																						,String.valueOf(entry.getAmount()),staght,true,backAmount);
			if("ʧ��".equals(str[0])){
				 throw new XRBillException(XRBillException.NOBUDGET, new Object[] {"Ԥ����룺"+budgetNumber+","+budgetName , str[1]});
			}
		}
	}
	/**
	 * ���ɲɹ���ͬ
	 * */
	void createPurContractInfo(Context ctx,ContractBillInfo info,BillBaseStatusEnum status) throws EASBizException, BOSException{
		PurContractInfo pur = PurContractFactory.getLocalInstance(ctx).getPurContractInfo("where number='99999'");
		if(!PurContractFactory.getLocalInstance(ctx).exists("where number='"+info.getNumber()+"' ")){
			pur.setId(BOSUuid.create(pur.getBOSType()));
			pur.getPaymentPlan().get(0).setId(null);
		}else{
			pur = PurContractFactory.getLocalInstance(ctx).getPurContractInfo("where number='"+info.getNumber()+"' ");
		}
		pur.setNumber(info.getNumber());
		pur.setSupplier(info.getPartB());
		pur.setTotalAmount(info.getAmount());
		ProjectInfo project = ProjectFactory.getLocalInstance(ctx).getProjectInfo(new ObjectUuidPK(info.getCurProject().getId()));
		pur.setPurOrgUnit(PurchaseOrgUnitFactory.getLocalInstance(ctx).getPurchaseOrgUnitInfo(new ObjectUuidPK(project.getCompany().getId())));
		PurContractEntryInfo entry = pur.getEntries().get(0);
		entry.setQty(new BigDecimal(1));
		entry.setBaseQty(new BigDecimal(1) );
		entry.setPrice(info.getAmount());
		entry.setAmount(info.getAmount());
		if(!BillBaseStatusEnum.AUDITED.equals(pur.getBaseStatus()))
			PurContractFactory.getLocalInstance(ctx).save(pur);
		if(PurContractFactory.getLocalInstance(ctx).exists("where number='"+info.getNumber()+"' ")){
			pur.setBaseStatus(status);
			PurContractFactory.getLocalInstance(ctx).update(new ObjectUuidPK(pur.getId()), pur);
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
	 * .��дʱ���ں�ͬ��������ͨ��ʱ���������������ͨ��ʱ�����ָ�����ʱ����ͬ��������ͨ��ʱ�� 4.
	 * ��ͬ�޶������󣬹滮���=�滮���-���޶����ǩԼ���+��������������=���ƽ��-�޶����ǩԼ��
	 * 4��δǩ��ͬ��δǩ��ʱ�� = �滮��ǩ�˺�ͬ = 0��
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
		builder.appendSql(" select parent.fid conId,entry.fcontent as amount from  ct_con_contractbillentry entry inner join  ct_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
		builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join  ct_con_contractbill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
		builder.appendSql(" where  entry.FRowkey='am' and ");
		builder.appendParam(" con.fid", billIdStr);
		rowSet = builder.executeQuery();
		
		BigDecimal supAmount = FDCHelper.ZERO;
		
		if (rowSet.next()) {
			billIdStr = rowSet.getString("conId").toString();
			supAmount=rowSet.getBigDecimal("amount");
		}
		ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx, new ObjectUuidPK(billIdStr), getSic());
		ContractBillInfo supplierContractBillInfo= this.getContractBillInfo(ctx, new ObjectUuidPK(billId), getSic());
//		if (contractBillInfo.getProgrammingContract() == null)
//			return;
//		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
//		ProgrammingContractInfo pcInfo = null;
//		if (pcInfo == null)
//			return;
//		// �滮���
//		BigDecimal balanceAmt = pcInfo.getBalance();
//		// �������
//		BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
//		// ��ͬǩԼ���
//		BigDecimal signAmount = contractBillInfo.getAmount();
//		// ��ܺ�ԼǩԼ���
//		BigDecimal signUpAmount = pcInfo.getSignUpAmount();
//		// ���
//		SelectorItemCollection sict = new SelectorItemCollection();
//		sict.add("balance");
//		sict.add("controlBalance");
//		sict.add("signUpAmount");
//		sict.add("changeAmount");
//		sict.add("settleAmount");
//		sict.add("srcId");
//		sict.add("estimateAmount");
//		sict.add("budgetAmount");
//		sict.add("billid");
//		sict.add("unAuditContractEA");
//		if (flag) {
//			if (relateAmt == null) {
//				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, signAmount));
//				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, signAmount));
//				pcInfo.setSignUpAmount(FDCHelper.add(signUpAmount, signAmount));
//				// ά�����
//				// otherBalance = FDCHelper.subtract(balanceAmt,
//				// FDCHelper.subtract(balanceAmt, signAmount));
//				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
//				// FDCHelper.subtract(controlBalanceAmt, signAmount));
////				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, signAmount), signUpAmount);
//			} else {
//				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, relateAmt));
//				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, relateAmt));
//				pcInfo.setSignUpAmount(FDCHelper.add(signUpAmount, relateAmt));
//				// ά�����
//				// otherBalance = FDCHelper.subtract(balanceAmt,
//				// FDCHelper.subtract(balanceAmt, relateAmt));
//				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
//				// FDCHelper.subtract(controlBalanceAmt, relateAmt));
////				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, relateAmt), signUpAmount);
//			}
//			if(ContractPropertyEnum.SUPPLY.equals(supplierContractBillInfo.getContractPropert())){
//				//�������汨��ŵ�ǰ̨��
////				if(supAmount.compareTo(pcInfo.getEstimateAmount())>0){
////					throw new EASBizException(new NumericExceptionSubItem("100","�����ͬԭ�ҽ���������ͬ��Լ�滮Ԥ����"));
////				}
//				//��Ҫ�ǲ����ͬ���Ѻ�Լ�滮��Ԥ�������Ϊ�㣬
////				BigDecimal  estimateAmount = pcInfo.getEstimateAmount();
////				pcInfo.setUnAuditContractEA(supAmount);
//				pcInfo.setEstimateAmount(FDCHelper.subtract(pcInfo.getEstimateAmount(), supAmount));
//				pcInfo.setBalance(FDCHelper.add(pcInfo.getBalance(), supAmount));
//				
//			}else{
//				pcInfo.setBudgetAmount(FDCHelper.ZERO);
//				pcInfo.setBillId(billIdStr);
//			}
//			
//			service.updatePartial(pcInfo, sict);
//			
////			// ���������ĺ�Լ�滮�汾���
////			String progId = pcInfo.getId().toString();
////			while (progId != null) {
////				String nextVersionProgId = getNextVersionProg(ctx, progId, builder, rowSet);
////				if (nextVersionProgId != null) {
////					pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
////					pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), otherSignUpAmount));
////					pcInfo.setControlBalance(FDCHelper.subtract(pcInfo.getControlBalance(), otherSignUpAmount));
////					pcInfo.setSignUpAmount(FDCHelper.add(pcInfo.getSignUpAmount(), otherSignUpAmount));
////					service.updatePartial(pcInfo, sict);
////					progId = pcInfo.getId().toString();
////				} else {
////					progId = null;
////				}
////			}
//		} else {
//			if (relateAmt == null) {
//				pcInfo.setBalance(FDCHelper.add(balanceAmt, signAmount));
//				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, signAmount));
//				pcInfo.setSignUpAmount(FDCHelper.subtract(signUpAmount, signAmount));
//				// ά�����
//				// otherBalance = FDCHelper.subtract(balanceAmt,
//				// FDCHelper.add(balanceAmt, signAmount));
//				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
//				// FDCHelper.add(controlBalanceAmt, signAmount));
////				otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, signAmount), signUpAmount);
//			} else {
//				pcInfo.setBalance(FDCHelper.add(balanceAmt, relateAmt));
//				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, relateAmt));
//				pcInfo.setSignUpAmount(FDCHelper.subtract(signUpAmount, relateAmt));
//				// ά�����
//				// otherBalance = FDCHelper.subtract(balanceAmt,
//				// FDCHelper.add(balanceAmt, relateAmt));
//				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
//				// FDCHelper.add(controlBalanceAmt, relateAmt));
////				otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, relateAmt), signUpAmount);
//			}
//			if(ContractPropertyEnum.SUPPLY.equals(supplierContractBillInfo.getContractPropert())){
////				BigDecimal  estimateAmount = FDCHelper.ZERO;
////				if(pcInfo.getUnAuditContractEA()!=null){
////					estimateAmount=pcInfo.getUnAuditContractEA();
////				}
//				//��Ҫ�ǲ����ͬ���Ѻ�Լ�滮��Ԥ�������Ϊ�㣬
////				if(!(pcInfo.getEstimateAmount()!=null&&pcInfo.getEstimateAmount().compareTo(FDCHelper.ZERO)>0)){
//					pcInfo.setEstimateAmount(FDCHelper.add(pcInfo.getEstimateAmount(), supAmount));
////				}
//				pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), supAmount));
//				
//				//��Ӧ��Ԥ���䶯����Ϊ�Ƿ�����Ϊfalse
////				FilterInfo filter = new FilterInfo();
////				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
////				filter.getFilterItems().add(new FilterItemInfo("isRespite",Boolean.TRUE));
////				EntityViewInfo view = new EntityViewInfo();
////				view.setFilter(filter);
////				ContractEstimateChangeBillCollection  coll = ContractEstimateChangeBillFactory.getLocalInstance(ctx).getContractEstimateChangeBillCollection(view);
////				if(coll.size()>0){
////					ContractEstimateChangeBillInfo contInfo = coll.get(0);
////					contInfo.setIsLastest(true);
////					contInfo.setIsRespite(false);
////					SelectorItemCollection sel = new SelectorItemCollection();
////					sel.add("isLastest");
////					sel.add("isRespite");
////					ContractEstimateChangeBillFactory.getLocalInstance(ctx).updatePartial(contInfo, sel);
////				}
//			}else{
//				pcInfo.setBudgetAmount(pcInfo.getAmount());
//				pcInfo.setBillId(null);
//			}
//			service.updatePartial(pcInfo, sict);
//			
////			// ���������ĺ�Լ�滮�汾���
////			String progId = pcInfo.getId().toString();
////			while (progId != null) {
////				String nextVersionProgId = getNextVersionProg(ctx, progId, builder, rowSet);
////				if (nextVersionProgId != null) {
////					pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
////					pcInfo.setBalance(FDCHelper.add(pcInfo.getBalance(), otherSignUpAmount));
////					pcInfo.setControlBalance(FDCHelper.add(pcInfo.getControlBalance(), otherSignUpAmount));
////					pcInfo.setSignUpAmount(FDCHelper.subtract(pcInfo.getSignUpAmount(), otherSignUpAmount));
////					service.updatePartial(pcInfo, sict);
////					progId = pcInfo.getId().toString();
////				} else {
////					progId = null;
////				}
////			}
//		}
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
		sql.append(" inner join  ct_con_contractbill CON ON  CSE.FContractBillID = CON.FID  ");
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
		
		String companyID = billInfo.getCurProject().getCompany().getId().toString();
		Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyID);
		boolean isInCore = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INCORPORATION);
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
		FilterInfo filter = new FilterInfo();
//		checkBillForUnAudit(ctx, billId);
		
		filter = new FilterInfo();
		// ��ͬ
		filter.getFilterItems().add(new FilterItemInfo("id", billId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("isArchived", BooleanEnum.TRUE));
		
		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.HASACHIVED);
		}	
		super._unAudit(ctx, billId);
		backBudgetAmount(ctx, new ObjectUuidPK(billId));
		ContractBillInfo info = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(billId));
		createPurContractInfo(ctx, info,BillBaseStatusEnum.ADD);
		// �ڽ���ۼ�֮ǰȡ�����ͬ�Ĳ�����
		// �ڽ���ۼ�֮ǰȡ����ͬ��ǩԼ���
		String mainSignAmount = null;
		try {
			mainSignAmount = getMainSignAmount(ctx, billId);
			synUpdateBillByRelation(ctx, billId, false, mainSignAmount);
		} catch (SQLException e1) {
			logger.error(e1);
		}
		//����ǷǶ�������Ĳ����ͬ������ͬ������
		supplyAdd(ctx, billId,false);
		
//		IContractEstimateChangeBill iFaced =  ContractEstimateChangeBillFactory.getLocalInstance(ctx);
//		  
//			SelectorItemCollection sel = new SelectorItemCollection();
//		   	sel.add("*");
//		   	sel.add("entrys.*");
//		   	ContractBillInfo contractInfo =  ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(billId), sel);
//		    EntityViewInfo view1 = new EntityViewInfo();
//			   FilterInfo Filter1 = new FilterInfo();
//		   if(ContractPropertyEnum.DIRECT.equals(contractInfo.getContractPropert())){//����ͬ �������ͬ������ʱ����������ɾ�����µ�Ԥ����ͬ�����
//			   Filter1.getFilterItems().add(new FilterItemInfo("contractbill.id",billId));
//		   }else if(ContractPropertyEnum.SUPPLY.equals(contractInfo.getContractPropert())){
//			   contractInfo =  ContractBillFactory.getLocalInstance(ctx).getContractBillInfo("select id where number = '"+contractInfo.getMainContractNumber()+"'");
//			   Filter1.getFilterItems().add(new FilterItemInfo("contractbill.id",contractInfo.getId().toString()));
//			  
//		   }
//		   Filter1.getFilterItems().add(new FilterItemInfo("isUsed",Boolean.FALSE));
//		   Filter1.getFilterItems().add(new FilterItemInfo("isLastest",Boolean.TRUE));
//		   view1.setFilter(Filter1);
//		   ContractEstimateChangeBillCollection coll1 = iFaced.getContractEstimateChangeBillCollection(view1);
//		   if(coll1.size()>0){
//			   ContractEstimateChangeBillInfo info1 = coll1.get(0);
//			   iFaced.unAudit(info1.getId());
//			   iFaced.delete(new ObjectUuidPK(info1.getId()));
//		   }
//		   if(ContractPropertyEnum.SUPPLY.equals(contractInfo.getContractPropert())){
			   //�Ѳ����ͬʹ�õ�Ԥ����ͬ�䶯��Ϊ���µģ�δʹ�õġ���� �� Ԥ����ͬ�䶯��� ����
//			   ContractBillEntryCollection entryColl = contractInfo.getEntrys();
//			   ContractBillEntryInfo entryInfo= null;
//			  for(int i = 0 ; i < entryColl.size(); i++){
//				  entryInfo = entryColl.get(i);
//				  if("esId".equalsIgnoreCase(entryInfo.getRowKey()))break;
//			  }
//			   SelectorItemCollection esSel = new SelectorItemCollection();
//			   esSel.add("*");
//			   esSel.add("contractbill.programmingContract.*");
//			   EntityViewInfo view = new EntityViewInfo();
//			   view.setSelector(esSel);
//			   FilterInfo newfilter = new FilterInfo();
//			   view.setFilter(newfilter);
			   //��¼�����ĺ�ͬΪ��ʹ����
//			  ContractEstimateChangeBillInfo esInfo = iFaced.getContractEstimateChangeBillInfo(new ObjectUuidPK(entryInfo.getContent()),esSel);
//			  esInfo.setIsLastest(true);
//			  esInfo.setIsUsed(false);
//			  SelectorItemCollection sel1 = new SelectorItemCollection();
//			  sel1.add("isUsed");
//			  sel1.add("isLastest");
//			  iFaced.updatePartial(esInfo, sel1);
//			
//			  ProgrammingContractInfo programmingInfo =  esInfo.getContractbill().getProgrammingContract();
//			  BigDecimal balance = programmingInfo.getBalance();
//			  BigDecimal esAmount  = programmingInfo.getEstimateAmount();
//			  balance =  balance.subtract(esInfo.getEstimateAmount());
//			  esAmount = esAmount.add(esInfo.getEstimateAmount());
//			  programmingInfo.setBalance(balance);
//			  programmingInfo.setEstimateAmount(esAmount);
//			  SelectorItemCollection pSel = new SelectorItemCollection();
//			  pSel.add("balance");
//			  pSel.add("estimateAmount");
//			  ProgrammingContractFactory.getLocalInstance(ctx).updatePartial(programmingInfo, pSel);
//		   }
//		try {
//			synContractProgAmt(ctx,billId,false);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	

	protected boolean _contractBillStore(Context ctx, IObjectValue cbInfo, String storeNumber) throws BOSException, EASBizException {
		boolean flag = false;
		ContractBillInfo cbi = (ContractBillInfo)cbInfo;
		///////////////////////////////////////////////////////////
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
			String updateNumberSql = "update  ct_con_contractbill set FArchivedNumber=?  where FID = ? ";
			Object[] params = new Object[]{storeNumber,cbi.getId().toString()};
			DbUtil.execute(ctx,updateNumberSql,params);
		}	
					
		//���º�ͬ�鵵״̬�͹鵵����
		String updateNumberSql = "update  ct_con_contractbill set FArchivingNumber=fnumber  where FID = ? ";
		Object[] params = new Object[]{cbi.getId().toString()};
		DbUtil.execute(ctx,updateNumberSql,params);
		
		//���º�ͬ�鵵״̬�͹鵵����
		String sql = "select fid from  ct_con_contractbill where FNumber = ? and FOrgUnitID = ? ";
		 
		params = new Object[]{storeNumber,cbi.getOrgUnit().getId().toString()};
		RowSet rowset = DbUtil.executeQuery(ctx, sql, params);
		 
		boolean hasCodeType =  cbi.getCodeType()!=null;
		if(hasCodeType){
			sql = "update  ct_con_contractbill set  FIsArchived = 1 ,FNumber = ? ,FCodeTypeID=? where FID = ? ";
			params = new Object[]{storeNumber,cbi.getCodeType().getId().toString(),cbi.getId().toString()};
		}else{
			sql = "update  ct_con_contractbill set  FIsArchived = 1 ,FNumber = ? where FID = ? ";
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
	    
		return flag;
	}
	
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("codeType.number"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.company.id"));
        
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
        	ProjectInfo project = ProjectFactory.getLocalInstance(ctx).getProjectInfo(new ObjectUuidPK((info).getCurProject().getId()));
        	curOrgId = project.getCompany().getId().toString();
        	
            if ((info).getCodeType()!=null && iCodingRuleManager.isExist(info, curOrgId,bindingProperty)
            		&& iCodingRuleManager.isUseIntermitNumber(info, curOrgId,bindingProperty)) {
                iCodingRuleManager.recycleNumber(info, curOrgId, bindingProperty,"",info.getNumber());
            }
        	
        }
		
	}
	
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
	
		FilterInfo filter = null;
		int i=0;
		do {
			handleIntermitNumber1(ctx, info);
			
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
		}while (_exists(ctx, filter) && i<100);
	}
	
	protected void handleIntermitNumber1(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
		ContractBillInfo cbi = (ContractBillInfo)info;
		String orgId = cbi.getOrgUnit()!=null? cbi.getOrgUnit().getId().toString():
			ContextUtil.getCurrentCostUnit(ctx).getId().toString();
		String bindingProperty = "codeType.number";
		// �޵�ǰ��֯�����ߵ�ǰ��֯û�����������ü��ŵ�
		if (orgId == null || orgId.trim().length() == 0) {			
			orgId = OrgConstants.DEF_CU_ID;
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		//
		if(!cbi.isIsArchived()){
			//���ñ�������
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ContractTypeInfo cti = cbi.getContractType();
			if(cbi.getContractType()!=null){
				if(cti.getLevel()!=1){
					//ȡһ����ͬ���
					String number = cbi.getContractType().getLongNumber();
					if(number.indexOf("!") != -1) {
						number = number.substring(0,number.indexOf("!"));
					}
					
					cti = ContractTypeFactory.getLocalInstance(ctx).getContractTypeInfo("select id where longNumber = '" + number + "'");
				}
			}
			filter.getFilterItems().add(new FilterItemInfo("contractType.id", cti.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));//���к�ͬ,��Ϊ�����в�֪��ͬ����
			filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.NEW_VALUE));//��ͬ��������״̬
			evi.setFilter(filter);
			ContractCodingTypeCollection cctc = ContractCodingTypeFactory.getLocalInstance(ctx).getContractCodingTypeCollection(evi);
			
			ContractCodingTypeInfo codingType = null;
			ContractCodingTypeInfo codingTypeAll = null;
			if(cctc.size()>0)
				codingType = cctc.get(0);
			
			filter = new FilterInfo();
			
			filter.getFilterItems().add(new FilterItemInfo("contractType.id", null));
			//�½��Ļ������к�ͬ
			filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));
			//����״̬
			filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.NEW_VALUE));
			evi.setFilter(filter);
			cctc = ContractCodingTypeFactory.getLocalInstance(ctx).getContractCodingTypeCollection(evi);
			if(cctc.size()>0)
				codingTypeAll = cctc.get(0); 
			
			/************
			 * ���ں�ͬ�ı�����򣬿��԰��պ�ͬ�������ñ�����򣬻�����һ��"ȫ��"�ı�������
			 * ���ͬʱ������ȫ�����͵������͵ı�����򣬻��г�ͻ
			 * ���������������������δ���
			 * ����ͬ�ı�������Ϊĳһ�����������ʱ������Ĵ�����ȡ�����������Ͷ�Ӧ�ı���������û��ȡ��[˵����û�����ñ�����򣬻�û������]
			 * ����ȡһ��"ȫ��"���Ͷ�Ӧ�ı������
			 * ��: ����ϸ�ĺ�ͬ���ͣ�û�����ñ�������ʱ��ȡȫ�����Ͷ�Ӧ�ı������[���ں�ͬ�޸Ľ��棬�޸ĺ�ͬ����ʱ,���ߴ˴�]
			 */
			/****
			 * �ɱ����ģ�һ�������ж�Ӧ�ı������
			 */
			if(codingType!=null){
				cbi.setCodeType(codingType);
				if(setNumber(ctx,info, cbi, orgId, bindingProperty, iCodingRuleManager))
					return;
			}
			/***
			 * �ɱ����ģ�ȫ�����Ͷ�Ӧ�б������
			 */
			if(codingTypeAll!=null){
				cbi.setCodeType(codingTypeAll);
				if(setNumber(ctx,info, cbi, orgId, bindingProperty, iCodingRuleManager))
					return;
			}
			ProjectInfo curProjectInfo = ProjectFactory.getLocalInstance(ctx).getProjectInfo(new ObjectUuidPK(cbi.getCurProject().getId()));
			orgId = curProjectInfo.getCompany().getId().toString();
			/****
			 * ������֯��һ�������ж�Ӧ�ı������
			 */
			if(codingType!=null){
				cbi.setCodeType(codingType);
				if(setNumber(ctx,info, cbi, orgId, bindingProperty, iCodingRuleManager))
					return;
			}
			/***
			 * ������֯��ȫ�����Ͷ�Ӧ�б������
			 */
			if(codingTypeAll!=null){
				cbi.setCodeType(codingTypeAll);
				if(setNumber(ctx,info, cbi, orgId, bindingProperty, iCodingRuleManager))
					return;
			}
		}
	}

	private boolean setNumber(Context ctx,FDCBillInfo info, ContractBillInfo cbi, String orgId, String bindingProperty, ICodingRuleManager iCodingRuleManager) throws BOSException, EASBizException {
		if (orgId != null && orgId.trim().length() > 0
				&& iCodingRuleManager.isExist(info, orgId, bindingProperty)) {			
			// ���ʹ����"������Ϻ�"
			if (iCodingRuleManager.isUseIntermitNumber(info, orgId, bindingProperty)) {
				String numberTemp = iCodingRuleManager.getNumber(info, orgId, bindingProperty, "");
				info.setNumber(numberTemp);				
			} else if (iCodingRuleManager.isAddView(info, orgId, bindingProperty)){				
				// �ж��Ƿ��޸��˱���,�Ƿ�Ĵ���˳���
				if (iCodingRuleManager.isModifiable(info, orgId, bindingProperty)) {					
					iCodingRuleManager.checkModifiedNumber(info, orgId,info.getNumber(), bindingProperty);
				}				
			} else {
				// ʲô��ûѡ,��������ʾ,����Ϻ�,ҵ�񴫿�numberֵ,�ڴ�����number
//				if(info.getNumber()==null||info.getNumber().equals("")){
					String numberTemp = iCodingRuleManager.getNumber(info,orgId, bindingProperty, "");
					info.setNumber(numberTemp);
//				}
			}
			return true;
		}
		return false;
	}
	
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		setCreatorPosition(ctx, (ContractBillInfo) model);
		IObjectPK objectPK = super._addnew(ctx, model);
		
//		ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
//		ContractBaseDataHelper.synToContractBaseData(ctx, false, objectPK.toString());
		
    	/**��ӷ�дContractBaseDataID�Ĵ��� -by neo**/
//		FDCSQLBuilder builder = new FDCSQLBuilder();
//		builder.appendSql(
//				"update ct_con_contractbill set fcontractbasedataid =" +
//				"(select fid from t_CON_contractbasedata where fcontractid = t_con_contractbill.fid) " +
//				"where");
//		builder.appendParam("fid", objectPK.toString());
//		builder.executeUpdate(ctx);
		return objectPK;
		/**��ӷ�дContractBaseDataID�Ĵ���**/
	}

	protected void _updatePartial(Context ctx, IObjectValue model, SelectorItemCollection selector) throws BOSException, EASBizException {
		super._updatePartial(ctx, model, selector);
		
//		ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
		String id = ((FDCBillInfo)model).getId().toString();
//		ContractBaseDataHelper.synToContractBaseData(ctx, false, id);

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
		builder.appendSql("select fprogrammingContract from  ct_con_contractbill where 1=1 and ");
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
	private void updateProgrammingContract(Context ctx, String proContId, int isCiting) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " ");
		buildSQL.appendSql("where FID = '" + proContId + "' ");
		try {
			buildSQL.executeUpdate();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����Ͽ�ܺ�Լ�Ƿ�����
	 * 
	 * @throws Exception
	 * @throws Exception
	 */
	private void updateOldProg(Context ctx, IObjectPK pk) throws Exception {
		String checkReaPre = checkReaPre(ctx, pk);
		if(checkReaPre!=null){
			updateProgrammingContract(ctx, checkReaPre, 0);
		}
	}

	/**
	 * �ҳ��������Ŀ�ܺ�Լ�ļ�¼��(���ı��Ѿ��ϳ������ٲ���)
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByProg(Context ctx, String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql(" select count(1) count from T_INV_InviteProject ");
		buildSQL.appendSql(" where FProgrammingContractId = '" + proContId + "' ");
		buildSQL.appendSql(" union ");
		buildSQL.appendSql(" select count(1) count from  ct_con_contractbill ");
		buildSQL.appendSql(" where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			while (iRowSet.next()) {
				count += iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			logger.error(e);
		} catch (SQLException e) {
			logger.error(e);
		}
		return count;
	}

	private String isUpdateNextProgState(Context ctx, String progId) throws Exception {
//		String flag = null;
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql(" select fid from t_con_programmingContract where ");
//		builder.appendParam("fSrcId", progId);
//		IRowSet rowSet = builder.executeQuery();
//		while (rowSet.next()) {
//			flag = rowSet.getString("fid").toString();
//		}
		return null;
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

		//����Ƿ񱻱������������
		this._isReferenced(ctx,pk);
		//����ͬɾ����ɾ�����еĺ�ͬԤ���䶯��
		//�����ͬɾ����ɾ����¼������Ԥ����ͬ�䶯�� 20120331
//	 	SelectorItemCollection sel = new SelectorItemCollection();
//	   	sel.add("*");
//	   	sel.add("entrys.*");
//	    IContractEstimateChangeBill iFaced =  ContractEstimateChangeBillFactory.getLocalInstance(ctx);
//	   	ContractBillInfo contractInfo =  ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk, sel);
//	   if(ContractPropertyEnum.DIRECT.equals(contractInfo.getContractPropert())){//����ͬ ɾ����ɾ���������Ԥ����ͬ�䶯��,��Լ�滮��Ԥ���������
//		   EntityViewInfo view1 = new EntityViewInfo();
//		   FilterInfo Filter1 = new FilterInfo();
//		   Filter1.getFilterItems().add(new FilterItemInfo("contractbill.id",pk.toString()));
//		   view1.setFilter(Filter1);
//		   ContractEstimateChangeBillCollection coll1 = iFaced.getContractEstimateChangeBillCollection(view1);
//		   for(int i = 0 ; i < coll1.size() ; i++){
//			   ContractEstimateChangeBillInfo info1 = coll1.get(i);
//			   iFaced.delete(new ObjectUuidPK(info1.getId()));
//		   }
//	   }else if(ContractPropertyEnum.SUPPLY.equals(contractInfo.getContractPropert())){//�����ͬ ɾ����ɾ��Ԥ����ͬ�䶯��,��Լ�滮��Ԥ���������
//		   ContractBillEntryCollection entryColl = contractInfo.getEntrys();
//		   ContractBillEntryInfo entryInfo= null;
//		  for(int i = 0 ; i < entryColl.size(); i++){
//			  entryInfo = entryColl.get(i);
//			  if("esId".equalsIgnoreCase(entryInfo.getRowKey())){
//				  EntityViewInfo view1 = new EntityViewInfo();
//				   FilterInfo Filter1 = new FilterInfo();
//				   Filter1.getFilterItems().add(new FilterItemInfo("suppyContractId",entryInfo.getContent()));
//				   view1.setFilter(Filter1);
//				   ContractEstimateChangeBillCollection coll1 = iFaced.getContractEstimateChangeBillCollection(view1);
//				   for(int j = 0 ; j < coll1.size() ; j++){
//					   ContractEstimateChangeBillInfo info1 = coll1.get(j);
//					   iFaced.delete(new ObjectUuidPK(info1.getId()));
//				   }
//				  
//				  break;
//			  }
//		  }
//			   
//	   }
		
//		//�������ɺ�ͬ״̬�仯��ʱ�����б�֪ͨ����б�Ԥ���
//		ContractBillAcceptanceLetterHelper.acceptanceLetterDeleteHelper(ctx, pk);
		try {
			SelectorItemCollection sel = new SelectorItemCollection();
		   	sel.add("contractPropert");
			ContractBillInfo contractInfo=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk, sel);
			if(!ContractPropertyEnum.SUPPLY.equals(contractInfo.getContractPropert())){
				updateOldProg(ctx,pk);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		
/*		//ͬ��ɾ����ͬ��������
		String sql = "delete t_con_contractbasedata where fcontractid = ?";
		
		DbUtil.execute(ctx, sql, new Object[]{pk.toString()});*/
		//ormap ɾ����ʱ��������ù�ϵ
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("contractId", pk.toString());
		ContractBaseDataFactory.getLocalInstance(ctx).delete(filter);
		
//		//ɾ�� ������ͬ�������Լ�滮
//		SelectorItemCollection sel = new SelectorItemCollection();
//	   	sel.add("*");
//	   	sel.add("programmingContract.*");
//	   	ContractBillInfo contractInfo =  ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk, sel);
//		ProgrammingContractInfo proInfo = contractInfo.getProgrammingContract();
//		if(proInfo!=null){
//			BigDecimal balance = proInfo.getBalance();
//			balance = balance.add(contractInfo.getAmount());
//			IProgrammingContract iFaced = ProgrammingContractFactory.getLocalInstance(ctx);
//			SelectorItemCollection sel1 = new SelectorItemCollection();
//			sel1.add("balance");
//			proInfo.setBalance(balance);
//			iFaced.updatePartial(proInfo, sel1);
//		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String sql = "SELECT FID FROM T_CON_CONTRACTCONTENT WHERE FCONTRACTID = '"+pk.toString()+"'";
		builder.appendSql(sql);
		IRowSet rowSet = builder.executeQuery();
		Set idSet = new HashSet();
		try {
			while(rowSet.next()){
				idSet.add(rowSet.getString("FID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(idSet.size()>0){
			FilterInfo delfilter = new FilterInfo();
			delfilter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
			ContractContentFactory.getLocalInstance(ctx).delete(delfilter);
		}
		
		SelectorItemCollection sel = new SelectorItemCollection();
	   	sel.add("agreementID");
		ContractBillInfo contractInfo=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk, sel);
		if(contractInfo.getAgreementID()!=null){
			deleteAttachment(ctx,contractInfo.getAgreementID());
		}
		deleteAttachment(ctx,pk.toString());
		super._delete(ctx, pk);	
	}
	protected void deleteAttachment(Context ctx,String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(view);
		if(col.size()>0){
			for(int i=0;i<col.size();i++){
				EntityViewInfo attview=new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();
				
				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol=BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(attview);
				if(attcol.size()==1){
					BizobjectFacadeFactory.getLocalInstance(ctx).delTempAttachment(id);
				}else if(attcol.size()>1){
					BoAttchAssoFactory.getLocalInstance(ctx).delete(filter);
				}
			}
		}
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
		String sql = "select FContent from  ct_con_contractbillentry where fparentid = ? and FRowkey = ?";
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
		ProjectInfo curProjectInfo = null;
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
			selector.add("curProject.fullOrgUnit.name");
			selector.add("curProject.fullOrgUnit.code");
			selector.add("curProject.CU.name");
			selector.add("curProject.CU.number");
			selector.add("curProject.CU.code");
			selector.add("curProject.landDeveloper.number");
			selector.add("curProject.landDeveloper.name");
			selector.add("curProject.costCenter.number");
			selector.add("curProject.costCenter.name");
			selector.add("curProject.costCenter.code");
			selector.add("curProject.costCenter.displayName");
			selector.add("curProject.costCenter.longNumber");
			selector.add("curProject.isWholeAgeStage");
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
			selector.add("costCenter.number");
			selector.add("costCenter.name");
			selector.add("costCenter.displayName");
			selector.add("costCenter.longNumber");
			selector.add("CU.name");
			selector.add("CU.number");
			selector.add("CU.code");
			selector.add("isWholeAgeStage");
			curProjectInfo = ProjectFactory.getLocalInstance(ctx).getProjectInfo(new ObjectUuidPK(projectId),selector);
		}
		initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
		
//		super.initProject(ctx, paramMap,initMap);
	}

	protected boolean _contractBillAntiStore(Context ctx, List idList) throws BOSException, EASBizException {
	
		String sql = "update  ct_con_contractbill set  FIsArchived = 0 where FID = ? ";
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
		
		HashMap param = FDCUtils.getDefaultFDCParam(ctx,contractBillInfo.getOrgUnit().getId().toString());		
		boolean splitBeforeAudit = false;
		if(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT)!=null){
			splitBeforeAudit = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString()).booleanValue();
			
			if(!splitBeforeAudit){
				return true;
			}
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
		
		String contractid=pk.toString();
		com.kingdee.eas.fdc.basedata.FDCSQLBuilder builder=new com.kingdee.eas.fdc.basedata.FDCSQLBuilder(ctx);
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
	    builder.appendSql("select sup.fid from  ct_con_contractbill sup ");
	    builder.appendSql("inner join  ct_con_contractbillentry supe on supe.fparentid=sup.fid ");
	    builder.appendSql("inner join  ct_con_contractbill main on main.fid=supe.fcontent ");
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
	    		super._cancel(ctx,  new ObjectUuidPK(BOSUuid.read(supId)));
	    		
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
		
		super._cancel(ctx, pk);
	}
	protected void _synUpdateProgramming(Context ctx, String billId,IObjectValue programming) throws BOSException, EASBizException {
		if(programming==null) return;
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("amount");
		ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx, new ObjectUuidPK(billId), sel);
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo)programming;
		
		BigDecimal pcBalance = pcInfo.getBalance();
		BigDecimal pcSignUpAmount=pcInfo.getSignUpAmount();
		BigDecimal wtAmount=pcInfo.getSignUpAmount();
		BigDecimal amount = contractBillInfo.getAmount();
		
		pcInfo.setBalance(FDCHelper.subtract(pcBalance, amount));
		pcInfo.setBudgetAmount(FDCHelper.ZERO);
		pcInfo.setSignUpAmount(FDCHelper.add(pcSignUpAmount, amount));
		pcInfo.setBillId(billId);
		
		SelectorItemCollection ecSel = new SelectorItemCollection();
		ecSel.add("estimateAmount");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(pcInfo.getSrcId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("isLastest",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getSrcId()));
		    view.setFilter(filter);
		    view.setSelector(ecSel);
		    ContractEstimateChangeBillCollection ecCol = ContractEstimateChangeBillFactory.getLocalInstance(ctx).getContractEstimateChangeBillCollection(view);
		    if(ecCol.size()>0){
		    	ContractEstimateChangeBillInfo ecBill = ecCol.get(0);
			    BigDecimal estimateAmount = ecBill.getEstimateAmount();
//			    pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), estimateAmount));
			    pcInfo.setEstimateAmount(estimateAmount);
		    }
		}
		SelectorItemCollection ccbsel = new SelectorItemCollection();
		ccbsel.add("amount");
		ccbsel.add("exRate");
		ccbsel.add("state");
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",billId));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.ANNOUNCE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.VISA_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3)");
		view.setFilter(filter);
		view.setSelector(ccbsel);
		ContractChangeBillCollection col=ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
		for(int i=0;i<col.size();i++){
			BigDecimal changeAmount = FDCHelper.ZERO;
			
			SelectorItemCollection cssel = new SelectorItemCollection();
			cssel.add("allowAmount");
			EntityViewInfo csview=new EntityViewInfo();
			FilterInfo csfilter=new FilterInfo();
			csfilter.getFilterItems().add(new FilterItemInfo("conChangeBill.id",col.get(i).getId().toString()));
			csfilter.getFilterItems().add(new FilterItemInfo("contractBill.id",billId));
			csfilter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			csview.setFilter(csfilter);
			csview.setSelector(cssel);
			
			ContractChangeSettleBillCollection settlecol=ContractChangeSettleBillFactory.getLocalInstance(ctx).getContractChangeSettleBillCollection(csview);
			if(settlecol.size()>0){
	        	changeAmount=settlecol.get(0).getAllowAmount().multiply(col.get(i).getExRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
			}else{
				changeAmount=col.get(i).getAmount();
			}
			pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), changeAmount));
			pcInfo.setChangeAmount(FDCHelper.add(pcInfo.getChangeAmount(), changeAmount));
			pcInfo.setEstimateAmount(FDCHelper.subtract(pcInfo.getEstimateAmount(), changeAmount));
		}
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			IRowSet rowSet = null;
			builder.appendSql(" select entry.fcontent as amount,con.fid as id from  ct_con_contractbillentry entry inner join  ct_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
			builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join  ct_con_contractbill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
			builder.appendSql(" where  entry.FRowkey='am' and con.fstate='4AUDITTED' and ");
			builder.appendParam(" parent.fid", billId);
			rowSet = builder.executeQuery();
			
			while (rowSet.next()) {
				String supplyId=rowSet.getString("id");
				pcInfo.setEstimateAmount(FDCHelper.subtract(pcInfo.getEstimateAmount(), rowSet.getBigDecimal("amount")));
				SelectorItemCollection supplySel=new SelectorItemCollection();
				supplySel.add("programmingContract");
				ContractBillInfo supply=new ContractBillInfo();
				supply.setId(BOSUuid.read(supplyId));
//				supply.setProgrammingContract(pcInfo);
				ContractBillFactory.getLocalInstance(ctx).updatePartial(supply, supplySel);
				
				pcInfo.setIsWTCiting(true);
			}
		} catch (UuidException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SelectorItemCollection seesel = new SelectorItemCollection();
		seesel.add("curSettlePrice");
		view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",billId));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		view.setFilter(filter);
		view.setSelector(seesel);
		ContractSettlementBillCollection seecol=ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(view);
		for(int i=0;i<seecol.size();i++){
			pcInfo.setBalance(FDCHelper.subtract(pcInfo.getAmount(),FDCHelper.add(wtAmount, seecol.get(i).getCurSettlePrice())));
			if(pcInfo.getSettleAmount()!=null){
				pcInfo.setSettleAmount(FDCHelper.add(pcInfo.getSettleAmount(), seecol.get(i).getCurSettlePrice()));
			}else{
				pcInfo.setSettleAmount(seecol.get(0).getCurSettlePrice());
			}
			pcInfo.setEstimateAmount(FDCHelper.ZERO);
		}
		pcInfo.setIsCiting(true);
		
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("budgetAmount");
		sict.add("signUpAmount");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("estimateAmount");
		sict.add("isCiting");
		sict.add("isWTCiting");
		sict.add("billId");
	    service.updatePartial(pcInfo, sict);
	}
	protected void _synReUpdateProgramming(Context ctx, String billId, IObjectValue programming) throws BOSException, EASBizException {
		if(programming==null) return;
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo)programming;
		
		BigDecimal pcAmount=pcInfo.getAmount();
		
		SelectorItemCollection wtSel = new SelectorItemCollection();
		wtSel.add("amount");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		view.setFilter(filter);
		view.setSelector(wtSel);
		ContractWithoutTextCollection col=ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(view);
		BigDecimal amount=FDCHelper.ZERO;
		for(int i=0;i<col.size();i++){
			amount =FDCHelper.add(amount,col.get(i).getAmount()) ;
		}
		pcInfo.setBalance(FDCHelper.subtract(pcAmount, amount));
		if(pcInfo.getBalance().compareTo(pcAmount)==0){
			pcInfo.setBudgetAmount(pcInfo.getAmount());
		}
		pcInfo.setSignUpAmount(amount);
		
		pcInfo.setChangeAmount(FDCHelper.ZERO);
		pcInfo.setSettleAmount(FDCHelper.ZERO);
		pcInfo.setEstimateAmount(FDCHelper.ZERO);
		
		pcInfo.setIsCiting(false);
		pcInfo.setBillId(null);
		
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("budgetAmount");
		sict.add("signUpAmount");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("estimateAmount");
		sict.add("isCiting");
		sict.add("billId");
		service.updatePartial(pcInfo, sict);
		
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			IRowSet rowSet = null;
			builder.appendSql(" select entry.fcontent as amount,con.fid as id from  ct_con_contractbillentry entry inner join  ct_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
			builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join  ct_con_contractbill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
			builder.appendSql(" where  entry.FRowkey='am' and con.fstate='4AUDITTED' and ");
			builder.appendParam(" parent.fid", billId);
			rowSet = builder.executeQuery();
			
			while (rowSet.next()) {
				String supplyId=rowSet.getString("id");
				SelectorItemCollection supplySel=new SelectorItemCollection();
				supplySel.add("programmingContract");
				ContractBillInfo supply=new ContractBillInfo();
				supply.setId(BOSUuid.read(supplyId));
//				supply.setProgrammingContract(null);
				ContractBillFactory.getLocalInstance(ctx).updatePartial(supply, supplySel);
			}
		} catch (UuidException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void _costIndex(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		
	}
}