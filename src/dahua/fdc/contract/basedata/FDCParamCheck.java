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
	 * FDC参数校验
	 * 
	 * @param ctx
	 * @param param
	 *            参数对象
	 * @param orgUnitId
	 *            组织
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
					// 启用了成本月结
					throw new FDCException(FDCException.COSTNOSIMPLE);
				}
				boolean isSettle = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT);

				if (isSettle) {
					// 启用了结算拆分生成凭证
					throw new FDCException(FDCException.SETTLEMENTCANTCOST);
				}

				ProjectPeriodStatusFactory.getLocalInstance(ctx).paramCheck(
						orgUnit);
			} else {

				// 如果存在结束初始化的工程项目
				StringBuffer sql = new StringBuffer();
				sql
						.append("select Fid from T_FNC_ProjectPeriodStatus where  FIsClosed=1 and FOrgUnitID=?		\r\n");
				IRowSet rs = DbUtil.executeQuery(ctx, sql.toString(),
						new Object[] { orgUnit });
				if (rs != null && rs.next()) {
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.HASCLOSED);
				}

				// 如果存在不同的期间不能取消财务一体化
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
					// 存在已经月结的工程项目
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.EX_HASCLOSED);
				}

				if (ProjectPeriodStatusUtil.checkPeriodClose(ctx, orgUnit)) {
					// 已经存在月结数据
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.EX_HASCLOSED);
				}
			}

		} else if (number.equals(FDCConstants.FDC_PARAM_FINACIAL)) {
			if ("true".equals(model.getValue())) {
				
				//启用了允许拆分提交状态的单据
				boolean isSplitSubmit=FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit,FDCConstants.FDC_PARAM_SPLITSUBMIT);
				if(isSplitSubmit){
					throw new FDCException(FDCException.CANNOTUSECOMPLEX);
				}
				
				boolean isIncorpr = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_INCORPORATION);
				if (!isIncorpr) {
					// 未启用成本月结
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.UN_START);
				}

				boolean isSimple = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
				if (isSimple) {
					// 启用了复杂模式
					throw new FDCException(FDCException.FINANCIALNOSIMPLE);
				}
				boolean isSettle = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT);

				if (isSettle) {
					// 启用了结算拆分生成凭证
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
				// 启用合同可进行多次结算，不能启用财务一体化
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
					// 已经生成过凭证，不能反启用财务一体化
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.HASVOUCHERD);
				}
				boolean isSeparate = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
				if(isSeparate){
					throw new FDCException(FDCException.SEPARATE);
				}
				/*********判断是否启用发票参数  --by neo*********/
				if(FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit,FDCConstants.FDC_PARAM_INVOICEMRG)){
					throw new FDCException(FDCException.INVOICEDEACTIVE);
				}
			}
		} else if (number.equals(FDCConstants.FDC_PARAM_MORESETTER)) {
			// 合同多次结算
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
							// 启用合同可进行多次结算，不能启用财务一体化
							boolean isIncorpr = FDCUtils
									.getDefaultFDCParamByKey(ctx, org,
											FDCConstants.FDC_PARAM_FINACIAL);
							if (isIncorpr) {
								// 启用财务一体化，不能启用合同可进行多次结算
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
					// 当前组织的工程项目已经存在不是最终结算的合同结算单，不能反启用
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.CBTNOTSETTMORE);
				}				
			}
			// 目前房地产业务不支持对合同多次结算,因此不能修改该参数.如需修改,请确认用于演示.请联系研发中心
			// throw new FDCException(FDCException.NOSUPPORTMORESETT);
		} else if (number.equals(FDCConstants.FDC_PARAM_SIMPLEFINACIAL)) {
			if ("true".equals(model.getValue())) {
				// 启用需要进行的校验
				boolean isIncorpr = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_INCORPORATION);

				if (isIncorpr) {
					// 启用了成本月结
					throw new FDCException(FDCException.SIMPLENOCOST);
				}
				boolean isFinancial = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_FINACIAL);
				if (isFinancial) {
					// 启用了复杂模式
					throw new FDCException(FDCException.SIMPLENOFINANCIAL);
				}
				// 启用需要进行的处理

				// 设置所有的付款类型的类别为进度款
