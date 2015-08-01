/**
 * @(#)ContractCodingUtil.java 1.0 2013-11-21
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */
package com.kingdee.eas.fdc.contract.util;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractWOTCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractWOTCodingTypeFactory;
import com.kingdee.eas.fdc.basedata.IContractCodingType;
import com.kingdee.eas.fdc.basedata.IContractWOTCodingType;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcTreeBaseUtil;

/**
 * ��������ͬ���빤����
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2013-11-21
 * @version 1.0, 2013-11-21
 * @see
 * @since JDK1.4
 */
public class ContractCodingUtil {

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ������ȡ�ø����ͬ����
	 * 
	 * @param ctx
	 *            Ӧ�������ģ��ɿ�
	 * @param contractTypeInfo
	 *            ��ͬ����
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-21
	 */
	public static ContractTypeInfo getParentContractType(Context ctx, ContractTypeInfo contractTypeInfo)
			throws BOSException, EASBizException {
		ContractTypeInfo parentContractTypeInfo = (contractTypeInfo != null) ? contractTypeInfo.getParent() : null;

		// �����ϼ�
		if (null != contractTypeInfo && null == parentContractTypeInfo) {
			try {
				// ȡ��ֱ�Ӹ���TreeBase
				parentContractTypeInfo = (ContractTypeInfo) FdcTreeBaseUtil.getDirectParentTreeBaseInfo(ctx,
						contractTypeInfo.getBOSType().toString(), null, contractTypeInfo, true);
			} catch (BOSException e) {
				throw (BOSException) e;
			} catch (EASBizException e) {
				throw new BOSException(e);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}

		return parentContractTypeInfo;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ������ȡ�ú�ͬ�������ͽӿ�ʵ��
	 * 
	 * @param ctx
	 *            Ӧ�������ģ��ɿ�
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-21
	 */
	public static IContractCodingType getContractCodingTypeInstance(Context ctx) throws BOSException, EASBizException {
		IContractCodingType iContractCodingType = null;

		if (null != ctx) {
			iContractCodingType = ContractCodingTypeFactory.getLocalInstance(ctx);
		} else {
			iContractCodingType = ContractCodingTypeFactory.getRemoteInstance();
		}

		return iContractCodingType;
	}

	/**
	 * ������ȡ�ú�ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
	 * <p>
	 * 1�����Ȳ�ѯ����ͬ���͡�������ͬ���ʡ�����ָ��ֵ����� <br>
	 * 2���ٲ�ѯ����ͬ���͡�Ϊָ��ֵ������ͬ���ʡ�Ϊ��OLD(ȫ��)������� <br>
	 * 3���ٲ�ѯ���ࡰ��ͬ���͡�Ϊָ��ֵ����� <br>
	 * 4���ٲ�ѯ����ͬ���͡�Ϊ��NULL(ȫ��)��������ͬ���ʡ�Ϊָ��ֵ����� <br>
	 * 5���ٲ�ѯ����ͬ���͡�Ϊ��NULL(ȫ��)��������ͬ���ʡ�Ϊ��OLD(ȫ��)������� <br>
	 * <p>
	 * 6.1������ͬ���͡�Ҫ֧�ֵݹ顣���磺����Ҫ�ҵ�ǰ�������û���ҵ�����Ҫ���ҵ��ϼ��������û���ҵ�����Ҫ�����ϼ����������� <br>
	 * 6.2���ݹ���ҡ���ͬ���͡��ϼ�������ͬ���͡������νṹ��ͨ��ֻ��3-4�������Բ�������������⣩ <br>
	 * 
	 * @param ctx
	 *            Ӧ�������ģ��ɿ�
	 * @param contractTypeInfo
	 *            ��ͬ���ͣ��ɿ�
	 * @param contractProperty
	 *            ��ͬ���ʣ��ǿ�
	 * @param thirdType
	 *            ����״̬��������鵵�����ǿ�
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-21
	 */
	public static ContractCodingTypeCollection getContractCodingTypeCollection(Context ctx,
			ContractTypeInfo contractTypeInfo, String contractProperty, String thirdType) throws BOSException,
			EASBizException {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);

		String contractTypeId = (contractTypeInfo == null) ? null : contractTypeInfo.getId().toString();
		// ��ͬ����
		FilterItemInfo contractTypeFilter = new FilterItemInfo("contractType.id", contractTypeId);
		filter.getFilterItems().add(contractTypeFilter);

		// ��ͬ����
		FilterItemInfo contractPropertFilter = new FilterItemInfo("secondType", contractProperty);
		filter.getFilterItems().add(contractPropertFilter);

		// ����״̬
		filter.getFilterItems().add(new FilterItemInfo("thirdType", thirdType));

		IContractCodingType iContractCodingType = getContractCodingTypeInstance(ctx);

		// 1�����Ȳ�ѯ����ͬ���͡�������ͬ���ʡ�����ָ��ֵ�����
		ContractCodingTypeCollection codingTypeCollection = iContractCodingType.getContractCodingTypeCollection(evi);

		// 2���ٲ�ѯ����ͬ���͡�Ϊָ��ֵ������ͬ���ʡ�Ϊ��OLD(ȫ��)�������
		if (contractTypeInfo != null) {
			contractTypeFilter.setCompareValue(contractTypeId);
			contractPropertFilter.setCompareValue(ContractSecondTypeEnum.OLD_VALUE);
			codingTypeCollection.addCollection(iContractCodingType.getContractCodingTypeCollection(evi));
		}

		// 3���ٲ�ѯ���ࡰ��ͬ���͡�Ϊָ��ֵ�����
		// ȡ�ø����ͬ����
		ContractTypeInfo parentContractTypeInfo = ContractCodingUtil.getParentContractType(ctx, contractTypeInfo);
		if (null != parentContractTypeInfo) {
			contractTypeInfo.setParent(parentContractTypeInfo);

			// ȡ�ø��ࡰ��ͬ���͡�������ͬ���ʡ�����ָ��ֵ�����
			ContractCodingTypeCollection parentCodingTypeCollection = ContractCodingUtil
					.getSelfContractCodingTypeCollection(ctx, parentContractTypeInfo, contractProperty, thirdType);
			codingTypeCollection.addCollection(parentCodingTypeCollection);

			// ȡ�ø��ࡰ��ͬ���͡�������ͬ���ʡ�Ϊ��OLD(ȫ��)�������
			parentCodingTypeCollection = ContractCodingUtil.getSelfContractCodingTypeCollection(ctx,
					parentContractTypeInfo, ContractSecondTypeEnum.OLD_VALUE, thirdType);
			codingTypeCollection.addCollection(parentCodingTypeCollection);
		}

		// 4���ٲ�ѯ����ͬ���͡�Ϊ��NULL(ȫ��)��������ͬ���ʡ�Ϊָ��ֵ�����
		contractTypeFilter.setCompareValue(null);
		contractPropertFilter.setCompareValue(contractProperty);
		codingTypeCollection.addCollection(iContractCodingType.getContractCodingTypeCollection(evi));

		// 5���ٲ�ѯ����ͬ���͡�Ϊ��NULL(ȫ��)��������ͬ���ʡ�Ϊ��OLD(ȫ��)�������
		contractTypeFilter.setCompareValue(null);
		contractPropertFilter.setCompareValue(ContractSecondTypeEnum.OLD_VALUE);
		codingTypeCollection.addCollection(iContractCodingType.getContractCodingTypeCollection(evi));

		// 6.1����ͬ��������Ҫ֧�ֵݹ顣���磺����Ҫ�ҵ�ǰ�������û���ҵ�����Ҫ�ҵ��ϼ��������û���ҵ�����Ҫ�����ϼ�����������
		// 7.2���ݹ���ҡ���ͬ���͡��ϼ�������ͬ���͡������νṹ��ͨ��ֻ��3-4�������Բ�������������⣩
		if (null != parentContractTypeInfo) {
			// ȡ�ø����ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
			ContractCodingTypeCollection parentCodingTypeCollection = (ContractCodingTypeCollection) getContractCodingTypeCollection(
					ctx, parentContractTypeInfo, contractProperty, thirdType);

			codingTypeCollection.addCollection(parentCodingTypeCollection);
		}

		// ����������е��ظ�ֵ��Nullֵ
		FdcObjectCollectionUtil.clearDuplicateAndNull(codingTypeCollection);

		return codingTypeCollection;
	}

	/**
	 * ������ȡ�������ͬ�������Ͷ��󼯺ϣ���ȷ��
	 * 
	 * @param ctx
	 *            Ӧ�������ģ��ɿ�
	 * @param contractTypeInfo
	 *            ��ͬ���ͣ��ɿ�
	 * @param contractProperty
	 *            ��ͬ���ʣ��ǿ�
	 * @param thirdType
	 *            ����״̬��������鵵�����ǿ�
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-21
	 */
	public static ContractCodingTypeCollection getSelfContractCodingTypeCollection(Context ctx,
			ContractTypeInfo contractTypeInfo, String contractProperty, String thirdType) throws BOSException,
			EASBizException {
		ContractCodingTypeCollection codingTypeCollection = new ContractCodingTypeCollection();

		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);

		String contractTypeId = (contractTypeInfo == null) ? null : contractTypeInfo.getId().toString();
		// ��ͬ����
		FilterItemInfo contractTypeFilter = new FilterItemInfo("contractType.id", contractTypeId);
		filter.getFilterItems().add(contractTypeFilter);

		// ��ͬ����
		FilterItemInfo contractPropertFilter = new FilterItemInfo("secondType", contractProperty);
		filter.getFilterItems().add(contractPropertFilter);

		// ����״̬
		filter.getFilterItems().add(new FilterItemInfo("thirdType", thirdType));

		IContractCodingType iContractCodingType = getContractCodingTypeInstance(ctx);
		// ��ѯ����ͬ���͡�������ͬ���ʡ�����ָ��ֵ�����
		codingTypeCollection = iContractCodingType.getContractCodingTypeCollection(evi);

		return codingTypeCollection;
	}

