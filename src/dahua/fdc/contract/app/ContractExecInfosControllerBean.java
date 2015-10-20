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
     * 2009-05-19 �����������󣬻����������ֶζ����Ӷ�Ӧ��ԭ���ֶΣ��������߼����������Ӷ�ԭ���ֶεĲ�����
     */
    
    
    /**
     * ��ͬ������������ʱ���������Ϣ
     * @param type ״̬ (����������)
     * @param contractId ��ͬid
     */
    protected void _updateContract(Context ctx, String type, String contractId)throws BOSException, EASBizException
    {
    	if(contractId.equals("")){
    		return;
    	}
    	boolean isContract = isContract(contractId);
    	/**
    	 * ��ͬ����  ������Ӧ��Ϣ
    	 * contractBill ��ͬ
    	 * curProject ������Ŀ
    	 * costAmt ��ͬ�ɱ�
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
	    		//����
	    		info.setCostAmt(conBill.getAmount());
	    		//ԭ��
	    		info.setCostAmtOri(conBill.getOriginalAmount());
	    		info.setIsNoText(false);
	    		_addnew(ctx,info);
	    		/**
	    		 * 2009-05-22 �п��ܺ�ͬ������ǰ�Ѿ����˱����������㣬���ں�ͬ����ʱ�����ø��±���ȡ�
	    		 */
    			_updateChange(ctx,ContractExecInfosInfo.EXECINFO_AUDIT,contractId);
    		}else {
    			ContractWithoutTextInfo conWithoutText = ContractWithoutTextFactory.
    					getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
    			info.setConWithoutText(conWithoutText);
	    		info.setCurProject(conWithoutText.getCurProject());
	    		//����
	    		info.setCostAmt(conWithoutText.getAmount());
	    		//ԭ��
	    		info.setCostAmtOri(conWithoutText.getOriginalAmount());
	    		info.setCompletedAmt(conWithoutText.getAmount());
	    		info.setInvoicedAmt(conWithoutText.getInvoiceAmt());
	    		info.setPeriod(conWithoutText.getPeriod());
	    		info.setCU(conWithoutText.getCU());
	    		info.setOrgUnit(conWithoutText.getOrgUnit());
	    		info.setIsNoText(true);
	    		_addnew(ctx,info);
    		}
    	}//��ͬ������ ɾ����Ӧ��Ϣ
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
     * ��ͬ���������������ʱ���������Ϣ
     * @param type ״̬ (����������)
     * @param contractId ��ͬid
     */     
    protected void _updateChange(Context ctx, String type, String contractId)throws BOSException, EASBizException
    {
    	//�ų�null��""
    	if(FDCHelper.isEmpty(contractId)){
    		return;
    	}
    	/**
    	 * �����������������ʱ���º�ִͬ�б��������
    	 * costAmt   ��ͬ�ɱ�
    	 * changeAmt ������ϼ� ���������� ��Ϊ��������� δ������� ��Ϊ������
    	 */
    	if(ContractExecInfosInfo.EXECINFO_AUDIT.equals(type) 
    			|| ContractExecInfosInfo.EXECINFO_UNAUDIT.equals(type)){
    		//���ִ�б����
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
	    	//��ñ�����
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
				//2009-05-23 �п����ڽ���������������
				_updateSettle(ctx,ContractExecInfosInfo.EXECINFO_AUDIT,contractId); 				
			}
    	}

    	//�п��ܸ���δ�깤������
    	_updatePayment(ctx,ContractExecInfosInfo.EXECINFO_PAY,contractId);
    }
    /**
     * ��ͬ��Ӧ���㵥������������ʱ���������Ϣ
     * @param type ״̬ ����������
     * @param contractId ��ͬid
     */
    protected void _updateSettle(Context ctx, String type, String contractId)throws BOSException, EASBizException
    {
    	if(contractId.equals("")){
    		return;
    	}
    	/**
    	 * ���㵥����ʱ����ִ�б��������
    	 * settleAmt  ������
    	 * costAmt    ��ͬ�ɱ�
    	 * hasSettled �Ƿ����ս���
    	 * completedAmt ���깤������ --���깤�������Ը���Ϊ���� δ���� ���깤 = �����Ѹ�����ȿ�֮��
    	 * notCompletedAmt δ�깤������ �ѽ���δ���� δ�깤 = ����� - �����Ѹ�����ȿ�֮��
    	 * 						      δ���� δ�깤 = ��ͬ���+ ������ - ���깤������
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
    			//��ñ�����
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
    		//���º�ͬ�ɱ� ������ ���ս��� ���깤������    		
    		info.setHasSettled(conBill.isHasSettled());
    		BigDecimal processAmt = getProcessAmt(ctx,contractId);
    		info.setCompletedAmt(processAmt);
    		//�ѽ���δ���� δ�깤 = ����� - �����Ѹ�����ȿ�֮��
    		info.setNotCompletedAmt(FDCHelper.subtract(temp,processAmt));
			selector.add("costAmt");
			selector.add("costAmtOri");
			selector.add("hasSettled");
    		selector.add("settleAmt");
    		selector.add("settleAmtOri");
    		//���깤�������Ը���Ϊ��׼
    		selector.add("completedAmt");
    		selector.add("notCompletedAmt");
    		_updatePartial(ctx,info,selector);
    	}else if(ContractExecInfosInfo.EXECINFO_UNAUDIT.equals(type)){
    		//������
    		info.setSettleAmt(null);
    		info.setSettleAmtOri(null);
    		info.setHasSettled(false);
    		//��ø��
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
    		//δ���� ���깤 = �����Ѹ����ȿ�ϼ�
    		temp = getProcessAmt(ctx,contractId);		
			//���깤������
			info.setCompletedAmt(temp);
			BigDecimal changeAmt = getChangeAmt(ctx,contractId);
			BigDecimal changeAmtOri = getChangeAmtOri(ctx,contractId);
			//������ ��ͬ�ɱ� 
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
			//δ�깤������ = ��ͬ���+ ������-���깤������
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
	 * ������������ʱ������������
	 * @param type ״̬ ���������
	 * @param contractId ��ͬid
	 */
    protected void _updatePayment(Context ctx, String type, String contractId)throws BOSException, EASBizException
    {
    	if(contractId.equals("")){
    		return;
    	}
    	/**
    	 * �������ʱ ����ִ�б��������
    	 * deductedAmt �ۿ���
    	 * compensatedAmt ΥԼ���
    	 * invoicedAmt �ѿ���Ʊ���
    	 * completedAmt ���깤������
    	 * notCompletedAmt δ�깤������
    	 * partAMatlAmt �׹��ۿ�ԭ�ҽ��
    	 * partAMatLoaAmt �׹��ۿ�ҽ��
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
	    	//�ۿ�
	    	BigDecimal deductedAmt = FDCHelper.ZERO;
	    	BigDecimal deductedAmtOri = FDCHelper.ZERO;
	    	//ΥԼ
	    	BigDecimal compensatedAmt = FDCHelper.ZERO;
	    	BigDecimal compensatedAmtOri = FDCHelper.ZERO;
	    	//����
	    	BigDecimal guerdonAmt = FDCHelper.ZERO;
	    	BigDecimal guerdonAmtOri = FDCHelper.ZERO;
	    	//��Ʊ���
	    	BigDecimal invoicedAmt = FDCHelper.ZERO;
//	    	�׹��ۿ���
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
	    	//�������
	    	if(ContractExecInfosInfo.EXECINFO_PAY.equals(type) ||
	    			ContractExecInfosInfo.EXECINFO_UNPAY.equals(type)){
	        	ContractBillInfo conBill = ContractBillFactory
	    			.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
	        	boolean hasPaySettle = hasPaySettle(ctx,contractId,conBill.isHasSettled());
	        	
	        	
	        	//add by zkyan �������ʱ���ڷ�д���깤����������Ϊ�ڸ������뵥����ʱ��д����Ϊ��ʱ��ִͬ�в�����Ϣ�������깤��������
				//	       	 ��ͬ�ɱ���Ϣ������깤����������ȣ�����ֻ�����Ѹ��������������������ڸ������뵥����ʱ����

	        	/**
	        	 * ������㲢�Ѹ������ �����깤Ϊ��ͬ����ۡ�δ�깤Ϊ0 
	        	 * δ���㸶����� �����깤Ϊ�����Ѹ����ȿ����깤֮�͡�δ�깤=�����-���깤
	        	 * ��δ���� �����깤Ϊ�����Ѹ�����ȿ����깤֮�� δ�깤 = ��ͬ��� + ������ - ���깤
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
    	}else{			//���ı���ͬ�Ĵ������ı���ͬû�н��㣬ֻ�и����ֻ��һ�Ÿ��
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
	    	
	    	//�������
	    	if(ContractExecInfosInfo.EXECINFO_PAY.equals(type) ||
	    			ContractExecInfosInfo.EXECINFO_UNPAY.equals(type)){
	    		
	    	 	// ���ı���ͬ�����θ����ѭ����selector.add("paidAmt")������ظ����У�ɾ��֮ǰ�Ĵ���
				// �������´��룬����getPaidAmt��getPaidAmtOri������ȡ�Ѿ������ Added by
				// Owen_wen 2011-05-23
				
				selector.add("paidAmt");
				selector.add("paidAmtOri");
				info.setPaidAmt(getPaidAmt(ctx, contractId));
				info.setPaidAmtOri(getPaidAmtOri(ctx, contractId));
				
	/*
				 * ���ı���ͬû�м׹��ģ���ˣ�ע�͵�����Ĵ��룬 Added by Owen_wen 2011-05-023
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
     * ��ú�ͬ������ϼ�
     * ����������� ��ȥ���������
     * ����ȡ������ ��ǩ֤ ���·�״̬�ı��ǩ֤ȷ�Ͻ��
     * @param ctx ������
     * @param contractId ��ͬid
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
     * ��ú�ͬ������ϼ� ԭ��
     * ����������� ��ȥ���������
     * ����ȡ������ ��ǩ֤ ���·�״̬�ı��ǩ֤ȷ�Ͻ��
     * @param ctx ������
     * @param contractId ��ͬid
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
     * ��ú�ͬ������ϼ�
     * ����������� ��ȥ���������
     * ����ȡ������ ��ǩ֤ ���·�״̬�ı��ǩ֤ȷ�Ͻ��
     * @param ctx ������
     * @param conIds ��ͬids
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
     * ��ú�ͬ������ϼ� ԭ��
     * ����������� ��ȥ���������
     * ����ȡ������ ��ǩ֤ ���·�״̬�ı��ǩ֤ȷ�Ͻ��
     * @param ctx ������
     * @param conIds ��ͬids
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
     * ��ú�ͬ��������Ѹ���ĸ�������Ϊ���ȿ�����깤�ϼ�
     * @param ctx ������
     * @param contractId ��ͬid
     * @param paymentType ��������id 
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
    	//��FCostAmount����ʹ�ã����滻ΪFCompletePrjAmt
    	//���Ľű�--�����ظ�����
    	String companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		BigDecimal temp = null;
		
		/****�ж��Ƿ����ù��������������������ò�������ֱ��ȡ�������嵥��ֵ -by neo***/
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
     * ��ú�ͬ��������Ѹ���ĸ�������Ϊ���ȿ�����깤�ϼ�
     * @param ctx ������
     * @param conIds ��ͬids
     * @param paymentType ��������id 
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
    	//���Ľű�--�����ظ�����
    	/****�ж��Ƿ����ù��������������������ò�������ֱ��ȡ�������嵥��ֵ -by neo***/
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
     * ��������Ѹ�����λ��֮��
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
     * ��������Ѹ���ԭ��֮��
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
     * ��������Ѹ�����λ��֮�͡�ԭ��֮��
     * @param ctx  ������
     * @param conIds ��ͬids
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
     * ��ú�ͬ��Ӧ�����пۿ����ΥԼ���
     * @param ctx ������
     * @param contractId ��ͬid
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
    	//R110503-380:��ִͬ�в�����Ϣ��ۿ�ΥԼ�����Ƚ��Ӧ��ֻͳ���Ѹ���ĸ���� by zhiyuan_tang 2010-05-13
    	//�ۿ�
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
		//ΥԼ
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
    	//����
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
		//��Ʊ���
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
//		�׹��ۿ���
		builder.clear();
//		�����Ƿ����ò����Ӳ�ͬ���л�ȡ�׹���ͬ��Ϣ
		
		
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
     * ��ú�ͬ��Ӧ�����пۿΥԼ�������ͷ�Ʊ���
     * @param ctx ������
     * @param conIds ��ͬids
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
    	//R110503-380:��ִͬ�в�����Ϣ��ۿ�ΥԼ�����Ƚ��Ӧ��ֻͳ���Ѹ���ĸ���� by zhiyuan_tang 2010-05-13
    	//�ۿ�
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
		//ΥԼ
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
		//����
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
		//��Ʊ���
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
//		�׹��ۿ���
		builder.clear();
//		�����Ƿ����ò����Ӳ�ͬ���л�ȡ�׹���ͬ��Ϣ
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
     * �ж��Ƿ��Ѹ������
     * @param ctx ������
     * @param contractId  ��ͬid
     * @param hasSettled  �Ƿ����ս���
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
		//����ͬ�����˲��Ҹ��˽���� ��δ�깤������Ϊ0
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
     * �ж��Ƿ��Ѹ������
     * @param ctx ������
     * @param conIds  ��ͬid
     * @param hasSettled  �Ƿ����ս���
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
//		//����ͬ�����˲��Ҹ��˽���� ��δ�깤������Ϊ0
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
     * ͬ��ԭ�к�ͬ����
     */
	protected void _synOldContract(Context ctx) throws BOSException, EASBizException {

    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
//    	//ִ�б����Ѵ������ݵĺ�ͬid
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
    	//��ѯ������������ͬ-�����Ѵ��ڵĵ���
    	builder.appendSql(" select fid, fcurprojectid ,famount,foriginalAmount, FControlUnitID,FOrgUnitID,FPeriodID from T_CON_ContractBill where ");
    	builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
    	builder.appendSql(" and (fcontractpropert!='SUPPLY' or fisamtwithoutcost!=1 )");
//    	if(conIds != null && conIds.size() > 0){
    		builder.appendSql(" and fid not in ( select FContractBillId from T_CON_ContractExecInfos ");
//    		builder.appendParam(conIds.toArray());
    		builder.appendSql(" where FContractBillId is not null)");
//    	}
    	//����CU����
    	
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
			//ͬ����������ͬ д�빤����Ŀid����ͬid����ͬ�ɱ�������
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
				//�ȼ򵥴���һ��,�Ժ���˵
				newConIds=(Set)list.get(i);
				if(newConIds.size()==0){
					continue;
				}
				//ͬ�����ǩ֤ȷ�� д��������
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
//						//ͬ�����ǩ֤ȷ��-������ɱ����
//						builder.executeBatch(updateChangeSql,updateChangeList);
//						
//					}
					builder.executeBatch(updateChangeSql,updateChangeList);
				} catch (SQLException e) {
					e.printStackTrace();
					throw new BOSException(e);
				}
				/**
				 * 2009-05-19 ������������ȷ�����������н�����ԭ���ֶ�
				 */
				
				//ͬ�����㵥�����--ͬ����������깤��������δ�깤����������ͬ�ɱ������ս��㡢ΥԼ���ۿ��������Ʊ����
				builder.clear();
				String updateSettleSql = " update T_CON_ContractExecInfos set FCostAmt = ? ,FCostAmtOri = ?, FSettleAmt = ? , FSettleAmtOri =? ," +
						" FHasSettled = ? , FCompleteAmt = ? ,FNotCompletedAmt = ? ,FDeductedAmt = ? , FDeductedAmtOri = ? ," +
						" FCompensatedAmt = ?, FCompensatedAmtOri = ?, FGuerdonAmt = ?, FGuerdonAmtOri = ?" +
						" ,FInvoicedAmt = ? , FPaidAmt = ? ,FPaidAmtOri = ?,FpartAMatlAmt=?, FpartAMatLoaAmt=? where FContractBillId = ? ";
				List updateSettleList = new ArrayList();
				//�Ѹ���ĸ����ʵ��֮��
				Map processAmtMap = getProcessAmt(ctx,newConIds);
				//��ͬ�Ƿ��Ѹ������
				Map hasPaySettleMap = hasPaySettle(ctx,newConIds,true);
				//��ͬ������
				Map changeAmtMap = getChangeAmt(ctx,newConIds);
				//��ͬ�����ԭ�ң�
				Map settleMap = getSettleAmtOri(ctx,newConIds);
				//��ͬΥԼ���ۿ�������ѿ���Ʊ���
				Map ortherMap = getOrtherAmt(ctx,newConIds);
				//��ͬ�Ѹ�����
				Map paidMap = getPaidAmt(ctx,newConIds);
				builder.appendSql("select FID,FHasSettled,FSettleAmt,FAmount,FOriginalAmount from T_CON_ContractBill where ");
				builder.appendParam("fid",newConIds.toArray());
//				builder.appendSql(" and ");
//				builder.appendParam("FHasSettled", new Integer(1));
				rs = builder.executeQuery();
				try {
					while(rs.next()){
						//��ͬid
						String id = rs.getString("FID");
						boolean hasSettled = rs.getBoolean("FHasSettled");
						boolean hasPaySettle = false;
						if(hasPaySettleMap != null && hasPaySettleMap.containsKey(id)){
							hasPaySettle = ((Boolean)hasPaySettleMap.get(id)).booleanValue();						
						}
						Integer hasSett = new Integer(0);
						//��ͬ�����(����)
						BigDecimal settleAmt = rs.getBigDecimal("FSettleAmt");
						//��ͬ�����(ԭ��)
						BigDecimal settleAmtOri = FDCHelper.ZERO;
						//��ͬ�Ѹ����ȿ����ʵ��֮��
						BigDecimal processAmt = FDCHelper.ZERO;
						//���깤
						BigDecimal completeAmt = FDCHelper.ZERO;
						//δ�깤
						BigDecimal notCompleteAmt = FDCHelper.ZERO;
						//��ͬ�ɱ�(����)
						BigDecimal costAmt = FDCHelper.ZERO;
						//��ͬ�ɱ�(ԭ��)
						BigDecimal costAmtOri = FDCHelper.ZERO;
				    	//�ۿ�(����)
				    	BigDecimal deductedAmt = FDCHelper.ZERO;
				    	//�ۿ�(ԭ��)
				    	BigDecimal deductedAmtOri = FDCHelper.ZERO;
				    	//ΥԼ(����)
				    	BigDecimal compensatedAmt = FDCHelper.ZERO;
				    	//ΥԼ(ԭ��)
				    	BigDecimal compensatedAmtOri = FDCHelper.ZERO;
				    	//����(����)
				    	BigDecimal guerdonAmt = FDCHelper.ZERO;
				    	//����(ԭ��)
				    	BigDecimal guerdonAmtOri = FDCHelper.ZERO;
				    	//��Ʊ���
				    	BigDecimal invoicedAmt = FDCHelper.ZERO;
				    	//�Ѹ�����(����)
				    	BigDecimal paidAmt = FDCHelper.ZERO;
				    	//�Ѹ�����(ԭ��)
				    	BigDecimal paidAmtOri = FDCHelper.ZERO;
				    	//�׹���ͬ�ۿ���(ԭ��)
				    	BigDecimal partAMatlAmt = FDCHelper.ZERO;
				    	//�׹���ͬ�ۿ���(����)
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
						//��ͬ�����ս���
						if(hasSettled){
							hasSett = new Integer(1);
							//�Ѹ�������
							if(hasPaySettle){
								costAmt = settleAmt;
								costAmtOri = settleAmtOri;
								completeAmt = settleAmt;
								notCompleteAmt = FDCHelper.ZERO;
							}
							//δ�������
							else{
								costAmt = settleAmt;
								costAmtOri = settleAmtOri;
								completeAmt = processAmt;
								notCompleteAmt = FDCHelper.subtract(settleAmt,processAmt);
							}
						}
						//��ͬδ����
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
				
//				//ͬ�����--ͬ�����깤��δ�깤��ΥԼ���ۿ��Ʊ��
//				builder.clear();
//				String updatePaymentSql = "update T_CON_ContractExecInfos set FCompleteAmt = ? ,FNotCompletedAmt = ?  " +
//						"";
			
			}
			
		}
		synOldNoTextContract(ctx);
	}
	/**
	 * ȡ��ͬ���ս���ԭ�ҽ��
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
	 * ȡ��ͬ���ս���ԭ�ҽ��
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
	 * �ж��Ǻ�ͬ�������ı���ͬ
	 * @param billID
	 * @return
	 *0D6DD1F4�Ǻ�ͬ��BOSType��3D9A5388�����ı���ͬ��BOSType
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
	 * �����ͬ��������������ִͬ�������¼(��Ҫ�Ǻ�ͬ�ɱ�,δ�깤������   -by neo)
	 * @param contractId Ӧ��������ͬ��ID
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
    	//��ѯ�������������ı���ͬ-�����Ѵ��ڵĵ���
    	builder.appendSql(" select fid, fcurprojectid ,famount,foriginalAmount, FControlUnitID,FOrgUnitID,FPeriodID,FInvoiceAmt from T_CON_ContractWithoutText where ");
    	builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
    		builder.appendSql(" and fid not in ( select FConWithoutTextID from T_CON_ContractExecInfos ");
//    		builder.appendParam(conIds.toArray());
    		builder.appendSql(" where FConWithoutTextID is not null)");
//    	}
    	//����CU����
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
			//ͬ���Ѹ������ı���ͬ�ĸ�����
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