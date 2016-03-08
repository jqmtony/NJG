package com.kingdee.eas.fdc.contract.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import com.kingdee.eas.base.codingrule.CodingRuleFactory;
import com.kingdee.eas.base.codingrule.CodingRuleInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.log.LogUtil;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractWOTCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractWOTCodingTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.util.ContractCodingUtil;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacadeFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.ma.budget.BgBudgetFacadeFactory;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.IBgBudgetFacade;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * 描述:无文本合同
 * 
 * @author liupd date:2006-10-13
 *         <p>
 * @version EAS5.1.3
 */
public class ContractWithoutTextControllerBean extends
		AbstractContractWithoutTextControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.ContractWithoutTextControllerBean");
	
	/**
	 * 无文本合同编码规则的标识属性
	 */
	private static final String BINDING_PROPERTY = "codeType.number";
	
	// 付款申请单编码
	private String payReqNumber = new String("");

	// 保存
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		/**start ken_liu**/
		//无文本合同新增付款申请单关联属性、调整代码..ken_liu
		ContractWithoutTextInfo con = ((ContractWithoutTextInfo) model);
		if (con.getId() == null) {
			BOSUuid uuID = BOSUuid.create(con.getBOSType());
			con.setId(uuID);
		}
		/**end ken_liu**/
		
