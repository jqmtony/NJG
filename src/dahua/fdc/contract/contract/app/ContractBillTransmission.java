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
	// 日期型属性名
	
	LandDeveloperInfo ldInfo = null;
	ContractBillInfo contractInfo = null;
	CurProjectInfo curProject = null;
	ContractDetailDefCollection conDetailDefs = null;
	HashMap conDetailDefMap = null;
	String mainName = "";

	String isLonelyCal = "是否单独计算";
	String amountWithOutCost = "不计成本的金额";
	String mainContractNumber = "对应主合同编码";
	String mainContractName = "对应主合同名称";
	/** 合同详细信息表列 － 详细信息 */
	public final static String CON_DETAIL_DETAIL_COL = "detail";

	/** 合同详细信息表列 － 内容 */
	public final static String CON_DETIAL_CONTENT_COL = "content";

	/** 合同详细信息表列 - 描述 */
	public final static String CON_DETIAL_DESC_COL = "desc";

	/** 合同详细信息表列 － ID */
	public final static String CON_DETIAL_ID_COL = "id";

	/** 合同详细信息表列 - 行标识 */
	public final static String CON_DETIAL_ROWKEY_COL = "rowKey";

	/** 合同详细信息表列 - 数据类型 */
	public final static String CON_DETIAL_DATATYPE_COL = "dataType";
	
	/** 合同性质附加行 － 是否单独计算 */
	public final static String IS_LONELY_CAL_ROW = "lo";

	public final static String AMOUNT_WITHOUT_COST_ROW = "am";

	public final static String MAIN_CONTRACT_NUMBER_ROW = "nu";

	public final static String MAIN_CONTRACT_NAME_ROW = "na";
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		// TODO 自动生成方法存根
		try{	
			return ContractBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("", e);
		}
	}
	
	/**
	 * 根据number获取id，如果没有则返回null
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
	 * 合同导入Excel
	 * 组合合同表头信息
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
			throw new TaskExternalException( number.toString().trim() +  " 编码不能大于80！");
		}
		/***
		 * 验证成本中心是否录入
		 */
		getCurProjectInfo(ctx,curProjectNumber);
		
		if (curProject != null && number != null && number.toString().trim().length() > 0){
			contractInfo = new ContractBillInfo();
			contractInfo.setNumber(number.toString().trim());
			contractInfo.setCurProject(curProject);
			String existId = getIdFromNumber(number.toString().trim(),ctx);
			// 是否覆盖相同记录。
			// 若不覆盖，且存在相同的number，则返回null。
			if (isSltImportUpdate() == false && !StringUtil.isEmptyString(existId)) {
				throw new TaskExternalException(number.toString().trim() + " 同工程项目下有相同的编码！");
			}
			
			// 若覆盖的单已提交，则不允许覆盖直接返回null
			if (isSltImportUpdate() && !StringUtil.isEmptyString(existId)){
				ContractBillInfo existInfo = null;
				try {
					existInfo = ((IContractBill)getController(ctx)).getContractBillInfo(new ObjectUuidPK(existId));
				} catch (Exception e) {
					throw new TaskExternalException("", e);
				}
				if (FDCBillStateEnum.SUBMITTED.equals(existInfo.getState())){
					throw new TaskExternalException(number.toString().trim() + " 合同已经是提交状态了");
				}
			}
			contractInfo.setOrgUnit(curProject.getFullOrgUnit());
		}else {
			/*
			 * 没有输入编码
			 */
			noNumber = true;
		}
		/***
		 * 检查合同类型
		 */
		//  TODO checkContractType()
		Object contractTypeNumber = ((DataToken) lineData.get("FContractType_longNumber")).data;
		if(contractTypeNumber==null || contractTypeNumber.toString().trim().length()==0){
			throw new TaskExternalException(number.toString().trim() + " 合同类型没有录入");
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longnumber",contractTypeNumber.toString().trim().replace('.','!')));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled","1"));
		view.setFilter(filter);
		view.getSelector().add("id");
		ContractTypeCollection contractTypes = ContractTypeFactory.getLocalInstance(ctx).getContractTypeCollection(view);
		if(contractTypes == null || contractTypes.size()==0)
			throw new TaskExternalException(number.toString().trim() + " 合同类型没有启用或长编码不正确");
		contractInfo.setContractType(contractTypes.get(0));
		
		contractInfo.setRespDept(contractInfo.getContractType().getDutyOrgUnit());
		
		Object name = ((DataToken) lineData.get("FName")).data;
		
		if(name != null && name.toString().length()>0)
		{	
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name",name.toString().trim()));
			filter.getFilterItems().add(new FilterItemInfo("curProject",curProject.getId().toString()));
			if(ContractBillFactory.getLocalInstance(ctx).exists(filter)){
				throw new TaskExternalException(number.toString().trim() + " 名称已经存在了");
			}
			else if(name.toString().length()>100){
				throw new TaskExternalException(number.toString().trim() + " 名称长度不能大于100");
			}
			else
				contractInfo.setName(name.toString());
		
		}
		else
			throw new TaskExternalException(number.toString().trim() + " 名称没有录入");
		
		/***
		 * 检查合同性质
		 * 枚举
		 */
		Object FContractPropert = ((DataToken)lineData.get("FContractPropert")).data;
		if(FContractPropert != null && FContractPropert.toString().trim().length()>0
				&&ContractPropertyEnum.getEnum(FContractPropert.toString().trim())!=null){
			contractInfo.setContractPropert(ContractPropertyEnum.getEnum(FContractPropert.toString().trim()));			
		}
		else{
			throw new TaskExternalException(number.toString().trim() + " 合同性质录入不正确");
		}
		
		/***
		 * 检查甲方
		 */
		Object FLandDeveloper_number = ((DataToken) lineData.get("FLandDeveloper_number")).data;
		checkLandDeveloper(ctx,FLandDeveloper_number);
		
		/****
		 * 检查乙方
		 */
		//  checkPartB()
		boolean isThree = contractInfo.getContractPropert().equals(ContractPropertyEnum.THREE_PARTY);
		Object FPartB_number = ((DataToken) lineData.get("FPartB_number")).data;
		contractInfo.setPartB(checkSupplier(ctx,FPartB_number,"乙方",!isThree));
		
		/***
		 * 检查丙方
		 */
		// checkPartC()
		Object FPartC_number = ((DataToken) lineData.get("FPartC_number")).data;
		contractInfo.setPartC(checkSupplier(ctx,FPartC_number,"丙方",!isThree));
		
		
		
				
		/***
		 * 币别
		 */
		Object FCurrency_number = ((DataToken) lineData.get("FCurrency_number")).data;
		view = this.getFilter(FCurrency_number.toString().trim());
		CurrencyCollection currencys = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(view);
		if(currencys == null || currencys.size()==0)
			throw new TaskExternalException(number.toString().trim() + " 币别录入不正确");
		contractInfo.setCurrency(currencys.get(0));
		
		
		// setExRate()
		
		Object FExRate = ((DataToken) lineData.get("FExRate")).data;
		contractInfo.setExRate(checkBigDecimalInfo(FExRate,number.toString(),"汇率",false));
		
		
		
		/*********
		 * 检测日期型字段格式并填充对象
		 */ 
		Object FSignDate = ((DataToken) lineData.get("FSignDate")).data;
		contractInfo.setSignDate(checkDateFormat(FSignDate,number.toString(),"签约日期",true));
		Object FBookedDate = ((DataToken) lineData.get("FBookedDate")).data;
		contractInfo.setBookedDate(checkDateFormat(FBookedDate,number.toString(),"业务记录日期",true));
		
		/***
		 * 检查责任部门
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
				throw new TaskExternalException(number.toString().trim() + " 责任部门录入不正确");
			}
		}
		
		
		
		//setOriginalAmount 原币
		Object FOriginalAmount = ((DataToken) lineData.get("FOriginalAmount")).data;
		contractInfo.setOriginalAmount(checkBigDecimalInfo(FOriginalAmount,number.toString(),"原币金额",false));
		
		//setAmount 本币
		Object FAmount = ((DataToken) lineData.get("FAmount")).data;
		contractInfo.setAmount(checkBigDecimalInfo(FAmount,number.toString(),"本币金额",false));
		
		/****
		 * 检查责任人
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
				throw new TaskExternalException(number.toString().trim() + " 责任人录入不正确");
			}
		}
		
		
		
		//setGrtRate 保修金比例
		Object FGrtRate = ((DataToken) lineData.get("FGrtRate")).data;
		contractInfo.setGrtRate(checkBigDecimalRate(FGrtRate,number.toString(),"保修金比例",true));
		
		
		/***
		 * 检查形成方式
		 * 枚举
		 */
		// TODO checkContractSource()
		Object FContractSource = ((DataToken)lineData.get("FContractSource")).data;
		if(FContractSource == null || FContractSource.toString().trim().length() == 0)
			throw new TaskExternalException(number.toString().trim() + " 形成方式录入不正确");
		try{
			String contractSourceNumber=FContractSource.toString().trim();
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number",contractSourceNumber));
			view.setFilter(filter);
			ContractSourceCollection contractSource = ContractSourceFactory.getLocalInstance(ctx).getContractSourceCollection(view);
			if(contractSource==null||contractSource.size()==0){
				throw new TaskExternalException(number.toString().trim() + " 形成方式录入不正确");
			}else{
				contractInfo.setContractSourceId(contractSource.get(0));
			}
		}
		catch(Exception e){
			throw new TaskExternalException(number.toString().trim() + " 形成方式录入不正确");
		}
		
		//setPayPercForWarn 付款提示比例
		Object FPayPercForWarn = ((DataToken) lineData.get("FPayPercForWarn")).data;
		contractInfo.setPayPercForWarn(checkBigDecimalRate(FPayPercForWarn,number.toString(),"付款提示比例",true));
		
				
		/***
		 * 造价性质
		 * 枚举
		 */
		//checkCostProperty 
		Object FCostProperty = ((DataToken)lineData.get("FCostProperty")).data;
		if(FCostProperty == null || FCostProperty.toString().trim().length() == 0)
			throw new TaskExternalException(number.toString().trim() + " 造价性质录入不正确");
		try{
			CostPropertyEnum costPro=CostPropertyEnum.getEnum(FCostProperty.toString().trim());
			if(costPro==null){
				throw new TaskExternalException(number.toString().trim() + " 造价性质录入不正确");
			}else{
				contractInfo.setCostProperty(costPro);
			}
		}
		catch(Exception e){
			throw new TaskExternalException(number.toString().trim() + " 造价性质录入不正确");
		}
		
		
		//setChgPercForWarn 变更提示比例
		Object FChgPercForWarn = ((DataToken) lineData.get("FChgPercForWarn")).data;
		contractInfo.setChgPercForWarn(checkBigDecimalRate(FChgPercForWarn,number.toString(),"变更提示比例",true));
		
		//isAmtWithoutCost 是否进入动态成本
		Object FIsCoseSplit = ((DataToken) lineData.get("FIsCoseSplit")).data;
		if(FIsCoseSplit == null || FIsCoseSplit.toString().trim().length() == 0)
			throw new TaskExternalException(number.toString().trim() + " 是否进入动态成本录入不正确");
		
		if(FIsCoseSplit.toString().trim().equals("true"))			
			contractInfo.setIsCoseSplit(true);
		else if(FIsCoseSplit.toString().trim().equals("false"))
			contractInfo.setIsCoseSplit(false);
		else
			throw new TaskExternalException(number.toString().trim() + " 是否进入动态成本录入不正确");
		
		
		//setPayScale //进度款付款比例
		
		Object FPayScale = ((DataToken) lineData.get("FPayScale")).data;
		contractInfo.setPayScale(checkBigDecimalRate(FPayScale,number.toString(),"进度款付款比例",true));
				
		//stampTaxRate印花税率
		Object FStampTaxRate = ((DataToken) lineData.get("FStampTaxRate")).data;
		contractInfo.setStampTaxRate(checkBigDecimalRate(FStampTaxRate,number.toString(),"进度款付款比例",true));
		//stampTaxAmt印花税金额
		Object FStampTaxAmt = ((DataToken) lineData.get("FStampTaxAmt")).data;
		contractInfo.setStampTaxAmt(checkBigDecimalInfo(FStampTaxAmt,number.toString(),"印花税金额",true));
		//remark
		
		
		/****
		 * 形成方式-招标
		 */
		
			// lowestPrice lowestPriceUnit
		Object FLowestPrice = ((DataToken) lineData.get("FLowestPrice")).data;
		contractInfo.setLowestPrice(checkBigDecimalInfo(FLowestPrice,number.toString(),"最低报价",true));
		Object FLowestPriceUnit_number = ((DataToken)lineData.get("FLowestPriceUnit_number")).data;
		contractInfo.setLowestPriceUnit(checkSupplier(ctx,FLowestPriceUnit_number,"最低报价单位",true));
			// lowerPrice lowerPriceUnit
		Object FLowerPrice = ((DataToken) lineData.get("FLowerPrice")).data;
		contractInfo.setLowerPrice(checkBigDecimalInfo(FLowerPrice,number.toString(),"次低报价",true));
		Object FLowerPriceUnit_number = ((DataToken)lineData.get("FLowerPriceUnit_number")).data;
		contractInfo.setLowerPriceUnit(checkSupplier(ctx,FLowerPriceUnit_number,"次低报价单位",true));
			// middlePrice middlePriceUnit
		Object FMiddlePrice = ((DataToken) lineData.get("FMiddlePrice")).data;
		contractInfo.setMiddlePrice(checkBigDecimalInfo(FMiddlePrice,number.toString(),"中间报价",true));
		Object FMiddlePriceUnit_number = ((DataToken)lineData.get("FMiddlePriceUnit_number")).data;
		contractInfo.setMiddlePriceUnit(checkSupplier(ctx,FMiddlePriceUnit_number,"中间报价单位",true));
			// higherPrice higherPriceUnit
		Object FHigherPrice = ((DataToken) lineData.get("FHigherPrice")).data;
		contractInfo.setHigherPrice(checkBigDecimalInfo(FHigherPrice,number.toString(),"次高报价",true));
		Object FHigherPriceUnit_number = ((DataToken)lineData.get("FHigherPriceUnit_number")).data;
		contractInfo.setHigherPriceUnit(checkSupplier(ctx,FHigherPriceUnit_number,"次高报价单位",true));
			// highestPrice highestPriceUni
		Object FHighestPrice = ((DataToken) lineData.get("FHighestPrice")).data;
		contractInfo.setHighestPrice(checkBigDecimalInfo(FHighestPrice,number.toString(),"最高报价",true));
		Object FHighestPriceUni_number = ((DataToken)lineData.get("FHighestPriceUni_number")).data;
		contractInfo.setHighestPriceUni(checkSupplier(ctx,FHighestPriceUni_number,"最高报价单位",true));
			// winPrice winUnit
		Object FWinPrice = ((DataToken) lineData.get("FWinPrice")).data;
		contractInfo.setWinPrice(checkBigDecimalInfo(FWinPrice,number.toString(),"中标报价",true));
		Object FWinUnit_number = ((DataToken)lineData.get("FWinUnit_number")).data;
		contractInfo.setWinUnit(checkSupplier(ctx,FWinUnit_number,"中标单位",true));
		
			// quantity
		Object FQuantity = ((DataToken) lineData.get("FQuantity")).data;
		contractInfo.setQuantity(checkBigDecimalInfo(FQuantity,number.toString(),"单位数量",true));
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
				throw new TaskExternalException(number.toString().trim() + " 招标类型录入不正确");
			}
			
		}
		
			// fileNo
		contractInfo.setFileNo((((DataToken) lineData.get("FFileNo")).data).toString());
		
			// secondPrice
		Object FSecondPrice = ((DataToken) lineData.get("FSecondPrice")).data;
		contractInfo.setSecondPrice(checkBigDecimalInfo(FSecondPrice,number.toString(),"二标价",true));
			// basePrice
		Object FBasePrice = ((DataToken) lineData.get("FBasePrice")).data;
		contractInfo.setBasePrice(checkBigDecimalInfo(FBasePrice,number.toString(),"底价",true));
			//
		
			// coopLevel enum
		Object FCoopLevel = ((DataToken)lineData.get("FCoopLevel")).data;
		if(FCoopLevel != null && FCoopLevel.toString().trim().length() > 0)
		{
			try{
				CoopLevelEnum costPro=CoopLevelEnum.getEnum(FCoopLevel.toString().trim());
				if(costPro==null){
					throw new TaskExternalException(number.toString().trim() + " 战略合作级别录入不正确");
				}else{
					contractInfo.setCoopLevel(costPro);
				}
			}
			catch(Exception e){
				throw new TaskExternalException(number.toString().trim() + " 战略合作级别录入不正确");
			}
		}
			//priceType enum
		
		Object FPriceType = ((DataToken)lineData.get("FPriceType")).data;
		if(FPriceType != null && FPriceType.toString().trim().length() > 0){
			try{
				PriceTypeEnum costPro=PriceTypeEnum.getEnum(FPriceType.toString().trim());
				if(costPro==null){
					throw new TaskExternalException(number.toString().trim() + " 计价方式录入不正确");
				}else{
					contractInfo.setPriceType(costPro);
				}
			}
			catch(Exception e){
				throw new TaskExternalException(number.toString().trim() + " 计价方式录入不正确");
			}
		}
		
		
		/***
		 * 设置来源方式，系统导入
		 * sourceType=0
		 */  
		contractInfo.setSourceType(SourceTypeEnum.IMP);
		
