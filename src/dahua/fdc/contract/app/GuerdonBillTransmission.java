package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeAuxFactory;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.PutOutTypeEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.StringUtils;

/**4
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		��������������ת����
 *		
 * @author		����	
 * @createDate	2011-6-10		
 */
public class GuerdonBillTransmission extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.contract.ContractTransmissionResource";
	
//	static Map stateMap = new HashedMap();
//	
//	static {
//		stateMap.put("����", "1SAVED");
//		stateMap.put("���ύ", "2SUBMITTED");
//		stateMap.put("������", "4AUDITTED");
//		stateMap.put("�ֽ� �Է��е�˰��", "Cash");
//		stateMap.put("��Ʊ �ҷ�ת��", "Ticket");
//	}
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = GuerdonBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		GuerdonBillInfo info = new GuerdonBillInfo();
		// ��֯����										
		String costOrgLongNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCurProject$costOrg_longNumber");
		// ������Ŀ����
		String fcurProjectLongNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCurProject_longNumber");
		// ��ͬ����
		String fcontractNumber = FDCTransmissionHelper.getFieldValue(hsData, "FContract_number");
		// ����
		String fnumber = FDCTransmissionHelper.getFieldValue(hsData, "FNumber");
		// ����
		String fname = FDCTransmissionHelper.getFieldValue(hsData, "FName");
		// ״̬
		String fstate = FDCTransmissionHelper.getFieldValue(hsData, "FState");
		// �ұ����
		String fcurrencyNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCurrency_number");
		// ��������
		String fguerdonThings = FDCTransmissionHelper.getFieldValue(hsData, "FGuerdonThings");
		// ������������
		String fguerdonDes = FDCTransmissionHelper.getFieldValue(hsData, "FGuerdonDes");
		// ���ŷ�ʽ
		String fputOutType = FDCTransmissionHelper.getFieldValue(hsData, "FPutOutType");
		// ����ԭ�ҽ��
		String foriginalAmount = FDCTransmissionHelper.getFieldValue(hsData, "FOriginalAmount");
		// ����
		String exRate = FDCTransmissionHelper.getFieldValue(hsData, "FExRate");
		// ��λ�ҽ��
		String amount = FDCTransmissionHelper.getFieldValue(hsData, "FAmount");
		// �Ƿ��ѽ���
		String fisGuerdoned = FDCTransmissionHelper.getFieldValue(hsData, "FIsGuerdoned");
		// �Ƶ��˱���
		String fcreatorNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCreator_number");
		// �Ƶ�ʱ��
		String fcreateTime = FDCTransmissionHelper.getFieldValue(hsData, "FCreateTime");
		// ����˱���
		String fauditorNumber = FDCTransmissionHelper.getFieldValue(hsData, "FAuditor_number");
		// ���ʱ��
		String fauditTime = FDCTransmissionHelper.getFieldValue(hsData, "FAuditTime");
		
		String stateStr = this.getStateStr(fstate, ctx);
		String putOutTypeStr = this.getPutOutTypeStr(fputOutType, ctx);
		
		/**
		 * ��¼��У��
		 */
		if(StringUtils.isEmpty(fcurProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "CurProjectLongNumberNotNull"));
		}
		if(StringUtils.isEmpty(fcontractNumber)) {
			throw new TaskExternalException(getResource(ctx, "ContractCodingNumberNotNull"));
		}
		if(StringUtils.isEmpty(fnumber)) {
			throw new TaskExternalException(getResource(ctx, "NumberNotNull"));
		}
		if(StringUtils.isEmpty(fname)) {
			throw new TaskExternalException(getResource(ctx, "NameNotNull"));
		}
		// �жϵ���״̬,AUDITTED ��ʾ������
		if(StringUtils.isEmpty(fstate)) {
			fstate = getResource(ctx, "FDCBillState_AUDITTED");
		}
		if(StringUtils.isEmpty(foriginalAmount)) {
			throw new TaskExternalException(getResource(ctx, "OriginalAmountNotNull"));
		}
		// Ϊ��ʱĬ��Ϊ �� 
		if(StringUtils.isEmpty(fisGuerdoned)) {
			fisGuerdoned = getResource(ctx, "No");
		}
		if(!fisGuerdoned.trim().equals(getResource(ctx, "Yes")) && !fisGuerdoned.trim().equals(getResource(ctx, "No"))) {
			throw new TaskExternalException(getResource(ctx, "IsGuerdoned"));
		}
		PutOutTypeEnum enums = null;
		if(!StringUtils.isEmpty(fputOutType)) {
			if(putOutTypeStr == null) {
				throw new TaskExternalException(getResource(ctx, "PutOutTypeIsError"));
			}
			enums = PutOutTypeEnum.getEnum(putOutTypeStr);
			if(enums == null) {
				throw new TaskExternalException(getResource(ctx, "PutOutTypeIsError"));
			}
		}else {
			enums = PutOutTypeEnum.Cash;
		}
		
		if(StringUtils.isEmpty(fcreatorNumber)) {
			throw new TaskExternalException(getResource(ctx, "CreatorNumberNotNull"));
		}
		if(StringUtils.isEmpty(fcreateTime)) {
			throw new TaskExternalException(getResource(ctx, "CreatorTimeNotNull"));
		}
		
		if(stateStr == null) {
			throw new TaskExternalException(getResource(ctx, "State"));
		}
		if(FDCBillStateEnum.getEnum(stateStr) == null) {
			throw new TaskExternalException(getResource(ctx, "State"));
		}
		
		/**
		 * �ַ�������֤
		 */
		if(costOrgLongNumber != null && costOrgLongNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "CostOrgLongNumberIsOver40"));
		}
		if(fcurProjectLongNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "CurProjectLongNumberIsOver40"));
		}
		if(fcontractNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "ContractCodingNumberIsOver80"));
		}
		if(fnumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "NumberIsOver80"));
		}
		if(fname.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "NameIsOver80"));
		}
		if(fstate != null && fstate.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "StateIsOver40"));
		}
		if(fcurrencyNumber != null && fcurrencyNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "CurrencyNumberIsOver40"));
		}
		if(fguerdonThings !=null && fguerdonThings.length() > 40){
			throw new TaskExternalException(getResource(ctx, "GuerdonThingsIsOver40"));
		}
		if(fguerdonDes !=null && fguerdonDes.length() > 40){
			throw new TaskExternalException(getResource(ctx, "GuerdonDesIsOver40"));
		}
		if(fputOutType != null && fputOutType.length() > 40){
			throw new TaskExternalException(getResource(ctx, "PutOutTypeIsOver40"));
		}
		if(foriginalAmount.length() > 40){
			throw new TaskExternalException(getResource(ctx, "OriginalAmountIsOver40"));
		}
		if(fcreatorNumber.length() > 40){
			throw new TaskExternalException(getResource(ctx, "CreatorNumberIsOver40"));
		}
		if(fauditorNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "AuditorNumberIsOver40"));
		}
		
		if(!FDCTransmissionHelper.isNumber(foriginalAmount)) {
			throw new TaskExternalException(getResource(ctx, "OriginalAmountNotNumber"));
		}
		BigDecimal fexRate = null;
		if(!StringUtils.isEmpty(exRate) && !FDCTransmissionHelper.isNumber(exRate)) {
			throw new TaskExternalException(getResource(ctx, "ExRateNotNumber"));
		} else {
			fexRate = FDCTransmissionHelper.strToBigDecimal(exRate);
		}
		BigDecimal famount = null;
		if(!StringUtils.isEmpty(amount) && !FDCTransmissionHelper.isNumber(amount)) {
			throw new TaskExternalException(getResource(ctx, "AmountNotNumber"));
		} else {
			famount = FDCTransmissionHelper.strToBigDecimal(amount);
		}
		
		FDCTransmissionHelper.checkDateFormat(fcreateTime, getResource(ctx, "CreateDateError"));
		
		UserInfo auditor = null;
		Date date = null;
		if(fstate.trim().equals(getResource(ctx, "FDCBillState_AUDITTED"))) {
			if(StringUtils.isEmpty(fauditorNumber)) {
				throw new TaskExternalException(getResource(ctx, "AuditorNumberNotNull"));
			}
			if(StringUtils.isEmpty(fauditTime)){
				throw new TaskExternalException(getResource(ctx, "AuditTimeNotNull"));
			}
		}
		if(!StringUtils.isEmpty(fauditTime)) {			
			date = FDCTransmissionHelper.checkDateFormat(fauditTime, getResource(ctx, "AuditTimeError"));
		}
		if(!StringUtils.isEmpty(fauditorNumber)) {
			auditor = isUserNumber(fauditorNumber, ctx);
			if(auditor == null){
				throw new TaskExternalException(getResource(ctx, "AuditorNumberNotFound"));
			}
		}
		
		CurProjectInfo curProjectInfo = null;
		ContractBillInfo contractBillInfo = null;
		CurrencyCollection currencyCollection = null;
		UserInfo creatot = null;
		CostCenterOrgUnitInfo costCenter = null;
		// �жϱ����Ƿ����
		if(getGuerdonBill("number", fnumber, ctx)) {
			throw new TaskExternalException(getResource(ctx, "NumbeIsRepate"));
		}
		// �ж������Ƿ����
		if(getGuerdonBill("name", fname, ctx)) {
			throw new TaskExternalException(getResource(ctx, "NameIsRepate"));
		}
		try {
			// ������Ŀ����
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", fcurProjectLongNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CurProjectNumberNotFound"));
			}
			
			// ��ͬ����
			FilterInfo contractBillFile = new FilterInfo();
			contractBillFile.getFilterItems().add(new FilterItemInfo("number", fcontractNumber));
			EntityViewInfo contractBillView = new EntityViewInfo();
			contractBillView.setFilter(contractBillFile);
			ContractBillCollection contractBillCollection = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(contractBillView);
			if(contractBillCollection.size() > 0) {
				contractBillInfo = contractBillCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "ContractBillNumberNotFound"));
			}
			
			// ¼��Ĺ�����Ŀ�ͺ�ͬ�еĹ�����Ŀ�Ƚ�
			String projectId = curProjectInfo.getId().toString();
			String curProjectId = contractBillInfo.getCurProject().getId().toString();
			if(!projectId.equals(curProjectId)) {
				throw new TaskExternalException(getResource(ctx, "ContractByCurProjectNotFound"));
			}
			
			// ��֯����
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
				if(!StringUtils.isEmpty(costOrgLongNumber) && !costCenterLongNumber.equals(costOrgLongNumber.replace('.', '!'))) {
					throw new TaskExternalException(getResource(ctx, "CostOrgLongNumberNotFound"));
				}
			}

			// �ұ�������û��¼�룬��Ĭ�ϱ�λ��
			if(fcurrencyNumber == null || StringUtils.isEmpty(fcurrencyNumber)) {
					FilterInfo currencyFilter = new FilterInfo();
					currencyFilter.getFilterItems().add(new FilterItemInfo("Number", "RMB"));
					EntityViewInfo currencyView = new EntityViewInfo();
					currencyView.setFilter(currencyFilter);
					currencyCollection = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(currencyView);
					if(currencyCollection.size() == 0) {
						throw new TaskExternalException(getResource(ctx, "CurrencyNotFound"));
					}
			} else {
					FilterInfo currencyFilter = new FilterInfo();
					currencyFilter.getFilterItems().add(new FilterItemInfo("Number", fcurrencyNumber));
					EntityViewInfo currencyView = new EntityViewInfo();
					currencyView.setFilter(currencyFilter);
					currencyCollection = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(currencyView);
					if(currencyCollection.size() == 0) {
						// �ұ���������ϵͳ���Ҳ�������Ĭ�ϱ�λ��
						currencyFilter = new FilterInfo();
						currencyFilter.getFilterItems().add(new FilterItemInfo("Number", "RMB"));
						currencyView = new EntityViewInfo();
						currencyView.setFilter(currencyFilter);
						currencyCollection = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(currencyView);
						if(currencyCollection.size() == 0) {
							throw new TaskExternalException(getResource(ctx, "CurrencyNotFound"));
						}
					}
			}
			
			CurrencyInfo cinfo = currencyCollection.get(0);
			ExchangeRateInfo erinfo = null;
			//����
			if(fexRate==null){//����Ϊ�� Ĭ��  �ұ�Ļ���
				erinfo  = ExchangeRateFactory.getLocalInstance(ctx).getExchangeRateInfo(new ObjectUuidPK(cinfo.getId()));
			}
			CurrencyInfo ci = null;//�����ұ�λ��
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			//����  �������Ϊ��   ȡ��ǰ����
			if(fexRate.compareTo(new BigDecimal(0)) == 0){
				//��λ�Ҷ���ci        //�û���д�ı���-ԭ�Ҷ��� cinfo
				ci = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where number='RMB'").get(0);
				if(ci==null){//ci�Ǳ�λ�ҹ�   �Ҳ�����λ��
					throw new TaskExternalException(getResource(ctx, "CurrencyNotFound"));
				}else{//�ҵ���λ�ҵ����
					if(ci.getName().trim().equals(cinfo.getName().trim())){//��������ҵ����
						fexRate = new BigDecimal(1.0000);
					}else{
						filter.getFilterItems().add(new FilterItemInfo("targetCurrency.id",ci.getId().toString(),CompareType.EQUALS));//Ŀ��ұ�λ��
						filter.getFilterItems().add(new FilterItemInfo("SourceCurrency.id", cinfo.getId().toString(), CompareType.EQUALS));//�û���д��ԭ��
						filter.setMaskString("#0 and #1");
						ev.setFilter(filter);
						ExchangeAuxInfo xinfo = ExchangeAuxFactory.getLocalInstance(ctx).getExchangeAuxCollection(ev).get(0);
						///ExchangeAuxInfo xinfo = ExchangeAuxFactory.getLocalInstance(ctx).getExchangeAuxCollection().get(0);
						if(xinfo==null){
							throw new TaskExternalException(getResource(ctx, "ExchangeNotFound"));
						}else{
							erinfo = ExchangeRateFactory.getLocalInstance(ctx).
										getExchangeRate(new ObjectUuidPK(xinfo.getExchangeTable().getId()),
												new ObjectUuidPK(xinfo.getSourceCurrency().getId()), 
												new ObjectUuidPK(xinfo.getTargetCurrency().getId()), 
												Calendar.getInstance().getTime());
							fexRate = erinfo.getConvertRate();
							if(erinfo==null){
								throw new TaskExternalException(getResource(ctx, "ExchangeNotFound"));
							}
						}
					}
				}	
			}
			
			// ���Ϊ0����ô���ݽ��*���ʼ���
			if(famount.compareTo(new BigDecimal(0)) == 0) {
				BigDecimal originalAmount = FDCTransmissionHelper.strToBigDecimal(foriginalAmount);
				famount = originalAmount.multiply(fexRate);
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		} catch (EASBizException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
		
		// �Ƶ��˱���
		creatot = isUserNumber(fcreatorNumber, ctx);
		if(creatot == null) {
			throw new TaskExternalException(getResource(ctx, "CreatorNumberNotFound"));
		}
		
		// ���ֵ
		info.setOrgUnit(costCenter.castToFullOrgUnitInfo());
		info.setCurProject(curProjectInfo);
		info.setContract(contractBillInfo);
		info.setNumber(fnumber);
		info.setName(fname);
		info.setState(FDCBillStateEnum.getEnum(stateStr));
		info.setCurrency(currencyCollection.get(0));
		info.setGuerdonThings(fguerdonThings);
		info.setGuerdonDes(fguerdonDes);
		info.setPutOutType(enums);
		info.setOriginalAmount(FDCTransmissionHelper.strToBigDecimal(foriginalAmount));
		info.setExRate(fexRate);
		info.setAmount(famount);
		if(fisGuerdoned.trim().equals(getResource(ctx, "Yes"))) {
			info.setIsGuerdoned(true);
		} else {
			info.setIsGuerdoned(false);
		}
		info.setCreator(creatot);
		info.setCreateTime(Timestamp.valueOf(fcreateTime + " 00:00:00"));
		info.setAuditor(auditor);
		info.setAuditTime(date);
	
		return info;
	}

	/**
	 * 	�ж��û��Ƿ����
	 * @param number �û�����
	 * @param ctx
	 * @return	true/false  ����/������
	 * @throws TaskExternalException 
	 */
	private UserInfo isUserNumber(String number, Context ctx) throws TaskExternalException {
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
			throw new TaskExternalException(e.getMessage());
		}
		return user;
	}
	
	private boolean getGuerdonBill(String field, String value, Context ctx) throws TaskExternalException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(field, value));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		GuerdonBillCollection guerdonCollection;
		try {
			guerdonCollection = GuerdonBillFactory.getLocalInstance(ctx).getGuerdonBillCollection(view);
			if(guerdonCollection.size() > 0) {
				return true;
			}
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
		return false;
	}
	
	private String  getStateStr(String fState, Context ctx) {
		String stateStr = null;
		if (fState.equals(getResource(ctx, "FDCBillState_SAVED"))) {
			// ����
			stateStr = FDCBillStateEnum.SAVED_VALUE;
		} else if (fState.equals(getResource(ctx, "FDCBillState_SUBMITTED"))) {
			// ���ύ
			stateStr = FDCBillStateEnum.SUBMITTED_VALUE;
		} else if (fState.equals(getResource(ctx, "FDCBillState_AUDITTED"))) {
			// ������
			stateStr = FDCBillStateEnum.AUDITTED_VALUE;
		}
		return stateStr;
	}
	
	private String  getPutOutTypeStr(String fputOutType, Context ctx) {
		String putOutTypeStr = null;
		if (fputOutType.equals(getResource(ctx, "Cash"))) {
			// �ֽ� �Է��е�˰��
			putOutTypeStr = PutOutTypeEnum.CASH_VALUE;
		} else if (fputOutType.equals(getResource(ctx, "Ticket"))) {
			// ��Ʊ �ҷ�ת��
			putOutTypeStr = PutOutTypeEnum.TICKET_VALUE;
		}
		return putOutTypeStr;
	}
	
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}