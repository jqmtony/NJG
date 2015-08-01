/**
 * @(#)FdcF7InitHelper.java 1.0 2014-8-7
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.client;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.f7.CostCenterF7;
import com.kingdee.eas.basedata.org.client.f7.TransportF7;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.util.SysUtil;

/**
 * 描述：房地产F7初始化 助手
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-8-7
 * @version 1.0, 2014-8-7
 * @see
 * @since JDK1.4
 */
public class FdcF7InitHelper {

	/**
	 * 描述：初始化产品等级F7
	 * 
	 * @param prmtBox
	 *            F7控件；非空
	 * @param owner
	 *            UI对象宿主；非空
	 * @param filterInfo
	 *            过滤器；可空
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 * @see EcF7CostUtils.initIndustryF7
	 */
	public static void initProductGradeF7(KDBizPromptBox prmtBox, CoreUIObject owner, FilterInfo filterInfo) {
		prmtBox.setEditable(false);
		// 格式
		prmtBox.setEditFormat("$name$");
		prmtBox.setDisplayFormat("$name$");
		prmtBox.setCommitFormat("$number$;$name$");

		// 1、Query对象
		prmtBox.setQueryInfo("com.kingdee.eas.fdc.aimcost.app.ProductGradeF7Query");

		// 2、EntityView
		EntityViewInfo view = prmtBox.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		prmtBox.setEntityViewInfo(view);

		// 3、过滤器
		FilterInfo tempFilterInfo = view.getFilter();
		if (null == tempFilterInfo) {
			tempFilterInfo = new FilterInfo();
		}
		view.setFilter(tempFilterInfo);

		if (null != filterInfo) {
			try {
				tempFilterInfo.mergeFilter(filterInfo, "and");
			} catch (BOSException e) {
				owner.handUIException(e);
				SysUtil.abort();
			}
		}

		// 4、排序
		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

		// 5、查询项(选中行后的返回值查询项)
		SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add(new SelectorItemInfo("number"));
		// sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("*"));
		prmtBox.setSelectorCollection(sic);
	}

	/**
	 * 描述：初始化计量单位F7
	 * 
	 * @param prmtBox
	 *            F7控件；非空
	 * @param owner
	 *            UI对象宿主；非空
	 * @param filterInfo
	 *            过滤器；可空
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 * @see EcF7CostUtils.initIndustryF7
	 */
	public static void initMeasureUnitF7(KDBizPromptBox prmtBox, CoreUIObject owner, FilterInfo filterInfo) {
		prmtBox.setEditable(false);
		// 格式
		prmtBox.setEditFormat("$name$");
		prmtBox.setDisplayFormat("$name$");
		prmtBox.setCommitFormat("$number$;$name$");

		// 1、Query对象
		prmtBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");

		// 2、EntityView
		EntityViewInfo view = prmtBox.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		prmtBox.setEntityViewInfo(view);

		// 3、过滤器
		FilterInfo tempFilterInfo = view.getFilter();
		if (null == tempFilterInfo) {
			tempFilterInfo = new FilterInfo();
		}
		view.setFilter(tempFilterInfo);

		if (null != filterInfo) {
			try {
				tempFilterInfo.mergeFilter(filterInfo, "and");
			} catch (BOSException e) {
				owner.handUIException(e);
				SysUtil.abort();
			}
		}

		// 4、排序
		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

		// 5、查询项(选中行后的返回值查询项)
		SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add(new SelectorItemInfo("number"));
		// sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("*"));
		prmtBox.setSelectorCollection(sic);
	}

