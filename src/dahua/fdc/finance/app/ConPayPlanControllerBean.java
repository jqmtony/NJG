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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeCollection;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.CalStandardEnum;
import com.kingdee.eas.fdc.finance.CalTypeEnum;
import com.kingdee.eas.fdc.finance.ConPayPlanByMonthCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanByMonthInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanByScheduleCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanByScheduleDatazCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanByScheduleDatazInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanByScheduleInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanByScheduleTaskCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanByScheduleTaskInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDataAInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanDataCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDataInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanDatapCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDatapInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanDetailCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDetailInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanInfo;
import com.kingdee.eas.fdc.finance.IPayPlanNewUnsign;
import com.kingdee.eas.fdc.finance.PayPlanModeEnum;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewFactory;
import com.kingdee.eas.fdc.finance.PayPlanNewInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewUnsignFactory;
import com.kingdee.eas.fdc.finance.PayPlanNewUnsignInfo;
import com.kingdee.eas.fdc.finance.PrepayWriteOffEnum;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

/**
 * 描述：合同付款规划
 * 
 * 
 */
public class ConPayPlanControllerBean extends AbstractConPayPlanControllerBean {

	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.ConPayPlanControllerBean");

	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		ConPayPlanInfo info = (ConPayPlanInfo) model;
		info = _caculate(ctx, info);
		IObjectPK pk = super._addnew(ctx, info);
		Integer settleMonth = getPayMonthKeyByDate(info.getSettleMonth());
		ContractBillInfo billInfo;
		try {
			billInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo("select programmingContract.id where id='"+info.getContractBill().getId().toString()+"'");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		if(billInfo.getProgrammingContract()!=null){
			String programmingID= billInfo.getProgrammingContract().getId().toString();
			_calPayPlanUnsign(ctx, settleMonth,programmingID);
		}
		return pk;
	}

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		ConPayPlanInfo info = (ConPayPlanInfo) model;
		info = _caculate(ctx, info);
		super._update(ctx, pk, info);
		Integer settleMonth = getPayMonthKeyByDate(info.getSettleMonth());
		ContractBillInfo billInfo;
		try {
			billInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo("select programmingContract.id where id='"+info.getContractBill().getId().toString()+"'");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		String programmingID= billInfo.getProgrammingContract().getId().toString();
		_calPayPlanUnsign(ctx, settleMonth,programmingID);
	}

	protected ConPayPlanInfo _caculate(Context ctx, ConPayPlanInfo info)
			throws BOSException, EASBizException {
		if (info == null) {
			return null;
		}
		info = _caculateBySchedule(ctx, info);
		return info;
	}