//				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//				builder.clear();
//				builder
//						.appendSql("update T_FDC_PaymentType set FPayTypeID = ?");
//				builder.addParam(PaymentTypeInfo.progressID);
//				builder.executeUpdate();
			
			}
			else {
				// 禁用需要进行的校验
				//生成凭证的付款单
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
					//生成凭证的付款拆分
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
					// 禁用需要进行的处理
					boolean isSimpleInvoice = FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit,
							FDCConstants.FDC_PARAM_SIMPLEINVOICE);
					if (isSimpleInvoice) {
						// 请先禁用参数“财务帐务以发票金额为准计开发成本”。
						throw new FDCException(FDCException.SIMPLEINVOICEENABLED);
					}  */
			}
		}
		/*****
		 * 結算拆分生成憑證修改
		 */
		else if(number.equals(FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT)){ 
			if ("true".equals(model.getValue())) {
				
				/***
				 * 结算拆分生成凭证上集团参数
				 * 需要校验所有组织是否有启用成本月结和复杂模式一体化
				 * 如果有任意一个组织启用了一体化复杂模式一体化的参数，则不能启用结算生成的参数
				 */
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("keyID.number",FDCConstants.FDC_PARAM_INCORPORATION));
				filter.getFilterItems().add(new FilterItemInfo("Value","true"));
				
				
				// 启用需要进行的校验
				boolean isIncorpr = ParamItemFactory.getLocalInstance(ctx).exists(filter);
//					FDCUtils.getDefaultFDCParamByKey(ctx,
//						orgUnit, FDCConstants.FDC_PARAM_INCORPORATION);

				if (isIncorpr) {
					// 启用了成本月结
					throw new FDCException(FDCException.COSTCANTSETTLEMENT);
				}
				filter.getFilterItems().clear();
				filter.getFilterItems().add(new FilterItemInfo("keyID.number",FDCConstants.FDC_PARAM_FINACIAL));
				filter.getFilterItems().add(new FilterItemInfo("Value","true"));
				
				boolean isFinancial = ParamItemFactory.getLocalInstance(ctx).exists(filter);
//					FDCUtils.getDefaultFDCParamByKey(ctx,
//						orgUnit, FDCConstants.FDC_PARAM_FINACIAL);
				if (isFinancial) {
					// 启用了复杂模式
					throw new FDCException(FDCException.COMPLEXCANTSETTLEMENT);
				}
				
//				boolean isSimple = FDCUtils.getDefaultFDCParamByKey(ctx,
//						orgUnit, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
//				if (isSimple) {
//					// 启用了简单模式
//					throw new FDCException(FDCException.SIMPLECANTSETTLEMENT);
//				}
			}
			else{
				/***
				 * 禁用時進行的校驗
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
				//生成凭证的結算拆分
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
				// 禁用需要进行的处理
			}
		}else if (number.equals(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND)){
			if ("true".equals(model.getValue())){
				boolean isSimple = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
					if (!isSimple) {
						// 没有启用简单模式
						throw new FDCException(FDCException.NOTOPENSIMPLE);
					}          
			}
		}else if (number.equals(FDCConstants.FDC_PARAM_MORESETTER_ALLNOTPAID)){
			if ("true".equals(model.getValue())) {
				boolean hasSettleMore = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_MORESETTER);
				// 没有启用多次结算时，不能启用 “完工未付款=累计完工-已付款” 计算公式
				if (!hasSettleMore){
					throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CANTSTARTBECAUSEMORESETTLE);
				}
			}
			
		}else if(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT.equals(number)){
			//String companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			if ("true".equals(model.getValue())) {
				/* R110222-006 要求简单模式下也能启用这个参数－－Added by Owen_wen 2011-05-10
				boolean isFinance = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_FINACIAL);
				boolean isAdjust = FDCUtils.getDefaultFDCParamByKey(ctx,
						orgUnit, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
				//复杂模式或调整模式未启用
				if (!isFinance || !isAdjust) {
					throw new FDCException(FDCException.ENABLEFINANCEANDADJUST);
				}
				*/ 
				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				
				/* modified by zhaoqin for R140421-0018 on 2014/04/14 start */
				/*
				 * 标准产品修正付款申请单据内容如下：
		     	 * 1、增加控制：参数FDC317在存在付款申请单或工程量确认单是不允许修改
		     	 * 2、明确字段表意：参数FDC317为是，即分离时，付款申请单上字段“本期完工工程量”
		     	 * 	     更改成“未付已完工工程量”，参数FDC317为否，即不分离时，付款申请单上字段“本期完工工程量”显示不变化
		     	 * 3、“进度款付款比例”名称不产生明显歧义，暂不作调整，“本次申请”金额=
		     	 * 	  “进度款付款比例”*“未付已完工工程量”计算逻辑不变，其中“未付已完工工程量”取值的数据不允修改且不变化，
		     	 * 	  “本次申请金额”与“进度款付款比例”值变化后互算，可清0但不支持申请负数金额。
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
					//存在录入了完工工程量的付款申请单
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
					//存在工程量确认单
					throw new FDCException(FDCException.EXISTWORKLOADBILL);
				}
				/*********判断是否启用发票参数  --by neo*********/
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
				/*********判断是否启用发票参数  --by neo*********/
				if(FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit,FDCConstants.FDC_PARAM_INVOICEMRG)){
					throw new FDCException(FDCException.INVOICEDEACTIVE);
				}
			}
		}else if(FDCConstants.FDC_PARAM_INVOICEMRG.equals(number)){		//发票参数：必须启用工程量分离、财务一体化及凭证调整模式  -by neo
			/**
			 * 不校验了，简单模式也可以支持发票 by hpw 2011.2.24
			 */
/*//			启用工程量与付款分离
			boolean isSeparatePayment = false;	
//			启用复杂模式成本财务一体化
			boolean isFinancial = false;
//			启用凭证调整模式
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
				 * 是否允许拆分提交状态的单据：集团参数
				 * 需要校验所有组织是否有启用复杂模式一体化
				 * 如果有任意一个组织启用了复杂模式一体化，则不能启用"是否允许拆分提交状态的单"
				 */
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("keyID.number",FDCConstants.FDC_PARAM_FINACIAL));
				filter.getFilterItems().add(new FilterItemInfo("Value","true"));
				
				boolean isfinacial = ParamItemFactory.getLocalInstance(ctx).exists(filter);
				if(isfinacial){//如果启用了简单模式成本财务一体化
					throw new FDCException(FDCException.CANNOTSPLITSUBMIT);//不允许拆分提交状态的单据
				}
			}
		}else if(number.equals(FDCConstants.FDC_PARAM_WRITEMARK)){
			/**
			 * 禁用参数
			 */
			if("false".equals(model.getValue())){
				//禁用参数""的时候判断如果合同有多个正文(不管每个正文是否有多个版本)就不允许用户再禁用参数
				//因为在有多个正文的情况下让客户禁用参数再次打开正文的的时候就不知道到底要打开哪一个正文了  by Cassiel_peng
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
				//如果一个合同有多个文件名就说明有多个正文                                                                                        
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
				//同步ID
				if(synchroIDS.size()>0){
					builder.clear();
					builder.appendParam("update T_CON_ContractContent set FContractID=FParent where  FParent ",synchroIDS.toArray());
					builder.executeUpdate();
				}
				/**
				 * 启用参数
				 */
			}else{
				// ("启用该参数后，如果为某一个合同添加了多个正文将不允许再禁用该参数！");
				//同步ID
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
				// 上面四行报错 invalid column index,因为都是固定的值直接写在sql里不报 
				builder.executeUpdate();
			}
		}else if(FDCConstants.FDC_PARAM_AIMCOSTADJUSTDELETE.equals(number)){
			/**
			 * 如果“启用复杂模式成本财务一体化”在任何一个组织下参数值为是，
			 * 则在启用“允许删除待发生成本的调整记录”参数时提示“已经启用复杂模式成本财务一体化，
			 * 所以不允许删除待发生成本的调整记录”，且不允许启用；否则，如果“允许删除待发生成本的调整记录”参数值为是，
			 * 则在任何组织下启用“启用复杂模式成本财务一体化”参数时提示“已经允许删除待发生成本的调整记录，
			 * 所以不允许启用复杂模式成本财务一体化”，且不允许启用
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
		*系统参数 ：未结算合同的实付款大于已实现产值时是否严格控制   启用时做判断
		*检查是否已启用了集团参数“合同可进行多次结算”，若没有启用，则提示“请先
		*启用集团参数【合同可进行多次结算】”；若已启用，则直接启用本参数。
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
			//				 * 仅当启用“启用简单模式成本财务一体化”参数时，本参数可启用。
			//				 * 当禁用“启用简单模式成本财务一体化”时，必须先禁用本参数。否则提示：请先禁用参数“财务帐务以发票金额为准计开发成本”。
			//				 */
			//				boolean isSimple = FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit,
			//						FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
			//				if (!isSimple) {
			//					// 没有启用简单模式
			//					throw new FDCException(FDCException.ENABLESIMPLE);
			//				}   
			//			}
			
		}
		/***
		 *进度管理增加
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
				if(isPaymentAllSplit){//存在拆分完成参数为是
					throw new FDCException(FDCException.SPLITALLENABLED);
				}
			}
		}else if(number.equals(FDCConstants.FDC_PARAM_CHECKPAYMENTALLSPLIT)){
			if ("true".equals(model.getValue())) {
				boolean isPaymentSplit = FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit, FDCConstants.FDC_PARAM_CHECKPAYMENTSPLIT);
				if(!isPaymentSplit){//先启用拆分
					throw new FDCException(FDCException.MUSTSPLIT);
				}
				boolean canSplitSubmit = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_SPLITSUBMIT);
				if(!canSplitSubmit){//先启用拆分
					throw new FDCException(FDCException.CANSPLITSUBMIT);
				}
			}
		}else if(number.equals(FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT)){
			if ("true".equals(model.getValue())) {
				boolean isMulti = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_MULTIPROJECT);
				if(isMulti){//已启用
					throw new FDCException(FDCException.MULTIPROJECT);
				}
			}
		}else if(number.equals(FDCConstants.FDC_PARAM_MULTIPROJECT)){
			if ("true".equals(model.getValue())) {
				boolean isCross = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT);
				if(isCross){//已启用
					throw new FDCException(FDCException.CROSSPROJECTSPLIT);
				}
			}
		}
	}
}
