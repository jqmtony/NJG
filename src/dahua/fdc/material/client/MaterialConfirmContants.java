package com.kingdee.eas.fdc.material.client;

/**
 * 
 * 描述:材料确认单常量类 date:2008-08-06 <p>
 * @version EAS 6.0
 */
public class MaterialConfirmContants {
	
	//材料确认单表头列名
	/**材料确认单录入表头ID*/
	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String NUMBER = "number";
	public static final String SUPPLY_DATE = "supplyDate";
	
	public static final String MATERIAL_CON_NUMBER = "materialConNumber";
	public static final String MATERIAL_CON_NAME = "materialConName";
	public static final String MATERIAL_CON_PARTB = "materialConPartB";
	public static final String MATERIAL_CON_AMT = "materialConAmt";
	public static final String MATERIAL_CON_CURRENCY = "materialConCurrency";
	
	public static final String MAIN_CON_NUMBER = "mainConNumber";
	public static final String MAIN_CON_NAME = "mainConName";
	public static final String MAIN_CON_PARTB = "mainConPartB";
	/**材料确认单录入表头 摘要*/
	public static final String DESC = "desc";
	public static final String SUPPLY_AMT = "supplyAm";
	public static final String TODATE_SUPPLY_AMT = "toDateSupplyAmt";
	public static final String CONFIRM_AMT = "confirmAmt";
	public static final String TODATE_CONFIRM_AMT = "toDateConfirmAmt";
	public static final String PAID_AMT = "paidAmt";
	public static final String TODATE_PAID_AMT = "toDatePaidAmt";
	public static final String CREATOR = "creator";
	public static final String CREATE_TIME = "createTime";
	public static final String AUDITOR = "auditor";
	public static final String AUDIT_DATE = "auditDate";
	public static final String HAS_APPLIED = "hasApplied";
	
	//	材料确认单表体列名
	/**材料确认录入单分录 ID*/
	public static final String  ENTRY_ID= "id";
	public static final String  MATERIAL_NUMBER= "materialNumber";
	public static final String  MATERIAL_NAME= "materialName";
	public static final String  MODEL= "model";
	public static final String  UNIT= "unit";
	public static final String  ORIGINAL_PRICE= "originalPrice";
	public static final String  PRICE= "price";
	public static final String  QUANTITY= "quantity";
	public static final String  AMOUNT= "amount";
	/**材料确认录入单分录 确认原币单价*/
	public static final String  CONFIRM_ORIGINAL_PRICE= "confirmOriginalPrice";
	/**材料确认录入单分录 确认本币单价*/
	public static final String  CONFIRM_PRICE= "confirmPrice";
	/**材料确认录入单分录 确认金额=数量*确认本币单价*/
	public static final String  CONFIRM_AMOUNT= "confirmAmount";
	public static final String  CONFIRM_ORI_AMT= "confirmOriAmt";
	/**材料确认录入单分录 备注*/
	public static final String  ENTRY_DESC= "desc";
	public static final String  PARTA_MATERIAL_ENTRY_ID= "partAMaterialEntryId";
}
