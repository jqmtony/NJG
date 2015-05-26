package com.kingdee.eas.custom.richinf.client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.assistant.CostObjectGroupFactory;
import com.kingdee.eas.basedata.assistant.CostObjectGroupInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ICostObjectGroup;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.assistant.IMeasureUnit;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.ICSSPGroup;
import com.kingdee.eas.basedata.master.cssp.ICSSPGroupStandard;
import com.kingdee.eas.basedata.master.cssp.ICustomer;
import com.kingdee.eas.basedata.master.cssp.ISupplier;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.material.IMaterial;
import com.kingdee.eas.basedata.master.material.IMaterialGroup;
import com.kingdee.eas.basedata.master.material.IMaterialGroupStandard;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupCollection;
import com.kingdee.eas.basedata.master.material.MaterialGroupFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.basedata.master.material.MaterialGroupStandardFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupStandardInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.ICostCenterOrgUnit;
import com.kingdee.eas.basedata.org.ICtrlUnit;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.basedata.org.IPurchaseOrgUnit;
import com.kingdee.eas.basedata.org.ISaleOrgUnit;
import com.kingdee.eas.basedata.org.IStorageOrgUnit;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitCollection;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitCollection;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.basedata.org.StorageOrgUnitCollection;
import com.kingdee.eas.basedata.org.StorageOrgUnitFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.basedata.person.IPerson;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.scm.common.BillTypeFactory;
import com.kingdee.eas.basedata.scm.common.BillTypeInfo;
import com.kingdee.eas.basedata.scm.common.BizTypeFactory;
import com.kingdee.eas.basedata.scm.common.BizTypeInfo;
import com.kingdee.eas.basedata.scm.common.IBillType;
import com.kingdee.eas.basedata.scm.common.IBizType;
import com.kingdee.eas.basedata.scm.common.ITransactionType;
import com.kingdee.eas.basedata.scm.common.TransactionTypeFactory;
import com.kingdee.eas.basedata.scm.common.TransactionTypeInfo;
import com.kingdee.eas.basedata.scm.im.inv.IStoreState;
import com.kingdee.eas.basedata.scm.im.inv.IStoreType;
import com.kingdee.eas.basedata.scm.im.inv.IWarehouse;
import com.kingdee.eas.basedata.scm.im.inv.StoreStateFactory;
import com.kingdee.eas.basedata.scm.im.inv.StoreStateInfo;
import com.kingdee.eas.basedata.scm.im.inv.StoreTypeFactory;
import com.kingdee.eas.basedata.scm.im.inv.StoreTypeInfo;
import com.kingdee.eas.basedata.scm.im.inv.WarehouseFactory;
import com.kingdee.eas.basedata.scm.im.inv.WarehouseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.richbase.ExamTypeFactory;
import com.kingdee.eas.custom.richbase.ExamTypeInfo;
import com.kingdee.eas.custom.richbase.IExamType;
import com.kingdee.eas.custom.richbase.IReceType;
import com.kingdee.eas.custom.richbase.ISaleType;
import com.kingdee.eas.custom.richbase.ReceTypeFactory;
import com.kingdee.eas.custom.richbase.ReceTypeInfo;
import com.kingdee.eas.custom.richbase.SaleTypeFactory;
import com.kingdee.eas.custom.richbase.SaleTypeInfo;