	protected void calOneByMonthInfo(Map resultMap, ConPayPlanByMonthInfo mInfo) {
		Date beginDate = mInfo.getBeginDate();
		Date endDate = mInfo.getEndDate();

		if (beginDate == null || endDate == null) {
			return;
		}

		Map monthMap = new HashMap();

		// 重复计算的模式，先注释，后续标准产品能使用这种算法
		// Calendar calendar = Calendar.getInstance();
		// Integer totalDays = new Integer(DateHelper.getDiffDays(beginDate,
		// endDate));
		// calDateMap(monthMap, beginDate, endDate);
		// 金地使用的是不能重复计算模式
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
			BigDecimal amount = FDCHelper.divide(FDCHelper.multiply(
					totalAmount, diffDays), totalDays);
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
	protected ConPayPlanInfo _caculateBySchedule(Context ctx,
			ConPayPlanInfo info) throws BOSException, EASBizException {
		if (info == null) {
			return null;
		}


		ConPayPlanByScheduleCollection bySchedule = info.getBySchedule();

		for (int i = 0; i < bySchedule.size(); i++) {
			ConPayPlanByScheduleInfo scheduleInfo = bySchedule.get(i);

			// 按时间点支付
			if (CalTypeEnum.TIME.equals(scheduleInfo.getCalType())) {
				caculateByTime(ctx, scheduleInfo);
			}
			// 按时间段支付,并且按月度摊分
			else if (CalTypeEnum.TIMERANGE.equals(scheduleInfo.getCalType())) {
				_caculateByTimeRange(ctx, scheduleInfo);
			}
		}

		// 汇总数据
		sumData(ctx, info);

		return info;
	}

	// 处理汇总数据
	protected void sumData(Context ctx, ConPayPlanInfo info)
			throws BOSException {
		Map dataMap = new TreeMap();

		ConPayPlanByScheduleCollection bySchedule = info.getBySchedule();

		Map pidMonth2InfoMap = new HashMap();

		ConPayPlanByScheduleCollection bySchPrepay = new ConPayPlanByScheduleCollection();
		Map bySchPrepayMap = new HashMap();
		ConPayPlanByScheduleCollection byOthers = new ConPayPlanByScheduleCollection();
		Map byOthersMap = new HashMap();
		BigDecimal sum = FDCHelper.ZERO;
		for (int i = 0; i < bySchedule.size(); i++) {
			ConPayPlanByScheduleInfo sInfo = bySchedule.get(i);

			ConPayPlanByScheduleDatazCollection dataz = sInfo.getDataz();

			// 预付款先不加
			if (sInfo.getPaymentType().isPreType()) {
				bySchPrepay.add(sInfo);

				if (!StringUtils.isEmpty(sInfo.getProgrammingID())) {
					if (bySchPrepayMap.containsKey(sInfo.getProgrammingID())) {
						ConPayPlanByScheduleCollection col = (ConPayPlanByScheduleCollection) bySchPrepayMap
								.get(sInfo.getProgrammingID());
						col.add(sInfo);
					} else {
						ConPayPlanByScheduleCollection col = new ConPayPlanByScheduleCollection();
						bySchPrepayMap.put(sInfo.getProgrammingID(), col);
						col.add(sInfo);
					}
				}
				continue;
			}

			sum = FDCHelper.add(sum, sInfo.getPlanPayAmount());

			// 非进度款先不加
			if (!sInfo.getPaymentType().isScheuleType()) {
				byOthers.add(sInfo);

				if (!StringUtils.isEmpty(sInfo.getProgrammingID())) {
					if (byOthersMap.containsKey(sInfo.getProgrammingID())) {
						ConPayPlanByScheduleCollection col = (ConPayPlanByScheduleCollection) byOthersMap
								.get(sInfo.getProgrammingID());
						col.add(sInfo);
					} else {
						ConPayPlanByScheduleCollection col = new ConPayPlanByScheduleCollection();
						byOthersMap.put(sInfo.getProgrammingID(), col);
						col.add(sInfo);
					}
				}
				continue;
			}

			// 统计汇总
			for (int j = 0; j < dataz.size(); j++) {
				ConPayPlanByScheduleDatazInfo datazInfo = dataz.get(j);

				Integer key = new Integer(datazInfo.getPayMonth());
				dataMap.put(key, FDCHelper.add(dataMap.get(key), datazInfo
						.getPayAmount()));
			}

			if (StringUtils.isEmpty(sInfo.getProgrammingID())) {
				continue;
			}
			// 统计明细
			for (int j = 0; j < dataz.size(); j++) {
				ConPayPlanByScheduleDatazInfo datazInfo = dataz.get(j);

				String key = sInfo.getProgrammingID() + datazInfo.getPayMonth();

				if (pidMonth2InfoMap.containsKey(key)) {
					ConPayPlanDetailInfo detailInfo = (ConPayPlanDetailInfo) pidMonth2InfoMap
							.get(key);
					detailInfo.setPayAmount(FDCHelper.add(detailInfo
							.getPayAmount(), datazInfo.getPayAmount()));
				} else {
					ConPayPlanDetailInfo detailInfo = new ConPayPlanDetailInfo();
					ProgrammingContractInfo programming = new ProgrammingContractInfo();
					programming.setId(BOSUuid.read(sInfo.getProgrammingID()));
					detailInfo.setProgramming(programming);
					detailInfo.setPayMonth(datazInfo.getPayMonth());
					detailInfo.setPayAmount(FDCHelper.add(detailInfo
							.getPayAmount(), datazInfo.getPayAmount()));
					pidMonth2InfoMap.put(key, detailInfo);
				}
			}

		}

		// 预付款抵消
		prepayAdjust(dataMap, bySchedule, bySchPrepay, byOthers);

		// 预付款抵消明细
		for (Iterator iterator = bySchPrepayMap.keySet().iterator(); iterator
				.hasNext();) {
			String pid = (String) iterator.next();
			ConPayPlanByScheduleCollection bySchPrepayCol = (ConPayPlanByScheduleCollection) bySchPrepayMap
					.get(pid);
			ConPayPlanByScheduleCollection byOthersCol = (ConPayPlanByScheduleCollection) byOthersMap
					.get(pid);
			prepayAdjustDetail(pid, bySchedule, pidMonth2InfoMap,
					bySchPrepayCol, byOthersCol);
		}
		info.getDatap().clear();
		BigDecimal total = FDCHelper.ZERO;
		for (Iterator iterator = dataMap.keySet().iterator(); iterator
				.hasNext();) {
			Integer key = (Integer) iterator.next();

			ConPayPlanDatapInfo item = new ConPayPlanDatapInfo();
			item.setPayMonth(key.intValue());
			BigDecimal amount = FDCHelper.toBigDecimal(dataMap.get(key));
			if (FDCHelper.isZero(amount)) {
				continue;
			}
			total = FDCHelper.add(total, amount);
			item.setPayAmount(amount);
			info.getDatap().add(item);
		}

		info.getDetail().clear();
		for (Iterator iterator = pidMonth2InfoMap.values().iterator(); iterator
				.hasNext();) {
			info.getDetail().add((ConPayPlanDetailInfo) iterator.next());
		}

		// 清除最后一位的差异
		if (FDCHelper.compareTo(sum, total) != 0) {
			ConPayPlanDatapInfo dataInfo = info.getDatap().get(info.getDatap().size() - 1);
			dataInfo.setPayAmount(FDCHelper.add(dataInfo.getPayAmount(),FDCHelper.subtract(sum, total)));
		}

		_settleByMonth(ctx, info);

	}

	/**
	 * 预付款抵消算法
	 * 
	 * @param dataMap
	 * @param bySchedule
	 * @param pidMonth2InfoMap
	 * @param bySchPrepay
	 */
	private void prepayAdjust(Map dataMap,
			ConPayPlanByScheduleCollection bySchedule,
			ConPayPlanByScheduleCollection bySchPrepay,
			ConPayPlanByScheduleCollection byOthers) {
		if (bySchPrepay != null && bySchPrepay.size() > 0) {
			// 计算总数
			BigDecimal onceAmount = FDCHelper.ZERO;
			BigDecimal batchAmount = FDCHelper.ZERO;
			for (int i = 0; i < bySchPrepay.size(); i++) {
				ConPayPlanByScheduleInfo byScheduleInfo = bySchPrepay.get(i);
				if (PrepayWriteOffEnum.ONCE.equals(byScheduleInfo
						.getWriteOffType())) {
					onceAmount = FDCHelper.add(onceAmount, byScheduleInfo
							.getPlanPayAmount());
				} else if (PrepayWriteOffEnum.BATCH.equals(byScheduleInfo
						.getWriteOffType())) {
					batchAmount = FDCHelper.add(batchAmount, byScheduleInfo
							.getPlanPayAmount());
				}
			}
			List monthKeyLst = new ArrayList(dataMap.keySet());
			// 排序
			Collections.sort(monthKeyLst, new Comparator() {

				public int compare(Object o1, Object o2) {
					Integer d1 = (Integer) o1;
					Integer d2 = (Integer) o2;
					return d2.intValue() - d1.intValue();
				}
			});

			// 一次性抵消
			if (!FDCHelper.isZero(onceAmount)) {
				BigDecimal balance = onceAmount;
				for (int i = monthKeyLst.size() - 1; i >= 0; i--) {
					Integer d = (Integer) monthKeyLst.get(i);
					balance = FDCHelper.subtract(balance, dataMap.get(d));
					// 未抵消完
					if (FDCHelper.compareTo(balance, FDCHelper.ZERO) > 0) {
						dataMap.remove(d);
						monthKeyLst.remove(i);
					}
					// 已经抵消完毕
					else {
						dataMap.put(d, balance.negate());
						break;
					}
				}
			}

			// 分批抵消
			if (!FDCHelper.isZero(batchAmount)) {
				BigDecimal avg = FDCHelper.divide(batchAmount, monthKeyLst
						.size());
				for (int i = monthKeyLst.size() - 1; i >= 0; i--) {
					Integer d = (Integer) monthKeyLst.get(i);
					BigDecimal amount = FDCHelper.subtract(dataMap.get(d), avg);
					dataMap.put(d, amount);

				}
			}

			// 第一次抵消完毕，添加预付款
			for (int i = 0; i < bySchedule.size(); i++) {
				ConPayPlanByScheduleInfo sInfo = bySchedule.get(i);

				// 预付款
				if (sInfo.getPaymentType().isPreType()) {
					ConPayPlanByScheduleDatazCollection dataz = sInfo
							.getDataz();

					for (int j = 0; j < dataz.size(); j++) {
						ConPayPlanByScheduleDatazInfo datazInfo = dataz.get(j);

						Integer key = new Integer(datazInfo.getPayMonth());
						dataMap.put(key, FDCHelper.add(dataMap.get(key),
								datazInfo.getPayAmount()));
					}
				}
			}
		}

		// 添加其他款项
		if (byOthers != null && byOthers.size() > 0) {
			for (int i = 0; i < byOthers.size(); i++) {
				ConPayPlanByScheduleInfo sInfo = byOthers.get(i);

				ConPayPlanByScheduleDatazCollection dataz = sInfo.getDataz();

				for (int j = 0; dataz != null && j < dataz.size(); j++) {
					ConPayPlanByScheduleDatazInfo datazInfo = dataz.get(j);

					Integer key = new Integer(datazInfo.getPayMonth());
					dataMap.put(key, FDCHelper.add(dataMap.get(key), datazInfo
							.getPayAmount()));
				}
			}
		}

		// 重新计算抵消负数的月份
		List monthKeyLst = new ArrayList(dataMap.keySet());
		// 排序
		Collections.sort(monthKeyLst, new Comparator() {

			public int compare(Object o1, Object o2) {
				Integer d1 = (Integer) o1;
				Integer d2 = (Integer) o2;
				return d2.intValue() - d1.intValue();
			}
		});

		// 处理负数的月份，往后一个月借款抵消负数
		for (int i = monthKeyLst.size() - 1; i > 0; i--) {
			Integer d = (Integer) monthKeyLst.get(i);
			if (FDCHelper.compareTo(dataMap.get(d), FDCHelper.ZERO) < 0) {
				Integer dNext = (Integer) monthKeyLst.get(i - 1);
				dataMap.put(dNext, FDCHelper.add(dataMap.get(d), dataMap
						.get(dNext)));
				dataMap.put(d, FDCHelper.ZERO);
			}
		}
		// 若最后一个月为负数，往前面的月份借款
		if (monthKeyLst.size() > 0) {
			Object lastKey = monthKeyLst.get(0);
			if (FDCHelper.compareTo(dataMap.get(lastKey), FDCHelper.ZERO) < 0) {
				BigDecimal balance = (BigDecimal) dataMap.get(lastKey);
				for (int i = 1; i < monthKeyLst.size() - 1; i++) {
					Integer d = (Integer) monthKeyLst.get(i);

					if (FDCHelper.compareTo(dataMap.get(d), FDCHelper.ZERO) > 0) {
						BigDecimal amount = FDCHelper.add(dataMap.get(d),
								balance);
						// 可以抵消完
						if (FDCHelper.compareTo(amount, FDCHelper.ZERO) > 0) {
							dataMap.put(d, amount);
							dataMap.put(lastKey, FDCHelper.ZERO);
							break;
						}
						// 不能抵消完
						else {
							dataMap.put(d, FDCHelper.ZERO);
							balance = amount.negate();
						}
					}
				}
			}
		}
	}

	/**
	 * 预付抵消明细算法
	 * 
	 * @param pid
	 * @param bySchedule
	 * @param pidMonth2InfoMap
	 * @param bySchPrepay
	 */
	private void prepayAdjustDetail(String pid,
			ConPayPlanByScheduleCollection bySchedule, Map pidMonth2InfoMap,
			ConPayPlanByScheduleCollection bySchPrepay,
			ConPayPlanByScheduleCollection byOthersCol) {
		Map dataMap = new HashMap();
		Set keySet = pidMonth2InfoMap.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String p = (String) iterator.next();

			if (p.startsWith(pid)) {
				String mKey = p.substring(pid.length());
				Integer m = new Integer(mKey);
				ConPayPlanDetailInfo dInfo = (ConPayPlanDetailInfo) pidMonth2InfoMap
						.get(p);
				dataMap.put(m, dInfo.getPayAmount());
			}
		}

		prepayAdjust(dataMap, bySchedule, bySchPrepay, byOthersCol);

		// 重新计算的明细
		for (Iterator iterator = dataMap.keySet().iterator(); iterator
				.hasNext();) {
			Integer m = (Integer) iterator.next();

			String key = pid + m;

			if (pidMonth2InfoMap.containsKey(key)) {
				ConPayPlanDetailInfo dInfo = (ConPayPlanDetailInfo) pidMonth2InfoMap
						.get(key);
				BigDecimal amount = (BigDecimal) dataMap.get(m);
				if (FDCHelper.isZero(amount)) {
					pidMonth2InfoMap.remove(key);
				} else {
					dInfo.setPayAmount(amount);
				}
			} else {
				BigDecimal amount = (BigDecimal) dataMap.get(m);
				if (!FDCHelper.isZero(amount)) {
					ConPayPlanDetailInfo dInfo = new ConPayPlanDetailInfo();
					ProgrammingContractInfo programming = new ProgrammingContractInfo();
					programming.setId(BOSUuid.read(pid));
					dInfo.setProgramming(programming);
					dInfo.setPayMonth(m.intValue());
					dInfo.setPayAmount(amount);
					pidMonth2InfoMap.put(key, dInfo);
				}
			}
		}

		// 去除已经移除的明细
		Set pIdSet = new HashSet(keySet);
		for (Iterator iterator = pIdSet.iterator(); iterator.hasNext();) {
			String p = (String) iterator.next();

			if (p.startsWith(pid)) {
				String mKey = p.substring(pid.length());
				Integer m = new Integer(mKey);
				String key = pid + m;
				if (!dataMap.containsKey(m)) {
					pidMonth2InfoMap.remove(key);
				}

			}
		}
	}