	/**
	 * 描述：初始化主数据三级产品类型F7
	 * 
	 * @param prmtBox
	 *            F7控件；非空
	 * @param owner
	 *            UI对象宿主；非空
	 * @param filterInfo
	 *            过滤器；可空
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 * @see EcF7CostUtils.initIndustryF7
	 */
	public static void initRcCPProductTypeEntryF7(KDBizPromptBox prmtBox, CoreUIObject owner, FilterInfo filterInfo) {
		prmtBox.setEditable(false);
		// 格式
		prmtBox.setEditFormat("$name$");
		prmtBox.setDisplayFormat("$name$");
		prmtBox.setCommitFormat("$number$;name$");

		// 1、Query对象
		prmtBox.setQueryInfo("com.kingdee.eas.fdc.aimcost.rc.app.RcCPProductTypeEntryF7Query");

		// 2、EntityView
		EntityViewInfo view = prmtBox.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		prmtBox.setEntityViewInfo(view);

		// 3、过滤器
		FilterInfo tempFilterInfo = view.getFilter();
		if (null == tempFilterInfo) {
			tempFilterInfo = new FilterInfo();
		}
		view.setFilter(tempFilterInfo);

		if (null != filterInfo) {
			try {
				tempFilterInfo.mergeFilter(filterInfo, "and");
			} catch (BOSException e) {
				owner.handUIException(e);
				SysUtil.abort();
			}
		}

		// 4、排序
		SorterItemCollection sorc = view.getSorter();
		sorc.add(new SorterItemInfo("productType.parent.number"));
		sorc.add(new SorterItemInfo("productType.number"));
		sorc.add(new SorterItemInfo("number"));

		// 5、查询项(选中行后的返回值查询项)
		SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add(new SelectorItemInfo("number"));
		// sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("*"));
		prmtBox.setSelectorCollection(sic);
	}

	/**
	 * 描述：初始化产品类型F7
	 * 
	 * @param prmtBox
	 *            F7控件；非空
	 * @param owner
	 *            UI对象宿主；非空
	 * @param productTypeIdCols
	 *            产品类型ID集合；可空
	 * @param filterInfo
	 *            过滤器；可空
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 * @see EcF7CostUtils.initIndustryF7
	 */
	public static void initProductTypeF7(KDBizPromptBox prmtBox, CoreUIObject owner, Collection productTypeIdCols,
			FilterInfo filterInfo) {
		prmtBox.setEditable(false);
		// 格式
		prmtBox.setEditFormat("$name$");
		prmtBox.setDisplayFormat("$name$");
		prmtBox.setCommitFormat("$number$;name$");

		// 1、Query对象
		prmtBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");

		// 2、EntityView
		EntityViewInfo view = prmtBox.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		prmtBox.setEntityViewInfo(view);

		// 3、过滤器
		FilterInfo tempFilterInfo = view.getFilter();
		if (null == tempFilterInfo) {
			tempFilterInfo = new FilterInfo();
		}
		view.setFilter(tempFilterInfo);

		Set productTypeIdSet = null;
		if (FdcCollectionUtil.isNotEmpty(productTypeIdCols)) {
			productTypeIdSet = new LinkedHashSet(productTypeIdCols);
		}
		if (FdcCollectionUtil.isNotEmpty(productTypeIdSet)) {
			tempFilterInfo.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			tempFilterInfo.getFilterItems().add(new FilterItemInfo("id", productTypeIdSet, CompareType.INCLUDE));
		}

		if (null != filterInfo) {
			try {
				tempFilterInfo.mergeFilter(filterInfo, "and");
			} catch (BOSException e) {
				owner.handUIException(e);
				SysUtil.abort();
			}
		}

		// 4、排序
		SorterItemCollection sorc = view.getSorter();
		sorc.add(new SorterItemInfo("parent.number"));
		sorc.add(new SorterItemInfo("number"));

		// 5、查询项(选中行后的返回值查询项)
		SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add(new SelectorItemInfo("number"));
		// sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("*"));
		prmtBox.setSelectorCollection(sic);
	}

