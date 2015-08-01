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
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		
 *		
 * @author		dingwen_yong
 * @version		EAS7.0		
 * @createDate	2011-6-13	 
 * @see						
 */
public class CostAccountTransmission extends AbstractDataTransmission {
	
	// ��Դ�ļ�
	private static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";

	/** ��֯ */
	private FullOrgUnitInfo fullOrgUnit = null; 
	/** ������Ŀ */
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
		 * ��ȡExcel�ı��ж�Ӧ��ֵ
		 */
		// 1.��֯������
		String fFullOrgUnitLongNumber = ((String) ((DataToken) hashtable.get("FFullOrgUnit_longNumber")).data).trim();
		// 2.������Ŀ������
		String fCurProjectLongNumber = ((String) ((DataToken) hashtable.get("FCurProject_longNumber")).data).trim();
		// 3.������֯
		String fFullOrgUnitNameL2 = ((String) ((DataToken) hashtable.get("FFullOrgUnit_name_l2")).data).trim();
		// 4.��Ŀ������
		String fLongNumber = ((String) ((DataToken) hashtable.get("FLongNumber")).data).trim();
		// ���ڵ�Ŀ�Ŀ������
		String fParentLongNumber = null;
		// ����
		String fNumber = null;
		// Coding����
		String fCodingNumber = null;
		// 5.��Ŀ����
		String fNameL2 = ((String) ((DataToken) hashtable.get("FName_l2")).data).trim();
		// ��ʾ�ÿ�Ŀ����
		String fDisplayNameL2 = null;
		// 6.��Ŀ���
		String fType = ((String) ((DataToken) hashtable.get("FType")).data).trim();
		// 7.�ɱ���
		String fIsCostAccount = ((String) ((DataToken) hashtable.get("FIsCostAccount")).data).trim();
		// 8.����
		String fDescriptionL2 = ((String) ((DataToken) hashtable.get("FDescription_l2")).data).trim();
		
		/**
		 * �жϲ����ֶ��Ƿ�Ϊ�գ��Լ�ÿ���ֶεĳ���(���ֶβ�Ϊ��ʱ���ж��䳤���Ƿ���Ч)
		 */
		if (StringUtils.isEmpty(fFullOrgUnitLongNumber)) {
			// "��֯�����벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CostAccount_Import_fFullOrgUnitLongNumber"));
		}
		if (!StringUtils.isEmpty(fCurProjectLongNumber)) {
			if (fCurProjectLongNumber.length() > 80) {
				// "������Ŀ�������ֶγ��Ȳ��ܳ���80��"
				throw new TaskExternalException(getResource(context, "CostAccount_Import_fCurProjectLongNumber"));
			}
		} else {
			// "������Ŀ�����벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CostAccount_Import_fCurProjectLongNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fLongNumber)) {
			if (fLongNumber.length() > 80) {
				// "��Ŀ�������ֶγ��Ȳ��ܳ���80��"
				throw new TaskExternalException(getResource(context, "CostAccount_Import_fLongNumber"));
			}
		} else {
			// "��Ŀ�����벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CostAccount_Import_fLongNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fNameL2)) {
			if (fNameL2.length() > 80) {
				// "��Ŀ�����ֶγ��Ȳ��ܳ���80��"
				throw new TaskExternalException(getResource(context, "CostAccount_Import_fNameL2"));
			}
		} else {
			// "��Ŀ���Ʋ���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CostAccount_Import_fNameL2NotNull"));
		}
		if (!StringUtils.isEmpty(fDescriptionL2) && fDescriptionL2.length() > 200) {
			// "�����ֶγ��Ȳ��ܳ���200��"
			throw new TaskExternalException(getResource(context, "CostAccount_Import_fDescriptionL2"));
		}
		
