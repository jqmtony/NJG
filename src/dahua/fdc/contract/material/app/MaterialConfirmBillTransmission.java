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
	
	//��Ŀ���̶���
	CurProjectInfo curProjectInfo = null;
	//���ú�ͬ����
	ContractBillInfo contractBillInfo = null;
	//���Ϻ�ͬ����
	ContractBillInfo mContractBillInfo = null;
	//����ȷ�ϵ�����
	MaterialConfirmBillInfo materialConfirmBillInfo = new MaterialConfirmBillInfo();
	//����ȷ�ϵ���¼
	MaterialConfirmBillEntryInfo materialConfirmBillEntryInfo = null;
	//�Ƶ��˶���
	UserInfo createUserInfo = null;
	//�����˶���
	UserInfo auditorUserInfo = null;
	//���϶���
	MaterialInfo materialInfo = null;
	//��֯����
	FullOrgUnitInfo fullOrgUnitInfo = null;
	//����
	BigDecimal exRate = new BigDecimal(1);
	//���ι������
	BigDecimal supplyAmt = new BigDecimal(0);
	//��ͬ�ۼƹ���(�ۼƲ��Ϻ�ͬ����)
	BigDecimal toDateSupplyAmt = new BigDecimal(0);
	//����ȷ�Ͻ��
	BigDecimal confirmAmt = new BigDecimal(0);
	//����ȷ��ԭ�ҽ��
	BigDecimal originalAmount = new BigDecimal(0);
	//�ۼ�ȷ�Ͻ��(�ۼƲ��Ϻ�ͬȷ�Ͻ��)
	BigDecimal toDateConfirmAmt = new BigDecimal(0);
	//����ʵ�����
	BigDecimal paidAmt = new BigDecimal(0);
	//�ۼ��Ѹ���(�ۼƲ��Ϻ�ͬ�Ѹ���)
	BigDecimal toDatePaidAmt = new BigDecimal(0);
	//�Ƿ�ȡ�Ѿ����ڵ�ȷ�Ͻ���ȷ��ԭ�ҽ��flag
	boolean flag = true;
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return MaterialConfirmBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	/**
	 * ����:����hashtable���������ת��Ϊһ��EAS�е�CoreBaseInfo����
	 */
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
//		
		try {
//			for (int i = 0; i < hsData.size(); i++) {
//				Hashtable lineData = (Hashtable) hsData.get(new Integer(i));
//				// ���α�Ϊ0����ʾ��һ����¼����ʱҪƴ��һ����ͷ��
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
	        	
	        	//���ι������
	            materialConfirmBillInfo.setSupplyAmt(supplyAmt);
	            //��ͬ�ۼƹ���(�ۼƲ��Ϻ�ͬ����)
	            materialConfirmBillInfo.setToDateSupplyAmt(toDateSupplyAmt);
	            //����ȷ�Ͻ��
	            materialConfirmBillInfo.setConfirmAmt(confirmAmt);
	            //����ȷ��ԭ�ҽ��
	            materialConfirmBillInfo.setOriginalAmount(originalAmount);
	            //�ۼ�ȷ�Ͻ��(�ۼƲ��Ϻ�ͬȷ�Ͻ��)
	            materialConfirmBillInfo.setToDateConfirmAmt(toDateConfirmAmt);
	            //����ʵ�����
	            materialConfirmBillInfo.setPaidAmt(paidAmt);
	            //�ۼ��Ѹ���(�ۼƲ��Ϻ�ͬ�Ѹ���)
	            materialConfirmBillInfo.setToDatePaidAmt(toDatePaidAmt);
//			}
		} catch (TaskExternalException e) {
			materialConfirmBillInfo = null;
			throw e;
		}
		
		
		return materialConfirmBillInfo;
	}
	
	
	private MaterialConfirmBillInfo transmitHead(Hashtable lineData, Context ctx) throws TaskExternalException{
		
		//��֯����
		String fOrgUnitLongNumber = (String) ((DataToken) lineData.get("FOrgUnit_longNumber")).data;
		//������Ŀ����
		String fCurProjectLongNumber = (String) ((DataToken) lineData.get("FCurProject_longNumber")).data;
		//���ݱ���
		String fNumber = (String) ((DataToken) lineData.get("FNumber")).data;
		//��������
		String fSupplyDate=(String) ((DataToken) lineData.get("FSupplyDate")).data;
		//���ú�ͬ����
		String mainContractBillNumber=(String) ((DataToken) lineData.get("FMainContractBill_number")).data;
		//���ú�ͬ����
		String mainContractBillName=(String) ((DataToken) lineData.get("FMainContractBill_name")).data;
		//���õ�λ
		String partBName12=(String) ((DataToken) lineData.get("FPartB_name_l2")).data;
		// ���Ϻ�ͬ���
		String fMaterialContractBillNumber = (String) ((DataToken) lineData.get("FMaterialContractBill_number")).data;
		// ���Ϻ�ͬ����
		String fMaterialContractBillName = (String) ((DataToken) lineData.get("FMaterialContractBill_name")).data;
		// ��Ӧ��
		String partB1Name12 = (String) ((DataToken) lineData.get("FPartB1_name_l2")).data;
		//��ͬ�ܶ�
		String fMainContractBillAmount = (String) ((DataToken) lineData.get("FMainContractBill_amount")).data;
		//�ұ�
		String fMainContractBillCurrencyNamel2=  (String) ((DataToken) lineData.get("FCurrency_name_l2")).data;
		//����
		String fMainContractBillExRate =  (String) ((DataToken) lineData.get("FMainContractBill_exRate")).data;
		//�Ƶ��˱���
		String fCreatorNumber =  (String) ((DataToken) lineData.get("FCreator_number")).data;
		//�Ƶ�����
		String fCreateTime =  (String) ((DataToken) lineData.get("FCreateTime")).data;
		//������
		String fAuditorNumber =  (String) ((DataToken) lineData.get("FAuditor_number")).data;
		//��������
		String fAuditTime =  (String) ((DataToken) lineData.get("FAuditTime")).data;
		//ժҪ
		String fDescription =  (String) ((DataToken) lineData.get("FDescription")).data;
		//״̬
		String fState =  (String) ((DataToken) lineData.get("FState")).data;
		/*
		 * �ж��Ƿ�Ϊ��
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
		 * �ж��ַ�����
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
			//״̬
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
			
			//������Ŀ����
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
			//�õ���֯����,�����֯����Ϊ����ӹ��̶�����ȡ����֯����
			CostCenterOrgUnitInfo costCenterOrgUnit = curProjectInfo.getCostCenter(); // ������Ŀ��Ӧ�ɱ�������֯
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
			//�õ����ú�ͬ
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
			
			//�õ����Ϻ�ͬ
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
			
			//�õ������˶���
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
			//�õ������˶���
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
	//�����ж�
	private Date checkDate(String dateStr,Context ctx) throws TaskExternalException{
		try {
			if(StringUtils.isEmpty(dateStr)) return null;
			DateFormat df = null;
			if(dateStr.trim().length() <= "yyyy-MM-dd".length()){ // ���� "yyyy-MM-d"
				df = new SimpleDateFormat("yyyy-MM-dd");
			}else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm".length()){ //���� yyyy-MM-d HH:mm���
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
		//���ϱ���
		String fMaterialNumber =  (String) ((DataToken) lineData.get("FMaterial_number")).data;
		
		//��������
		String fMaterialNamel2 =  (String) ((DataToken) lineData.get("FMaterial_name_l2")).data;
		
		//����ͺ�
		String fMaterialModel =  (String) ((DataToken) lineData.get("FMaterial_model")).data;
		
		//��λ
		String fMaterialBaseUnitNamel2 =  (String) ((DataToken) lineData.get("FBaseUnit_name_l2")).data;
		
		//ԭ�ҵ���
		String fPartAMaterialEntryOriginalPrice =  (String) ((DataToken) lineData.get("FPartAMaterialEntry_originalPrice")).data;
		
		//��λ�ҵ���
		String fPartAMaterialEntryPrice =  (String) ((DataToken) lineData.get("FPartAMaterialEntry_price")).data;
		
		//��������
		String fEntrysQuantity =  (String) ((DataToken) lineData.get("FEntrys_quantity")).data;
		
		//���
		String fAmount =  (String) ((DataToken) lineData.get("FEntrys_amount")).data;
		
		//ȷ��ԭ�ҵ���
		String fEntrysOriginalPrice =  (String) ((DataToken) lineData.get("FEntrys_originalPrice")).data;
		
		//ȷ�ϱ�λ�ҵ���
		String fEntrysOriginalPrice2 =  (String) ((DataToken) lineData.get("FEntrys_originalPrice2")).data;
		BigDecimal price = new BigDecimal(0);
		
		//ȷ��ԭ�ҽ��
		String fEntrysPrice =  (String) ((DataToken) lineData.get("FEntrys_originalAmount")).data;
		
		//ȷ�ϱ�λ�ҽ��
		String fEntrysPrice2 =  (String) ((DataToken) lineData.get("FEntrys_originalAmount2")).data;
		BigDecimal amount = new BigDecimal(0);
		
		//���
		String fEntrysSection =  (String) ((DataToken) lineData.get("FEntrys_section")).data;
		
		//��ע
		String fEntrysDesc =  (String) ((DataToken) lineData.get("FEntrys_desc")).data;
		/*
		 * �ж��Ƿ�Ϊ��
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
		 * �ж��ַ�����
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
		//��ֵ�ж�
		// ������ʽ ��֤������Ƿ�Ϊ�����Ͳ�����    "^\\d+(\\.\\d+)?$"
		//ԭ�ҵ���
		if(!fPartAMaterialEntryOriginalPrice.matches("^\\d+(\\.\\d+)?$")){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_yuanbidanjia"));
		}
		//��λ�ҵ���
		if((!StringUtils.isEmpty(fPartAMaterialEntryPrice))&&(!fPartAMaterialEntryPrice.matches("^\\d+(\\.\\d+)?$"))){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_benweibidanjia"));
		}
		//��������
		if(!fEntrysQuantity.matches("^\\d+(\\.\\d+)?$")){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_daohuoshuliang"));
		}
		//���
		if((!StringUtils.isEmpty(fAmount))&&(!fAmount.matches("^\\d+(\\.\\d+)?$"))){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_jine"));
		}
		//ȷ��ԭ�ҵ���
		if(!fEntrysOriginalPrice.matches("^\\d+(\\.\\d+)?$")){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_querenyuanbidanjia"));
		}
		//ȷ�ϱ�λ�ҵ���
		if((!StringUtils.isEmpty(fEntrysOriginalPrice2))&&(!fEntrysOriginalPrice2.matches("^\\d+(\\.\\d+)?$"))){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_querenbenweibidanjia"));
		}
		//ȷ��ԭ�ҽ��
		if(!fEntrysPrice.matches("^\\d+(\\.\\d+)?$")){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_querenyuanbijine"));
		}
		//ȷ�ϱ�λ�ҽ��
		if((!StringUtils.isEmpty(fEntrysPrice2))&&(!fEntrysPrice2.matches("^\\d+(\\.\\d+)?$"))){
			throw new TaskExternalException(getResource(ctx, "MaterialConfirmBill_querenbenweibijine"));
		}
		
		try {
			//�õ����϶���
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
//			//�õ�������ϸ����¼
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
			//�жϷ�¼�������Ƿ��ظ�
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
			//���
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
			//ȷ�ϱ�λ�ҵ���
			if (fEntrysOriginalPrice2 == null || StringUtils.isEmpty(fEntrysOriginalPrice2)) {
				//ȷ�ϱ�λ�ҵ���  = ȷ��ԭ�ҵ��� * ����
				price = FDCHelper.multiply(new BigDecimal(fEntrysOriginalPrice), exRate);
			} else {
				price = new BigDecimal(fEntrysOriginalPrice);
			}
			//ȷ�ϱ�λ�ҽ��
			if (fEntrysPrice2 == null || StringUtils.isEmpty(fEntrysPrice2)) {
				//ȷ�ϱ�λ�ҽ�� = ȷ��ԭ�ҽ�� * ����
				amount = FDCHelper.multiply(new BigDecimal(fEntrysPrice), exRate);
			} else {
				amount = new BigDecimal(fEntrysPrice);
			}
			//�Ѿ����ڵ�ȷ�Ͻ���ȷ��ԭ�ҽ��ȡ��
			if(mColl!=null&&mColl.size()>0)
			{
				MaterialConfirmBillEntryInfo tempInfo = null;
				for(int i=0;i<mColl.size();i++)
				{
					tempInfo = mColl.get(i);
					if (flag == true) {
						//����ȷ�Ͻ��
						confirmAmt = FDCHelper.add(confirmAmt, tempInfo.getAmount());
						//����ȷ��ԭ�ҽ��
						originalAmount = FDCHelper.add(originalAmount, tempInfo.getOriginalAmount());
						//���ι������
						supplyAmt = FDCHelper.add(supplyAmt, tempInfo.getAmount());
						//��ͬ�ۼƹ���
						toDateSupplyAmt = supplyAmt;						
					}
				}
			}
			//����ȷ�Ͻ��
			confirmAmt = FDCHelper.add(confirmAmt, amount);
			//����ȷ��ԭ�ҽ��
			originalAmount = FDCHelper.add(originalAmount, new BigDecimal(fEntrysPrice));
			//�ۼ�ȷ�Ͻ��
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
	 * ����number��ȡid�����û���򷵻�null
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
	
//	//����һ���Լ�������
//	public int getSubmitType() {
//		return SUBMITMULTIRECTYPE;
//	}

	/**
	 * �õ���Դ�ļ�
	 * @author ֣��Ԫ
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}

}
