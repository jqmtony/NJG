package com.kingdee.eas.fdc.contract.programming.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.programming.ProgrammingCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanFactory;
import com.kingdee.eas.fdc.finance.IPayPlanNew;
import com.kingdee.eas.fdc.finance.PayPlanNewCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewFactory;
import com.kingdee.eas.fdc.finance.PayPlanNewInfo;
import com.kingdee.eas.fdc.invite.IInviteProject;
import com.kingdee.eas.fdc.invite.InviteProjectCollection;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class ProgrammingControllerBean extends AbstractProgrammingControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.programming.app.ProgrammingControllerBean");
    
	private SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("version");
		sic.add("versionGroup");
		sic.add("aimCost.versionName");
		sic.add("aimCost.versionNumber");
		sic.add("state");
		sic.add("creator.name");
		sic.add("creator.createTime");
		sic.add("lastUpdateUser.name");
		sic.add("lastUpdateUser.lastUpdateTime");
		sic.add("isLatest");
		sic.add("entries.id");
		sic.add("entries.srcId");
		return sic;
	}

	protected boolean _isLastVersion(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		if (pk == null) {
			throw new BOSException("pk is empty");
    	}
		
		ProgrammingInfo info = getProgrammingInfo(ctx, pk, getSelectors());
		return info.isIsLatest();
	}
	
	protected IObjectValue _getLastVersion(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		if (pk == null) {
			throw new BOSException("pk is empty");
    	}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("versionGroup");
    	return _getLastVersion(ctx, getProgrammingInfo(ctx, pk, sic).getVersionGroup());
	}

	protected IObjectValue _getLastVersion(Context ctx, String versionGroup) throws BOSException, EASBizException {
		if (StringUtils.isEmpty(versionGroup)) {
			return new ProgrammingInfo();
		}
		
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("versionGroup", versionGroup));
    	filter.getFilterItems().add(new FilterItemInfo("isLatest", Boolean.TRUE));
    	EntityViewInfo evi = new EntityViewInfo();
    	evi.setSelector(getSelectors());
    	evi.setFilter(filter);
    	
    	ProgrammingCollection collection = getProgrammingCollection(ctx, evi);
    	return collection == null || collection.isEmpty() ? null : collection.get(0);
	}
	
	/**
	 * �õ���һ�汾
	 * @param ctx
	 * @param model
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private IObjectValue getPreVersion(Context ctx, IObjectValue model) 
	throws BOSException, EASBizException {
		if (model == null) {
			return new ProgrammingInfo(); 
		}
		
		ProgrammingInfo info = (ProgrammingInfo) model;
		BigDecimal version = info.getVersion();
		String versionGroup = info.getVersionGroup();
		if (version == null || versionGroup == null) {
			IObjectPK pk = new ObjectUuidPK(info.getId());
			info = getProgrammingInfo(ctx, pk, getSelectors());
			version = info.getVersion();
			versionGroup = info.getVersionGroup();
		}
		if (version.compareTo(FDCHelper.ONE) == 0) {
			return model;
		}
		
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("versionGroup", versionGroup));
    	//filter.getFilterItems().add(new FilterItemInfo("version", version.subtract(FDCHelper.ONE)));
    	filter.getFilterItems().add(new FilterItemInfo("version", version, CompareType.LESS));
  
    	EntityViewInfo evi = new EntityViewInfo();
    	evi.setSelector(getSelectors());
    	evi.setFilter(filter);
    	
    	/* modified by zhaoqin for R140507-0295 on 2014/05/13 start */
    	SorterItemInfo sorterVsersion = new SorterItemInfo("version");
		sorterVsersion.setSortType(SortType.DESCEND);
    	evi.getSorter().add(sorterVsersion);
    	evi.setTopCount(1);
    	/* modified by zhaoqin for R140507-0295 on 2014/05/13 end */
    	
    	ProgrammingCollection collection = getProgrammingCollection(ctx, evi);
    	return collection == null || collection.isEmpty() ? null : collection.get(0);
	}
	
	protected void _setAuttingForWF(Context ctx, BOSUuid pk) throws BOSException, EASBizException {
		ProgrammingInfo billInfo = null;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		billInfo=(ProgrammingInfo)super.getValue(ctx, new ObjectUuidPK(pk),selector);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		ProgrammingFactory.getLocalInstance(ctx).updatePartial(billInfo, selector);
	}
	
	public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		ProgrammingInfo billInfo = ProgrammingFactory.getLocalInstance(ctx).getProgrammingInfo(new ObjectUuidPK(billId));
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);
	}
	
	protected void _checkNameBlank(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//������������ʾ�����û���У��ʱ����ͨ���Ļ��������Ч��ȥ�����Ը��ǵ����෽����У����ǰ̨���
	}
	
	 protected void _checkNameDup(Context ctx , IObjectValue model) throws BOSException , EASBizException {
		//������������ʾ�����û���У��ʱ����ͨ���Ļ��������Ч��ȥ�����Ը��ǵ����෽����У����ǰ̨���
	 }
	    
	
    protected void trimName(FDCBillInfo fDCBillInfo) {
		if(fDCBillInfo.getName() != null) {
			fDCBillInfo.setName(fDCBillInfo.getName().trim());
		}
	}
    
	protected IObjectPK _billModify(Context ctx, String versionGroup, String curVersion) throws BOSException,
			EASBizException {
		return null;
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ProgrammingInfo info = (ProgrammingInfo)model;
		info.getVersionGroup();
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx, "select fid from t_con_programming where FVersionGroup = '"+info.getVersionGroup()+"'");
		IRowSet rs = sql.executeQuery();
		try {
			if(!rs.next()){
				info.setIsLatest(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
		ProgrammingContractCollection eColl = info.getEntries();
		for (int i = 0; eColl != null && i < eColl.size(); i++) {
			ProgrammingContractInfo eInfo = eColl.get(i);
			boolean isBaseVersion = info.getVersion().compareTo(FDCHelper.ONE) == 0;
			//��ʼ��baseid����������汾
			if (isBaseVersion) {
				if (eInfo.getId() == null) {
					eInfo.setId(BOSUuid.create(eInfo.getBOSType()));
				}
				eInfo.setBaseId(eInfo.getId().toString());
			} else if (eInfo.getBaseId() == null && eInfo.getSrcId() == null) {
				if (eInfo.getId() == null) {
					eInfo.setId(BOSUuid.create(eInfo.getBOSType()));
				}
				eInfo.setBaseId(eInfo.getId().toString());
			} else if (eInfo.getBaseId() == null && eInfo.getSrcId() != null) {
				ProgrammingContractInfo lInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(
						new ObjectUuidPK(eInfo.getSrcId()));
				eInfo.setBaseId(lInfo.getBaseId());
			}
		}
		
		IObjectPK objectPK = super._submit(ctx, model);
		insertPayPLan(info, ctx);
		
		dynamicUpdateAssigned(ctx, info);	// modified by zhaoqin on 2013/11/08
			
		return objectPK;
	}
	
	/**
	 * ������
	 * @param info 
	 * @param ctx 
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author��keyan_zhao
	 * @CreateTime��2013-8-23
	 */
	private void insertPayPLan(ProgrammingInfo info, Context ctx) throws EASBizException, BOSException {
		ProgrammingContractCollection eColl = info.getEntries();
		IPayPlanNew localInstance = PayPlanNewFactory.getLocalInstance(ctx);
		for (int i = 0; eColl != null && i < eColl.size(); i++) {
			ProgrammingContractInfo eInfo = eColl.get(i);
			eInfo.setProgramming(info);
			//����ϸ��Լ�滮ɾ������滮
			if (eInfo.containsKey("deletePayPlan")) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("programming.id", eInfo.getId().toString()));
				localInstance.delete(filter);
			} else if (eInfo.containsKey("PayPlan")) {
				PayPlanNewInfo tInfo = (PayPlanNewInfo) eInfo.get("PayPlan");
				tInfo.setProgramming(eInfo);
				localInstance.submit(tInfo);
			} else {
				EntityViewInfo evi = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				if(eInfo.getId()!=null){
					filter.getFilterItems().add(new FilterItemInfo("programming.id", eInfo.getId().toString()));
					if(localInstance.exists(filter)){
						continue;
					}
				}
				
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("programming.id", eInfo.getSrcId()));
				evi.setFilter(filter);

				evi.setSelector(getPayPlanNewSelectors());

				PayPlanNewCollection coll = localInstance.getPayPlanNewCollection(evi);

				if (coll != null && coll.size() > 0) {
					PayPlanNewInfo tInfo = coll.get(0);
					tInfo.setProgramming(eInfo);
					localInstance.submit(tInfo);
				}
			}
			
			
			
		}
	}

	public SelectorItemCollection getPayPlanNewSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("Data.*");
		sic.add("BySchedule.Task.task.name");
		sic.add("BySchedule.Task.task.start");
		sic.add("BySchedule.Task.task.end");
		sic.add("BySchedule.TaskName.*");
		sic.add("BySchedule.Dataz.*");
		sic.add("ByBuilding.curBuilding.*");
		sic.add("ByBuilding.*");
		sic.add("ByBuilding.Datas.*");
		sic.add("ByMonth.*");
		sic.add("ByMonth.costAccount.number");
		sic.add("BySchedule.paymentType.*");
		sic.add("BySchedule.costAccount.*");
		sic.add("BySchedule.*");
		sic.add("ByMonth.costAccount.*");
		return sic;
	}
	
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ProgrammingInfo info = (ProgrammingInfo) model;
		info.getVersionGroup();
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx, "select fid from t_con_programming where FVersionGroup = '" + info.getVersionGroup() + "'");
		IRowSet rs = sql.executeQuery();
		try {
			if (!rs.next()) {
				info.setIsLatest(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		ProgrammingContractCollection eColl = info.getEntries();
		for (int i = 0; eColl != null && i < eColl.size(); i++) {
			ProgrammingContractInfo eInfo = eColl.get(i);
			boolean isBaseVersion = info.getVersion().compareTo(FDCHelper.ONE) == 0;
			//��ʼ��baseid����������汾
			if (isBaseVersion) {
				if (eInfo.getId() == null) {
					eInfo.setId(BOSUuid.create(eInfo.getBOSType()));
				}
				eInfo.setBaseId(eInfo.getId().toString());
			} else if (eInfo.getBaseId() == null && eInfo.getSrcId() == null) {
				if (eInfo.getId() == null) {
					eInfo.setId(BOSUuid.create(eInfo.getBOSType()));
				}
				eInfo.setBaseId(eInfo.getId().toString());
			} else if (eInfo.getBaseId() == null && eInfo.getSrcId() != null) {
				ProgrammingContractInfo lInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(
						new ObjectUuidPK(eInfo.getSrcId()));
				eInfo.setBaseId(lInfo.getBaseId());
			}
		}
		IObjectPK objectPK = super._save(ctx, model);
		insertPayPLan(info, ctx);
		
		dynamicUpdateAssigned(ctx, info);	// modified by zhaoqin on 2013/11/08
		updateHasHappenAmount(ctx, objectPK.toString());	// modified by zhaoqin for R140402-0042 on 2014/05/26
		return objectPK;
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		
		// modified by zhaoqin for R140402-0042 on 2014/05/26
		updateHasHappenAmount(ctx, billId.toString());
		
		ProgrammingInfo info = getProgrammingInfo(ctx, new ObjectUuidPK(billId), getSelectors());
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update t_con_programming set FIsLatest = 1 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		String versionGroup = info.getVersionGroup();
		
		/* modified by zhaoqin for R140507-0295 on 2014/05/13 start */
		//BigDecimal version = info.getVersion().subtract(new BigDecimal("1"));
		sql.setLength(0);
		sql.append("update t_con_programming set FIsLatest = 0 where FVersionGroup = '");
		sql.append(versionGroup).append("' ");
		//sql.append("and FVersion <= ").append(version.toString());
		sql.append("and FVersion < ").append(info.getVersion().toString());
		/* modified by zhaoqin for R140507-0295 on 2014/05/13 end */
		
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
		
		if (!info.getEntries().isEmpty()) {
			ProgrammingContractCollection currentEntries = info.getEntries();
			info = (ProgrammingInfo) getPreVersion(ctx, info);
			ProgrammingContractCollection entries = info.getEntries();
			
			if (!entries.isEmpty()) {
				Set entriesIdSet = new HashSet();
				for (int i = 0, size = entries.size(); i < size; i++) {
					entriesIdSet.add(entries.get(i).getId().toString());
				}
				String entriesIds = FDCUtils.buildBillIds(entriesIdSet);
				
				//�����޶���Լ�滮����������Լ�滮��ԭ����ͬ�Ѿ����õĺ�Լ�滮���ܹ��������µĺ�Լ�滮�ˣ����������±���ͬ���õĺ�Լ�滮
				//�˴�Ϊʲôֻ�����˺�ͬ�����srcProID����û�и���programmingContract
				resetContractSrcPro(ctx, entriesIds, currentEntries);
				resetInviteSrcPro(ctx, entriesIds, currentEntries);
			}
		}
	}

	/**
	 * �������ͬ���õĿ�ܺ�Լ��Ҫ����Ϊ�޶�����°汾
	 * @param ctx
	 * @param entriesIds - ��һ�汾��ܺ�Լ
	 * @param currentEntries - ��ǰ�汾��ܺ�Լ
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private void resetContractSrcPro(Context ctx, String entriesIds, ProgrammingContractCollection currentEntries)
	throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		
		/* modified by zhaoqin for R140507-0196,R131119-0321,R131127-0389 on 2014/05/14 start */
		Set pcIds = new HashSet();	// ��һ�汾�й�����ͬ�Ŀ�ܺ�Լid
		//sql.append("select id, srcproid where srcProID in ").append(entriesIds);
		sql.append("select id, srcproid, programmingContract where programmingContract in ").append(entriesIds);
		IContractBill contractService = ContractBillFactory.getLocalInstance(ctx);
		ContractBillCollection contractCollection = contractService.getContractBillCollection(sql.toString());
		if (contractCollection.isEmpty()) {
			return;
		}
		for (int i = 0; i < currentEntries.size(); i++) {
			ProgrammingContractInfo currentEntry = currentEntries.get(i);
			for (int j = 0; j < contractCollection.size(); j++) {
				ContractBillInfo contract = contractCollection.get(j);
				String progContractId = contract.getProgrammingContract().getId().toString();
				//if (contract.getSrcProID().equals(currentEntry.getSrcId())) {
				if (progContractId.equals(currentEntry.getSrcId())) {
					pcIds.add(currentEntry.getId().toString());
					//contract.setSrcProID(currentEntry.getId().toString())
					contract.setSrcProID(progContractId);
					contract.setProgrammingContract(currentEntry);
					contractService.updatePartial(contract, getresetSic());
					ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(contract.getId().toString(),false);
				}
			}
		}
		updateProIsCiting(ctx, pcIds);
		/* modified by zhaoqin for R140507-0196,R131119-0321,R131127-0389 on 2014/05/14 end */
	}
	
	/**
	 * �������б��������õĿ�ܺ�Լ��Ҫ����Ϊ�޶�����°汾
	 * @param ctx
	 * @param billId
	 * @param entries
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private void resetInviteSrcPro(Context ctx, String entriesIds, ProgrammingContractCollection currentEntries)
	throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		
		/* modified by zhaoqin for R140507-0196,R131119-0321,R131127-0389 on 2014/05/14 start */
		Set pcIds = new HashSet();	// ��һ�汾�й����б�����Ŀ�ܺ�Լid	
		//sql.append("select id, srcproid where srcProID in ").append(entriesIds);
		sql.append("select id, srcproid, programmingContract where programmingContract in ").append(entriesIds);
		IInviteProject inviteService = InviteProjectFactory.getLocalInstance(ctx);
		InviteProjectCollection inviteCollection = inviteService.getInviteProjectCollection(sql.toString());
		if (inviteCollection.isEmpty()) {
			return;
		}
		for (int i = 0; i < currentEntries.size(); i++) {
			ProgrammingContractInfo currentEntrie = currentEntries.get(i);
			for (int j = 0; j < inviteCollection.size(); j++) {
				InviteProjectInfo invite = inviteCollection.get(j);
				String progContractId = invite.getProgrammingContract().getId().toString();
				//if (invite.getSrcProID().equals(currentEntrie.getSrcId())) {
				if (progContractId.equals(currentEntrie.getSrcId())) {
					pcIds.add(currentEntrie.getId().toString());
					//invite.setSrcProID(currentEntrie.getId().toString());
					invite.setSrcProID(progContractId);
					/* modified by zhaoqin for R140507-0196,R131119-0321,R131127-0389 on 2014/05/14 end */
					invite.setProgrammingContract(currentEntrie);
					inviteService.updatePartial(invite, getresetSic());
				}
			}
		}
		updateProIsCiting(ctx, pcIds);
		/* modified by zhaoqin for R140507-0196,R131119-0321,R131127-0389 on 2014/05/14 end */
	}
	
	private SelectorItemCollection getresetSic() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("srcProID");
		/* modified by zhaoqin for R140507-0196,R131119-0321,R131127-0389 on 2014/05/14 */
		//sic.add("programmingContract.id");
		sic.add("programmingContract");
		return sic;
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		String sql_Query = "select fid from T_CON_ProgrammingContract where FProgrammingID = '"+billId.toString()+"' and FIsCiting = 1";
		FDCSQLBuilder checkSQL = new FDCSQLBuilder(ctx);
		checkSQL.appendSql(sql_Query);
		IRowSet rs = checkSQL.executeQuery();
		try {
			if(rs.next()){
				throw new EASBizException(new NumericExceptionSubItem("1", "�����Ѿ������õĿ�ܺ�Լ��������������"));
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new BOSException(e);
		}
		
		super._unAudit(ctx, billId);
		ProgrammingInfo info = getProgrammingInfo(ctx, new ObjectUuidPK(billId), getSelectors());
		String versionGroup = info.getVersionGroup();
		
		/* modified by zhaoqin for R140507-0295 on 2014/05/13 start */
		//BigDecimal version = info.getVersion();
		//version = version.subtract(new BigDecimal("1"));
		BigDecimal version = getPreVersionNumber(ctx, info);
		/* modified by zhaoqin for R140507-0295 on 2014/05/13 end */
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update t_con_programming set FIsLatest = 0 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		/* modified by zhaoqin for R140507-0295 on 2014/05/15 */
		if(null != version) {
			sql.setLength(0);
			sql.append("update t_con_programming set FIsLatest = 1 where FVersionGroup = '");
			sql.append(versionGroup).append("' ");
			sql.append("and FVersion = ").append(version.toString());
			fdcSB.addBatch(sql.toString());
		}
		
		fdcSB.executeBatch();
	}
	
	
	/**
	 * ��̬���� ������Լ�滮 �ĳɱ�����"������"ֵ
	 * @author zhaoqin
	 * @date 2013/11/8
	 */
	private void dynamicUpdateAssigned(Context ctx, ProgrammingInfo info) throws BOSException  {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		Set costAccoutIds = new HashSet();
		String projectid = info.getProject().getId().toString();
		ProgrammingContractCollection pclist = info.getEntries();
		for(int i = 0; i < pclist.size(); i++) {
			ProgrammingContractInfo pcInfo = pclist.get(i);
			ProgrammingContracCostCollection pcclist =  pcInfo.getCostEntries();
			for(int j = 0; j < pcclist.size(); j++) {
				ProgrammingContracCostInfo pccInfo = pcclist.get(j);
				CostAccountInfo caInfo = pccInfo.getCostAccount();
				String costAccountId = caInfo.getId().toString();
				if(costAccoutIds.contains(costAccountId))	// ����Լ�滮����ͬ��ĿֻҪִ��һ��
					continue;
				costAccoutIds.add(costAccountId);
				StringBuffer sql = new StringBuffer();
				sql.append("update T_CON_ProgrammingContracCost as pccost set ");
				sql.append("FASSIGNED = (FGOALCOST*(select sum(cost.FContractScale) as assignedScale");
				sql.append(" from T_CON_ProgrammingContracCost as cost");
				sql.append(" join T_CON_ProgrammingContract as con on con.fid= cost.FContractID");
				sql.append(" join T_CON_Programming as pro on pro.fid = con.FProgrammingID");
				sql.append(" join (select max(pro.fversion) as fver,pro.fprojectid as proid");
				sql.append("  from T_CON_ProgrammingContracCost as cost");
				sql.append("  join T_CON_ProgrammingContract as con on con.fid= cost.FContractID");
				sql.append("  join T_CON_Programming as pro on pro.fid = con.FProgrammingID");
				sql.append("  where cost.fcostAccountid='").append(costAccountId).append("' group by pro.fprojectid");
				sql.append(" ) t on t.fver=pro.fversion and t.proid = pro.fprojectid");
				sql.append(" where cost.fid <> pccost.FID");
				sql.append(" and cost.fcostAccountid='").append(costAccountId).append("')/100), ");
				sql.append("FASSIGNING = (FGOALCOST*(100-(select sum(cost.FContractScale) as assignedScale");
				sql.append(" from T_CON_ProgrammingContracCost as cost");
				sql.append(" join T_CON_ProgrammingContract as con on con.fid= cost.FContractID");
				sql.append(" join T_CON_Programming as pro on pro.fid = con.FProgrammingID");
				sql.append(" join (select max(pro.fversion) as fver,pro.fprojectid as proid");
				sql.append("  from T_CON_ProgrammingContracCost as cost");
				sql.append("  join T_CON_ProgrammingContract as con on con.fid= cost.FContractID");
				sql.append("  join T_CON_Programming as pro on pro.fid = con.FProgrammingID");
				sql.append("  where cost.fcostAccountid='").append(costAccountId).append("' group by pro.fprojectid");
				sql.append(" ) t on t.fver=pro.fversion and t.proid = pro.fprojectid");
				sql.append(" where cost.fid <> pccost.FID");
				sql.append(" and cost.fcostAccountid='").append(costAccountId).append("'))/100) ");
				sql.append("where fid in (select cost.fid from T_CON_ProgrammingContracCost as cost");
				sql.append(" join T_CON_ProgrammingContract as con on con.fid= cost.FContractID");
				sql.append(" join T_CON_Programming as pro on pro.fid = con.FProgrammingID");
				sql.append(" join (select max(pro.fversion) as fver,pro.fprojectid as proid");
				sql.append("  from T_CON_ProgrammingContracCost as cost");
				sql.append("  join T_CON_ProgrammingContract as con on con.fid= cost.FContractID");
				sql.append("  join T_CON_Programming as pro on pro.fid = con.FProgrammingID");
				sql.append("  where pro.fprojectid<>'").append(projectid).append("'");
				sql.append("  and cost.fcostAccountid='").append(costAccountId).append("' group by pro.fprojectid");
				sql.append(" ) t on t.fver=pro.fversion and t.proid = pro.fprojectid");
				sql.append(" and cost.fcostAccountid='").append(costAccountId).append("') ");
				sql.append("and FCOSTACCOUNTID='").append(costAccountId).append("'");
				builder.addBatch(sql.toString());
			}
		}
		builder.executeBatch();
	}
	
	/**
	 * �õ���һ�汾 - modified for R140507-0295 
	 * @author RD_zhaoqin
	 * @date 2014/05/13
	 */
	private BigDecimal getPreVersionNumber(Context ctx, ProgrammingInfo model) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select fversion from T_CON_Programming where fversionGroup = '");
		builder.appendSql(model.getVersionGroup());
		builder.appendSql("' and fid <> '");
		builder.appendSql(model.getId().toString());
		builder.appendSql("' order by fversion desc");
		IRowSet rs = builder.executeQuery();
		try {
			if(rs.next()) {
				return rs.getBigDecimal("fversion");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ���±��汾�Ŀ�ܺ�Լ�Ƿ������״ֵ̬
	 * @param ctx
	 * @param ids
	 * @throws BOSException
	 */
	private void updateProIsCiting(Context ctx, Set ids) throws BOSException {
		if(ids.size() == 0)
			return;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_con_programmingContract set FIsCiting = 1 where ");
		builder.appendParam("fid", ids.toArray(), "VARCHAR(44)");
		builder.executeUpdate();
	}
	
	/**
	 * �����ѷ������ - R140402-0042
	 * ��Լ�滮���ڱ༭״̬ʱ����ʱ�����˺�ͬ���Լ�滮�Ĺ������ڣ��û���û�����¼������ݵ�����£�
	 * ���˱��桢�ύ��������ʱ���ᵼ���°汾�ĺ�Լ�滮���������⡣
	 * ��������Щ���������¼���һ��һ�ѷ������
	 * 
	 * @param billId
	 * @throws BOSException 
	 * @author rd_zhaoqin
	 * @date 2014/05/26
	 */
	private void updateHasHappenAmount(Context ctx, String billId) throws BOSException {
		if(null == billId || billId.length() == 0)
			return ;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" update T_CON_ProgrammingContract pc set ");
		builder.appendSql(" FBalance = (isnull(FAmount,0) - (select (isnull(FAmount,0) - isnull(FBalance,0)) hasHappen from T_CON_ProgrammingContract where fid = pc.FSrcId)), ");
		builder.appendSql(" FControlBalance = (isnull(FControlAmount,0) - (select (isnull(FControlAmount,0) - isnull(FControlBalance,0)) hasHappen from T_CON_ProgrammingContract where fid = pc.FSrcId)), ");
		builder.appendSql(" FSignUpAmount = (select FSignUpAmount from T_CON_ProgrammingContract where fid = pc.FSrcId), ");
		builder.appendSql(" FChangeAmount = (select FChangeAmount from T_CON_ProgrammingContract where fid = pc.FSrcId), ");
		builder.appendSql(" FSettleAmount = (select FSettleAmount from T_CON_ProgrammingContract where fid = pc.FSrcId) ");
		builder.appendSql(" where FProgrammingID = '").appendSql(billId).appendSql("' ");
		builder.appendSql(" and FSrcId is not null ");
		builder.executeUpdate();
	}
}