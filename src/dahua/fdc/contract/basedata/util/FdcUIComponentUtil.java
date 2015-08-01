/**
 * @(#)FdcUIComponentUtil.java 1.0 2014-3-19
 * @author ����
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
 * ���������ز�UI���������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcUIComponentUtil {

	/**
	 * ��� ӳ��
	 */
	private static final Map COMPONENT_MAP = new LinkedHashMap();

	/**
	 * ȡ�� ListUI���
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
	 * ȡ�� EditUI���
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
	 * ��ʼ�� UI���
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

			// ҵ��ӿڶ��󣬹����ú�̨����ʹ��
			ICoreBase bizInterface = FDCClientHelper.getBizInterface(coreUI);
			// ҵ��ӿڶ�Ӧ��BOSType����
			BOSObjectType bizType = (null != bizInterface) ? bizInterface.getType() : null;
			// ҵ��ӿڶ�Ӧ��BOSType�ַ���
			String entityBOSType = (null != bizType) ? bizType.toString() : null;
			// �༭��������
			String editUIName = FDCClientHelper.getEditUIName(coreUI);
			// �༭����Ĭ�ϴ򿪷�ʽ
			String editUIModal = FDCClientHelper.getEditUIModal(coreUI);

			// has*����

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
	 * ��ʼ�� ListUI���
	 * 
	 * @param listUI
	 */
	private static FdcListUIComponent _initUIComponent(ListUI listUI) {
		FdcListUIComponent fdcUIComponent = null;

		try {
			// ��ʼ�� UI���
			FdcListUIComponent fdcListUIComponent = (FdcListUIComponent) _initUIComponent((CoreUI) listUI);

			if (null != fdcListUIComponent) {
				fdcListUIComponent.setListUI(listUI);
				
				// ȡ������ѯPK
				IMetaDataPK mainQueryPK = listUI.getMainQueryPK();
				// ȡ���״��ѯPK
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
	 * ��ʼ�� EditUI���
	 * 
	 * @param editUI
	 */
	private static FdcEditUIComponent _initUIComponent(EditUI editUI) {
		FdcEditUIComponent fdcUIComponent = null;

		try {
			// ��ʼ�� UI���
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