	/**
	 * 描述：初始化项目指标分录F7
	 * 
	 * @param prmtBox
	 *            F7控件；非空
	 * @param owner
	 *            UI对象宿主；非空
	 * @param filterInfo
	 *            过滤器；可空
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 * @see EcF7CostUtils.initIndustryF7
	 */
	public static void initProjectTargetEntryF7(KDBizPromptBox prmtBox, CoreUIObject owner, FilterInfo filterInfo) {
		prmtBox.setEditable(false);
		// 格式
		prmtBox.setEditFormat("$name$");
		prmtBox.setDisplayFormat("$name$");
		prmtBox.setCommitFormat("$number$;$name$");

		// 1、Query对象
		prmtBox.setQueryInfo("com.kingdee.eas.fdc.basedata.mobile.app.F7ProjectTargetEntryQuery");

		// 2、EntityView
		EntityViewInfo view = prmtBox.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		prmtBox.setEntityViewInfo(view);

		// 3、过滤器
		FilterInfo tempFilterInfo = view.getFilter();
		if (null == tempFilterInfo) {
			tempFilterInfo = new FilterInfo();
		}
		// 默认启用
		tempFilterInfo.appendFilterItem("isEnable", Boolean.TRUE);
		view.setFilter(tempFilterInfo);

		if (null != filterInfo) {
			try {
				tempFilterInfo.mergeFilter(filterInfo, "and");
			} catch (BOSException e) {
				owner.handUIException(e);
				SysUtil.abort();
			}
		}

		// 4、排序
		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("seqNum");
		sorc.add(sort);

		// 5、查询项(选中行后的返回值查询项)
		SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add(new SelectorItemInfo("number"));
		// sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("unit.id"));
		sic.add(new SelectorItemInfo("unit.number"));
		sic.add(new SelectorItemInfo("unit.name"));
		prmtBox.setSelectorCollection(sic);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：初始化成本中心F7
	 * 
	 * @param bizPromptBox
	 * @param ui
	 * @param cuId
	 * @param rootUnitId
	 * @param isPermission
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-5
	 */
	public static void initCostCenterF7(KDBizPromptBox bizPromptBox, CoreUIObject ui, String cuId, String rootUnitId,
			boolean isPermission) {
		initCostCenterF7(bizPromptBox, ui, cuId, rootUnitId, isPermission, false);
	}

	/**
	 * 描述：初始化成本中心F7
	 * 
	 * @param bizPromptBox
	 * @param ui
	 * @param cuId
	 * @param rootUnitId
	 * @param isPermission
	 * @param isCUFilter
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-5
	 */
	public static void initCostCenterF7(KDBizPromptBox bizPromptBox, CoreUIObject ui, String cuId, String rootUnitId,
			boolean isPermission, boolean isCUFilter) {

		bizPromptBox.setEditable(true);
		bizPromptBox.setEditFormat("$number$");
		bizPromptBox.setDisplayFormat("$name$");
		bizPromptBox.setCommitFormat("$number$;$name$");
		bizPromptBox.getQueryAgent().setHasCUDefaultFilter(false);
		bizPromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterItemQuery");

		EntityViewInfo view = new EntityViewInfo();

		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

		FilterInfo filter = new FilterInfo();

		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("isFreeze", Boolean.FALSE));
		fic.add(new FilterItemInfo("isSealUp", Boolean.FALSE));
		fic.add(new FilterItemInfo("isBizUnit", Boolean.TRUE));

		if (cuId != null) {
			fic.add(new FilterItemInfo("CU.id", cuId));
		}

		CostCenterF7 f7 = new CostCenterF7(ui);
		// 用户组织范围内的组织才能选择
		if (isPermission) {
			try {
				Set authorizedOrgs = new HashSet();
				Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
						new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter,
						null, null, null);
				if (orgs != null) {
					Set orgSet = orgs.keySet();
					Iterator it = orgSet.iterator();
					while (it.hasNext()) {
						authorizedOrgs.add(it.next());
					}
				}
				FilterInfo filterID = new FilterInfo();
				filterID.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs, CompareType.INCLUDE));

