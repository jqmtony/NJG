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
	
	/** ��Դ�ļ�·�� */
	public final static String resource = "com.kingdee.eas.fdc.aimcost.AimCostResource";
	
	/**
	 * @description ȡ��Դ�ļ�����
	 * @author ��ΰ
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
	 * @description �����Ӽ���Ŀ
	 * @author ��ΰ
	 * @createDate 2011-8-12
	 * @param info
	 *            ����Ŀ����
	 * @param objectId
	 *            ��Ŀ������֯Id
	 * @param acctChidrenMap
	 *            �Ӽ���Ŀ����
	 * @return CostAccountCollection �Ӽ�����
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	public static CostAccountCollection getCostAccountCollection(CostAccountInfo info,
			String objectId, final Map acctChidrenMap) throws BOSException {
		CostAccountCollection cion = null;
 
		// �Ѿ����ڵ�ֱ�ӷ���
		cion = (CostAccountCollection) acctChidrenMap.get(info.getId().toString());
		if (cion != null && cion.size() > 0) {
			return cion;
		}
		// �ж��Ƿ�����Ŀid�������Ŀid�͸�����Ŀ��ѯ������Ǿ�����֯��ѯ
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

		// ȡ�������¼�����
		cion = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		// �Գ�����Ϊkey���뻺����
		acctChidrenMap.put(info.getId().toString(), cion);
		return cion;
	}
}
