package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCNumberConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.util.SysUtil;

public class MeasureCostInfo extends AbstractMeasureCostInfo implements Serializable 
{
    public MeasureCostInfo()
    {
        super();
    }
    protected MeasureCostInfo(String pkField)
    {
        super(pkField);
    }

	/**
	 * 比较目标成本测算修订前后的调整额，根据不同情况返回不同的值
	 * 
	 * @author owen_wen 2011-09-02
	 * @param ctx 上下文，传入工作流中的上下文流程变量也可以
	 * @param measureCostId 当前目标成本测算的ID
	 * @param curProjectId 当前目标成本测算所属工程项目的ID
	 * @param srcMeasureCostId 被修订的目标成本测算的ID
	 * @param baseAmt 基准调整金额，例如是300万，就传入“3000000”，大于或小于各走哪个分支
	 * 
	 * @return 1： 三级科目调整额大于300万； 2：三级科目调整额小于等于300万； 3: 科目级次大于三级有内部调整，但三级科目调整额为0<br>
	 *         -1 ：表示没有调整；
	 */
	public static int getAdjustAmountRate(com.kingdee.bos.Context ctx, String measureCostId, String curProjectId, 
			String srcMeasureCostId, String baseAmt) {
		int returnFlag = -1; 
		try {
			// 1. 先取出这个工程项目下的所有成本科目costAccountCol
			FilterInfo acctFilter = new FilterInfo();
			acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectId));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(acctFilter);
			CostAccountCollection costAccountCol = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
			
			// 2. 取出修订前的合价，放到成本科目info中去，key是totalAmt1
			FilterInfo meFilter = new FilterInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("costAccount.longNumber");
			sic.add("amount");
			view.setSelector(sic);
			meFilter.getFilterItems().add(new FilterItemInfo("head.id", srcMeasureCostId));
			view.setFilter(meFilter);	
			
			MeasureEntryCollection measureEntryCol = MeasureEntryFactory.getLocalInstance(ctx).getMeasureEntryCollection(view);
			
			// 3. 取出修订后的合价，放到成本科目info中去，key是totalAmt2
			meFilter.getFilterItems().clear();
			meFilter.getFilterItems().add(new FilterItemInfo("head.id", measureCostId));
			MeasureEntryCollection measureEntryCol2 = MeasureEntryFactory.getLocalInstance(ctx).getMeasureEntryCollection(view);
			
			for (int i = 0; i < costAccountCol.size(); i++) {
				CostAccountInfo costAccInfo = costAccountCol.get(i);
				for (Iterator it = measureEntryCol.iterator(); it.hasNext();) {
					MeasureEntryInfo meInfo = (MeasureEntryInfo) it.next();
					if (meInfo.getCostAccount().getLongNumber().startsWith(costAccInfo.getLongNumber())) {
						costAccInfo.put("totalAmt1", FDCNumberHelper.toBigDecimal(costAccInfo.get("totalAmt1")).add(meInfo.getAmount()));
					}
				}
				for (Iterator it = measureEntryCol2.iterator(); it.hasNext();) {
					MeasureEntryInfo meInfo = (MeasureEntryInfo) it.next();
					if (meInfo.getCostAccount().getLongNumber().startsWith(costAccInfo.getLongNumber())) {
						costAccInfo.put("totalAmt2", FDCNumberHelper.toBigDecimal(costAccInfo.get("totalAmt2")).add(meInfo.getAmount()));
					}
				}
				
				// 4. 比较每个科目中的totalAmt1和totalAmt2值，
				BigDecimal totalAmt1 = FDCNumberHelper.toBigDecimal(costAccInfo.get("totalAmt1"));
				BigDecimal totalAmt2 = FDCNumberHelper.toBigDecimal(costAccInfo.get("totalAmt2"));
				if (costAccInfo.getLevel() == 3) { // 三级科目是Level是3
					if (totalAmt1.subtract(totalAmt2).abs().compareTo(new BigDecimal(baseAmt)) > 0) {
						return 1; // 调整额大于 300万
					} else if (totalAmt1.subtract(totalAmt2).abs().compareTo(FDCNumberHelper.ZERO) > 0) {
						return 2; // 调整额大于0，小于等于300万
					}
				} else if (costAccInfo.getLevel() > 3) {
					// 科目级次比3级更明细有调整， 但三级科目调整额为0
					if (totalAmt1.subtract(totalAmt2).abs().compareTo(FDCNumberConstants.ZERO) > 0) {
						returnFlag = 3; // 调整额不为0
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
			SysUtil.abort();
		} 

		return returnFlag;
	}
}