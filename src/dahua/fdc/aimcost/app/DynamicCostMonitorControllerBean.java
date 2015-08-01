package com.kingdee.eas.fdc.aimcost.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;

public final class DynamicCostMonitorControllerBean extends AbstractDynamicCostMonitorControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.DynamicCostMonitorControllerBean");
    
    @Override
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException {
    	//�ò������룬���账������������
	}
    
   @Override
	protected boolean isUseNumber() {
		return false;
	}
   
   	protected boolean isUseName() {
		return false;
	}

	@Override
	protected IObjectValue _getLatestProgrammingByPrjId(Context ctx, String prjID) throws BOSException, EASBizException {
		String oql = "where project.id = '" + prjID + "' and isLatest = 1 ";

		/*		FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("programming.project.id", prjID));
				filter.getFilterItems().add(new FilterItemInfo("programming.isLatest", 1));
				EntityViewInfo viewInfo = new EntityViewInfo();
				viewInfo.setFilter(filter);
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("reservedChangeRate");
				sic.add("name");
				sic.add("number");
				sic.add("longNumber");
				sic.add("amount"); //�滮���
				sic.add("controlAmount"); //���ƽ��
				sic.add("reservedChangeRate"); // Ԥ������ʣ������ʽ�ƻ������ӵ��ֶΣ����Կ������
				sic.add("parent.id");
				
				sic.add("programming.project.id");
				sic.add("programming.id");
				viewInfo.setSelector(sic);*/
		
		return ProgrammingFactory.getLocalInstance(ctx).getProgrammingInfo(oql);
	}
	
}