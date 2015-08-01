package com.kingdee.eas.fdc.material.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.PartAMaterialCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.fdc.material.PartAMaterialFactory;
import com.kingdee.eas.fdc.material.PartAMaterialInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.StringUtils;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		材料明细单引入引出数据转换类
 *		
 * @author		王川	
 * @createDate	2011-6-29		
 */
public class PartAMaterialTransmission extends AbstractDataTransmission {

	PartAMaterialInfo info = null;
	
	PartAMaterialEntryInfo infoEntry = null;
	
	private static String resource = "com.kingdee.eas.fdc.material.FinanceTransmissionResource";
	
//	static Map stateMap = new HashedMap();
//	
//	static {
//		stateMap.put("保存", "1SAVED");
//		stateMap.put("已提交", "2SUBMITTED");
//		stateMap.put("已审批", "4AUDITTED");
//	}
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = PartAMaterialFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		try {
			info = transmitHead(hsData, ctx);
			if (info == null) {
				return null;
			}
			PartAMaterialEntryInfo entry = transmitEntry(hsData, ctx);
	        int seq = info.getEntrys().size() + 1;
	        entry.setSeq(seq);
	        entry.setParent(info);
	        info.getEntrys().add(entry);
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		}
		return info;
	}
	
	/**
	 * 根据number获取PartAMaterialInfo，如果没有则返回null
	 * @param number
	 * @param ctx
	 * @return
	 * @throws TaskExternalException
	 * @author Robin
	 * @throws EASBizException 
	 */
	private PartAMaterialInfo getPartAMaterialInfo(String number, Context ctx) throws TaskExternalException{
		try {
			FilterInfo partAMaterialFilter = new FilterInfo();
			partAMaterialFilter.getFilterItems().add(new FilterItemInfo("number", number));
			EntityViewInfo partAMaterialView = new EntityViewInfo();
			partAMaterialView.setFilter(partAMaterialFilter);
			PartAMaterialCollection info = PartAMaterialFactory.getLocalInstance(ctx).getPartAMaterialCollection(partAMaterialView);
			if (info.size() > 0) {
				return info.get(0);
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	private PartAMaterialInfo transmitHead(Hashtable hsData, Context ctx) throws TaskExternalException {
		//组织编码
		String fcostOrg_longNumber=(String) ((DataToken) hsData.get("FOrgUnit_longNumber")).data;
		// 工程项目编码
		String fCurProject_longNumber=(String) ((DataToken) hsData.get("FCurProject_longNumber")).data;
		// 单据编号
		String fnumber=(String) ((DataToken) hsData.get("FNumber")).data;
		// 合同编号
		String fContractBill_number=(String) ((DataToken) hsData.get("FContractBill_number")).data;
		// 合同名称
		String fContractBill_name=(String) ((DataToken) hsData.get("FContractBill_name")).data;
		// 币别
		String fCurrency_number= (String) ((DataToken) hsData.get("FCurrency_number")).data;
		// 汇率
		String fContractBill_exRate=(String) ((DataToken) hsData.get("FContractBill_exRate")).data;
		// 状态
		String fstate=(String) ((DataToken) hsData.get("FState")).data;
		// 制单人编码
		String fCreator_number=(String) ((DataToken) hsData.get("FCreator_number")).data;
		// 制单日期
		String fCreateTime=(String) ((DataToken) hsData.get("FCreateTime")).data;
		// 审批人编码
		String fAuditor_number=(String) ((DataToken) hsData.get("FAuditor_number")).data;
		// 审批时间
		String fAuditorTime=(String) ((DataToken) hsData.get("FAuditTime")).data;
		
		String stateStr = this.getStateStr(fstate, ctx);
		
		/**
		 * 必录项校验
		 */
		if(StringUtils.isEmpty(fCurProject_longNumber)) {
			throw new TaskExternalException(getResource(ctx, "CurProjectLongNumberNotNull"));
		}
		if(StringUtils.isEmpty(fnumber)) {
			throw new TaskExternalException(getResource(ctx, "Number"));
		}
		if(StringUtils.isEmpty(fContractBill_number)) {
			throw new TaskExternalException(getResource(ctx, "ContractBillCodingNumberNotNull"));
		}
		if(StringUtils.isEmpty(fCreator_number)) {
			throw new TaskExternalException(getResource(ctx, "CreatorNumberNotNull"));
		}
		if(StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException(getResource(ctx, "CreateTimeNotNull"));
		}
		if(StringUtils.isEmpty(fstate)) {
			fstate = getResource(ctx, "AUDITTED");
		}
		if(stateStr == null) {
			throw new TaskExternalException(getResource(ctx, "State"));
		} 
		if(FDCBillStateEnum.getEnum(stateStr) == null) {
			throw new TaskExternalException(getResource(ctx, "State"));
		}
		
		/**
		 * 字符长度校验
		 */
		if(!StringUtils.isEmpty(fcostOrg_longNumber) && fcostOrg_longNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "CostOrgLongNumberIsOver40"));
		}
		if(fCurProject_longNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "CurProjectLongNumberIsOver80"));
		}
		if(fnumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "NumberIsOver40"));
		}
		if(fContractBill_number.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "ContractBillNumberIsOver40"));
		}
		if(fCurrency_number.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "CurrencyNumberIsOver40"));
		}
		if(fCreator_number.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "CreatorNumberIsOver40"));
		}
		if(fstate != null && fstate.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "StateIsOver40"));
		}
		
		if(!StringUtils.isEmpty(fContractBill_exRate) && !FDCTransmissionHelper.isNumber(fContractBill_exRate)) {
			throw new TaskExternalException(getResource(ctx, "ContractBillExRateNotNumber"));
		}
		// 校验制单日期格式
		Date createTime = FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(ctx, "CreateDateFormatError"));
		
		/**
		 * 查询数据校验
		 */
		BigDecimal fcontractBill_exRate = FDCTransmissionHelper.strToBigDecimal(fContractBill_exRate);
		CurProjectInfo curProjectInfo = null;
		CurrencyCollection currencyCollection = null;
		ContractBillInfo contractBillInfo = null;
		UserInfo creatot = null;
		UserInfo auditor = null;
		Date auditDate = null;
		CostCenterOrgUnitInfo costCenter = null;
		BigDecimal exRateSrc = null;
		try {
			
			info = this.getPartAMaterialInfo(fnumber, ctx);
			if(info!=null)
			{
				return info;
			}else
			{
				info = new PartAMaterialInfo();
			}
			
			// 工程项目
			FilterInfo peojectFilter = new FilterInfo();
			peojectFilter.getFilterItems().add(new FilterItemInfo("longnumber", fCurProject_longNumber.replace('.', '!')));
			EntityViewInfo projectView = new EntityViewInfo();
			projectView.setFilter(peojectFilter);
			CurProjectCollection projectColl = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(projectView);
			if(projectColl.size() > 0) {
				curProjectInfo = projectColl.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CurProjectInfoNotFound"));				
			}
			
			// 合同编码
			FilterInfo contractBillFile = new FilterInfo();
			contractBillFile.getFilterItems().add(new FilterItemInfo("number", fContractBill_number));
			EntityViewInfo contractBillView = new EntityViewInfo();
			contractBillView.setFilter(contractBillFile);
			ContractBillCollection contractBillCollection = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(contractBillView);
			if(contractBillCollection.size() > 0) {
				contractBillInfo = contractBillCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "ContractBillInfoNotFound"));
			}

			// 录入的工程项目和合同中的工程项目比较
			String projectId = curProjectInfo.getId().toString();
			String curProjectId = contractBillInfo.getCurProject().getId().toString();
			if(!projectId.equals(curProjectId)) {
				throw new TaskExternalException(getResource(ctx, "ContractByCurProjectNotFound"));
			}
			
			// 组织编码
			costCenter = curProjectInfo.getCostCenter();
			String costCenterLongNumber = "";
			if(costCenter == null) {
				throw new TaskExternalException(getResource(ctx, "CostCenterNotFound"));
			}
			BOSUuid id = costCenter.getId();
			FilterInfo costCenterFilter = new FilterInfo();
			costCenterFilter.getFilterItems().add(new FilterItemInfo("id", id));
			EntityViewInfo costCenterView = new EntityViewInfo();
			costCenterView.setFilter(costCenterFilter);
			CostCenterOrgUnitCollection costCenterOrgUnitCollection = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(costCenterView);
			if(costCenterOrgUnitCollection.size() > 0) {
				costCenter = costCenterOrgUnitCollection.get(0);
				costCenterLongNumber = costCenter.getLongNumber();
				if(!StringUtils.isEmpty(fcostOrg_longNumber) && !costCenterLongNumber.equals(fcostOrg_longNumber.replace('.', '!'))) {
					throw new TaskExternalException(getResource(ctx, "CostOrgLongNumberNotFound"));
				}
			}
			
			// 取合同中的币别编码
			String currencyId = contractBillInfo.getCurrency().getId().toString();
			FilterInfo currencyFilter = new FilterInfo();
			currencyFilter.getFilterItems().add(new FilterItemInfo("ID", currencyId));
			EntityViewInfo currencyView = new EntityViewInfo();
			currencyView.setFilter(currencyFilter);
			currencyCollection = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(currencyView);
			if(currencyCollection == null) {
				throw new TaskExternalException(getResource(ctx, "CurrencyNumberNotFound"));
			}
			
			// 取合同中的汇率
			exRateSrc = contractBillInfo.getExRate();

		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		// 制单人编码
		creatot = isUserNumber(fCreator_number, ctx);
		if(creatot == null) {
			throw new TaskExternalException(getResource(ctx, "CreatotNotFound"));
		}
		
		// 单据是审批状态时的判断
		
		if(fstate.trim().equals(getResource(ctx, "AUDITTED"))) {
			if(StringUtils.isEmpty(fAuditor_number)) {
				throw new TaskExternalException(getResource(ctx, "AuditorNumberNotNull"));
			}
			if(StringUtils.isEmpty(fAuditorTime)){
				throw new TaskExternalException(getResource(ctx, "AuditTimeNotNull"));
			}
		}
		
		if(!StringUtils.isEmpty(fAuditorTime)){
			// 审批日期格式验证
			auditDate = FDCTransmissionHelper.checkDateFormat(fAuditorTime, getResource(ctx, "AuditorTimeFormatError"));
		}
		
		if(!StringUtils.isEmpty(fAuditor_number)) {
			// 审核人校验
			auditor = isUserNumber(fAuditor_number, ctx);
			if(auditor == null){
				throw new TaskExternalException(getResource(ctx, "AuditorNumberNotFound"));
			}
		}
		
		/**
		 * 填充值
		 */
		contractBillInfo.setCurProject(curProjectInfo);
		contractBillInfo.setCurrency(currencyCollection.get(0));
		contractBillInfo.setExRate(exRateSrc);
		info.setOrgUnit(costCenter.castToFullOrgUnitInfo());
		info.setNumber(fnumber);
		info.setContractBill(contractBillInfo);
		info.setState(FDCBillStateEnum.getEnum(stateStr));
		info.setCreator(creatot);
		info.setCreateTime(Timestamp.valueOf(fCreateTime + " 00:00:00"));
		info.setAuditor(auditor);
		info.setAuditTime(auditDate);
		
		return info;
	}
	
	private PartAMaterialEntryInfo transmitEntry(Hashtable lineData, Context ctx) throws TaskExternalException{
		infoEntry = new PartAMaterialEntryInfo();
		// 领用合同编码
		String fMainContractBill_number=(String) ((DataToken) lineData.get("FMainContractBill_number")).data;
		// 领用合同名称
		String fMainContractBill_nname=(String) ((DataToken) lineData.get("FMainContractBill_name")).data;
		// 物料编码
		String fMaterial_number=(String) ((DataToken) lineData.get("FMaterial_number")).data;
		// 物料名称
		String fMaterial_name=(String) ((DataToken) lineData.get("FMaterial_name_l2")).data;
		// 规格型号
		String fMaterial_model=(String) ((DataToken) lineData.get("FMaterial_model")).data;
		// 单位
		String FBaseUnit_name_l2=(String) ((DataToken) lineData.get("FBaseUnit_name_l2")).data;
		// 采购单价(原币)
		String fOriginalPrice=(String) ((DataToken) lineData.get("FEntrys_originalPrice")).data;
		// 采购单价(本币)
		String fPrice=(String) ((DataToken) lineData.get("FEntrys_price")).data;
		// 数量
		String fQuantity=(String) ((DataToken) lineData.get("FEntrys_quantity")).data;
		// 金额
		String fAmount=(String) ((DataToken) lineData.get("FEntrys_amount")).data;
		// 到货日期
		String fArriveDate=(String) ((DataToken) lineData.get("FEntrys_arriveDate")).data;
		// 备注
		String fDescription_l2=(String) ((DataToken) lineData.get("FEntrys_description_l2")).data;
		
		/**
		 * 必录项校验
		 */
		if(StringUtils.isEmpty(fMaterial_number)) {
			throw new TaskExternalException(getResource(ctx, "MaterialNumberNotNull"));
		}
		if(StringUtils.isEmpty(fOriginalPrice)) {
			throw new TaskExternalException(getResource(ctx, "OriginalPriceNotNull"));
		}
		if(StringUtils.isEmpty(fQuantity)) {
			throw new TaskExternalException(getResource(ctx, "QuantityNotNull"));
		}
		BigDecimal bigFOriginalPrice = FDCTransmissionHelper.strToBigDecimal(fOriginalPrice);
		BigDecimal bigFQuantity = FDCTransmissionHelper.strToBigDecimal(fQuantity);
		
		/**
		 * 字符长度校验
		 */
		if(!StringUtils.isEmpty(fMainContractBill_number) && fMainContractBill_number.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "MainContractBillNumberIsOver40"));
		}
		if(!StringUtils.isEmpty(fMainContractBill_nname) && fMainContractBill_nname.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "MainContractBillNnameIsOver40"));
		}
		if(fMaterial_number.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "MaterialNumberIsOver40"));
		}
		if(!StringUtils.isEmpty(fDescription_l2) && fDescription_l2.length() > 200) {
			throw new TaskExternalException(getResource(ctx, "DescriptionIsOver200"));
		}
		
		if(!FDCTransmissionHelper.isNumber(fOriginalPrice)) {
			throw new TaskExternalException(getResource(ctx, "OriginalPriceNotNumber"));
		}
		if(!StringUtils.isEmpty(fPrice) && !FDCTransmissionHelper.isNumber(fPrice)) {
			throw new TaskExternalException(getResource(ctx, "PriceNotNumber"));
		}
		if(!FDCTransmissionHelper.isNumber(fQuantity)) {
			throw new TaskExternalException(getResource(ctx, "QuantityNotNumber"));
		}
		if(!StringUtils.isEmpty(fAmount) && !FDCTransmissionHelper.isNumber(fAmount)) {
			throw new TaskExternalException(getResource(ctx, "AmountNotNumber"));
		}
		
		/**
		 * 查询数据校验
		 */
		ContractBillInfo contractBillInfo = null;
		MaterialInfo materialInfo = null;
		MeasureUnitInfo measureUnitInfo = null;
		BigDecimal exRate = null;
		try {
			// 合同编码
			if(!StringUtils.isEmpty(fMainContractBill_number)) {
				FilterInfo contractBillFile = new FilterInfo();
				contractBillFile.getFilterItems().add(new FilterItemInfo("number", fMainContractBill_number));
				EntityViewInfo contractBillView = new EntityViewInfo();
				contractBillView.setFilter(contractBillFile);
				ContractBillCollection contractBillCollection = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(contractBillView);
				if(contractBillCollection.size() > 0) {
					contractBillInfo = contractBillCollection.get(0);
				}
			}
		
			// 物料
			FilterInfo materialFilter = new FilterInfo();
			materialFilter.getFilterItems().add(new FilterItemInfo("number", fMaterial_number));
			EntityViewInfo materialView = new EntityViewInfo();
			materialView.setFilter(materialFilter);
			MaterialCollection materialCollection = MaterialFactory.getLocalInstance(ctx).getMaterialCollection(materialView);
			if (materialCollection.size() > 0){
				materialInfo = materialCollection.get(0);
			}else{
				throw new TaskExternalException(getResource(ctx, "MaterialNumberNotFound"));
			}
			
			PartAMaterialEntryCollection coll = info.getEntrys();
			if(coll!=null&&coll.size()>0)
			{
				PartAMaterialEntryInfo tempInfo = null;
				for(int i=0;i<coll.size();i++) {
					tempInfo = coll.get(i);
					if(tempInfo.getMaterial().getId().toString().equals(materialInfo.getId().toString())) {
						throw new TaskExternalException(getResource(ctx, "EntryIsRepate"));
					}
				}
			}
			
			// 取物料的计量单位
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("ID", materialInfo.getBaseUnit().getId().toString()));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			MeasureUnitCollection collection = MeasureUnitFactory.getLocalInstance(ctx).getMeasureUnitCollection(view);
			if(collection.size() > 0) {
				measureUnitInfo = collection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "MeasureUnitInfoNotFound"));
			}
			
			// 取主合同的汇率
			FilterInfo file = new FilterInfo();
			file.getFilterItems().add(new FilterItemInfo("id", info.getContractBill().getId().toString()));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(file);
			ContractBillCollection colls = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view1);
			if(colls.size() > 0) {
				exRate = colls.get(0).getExRate();
			} else {
				throw new TaskExternalException(getResource(ctx, "ContractBillInfoNotFound"));
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		materialInfo.setBaseUnit(measureUnitInfo);
		infoEntry.setMainContractBill(contractBillInfo);
		infoEntry.setMaterial(materialInfo);
		DecimalFormat decimalFormat = new DecimalFormat("#.####");
		BigDecimal OriginalPrice = FDCTransmissionHelper.strToBigDecimal(decimalFormat.format(bigFOriginalPrice));
		infoEntry.setOriginalPrice(OriginalPrice);
		// 计算采购单价(本币)   采购单价(原币) * 汇率
		 	// 取主合同中的汇率
		BigDecimal sum = FDCNumberHelper.multiply(bigFOriginalPrice, exRate);
		infoEntry.setPrice(sum);
		DecimalFormat amountFormat = new DecimalFormat("#.##");
		BigDecimal quantity = FDCTransmissionHelper.strToBigDecimal(amountFormat.format(bigFQuantity));
		infoEntry.setQuantity(quantity);
		// 计算金额
		BigDecimal amount = sum.multiply(bigFQuantity);
		infoEntry.setAmount(amount);
		if(!StringUtils.isEmpty(fArriveDate)) {
			infoEntry.setArriveDate(FDCTransmissionHelper.checkDateFormat(fArriveDate, getResource(ctx, "ArriveDateFormatError")));
		}
		infoEntry.setDescription(fDescription_l2);
		
		return infoEntry;
	}
	
	/**
	 * 	判断用户是否存在
	 * @param number 用户编码
	 * @param ctx
	 * @return	true/false  存在/不存在
	 */
	private UserInfo  isUserNumber(String number, Context ctx) {
		UserInfo user = null;
		try {
			FilterInfo userFilter = new FilterInfo();
			userFilter.getFilterItems().add(new FilterItemInfo("number",number));
			EntityViewInfo userView = new EntityViewInfo();
			userView.setFilter(userFilter);
			UserCollection userCollection;
			userCollection = UserFactory.getLocalInstance(ctx).getUserCollection(userView);
			if(userCollection.size() > 0) {
				user = userCollection.get(0);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	private String  getStateStr(String fState, Context ctx) {
		String stateStr = null;
		if (fState.equals(getResource(ctx, "SAVED"))) {
			// 保存
			stateStr = FDCBillStateEnum.SAVED_VALUE;
		} else if (fState.equals(getResource(ctx, "SUBMITTED"))) {
			// 已提交
			stateStr = FDCBillStateEnum.SUBMITTED_VALUE;
		} else if (fState.equals(getResource(ctx, "AUDITTED"))) {
			// 已审批
			stateStr = FDCBillStateEnum.AUDITTED_VALUE;
		}
		return stateStr;
	}
	
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
