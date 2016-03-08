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
 * ����:���ı���ͬ
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
	 * ���ı���ͬ�������ı�ʶ����
	 */
	private static final String BINDING_PROPERTY = "codeType.number";
	
	// �������뵥����
	private String payReqNumber = new String("");

	// ����
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		/**start ken_liu**/
		//���ı���ͬ�����������뵥�������ԡ���������..ken_liu
		ContractWithoutTextInfo con = ((ContractWithoutTextInfo) model);
		if (con.getId() == null) {
			BOSUuid uuID = BOSUuid.create(con.getBOSType());
			con.setId(uuID);
		}
		/**end ken_liu**/
		
//		FDCBillInfo con = ((FDCBillInfo) model);
		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		
		// ������������ֶΣ���ֹ���ձ���ʱ�����쳣
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

	// �ύ
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//���ı���ͬ�����������뵥�������ԡ���������..ken_liu
		ContractWithoutTextInfo con = ((ContractWithoutTextInfo) model);

		checkBillForSubmit(ctx, con);
		// ������������ֶΣ���ֹ���ձ���ʱ�����쳣
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

		//del Commented-Out code for '�������뵥�����ظ���У��'.. ken_liu
		//�������뵥�����ظ���У��
	}
	
	/**
	 * ���������ύ���õķ���
	 */
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		//���ı���ͬ�����������뵥�������ԡ���������..ken_liu
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

	// ���
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {

		checkBillForAudit(ctx, billId, null);
		checkContractProgramming(ctx, billId.toString());

		super._audit(ctx, billId);

		// ͬ����Ǹ������뵥Ϊ����״̬
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

	// �����
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {

		if (billId == null)
			return;
		// �ڷ�������ʱ���ж���û�и��,����в���������
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
			// 20050511 ��־���ṩԤ��ӿ�
			boolean isMbgCtrl = FDCUtils.getBooleanValue4FDCParamByKey(ctx, payRequestBillInfo.getOrgUnit().getId().toString(),
					FDCConstants.FDC_PARAM_STARTMG);
			if (isMbgCtrl) {
				IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(ctx);
				iBgCtrl.cancelRequestBudget(col.get(i).getId().toString());
			}
		}

		super._unAudit(ctx, billId);

		// �Զ�ɾ�������½�����
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

	// ����
	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		ContractWithoutTextInfo info = (ContractWithoutTextInfo) model;
		
		IObjectPK objectPK = super._addnew(ctx, model);
		
		// ������������ֶΣ���ֹ���ձ���ʱ�����쳣
		dealWithCodeType(ctx, info, true);

		// ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
		ContractBaseDataHelper.synToContractBaseData(ctx, true, objectPK
				.toString());

		/** ��ӷ�дContractBaseDataID�Ĵ��� -by neo */
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("update t_con_contractwithouttext set fcontractbasedataid ="
						+ "(select fid from t_CON_contractbasedata where fcontractid = t_con_contractwithouttext.fid) "
						+ "where");
		builder.appendParam("fid", objectPK.toString());
		builder.executeUpdate(ctx);
		/** ��ӷ�дContractBaseDataID�Ĵ��� -by neo */
		return objectPK;
	}

	// �޸�
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {

		super._update(ctx, pk, model);

		// ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
		ContractBaseDataHelper.synToContractBaseData(ctx, true, pk.toString());
	}

	protected void _updatePartial(Context ctx, IObjectValue model,
			SelectorItemCollection selector) throws BOSException,
			EASBizException {
		super._updatePartial(ctx, model, selector);

		// ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
		String id = ((FDCBillInfo) model).getId().toString();
		ContractBaseDataHelper.synToContractBaseData(ctx, true, id);

	}
	
	/**
	 * ����: �ں�ͬ<<����~֮ǰ>> �������Ŀ�ܺ�Լ�Ƿ�Ϊ���°汾���粻�ǣ������Ϊ���°汾
	 * ���Σ���ͬ���ύ������֮����˺ó�ʱ�䣬�ڼ��Լ�滮�����°汾���Ǿͻ��������
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

		// ͬ��ɾ����ͬ��������
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

		// �ڷ�������ʱ���ж���û�и��,����в���������
		// filter.getFilterItems().add(new
		// FilterItemInfo("contractBillId",set,CompareType.INCLUDE));
		// if(PaymentBillFactory.getLocalInstance(ctx).exists(filter)){
		// throw new ContractException(ContractException.HASPAYMENTBILL);
		// }
		// SQL����������¼��־
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
		// ���ݣ���Ҫ��¼��־
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
		 * //����ֱ��ɾ�����������Ϊ������Ŀ��������õĻ��ǲ�����ɾ���ģ���ORMap�ķ�����������ļ�� String
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
	 * �����Ͽ�ܺ�Լ�Ƿ�����
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
	 * �ҳ��������Ŀ�ܺ�Լ�ļ�¼��(���ı��Ѿ��ϳ������ٲ���)
	 * ������Ҫ�������ı������ж�
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
	 * ����ͬ�Ƿ������ܺ�Լ
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

		// ɾ��֮ǰ�Ķ��ึ�����뵥
		String sql = "delete from T_CON_PayRequestBill where FContractId = ? ";
		Object[] params = new Object[] { con.getId().toString() };
		DbUtil.execute(ctx, sql, params);
		checkNumber(ctx, con.getNumber());
		// �������뵥�����ظ���У��
		String number = payReqNumber;

		// UI����
		// �������뵥
		PayRequestBillInfo prbiNew = new PayRequestBillInfo();

		// ��������
		prbiNew.setPaymentType(prbi.getPaymentType());
		// �ÿ��
		prbiNew.setUseDepartment(prbi.getUseDepartment());
		// �������뵥����
		// prbiNew.setNumber(prbi.getNumber());
		// if(prbi.getId() == null){
		// 2009-2-5 ʼ��ʹ��checkNumber����������ı������ɸ������뵥
		prbiNew.setNumber(number);
		// }else{
		// prbiNew.setNumber(prbi.getNumber());
		// }
		// ��������
		prbiNew.setPayDate(prbi.getPayDate());
		// �տλ
		prbiNew.setSupplier(prbi.getSupplier());
		// ʵ���տλ
		prbiNew.setRealSupplier(prbi.getRealSupplier());
		// ���㷽ʽ
		prbiNew.setSettlementType(prbi.getSettlementType());
		// �տ�����
		prbiNew.setRecBank(prbi.getRecBank());
		// �տ��ʺ�
		prbiNew.setRecAccount(prbi.getRecAccount());
		// �����̶�
		prbiNew.setUrgentDegree(prbi.getUrgentDegree());
		// �ұ�
		prbiNew.setCurrency(prbi.getCurrency());
		// �������
		prbiNew.setLatestPrice(prbi.getLatestPrice());
		// ����
		prbiNew.setExchangeRate(prbi.getExchangeRate());
		// ��д���
		prbiNew.setCapitalAmount(prbi.getCapitalAmount());
		// �������
		prbiNew.setPaymentProportion(prbi.getPaymentProportion());
		// ���깤���������
		prbiNew.setCompletePrjAmt(prbi.getCompletePrjAmt());
		// ��λ�ҽ��
		prbiNew.setAmount(prbi.getAmount());
		// ԭ�ҽ��
		prbiNew.setOriginalAmount(prbi.getOriginalAmount());
		
		// ��ͬ�ڹ��̿�
		// ��λ�ҽ��
		prbiNew.setProjectPriceInContract(prbi.getAmount());
		// ԭ�ҽ��
		prbiNew.setProjectPriceInContractOri(prbi.getOriginalAmount());
		
		// ����˵��
		prbiNew.setUsage(prbi.getUsage());
		// ����˵��
		prbiNew.setMoneyDesc(prbi.getMoneyDesc());
		// ��ע
		prbiNew.setDescription(prbi.getDescription());

		// ����
		prbiNew.setAttachment(prbi.getAttachment());
		// ������Ŀ
		prbiNew.setCurProject(prbi.getCurProject());
		// �ر�״̬
		prbiNew.setHasClosed(false);
		// ��������
		prbiNew.setBookedDate(con.getBookedDate());
		// �ڼ�
		prbiNew.setPeriod(con.getPeriod());

		// �˴���ȡ��
		// ��ͬ��
		prbiNew.setContractNo(con.getNumber());
		// ��ͬID
		prbiNew.setContractId(con.getId().toString());
		prbiNew.setSource(con.getBOSType().toString());
		// ��ͬ����
		prbiNew.setContractName(con.getName());
		// ��ͬ���
		prbiNew.setContractPrice(con.getAmount());
		// ״̬
		prbiNew.setState(state);
		// �������
		// prbiNew.setPaymentProportion(new BigDecimal(100));
		prbiNew.setPaymentProportion(FDCHelper.ONE_HUNDRED);
		// ���깤���������
		prbiNew.setCompletePrjAmt(con.getAmount());// ���깤���������
		// �ɱ�����
		prbiNew.setOrgUnit(con.getOrgUnit());
		// ����Ԫ
		prbiNew.setCU(con.getCU());
		
	
		// ���踶������뵥 update by liang_ren at 2010-5-10
	
		prbiNew.setIsPay(!((ContractWithoutTextInfo) con).isIsNeedPaid());
			
		// ��Ʊ��
		prbiNew.setInvoiceNumber(prbi.getInvoiceNumber());
		// ��Ʊ����
		prbiNew.setInvoiceDate(prbi.getInvoiceDate());
		// ��Ʊԭ��
		prbiNew.setInvoiceOriAmt(prbi.getInvoiceOriAmt());
		// ��Ʊ���
		prbiNew.setInvoiceAmt(prbi.getInvoiceAmt());
		// �ۼƷ�Ʊԭ��
		prbiNew.setAllInvoiceOriAmt(prbi.getAllInvoiceOriAmt());
		// �ۼƷ�Ʊ���
		prbiNew.setAllInvoiceAmt(prbi.getInvoiceAmt());

		return PayRequestBillFactory.getLocalInstance(ctx).addnew(prbiNew);
	}

	// ������ʱ���༭�����ʼ���ݴ����ȷ���
	protected Map _fetchInitData(Context ctx, Map paramMap)
			throws BOSException, EASBizException {

		paramMap.put("isCost", Boolean.FALSE);
		Map initMap = super._fetchInitData(ctx, paramMap);

		return initMap;
	}

	private SelectorItemCollection getSic() {
		// �˹���Ϊ��ϸ��Ϣ����
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

	// �ύʱУ�鵥���ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ
	private void checkBillForSubmit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		// �������ڵ�ǰ�ɱ��ڼ�֮ǰ
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
				// �����ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
	}

	// ���У��
	private void checkBillForAudit(Context ctx, BOSUuid billId,
			FDCBillInfo billInfo) throws BOSException, EASBizException {

		ContractWithoutTextInfo model = (ContractWithoutTextInfo) billInfo;

		if (model == null) {
			model = this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(
					billId.toString()), getSic());
		}
		// ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,new
		// ObjectUuidPK(billId.toString()),sic);
		// ��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId()
				.toString();

		// �Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
		if (isInCore) {
			String curProject = model.getCurProject().getId().toString();

			if (!ProjectPeriodStatusUtil._isClosed(ctx, curProject)) {
				throw new ProjectPeriodStatusException(
						ProjectPeriodStatusException.ISNOT_INIT,
						new Object[] { model.getCurProject().getDisplayName() });
			}
			// //�ɱ��Ѿ��½�
			// PeriodInfo bookedPeriod =
			// FDCUtils.getCurrentPeriod(ctx,curProject,true);
			//if(model.getPeriod().getBeginDate().after(bookedPeriod.getEndDate(
			// ))){
			// throw new ContractException(ContractException.AUD_AFTERPERIOD,new
			// Object[]{model.getNumber()});
			// }

			// ����û���½ᣬ������˵���{0}
			// PeriodInfo finPeriod =
			// FDCUtils.getCurrentPeriod(ctx,curProject,false);
			//if(model.getPeriod().getBeginDate().after(finPeriod.getEndDate()))
			// {
			// throw new ContractException(ContractException.AUD_FINNOTCLOSE,new
			// Object[]{model.getNumber()});
			// }
		}
		// ���ݲ����Ƿ���ʾ��ͬ������Ŀ
		// ��������ʱԤ�����
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx, null,
				FDCConstants.FDC_PARAM_CHARGETYPE);
		if (isShowCharge && model.getConChargeType() != null) {
			invokeAuditBudgetCtrl(ctx, model);
		}
	}

	// ��������ʱ Ԥ�����
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
				// 5.1.1��ʱ����
				bgControlFacade.bgAuditAllowAccess(conBill.getId(),
						FDCConstants.ContractWithoutText, null);
			} else {
				isPass = bgControlFacade.bgAudit(conBill.getId().toString(),
						FDCConstants.ContractWithoutText, null);
			}
			// ����isPass�ж��Ƿ����쳣
			if (!isPass) {
				throw new ContractException(ContractException.BEFOREBGBAL);
			}
		}
	}

	// ���ݷ�����ʱ Ԥ�����
	private void invokeUnAuditBudgetCtrl(Context ctx,
			ContractWithoutTextInfo conBill) throws BOSException,
			EASBizException {

		IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(ctx);
		iBgCtrl.cancelRequestBudget(conBill.getId().toString());
	}

	// ���У��
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,
			FDCBillInfo billInfo) throws BOSException, EASBizException {
		ContractWithoutTextInfo model = (ContractWithoutTextInfo) billInfo;
		// �˹���Ϊ��ϸ��Ϣ����
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
		// ��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId()
				.toString();

		// �Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
		if (isInCore) {
			String curProject = model.getCurProject().getId().toString();

			// �����ڼ��ڹ�����Ŀ��ǰ�ڼ�֮ǰ�����ܷ����
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
		// ���ݲ����Ƿ���ʾ��ͬ������Ŀ
		// ��������ʱԤ�����
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx, null,
				FDCConstants.FDC_PARAM_CHARGETYPE);
		if (isShowCharge && model.getConChargeType() != null) {
			invokeUnAuditBudgetCtrl(ctx, model);
		}
	}

	// �Ƿ�ɱ�
	protected boolean isCost() {
		return false;
	}

	// ��ȡ��ͬ���ͱ���
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

	// ��������ظ�����ȡ����
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info)
			throws BOSException, CodingRuleException, EASBizException {
		handleIntermitNumberForReset(ctx, info);
	}

	// �������Ƿ���ͬ
	private void checkNumber(Context ctx, String number) throws BOSException {
		if (number==null || number.equals("")) {
			return;
		}
		payReqNumber = number;
		// �������뵥�����ظ���У��
		String sql = "select fid from T_CON_PayRequestBill where FNumber = ? ";
		Object[] params = new Object[] { number };

		RowSet rowset;
			rowset = DbUtil.executeQuery(ctx, sql, params);
			try {
				if (rowset.next()) {
					payReqNumber = payReqNumber + "(���ı�)";
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
	 * ������ȡ�����ı���ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
	 * <p>
	 * 1�����Ȳ�ѯ����ͬ���͡���ָ��ֵ����� <br>
	 * 2���ٲ�ѯ���ࡰ��ͬ���͡�Ϊָ��ֵ����� <br>
	 * 3���ٲ�ѯ����ͬ���͡�Ϊ��NULL(ȫ��)��Ϊָ��ֵ����� <br>
	 * <p>
	 * 4.1������ͬ���͡�Ҫ֧�ֵݹ顣���磺����Ҫ�ҵ�ǰ�������û���ҵ�����Ҫ���ҵ��ϼ��������û���ҵ�����Ҫ�����ϼ����������� <br>
	 * 4.2���ݹ���ҡ���ͬ���͡��ϼ�������ͬ���͡������νṹ��ͨ��ֻ��3-4�������Բ�������������⣩ <br>
	 * 
	 * @param ctx
	 *            Ӧ�������ģ��ǿ�
	 * @param contractType
	 *            ��ͬ���ͣ��ɿ�
	 * @param thirdType
	 *            ����״̬��������鵵�����ǿ�
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

		// ȡ�����ı���ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
		ContractWOTCodingTypeCollection codingTypeCollection = ContractCodingUtil.getContractWOTCodingTypeCollection(
				ctx, contractTypeInfo, thirdType);

		return codingTypeCollection;
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
	 * ����ڵ��ñ�������壨ContractWithoutTextControllerBean.handleIntermitNumber1����ʱ��<br>
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
		ContractWithoutTextInfo contractWithoutTextInfo = (ContractWithoutTextInfo) fdcBillInfo;

		if (null == contractWithoutTextInfo.getCodeType()) {
			ContractTypeInfo contractType = contractWithoutTextInfo.getContractType();
			ContractThirdTypeEnum contractThirdTypeEnum = ContractThirdTypeEnum.NEW;
			// ��ȡ���ı���ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
			ContractWOTCodingTypeCollection codingTypeList = (ContractWOTCodingTypeCollection) _getContractWOTCodingType(
					ctx, contractType, contractThirdTypeEnum.getValue());

			if (FdcObjectCollectionUtil.isNotEmpty(codingTypeList)) {
				// ��һ�����ı���ͬ�������ͣ���ӽ�����ƥ�䣩
				ContractWOTCodingTypeInfo firstCodingTypeInfo = codingTypeList.get(0);
				// �������ı���ͬ��������
				contractWithoutTextInfo.setCodeType(firstCodingTypeInfo);
			}

			if (null != contractWithoutTextInfo.getCodeType() && isUpdate && null != contractWithoutTextInfo.getId()
					&& this._exists(ctx, new ObjectUuidPK(contractWithoutTextInfo.getId()))) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("codeType");

				super._updatePartial(ctx, contractWithoutTextInfo, selector);
			}
		}

		// ��ӡ������Ϣ
		logger.info("��ͬ��contractWithoutTextInfo��" + contractWithoutTextInfo);
		logger.info("���ı���ͬ����������ԣ�contractWithoutTextInfo.codeType��" + contractWithoutTextInfo.getCodeType());
	}

	// ////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////

	// ȡ���룬�������������ð�����
	protected String getBindingProperty() {
		return BINDING_PROPERTY;
	}

	/**
	 * �����������ֶβ������������ֵ�����ı䣬����Ҫ���»�ȡ����
	 * 
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected boolean isNeedReHandleIntermitNumberByProperty() {
		return true;
	}

	/**
	 * �������Ƿ�����ϼ���֯���ݵı������
	 * 
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected boolean isRecycleParentOrgNumber() {
		return true;
	}

	/**
	 * ������ȡ�û��ձ���Seletor
	 * 
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
			logger.info("iCodingRuleManager.isUseIntermitNumber(info, curOrgId, BINDING_PROPERTY):"
					+ isUseIntermitNumber);
			logger
					.info("info.getCodeType() != null && iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId):"
							+ flag);

			if (flag) {
				// ������ճɹ��˾�����ѭ��
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
		ContractWithoutTextInfo contractWithoutTextInfo = (ContractWithoutTextInfo) info;

		ContractTypeInfo contractType = contractWithoutTextInfo.getContractType();
		ContractThirdTypeEnum contractThirdTypeEnum = ContractThirdTypeEnum.NEW;
		// ��ȡ���ı���ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
		ContractWOTCodingTypeCollection codingTypeList = (ContractWOTCodingTypeCollection) _getContractWOTCodingType(
				ctx, contractType, contractThirdTypeEnum.getValue());

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

			// ��һ�����ı���ͬ�������ͣ���ӽ�����ƥ�䣩
			ContractWOTCodingTypeInfo firstCodingTypeInfo = codingTypeList.get(0);

			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			for (int i = 0, size = codingTypeList.size(); i < size; i++) {
				ContractWOTCodingTypeInfo tempCodingTypeInfo = codingTypeList.get(i);
				// �������ı���ͬ�������ͣ���ʱ���ã����ں����ȡ�������ʹ�ã�
				contractWithoutTextInfo.setCodeType(tempCodingTypeInfo);

				// ��������ȡҪ֧����֯�ݹ顣���磺����Ҫ�ҵ�ǰ��֯�����û���ҵ�����Ҫ���ҵ��ϼ���֯�������û���ҵ�����Ҫ�����ϼ���֯����������
				for (int j = 0; j < orgIdList.size(); j++) {
					if (setNumber(ctx, contractWithoutTextInfo, orgIdList.get(j).toString(), BINDING_PROPERTY,
							iCodingRuleManager, count)) {
						// ����ɹ�ƥ�䵽�����������ֹѭ����ֱ�ӷ��أ����ı���ͬ��������Ϊ���һ�����õ���ʱֵ��
						return;
					}
				}
			}

			// ���ǰ��ѭ����ʼ��û��ƥ�䵽��������������������ı���ͬ�������ͣ���һ�����ı���ͬ�������ͣ���ӽ�����ƥ�䣩��
			contractWithoutTextInfo.setCodeType(firstCodingTypeInfo);
		}
	}

	/**
	 * ������ƥ�������򣬻�ȡ���룬���ñ���
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
			// ��ȡ��ͬ�ı���������
			CodingRuleInfo codingRuleInfo = iCodingRuleManager.getCodingRule(contractWithoutTextInfo, orgId,
					bindingProperty);
			// ���֧���޸ľ�Ҫ�������Ƿ���ϵ�ǰ���õı������Ĺ���
			if (codingRuleInfo.isIsModifiable()) {
				CodingRuleFactory.getLocalInstance(ctx).checkModifiedNumber(contractWithoutTextInfo, codingRuleInfo,
						contractWithoutTextInfo.getNumber());
			} else {
				// �����������ʾ
				if (codingRuleInfo.isIsAddView()) {
					// ����Ϊ�յ�ʱ��Ҫ��ȡ����
					if (contractWithoutTextInfo.getNumber() == null
							|| "".equals(contractWithoutTextInfo.getNumber().trim())) {
						contractWithoutTextInfo.setNumber(iCodingRuleManager.getNumber(contractWithoutTextInfo, orgId,
								bindingProperty, ""));
					} else {
						// �����벻Ϊ�յ�ʱ��count=0��ʱ����Ҫ���»�ȡ���룬������>0��ʱ��֤�������ظ�Ҫ���»�ȡ�µı���
						if (count > 0) {
							contractWithoutTextInfo.setNumber(iCodingRuleManager.getNumber(contractWithoutTextInfo,
									orgId, bindingProperty, ""));
						}
					}
				} else {
					// ������ǲ�����Ϻţ�ÿ�ζ�Ҫ���ֻ�ȡ
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