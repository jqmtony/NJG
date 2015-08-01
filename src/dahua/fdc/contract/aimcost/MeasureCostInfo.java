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
	 * �Ƚ�Ŀ��ɱ������޶�ǰ��ĵ�������ݲ�ͬ������ز�ͬ��ֵ
	 * 
	 * @author owen_wen 2011-09-02
	 * @param ctx �����ģ����빤�����е����������̱���Ҳ����
	 * @param measureCostId ��ǰĿ��ɱ������ID
	 * @param curProjectId ��ǰĿ��ɱ���������������Ŀ��ID
	 * @param srcMeasureCostId ���޶���Ŀ��ɱ������ID
	 * @param baseAmt ��׼������������300�򣬾ʹ��롰3000000�������ڻ�С�ڸ����ĸ���֧
	 * 
	 * @return 1�� ������Ŀ���������300�� 2��������Ŀ������С�ڵ���300�� 3: ��Ŀ���δ����������ڲ���������������Ŀ������Ϊ0<br>
	 *         -1 ����ʾû�е�����
	 */
	public static int getAdjustAmountRate(com.kingdee.bos.Context ctx, String measureCostId, String curProjectId, 
			String srcMeasureCostId, String baseAmt) {
		int returnFlag = -1; 
		try {
			// 1. ��ȡ�����������Ŀ�µ����гɱ���ĿcostAccountCol
			FilterInfo acctFilter = new FilterInfo();
			acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectId));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(acctFilter);
			CostAccountCollection costAccountCol = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
			
			// 2. ȡ���޶�ǰ�ĺϼۣ��ŵ��ɱ���Ŀinfo��ȥ��key��totalAmt1
			FilterInfo meFilter = new FilterInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("costAccount.longNumber");
			sic.add("amount");
			view.setSelector(sic);
			meFilter.getFilterItems().add(new FilterItemInfo("head.id", srcMeasureCostId));
			view.setFilter(meFilter);	
			
			MeasureEntryCollection measureEntryCol = MeasureEntryFactory.getLocalInstance(ctx).getMeasureEntryCollection(view);
			
			// 3. ȡ���޶���ĺϼۣ��ŵ��ɱ���Ŀinfo��ȥ��key��totalAmt2
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
				
				// 4. �Ƚ�ÿ����Ŀ�е�totalAmt1��totalAmt2ֵ��
				BigDecimal totalAmt1 = FDCNumberHelper.toBigDecimal(costAccInfo.get("totalAmt1"));
				BigDecimal totalAmt2 = FDCNumberHelper.toBigDecimal(costAccInfo.get("totalAmt2"));
				if (costAccInfo.getLevel() == 3) { // ������Ŀ��Level��3
					if (totalAmt1.subtract(totalAmt2).abs().compareTo(new BigDecimal(baseAmt)) > 0) {
						return 1; // ��������� 300��
					} else if (totalAmt1.subtract(totalAmt2).abs().compareTo(FDCNumberHelper.ZERO) > 0) {
						return 2; // ���������0��С�ڵ���300��
					}
				} else if (costAccInfo.getLevel() > 3) {
					// ��Ŀ���α�3������ϸ�е����� ��������Ŀ������Ϊ0
					if (totalAmt1.subtract(totalAmt2).abs().compareTo(FDCNumberConstants.ZERO) > 0) {
						returnFlag = 3; // �����Ϊ0
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