	// 计算月度付款计划
	protected void _caculateByTimeRange(Context ctx,
			ConPayPlanByScheduleInfo scheduleInfo) throws BOSException {

		ConPayPlanByScheduleTaskCollection task = scheduleInfo.getTask();

		// 1.准备数据
		Set taskIdSet = new HashSet();
		for (int j = 0; j < task.size(); j++) {
			ConPayPlanByScheduleTaskInfo tInfo = task.get(j);
			taskIdSet.add(tInfo.getTask().getId().toString());
		}

		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", taskIdSet, CompareType.INCLUDE));
		evi.setFilter(filter);

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("number");
		sic.add("name");
		sic.add("start");
		sic.add("end");
		evi.setSelector(sic);
		FDCScheduleTaskCollection taskCollection = FDCScheduleTaskFactory
				.getLocalInstance(ctx).getFDCScheduleTaskCollection(evi);

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

			// totalDays = FDCHelper.add(totalDays, new
			// Integer(DateHelper.getDiffDays(beginDate, endDate)));
			// calDateMap(monthMap, beginDate, endDate);
			calDateMapNotRepeat(dateMap, beginDate, endDate);
		}

		calMonthMap(monthMap, dateMap);
		totalDays = new BigDecimal(dateMap.size());

		// 3.按比例分摊
		scheduleInfo.getDataz().clear();
		BigDecimal planPayAmount = FDCHelper.toBigDecimal(scheduleInfo
				.getPlanPayAmount());
		Iterator it = monthMap.keySet().iterator();
		while (it.hasNext()) {
			Integer datekey = (Integer) it.next();
			BigDecimal days = (BigDecimal) monthMap.get(datekey);

			ConPayPlanByScheduleDatazInfo item = new ConPayPlanByScheduleDatazInfo();
			item.setPayMonth(datekey.intValue());

			item.setPayAmount(FDCHelper.divide(FDCHelper.multiply(
					planPayAmount, days), totalDays));
			scheduleInfo.getDataz().add(item);
		}

	}


	private void calUnSign(ConPayPlanDetailCollection detail, Map dataMap, Integer settleMonth) {
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
		
		BigDecimal unSettAmount = FDCHelper.ZERO;
		BigDecimal settAmount = FDCHelper.ZERO;
		for (Iterator iterator = dataMap.keySet().iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			BigDecimal amount = FDCHelper.toBigDecimal(dataMap.get(key));
			if(settleMonth >= key){
				settAmount =FDCHelper.add(settAmount, amount);
			}else{
				unSettAmount =FDCHelper.add(unSettAmount, amount);
			}
		}
		

		BigDecimal total = FDCHelper.ZERO;
		for (Iterator iterator = dataMap.keySet().iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();

			ConPayPlanDetailInfo item = new ConPayPlanDetailInfo();
			item.setPayMonth(key.intValue());
			BigDecimal amount = FDCHelper.toBigDecimal(dataMap.get(key));
			if (FDCHelper.isZero(amount)) {
				continue;
			}
			total = FDCHelper.add(total, amount);
			if(settleMonth >= key){
				item.setPayAmount(FDCHelper.ZERO);
			}else{
				item.setPayAmount(FDCHelper.add(amount, FDCHelper.divide(FDCHelper.multiply(settAmount, amount), unSettAmount, 2, BigDecimal.ROUND_HALF_UP)));
			}
			detail.add(item);
		}

		//清除最后一位的差异
//		if (FDCHelper.compareTo(sum, total) != 0) {
//			ConPayPlanDetailInfo dataInfo = detail.get(detail.size() - 1);
//			if (dataInfo != null) {
//				dataInfo.setPayAmount(FDCHelper.add(dataInfo.getPayAmount(), FDCHelper.subtract(sum, total)));
//			}
//		}
	}

	protected Map getSrcMap(Context ctx, Set pidSet) throws BOSException {
		// key->pId value->pInfo
		Map id2InfoMap = new HashMap();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("programming.id", pidSet,
						CompareType.INCLUDE));
		evi.setFilter(filter);
		PayPlanNewCollection col = PayPlanNewFactory.getLocalInstance(ctx)
				.getPayPlanNewCollection(evi);
		for (int i = 0; i < col.size(); i++) {
			PayPlanNewInfo info = col.get(i);
			id2InfoMap.put(info.getProgramming().getId().toString(), info);
		}

		return id2InfoMap;
	}

	protected Map getTotalAmountMap(Context ctx,String programmingID, StringBuffer payPlanNewID)
			throws BOSException, EASBizException {
		// key->srcId value->amount
		Map amountMap = new HashMap();
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql(" select pay.fid,data.FPayMonth,data.FPayAmount from T_FDC_PayPlanNewData data ");
			sql.appendSql("inner join T_FDC_PayPlanNew pay on data.FParentID=pay.fid ");
			sql.appendSql("where pay.FProgrammingID =");
			sql.appendParam(programmingID);
		IRowSet rowSet = sql.executeQuery();
		try {
			while (rowSet.next()) {
				payPlanNewID.delete(0,payPlanNewID.length());
				payPlanNewID = payPlanNewID.append(rowSet.getString(1));
				int pId = rowSet.getInt(2);
				BigDecimal amount = rowSet.getBigDecimal(3);

				amountMap.put(pId, amount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

		return amountMap;
	}

	protected Map getSignAmountMap(Context ctx, String programmingID)
			throws BOSException {
		// key->srcId value->amount
		Map amountMap = new HashMap();
		
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql(" select data.FPayMonth,sum(data.FPayAmount) from T_FDC_ConPayPlanData data ");
			sql.appendSql(" inner join T_FDC_ConPayPlan pay on data.FParentID=pay.fid ");
			sql.appendSql(" inner join t_con_contractbill con on con.fid=pay.FContractBillID ");
			sql.appendSql("where con.FProgrammingContract= ");
			sql.appendParam(programmingID);
			sql.appendSql(" group by data.FPayMonth");
		IRowSet rowSet = sql.executeQuery();
		try {
			while (rowSet.next()) {
				int pId = rowSet.getInt(1);
				BigDecimal amount = rowSet.getBigDecimal(2);

				amountMap.put(pId, amount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

		return amountMap;
	}

	/**
	 * 计算累计分摊比例天数 key->monthKey value->monthDays
	 * 
	 * @param dateMap
	 * @param beginDate
	 * @param endDate
	 */
	// protected void calDateMap(Map dateMap, Date beginDate, Date endDate) {
	// Calendar calendar = Calendar.getInstance();
	// Map dayMap = new HashMap();
	// while (beginDate.compareTo(endDate) <= 0) {
	// Date beginDateOld = beginDate;
	// calendar.setTime(beginDate);
	// Integer datekey = new Integer(calendar.get(Calendar.YEAR) * 100 +
	// calendar.get(Calendar.MONTH) + 1);
	//			
	// calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
	// calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
	// calendar.set(Calendar.MINUTE,
	// calendar.getActualMaximum(Calendar.MINUTE));
	// calendar.set(Calendar.SECOND,
	// calendar.getActualMaximum(Calendar.SECOND));
	// if(calendar.getTime().compareTo(endDate) > 0){
	// beginDate = endDate;
	// }else{
	// beginDate = calendar.getTime();
	// }
	// int diffDays = DateHelper.getDiffDays(beginDateOld, beginDate);
	//			
	// dateMap.put(datekey, FDCHelper.add(new Integer(diffDays),
	// dateMap.get(datekey)));
	//			
	// calendar.add(Calendar.MONTH, 1);
	// calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
	// calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR));
	// calendar.set(Calendar.MINUTE,
	// calendar.getActualMinimum(Calendar.MINUTE));
	// calendar.set(Calendar.SECOND,
	// calendar.getActualMinimum(Calendar.SECOND));
	// beginDate = calendar.getTime();
	//
	//
	// }
	// }
	/**
	 * 计算累计分摊比例天数,不能重复统计 key->monthKey value->monthDays
	 * 
	 * @param dateMap
	 * @param beginDate
	 * @param endDate
	 */
	private void calDateMapNotRepeat(Map dateMap, Date beginDate, Date endDate) {
		Calendar calendar = Calendar.getInstance();
		while (beginDate.compareTo(endDate) <= 0) {
			calendar.setTime(beginDate);
			Integer monthkey = new Integer(calendar.get(Calendar.YEAR) * 100
					+ calendar.get(Calendar.MONTH) + 1);
			Integer datekey = new Integer(calendar.get(Calendar.YEAR) * 10000
					+ (calendar.get(Calendar.MONTH) + 1) * 100
					+ calendar.get(Calendar.DATE));

			dateMap.put(datekey, monthkey);

			calendar.add(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR, calendar
					.getActualMinimum(Calendar.HOUR));
			calendar.set(Calendar.MINUTE, calendar
					.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar
					.getActualMinimum(Calendar.SECOND));
			beginDate = calendar.getTime();

		}
	}

	private void calMonthMap(Map monthMap, Map dateMap) {
		Iterator iterator = dateMap.values().iterator();
		while (iterator.hasNext()) {
			Integer monthKey = (Integer) iterator.next();
			monthMap.put(monthKey, FDCHelper.add(monthMap.get(monthKey),
					FDCHelper.ONE));
		}
	}

	/**
	 * 描述：按时间点支付
	 * 
	 * @param ctx
	 * @param scheduleInfo
	 */
	protected void caculateByTime(Context ctx,
			ConPayPlanByScheduleInfo scheduleInfo) {
		// 付款时间
		Date payDate = null;
		if (scheduleInfo.getTask().size() > 0) {
			payDate = DateTimeUtils.addDay(scheduleInfo.getTask().get(0)
					.getTask().getEnd(), scheduleInfo.getDelayDay());
		}else{
			payDate = scheduleInfo.getPlanPayDate();
		}

		// 付款金额
		BigDecimal payAmount = FDCHelper.toBigDecimal(scheduleInfo
				.getPlanPayAmount());

		scheduleInfo.getDataz().clear();
		ConPayPlanByScheduleDatazInfo item = new ConPayPlanByScheduleDatazInfo();
		item.setPayAmount(payAmount);
		item.setPayMonth(getPayMonthKeyByDate(payDate));
		scheduleInfo.getDataz().add(item);
	}

	protected int getPayMonthKeyByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) * 100 + calendar.get(Calendar.MONTH)
				+ 1;
	}

	protected boolean isUseNumber() {
		return false;
	}

	protected boolean isUseName() {
		return false;
	}

	/**
	 * 描述：导入付款模板 isFromSch是否进度任务审批
	 */
	protected IObjectPK _importPayPlan(Context ctx, String contractBillId,
			boolean isFromSch) throws BOSException, EASBizException {

		ConPayPlanInfo info = getConPayPlanInfoByContractBill(ctx,
				contractBillId);

		boolean isexists = ContractBillFactory.getLocalInstance(ctx).exists(
				new ObjectUuidPK(contractBillId));
		if (!isexists) {
			return null;
		}

		ContractBillInfo billInfo = ContractBillFactory.getLocalInstance(ctx)
				.getContractBillInfo(new ObjectUuidPK(contractBillId));
		if (billInfo.getProgrammingContract() == null) {
			return null;
		}

		/**
		 * 更新合同付款计划
		 */

		if (info.getId() != null) {
			updateConPayPlan(billInfo, info, ctx,isFromSch);
			return _save(ctx, info);
		}

		/**
		 * 新增合同付款规划时执行下面操作
		 */

		// Map pAmountRateMap = getPAmoutRateMap(ctx, contractBillId);
		Map pAmountMap = getPAmoutMap(ctx, contractBillId, billInfo);
		PayPlanNewCollection pCol = getPayPlanByContractBill(ctx,
				contractBillId);
		if(pCol.size()==0){
			return null;
		}
		// String settlementID = "";

		BigDecimal conSum = FDCHelper.ZERO;
		Iterator iterator = pAmountMap.values().iterator();
		while (iterator.hasNext()) {
			conSum = FDCHelper.add(conSum, iterator.next());
		}

		Map paymentMap = new TreeMap();
		Map costAcctMap = new TreeMap();
		info.setMode(PayPlanModeEnum.BYSCHEDULE);
		for (int i = 0; i < pCol.size(); i++) {
			PayPlanNewInfo pInfo = pCol.get(i);
			String pId = pInfo.getProgramming().getId().toString();
			if (PayPlanModeEnum.BYSCHEDULE.equals(pInfo.getMode())) {
				PayPlanNewByScheduleCollection bySchedule = pInfo
						.getBySchedule();

				BigDecimal total = FDCHelper.ZERO;
				//
				// BigDecimal schTotal = FDCHelper.ZERO;

				for (int j = 0; j < bySchedule.size(); j++) {
					PayPlanNewByScheduleInfo byScheduleInfo = bySchedule.get(j);

					PaymentTypeInfo paymentType = byScheduleInfo
							.getPaymentType();

					// 预付款
					if (!paymentType.isPreType()) {
						total = FDCHelper.add(total, byScheduleInfo
								.getPlanPayAmount());

					}

				}

				// 计算节点预计结算金额 = 节点成本测试金额/节点成本测试金额总金额*(结算金额or签约金额)
				BigDecimal baseAmount = FDCHelper.ZERO;
				baseAmount = billInfo.getCeremonyb();
				// 节点预计结算总金额，用于最后一个用减法
				// BigDecimal totalSettlmentAmount = FDCHelper.ZERO;

				BigDecimal conTotal = FDCHelper.ZERO;
				for (int j = 0; j < bySchedule.size(); j++) {
					PayPlanNewByScheduleInfo byScheduleInfo = bySchedule.get(j);

					PaymentTypeInfo paymentType = byScheduleInfo
							.getPaymentType();

					// 预付款
					if (!paymentType.isPreType()) {
						// 按合约金额的
						CalStandardEnum calStandard = byScheduleInfo
								.getCalStandard();
						if (CalStandardEnum.CONTRACTAMOUNT.equals(calStandard)) {
							conTotal = FDCHelper.add(conTotal, FDCHelper
									.divide(FDCHelper.multiply(conSum,
											byScheduleInfo.getPayScale()),
											FDCHelper.ONE_HUNDRED));

						}
					}
				}

				for (int j = 0; j < bySchedule.size(); j++) {
					PayPlanNewByScheduleInfo byScheduleInfo = bySchedule.get(j);

					ConPayPlanByScheduleInfo cpInfo = new ConPayPlanByScheduleInfo();
					cpInfo.putAll(byScheduleInfo);
					cpInfo.setId(null);
					cpInfo.setSrcID(byScheduleInfo.getId());
					cpInfo.setProgrammingID(pInfo.getProgramming().getId()
							.toString());

					cpInfo
							.put("task",
									new ConPayPlanByScheduleTaskCollection());
					cpInfo.put("Dataz",
							new ConPayPlanByScheduleDatazCollection());

					for (int k = 0; k < byScheduleInfo.getTask().size(); k++) {
						PayPlanNewByScheduleTaskInfo taskInfo = byScheduleInfo
								.getTask().get(k);
						ConPayPlanByScheduleTaskInfo ctInfo = new ConPayPlanByScheduleTaskInfo();
						ctInfo.putAll(taskInfo);
						ctInfo.setId(null);
						cpInfo.getTask().add(ctInfo);
					}
					PaymentTypeInfo paymentType = byScheduleInfo
							.getPaymentType();
					CalStandardEnum calStandard = byScheduleInfo
							.getCalStandard();
					String key = paymentType.getId().toString();

					// if (paymentType.isSettleType()) {
					// settlementID = key;
					// }

					if (paymentMap.containsKey(key)) {
						ConPayPlanByScheduleCollection cpCol = (ConPayPlanByScheduleCollection) paymentMap
								.get(key);
						cpCol.add(cpInfo);
					} else {
						ConPayPlanByScheduleCollection cpCol = new ConPayPlanByScheduleCollection();
						cpCol.add(cpInfo);
						paymentMap.put(key, cpCol);
					}

					// 预付款
					if (paymentType.isPreType()) {
						cpInfo.setPlanPayAmount(FDCHelper.divide(FDCHelper
								.multiply(pAmountMap.get(pId), byScheduleInfo
										.getPlanPayAmount()), total));
					} else if (CalStandardEnum.CONTRACTAMOUNT
							.equals(calStandard)) {
						// 按合约金额支付
						cpInfo
								.setPlanPayAmount(FDCHelper.divide(FDCHelper
										.multiply(conSum, byScheduleInfo
												.getPayScale()),
										FDCHelper.ONE_HUNDRED));

					}

				}

			}

		}

		info.getBySchedule().clear();
		PaymentTypeCollection coll = PaymentTypeFactory.getLocalInstance(ctx)
				.getPaymentTypeCollection("order by number");
		for (int i = 0; i < coll.size(); i++) {
			PaymentTypeInfo pInfo = coll.get(i);
			String key = pInfo.getId().toString();
			if (paymentMap.containsKey(key)) {
				ConPayPlanByScheduleCollection col = (ConPayPlanByScheduleCollection) paymentMap
						.get(key);
				info.getBySchedule().addCollection(col);
			}
		}

		return _save(ctx, info);
	}

	private void updateConPayPlan(ContractBillInfo billInfo,
			ConPayPlanInfo info, Context ctx,boolean isFromSch) throws BOSException {
		ConPayPlanByScheduleCollection bySchedule = info.getBySchedule();
		Map srcInfo = new HashMap();
		if(isFromSch){
			Set taskSetId = new HashSet();
			for (int i = 0; i < bySchedule.size(); i++) {
				ConPayPlanByScheduleInfo scheduleInfo = bySchedule.get(i);
				if (scheduleInfo.getTask().size() > 0) {
					for (int j = 0; j < scheduleInfo.getTask().size(); j++) {
						taskSetId.add(scheduleInfo.getTask().get(j).getTask()
								.getSrcID());
					}
				}
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("srcId", taskSetId, CompareType.INCLUDE));
			filter.getFilterItems().add(
					new FilterItemInfo("schedule.isLatestVer ", true));
			view.setFilter(filter);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("start");
			sic.add("end");
			sic.add("srcID");
			sic.add("id");
			view.setSelector(sic);
			FDCScheduleTaskCollection taskCollection = FDCScheduleTaskFactory
			.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
			if (taskCollection.size() > 0) {
				for (int i = 0; i < taskCollection.size(); i++) {
					FDCScheduleTaskInfo taskInfo = taskCollection.get(i);
					srcInfo.put(taskInfo.getSrcID(), taskInfo);
				}
			} else {
				return;
			}
		}

		Map amoutMap = getPAmoutMap(ctx, billInfo.getId().toString(), billInfo);
		
		if(amoutMap.size()==0){
			return;
		}
		//合同最新造价
		Object conLstAmount = amoutMap.values().iterator().next();
		
		for (int i = 0; i < bySchedule.size(); i++) {
			ConPayPlanByScheduleInfo scheduleInfo = bySchedule.get(i);
			scheduleInfo.setProgrammingID(billInfo.getProgrammingContract()
					.getId().toString());
			// scheduleInfo.setEndDate(item)
			scheduleInfo.setPlanPayAmount(FDCHelper.divide(FDCHelper.multiply(conLstAmount, scheduleInfo.getPayScale()), FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP));
			if (scheduleInfo.getTask().size() > 0 && isFromSch) {
				for (int j = 0; j < scheduleInfo.getTask().size(); j++) {
					ConPayPlanByScheduleTaskInfo scheduleTaskInfo = scheduleInfo
							.getTask().get(j);
					FDCScheduleTaskInfo FDCScheduleTaskInfo = (FDCScheduleTaskInfo) srcInfo
							.get(scheduleTaskInfo.getTask().getSrcID());
					scheduleTaskInfo.setTask(FDCScheduleTaskInfo);
				}
			}
		}

	}

	protected PayPlanNewCollection getPayPlanByContractBill(Context ctx,
			String contractBillId) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("programming.id",
						"select FProgrammingContract from T_CON_Contractbill where fid = '"
								+ contractBillId + "'", CompareType.INNER));
		view.setFilter(filter);

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("Data.*");
		sic.add("BySchedule.*");
		sic.add("BySchedule.costAccount.*");
		sic.add("BySchedule.paymentType.*");
		sic.add("BySchedule.Task.task.name");
		sic.add("BySchedule.Task.task.start");
		sic.add("BySchedule.Task.task.end");
		sic.add("BySchedule.Dataz.*");
		view.setSelector(sic);
		PayPlanNewCollection pCol = PayPlanNewFactory.getLocalInstance(ctx)
				.getPayPlanNewCollection(view);
		return pCol;
	}

	protected ConPayPlanInfo getConPayPlanInfoByContractBill(Context ctx,
			String contractBillId) throws BOSException {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", contractBillId));
		evi.setFilter(filter);

		SelectorItemCollection sic = new SelectorItemCollection();
		;
		sic.add("*");
		sic.add("contractBill.number");
		sic.add("BySchedule.*");
		sic.add("BySchedule.paymentType.*");
		sic.add("BySchedule.Task.*");
		sic.add("BySchedule.Task.task.name");
		sic.add("BySchedule.Task.task.start");
		sic.add("BySchedule.Task.task.end");
		sic.add("BySchedule.Task.task.id");
		sic.add("BySchedule.Task.task.srcID");
		// sic.add("Detail.*");
		evi.setSelector(sic);
		ConPayPlanCollection col = getConPayPlanCollection(ctx, evi);
		ConPayPlanInfo info = null;
		if (col != null && col.size() > 0) {
			info = col.get(0);
		} else {
			info = new ConPayPlanInfo();
			ContractBillInfo conBill = new ContractBillInfo();
			conBill.setId(BOSUuid.read(contractBillId));
			info.setContractBill(conBill);
			info.setSettleMonth(getPreMonthDate(new Date()));
		}
		return info;
	}

	protected Map getPAmoutMap(Context ctx, String contractBillId,
			ContractBillInfo billInfo) throws BOSException {
		// key->programmingid value->amount
		Map amountMap = new HashMap();
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		if(billInfo.isHasSettled()){
			sql.appendSql(" select distinct FProgrammingContract,FSettleAmt from T_CON_contractbill where fid = ");
			sql.appendParam(contractBillId);
		}else{
			sql.appendSql(" select t1.proid,sum(t1.AMOUNT) as AMOUNT from ( ");
			sql.appendSql("select  con.FProgrammingContract as proid,isnull(con.famount,0)  as AMOUNT  ");
			sql.appendSql("from T_CON_contractbill as con  ");
			sql.appendSql("where con.fid= ");
			sql.appendParam(contractBillId);
			sql.appendSql(" union all");
			sql.appendSql(" select con.FProgrammingContract as proid,sum(ISNULL(change.FBalanceAmount, change.FAmount)) as AMOUNT  ");
			sql.appendSql("from T_CON_contractbill as con ");
			sql.appendSql("left join T_CON_ContractChangeBill as change on change.FContractBillID = con.fid  ");
			sql.appendSql("where  (change.fstate = '8VISA' or change.fstate='4AUDITTED' ) and con.fid= ");
			sql.appendParam(contractBillId);
			sql.appendSql(" group by con.FProgrammingContract ");
			sql.appendSql(") t1  group by t1.proid");
			
		}
		
		IRowSet rowSet = sql.executeQuery();
		try {
			while (rowSet.next()) {
				String pId = rowSet.getString(1);
				BigDecimal amount = rowSet.getBigDecimal(2);
				amountMap.put(pId, amount);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

		return amountMap;
	}

	protected Map getSumMap(Context ctx, String contractBillId)
			throws BOSException {
		// key->programmingid value->amount
		Map amountMap = new HashMap();
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		sql
				.appendSql(" select n.FProgrammingID,sum(d.FPlanPayAmount) from T_FDC_PayPlanNewBySchedule d ");
		sql.appendSql(" inner join T_FDC_PayPlanNew n on d.FParentID = n.FID ");
		sql
				.appendSql(" inner join T_FDC_PaymentType t on d.FPaymentTypeID = t.FID and t.FName_l2 != N'预付款'");
		sql
				.appendSql(" where n.FProgrammingID in ( select FProgrammingContract from T_CON_contractbill where fid = ");
		sql.appendParam(contractBillId);
		sql.appendSql(" and d.FCalStandard = 2 )group by n.FProgrammingID ");
		IRowSet rowSet = sql.executeQuery();
		try {
			while (rowSet.next()) {
				String pId = rowSet.getString(1);
				// String cId = rowSet.getString(2);
				BigDecimal amount = rowSet.getBigDecimal(2);

				amountMap.put(pId, amount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

		return amountMap;
	}

	protected void _onScheduleChange(Context ctx, String scheduleId)
			throws BOSException, EASBizException {
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		sql
				.appendSql(" select distinct cp.FContractBillID from T_FDC_ConPayPlan cp ");
		sql.appendSql(" left outer join T_FDC_ConPayPlanBySchedule s ");
		sql.appendSql(" on s.FParentId = cp.FID ");
		sql.appendSql(" right outer join T_FDC_ConPayPlanTask t ");
		sql.appendSql(" on s.FID = t.FParentID ");
		sql.appendSql(" inner join T_CON_ProgrammingContract c ");
		sql.appendSql(" on s.FProgrammingID = c.FID ");
		sql.appendSql(" inner join T_CON_Programming p ");
		sql.appendSql(" on c.FParentId = p.FID ");
		sql.appendSql(" where 1 = 1 ");
		sql.appendSql(" and p.FIsLatest = 1 ");
		sql.appendSql(" and t.FSrcID in (");
		sql
				.appendSql(" select distinct f.FSrcId from T_SCH_FDCScheduleTask f ");
		sql.appendSql(" where f.FParentID = ");
		sql.appendParam(scheduleId);
		sql.appendSql(" )");

		IRowSet rowSet = sql.executeQuery();
		try {
			while (rowSet.next()) {
				String pId = rowSet.getString(1);

				_importPayPlan(ctx, pId, true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

	}

	private Date getPreMonthDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 描述：月结
	 */
	protected void _settleByMonth(Context ctx, ConPayPlanInfo info)
			throws BOSException {
		// 未签约的月结
		if (info.isCalUnsign()) {
//			settleUnSigned(ctx, info);
		}
		// 已签约的月结
		else {
			settleSigned(ctx, info);
		}

	}

	/**
	 * 描述：未签约的月结
	 * 
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 */
	private void settleUnSigned(Context ctx, ConPayPlanInfo info)
			throws BOSException {

		Map dataMap = new HashMap();
		ConPayPlanDatapCollection datap = info.getDatap();
		for (int i = 0; i < datap.size(); i++) {
			ConPayPlanDatapInfo datapInfo = datap.get(i);
			dataMap.put(new Integer(datapInfo.getPayMonth()), datapInfo
					.getPayAmount());
		}
		if (info.getSettleMonth() == null) {
			info.setSettleMonth(getPreMonthDate(new Date()));
		}
		Integer settleMonth = getPayMonthKeyByDate(info.getSettleMonth());

		info.getDataA().clear();

		Iterator iterator = dataMap.keySet().iterator();
		BigDecimal sum = FDCHelper.ZERO;
		BigDecimal balance = FDCHelper.ZERO;
		while (iterator.hasNext()) {
			Integer month = (Integer) iterator.next();

			if (settleMonth >= month) {
				sum = FDCHelper.add(sum, dataMap.get(month));
			} else {
				balance = FDCHelper.add(balance, dataMap.get(month));
			}
		}

		info.getData().clear();
		List lst = new ArrayList();
		iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			Integer month = (Integer) iterator.next();

			if (settleMonth >= month) {
				// DO NOTHING
				ConPayPlanDataInfo dataInfo = new ConPayPlanDataInfo();
				dataInfo.setPayMonth(month);
				dataInfo.setPayAmount(FDCHelper.ZERO);
				lst.add(dataInfo);
			} else {
				ConPayPlanDataInfo dataInfo = new ConPayPlanDataInfo();
				dataInfo.setPayMonth(month);
				BigDecimal payAmount = FDCHelper.add(dataMap.get(month),
						FDCHelper.divide(FDCHelper.multiply(sum, dataMap
								.get(month)), balance));
				dataInfo.setPayAmount(payAmount);
				lst.add(dataInfo);
			}

		}

		Collections.sort(lst, new Comparator() {

			public int compare(Object o1, Object o2) {
				ConPayPlanDataInfo d1 = (ConPayPlanDataInfo) o1;
				ConPayPlanDataInfo d2 = (ConPayPlanDataInfo) o2;
				return d1.getPayMonth() - d2.getPayMonth();
			}
		});

		for (Iterator it = lst.iterator(); it.hasNext();) {
			ConPayPlanDataInfo dInfo = (ConPayPlanDataInfo) it.next();
			info.getData().add(dInfo);
		}
	}

	/**
	 * 描述：已签约的月结
	 * 
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 */
	private void settleSigned(Context ctx, ConPayPlanInfo info)
			throws BOSException {
		String contractBillId = info.getContractBill().getId().toString();
		Map settleMap = getSettleAmoutMap(ctx, contractBillId);

		Map dataMap = new HashMap();
		ConPayPlanDatapCollection datap = info.getDatap();
		for (int i = 0; i < datap.size(); i++) {
			ConPayPlanDatapInfo datapInfo = datap.get(i);
			dataMap.put(new Integer(datapInfo.getPayMonth()), datapInfo
					.getPayAmount());
		}
		if (info.getSettleMonth() == null) {
			info.setSettleMonth(getPreMonthDate(new Date()));
		}
		Integer settleMonth = getPayMonthKeyByDate(info.getSettleMonth());

		Iterator iterator = settleMap.keySet().iterator();
		info.getDataA().clear();
		while (iterator.hasNext()) {
			Integer month = (Integer) iterator.next();

			ConPayPlanDataAInfo item = new ConPayPlanDataAInfo();
			item.setPayMonth(month);
			item.setPayAmount((BigDecimal) settleMap.get(month));
			info.getDataA().add(item);

			if (settleMonth < month) {
				continue;
			}

			if (dataMap.containsKey(month)) {
				dataMap.put(month, FDCHelper.subtract(dataMap.get(month),
						settleMap.get(month)));
			} else {
				dataMap
						.put(month, ((BigDecimal) settleMap.get(month))
								.negate());
			}
		}

		iterator = dataMap.keySet().iterator();
		BigDecimal sum = FDCHelper.ZERO;
		BigDecimal balance = FDCHelper.ZERO;
		while (iterator.hasNext()) {
			Integer month = (Integer) iterator.next();

			if (settleMonth >= month) {
				sum = FDCHelper.add(sum, dataMap.get(month));
			} else {
				balance = FDCHelper.add(balance, dataMap.get(month));
			}
		}

		info.getData().clear();
		List lst = new ArrayList();
		iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			Integer month = (Integer) iterator.next();

			if (settleMonth >= month) {
				ConPayPlanDataInfo dataInfo = new ConPayPlanDataInfo();
				dataInfo.setPayMonth(month);
				dataInfo.setPayAmount(FDCHelper.toBigDecimal(settleMap
						.get(month)));
				lst.add(dataInfo);
			} else {
				ConPayPlanDataInfo dataInfo = new ConPayPlanDataInfo();
				dataInfo.setPayMonth(month);
				BigDecimal payAmount = FDCHelper.add(dataMap.get(month),
						FDCHelper.divide(FDCHelper.multiply(sum, dataMap
								.get(month)), balance));
				dataInfo.setPayAmount(payAmount);
				lst.add(dataInfo);
			}

		}

		Collections.sort(lst, new Comparator() {

			public int compare(Object o1, Object o2) {
				ConPayPlanDataInfo d1 = (ConPayPlanDataInfo) o1;
				ConPayPlanDataInfo d2 = (ConPayPlanDataInfo) o2;
				return d1.getPayMonth() - d2.getPayMonth();
			}
		});

		for (Iterator it = lst.iterator(); it.hasNext();) {
			ConPayPlanDataInfo dInfo = (ConPayPlanDataInfo) it.next();
			info.getData().add(dInfo);
		}
		// 更新部门月度付款计划

	}

	protected Map getSettleAmoutMap(Context ctx, String contractBillId)
			throws BOSException {
		// key->month value->amount
		Map amountMap = new HashMap();
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		sql
				.appendSql(" select year(FBizDate)*100 + month(FBizDate),sum(FAmount) from T_CAS_PaymentBill where FContractbillID = ");
		sql.appendParam(contractBillId);
		sql.appendSql(" and FBillStatus = " + BillStatusEnum.PAYED_VALUE
				+ " group by year(FBizDate)*100 + month(FBizDate) ");
		IRowSet rowSet = sql.executeQuery();
		try {
			while (rowSet.next()) {
				Integer month = rowSet.getInt(1);
				BigDecimal amount = rowSet.getBigDecimal(2);

				amountMap.put(month, amount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

		return amountMap;
	}

	protected void _calPayPlanUnsign(Context ctx, int settleMonth,
			String programmingID) throws BOSException, EASBizException {
		StringBuffer payPlanNewID = new StringBuffer();

		// 1、获取付款规划总金额
		Map totalMap = getTotalAmountMap(ctx,programmingID,payPlanNewID);
		
		if(payPlanNewID.length()==0){
			return;
		}
		// 2、获取已经签约的付款规划总金额
		Map signMap = getSignAmountMap(ctx, programmingID);
		
		
		
		ConPayPlanDetailCollection detail = new ConPayPlanDetailCollection();
		
		BigDecimal totalPayPlan = FDCHelper.ZERO;
		BigDecimal totalConPlan = FDCHelper.ZERO;
		for(Iterator ite =signMap.keySet().iterator();ite.hasNext();){
			int payMon = (Integer) ite.next();
			BigDecimal payAmount = (BigDecimal) signMap.get(payMon);
			totalConPlan = FDCHelper.add(totalConPlan, payAmount);
		}
		
		for(Iterator ite =totalMap.keySet().iterator();ite.hasNext();){
			int payMon = (Integer) ite.next();
			BigDecimal payAmount = (BigDecimal) totalMap.get(payMon);
			totalPayPlan = FDCHelper.add(totalPayPlan, payAmount);
		}
		BigDecimal totalUnSign = FDCHelper.subtract(totalPayPlan, totalConPlan);
		BigDecimal totalUnSign2 = FDCHelper.ZERO;
		Map unSignMap = new HashMap();
		for(Iterator ite =totalMap.keySet().iterator();ite.hasNext();){
			int payMon = (Integer) ite.next();
			BigDecimal payAmount = (BigDecimal) totalMap.get(payMon);
			BigDecimal bigDecimal = FDCHelper.divide(FDCHelper.multiply(payAmount, totalUnSign), totalPayPlan, 2, BigDecimal.ROUND_HALF_UP);
			if(ite.hasNext()){
				totalUnSign2 = FDCHelper.add(totalUnSign2, bigDecimal);
				unSignMap.put(payMon, bigDecimal);
			}else{
				unSignMap.put(payMon, FDCHelper.subtract(totalUnSign, totalUnSign2));
			}
		}
		
//		/**
//		 * 计算待签订付款计划金额
//		 */
//		for(Iterator ite =signMap.keySet().iterator();ite.hasNext();){
//			int payMon = (Integer) ite.next();
//			BigDecimal payAmount = (BigDecimal) signMap.get(payMon);
//			if(totalMap.containsKey(payMon)){
//				totalMap.put(payMon, FDCHelper.subtract(totalMap.get(payMon), payAmount));
//			}else{
//				totalMap.put(payMon,payAmount.negate());
//			}
//		}
		
		/**
		 * 计算逻辑
		 */
		calUnSign(detail,unSignMap,settleMonth);
		

		Set pidSet = new HashSet();
		pidSet.add(programmingID);

		if (pidSet.size() > 0) { // 删除旧数据
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("parent.programming.id", pidSet,
							CompareType.INCLUDE));
			IPayPlanNewUnsign localInstance = PayPlanNewUnsignFactory
					.getLocalInstance(ctx);
			localInstance.delete(filter);

//			Map id2InfoMap = getSrcMap(ctx, pidSet);
			PayPlanNewInfo payInfo = new PayPlanNewInfo();
			payInfo.setId(BOSUuid.read(payPlanNewID.toString()));
			CoreBaseCollection colls = new CoreBaseCollection();
			for (int i = 0; i < detail.size(); i++) {
				ConPayPlanDetailInfo detailInfo = detail.get(i);
				PayPlanNewUnsignInfo uInfo = new PayPlanNewUnsignInfo();
				uInfo.setPayAmount(detailInfo.getPayAmount());
				uInfo.setPayMonth(detailInfo.getPayMonth());
				uInfo.setParent(payInfo);
				colls.add(uInfo);
			}

			localInstance.addnew(colls);
		}
		
	}
}