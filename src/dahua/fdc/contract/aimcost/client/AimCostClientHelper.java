package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.FdcArrayUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.client.EASResource;

public class AimCostClientHelper {
	public final static String resource = "com.kingdee.eas.fdc.aimcost.AimCostResource";

	public static String getRes(String key) {
		return EASResource.getString(resource, key);
	}

	// ////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：设置汇总行
	 * 
	 * @param table
	 * @param columns
	 * @throws EASBizException
	 * @throws BOSException
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-11
	 */
	public static void setTotalCostRow(KDTable table, String[] columns) throws EASBizException, BOSException {
		if (FDCTableHelper.isEmpty(table) || FdcArrayUtil.isEmpty(columns)) {
			return;
		}

		boolean isNeedShowTotalRow = FDCUtils.getBooleanValue4FDCParamByKey(null, null, FDCConstants.FDC_PARAM_TOTALCOST);
		setTotalCostRow(table, columns, isNeedShowTotalRow);
	}

	/**
	 * 描述：设置汇总行
	 * 
	 * @param table
	 * @param columns
	 * @param isNeedShowTotalRow
	 * @throws EASBizException
	 * @throws BOSException
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-11
	 */
	public static void setTotalCostRow(KDTable table, String[] columns, boolean isNeedShowTotalRow) throws EASBizException, BOSException {
		if (FDCTableHelper.isEmpty(table) || FdcArrayUtil.isEmpty(columns)) {
			return;
		}

		if (!isNeedShowTotalRow) {
			return;
		}

		// ////////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////

		KDTFootManager footRowManager = table.getFootManager();
		IRow footRow = null;
		if (footRowManager == null) {
			String total = getRes("totalCost");
			footRowManager = new KDTFootManager(table);
			footRowManager.addFootView();
			table.setFootManager(footRowManager);
			footRow = footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			table.getIndexColumn().setWidth(60);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			// 设置到第一个可视行
			footRowManager.addIndexText(0, total);
		} else {
			footRow = table.getFootRow(0);
			if (footRow.getUserObject() == null || !footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")) {
				footRow = table.addFootRow(1);
			}
			;
		}

		HashMap map = new HashMap();
		for (int i = 0; i < columns.length; i++) {
			map.put(columns[i], FDCHelper.ZERO);
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() != 0) {
				continue;
			}
			for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				BigDecimal amount = FDCHelper.toBigDecimal(row.getCell(key).getValue());
				amount = amount.add((BigDecimal) map.get(key));
				map.put(key, amount);
			}
		}
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			BigDecimal amount = FDCHelper.toBigDecimal(map.get(key));
			footRow.getCell(key).setValue(amount);
		}
	}

	// ////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////

	public static MeasureStageInfo getLastMeasureStage(CurProjectInfo curProject,boolean isAimMeasure) throws EASBizException, BOSException{
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("measureStage.number");
		view.getSelector().add("measureStage.name");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("project.id",curProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
		if(isAimMeasure){
			filter.getFilterItems().add(new FilterItemInfo("isAimMeasure",Boolean.TRUE,CompareType.EQUALS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("isAimMeasure",Boolean.TRUE,CompareType.NOTEQUALS));
		}
		MeasureCostCollection measureCostCollection = MeasureCostFactory.getRemoteInstance().getMeasureCostCollection(view);
		if (measureCostCollection != null && measureCostCollection.size()==1){
			MeasureCostInfo measureCostInfo = measureCostCollection.get(0);
			if(measureCostInfo.getMeasureStage()!=null){
				MeasureStageInfo lastStageInfo = measureCostInfo.getMeasureStage();
				return lastStageInfo;
			}
		}
		return null;
	}
}
