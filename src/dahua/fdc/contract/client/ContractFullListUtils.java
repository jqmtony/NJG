package com.kingdee.eas.fdc.contract.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * @author Cassiel_peng
 * @date 2009-11-24  
 * @work 该类经过一次性能优化又处理了R090927-138提单已经变得过分庞大了，现抽取出部分逻辑封装到该类中
 */
public class ContractFullListUtils {
	
	public ContractFullListUtils() {
		
	}
	/**
	 * 是否启用成本财务一体化
	 */
	private static boolean isSimpleFinacialMode() {
//			if(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL.toString())!=null){
//				isFinacial = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL).toString()).booleanValue();
//			}
//	       return isFinacial;
		boolean isSimpleFinacialMode=true;
		CompanyOrgUnitInfo	company =  SysContext.getSysContext().getCurrentFIUnit();
		try {
			isSimpleFinacialMode=FDCUtils.getDefaultFDCParamByKey(null, company.getId().toString(),FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		} catch (EASBizException e) {
			e.printStackTrace();
			SysUtil.abort();
		} catch (BOSException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		return isSimpleFinacialMode;
	}
	/**
	 * 是否启用成本财务一体化
	 */
	private static boolean isFinacialModel() {
		boolean isFinacial=false;
		CompanyOrgUnitInfo	company =  SysContext.getSysContext().getCurrentFIUnit();
		try {
			isFinacial = FDCUtils.IsFinacial(null, company.getId().toString());
		} catch (EASBizException e) {
			e.printStackTrace();
			SysUtil.abort();
		} catch (BOSException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		return isFinacial;
	}
	 /**
	 * 合同拆分:需要保留合同拆分序时簿界面点击拆分按钮所有的逻辑，"量价拆分"作为特殊情况极少数客户使用故不在考虑之列
	 * 1、成本月结已经引用本数据，不能修改，如需修改，请先作废本合同拆分！
	 * 2、拆分已经被结算拆分引用，不能进行此操作!
	 * 3、拆分已经被工程量拆分或付款拆分引用，不能进行此操作!
	 * 4、非复杂模式情况下，合同拆分被付款拆分引用，拆分需要更改时，提示引用，可由用户选择是否直接删除后续拆分
	 *  by Cassiel_peng  2009-11-17
	 */
	public static boolean  verifyCanSplit(KDTable tblMain,boolean isCostSplit) throws EASBizException, BOSException {
		
		boolean canSplit=true;
		
		Object billId=tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		FilterInfo filtertemp = new FilterInfo();
		filtertemp.getFilterItems().add(new FilterItemInfo("costBillId", billId));
		// 作废单据不算
		filtertemp.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		
		boolean existSettlecoll = false;
		boolean existcollPay = false;
		boolean existcollNoCost = false;
		boolean existcollPayNoCost = false;
		if(isCostSplit){//成本类合同拆分
			existSettlecoll = SettlementCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			existcollPay = PaymentSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			if((existSettlecoll||existcollPay)){		// 成本拆分：结算/工程量/付款拆分被引用  
				if(isSimpleFinacialMode()){
					int value = MsgBox.showConfirm2("合同拆分被后续拆分引用，是否直接删除后续拆分");
					if(value == MsgBox.YES){
						if(existSettlecoll){
							filtertemp = new FilterInfo();
							filtertemp.getFilterItems().add(
									new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));
							SettlementCostSplitFactory.getRemoteInstance().delete(filtertemp);
						}
						if(existcollPay){
							filtertemp = new FilterInfo();
							filtertemp.getFilterItems().add(
									new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));					
							PaymentSplitFactory.getRemoteInstance().delete(filtertemp);
						}
					}else{
						canSplit=false;
						SysUtil.abort();
					}
				}else{
					if (existSettlecoll) {			//被结算拆分引用
						canSplit=false;
						MsgBox.showError(FDCSplitClientHelper.getRes("impBySett"));
						SysUtil.abort();
					}
					if (existcollPay) {				//拆分被工程量拆分或付款拆分引用
						canSplit=false;
						MsgBox.showError(FDCSplitClientHelper.getRes("impByPay"));
						SysUtil.abort();
					}
				}
			}
		}else{//非成本类合同拆分
			existcollNoCost = SettNoCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			existcollPayNoCost = PaymentNoCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			if((existcollNoCost||existcollPayNoCost)){		//非成本拆分：结算/工程量/付款拆分被引用
				if(!isFinacialModel()){
					int value = MsgBox.showConfirm2("合同拆分被后续拆分引用，是否直接删除后续拆分");
					if(value == MsgBox.YES){
						if(existcollNoCost){
							filtertemp = new FilterInfo();
							filtertemp.getFilterItems().add(
									new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));
							SettNoCostSplitFactory.getRemoteInstance().delete(filtertemp);
						}
						if(existcollPayNoCost){
							filtertemp = new FilterInfo();
							filtertemp.getFilterItems().add(
									new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));
							PaymentNoCostSplitFactory.getRemoteInstance().delete(filtertemp);
						}
					}else{
						canSplit=false;
						SysUtil.abort();
					}
				}else{
					if (existcollNoCost) {			//被结算拆分引用
						canSplit=false;
						MsgBox.showError(FDCSplitClientHelper.getRes("impBySett"));
						SysUtil.abort();
					}
					if (existcollPayNoCost) {				//拆分被工程量拆分或付款拆分引用
						canSplit=false;
						MsgBox.showError(FDCSplitClientHelper.getRes("impByPay"));
						SysUtil.abort();
					}
				}
			}
		}
		return canSplit;
	}
	/**
	 * 判断合同拆分是成本类拆分还是非成本类拆分
	 *   by Cassiel_peng  2009-11-17
	 */
	public static boolean isCostSplit(KDTable tblMain) throws EASBizException, BOSException, UuidException{
		boolean isCostSplit=true;
		//对于合同拆分需区分是成本类拆分还是非成本类拆分
		Object contractId=tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("isCoseSplit");
		ContractBillInfo contractBill=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId==null?"":contractId.toString())),selector);
		if(!contractBill.isIsCoseSplit()){
			isCostSplit=false;
		}
		return isCostSplit;
	}
	/**
	 * 判断是否有某一个权限
	 */
	public static boolean verifyPermission(String UI,String action_permission,String currentUserInfoId,String currentOrgUnitId) throws EASBizException, BOSException {
		boolean hasPermission=false;
		hasPermission=PermissionFactory.getRemoteInstance().hasFunctionPermission(
				new ObjectUuidPK(currentUserInfoId),
				new ObjectUuidPK(currentOrgUnitId),
				new MetaDataPK(UI),
				new MetaDataPK(action_permission) );
		return hasPermission;
	}
	/**
	 * 若合同还未审批，则按钮“合同拆分”、“新增结算单”、 “新增付款申请单”、“付款单序事簿”都不可用
	 *  by Cassiel_peng 2009-11-18
	 */
	public static void verifyAudited(KDTable tblMain) throws EASBizException, BOSException, UuidException {
		/*// TODO 直接这样判断的话可能会存在合同已经审批，数据库中状态字段已经改变，但是合同查询界面每刷新过数据状态字段还没变化的情况
		Object state=tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("state").getValue();
		if(state!=null&&("保存".equals(state.toString())||"已提交".equals(state.toString())||"审批中".equals(state.toString()))){
			FDCMsgBox.showError(this,"该合同尚未审批，不允许进行该操作！");
			SysUtil.abort();
		}*/
		if(tblMain.getRowCount()==0){
			FDCMsgBox.showWarning("请先选择一行单据！");
			SysUtil.abort();
		};
		Object contractId=tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("state");
		ContractBillInfo contractBill=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId==null?"":contractId.toString())));
		if(contractBill!=null&&contractBill.getState()!=null&&(FDCBillStateEnum.SAVED.equals(contractBill.getState())||FDCBillStateEnum.SUBMITTED.equals(contractBill.getState())||FDCBillStateEnum.AUDITTING.equals(contractBill.getState()))){
			FDCMsgBox.showError("该合同尚未审批，不允许进行该操作！");
			SysUtil.abort();
		}
	
	}
	/**
	 * 若合同已结算，则按钮 “新增变更签证申请” 、“新增结算单”都不可用
	 *  by Cassiel_peng 2009-11-18
	 */
	public static void verifyHasSettled(KDTable tblMain) throws EASBizException, BOSException, UuidException {
		if(tblMain.getRowCount()==0){
			FDCMsgBox.showWarning("请先选择一行单据！");
			SysUtil.abort();
		};
		Object contractId=tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("hasSettled");
		ContractBillInfo contractBill=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId==null?"":contractId.toString())));
		if(contractBill.isHasSettled()){
			FDCMsgBox.showError("该合同已经结算，不能进行此操作！");
			SysUtil.abort();
		}
	}
	
}