//		FDCBillInfo con = ((FDCBillInfo) model);
		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		
		// 处理编码类型字段，防止回收编码时出现异常
		dealWithCodeType(ctx, con, true);
		
		/**start ken_liu**/
		IObjectPK payRequestBillPK = createPayRequestBill(ctx, con, prbi, FDCBillStateEnum.SAVED);
		prbi.setId(BOSUuid.read(payRequestBillPK.toString()));
		
		con.setPayRequestBill(prbi);
		
		if(con.getProgrammingContract()!=null){
    		updateProgrammingContract(ctx, con.getProgrammingContract().getId().toString(), 1);
    	}

		IObjectPK pk = super._save(ctx, con);
		/**end ken_liu**/
		
		return pk;
	}

	// 提交
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//无文本合同新增付款申请单关联属性、调整代码..ken_liu
		ContractWithoutTextInfo con = ((ContractWithoutTextInfo) model);

		checkBillForSubmit(ctx, con);
		// 处理编码类型字段，防止回收编码时出现异常
		dealWithCodeType(ctx, con, true);

		if (con.getId() == null) {
			BOSUuid uuID = BOSUuid.create(con.getBOSType());
			con.setId(uuID);
			
			super._save(ctx, con);	// modified by zhaoqin for R140314-0311 on 2014/04/04
		}

		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		IObjectPK payRequestBillPK = createPayRequestBill(ctx, con, prbi, FDCBillStateEnum.SUBMITTED);
		prbi.setId(BOSUuid.read(payRequestBillPK.toString()));

		con.setPayRequestBill(prbi);

		if(con.getProgrammingContract()!=null){
    		updateProgrammingContract(ctx, con.getProgrammingContract().getId().toString(), 1);
    	}
		IObjectPK pk = super._submit(ctx, con);
		return pk;

		//del Commented-Out code for '付款申请单编码重复性校验'.. ken_liu
		//付款申请单编码重复性校验
	}
	
	/**
	 * 工作流中提交调用的方法
	 */
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		//无文本合同新增付款申请单关联属性、调整代码..ken_liu
		ContractWithoutTextInfo con = ((ContractWithoutTextInfo) model);
		if (con.getId() == null) {
			BOSUuid uuID = BOSUuid.create(con.getBOSType());
			con.setId(uuID);
			super._save(ctx, con);
		}
		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		IObjectPK payRequestBillPK = createPayRequestBill(ctx, con, prbi, FDCBillStateEnum.SUBMITTED);
		prbi.setId(BOSUuid.read(payRequestBillPK.toString()));
		con.setPayRequestBill(prbi);
		super._submit(ctx, pk, con);
	}

	protected void trimName(FDCBillInfo fDCBillInfo) {
		super.trimName(fDCBillInfo);
	}

	// 审核
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {

		checkBillForAudit(ctx, billId, null);
		checkContractProgramming(ctx, billId.toString());

		super._audit(ctx, billId);

		// 同步标记付款申请单为审批状态
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("contractId", billId.toString()));
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.setFilter(filterInfo);

		IPayRequestBill iPayReq = PayRequestBillFactory.getLocalInstance(ctx);
		PayRequestBillCollection prbc = iPayReq
				.getPayRequestBillCollection(evi);

		if (prbc.size() > 0) {
			iPayReq.audit(prbc.get(0).getId());
		}
		ContractExecInfosFactory.getLocalInstance(ctx).updateContract(
				ContractExecInfosInfo.EXECINFO_AUDIT, billId.toString());
	}

	// 反审核
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {

		if (billId == null)
			return;
		// 在反审批的时候判断有没有付款单,如果有不允许反审批
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBillId", billId.toString()));
		if (PaymentBillFactory.getLocalInstance(ctx).exists(filter)) {
			throw new ContractException(ContractException.HASPAYMENTBILL);
		}

		checkBillForUnAudit(ctx, billId, null);

		String sql = "update T_CON_PayRequestBill set fstate=?,fhasClosed=0 where fcontractid=?";
		String[] params = new String[] { FDCBillStateEnum.SUBMITTED_VALUE,
				billId.toString() };
		DbUtil.execute(ctx, sql, params);

		EntityViewInfo view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractId", billId.toString()));
		view.setFilter(filter);

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("contractId");
		selector.add("curProject.id");
		selector.add("orgUnit.id");

		PayRequestBillCollection col = PayRequestBillFactory.getLocalInstance(
				ctx).getPayRequestBillCollection(view);
		for (int i = 0; i < col.size(); i++) {
			PayRequestBillInfo payRequestBillInfo = col.get(i);
			// 20050511 吴志敏提供预算接口
			boolean isMbgCtrl = FDCUtils.getBooleanValue4FDCParamByKey(ctx, payRequestBillInfo.getOrgUnit().getId().toString(),
					FDCConstants.FDC_PARAM_STARTMG);
			if (isMbgCtrl) {
				IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(ctx);
				iBgCtrl.cancelRequestBudget(col.get(i).getId().toString());
			}
		}

		super._unAudit(ctx, billId);

		// 自动删除本期月结数据
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("period.id");

		ContractWithoutTextInfo info = (ContractWithoutTextInfo) this.getValue(
				ctx, new ObjectUuidPK(billId), selectors);
		if (info.getPeriod() != null) {
			CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(
					info.getId().toString(),
					info.getPeriod().getId().toString());
		}
		ContractExecInfosFactory.getLocalInstance(ctx).updateContract(
				ContractExecInfosInfo.EXECINFO_UNAUDIT, billId.toString());
	}

	// 新增
	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		ContractWithoutTextInfo info = (ContractWithoutTextInfo) model;
		
		IObjectPK objectPK = super._addnew(ctx, model);
		
		// 处理编码类型字段，防止回收编码时出现异常
		dealWithCodeType(ctx, info, true);

		// 同步到合同基本资料，用于合同做核算项目
		ContractBaseDataHelper.synToContractBaseData(ctx, true, objectPK
				.toString());

		/** 添加反写ContractBaseDataID的代码 -by neo */
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("update t_con_contractwithouttext set fcontractbasedataid ="
						+ "(select fid from t_CON_contractbasedata where fcontractid = t_con_contractwithouttext.fid) "
						+ "where");
		builder.appendParam("fid", objectPK.toString());
		builder.executeUpdate(ctx);
		/** 添加反写ContractBaseDataID的代码 -by neo */
		return objectPK;
	}

	// 修改
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {

		super._update(ctx, pk, model);

		// 同步到合同基本资料，用于合同做核算项目
		ContractBaseDataHelper.synToContractBaseData(ctx, true, pk.toString());
	}

	protected void _updatePartial(Context ctx, IObjectValue model,
			SelectorItemCollection selector) throws BOSException,
			EASBizException {
		super._updatePartial(ctx, model, selector);

		// 同步到合同基本资料，用于合同做核算项目
		String id = ((FDCBillInfo) model).getId().toString();
		ContractBaseDataHelper.synToContractBaseData(ctx, true, id);

	}
	
	/**
	 * 描述: 在合同<<审批~之前>> 检查关联的框架合约是否为最新版本，如不是，则更新为最新版本
	 * 情形：合同从提交到审批之间隔了好长时间，期间合约规划作了新版本，那就会出现问题
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
		
		builder.appendSql("select FProgrammingContract from T_CON_ContractWithoutText where FProgrammingContract is not null and fid = ?");
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
						builder.appendSql("update T_CON_ContractWithoutText set FProgrammingContract = null where fid = ?");
						builder.addParam(billId);
					} else {
						builder.appendSql("update T_CON_ContractWithoutText set FProgrammingContract = ? where fid = ?");
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

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		_delete(ctx, new IObjectPK[] { pk });
	}

	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		super._cancel(ctx, pk);

		// 同步删除合同基本资料
		String sql = "delete t_con_contractbasedata where fcontractid = ?";
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });

		sql = "delete from T_CON_PayRequestBillEntry where fparentid in (select fid from T_CON_PayRequestBill where fcontractid =?) "; // TODO
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });
		sql = "delete from T_CON_PayRequestBill where fcontractid =?";
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });
		sql = "delete from T_CON_ContractBaseData where fcontractid =?";
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });

	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		Set set = new HashSet();
		// String str="'";
		for (int i = 0; i < arrayPK.length; i++) {
			set.add(arrayPK[i]);
			// str+=arrayPK[i].toString()+"','";
		}
		// str=str.substring(0,str.length()-2);
		FilterInfo filter = new FilterInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", set, CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.HASAUDIT);
		}

		// 在反审批的时候判断有没有付款单,如果有不允许反审批
		// filter.getFilterItems().add(new
		// FilterItemInfo("contractBillId",set,CompareType.INCLUDE));
		// if(PaymentBillFactory.getLocalInstance(ctx).exists(filter)){
		// throw new ContractException(ContractException.HASPAYMENTBILL);
		// }
		// SQL操作，不记录日志
		// String sql=
		// "delete from T_CON_PayRequestBillEntry where fparentid in (select fid from T_CON_PayRequestBill where fcontractid in ("
		// +str+"));"; //TODO
		// DbUtil.execute(ctx, sql);
		//sql="delete from T_CON_PayRequestBill where fcontractid in ("+str+");"
		// ;
		// DbUtil.execute(ctx, sql);
		// IObjectPK payrequestPK =null;
		// PayRequestBillFactory.getLocalInstance(ctx).delete(payrequestPK);

		//
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("parent.contractid", set,
						CompareType.INCLUDE));
		PayRequestBillEntryFactory.getLocalInstance(ctx).delete(filter);
		// 单据，需要记录日志
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("number"));
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractid", set, CompareType.INCLUDE));
		view.setFilter(filter);
		PayRequestBillCollection payRequestBills = PayRequestBillFactory
				.getLocalInstance(ctx).getPayRequestBillCollection(view);
		IObjectPK[] payPks = new IObjectPK[payRequestBills.size()];
		int i = 0;
		String paysLogInfo = "";
		for (Iterator it = payRequestBills.iterator(); it.hasNext();) {
			PayRequestBillInfo pay = (PayRequestBillInfo) it.next();
			payPks[i++] = new ObjectUuidPK(pay.getId());
			paysLogInfo += paysLogInfo.equals("") ? pay.getNumber() : ","
					+ pay.getNumber();
		}
		// begin log
		IObjectPK logPk = LogUtil.beginLog(ctx, "fin_payRequst_delete",
				(new PayRequestBillInfo()).getBOSType(), null, paysLogInfo,
				"fin_payRequst_delete");
		// do delete
		PayRequestBillFactory.getLocalInstance(ctx).deleteForContWithoutText(
				payPks);
		// after log
		LogUtil.afterLog(ctx, logPk);
		// delete payrequestbill end
		/*
		 * //不能直接删除，如果被作为核算项目被横表引用的话是不允许删除的，掉ORMap的方法会有这类的检查 String
		 * sql="delete from T_CON_ContractBaseData where fcontractid in ("
		 * +str+");"; DbUtil.execute(ctx, sql);
		 */
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId", set, CompareType.INCLUDE));
		ContractBaseDataFactory.getLocalInstance(ctx).delete(filter);
		for (int j = 0; j < arrayPK.length; j++) {
			try {
				updateOldProg(ctx,arrayPK[j]);
			} catch (Exception e) {
				logger.error(e);
				throw new BOSException(e);
			}
		}
		super._delete(ctx, arrayPK);
	}

	/**
	 * 更新老框架合约是否被引用
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
	
	private void updateProgrammingContract(Context ctx, String proContId, int isCiting) throws BOSException {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " ");
		buildSQL.appendSql("where FID = '" + proContId + "' ");
			buildSQL.executeUpdate();
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
	
	/**
	 * 找出所关联的框架合约的记录数(无文本已经废除，不再查找)
	 * 现在需要加上无文本这块的判断
	 */
	private int isCitingByProg(Context ctx, String proContId) throws BOSException {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql(" select count(1) count from T_INV_InviteProject ");
		buildSQL.appendSql(" where FProgrammingContractId = '" + proContId + "' ");
		buildSQL.appendSql(" union ");
		buildSQL.appendSql(" select count(1) count from T_CON_ContractBill ");
		buildSQL.appendSql(" where FProgrammingContract = '" + proContId + "' ");
		buildSQL.appendSql(" union ");
		buildSQL.appendSql(" select count(1) count from T_CON_ContractWithoutText ");
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
	
	/**
	 * 检查合同是否关联框架合约
	 */
	private String checkReaPre(Context ctx, IObjectPK pk) throws Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add("*");
//		sic.add("programmingContract.*");
		builder.clear();
		builder.appendSql("select fprogrammingContract from T_CON_ContractWithoutText where 1=1 and ");
		builder.appendParam("fid", pk.toString());
		IRowSet rowSet = builder.executeQuery();
		if (rowSet.next()) {
			if (rowSet.getString("fprogrammingContract") != null) {
				return rowSet.getString("fprogrammingContract").toString();
			}
		}
		return null;
	}
	
	private IObjectPK createPayRequestBill(Context ctx, FDCBillInfo con,
			PayRequestBillInfo prbi, FDCBillStateEnum state)
			throws BOSException, EASBizException {

		// 删除之前的多余付款申请单
		String sql = "delete from T_CON_PayRequestBill where FContractId = ? ";
		Object[] params = new Object[] { con.getId().toString() };
		DbUtil.execute(ctx, sql, params);
		checkNumber(ctx, con.getNumber());
		// 付款申请单编码重复性校验
		String number = payReqNumber;

		// UI传入
		// 付款申请单
		PayRequestBillInfo prbiNew = new PayRequestBillInfo();

		// 付款类型
		prbiNew.setPaymentType(prbi.getPaymentType());
		// 用款部门
		prbiNew.setUseDepartment(prbi.getUseDepartment());
		// 付款申请单编码
		// prbiNew.setNumber(prbi.getNumber());
		// if(prbi.getId() == null){
		// 2009-2-5 始终使用checkNumber方法计算出的编码生成付款申请单
		prbiNew.setNumber(number);
		// }else{
		// prbiNew.setNumber(prbi.getNumber());
		// }
		// 付款日期
		prbiNew.setPayDate(prbi.getPayDate());
		// 收款单位
		prbiNew.setSupplier(prbi.getSupplier());
		// 实际收款单位
		prbiNew.setRealSupplier(prbi.getRealSupplier());
		// 结算方式
		prbiNew.setSettlementType(prbi.getSettlementType());
		// 收款银行
		prbiNew.setRecBank(prbi.getRecBank());
		// 收款帐号
		prbiNew.setRecAccount(prbi.getRecAccount());
		// 紧急程度
		prbiNew.setUrgentDegree(prbi.getUrgentDegree());
		// 币别
		prbiNew.setCurrency(prbi.getCurrency());
		// 最新造价
		prbiNew.setLatestPrice(prbi.getLatestPrice());
		// 汇率
		prbiNew.setExchangeRate(prbi.getExchangeRate());
		// 大写金额
		prbiNew.setCapitalAmount(prbi.getCapitalAmount());
		// 付款比例
		prbiNew.setPaymentProportion(prbi.getPaymentProportion());
		// 已完工工程量金额
		prbiNew.setCompletePrjAmt(prbi.getCompletePrjAmt());
		// 本位币金额
		prbiNew.setAmount(prbi.getAmount());
		// 原币金额
		prbiNew.setOriginalAmount(prbi.getOriginalAmount());
		
		// 合同内工程款
		// 本位币金额
		prbiNew.setProjectPriceInContract(prbi.getAmount());
		// 原币金额
		prbiNew.setProjectPriceInContractOri(prbi.getOriginalAmount());
		
		// 款项说明
		prbiNew.setUsage(prbi.getUsage());
		// 款项说明
		prbiNew.setMoneyDesc(prbi.getMoneyDesc());
		// 备注
		prbiNew.setDescription(prbi.getDescription());

		// 附件
		prbiNew.setAttachment(prbi.getAttachment());
		// 工程项目
		prbiNew.setCurProject(prbi.getCurProject());
		// 关闭状态
		prbiNew.setHasClosed(false);
		// 申请日期
		prbiNew.setBookedDate(con.getBookedDate());
		// 期间
		prbiNew.setPeriod(con.getPeriod());

		// 此处获取的
		// 合同号
		prbiNew.setContractNo(con.getNumber());
		// 合同ID
		prbiNew.setContractId(con.getId().toString());
		prbiNew.setSource(con.getBOSType().toString());
		// 合同名称
		prbiNew.setContractName(con.getName());
		// 合同造价
		prbiNew.setContractPrice(con.getAmount());
		// 状态
		prbiNew.setState(state);
		// 付款比例
		// prbiNew.setPaymentProportion(new BigDecimal(100));
		prbiNew.setPaymentProportion(FDCHelper.ONE_HUNDRED);
		// 已完工工程量金额
		prbiNew.setCompletePrjAmt(con.getAmount());// 已完工工程量金额
		// 成本中心
		prbiNew.setOrgUnit(con.getOrgUnit());
		// 管理单元
		prbiNew.setCU(con.getCU());
		
	
		// 无需付款的申请单 update by liang_ren at 2010-5-10
	
		prbiNew.setIsPay(!((ContractWithoutTextInfo) con).isIsNeedPaid());
			
		// 发票号
		prbiNew.setInvoiceNumber(prbi.getInvoiceNumber());
		// 发票日期
		prbiNew.setInvoiceDate(prbi.getInvoiceDate());
		// 发票原币
		prbiNew.setInvoiceOriAmt(prbi.getInvoiceOriAmt());
		// 发票金额
		prbiNew.setInvoiceAmt(prbi.getInvoiceAmt());
		// 累计发票原币
		prbiNew.setAllInvoiceOriAmt(prbi.getAllInvoiceOriAmt());
		// 累计发票金额
		prbiNew.setAllInvoiceAmt(prbi.getInvoiceAmt());

		return PayRequestBillFactory.getLocalInstance(ctx).addnew(prbiNew);
	}

	// 单据序时簿编辑界面初始数据粗粒度方法
	protected Map _fetchInitData(Context ctx, Map paramMap)
			throws BOSException, EASBizException {

		paramMap.put("isCost", Boolean.FALSE);
		Map initMap = super._fetchInitData(ctx, paramMap);

		return initMap;
	}

	private SelectorItemCollection getSic() {
		// 此过滤为详细信息定义
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("period.id"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("conChargeType.id"));

		return sic;
	}

	// 提交时校验单据期间不能在工程项目的当前期间之前
	private void checkBillForSubmit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		// 不能落于当前成本期间之前
		ContractWithoutTextInfo contractBill = (ContractWithoutTextInfo) model;

		String comId = null;
		if (contractBill.getCurProject().getFullOrgUnit() == null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(
							new ObjectUuidPK(contractBill.getCurProject()
									.getId().toString()), sic);

			comId = curProject.getFullOrgUnit().getId().toString();
		} else {
			comId = contractBill.getCurProject().getFullOrgUnit().getId()
					.toString();
		}

		boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
		if (isInCore) {
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,
					contractBill.getCurProject().getId().toString(), false);
			if (contractBill.getPeriod().getBeginDate().before(
					bookedPeriod.getBeginDate())) {
				// 单据期间不能在工程项目的当前期间之前 CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
	}

	// 审核校验
	private void checkBillForAudit(Context ctx, BOSUuid billId,
			FDCBillInfo billInfo) throws BOSException, EASBizException {

		ContractWithoutTextInfo model = (ContractWithoutTextInfo) billInfo;

		if (model == null) {
			model = this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(
					billId.toString()), getSic());
		}
		// ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,new
		// ObjectUuidPK(billId.toString()),sic);
		// 检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId()
				.toString();

		// 是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
		if (isInCore) {
			String curProject = model.getCurProject().getId().toString();

			if (!ProjectPeriodStatusUtil._isClosed(ctx, curProject)) {
				throw new ProjectPeriodStatusException(
						ProjectPeriodStatusException.ISNOT_INIT,
						new Object[] { model.getCurProject().getDisplayName() });
			}
			// //成本已经月结
			// PeriodInfo bookedPeriod =
			// FDCUtils.getCurrentPeriod(ctx,curProject,true);
			//if(model.getPeriod().getBeginDate().after(bookedPeriod.getEndDate(
			// ))){
			// throw new ContractException(ContractException.AUD_AFTERPERIOD,new
			// Object[]{model.getNumber()});
			// }

			// 财务还没有月结，不能审核单据{0}
			// PeriodInfo finPeriod =
			// FDCUtils.getCurrentPeriod(ctx,curProject,false);
			//if(model.getPeriod().getBeginDate().after(finPeriod.getEndDate()))
			// {
			// throw new ContractException(ContractException.AUD_FINNOTCLOSE,new
			// Object[]{model.getNumber()});
			// }
		}
		// 根据参数是否显示合同费用项目
		// 单据审批时预算控制
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx, null,
				FDCConstants.FDC_PARAM_CHARGETYPE);
		if (isShowCharge && model.getConChargeType() != null) {
			invokeAuditBudgetCtrl(ctx, model);
		}
	}

	// 单据审批时 预算控制
	private void invokeAuditBudgetCtrl(Context ctx,
			ContractWithoutTextInfo conBill) throws BOSException,
			EASBizException {
		boolean useWorkflow = FDCUtils.isRunningWorkflow(ctx, conBill.getId()
				.toString());
		if (!useWorkflow) {
			IBgControlFacade bgControlFacade = BgControlFacadeFactory
					.getLocalInstance(ctx);
			IBgBudgetFacade iBgBudget = BgBudgetFacadeFactory
					.getLocalInstance(ctx);

			boolean isPass = false;
			isPass = iBgBudget
					.getAllowAccessNoWF(FDCConstants.ContractWithoutText);
			if (isPass) {
				// 5.1.1暂时屏蔽
				bgControlFacade.bgAuditAllowAccess(conBill.getId(),
						FDCConstants.ContractWithoutText, null);
			} else {
				isPass = bgControlFacade.bgAudit(conBill.getId().toString(),
						FDCConstants.ContractWithoutText, null);
			}
			// 根据isPass判断是否抛异常
			if (!isPass) {
				throw new ContractException(ContractException.BEFOREBGBAL);
			}
		}
	}

	// 单据反审批时 预算控制
	private void invokeUnAuditBudgetCtrl(Context ctx,
			ContractWithoutTextInfo conBill) throws BOSException,
			EASBizException {

		IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(ctx);
		iBgCtrl.cancelRequestBudget(conBill.getId().toString());
	}

	// 审核校验
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,
			FDCBillInfo billInfo) throws BOSException, EASBizException {
		ContractWithoutTextInfo model = (ContractWithoutTextInfo) billInfo;
		// 此过滤为详细信息定义
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("conChargeType.id"));

		if (model == null) {
			model = this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(
					billId.toString()), getSic());
		}
		// 检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId()
				.toString();

		// 是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
		if (isInCore) {
			String curProject = model.getCurProject().getId().toString();

			// 单据期间在工程项目当前期间之前，不能反审核
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,
					curProject, false);
			if (model.getPeriod().getBeginDate().before(
					bookedPeriod.getBeginDate())) {
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}

			// if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
			// throw new
			// ProjectPeriodStatusException(ProjectPeriodStatusException
			// .CLOPRO_HASEND,new
			// Object[]{model.getCurProject().getDisplayName()});
			// }
		}
		// 根据参数是否显示合同费用项目
		// 单据审批时预算控制
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx, null,
				FDCConstants.FDC_PARAM_CHARGETYPE);
		if (isShowCharge && model.getConChargeType() != null) {
			invokeUnAuditBudgetCtrl(ctx, model);
		}
	}

	// 是否成本
	protected boolean isCost() {
		return false;
	}

	// 获取合同类型编码
	protected String _getContractTypeNumber(Context ctx, IObjectPK id)
			throws BOSException, EASBizException {
		//
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.longNumber"));
		ContractWithoutTextInfo contractBill = (ContractWithoutTextInfo) this
				.getValue(ctx, id, sic);

		if (contractBill.getContractType() != null) {
			return contractBill.getContractType().getLongNumber().replace('!',
					'.');
		} else {
			return "*";
		}
	}

	// 如果编码重复重新取编码
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info)
			throws BOSException, CodingRuleException, EASBizException {
		handleIntermitNumberForReset(ctx, info);
	}

	// 检查编码是否相同
	private void checkNumber(Context ctx, String number) throws BOSException {
		if (number==null || number.equals("")) {
			return;
		}
		payReqNumber = number;
		// 付款申请单编码重复性校验
		String sql = "select fid from T_CON_PayRequestBill where FNumber = ? ";
		Object[] params = new Object[] { number };

		RowSet rowset;
			rowset = DbUtil.executeQuery(ctx, sql, params);
			try {
				if (rowset.next()) {
					payReqNumber = payReqNumber + "(无文本)";
					checkNumber(ctx, payReqNumber);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		
	}

	protected String _getUseDepartment(Context ctx, String id)
			throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("contractId",id.toString()));
		view.setSelector(new SelectorItemCollection());
		view.getSelector().add("useDepartment.id");
		view.getSelector().add("useDepartment.name");
		view.getSelector().add("useDepartment.number");
		view.getSelector().add("useDepartment.longNumber");
		PayRequestBillCollection payReqBillCol = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		if(payReqBillCol.size() > 0){
			PayRequestBillInfo billInfo = payReqBillCol.get(0);
			return billInfo.getUseDepartment().getLongNumber().replace('!', '.');
		}
		return "*";
	}

	protected String _getPaymentType(Context ctx, BOSUuid contractId)
			throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("contractId",contractId.toString()));
		view.setSelector(new SelectorItemCollection());
		view.getSelector().add("paymentType.id");
		view.getSelector().add("paymentType.name");
		view.getSelector().add("paymentType.number");
		PayRequestBillCollection payReqBillCol = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		if(payReqBillCol.size() > 0){
			PayRequestBillInfo billInfo = payReqBillCol.get(0);
			return billInfo.getPaymentType().getNumber();
		}
		return "*";
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：取得无文本合同编码类型对象集合（精确，模糊，递归）
	 * <p>
	 * 1、首先查询“合同类型”是指定值的情况 <br>
	 * 2、再查询父类“合同类型”为指定值的情况 <br>
	 * 3、再查询“合同类型”为“NULL(全部)”为指定值的情况 <br>
	 * <p>
	 * 4.1、“合同类型”要支持递归。例如：首先要找当前级，如果没有找到，则要能找到上级，如果还没有找到，则要找上上级，依次类推 <br>
	 * 4.2、递归查找“合同类型”上级（“合同类型”是树形结构，通常只有3-4级，所以不会产生性能问题） <br>
	 * 
	 * @param ctx
	 *            应用上下文；非空
	 * @param contractType
	 *            合同类型；可空
	 * @param thirdType
	 *            单据状态（新增或归档）；非空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-20
	 * @see com.kingdee.eas.fdc.contract.app.AbstractContractWithoutTextControllerBean#_getContractWOTCodingType(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectValue, java.lang.String, java.lang.String)
	 */
	protected IObjectCollection _getContractWOTCodingType(Context ctx, IObjectValue contractType, String thirdType)
			throws BOSException, EASBizException {
		ContractTypeInfo contractTypeInfo = (ContractTypeInfo) contractType;

		// 取得无文本合同编码类型对象集合（精确，模糊，递归）
		ContractWOTCodingTypeCollection codingTypeCollection = ContractCodingUtil.getContractWOTCodingTypeCollection(
				ctx, contractTypeInfo, thirdType);

		return codingTypeCollection;
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
	 * 如果在调用编码规则定义（ContractWithoutTextControllerBean.handleIntermitNumber1）的时候，<br>
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
		ContractWithoutTextInfo contractWithoutTextInfo = (ContractWithoutTextInfo) fdcBillInfo;

		if (null == contractWithoutTextInfo.getCodeType()) {
			ContractTypeInfo contractType = contractWithoutTextInfo.getContractType();
			ContractThirdTypeEnum contractThirdTypeEnum = ContractThirdTypeEnum.NEW;
			// 获取无文本合同编码类型对象集合（精确，模糊，递归）
			ContractWOTCodingTypeCollection codingTypeList = (ContractWOTCodingTypeCollection) _getContractWOTCodingType(
					ctx, contractType, contractThirdTypeEnum.getValue());

			if (FdcObjectCollectionUtil.isNotEmpty(codingTypeList)) {
				// 第一个无文本合同编码类型（最接近，最匹配）
				ContractWOTCodingTypeInfo firstCodingTypeInfo = codingTypeList.get(0);
				// 设置无文本合同编码类型
				contractWithoutTextInfo.setCodeType(firstCodingTypeInfo);
			}

			if (null != contractWithoutTextInfo.getCodeType() && isUpdate && null != contractWithoutTextInfo.getId()
					&& this._exists(ctx, new ObjectUuidPK(contractWithoutTextInfo.getId()))) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("codeType");

				super._updatePartial(ctx, contractWithoutTextInfo, selector);
			}
		}

		// 打印调试信息
		logger.info("合同，contractWithoutTextInfo：" + contractWithoutTextInfo);
		logger.info("无文本合同编码关联属性，contractWithoutTextInfo.codeType：" + contractWithoutTextInfo.getCodeType());
	}

	// ////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////

	// 取编码，编码规则必须设置绑定属性
	protected String getBindingProperty() {
		return BINDING_PROPERTY;
	}

	/**
	 * 描述：属性字段参与编码规则，如果值发生改变，则需要重新获取编码
	 * 
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-10-10
	 */
	protected boolean isNeedReHandleIntermitNumberByProperty() {
		return true;
	}

	/**
	 * 描述：是否回收上级组织传递的编码规则
	 * 
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-10-10
	 */
	protected boolean isRecycleParentOrgNumber() {
		return true;
	}

	/**
	 * 描述：取得回收编码Seletor
	 * 
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

		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("CU.name"));
		sic.add(new SelectorItemInfo("CU.number"));
		sic.add(new SelectorItemInfo("CU.code"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.code"));

		FdcObjectCollectionUtil.clearDuplicate(sic);

		return sic;
	}

	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		logger.info("===============================================================================");
		logger.info("ContractWithoutTextControllerBean.recycleNumber(),start");
		logger.info("===============================================================================");

		SelectorItemCollection sic = getSeletorForRecycleNumber();
		ContractWithoutTextInfo info = (ContractWithoutTextInfo) _getValue(ctx, pk, sic);
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
			logger.info("iCodingRuleManager.isUseIntermitNumber(info, curOrgId, BINDING_PROPERTY):"
					+ isUseIntermitNumber);
			logger
					.info("info.getCodeType() != null && iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId):"
							+ flag);

			if (flag) {
				// 如果回收成功了就跳出循环
				if (iCodingRuleManager.recycleNumber(info, curOrgId, BINDING_PROPERTY, "", info.getNumber())) {
					break;
				}
			}
		}

		logger.info("===============================================================================");
		logger.info("ContractWithoutTextControllerBean.recycleNumber(),end");
		logger.info("===============================================================================");
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
		ContractWithoutTextInfo contractWithoutTextInfo = (ContractWithoutTextInfo) info;

		ContractTypeInfo contractType = contractWithoutTextInfo.getContractType();
		ContractThirdTypeEnum contractThirdTypeEnum = ContractThirdTypeEnum.NEW;
		// 获取无文本合同编码类型对象集合（精确，模糊，递归）
		ContractWOTCodingTypeCollection codingTypeList = (ContractWOTCodingTypeCollection) _getContractWOTCodingType(
				ctx, contractType, contractThirdTypeEnum.getValue());

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

			// 第一个无文本合同编码类型（最接近，最匹配）
			ContractWOTCodingTypeInfo firstCodingTypeInfo = codingTypeList.get(0);

			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			for (int i = 0, size = codingTypeList.size(); i < size; i++) {
				ContractWOTCodingTypeInfo tempCodingTypeInfo = codingTypeList.get(i);
				// 设置无文本合同编码类型（临时设置，用于后面获取编码规则使用）
				contractWithoutTextInfo.setCodeType(tempCodingTypeInfo);

				// 编码规则获取要支持组织递归。例如：首先要找当前组织，如果没有找到，则要能找到上级组织，如果还没有找到，则要找上上级组织，依次类推
				for (int j = 0; j < orgIdList.size(); j++) {
					if (setNumber(ctx, contractWithoutTextInfo, orgIdList.get(j).toString(), BINDING_PROPERTY,
							iCodingRuleManager, count)) {
						// 如果成功匹配到编码规则，则终止循环，直接返回（无文本合同编码类型为最后一次设置的临时值）
						return;
					}
				}
			}

			// 如果前面循环中始终没有匹配到编码规则，则重新设置无文本合同编码类型（第一个无文本合同编码类型（最接近，最匹配））
			contractWithoutTextInfo.setCodeType(firstCodingTypeInfo);
		}
	}

	/**
	 * 描述：匹配编码规则，获取编码，设置编码
	 * 
	 * @param ctx
	 * @param contractWithoutTextInfo
	 * @param orgId
	 * @param bindingProperty
	 * @param iCodingRuleManager
	 * @param count
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-20
	 */
	protected boolean setNumber(Context ctx, ContractWithoutTextInfo contractWithoutTextInfo, String orgId,
			String bindingProperty, ICodingRuleManager iCodingRuleManager, int count) throws BOSException,
			EASBizException {

		if (iCodingRuleManager.isExist(contractWithoutTextInfo, orgId, bindingProperty)) {
			// 获取合同的编码规则对象
			CodingRuleInfo codingRuleInfo = iCodingRuleManager.getCodingRule(contractWithoutTextInfo, orgId,
					bindingProperty);
			// 如果支持修改就要检查编码是否符合当前启用的编码规则的规则
			if (codingRuleInfo.isIsModifiable()) {
				CodingRuleFactory.getLocalInstance(ctx).checkModifiedNumber(contractWithoutTextInfo, codingRuleInfo,
						contractWithoutTextInfo.getNumber());
			} else {
				// 如果是新增显示
				if (codingRuleInfo.isIsAddView()) {
					// 编码为空的时候要获取编码
					if (contractWithoutTextInfo.getNumber() == null
							|| "".equals(contractWithoutTextInfo.getNumber().trim())) {
						contractWithoutTextInfo.setNumber(iCodingRuleManager.getNumber(contractWithoutTextInfo, orgId,
								bindingProperty, ""));
					} else {
						// 当编码不为空的时候，count=0的时候不需要重新获取编码，当编码>0的时候证明编码重复要重新获取新的编码
						if (count > 0) {
							contractWithoutTextInfo.setNumber(iCodingRuleManager.getNumber(contractWithoutTextInfo,
									orgId, bindingProperty, ""));
						}
					}
				} else {
					// 否则就是不允许断号，每次都要重现获取
					contractWithoutTextInfo.setNumber(iCodingRuleManager.getNumber(contractWithoutTextInfo, orgId,
							bindingProperty, ""));
				}
			}

			return true;
		}

		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}