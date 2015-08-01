/**
 * @(#)FdcF7InitHelper.java 1.0 2014-8-7
 * @author ����
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
 * ���������ز�F7��ʼ�� ����
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-8-7
 * @version 1.0, 2014-8-7
 * @see
 * @since JDK1.4
 */
public class FdcF7InitHelper {

	/**
	 * ��������ʼ����Ʒ�ȼ�F7
	 * 
	 * @param prmtBox
	 *            F7�ؼ����ǿ�
	 * @param owner
	 *            UI�����������ǿ�
	 * @param filterInfo
	 *            ���������ɿ�
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 * @see EcF7CostUtils.initIndustryF7
	 */
	public static void initProductGradeF7(KDBizPromptBox prmtBox, CoreUIObject owner, FilterInfo filterInfo) {
		prmtBox.setEditable(false);
		// ��ʽ
		prmtBox.setEditFormat("$name$");
		prmtBox.setDisplayFormat("$name$");
		prmtBox.setCommitFormat("$number$;$name$");

		// 1��Query����
		prmtBox.setQueryInfo("com.kingdee.eas.fdc.aimcost.app.ProductGradeF7Query");

		// 2��EntityView
		EntityViewInfo view = prmtBox.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		prmtBox.setEntityViewInfo(view);

		// 3��������
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

		// 4������
		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

		// 5����ѯ��(ѡ���к�ķ���ֵ��ѯ��)
		SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add(new SelectorItemInfo("number"));
		// sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("*"));
		prmtBox.setSelectorCollection(sic);
	}

	/**
	 * ��������ʼ��������λF7
	 * 
	 * @param prmtBox
	 *            F7�ؼ����ǿ�
	 * @param owner
	 *            UI�����������ǿ�
	 * @param filterInfo
	 *            ���������ɿ�
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 * @see EcF7CostUtils.initIndustryF7
	 */
	public static void initMeasureUnitF7(KDBizPromptBox prmtBox, CoreUIObject owner, FilterInfo filterInfo) {
		prmtBox.setEditable(false);
		// ��ʽ
		prmtBox.setEditFormat("$name$");
		prmtBox.setDisplayFormat("$name$");
		prmtBox.setCommitFormat("$number$;$name$");

		// 1��Query����
		prmtBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");

		// 2��EntityView
		EntityViewInfo view = prmtBox.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		prmtBox.setEntityViewInfo(view);

		// 3��������
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

		// 4������
		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

		// 5����ѯ��(ѡ���к�ķ���ֵ��ѯ��)
		SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add(new SelectorItemInfo("number"));
		// sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("*"));
		prmtBox.setSelectorCollection(sic);
	}

	/**
	 * ��������ʼ��������������Ʒ����F7
	 * 
	 * @param prmtBox
	 *            F7�ؼ����ǿ�
	 * @param owner
	 *            UI�����������ǿ�
	 * @param filterInfo
	 *            ���������ɿ�
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 * @see EcF7CostUtils.initIndustryF7
	 */
	public static void initRcCPProductTypeEntryF7(KDBizPromptBox prmtBox, CoreUIObject owner, FilterInfo filterInfo) {
		prmtBox.setEditable(false);
		// ��ʽ
		prmtBox.setEditFormat("$name$");
		prmtBox.setDisplayFormat("$name$");
		prmtBox.setCommitFormat("$number$;name$");

		// 1��Query����
		prmtBox.setQueryInfo("com.kingdee.eas.fdc.aimcost.rc.app.RcCPProductTypeEntryF7Query");

		// 2��EntityView
		EntityViewInfo view = prmtBox.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		prmtBox.setEntityViewInfo(view);

		// 3��������
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

		// 4������
		SorterItemCollection sorc = view.getSorter();
		sorc.add(new SorterItemInfo("productType.parent.number"));
		sorc.add(new SorterItemInfo("productType.number"));
		sorc.add(new SorterItemInfo("number"));

		// 5����ѯ��(ѡ���к�ķ���ֵ��ѯ��)
		SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add(new SelectorItemInfo("number"));
		// sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("*"));
		prmtBox.setSelectorCollection(sic);
	}

	/**
	 * ��������ʼ����Ʒ����F7
	 * 
	 * @param prmtBox
	 *            F7�ؼ����ǿ�
	 * @param owner
	 *            UI�����������ǿ�
	 * @param productTypeIdCols
	 *            ��Ʒ����ID���ϣ��ɿ�
	 * @param filterInfo
	 *            ���������ɿ�
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 * @see EcF7CostUtils.initIndustryF7
	 */
	public static void initProductTypeF7(KDBizPromptBox prmtBox, CoreUIObject owner, Collection productTypeIdCols,
			FilterInfo filterInfo) {
		prmtBox.setEditable(false);
		// ��ʽ
		prmtBox.setEditFormat("$name$");
		prmtBox.setDisplayFormat("$name$");
		prmtBox.setCommitFormat("$number$;name$");

		// 1��Query����
		prmtBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");

		// 2��EntityView
		EntityViewInfo view = prmtBox.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		prmtBox.setEntityViewInfo(view);

		// 3��������
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

		// 4������
		SorterItemCollection sorc = view.getSorter();
		sorc.add(new SorterItemInfo("parent.number"));
		sorc.add(new SorterItemInfo("number"));

		// 5����ѯ��(ѡ���к�ķ���ֵ��ѯ��)
		SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add(new SelectorItemInfo("number"));
		// sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("*"));
		prmtBox.setSelectorCollection(sic);
	}

	/**
	 * ��������ʼ����Ŀָ���¼F7
	 * 
	 * @param prmtBox
	 *            F7�ؼ����ǿ�
	 * @param owner
	 *            UI�����������ǿ�
	 * @param filterInfo
	 *            ���������ɿ�
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 * @see EcF7CostUtils.initIndustryF7
	 */
	public static void initProjectTargetEntryF7(KDBizPromptBox prmtBox, CoreUIObject owner, FilterInfo filterInfo) {
		prmtBox.setEditable(false);
		// ��ʽ
		prmtBox.setEditFormat("$name$");
		prmtBox.setDisplayFormat("$name$");
		prmtBox.setCommitFormat("$number$;$name$");

		// 1��Query����
		prmtBox.setQueryInfo("com.kingdee.eas.fdc.basedata.mobile.app.F7ProjectTargetEntryQuery");

		// 2��EntityView
		EntityViewInfo view = prmtBox.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		prmtBox.setEntityViewInfo(view);

		// 3��������
		FilterInfo tempFilterInfo = view.getFilter();
		if (null == tempFilterInfo) {
			tempFilterInfo = new FilterInfo();
		}
		// Ĭ������
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

		// 4������
		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("seqNum");
		sorc.add(sort);

		// 5����ѯ��(ѡ���к�ķ���ֵ��ѯ��)
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
	 * ��������ʼ���ɱ�����F7
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
	 * ��������ʼ���ɱ�����F7
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
		// �û���֯��Χ�ڵ���֯����ѡ��
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
		// Ĭ����ʾ ȫ������ѡ
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
	 * ��������ʼ���ɱ�����F7
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

	// ��Ŀ��֯\������֯
	public static void initProjectOrgF7(KDBizPromptBox bizPromptBox, CoreUIObject ui, String cuId, String rootUnitId,
			boolean isPermission) {
		initProjectOrgF7(bizPromptBox, ui, cuId, rootUnitId, isPermission, false);
	}

	// ��Ŀ��֯\������֯
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
		// �û���֯��Χ�ڵ���֯����ѡ��
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
		// Ĭ����ʾ ȫ������ѡ
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

	// ��Ŀ��֯\������֯
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
