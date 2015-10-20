package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.RowSetMetaData;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.master.cssp.ISupplier;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.ContractSourceCollection;
import com.kingdee.eas.fdc.basedata.ContractSourceFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.ILandDeveloper;
import com.kingdee.eas.fdc.basedata.InviteTypeCollection;
import com.kingdee.eas.fdc.basedata.InviteTypeFactory;
import com.kingdee.eas.fdc.basedata.LandDeveloperCollection;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractSourceEnum;
import com.kingdee.eas.fdc.contract.CoopLevelEnum;
import com.kingdee.eas.fdc.contract.CostPropertyEnum;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.PriceTypeEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractBillTransmission extends AbstractDataTransmission {
	private static String NUMBER = "FNumber";
	// ������������
	
	LandDeveloperInfo ldInfo = null;
	ContractBillInfo contractInfo = null;
	CurProjectInfo curProject = null;
	ContractDetailDefCollection conDetailDefs = null;
	HashMap conDetailDefMap = null;
	String mainName = "";

	String isLonelyCal = "�Ƿ񵥶�����";
	String amountWithOutCost = "���Ƴɱ��Ľ��";
	String mainContractNumber = "��Ӧ����ͬ����";
	String mainContractName = "��Ӧ����ͬ����";
	/** ��ͬ��ϸ��Ϣ���� �� ��ϸ��Ϣ */
	public final static String CON_DETAIL_DETAIL_COL = "detail";

	/** ��ͬ��ϸ��Ϣ���� �� ���� */
	public final static String CON_DETIAL_CONTENT_COL = "content";

	/** ��ͬ��ϸ��Ϣ���� - ���� */
	public final static String CON_DETIAL_DESC_COL = "desc";

	/** ��ͬ��ϸ��Ϣ���� �� ID */
	public final static String CON_DETIAL_ID_COL = "id";

	/** ��ͬ��ϸ��Ϣ���� - �б�ʶ */
	public final static String CON_DETIAL_ROWKEY_COL = "rowKey";

	/** ��ͬ��ϸ��Ϣ���� - �������� */
	public final static String CON_DETIAL_DATATYPE_COL = "dataType";
	
	/** ��ͬ���ʸ����� �� �Ƿ񵥶����� */
	public final static String IS_LONELY_CAL_ROW = "lo";

	public final static String AMOUNT_WITHOUT_COST_ROW = "am";

	public final static String MAIN_CONTRACT_NUMBER_ROW = "nu";

	public final static String MAIN_CONTRACT_NAME_ROW = "na";
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		// TODO �Զ����ɷ������
		try{	
			return ContractBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("", e);
		}
	}
	
	/**
	 * ����number��ȡid�����û���򷵻�null
	 * @param number
	 * @param ctx
	 * @return
	 * @throws TaskExternalException
	 * @author Robin
	 */
	private String getIdFromNumber(String number, Context ctx) throws TaskExternalException {
		ContractBillCollection collection;
		try {
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("id");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject",curProject.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("number",number));
			view.setFilter(filter);
			collection = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}

		if (collection != null && collection.size() > 0) {
			return collection.get(0).getId().toString();
		}
		return null;
	}
	/****
	 * ��ͬ����Excel
	 * ��Ϻ�ͬ��ͷ��Ϣ
	 * @param lineData
	 * @param ctx
	 * @return
	 * @throws TaskExternalException
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws ContractException 
	 * @throws EASBizException 
	 */
	private ContractBillInfo transmitHead(Hashtable lineData, Context ctx) throws TaskExternalException, BOSException, EASBizException, ContractException, EASBizException{		
		boolean noNumber = false;
		
		Object number = ((DataToken) lineData.get(NUMBER)).data;
		Object curProjectNumber = ((DataToken) lineData.get("FCurProject_longNumber")).data;		
		if(curProjectNumber == null || curProjectNumber.toString().trim().length()==0
				|| number == null || number.toString().trim().length()==0
				){
			return null;
		}
		if(number.toString().length()>80){
			throw new TaskExternalException( number.toString().trim() +  " ���벻�ܴ���80��");
		}
		/***
		 * ��֤�ɱ������Ƿ�¼��
		 */
		getCurProjectInfo(ctx,curProjectNumber);
		
		if (curProject != null && number != null && number.toString().trim().length() > 0){
			contractInfo = new ContractBillInfo();
			contractInfo.setNumber(number.toString().trim());
			contractInfo.setCurProject(curProject);
			String existId = getIdFromNumber(number.toString().trim(),ctx);
			// �Ƿ񸲸���ͬ��¼��
			// �������ǣ��Ҵ�����ͬ��number���򷵻�null��
			if (isSltImportUpdate() == false && !StringUtil.isEmptyString(existId)) {
				throw new TaskExternalException(number.toString().trim() + " ͬ������Ŀ������ͬ�ı��룡");
			}
			
			// �����ǵĵ����ύ����������ֱ�ӷ���null
			if (isSltImportUpdate() && !StringUtil.isEmptyString(existId)){
				ContractBillInfo existInfo = null;
				try {
					existInfo = ((IContractBill)getController(ctx)).getContractBillInfo(new ObjectUuidPK(existId));
				} catch (Exception e) {
					throw new TaskExternalException("", e);
				}
				if (FDCBillStateEnum.SUBMITTED.equals(existInfo.getState())){
					throw new TaskExternalException(number.toString().trim() + " ��ͬ�Ѿ����ύ״̬��");
				}
			}
			contractInfo.setOrgUnit(curProject.getFullOrgUnit());
		}else {
			/*
			 * û���������
			 */
			noNumber = true;
		}
		/***
		 * ����ͬ����
		 */
		//  TODO checkContractType()
		Object contractTypeNumber = ((DataToken) lineData.get("FContractType_longNumber")).data;
		if(contractTypeNumber==null || contractTypeNumber.toString().trim().length()==0){
			throw new TaskExternalException(number.toString().trim() + " ��ͬ����û��¼��");
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longnumber",contractTypeNumber.toString().trim().replace('.','!')));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled","1"));
		view.setFilter(filter);
		view.getSelector().add("id");
		ContractTypeCollection contractTypes = ContractTypeFactory.getLocalInstance(ctx).getContractTypeCollection(view);
		if(contractTypes == null || contractTypes.size()==0)
			throw new TaskExternalException(number.toString().trim() + " ��ͬ����û�����û򳤱��벻��ȷ");
		contractInfo.setContractType(contractTypes.get(0));
		
		contractInfo.setRespDept(contractInfo.getContractType().getDutyOrgUnit());
		
		Object name = ((DataToken) lineData.get("FName")).data;
		
		if(name != null && name.toString().length()>0)
		{	
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name",name.toString().trim()));
			filter.getFilterItems().add(new FilterItemInfo("curProject",curProject.getId().toString()));
			if(ContractBillFactory.getLocalInstance(ctx).exists(filter)){
				throw new TaskExternalException(number.toString().trim() + " �����Ѿ�������");
			}
			else if(name.toString().length()>100){
				throw new TaskExternalException(number.toString().trim() + " ���Ƴ��Ȳ��ܴ���100");
			}
			else
				contractInfo.setName(name.toString());
		
		}
		else
			throw new TaskExternalException(number.toString().trim() + " ����û��¼��");
		
		/***
		 * ����ͬ����
		 * ö��
		 */
		Object FContractPropert = ((DataToken)lineData.get("FContractPropert")).data;
		if(FContractPropert != null && FContractPropert.toString().trim().length()>0
				&&ContractPropertyEnum.getEnum(FContractPropert.toString().trim())!=null){
			contractInfo.setContractPropert(ContractPropertyEnum.getEnum(FContractPropert.toString().trim()));			
		}
		else{
			throw new TaskExternalException(number.toString().trim() + " ��ͬ����¼�벻��ȷ");
		}
		
		/***
		 * ���׷�
		 */
		Object FLandDeveloper_number = ((DataToken) lineData.get("FLandDeveloper_number")).data;
		checkLandDeveloper(ctx,FLandDeveloper_number);
		
		/****
		 * ����ҷ�
		 */
		//  checkPartB()
		boolean isThree = contractInfo.getContractPropert().equals(ContractPropertyEnum.THREE_PARTY);
		Object FPartB_number = ((DataToken) lineData.get("FPartB_number")).data;
		contractInfo.setPartB(checkSupplier(ctx,FPartB_number,"�ҷ�",!isThree));
		
		/***
		 * ������
		 */
		// checkPartC()
		Object FPartC_number = ((DataToken) lineData.get("FPartC_number")).data;
		contractInfo.setPartC(checkSupplier(ctx,FPartC_number,"����",!isThree));
		
		
		
				
		/***
		 * �ұ�
		 */
		Object FCurrency_number = ((DataToken) lineData.get("FCurrency_number")).data;
		view = this.getFilter(FCurrency_number.toString().trim());
		CurrencyCollection currencys = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(view);
		if(currencys == null || currencys.size()==0)
			throw new TaskExternalException(number.toString().trim() + " �ұ�¼�벻��ȷ");
		contractInfo.setCurrency(currencys.get(0));
		
		
		// setExRate()
		
		Object FExRate = ((DataToken) lineData.get("FExRate")).data;
		contractInfo.setExRate(checkBigDecimalInfo(FExRate,number.toString(),"����",false));
		
		
		
		/*********
		 * ����������ֶθ�ʽ��������
		 */ 
		Object FSignDate = ((DataToken) lineData.get("FSignDate")).data;
		contractInfo.setSignDate(checkDateFormat(FSignDate,number.toString(),"ǩԼ����",true));
		Object FBookedDate = ((DataToken) lineData.get("FBookedDate")).data;
		contractInfo.setBookedDate(checkDateFormat(FBookedDate,number.toString(),"ҵ���¼����",true));
		
		/***
		 * ������β���
		 */
		// TODO checkRespDept()
		Object FRespDept_longNumber = ((DataToken)lineData.get("FRespDept_longNumber")).data;
		if(FRespDept_longNumber != null && FRespDept_longNumber.toString().trim().length() > 0){
			String respLongNumber = FRespDept_longNumber.toString().trim().replace('.','!');
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("longnumber",respLongNumber));
			view.setFilter(filter);
			AdminOrgUnitCollection resps = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(view);
			if(resps != null && resps.size() > 0)
				contractInfo.setRespDept(resps.get(0));
			else{
				throw new TaskExternalException(number.toString().trim() + " ���β���¼�벻��ȷ");
			}
		}
		
		
		
		//setOriginalAmount ԭ��
		Object FOriginalAmount = ((DataToken) lineData.get("FOriginalAmount")).data;
		contractInfo.setOriginalAmount(checkBigDecimalInfo(FOriginalAmount,number.toString(),"ԭ�ҽ��",false));
		
		//setAmount ����
		Object FAmount = ((DataToken) lineData.get("FAmount")).data;
		contractInfo.setAmount(checkBigDecimalInfo(FAmount,number.toString(),"���ҽ��",false));
		
		/****
		 * ���������
		 */
		// TODO checkRespPerson()
		Object FRespPerson_number = ((DataToken) lineData.get("FRespPerson_number")).data;
		if(FRespPerson_number !=null && FRespPerson_number.toString().trim().length()>0){
			String respNumber = FRespPerson_number.toString().trim();
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number",respNumber));
			view.setFilter(filter);
			PersonCollection resps = PersonFactory.getLocalInstance(ctx).getPersonCollection(view);
			if(resps != null && resps.size() > 0)
				contractInfo.setRespPerson(resps.get(0));
			else{
				throw new TaskExternalException(number.toString().trim() + " ������¼�벻��ȷ");
			}
		}
		
		
		
		//setGrtRate ���޽����
		Object FGrtRate = ((DataToken) lineData.get("FGrtRate")).data;
		contractInfo.setGrtRate(checkBigDecimalRate(FGrtRate,number.toString(),"���޽����",true));
		
		
		/***
		 * ����γɷ�ʽ
		 * ö��
		 */
		// TODO checkContractSource()
		Object FContractSource = ((DataToken)lineData.get("FContractSource")).data;
		if(FContractSource == null || FContractSource.toString().trim().length() == 0)
			throw new TaskExternalException(number.toString().trim() + " �γɷ�ʽ¼�벻��ȷ");
		try{
			String contractSourceNumber=FContractSource.toString().trim();
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number",contractSourceNumber));
			view.setFilter(filter);
			ContractSourceCollection contractSource = ContractSourceFactory.getLocalInstance(ctx).getContractSourceCollection(view);
			if(contractSource==null||contractSource.size()==0){
				throw new TaskExternalException(number.toString().trim() + " �γɷ�ʽ¼�벻��ȷ");
			}else{
				contractInfo.setContractSourceId(contractSource.get(0));
			}
		}
		catch(Exception e){
			throw new TaskExternalException(number.toString().trim() + " �γɷ�ʽ¼�벻��ȷ");
		}
		
		//setPayPercForWarn ������ʾ����
		Object FPayPercForWarn = ((DataToken) lineData.get("FPayPercForWarn")).data;
		contractInfo.setPayPercForWarn(checkBigDecimalRate(FPayPercForWarn,number.toString(),"������ʾ����",true));
		
				
		/***
		 * �������
		 * ö��
		 */
		//checkCostProperty 
		Object FCostProperty = ((DataToken)lineData.get("FCostProperty")).data;
		if(FCostProperty == null || FCostProperty.toString().trim().length() == 0)
			throw new TaskExternalException(number.toString().trim() + " �������¼�벻��ȷ");
		try{
			CostPropertyEnum costPro=CostPropertyEnum.getEnum(FCostProperty.toString().trim());
			if(costPro==null){
				throw new TaskExternalException(number.toString().trim() + " �������¼�벻��ȷ");
			}else{
				contractInfo.setCostProperty(costPro);
			}
		}
		catch(Exception e){
			throw new TaskExternalException(number.toString().trim() + " �������¼�벻��ȷ");
		}
		
		
		//setChgPercForWarn �����ʾ����
		Object FChgPercForWarn = ((DataToken) lineData.get("FChgPercForWarn")).data;
		contractInfo.setChgPercForWarn(checkBigDecimalRate(FChgPercForWarn,number.toString(),"�����ʾ����",true));
		
		//isAmtWithoutCost �Ƿ���붯̬�ɱ�
		Object FIsCoseSplit = ((DataToken) lineData.get("FIsCoseSplit")).data;
		if(FIsCoseSplit == null || FIsCoseSplit.toString().trim().length() == 0)
			throw new TaskExternalException(number.toString().trim() + " �Ƿ���붯̬�ɱ�¼�벻��ȷ");
		
		if(FIsCoseSplit.toString().trim().equals("true"))			
			contractInfo.setIsCoseSplit(true);
		else if(FIsCoseSplit.toString().trim().equals("false"))
			contractInfo.setIsCoseSplit(false);
		else
			throw new TaskExternalException(number.toString().trim() + " �Ƿ���붯̬�ɱ�¼�벻��ȷ");
		
		
		//setPayScale //���ȿ�����
		
		Object FPayScale = ((DataToken) lineData.get("FPayScale")).data;
		contractInfo.setPayScale(checkBigDecimalRate(FPayScale,number.toString(),"���ȿ�����",true));
				
		//stampTaxRateӡ��˰��
		Object FStampTaxRate = ((DataToken) lineData.get("FStampTaxRate")).data;
		contractInfo.setStampTaxRate(checkBigDecimalRate(FStampTaxRate,number.toString(),"���ȿ�����",true));
		//stampTaxAmtӡ��˰���
		Object FStampTaxAmt = ((DataToken) lineData.get("FStampTaxAmt")).data;
		contractInfo.setStampTaxAmt(checkBigDecimalInfo(FStampTaxAmt,number.toString(),"ӡ��˰���",true));
		//remark
		
		
		/****
		 * �γɷ�ʽ-�б�
		 */
		
			// lowestPrice lowestPriceUnit
		Object FLowestPrice = ((DataToken) lineData.get("FLowestPrice")).data;
		contractInfo.setLowestPrice(checkBigDecimalInfo(FLowestPrice,number.toString(),"��ͱ���",true));
		Object FLowestPriceUnit_number = ((DataToken)lineData.get("FLowestPriceUnit_number")).data;
		contractInfo.setLowestPriceUnit(checkSupplier(ctx,FLowestPriceUnit_number,"��ͱ��۵�λ",true));
			// lowerPrice lowerPriceUnit
		Object FLowerPrice = ((DataToken) lineData.get("FLowerPrice")).data;
		contractInfo.setLowerPrice(checkBigDecimalInfo(FLowerPrice,number.toString(),"�εͱ���",true));
		Object FLowerPriceUnit_number = ((DataToken)lineData.get("FLowerPriceUnit_number")).data;
		contractInfo.setLowerPriceUnit(checkSupplier(ctx,FLowerPriceUnit_number,"�εͱ��۵�λ",true));
			// middlePrice middlePriceUnit
		Object FMiddlePrice = ((DataToken) lineData.get("FMiddlePrice")).data;
		contractInfo.setMiddlePrice(checkBigDecimalInfo(FMiddlePrice,number.toString(),"�м䱨��",true));
		Object FMiddlePriceUnit_number = ((DataToken)lineData.get("FMiddlePriceUnit_number")).data;
		contractInfo.setMiddlePriceUnit(checkSupplier(ctx,FMiddlePriceUnit_number,"�м䱨�۵�λ",true));
			// higherPrice higherPriceUnit
		Object FHigherPrice = ((DataToken) lineData.get("FHigherPrice")).data;
		contractInfo.setHigherPrice(checkBigDecimalInfo(FHigherPrice,number.toString(),"�θ߱���",true));
		Object FHigherPriceUnit_number = ((DataToken)lineData.get("FHigherPriceUnit_number")).data;
		contractInfo.setHigherPriceUnit(checkSupplier(ctx,FHigherPriceUnit_number,"�θ߱��۵�λ",true));
			// highestPrice highestPriceUni
		Object FHighestPrice = ((DataToken) lineData.get("FHighestPrice")).data;
		contractInfo.setHighestPrice(checkBigDecimalInfo(FHighestPrice,number.toString(),"��߱���",true));
		Object FHighestPriceUni_number = ((DataToken)lineData.get("FHighestPriceUni_number")).data;
		contractInfo.setHighestPriceUni(checkSupplier(ctx,FHighestPriceUni_number,"��߱��۵�λ",true));
			// winPrice winUnit
		Object FWinPrice = ((DataToken) lineData.get("FWinPrice")).data;
		contractInfo.setWinPrice(checkBigDecimalInfo(FWinPrice,number.toString(),"�б걨��",true));
		Object FWinUnit_number = ((DataToken)lineData.get("FWinUnit_number")).data;
		contractInfo.setWinUnit(checkSupplier(ctx,FWinUnit_number,"�б굥λ",true));
		
			// quantity
		Object FQuantity = ((DataToken) lineData.get("FQuantity")).data;
		contractInfo.setQuantity(checkBigDecimalInfo(FQuantity,number.toString(),"��λ����",true));
			// inviteType 
		Object FInviteType_number = ((DataToken) lineData.get("FInviteType_number")).data;
		if(FInviteType_number !=null && FInviteType_number.toString().trim().length()>0){
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number",FInviteType_number.toString().trim()));
			view.setFilter(filter);
			InviteTypeCollection cc =  InviteTypeFactory.getLocalInstance(ctx).getInviteTypeCollection(view);
			if(cc!=null&&cc.size()>0)
				contractInfo.setInviteType(cc.get(0));
			else{
				throw new TaskExternalException(number.toString().trim() + " �б�����¼�벻��ȷ");
			}
			
		}
		
			// fileNo
		contractInfo.setFileNo((((DataToken) lineData.get("FFileNo")).data).toString());
		
			// secondPrice
		Object FSecondPrice = ((DataToken) lineData.get("FSecondPrice")).data;
		contractInfo.setSecondPrice(checkBigDecimalInfo(FSecondPrice,number.toString(),"�����",true));
			// basePrice
		Object FBasePrice = ((DataToken) lineData.get("FBasePrice")).data;
		contractInfo.setBasePrice(checkBigDecimalInfo(FBasePrice,number.toString(),"�׼�",true));
			//
		
			// coopLevel enum
		Object FCoopLevel = ((DataToken)lineData.get("FCoopLevel")).data;
		if(FCoopLevel != null && FCoopLevel.toString().trim().length() > 0)
		{
			try{
				CoopLevelEnum costPro=CoopLevelEnum.getEnum(FCoopLevel.toString().trim());
				if(costPro==null){
					throw new TaskExternalException(number.toString().trim() + " ս�Ժ�������¼�벻��ȷ");
				}else{
					contractInfo.setCoopLevel(costPro);
				}
			}
			catch(Exception e){
				throw new TaskExternalException(number.toString().trim() + " ս�Ժ�������¼�벻��ȷ");
			}
		}
			//priceType enum
		
		Object FPriceType = ((DataToken)lineData.get("FPriceType")).data;
		if(FPriceType != null && FPriceType.toString().trim().length() > 0){
			try{
				PriceTypeEnum costPro=PriceTypeEnum.getEnum(FPriceType.toString().trim());
				if(costPro==null){
					throw new TaskExternalException(number.toString().trim() + " �Ƽ۷�ʽ¼�벻��ȷ");
				}else{
					contractInfo.setPriceType(costPro);
				}
			}
			catch(Exception e){
				throw new TaskExternalException(number.toString().trim() + " �Ƽ۷�ʽ¼�벻��ȷ");
			}
		}
		
		
		/***
		 * ������Դ��ʽ��ϵͳ����
		 * sourceType=0
		 */  
		contractInfo.setSourceType(SourceTypeEnum.IMP);
		
