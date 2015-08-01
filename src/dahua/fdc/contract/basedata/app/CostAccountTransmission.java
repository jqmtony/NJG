/**
 * 
 */
package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		dingwen_yong
 * @version		EAS7.0		
 * @createDate	2011-6-13	 
 * @see						
 */
public class CostAccountTransmission extends AbstractDataTransmission {
	
	// 资源文件
	private static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";

	/** 组织 */
	private FullOrgUnitInfo fullOrgUnit = null; 
	/** 工程项目 */
	private CurProjectInfo curProject = null;
	
	/**
	 * @description		
	 * @author			dingwen_yong		
	 * @createDate		2011-6-13
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	protected ICoreBase getController(Context context) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = CostAccountFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			dingwen_yong		
	 * @createDate		2011-6-13
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		
		CostAccountInfo info = new CostAccountInfo();
		CostAccountInfo parentCostAccountInfo = null;
		
		/**
		 * 获取Excel文本中对应的值
		 */
		// 1.组织长编码
		String fFullOrgUnitLongNumber = ((String) ((DataToken) hashtable.get("FFullOrgUnit_longNumber")).data).trim();
		// 2.工程项目长编码
		String fCurProjectLongNumber = ((String) ((DataToken) hashtable.get("FCurProject_longNumber")).data).trim();
		// 3.创建组织
		String fFullOrgUnitNameL2 = ((String) ((DataToken) hashtable.get("FFullOrgUnit_name_l2")).data).trim();
		// 4.科目长编码
		String fLongNumber = ((String) ((DataToken) hashtable.get("FLongNumber")).data).trim();
		// 父节点的科目长编码
		String fParentLongNumber = null;
		// 编码
		String fNumber = null;
		// Coding编码
		String fCodingNumber = null;
		// 5.科目名称
		String fNameL2 = ((String) ((DataToken) hashtable.get("FName_l2")).data).trim();
		// 表示用科目名称
		String fDisplayNameL2 = null;
		// 6.科目类别
		String fType = ((String) ((DataToken) hashtable.get("FType")).data).trim();
		// 7.成本类
		String fIsCostAccount = ((String) ((DataToken) hashtable.get("FIsCostAccount")).data).trim();
		// 8.描述
		String fDescriptionL2 = ((String) ((DataToken) hashtable.get("FDescription_l2")).data).trim();
		
