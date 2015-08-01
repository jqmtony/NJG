/**
 * @(#)ProjectTargetShowUtil.java 1.0 2014-11-4
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.common.EASBizException;

/**
 * ��������Ŀָ��չʾ ������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-11-4
 * @version 1.0, 2014-11-4
 * @see
 * @since JDK1.4
 */
public class ProjectTargetShowUtil {

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * ȡ�õ��ݽӿ�
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static IProjectTargetShowBill getBillInstance(Context ctx) throws EASBizException, BOSException {
		IProjectTargetShowBill iProjectTargetShowBill = null;

		if (null == ctx) {
			iProjectTargetShowBill = ProjectTargetShowBillFactory.getRemoteInstance();
		} else {
			iProjectTargetShowBill = ProjectTargetShowBillFactory.getLocalInstance(ctx);
		}

		return iProjectTargetShowBill;
	}

	/**
	 * ȡ�÷�¼�ӿ�
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static IProjectTargetShowEntry getEntryInstance(Context ctx) throws EASBizException, BOSException {
		IProjectTargetShowEntry iProjectTargetShowEntry = null;

		if (null == ctx) {
			iProjectTargetShowEntry = ProjectTargetShowEntryFactory.getRemoteInstance();
		} else {
			iProjectTargetShowEntry = ProjectTargetShowEntryFactory.getLocalInstance(ctx);
		}

		return iProjectTargetShowEntry;
	}

	/**
	 * ȡ��ϸĿ�ӿ�
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static IProjectTargetShowItem getItemInstance(Context ctx) throws EASBizException, BOSException {
		IProjectTargetShowItem iProjectTargetShowItem = null;

		if (null == ctx) {
			iProjectTargetShowItem = ProjectTargetShowItemFactory.getRemoteInstance();
		} else {
			iProjectTargetShowItem = ProjectTargetShowItemFactory.getLocalInstance(ctx);
		}

		return iProjectTargetShowItem;
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * ȡ�õ��ݼ���
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param filter
	 *            ������������Ϊ��
	 * @return
	 * @throws Exception
	 */
	public static ProjectTargetShowBillCollection getBillCols(Context ctx, FilterInfo filter) throws Exception {
		ProjectTargetShowBillCollection cols = new ProjectTargetShowBillCollection();

		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection selector = new SelectorItemCollection();
		view.setSelector(selector);
		selector.add("*");

		view.setFilter(filter);

		// SorterItemCollection sorter = view.getSorter();
		// sorter.add(new SorterItemInfo("seqNum"));

		// ȡ�õ��ݽӿ�
		IProjectTargetShowBill iProjectTargetShowBill = getBillInstance(ctx);
		// ȡ�õ��ݼ���
		cols = iProjectTargetShowBill.getProjectTargetShowBillCollection(view);

		return cols;
	}

	/**
	 * ȡ�÷�¼����
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param filter
	 *            ������������Ϊ��
	 * @return
	 * @throws Exception
	 */
	public static ProjectTargetShowEntryCollection getEntryCols(Context ctx, FilterInfo filter) throws Exception {
		ProjectTargetShowEntryCollection cols = new ProjectTargetShowEntryCollection();

		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection selector = new SelectorItemCollection();
		view.setSelector(selector);
		selector.add("*");
		selector.add("bill.id");
		// selector.add("bill.seqNum");
		selector.add("bill.number");
		selector.add("bill.name");

		view.setFilter(filter);

		SorterItemCollection sorter = view.getSorter();
		// sorter.add(new SorterItemInfo("bill.seqNum"));
		sorter.add(new SorterItemInfo("seqNum"));

		// ȡ�÷�¼�ӿ�
		IProjectTargetShowEntry iProjectTargetShowEntry = getEntryInstance(ctx);
		// ȡ�÷�¼����
		cols = iProjectTargetShowEntry.getProjectTargetShowEntryCollection(view);

		return cols;
	}

	/**
	 * ȡ��ϸĿ����
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param filter
	 *            ������������Ϊ��
	 * @return
	 * @throws Exception
	 */
	public static ProjectTargetShowItemCollection getItemCols(Context ctx, FilterInfo filter) throws Exception {
		ProjectTargetShowItemCollection cols = new ProjectTargetShowItemCollection();

		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection selector = new SelectorItemCollection();
		view.setSelector(selector);
		selector.add("*");
		selector.add("bill.id");
		// selector.add("bill.seqNum");
		selector.add("bill.number");
		selector.add("bill.name");
		selector.add("parent.id");
		selector.add("parent.seqNum");
		selector.add("parent.number");
		selector.add("parent.name");
		selector.add("projectTargetEntry.*");

		view.setFilter(filter);

		SorterItemCollection sorter = view.getSorter();
		// sorter.add(new SorterItemInfo("bill.seqNum"));
		sorter.add(new SorterItemInfo("parent.seqNum"));
		sorter.add(new SorterItemInfo("seqNum"));

		// ȡ��ϸĿ�ӿ�
		IProjectTargetShowItem iProjectTargetShowItem = getItemInstance(ctx);
		// ȡ��ϸĿ����
		cols = iProjectTargetShowItem.getProjectTargetShowItemCollection(view);

		return cols;
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////
	
}
