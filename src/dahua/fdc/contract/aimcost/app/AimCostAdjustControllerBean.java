package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostAdjustEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostAdjustInfo;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.util.FdcCodingRuleUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;

/**
 * 目标成本调整单
 * 
 * @author pengwei_hou
 * @date 2009-07-13
 * 
 * @version EAS FDC6.1
 */
public class AimCostAdjustControllerBean extends
		AbstractAimCostAdjustControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.aimcost.app.AimCostAdjustControllerBean");

	/**
	 * 审批时分录同步至待发生成本预测调整分录
	 * 
	 * @param ctx
	 * @param billId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._audit(ctx, billId);
		
		synToDynamicCost(ctx, billId);
	}

	private void synToDynamicCost(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		AimCostAdjustInfo info = this.getAimCostAdjustInfo(ctx,
				new ObjectUuidPK(billId), getSic());
		String prjId = info.getCurProject().getId().toString();
		
		DyCostSplitDataGetter dyGetter;

		TimeTools.getInstance().msValuePrintln("start fetchData");
		//获取当前期间
		PeriodInfo curPeriod = FDCUtils.getCurrentPeriod(ctx,prjId,true);
		//取当前期间数据
		final DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory.getLocalInstance(ctx).getDynamicCost(prjId, curPeriod,true);
		if(dynamicCostMap==null){
			return;
		}
		dyGetter=dynamicCostMap.getDyCostSplitDataGetter();
		TimeTools.getInstance().msValuePrintln("end fetchData");
		
		BOSUuid id = null;
		DynamicCostInfo parent = null;
		AdjustRecordEntryInfo recordInfo = null;
		
		Map dynCostMap = new HashMap();
		int seq = 1;
		for (int i = 0; i < info.getEntrys().size(); i++) {
			AimCostAdjustEntryInfo entry = info.getEntrys().get(i);
			
			String acctId = entry.getCostAccount().getId().toString();
			if(dynCostMap.containsKey(acctId)){
				parent = (DynamicCostInfo)dynCostMap.get(acctId);
			}else{
				dynCostMap.put(acctId, parent);
			}
			parent = dyGetter.getDynamicInfo(acctId);
			if(parent==null){
				parent=new DynamicCostInfo();
				parent.setId(BOSUuid.create(parent.getBOSType()));
				parent.setAccount(entry.getCostAccount());
				parent.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
				parent.setPeriod(curPeriod);
				parent.setCreator(info.getCreator());
				parent.setCreateTime(info.getCreateTime());
				parent.setAuditor(info.getAuditor());
				//Add by zhiyuan_tang 2010/07/12 R100622-111 待发生成本预测的历史记录不能保存
				parent.setAdjustSumAmount(FDCHelper.ZERO);	//金额初始化为0
				dyGetter.updateDynamic(acctId, parent);
			}
			if(info.getId()==null){
				info.setId(BOSUuid.create(info.getBOSType()));
			}
			seq = parent.getAdjustEntrys().size();
			recordInfo = new AdjustRecordEntryInfo();
			id = BOSUuid.create(recordInfo.getBOSType());
			recordInfo.setId(id);
			recordInfo.setParent(parent);
			recordInfo.setAdjustAcctName(entry.getCostAccount().getName());
			recordInfo.setParent(parent);
			recordInfo.setAdjustDate(entry.getAdjustDate());
			if (entry.getAdjuster() != null) {
				recordInfo.setAdjuster(entry.getAdjuster());
				recordInfo.setAdjusterName(entry.getAdjuster().getName());
			}
			recordInfo.setWorkload(entry.getWorkload());
			recordInfo.setUnit(entry.getUnit());
			recordInfo.setPrice(entry.getPrice());
			recordInfo.setCostAmount(entry.getCostAmount());
			recordInfo.setProduct(entry.getProductType());
			recordInfo.setDescription(entry.getDescription());
			recordInfo.setAdjustType(entry.getAdjustType());
			recordInfo.setAdjustReason(entry.getAdjustReason());
			recordInfo.setAdjustEntryId(entry.getId().toString());
			recordInfo.setPeriod(curPeriod);
			recordInfo.setSeq(seq +i);
			
			//Delete by zhiyuan_tang 2010/07/09  	R100622-111 待发生成本预测的历史记录不能保存
			//审批时不删除历史记录
			//parent.getAdjustEntrys().clear();
			AdjustRecordEntryCollection adjustCol = new AdjustRecordEntryCollection();
			adjustCol.add(recordInfo);
			parent.getAdjustEntrys().addCollection(adjustCol);
			//Update by zhiyuan_tang 2010/07/09   R100622-111 待发生成本预测的历史记录不能保存
			//金额在原来的基础上累加
			BigDecimal adjustAmount = FDCHelper.ZERO;
			if( parent.getAdjustSumAmount()!=null){
				adjustAmount = parent.getAdjustSumAmount();
			}

			for (int j = 0; j < adjustCol.size(); j++) {
				AdjustRecordEntryInfo adjustInfo = adjustCol.get(j);
				adjustAmount = adjustAmount.add(adjustInfo.getCostAmount());
				if (adjustInfo.getId() == null) {
					adjustInfo.setId(BOSUuid.create(adjustInfo.getBOSType()));
				}
				
			}
			parent.setAdjustSumAmount(adjustAmount);
			DynamicCostFactory.getLocalInstance(ctx).submit(parent);
		}
		
	}
	private void unSynToDynamicCost(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		AimCostAdjustInfo info = this.getAimCostAdjustInfo(ctx,
				new ObjectUuidPK(billId), getSic());
		String prjId = info.getCurProject().getId().toString();

		DyCostSplitDataGetter dyGetter;

		TimeTools.getInstance().msValuePrintln("start fetchData");
		// 获取当前期间
		PeriodInfo curPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
		// 取当前期间数据
		final DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory
				.getLocalInstance(ctx).getDynamicCost(prjId, curPeriod, true);
		if (dynamicCostMap == null) {
			return;
		}
		dyGetter = dynamicCostMap.getDyCostSplitDataGetter();
		TimeTools.getInstance().msValuePrintln("end fetchData");

		DynamicCostInfo parent = null;
		for (int i = 0; i < info.getEntrys().size(); i++) {
			AimCostAdjustEntryInfo entry = info.getEntrys().get(i);

			String acctId = entry.getCostAccount().getId().toString();
			parent = dyGetter.getDynamicInfo(acctId);

			AdjustRecordEntryCollection adjustCol = parent.getAdjustEntrys();
			BigDecimal adjustAmount = FDCHelper.toBigDecimal(parent.getAdjustSumAmount());
			for (int j = 0; j < adjustCol.size(); j++) {
				AdjustRecordEntryInfo adjustInfo = adjustCol.get(j);
				//Add by zhiyuan_tang 2010/07/12 	R100622-111 待发生成本预测的历史记录不能保存
				//添加Null值判断
				if (adjustInfo.getAdjustEntryId() != null) {
					if(entry.getId().toString().equals(adjustInfo.getAdjustEntryId().toString())){
						adjustAmount = adjustAmount.subtract(adjustInfo.getCostAmount());
						adjustCol.remove(adjustInfo);
					}
				}

			}
			//Delete by zhiyuan_tang 2010/07/12 	R100622-111 待发生成本预测的历史记录不能保存
			//反审批时不清空明细记录
			//parent.getAdjustEntrys().clear();
			parent.getAdjustEntrys().addCollection(adjustCol);
			parent.setAdjustSumAmount(adjustAmount);
			DynamicCostFactory.getLocalInstance(ctx).submit(parent);
		}

	}
	
	/**
	 * 反审批时将待发生成本预测生成的分录删除
	 * 
	 * @param ctx
	 * @param billId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._unAudit(ctx, billId);
		unSynToDynamicCost(ctx,billId);
	}

	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		// 没有name，不需要校验
	}

	private SelectorItemCollection getSic() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("entrys.*");
		sic.add("entrys.costAccount.id");
		sic.add("entrys.costAccount.name");
		sic.add("entrys.adjuster.name");
		sic.add("entrys.adjuster.id");
		return sic;
	}
	
	
	// ////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////

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
		sic.add(new SelectorItemInfo("curProject.*"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.*"));

		FdcObjectCollectionUtil.clearDuplicate(sic);

		return sic;
	}

	// ////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////

	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException,
			EASBizException {

		FilterInfo filter = null;
		int i = 0;
		do {
			handleIntermitNumber1(ctx, info, i);

			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", info.getNumber()));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID, CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", info.getOrgUnit().getId()));
			if (info.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
			}
			i++;
		} while (_exists(ctx, filter) && i < 100);
	}

	protected void handleIntermitNumber1(Context ctx, FDCBillInfo info, int count) throws BOSException,
			CodingRuleException, EASBizException {
		String currentOrgId = getOrgUnitId(ctx, info);
		if (FdcStringUtil.isBlank(currentOrgId)) {
			return;
		}

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

		String bindingProperty = getBindingProperty();
		String orgId = null;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		// 编码规则获取要支持组织递归。例如：首先要找当前组织，如果没有找到，则要能找到上级组织，如果还没有找到，则要找上上级组织，依次类推
		for (int j = 0; j < orgIdList.size(); j++) {
			orgId = orgIdList.get(j).toString();
			if (setNumber(ctx, info, orgId, bindingProperty, iCodingRuleManager, count)) {
				// 如果成功匹配到编码规则，则终止循环，直接返回（合同编码类型为最后一次设置的临时值）
				return;
			}
		}
	}

	/**
	 * 描述：匹配编码规则，获取编码，设置编码
	 * 
	 * @param ctx
	 * @param info
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
	protected boolean setNumber(Context ctx, FDCBillInfo info, String orgId, String bindingProperty,
			ICodingRuleManager iCodingRuleManager, int count) throws BOSException, EASBizException {

		if (FdcCodingRuleUtil.isExist(iCodingRuleManager, info, orgId, bindingProperty)) {
			// 获取编码规则对象
			CodingRuleInfo codingRuleInfo = FdcCodingRuleUtil.getCodingRule(iCodingRuleManager, info, orgId,
					bindingProperty);
			// 如果支持修改就要检查编码是否符合当前启用的编码规则的规则

			// 获取编码规则是否支持修改
			boolean flag = FdcCodingRuleUtil.isAllowModifyNumber(iCodingRuleManager, info, orgId, bindingProperty);
			// 判断是否修改了编码,是否改大了顺序号
			if (flag) {
				iCodingRuleManager.checkModifiedNumber(info, orgId, info.getNumber());
			} else {
				// 如果是新增显示
				if (codingRuleInfo.isIsAddView()) {
					// 编码为空的时候要获取编码
					if (FdcStringUtil.isNotBlank(info.getNumber())) {
						String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId, bindingProperty);
						info.setNumber(number);
					} else {
						// 当编码不为空的时候，count=0的时候不需要重新获取编码，当编码>0的时候证明编码重复要重新获取新的编码
						if (count > 0) {
							String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId,
									bindingProperty);
							info.setNumber(number);
						}
					}
				} else {
					// 否则就是不允许断号，每次都要重现获取
					String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId, bindingProperty);
					info.setNumber(number);
				}
			}

			return true;
		}

		return false;
	}

	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		logger.info("===============================================================================");
		logger.info("recycleNumber(),start");
		logger.info("===============================================================================");

		SelectorItemCollection sic = getSeletorForRecycleNumber();
		FDCBillInfo info = (FDCBillInfo) _getValue(ctx, pk, sic);
		String currentOrgId = getOrgUnitId(ctx, info);

		logger.info("sic:" + sic);
		logger.info("info:" + info);
		logger.info("info.number:" + info.getNumber());

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

		String bindingProperty = getBindingProperty();
		String curOrgId = null;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		for (int i = 0, size = orgIdList.size(); i < size; i++) {
			curOrgId = orgIdList.get(i).toString();
			logger.info("回收组织为：" + curOrgId + "的编码规则。");

			boolean flag = FdcCodingRuleUtil.isRecycleNumber(iCodingRuleManager, info, curOrgId, bindingProperty);
			if (flag) {
				// 如果回收成功了就跳出循环
				if (FdcCodingRuleUtil.recycleNumber(iCodingRuleManager, info, curOrgId, bindingProperty, "", info
						.getNumber())) {
					break;
				}
			}
		}

		logger.info("===============================================================================");
		logger.info("recycleNumber(),end");
		logger.info("===============================================================================");
	}

	// ////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////
}