/*
 * @(#)FdcEntityViewUtil.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.basedata.util;

import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;

/**
 * ���ز� ʵ����ͼ������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-10
 * @see
 * @since 1.4
 */
public class FdcEntityViewUtil {
	/**
	 * ����һ������selectorԪ�ص�EntityViewInfo
	 * @param selectors 
	 * @return
	 */
	public static EntityViewInfo getView(String[] selectors) {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		if (selectors != null) {
			for (int i = 0; i < selectors.length; i++) {
				sic.add(new SelectorItemInfo(selectors[i]));
			}
		}
		view.setSelector(sic);
		return view;
	}

	/**
	 * ����SelectorItemCollection
	 * @param selectors
	 * @return
	 */
	public static SelectorItemCollection getSelector(String[] selectors) {
		SelectorItemCollection sic = new SelectorItemCollection();
		if (selectors != null) {
			for (int i = 0; i < selectors.length; i++) {
				sic.add(new SelectorItemInfo(selectors[i]));
			}
		}
		return sic;
	}

	/**
	 * ����SorterItemCollection
	 * @param sortors
	 * @return
	 */
	public static SorterItemCollection getSorter(String[] sortors) {
		SorterItemCollection sc = new SorterItemCollection();
		if (sortors != null) {
			for (int i = 0; i < sortors.length; i++) {
				sc.add(new SorterItemInfo(sortors[i]));
			}
		}
		return sc;
	}

	/**
	 * ����FilterInfo
	 * @param items
	 * @return
	 */
	public static FilterInfo getFilter(Map items) {
		FilterInfo filter = new FilterInfo();
		Iterator keySet = items.keySet().iterator();
		while (keySet.hasNext()) {
			String key = (String) keySet.next();
			filter.getFilterItems().add(new FilterItemInfo(key, items.get(key)));
		}
		return filter;
	}
}
