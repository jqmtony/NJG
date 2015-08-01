package com.kingdee.eas.fdc.material.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.invite.SectionEnum;
import com.kingdee.eas.fdc.material.MaterialConfirmBillCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryInfo;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;



public class MaterialConfirmBillTransmission extends AbstractDataTransmission {
	
	private static final Logger logger = CoreUIObject.getLogger(MaterialConfirmBillTransmission.class);
	private static String resource = "com.kingdee.eas.fdc.material.MaterialTransResource";
	private static FDCTransmissionHelper helper = new FDCTransmissionHelper();
	
	//项目工程对象
	CurProjectInfo curProjectInfo = null;
	//领用合同对象
	ContractBillInfo contractBillInfo = null;
	//材料合同对象
	ContractBillInfo mContractBillInfo = null;
	//材料确认单对象
	MaterialConfirmBillInfo materialConfirmBillInfo = new MaterialConfirmBillInfo();
	//材料确认单分录
	MaterialConfirmBillEntryInfo materialConfirmBillEntryInfo = null;
	//制单人对象
	UserInfo createUserInfo = null;
	//审批人对象
	UserInfo auditorUserInfo = null;
	//物料对象
	MaterialInfo materialInfo = null;
	//组织中心
	FullOrgUnitInfo fullOrgUnitInfo = null;
	//汇率
	BigDecimal exRate = new BigDecimal(1);
	//本次供货金额
	BigDecimal supplyAmt = new BigDecimal(0);
	//合同累计供货(累计材料合同供货)
	BigDecimal toDateSupplyAmt = new BigDecimal(0);
	//本次确认金额
	BigDecimal confirmAmt = new BigDecimal(0);
	//本次确认原币金额
	BigDecimal originalAmount = new BigDecimal(0);
	//累计确认金额(累计材料合同确认金额)
	BigDecimal toDateConfirmAmt = new BigDecimal(0);
	//本期实付金额
	BigDecimal paidAmt = new BigDecimal(0);
	//累计已付款(累计材料合同已付款)
	BigDecimal toDatePaidAmt = new BigDecimal(0);
	//是否取已经存在的确认金额和确认原币金额flag
	boolean flag = true;
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return MaterialConfirmBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	/**
	 * 描述:将用hashtable保存的数据转化为一个EAS中的CoreBaseInfo对象
	 */
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
//		
		try {
//			for (int i = 0; i < hsData.size(); i++) {
//				Hashtable lineData = (Hashtable) hsData.get(new Integer(i));
//				// 当游标为0，表示第一条记录，此时要拼凑一个表头来
//				if (i == 0) {
					materialConfirmBillInfo = transmitHead(hsData, ctx);
//				}
				if (materialConfirmBillInfo == null) {
					return null;
				}
				MaterialConfirmBillEntryInfo entry = transmitEntry(hsData, ctx);
	            int seq = materialConfirmBillInfo.getEntrys().size() + 1;
	            entry.setSeq(seq);
	            entry.setParent(materialConfirmBillInfo);
	            materialConfirmBillInfo.getEntrys().add(entry);
	        	
	        	//本次供货金额
	            materialConfirmBillInfo.setSupplyAmt(supplyAmt);
	            //合同累计供货(累计材料合同供货)
	            materialConfirmBillInfo.setToDateSupplyAmt(toDateSupplyAmt);
	            //本次确认金额
	            materialConfirmBillInfo.setConfirmAmt(confirmAmt);
	            //本次确认原币金额
	            materialConfirmBillInfo.setOriginalAmount(originalAmount);
	            //累计确认金额(累计材料合同确认金额)
	            materialConfirmBillInfo.setToDateConfirmAmt(toDateConfirmAmt);
	            //本期实付金额
	            materialConfirmBillInfo.setPaidAmt(paidAmt);
	            //累计已付款(累计材料合同已付款)
	            materialConfirmBillInfo.setToDatePaidAmt(toDatePaidAmt);
//			}
		} catch (TaskExternalException e) {
			materialConfirmBillInfo = null;
			throw e;
		}
		
		
		return materialConfirmBillInfo;
	}
	
	
	private MaterialConfirmBillInfo transmitHead(Hashtable lineData, Context ctx) throws TaskExternalException{
		
		//组织编码
		String fOrgUnitLongNumber = (String) ((DataToken) lineData.get("FOrgUnit_longNumber")).data;
		//工程项目编码
		String fCurProjectLongNumber = (String) ((DataToken) lineData.get("FCurProject_longNumber")).data;
		//单据编码
		String fNumber = (String) ((DataToken) lineData.get("FNumber")).data;
		//供货日期
		String fSupplyDate=(String) ((DataToken) lineData.get("FSupplyDate")).data;
		//领用合同编码
		String mainContractBillNumber=(String) ((DataToken) lineData.get("FMainContractBill_number")).data;
		//领用合同名称
		String mainContractBillName=(String) ((DataToken) lineData.get("FMainContractBill_name")).data;
		//领用单位
		String partBName12=(String) ((DataToken) lineData.get("FPartB_name_l2")).data;
		// 材料合同编号
		String fMaterialContractBillNumber = (String) ((DataToken) lineData.get("FMaterialContractBill_number")).data;
		// 材料合同名称
		String fMaterialContractBillName = (String) ((DataToken) lineData.get("FMaterialContractBill_name")).data;
		// 供应商
		String partB1Name12 = (String) ((DataToken) lineData.get("FPartB1_name_l2")).data;
		//合同总额
		String fMainContractBillAmount = (String) ((DataToken) lineData.get("FMainContractBill_amount")).data;
		//币别
		String fMainContractBillCurrencyNamel2=  (String) ((DataToken) lineData.get("FCurrency_name_l2")).data;
		//汇率
		String fMainContractBillExRate =  (String) ((DataToken) lineData.get("FMainContractBill_exRate")).data;
		//制单人编码
		String fCreatorNumber =  (String) ((DataToken) lineData.get("FCreator_number")).data;
		//制单日期
		String fCreateTime =  (String) ((DataToken) lineData.get("FCreateTime")).data;
		//审批人
		String fAuditorNumber =  (String) ((DataToken) lineData.get("FAuditor_number")).data;
		//审批日期
		String fAuditTime =  (String) ((DataToken) lineData.get("FAuditTime")).data;
		//摘要
		String fDescription =  (String) ((DataToken) lineData.get("FDescription")).data;
		//状态
		String fState =  (String) ((DataToken) lineData.get("FState")).data;
		/*
		 * 判断是否为空
		 */
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "Import_ProjectNumberIsNull"));
		}
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fNumber"));
		}
		if (StringUtils.isEmpty(mainContractBillNumber)) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_mainContractBillNumber"));
		}
		if (StringUtils.isEmpty(fMaterialContractBillNumber)) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fMaterialContractBillNumber"));
		}
		if (StringUtils.isEmpty(fCreatorNumber)) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fCreatorNumber"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fCreateTime"));
		}
		/*
		 * 判断字符长度
		 */
		if (fOrgUnitLongNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fOrgUnitLongNumber"));
		}
		if (fCurProjectLongNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fCurProjectLongNumber"));
		}
		if (fNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fNumber2"));
		}
		if (fMaterialContractBillName.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fMaterialContractBillName"));
		}
		if (fDescription.length() > 200) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fDescription"));
		}
		
		try {
			materialConfirmBillInfo = this.getMaterialConfirmBillInfoFromNumber(fNumber, ctx) ;
			if(materialConfirmBillInfo!=null)
			{
				return materialConfirmBillInfo;
			}else
			{
				materialConfirmBillInfo = new MaterialConfirmBillInfo();
			}
			//状态
			if(fState.trim().equals(getResource(ctx, "baocun")))
			{
				materialConfirmBillInfo.setState(FDCBillStateEnum.SAVED);
			}else if(fState.trim().equals(getResource(ctx, "yitijiao")))
			{
				materialConfirmBillInfo.setState(FDCBillStateEnum.SUBMITTED);
			}else
			{
				materialConfirmBillInfo.setState(FDCBillStateEnum.AUDITTED);
			}
			
			//工程项目编码
			FilterInfo curFilter = new FilterInfo();
			curFilter.getFilterItems().add(new FilterItemInfo("LongNumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo curView = new EntityViewInfo();
			curView.setFilter(curFilter);
			CurProjectCollection curcoll = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curView);
			if(curcoll.size()>0){
				curProjectInfo = curcoll.get(0);
			}else{
				throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_curProjectInfo") + getResource(ctx, "MaterialConfirmBill_NotEx"));
			}
			//得到组织对象,如果组织编码为空则从工程对象中取得组织编码
			CostCenterOrgUnitInfo costCenterOrgUnit = curProjectInfo.getCostCenter(); // 工程项目对应成本中心组织
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			CostCenterOrgUnitCollection collection = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(view);
			if(collection.size() > 0) {
				costCenterOrgUnit = collection.get(0);
			} 
			materialConfirmBillInfo.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
//			if(StringUtils.isEmpty(fOrgUnitLongNumber)){
//				materialConfirmBillInfo.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
//			}else{
//				if(fOrgUnitLongNumber.trim().equals(costCenterOrgUnit.getLongNumber().toString())){
//					materialConfirmBillInfo.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
//				}else{
//					throw new TaskExternalException(getResource(ctx,"Import_FOrgUnitLongNumberNotE"));
//				}
//			}
			//得到领用合同
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("number", mainContractBillNumber));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filter1);
			ContractBillCollection contractColl = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view1);
			if (contractColl.size() > 0){
				contractBillInfo = contractColl.get(0);
			}else{
				throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_contractBil"));
			}
			
			//得到材料合同
			FilterInfo mFilter = new FilterInfo();
			mFilter.getFilterItems().add(new FilterItemInfo("number", fMaterialContractBillNumber));
			EntityViewInfo mView = new EntityViewInfo();
			mView.setFilter(mFilter);
			ContractBillCollection mContractColl = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(mView);
			if (mContractColl.size() > 0){
				mContractBillInfo = mContractColl.get(0);
			}else{
				throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_mContractBill"));
			}
			exRate = contractBillInfo.getExRate();
			
			//得到创建人对象
			FilterInfo creatfilter = new FilterInfo();
			creatfilter.getFilterItems().add(new FilterItemInfo("number",fCreatorNumber));
			EntityViewInfo creatview = new EntityViewInfo();
			creatview.setFilter(creatfilter);
			UserCollection creatcoll = UserFactory.getLocalInstance(ctx).getUserCollection(creatview);
			if(creatcoll.size()>0){
				createUserInfo = creatcoll.get(0);	
			}else{
				throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_zhidanrenzhaobudao"));
			}
			//得到审批人对象
			if(!(fState.trim().equals(getResource(ctx, "baocun"))||fState.trim().equals(getResource(ctx, "yitijiao")))){
				if (!StringUtils.isEmpty(fAuditorNumber)){ 
					FilterInfo audiofilter = new FilterInfo();
					audiofilter.getFilterItems().add(new FilterItemInfo("number",fAuditorNumber));
					EntityViewInfo audioview = new EntityViewInfo();
					audioview.setFilter(audiofilter);
					UserCollection audiocoll = UserFactory.getLocalInstance(ctx).getUserCollection(audioview);
					if(audiocoll.size()>0){
						auditorUserInfo = audiocoll.get(0);	
						materialConfirmBillInfo.setAuditor(auditorUserInfo);
					} else {
						throw new TaskExternalException(getResource(ctx,"MaterialConfirmBill_audiocontF") );
					}
				} else {
					throw new TaskExternalException(getResource(ctx,"MaterialConfirmBill_Audiocontnull"));
				}
			}	
			if(!(fState.trim().equals(getResource(ctx, "baocun"))||fState.trim().equals(getResource(ctx, "yitijiao")))){
				if (StringUtils.isEmpty(fAuditTime)) 
					throw new TaskExternalException(getResource(ctx,"MaterialConfirmBill_audiotimecontnull"));
				materialConfirmBillInfo.setAuditTime(checkDate(fAuditTime,ctx));
			}
			
			materialConfirmBillInfo.setMainContractBill(contractBillInfo);
			materialConfirmBillInfo.setMaterialContractBill(mContractBillInfo);
			materialConfirmBillInfo.setNumber(fNumber);
			materialConfirmBillInfo.setSupplyDate(checkDate(fSupplyDate,ctx));
			materialConfirmBillInfo.setCreator(createUserInfo);
			materialConfirmBillInfo.setCreateTime(new Timestamp(checkDate(fCreateTime, ctx).getTime()));
	        materialConfirmBillInfo.setDescription(fDescription);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return materialConfirmBillInfo;
	}
	//日期判断
	private Date checkDate(String dateStr,Context ctx) throws TaskExternalException{
		try {
			if(StringUtils.isEmpty(dateStr)) return null;
			DateFormat df = null;
			if(dateStr.trim().length() <= "yyyy-MM-dd".length()){ // 处理 "yyyy-MM-d"
				df = new SimpleDateFormat("yyyy-MM-dd");
			}else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm".length()){ //处理 yyyy-MM-d HH:mm情况
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
			}else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm:ss".length()){
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_DateForm"));
			}
			df.setLenient(false);
			return df.parse(dateStr);
		} catch (ParseException e) {
//			e.printStackTrace();
			throw new TaskExternalException(getResource(ctx,"Import_DateForm"));
		}
    }

	
	private MaterialConfirmBillEntryInfo transmitEntry(Hashtable lineData, Context ctx) throws TaskExternalException{
		
		materialConfirmBillEntryInfo = new MaterialConfirmBillEntryInfo();
		
		PartAMaterialEntryInfo partAMaterialEntry = null;
		//物料编码
		String fMaterialNumber =  (String) ((DataToken) lineData.get("FMaterial_number")).data;
		
		//物料名称
		String fMaterialNamel2 =  (String) ((DataToken) lineData.get("FMaterial_name_l2")).data;
		
		//规格型号
		String fMaterialModel =  (String) ((DataToken) lineData.get("FMaterial_model")).data;
		
		//单位
		String fMaterialBaseUnitNamel2 =  (String) ((DataToken) lineData.get("FBaseUnit_name_l2")).data;
		
		//原币单价
		String fPartAMaterialEntryOriginalPrice =  (String) ((DataToken) lineData.get("FPartAMaterialEntry_originalPrice")).data;
		
		//本位币单价
		String fPartAMaterialEntryPrice =  (String) ((DataToken) lineData.get("FPartAMaterialEntry_price")).data;
		
		//到货数量
		String fEntrysQuantity =  (String) ((DataToken) lineData.get("FEntrys_quantity")).data;
		
		//金额
		String fAmount =  (String) ((DataToken) lineData.get("FEntrys_amount")).data;
		
		//确认原币单价
		String fEntrysOriginalPrice =  (String) ((DataToken) lineData.get("FEntrys_originalPrice")).data;
		
		//确认本位币单价
		String fEntrysOriginalPrice2 =  (String) ((DataToken) lineData.get("FEntrys_originalPrice2")).data;
		BigDecimal price = new BigDecimal(0);
		
		//确认原币金额
		String fEntrysPrice =  (String) ((DataToken) lineData.get("FEntrys_originalAmount")).data;
		
		//确认本位币金额
		String fEntrysPrice2 =  (String) ((DataToken) lineData.get("FEntrys_originalAmount2")).data;
		BigDecimal amount = new BigDecimal(0);
		
		//标段
		String fEntrysSection =  (String) ((DataToken) lineData.get("FEntrys_section")).data;
		
		//备注
		String fEntrysDesc =  (String) ((DataToken) lineData.get("FEntrys_desc")).data;
		/*
		 * 判断是否为空
		 */
		if (StringUtils.isEmpty(fMaterialNumber)) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fMaterialNumber"));
		}
		if (StringUtils.isEmpty(fPartAMaterialEntryOriginalPrice)) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fPartAMaterialEntryOriginalPrice"));
		}
		if (StringUtils.isEmpty(fEntrysQuantity)) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fEntrysQuantity"));
		}
		if (StringUtils.isEmpty(fEntrysOriginalPrice)) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fEntrysOriginalPrice"));
		}
		if (StringUtils.isEmpty(fEntrysPrice)) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fEntrysPrice"));
		}
		/*
		 * 判断字符长度
		 */
		if (fMaterialNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fMaterialNumber2"));
		}
		if (fMaterialNamel2.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fMaterialNamel2"));
		}
		if (fMaterialModel.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fMaterialModel"));
		}
		if (fEntrysOriginalPrice.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fEntrysOriginalPrice2"));
		}
		if (fEntrysDesc.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_fEntrysDesc"));
		}
		//数值判断
		// 正则表达式 验证输入的是否为数字型参数：    "^\\d+(\\.\\d+)?$"
		//原币单价
		if(!fPartAMaterialEntryOriginalPrice.matches("^\\d+(\\.\\d+)?$")){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_yuanbidanjia"));
		}
		//本位币单价
		if((!StringUtils.isEmpty(fPartAMaterialEntryPrice))&&(!fPartAMaterialEntryPrice.matches("^\\d+(\\.\\d+)?$"))){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_benweibidanjia"));
		}
		//到货数量
		if(!fEntrysQuantity.matches("^\\d+(\\.\\d+)?$")){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_daohuoshuliang"));
		}
		//金额
		if((!StringUtils.isEmpty(fAmount))&&(!fAmount.matches("^\\d+(\\.\\d+)?$"))){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_jine"));
		}
		//确认原币单价
		if(!fEntrysOriginalPrice.matches("^\\d+(\\.\\d+)?$")){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_querenyuanbidanjia"));
		}
		//确认本位币单价
		if((!StringUtils.isEmpty(fEntrysOriginalPrice2))&&(!fEntrysOriginalPrice2.matches("^\\d+(\\.\\d+)?$"))){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_querenbenweibidanjia"));
		}
		//确认原币金额
		if(!fEntrysPrice.matches("^\\d+(\\.\\d+)?$")){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_querenyuanbijine"));
		}
		//确认本位币金额
		if((!StringUtils.isEmpty(fEntrysPrice2))&&(!fEntrysPrice2.matches("^\\d+(\\.\\d+)?$"))){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_querenbenweibijine"));
		}
		
		try {
			//得到物料对象
			FilterInfo materialFilter = new FilterInfo();
			materialFilter.getFilterItems().add(new FilterItemInfo("number", fMaterialNumber));
			EntityViewInfo materialView = new EntityViewInfo();
			materialView.setFilter(materialFilter);
			MaterialCollection materialCollection = MaterialFactory.getLocalInstance(ctx).getMaterialCollection(materialView);
			if (materialCollection.size() > 0) {
				materialInfo = materialCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx,"MaterialConfirmBill_materialInfo"));
			}
