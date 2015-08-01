package com.kingdee.eas.fdc.aimcost.client;

import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.util.client.EASResource;

public class FDCAimcostClientHelper {
	
	/** 资源文件路径 */
	public final static String resource = "com.kingdee.eas.fdc.aimcost.AimCostResource";
	
	/**
	 * @description 取资源文件方法
	 * @author 陈伟
	 * @createDate 2011-8-9
	 * @param key
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	public static String getRes(String key) {
		return EASResource.getString(resource, key);
	}

	/**
	 * @description 返回子集科目
	 * @author 陈伟
	 * @createDate 2011-8-12
	 * @param info
	 *            父科目对象
	 * @param objectId
	 *            项目或者组织Id
	 * @param acctChidrenMap
	 *            子集科目缓存
	 * @return CostAccountCollection 子集集合
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	public static CostAccountCollection getCostAccountCollection(CostAccountInfo info,
			String objectId, final Map acctChidrenMap) throws BOSException {
		CostAccountCollection cion = null;
 
		// 已经存在的直接返回
		cion = (CostAccountCollection) acctChidrenMap.get(info.getId().toString());
		if (cion != null && cion.size() > 0) {
			return cion;
		}
		// 判断是否是项目id如果是项目id就给用项目查询如果不是就用组织查询
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo filter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", objectId));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", objectId));
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.id", null, CompareType.EMPTY));
		}
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("curProject.FullOrgUnit.longnumber"));
		view.getSelector().add(new SelectorItemInfo("curProject.id"));
		view.getSelector().add(new SelectorItemInfo("FullOrgUnit.longnumber"));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", info.getLongNumber() + "!%", CompareType.LIKE));

		view.setFilter(filter);

		// 取出所有下级集合
		cion = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		// 以长编码为key放入缓存中
		acctChidrenMap.put(info.getId().toString(), cion);
		return cion;
	}
}