	/**
	 * ������ȡ�ø����ͬ�������Ͷ��󼯺ϣ���ȷ��
	 * 
	 * @param ctx
	 *            Ӧ�������ģ��ɿ�
	 * @param contractTypeInfo
	 *            ��ͬ���ͣ��ɿ�
	 * @param contractProperty
	 *            ��ͬ���ʣ��ǿ�
	 * @param thirdType
	 *            ����״̬��������鵵�����ǿ�
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-21
	 */
	public static ContractCodingTypeCollection getParentContractCodingTypeCollection(Context ctx,
			ContractTypeInfo contractTypeInfo, String contractProperty, String thirdType) throws BOSException,
			EASBizException {
		ContractCodingTypeCollection codingTypeCollection = new ContractCodingTypeCollection();

		// ȡ�ø����ͬ����
		ContractTypeInfo parentContractTypeInfo = getParentContractType(ctx, contractTypeInfo);
		if (null != parentContractTypeInfo) {
			codingTypeCollection = getSelfContractCodingTypeCollection(ctx, parentContractTypeInfo, contractProperty,
					thirdType);
		}

		return codingTypeCollection;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ������ȡ�����ı���ͬ�������ͽӿ�ʵ��
	 * 
	 * @param ctx
	 *            Ӧ�������ģ��ɿ�
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-21
	 */
	public static IContractWOTCodingType getContractWOTCodingTypeInstance(Context ctx) throws BOSException,
			EASBizException {
		IContractWOTCodingType iContractWOTCodingType = null;

		if (null != ctx) {
			iContractWOTCodingType = ContractWOTCodingTypeFactory.getLocalInstance(ctx);
		} else {
			iContractWOTCodingType = ContractWOTCodingTypeFactory.getRemoteInstance();
		}

		return iContractWOTCodingType;
	}

	/**
	 * ������ȡ�����ı���ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
	 * <p>
	 * 1�����Ȳ�ѯ����ͬ���͡���ָ��ֵ����� <br>
	 * 2���ٲ�ѯ���ࡰ��ͬ���͡�Ϊָ��ֵ����� <br>
	 * 3���ٲ�ѯ����ͬ���͡�Ϊ��NULL(ȫ��)������� <br>
	 * <p>
	 * 4.1������ͬ���͡�Ҫ֧�ֵݹ顣���磺����Ҫ�ҵ�ǰ�������û���ҵ�����Ҫ���ҵ��ϼ��������û���ҵ�����Ҫ�����ϼ����������� <br>
	 * 4.2���ݹ���ҡ���ͬ���͡��ϼ�������ͬ���͡������νṹ��ͨ��ֻ��3-4�������Բ�������������⣩ <br>
	 * 
	 * @param ctx
	 *            Ӧ�������ģ��ɿ�
	 * @param contractTypeInfo
	 *            ��ͬ���ͣ��ɿ�
	 * @param thirdType
	 *            ����״̬��������鵵�����ǿ�
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-21
	 */
	public static ContractWOTCodingTypeCollection getContractWOTCodingTypeCollection(Context ctx,
			ContractTypeInfo contractTypeInfo, String thirdType) throws BOSException, EASBizException {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);

		String contractTypeId = (contractTypeInfo == null) ? null : contractTypeInfo.getId().toString();
		// ��ͬ����
		FilterItemInfo contractTypeFilter = new FilterItemInfo("contractType.id", contractTypeId);
		filter.getFilterItems().add(contractTypeFilter);

		// ����״̬
		filter.getFilterItems().add(new FilterItemInfo("thirdType", thirdType));

		IContractWOTCodingType iContractWOTCodingType = getContractWOTCodingTypeInstance(ctx);

		// 1�����Ȳ�ѯ����ͬ���͡���ָ��ֵ�����
		ContractWOTCodingTypeCollection codingTypeCollection = iContractWOTCodingType
				.getContractWOTCodingTypeCollection(evi);

		// 2���ٲ�ѯ���ࡰ��ͬ���͡�Ϊָ��ֵ�����
		// ȡ�ø����ͬ����
		ContractTypeInfo parentContractTypeInfo = ContractCodingUtil.getParentContractType(ctx, contractTypeInfo);
		if (null != parentContractTypeInfo) {
			contractTypeInfo.setParent(parentContractTypeInfo);

			// ȡ�ø��ࡰ��ͬ���͡���ָ��ֵ�����
			ContractWOTCodingTypeCollection parentCodingTypeCollection = ContractCodingUtil
					.getSelfContractWOTCodingTypeCollection(ctx, parentContractTypeInfo, thirdType);
			codingTypeCollection.addCollection(parentCodingTypeCollection);
		}

		// 3���ٲ�ѯ����ͬ���͡�Ϊ��NULL(ȫ��)�������
		contractTypeFilter.setCompareValue(null);
		codingTypeCollection.addCollection(iContractWOTCodingType.getContractWOTCodingTypeCollection(evi));

		// 4.1�����ı���ͬ��������Ҫ֧�ֵݹ顣���磺����Ҫ�ҵ�ǰ�������û���ҵ�����Ҫ�ҵ��ϼ��������û���ҵ�����Ҫ�����ϼ�����������
		// 4.2���ݹ���ҡ���ͬ���͡��ϼ�������ͬ���͡������νṹ��ͨ��ֻ��3-4�������Բ�������������⣩
		if (null != parentContractTypeInfo) {
			// ȡ�ø������ı���ͬ�������Ͷ��󼯺ϣ���ȷ��ģ�����ݹ飩
			ContractWOTCodingTypeCollection parentCodingTypeCollection = (ContractWOTCodingTypeCollection) getContractWOTCodingTypeCollection(
					ctx, parentContractTypeInfo, thirdType);

			codingTypeCollection.addCollection(parentCodingTypeCollection);
		}

		// ����������е��ظ�ֵ��Nullֵ
		FdcObjectCollectionUtil.clearDuplicateAndNull(codingTypeCollection);

		return codingTypeCollection;
	}