		/**
		 * 判断部分字段是否为空，以及每个字段的长度(当字段不为空时，判断其长度是否有效)
		 */
		if (StringUtils.isEmpty(fFullOrgUnitLongNumber)) {
			// "组织长编码不能为空！"
			throw new TaskExternalException(getResource(context, "CostAccount_Import_fFullOrgUnitLongNumber"));
		}
		if (!StringUtils.isEmpty(fCurProjectLongNumber)) {
			if (fCurProjectLongNumber.length() > 80) {
				// "工程项目长编码字段长度不能超过80！"
				throw new TaskExternalException(getResource(context, "CostAccount_Import_fCurProjectLongNumber"));
			}
		} else {
			// "工程项目长编码不能为空！"
			throw new TaskExternalException(getResource(context, "CostAccount_Import_fCurProjectLongNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fLongNumber)) {
			if (fLongNumber.length() > 80) {
				// "科目长编码字段长度不能超过80！"
				throw new TaskExternalException(getResource(context, "CostAccount_Import_fLongNumber"));
			}
		} else {
			// "科目长编码不能为空！"
			throw new TaskExternalException(getResource(context, "CostAccount_Import_fLongNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fNameL2)) {
			if (fNameL2.length() > 80) {
				// "科目名称字段长度不能超过80！"
				throw new TaskExternalException(getResource(context, "CostAccount_Import_fNameL2"));
			}
		} else {
			// "科目名称不能为空！"
			throw new TaskExternalException(getResource(context, "CostAccount_Import_fNameL2NotNull"));
		}
		if (!StringUtils.isEmpty(fDescriptionL2) && fDescriptionL2.length() > 200) {
			// "描述字段长度不能超过200！"
			throw new TaskExternalException(getResource(context, "CostAccount_Import_fDescriptionL2"));
		}
		
		try {
			//组织编码
			FilterInfo filterfullOrgUnit = new FilterInfo();
			filterfullOrgUnit.getFilterItems().add(new FilterItemInfo("longnumber", fFullOrgUnitLongNumber.replace('.', '!')));
			EntityViewInfo viewfullOrgUnit = new EntityViewInfo();
			viewfullOrgUnit.setFilter(filterfullOrgUnit);
			FullOrgUnitCollection fullOrgUnitColl = FullOrgUnitFactory.getLocalInstance(context).getFullOrgUnitCollection(viewfullOrgUnit);
			if (fullOrgUnitColl.size() > 0){
				fullOrgUnit = fullOrgUnitColl.get(0);
				info.setFullOrgUnit(fullOrgUnit); 
			}else{
				// 1 "组织编码为空,或者该组织编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "CostAccount_Import_fFullOrgUnitLongNumber1") + fFullOrgUnitLongNumber + getResource(context, "CostAccount_Import_NOTNULL"));
			}
			// 工程项目长编码
			FilterInfo filterCurProject = new FilterInfo();
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
				info.setCurProject(curProject);
			} else {
				// 1 "工程项目长编码为空,或该工程项目长编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "CostAccount_Import_fCurProjectLongNumber1") + fCurProjectLongNumber + getResource(context, "CostAccount_Import_NOTNULL"));
			}
			/**
			 * 成本科目表中重复校验
			 */
//			// 工程项目长编码对应的当前工程项目ID重复
//			FilterInfo filterCostAccount1 = new FilterInfo();
//			filterCostAccount1.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fullOrgUnit.getId().toString()));
//			filterCostAccount1.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
//			EntityViewInfo viewCostAccount1 = new EntityViewInfo();
//			viewCostAccount1.setFilter(filterCostAccount1);
//			CostAccountCollection costAccountColl1 = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewCostAccount1);
//			if (costAccountColl1.size() > 0){
//				// 成本科目表中工程项目长编码对应的当前工程项目ID重复时，提示：编码**重复！ 
//				throw new TaskExternalException(getResource(context, "CostAccount_Import_LongNumber") + fCurProjectLongNumber + getResource(context, "CostAccount_Import_HasExisted"));
//			}
//			// 科目长编码重复
//			FilterInfo filterCostAccount2 = new FilterInfo();
//			filterCostAccount2.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fullOrgUnit.getId().toString()));
//			filterCostAccount2.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
//			filterCostAccount2.getFilterItems().add(new FilterItemInfo("longnumber", fLongNumber));
//			EntityViewInfo viewCostAccount2 = new EntityViewInfo();
//			viewCostAccount2.setFilter(filterCostAccount2);
//			CostAccountCollection costAccountColl2 = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewCostAccount2);
//			if (costAccountColl2.size() > 0){
//				// 成本科目表中科目长编码重复时，提示：编码**重复！ 
//				throw new TaskExternalException(getResource(context, "CostAccount_Import_LongNumber") + fLongNumber + getResource(context, "CostAccount_Import_HasExisted"));
//			}
			// 科目名称重复
			FilterInfo filterCostAccount3 = new FilterInfo();
			filterCostAccount3.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fullOrgUnit.getId().toString()));
			filterCostAccount3.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
			filterCostAccount3.getFilterItems().add(new FilterItemInfo("name", fNameL2));
			EntityViewInfo viewCostAccount3 = new EntityViewInfo();
			viewCostAccount3.setFilter(filterCostAccount3);
			CostAccountCollection costAccountColl3 = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewCostAccount3);
			if (costAccountColl3.size() > 0){
				// 成本科目表中科目名称重复时，提示：科目名称**重复！
				throw new TaskExternalException(getResource(context, "CostAccount_Import_Name") + fNameL2 + getResource(context, "CostAccount_Import_HasExisted"));
			}
			
			// 为子节点时，取其父节点的信息
			String[] fNumbers = fLongNumber.replace('.', '!').split("!");
			if (fNumbers.length != 1) {
				fNumber = fNumbers[fNumbers.length - 1];
				fParentLongNumber = fLongNumber.replace('.', '!').substring(0, fLongNumber.length() - fNumber.length() - 1);
				FilterInfo filterParentCostAccount = new FilterInfo();
				filterParentCostAccount.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fullOrgUnit.getId().toString()));
				filterParentCostAccount.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
				filterParentCostAccount.getFilterItems().add(new FilterItemInfo("longnumber", fParentLongNumber));
				EntityViewInfo viewParentCostAccount = new EntityViewInfo();
				viewParentCostAccount.setFilter(filterParentCostAccount);
				CostAccountCollection costParentAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewParentCostAccount);
				if (costParentAccountColl.size() > 0){
					parentCostAccountInfo = costParentAccountColl.get(0);
					info.setParent(parentCostAccountInfo);
					info.setIsCostAccount(parentCostAccountInfo.isIsCostAccount());
					fDisplayNameL2 = parentCostAccountInfo.getDisplayName() + "_" + fNameL2;
					info.setDisplayName(fDisplayNameL2);
				} else {
					// 成本科目表中该子节点的父节点信息不存在时，提示：成本科目表中该子节点的父节点信息不存在！
					throw new TaskExternalException(getResource(context, "CostAccount_Import_LongNumber") + fLongNumber + getResource(context, "CostAccount_Import_ParentLongNumberIsNotExsit"));
				}
				// 科目类别 :如果是子节点的科目，科目类别为空时提示：科目类别不能为空！录入科目类别不存在时，提示：科目类别**不存在！
				if (!StringUtils.isEmpty(fType)) {
					// 科目长编码
					info.setLongNumber(fLongNumber.replace('.', '!'));
					// 科目名称
					info.setName(fNameL2);

					if (fType.equals(getResource(context, "MAIN"))) {
						info.setType(CostAccountTypeEnum.MAIN);
					} else if (fType.equals(getResource(context, "SIX"))) {
						info.setType(CostAccountTypeEnum.SIX);
					} else {
						// 1 "科目类别 "
						// 2 "在系统中不存在" 
						throw new TaskExternalException(getResource(context, "CostAccount_Import_fType") + fType + getResource(context, "CostAccount_Import_NOTNULL"));
					}
					// 子节点的科目 类别必须与上级科目的科目类别一致，否则子级科目取父级科目的科目类别。
					if (!info.getType().equals(parentCostAccountInfo.getType())) {
						info.setType(parentCostAccountInfo.getType());
					}					
				} else {
					// "科目类别 不能为空！"
					throw new TaskExternalException(getResource(context, "CostAccount_Import_fTypeNotNull"));
				}
			} else { // 为父节点时
				// 科目长编码
				info.setLongNumber(fLongNumber.replace('.', '!'));
				// 科目名称
				info.setName(fNameL2);
				// 表示用科目名称
				info.setDisplayName(fNameL2);
				// 成本类 
				if (fIsCostAccount.equals(getResource(context, "No"))) {
					info.setIsCostAccount(false);
				} else {
					info.setIsCostAccount(true);				
				}
			}
			// 编码
			if (fNumbers.length > 1) {				
				info.setNumber(fNumber);
			} else {
				info.setNumber(fLongNumber);
			}
			// Coding编码
			fCodingNumber = fLongNumber.replace('!', '.');
			info.setCodingNumber(fCodingNumber);
			// 描述 
			info.setDescription(fDescriptionL2);
			info.setIsSource(true);
			info.setLevel(fNumbers.length);
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		return info;
	}

	/**
	 * 得到资源文件
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
	
}
