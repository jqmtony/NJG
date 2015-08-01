package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

public class ContractDetailDefTransmission extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";

	protected ICoreBase getController(Context ctx)throws TaskExternalException {
		try {
			return ContractDetailDefFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		ContractDetailDefInfo info = new ContractDetailDefInfo();
		ContractTypeInfo contractTypeInfo = null;
		
		//��ͬ����
		String fContractTypeName=(String) ((DataToken) hsData.get("FContractType_name_l2")).data;
		//��ͬ��ϸ��Ϣ����
		String fNumber=(String) ((DataToken) hsData.get("FNumber")).data;
		//��ͬ��ϸ��Ϣ����
		String fName=(String) ((DataToken) hsData.get("FName_l2")).data;
		//��������
		String fDataTypeEnum=(String) ((DataToken) hsData.get("FDataTypeEnum")).data;
		//�Ƿ��¼
		String fIsMustInput=(String) ((DataToken) hsData.get("FIsMustInput")).data;
		//����
		String fDescription=(String) ((DataToken) hsData.get("FDescription_l2")).data;
		//����
		String fIsEnabled=(String) ((DataToken) hsData.get("FIsEnabled")).data;
		/*
		 * �ж��Ƿ�Ϊ��
		 */
		if (StringUtils.isEmpty(fContractTypeName)) {
			throw new TaskExternalException(getResource(ctx,"Import_fContractTypeNotNull",resource));
		}
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fNumberNotNull",resource));
		}
		if (StringUtils.isEmpty(fName)) {
			throw new TaskExternalException(getResource(ctx,"Import_fNameNotNull",resource));
		}
		if (StringUtils.isEmpty(fDataTypeEnum)) {
			throw new TaskExternalException(getResource(ctx,"Import_fDataTypeEnumNotNull",resource));
		}
		/*
		 * �ж��ַ�����
		 */
		if (fNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_fConNumberLen80",resource));
		}
		if (fName.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_fConNameLen80",resource));
		}
		if (fDescription.length() > 200) {
			throw new TaskExternalException(getResource(ctx,"Import_fDescriptionLen200",resource));
		}
		//��ͬ���Ͳ������ж�
		try {
		    FilterInfo Filter = new FilterInfo();
		    Filter.getFilterItems().add(new FilterItemInfo("name", fContractTypeName));
		    EntityViewInfo View = new EntityViewInfo();
		    View.setFilter(Filter);
		    ContractTypeCollection contractTypeColl;
			contractTypeColl = ContractTypeFactory.getLocalInstance(ctx).getContractTypeCollection(View);
			if (contractTypeColl.size() > 0){
				contractTypeInfo = contractTypeColl.get(0);
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_fContractType",resource)+fContractTypeName+getResource(ctx,"Import_IsNotEx",resource));
			}
		} catch (BOSException e1) {
			throw new TaskExternalException(e1.getMessage(), e1);
		}

		//��������,ö���ж�
		if(fDataTypeEnum.trim().equals(getResource(ctx,"Import_DateType",resource))){
			info.setDataTypeEnum(DataTypeEnum.DATE) ;
		}else if(fDataTypeEnum.trim().equals(getResource(ctx,"Import_BooleanType",resource))){
			info.setDataTypeEnum(DataTypeEnum.BOOL);
		}else if(fDataTypeEnum.trim().equals(getResource(ctx,"Import_NumberType",resource))){
			info.setDataTypeEnum(DataTypeEnum.NUMBER);
		}else if(fDataTypeEnum.trim().equals(getResource(ctx,"Import_CharType",resource))){
			info.setDataTypeEnum(DataTypeEnum.STRING);
		}else if(fDataTypeEnum.trim().equals(getResource(ctx,"Import_BaseType",resource))){
			info.setDataTypeEnum(DataTypeEnum.BASEDATA);
		}else{
			throw new TaskExternalException(getResource(ctx,"Import_fDataType",resource) + fDataTypeEnum +getResource(ctx,"Import_LuRuWuX",resource));
		}
		/*
		 * �����ж�
		 */
		//�Ƿ��¼
		if(fIsMustInput.trim().equals(getResource(ctx, "yes",resourceCommon))){
			info.setIsMustInput(true);
		}else {
			info.setIsMustInput(false);
		}
		//����
		if (fIsEnabled.trim().equals(getResource(ctx, "yes",resourceCommon))) {
		    info.setIsEnabled(true);
	    }else {
		    info.setIsEnabled(false);
	    }
		info.setContractType(contractTypeInfo);
		info.setNumber(fNumber);
		info.setName(fName);
		info.setDescription(fDescription);
		return info;
	}
	/**
	 * �õ���Դ�ļ�
	 * @author ֣��Ԫ
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
}
