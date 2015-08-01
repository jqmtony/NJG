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
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.LandDeveloperCollection;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusCollection;
import com.kingdee.eas.fdc.basedata.ProjectStatusFactory;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeCollection;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.DbUtil;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		
 *		
 * @author		�쿡
 * @version		EAS7.0		
 * @createDate	2011-6-15	 
 * @see						
 */
public class ProjectTransmission extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";
	/** ���ڽ��ձ���һ�������Ĺ�����Ŀ�����룬������������޸ĸõ���Ĺ�����Ŀ������ */
	private String str_LongNumber = "";
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return CurProjectFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		CurProjectInfo info = new CurProjectInfo();
		ProjectStatusInfo projectStatusInfo = null;
		ProjectTypeInfo projectTypeInfo = null;
		LandDeveloperInfo landDeveloperInfo = null;
		ProductTypeInfo productTypeInfo = null;
		FullOrgUnitInfo fullOrgUnit = null; 
		FullOrgUnitInfo baseUnitInfo = null;

		//��֯����
		String fCostOrgLongNumber=(String) ((DataToken) hsData.get("FCostOrg_longNumber")).data;
		//������Ŀ������
		String fLongNumber=(String) ((DataToken) hsData.get("FLongNumber")).data;
		//��Ŀ״̬
		String fProjectStatusName=(String) ((DataToken) hsData.get("FProjectStatus_name_l2")).data;
		//��Ŀϵ��
		String fProjectTypeName=(String) ((DataToken) hsData.get("FProjectType_name_l2")).data;
		//����
		String fName=(String) ((DataToken) hsData.get("FName_l2")).data;
		//���̼���
		String fCodingNumber=(String) ((DataToken) hsData.get("FCodingNumber")).data;
		//������Ŀ
		String fIsDevPrj=(String) ((DataToken) hsData.get("FIsDevPrj")).data;
		//�׷�
		String fLandDeveloperName=(String) ((DataToken) hsData.get("FLandDeveloper_name_l2")).data;
		//��ʼ����
		String fStartDate=(String) ((DataToken) hsData.get("FStartDate")).data;
		//����˳��
		String fSortNo=(String) ((DataToken) hsData.get("FSortNo")).data;
		//����
		String fDescription=(String) ((DataToken) hsData.get("FDescription_l2")).data;
		//��Ŀ��ַ
		String fProjectAddress=(String) ((DataToken) hsData.get("FProjectAddress")).data;
		//����
		String fIsEnabled=(String) ((DataToken) hsData.get("FIsEnabled")).data;
		//��Ʒ����/�Ƿ�������
		String fProductTypeIsAccObj=(String) ((DataToken) hsData.get("FProductType&isAccObj")).data;
		
		
		if (StringUtils.isEmpty(fCostOrgLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "Import_CostOrgLongNumberIsNull",resource));
		}
		if (StringUtils.isEmpty(fLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "Import_NumberIsNull",resource));
		}
		if (fLongNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_LongNumberIsTooLong",resource));
		}
		if (StringUtils.isEmpty(fProjectStatusName)) {
			throw new TaskExternalException(getResource(ctx, "Import_ProjectStatusNameIsNull",resource));
		}
		if (StringUtils.isEmpty(fProjectTypeName)) {
			throw new TaskExternalException(getResource(ctx, "Import_ProjectTypeNameIsNull",resource));
		}
		if (StringUtils.isEmpty(fName)) {
			throw new TaskExternalException(getResource(ctx, "Import_NameIsNull",resource));
		}
		if (!StringUtils.isEmpty(fName) && fName.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_NameIsTooLong",resource));
		}
		
		//���̼���
		info.setCodingNumber(fCodingNumber);
		
		//������Ŀ
		if (fIsDevPrj.trim().equals(getResource(ctx, "no",resourceCommon))) {
			info.setIsDevPrj(false);
		} else {
			info.setIsDevPrj(true);
		}
		
		//��ʼ����
		if (StringUtils.isEmpty(fStartDate)) {
			throw new TaskExternalException(getResource(ctx, "Import_StartDateIsNull",resource));
		}
		info.setStartDate(FDCTransmissionHelper.checkDateFormat(fStartDate, getResource(ctx,"DateFormatIsWrong",resource)));
		
		//����˳��
		if(StringUtils.isEmpty(fSortNo)) {
			info.setSortNo(0);
		}else if(fSortNo.matches("^\\d*|\\d*.\\d*$")) {
			int a;
			double b = Double.parseDouble(fSortNo);
			a = (int)b;
			info.setSortNo(a);
		}else {
			throw new TaskExternalException(getResource(ctx, "Import_IsNotNumber",resource));
		}
		//����
		if (!StringUtils.isEmpty(fDescription) && fDescription.length() > 200) {
			throw new TaskExternalException(getResource(ctx, "Import_DescriptionTooLength",resource));
		}
		info.setDescription(fDescription);
		//��Ŀ��ַ
		if (!StringUtils.isEmpty(fProjectAddress) && fProjectAddress.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_AddressTooLang",resource));
		}
		info.setProjectAddress(fProjectAddress);
		
		//����
		if (fIsEnabled.trim().equals(getResource(ctx, "yes",resourceCommon))) {
			info.setIsEnabled(true);
		}else {
			info.setIsEnabled(false);
		}

		try {
			
			//����
			FilterInfo curProjectNameFilter = new FilterInfo();
			EntityViewInfo curProjectNameView = new EntityViewInfo();
			curProjectNameFilter.getFilterItems().add(new FilterItemInfo("name", fName));
			curProjectNameView.setFilter(curProjectNameFilter);
			CurProjectCollection curProjectNameColl = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectNameView);
			if(curProjectNameColl.size()>0){
				throw new TaskExternalException(getResource(ctx, "Import_NameIsExist",resource));
			}else{
				info.setName(fName);
			}
			
			// ������Ŀ������
			FilterInfo curFilter = new FilterInfo();
			EntityViewInfo curView = new EntityViewInfo();
			curFilter.getFilterItems().add(new FilterItemInfo("LongNumber", fLongNumber.replace('.', '!')));
			curView.setFilter(curFilter);
			CurProjectCollection curcoll = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curView);
			if(curcoll.size()>0){
				throw new TaskExternalException(getResource(ctx, "Import_IsExist",resource));
			}else{
				info.setLongNumber(fLongNumber.replace('.', '!'));
			}
			//��������ֵ
			String[] numbers = fLongNumber.replace('.', '!').split("!");
			if(numbers.length>1)
			{
				info.setNumber(numbers[numbers.length-1]);
			}else if(numbers.length == 1)
			{
				info.setNumber(fLongNumber.replace('.', '!'));
			}
			
			//��֯����		
			FilterInfo filterfullOrgUnit = new FilterInfo();
			filterfullOrgUnit.getFilterItems().add(new FilterItemInfo("longnumber", fCostOrgLongNumber.replace('.', '!')));
			EntityViewInfo viewfullOrgUnit = new EntityViewInfo();
			viewfullOrgUnit.setFilter(filterfullOrgUnit);
			FullOrgUnitCollection fullOrgUnitColl = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection(viewfullOrgUnit);
			//�������֯����
			if (fullOrgUnitColl.size() > 0){
				fullOrgUnit = fullOrgUnitColl.get(0);
				//�Ƿ���ʵ�������֯
				if(fullOrgUnit.isIsCompanyOrgUnit()){
					//�ڲ�����֯�в��Ƿ����
					FilterInfo companyOrgUnitFilter = new FilterInfo();
					companyOrgUnitFilter.getFilterItems().add(new FilterItemInfo("longnumber", fCostOrgLongNumber.replace('.', '!')));
					EntityViewInfo companyOrgUnitView = new EntityViewInfo();
					companyOrgUnitView.setFilter(companyOrgUnitFilter);
					CompanyOrgUnitCollection companyOrgUnitColl = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection(companyOrgUnitView);
					if(companyOrgUnitColl.size() > 0) {
						info.setFullOrgUnit(fullOrgUnit);
					}
				}
				//�Ƿ��ǳɱ�����
				else if(fullOrgUnit.isIsCostOrgUnit()){
					//�ڳɱ������в��Ƿ����
					FilterInfo costCenterOrgUnitFilter = new FilterInfo();
					costCenterOrgUnitFilter.getFilterItems().add(new FilterItemInfo("longnumber", fCostOrgLongNumber.replace('.', '!')));
					EntityViewInfo costCenterOrgUnitView = new EntityViewInfo();
					costCenterOrgUnitView.setFilter(costCenterOrgUnitFilter);
					CostCenterOrgUnitCollection costCenterOrgUnitColl = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(costCenterOrgUnitView);
					if(costCenterOrgUnitColl.size() > 0) {
						costCenterOrgUnitColl.get(0);
						//�õ��ϼ���֯����
//						String baseUnitNumber = costCenterOrgUnitInfo.getParent().getNumber();//baseUnitNumberΪ�հ�
						String[] baseUnitNumbers = fCostOrgLongNumber.replace('.', '!').split("!");
						String newBaseUnitNumber = null;
						for(int i=0; i<baseUnitNumbers.length-1; i++) {
							newBaseUnitNumber += baseUnitNumbers[i] + "!";
						}
						newBaseUnitNumber = newBaseUnitNumber.substring(4, newBaseUnitNumber.length()-1);
						FilterInfo baseUnitFilter = new FilterInfo();
						baseUnitFilter.getFilterItems().add(new FilterItemInfo("longnumber", newBaseUnitNumber));
						EntityViewInfo baseUnitView = new EntityViewInfo();
						baseUnitView.setFilter(baseUnitFilter);
						FullOrgUnitCollection baseUnitColl = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection(baseUnitView);
						//�������֯����
						if (baseUnitColl.size() > 0){
							baseUnitInfo = baseUnitColl.get(0);
							info.setFullOrgUnit(baseUnitInfo);
//							info.setCostOrg(costCenterOrgUnitInfo);
						}
					}
				}
			}else {
				throw new TaskExternalException(getResource(ctx, "OrgUnitNumberNonexist",resource));
			}
			
			//��Ŀ״̬
			FilterInfo projectStatusFilter = new FilterInfo();
			projectStatusFilter.getFilterItems().add(new FilterItemInfo("name", fProjectStatusName));
			EntityViewInfo projectStatusView = new EntityViewInfo();
			projectStatusView.setFilter(projectStatusFilter);
			ProjectStatusCollection projectStatusCollection = ProjectStatusFactory.getLocalInstance(ctx).getProjectStatusCollection(projectStatusView);
			if (projectStatusCollection.size() > 0){
				projectStatusInfo = projectStatusCollection.get(0);
				info.setProjectStatus(projectStatusInfo);
			}else{
				throw new TaskExternalException(getResource(ctx, "ProjectStatus",resource)+getResource(ctx, "Import_IsNotExist",resource));
			}
		
			//��Ŀϵ��
			FilterInfo projectTypeFilter = new FilterInfo();
			projectTypeFilter.getFilterItems().add(new FilterItemInfo("name", fProjectTypeName));
			EntityViewInfo projectTypeView = new EntityViewInfo();
			projectTypeView.setFilter(projectTypeFilter);
			ProjectTypeCollection projectTypeCollection = ProjectTypeFactory.getLocalInstance(ctx).getProjectTypeCollection(projectTypeView);
			if (projectTypeCollection.size() > 0){
				projectTypeInfo = projectTypeCollection.get(0);
				info.setProjectType(projectTypeInfo);
			}else{
				throw new TaskExternalException(getResource(ctx, "ProjectType",resource)+getResource(ctx, "Import_IsNotExist",resource));
			}
		
			//�׷�
			FilterInfo landDeveloperFilter = new FilterInfo();
			landDeveloperFilter.getFilterItems().add(new FilterItemInfo("name", fLandDeveloperName));
			EntityViewInfo landDeveloperView = new EntityViewInfo();
			landDeveloperView.setFilter(landDeveloperFilter);
			LandDeveloperCollection landDeveloperCollection = LandDeveloperFactory.getLocalInstance(ctx).getLandDeveloperCollection(landDeveloperView);
			if (landDeveloperCollection.size() > 0){
				landDeveloperInfo = landDeveloperCollection.get(0);
				info.setLandDeveloper(landDeveloperInfo);
			}
			
			
			// ��Ʒ����/�Ƿ������� 
