package com.kingdee.eas.fdc.basedata.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.Context;
import com.kingdee.bos.IBOSObject;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCCostSplitAdapter;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCAction;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryTemp;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryTempInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitTemp;
import com.kingdee.eas.fdc.contract.ConChangeSplitTempFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitTempInfo;
import com.kingdee.eas.fdc.contract.ConChangeTemp;
import com.kingdee.eas.fdc.contract.ConChangeTempFactory;
import com.kingdee.eas.fdc.contract.ConChangeTempInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class FDCBillFacadeControllerBean extends AbstractFDCBillFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.FDCBillFacadeControllerBean");

	protected void _autoChangeSettle(Context ctx, String settleId, boolean isAll) throws BOSException, EASBizException {
		_dealSaveAboutConChange(ctx,settleId);
		changeSettle(ctx, settleId, isAll);
	}
	/**
	 * ��ͬ���ս���ʱ�Զ����б�����㣬�����ս��㵥������ɾ���󣬱������������Ȼ���ڡ� 
	 * ��Ȼ���ս��㵥����ʱ����Ӱ�������㣬�ǽ��㵥�������󣬱����������ҲӦ�ûص�ԭ��״̬
	 * Ҫ���ܹ�������������Ϣ��ԭ�ͱ������ȱ��� by cassiel 2010-10-08
	 */
	protected void _dealSaveAboutConChange(Context ctx, String settleId)
	throws BOSException, EASBizException {
		//��ͬID
		SelectorItemCollection selector = new SelectorItemCollection();
//		SysUtil.abort();
		selector.add("contractBill.id");
		ContractSettlementBillInfo settlement = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillInfo(new ObjectUuidPK(settleId));
		String contractId = settlement.getContractBill().getId().toString();
		//���ݺ�ͬ�ҵ����ǩ֤ȷ�ϡ������ֵ�
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
		selector = new SelectorItemCollection();
		selector.add("oriBalanceAmount");
		selector.add("balanceAmount");
		selector.add("hasSettled");
		selector.add("settleTime");
		view.setFilter(filter);
		view.setSelector(selector);
		ContractChangeBillCollection conChangeColl = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
		for(Iterator iter = conChangeColl.iterator();iter.hasNext();){
			//������ǩ֤ȷ�ϵ���Ϣ
			ContractChangeBillInfo conChangeInfo = (ContractChangeBillInfo)iter.next();
			//����ָ�ID�жϣ�������ھ͸��£������ھ�����
			String conChangeID = conChangeInfo.getId().toString();
			 filter = new FilterInfo();
			 filter.getFilterItems().add(new FilterItemInfo("sourceID",conChangeID));
			boolean isExist = ConChangeTempFactory.getLocalInstance(ctx).exists(filter);
			if(isExist){
				ConChangeTempInfo conChangeTemp = new ConChangeTempInfo();
				view = new EntityViewInfo();
				selector = new SelectorItemCollection();
				view.setFilter(filter);
				selector.add("id");
				ConChangeTempInfo temp = (ConChangeTempInfo)ConChangeTempFactory.getLocalInstance(ctx).getCollection(view).get(0);
				conChangeTemp.setId(temp.getId());
				conChangeTemp.setSourceID(conChangeID);
				conChangeTemp.setSettleTime(conChangeInfo.getSettleTime());
				conChangeTemp.setOriBalanceAmount(conChangeInfo.getOriBalanceAmount());
				conChangeTemp.setBalanceAmount(conChangeInfo.getBalanceAmount());
				conChangeTemp.setHasSettled(conChangeInfo.isHasSettled());
				
				ConChangeTempFactory.getLocalInstance(ctx).updatePartial(conChangeInfo, selector);
			
			}else{
				ConChangeTempInfo conChangeTemp = new ConChangeTempInfo();
				
				conChangeTemp.setId(BOSUuid.create(new ConChangeTemp().getType()));
				conChangeTemp.setSettleTime(conChangeInfo.getSettleTime());
				conChangeTemp.setOriBalanceAmount(conChangeInfo.getOriBalanceAmount());
				conChangeTemp.setBalanceAmount(conChangeInfo.getBalanceAmount());
				conChangeTemp.setHasSettled(conChangeInfo.isHasSettled());
				conChangeTemp.setSettleID(settleId);
				conChangeTemp.setSourceID(conChangeInfo.getId().toString());
				
				ConChangeTempFactory.getLocalInstance(ctx).addnew(conChangeTemp);
			}
			
			
			//������ǩ֤�������Ϣ
		}
		//��������ֵ�����Ϣ
		//�ж��Ƿ���Ҫ����
		view = new EntityViewInfo();
		filter = new FilterInfo();
		
		selector = new SelectorItemCollection();
		selector.add("amount");
		selector.add("originalAmount");
		selector.add("id");
		selector.add("createTime");
		selector.add("entrys.id");//��¼
		selector.add("entrys.amount");//��¼
		selector.add("contractChange.id");//���ǩ֤ȷ��
		
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
//		filter.getFilterItems().add(new FilterItemInfo("isInvalid",Boolean.FALSE));
		view.setFilter(filter);
		view.setSelector(selector);
		ConChangeSplitCollection conChangeSplitColl = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitCollection(view);
		for(Iterator iter = conChangeSplitColl.iterator();iter.hasNext();){
			ConChangeSplitInfo conChangeSplit = (ConChangeSplitInfo)iter.next();
			String conChangeId = conChangeSplit.getContractChange().getId().toString();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select count(*)  jishu from T_CON_ConChangeTemp where FSourceID = ? ");
			builder.addParam(conChangeId);
			IRowSet rowSet = builder.executeQuery();
			try {
				if(rowSet.next()){
					int count = rowSet.getInt("jishu");
					if(count > 0){
						builder.clear();
						builder.appendSql("select count(*) jishu from T_CON_ConChangeSplitTemp where FConChangeID=? ");
						builder.addParam(conChangeId);
						IRowSet _rowSet = builder.executeQuery();
						if(_rowSet.next()){
							int _jishu = _rowSet.getInt("jishu");
							if(_jishu == 0){
								ConChangeSplitTempInfo splitTemp = new  ConChangeSplitTempInfo();
								splitTemp.setId(BOSUuid.create(new ConChangeSplitTemp().getType()));
								splitTemp.setSplitID(conChangeSplit.getId().toString());
								splitTemp.setAmount(conChangeSplit.getAmount());
								splitTemp.setOriAmount(conChangeSplit.getOriginalAmount());
								splitTemp.setSplitDate(conChangeSplit.getCreateTime());
								splitTemp.setConChangeID(conChangeId);
								for(Iterator _iter = conChangeSplit.getEntrys().iterator();_iter.hasNext();){//��¼
									ConChangeSplitEntryInfo entry = (ConChangeSplitEntryInfo)_iter.next();
									ConChangeSplitEntryTempInfo tempEntryInfo = new ConChangeSplitEntryTempInfo();
									tempEntryInfo.setId(BOSUuid.create(new ConChangeSplitEntryTemp().getType() ));
									tempEntryInfo.setAmount(entry.getAmount());
									tempEntryInfo.setEntryID(entry.getId().toString());
									
									tempEntryInfo.setParent(splitTemp);
									splitTemp.getEntrys().add(tempEntryInfo);
								}
								
								ConChangeSplitTempFactory.getLocalInstance(ctx).addnew(splitTemp);
							}
						}
					}
				}
			} catch (SQLException e) {
				logger.info(e.getMessage(), e);
				throw new BOSException("SQL Exception", e);
			}
		}
	}
	/**
	 * �������
	 */
	private void changeSettle(Context ctx, String settleId, boolean isAll) throws BOSException, EASBizException {
		if (FDCHelper.isEmpty(settleId)) {
			return;
		}
		/*
		 * isAll=true �����Ԥ��ۣ�����ֵ���������
		 * ���������ͬ����ۣ���ͬǩԼ�ۣ���ͬ����ָ�Ԥ����ܺͣ��������ǩ֤ȷ��Ԥ���/��ͬ����ָ�Ԥ����ܺͣ� 
		 * isAll=false
		 * �����ܶ�-��ͬ�ܶ�-��¼�����ı��ָ���ܶ� ��δ¼�����ı���а������Զ����㼰���
		 */
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("contractBill.id");
		selector.add("contractBill.amount");
		selector.add("settlePrice");
		ContractSettlementBillInfo settleInfo = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillInfo(new ObjectUuidPK(settleId), selector);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBill.id", settleInfo.getContractBill().getId().toString());
		view.getSelector().add("number");
		view.getSelector().add("amount");
		view.getSelector().add("balanceAmount");
		view.getSelector().add("state");
		view.getSelector().add("hasSettled");
		view.getSelector().add("exRate");
		ContractChangeBillCollection changes = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
		BigDecimal total = FDCHelper.ZERO;
		BigDecimal measureAmt=FDCHelper.ZERO;
		ContractChangeBillInfo tempChange = null;
		
		// ֮ǰ�ĺ�ͬ���б�����
		BigDecimal oldChangeAmt = FDCHelper.ZERO;
		for (Iterator iter = changes.iterator(); iter.hasNext();) {
			ContractChangeBillInfo change = (ContractChangeBillInfo) iter.next();
			if (change.isHasSettled()) {
				oldChangeAmt = FDCHelper.add(oldChangeAmt, change.getBalanceAmount());
			} else {
				oldChangeAmt = FDCHelper.add(oldChangeAmt, change.getAmount());
			}
		}

		for (Iterator iter = changes.iterator(); iter.hasNext();) {
			ContractChangeBillInfo change = (ContractChangeBillInfo) iter.next();
			if (change.getState() != FDCBillStateEnum.VISA) {
				throw new EASBizException(new NumericExceptionSubItem("120", "�����ָ�����" + change.getNumber() + "�� ״̬Ϊ����" + change.getState() + "�� ���ܽ��б������,����Ϊ����ǩ֤��״̬"));
			}

			if (isAll && !change.isHasSettled()) {
				BigDecimal amt = FDCHelper.toBigDecimal(change.getAmount());
				total = total.add(amt);
				measureAmt=measureAmt.add(amt);
			} else if (change.isHasSettled()) {
				total = total.add(FDCHelper.toBigDecimal(change.getBalanceAmount()));
				measureAmt = FDCHelper.add(measureAmt, change.getBalanceAmount());
				tempChange = change;
			}
			
		}
		if (total.compareTo(FDCHelper.ZERO) == 0) {
			//			return;
		}
		BigDecimal _settleDifAmt = FDCHelper.toBigDecimal(settleInfo.getSettlePrice()).subtract(FDCHelper.toBigDecimal(settleInfo.getContractBill().getAmount()));
		_settleDifAmt = _settleDifAmt.subtract(FDCHelper.toBigDecimal(total));
		
		if (changes != null && changes.size() == 1 && _settleDifAmt.compareTo(FDCHelper.ZERO) != 0) {
			SelectorItemCollection sic = new SelectorItemCollection();
			FilterInfo filter = new FilterInfo();
			EntityViewInfo views = new EntityViewInfo();
			sic.add("fid");
			filter.getFilterItems().add(new FilterItemInfo("parent.isInvalid", Boolean.FALSE));
			filter.getFilterItems().add(new FilterItemInfo("parent.contractbill.id", changes.get(0).getContractBill().getId().toString()));
			views.setFilter(filter);
			views.setSelector(sic);

			ConChangeSplitEntryCollection changeSplitEntryCollection;
			changeSplitEntryCollection = ConChangeSplitEntryFactory.getLocalInstance(ctx).getConChangeSplitEntryCollection(views);
			if (changeSplitEntryCollection == null || changeSplitEntryCollection.size() == 0) {
				// TODO ���ģ�ҵ����0����������޷����
				// throw new EASBizException(new NumericExceptionSubItem("120",
				// "��������ں�ͬ���+��������ұ����ֵ�û�з�¼����������н�����"));
			}
		}
		
		
		BigDecimal balanceAmt = FDCHelper.ZERO;
		SelectorItemCollection updateSelector = new SelectorItemCollection();
		updateSelector.add("balanceAmount");
		updateSelector.add("hasSettled");
		updateSelector.add("settleTime");
		updateSelector.add("oriBalanceAmount");
		Date settleTime = new Date();
		//������ڸ�����������
		boolean isLessZero=false;
		for (Iterator iter = changes.iterator(); iter.hasNext();) {
			ContractChangeBillInfo change = (ContractChangeBillInfo) iter.next();
			if (!isAll && change.isHasSettled()) {
				continue;
			}
			//�����������Ϊ0�����
			// if (FDCHelper.toBigDecimal(change.getBalanceAmount()).compareTo(
			// FDCHelper.ZERO) == 0) {
			// ����ˣ����������һ���ϣ�֮ǰ�ı�������ֲ���
				if (!change.isHasSettled()) {
					balanceAmt = FDCHelper.add(_settleDifAmt, change.getAmount());
					change.setBalanceAmount(change.getAmount());
				} else {
					balanceAmt = FDCHelper.add(_settleDifAmt, change.getBalanceAmount());
					change.setBalanceAmount(change.getBalanceAmount());
				}
			// } else {
			// balanceAmt =
			// _settleDifAmt.multiply(FDCHelper.toBigDecimal(change.
			// getBalanceAmount()));
			// balanceAmt = balanceAmt.divide(measureAmt, 2,
			// BigDecimal.ROUND_HALF_UP);
			// balanceAmt =
			// balanceAmt.add(FDCHelper.toBigDecimal(change.getBalanceAmount
			// ()));
			// change.setBalanceAmount(FDCHelper.toBigDecimal(balanceAmt));
			// }
			// ��ֻ����һ��ʱ�����ﲻ�����ں����tempChange��β��� by hpw12.6.22
			if (changes.size() > 1) {
				change.setHasSettled(true);
				change.setSettleTime(settleTime);
				change.setOriBalanceAmount(FDCHelper.divide(change.getBalanceAmount(), change.getExRate()));
				ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, updateSelector);
				ConChangeSplitFactory.getLocalInstance(ctx).autoSplit(new ObjectUuidPK(change.getId()));
			}
			if(FDCHelper.toBigDecimal(change.getBalanceAmount()).signum()<0){
				isLessZero=true;
			}
			//if(tempAmt.compareTo(FDCHelper.toBigDecimal(balanceAmt.abs(),2))<0
			// ){
			// tempAmt = FDCHelper.toBigDecimal(balanceAmt.abs(),2);
				tempChange=change;
			// }
		}
		BigDecimal changeAmt=FDCHelper.ZERO;
		FDCSQLBuilder builder1=new FDCSQLBuilder(ctx);
		builder1.appendSql("select case when fhassettled =1 then fbalanceamount  else famount end  as amount from T_Con_ContractChangeBill change  where fcontractbillId=?");
		builder1.addParam(settleInfo.getContractBill().getId().toString());
		IRowSet rowSet1=builder1.executeQuery();
		if(rowSet1!=null){
			try {
				while(rowSet1.next()){
					changeAmt=FDCHelper.add(changeAmt, rowSet1.getBigDecimal("amount"));
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		// ���Ϊ0ʱ����β���
		if (tempChange != null && _settleDifAmt.compareTo(FDCHelper.ZERO) != 0) {
			tempChange.setBalanceAmount(FDCHelper.toBigDecimal(balanceAmt));
			tempChange.setHasSettled(true);
			tempChange.setSettleTime(settleTime);
			tempChange.setOriBalanceAmount(FDCHelper.divide(tempChange.getBalanceAmount(), tempChange.getExRate()));
			ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(tempChange, updateSelector);
			ConChangeSplitFactory.getLocalInstance(ctx).autoSplit(new ObjectUuidPK(tempChange.getId()));
		}
		
		if(ContextUtil.getCurrentOrgUnit(ctx)!=null&&ContextUtil.getCurrentOrgUnit(ctx).getId()!=null	){
			//�����������Ŀ�ܺ�Ϊ���ı�����
			boolean noCtrl=false;
			String comPK=ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
			HashMap hmParamIn = new HashMap();
	        hmParamIn.put(FDCConstants.FDC_PARAM_NOCHANGESPLITACCTCTRL, comPK);
	        try{
	        	HashMap hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
	            Object theValue = hmAllParam.get(FDCConstants.FDC_PARAM_NOCHANGESPLITACCTCTRL);
	            if(theValue != null){
	            	noCtrl=Boolean.valueOf(theValue.toString()).booleanValue();
	    		}
	            if(noCtrl){
	            	isLessZero=false;
	            }
	        }catch(Exception e){
	        	logger.error("check acct sum err:" + e.getMessage(), e);
				throw new BOSException("SQL Exception", e);
	        }
		}
		
		if (isLessZero) {
			// �ж��ǲ����п�ĿС��0�����
			String contractId = settleInfo.getContractBill().getId().toString();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("select fcostaccountid,sum(famount) famount from (\n");
			builder
					.appendSql("(select fcostaccountid,famount from T_CON_ContractCostSplitEntry where fparentid in (select fid from T_CON_ContractCostSplit conSplit where conSplit.fcontractBillId=? and fstate<>?) and ");
			builder.addParam(contractId);
			builder.addParam(FDCBillStateEnum.INVALID_VALUE);
			builder
					.appendSql(" ((fisleaf=1 and (fproductid is null or fsplitType is null or fsplitType<>? )) or (fisleaf=0 and fproductid is null and fsplitType=?))");
			builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
			builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
			builder.appendSql("\n) \nunion all ");
			builder
					.appendSql("(\nselect fcostaccountid,famount from T_CON_ConChangeSplitEntry where fparentid in (select fid from T_CON_ConChangeSplit changeSplit where changeSplit.fcontractBillId=? and fstate<>?) and ");
			builder.addParam(contractId);
			builder.addParam(FDCBillStateEnum.INVALID_VALUE);
			// changeId������ʱ=1ʹ�ò��ı�sql��ȡ������ֵ
			builder
					.appendSql(" ((fisleaf=1 and (fproductid is null or fsplitType is null or fsplitType<>? )) or (fisleaf=0 and fproductid is null and fsplitType=?))");
			builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
			builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
			builder.appendSql(")\n)t group by fcostaccountid");
			IRowSet rowSet = builder.executeQuery();
			try {
				while (rowSet.next()) {
					// String key = rowSet.getString("fcostaccountid");
					// map.put(key,
					// FDCNumberHelper.add(rowSet.getBigDecimal("famount"),
					// map.get(key)));
					BigDecimal tmpAmt = FDCHelper.toBigDecimal(rowSet
							.getBigDecimal("famount"));
					if (tmpAmt.signum() < 0) {
						throw new EASBizException(new NumericExceptionSubItem(
								"101", "�Զ���ֱ������ڿ�Ŀ���֮��С��������,����ȡ��!"));
					}
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		
		// ���º�Լ�滮���
		synProgAmount(ctx, settleInfo.getContractBill().getId().toString(), oldChangeAmt);
	}

	private void synProgAmount(Context ctx, String contractId, BigDecimal oldChangeAmt) throws BOSException, EASBizException {
		// ���ݺ�ͬ�ҵ����ǩ֤ȷ�ϡ������ֵ�
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("oriBalanceAmount");
		selector.add("balanceAmount");
		selector.add("hasSettled");
		selector.add("settleTime");
		selector.add("state");
		view.setFilter(filter);
		view.setSelector(selector);
		ContractChangeBillCollection conChangeColl = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
		// �µı�����
		BigDecimal newChangeAmt = FDCHelper.ZERO;
		for (Iterator iter = conChangeColl.iterator(); iter.hasNext();) {
			// ������ǩ֤ȷ�ϵ���Ϣ
			ContractChangeBillInfo conChangeInfo = (ContractChangeBillInfo) iter.next();
			if (conChangeInfo.isHasSettled()) {
				newChangeAmt = FDCHelper.add(newChangeAmt, conChangeInfo.getBalanceAmount());
			} else {
				newChangeAmt = FDCHelper.add(newChangeAmt, conChangeInfo.getAmount());
			}

		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId), sic);
		ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
		if (pcInfo == null)
			return;
		// �滮���
		// BigDecimal balanceAmt = pcInfo.getBalance();
		// �������
		// BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
		// �����Ԥ�㱾�ҽ��
		// BigDecimal changeAmount = model.getAmount();
		// ��ܺ�ԼǩԼ���
		BigDecimal changeAmountProg = pcInfo.getChangeAmount();
		// ���
		BigDecimal otherChangeAmount = FDCHelper.ZERO;
		// if(flag){
		// pcInfo.setBalance(FDCHelper.subtract(balanceAmt, changeAmount));
		pcInfo.setChangeAmount(FDCHelper.add(FDCHelper.subtract(changeAmountProg, oldChangeAmt), newChangeAmt));
		otherChangeAmount = FDCHelper.subtract(FDCHelper.add(FDCHelper.subtract(changeAmountProg, oldChangeAmt), newChangeAmt), changeAmountProg);
		// }else{
		// pcInfo.setBalance(FDCHelper.add(balanceAmt, changeAmount));
		// pcInfo.setChangeAmount(FDCHelper.subtract(changeAmountProg,
		// changeAmount));
		// }

		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("controlBalance");
		sict.add("signUpAmount");
		sict.add("changeAmount");
		sict.add("settleAmount");
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		service.updatePartial(pcInfo, sict);
		// ���������ĺ�Լ�滮�汾���
		String progId = pcInfo.getId().toString();
		while (progId != null) {
			String nextVersionProgId;
			try {
				nextVersionProgId = getNextVersionProg(ctx, progId);
				if (nextVersionProgId != null) {
					pcInfo = service.getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
					pcInfo.setChangeAmount(FDCHelper.add(pcInfo.getChangeAmount(), otherChangeAmount));
					service.updatePartial(pcInfo, sict);
					progId = pcInfo.getId().toString();
				} else {
					break;
				}
			} catch (SQLException e) {
				logger.error(e);
				throw new BOSException("SQL Exception", e);
			}
		}
	}

	private String getNextVersionProg(Context ctx, String nextProgId) throws BOSException, SQLException {
		String tempId = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rowSet = null;
		builder.appendSql(" select fid from t_con_programmingContract where  FSrcId = '" + nextProgId + "' and fid <>'" + nextProgId + "'");
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			tempId = rowSet.getString("fid");
		}
		return tempId;
	}

//��ʱ��������Ϊ�ǳɱ�����û��ˢ�¹���
	protected void _autoChangeSplit(Context ctx, IObjectPK changePK) throws BOSException, EASBizException {
		if (changePK == null)
			return;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("balanceAmount");
		selector.add("hasSettled");
		selector.add("contractBill.id");
		selector.add("contractBill.isCoseSplit");
		ContractChangeBillInfo info = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo(changePK, selector);
		if (!info.isHasSettled())
			return;
		if(info.getContractBill().isIsCoseSplit()){
			autoCostChangeSplit(ctx, info);
		}else{
			autoNoCostChangeSplit(ctx, info);
		}
	}

	private void autoCostChangeSplit(Context ctx, ContractChangeBillInfo info) throws BOSException, EASBizException {
		// �ý�����滻ԭ���Ĳ��
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select split.fid as splitId,entry.fid as fid,entry.famount as amount from T_CON_ConChangeSplit split ");
		builder.appendSql("inner join T_CON_ConChangeSplitEntry entry on split.fid=entry.fparentid ");
		builder.appendSql("where split.FContractChangeID=? and entry.flevel=0 and split.fstate<>? order by entry.fseq");

		builder.addParam(info.getId().toString());
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
		
		try {
			String splitId = null;
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.size() > 0) {
				HashMap map = new HashMap(rowSet.size());
				BigDecimal sum = FDCHelper.ZERO;
				while (rowSet.next()) {
					BigDecimal amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
					sum = sum.add(amount);
					map.put(rowSet.getString("fid"), amount);
					splitId = rowSet.getString("splitId");
				}
				if (sum.compareTo(FDCHelper.ZERO) == 0) {
					// ��Ŀƽ��
					BigDecimal amount = info.getBalanceAmount().divide(BigDecimal.valueOf(rowSet.size()), 2, BigDecimal.ROUND_HALF_UP);
					Object key = null;
					for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
						key = iter.next();
						map.put(key, amount);
					}

					// ���
					amount = amount.add(info.getBalanceAmount().subtract(amount.multiply(BigDecimal.valueOf(rowSet.size()))));
					map.put(key, amount);

				} else {
					BigDecimal sumTemp = FDCHelper.ZERO;
					BigDecimal amount = FDCHelper.ZERO;
					BigDecimal bigAmount = FDCHelper.MIN_DECIMAL;
					Object key = null;
					Object bigKey = null;
					for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
						// ������̯
						key = iter.next();
						amount = (BigDecimal) map.get(key);
						if (amount.compareTo(bigAmount) > 0) {
							bigAmount = amount;
							bigKey = key;
						}
						amount = amount.multiply(info.getBalanceAmount()).divide(sum, 2, BigDecimal.ROUND_HALF_UP);
						map.put(key, amount);
						sumTemp = sumTemp.add(amount);
					}
					// ����
					amount = info.getBalanceAmount().subtract(sumTemp);
					amount = ((BigDecimal) map.get(bigKey)).add(amount);
					map.put(bigKey, amount);
				}
				for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
					builder.clear();
					Object key = iter.next();
					builder.appendSql("update T_CON_ConChangeSplitEntry set famount=? where fid=?");
					builder.addParam(map.get(key));
					builder.addParam(key);
					builder.execute();
				}

			}
			if (splitId != null) {
				builder.clear();
				builder.appendSql("update T_CON_ConChangeSplit set famount=? where fid=?");
				builder.addParam(info.getBalanceAmount());
				builder.addParam(splitId);
				builder.execute();
				FDCCostSplitAdapter fdcostSplit = new FDCCostSplitAdapter(ctx);
				fdcostSplit.refreshConChange(splitId);
				// �����ֵ����ˢ��������
				/*
				 * //����н�����ˢ�½����� builder.clear(); builder.appendSql("select
				 * split.fid as fid from T_CON_SettlementCostSplit split inner
				 * join T_CON_ContractSettlementBill settle on
				 * split.FSettlementBillID=settle.fid where
				 * settle.FContractBillID=?");
				 * builder.addParam(info.getContractBill().getId().toString());
				 * rowSet= builder.executeQuery(); while(rowSet.next()){
				 * fdcostSplit.refreshSettlement(rowSet.getString("fid")); }
				 */
			}
		} catch (Exception e) {
			throw new BOSException(e);
		}
	}
	
	private void autoNoCostChangeSplit(Context ctx, ContractChangeBillInfo info) throws BOSException, EASBizException {
		// �ý�����滻ԭ���Ĳ��
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select split.fid as splitId,entry.fid as fid,entry.famount as amount from T_CON_ConChangeNoCostSplit split ");
		builder.appendSql("inner join T_CON_ChangeNoSplitEntry entry on split.fid=entry.fparentid and entry.flevel=0 ");
		builder.appendSql("where split.FContractChangeID=? order by entry.fseq");

		builder.addParam(info.getId().toString());
		try {
			String splitId = null;
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.size() > 0) {
				HashMap map = new HashMap(rowSet.size());
				BigDecimal sum = FDCHelper.ZERO;
				while (rowSet.next()) {
					BigDecimal amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
					sum = sum.add(amount);
					map.put(rowSet.getString("fid"), amount);
					splitId = rowSet.getString("splitId");
				}
				if (sum.compareTo(FDCHelper.ZERO) == 0) {
					// ��Ŀƽ��
					BigDecimal amount = info.getBalanceAmount().divide(BigDecimal.valueOf(rowSet.size()), 2, BigDecimal.ROUND_HALF_UP);
					Object key = null;
					for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
						key = iter.next();
						map.put(key, amount);
					}

					// ���
					amount = amount.add(info.getBalanceAmount().subtract(amount.multiply(BigDecimal.valueOf(rowSet.size()))));
					map.put(key, amount);

				} else {
					BigDecimal sumTemp = FDCHelper.ZERO;
					BigDecimal amount = FDCHelper.ZERO;
					BigDecimal bigAmount = FDCHelper.MIN_DECIMAL;
					Object key = null;
					Object bigKey = null;
					for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
						// ������̯
						key = iter.next();
						amount = (BigDecimal) map.get(key);
						if (amount.compareTo(bigAmount) > 0) {
							bigAmount = amount;
							bigKey = key;
						}
						amount = amount.multiply(info.getBalanceAmount()).divide(sum, 2, BigDecimal.ROUND_HALF_UP);
						map.put(key, amount);
						sumTemp = sumTemp.add(amount);
					}
					// ����
					amount = info.getBalanceAmount().subtract(sumTemp);
					amount = ((BigDecimal) map.get(bigKey)).add(amount);
					map.put(bigKey, amount);
				}
				for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
					builder.clear();
					Object key = iter.next();
					builder.appendSql("update T_CON_ChangeNoSplitEntry set famount=? where fid=?");
					builder.addParam(map.get(key));
					builder.addParam(key);
					builder.execute();
				}

			}
			if (splitId != null) {
				builder.clear();
				builder.appendSql("update T_CON_ConChangeNoCostSplit set famount=? where fid=?");
				builder.addParam(info.getBalanceAmount());
				builder.addParam(splitId);
				builder.execute();
				
				FDCCostSplitAdapter fdcostSplit = new FDCCostSplitAdapter(ctx);
				fdcostSplit.refreshConChange(splitId);
				// �����ֵ����ˢ��������
				/*
				 * //����н�����ˢ�½����� builder.clear(); builder.appendSql("select
				 * split.fid as fid from T_CON_SettlementCostSplit split inner
				 * join T_CON_ContractSettlementBill settle on
				 * split.FSettlementBillID=settle.fid where
				 * settle.FContractBillID=?");
				 * builder.addParam(info.getContractBill().getId().toString());
				 * rowSet= builder.executeQuery(); while(rowSet.next()){
				 * fdcostSplit.refreshSettlement(rowSet.getString("fid")); }
				 */
			}
		} catch (Exception e) {
			throw new BOSException(e);
		}
	}
	
	/* ͨ������һ��map����,ִ��ͨ�õķ���������
	 * @see com.kingdee.eas.fdc.basedata.app.AbstractFDCBillFacadeControllerBean#_execAction(com.kingdee.bos.Context, java.util.Map)
	 */
	protected Object _execAction(Context ctx, Map map) throws BOSException {
		if(map==null){
			throw new NullPointerException();
		}
		if(map.get("action")!=null){
			IFDCAction action=(IFDCAction)map.get("action");
			try {
				return action.actionPerformed(ctx);
			} catch (Exception e) {
				throw new BOSException(e);
			}
			
		}
		
		return null;
	}
	
	protected IObjectValue _autoPropSplit(Context ctx,String type,Map dataMap)throws BOSException,EASBizException{
		if(FDCHelper.isEmpty(dataMap)){
			return null;
		}
		BigDecimal amount=FDCHelper.toBigDecimal(dataMap.get("amount"));
		if(type.equals("contract")){
			String contractId=(String)dataMap.get("contractId");
			if(contractId!=null){
				FDCCostSplit fdccostSplit=new FDCCostSplit(ctx);
				EntityViewInfo view=new EntityViewInfo();
				view.setFilter(new FilterInfo());
				view.getFilter().appendFilterItem("contractBill.id", contractId);
				view.getFilter().getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
				view.getSelector().add("*");
				fdccostSplit.setSelectorsEntry(view.getSelector(), false);
				final ContractCostSplitCollection contractCostSplitCollection = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(view);
				BigDecimal sum=FDCHelper.ZERO;
				
				if(contractCostSplitCollection.size()==1){
					final ContractCostSplitInfo info = contractCostSplitCollection.get(0);
					info.setId(null);
					BigDecimal totalSplitAmt=FDCHelper.toBigDecimal(info.getAmount());
					ContractCostSplitEntryInfo entry=null;
					ContractCostSplitEntryInfo entryMax=new ContractCostSplitEntryInfo();
					for(Iterator iter=info.getEntrys().iterator();iter.hasNext();){
						entry=(ContractCostSplitEntryInfo)iter.next();
						entry.setId(null);
						if(entry.getLevel()==0){
							BigDecimal amt=FDCHelper.toBigDecimal(entry.getAmount()).multiply(amount).divide(totalSplitAmt, BigDecimal.ROUND_HALF_UP);
							sum=sum.add(amt);
							entry.setAmount(amt);
							if(FDCHelper.toBigDecimal(entryMax.getAmount()).subtract(amt).signum()<=0){
								entryMax=entry;
							}
						}
					}
					BigDecimal diff=amount.subtract(sum);
					if(sum.compareTo(amount)!=0&&entry!=null){
						entryMax.setAmount(FDCHelper.toBigDecimal(entry.getAmount()).add(diff));
					}
					fdccostSplit.refreshApportionAmount(info, null);
					return info;
				}
				
				return null;
			}
		}
		return null;
	}

	protected void _setBillAudited4wf(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		IBOSObject object = BOSObjectFactory.createBOSObject(ctx,billId.getType());
		IFDCBill iBillBase = (IFDCBill) object;
		iBillBase.audit(billId);
	}

	protected void _setBillAudited4wf2(Context ctx, BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		IBOSObject object = BOSObjectFactory.createBOSObject(ctx,billId.getType());
		IFDCBill iBillBase = (IFDCBill) object;
		/***
		 * ����ԭ����������������֤�߼��жϵ���������
		 */
		iBillBase.audit(billId);
		/***
		 * Ȼ���ٸ���������
		 */
		FDCBillInfo billInfo = new FDCBillInfo();
		billInfo.setId(billId);
		UserInfo userInfo = new UserInfo();
		userInfo.setId(auditorId);
		billInfo.setAuditor(userInfo);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("auditor");
		iBillBase.updatePartial(billInfo,selector);
	}

	protected void _setBillAuditing4wf2(Context ctx, BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		IBOSObject object = BOSObjectFactory.createBOSObject(ctx,billId.getType());
		IFDCBill iBillBase = (IFDCBill) object;
		/***
		 * ����ԭ����������������֤�߼��жϵ���������
		 */
		iBillBase.setAudittingStatus(billId);
		/***
		 * Ȼ���ٸ���������
		 */
		FDCBillInfo billInfo = new FDCBillInfo();
		billInfo.setId(billId);
		UserInfo userInfo = new UserInfo();
		userInfo.setId(auditorId);
		billInfo.setAuditor(userInfo);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("auditor");
		iBillBase.updatePartial(billInfo,selector);
	}
}