package com.kingdee.eas.fdc.basedata;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.param.IParamSetModify;
import com.kingdee.eas.base.param.ParamInfo;
import com.kingdee.eas.base.param.ParamItemFactory;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.common.REParamCheckAdaptor;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCParamCheck extends REParamCheckAdaptor implements IParamSetModify {

	public void check(Context ctx, ParamItemInfo model) throws BOSException,
			EASBizException {

		String orgunitid = null;
		if (model.getOrgUnitID() != null) {
			orgunitid = model.getOrgUnitID().getId().toString();
		}

		try {
			checkParamUpdate(ctx, model, model.getKeyID(), orgunitid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}

	public boolean setParamModify(Context ctx, ParamInfo paramInfo)
			throws BOSException, EASBizException {

		return false;
	}

	/**
	 * FDC����У��
	 * 
	 * @param ctx
	 * @param param
	 *            ��������
	 * @param orgUnitId
	 *            ��֯
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private void checkParamUpdate(Context ctx, ParamItemInfo model,
			ParamInfo param, String orgUnit) throws BOSException,
			EASBizException, SQLException {
//		if(true) return;
		String number = param.getNumber();
		//			 			
		if (number.equals(FDCConstants.FDC_PARAM_INCORPORATION)) {
			if ("true".equals(model.getValue())) {
				boolean isSimple = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);

				if (isSimple) {
					// �����˳ɱ��½�
					throw new FDCException(FDCException.COSTNOSIMPLE);
				}
				boolean isSettle = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT);

				if (isSettle) {
					// �����˽���������ƾ֤
					throw new FDCException(FDCException.SETTLEMENTCANTCOST);
				}

				ProjectPeriodStatusFactory.getLocalInstance(ctx).paramCheck(
						orgUnit);
			} else {

				// ������ڽ�����ʼ���Ĺ�����Ŀ
				StringBuffer sql = new StringBuffer();
				sql
						.append("select Fid from T_FNC_ProjectPeriodStatus where  FIsClosed=1 and FOrgUnitID=?		\r\n");
				IRowSet rs = DbUtil.executeQuery(ctx, sql.toString(),
						new Object[] { orgUnit });
				if (rs != null && rs.next()) {
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.HASCLOSED);
				}

				// ������ڲ�ͬ���ڼ䲻��ȡ������һ�廯
				StringBuffer selectCurProSql = new StringBuffer();
				selectCurProSql
						.append("select p.fid Fid from T_FNC_ProjectPeriodStatus p 		\r\n");
				selectCurProSql
						.append(" inner join t_fdc_curproject c on c.fid=p.fprojectid									\r\n");
				selectCurProSql
						.append(" where c.ffullorgunit=?	 and ( Fstartperiodid<>FCostPeriodID  or  Fstartperiodid<>FFinacialPeriodID)	 ");

				IRowSet rs2 = DbUtil.executeQuery(ctx, selectCurProSql
						.toString(), new Object[] { orgUnit });
				if (rs2 != null && rs2.next()) {
					// �����Ѿ��½�Ĺ�����Ŀ
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.EX_HASCLOSED);
				}

				if (ProjectPeriodStatusUtil.checkPeriodClose(ctx, orgUnit)) {
					// �Ѿ������½�����
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.EX_HASCLOSED);
				}
			}

		} else if (number.equals(FDCConstants.FDC_PARAM_FINACIAL)) {
			if ("true".equals(model.getValue())) {
				
				//�������������ύ״̬�ĵ���
				boolean isSplitSubmit=FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit,FDCConstants.FDC_PARAM_SPLITSUBMIT);
				if(isSplitSubmit){
					throw new FDCException(FDCException.CANNOTUSECOMPLEX);
				}
				
				boolean isIncorpr = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_INCORPORATION);
				if (!isIncorpr) {
					// δ���óɱ��½�
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.UN_START);
				}

				boolean isSimple = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
				if (isSimple) {
					// �����˸���ģʽ
					throw new FDCException(FDCException.FINANCIALNOSIMPLE);
				}
				boolean isSettle = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT);

				if (isSettle) {
					// �����˽���������ƾ֤
					throw new FDCException(FDCException.SETTLEMENTCANTCOMPLEXT);
				}
				// String sql2 = " select ch.fid,ch.Fname_l2 from
				// t_org_costcenter pa \r\n"+
				// " inner join t_org_costcenter ch on
				// charindex(pa.flongnumber||'!',ch.flongnumber||'!')=1 \r\n"+
				// " where pa.fid=? and ch.FisBizUnit=1 \r\n";
				// IRowSet rs = DbUtil.executeQuery(ctx,sql2.toString(),new
				// Object[]{orgUnit});
				// if(rs!=null){
				// try {
				// while(rs.next()){
				// String org = rs.getString("Fid");
				// String name = rs.getString("Fname_l2");
				// ���ú�ͬ�ɽ��ж�ν��㣬�������ò���һ�廯
				boolean hasSettleMore = FDCUtils.getDefaultFDCParamByKey(ctx,
						null, FDCConstants.FDC_PARAM_MORESETTER);
				if (hasSettleMore) {
					//
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.NOTFINACIALBYSEETTMORE,
							new Object[] { "" });
				}
				// }
				// } catch (SQLException e) {
				// e.printStackTrace();
				// throw new BOSException(e);
				// }
				// }
				boolean isAdjustDelete = FDCUtils.getDefaultFDCParamByKey(ctx,
						null, FDCConstants.FDC_PARAM_AIMCOSTADJUSTDELETE);
				if(isAdjustDelete){
					throw new FDCException(FDCException.AIMCOSTADJUSTDELETE);
				}
			} else {
				if (ProjectPeriodStatusUtil.checkExistVoucher(ctx, orgUnit)) {
					// �Ѿ����ɹ�ƾ֤�����ܷ����ò���һ�廯
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.HASVOUCHERD);
				}
				boolean isSeparate = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
				if(isSeparate){
					throw new FDCException(FDCException.SEPARATE);
				}
				/*********�ж��Ƿ����÷�Ʊ����  --by neo*********/
				if(FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit,FDCConstants.FDC_PARAM_INVOICEMRG)){
					throw new FDCException(FDCException.INVOICEDEACTIVE);
				}
			}
		} else if (number.equals(FDCConstants.FDC_PARAM_MORESETTER)) {
			// ��ͬ��ν���
			if ("true".equals(model.getValue())) {
				// String companyId =
				// ContextUtil.getCurrentFIUnit(ctx).getId().toString();
				String sql2 = "  select fid,Fname_l2  from t_org_Company  	 where FisBizUnit=1			";
				// " inner join t_org_Company ch on
				// charindex(pa.flongnumber||'!',ch.flongnumber||'!')=1 \r\n"+
				// " where pa.fid=? and ch.FisBizUnit=1 \r\n";
				IRowSet rs = DbUtil.executeQuery(ctx, sql2); // ,new
				// Object[]{orgUnit}
				if (rs != null) {
					try {
						while (rs.next()) {
							String org = rs.getString("Fid");
							String name = rs.getString("Fname_l2");
							// ���ú�ͬ�ɽ��ж�ν��㣬�������ò���һ�廯
							boolean isIncorpr = FDCUtils
									.getDefaultFDCParamByKey(ctx, org,
											FDCConstants.FDC_PARAM_FINACIAL);
							if (isIncorpr) {
								// ���ò���һ�廯���������ú�ͬ�ɽ��ж�ν���
								throw new ProjectPeriodStatusException(
										ProjectPeriodStatusException.NOTSETTMORE,
										new Object[] { name });
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
						throw new BOSException(e);
					}
				}
			} else {
				StringBuffer selectCurProSql = new StringBuffer();
				selectCurProSql
						.append("select p.Fid from T_Con_ContractSettlementBill p  		\r\n");
				// selectCurProSql.append(" inner join t_fdc_curproject c on
				// c.fid=p.fcurprojectid \r\n");
				selectCurProSql.append(" where  p.FIsFinalSettle=0	 ");
				// p.ForgUnitId=? and

				IRowSet rs2 = DbUtil.executeQuery(ctx, selectCurProSql
						.toString());
				if (rs2 != null && rs2.next()) {
					// ��ǰ��֯�Ĺ�����Ŀ�Ѿ����ڲ������ս���ĺ�ͬ���㵥�����ܷ�����
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.CBTNOTSETTMORE);
				}				
			}
			// Ŀǰ���ز�ҵ��֧�ֶԺ�ͬ��ν���,��˲����޸ĸò���.�����޸�,��ȷ��������ʾ.����ϵ�з�����
			// throw new FDCException(FDCException.NOSUPPORTMORESETT);
		} else if (number.equals(FDCConstants.FDC_PARAM_SIMPLEFINACIAL)) {
			if ("true".equals(model.getValue())) {
				// ������Ҫ���е�У��
				boolean isIncorpr = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_INCORPORATION);

				if (isIncorpr) {
					// �����˳ɱ��½�
					throw new FDCException(FDCException.SIMPLENOCOST);
				}
				boolean isFinancial = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_FINACIAL);
				if (isFinancial) {
					// �����˸���ģʽ
					throw new FDCException(FDCException.SIMPLENOFINANCIAL);
				}
				// ������Ҫ���еĴ���

				// �������еĸ������͵����Ϊ���ȿ�
//				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//				builder.clear();
//				builder
//						.appendSql("update T_FDC_PaymentType set FPayTypeID = ?");
//				builder.addParam(PaymentTypeInfo.progressID);
//				builder.executeUpdate();
			
			}
			else {
				// ������Ҫ���е�У��
				//����ƾ֤�ĸ��
			/*	add by zkyan
				 * FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
					builder.clear();
					builder
							.appendSql("select count(*) as count from t_cas_paymentbill where FFiVouchered = 1 and fsourcetype=107 and fcompanyid = ?");
					builder.addParam(orgUnit);
					IRowSet rowSet = builder.executeQuery();
					int oriSize = 0;
					if (rowSet.size() > 0) {
						rowSet.next();
						oriSize = rowSet.getInt("count");
					}
					//����ƾ֤�ĸ�����
					builder = new FDCSQLBuilder(ctx);
					builder.clear();
					builder
							.appendSql("select count(*) as count from t_fnc_paymentsplit where FFivouchered = 1 and fpaymentbillid in (select fid from t_cas_paymentbill where fcompanyid = ?)");
					builder.addParam(orgUnit);
					rowSet = builder.executeQuery();
					int newSize = 0;
					if (rowSet.size() > 0) {
						rowSet.next();
						newSize = rowSet.getInt("count");
					}
					
					if(oriSize>newSize){
						throw new FDCException(FDCException.PAYMENTEXIST);
					}
					// ������Ҫ���еĴ���
					boolean isSimpleInvoice = FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit,
							FDCConstants.FDC_PARAM_SIMPLEINVOICE);
					if (isSimpleInvoice) {
						// ���Ƚ��ò��������������Է�Ʊ���Ϊ׼�ƿ����ɱ�����
						throw new FDCException(FDCException.SIMPLEINVOICEENABLED);
					}  */
			}
		}
		/*****
		 * �Y�������ɑ{�C�޸�
		 */
		else if(number.equals(FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT)){ 
			if ("true".equals(model.getValue())) {
				
				/***
				 * ����������ƾ֤�ϼ��Ų���
				 * ��ҪУ��������֯�Ƿ������óɱ��½�͸���ģʽһ�廯
				 * ���������һ����֯������һ�廯����ģʽһ�廯�Ĳ������������ý������ɵĲ���
				 */
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("keyID.number",FDCConstants.FDC_PARAM_INCORPORATION));
				filter.getFilterItems().add(new FilterItemInfo("Value","true"));
				
				
				// ������Ҫ���е�У��
				boolean isIncorpr = ParamItemFactory.getLocalInstance(ctx).exists(filter);
//					FDCUtils.getDefaultFDCParamByKey(ctx,
//						orgUnit, FDCConstants.FDC_PARAM_INCORPORATION);

				if (isIncorpr) {
					// �����˳ɱ��½�
					throw new FDCException(FDCException.COSTCANTSETTLEMENT);
				}
				filter.getFilterItems().clear();
				filter.getFilterItems().add(new FilterItemInfo("keyID.number",FDCConstants.FDC_PARAM_FINACIAL));
				filter.getFilterItems().add(new FilterItemInfo("Value","true"));
				
				boolean isFinancial = ParamItemFactory.getLocalInstance(ctx).exists(filter);
//					FDCUtils.getDefaultFDCParamByKey(ctx,
//						orgUnit, FDCConstants.FDC_PARAM_FINACIAL);
				if (isFinancial) {
					// �����˸���ģʽ
					throw new FDCException(FDCException.COMPLEXCANTSETTLEMENT);
				}
				
//				boolean isSimple = FDCUtils.getDefaultFDCParamByKey(ctx,
//						orgUnit, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
//				if (isSimple) {
//					// �����˼�ģʽ
//					throw new FDCException(FDCException.SIMPLECANTSETTLEMENT);
//				}
			}
			else{
				/***
				 * ���Õr�M�е�У�
				 */
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
//				builder
//						.appendSql("select count(*) as count from T_CON_ContractSettlementBill where FFiVouchered = 1 ");
//				//builder.addParam(orgUnit);
//				IRowSet rowSet = builder.executeQuery();
//				int oriSize = 0;
//				if (rowSet.size() > 0) {
//					rowSet.next();
//					oriSize = rowSet.getInt("count");
//				}
				//����ƾ֤�ĽY����
				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder
						.appendSql("select count(*) as count from T_CON_SettlementCostSplit where FFivouchered = 1 ");
				//builder.addParam(orgUnit);
				IRowSet rowSet = builder.executeQuery();
				int newSize = 0;
				if (rowSet.size() > 0) {
					rowSet.next();
					newSize = rowSet.getInt("count");
				}
				
				if(newSize>0){
					throw new FDCException(FDCException.SETTELMENTCOSTSPLITEXIST);
				}
				// ������Ҫ���еĴ���
			}
		}else if (number.equals(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND)){
			if ("true".equals(model.getValue())){
				boolean isSimple = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
					if (!isSimple) {
						// û�����ü�ģʽ
						throw new FDCException(FDCException.NOTOPENSIMPLE);
					}          
			}
		}else if (number.equals(FDCConstants.FDC_PARAM_MORESETTER_ALLNOTPAID)){
			if ("true".equals(model.getValue())) {
				boolean hasSettleMore = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_MORESETTER);
				// û�����ö�ν���ʱ���������� ���깤δ����=�ۼ��깤-�Ѹ�� ���㹫ʽ
				if (!hasSettleMore){
					throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CANTSTARTBECAUSEMORESETTLE);
				}
			}
			
		}else if(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT.equals(number)){
			//String companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			if ("true".equals(model.getValue())) {
				/* R110222-006 Ҫ���ģʽ��Ҳ�����������������Added by Owen_wen 2011-05-10
				boolean isFinance = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_FINACIAL);
				boolean isAdjust = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
				//����ģʽ�����ģʽδ����
				if (!isFinance || !isAdjust) {
					throw new FDCException(FDCException.ENABLEFINANCEANDADJUST);
				}
				*/ 
				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				
				/* modified by zhaoqin for R140421-0018 on 2014/04/14 start */
				/*
				 * ��׼��Ʒ�����������뵥���������£�
		     	 * 1�����ӿ��ƣ�����FDC317�ڴ��ڸ������뵥�򹤳���ȷ�ϵ��ǲ������޸�
		     	 * 2����ȷ�ֶα��⣺����FDC317Ϊ�ǣ�������ʱ���������뵥���ֶΡ������깤��������
		     	 * 	     ���ĳɡ�δ�����깤��������������FDC317Ϊ�񣬼�������ʱ���������뵥���ֶΡ������깤����������ʾ���仯
		     	 * 3�������ȿ����������Ʋ������������壬�ݲ������������������롱���=
		     	 * 	  �����ȿ�������*��δ�����깤�������������߼����䣬���С�δ�����깤��������ȡֵ�����ݲ����޸��Ҳ��仯��
		     	 * 	  ������������롰���ȿ�������ֵ�仯���㣬����0����֧�����븺����
				 */
				//builder.appendSql("SELECT count(*) as count FROM T_CON_PAYREQUESTBILL WHERE FOrgUnitID = ? ");
				//builder.appendSql("AND ISNULL(FCompletePrjAmt,0) > 0");
				builder.appendSql("select sum(count) as count from ( ");
				builder.appendSql("	select count(fid) as count FROM T_CON_PayRequestBill where ISNULL(FCompletePrjAmt,0) > 0 and fcurprojectid in (select fid from t_fdc_curproject where FFullOrgUnit = ?) ");
				builder.appendSql("	union ");
				builder.appendSql("	select count(fid) as count from T_CON_PayRequestBill where FOrgUnitID = ? and ISNULL(FCompletePrjAmt,0) > 0 ) t");
				builder.addParam(orgUnit);
				//builder.addParam(companyID);
				builder.addParam(orgUnit);
				/* modified by zhaoqin for R140421-0018 on 2014/04/14 end */
				
				IRowSet rowSet = builder.executeQuery();
				int newSize = 0;
				if (rowSet.size() > 0) {
					rowSet.next();
					newSize = rowSet.getInt("count");
				}
				if(newSize>0){
					//����¼�����깤�������ĸ������뵥
					throw new FDCException(FDCException.EXISTCOMPLETEPRJAMTBILL);
				}
			} else {
				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				
				/* modified by zhaoqin on 2014/04/14 start */
				//builder.appendSql("select count(*) as count from T_FNC_WorkLoadConfirmBill where FOrgUnitID = ?");
				builder.appendSql("select sum(count) as count from ( ");
				builder.appendSql("	select count(fid) as count from T_FNC_WorkLoadConfirmBill where fcurprojectid in (select fid from t_fdc_curproject where FFullOrgUnit = ?) ");
				builder.appendSql("	union ");
				builder.appendSql("	select count(fid) as count from T_FNC_WorkLoadConfirmBill where FOrgUnitID = ? ) t");
				builder.addParam(orgUnit);
				//builder.addParam(companyID);
				builder.addParam(orgUnit);
				/* modified by zhaoqin on 2014/04/14 end */
				
				IRowSet rowSet = builder.executeQuery();
				int newSize = 0;
				if (rowSet.size() > 0) {
					rowSet.next();
					newSize = rowSet.getInt("count");
				}
				if(newSize>0){
					//���ڹ�����ȷ�ϵ�
					throw new FDCException(FDCException.EXISTWORKLOADBILL);
				}
				/*********�ж��Ƿ����÷�Ʊ����  --by neo*********/
				if(FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit,FDCConstants.FDC_PARAM_INVOICEMRG)){
					throw new FDCException(FDCException.INVOICEDEACTIVE);
				}
			}
		}else if(FDCConstants.FDC_PARAM_ADJUSTVOURCHER.equals(number)){
			if("true".equals(model.getValue())){
				//
			}else{
				boolean isSeparate = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
				if(isSeparate){
					throw new FDCException(FDCException.SEPARATE);
				}
				/*********�ж��Ƿ����÷�Ʊ����  --by neo*********/
				if(FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit,FDCConstants.FDC_PARAM_INVOICEMRG)){
					throw new FDCException(FDCException.INVOICEDEACTIVE);
				}
			}
		}else if(FDCConstants.FDC_PARAM_INVOICEMRG.equals(number)){		//��Ʊ�������������ù��������롢����һ�廯��ƾ֤����ģʽ  -by neo
			/**
			 * ��У���ˣ���ģʽҲ����֧�ַ�Ʊ by hpw 2011.2.24
			 */
/*//			���ù������븶�����
			boolean isSeparatePayment = false;	
//			���ø���ģʽ�ɱ�����һ�廯
			boolean isFinancial = false;
//			����ƾ֤����ģʽ
			boolean isAdjustVoucher = false;
			if("true".equals(model.getValue())){
				isSeparatePayment = FDCUtils.getDefaultFDCParamByKey(
						ctx, orgUnit, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
				isFinancial = FDCUtils.getDefaultFDCParamByKey(
						ctx, orgUnit, FDCConstants.FDC_PARAM_FINACIAL);
				isAdjustVoucher = FDCUtils.getDefaultFDCParamByKey(
						ctx, orgUnit, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
				if(!isSeparatePayment || !isFinancial || !isAdjustVoucher){
					throw new FDCException(FDCException.INVOICEACTIVE);
				}
			}*/
		}
		// by Cassiel_peng
		else if(number.equals(FDCConstants.FDC_PARAM_SPLITSUBMIT)){
			
			if("true".equals(model.getValue())){
				/***
				 * �Ƿ��������ύ״̬�ĵ��ݣ����Ų���
				 * ��ҪУ��������֯�Ƿ������ø���ģʽһ�廯
				 * ���������һ����֯�����˸���ģʽһ�廯����������"�Ƿ��������ύ״̬�ĵ�"
				 */
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("keyID.number",FDCConstants.FDC_PARAM_FINACIAL));
				filter.getFilterItems().add(new FilterItemInfo("Value","true"));
				
				boolean isfinacial = ParamItemFactory.getLocalInstance(ctx).exists(filter);
				if(isfinacial){//��������˼�ģʽ�ɱ�����һ�廯
					throw new FDCException(FDCException.CANNOTSPLITSUBMIT);//���������ύ״̬�ĵ���
				}
			}
		}else if(number.equals(FDCConstants.FDC_PARAM_WRITEMARK)){
			/**
			 * ���ò���
			 */
			if("false".equals(model.getValue())){
				//���ò���""��ʱ���ж������ͬ�ж������(����ÿ�������Ƿ��ж���汾)�Ͳ������û��ٽ��ò���
				//��Ϊ���ж�����ĵ�������ÿͻ����ò����ٴδ����ĵĵ�ʱ��Ͳ�֪������Ҫ����һ��������  by Cassiel_peng
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
				//���һ����ͬ�ж���ļ�����˵���ж������                                                                                        
				builder.appendSql("select FParent, count(FFileType) sum from t_Con_Contractcontent \n ");
				builder.appendSql("where FFileType in (select distinct FFileType from t_con_contractcontent) \n");
				builder.appendSql("and FParent!='' or FParent is not null \n ");
				builder.appendSql("group by FParent \n ");
				IRowSet rowSet=builder.executeQuery();
				Set synchroIDS=new HashSet();   
				while(rowSet.next()){
					int content_count=rowSet.getInt("sum");
					if(content_count>1){
						throw new FDCException(FDCException.MORNTHANONE);
					}
					if(content_count==1){
						synchroIDS.add(rowSet.getString("FParent"));
					}
				}
				//ͬ��ID
				if(synchroIDS.size()>0){
					builder.clear();
					builder.appendParam("update T_CON_ContractContent set FContractID=FParent where  FParent ",synchroIDS.toArray());
					builder.executeUpdate();
				}
				/**
				 * ���ò���
				 */
			}else{
				// ("���øò��������Ϊĳһ����ͬ����˶�����Ľ��������ٽ��øò�����");
				//ͬ��ID
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
				builder.appendSql("update T_CON_ContractContent set FParent=FContractID where FContractID!='' or FContractID is not null \n ");
//				builder.appendSql(" having count(FFileType)>0 group by FContractID ");
				builder.executeUpdate();
			}
		}else if(number.equals(FDCConstants.FDC_PARAM_AIMCOSTAUDIT)){
			if("true".equals(model.getValue())){
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.appendSql(" If exists (select 1 from KSQL_USERCOLUMNS where KSQL_COL_NAME ='FState' and KSQL_COL_TABNAME='T_AIM_AIMCOST') \n"+
					" update t_aim_aimcost set fstate='4AUDITTED' where fislastversion=1 and fstate='1SAVED';");
//				" update t_aim_aimcost set fstate=? where fislastversion=? and fstate=?;");
//				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
//				builder.addParam(Boolean.TRUE);
//				builder.addParam(FDCBillStateEnum.SAVED_VALUE);
				// �������б��� invalid column index,��Ϊ���ǹ̶���ֱֵ��д��sql�ﲻ�� 
				builder.executeUpdate();
			}
		}else if(FDCConstants.FDC_PARAM_AIMCOSTADJUSTDELETE.equals(number)){
			/**
			 * ��������ø���ģʽ�ɱ�����һ�廯�����κ�һ����֯�²���ֵΪ�ǣ�
			 * �������á�����ɾ���������ɱ��ĵ�����¼������ʱ��ʾ���Ѿ����ø���ģʽ�ɱ�����һ�廯��
			 * ���Բ�����ɾ���������ɱ��ĵ�����¼�����Ҳ��������ã��������������ɾ���������ɱ��ĵ�����¼������ֵΪ�ǣ�
			 * �����κ���֯�����á����ø���ģʽ�ɱ�����һ�廯������ʱ��ʾ���Ѿ�����ɾ���������ɱ��ĵ�����¼��
			 * ���Բ��������ø���ģʽ�ɱ�����һ�廯�����Ҳ���������
			 */
			if ("true".equals(model.getValue())) {
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("select count(*) as count from T_BAS_ParamItem item \n");
				builder.appendSql("inner join T_BAS_Param param on param.FID=item.FKeyID \n");
				builder.appendSql(" where param.FNumber=? and item.FValue_L2 = 'true' \n");
				builder.addParam(FDCConstants.FDC_PARAM_FINACIAL);
				IRowSet rowSet = builder.executeQuery();
				int size = 0;
				if (rowSet.size() > 0) {
					rowSet.next();
					size = rowSet.getInt("count");
				}
				if (size > 0) {
					throw new FDCException(
							FDCException.EXISTFINANCIALORG);
				}

			}
		}
		/**
		*ϵͳ���� ��δ�����ͬ��ʵ���������ʵ�ֲ�ֵʱ�Ƿ��ϸ����   ����ʱ���ж�
		*����Ƿ��������˼��Ų�������ͬ�ɽ��ж�ν��㡱����û�����ã�����ʾ������
		*���ü��Ų�������ͬ�ɽ��ж�ν��㡿�����������ã���ֱ�����ñ�������
		* by jian_wen 2009.12.15
		**/
		else if (number.equals(FDCConstants.FDC_PARAM_ISCONTROLPAYMENT)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("keyID.number",FDCConstants.FDC_PARAM_MORESETTER));
			filter.getFilterItems().add(new FilterItemInfo("Value","true"));
			boolean b = ParamItemFactory.getLocalInstance(ctx).exists(filter);
			if(!b){
				throw new FDCException(
						FDCException.NOTCANCELCANELISCONTROLPAYMENT);
			}
		}else if(number.equals(FDCConstants.FDC_PARAM_SIMPLEINVOICE)){
			//			if ("true".equals(model.getValue())) {
			//				/**
			//				 * �������á����ü�ģʽ�ɱ�����һ�廯������ʱ�������������á�
			//				 * �����á����ü�ģʽ�ɱ�����һ�廯��ʱ�������Ƚ��ñ�������������ʾ�����Ƚ��ò��������������Է�Ʊ���Ϊ׼�ƿ����ɱ�����
			//				 */
			//				boolean isSimple = FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit,
			//						FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
			//				if (!isSimple) {
			//					// û�����ü�ģʽ
			//					throw new FDCException(FDCException.ENABLESIMPLE);
			//				}   
			//			}
			
		}
		/***
		 *���ȹ�������
		 */
		else if(number.equals(FDCConstants.FDCSCH_PARAM_BASEONTASK)){
			if("false".equals(model.getValue())){
				boolean isBaseOnContract =FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit, FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT)	;
				if(isBaseOnContract){
					throw new FDCException(FDCException.BASEONTASKMUSTDISABLEANOTHER);
				}
		     }
		}else if(number.equals(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT)){
			if("false".equals(model.getValue())){
				boolean isBaseOnTask =FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit, FDCConstants.FDCSCH_PARAM_BASEONTASK)	;
				if(isBaseOnTask){
					throw new FDCException(FDCException.BASEONCONTRACTMUSTDISABLEANOTHER);
				}
			}
		}else if(number.equals(FDCConstants.FDC_PARAM_CHECKPAYMENTSPLIT)){
			if ("false".equals(model.getValue())) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("keyID.number",FDCConstants.FDC_PARAM_CHECKPAYMENTALLSPLIT));
				filter.getFilterItems().add(new FilterItemInfo("Value","true"));
				boolean isPaymentAllSplit = ParamItemFactory.getLocalInstance(ctx).exists(filter);
				if(isPaymentAllSplit){//���ڲ����ɲ���Ϊ��
					throw new FDCException(FDCException.SPLITALLENABLED);
				}
			}
		}else if(number.equals(FDCConstants.FDC_PARAM_CHECKPAYMENTALLSPLIT)){
			if ("true".equals(model.getValue())) {
				boolean isPaymentSplit = FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit, FDCConstants.FDC_PARAM_CHECKPAYMENTSPLIT);
				if(!isPaymentSplit){//�����ò��
					throw new FDCException(FDCException.MUSTSPLIT);
				}
				boolean canSplitSubmit = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_SPLITSUBMIT);
				if(!canSplitSubmit){//�����ò��
					throw new FDCException(FDCException.CANSPLITSUBMIT);
				}
			}
		}else if(number.equals(FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT)){
			if ("true".equals(model.getValue())) {
				boolean isMulti = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_MULTIPROJECT);
				if(isMulti){//������
					throw new FDCException(FDCException.MULTIPROJECT);
				}
			}
		}else if(number.equals(FDCConstants.FDC_PARAM_MULTIPROJECT)){
			if ("true".equals(model.getValue())) {
				boolean isCross = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT);
				if(isCross){//������
					throw new FDCException(FDCException.CROSSPROJECTSPLIT);
				}
			}
		}
	}
}