//			if(StringUtils.isEmpty(fProductTypeIsAccObj)) {
//				throw new TaskExternalException(getResource(ctx, "Import_FormatIsNotRight"));
//			}
			if(!StringUtils.isEmpty(fProductTypeIsAccObj))
			{
				String [] fProductTypeIsAccObjs = fProductTypeIsAccObj.split(",");
	//			if(fProductTypeIsAccObjs.length == 1) {
	//				throw new TaskExternalException(getResource(ctx, "Import_FormatIsNotRight"));
	//			}
				String fProductType = fProductTypeIsAccObjs[0];
				String fIsAccObjs = "";
				if(fProductTypeIsAccObjs.length>=2 ){//
					fIsAccObjs = fProductTypeIsAccObjs[1];
				}
				if(StringUtils.isEmpty(fProductType)) {
					throw new TaskExternalException(getResource(ctx, "Import_ProductTypeIsNull",resource));
				}
				CurProjProductEntriesInfo curProjProductEntriesInfo = new CurProjProductEntriesInfo();
				
				FilterInfo productTypeFilter = new FilterInfo();
				productTypeFilter.getFilterItems().add(new FilterItemInfo("name", fProductType));
				EntityViewInfo productTypeView = new EntityViewInfo();
				productTypeView.setFilter(productTypeFilter);
				ProductTypeCollection productTypeCollection = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(productTypeView);
				if (productTypeCollection.size() > 0){
					productTypeInfo = productTypeCollection.get(0);
					curProjProductEntriesInfo.setProductType(productTypeInfo);
				}else{
					throw new TaskExternalException(getResource(ctx, "Import_ProductTypeIsNotExsit",resource));
				}
				if (fIsAccObjs.trim().equals(getResource(ctx, "no",resourceCommon))) {
					curProjProductEntriesInfo.setIsAccObj(false);
				}else {
					curProjProductEntriesInfo.setIsAccObj(true);
				}
				curProjProductEntriesInfo.setCurProject(info);
				info.getCurProjProductEntries().add(curProjProductEntriesInfo);
			}

			// ��Ϊֱ�ӷ���info֮�����ݿ��б����LongNumber���Զ�����Ϊ��number��ͬ������ڷ���֮ǰ��LongNumber����ȫ�ֱ���str_LongNumber��������޸�LongNumber
			str_LongNumber = info.getLongNumber();
			
			return info;			
		}catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
	}
	
	/**
	 * @description		����submit�����������޸ĵ���Ĺ�����Ŀ�ĳ����룡
	 * @author				Ӻ����		
	 * @createDate			2011-7-21
	 * @param				coreBaseInfo
	 * @param				context
	 * @return				void	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#submit(com.kingdee.eas.framework.CoreBaseInfo, com.kingdee.bos.Context)					
	 */
	public void submit(CoreBaseInfo coreBaseInfo, Context context) throws TaskExternalException {
		
		// �ڱ���õ���Ĺ�����Ŀ֮ǰ��Ҫ�ж��Ƿ��й�����Ŀ
		// �õ�ȫ�ֱ���str_LongNumber������õ��Ĺ�����ĿΪ�����׳���Ϣ��ʾ��
		String longNumber = str_LongNumber.trim();
		if (StringUtils.isEmpty(longNumber)) {
			throw new TaskExternalException("���빤����Ŀʧ�ܣ�ԭ��û�л�ȡ��������Ŀ�����룡�볢�����µ��롣");
		}
		
		/** ����õ��Ĺ�����Ŀ��Ϊ�գ���ִ��submit��������õ���Ĺ�����Ŀ����ִ��<����һ��ʼ�Ѿ��ж�> */
		super.submit(coreBaseInfo, context);
		
		/**	
		 * �޸Ĺ�����Ŀ����������������
		 * 		��Ϊ������ʲô�����<�������µ�ֱ��SQL>ȥִ���޸�ʱ�����������CurProjectControllerBean.java���еķ���saveBaseProject��
		 * 		һ��ִ�е��÷����󣬽��ᾭ��һϵ�е�Դ�����ڴ���֮��Ĺ�����Ŀ�������빤����Ŀ������Ȼ����ı䡣
		 *  	��˲�����ֱ�ӵ�SQLִ��Update�������޸ĸù�����Ŀ������Ϊ�������Ŀ�����룡
		 *  ��ִ��ԴSQL�����ɣ� ��Ϊ������Ŀ�����������ݿ���û�п��ظ����ݣ���˿����ж�Ψһ�ԡ�
		 */
		try {
			String sql_UpdateLongNumber = "update T_FDC_CurProject set  FLongNumber = ? where FName_l2 = ?";
			Object[] obj_params = new Object[] {longNumber, ((CurProjectInfo) coreBaseInfo).getName()};
			DbUtil.execute(context, sql_UpdateLongNumber, obj_params);
		} catch (BOSException e) {
			// @AbortException ���SQLִ��ʧ������ʾ�����õ���Ĺ�����Ŀ��¼ִ��Deleteɾ��
			String sql_DeleteCurProjectInfo = "delete from T_FDC_CurProject where FName_l2 = ?";
			Object[] obj_param = new Object[] {((CurProjectInfo) coreBaseInfo).getName()};
			try {
				DbUtil.execute(context, sql_DeleteCurProjectInfo, obj_param);
			} catch (BOSException e1) {
				// ���ɾ��ʧ��ʱ��
				throw new TaskExternalException("�㵼��Ĺ�����Ŀ�Ѿ���Ϊ���ݿ��е������ݣ������������ݿ⣡��ܰ��ʾ���ڵ��빤����Ŀʱ�뱣֤���ݵ���ȷ�ԣ�������ִ��󣡣�");
			}

		}
		
//		//��ע�����ã�ԭ�������Ѿ�ע��
//		/** �ڸĹ�����Ŀ�ɹ�����֮������޸ĸù�����Ŀ�ĳ�����<ԭ��Ϊ�ù�����Ŀ��֮ǰ����ʱ�����볤������ͬ> */
//		try {
//			if (!(coreBaseInfo instanceof CurProjectInfo)) {
//				return;
//			}
//			CurProjectInfo curProject = (CurProjectInfo) coreBaseInfo;
//			// ���ҹ�����Ŀ����Ϊnull�������޸�����롣
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("number", curProject.getNumber()));
//			EntityViewInfo view = new EntityViewInfo();
//			view.setFilter(filter);
//			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(view);
//			if (curProjectColl.size() > 0) {
//				CurProjectInfo curProjectInfo = curProjectColl.get(0);
//				//����������ֵ
//				curProjectInfo.setLongNumber(longNumber);
//				CurProjectFactory.getLocalInstance(context).update(new ObjectUuidPK(String.valueOf(curProjectInfo.getId())), curProjectInfo);
//			}
//		} catch (BOSException e) {
//			e.printStackTrace();
//		} catch (EASBizException e) {
//			e.printStackTrace();
//		}
		
	}
	
	/**
	 * �õ���Դ�ļ�
	 * @author �쿡
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
	 
	
}
