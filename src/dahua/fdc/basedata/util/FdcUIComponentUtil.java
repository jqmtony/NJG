/**
 * @(#)FdcUIComponentUtil.java 1.0 2014-3-19
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.fdc.basedata.FdcEditUIComponent;
import com.kingdee.eas.fdc.basedata.FdcListUIComponent;
import com.kingdee.eas.fdc.basedata.FdcUIComponent;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.eas.framework.client.ListUI;

/**
 * 描述：房地产UI组件工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcUIComponentUtil {

	/**
	 * 组件 映射
	 */
	private static final Map COMPONENT_MAP = new LinkedHashMap();

	/**
	 * 取得 ListUI组件
	 * 
	 * @param listUI
	 * @return
	 */
	public static FdcListUIComponent getUIComponent(ListUI listUI) {
		FdcListUIComponent fdcUIComponent = (FdcListUIComponent) COMPONENT_MAP.get(listUI.getClass());
		if (null == fdcUIComponent) {
			fdcUIComponent = _initUIComponent(listUI);
		}

		return fdcUIComponent;
	}

	/**
	 * 取得 EditUI组件
	 * 
	 * @param editUI
	 * @return
	 */
	public static FdcEditUIComponent getUIComponent(EditUI editUI) {
		FdcEditUIComponent fdcUIComponent = (FdcEditUIComponent) COMPONENT_MAP.get(editUI.getClass());
		if (null == fdcUIComponent) {
			fdcUIComponent = _initUIComponent(editUI);
		}

		return fdcUIComponent;
	}

	/**
	 * 初始化 UI组件
	 * 
	 * @param coreUI
	 */
	private static FdcUIComponent _initUIComponent(CoreUI coreUI) {
		FdcUIComponent fdcUIComponent = null;

		try {
			FdcUIComponent tempFdcUIComponent = null;
			if (coreUI instanceof ListUI) {
				tempFdcUIComponent = new FdcListUIComponent();
			} else if (coreUI instanceof EditUI) {
				tempFdcUIComponent = new FdcEditUIComponent();
			}

			// 业务接口对象，供调用后台方法使用
			ICoreBase bizInterface = FDCClientHelper.getBizInterface(coreUI);
			// 业务接口对应的BOSType对象
			BOSObjectType bizType = (null != bizInterface) ? bizInterface.getType() : null;
			// 业务接口对应的BOSType字符串
			String entityBOSType = (null != bizType) ? bizType.toString() : null;
			// 编辑界面名称
			String editUIName = FDCClientHelper.getEditUIName(coreUI);
			// 编辑界面默认打开方式
			String editUIModal = FDCClientHelper.getEditUIModal(coreUI);

			// has*待定

			tempFdcUIComponent.setBizInterface(bizInterface);
			tempFdcUIComponent.setBizType(bizType);
			tempFdcUIComponent.setEntityBOSType(entityBOSType);
			tempFdcUIComponent.setEditUIName(editUIName);
			tempFdcUIComponent.setEditUIModal(editUIModal);

			COMPONENT_MAP.put(coreUI.getClass(), tempFdcUIComponent);
			fdcUIComponent = tempFdcUIComponent;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fdcUIComponent;
	}

	/**
	 * 初始化 ListUI组件
	 * 
	 * @param listUI
	 */
	private static FdcListUIComponent _initUIComponent(ListUI listUI) {
		FdcListUIComponent fdcUIComponent = null;

		try {
			// 初始化 UI组件
			FdcListUIComponent fdcListUIComponent = (FdcListUIComponent) _initUIComponent((CoreUI) listUI);

			if (null != fdcListUIComponent) {
				fdcListUIComponent.setListUI(listUI);
				
				// 取得主查询PK
				IMetaDataPK mainQueryPK = listUI.getMainQueryPK();
				// 取得套打查询PK
				IMetaDataPK printQueryPK = FDCClientHelper.getPrintQueryPK(listUI);

				fdcListUIComponent.setMainQueryPK(mainQueryPK);
				fdcListUIComponent.setPrintQueryPK(printQueryPK);

				fdcUIComponent = fdcListUIComponent;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fdcUIComponent;
	}

	/**
	 * 初始化 EditUI组件
	 * 
	 * @param editUI
	 */
	private static FdcEditUIComponent _initUIComponent(EditUI editUI) {
		FdcEditUIComponent fdcUIComponent = null;

		try {
			// 初始化 UI组件
			FdcEditUIComponent fdcEditUIComponent = (FdcEditUIComponent) _initUIComponent((CoreUI) editUI);

			if (null != fdcEditUIComponent) {
				fdcEditUIComponent.setEditUI((EditUI) editUI);

				fdcUIComponent = fdcEditUIComponent;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fdcUIComponent;
	}

}