//		if (noNumber) {// 导入时无编码要重新生成
//			String orgID = orgUnit.getId().toString().trim();
//			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
//			if (iCodingRuleManager.isExist(contractInfo, orgID)) {// 设置过编码规则
//				contractInfo.setNumber(iCodingRuleManager.getNumber(contractInfo, orgID, ""));
//			} else {// 抛出异常提示未设置过编码规则。
//				IMetaDataLoader imeataLoader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
//				EntityObjectInfo entityObjectInfo = imeataLoader.getEntity(orgUnit.getBOSType());
//				String[] params = new String[1];
//				params[0] = entityObjectInfo.getAlias();
//				throw new ContractException(ContractException.NOCODEINGRULE, params);
//			}
//		}
		// 填充单头其他对象
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
					throw new TaskExternalException(number.toString().trim() + " "+ msg +"录入不你能大于100");
				}
				return amount;
			}
			catch(Exception e){
				throw new TaskExternalException(number.toString().trim() + " "+ msg +"录入不正确");
			}
		}
		else if(canNull){
			return FDCHelper._ONE;
		}
		else
			throw new TaskExternalException(number.toString().trim() + " "+ msg +"录入不正确");
		
	}
	public BigDecimal checkBigDecimalInfo(Object ldData,String number,String msg,boolean canNull) throws TaskExternalException{
		if(ldData != null && ldData.toString().trim().length() > 0){
			try{
				BigDecimal amount = new BigDecimal(ldData.toString().trim());
				return amount;
			}
			catch(Exception e){
				throw new TaskExternalException(number.toString().trim() + " "+ msg +"录入不正确");
			}
		}
		else if(canNull){
			return FDCHelper._ONE;
		}
		else
			throw new TaskExternalException(number.toString().trim() + " "+ msg +"录入不正确");
		
	}
	/****
	 * 获取工程项目信息
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
					throw new TaskExternalException(curProjectNumber.toString().trim() + " 工程项目非明细节点");
				}
				else
					curProject = collection.get(0);
			} else {
				// 工程项目不存在
				curProject = null;
				throw new TaskExternalException(curProjectNumber.toString().trim() + " 工程项目不存在");
			}
		} else {
			curProject = null;
			throw new TaskExternalException("工程项目不存在没有录入");
		}
	}
	
	/****
	 * 检查日期格式的数据是否正确
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
				// :格式有误，必须为日期格式（yyyy-mm-dd），当前格式为:
				throw new TaskExternalException(number +  msg + " 必须为日期格式 yyyy-mm-dd");
			}			
		}else if(canNull){
			return null;
		}else{
			throw new TaskExternalException(number + msg + "必须录入");
		}
		
	}
	/*****
	 * 检查供应商字段是否录入正确
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
					throw new TaskExternalException(msg+"列编码为"+ldData.toString()+"的供应商不存在");
				}
			}
			else if(canNull){
				return null;
			}
			else
				throw new TaskExternalException(msg+"列编码为"+ldData.toString()+"的供应商必须录入");
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
	}
	/************
	 * 检查甲方编码是否录入及正确
	 * @param ctx
	 * @param ldData
	 * @throws TaskExternalException
	 */
	private void checkLandDeveloper(Context ctx,Object ldData) throws TaskExternalException{
		//  检测甲方
		try {
			// 甲方 landDeveloper
			if (ldData != null && ldData.toString().trim().length() > 0) {
				ILandDeveloper iLD = LandDeveloperFactory.getLocalInstance(ctx);
				LandDeveloperCollection collection = iLD.getLandDeveloperCollection(getFilter(ldData.toString()
						.trim()));
				if (collection != null && collection.size() > 0) {
					contractInfo.setLandDeveloper(collection.get(0));					
				} else {
					// 甲方不存在
					throw new TaskExternalException(ldData.toString().trim() + " 甲方不存在");
				}
			} else {
				throw new TaskExternalException("没有录入甲方的值");
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
	}
	/**************
	 * 获取编码类型的EntityViewInfo
	 */	 
	private EntityViewInfo getFilter(String number) {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		viewInfo.setFilter(filter);
		return viewInfo;
	}
	/***
	 * 合同导入Excel
	 * 组合合同分录信息
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
			 * 是否单独计算
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
			 * 不计入成本的金额
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
							throw new TaskExternalException(contractInfo.getNumber()+"分录不计成本的金额录入不正确");
						}
						
					}
					if(FEntrys_desc != null)
						entryInfo.setDesc(FEntrys_desc.toString());
				}
			}
			/*****
			 * 对应主合同的编码
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
			 * 对应主合同的名称
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
	 * 合同导入Excel的具体实现
	 * @param hsData
	 * @param ctx
	 * @return
	 * @throws EASBizException
	 * @throws TaskExternalException 
	 * @throws BOSException 
	 */
	protected CoreBaseInfo innerTransform(Hashtable hsData, Context ctx) throws EASBizException, TaskExternalException, BOSException{
		
		contractInfo = null;
		// 游标，表示目前iterator所指的位置
		try{
			for (int i = 0; i < hsData.size(); i++){
				Hashtable lineData = (Hashtable) hsData.get(new Integer(i));
				// 当游标为0，表示第一条记录，此时要拼凑一个表头来
				if (i == 0){
					contractInfo = transmitHead(lineData, ctx);
				}
				if(contractInfo==null){
					return null;
				}
				// 更新分录
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
		// TODO 自动生成方法存根
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
			// TODO 自动生成 catch 块
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
