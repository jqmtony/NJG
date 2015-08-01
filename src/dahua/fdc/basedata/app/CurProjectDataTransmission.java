package com.kingdee.eas.fdc.basedata.app;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeCollection;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

public class CurProjectDataTransmission extends AbstractDataTransmission {
	private static final Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.CurProjectDataTransmission");
	private final String KEY_NUMBER = "FLongNumber";
	private final String KEY_PROJECT_TYPE = "FProjectType_name_l2";
	private final String KEY_NAME = "FName_l2";
	private final String KEY_IS_DEV = "FIsDevPrj";
	private final String KEY_START_DATE = "FStartDate";
	private final String KEY_SORT_NO = "FSortNo";
	private final String KEY_DESCRIPTION = "FDescription_l2";
	private final String KEY_ADDRESS = "FProjectAddress";
	private final String KEY_IS_ENABLED = "FIsEnabled";
	private final String PARAM_PARENT = "parentInfo";
	private final String PARAM_PROJECT_STATUS = "projectStatus";
	private final Map projectTypeMap = new HashMap(); //“项目系列”缓存起来，可以减少RPC次数 added by Owen_wen
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return CurProjectFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			logger.error(e.getMessage(), e);
			throw new TaskExternalException(e.getMessage(), e);
		}
	}
	private CurProjectInfo getParentInfo(Context ctx, Object parentInfo, String parentNumber) throws TaskExternalException {
		String orgId = null;
		if (parentInfo != null) {
			if (parentInfo instanceof CurProjectInfo) {
				CurProjectInfo prj = (CurProjectInfo) parentInfo;
				parentNumber = prj.getLongNumber().replace('!', '.') + "." + parentNumber;
				orgId = prj.getFullOrgUnit().getId().toString();
			} else if (parentInfo instanceof FullOrgUnitInfo) {
				orgId = ((FullOrgUnitInfo) parentInfo).getId().toString();
			}
		}
		parentNumber = parentNumber.replace('.', '!');
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("fullOrgUnit");
		view.getSelector().add("isEnabled");
		view.getSelector().add("level");
		view.getSelector().add("isDevPrj");
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("longNumber", parentNumber));
		view.getFilter().getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", orgId));
		try {
			CurProjectCollection projectCol = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(view);
			if (projectCol != null && projectCol.size() == 1) {
				return projectCol.get(0);
			}
		} catch (BOSException e) {
			logger.error(e.getCause(), e);
			throw new TaskExternalException("获取上级工程项目失败", e);
		}
		throw new TaskExternalException("编码为" + parentNumber + "的工程项目不存在");
	}
	private void fillProjectByParent(CurProjectInfo info, Object parentInfo) {
		if (parentInfo != null) {
			if (parentInfo instanceof CurProjectInfo) {
				CurProjectInfo prj = (CurProjectInfo) parentInfo;
				info.setParent(prj);
				info.setFullOrgUnit(prj.getFullOrgUnit());
				// 启用状态,与上级同
				info.setIsEnabled(prj.isIsEnabled());
				// 级别，上级级别+1
				info.setLevel(prj.getLevel() + 1);
				// 开发项目,与上级相同,不可修改
				info.setIsDevPrj(prj.isIsDevPrj());
			} else if (parentInfo instanceof FullOrgUnitInfo) {
				info.setFullOrgUnit((FullOrgUnitInfo) parentInfo);
				info.setLevel(1);
			}
		}
	}
	
	private void initProject(Context ctx, CurProjectInfo info, Hashtable hsData) throws TaskExternalException {		
		Object parentInfo = getContextParameter(PARAM_PARENT);
		String currentNumber = getValue(hsData, KEY_NUMBER);
		logger.info(" >>>>>>>>>> handle:" + currentNumber);
		if (currentNumber.startsWith(".") && currentNumber.length() > 1) {
			currentNumber = currentNumber.substring(1);
		}
		int lastDotIndex = currentNumber.lastIndexOf(".");		
		if (lastDotIndex > 0) {
			String parentNumber = currentNumber.substring(0, lastDotIndex);
			currentNumber = currentNumber.substring(lastDotIndex + 1, currentNumber.length());
			parentInfo = this.getParentInfo(ctx, parentInfo, parentNumber);
		}
		fillProjectByParent(info, parentInfo);
		info.setNumber(currentNumber);
		String name = getValue(hsData, KEY_NAME);
		info.setName(name);
		//verifyName(ctx, parentInfo, name);
		info.setStartDate(getStartDate(hsData));
		info.setProjectType(getProjectType(ctx, getValue(hsData, KEY_PROJECT_TYPE)));
		info.setSortNo(getInt(hsData, KEY_SORT_NO));
		info.setDescription(getValue(hsData, KEY_DESCRIPTION));
		info.setProjectAddress(getValue(hsData, KEY_ADDRESS));
		info.setProjectStatus((ProjectStatusInfo) getContextParameter(PARAM_PROJECT_STATUS));
		if (!(parentInfo instanceof CurProjectInfo)) {
			info.setIsEnabled(getBoolean(hsData, KEY_IS_ENABLED));
			info.setIsDevPrj(getBoolean(hsData, KEY_IS_DEV));
		}
	}
	
	private ProjectTypeInfo getProjectType(Context ctx, String projectTypeName) throws TaskExternalException {
		if (projectTypeMap.get("projectTypeName") != null) {
			return (ProjectTypeInfo) projectTypeMap.get("projectTypeName");
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("name", projectTypeName));
		SelectorItemCollection selector = new SelectorItemCollection();
		view.setSelector(selector);
		selector.add("id");
		try {
			ProjectTypeCollection col = ProjectTypeFactory.getLocalInstance(ctx).getProjectTypeCollection(view);
			if (col == null || col.isEmpty()) {
				throw new TaskExternalException("名称为" + projectTypeName + "的项目系列不存在");
			}
			if (col.size() > 1) {
				throw new TaskExternalException("名称为" + projectTypeName + "的项目系列找到多条");
			}
			projectTypeMap.put(projectTypeName, col.get(0));// 缓存起来，可以减少RPC次数
			return col.get(0);
		} catch (BOSException e) {
			logger.warn(e.getMessage(), e);
			throw new TaskExternalException("名称为" + projectTypeName + "的项目系列不存在");
		}

	}
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		CurProjectInfo info = new CurProjectInfo();
		verifyData(hsData);
		initProject(ctx, info, hsData);		
		return info;
	}
	private void verifyName(Context ctx, Object parentInfo, String name) throws TaskExternalException {
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));
			if (parentInfo instanceof CurProjectInfo) {
				filter.getFilterItems().add(new FilterItemInfo("parent.id", ((CurProjectInfo) parentInfo).getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("parent.id", null));
				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", ((FullOrgUnitInfo) parentInfo).getId().toString()));
			}
			if (!CurProjectFactory.getLocalInstance(ctx).exists(filter)) {
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TaskExternalException("名称'" + name + "'重复.");
		}
	}
	private void verifyData(Hashtable hsData) throws TaskExternalException {
		if (StringUtils.isEmpty(getValue(hsData, KEY_NUMBER))) {
			throw new TaskExternalException("本次编码不能为空。");
		}
		if (StringUtils.isEmpty(getValue(hsData, KEY_PROJECT_TYPE))) {
			throw new TaskExternalException("项目系列不能为空。");
		}		
		if (StringUtils.isEmpty(getValue(hsData, KEY_NAME))) {
			throw new TaskExternalException("名称不能为空。");
		}
		if (StringUtils.isEmpty(getValue(hsData, KEY_START_DATE))) {
			throw new TaskExternalException("开始日期不能为空。");
		}
		String description = getValue(hsData, KEY_DESCRIPTION);
		if (!StringUtils.isEmpty(description) && description.length() > 200) {
			throw new TaskExternalException("描述最多200个字符。");
		}
		String address = getValue(hsData, KEY_ADDRESS);
		if (!StringUtils.isEmpty(address) && address.length() > 80) {
			throw new TaskExternalException("地址最多80个字符。");
		}
	}
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		super.submit(coreBaseInfo, ctx);
	}
	private Date getStartDate(Hashtable hsData) throws TaskExternalException{
		try {
			String s = getValue(hsData, KEY_START_DATE);
			if(s != null){
				Date ret = DateTimeUtils.parseDate(s);
				DataToken token = (DataToken) hsData.get(KEY_START_DATE);
				token.data = DateTimeUtils.format(ret);
				return ret; 
			}
			throw new TaskExternalException("开始日期不能为空");
		} catch (ParseException e) {
			throw new TaskExternalException("开始日期" + getValue(hsData, KEY_START_DATE) + "不是正确的日期格式");
		}
	}
	private boolean getBoolean(Hashtable hsData, String key) {
		String b = getValue(hsData, key);
		if (b != null) {
			return Boolean.valueOf(b).booleanValue();
		}
		return false;
	}

	private int getInt(Hashtable hsData, String key) throws TaskExternalException {
		String b = getValue(hsData, key);		
		if (b != null) {
			return Integer.valueOf(b).intValue();
		}
		return 0;
	}
	
	private String getValue(Hashtable hsData, String key) {
		if( hsData.get(key) instanceof DataToken){
			DataToken token = (DataToken) hsData.get(key);
			if (token != null && token.data != null) {
				return token.data.toString();
			}
		}
		return null;
	}
}