	/**
	 * ������ȡ���������ı���ͬ�������Ͷ��󼯺ϣ���ȷ��
	 * 
	 * @param ctx
	 *            Ӧ�������ģ��ɿ�
	 * @param contractTypeInfo
	 *            ��ͬ���ͣ��ɿ�
	 * @param thirdType
	 *            ����״̬��������鵵�����ǿ�
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-21
	 */
	public static ContractWOTCodingTypeCollection getSelfContractWOTCodingTypeCollection(Context ctx,
			ContractTypeInfo contractTypeInfo, String thirdType) throws BOSException, EASBizException {
		ContractWOTCodingTypeCollection codingTypeCollection = new ContractWOTCodingTypeCollection();

		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);

		String contractTypeId = (contractTypeInfo == null) ? null : contractTypeInfo.getId().toString();
		// ��ͬ����
		FilterItemInfo contractTypeFilter = new FilterItemInfo("contractType.id", contractTypeId);
		filter.getFilterItems().add(contractTypeFilter);

		// ����״̬
		filter.getFilterItems().add(new FilterItemInfo("thirdType", thirdType));

		IContractWOTCodingType iContractWOTCodingType = getContractWOTCodingTypeInstance(ctx);
		// ��ѯ����ͬ���͡���ָ��ֵ�����
		codingTypeCollection = iContractWOTCodingType.getContractWOTCodingTypeCollection(evi);

		return codingTypeCollection;
	}

	/**
	 * ������ȡ�ø������ı���ͬ�������Ͷ��󼯺ϣ���ȷ��
	 * 
	 * @param ctx
	 *            Ӧ�������ģ��ɿ�
	 * @param contractTypeInfo
	 *            ��ͬ���ͣ��ɿ�
	 * @param thirdType
	 *            ����״̬��������鵵�����ǿ�
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-21
	 */
	public static ContractWOTCodingTypeCollection getParentContractWOTCodingTypeCollection(Context ctx,
			ContractTypeInfo contractTypeInfo, String contractProperty, String thirdType) throws BOSException,
			EASBizException {
		ContractWOTCodingTypeCollection codingTypeCollection = new ContractWOTCodingTypeCollection();

		// ȡ�ø����ͬ����
		ContractTypeInfo parentContractTypeInfo = getParentContractType(ctx, contractTypeInfo);
		if (null != parentContractTypeInfo) {
			codingTypeCollection = getSelfContractWOTCodingTypeCollection(ctx, parentContractTypeInfo, thirdType);
		}

		return codingTypeCollection;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}
