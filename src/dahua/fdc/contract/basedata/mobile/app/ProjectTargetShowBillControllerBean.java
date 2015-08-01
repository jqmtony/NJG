package com.kingdee.eas.fdc.basedata.mobile.app;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowEntry;
import com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowItem;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowBillInfo;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowEntryCollection;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowEntryFactory;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowEntryInfo;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowItemCollection;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowItemFactory;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowItemInfo;
import com.kingdee.eas.fdc.basedata.util.FdcMapUtil;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.DbUtil;

public class ProjectTargetShowBillControllerBean extends AbstractProjectTargetShowBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.mobile.app.ProjectTargetShowBillControllerBean");
    
    
	// 保存分录细目
	protected void saveEntrysItems(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		ProjectTargetShowBillInfo billInfo = (ProjectTargetShowBillInfo) model;

		// 保存分录数据
		ProjectTargetShowEntryCollection entryClos = (ProjectTargetShowEntryCollection) billInfo.get("entryClos");
		if (null != entryClos) {
			BOSUuid id = BOSUuid.read(pk.toString());
			billInfo.setId(id);

			IProjectTargetShowEntry projectTargetShowEntry = ProjectTargetShowEntryFactory.getLocalInstance(ctx);

			// 先删除，再新增
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("bill.id", pk.toString());
			projectTargetShowEntry.delete(filter);

			CoreBaseCollection colls = new CoreBaseCollection();
			ProjectTargetShowEntryInfo projectTargetShowEntryInfo = null;
			for (int i = 0, size = entryClos.size(); i < size; i++) {
				projectTargetShowEntryInfo = entryClos.get(i);
				projectTargetShowEntryInfo.setBill(billInfo);
				colls.add(projectTargetShowEntryInfo);
			}

			// 批量保存
			projectTargetShowEntry.saveBatchData(colls);
		}

		// 保存细目数据
		Map<String, ProjectTargetShowItemCollection> itemMap = (Map<String, ProjectTargetShowItemCollection>) billInfo
				.get("itemMap");

		Set<String> set = itemMap.keySet();
		Iterator<String> it = set.iterator();
		IProjectTargetShowItem projectTargetShowItem = ProjectTargetShowItemFactory.getLocalInstance(ctx);
		// 先删除，再新增
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("bill.id", pk.toString());
		projectTargetShowItem.delete(filter);

		if (FdcMapUtil.isNotEmpty(itemMap)) {
			CoreBaseCollection colls = new CoreBaseCollection();

			ProjectTargetShowItemCollection itemCols = null;
			ProjectTargetShowItemInfo projectTargetShowItemInfo = null;

			while (it.hasNext()) {
				String key = (String) it.next();

				// 保存分摊数据
				itemCols = (ProjectTargetShowItemCollection) itemMap.get(key);
				for (int i = 0, size = itemCols.size(); i < size; i++) {
					projectTargetShowItemInfo = itemCols.get(i);
					projectTargetShowItemInfo.setBill((ProjectTargetShowBillInfo) model);

					colls.add(projectTargetShowItemInfo);
				}
			}

			// 批量保存
			projectTargetShowItem.saveBatchData(colls);
		}

		// //////////////////////////////////////////////////////////////////////
		// 删除无用的数据
		// //////////////////////////////////////////////////////////////////////

		StringBuffer sql = new StringBuffer();
		sql.append("	DELETE FROM T_FDC_ProjectTargetSItem	\r\n");
		sql.append("	 WHERE FBillID = ?	\r\n");
		sql.append("	   AND FParentID NOT IN	\r\n");
		sql.append("	       (SELECT FID FROM T_FDC_ProjectTargetSEntry WHERE FBillID = ?)	\r\n");

		String billInfoID = billInfo.getId().toString();
		Object[] params = new Object[] { billInfoID, billInfoID };
		DbUtil.execute(ctx, sql.toString(), params);
	}

	// 删除后的其他业务
	protected void afterDelete(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		// //////////////////////////////////////////////////////////////////////
		// 删除无用的数据
		// //////////////////////////////////////////////////////////////////////

		String billInfoID = pk.toString();
		StringBuffer sql = null;
		Object[] params = null;

		// 细目
		sql = new StringBuffer();
		sql.append("	DELETE FROM T_FDC_ProjectTargetSItem	\r\n");
		sql.append("	 WHERE FBillID = ?	\r\n");

		params = new Object[] { billInfoID };
		DbUtil.execute(ctx, sql.toString(), params);

		// 分录
		sql = new StringBuffer();
		sql.append("	DELETE FROM T_FDC_ProjectTargetSEntry	\r\n");
		sql.append("	 WHERE FBillID = ?	\r\n");

		params = new Object[] { billInfoID };
		DbUtil.execute(ctx, sql.toString(), params);
	}
    
}