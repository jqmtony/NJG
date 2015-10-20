package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractExecInfosCollection;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractExecInfos;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractExecInfosControllerBean extends AbstractContractExecInfosControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractExecInfosControllerBean");
    
    /**
     * 2009-05-19 根据最新需求，基本上所有字段都增加对应的原币字段，故所有逻辑都必须增加对原币字段的操作。
     */
    
    
    /**
     * 合同审批、反审批时更新相关信息
     * @param type 状态 (审批或反审批)
     * @param contractId 合同id
     */
    protected void _updateContract(Context ctx, String type, String contractId)throws BOSException, EASBizException
    {
    	if(contractId.equals("")){
    		return;
    	}
    	boolean isContract = isContract(contractId);
    	/**
    	 * 合同审批  增加相应信息
    	 * contractBill 合同
    	 * curProject 工程项目
    	 * costAmt 合同成本
    	 */
    	if(ContractExecInfosInfo.EXECINFO_AUDIT.equals(type)){
    		ContractExecInfosInfo info = new ContractExecInfosInfo();
    		if(isContract){
	    		ContractBillInfo conBill = ContractBillFactory
	    			.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
	    		info.setContractBill(conBill);
	    		info.setCurProject(conBill.getCurProject());
	    		info.setPeriod(conBill.getPeriod());
	    		info.setCU(conBill.getCU());
	    		info.setOrgUnit(conBill.getOrgUnit());
	    		info.setHasSettled(conBill.isHasSettled());
	    		//本币
	    		info.setCostAmt(conBill.getAmount());
	    		//原币
	    		info.setCostAmtOri(conBill.getOriginalAmount());
	    		info.setIsNoText(false);
	    		_addnew(ctx,info);
	    		/**
	    		 * 2009-05-22 有可能合同在审批前已经做了变更审批或结算，故在合同审批时，还得更新变更等。
	    		 */
    			_updateChange(ctx,ContractExecInfosInfo.EXECINFO_AUDIT,contractId);
    		}else {
    			ContractWithoutTextInfo conWithoutText = ContractWithoutTextFactory.
    					getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
    			info.setConWithoutText(conWithoutText);
	    		info.setCurProject(conWithoutText.getCurProject());
	    		//本币
	    		info.setCostAmt(conWithoutText.getAmount());
	    		//原币
	    		info.setCostAmtOri(conWithoutText.getOriginalAmount());
	    		info.setCompletedAmt(conWithoutText.getAmount());
	    		info.setInvoicedAmt(conWithoutText.getInvoiceAmt());
	    		info.setPeriod(conWithoutText.getPeriod());
	    		info.setCU(conWithoutText.getCU());
	    		info.setOrgUnit(conWithoutText.getOrgUnit());
	    		info.setIsNoText(true);
	    		_addnew(ctx,info);
    		}
    	}//合同反审批 删除对应信息
    	else if(ContractExecInfosInfo.EXECINFO_UNAUDIT.equals(type)){
//    		ContractExecInfosInfo info = ice
//    			.getContractExecInfosInfo("select fid from T_CON_ContractExecInfos where FContractBillId ="+contractId);
//    		if(info!=null){
//    			ice.delete(new ObjectUuidPK(info.getId()));
//    		}
    		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    		if(isContract)
    			builder.appendSql("delete from T_CON_ContractExecInfos where FContractBillId=?");
    		else 
    			builder.appendSql("delete from T_CON_ContractExecInfos where FConWithoutTextID=?");
    		builder.addParam(contractId);
    		builder.execute();
    	}
    }
    /**
     * 合同变更审批、反审批时更新相关信息
     * @param type 状态 (审批或反审批)
     * @param contractId 合同id
     */     
    protected void _updateChange(Context ctx, String type, String contractId)throws BOSException, EASBizException
    {
    	//排除null与""
    	if(FDCHelper.isEmpty(contractId)){
    		return;
    	}
    	/**
    	 * 变更单审批、反审批时更新合同执行表相关数据
    	 * costAmt   合同成本
    	 * changeAmt 变更金额合计 如果变更结算 则为变更结算金额 未变更结算 则为变更金额
    	 */
    	if(ContractExecInfosInfo.EXECINFO_AUDIT.equals(type) 
    			|| ContractExecInfosInfo.EXECINFO_UNAUDIT.equals(type)){
    		//获得执行表对象
	    	IContractExecInfos ice = ContractExecInfosFactory.getLocalInstance(ctx);
	    	EntityViewInfo view = new EntityViewInfo();
	    	view.getSelector().add("id");
	    	view.getSelector().add("hasSettled");
	    	view.getSelector().add("costAmt");
	    	view.getSelector().add("costAmtOri");
	    	view.getSelector().add("changeAmt");
	    	view.getSelector().add("changeAmtOri");
	    	FilterInfo filter = new FilterInfo();
	    	filter.getFilterItems().add(new FilterItemInfo("contractBill",contractId));
	    	view.setFilter(filter);
	    	
	    	ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
	    	ContractExecInfosInfo info = null;
	    	if(coll != null && coll.size() == 1){
	    		info = coll.get(0);
	    	}
//	    	ContractExecInfosInfo info = ice
//				.getContractExecInfosInfo("select * from T_CON_ContractExecInfos" +
//						" where FContractBillId = '"+contractId+"'");
	    	BigDecimal temp = FDCHelper.ZERO;
	    	BigDecimal tempOri = FDCHelper.ZERO;
	    	if(info == null){
	    		return;
	    	}
	    	ContractBillInfo conBill = ContractBillFactory.getLocalInstance(ctx)
	    		.getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
//	    	temp = info.getCostAmt();
	    	temp = conBill.getAmount();
	    	tempOri = conBill.getOriginalAmount();
	    	//获得变更金额
	    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	    	builder.appendSql("select sum(case fhasSettled when  0 then  famount" +
	    			" else fbalanceamount end ) as changeAmt ," +
	    			" sum(case fhasSettled when 0 then foriginalAmount " +
	    			"else foriBalanceAmount end) as changeAmtOri" +
	    			" from T_Con_ContractChangeBill where ");
	    	builder.appendParam("FContractBillID",contractId);
	    	builder.appendSql(" and (");
			builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState",FDCBillStateEnum.VISA_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState",FDCBillStateEnum.ANNOUNCE_VALUE);
			builder.appendSql(" )");
			IRowSet rs = builder.executeQuery();
			BigDecimal changeAmt = FDCHelper.ZERO;
			BigDecimal changeAmtOri = FDCHelper.ZERO;
			try {
				while(rs.next()){
					changeAmt = rs.getBigDecimal("changeAmt");
					changeAmtOri = rs.getBigDecimal("changeAmtOri");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new BOSException(e);
			}
			info.setCostAmt(FDCHelper.add(temp,changeAmt));
			info.setCostAmtOri(FDCHelper.add(tempOri, changeAmtOri));
			info.setChangeAmt(changeAmt);
			info.setChangeAmtOri(changeAmtOri);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("changeAmt");
			selector.add("changeAmtOri");
			selector.add("costAmt");
			selector.add("costAmtOri");
			if(!info.isHasSettled()){
				BigDecimal completePrjAmt = getProcessAmt(ctx,contractId);
				info.setNotCompletedAmt(FDCHelper.subtract(FDCHelper.add(temp, changeAmt),completePrjAmt));
				selector.add("notCompletedAmt");
			}
			_updatePartial(ctx,info,selector);
			if(info.isHasSettled()){
				//2009-05-23 有可能在结算后再做变更结算
				_updateSettle(ctx,ContractExecInfosInfo.EXECINFO_AUDIT,contractId); 				
			}
    	}

    	//有可能更新未完工工程量
    	_updatePayment(ctx,ContractExecInfosInfo.EXECINFO_PAY,contractId);
    }
    /**
     * 合同对应结算单审批、反审批时更新相关信息
     * @param type 状态 审批或反审批
     * @param contractId 合同id
     */
    protected void _updateSettle(Context ctx, String type, String contractId)throws BOSException, EASBizException
    {
    	if(contractId.equals("")){
    		return;
    	}
    	/**
    	 * 结算单审批时更新执行表相关数据
    	 * settleAmt  结算金额
    	 * costAmt    合同成本
    	 * hasSettled 是否最终结算
    	 * completedAmt 已完工工程量 --已完工工程量以付款为界限 未结算 已完工 = 所有已付款进度款之和
    	 * notCompletedAmt 未完工工程量 已结算未付款 未完工 = 结算价 - 所有已付款进度款之和
    	 * 						      未结算 未完工 = 合同金额+ 变更金额 - 已完工工程量
    	 */ 
    	ContractBillInfo conBill = ContractBillFactory.getLocalInstance(ctx)
			.getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
    	BigDecimal temp = FDCHelper.ZERO;
    	BigDecimal tempOri = FDCHelper.ZERO;
    	SelectorItemCollection selector = new SelectorItemCollection();
    	IContractExecInfos ice = ContractExecInfosFactory.getLocalInstance(ctx);
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("id");
    	view.getSelector().add("costAmt");
    	view.getSelector().add("changeAmt");
    	view.getSelector().add("costAmtOri");
    	view.getSelector().add("changeAmtOri");
    	view.getSelector().add("completedAmt");
    	view.getSelector().add("notCompletedAmt");
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill",contractId));
    	view.setFilter(filter);
    	
    	ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
    	ContractExecInfosInfo info = null;
    	if(coll != null && coll.size() == 1){
    		info = coll.get(0);
    	}
    	if(info == null){
    		return;
    	}
    	if(ContractExecInfosInfo.EXECINFO_AUDIT.equals(type)){
    		if(conBill.isHasSettled()){
    			temp = conBill.getSettleAmt();
    			tempOri = getSettleAmtOri(ctx,contractId);
    			info.setCostAmt(temp);
        		info.setCostAmtOri(tempOri);
        		info.setSettleAmt(temp);
        		info.setSettleAmtOri(tempOri);
    		}else{
    			//获得变更金额
    	    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	    	builder.appendSql("select sum(case fhasSettled when  0 then  famount" +
    	    			" else fbalanceamount end ) as changeAmt ," +
    	    			" sum(case fhasSettled when 0 then foriginalAmount " +
    	    			"else foriBalanceAmount end) as changeAmtOri" +
    	    			" from T_Con_ContractChangeBill where ");
    	    	builder.appendParam("FContractBillID",contractId);
    	    	builder.appendSql(" and (");
    			builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
    			builder.appendSql(" or ");
    			builder.appendParam("FState",FDCBillStateEnum.VISA_VALUE);
    			builder.appendSql(" or ");
    			builder.appendParam("FState",FDCBillStateEnum.ANNOUNCE_VALUE);
    			builder.appendSql(" )");
    			IRowSet rs = builder.executeQuery();
    			BigDecimal changeAmt = FDCHelper.ZERO;
    			BigDecimal changeAmtOri = FDCHelper.ZERO;
    			try {
    				while(rs.next()){
    					changeAmt = rs.getBigDecimal("changeAmt");
    					changeAmtOri = rs.getBigDecimal("changeAmtOri");
    				}
    			} catch (SQLException e) {
    				e.printStackTrace();
    				logger.error(e.getMessage());
    				throw new BOSException(e);
    			}
    			
    			temp = FDCHelper.add(conBill.getAmount(), changeAmt);
    			tempOri = FDCHelper.add(conBill.getOriginalAmount(), changeAmtOri);
    			info.setCostAmt(temp);
        		info.setCostAmtOri(tempOri);
    		}
    		//更新合同成本 结算金额 最终结算 已完工工程量    		
    		info.setHasSettled(conBill.isHasSettled());
    		BigDecimal processAmt = getProcessAmt(ctx,contractId);
    		info.setCompletedAmt(processAmt);
    		//已结算未付款 未完工 = 结算价 - 所有已付款进度款之和
    		info.setNotCompletedAmt(FDCHelper.subtract(temp,processAmt));
			selector.add("costAmt");
			selector.add("costAmtOri");
			selector.add("hasSettled");
    		selector.add("settleAmt");
    		selector.add("settleAmtOri");
    		//已完工工程量以付款为基准
    		selector.add("completedAmt");
    		selector.add("notCompletedAmt");
    		_updatePartial(ctx,info,selector);
    	}else if(ContractExecInfosInfo.EXECINFO_UNAUDIT.equals(type)){
    		//结算金额
    		info.setSettleAmt(null);
    		info.setSettleAmtOri(null);
    		info.setHasSettled(false);
    		//获得付款单
//    		EntityViewInfo viewInfo = new EntityViewInfo();
//    		viewInfo.getSelector().add("localAmt");
//    		viewInfo.getSelector().add("fdcPayReqID");
//    		FilterInfo filterInfo = new FilterInfo();
//    		filterInfo.getFilterItems().add(
//    				new FilterItemInfo("billState",BillStatusEnum.PAYED));
//    		filterInfo.getFilterItems().add(
//    				new FilterItemInfo("contractBillId",contractId));
//    		PaymentBillCollection pbc = PaymentBillFactory
//    			.getLocalInstance(ctx).getPaymentBillCollection(viewInfo);
    		//未结算 已完工 = 所有已付进度款合计
    		temp = getProcessAmt(ctx,contractId);		
			//已完工工程量
			info.setCompletedAmt(temp);
			BigDecimal changeAmt = getChangeAmt(ctx,contractId);
			BigDecimal changeAmtOri = getChangeAmtOri(ctx,contractId);
			//变更金额 合同成本 
			if(changeAmt == null ||changeAmt.compareTo(FDCHelper.ZERO)==0){
				info.setChangeAmt(null);
				info.setChangeAmtOri(null);
				info.setCostAmt(conBill.getAmount());
				info.setCostAmtOri(conBill.getOriginalAmount());
			}else{
				info.setChangeAmt(changeAmt);
				info.setChangeAmtOri(changeAmtOri);
				info.setCostAmt(FDCHelper.add(conBill.getAmount(),changeAmt));
				info.setCostAmtOri(FDCHelper.add(conBill.getOriginalAmount(), changeAmtOri));
			}
			//未完工工程量 = 合同金额+ 变更金额-已完工工程量
			info.setNotCompletedAmt(FDCHelper.subtract(info.getCostAmt(),info.getCompletedAmt()));
			selector.add("costAmt");
			selector.add("costAmtOri");
			selector.add("hasSettled");
    		selector.add("settleAmt");
    		selector.add("settleAmtOri");
    		selector.add("completedAmt");
    		selector.add("notCompletedAmt");
    		_updatePartial(ctx,info,selector);
			
    	}
    	
    }

	/**
	 * 付款单付款、反付款时候更新相关数据
	 * @param type 状态 付款、反付款
	 * @param contractId 合同id
	 */
    protected void _updatePayment(Context ctx, String type, String contractId)throws BOSException, EASBizException
    {
    	if(contractId.equals("")){
    		return;
    	}
    	/**
    	 * 付款单付款时 更新执行表相关数据
    	 * deductedAmt 扣款金额
    	 * compensatedAmt 违约金额
    	 * invoicedAmt 已开发票金额
    	 * completedAmt 已完工工程量
    	 * notCompletedAmt 未完工工程量
    	 * partAMatlAmt 甲供扣款原币金额
    	 * partAMatLoaAmt 甲供扣款本币金额
    	 */
    	boolean isCon = isContract(contractId);
    	IContractExecInfos ice = ContractExecInfosFactory.getLocalInstance(ctx);
    	EntityViewInfo view = new EntityViewInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
    	if(isCon){
	    	view.getSelector().add("id");
	    	view.getSelector().add("costAmt");
	    	view.getSelector().add("costAmtOri");
	    	view.getSelector().add("deductedAmt");
	    	view.getSelector().add("deductedAmtOri");
	    	view.getSelector().add("compensatedAmt");
	    	view.getSelector().add("compensatedAmtOri");
	    	view.getSelector().add("guerdonAmt");
	    	view.getSelector().add("guerdonAmtOri");
	    	view.getSelector().add("completedAmt");
	    	view.getSelector().add("notCompletedAmt");
	    	view.getSelector().add("invoicedAmt");
	    	view.getSelector().add("partAMatlAmt");
	    	view.getSelector().add("partAMatLoaAmt");
	    	FilterInfo filter = new FilterInfo();
	    	filter.getFilterItems().add(new FilterItemInfo("contractBill",contractId));
	    	view.setFilter(filter);
	    	ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
	    	ContractExecInfosInfo info = null;
	    	if(coll != null && coll.size() == 1){
	    		info = coll.get(0);
	    	}
	    	if(info == null){
	    		return;
	    	}
	    	//扣款
	    	BigDecimal deductedAmt = FDCHelper.ZERO;
	    	BigDecimal deductedAmtOri = FDCHelper.ZERO;
	    	//违约
	    	BigDecimal compensatedAmt = FDCHelper.ZERO;
	    	BigDecimal compensatedAmtOri = FDCHelper.ZERO;
	    	//奖励
	    	BigDecimal guerdonAmt = FDCHelper.ZERO;
	    	BigDecimal guerdonAmtOri = FDCHelper.ZERO;
	    	//发票金额
	    	BigDecimal invoicedAmt = FDCHelper.ZERO;
//	    	甲供扣款金额
	    	BigDecimal partAMatlAmt = FDCHelper.ZERO;
	    	BigDecimal partAMatLoaAmt = FDCHelper.ZERO;
	    	Map ortherAmt = getOrtherAmt(ctx,contractId);
	    	if(ortherAmt != null && ortherAmt.size() >0){
	    		deductedAmt = (BigDecimal)ortherAmt.get(contractId+"deductedAmt");
	    		deductedAmtOri = (BigDecimal)ortherAmt.get(contractId+"deductedAmtOri");
	    		compensatedAmt = (BigDecimal)ortherAmt.get(contractId+"compensatedAmt");
	    		compensatedAmtOri = (BigDecimal)ortherAmt.get(contractId+"compensatedAmtOri");
	    		guerdonAmt = (BigDecimal)ortherAmt.get(contractId+"guerdonAmt");
	    		guerdonAmtOri = (BigDecimal)ortherAmt.get(contractId+"guerdonAmtOri");
	    		invoicedAmt = (BigDecimal)ortherAmt.get(contractId+"invoicedAmt");
	    		partAMatlAmt = (BigDecimal)ortherAmt.get(contractId+"partAMatlAmt");
	    		partAMatLoaAmt = (BigDecimal)ortherAmt.get(contractId+"partAMatLoaAmt");
	    	}
	    	//付款单付款
	    	if(ContractExecInfosInfo.EXECINFO_PAY.equals(type) ||
	    			ContractExecInfosInfo.EXECINFO_UNPAY.equals(type)){
	        	ContractBillInfo conBill = ContractBillFactory
	    			.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
	        	boolean hasPaySettle = hasPaySettle(ctx,contractId,conBill.isHasSettled());
	        	
	        	
	        	//add by zkyan 付款单计算时不在反写已完工工程量，改为在付款申请单审批时反写，因为此时合同执行财务信息表上已完工工程量和
				//	       	 合同成本信息里的已完工工程量不相等，这是只处理已付结算款的情况，其他情况在付款申请单审批时处理

	        	/**
	        	 * 如果结算并已付结算款 则已完工为合同结算价、未完工为0 
	        	 * 未结算付结算款 则已完工为所有已付进度款已完工之和、未完工=结算价-已完工
	        	 * 若未结算 则已完工为所有已付款进度款已完工之和 未完工 = 合同金额 + 变更金额 - 已完工
	        	 */
	        	if (hasPaySettle) {
					info.setCompletedAmt(conBill.getSettleAmt());
					info.setNotCompletedAmt(FDCHelper.ZERO);
				}
				//	        	else if(conBill.isHasSettled()){
//	        		info.setCompletedAmt(getProcessAmt(ctx,contractId));
//	        		info.setNotCompletedAmt(FDCHelper.subtract(conBill.getSettleAmt(),info.getCompletedAmt()));
//	        	}else if(!conBill.isHasSettled()){
//	        		info.setCompletedAmt(getProcessAmt(ctx,contractId));
//	        		info.setNotCompletedAmt(FDCHelper.subtract(info.getCostAmt(),info.getCompletedAmt()));
//	        	}
	    		info.setDeductedAmt(deductedAmt);
	    		info.setDeductedAmtOri(deductedAmtOri);
	    		info.setCompensatedAmt(compensatedAmt);
	    		info.setCompensatedAmtOri(compensatedAmtOri);
	    		info.setGuerdonAmt(guerdonAmt);
	    		info.setGuerdonAmtOri(guerdonAmtOri);
	    		info.setInvoicedAmt(invoicedAmt);
	    		info.setPaidAmt(getPaidAmt(ctx,contractId));
	    		info.setPaidAmtOri(getPaidAmtOri(ctx, contractId));
	    		info.setPartAMatlAmt(partAMatlAmt);
	    		info.setPartAMatLoaAmt(partAMatLoaAmt);
				selector.add("deductedAmt");
				selector.add("deductedAmtOri");
				selector.add("compensatedAmt");
				selector.add("compensatedAmtOri");
				selector.add("guerdonAmt");
				selector.add("guerdonAmtOri");
	    		selector.add("paidAmt");
	    		selector.add("paidAmtOri");
	    		selector.add("completedAmt");
				selector.add("notCompletedAmt");
	    		selector.add("invoicedAmt");
	    		selector.add("partAMatlAmt");
	    		selector.add("partAMatLoaAmt");
	    		_updatePartial(ctx,info,selector);
	    	}
    	}else{			//无文本合同的处理，无文本合同没有结算，只有付款，且只有一张付款单
    		view.getSelector().add("id");
	    	view.getSelector().add("costAmt");
	    	view.getSelector().add("costAmtOri");
	    	view.getSelector().add("completedAmt");
	    	view.getSelector().add("notCompletedAmt");
	    	view.getSelector().add("invoicedAmt");
	    	FilterInfo filter = new FilterInfo();
	    	filter.getFilterItems().add(new FilterItemInfo("conWithoutText",contractId));
	    	view.setFilter(filter);
	    	ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
	    	ContractExecInfosInfo info = null;
	    	if(coll != null && coll.size() == 1){
	    		info = coll.get(0);
	    	}
	    	if(info == null){
	    		return;
	    	}	    	
	    	
	    	//付款单付款
	    	if(ContractExecInfosInfo.EXECINFO_PAY.equals(type) ||
	    			ContractExecInfosInfo.EXECINFO_UNPAY.equals(type)){
	    		
	    	 	// 无文本合同允许多次付款，在循环中selector.add("paidAmt")会插入重复的列，删除之前的代码
				// 改用如下代码，利用getPaidAmt和getPaidAmtOri方法获取已经付款金额。 Added by
				// Owen_wen 2011-05-23
				
				selector.add("paidAmt");
				selector.add("paidAmtOri");
				info.setPaidAmt(getPaidAmt(ctx, contractId));
				info.setPaidAmtOri(getPaidAmtOri(ctx, contractId));
				
	/*
				 * 无文本合同没有甲供材，因此，注释掉下面的代码， Added by Owen_wen 2011-05-023
				 * builder.clear();builder.appendSql(
				 * "select sum(pa.FAMOUNT) as partAMatlAmt,sum(pa.FOriginalAmount) as partAMatLoaAmt "
				 * +
				 * "from T_CON_PartAOfPayReqBill as pa,T_CAS_PaymentBill as py where"
				 * ); builder.appendParam("py.FContractBillID",contractId);
				 * builder.appendSql(" and pa.FPaymentBillID=py.FID"); rowSet =
				 * builder.executeQuery(); try { while(rowSet.next()){
				 * if(ContractExecInfosInfo.EXECINFO_PAY.equals(type)){
				 * info.setPartAMatlAmt(rowSet.getBigDecimal("partAMatlAmt"));
				 * info
				 * .setPartAMatLoaAmt(rowSet.getBigDecimal("partAMatLoaAmt"));
				 * selector.add("partAMatlAmt"); selector.add("partAMatLoaAmt");
				 * }else{ info.setPaidAmt(null); info.setPaidAmtOri(null);
				 * selector.add("partAMatlAmt"); selector.add("partAMatLoaAmt");
				 * } } } catch (SQLException e) { e.printStackTrace();
				 * logger.error(e.getMessage()); }
				 */ 
				
				_updatePartial(ctx,info,selector);
	    	}
	    }
    }
    /**
     * 获得合同变更金额合计
     * 若变更结算了 则去变更结算金额
     * 否则取已审批 已签证 已下发状态的变更签证确认金额
     * @param ctx 上下文
     * @param contractId 合同id
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected BigDecimal getChangeAmt(Context ctx,String contractId) throws BOSException, EASBizException{
    	if(contractId.equals("")){
    		return null;
    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select sum(case fhasSettled when  0 then  famount" +
    			" else fbalanceamount end ) as changeAmt" +
    			" from T_Con_ContractChangeBill where ");
    	builder.appendParam("FContractBillID",contractId);
    	builder.appendSql(" and (");
		builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
		builder.appendSql(" or ");
		builder.appendParam("FState",FDCBillStateEnum.VISA_VALUE);
		builder.appendSql(" or ");
		builder.appendParam("FState",FDCBillStateEnum.ANNOUNCE_VALUE);
		builder.appendSql(" )");
		IRowSet rs = builder.executeQuery();
		BigDecimal changeAmt =null;
		try {
			while(rs.next()){
				changeAmt = rs.getBigDecimal("changeAmt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		return changeAmt;
    }
    /**
     * 获得合同变更金额合计 原币
     * 若变更结算了 则去变更结算金额
     * 否则取已审批 已签证 已下发状态的变更签证确认金额
     * @param ctx 上下文
     * @param contractId 合同id
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected BigDecimal getChangeAmtOri(Context ctx,String contractId) throws BOSException, EASBizException{
    	if(contractId.equals("")){
    		return null;
    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select sum(case fhasSettled when 0 then foriginalAmount " +
	    			"else foriBalanceAmount end) as changeAmtOri" +
    			" from T_Con_ContractChangeBill where ");
    	builder.appendParam("FContractBillID",contractId);
    	builder.appendSql(" and (");
		builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
		builder.appendSql(" or ");
		builder.appendParam("FState",FDCBillStateEnum.VISA_VALUE);
		builder.appendSql(" or ");
		builder.appendParam("FState",FDCBillStateEnum.ANNOUNCE_VALUE);
		builder.appendSql(" )");
		IRowSet rs = builder.executeQuery();
		BigDecimal changeAmtOri = null;
		try {
			while(rs.next()){
				changeAmtOri = rs.getBigDecimal("changeAmtOri");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		return changeAmtOri;
    }
    /**
     * 获得合同变更金额合计
     * 若变更结算了 则去变更结算金额
     * 否则取已审批 已签证 已下发状态的变更签证确认金额
     * @param ctx 上下文
     * @param conIds 合同ids
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected Map getChangeAmt(Context ctx,Set conIds) throws BOSException, EASBizException{
    	Map changeMap = new HashMap();
    	if(conIds == null || conIds.size() < 0){
    		return changeMap;
    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select FContractBillID as contractId , sum(case fhasSettled when  0 then  famount" +
    			" else fbalanceamount end ) as changeAmt" +
    			", sum(case fhasSettled when 0 then foriginalAmount else foriBalanceAmount end) as changeAmtOri" +
    			" from T_Con_ContractChangeBill where ");
    	builder.appendParam("FContractBillID",conIds.toArray());
    	builder.appendSql(" and (");
		builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
		builder.appendSql(" or ");
		builder.appendParam("FState",FDCBillStateEnum.VISA_VALUE);
		builder.appendSql(" or ");
		builder.appendParam("FState",FDCBillStateEnum.ANNOUNCE_VALUE);
		builder.appendSql(" ) group by FContractBillID");
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				String contractId = rs.getString("contractId");
				BigDecimal changeAmt = rs.getBigDecimal("changeAmt");
				BigDecimal changeAmtOri = rs.getBigDecimal("changeAmtOri");
				changeMap.put(contractId,changeAmt);
				changeMap.put(contractId+"ori",changeAmtOri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		return changeMap;
    }
    /**
     * 获得合同变更金额合计 原币
     * 若变更结算了 则去变更结算金额
     * 否则取已审批 已签证 已下发状态的变更签证确认金额
     * @param ctx 上下文
     * @param conIds 合同ids
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected Map getChangeAmtOri(Context ctx,Set conIds) throws BOSException, EASBizException{
    	Map changeOriMap = new HashMap();
    	if(conIds == null || conIds.size() < 0){
    		return changeOriMap;
    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select FContractBillID as contractId , sum(case fhasSettled when 0 then foriginalAmount " +
	    			"else foriBalanceAmount end) as changeAmtOri" +
    			" from T_Con_ContractChangeBill where ");
    	builder.appendParam("FContractBillID",conIds.toArray());
    	builder.appendSql("  and (");
		builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
		builder.appendSql(" or ");
		builder.appendParam("FState",FDCBillStateEnum.VISA_VALUE);
		builder.appendSql(" or ");
		builder.appendParam("FState",FDCBillStateEnum.ANNOUNCE_VALUE);
		builder.appendSql(" ) group by FContractBillID");
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				String contractId = rs.getString("contractId");
				BigDecimal changeAmtOri = rs.getBigDecimal("changeAmtOri");
				changeOriMap.put(contractId,changeAmtOri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		return changeOriMap;
    }
    
    /**
     * 获得合同付款单所有已付款的付款类型为进度款的已完工合计
     * @param ctx 上下文
     * @param contractId 合同id
     * @param paymentType 付款类型id 
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected BigDecimal getProcessAmt(Context ctx,String contractId) throws BOSException ,EASBizException{
    	if(contractId.equals("")){
    		return null;
    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql("select pay.FContractBillId as contractId , sum( req.FCostAmount) as amount " +
//				" from t_cas_paymentbill pay inner join t_con_payrequestbill req on req.fid = pay.ffdcpayreqid " +
//				" inner join T_FDC_PaymentType type on type.fid = pay.ffdcPayTypeId " +
//				" where ");
//		builder.appendParam("pay.fbillstatus",new Integer(BillStatusEnum.PAYED_VALUE));
//		builder.appendSql(" and ");
//		builder.appendParam("pay.fcontractbillid",contractId);
//		builder.appendSql(" and ");
//		builder.appendParam("type.FPayTypeID",PaymentTypeInfo.progressID);
//		builder.appendSql(" group by pay.FcontractBillId ");
    	//因FCostAmount废弃使用，均替换为FCompletePrjAmt
    	//更改脚本--避免重复计算
    	String companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		BigDecimal temp = null;
		
		/****判断是否启用工程量分离参数，如果启用参数，则直接取工程量清单的值 -by neo***/
    	if(FDCUtils.getDefaultFDCParamByKey(ctx, companyID, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)){
    		temp = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(contractId);
    	}else{
	    	builder.appendSql(" select fcontractid as contractId, sum(FCompletePrjAmt) as amount "+
	    			" from t_con_payrequestbill where fid in  ( " +
	    			" select distinct pay.ffdcpayreqid from t_cas_paymentbill pay " +
	    			" inner join T_FDC_PaymentType type on type.fid = pay.ffdcPayTypeId " +
	    			" where ");
	    	builder.appendParam("pay.fbillstatus",new Integer(BillStatusEnum.PAYED_VALUE));
	    	builder.appendSql(" and ");
	    	builder.appendParam("type.FPayTypeID",PaymentTypeInfo.progressID);
	    	builder.appendSql(" and ");
	    	builder.appendParam("pay.fcontractbillid",contractId);
	    	builder.appendSql(" ) group by fcontractid");
			IRowSet rs = builder.executeQuery();

			try {
				while(rs.next()){
					temp = rs.getBigDecimal("amount");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
    	}
		return temp;
    }
    /**
     * 获得合同付款单所有已付款的付款类型为进度款的已完工合计
     * @param ctx 上下文
     * @param conIds 合同ids
     * @param paymentType 付款类型id 
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected Map getProcessAmt(Context ctx,Set conIds) throws BOSException ,EASBizException{
    	Map processAmt = new HashMap();
    	if(conIds == null || conIds.size() < 0){
    		return processAmt;
    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	String companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
//		builder.appendSql("select pay.FContractBillId as contractId , sum( req.FCostAmount) as amount " +
//				" from t_cas_paymentbill pay inner join t_con_payrequestbill req on req.fid = pay.ffdcpayreqid " +
//				" inner join T_FDC_PaymentType type on type.fid = pay.ffdcPayTypeId " +
//				" where ");
//		builder.appendParam("pay.fbillstatus",new Integer(BillStatusEnum.PAYED_VALUE));
//		builder.appendSql(" and pay.fcontractbillid in ( ");
//		builder.appendParam(conIds.toArray());
//		builder.appendSql(" ) and ");
//		builder.appendParam("type.FPayTypeID",PaymentTypeInfo.progressID);
//		builder.appendSql(" group by pay.FcontractBillId ");
    	//更改脚本--避免重复计算
    	/****判断是否启用工程量分离参数，如果启用参数，则直接取工程量清单的值 -by neo***/
    	if(FDCUtils.getDefaultFDCParamByKey(ctx, companyID, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)){
    		processAmt = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(conIds);
    	}else{
	    	builder.appendSql(" select fcontractid as contractId, sum(FCompletePrjAmt) as amount "+
	    			" from t_con_payrequestbill where fid in  ( " +
	    			" select distinct pay.ffdcpayreqid from t_cas_paymentbill pay " +
	    			" inner join T_FDC_PaymentType type on type.fid = pay.ffdcPayTypeId " +
	    			" where ");
	    	builder.appendParam("pay.fbillstatus",new Integer(BillStatusEnum.PAYED_VALUE));
	    	builder.appendSql(" and ");
	    	builder.appendParam("type.FPayTypeID",PaymentTypeInfo.progressID);
	    	builder.appendSql(" and  ");
	    	builder.appendParam("pay.fcontractbillid",conIds.toArray());
	    	builder.appendSql(" ) group by fcontractid");
			IRowSet rs = builder.executeQuery();
			try {
				while(rs.next()){
					String conId = rs.getString("contractId");
					BigDecimal temp = rs.getBigDecimal("amount");
					if(!processAmt.containsKey(conId)){
						processAmt.put(conId,temp);					
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
    	}
		return processAmt;
    }
    /**
     * 获得所有已付款付款单本位币之和
     * @param ctx 
     * @param contractId
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected BigDecimal getPaidAmt(Context ctx,String contractId) throws BOSException ,EASBizException{
    	if(contractId.equals("")){
    		return null;
    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select FContractBillId as contractId , sum( FLocalAmount) as amount " +
				" from t_cas_paymentbill  where ") ;
		builder.appendParam("fbillstatus",new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" and ");
		builder.appendParam("fcontractbillid",contractId);
		builder.appendSql(" group by FcontractBillId ");
		IRowSet rs = builder.executeQuery();
		BigDecimal temp = null;
		try {
			while(rs.next()){
				temp = rs.getBigDecimal("amount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		return temp;
    }
    /**
     * 获得所有已付款付款单原币之和
     * @param ctx 
     * @param contractId
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected BigDecimal getPaidAmtOri(Context ctx,String contractId) throws BOSException ,EASBizException{
    	if(contractId.equals("")){
    		return null;
    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select FContractBillId as contractId , sum( FAmount) as amount " +
				" from t_cas_paymentbill  where ") ;
		builder.appendParam("fbillstatus",new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" and ");
		builder.appendParam("fcontractbillid",contractId);
		builder.appendSql(" group by FcontractBillId ");
		IRowSet rs = builder.executeQuery();
		BigDecimal temp = null;
		try {
			while(rs.next()){
				temp = rs.getBigDecimal("amount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		return temp;
    }
    /**
     * 获得所有已付款付款单本位币之和、原币之和
     * @param ctx  上下文
     * @param conIds 合同ids
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected Map getPaidAmt(Context ctx,Set conIds) throws BOSException ,EASBizException{
    	Map paidAmt = new HashMap();
    	if(conIds == null || conIds.size() <0){
    		return paidAmt;
    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select FContractBillId as contractId , sum( FLocalAmount) as amount " +
				" ,sum(FAmount) as oriAmount from t_cas_paymentbill  where ") ;
		builder.appendParam("fbillstatus",new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" and  ");
		builder.appendParam("fcontractbillid",conIds.toArray());
		builder.appendSql("  group by FcontractBillId ");
		IRowSet rs = builder.executeQuery();		 
		try {
			while(rs.next()){
				String id = rs.getString("contractId");
				BigDecimal temp = rs.getBigDecimal("amount");
				BigDecimal tempOri = rs.getBigDecimal("oriAmount");
				paidAmt.put(id,temp);
				paidAmt.put(id+"ori", tempOri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		return paidAmt;
    }
    
    
    /**
     * 获得合同对应的所有扣款金额和违约金额
     * @param ctx 上下文
     * @param contractId 合同id
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected Map getOrtherAmt(Context ctx,String contractId) throws BOSException,EASBizException{
    	Map ortherAmt = new HashMap();
    	if(contractId == null || contractId.equals("")){
    		return ortherAmt;
    	}    	                                                
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	//R110503-380:合同执行财务信息表扣款违约奖励等金额应该只统计已付款的付款单。 by zhiyuan_tang 2010-05-13
    	//扣款
		builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_DeductOfPayReqBill doprb ");
		builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
		builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
		builder.appendSql("where ");
		builder.appendParam("prb.FContractID",contractId);
		builder.appendSql(" and ");
		builder.appendParam(" doprb.FPasPaid",new Integer(1));
		builder.appendSql(" and ");
		builder.appendParam(" pmb.FBillstatus", new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" group by prb.FContractID ");
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				String id = rs.getString("FContractID");
				BigDecimal deductedAmt = rs.getBigDecimal("amount");
				BigDecimal deductedAmtOri = rs.getBigDecimal("oriAmount");
				ortherAmt.put(id+"deductedAmt",deductedAmt);
				ortherAmt.put(id+"deductedAmtOri", deductedAmtOri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		//违约
		builder.clear();
		builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount,sum(doprb.foriginalAmount) as oriAmount from T_CON_CompensationOfPayReqBill doprb ");
		builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
		builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
		builder.appendSql("where ");
		builder.appendParam("prb.FContractID",contractId);
		builder.appendSql(" and ");
		builder.appendParam("doprb.FPasPaid",new Integer(1));
		builder.appendSql(" and ");
		builder.appendParam(" pmb.FBillstatus", new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" group by prb.FContractID ");
		
//		builder.appendSql("select FContractID,sum(famount) as amount from T_CON_CompensationBill where ");
//		builder.appendParam("FContractID",contractId);
//		builder.appendSql(" and ");
//		builder.appendParam("fstate",FDCBillStateEnum.AUDITTED_VALUE);
//		builder.appendSql(" AND fisCompensated = 1 group by fcontractid ");
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				String id = rs.getString("FContractID");
				BigDecimal compensatedAmt = rs.getBigDecimal("amount");
				BigDecimal compensatedAmtOri = rs.getBigDecimal("oriAmount");
				ortherAmt.put(id+"compensatedAmt",compensatedAmt);
				ortherAmt.put(id+"compensatedAmtOri", compensatedAmtOri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
    	//奖励
		builder.clear();
		builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_GuerdonOfPayReqBill doprb ");
		builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
		builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
		builder.appendSql("where ");
		builder.appendParam("prb.FContractID",contractId);
		builder.appendSql(" and ");
		builder.appendParam(" doprb.FPasPaid",new Integer(1));
		builder.appendSql(" and ");
		builder.appendParam(" pmb.FBillstatus", new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" group by prb.FContractID ");
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				String id = rs.getString("FContractID");
				BigDecimal deductedAmt = rs.getBigDecimal("amount");
				BigDecimal deductedAmtOri = rs.getBigDecimal("oriAmount");
				ortherAmt.put(id+"guerdonAmt",deductedAmt);
				ortherAmt.put(id+"guerdonAmtOri", deductedAmtOri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		//发票金额
		builder.clear();
//		builder.appendSql("select prb.FContractID,sum(prb.FInvoiceAmt) as amount from T_CAS_PaymentBill pay ");
//		builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = pay.FFdcPayReqID ");
//		builder.appendSql("where ");
//		builder.appendParam("prb.FContractID",contractId);
//		builder.appendSql("and ");
//		builder.appendParam("pay.fbillstatus",new Integer(BillStatusEnum.PAYED_VALUE));
//		builder.appendSql(" group by prb.FContractID "); 
		builder.appendSql(" select fcontractid ,sum(finvoiceamt) as amount from t_con_payrequestbill where ");
		builder.appendParam("FContractId", contractId);
		builder.appendSql(" and fid in ( select ffdcpayreqid from t_cas_paymentbill where ");
		builder.appendParam("fbillstatus",new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" ) group by FContractID ");
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				String id = rs.getString("FContractID");
				BigDecimal invoicedAmt = rs.getBigDecimal("amount");
				ortherAmt.put(id+"invoicedAmt",invoicedAmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
//		甲供扣款金额
		builder.clear();
//		根据是否启用参数从不同表中获取甲供合同信息
		
		
		if(FDCUtils.getDefaultFDCParamByKey(
				ctx,null,FDCConstants.FDC_PARAM_CREATEPARTADEDUCT)){
			builder.appendSql("select sum(pa.FAMOUNT) as partAMatLoaAmt,sum(pa.FOriginalAmount) as  partAMatlAmt " +
					" from T_CON_PartAOfPayReqBill as pa,T_CAS_PaymentBill as py where ");
			builder.appendParam("py.FContractBillID",contractId);
			builder.appendSql(" and ");
			builder.appendParam(" py.FBillstatus", new Integer(BillStatusEnum.PAYED_VALUE));
			builder.appendSql(" and pa.FPaymentBillID=py.FID");
		}else{
			builder.appendSql("select sum(pa.FAMOUNT) as partAMatLoaAmt,sum(pa.FOriginalAmount) as  partAMatlAmt " +
					" from T_CON_PartAConfmOfPayReqBill as pa,T_CAS_PaymentBill as py where ");
			builder.appendParam("py.FContractBillID",contractId);
			builder.appendSql(" and ");
			builder.appendParam(" py.FBillstatus", new Integer(BillStatusEnum.PAYED_VALUE));
			builder.appendSql(" and pa.FPaymentBillID=py.FID");
		}

		rs = builder.executeQuery();
		try {
			while (rs.next()) {
				BigDecimal partAMatlAmt = rs.getBigDecimal("partAMatlAmt");
				BigDecimal partAMatLoaAmt = rs.getBigDecimal("partAMatLoaAmt");
				ortherAmt.put(contractId+"partAMatlAmt", partAMatlAmt);
				ortherAmt.put(contractId+"partAMatLoaAmt", partAMatLoaAmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
    	return ortherAmt;
    }
    /**
     * 获得合同对应的所有扣款、违约、奖励和发票金额
     * @param ctx 上下文
     * @param conIds 合同ids
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected Map getOrtherAmt(Context ctx,Set conIds) throws BOSException,EASBizException{
    	Map ortherAmt = new HashMap();
    	if(conIds == null || conIds.size() <0){
    		return ortherAmt;
    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	//R110503-380:合同执行财务信息表扣款违约奖励等金额应该只统计已付款的付款单。 by zhiyuan_tang 2010-05-13
    	//扣款
		builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount,sum(doprb.foriginalAmount) as oriAmount from T_CON_DeductOfPayReqBill doprb ");
		builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
		builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
		builder.appendSql("where  ");
		builder.appendParam("prb.FContractID",conIds.toArray());
		builder.appendSql(" and ");
		builder.appendParam("doprb.FPasPaid",new Integer(1));
		builder.appendSql(" and ");
		builder.appendParam(" pmb.FBillstatus", new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" group by prb.FContractID ");
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				String id = rs.getString("FContractID");
				BigDecimal deductedAmt = rs.getBigDecimal("amount");
				BigDecimal deductedAmtOri = rs.getBigDecimal("oriAmount");
				ortherAmt.put(id+"deductedAmt",deductedAmt);
				ortherAmt.put(id+"deductedAmtOri", deductedAmtOri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		//违约
		builder.clear();
		builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_CompensationOfPayReqBill doprb ");
		builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
		builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
		builder.appendSql("where ");
		builder.appendParam("prb.FContractID",conIds.toArray());
		builder.appendSql(" and ");
		builder.appendParam("doprb.FPasPaid",new Integer(1));
		builder.appendSql(" and ");
		builder.appendParam(" pmb.FBillstatus", new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" group by prb.FContractID ");
		
//		builder.appendSql("select FContractID,sum(famount) as amount from T_CON_CompensationBill where ");
//		builder.appendParam("FContractID",contractId);
//		builder.appendSql(" and ");
//		builder.appendParam("fstate",FDCBillStateEnum.AUDITTED_VALUE);
//		builder.appendSql(" AND fisCompensated = 1 group by fcontractid ");
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				String id = rs.getString("FContractID");
				BigDecimal compensatedAmt = rs.getBigDecimal("amount");
				BigDecimal compensatedAmtOri = rs.getBigDecimal("oriAmount");
				ortherAmt.put(id+"compensatedAmt",compensatedAmt);
				ortherAmt.put(id+"compensatedAmtOri", compensatedAmtOri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		//奖励
		builder.clear();
		builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_GuerdonOfPayReqBill doprb ");
		builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
		builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
		builder.appendSql("where ");
		builder.appendParam("prb.FContractID",conIds.toArray());
		builder.appendSql(" and ");
		builder.appendParam("doprb.FPasPaid",new Integer(1));
		builder.appendSql(" and ");
		builder.appendParam(" pmb.FBillstatus", new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" group by prb.FContractID ");

		rs = builder.executeQuery();
		try {
			while(rs.next()){
				String id = rs.getString("FContractID");
				BigDecimal guerdonAmt = rs.getBigDecimal("amount");
				BigDecimal guerdonAmtOri = rs.getBigDecimal("oriAmount");
				ortherAmt.put(id+"guerdonAmt",guerdonAmt);
				ortherAmt.put(id+"guerdonAmtOri", guerdonAmtOri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		//发票金额
		builder.clear();
//		builder.appendSql("select prb.FContractID,sum(prb.FInvoiceAmt) as amount from T_CAS_PaymentBill pay ");
//		builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = pay.FFdcPayReqID ");
//		builder.appendSql("where  ");
//		builder.appendParam("prb.FContractID",conIds.toArray());
//		builder.appendSql(" and ");
//		builder.appendParam("pay.fbillstatus",new Integer(BillStatusEnum.PAYED_VALUE));
//		builder.appendSql(" group by prb.FContractID ");
		builder.appendSql(" select fcontractid ,sum(finvoiceamt) as amount from t_con_payrequestbill where ");
		builder.appendParam("FContractId", conIds.toArray());
		builder.appendSql(" and fid in ( select ffdcpayreqid from t_cas_paymentbill where ");
		builder.appendParam("fbillstatus",new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" ) group by FContractID ");
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				String id = rs.getString("FContractID");
				BigDecimal invoicedAmt = rs.getBigDecimal("amount");
				ortherAmt.put(id+"invoicedAmt",invoicedAmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
//		甲供扣款金额
		builder.clear();
//		根据是否启用参数从不同表中获取甲供合同信息
		if(FDCUtils.getDefaultFDCParamByKey(
				ctx,null,FDCConstants.FDC_PARAM_CREATEPARTADEDUCT)){
			builder.appendSql("select py.fcontractbillid, sum(pa.FAMOUNT) as partAMatLoaAmt, sum(pa.FOriginalAmount) as  partAMatlAmt" +
					" from T_CON_PartAOfPayReqBill as pa,T_CAS_PaymentBill as py where ");
			builder.appendParam("(py.FContractBillID",conIds.toArray());
			builder.appendSql(" and ");
			builder.appendParam(" py.FBillstatus", new Integer(BillStatusEnum.PAYED_VALUE));
			builder.appendSql(" and pa.FPaymentBillID=py.FID");
			builder.appendSql(" ) group by py.fcontractbillid ");
		}else{
			builder.appendSql("select py.fcontractbillid, sum(pa.FAMOUNT) as partAMatLoaAmt,sum(pa.FOriginalAmount) as partAMatlAmt " +
					" from T_CON_PartAConfmOfPayReqBill as pa,T_CAS_PaymentBill as py where ");
			builder.appendParam("(py.FContractBillID",conIds.toArray());
			builder.appendSql(" and ");
			builder.appendParam(" py.FBillstatus", new Integer(BillStatusEnum.PAYED_VALUE));
			builder.appendSql(" and pa.FPaymentBillID=py.FID");
			builder.appendSql(" ) group by py.fcontractbillid ");
		}
		rs = builder.executeQuery();
		try {
			while (rs.next()) {
				String contractId = rs.getString("fcontractbillid");
				BigDecimal partAMatlAmt = rs.getBigDecimal("partAMatlAmt");
				BigDecimal partAMatLoaAmt = rs.getBigDecimal("partAMatLoaAmt");
				ortherAmt.put(contractId+"partAMatlAmt", partAMatlAmt);
				ortherAmt.put(contractId+"partAMatLoaAmt", partAMatLoaAmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
    	return ortherAmt;
    }
    /**
     * 判断是否已付结算款
     * @param ctx 上下文
     * @param contractId  合同id
     * @param hasSettled  是否最终结算
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected boolean hasPaySettle(Context ctx,String contractId,boolean hasSettled) throws BOSException,EASBizException{
    	if(!hasSettled){
    		return false;
    	}
    	boolean hasPaySettle = false;
		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.getSelector().add("FdcPayType.payType.id");
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("billStatus",BillStatusEnum.PAYED));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("contractBillId",contractId));
		viewInfo.setFilter(filterInfo);
		PaymentBillCollection pbc = PaymentBillFactory
			.getLocalInstance(ctx).getPaymentBillCollection(viewInfo);
		//若合同结算了并且付了结算款 则未完工工程量为0
		if(pbc != null && pbc.size() > 0){
			for(int i = 0 ; i < pbc.size(); i++){
				PaymentBillInfo pbi = pbc.get(i);
				if (pbi != null
						&& pbi.getFdcPayType() != null
						&& pbi.getFdcPayType().getPayType() != null
						&& pbi.getFdcPayType().getPayType().getId() != null
						&& PaymentTypeInfo.settlementID.equals(pbi
								.getFdcPayType().getPayType().getId()
								.toString())) {
					hasPaySettle = true;
					break;
				}
			}		
		}
    	return hasPaySettle;
    }
    /**
     * 判断是否已付结算款
     * @param ctx 上下文
     * @param conIds  合同id
     * @param hasSettled  是否最终结算
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected Map hasPaySettle(Context ctx,Set conIds,boolean hasSettled) throws BOSException,EASBizException{
    	Map hasPaySettleMap = new HashMap();
    	if(!hasSettled){
    		return hasPaySettleMap;
    	}
//		EntityViewInfo viewInfo = new EntityViewInfo();
//		viewInfo.getSelector().add("contractBillId");
//		viewInfo.getSelector().add("FdcPayType.payType.id");
//		FilterInfo filterInfo = new FilterInfo();
//		filterInfo.getFilterItems().add(
//				new FilterItemInfo("billStatus",BillStatusEnum.PAYED));
//		filterInfo.getFilterItems().add(
//				new FilterItemInfo("contractBillId",conIds,CompareType.INCLUDE));
//		viewInfo.setFilter(filterInfo);
//		viewInfo.getSorter().add(new SorterItemInfo("contractBillId"));
//		PaymentBillCollection pbc = PaymentBillFactory
//			.getLocalInstance(ctx).getPaymentBillCollection(viewInfo);
//		//若合同结算了并且付了结算款 则未完工工程量为0
//		if(pbc != null && pbc.size() > 0){
//			for(int i = 0 ; i < pbc.size(); i++){
//				PaymentBillInfo pbi = pbc.get(i);
//				String id = pbi.getContractBillId();
//				if(pbi != null && PaymentTypeInfo.settlementID.equals(
//						pbi.getFdcPayType().getPayType().getId().toString())){
//					hasPaySettleMap.put(id,Boolean.valueOf(true));
//					break;
//				}else{
//					hasPaySettleMap.put(id,Boolean.valueOf(false));
//				}
//			}		
//		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("       select distinct(pay.fcontractbillid) as contractId from t_cas_paymentbill pay inner join \r\n");
		builder.appendSql("       t_fdc_paymenttype payType on payType.Fid = pay.ffdcpaytypeid \r\n");
		builder.appendSql("       where payType.Fpaytypeid = 'Ga7RLQETEADgAAC/wKgOlOwp3Sw=' and \r\n" );
		builder.appendParam("pay.fcontractbillid",conIds.toArray());
		builder.appendSql("       and pay.fbillstatus = 15 \r\n");
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				String contractId = rs.getString("contractId");
				hasPaySettleMap.put(contractId, Boolean.TRUE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
    	return hasPaySettleMap;
    }
    /**
     * 同步原有合同数据
     */
	protected void _synOldContract(Context ctx) throws BOSException, EASBizException {

    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
//    	//执行表中已存在数据的合同id
//    	Set conIds = new HashSet();
//    	builder.appendSql("select FContractBillId from T_CON_ContractExecInfos");
//    	IRowSet rs;
//		try {
//			rs = builder.executeQuery();
//			while(rs.next()){
//				String conBillId = rs.getString("FContractBillId");
//				conIds.add(conBillId);				
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		builder.clear();
    	IRowSet rs;
		Set newConIds = new HashSet();
    	//查询所有已审批合同-不计已存在的单据
    	builder.appendSql(" select fid, fcurprojectid ,famount,foriginalAmount, FControlUnitID,FOrgUnitID,FPeriodID from T_CON_ContractBill where ");
    	builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
    	builder.appendSql(" and (fcontractpropert!='SUPPLY' or fisamtwithoutcost!=1 )");
//    	if(conIds != null && conIds.size() > 0){
    		builder.appendSql(" and fid not in ( select FContractBillId from T_CON_ContractExecInfos ");
//    		builder.appendParam(conIds.toArray());
    		builder.appendSql(" where FContractBillId is not null)");
//    	}
    	//加上CU过滤
    	
    	String insertSql = "insert into T_Con_ContractExecInfos (FID,FContractBillid,FCurProjectid,FCostAmt ,FCostAmtOri ," +
    			" FControlUnitID,FOrgUnitID,FPeriodID,FisNoText )" +
    			" values (?,?,?,?,?,?,?,?,?)";
    	List insertList = new ArrayList();
    	try {
			rs = builder.executeQuery();
			while(rs.next()){
				ContractExecInfosInfo info = new ContractExecInfosInfo();
				String id = BOSUuid.create(info.getBOSType()).toString();
				String contractId = rs.getString("fid");
				newConIds.add(contractId);
				String curProjectId = rs.getString("fcurprojectid").toString();
				BigDecimal costAmt = rs.getBigDecimal("famount");
				BigDecimal costAmtOri = rs.getBigDecimal("foriginalAmount");
				String controlUnitId = rs.getString("FControlUnitID");
				String orgUnitId = rs.getString("FOrgUnitID");
				String periodId = rs.getString("FPeriodID");
				insertList.add(Arrays.asList(new Object[] {id, contractId,curProjectId,costAmt,costAmtOri
						,controlUnitId,orgUnitId,periodId,new Integer(0)}));
			}
			//同步已审批合同 写入工程项目id、合同id、合同成本等数据
			builder.executeBatch(insertSql,insertList);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
		if(newConIds != null && newConIds.size() >0){
			List list=new ArrayList();
			if(newConIds.size()>500){
				int i=0;
				Set mySet=new HashSet();
				for(Iterator iter=newConIds.iterator();iter.hasNext();i++){
					if(i%500==0){
						list.add(mySet);
						mySet=new HashSet();
					}
					mySet.add(iter.next());
				}
				list.add(mySet);
			}else{
				list.add(newConIds);
			}
			
			for(int i=0;i<list.size();i++){
				//先简单处理一下,以后再说
				newConIds=(Set)list.get(i);
				if(newConIds.size()==0){
					continue;
				}
				//同步变更签证确认 写入变更金额等
				builder.clear();
				String updateChangeSql = " update T_CON_ContractExecInfos set FChangeAmt = ? ,FChangeAmtOri = ?, FCostAmt = ?" +
				", FCostAmtOri = ? where FContractBillId = ?";
				List updateChangeList  = new ArrayList();
//				Set changeConIds = new HashSet(); 
				builder.appendSql("select con.FID as contractId,con.FAmount as amount ,con.FOriginalAmount as oriAmount ," +
						" sum(case conChange.fhasSettled when  0 then  conChange.famount " +
						" else conChange.fbalanceamount end ) as changeAmt " +
						", sum(case conChange.fhasSettled when 0 then conChange.foriginalAmount else conChange.foriBalanceAmount end) as changeAmtOri" +
				" from T_Con_ContractChangeBill conChange inner join T_CON_ContractBill con on " +
				" con.FID = conChange.FContractBillID where ");
				builder.appendParam("con.FID",newConIds.toArray());
				builder.appendSql(" and ( ");
				builder.appendParam("conChange.FState",FDCBillStateEnum.AUDITTED_VALUE);
				builder.appendSql(" or ");
				builder.appendParam("conChange.FState",FDCBillStateEnum.VISA_VALUE);
				builder.appendSql(" or ");
				builder.appendParam("conChange.FState",FDCBillStateEnum.ANNOUNCE_VALUE);
				builder.appendSql(" ) ");		
				builder.appendSql(" group by con.FID,con.FAmount,con.FOriginalAmount ");
				try {
					rs = builder.executeQuery();
					while(rs.next()){
						String contractId = rs.getString("contractId");
						BigDecimal amount = rs.getBigDecimal("amount");
						BigDecimal amountOri = rs.getBigDecimal("oriAmount");
						BigDecimal changeAmt = rs.getBigDecimal("changeAmt");
						BigDecimal changeAmtOri = rs.getBigDecimal("changeAmtOri");
						updateChangeList.add(Arrays.asList(new Object[]{changeAmt,changeAmtOri,
								FDCHelper.add(changeAmt,amount),FDCHelper.add(changeAmtOri,amountOri),contractId}));
//						changeConIds.add(contractId);
					}
//					if(changeConIds != null && changeConIds.size() > 0){
//						EntityViewInfo view = new EntityViewInfo();
//						view.getSelector().add("id");
//						view.getSelector().add("amount");
//						view.getFilter().getFilterItems().add(new FilterItemInfo("id",changeConIds,CompareType.INCLUDE));
//						ContractBillCollection coll = ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
//						while(rs.next()){
//							String contractId = rs.getString("contractId");
//							BigDecimal changeAmt = rs.getBigDecimal("changeAmt");
//							for(int i = 0 ; i < coll.size(); i++){
//								String id = coll.get(i).getId().toString();
//								if(id.equals(contractId)){
//									BigDecimal amount = coll.get(i).getAmount();
//									updateChangeList.add(Arrays.asList(new Object[]{changeAmt,FDCHelper.add(changeAmt,amount),id}));
//								}
//							}
//						}
//						//同步变更签证确认-变更金额、成本金额
//						builder.executeBatch(updateChangeSql,updateChangeList);
//						
//					}
					builder.executeBatch(updateChangeSql,updateChangeList);
				} catch (SQLException e) {
					e.printStackTrace();
					throw new BOSException(e);
				}
				/**
				 * 2009-05-19 根据最新需求确定，基本所有金额都加上原币字段
				 */
				
				//同步结算单、付款单--同步结算金额、已完工工程量、未完工工程量、合同成本、最终结算、违约、扣款、奖励、发票金额等
				builder.clear();
				String updateSettleSql = " update T_CON_ContractExecInfos set FCostAmt = ? ,FCostAmtOri = ?, FSettleAmt = ? , FSettleAmtOri =? ," +
						" FHasSettled = ? , FCompleteAmt = ? ,FNotCompletedAmt = ? ,FDeductedAmt = ? , FDeductedAmtOri = ? ," +
						" FCompensatedAmt = ?, FCompensatedAmtOri = ?, FGuerdonAmt = ?, FGuerdonAmtOri = ?" +
						" ,FInvoicedAmt = ? , FPaidAmt = ? ,FPaidAmtOri = ?,FpartAMatlAmt=?, FpartAMatLoaAmt=? where FContractBillId = ? ";
				List updateSettleList = new ArrayList();
				//已付款的付款单已实现之和
				Map processAmtMap = getProcessAmt(ctx,newConIds);
				//合同是否已付结算款
				Map hasPaySettleMap = hasPaySettle(ctx,newConIds,true);
				//合同变更金额
				Map changeAmtMap = getChangeAmt(ctx,newConIds);
				//合同结算金额（原币）
				Map settleMap = getSettleAmtOri(ctx,newConIds);
				//合同违约、扣款、奖励、已开发票金额
				Map ortherMap = getOrtherAmt(ctx,newConIds);
				//合同已付款金额
				Map paidMap = getPaidAmt(ctx,newConIds);
				builder.appendSql("select FID,FHasSettled,FSettleAmt,FAmount,FOriginalAmount from T_CON_ContractBill where ");
				builder.appendParam("fid",newConIds.toArray());
//				builder.appendSql(" and ");
//				builder.appendParam("FHasSettled", new Integer(1));
				rs = builder.executeQuery();
				try {
					while(rs.next()){
						//合同id
						String id = rs.getString("FID");
						boolean hasSettled = rs.getBoolean("FHasSettled");
						boolean hasPaySettle = false;
						if(hasPaySettleMap != null && hasPaySettleMap.containsKey(id)){
							hasPaySettle = ((Boolean)hasPaySettleMap.get(id)).booleanValue();						
						}
						Integer hasSett = new Integer(0);
						//合同结算价(本币)
						BigDecimal settleAmt = rs.getBigDecimal("FSettleAmt");
						//合同结算价(原币)
						BigDecimal settleAmtOri = FDCHelper.ZERO;
						//合同已付进度款的已实现之和
						BigDecimal processAmt = FDCHelper.ZERO;
						//已完工
						BigDecimal completeAmt = FDCHelper.ZERO;
						//未完工
						BigDecimal notCompleteAmt = FDCHelper.ZERO;
						//合同成本(本币)
						BigDecimal costAmt = FDCHelper.ZERO;
						//合同成本(原币)
						BigDecimal costAmtOri = FDCHelper.ZERO;
				    	//扣款(本币)
				    	BigDecimal deductedAmt = FDCHelper.ZERO;
				    	//扣款(原币)
				    	BigDecimal deductedAmtOri = FDCHelper.ZERO;
				    	//违约(本币)
				    	BigDecimal compensatedAmt = FDCHelper.ZERO;
				    	//违约(原币)
				    	BigDecimal compensatedAmtOri = FDCHelper.ZERO;
				    	//奖励(本币)
				    	BigDecimal guerdonAmt = FDCHelper.ZERO;
				    	//奖励(原币)
				    	BigDecimal guerdonAmtOri = FDCHelper.ZERO;
				    	//发票金额
				    	BigDecimal invoicedAmt = FDCHelper.ZERO;
				    	//已付款金额(本币)
				    	BigDecimal paidAmt = FDCHelper.ZERO;
				    	//已付款金额(原币)
				    	BigDecimal paidAmtOri = FDCHelper.ZERO;
				    	//甲供合同扣款金额(原币)
				    	BigDecimal partAMatlAmt = FDCHelper.ZERO;
				    	//甲供合同扣款金额(本币)
				    	BigDecimal partAMatLoaAmt = FDCHelper.ZERO;
				    	if(settleMap != null && settleMap.containsKey(id)){
				    		settleAmtOri = (BigDecimal)settleMap.get(id+"ori");
				    	}
				    	if(ortherMap != null ){
				    		deductedAmt = (BigDecimal)ortherMap.get(id+"deductedAmt");
				    		deductedAmtOri = (BigDecimal)ortherMap.get(id+"deductedAmtOri");
				    		compensatedAmt = (BigDecimal)ortherMap.get(id+"compensatedAmt");
				    		compensatedAmtOri = (BigDecimal)ortherMap.get(id+"compensatedAmtOri");
				    		guerdonAmt = (BigDecimal)ortherMap.get(id+"guerdonAmt");
				    		guerdonAmtOri = (BigDecimal)ortherMap.get(id+"guerdonAmtOri");
				    		invoicedAmt = (BigDecimal)ortherMap.get(id+"invoicedAmt");
				    		partAMatlAmt = (BigDecimal) ortherMap.get(id+"partAMatlAmt");
				    		partAMatLoaAmt = (BigDecimal) ortherMap.get(id+"partAMatLoaAmt");
				    	}
				    	if(paidMap != null && paidMap.containsKey(id)){
				    		paidAmt = (BigDecimal)paidMap.get(id);
				    		paidAmtOri = (BigDecimal)paidMap.get(id+"ori");
				    	}
						if(processAmtMap != null && processAmtMap.containsKey(id)){
							processAmt = (BigDecimal)processAmtMap.get(id);
						}
						//合同已最终结算
						if(hasSettled){
							hasSett = new Integer(1);
							//已付款结算款
							if(hasPaySettle){
								costAmt = settleAmt;
								costAmtOri = settleAmtOri;
								completeAmt = settleAmt;
								notCompleteAmt = FDCHelper.ZERO;
							}
							//未付结算款
							else{
								costAmt = settleAmt;
								costAmtOri = settleAmtOri;
								completeAmt = processAmt;
								notCompleteAmt = FDCHelper.subtract(settleAmt,processAmt);
							}
						}
						//合同未结算
						else{
							BigDecimal amount = rs.getBigDecimal("FAmount");
							BigDecimal amountOri = rs.getBigDecimal("FOriginalAmount");
							BigDecimal changeAmt = FDCHelper.ZERO;
							BigDecimal changeAmtOri = FDCHelper.ZERO;
							if(changeAmtMap != null && changeAmtMap.containsKey(id)){
								changeAmt = (BigDecimal)changeAmtMap.get(id);
								changeAmtOri = (BigDecimal)changeAmtMap.get(id+"ori");
							}
							costAmt = FDCHelper.add(amount,changeAmt);
							costAmtOri = FDCHelper.add(amountOri, changeAmtOri);
							settleAmt = FDCHelper.ZERO;
							completeAmt = processAmt;
							notCompleteAmt = FDCHelper.subtract(costAmt,processAmt);
						}
						updateSettleList.add(Arrays.asList(new Object[]{costAmt,costAmtOri,settleAmt,settleAmtOri,hasSett,
								completeAmt,notCompleteAmt,deductedAmt,deductedAmtOri,compensatedAmt,compensatedAmtOri,
								guerdonAmt,guerdonAmtOri,invoicedAmt,paidAmt,paidAmtOri,partAMatlAmt,partAMatLoaAmt,id}));
					}
					builder.executeBatch(updateSettleSql,updateSettleList);
				} catch (SQLException e) {
					e.printStackTrace();
					throw new BOSException(e);
				}
				
//				//同步付款单--同步已完工、未完工、违约、扣款、发票、
//				builder.clear();
//				String updatePaymentSql = "update T_CON_ContractExecInfos set FCompleteAmt = ? ,FNotCompletedAmt = ?  " +
//						"";
			
			}
			
		}
		synOldNoTextContract(ctx);
	}
	/**
	 * 取合同最终结算原币金额
	 * @param ctx
	 * @param contractId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected BigDecimal getSettleAmtOri(Context ctx,String contractId) throws BOSException,EASBizException{
		BigDecimal settlePriceOri = FDCHelper.ZERO;
		if(contractId == null || "".equals(contractId))
			return settlePriceOri;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select fsettleprice,foriginalamount from t_con_contractsettlementbill where fissettled = 1 and ");
		builder.appendParam("fcontractbillid", contractId);
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				settlePriceOri = rs.getBigDecimal("foriginalamount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		return settlePriceOri;
	}
	
	/**
	 * 取合同最终结算原币金额
	 * @param ctx
	 * @param contractId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected Map getSettleAmtOri(Context ctx,Set conIds) throws BOSException,EASBizException{
		Map settleAmtOriMap = new HashMap();
		if(conIds == null || conIds.size()<=0)
			return settleAmtOriMap;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select fcontractbillid, fsettleprice,foriginalamount from t_con_contractsettlementbill where fissettled = 1 and ");
		builder.appendParam("fcontractbillid", conIds.toArray());
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				String contractId = rs.getString("FContractBillID");
				BigDecimal settleAmt = rs.getBigDecimal("fsettleprice");
				BigDecimal settleAmtOri = rs.getBigDecimal("foriginalamount");
				settleAmtOriMap.put(contractId, settleAmt);
				settleAmtOriMap.put(contractId+"ori", settleAmtOri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
		return settleAmtOriMap;
	}
	
	/**
	 * 判断是合同还是无文本合同
	 * @param billID
	 * @return
	 *0D6DD1F4是合同的BOSType，3D9A5388是无文本合同的BOSType
	 */
	private boolean isContract(String billID){
		boolean isContract = false;
		BOSObjectType conType = BOSUuid.read(billID).getType();
		if(conType.equals(new BOSObjectType("0D6DD1F4")))
			isContract = true;
		else if(conType.equals(new BOSObjectType("3D9A5388")))
			isContract =false;
		return isContract;
	}
	
	/**
	 * 补充合同审批，更新主合同执行情况记录(主要是合同成本,未完工工程量   -by neo)
	 * @param contractId 应该是主合同的ID
	 * @return
	 */
	
	protected void _updateSuppliedContract(Context ctx, String type,
			String contractId) throws BOSException, EASBizException {
		if(contractId == null) return;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_CON_ContractExecInfos set (FCostAmt,FCostAmtOri,Fnotcompletedamt)=" +
				"(select c.famount,c.fOriginalAmount,c.famount-i.fcompleteamt from t_con_contractbill c,t_con_contractexecinfos i " +
				" where c.fid=? and c.fid=i.fcontractbillid) " +
				" where FContractBillId=?");
		builder.addParam(contractId);
		builder.addParam(contractId);
		builder.executeUpdate(ctx);
	}
	private void synOldNoTextContract(Context ctx) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	IRowSet rs;
		List newNoTextConIds = new ArrayList();
    	//查询所有已审批无文本合同-不计已存在的单据
    	builder.appendSql(" select fid, fcurprojectid ,famount,foriginalAmount, FControlUnitID,FOrgUnitID,FPeriodID,FInvoiceAmt from T_CON_ContractWithoutText where ");
    	builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
    		builder.appendSql(" and fid not in ( select FConWithoutTextID from T_CON_ContractExecInfos ");
//    		builder.appendParam(conIds.toArray());
    		builder.appendSql(" where FConWithoutTextID is not null)");
//    	}
    	//加上CU过滤
    	String insertSQL = "insert into T_Con_ContractExecInfos (FID,FConWithoutTextID,FCurProjectid,FAmount,FOriginalAmount,FCostAmt ,FCostAmtOri ," +
    			" FControlUnitID,FOrgUnitID,FPeriodID,FisNoText,FCompleteAmt,FInvoicedAmt )" +
    			" values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	List insertList = new ArrayList();
    	try {
			rs = builder.executeQuery();
			while(rs.next()){
				ContractExecInfosInfo info = new ContractExecInfosInfo();
				String id = BOSUuid.create(info.getBOSType()).toString();
				String contractId = rs.getString("fid");
				newNoTextConIds.add(contractId);
				String curProjectId = rs.getString("fcurprojectid").toString();
				BigDecimal costAmt = rs.getBigDecimal("famount");
				BigDecimal costAmtOri = rs.getBigDecimal("foriginalAmount");
				BigDecimal invoiceAmt = rs.getBigDecimal("FInvoiceAmt");
				String controlUnitId = rs.getString("FControlUnitID");
				String orgUnitId = rs.getString("FOrgUnitID");
				String periodId = rs.getString("FPeriodID");
				insertList.add(Arrays.asList(new Object[] {id, contractId,curProjectId,costAmt,costAmtOri,
						costAmt,costAmtOri,controlUnitId,orgUnitId,periodId,new Integer(1),costAmt,invoiceAmt}));
			}
			builder.executeBatch(insertSQL,insertList);
			//同步已付款无文本合同的付款金额
			builder.clear();
//			String updateSQL = "update t_con_contractexecinfos set (FPaidAmt,FPaidAmtOri)=" +
//						"(select famount,flocalamount from t_cas_paymentbill where fbillstatus=? and fcontractbillid=?) " +
//						" where FConWithoutTextID=?";
			String updateSQL = "update t_con_contractexecinfos set FPaidAmt=?,FPaidAmtOri=? where FConWithoutTextID=?";
			List updatePaiedList = new ArrayList();
			if(newNoTextConIds != null && newNoTextConIds.size() > 0){
				for(int i = 0;i<newNoTextConIds.size();i++){
					String noTextContractId = newNoTextConIds.get(i).toString();
					IRowSet rs1;
					builder.clear();
					builder.appendSql("select fcontractbillid,famount,flocalamount from t_cas_paymentbill where fbillstatus=? and fcontractbillid=?");
					builder.addParam(new Integer(BillStatusEnum.PAYED_VALUE));
					builder.addParam(noTextContractId);
					rs1 = builder.executeQuery();
					while(rs1.next()){
						noTextContractId = rs1.getString("fcontractbillid");
						BigDecimal paiedAmt = rs1.getBigDecimal("famount");
						BigDecimal paiedOriAmt = rs1.getBigDecimal("flocalamount");
						updatePaiedList.add(Arrays.asList(new Object[]{paiedAmt,paiedOriAmt,noTextContractId}));
					}
				}
				builder.executeBatch(updateSQL, updatePaiedList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}
}