		try {
			//��֯����
			FilterInfo filterfullOrgUnit = new FilterInfo();
			filterfullOrgUnit.getFilterItems().add(new FilterItemInfo("longnumber", fFullOrgUnitLongNumber.replace('.', '!')));
			EntityViewInfo viewfullOrgUnit = new EntityViewInfo();
			viewfullOrgUnit.setFilter(filterfullOrgUnit);
			FullOrgUnitCollection fullOrgUnitColl = FullOrgUnitFactory.getLocalInstance(context).getFullOrgUnitCollection(viewfullOrgUnit);
			if (fullOrgUnitColl.size() > 0){
				fullOrgUnit = fullOrgUnitColl.get(0);
				info.setFullOrgUnit(fullOrgUnit); 
			}else{
				// 1 "��֯����Ϊ��,���߸���֯���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "CostAccount_Import_fFullOrgUnitLongNumber1") + fFullOrgUnitLongNumber + getResource(context, "CostAccount_Import_NOTNULL"));
			}
			// ������Ŀ������
			FilterInfo filterCurProject = new FilterInfo();
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
				info.setCurProject(curProject);
			} else {
				// 1 "������Ŀ������Ϊ��,��ù�����Ŀ������ "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "CostAccount_Import_fCurProjectLongNumber1") + fCurProjectLongNumber + getResource(context, "CostAccount_Import_NOTNULL"));
			}
			/**
			 * �ɱ���Ŀ�����ظ�У��
			 */
//			// ������Ŀ�������Ӧ�ĵ�ǰ������ĿID�ظ�
//			FilterInfo filterCostAccount1 = new FilterInfo();
//			filterCostAccount1.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fullOrgUnit.getId().toString()));
//			filterCostAccount1.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
//			EntityViewInfo viewCostAccount1 = new EntityViewInfo();
//			viewCostAccount1.setFilter(filterCostAccount1);
//			CostAccountCollection costAccountColl1 = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewCostAccount1);
//			if (costAccountColl1.size() > 0){
//				// �ɱ���Ŀ���й�����Ŀ�������Ӧ�ĵ�ǰ������ĿID�ظ�ʱ����ʾ������**�ظ��� 
//				throw new TaskExternalException(getResource(context, "CostAccount_Import_LongNumber") + fCurProjectLongNumber + getResource(context, "CostAccount_Import_HasExisted"));
//			}
//			// ��Ŀ�������ظ�
//			FilterInfo filterCostAccount2 = new FilterInfo();
//			filterCostAccount2.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fullOrgUnit.getId().toString()));
//			filterCostAccount2.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
//			filterCostAccount2.getFilterItems().add(new FilterItemInfo("longnumber", fLongNumber));
//			EntityViewInfo viewCostAccount2 = new EntityViewInfo();
//			viewCostAccount2.setFilter(filterCostAccount2);
//			CostAccountCollection costAccountColl2 = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewCostAccount2);
//			if (costAccountColl2.size() > 0){
//				// �ɱ���Ŀ���п�Ŀ�������ظ�ʱ����ʾ������**�ظ��� 
//				throw new TaskExternalException(getResource(context, "CostAccount_Import_LongNumber") + fLongNumber + getResource(context, "CostAccount_Import_HasExisted"));
//			}
			// ��Ŀ�����ظ�
			FilterInfo filterCostAccount3 = new FilterInfo();
			filterCostAccount3.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fullOrgUnit.getId().toString()));
			filterCostAccount3.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
			filterCostAccount3.getFilterItems().add(new FilterItemInfo("name", fNameL2));
			EntityViewInfo viewCostAccount3 = new EntityViewInfo();
			viewCostAccount3.setFilter(filterCostAccount3);
			CostAccountCollection costAccountColl3 = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewCostAccount3);
			if (costAccountColl3.size() > 0){
				// �ɱ���Ŀ���п�Ŀ�����ظ�ʱ����ʾ����Ŀ����**�ظ���
				throw new TaskExternalException(getResource(context, "CostAccount_Import_Name") + fNameL2 + getResource(context, "CostAccount_Import_HasExisted"));
			}
			
			// Ϊ�ӽڵ�ʱ��ȡ�丸�ڵ����Ϣ
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
					// �ɱ���Ŀ���и��ӽڵ�ĸ��ڵ���Ϣ������ʱ����ʾ���ɱ���Ŀ���и��ӽڵ�ĸ��ڵ���Ϣ�����ڣ�
					throw new TaskExternalException(getResource(context, "CostAccount_Import_LongNumber") + fLongNumber + getResource(context, "CostAccount_Import_ParentLongNumberIsNotExsit"));
				}
				// ��Ŀ��� :������ӽڵ�Ŀ�Ŀ����Ŀ���Ϊ��ʱ��ʾ����Ŀ�����Ϊ�գ�¼���Ŀ��𲻴���ʱ����ʾ����Ŀ���**�����ڣ�
				if (!StringUtils.isEmpty(fType)) {
					// ��Ŀ������
					info.setLongNumber(fLongNumber.replace('.', '!'));
					// ��Ŀ����
					info.setName(fNameL2);

					if (fType.equals(getResource(context, "MAIN"))) {
						info.setType(CostAccountTypeEnum.MAIN);
					} else if (fType.equals(getResource(context, "SIX"))) {
						info.setType(CostAccountTypeEnum.SIX);
					} else {
						// 1 "��Ŀ��� "
						// 2 "��ϵͳ�в�����" 
						throw new TaskExternalException(getResource(context, "CostAccount_Import_fType") + fType + getResource(context, "CostAccount_Import_NOTNULL"));
					}
					// �ӽڵ�Ŀ�Ŀ ���������ϼ���Ŀ�Ŀ�Ŀ���һ�£������Ӽ���Ŀȡ������Ŀ�Ŀ�Ŀ���
					if (!info.getType().equals(parentCostAccountInfo.getType())) {
						info.setType(parentCostAccountInfo.getType());
					}					
				} else {
					// "��Ŀ��� ����Ϊ�գ�"
					throw new TaskExternalException(getResource(context, "CostAccount_Import_fTypeNotNull"));
				}
			} else { // Ϊ���ڵ�ʱ
				// ��Ŀ������
				info.setLongNumber(fLongNumber.replace('.', '!'));
				// ��Ŀ����
				info.setName(fNameL2);
				// ��ʾ�ÿ�Ŀ����
				info.setDisplayName(fNameL2);
				// �ɱ��� 
				if (fIsCostAccount.equals(getResource(context, "No"))) {
					info.setIsCostAccount(false);
				} else {
					info.setIsCostAccount(true);				
				}
			}
			// ����
			if (fNumbers.length > 1) {				
				info.setNumber(fNumber);
			} else {
				info.setNumber(fLongNumber);
			}
			// Coding����
			fCodingNumber = fLongNumber.replace('!', '.');
			info.setCodingNumber(fCodingNumber);
			// ���� 
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
	 * �õ���Դ�ļ�
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
	
}