//		if (noNumber) {// ����ʱ�ޱ���Ҫ��������
//			String orgID = orgUnit.getId().toString().trim();
//			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
//			if (iCodingRuleManager.isExist(contractInfo, orgID)) {// ���ù��������
//				contractInfo.setNumber(iCodingRuleManager.getNumber(contractInfo, orgID, ""));
//			} else {// �׳��쳣��ʾδ���ù��������
//				IMetaDataLoader imeataLoader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
//				EntityObjectInfo entityObjectInfo = imeataLoader.getEntity(orgUnit.getBOSType());
//				String[] params = new String[1];
//				params[0] = entityObjectInfo.getAlias();
//				throw new ContractException(ContractException.NOCODEINGRULE, params);
//			}
//		}
		// ��䵥ͷ��������
		// CU
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id", contractInfo.getContractType().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		conDetailDefs = ContractDetailDefFactory
				.getLocalInstance(ctx).getContractDetailDefCollection(view);
		conDetailDefMap = new HashMap();
		contractInfo.getEntrys().clear();
		ContractBillEntryInfo entryInfo = null;
		for(Iterator it=conDetailDefs.iterator();it.hasNext();){
			ContractDetailDefInfo defInfo = (ContractDetailDefInfo)it.next();
			entryInfo = new ContractBillEntryInfo();
			int seq = contractInfo.getEntrys().size() + 1;
			entryInfo.setParent(contractInfo);
			entryInfo.setDetail(defInfo.getName());
			entryInfo.setSeq(seq);
			entryInfo.setDataType(defInfo.getDataTypeEnum());
			entryInfo.setDetailDefID(defInfo.getId().toString());
			contractInfo.getEntrys().add(entryInfo);
			conDetailDefMap.put(defInfo.getNumber(),entryInfo);
		}
		if(!ContractPropertyEnum.DIRECT.equals(contractInfo.getContractPropert())){
			entryInfo = new ContractBillEntryInfo();
			entryInfo.setParent(contractInfo);
			entryInfo.setSeq(contractInfo.getEntrys().size()+1);
			entryInfo.setDetail(isLonelyCal);
			entryInfo.setDataType(DataTypeEnum.BOOL);
			entryInfo.setRowKey(IS_LONELY_CAL_ROW);
			contractInfo.getEntrys().add(entryInfo);
			conDetailDefMap.put("isLonelyCal",entryInfo);
			
			entryInfo = new ContractBillEntryInfo();
			entryInfo.setParent(contractInfo);
			entryInfo.setSeq(contractInfo.getEntrys().size()+1);
			entryInfo.setDetail(amountWithOutCost);
			entryInfo.setDataType(DataTypeEnum.NUMBER);
			entryInfo.setRowKey(AMOUNT_WITHOUT_COST_ROW);			
			contractInfo.getEntrys().add(entryInfo);
			conDetailDefMap.put("amountWithOutCost",entryInfo);
			
			entryInfo = new ContractBillEntryInfo();
			entryInfo.setParent(contractInfo);
			entryInfo.setSeq(contractInfo.getEntrys().size()+1);
			entryInfo.setDetail(mainContractNumber);
			entryInfo.setDataType(DataTypeEnum.STRING);
			entryInfo.setRowKey(MAIN_CONTRACT_NUMBER_ROW);			
			contractInfo.getEntrys().add(entryInfo);
			conDetailDefMap.put("mainContractNumber",entryInfo);
			
			entryInfo = new ContractBillEntryInfo();
			entryInfo.setParent(contractInfo);
			entryInfo.setSeq(contractInfo.getEntrys().size()+1);
			entryInfo.setDetail(mainContractName);
			entryInfo.setDataType(DataTypeEnum.STRING);
			entryInfo.setRowKey(MAIN_CONTRACT_NAME_ROW);
			contractInfo.getEntrys().add(entryInfo);
			conDetailDefMap.put("mainContractName",entryInfo);
			
		}
		
		contractInfo.setCU(curProject.getCU());
		contractInfo.setState(FDCBillStateEnum.SAVED);
		contractInfo.setWebSrvNumber(contractInfo.getNumber());
		
		return contractInfo;
	}
	public BigDecimal checkBigDecimalRate(Object ldData,String number,String msg,boolean canNull) throws TaskExternalException{
		if(ldData != null && ldData.toString().trim().length() > 0){
			try{
				BigDecimal amount = new BigDecimal(ldData.toString().trim());
				if(amount.compareTo(FDCHelper.ONE_HUNDRED)>0){
					throw new TaskExternalException(number.toString().trim() + " "+ msg +"¼�벻���ܴ���100");
				}
				return amount;
			}
			catch(Exception e){
				throw new TaskExternalException(number.toString().trim() + " "+ msg +"¼�벻��ȷ");
			}
		}
		else if(canNull){
			return FDCHelper._ONE;
		}
		else
			throw new TaskExternalException(number.toString().trim() + " "+ msg +"¼�벻��ȷ");
		
	}
	public BigDecimal checkBigDecimalInfo(Object ldData,String number,String msg,boolean canNull) throws TaskExternalException{
		if(ldData != null && ldData.toString().trim().length() > 0){
			try{
				BigDecimal amount = new BigDecimal(ldData.toString().trim());
				return amount;
			}
			catch(Exception e){
				throw new TaskExternalException(number.toString().trim() + " "+ msg +"¼�벻��ȷ");
			}
		}
		else if(canNull){
			return FDCHelper._ONE;
		}
		else
			throw new TaskExternalException(number.toString().trim() + " "+ msg +"¼�벻��ȷ");
		
	}
	/****
	 * ��ȡ������Ŀ��Ϣ
	 * @param ctx
	 * @param curProjectNumber
	 * @throws TaskExternalException
	 * @throws EASBizException 
	 * @throws BOSException 
	 */
	public void getCurProjectInfo(Context ctx,Object curProjectNumber) throws TaskExternalException, EASBizException, BOSException{		
	
		if (curProjectNumber != null && curProjectNumber.toString().trim().length() > 0) {
			String curLongNumber = curProjectNumber.toString();
			curLongNumber = curLongNumber.replace('.','!');
			curProjectNumber = curLongNumber;
			ICurProject icur = CurProjectFactory.getLocalInstance(ctx);
			EntityViewInfo view = new EntityViewInfo();
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("longnumber",curLongNumber));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled","1"));
			view.setFilter(filter);
			CurProjectCollection collection = icur.getCurProjectCollection(view);
			if (collection != null && collection.size() > 0) {
				String curid = collection.get(0).getId().toString();					
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("parent",curid));
				if(icur.exists(filter)){
					curProject = null;
					throw new TaskExternalException(curProjectNumber.toString().trim() + " ������Ŀ����ϸ�ڵ�");
				}
				else
					curProject = collection.get(0);
			} else {
				// ������Ŀ������
				curProject = null;
				throw new TaskExternalException(curProjectNumber.toString().trim() + " ������Ŀ������");
			}
		} else {
			curProject = null;
			throw new TaskExternalException("������Ŀ������û��¼��");
		}
	}
	
	/****
	 * ������ڸ�ʽ�������Ƿ���ȷ
	 * @param lineData
	 * @throws TaskExternalException
	 */
	private Date checkDateFormat(Object ldData,String number,String msg,boolean canNull) throws TaskExternalException{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (ldData != null && ldData.toString().length() > 0) {
			try {
				Date d = df.parse(ldData.toString());
				return d;
			} catch (ParseException e) {
				e.printStackTrace();
				// :��ʽ���󣬱���Ϊ���ڸ�ʽ��yyyy-mm-dd������ǰ��ʽΪ:
				throw new TaskExternalException(number +  msg + " ����Ϊ���ڸ�ʽ yyyy-mm-dd");
			}			
		}else if(canNull){
			return null;
		}else{
			throw new TaskExternalException(number + msg + "����¼��");
		}
		
	}
	/*****
	 * ��鹩Ӧ���ֶ��Ƿ�¼����ȷ
	 */
	private SupplierInfo checkSupplier(Context ctx,Object ldData,String msg,boolean canNull) throws TaskExternalException{
		try {			
			if (ldData != null && ldData.toString().trim().length() > 0) {
				ISupplier iLD = SupplierFactory.getLocalInstance(ctx);
				SupplierCollection collection = iLD.getSupplierCollection(getFilter(ldData.toString()
						.trim()));
				if (collection != null && collection.size() > 0) {
					return collection.get(0);				
				}else{ 
					throw new TaskExternalException(msg+"�б���Ϊ"+ldData.toString()+"�Ĺ�Ӧ�̲�����");
				}
			}
			else if(canNull){
				return null;
			}
			else
				throw new TaskExternalException(msg+"�б���Ϊ"+ldData.toString()+"�Ĺ�Ӧ�̱���¼��");
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
	}
	/************
	 * ���׷������Ƿ�¼�뼰��ȷ
	 * @param ctx
	 * @param ldData
	 * @throws TaskExternalException
	 */
	private void checkLandDeveloper(Context ctx,Object ldData) throws TaskExternalException{
		//  ���׷�
		try {
			// �׷� landDeveloper
			if (ldData != null && ldData.toString().trim().length() > 0) {
				ILandDeveloper iLD = LandDeveloperFactory.getLocalInstance(ctx);
				LandDeveloperCollection collection = iLD.getLandDeveloperCollection(getFilter(ldData.toString()
						.trim()));
				if (collection != null && collection.size() > 0) {
					contractInfo.setLandDeveloper(collection.get(0));					
				} else {
					// �׷�������
					throw new TaskExternalException(ldData.toString().trim() + " �׷�������");
				}
			} else {
				throw new TaskExternalException("û��¼��׷���ֵ");
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
	}
	/**************
	 * ��ȡ�������͵�EntityViewInfo
	 */	 
	private EntityViewInfo getFilter(String number) {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		viewInfo.setFilter(filter);
		return viewInfo;
	}
	/***
	 * ��ͬ����Excel
	 * ��Ϻ�ͬ��¼��Ϣ
	 * @param hsData
	 * @param ctx
	 * @return
	 * @throws TaskExternalException
	 * @throws BOSException 
	 */
	private ContractBillEntryInfo transmitEntry(Hashtable hsData, Context ctx) throws TaskExternalException, BOSException{
		if (hsData == null || conDetailDefs == null) {
			return null;
		}
		ContractBillEntryInfo  entryInfo = null;
		Object detailNumber = ((DataToken)hsData.get("FEntrys_detailNumber")).data;
		
		if(detailNumber==null || detailNumber.toString().trim().length()==0){
			return null;
		}
		Object FEntrys_content = ((DataToken)hsData.get("FEntrys_content")).data;
		Object FEntrys_desc = ((DataToken)hsData.get("FEntrys_desc")).data;
		if(conDetailDefMap.containsKey(detailNumber.toString().trim())){
			entryInfo = 	(ContractBillEntryInfo)conDetailDefMap.get(detailNumber.toString().trim());
			
			if(FEntrys_content != null)
				entryInfo.setContent(FEntrys_content.toString());
			if(FEntrys_desc != null)
				entryInfo.setDesc(FEntrys_desc.toString());
		}
		else if(!ContractPropertyEnum.DIRECT.equals(contractInfo.getContractPropert())){
			/*****
			 * �Ƿ񵥶�����
			 */
			if(IS_LONELY_CAL_ROW.equals(detailNumber)){
				if(conDetailDefMap.get("isLonelyCal")!=null){
					entryInfo = (ContractBillEntryInfo)conDetailDefMap.get("isLonelyCal");
					if(FEntrys_content != null && 
							(FEntrys_content.toString().equals(BooleanEnum.TRUE.getAlias())||
									FEntrys_content.toString().equals(BooleanEnum.FALSE.getAlias())	))
						entryInfo.setContent(FEntrys_content.toString());
					if(FEntrys_desc != null)
						entryInfo.setDesc(FEntrys_desc.toString());
				}
			}
			/*****
			 * ������ɱ��Ľ��
			 */
			else if(AMOUNT_WITHOUT_COST_ROW.equals(detailNumber)){
				if(conDetailDefMap.get("amountWithOutCost")!=null){
					entryInfo = (ContractBillEntryInfo)conDetailDefMap.get("amountWithOutCost");
					if(FEntrys_content != null && FEntrys_content.toString().length()>0){
						try{
							BigDecimal value = new BigDecimal(FEntrys_content.toString());
							entryInfo.setContent(String.valueOf(value));
						}
						catch(Exception e){
							throw new TaskExternalException(contractInfo.getNumber()+"��¼���Ƴɱ��Ľ��¼�벻��ȷ");
						}
						
					}
					if(FEntrys_desc != null)
						entryInfo.setDesc(FEntrys_desc.toString());
				}
			}
			/*****
			 * ��Ӧ����ͬ�ı���
			 */
			else if(MAIN_CONTRACT_NUMBER_ROW.equals(detailNumber)){
				if(conDetailDefMap.get("mainContractNumber")!=null){
					entryInfo = (ContractBillEntryInfo)conDetailDefMap.get("mainContractNumber");
					String mainId = "";
					
					String mainNumber = "";
					if(FEntrys_content != null && FEntrys_content.toString().trim().length() > 0){
						mainNumber =  FEntrys_content.toString().trim();
						contractInfo.setMainContractNumber(mainNumber);
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						view.getSelector().add("id");
						view.getSelector().add("name");
						filter.getFilterItems().add(new FilterItemInfo("number",mainNumber));
						filter.getFilterItems().add(new FilterItemInfo("curproject",contractInfo.getCurProject().getId()));
						view.setFilter(filter);
						ContractBillCollection mains = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
						if(mains !=null && mains.size() > 0){
							mainId = mains.get(0).getId().toString();
							mainName = mains.get(0).getName();
						}
						entryInfo.setContent(mainId);
					}
					
					if(FEntrys_desc != null)
						entryInfo.setDesc(FEntrys_desc.toString());
				}
			}
			/*****
			 * ��Ӧ����ͬ������
			 */
			else if(MAIN_CONTRACT_NAME_ROW.equals(detailNumber)){
				if(conDetailDefMap.get("mainContractName")!=null){
					entryInfo = (ContractBillEntryInfo)conDetailDefMap.get("mainContractName");
					entryInfo.setContent(mainName);
					if(FEntrys_desc != null)
						entryInfo.setDesc(FEntrys_desc.toString());
				}
			}
		}
		return entryInfo;
	}
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof ContractBillInfo == false) {
			return;
		}

		try {
			ContractBillInfo billBase = (ContractBillInfo) coreBaseInfo;
			String id = getIdFromNumber(billBase.getNumber(), ctx);
			if (StringUtil.isEmptyString(id)) {
				getController(ctx).addnew(coreBaseInfo);
			} else {
				coreBaseInfo.setId(BOSUuid.read(id));
				getController(ctx).update(new ObjectUuidPK(id), coreBaseInfo);
			}

		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}
	}
	/***
	 * ��ͬ����Excel�ľ���ʵ��
	 * @param hsData
	 * @param ctx
	 * @return
	 * @throws EASBizException
	 * @throws TaskExternalException 
	 * @throws BOSException 
	 */
	protected CoreBaseInfo innerTransform(Hashtable hsData, Context ctx) throws EASBizException, TaskExternalException, BOSException{
		
		contractInfo = null;
		// �α꣬��ʾĿǰiterator��ָ��λ��
		try{
			for (int i = 0; i < hsData.size(); i++){
				Hashtable lineData = (Hashtable) hsData.get(new Integer(i));
				// ���α�Ϊ0����ʾ��һ����¼����ʱҪƴ��һ����ͷ��
				if (i == 0){
					contractInfo = transmitHead(lineData, ctx);
				}
				if(contractInfo==null){
					return null;
				}
				// ���·�¼
				transmitEntry(lineData, ctx);			
				//entry.setParent(contractInfo);
				//contractInfo.getEntrys().add(entry);
			}
		}
		catch(TaskExternalException e){
			contractInfo = null;
			throw e; 
		}
		
		
		return contractInfo;
	}
	public FilterInfo getExportFilterForQuery(Context ctx){
		FilterInfo filter = super.getExportFilterForQuery(ctx);
		if(getContextParameter("ids")!=null)
		{
			Set ids = (Set)getContextParameter("ids");
			filter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
		}
		return filter;
	}
	public String getExportQueryInfo(Context ctx) {
		return "com.kingdee.eas.fdc.contract.app.ContractBillQuery";
	}
	public int getSubmitType() {
		return SUBMITMULTIRECTYPE;
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		// TODO �Զ����ɷ������
		try {
			return innerTransform(hsData, ctx);
		} catch (EASBizException e) {
			throw new TaskExternalException(e.getMessage(), e);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
	}
	public Hashtable exportTransmit(IRowSet rs, Context ctx) throws TaskExternalException{
		Hashtable record = new Hashtable();
		try {
			
			RowSetMetaData metas = rs.getRowSetMetaData();
			for(int i=1;i<=metas.getColumnCount();i++){
				
				if(!metas.getColumnName(i).equals("id")
						//&&!metas.getColumnName(i).equals("FAmount")
						//&&!metas.getColumnName(i).equals("FBookedDate")
						){
					
					if(metas.getColumnName(i)!=null&&rs.getObject(i)!=null)
					{	
						String value = String.valueOf(rs.getObject(i));
						if(rs.getObject(i) instanceof BigDecimal){
							BigDecimal v = (BigDecimal)rs.getObject(i);
							if(v.compareTo(FDCHelper.ZERO)==0)
								value = "0";
						}
						
						if(metas.getColumnName(i).endsWith("_longNumber")){
							record.put(metas.getColumnName(i),value.replace('!','.'));
						}
						else if(metas.getColumnName(i).equals("FEntrys_detail"))
							record.put("FEntrys_detailNumber",value);
						else
							record.put(metas.getColumnName(i),value);
					}
					
				}
			}
		} catch (SQLException e) {
			// TODO �Զ����� catch ��
			throw new TaskExternalException(e.getMessage(), e);
		}
		return record;
	}
	
	public boolean isSameBlock(Hashtable firstData, Hashtable currentData) {
		if (firstData == null || currentData == null) {
			return false;
		}

		DataToken firstNumber = (DataToken) firstData.get(getMainField());
		DataToken currentNumber = (DataToken) currentData.get(getMainField());

		if (firstNumber != null
				&& (currentNumber.data == null || currentNumber.data.toString().length() == 0 || firstNumber.data
						.equals(currentNumber.data))) {
			return true;
		}

		return false;
	}

}
