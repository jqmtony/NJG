/*
 * @(#)ContractChangeBillContants.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.contract.client;

/**
 * Description:付款申请单常量类
 *
 * @author sxhong  		Date 2006-8-29
 * 
 */
public final class PayRequestBillContants {

	/*
	 * 
	 * ListUI 付款申请单列名 
	 */
	public static final String COL_ID = "id";
	public static final String COL_STATE = "state";
    public static final String COL_NUMBER = "number";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_ORIGINALAMOUNT = "originalAmount";
    public static final String COL_ACTPAYLOCAMOUNT = "actPaidLocAmount";
    public static final String COL_ACTPAYAMOUNT = "actPaidAmount";
    public static final String COL_PAYDATE = "payDate";
    public static final String COL_SUPPLIER = "supplier.name";
    public static final String COL_CREATER = "creator.name";
    public static final String COL_CREATETIME = "createTime";
    public static final String COL_MONEYDESC = "moneyDesc";
    public static final String COL_RECBANK = "recBank";
    public static final String COL_RECACCOUNT = "recAccount";
    public static final String COL_AUDITOR = "auditor.name";
    public static final String COL_AUDITDATE = "auditTime";
    public static final String COL_DESC = "description";
    public static final String COL_ATTACHMENT = "attachment";
    public static final String COL_ISRESPITE = "isRespite";
    
//    	EditUI分录内要绑定的对象的常量名
    public static final String CONTRACTNAME="contractName";
    public static final String CONTRACTPRICE="contractPrice"; //合同造价本币
    public static final String CONTRACTORGPRICE="contractOrgPrice"; //原币
    
    public static final String IMAGESCHEDULE="imageSchedule"; //形象进度
    public static final String SCHEDULEAMT="scheduleAmt"; //进度款
    
    public static final String LATESTPRICE="latestPrice"; //最新造价本币
    public static final String LATESTORGPRICE="latestOrgPrice"; //最新造价原币
    
    public static final String CHANGEAMT="changeAmt"; //变更金额
    public static final String CHANGEORGAMT="changeOrgAmt"; //变更金额原币
    
    public static final String PAYTIMES="payTimes"; //付款次数
    
    public static final String SETTLEAMT="settleAmt"; //结算本币
    public static final String SETTLEORGAMT="settleOrgAmt"; //结算原币
    
    public static final String URGENTDEGREE="urgentDegree"; 
    public static final String CAPITALAMOUNT="capitalAmount";
    
    public static final String PAYEDAMT="payedAmt"; //本申请单已付本币
    public static final String PAYEDORGAMT="payedOrgAmt"; //本申请单已付原币
    
    public static final String CURPAID="curPaid"; //实付款本次申请原币
    public static final String CURPAIDLOCAL="curpaidlocal"; //实付款本次申请本币
    
    public static final String ADDPRJALLREQAMT="addPrjAllReqAmt"; //增加工程款（隐藏）本期累计申请
    
    /**
     * 甲供材本次发生
     */
    public static final String PAYPARTAMATLAMT="payPartAMatlAmt"; //本次申请本币
    public static final String PAYPARTAMATLAMTORI="payPartAMatlOriAmt"; //本次申请原币
    public static final String PAYPARTAMATLALLREQAMT="payPartAMatlAllReqAmt"; //甲供材截止本期累计申请
    public static final String PAYPARTAMATLALLREQORGAMT="payPartAMatlAllReqOrgAmt"; //甲供材截止本期累计申请原币
    public static final String PAYPARTAMATLALLPAIDAMT="payPartAMatlAllPaidAmt"; //甲供材截止本期累计实付
    public static final String LSTAMATLALLREQAMT="lstAMatlAllReqAmt"; //甲供材上期累计申请
    public static final String LSTAMATLALLPAIDAMT="LstAMatlAllPaidAmt"; //甲供材上期累计实付
    
    /**
     * 合同内工程款
     */
    public static final String LSTPRJALLREQAMT="lstPrjAllReqAmt"; //合同内工程款上期累计申请
    public static final String LSTPRJALLREQORGAMT="lstPrjAllReqOrgAmt"; //合同内工程款上期累计申请原币
    public static final String LSTPRJALLPAIDAMT="LstPrjAllPaidAmt"; //合同内工程款上期累计实付
    public static final String LSTPRJALLPAIDORGAMT="LstPrjAllPaidOrgAmt"; //合同内工程款上期累计实付原币
    public static final String PRJALLREQAMT="prjAllReqAmt"; //合同内工程款 截至本期累计申请（本币）
    public static final String PRJALLREQORGAMT="prjAllReqOrgAmt"; //合同内工程款 截至本期累计申请（原币）
    public static final String PROJECTPRICEINCONTRACT="projectPriceInContract"; // 本次申请（本币）合同内工程款
    public static final String PROJECTPRICEINCONTRACTORI="projectPriceInContractOri"; // 本次申请（原币）合同内工程款

    /**
     * 增加工程款
     */
    public static final String LSTADDPRJALLREQAMT="lstAddPrjAllReqAmt"; //上期累计申请
    public static final String LSTADDPRJALLPAIDAMT="LstAddPrjAllPaidAmt"; //增加工程款上期累计实付
    public static final String ADDPROJECTAMT="addProjectAmt"; //增加工程款本币
    public static final String ADDPROJECTAMTORI="addProjectOriAmt"; //增加工程款原币

    /**
     * 预付款
     */
	public static final String LSTADVANCEALLPAID = "lstAdvanceAllPaid"; //预付款截至上期累计实付
	public static final String LSTADVANCEALLREQ = "lstAdvanceAllReq"; //预付款-截至上期累计申请
	public static final String ADVANCE = "advance"; //预付款-原币
	public static final String LOCALADVANCE = "locAdvance"; //预付款-本币
	public static final String ADVANCEALLREQ = "advanceAllReq"; //预付款-截至本期累计申请
	public static final String ADVANCEALLPAID = "advanceAllPaid"; //预付款-截至本期累计实付
	
    /** 本期计划付款*/
    public static final String CURPLANNEDPAYMENT="curPlannedPayment";
    /** 本期欠付款*/
    public static final String CURBACKPAY="curBackPay";
    /** 计划付款*/
    public static final String PAYMENTPLAN="paymentPlan";
    /** 本次申请%*/
    public static final String CURREQPERCENT="curReqPercent";
    /** 累计申请%*/
    public static final String ALLREQPERCENT="allReqPercent";
    /** 当前项目*/
    public static final String CURPROJECT="curProject";


    

}