				filter.mergeFilter(filterID, "and");

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			f7.disablePerm();
		}

		view.setFilter(filter);
		bizPromptBox.setEntityViewInfo(view);

		if (rootUnitId != null) {
			f7.setRootUnitID(rootUnitId);
		} else {
			f7.showCheckBoxOfShowingAllOUs();
			f7.setRootUnitID(cuId);
		}

		if (cuId != null) {
			f7.setCurrentCUID(cuId);
		}

		bizPromptBox.setSelector(f7);
		// 默认显示 全部不勾选
		if (isCUFilter) {
			f7.setIsCUFilter(isCUFilter);
		}
		SelectorItemCollection sic = bizPromptBox.getSelectorCollection();
		if (sic == null) {
			sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("number"));
			sic.add(new SelectorItemInfo("name"));
			bizPromptBox.setSelectorCollection(sic);
		}
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("displayName"));
	}

	/**
	 * 描述：初始化成本中心F7
	 * 
	 * @param bizPromptBox
	 * @param ui
	 * @param filter
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-5
	 */
	public static void initCostCenterF7(KDBizPromptBox bizPromptBox, CoreUIObject ui, FilterInfo filter) {

		bizPromptBox.setEditable(true);
		bizPromptBox.setEditFormat("$number$");
		bizPromptBox.setDisplayFormat("$name$");
		bizPromptBox.setCommitFormat("$number$;$name$");
		bizPromptBox.getQueryAgent().setHasCUDefaultFilter(false);
		bizPromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterItemQuery");

		EntityViewInfo view = new EntityViewInfo();

		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

		if (filter == null) {
			filter = new FilterInfo();
		}

		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("isFreeze", Boolean.FALSE));
		fic.add(new FilterItemInfo("isSealUp", Boolean.FALSE));
		fic.add(new FilterItemInfo("isBizUnit", Boolean.TRUE));

		view.setFilter(filter);
		bizPromptBox.setEntityViewInfo(view);
		SelectorItemCollection sic = bizPromptBox.getSelectorCollection();
		if (sic == null) {
			sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("number"));
			sic.add(new SelectorItemInfo("name"));
			bizPromptBox.setSelectorCollection(sic);
		}
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("displayName"));
	}
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	// 项目组织\发运组织
	public static void initProjectOrgF7(KDBizPromptBox bizPromptBox, CoreUIObject ui, String cuId, String rootUnitId,
			boolean isPermission) {
		initProjectOrgF7(bizPromptBox, ui, cuId, rootUnitId, isPermission, false);
	}

	// 项目组织\发运组织
	public static void initProjectOrgF7(KDBizPromptBox bizPromptBox, CoreUIObject ui, String cuId, String rootUnitId,
			boolean isPermission, boolean isCUFilter) {

		bizPromptBox.setEditable(true);
		bizPromptBox.setEditFormat("$number$");
		bizPromptBox.setDisplayFormat("$name$");
		bizPromptBox.setCommitFormat("$number$;$name$");
		bizPromptBox.getQueryAgent().setHasCUDefaultFilter(false);
		bizPromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.TransportItemQuery");

		EntityViewInfo view = new EntityViewInfo();

		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

		FilterInfo filter = new FilterInfo();

		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("isFreeze", new Integer(0)));
		fic.add(new FilterItemInfo("isSealUp", new Integer(0)));
		fic.add(new FilterItemInfo("isBizUnit", new Integer(1)));

		if (cuId != null) {
			fic.add(new FilterItemInfo("CU.id", cuId));
		}

		TransportF7 f7 = new TransportF7(ui);
		// 用户组织范围内的组织才能选择
		if (isPermission) {
			try {
				Set authorizedOrgs = new HashSet();
				Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
						new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.Transport,
						null, null, null);
				if (orgs != null) {
					Set orgSet = orgs.keySet();
					Iterator it = orgSet.iterator();
					while (it.hasNext()) {
						authorizedOrgs.add(it.next());
					}
				}
				FilterInfo filterID = new FilterInfo();
				filterID.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs, CompareType.INCLUDE));

				filter.mergeFilter(filterID, "and");

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			f7.disablePerm();
		}

		view.setFilter(filter);
		bizPromptBox.setEntityViewInfo(view);

		if (rootUnitId != null) {
			f7.setRootUnitID(rootUnitId);
		} else {
			f7.showCheckBoxOfShowingAllOUs();
			f7.setRootUnitID(cuId);
		}

		if (cuId != null) {
			f7.setCurrentCUID(cuId);
		}

		bizPromptBox.setSelector(f7);
		// 默认显示 全部不勾选
		if (isCUFilter) {
			f7.setIsCUFilter(isCUFilter);
		}
		SelectorItemCollection sic = bizPromptBox.getSelectorCollection();
		if (sic == null) {
			sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("number"));
			sic.add(new SelectorItemInfo("name"));
			bizPromptBox.setSelectorCollection(sic);
		}
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("displayName"));
	}

	// 项目组织\发运组织
	public static void initProjectOrgF7(KDBizPromptBox bizPromptBox, CoreUIObject ui, FilterInfo filter) {

		bizPromptBox.setEditable(true);
		bizPromptBox.setEditFormat("$number$");
		bizPromptBox.setDisplayFormat("$name$");
		bizPromptBox.setCommitFormat("$number$;$name$");
		bizPromptBox.getQueryAgent().setHasCUDefaultFilter(false);
		bizPromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.TransportItemQuery");

		EntityViewInfo view = new EntityViewInfo();

		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

		if (filter == null) {
			filter = new FilterInfo();
		}

		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("isFreeze", new Integer(0)));
		fic.add(new FilterItemInfo("isSealUp", new Integer(0)));
		fic.add(new FilterItemInfo("isBizUnit", new Integer(1)));

		view.setFilter(filter);
		bizPromptBox.setEntityViewInfo(view);
		SelectorItemCollection sic = bizPromptBox.getSelectorCollection();
		if (sic == null) {
			sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("number"));
			sic.add(new SelectorItemInfo("name"));
			bizPromptBox.setSelectorCollection(sic);
		}
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("displayName"));
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}