public class Utils {
	/**
	 * String格式转换成Date日期 
	 * @param sdate 字符日期2013-08-16
	 * @param format yyyy-MM-dd
	 */
	public static Date parseCustomDateString(String sDate, String format)
    {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date d = null;
//		if(sDate != null && sDate.length() == format.length())
		try{
			d = dateFormat.parse(sDate);
        }catch(ParseException ex){
        	return null;
        }
		return d;
    }
	/**
	 * 获取当前一个月的第一天日期
	 * @return
	 */
	public static Date getFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	/**
	 * 获取当前一个月的最后一天日期
	 * @return
	 */
	public static Date getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	/**
	 * 根据分类编码获取供应商基本分类
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static CSSPGroupInfo getCSSPGroupInfo(Context ctx, String number) throws BOSException, EASBizException {
		ICSSPGroup iCssPGroup = null;
		CSSPGroupInfo cSSPGroupInfo = null;
		if(ctx != null)
			iCssPGroup = CSSPGroupFactory.getLocalInstance(ctx);
		else
			iCssPGroup = CSSPGroupFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		CSSPGroupCollection groupCollection = iCssPGroup.getCSSPGroupCollection(oql);
		if(groupCollection != null)
			cSSPGroupInfo = groupCollection.get(0);
		return cSSPGroupInfo;
	}
	/**
	 * 根据编码获取供应商分类标准
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static CSSPGroupStandardInfo getCSSPGroupStandardInfo(Context ctx, String number) throws BOSException, EASBizException {
		ICSSPGroupStandard iCSSPGroupStandard = null;
		CSSPGroupStandardInfo cSSPGroupStandardInfo = null;
		if(ctx != null)
			iCSSPGroupStandard = CSSPGroupStandardFactory.getLocalInstance(ctx);
		else
			iCSSPGroupStandard = CSSPGroupStandardFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		CSSPGroupStandardCollection groupStandardCollection = iCSSPGroupStandard.getCSSPGroupStandardCollection(oql);
		if(groupStandardCollection != null)
			cSSPGroupStandardInfo = groupStandardCollection.get(0);
		return cSSPGroupStandardInfo;
	}
	/**
	 * 根据编码获取财务组织
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static CompanyOrgUnitInfo getCompanyOrgUnit(Context ctx, String number) throws BOSException, EASBizException {
		ICompanyOrgUnit iCompanyOrgUnit = null;
		CompanyOrgUnitInfo CompanyOrgInfo = null;
		if(ctx != null)
			iCompanyOrgUnit = CompanyOrgUnitFactory.getLocalInstance(ctx);
		else
			iCompanyOrgUnit = CompanyOrgUnitFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		if(iCompanyOrgUnit.exists(oql))
			CompanyOrgInfo = iCompanyOrgUnit.getCompanyOrgUnitInfo(oql);
		return CompanyOrgInfo;
	}
	/**
	 * 根据编码获取管理单元
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static CtrlUnitInfo getCtrlUnitInfo(Context ctx, String number) throws BOSException, EASBizException {
		ICtrlUnit iCtrlUnit = null;
		CtrlUnitInfo ctrlUnitInfo = null;
		if(ctx != null) 
			iCtrlUnit = CtrlUnitFactory.getLocalInstance(ctx);
		else
			iCtrlUnit = CtrlUnitFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		if(iCtrlUnit.exists(oql))
			ctrlUnitInfo = iCtrlUnit.getCtrlUnitInfo(oql);
		return ctrlUnitInfo;
	}
	/**
	 * 根据编码获取采购组织
	 * @throws BOSException 
	 */
	public static PurchaseOrgUnitInfo getPurchaseOrgUnitInfo(Context ctx, String number) throws BOSException {
		IPurchaseOrgUnit iPurchaseOrgUnit = null;
		PurchaseOrgUnitInfo purchaseOrgUnitInfo = null;
		if(ctx != null) 
			iPurchaseOrgUnit = PurchaseOrgUnitFactory.getLocalInstance(ctx);
		else
			iPurchaseOrgUnit = PurchaseOrgUnitFactory.getRemoteInstance();
//		String oql = "where number='" + number + "'";
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		PurchaseOrgUnitCollection purchaseOrgUnitCollection = iPurchaseOrgUnit.getPurchaseOrgUnitCollection(evi);
		if(purchaseOrgUnitCollection != null)
			purchaseOrgUnitInfo = purchaseOrgUnitCollection.get(0);
		return purchaseOrgUnitInfo;
	}
	/**
	 * 根据编码获取组织单元
	 * @throws BOSException 
	 */
	public static FullOrgUnitInfo getOrgUnitInfo(Context ctx, String number) throws BOSException {
		IFullOrgUnit iFullOrgUnit = null;
		FullOrgUnitInfo orgUnitInfo = null;
		if(ctx != null)
			iFullOrgUnit = FullOrgUnitFactory.getLocalInstance(ctx);
		else
			iFullOrgUnit = FullOrgUnitFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		FullOrgUnitCollection fullOrgUnitCollection = iFullOrgUnit.getFullOrgUnitCollection(oql);
		if(fullOrgUnitCollection != null)
			orgUnitInfo = fullOrgUnitCollection.get(0);
		return orgUnitInfo;
	}
	/**
	 * 根据编码获取币别信息
	 * @throws BOSException 
	 */
	public static CurrencyInfo getCurrencyInfo(Context ctx, String number) throws BOSException {
		ICurrency iCurrency = null;
		CurrencyInfo currencyInfo = null;
		if(ctx != null)
			iCurrency = CurrencyFactory.getLocalInstance(ctx);
		else
			iCurrency = CurrencyFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		currencyInfo = iCurrency.getCurrencyCollection(oql).get(0);
		return currencyInfo;
	}
	
