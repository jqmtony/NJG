/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * 出纳付款查询
 * 因为license原因，有的客户没有购买财务出纳模块，不能直接使用财务出纳的付款查询，改用直接继承，但不能改逻辑
 * @author owen_wen 2013-08-01
 */
public class FDCCasPaymentBillListUI extends AbstractFDCCasPaymentBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCCasPaymentBillListUI.class);
    
    public FDCCasPaymentBillListUI() throws Exception {
		super();
	}

}