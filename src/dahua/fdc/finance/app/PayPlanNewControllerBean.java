package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.finance.AbstractPayPlanNewInfo;
import com.kingdee.eas.fdc.finance.CalStandardEnum;
import com.kingdee.eas.fdc.finance.CalTypeEnum;
import com.kingdee.eas.fdc.finance.ConPayPlanFactory;
import com.kingdee.eas.fdc.finance.PayPlanModeEnum;
import com.kingdee.eas.fdc.finance.PayPlanNewByMonthCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByMonthInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleDatazCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleDatazInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskNameCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskNameInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewDataCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewInfo;
import com.kingdee.eas.fdc.finance.PrepayWriteOffEnum;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

/**
 * 描述：付款规划
 * 
 * 
 */
public class PayPlanNewControllerBean extends AbstractPayPlanNewControllerBean {

	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.finance.app.PayPlanNewControllerBean");

	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		PayPlanNewInfo info = (PayPlanNewInfo) model;
		info = _caculate(ctx, info);
		IObjectPK objectPK = super._addnew(ctx, info);
		/**
		 * 更新合约规划是否编制付款计划状态
		 */
		updateProgrammingAndConPlan(info, ctx);
		
		return objectPK;
		
	}
	private void updateProgrammingAndConPlan(PayPlanNewInfo info, Context ctx) throws EASBizException, BOSException {
		/**
		 * 更新合约规划是否关联付款计划状态
		 */
		BigDecimal payScale =FDCHelper.ZERO;
		for(int i=0;i<info.getBySchedule().size();i++){
			PayPlanNewByScheduleInfo byScheduleInfo = info.getBySchedule().get(i);
			if(!byScheduleInfo.getPaymentType().isPreType()){
				payScale = FDCHelper.add(byScheduleInfo.getPayScale(), payScale);
			}
		}
		ProgrammingContractInfo programming = info.getProgramming();
		if(FDCHelper.compareTo(payScale, FDCHelper.ONE_HUNDRED)==0){
			programming.setIsHasPlan(true);
		}else{
			programming.setIsHasPlan(false);
		}
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("isHasPlan");
		ProgrammingContractFactory.getLocalInstance(ctx).updatePartial(programming, selector);
		
		
		
		/**
		 * 更新合同付款计划
		 */
		if(info.get("updateCon")!=null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",info.getProgramming().getId()));
			if(ContractBillFactory.getLocalInstance(ctx).exists(filter)){
				
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				SelectorItemCollection coll = new SelectorItemCollection();
				coll.add("id");
				view.setSelector(coll );
				ContractBillCollection contractBillCollection = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view );
				for(int i=0;i<contractBillCollection.size();i++){
					ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(contractBillCollection.get(i).getId().toString(),false);
				}
			}
		}
		
	}

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		PayPlanNewInfo info = (PayPlanNewInfo) model;
		info = _caculate(ctx, info);
		super._update(ctx, pk, info);
		/**
		 * 更新合约规划是否编制付款计划状态
		 */
		updateProgrammingAndConPlan(info, ctx);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		PayPlanNewInfo info = (PayPlanNewInfo) model;
		_checkForCalculate(ctx, info);
		return super._submit(ctx, model);
	}

	protected boolean _checkCanCalculate(Context ctx, PayPlanNewInfo info) throws BOSException, EASBizException {
		if (info == null) {
			return false;
		}

		if (PayPlanModeEnum.BYSCHEDULE.equals(info.getMode())) {
			return _checkCanBySchedule(ctx, info);
		} else {
			return false;
		}
	};

	private boolean _checkCanBySchedule(Context ctx, PayPlanNewInfo info) throws EASBizException {

		PayPlanNewByScheduleCollection bySchedule = info.getBySchedule();
		for (int i = 0; i < bySchedule.size(); i++) {
			PayPlanNewByScheduleInfo byScheduleInfo = bySchedule.get(i);

			if (byScheduleInfo.getPaymentType() == null) {
				return false;
			}


			if (byScheduleInfo.getCalStandard() == null) {
				return false;
			}

			if (CalStandardEnum.SCHEDULEAMOUNT.equals(byScheduleInfo.getCalStandard()) && byScheduleInfo.getCostAccount() == null) {
				return false;
			}

			if (byScheduleInfo.getCalType() == null) {
				return false;
			}

			if (byScheduleInfo.getPayScale() == null) {
				return false;
			}

		}
		return true;

	}


	protected void _checkForCalculate(Context ctx, PayPlanNewInfo info) throws BOSException, EASBizException {
		if (info == null) {
			return;
		}
		if (PayPlanModeEnum.BYSCHEDULE.equals(info.getMode())) {
			//_checkBySchedule(ctx, info);
		} else {
			throw new EASBizException(new NumericExceptionSubItem("0001", "付款规划没有指定通用合同还是零星合同！"));
		}
	};

	protected void _checkBySchedule(Context ctx, PayPlanNewInfo info) throws EASBizException {
		PayPlanNewByScheduleCollection bySchedule = info.getBySchedule();
		String name = info.getProgramming().getName();
		BigDecimal sum = FDCHelper.ZERO;
		for (int i = 0; i < bySchedule.size(); i++) {
			PayPlanNewByScheduleInfo byScheduleInfo = bySchedule.get(i);

			if (byScheduleInfo.getPaymentType() == null) {
				throw new EASBizException(new NumericExceptionSubItem("0002", "合约规划" + name + "的合同付款规划的付款类型不能为空！"));
			}

			//			if (byScheduleInfo.getTaskName() == null || byScheduleInfo.getTaskName().size() < 1) {
			//				throw new EASBizException(new NumericExceptionSubItem("0002", "合约规划" + name + "的通用合同付款规划的计划支付节点名称不能为空！"));
			//			}

//			for (int j = 0; j < byScheduleInfo.getTaskName().size(); j++) {
//				PayPlanNewByScheduleTaskNameInfo taskNameInfo = byScheduleInfo.getTaskName().get(j);
//				if (StringUtils.isEmpty(taskNameInfo.getName())) {
//					throw new EASBizException(new NumericExceptionSubItem("0002", "合约规划" + name + "的通用合同付款规划的计划支付节点名称不能为空！"));
//				}
//			}

			//			if (byScheduleInfo.getTask() == null || byScheduleInfo.getTask().size() < 1) {
			//				throw new EASBizException(new NumericExceptionSubItem("0002", "合约规划" + name + "的通用合同付款规划的计划支付节点名称对应的节点不能为空！"));
			//			}

			if (byScheduleInfo.getCalStandard() == null) {
				throw new EASBizException(new NumericExceptionSubItem("0002", "合约规划" + name + "的合同付款规划的计算基准不能为空！"));
			}


			if (byScheduleInfo.getCalType() == null) {
				throw new EASBizException(new NumericExceptionSubItem("0002", "合约规划" + name + "的合同付款规划的计算方式不能为空！"));
			}

			if (byScheduleInfo.getPayScale() == null) {
				throw new EASBizException(new NumericExceptionSubItem("0002", "合约规划" + name + "的合同付款规划的预定支付比例不能为空！"));
			}
			
			if (byScheduleInfo.getTask() == null && byScheduleInfo.getPlanPayDate()==null) {
				throw new EASBizException(new NumericExceptionSubItem("0002", "合约规划" + name + "的合同付款规划的计划支付日期不能为空！"));
			}

			if (!byScheduleInfo.getPaymentType().isPreType()) {
				sum = FDCHelper.add(sum, byScheduleInfo.getPlanPayAmount());
			}
		}

		ProgrammingContractInfo pInfo = null;
		if (info.containsKey("uiInfo")) {
			pInfo = (ProgrammingContractInfo) info.get("uiInfo");
		} else {
			pInfo = info.getProgramming();
		}

	}


	protected PayPlanNewInfo _caculate(Context ctx, PayPlanNewInfo info) throws BOSException, EASBizException {
		if (info == null) {
			return null;
		} 

		if (!_checkCanCalculate(ctx, info)) {
			return info;
		}
		// modify by yxl
//		if (PayPlanModeEnum.BYSCHEDULE.equals(info.getMode())) {
//			info = _caculateBySchedule(ctx, info);
//		}

		return info;
	}


	private void calOneByMonthInfo(Map resultMap, PayPlanNewByMonthInfo mInfo) {
		Date beginDate = mInfo.getBeginDate();
		Date endDate = mInfo.getEndDate();

		if (beginDate == null || endDate == null) {
			return;
		}

		Map monthMap = new HashMap();

		//		重复计算的模式，先注释，后续标准产品能使用这种算法
		//		Calendar calendar = Calendar.getInstance();
		//		Integer totalDays = new Integer(DateHelper.getDiffDays(beginDate, endDate));
		//		calDateMap(monthMap, beginDate, endDate);
		//金地使用的是不能重复计算模式
		Map dateMap = new HashMap();
		calDateMapNotRepeat(dateMap, beginDate, endDate);
		calMonthMap(monthMap, dateMap);
		Integer totalDays = new Integer(dateMap.size());

		Iterator it = monthMap.keySet().iterator();
		BigDecimal totalAmount = mInfo.getPayAmount();
		while (it.hasNext()) {
			Object key = it.next();

			BigDecimal diffDays = (BigDecimal) monthMap.get(key);
			// 按天数比例摊分金额 amount = totalAmount * （diffDays / totalDays）
			BigDecimal amount = FDCHelper.divide(FDCHelper.multiply(totalAmount, diffDays), totalDays);
			// 加到结果集里面去
			resultMap.put(key, FDCHelper.add(amount, resultMap.get(key)));
		}

	}

	/**
	 * 按进度支付
	 * 
	 * @param ctx
	 * @param info
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected PayPlanNewInfo _caculateBySchedule(Context ctx, PayPlanNewInfo info) throws BOSException, EASBizException {
		if (info == null) {
			return null;
		}

		//初始化金额
		initPlanPayAmount(ctx, info);

		//		initFDCScheTaskByName(ctx, info);


 

		initBeginEndDate(ctx, info);

		PayPlanNewByScheduleCollection bySchedule = info.getBySchedule();

//		PayPlanNewByScheduleCollection bySchBuilding = new PayPlanNewByScheduleCollection();

		for (int i = 0; i < bySchedule.size(); i++) {
			PayPlanNewByScheduleInfo scheduleInfo = bySchedule.get(i);

			// 按时间点支付
			if (CalTypeEnum.TIME.equals(scheduleInfo.getCalType())) {
				caculateByTime(ctx, scheduleInfo);
			}

			// 按时间段支付,并且按月度摊分
			else if (CalTypeEnum.TIMERANGE.equals(scheduleInfo.getCalType())) {
				_caculateByTimeRange(ctx, info, scheduleInfo);
			}
		}


		//汇总数据
		sumData(ctx, info);

		return info;
	}


	private void initBeginEndDate(Context ctx, PayPlanNewInfo info) throws BOSException {
		//1.准备数据
		PayPlanNewByScheduleCollection bySchedule = info.getBySchedule();
		Set taskIdSet = new HashSet();
		for (int i = 0; i < bySchedule.size(); i++) {
			PayPlanNewByScheduleInfo sInfo = bySchedule.get(i);

			PayPlanNewByScheduleTaskCollection task = sInfo.getTask();
			if (task == null || task.size() == 0) {
				continue;
			}

			for (int j = 0; task != null && j < task.size(); j++) {
				PayPlanNewByScheduleTaskInfo tInfo = task.get(j);
				taskIdSet.add(tInfo.getTask().getId().toString());
			}
		}
		if (taskIdSet.size() == 0) {
			return;
		}
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", taskIdSet, CompareType.INCLUDE));
		evi.setFilter(filter);

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("start");
		sic.add("end");
		sic.add("name");
		evi.setSelector(sic);
		FDCScheduleTaskCollection taskCollection = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(evi);

		Map map = new HashMap();
		for (int i = 0; i < taskCollection.size(); i++) {
			FDCScheduleTaskInfo taskInfo = taskCollection.get(i);
			map.put(taskInfo.getId().toString(), taskInfo);
		}

		for (int i = 0; i < bySchedule.size(); i++) {
			PayPlanNewByScheduleInfo sInfo = bySchedule.get(i);

			PayPlanNewByScheduleTaskCollection task = sInfo.getTask();
			if (sInfo.getTask() == null || sInfo.getTask().size() == 0) {
				continue;
			}
			if (task.size() > 1) {
				sInfo.setBeginDate(null);
				sInfo.setEndDate(null);
			} else if (task.size() == 1) {
				PayPlanNewByScheduleTaskInfo tInfo = task.get(0);
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) map.get(tInfo.getTask().getId().toString());
				if (taskInfo != null) {
					sInfo.setBeginDate(taskInfo.getStart());
					sInfo.setEndDate(taskInfo.getEnd());
					tInfo.setTask(taskInfo);
				}
			}
		}

	}

	/**
	 * 描述：初始化楼栋数据
	 * @param ctx
	 * @param info
	 * @param taskBuildingMap
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void initPlanPayAmount(Context ctx, PayPlanNewInfo info) throws EASBizException, BOSException {

		ProgrammingContractInfo pInfo = null;
		if (info.containsKey("uiInfo")) {
			pInfo = (ProgrammingContractInfo) info.get("uiInfo");
		} else {
			pInfo = info.getProgramming();
		}

		PayPlanNewByScheduleCollection bySchedule = info.getBySchedule();

		BigDecimal totalPayPlan = FDCHelper.ZERO;
		for (int i = 0; i < bySchedule.size(); i++) {
			PayPlanNewByScheduleInfo byScheduleInfo = bySchedule.get(i);
			//按节点金额
				if(i==bySchedule.size() -1 ){
					byScheduleInfo.setPlanPayAmount(FDCHelper.subtract(pInfo.getAmount(), totalPayPlan));
				}else{
					BigDecimal planPayAmount = FDCHelper.divide(FDCHelper.multiply(pInfo.getAmount(), byScheduleInfo.getPayScale()),
							FDCHelper.ONE_HUNDRED);
					byScheduleInfo.setPlanPayAmount(planPayAmount);
					if(!byScheduleInfo.getPaymentType().isPreType()){
						totalPayPlan=FDCHelper.add(totalPayPlan, planPayAmount);
					}
				}
		}
	}


	private SelectorItemCollection getProgrammingContractSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("costEntries.*"));
		sic.add(new SelectorItemInfo("costEntries.costAccount.*"));
		sic.add(new SelectorItemInfo("costEntries.costAccount.curProject.*"));
		sic.add(new SelectorItemInfo("costEntries.buildings.building.id"));
		sic.add(new SelectorItemInfo("costEntries.buildings.building.name"));
		sic.add(new SelectorItemInfo("costEntries.buildings.building.number"));
		return sic;
	}




	//汇总最终结果到Data分录
	protected void sumData(Context ctx, PayPlanNewInfo info) {
		Map dataMap = new TreeMap();

		PayPlanNewByScheduleCollection bySchedule = info.getBySchedule();
		PayPlanNewByScheduleCollection bySchPrepay = new PayPlanNewByScheduleCollection();
		PayPlanNewByScheduleCollection byOthers = new PayPlanNewByScheduleCollection();
		BigDecimal sum = FDCHelper.ZERO;
		for (int i = 0; i < bySchedule.size(); i++) {
			PayPlanNewByScheduleInfo sInfo = bySchedule.get(i);

			//预付款先不加
			if (sInfo.getPaymentType().isPreType()) {
				bySchPrepay.add(sInfo);
				continue;
			}

			//非进度款的先不加
			if (!sInfo.getPaymentType().isScheuleType()) {
				byOthers.add(sInfo);
				continue;
			}

			sum = FDCHelper.add(sum, sInfo.getPlanPayAmount());

			PayPlanNewByScheduleDatazCollection dataz = sInfo.getDataz();

			for (int j = 0; dataz != null && j < dataz.size(); j++) {
				PayPlanNewByScheduleDatazInfo datazInfo = dataz.get(j);

				Integer key = new Integer(datazInfo.getPayMonth());
				dataMap.put(key, FDCHelper.add(dataMap.get(key), datazInfo.getPayAmount()));
			}
		}



		//预付款抵消
		if (bySchPrepay != null && bySchPrepay.size() > 0) {
			BigDecimal onceAmount = FDCHelper.ZERO;
			BigDecimal batchAmount = FDCHelper.ZERO;
			for (int i = 0; i < bySchPrepay.size(); i++) {
				PayPlanNewByScheduleInfo byScheduleInfo = bySchPrepay.get(i);
				if (PrepayWriteOffEnum.ONCE.equals(byScheduleInfo.getWriteOffType())) {
					onceAmount = FDCHelper.add(onceAmount, byScheduleInfo.getPlanPayAmount());
				} else if (PrepayWriteOffEnum.BATCH.equals(byScheduleInfo.getWriteOffType())) {
					batchAmount = FDCHelper.add(batchAmount, byScheduleInfo.getPlanPayAmount());
				}
			}
			List monthKeyLst = new ArrayList(dataMap.keySet());
			//排序
			Collections.sort(monthKeyLst, new Comparator() {

				public int compare(Object o1, Object o2) {
					Integer d1 = (Integer) o1;
					Integer d2 = (Integer) o2;
					return d2.intValue() - d1.intValue();
				}
			});

			//一次性抵消
			if (!FDCHelper.isZero(onceAmount)) {
				BigDecimal balance = onceAmount;
				for (int i = monthKeyLst.size() - 1; i >= 0; i--) {
					Integer d = (Integer) monthKeyLst.get(i);
					balance = FDCHelper.subtract(balance, dataMap.get(d));
					//未抵消完
					if (FDCHelper.compareTo(balance, FDCHelper.ZERO) > 0) {
						dataMap.remove(d);
						monthKeyLst.remove(i);
					}
					//已经抵消完毕
					else {
						dataMap.put(d, balance.negate());
						break;
					}
				}
			}
			
			BigDecimal sumBat = FDCHelper.ZERO;
			//分批抵消
			if (!FDCHelper.isZero(batchAmount)) {
				BigDecimal avg = FDCHelper.divide(batchAmount, monthKeyLst.size());
				for (int i = monthKeyLst.size() - 1; i >= 0; i--) {
					Integer d = (Integer) monthKeyLst.get(i);
					BigDecimal amount = FDCHelper.ZERO;
					if(i==0){
						amount =FDCHelper.subtract(dataMap.get(d), FDCHelper.subtract(batchAmount, sumBat));
					}else{
						amount =FDCHelper.subtract(dataMap.get(d), avg);
					}
					sumBat = FDCHelper.add(sumBat, avg);
					dataMap.put(d, amount);

				}
			}
			
			//处理负数的月份，往后一个月借款抵消负数
			for (int i = monthKeyLst.size() - 1; i > 0; i--) {
				Integer d = (Integer) monthKeyLst.get(i);
				if (FDCHelper.compareTo(dataMap.get(d), FDCHelper.ZERO) < 0) {
					Integer dNext = (Integer) monthKeyLst.get(i - 1);
					dataMap.put(dNext, FDCHelper.add(dataMap.get(d), dataMap.get(dNext)));
					dataMap.put(d, FDCHelper.ZERO);
				}
			}

			for (int i = 0; i < bySchedule.size(); i++) {
				PayPlanNewByScheduleInfo sInfo = bySchedule.get(i);

				//预付款
				if (sInfo.getPaymentType().isPreType()) {
					PayPlanNewByScheduleDatazCollection dataz = sInfo.getDataz();

					for (int j = 0; dataz != null && j < dataz.size(); j++) {
						PayPlanNewByScheduleDatazInfo datazInfo = dataz.get(j);

						Integer key = new Integer(datazInfo.getPayMonth());
						dataMap.put(key, FDCHelper.add(dataMap.get(key), datazInfo.getPayAmount()));
					}
				}
			}
		}

		//添加其他款项
		if (byOthers != null && byOthers.size() > 0) {
			for (int i = 0; i < byOthers.size(); i++) {
				PayPlanNewByScheduleInfo sInfo = byOthers.get(i);

				sum = FDCHelper.add(sum, sInfo.getPlanPayAmount());

				PayPlanNewByScheduleDatazCollection dataz = sInfo.getDataz();

				for (int j = 0; dataz != null && j < dataz.size(); j++) {
					PayPlanNewByScheduleDatazInfo datazInfo = dataz.get(j);

					Integer key = new Integer(datazInfo.getPayMonth());
					dataMap.put(key, FDCHelper.add(dataMap.get(key), datazInfo.getPayAmount()));
				}
			}
		}

		List monthKeyLst = new ArrayList(dataMap.keySet());
		//排序
		Collections.sort(monthKeyLst, new Comparator() {

			public int compare(Object o1, Object o2) {
				Integer d1 = (Integer) o1;
				Integer d2 = (Integer) o2;
				return d2.intValue() - d1.intValue();
			}
		});

		//处理负数的月份，往后一个月借款抵消负数
		for (int i = monthKeyLst.size() - 1; i > 0; i--) {
			Integer d = (Integer) monthKeyLst.get(i);
			if (FDCHelper.compareTo(dataMap.get(d), FDCHelper.ZERO) < 0) {
				Integer dNext = (Integer) monthKeyLst.get(i - 1);
				dataMap.put(dNext, FDCHelper.add(dataMap.get(d), dataMap.get(dNext)));
				dataMap.put(d, FDCHelper.ZERO);
			}
		}
		//若最后一个月为负数，往前面的月份借款
		if (monthKeyLst.size() > 0) {
			Object lastKey = monthKeyLst.get(0);
			if (FDCHelper.compareTo(dataMap.get(lastKey), FDCHelper.ZERO) < 0) {
				BigDecimal balance = (BigDecimal) dataMap.get(lastKey);
				for (int i = 1; i < monthKeyLst.size() - 1; i++) {
					Integer d = (Integer) monthKeyLst.get(i);

					if (FDCHelper.compareTo(dataMap.get(d), FDCHelper.ZERO) > 0) {
						BigDecimal amount = FDCHelper.add(dataMap.get(d), balance);
						//可以抵消完
						if (FDCHelper.compareTo(amount, FDCHelper.ZERO) > 0) {
							dataMap.put(d, amount);
							dataMap.put(lastKey, FDCHelper.ZERO);
							break;
						}
						//不能抵消完
						else {
							dataMap.put(d, FDCHelper.ZERO);
							balance = amount.negate();
						}
					}
				}
			}
		}

		info.put("Data", new PayPlanNewDataCollection());
		BigDecimal total = FDCHelper.ZERO;
		for (Iterator iterator = dataMap.keySet().iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();

			PayPlanNewDataInfo item = new PayPlanNewDataInfo();
			item.setPayMonth(key.intValue());
			BigDecimal amount = FDCHelper.toBigDecimal(dataMap.get(key));
			if (FDCHelper.isZero(amount)) {
				continue;
			}
			total = FDCHelper.add(total, amount);
			item.setPayAmount(amount);
			info.getData().add(item);
		}

		//清除最后一位的差异
		if (FDCHelper.compareTo(sum, total) != 0) {
			PayPlanNewDataInfo dataInfo = info.getData().get(info.getData().size() - 1);
			if (dataInfo != null) {
				dataInfo.setPayAmount(FDCHelper.add(dataInfo.getPayAmount(), FDCHelper.subtract(sum, total)));
			}
		}

	}


	private void _caculateByTimeRange(Context ctx, PayPlanNewInfo info, PayPlanNewByScheduleInfo scheduleInfo) throws BOSException {

		PayPlanNewByScheduleTaskCollection task = scheduleInfo.getTask();

		//1.准备数据
		Set taskIdSet = new HashSet();
		for (int j = 0; j < task.size(); j++) {
			PayPlanNewByScheduleTaskInfo tInfo = task.get(j);
			taskIdSet.add(tInfo.getTask().getId().toString());
		}

		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", taskIdSet, CompareType.INCLUDE));
		evi.setFilter(filter);

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("number");
		sic.add("name");
		sic.add("start");
		sic.add("end");
		evi.setSelector(sic);
		FDCScheduleTaskCollection taskCollection = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(evi);

		Map monthMap = new HashMap();
		Map dateMap = new HashMap();
		BigDecimal totalDays = FDCHelper.ZERO;
		for (int i = 0; i < taskCollection.size(); i++) {
			FDCScheduleTaskInfo tInfo = taskCollection.get(i);

			Date beginDate = tInfo.getStart();
			Date endDate = tInfo.getEnd();

			if (beginDate == null || endDate == null) {
				continue;
			}

			//			totalDays = FDCHelper.add(totalDays, new Integer(DateHelper.getDiffDays(beginDate, endDate)));
			//			calDateMap(monthMap, beginDate, endDate);

			calDateMapNotRepeat(dateMap, beginDate, endDate);
		}

		calMonthMap(monthMap, dateMap);
		totalDays = new BigDecimal(dateMap.size());

		//3.按比例分摊
		scheduleInfo.put("Dataz", new PayPlanNewByScheduleDatazCollection());
		Iterator it = monthMap.keySet().iterator();

		BigDecimal planPayAmount = FDCHelper.toBigDecimal(scheduleInfo.getPlanPayAmount());

		while (it.hasNext()) {
			Integer datekey = (Integer) it.next();
			BigDecimal days = (BigDecimal) monthMap.get(datekey);

			PayPlanNewByScheduleDatazInfo item = new PayPlanNewByScheduleDatazInfo();
			item.setPayMonth(datekey.intValue());

			item.setPayAmount(FDCHelper.divide(FDCHelper.multiply(planPayAmount, days), totalDays));
			scheduleInfo.getDataz().add(item);
		}

	}

	/**
	 * 计算累计分摊比例天数
	 * key->monthKey
	 * value->monthDays
	 * @param dateMap
	 * @param beginDate
	 * @param endDate
	 */
	private void calDateMap(Map dateMap, Date beginDate, Date endDate) {
		Calendar calendar = Calendar.getInstance();
		Map dayMap = new HashMap();
		while (beginDate.compareTo(endDate) <= 0) {
			Date beginDateOld = beginDate;
			calendar.setTime(beginDate);
			Integer datekey = new Integer(calendar.get(Calendar.YEAR) * 100 + calendar.get(Calendar.MONTH) + 1);

			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
			calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
			calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
			if (calendar.getTime().compareTo(endDate) > 0) {
				beginDate = endDate;
			} else {
				beginDate = calendar.getTime();
			}
			int diffDays = DateHelper.getDiffDays(beginDateOld, beginDate);

			dateMap.put(datekey, FDCHelper.add(new Integer(diffDays), dateMap.get(datekey)));

			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
			calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			beginDate = calendar.getTime();

		}
	}

	/**
	 * 计算累计分摊比例天数,不能重复统计
	 * key->monthKey
	 * value->monthDays
	 * @param dateMap
	 * @param beginDate
	 * @param endDate
	 */
	private void calDateMapNotRepeat(Map dateMap, Date beginDate, Date endDate) {
		Calendar calendar = Calendar.getInstance();
		while (beginDate.compareTo(endDate) <= 0) {
			calendar.setTime(beginDate);
			Integer monthkey = new Integer(calendar.get(Calendar.YEAR) * 100 + calendar.get(Calendar.MONTH) + 1);
			Integer datekey = new Integer(calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH) + 1) * 100
					+ calendar.get(Calendar.DATE));

			dateMap.put(datekey, monthkey);

			calendar.add(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			beginDate = calendar.getTime();

		}
	}

	private void calMonthMap(Map monthMap, Map dateMap) {
		Iterator iterator = dateMap.values().iterator();
		while (iterator.hasNext()) {
			Integer monthKey = (Integer) iterator.next();
			monthMap.put(monthKey, FDCHelper.add(monthMap.get(monthKey), FDCHelper.ONE));
		}
	}

	/**
	 * 描述：按时间点支付
	 * 
	 * @param ctx
	 * @param scheduleInfo
	 */
	private void caculateByTime(Context ctx, PayPlanNewByScheduleInfo scheduleInfo) {

		if (scheduleInfo.getEndDate() == null && scheduleInfo.getPlanPayDate()==null) {
			return;
		}
		Date payDate = null;
		if (scheduleInfo.getTask()!=null && scheduleInfo.getTask().size() > 0) {
			payDate = DateTimeUtils.addDay(scheduleInfo.getTask().get(0)
					.getTask().getEnd(), scheduleInfo.getDelayDay());
		}else{
			payDate = scheduleInfo.getPlanPayDate();
		}
		// 付款时间
//		Date payDate = DateTimeUtils.addDay(scheduleInfo.getEndDate(), scheduleInfo.getDelayDay());

		// 付款金额
		BigDecimal payAmount = FDCHelper.toBigDecimal(scheduleInfo.getPlanPayAmount());

		scheduleInfo.put("Dataz", new PayPlanNewByScheduleDatazCollection());
		PayPlanNewByScheduleDatazInfo item = new PayPlanNewByScheduleDatazInfo();
		item.setPayAmount(payAmount);
		item.setPayMonth(getPayMonthKeyByDate(payDate));
		scheduleInfo.getDataz().add(item);
	}

	private int getPayMonthKeyByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) * 100 + calendar.get(Calendar.MONTH) + 1;
	}


	/**
	 * 描述：进度计划审批时，更新最新版本的合约规划关联的付款规划
	 */
	protected void _onScheduleChange(Context ctx, String scheduleId) throws BOSException, EASBizException {
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		sql.appendSql(" select distinct n.FId,c.FID from T_FDC_PayPlanNewBySchedule s ");
		sql.appendSql(" right outer join T_FDC_PayPlanNewTask t ");
		sql.appendSql(" on s.FID = t.FParentID ");
		sql.appendSql(" left outer join T_SCH_FDCScheduleTask tt  ");
		sql.appendSql(" on t.FTaskID = tt.FID ");
		sql.appendSql(" inner join T_FDC_PayPlanNew n ");
		sql.appendSql(" on s.FParentId = n.FID ");
		sql.appendSql(" inner join T_CON_ProgrammingContract c ");
		sql.appendSql(" on n.FProgrammingID = c.FID ");
		sql.appendSql(" inner join T_CON_Programming p ");
		sql.appendSql(" on c.FProgrammingID = p.FID ");
		sql.appendSql(" where 1 = 1 ");
		sql.appendSql(" and p.FIsLatest = 1 ");
		sql.appendSql(" and tt.FSrcID in (");
		sql.appendSql(" select distinct f.FSrcId from T_SCH_FDCScheduleTask f ");
		sql.appendSql(" where f.FScheduleID = ");
		sql.appendParam(scheduleId);
		sql.appendSql(" )");

		IRowSet rowSet = sql.executeQuery();
		Set cIdSet = new HashSet();
		try {
			while (rowSet.next()) {
				String pId = rowSet.getString(1);
				String cId = rowSet.getString(2);
				if (!StringUtils.isEmpty(pId)) {
					_updateBySchedule(ctx, pId);
				}
				if (!StringUtils.isEmpty(cId)) {
					cIdSet.add(cId);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

		if (cIdSet.size() > 0) {
			sql = new FDCSQLBuilder(ctx);
			sql.appendSql(" select distinct c.fid from T_CON_ContractBill c ");
			sql.appendSql(" where 1 = 1 ");
			sql.appendFilter("c.Fprogrammingcontract", cIdSet, CompareType.INCLUDE);

			rowSet = sql.executeQuery();
			try {
				while (rowSet.next()) {
					String cId = rowSet.getString(1);
					ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(cId,true);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}

		}

	}

	public static SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("Data.*");
		sic.add("BySchedule.*");
		sic.add("BySchedule.TaskName.*");
		sic.add("BySchedule.Task.task.name");
		sic.add("BySchedule.Task.task.start");
		sic.add("BySchedule.Task.task.end");
		sic.add("BySchedule.Dataz.*");
		sic.add("BySchedule.paymentType.*");
		sic.add("programming.programming.project.id");
		sic.add("programming.*");
		sic.add("programming.costEntries.*");
		sic.add("programming.costEntries.costAccount.*");
		sic.add("programming.costEntries.costAccount.curProject.*");
		return sic;
	}

	/**
	 * 更新进度节点的日期
	 */
	protected void _updateBySchedule(Context ctx, String payPlanNewId) throws BOSException, EASBizException {
		ObjectUuidPK pk = new ObjectUuidPK(payPlanNewId);

		PayPlanNewInfo info = (PayPlanNewInfo) getValue(ctx, pk, getSelectors());

		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		sql.appendSql(" select t1.Fid,t2.FID from T_SCH_FDCScheduleTask t1");
		sql.appendSql(" left outer join T_SCH_FDCScheduleTask t2 ");
		sql.appendSql(" on t1.FSrcID = t2.FSrcID ");//当前版本和下个版本匹配
		sql.appendSql(" inner join T_SCH_FDCSchedule fs ");
		sql.appendSql(" on fs.FID = t2.FScheduleID and fs.FIsLatestVer = 1 ");
		sql.appendSql(" inner join T_FDC_PayPlanNewTask t ");
		sql.appendSql(" on t.FTaskID = t1.FID ");
		sql.appendSql(" inner join T_FDC_PayPlanNewBySchedule s ");
		sql.appendSql(" on t.FParentID = s.FID ");
		sql.appendSql(" inner join T_FDC_PayPlanNew p ");
		sql.appendSql(" on s.FParentID = p.FID ");
		sql.appendSql(" where 1 = 1 ");
		sql.appendSql(" and p.FID = ");
		sql.appendParam(payPlanNewId);

		IRowSet rowSet = sql.executeQuery();
		Map oldId2newIdMap = new HashMap();
		try {
			while (rowSet.next()) {
				String oldId = rowSet.getString(1);
				String newId = rowSet.getString(2);

				oldId2newIdMap.put(oldId, newId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

		PayPlanNewByScheduleCollection bySchedule = info.getBySchedule();
		for (int i = 0; i < bySchedule.size(); i++) {
			PayPlanNewByScheduleInfo sInfo = bySchedule.get(i);

			PayPlanNewByScheduleTaskCollection task = sInfo.getTask();

			for (int j = 0; j < task.size(); j++) {
				PayPlanNewByScheduleTaskInfo tInfo = task.get(j);

				if (tInfo.getTask() != null) {
					String key = tInfo.getTask().getId().toString();

					if (oldId2newIdMap.containsKey(key)) {
						String newId = (String) oldId2newIdMap.get(key);

						if (StringUtils.isEmpty(newId)) {
							task.remove(tInfo);
						} else {
							tInfo.getTask().setId(BOSUuid.read(newId));
						}
					}
				}
			}
		}

//		info = _caculate(ctx, info);

		_update(ctx, pk, info);

	}

	protected List _getFDCSchTasksListByProjectID(Context ctx, String projectID) throws BOSException, EASBizException {
		List lst = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FDCScheduleCollection mainSchedules = getMainSchedule(ctx, projectID, view, filter);
		if (!FDCHelper.isEmpty(mainSchedules) && mainSchedules.size() > 0) {
			FDCScheduleInfo editData = mainSchedules.get(0);
			loadSpecailTaskInMainTask(ctx, editData, filter, mainSchedules);

			//			filterBizType(ctx, editData);

			FDCScheduleTaskCollection taskEntrys = editData.getTaskEntrys();
			if (null != taskEntrys) {
				for (int i = taskEntrys.size() - 1; i >= 0; i--) {
					FDCScheduleTaskInfo taskInfo = taskEntrys.get(i);
					if (taskInfo.isIsLeaf()) {
						lst.add(taskInfo);
					}

				}
			}
		}
		return lst;
	}

	private FDCScheduleCollection getMainSchedule(Context ctx, String rememberProjectID, EntityViewInfo view, FilterInfo filter)
			throws BOSException {
		SelectorItemCollection selectors = view.getSelector();
		view.getSelector().add("id");
		view.getSelector().add("taskEntrys.id");
		selectors.add("taskEntrys.isLeaf");
		selectors.add("taskEntrys.level");
		selectors.add("taskEntrys.longNumber");
		selectors.add("taskEntrys.number");
		selectors.add("taskEntrys.name");
		selectors.add("taskEntrys.start");
		selectors.add("taskEntrys.end");
		selectors.add("taskEntrys.bizType.*");
		filter.getFilterItems().add(new FilterItemInfo("project.id", rememberProjectID));
		filter.getFilterItems().add(new FilterItemInfo("isLatestver", "1"));
		filter.getFilterItems().add(new FilterItemInfo("projectSpecial", null));
		view.setFilter(filter);

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo info = new SorterItemInfo();
		info.setPropertyName("taskEntrys.longNumber");
		info.setSortType(SortType.ASCEND);
		sorter.add(info);
		view.setSorter(sorter);
		FDCScheduleCollection mainSchedules = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleCollection(view);
		return mainSchedules;
	}

	private void loadSpecailTaskInMainTask(Context ctx, FDCScheduleInfo editData, FilterInfo filter, FDCScheduleCollection mainSchedules)
			throws BOSException {
		FDCScheduleInfo mainSchedule = mainSchedules.get(0);

		// 获取到了获取到主项任务关联的专项计划ID、专项任务的longNumber
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		Map map = new HashMap();
		if (mainSchedule != null) {
			putSpecialScheduleData(map, mainSchedules, builder);
		}
		/** 获得相关联的专项任务的任务 **/
		filter.getFilterItems().clear();
		filter = getSpecialScheduleFilter(filter, map);
		if (filter.getFilterItems().size() != 0) {
			EntityViewInfo specailTaskView = new EntityViewInfo();
			SelectorItemCollection selector = specailTaskView.getSelector();
			selector = getSpecialRalateSelector(selector);
			specailTaskView.setFilter(filter);
			// 获得主项关联的相关专项任务以及下级任务集合
			FDCScheduleTaskCollection lowerLevelSpcailSchedule = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(
					specailTaskView);
			/** 嵌入相应专项任务到关联主项关联相关专项任务 **/
			putDependSpecailTask(editData, lowerLevelSpcailSchedule);
		}
	}

	/**
	 * 
	 * @description 嵌入相应专项任务到关联主项关联相关专项任务
	 * @author 杜红明
	 * @createDate 2011-9-28
	 * @param lowerLevelSpcailSchedule
	 *            相关专项及专项的子项任务
	 * @version EAS7.0
	 * @see
	 */
	private void putDependSpecailTask(FDCScheduleInfo editData, FDCScheduleTaskCollection lowerLevelSpcailSchedule) {
		FDCScheduleTaskCollection taskEntrys = editData.getTaskEntrys();
		if (lowerLevelSpcailSchedule != null && null != taskEntrys) {
			for (int i = 0; i < taskEntrys.size(); i++) {
				FDCScheduleTaskInfo scheduleTaskInfo = taskEntrys.get(i);
				for (int j = 0; j < lowerLevelSpcailSchedule.size(); j++) {
					FDCScheduleTaskInfo scheduleTaskInfo2 = lowerLevelSpcailSchedule.get(j);
					if (null != scheduleTaskInfo2.getDependMainTaskID()
							&& scheduleTaskInfo.getId().toString().equals(scheduleTaskInfo2.getDependMainTaskID().getId().toString())) {
						scheduleTaskInfo2.setLongNumber(scheduleTaskInfo.getLongNumber() + "!" + "121");
						scheduleTaskInfo2.setLevel(scheduleTaskInfo.getLevel() + 1);
						scheduleTaskInfo2.setParent(scheduleTaskInfo);
						taskEntrys.add(scheduleTaskInfo2);
						editData.getTaskEntrys().addCollection(lowerLevelSpcailSchedule);
					}
				}
			}
		}
	}

	private FilterInfo getSpecialScheduleFilter(FilterInfo filter, Map map) throws BOSException {
		int i = 1;
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String fid = (String) it.next();
			filter.getFilterItems().add(new FilterItemInfo("schedule", fid));

			Set longNumbers = (Set) map.get(fid);
			int j = 1;
			for (Iterator longNumber = longNumbers.iterator(); longNumber.hasNext();) {
				String number = (String) longNumber.next();
				filter.getFilterItems().add(new FilterItemInfo("longNumber", number + "%", CompareType.LIKE));

				if (j != longNumbers.size()) {
					filter.mergeFilter(filter, "or");
				}
				j++;
			}
			if (i != map.size()) {
				filter.mergeFilter(filter, "or");
			}
			i++;
		}
		return filter;
	}

	private Map putSpecialScheduleData(Map map, FDCScheduleCollection mainSchedules, FDCSQLBuilder builder) throws BOSException {
		builder.appendSql("select schedule.fid as fid,task.flongnumber as flongnumber ");
		builder.appendSql("from t_sch_fdcscheduletask as task inner join t_sch_fdcschedule schedule on schedule.fid=task.fscheduleid ");
		builder.appendSql("where schedule.fislatestver=1 and task.fdependmaintaskid in ");
		builder.appendSql("(select fsrcid from t_sch_fdcscheduletask where fscheduleid=?)");
		builder.addParam(mainSchedules.get(0).getId().toString());
		IRowSet rs = builder.executeQuery();
		Set set = null;
		try {
			while (rs.next()) {
				String key = rs.getString("fid");
				String value = rs.getString("flongnumber");
				if (!map.containsKey(key)) {
					set = new HashSet();
				}
				set.add(value);
				map.put(key, set);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		return map;
	}

	private SelectorItemCollection getSpecialRalateSelector(SelectorItemCollection selector) {
		selector.add("id");
		selector.add("name");
		selector.add("number");
		selector.add("longNumber");
		selector.add("level");
		selector.add("isLeaf");
		selector.add("duration");
		selector.add("schedule.id");
		selector.add("parent.*");
		selector.add("srcID");
		selector.add("dependMainTaskID.*");
		selector.add("bizType.*");
		return selector;
	}

	private static final String BIZ_TYPE_CASHFLOW = "+BONIPDySL67xM7mEm9tXmLF6cA=";

	private void filterBizType(Context ctx, FDCScheduleInfo editData) {

		FDCScheduleTaskCollection taskEntrys = editData.getTaskEntrys();
		if (null != taskEntrys) {
			for (int i = taskEntrys.size() - 1; i >= 0; i--) {
				FDCScheduleTaskInfo taskInfo = taskEntrys.get(i);
				//				if (taskInfo.isIsLeaf()) {
				//					continue;
				//				}
				boolean isHasCash = false;
				if (taskInfo.getBizType() != null) {
					ScheduleTaskBizTypeCollection bizTypes = taskInfo.getBizType();
					ScheduleTaskBizTypeInfo bizType = null;
					int bizTypeSize = bizTypes.size();
					for (int k = 0; k < bizTypeSize; k++) {
						bizType = bizTypes.get(k);
						if (bizType.getBizType().getId().toString().equals(BIZ_TYPE_CASHFLOW)) {
							isHasCash = true;
						}
					}
				}

				if (!taskInfo.isIsLeaf()) {
					isHasCash = true;
				}

				if (!isHasCash) {
					taskEntrys.remove(taskInfo);
				}
			}
		}

		FDCScheduleTaskInfo preTaskInfo = null;
		//删除没有下级任务的上级任务
		if (null != taskEntrys) {
			for (int i = taskEntrys.size() - 1; i >= 0; i--) {
				FDCScheduleTaskInfo taskInfo = taskEntrys.get(i);
				boolean isHasCash = true;
				if (!taskInfo.isIsLeaf()) {
					if (preTaskInfo == null) {
						isHasCash = false;
					} else {
						if ((!preTaskInfo.isIsLeaf()) && preTaskInfo.getLevel() <= taskInfo.getLevel()) {
							isHasCash = false;
						}
					}
				}

				if (!isHasCash) {
					taskEntrys.remove(taskInfo);
				}
				preTaskInfo = taskInfo;
			}
		}
	}

}