	/**
	 * 根据编码获取人员信息
	 * @throws BOSException 
	 */
	public static PersonInfo getPersonInfo(Context ctx, String number) throws BOSException {
		IPerson iPerson = null;
		PersonInfo personInfo = null;
		if(ctx != null)
			iPerson = PersonFactory.getLocalInstance(ctx);
		else
			iPerson = PersonFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		personInfo = iPerson.getPersonCollection(oql).get(0);
		return personInfo;
	}
	
	/**
	 * 根据编码获取体检类别
	 * @throws BOSException 
	 */
	public static ExamTypeInfo getExamTypeInfo(Context ctx, String number) throws BOSException {
		int index = number.indexOf("|");
		String basenumber = number.substring(0,index);
		String name = number.substring(index+1,number.length());
		IExamType iExamType = null;
		ExamTypeInfo examTypeInfo = null;
		if(ctx != null)
			iExamType = ExamTypeFactory.getLocalInstance(ctx);
		else
			iExamType = ExamTypeFactory.getRemoteInstance();
		String oql = "where number='" + basenumber + "'";
		examTypeInfo = iExamType.getExamTypeCollection(oql).get(0);
		if(examTypeInfo==null){
			examTypeInfo = new ExamTypeInfo();
			examTypeInfo.setNumber(basenumber);
			examTypeInfo.setName(name);
			try {
				iExamType.save(examTypeInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}
		return examTypeInfo;
	}
	
	/**
	 * 根据编码获取销售类别
	 * @throws BOSException 
	 */
	public static SaleTypeInfo getSaleTypeInfo(Context ctx, String number) throws BOSException {
		int index = number.indexOf("|");
		String basenumber = number.substring(0,index);
		String name = number.substring(index+1,number.length());
		ISaleType iSaleType = null;
		SaleTypeInfo saleTypeInfo = null;
		if(ctx != null)
			iSaleType = SaleTypeFactory.getLocalInstance(ctx);
		else
			iSaleType = SaleTypeFactory.getRemoteInstance();
		String oql = "where number='" + basenumber+ "'";
		saleTypeInfo = iSaleType.getSaleTypeCollection(oql).get(0);
		if(saleTypeInfo==null){
			saleTypeInfo = new SaleTypeInfo();
			saleTypeInfo.setNumber(basenumber);
			saleTypeInfo.setName(name);
			try {
				iSaleType.save(saleTypeInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}
		return saleTypeInfo;
	}
	
	/**
	 * 根据编码获取收款类别
	 * @throws BOSException 
	 */
	public static ReceTypeInfo getReceTypeInfo(Context ctx, String number) throws BOSException {
		int index = number.indexOf("|");
		String basenumber = number.substring(0,index);
		String name = number.substring(index+1,number.length());
		IReceType iReceType = null;
		ReceTypeInfo receTypeInfo = null;
		if(ctx != null)
			iReceType = ReceTypeFactory.getLocalInstance(ctx);
		else
			iReceType = ReceTypeFactory.getRemoteInstance();
		String oql = "where number='" + basenumber+ "'";
		receTypeInfo = iReceType.getReceTypeCollection(oql).get(0);
		if(receTypeInfo==null){
			receTypeInfo = new ReceTypeInfo();
			receTypeInfo.setNumber(basenumber);
			receTypeInfo.setName(name);
			try {
				iReceType.save(receTypeInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}
		return receTypeInfo;
	}
	
	/**
	 * 根据编码获取物料基本分类
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static MaterialGroupInfo getMaterialGroupInfo(Context ctx, String number) throws BOSException, EASBizException {
		IMaterialGroup iMaterialGroup = null;
		MaterialGroupInfo materialGroupInfo = null;
		if(ctx != null)
			iMaterialGroup = MaterialGroupFactory.getLocalInstance(ctx);
		else
			iMaterialGroup = MaterialGroupFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		
		if(iMaterialGroup.exists(filter)) {
			MaterialGroupCollection materialGroupCollection = iMaterialGroup.getMaterialGroupCollection(evi);
			if(materialGroupCollection != null)
				materialGroupInfo = materialGroupCollection.get(0);
		}
		return materialGroupInfo;
	}
	/**
	 * 根据编码获取物料分类标准
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static MaterialGroupStandardInfo getMaterialGroupStandardInfo(Context ctx, String number) throws EASBizException, BOSException {
		IMaterialGroupStandard iMaterialGroupStandard = null;
		MaterialGroupStandardInfo materialGroupStandardInfo = null;
		if(ctx != null)
			iMaterialGroupStandard = MaterialGroupStandardFactory.getLocalInstance(ctx);
		else
			iMaterialGroupStandard = MaterialGroupStandardFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		if(iMaterialGroupStandard.exists(oql))
			materialGroupStandardInfo = iMaterialGroupStandard.getMaterialGroupStandardInfo(oql);
		return materialGroupStandardInfo;
	}
	/**
	 * 根据名称获取计量单位信息
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static MeasureUnitInfo getMeasureUnitInfo(Context ctx, String name) throws BOSException, EASBizException {
		IMeasureUnit iMeasureUnit = null;
		MeasureUnitInfo measureUnitInfo = null;
		if(ctx != null)
			iMeasureUnit = MeasureUnitFactory.getLocalInstance(ctx);
		else
			iMeasureUnit = MeasureUnitFactory.getRemoteInstance();
		String oql = "where name='" + name + "'";
		if(iMeasureUnit.exists(oql)) {
			MeasureUnitCollection measureUnitCollection = iMeasureUnit.getMeasureUnitCollection(oql);
			if(measureUnitCollection != null)
				measureUnitInfo = measureUnitCollection.get(0);
		}
		return measureUnitInfo;
	}
	/**
	 * 根据编码获取成本对象分组
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static CostObjectGroupInfo getCostObjectGroupInfo(Context ctx, String number) throws BOSException, EASBizException {
		ICostObjectGroup iCostObjectGroup = null;
		CostObjectGroupInfo costObjectGroupInfo = null;
		if(ctx != null)
			iCostObjectGroup = CostObjectGroupFactory.getLocalInstance(ctx);
		else
			iCostObjectGroup = CostObjectGroupFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		if(iCostObjectGroup.exists(oql))
			costObjectGroupInfo = iCostObjectGroup.getCostObjectGroupInfo(oql);
		return costObjectGroupInfo;
	}
	/**
	 * 根据编码获取库存组织
	 * @throws BOSException 
	 */
	public static StorageOrgUnitInfo getStorageOrgUnitInfo(Context ctx, String number) throws BOSException {
		IStorageOrgUnit iStorageOrgUnit = null;
		StorageOrgUnitInfo StorageOrgUnitInfo = null;
		if(ctx != null) 
			iStorageOrgUnit = StorageOrgUnitFactory.getLocalInstance(ctx);
		else
			iStorageOrgUnit = StorageOrgUnitFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		StorageOrgUnitCollection storageOrgUnitCollection = iStorageOrgUnit.getStorageOrgUnitCollection(evi);
		if(storageOrgUnitCollection != null)
			StorageOrgUnitInfo = storageOrgUnitCollection.get(0);
		return StorageOrgUnitInfo;
	}
	/**
	 * 根据编码获取销售组织
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static SaleOrgUnitInfo getSaleOrgUnitInfo(Context ctx, String number) throws EASBizException, BOSException {
		ISaleOrgUnit iSaleOrgUnit = null;
		SaleOrgUnitInfo saleOrgUnitInfo = null;
		if(ctx != null)
			iSaleOrgUnit = SaleOrgUnitFactory.getLocalInstance(ctx);
		else
			iSaleOrgUnit = SaleOrgUnitFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		if(iSaleOrgUnit.exists(filter)) {
			SaleOrgUnitCollection saleOrgUnitCollection = iSaleOrgUnit.getSaleOrgUnitCollection(evi);
			saleOrgUnitInfo = saleOrgUnitCollection.get(0);
		}
		
		return saleOrgUnitInfo;
	}
	/**
	 * 根据编码获取销售出库单业务类别
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static BizTypeInfo getBizTypeInfo(Context ctx, String number) throws EASBizException, BOSException {
		IBizType iBizType = null;
		BizTypeInfo bizTypeInfo = null;
		if(ctx != null)
			iBizType = BizTypeFactory.getLocalInstance(ctx);
		else
			iBizType = BizTypeFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		if(iBizType.exists(oql))
			bizTypeInfo = iBizType.getBizTypeInfo(oql);
		
		return bizTypeInfo;
	}
	/**
	 * 编码获取销售出库单事务类型
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static TransactionTypeInfo getTransactionTypeInfo(Context ctx, String number) throws BOSException, EASBizException {
		ITransactionType iTransactionType = null;
		TransactionTypeInfo TransactionTypeInfo = null;
		if(ctx != null) 
			iTransactionType = TransactionTypeFactory.getLocalInstance(ctx);
		else
			iTransactionType = TransactionTypeFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		if(iTransactionType.exists(oql))
			TransactionTypeInfo = iTransactionType.getTransactionTypeInfo(oql);
		
		return TransactionTypeInfo;
	}
	/**
	 * 根据编码获取客户
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static CustomerInfo getCustomerInfo(Context ctx, String number) throws BOSException, EASBizException {
		ICustomer iCustomer = null;
		CustomerInfo CustomerInfo = null;
		if(ctx != null)
			iCustomer = CustomerFactory.getLocalInstance(ctx);
		else
			iCustomer = CustomerFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		if(iCustomer.exists(oql))
			CustomerInfo = iCustomer.getCustomerInfo(oql);
		return CustomerInfo;
	}
	/**
	 * 根据编码获取物料
	 * @throws BOSException 
	 */
	public static MaterialInfo getMaterialInfo(Context ctx, String number) throws BOSException {
		IMaterial iMaterial = null;
		MaterialInfo MaterialInfo = null;
		if(ctx != null)
			iMaterial = MaterialFactory.getLocalInstance(ctx);
		else
			iMaterial = MaterialFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		MaterialCollection materialCollection = iMaterial.getMaterialCollection(evi);
		if(materialCollection != null)
			MaterialInfo = materialCollection.get(0);
		return MaterialInfo;
	}
	/**
	 * 根据编码获取单据类型
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static BillTypeInfo getBillTypeInfo(Context ctx, String number) throws EASBizException, BOSException {
		IBillType iBillType = null;
		BillTypeInfo BillTypeInfo = null;
		if(ctx != null) 
			iBillType = BillTypeFactory.getLocalInstance(ctx);
		else
			iBillType = BillTypeFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		if(iBillType.exists(oql))
			BillTypeInfo = iBillType.getBillTypeInfo(oql);
		return BillTypeInfo;
	}
	/**
	 * 根据编码获取供应商
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static SupplierInfo getSupplierInfo(Context ctx, String number) throws EASBizException, BOSException {
		ISupplier iSupplier = null;
		SupplierInfo SupplierInfo = null;
		if(ctx!= null)
			iSupplier = SupplierFactory.getLocalInstance(ctx);
		else
			iSupplier = SupplierFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		if(iSupplier.exists(oql))
			SupplierInfo = iSupplier.getSupplierInfo(oql);
		return SupplierInfo;
	}
	/**
	 * 根据编码获取库存类型
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static StoreTypeInfo getStoreTypeInfo(Context ctx, String number) throws EASBizException, BOSException {
		IStoreType iStoreType = null;
		StoreTypeInfo StoreTypeInfo = null;
		if(ctx != null)
			iStoreType = StoreTypeFactory.getLocalInstance(ctx);
		else
			iStoreType = StoreTypeFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		if(iStoreType.exists(oql))
			StoreTypeInfo = iStoreType.getStoreTypeInfo(oql);
		return StoreTypeInfo;
	}
	/**
	 * 根据编码获取库存状态
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static StoreStateInfo getStoreStateInfo(Context ctx, String number) throws EASBizException, BOSException {
		IStoreState iStoreState = null;
		StoreStateInfo StoreStateInfo = null;
		if(ctx != null)
			iStoreState = StoreStateFactory.getLocalInstance(ctx);
		else
			iStoreState = StoreStateFactory.getRemoteInstance();
		String oql = "where number='" + number + "'";
		if(iStoreState.exists(oql))
			StoreStateInfo = iStoreState.getStoreStateInfo(oql);
		return StoreStateInfo;
	}
	/**
	 * 根据编码获取仓库
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static WarehouseInfo getWarehouseInfo(Context ctx, String number) throws BOSException, EASBizException {
		IWarehouse iWarehouse = null;
		WarehouseInfo WarehouseInfo = null;
		if(ctx != null)
			iWarehouse = WarehouseFactory.getLocalInstance(ctx);
		else
			iWarehouse = WarehouseFactory.getRemoteInstance();
		String oql = "where number='"+number+"'";
		if(iWarehouse.exists(oql))
			WarehouseInfo = iWarehouse.getWarehouseInfo(oql);
		return WarehouseInfo;
	}
	/**
	 * 根据编码获取成本中心
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static CostCenterOrgUnitInfo getCostCenterOrgUnit(Context ctx, String number) throws EASBizException, BOSException {
		ICostCenterOrgUnit ICostCenterOrgUnit = null;
		CostCenterOrgUnitInfo CostCenterOrgUnitInfo = null;
		if(ctx != null)
			ICostCenterOrgUnit = CostCenterOrgUnitFactory.getLocalInstance(ctx);
		else
			ICostCenterOrgUnit = CostCenterOrgUnitFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		if(ICostCenterOrgUnit.exists(filter)) {
			CostCenterOrgUnitCollection coll = ICostCenterOrgUnit.getCostCenterOrgUnitCollection(evi);
			CostCenterOrgUnitInfo = coll.get(0);
		}
		return 	CostCenterOrgUnitInfo;
	}
	/**
	 * 根据编码获取行政组织
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static AdminOrgUnitInfo getAdminOrgUnitInfo(Context ctx, String number) throws EASBizException, BOSException {
		IAdminOrgUnit IAdminOrgUnit = null;
		AdminOrgUnitInfo AdminOrgUnitInfo = null;
		if(ctx != null)
			IAdminOrgUnit = AdminOrgUnitFactory.getLocalInstance(ctx);
		else
			IAdminOrgUnit = AdminOrgUnitFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		if(IAdminOrgUnit.exists(filter)) {
			AdminOrgUnitCollection collection = IAdminOrgUnit.getAdminOrgUnitCollection(evi);
			AdminOrgUnitInfo = collection.get(0);
		}
		return AdminOrgUnitInfo;
	}
}
