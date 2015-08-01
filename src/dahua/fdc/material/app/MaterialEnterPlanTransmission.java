package com.kingdee.eas.fdc.material.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillCollection;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillFactory;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillInfo;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryCollection;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * 
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		���Ͻ����ƻ�ת����
 *		
 * @author		xiaochong
 * @version		EAS7.0		
 * @createDate	2011-6-13	 
 * @see
 */
public class MaterialEnterPlanTransmission extends AbstractDataTransmission {
	
	private static final String resource = "com.kingdee.eas.fdc.material.MaterialTransmissionResource";
	// ����״̬ӳ��
	private Map stateMap = new HashMap();
	// ���Ͻ����ƻ�ʵ��
	MaterialEnterPlanBillInfo materialEnterPlanBillinfo = null;
	// ������Ŀʵ��
	CurProjectInfo curProjectInfo = null;
	// ��֯ʵ��
	FullOrgUnitInfo orgUnitInfo = null;
	// ʩ����ͬʵ��
	ContractBillInfo contractBillInfo = null;
	// �Ƶ���ʵ��
	UserInfo creatorInfo = null;

	/** ����״̬ӳ���ʼ�� */
	public void initStateMap(Context ctx) {
		stateMap.put(getResource(ctx, "AUDITTED"), "4AUDITTED");
		stateMap.put(getResource(ctx, "SUBMITTED"), "2SUBMITTED");
		stateMap.put(getResource(ctx, "SAVED"), "1SAVED");
	}

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return MaterialEnterPlanBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
	}


	/** ת������ */
	public CoreBaseInfo transmit(Hashtable hsdata, Context ctx)
			throws TaskExternalException {
		initStateMap(ctx);
		return innerTransform(hsdata, ctx);
	}

	/** �ڵ�ת�������� */
	protected CoreBaseInfo innerTransform(Hashtable hsdata, Context ctx)
			throws TaskExternalException {
		try {
			materialEnterPlanBillinfo = innerTransformHead(hsdata, ctx);
			MaterialEnterPlanEntryInfo entry = innerTransformEntry(hsdata, ctx);
			int seq = materialEnterPlanBillinfo.getEntrys().size() + 1;
			entry.setSeq(seq);
			entry.setParent(materialEnterPlanBillinfo);
			materialEnterPlanBillinfo.getEntrys().add(entry);
		} catch (TaskExternalException e) {
			materialEnterPlanBillinfo = null;
			throw e;
		}
		return materialEnterPlanBillinfo;
	}
	
	
	/** ����ͷ*ת�������� */
	private MaterialEnterPlanBillInfo innerTransformHead(Hashtable hsdata, Context ctx) throws TaskExternalException{
		
		String curProjectCostCenterLongNumber = getString(hsdata, "FCurProjectCostCenter_longNumber"); 		// ��֯������
		String curProjectLongNumber = getString(hsdata, "FCurProject_longNumber"); 							// ������Ŀ����
		String number = getString(hsdata, "FNumber"); 														// ���ݱ���
		String contractBillNumber = getString(hsdata, "FContractBill_number"); 								// ʩ����ͬ
//		String contractBillPartBName = getString(hsdata, "FContractBillPartB_name_l2"); 					// ʩ����λ
		String address = getString(hsdata, "FAddress"); 													// �ͻ���ַ
		String creatorNumber = getString(hsdata, "FCreator_number"); 										// �Ƶ���
		String createTime = getString(hsdata, "FCreateTime"); 												// �Ƶ�����
		String auditorNumber = getString(hsdata, "FAuditor_number"); 										// ������
		String auditTime = getString(hsdata, "FAuditTime"); 												// ����ʱ��
		String state = getString(hsdata, "FState");															// ����״̬
		
		
		
		// ��¼У��
		FDCTransmissionHelper.isFitLength(getResource(ctx, "CurProjectLongNumberIsNull"), curProjectLongNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanNumberIsNull"), number);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "ContractBillNumberIsNull"), contractBillNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanCreatorNumberIsNull"), creatorNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanCreateTimeIsNull"), createTime);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanState"), state);
		// ����У��
		FDCTransmissionHelper.isFitLength(curProjectCostCenterLongNumber, 40, getResource(ctx, "CurProjectCostCenterLongNumberIsOver40"));
		FDCTransmissionHelper.isFitLength(curProjectLongNumber, 40, getResource(ctx, "CurProjectLongNumberIsOver40"));
		FDCTransmissionHelper.isFitLength(number, 80, getResource(ctx, "MaterialEnterPlanNumberIsOver80"));
		FDCTransmissionHelper.isFitLength(address, 80, getResource(ctx, "MaterialEnterPlanAddressIsOver80"));
		
		// ����ת��У��
		Date createDate = FDCTransmissionHelper.checkDateFormat(createTime, getResource(ctx, "DateFormatIsError"));
		// ����״̬У��
		FDCBillStateEnum stateEnum = getState(ctx, (String) stateMap.get(state.trim()));
		// ���ݵ���״̬У�� �����ˡ��������ڱ�¼
		if(stateEnum.equals(FDCBillStateEnum.AUDITTED)){
			FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanAuditor"), auditorNumber);
			FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanAuditTime"),auditTime);
		}
		
		
		
		
		UserInfo auditorInfo = null;					// ������ʵ��
		Date auditorDate = null;						// ����ʱ��
		
		try {
			// ��鵥���Ƿ��Ѵ���
			MaterialEnterPlanBillCollection coll = MaterialEnterPlanBillFactory.getLocalInstance(ctx)
							.getMaterialEnterPlanBillCollection(getEntityViewInfoInstance("number", number));
			if (isExist(coll)) {
				return materialEnterPlanBillinfo = coll.get(0);
			} else {
				materialEnterPlanBillinfo = new MaterialEnterPlanBillInfo();
			}
			
			
			
			// �����ݿ��л�ȡ������Ŀ
			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(
							getEntityViewInfoInstance("longNumber", curProjectLongNumber.replace('.', '!')));
			if (!isExist(curProjectColl)) {
				FDCTransmissionHelper.isThrow(getResource(ctx, "CurProjectIsNotExist"));
			}
			curProjectInfo = curProjectColl.get(0);
			

			// У����֯������
			CostCenterOrgUnitInfo costCenterOrgUnit = curProjectInfo.getCostCenter();
			String id = getUid(costCenterOrgUnit);
			CostCenterOrgUnitCollection costCenterOrgUnitColl = CostCenterOrgUnitFactory
					.getLocalInstance(ctx).getCostCenterOrgUnitCollection(getEntityViewInfoInstance("id", id));
			
			if (isExist(costCenterOrgUnitColl)) {
				if (!StringUtils.isEmpty(curProjectCostCenterLongNumber)
						&& !costCenterOrgUnitColl.get(0).getLongNumber()
								.equalsIgnoreCase(curProjectCostCenterLongNumber.trim().replace('.', '!'))) {
					FDCTransmissionHelper.isThrow(getResource(ctx, "CurProjectCostCenterIsNotExist"));
				}
				costCenterOrgUnit = costCenterOrgUnitColl.get(0);
			}
			orgUnitInfo = costCenterOrgUnit.castToFullOrgUnitInfo();
			
			
			
			// �����ݿ��л�ȡʩ����ͬ// ����ʩ����ͬ�Զ�����ʩ����λ
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(
							getEntityViewInfoInstance("number", contractBillNumber));
			if (!isExist(contractBillColl)) {
					FDCTransmissionHelper.isThrow(getResource(ctx, "ContractBillIsNotExist"));
			}
			contractBillInfo = contractBillColl.get(0);
			
			

			// �����ݿ��л�ȡ�Ƶ���
			UserCollection userColl = UserFactory.getLocalInstance(ctx)
					.getUserCollection( getEntityViewInfoInstance("number", creatorNumber));
			if (!isExist(userColl)) {
					FDCTransmissionHelper.isThrow(getResource(ctx, "CreatorIsNotExist"));
			}
			creatorInfo = userColl.get(0);
			

			
			
			// ��auditorNumber��Ϊ��ʱ�����ݿ��л�ȡ������
			if (!StringUtils.isEmpty(auditorNumber)) {
				userColl = UserFactory.getLocalInstance(ctx).getUserCollection(
						getEntityViewInfoInstance("number", auditorNumber));
				if (!isExist(userColl)) {
						FDCTransmissionHelper.isThrow(getResource(ctx, "AuditorIsNotExist"));
				}
				auditorInfo = userColl.get(0);
			}
			// ��auditTime��Ϊ��ʱ����ת��У��
			if (!StringUtils.isEmpty(auditTime)) {
				auditorDate = FDCTransmissionHelper.checkDateFormat(auditTime, getResource(ctx, "DateFormatIsError"));
			}
			
			
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		materialEnterPlanBillinfo.setOrgUnit(orgUnitInfo);
		materialEnterPlanBillinfo.setCurProject(curProjectInfo);
		materialEnterPlanBillinfo.setNumber(number);
		materialEnterPlanBillinfo.setContractBill(contractBillInfo);
		materialEnterPlanBillinfo.setAddress(address);
		materialEnterPlanBillinfo.setCreator(creatorInfo);
		materialEnterPlanBillinfo.setCreateTime(new Timestamp(createDate.getTime()));
		materialEnterPlanBillinfo.setAuditor(auditorInfo);
		materialEnterPlanBillinfo.setAuditTime(auditorDate);
		materialEnterPlanBillinfo.setState(stateEnum);
		
		return materialEnterPlanBillinfo;
	}
	
	
	/** ��¼*ת�������� */
	private MaterialEnterPlanEntryInfo innerTransformEntry(Hashtable hsdata, Context ctx) throws TaskExternalException{
		
		String entrysMaterialNumber = getString(hsdata, "FEntrysMaterial_number"); 							// ���ϱ���
//		String entrysMaterialName = getString(hsdata, "FEntrysMaterial_name_l2"); 							// ��������
		String entrysModel = getString(hsdata, "FEntrys_model"); 											// ����ͺ�
		String entrysProduceLeadTime = getString(hsdata, "FEntrys_produceLeadTime"); 						// �ջ�����ǰ����
		String entrysUnitName = getString(hsdata, "FEntrysUnit_name_l2"); 									// ��λ
		String entrysQuantity = getString(hsdata, "FEntrys_quantity"); 										// �걨����
		String entrysEnterTime = getString(hsdata, "FEntrys_enterTime"); 									// ����ʱ��
		String entrysAuditQuantity = getString(hsdata, "FEntrys_auditQuantity"); 							// �˶�����
		String entrysRemark = getString(hsdata, "FEntrys_remark"); 											// ��ע
		String entrysMaterialCon = getString(hsdata, "FEntrys_materialCon"); 								// ���Ϻ�ͬ
		String entrysSupplier = getString(hsdata, "FEntrys_supplier"); 										// ��Ӧ��
		
		
		// ��¼У��
		FDCTransmissionHelper.isFitLength(getResource(ctx, "EntrysMaterialNumberIsNull"), entrysMaterialNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "EntrysQuantityIsNull"), entrysQuantity);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "EntrysAuditQuantityIsNull"), entrysAuditQuantity);
		// ����У��
		FDCTransmissionHelper.isFitLength(entrysMaterialNumber, 40, getResource(ctx, "EntrysMaterialNumberIsOver80"));
		FDCTransmissionHelper.isFitLength(entrysRemark, 80, getResource(ctx, "EntrysRemarkIsOver80"));
		
		// BigDecimal��ֵת��У��
		BigDecimal quantityDecimal=FDCTransmissionHelper.str2BigDecimal(entrysQuantity);						
		BigDecimal auditQuantityDecimal=FDCTransmissionHelper.str2BigDecimal(entrysAuditQuantity);
		
		// ����д���ջ����ǰ�������������ֵת��У��
		BigDecimal produceLeadTime=null;
		if(!StringUtils.isEmpty(entrysProduceLeadTime)){
			if(!entrysProduceLeadTime.matches("^\\d+$")) FDCTransmissionHelper.isThrow(getResource(ctx, "MaterialEnterPlanNumberFormatError"));
			produceLeadTime=new BigDecimal(entrysProduceLeadTime);
		}
		// ����д�˽���ʱ�䣬����и�ʽת��У��
		Date enterTime=null;
		if(!StringUtils.isEmpty(entrysEnterTime)){
			enterTime = FDCTransmissionHelper.checkDateFormat(entrysEnterTime, getResource(ctx, "DateFormatIsError"));
		}
		
		
		
		
			
		
		
		MaterialInfo materialInfo = null; 					// ����
		MeasureUnitInfo unitInfo = null; 					// ��λ
		try {
			
			if(materialEnterPlanBillinfo.getId()!=null)
			{
				MaterialEnterPlanEntryCollection planEntryColl = materialEnterPlanBillinfo.getEntrys();
				if (isExist(planEntryColl)) {
					for (int i = 0; i < planEntryColl.size(); i++) {
						
						String id = getUid(planEntryColl.get(i).getMaterial());
						
						MaterialCollection materialColl = MaterialFactory.getLocalInstance(ctx)
										.getMaterialCollection(getEntityViewInfoInstance("id", id));
						if (isExist(materialColl) && materialColl.get(0).getNumber()
										.equalsIgnoreCase(entrysMaterialNumber)) {
							FDCTransmissionHelper.isThrow(getResource(ctx, "EntryIsRepeated"));
						}
					}
				}
			}
			
			// �����ݿ��л�ȡ����
			MaterialCollection materialColl = MaterialFactory.getLocalInstance(
					ctx).getMaterialCollection(getEntityViewInfoInstance("number",entrysMaterialNumber));
			if (!isExist(materialColl)) {
				FDCTransmissionHelper.isThrow(getResource(ctx, "EntrysMaterialIsNotExist"));
			}
			materialInfo = materialColl.get(0);

			// �����ݿ��л�ȡ��λ
			MeasureUnitCollection unitColl = MeasureUnitFactory.getLocalInstance(
					ctx).getMeasureUnitCollection(getEntityViewInfoInstance("name", entrysUnitName));
			if (StringUtils.isEmpty(entrysUnitName) || !isExist(unitColl)) {
				// FDCTransmissionHelper.isThrow(entrysUnitName, getResource(ctx, "MaterialMeasureUnitIsNotExist"));
				String id = getUid(materialInfo.getBaseUnit());

				MeasureUnitCollection coll = MeasureUnitFactory .getLocalInstance(ctx)
									.getMeasureUnitCollection(getEntityViewInfoInstance("id", id));
				if (isExist(coll)) unitInfo = coll.get(0);
			} else {
				unitInfo = unitColl.get(0);
			}
			
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		
		
		
		MaterialEnterPlanEntryInfo info=new MaterialEnterPlanEntryInfo();
		info.setMaterial(materialInfo);
		info.setModel(entrysModel);
		info.setProduceLeadTime(produceLeadTime);
		info.setUnit(unitInfo);	
		info.setQuantity(quantityDecimal);
		info.setEnterTime(enterTime);
		info.setAuditQuantity(auditQuantityDecimal);
		info.setRemark(entrysRemark);
		info.setMaterialCon(entrysMaterialCon);
		info.setSupplier(entrysSupplier);
		
		return info;
	}
	
	
	/** ��ȡ�ַ������� */
	private String getString(Hashtable hsdata, Object key) {
		return (String) ((DataToken) hsdata.get(key)).data;
	}
	
	/** У�鵥��״̬����ȡ��Ӧ״̬ö�ٶ��� */
	private FDCBillStateEnum getState(Context ctx,String state)
			throws TaskExternalException {
		FDCBillStateEnum stateEnum = null;

		if (FDCBillStateEnum.SAVED_VALUE.equalsIgnoreCase(state)) {
			stateEnum = FDCBillStateEnum.SAVED;
		} else if (FDCBillStateEnum.SUBMITTED_VALUE.equalsIgnoreCase(state)) {
			stateEnum = FDCBillStateEnum.SUBMITTED;
		} else if (FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase(state)) {
			stateEnum = FDCBillStateEnum.AUDITTED;
		} else {
			FDCTransmissionHelper.isThrow(state, getResource(ctx, "MaterialEnterPlanUnknownState"));
		}

		return stateEnum;
	}
	
	/** ָ������������ȡ��ͼʵ�� */
	private EntityViewInfo getEntityViewInfoInstance(String property, String value) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(property, value, CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);

		return view;
	}
	
	/** �����Ƿ���� */
	private boolean isExist(IObjectCollection collection) {
		return (collection == null || collection.size() == 0) ? false : true;
	}
	
	/** ��ȡInfo�����ID�ַ��� */
	private String getUid(CoreBaseInfo info) {
		BOSUuid uId = info.getId();
		return (uId != null) ? uId.toString() : "";
	}
	
	/**
	 * �õ���Դ�ļ�
	 * @author ֣��Ԫ
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
