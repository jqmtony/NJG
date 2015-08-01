package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.TargetTypeCollection;
import com.kingdee.eas.fdc.basedata.TargetTypeFactory;
import com.kingdee.eas.fdc.basedata.TargetTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

public class ApportionTypeTransmission extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";
    	

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return ApportionTypeFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		ApportionTypeInfo info = new ApportionTypeInfo();
		MeasureUnitInfo measureUnitInfo = null;
		TargetTypeInfo targetTypeInfo = null;
		//ָ�����
		String fNumber = (String) ((DataToken) hsData.get("FNumber")).data;
		//ָ������
		String fNamel2 = (String) ((DataToken) hsData.get("FName_l2")).data;
		//ָ������
		String fTargetTypeNamel2 = (String) ((DataToken) hsData.get("FTargetType_name_l2")).data;
		//������λ
		String fMeasureUnitNamel2 = (String) ((DataToken) hsData.get("FMeasureUnit_name_l2")).data;
		//�ɱ���̯ʱʹ��
		String fForCostApportion = (String) ((DataToken) hsData.get("FForCostApportion")).data;
		//����
		String fDescriptionl2 = (String) ((DataToken) hsData.get("FDescription_l2")).data;
		//����
		String fIsEnabled = (String) ((DataToken) hsData.get("FIsEnabled")).data;
		
				
		//ָ�����
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_ZBfNumberNotNull",resource));
		}
		if (fNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_ZBfNumberLen80",resource));
		}
		//ָ������
		if (StringUtils.isEmpty(fNamel2)) {
			throw new TaskExternalException(getResource(ctx,"Import_ZBfNameNotNull",resource));
		}
		if (fNamel2.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_ZBfNameLen80",resource));
		}
		if (StringUtils.isEmpty(fTargetTypeNamel2)) {
			throw new TaskExternalException(getResource(ctx,"Import_ZBTypeNotNull",resource));
		}
		//ָ������
		try {
		    FilterInfo Filter = new FilterInfo();
		    Filter.getFilterItems().add(new FilterItemInfo("name", fTargetTypeNamel2));
		    EntityViewInfo View = new EntityViewInfo();
		    View.setFilter(Filter);
		    TargetTypeCollection targetTypeColl;
			targetTypeColl = TargetTypeFactory.getLocalInstance(ctx).getTargetTypeCollection(View);
			if (targetTypeColl.size() > 0){
				targetTypeInfo = targetTypeColl.get(0);
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_ZBType",resource)+fTargetTypeNamel2+getResource(ctx,"Import_IsNotEx",resource));
			}
			} catch (BOSException e) {
				throw new TaskExternalException(e.getMessage(), e);
			}
		//������λ
		try {
		    FilterInfo MFilter = new FilterInfo();
		    MFilter.getFilterItems().add(new FilterItemInfo("name", fMeasureUnitNamel2));
		    EntityViewInfo MView = new EntityViewInfo();
		    MView.setFilter(MFilter);
			MeasureUnitCollection measureUnitCollection = MeasureUnitFactory.getLocalInstance(ctx).getMeasureUnitCollection(MView);
			if (measureUnitCollection.size() > 0){
				measureUnitInfo = measureUnitCollection.get(0);
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_MeasureUnit",resource)+fMeasureUnitNamel2+getResource(ctx,"Import_IsNotEx",resource));
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
		//�ɱ���̯ʱʹ��
		if (fForCostApportion.trim().equals(getResource(ctx, "yes",resourceCommon))) {
				info.setForCostApportion(true);
		}else {
				info.setForCostApportion(false);
		}
		//����
		if (fDescriptionl2.length() > 200) {
			throw new TaskExternalException(getResource(ctx,"Import_fDescriptionLen200",resource));
		}
		//����
		if (fIsEnabled.trim().equals(getResource(ctx, "yes",resourceCommon))) {
			    info.setIsEnabled(true);
		}else {
			    info.setIsEnabled(false);
		}
		
		info.setTargetType(targetTypeInfo);
		info.setMeasureUnit(measureUnitInfo);
		info.setNumber(fNumber);
		info.setName(fNamel2);
		info.setDescription(fDescriptionl2);
		
				
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
