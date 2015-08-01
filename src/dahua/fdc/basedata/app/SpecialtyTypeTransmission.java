package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeFactory;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.jdbc.rowset.IRowSet;

public class SpecialtyTypeTransmission extends AbstractDataTransmission {
	
	private static final Logger logger = CoreUIObject.getLogger(SpecialtyTypeTransmission.class);
	private static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return SpecialtyTypeFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			logger.debug(e.getMessage(), e.getCause());
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	/**
	 * 描述:将用hashtable保存的数据转化为一个EAS中的CoreBaseInfo对象
	 */
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {

		SpecialtyTypeInfo info = new SpecialtyTypeInfo();

		// 变更类型
		String fChangeTypeNamel2 = (String) ((DataToken) hsData.get("FChangeType_name_l2")).data;

		// 专业类型编码
		String fNumber = (String) ((DataToken) hsData.get("FNumber")).data;

		// 专业类型名称
		String fNamel2 = (String) ((DataToken) hsData.get("FName_l2")).data;

		// 描述
		String fDescriptionl2 = (String) ((DataToken) hsData.get("FDescription_l2")).data;

		// 启用
		String fIsEnabled = (String) ((DataToken) hsData.get("FIsEnabled")).data;

		/*
		 * 判断是否为空
		 */
		if (StringUtils.isEmpty(fChangeTypeNamel2)) {
			throw new TaskExternalException(getResource(ctx, "Import_ChangeTypeNameIsNull",resource));
		}
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException(getResource(ctx, "Import_SpecialtyTypeNumberIsNull",resource));
		}
		if (StringUtils.isEmpty(fNamel2)) {
			throw new TaskExternalException(getResource(ctx, "Import_SpecialtyTypeNameIsNull",resource));
		}
		if (fNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_SpecialtyTypeNumberTooLength",resource));
		}
		if (fNamel2.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_SpecialtyTypeNameTooLength",resource));
		}
		if (!StringUtils.isEmpty(fDescriptionl2)) {
			if (fDescriptionl2.length() > 200) {
				throw new TaskExternalException(getResource(ctx, "Import_DescriptionTooLength",resource));
			}
		}
		
		if (fIsEnabled.trim().equals(getResource(ctx, "yes",resourceCommon))) {
			info.setIsEnabled(true);
		} else {
			info.setIsEnabled(false);
		}
		info.setNumber(fNumber);
		info.setDescription(fDescriptionl2);
		info.setName(fNamel2);
		// 得到变更类型对象
		try {
			//查询变更类型
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", fChangeTypeNamel2,CompareType.EQUALS));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			ChangeTypeCollection coll = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeCollection(view);
			ChangeTypeInfo changeTypeInfo = null;
			if (coll.size()>0) {
				changeTypeInfo = coll.get(0);
				info.setChangeType(changeTypeInfo);
			}else
			{
				throw new TaskExternalException(getResource(ctx, "Import_ChangeTypeIsNotExist",resource));
			}
		} catch (BOSException e) {
			throw new TaskExternalException(getResource(ctx, "Import_ChangeTypeIsNotExist",resource));
		}
		return info;
	}
	
	/**
	 * 得到资源文件
	 * @author 郑杰元
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
	
	
	/**
	 * @description		
	 * @author			朱俊		
	 * @createDate		2011-6-23
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#exportTransmit(com.kingdee.jdbc.rowset.IRowSet, com.kingdee.bos.Context)					
	 */
	public Hashtable exportTransmit(IRowSet rs, Context ctx)
			throws TaskExternalException {
		super.exportTransmit(rs, ctx);
		return super.exportTransmit(rs, ctx);
	}
}
