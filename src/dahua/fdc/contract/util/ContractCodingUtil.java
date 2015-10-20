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
 * 描述：合同编码工具类
 * 
 * @author 王正
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
	 * 描述：取得父类合同类型
	 * 
	 * @param ctx
	 *            应用上下文；可空
	 * @param contractTypeInfo
	 *            合同类型
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-21
	 */
	public static ContractTypeInfo getParentContractType(Context ctx, ContractTypeInfo contractTypeInfo)
			throws BOSException, EASBizException {
		ContractTypeInfo parentContractTypeInfo = (contractTypeInfo != null) ? contractTypeInfo.getParent() : null;

		// 查找上级
		if (null != contractTypeInfo && null == parentContractTypeInfo) {
			try {
				// 取得直接父类TreeBase
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
	 * 描述：取得合同编码类型接口实例
	 * 
	 * @param ctx
	 *            应用上下文；可空
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
	 * 描述：取得合同编码类型对象集合（精确，模糊，递归）
	 * <p>
	 * 1、首先查询“合同类型”，“合同性质”都是指定值的情况 <br>
	 * 2、再查询“合同类型”为指定值，“合同性质”为“OLD(全部)”的情况 <br>
	 * 3、再查询父类“合同类型”为指定值的情况 <br>
	 * 4、再查询“合同类型”为“NULL(全部)”，“合同性质”为指定值的情况 <br>
	 * 5、再查询“合同类型”为“NULL(全部)”，“合同性质”为“OLD(全部)”的情况 <br>
	 * <p>
	 * 6.1、“合同类型”要支持递归。例如：首先要找当前级，如果没有找到，则要能找到上级，如果还没有找到，则要找上上级，依次类推 <br>
	 * 6.2、递归查找“合同类型”上级（“合同类型”是树形结构，通常只有3-4级，所以不会产生性能问题） <br>
	 * 
	 * @param ctx
	 *            应用上下文；可空
	 * @param contractTypeInfo
	 *            合同类型；可空
	 * @param contractProperty
	 *            合同性质；非空
	 * @param thirdType
	 *            单据状态（新增或归档）；非空
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
		// 合同类型
		FilterItemInfo contractTypeFilter = new FilterItemInfo("contractType.id", contractTypeId);
		filter.getFilterItems().add(contractTypeFilter);

		// 合同性质
		FilterItemInfo contractPropertFilter = new FilterItemInfo("secondType", contractProperty);
		filter.getFilterItems().add(contractPropertFilter);

		// 单据状态
		filter.getFilterItems().add(new FilterItemInfo("thirdType", thirdType));

		IContractCodingType iContractCodingType = getContractCodingTypeInstance(ctx);

		// 1、首先查询“合同类型”，“合同性质”都是指定值的情况
		ContractCodingTypeCollection codingTypeCollection = iContractCodingType.getContractCodingTypeCollection(evi);

		// 2、再查询“合同类型”为指定值，“合同性质”为“OLD(全部)”的情况
		if (contractTypeInfo != null) {
			contractTypeFilter.setCompareValue(contractTypeId);
			contractPropertFilter.setCompareValue(ContractSecondTypeEnum.OLD_VALUE);
			codingTypeCollection.addCollection(iContractCodingType.getContractCodingTypeCollection(evi));
		}

		// 3、再查询父类“合同类型”为指定值的情况
		// 取得父类合同类型
		ContractTypeInfo parentContractTypeInfo = ContractCodingUtil.getParentContractType(ctx, contractTypeInfo);
		if (null != parentContractTypeInfo) {
			contractTypeInfo.setParent(parentContractTypeInfo);

			// 取得父类“合同类型”，“合同性质”都是指定值的情况
			ContractCodingTypeCollection parentCodingTypeCollection = ContractCodingUtil
					.getSelfContractCodingTypeCollection(ctx, parentContractTypeInfo, contractProperty, thirdType);
			codingTypeCollection.addCollection(parentCodingTypeCollection);

			// 取得父类“合同类型”，“合同性质”为“OLD(全部)”的情况
			parentCodingTypeCollection = ContractCodingUtil.getSelfContractCodingTypeCollection(ctx,
					parentContractTypeInfo, ContractSecondTypeEnum.OLD_VALUE, thirdType);
			codingTypeCollection.addCollection(parentCodingTypeCollection);
		}

		// 4、再查询“合同类型”为“NULL(全部)”，“合同性质”为指定值的情况
		contractTypeFilter.setCompareValue(null);
		contractPropertFilter.setCompareValue(contractProperty);
		codingTypeCollection.addCollection(iContractCodingType.getContractCodingTypeCollection(evi));

		// 5、再查询“合同类型”为“NULL(全部)”，“合同性质”为“OLD(全部)”的情况
		contractTypeFilter.setCompareValue(null);
		contractPropertFilter.setCompareValue(ContractSecondTypeEnum.OLD_VALUE);
		codingTypeCollection.addCollection(iContractCodingType.getContractCodingTypeCollection(evi));

		// 6.1、合同编码类型要支持递归。例如：首先要找当前级，如果没有找到，则要找到上级，如果还没有找到，则要找上上级，依次类推
		// 7.2、递归查找“合同类型”上级（“合同类型”是树形结构，通常只有3-4级，所以不会产生性能问题）
		if (null != parentContractTypeInfo) {
			// 取得父类合同编码类型对象集合（精确，模糊，递归）
			ContractCodingTypeCollection parentCodingTypeCollection = (ContractCodingTypeCollection) getContractCodingTypeCollection(
					ctx, parentContractTypeInfo, contractProperty, thirdType);

			codingTypeCollection.addCollection(parentCodingTypeCollection);
		}

		// 清除掉集合中的重复值和Null值
		FdcObjectCollectionUtil.clearDuplicateAndNull(codingTypeCollection);

		return codingTypeCollection;
	}

	/**
	 * 描述：取得自身合同编码类型对象集合（精确）
	 * 
	 * @param ctx
	 *            应用上下文；可空
	 * @param contractTypeInfo
	 *            合同类型；可空
	 * @param contractProperty
	 *            合同性质；非空
	 * @param thirdType
	 *            单据状态（新增或归档）；非空
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
		// 合同类型
		FilterItemInfo contractTypeFilter = new FilterItemInfo("contractType.id", contractTypeId);
		filter.getFilterItems().add(contractTypeFilter);

		// 合同性质
		FilterItemInfo contractPropertFilter = new FilterItemInfo("secondType", contractProperty);
		filter.getFilterItems().add(contractPropertFilter);

		// 单据状态
		filter.getFilterItems().add(new FilterItemInfo("thirdType", thirdType));

		IContractCodingType iContractCodingType = getContractCodingTypeInstance(ctx);
		// 查询“合同类型”，“合同性质”都是指定值的情况
		codingTypeCollection = iContractCodingType.getContractCodingTypeCollection(evi);

		return codingTypeCollection;
	}

	/**
	 * 描述：取得父类合同编码类型对象集合（精确）
	 * 
	 * @param ctx
	 *            应用上下文；可空
	 * @param contractTypeInfo
	 *            合同类型；可空
	 * @param contractProperty
	 *            合同性质；非空
	 * @param thirdType
	 *            单据状态（新增或归档）；非空
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

		// 取得父类合同类型
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
	 * 描述：取得无文本合同编码类型接口实例
	 * 
	 * @param ctx
	 *            应用上下文；可空
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
	 * 描述：取得无文本合同编码类型对象集合（精确，模糊，递归）
	 * <p>
	 * 1、首先查询“合同类型”是指定值的情况 <br>
	 * 2、再查询父类“合同类型”为指定值的情况 <br>
	 * 3、再查询“合同类型”为“NULL(全部)”的情况 <br>
	 * <p>
	 * 4.1、“合同类型”要支持递归。例如：首先要找当前级，如果没有找到，则要能找到上级，如果还没有找到，则要找上上级，依次类推 <br>
	 * 4.2、递归查找“合同类型”上级（“合同类型”是树形结构，通常只有3-4级，所以不会产生性能问题） <br>
	 * 
	 * @param ctx
	 *            应用上下文；可空
	 * @param contractTypeInfo
	 *            合同类型；可空
	 * @param thirdType
	 *            单据状态（新增或归档）；非空
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
		// 合同类型
		FilterItemInfo contractTypeFilter = new FilterItemInfo("contractType.id", contractTypeId);
		filter.getFilterItems().add(contractTypeFilter);

		// 单据状态
		filter.getFilterItems().add(new FilterItemInfo("thirdType", thirdType));

		IContractWOTCodingType iContractWOTCodingType = getContractWOTCodingTypeInstance(ctx);

		// 1、首先查询“合同类型”是指定值的情况
		ContractWOTCodingTypeCollection codingTypeCollection = iContractWOTCodingType
				.getContractWOTCodingTypeCollection(evi);

		// 2、再查询父类“合同类型”为指定值的情况
		// 取得父类合同类型
		ContractTypeInfo parentContractTypeInfo = ContractCodingUtil.getParentContractType(ctx, contractTypeInfo);
		if (null != parentContractTypeInfo) {
			contractTypeInfo.setParent(parentContractTypeInfo);

			// 取得父类“合同类型”是指定值的情况
			ContractWOTCodingTypeCollection parentCodingTypeCollection = ContractCodingUtil
					.getSelfContractWOTCodingTypeCollection(ctx, parentContractTypeInfo, thirdType);
			codingTypeCollection.addCollection(parentCodingTypeCollection);
		}

		// 3、再查询“合同类型”为“NULL(全部)”的情况
		contractTypeFilter.setCompareValue(null);
		codingTypeCollection.addCollection(iContractWOTCodingType.getContractWOTCodingTypeCollection(evi));

		// 4.1、无文本合同编码类型要支持递归。例如：首先要找当前级，如果没有找到，则要找到上级，如果还没有找到，则要找上上级，依次类推
		// 4.2、递归查找“合同类型”上级（“合同类型”是树形结构，通常只有3-4级，所以不会产生性能问题）
		if (null != parentContractTypeInfo) {
			// 取得父类无文本合同编码类型对象集合（精确，模糊，递归）
			ContractWOTCodingTypeCollection parentCodingTypeCollection = (ContractWOTCodingTypeCollection) getContractWOTCodingTypeCollection(
					ctx, parentContractTypeInfo, thirdType);

			codingTypeCollection.addCollection(parentCodingTypeCollection);
		}

		// 清除掉集合中的重复值和Null值
		FdcObjectCollectionUtil.clearDuplicateAndNull(codingTypeCollection);

		return codingTypeCollection;
	}

	/**
	 * 描述：取得自身无文本合同编码类型对象集合（精确）
	 * 
	 * @param ctx
	 *            应用上下文；可空
	 * @param contractTypeInfo
	 *            合同类型；可空
	 * @param thirdType
	 *            单据状态（新增或归档）；非空
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
		// 合同类型
		FilterItemInfo contractTypeFilter = new FilterItemInfo("contractType.id", contractTypeId);
		filter.getFilterItems().add(contractTypeFilter);

		// 单据状态
		filter.getFilterItems().add(new FilterItemInfo("thirdType", thirdType));

		IContractWOTCodingType iContractWOTCodingType = getContractWOTCodingTypeInstance(ctx);
		// 查询“合同类型”是指定值的情况
		codingTypeCollection = iContractWOTCodingType.getContractWOTCodingTypeCollection(evi);

		return codingTypeCollection;
	}

	/**
	 * 描述：取得父类无文本合同编码类型对象集合（精确）
	 * 
	 * @param ctx
	 *            应用上下文；可空
	 * @param contractTypeInfo
	 *            合同类型；可空
	 * @param thirdType
	 *            单据状态（新增或归档）；非空
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

		// 取得父类合同类型
		ContractTypeInfo parentContractTypeInfo = getParentContractType(ctx, contractTypeInfo);
		if (null != parentContractTypeInfo) {
			codingTypeCollection = getSelfContractWOTCodingTypeCollection(ctx, parentContractTypeInfo, thirdType);
		}

		return codingTypeCollection;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}