//			//得到材料明细单分录
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("FMainContractBillId", contractBillInfo.getId()));
//			EntityViewInfo view = new EntityViewInfo();
//			view.setFilter(filter);
//			PartAMaterialEntryCollection collection;
//			collection = PartAMaterialEntryFactory.getLocalInstance(ctx).getPartAMaterialEntryCollection(view);
//			if (collection.size() > 0) {
//				partAMaterialEntry = collection.get(0);
//			} else {
//				throw new TaskExternalException(getResource(ctx,"MaterialConfirmBill_materialInfo"));
//			}
//			
			//判断分录中物料是否重复
			MaterialConfirmBillEntryCollection mColl = materialConfirmBillInfo.getEntrys();
			if(mColl!=null&&mColl.size()>0)
			{
				MaterialConfirmBillEntryInfo tempInfo = null;
				for(int i=0;i<mColl.size();i++)
				{
					tempInfo = mColl.get(i);
					if(tempInfo.getMaterial().getId().toString().equals(materialInfo.getId().toString()))
					{
						throw new TaskExternalException(getResource(ctx,"Import_materialInfo"));
					}
				}
			}			
			//标段
			if(fEntrysSection.trim().equals(getResource(ctx,"No.1"))){
				materialConfirmBillEntryInfo.setSection(SectionEnum.SECTION1);
			}else if(fEntrysSection.trim().equals(getResource(ctx,"No.2"))){
				materialConfirmBillEntryInfo.setSection(SectionEnum.SECTION2);
			}else if(fEntrysSection.trim().equals(getResource(ctx,"No.3"))){
				materialConfirmBillEntryInfo.setSection(SectionEnum.SECTION3);
			}else if(fEntrysSection.trim().equals(getResource(ctx,"No.4"))){
				materialConfirmBillEntryInfo.setSection(SectionEnum.SECTION4);
			}else if(fEntrysSection.trim().equals(getResource(ctx,"No.5"))){
				materialConfirmBillEntryInfo.setSection(SectionEnum.SECTION5);
			}else if(fEntrysSection.trim().equals(getResource(ctx,"No.6"))){
				materialConfirmBillEntryInfo.setSection(SectionEnum.SECTION6);
			}else if(fEntrysSection.trim().equals(getResource(ctx,"No.7"))){
				materialConfirmBillEntryInfo.setSection(SectionEnum.SECTION7);
			}else if(fEntrysSection.trim().equals(getResource(ctx,"No.8"))){
				materialConfirmBillEntryInfo.setSection(SectionEnum.SECTION8);
			}else if(fEntrysSection.trim().equals(getResource(ctx,"No.9"))){
				materialConfirmBillEntryInfo.setSection(SectionEnum.SECTION9);
			}else if(fEntrysSection.trim().equals(getResource(ctx,"No.10"))){
				materialConfirmBillEntryInfo.setSection(SectionEnum.SECTION10);
			}
			//确认本位币单价
			if (fEntrysOriginalPrice2 == null || StringUtils.isEmpty(fEntrysOriginalPrice2)) {
				//确认本位币单价  = 确认原币单价 * 汇率
				price = FDCHelper.multiply(new BigDecimal(fEntrysOriginalPrice), exRate);
			} else {
				price = new BigDecimal(fEntrysOriginalPrice);
			}
			//确认本位币金额
			if (fEntrysPrice2 == null || StringUtils.isEmpty(fEntrysPrice2)) {
				//确认本位币金额 = 确认原币金额 * 汇率
				amount = FDCHelper.multiply(new BigDecimal(fEntrysPrice), exRate);
			} else {
				amount = new BigDecimal(fEntrysPrice);
			}
			//已经存在的确认金额和确认原币金额取得
			if(mColl!=null&&mColl.size()>0)
			{
				MaterialConfirmBillEntryInfo tempInfo = null;
				for(int i=0;i<mColl.size();i++)
				{
					tempInfo = mColl.get(i);
					if (flag == true) {
						//本次确认金额
						confirmAmt = FDCHelper.add(confirmAmt, tempInfo.getAmount());
						//本次确认原币金额
						originalAmount = FDCHelper.add(originalAmount, tempInfo.getOriginalAmount());
						//本次供货金额
						supplyAmt = FDCHelper.add(supplyAmt, tempInfo.getAmount());
						//合同累计供货
						toDateSupplyAmt = supplyAmt;						
					}
				}
			}
			//本次确认金额
			confirmAmt = FDCHelper.add(confirmAmt, amount);
			//本次确认原币金额
			originalAmount = FDCHelper.add(originalAmount, new BigDecimal(fEntrysPrice));
			//累计确认金额
			toDateConfirmAmt = confirmAmt;
			flag = false;
		} catch (BOSException e) {
			e.printStackTrace();
		}

		materialConfirmBillEntryInfo.setMaterial(materialInfo);
		materialConfirmBillEntryInfo.setQuantity(FDCNumberHelper.toBigDecimal(fEntrysQuantity, 2));
		materialConfirmBillEntryInfo.setOriginalPrice(FDCNumberHelper.toBigDecimal(fEntrysOriginalPrice, 4));
		materialConfirmBillEntryInfo.setPrice(FDCNumberHelper.toBigDecimal(price, 4));
		materialConfirmBillEntryInfo.setOriginalAmount(FDCNumberHelper.toBigDecimal(fEntrysPrice, 2));
		materialConfirmBillEntryInfo.setAmount(FDCNumberHelper.toBigDecimal(amount, 2));
		materialConfirmBillEntryInfo.setDesc(fEntrysDesc);
		
		
		return materialConfirmBillEntryInfo;
	}
//	private UserInfo getUserInfo(String userNumber, Context ctx) throws TaskExternalException{
//		try {
//			UserInfo userInfo = UserFactory.getLocalInstance(ctx).getUserInfoByNumber(userNumber);
//			if(userInfo==null)
//			{
//				throw new TaskExternalException(getResource(ctx,"MaterialConfirmBill_infoCanotF"));
//			}else
//			{
//			    return userInfo;
//			}
//		} catch (EASBizException e) {
//			e.printStackTrace();
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
//		if (coreBaseInfo == null || coreBaseInfo instanceof ContractBillInfo == false) {
//			return;
//		}
//		try {
//			MaterialConfirmBillInfo billBase = (MaterialConfirmBillInfo) coreBaseInfo;
//			String id = getIdFromNumber(billBase.getNumber(), ctx);
//			if (StringUtil.isEmptyString(id)) {
//				getController(ctx).addnew(coreBaseInfo);
//			} else {
//				coreBaseInfo.setId(BOSUuid.read(id));
//				getController(ctx).update(new ObjectUuidPK(id), coreBaseInfo);
//			}
//
//		} catch (Exception ex) {
//			throw new TaskExternalException(ex.getMessage(), ex.getCause());
//		}
//	}
	
	/**
	 * 根据number获取id，如果没有则返回null
	 * @param number
	 * @param ctx
	 * @return
	 * @throws TaskExternalException
	 * @author Robin
	 * @throws EASBizException 
	 */
	private MaterialConfirmBillInfo getMaterialConfirmBillInfoFromNumber(String number, Context ctx) throws TaskExternalException{
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			MaterialConfirmBillCollection materialConfirmBillColl = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(view);
			if (materialConfirmBillColl!=null&&materialConfirmBillColl.size() > 0) {
				return materialConfirmBillColl.get(0);
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return null;
	}
	
//	//设置一次性加载数据
//	public int getSubmitType() {
//		return SUBMITMULTIRECTYPE;
//	}

	/**
	 * 得到资源文件
	 * @author 郑杰元
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}

}
