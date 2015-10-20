/*
 * @(#)ContractChangeBillContants.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.contract.client;

/**
 * Description:�������뵥������
 *
 * @author sxhong  		Date 2006-8-29
 * 
 */
public final class PayRequestBillContants {

	/*
	 * 
	 * ListUI �������뵥���� 
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
    
//    	EditUI��¼��Ҫ�󶨵Ķ���ĳ�����
    public static final String CONTRACTNAME="contractName";
    public static final String CONTRACTPRICE="contractPrice"; //��ͬ��۱���
    public static final String CONTRACTORGPRICE="contractOrgPrice"; //ԭ��
    
    public static final String IMAGESCHEDULE="imageSchedule"; //�������
    public static final String SCHEDULEAMT="scheduleAmt"; //���ȿ�
    
    public static final String LATESTPRICE="latestPrice"; //������۱���
    public static final String LATESTORGPRICE="latestOrgPrice"; //�������ԭ��
    
    public static final String CHANGEAMT="changeAmt"; //������
    public static final String CHANGEORGAMT="changeOrgAmt"; //������ԭ��
    
    public static final String PAYTIMES="payTimes"; //�������
    
    public static final String SETTLEAMT="settleAmt"; //���㱾��
    public static final String SETTLEORGAMT="settleOrgAmt"; //����ԭ��
    
    public static final String URGENTDEGREE="urgentDegree"; 
    public static final String CAPITALAMOUNT="capitalAmount";
    
    public static final String PAYEDAMT="payedAmt"; //�����뵥�Ѹ�����
    public static final String PAYEDORGAMT="payedOrgAmt"; //�����뵥�Ѹ�ԭ��
    
    public static final String CURPAID="curPaid"; //ʵ���������ԭ��
    public static final String CURPAIDLOCAL="curpaidlocal"; //ʵ��������뱾��
    
    public static final String ADDPRJALLREQAMT="addPrjAllReqAmt"; //���ӹ��̿���أ������ۼ�����
    
    /**
     * �׹��ı��η���
     */
    public static final String PAYPARTAMATLAMT="payPartAMatlAmt"; //�������뱾��
    public static final String PAYPARTAMATLAMTORI="payPartAMatlOriAmt"; //��������ԭ��
    public static final String PAYPARTAMATLALLREQAMT="payPartAMatlAllReqAmt"; //�׹��Ľ�ֹ�����ۼ�����
    public static final String PAYPARTAMATLALLREQORGAMT="payPartAMatlAllReqOrgAmt"; //�׹��Ľ�ֹ�����ۼ�����ԭ��
    public static final String PAYPARTAMATLALLPAIDAMT="payPartAMatlAllPaidAmt"; //�׹��Ľ�ֹ�����ۼ�ʵ��
    public static final String LSTAMATLALLREQAMT="lstAMatlAllReqAmt"; //�׹��������ۼ�����
    public static final String LSTAMATLALLPAIDAMT="LstAMatlAllPaidAmt"; //�׹��������ۼ�ʵ��
    
    /**
     * ��ͬ�ڹ��̿�
     */
    public static final String LSTPRJALLREQAMT="lstPrjAllReqAmt"; //��ͬ�ڹ��̿������ۼ�����
    public static final String LSTPRJALLREQORGAMT="lstPrjAllReqOrgAmt"; //��ͬ�ڹ��̿������ۼ�����ԭ��
    public static final String LSTPRJALLPAIDAMT="LstPrjAllPaidAmt"; //��ͬ�ڹ��̿������ۼ�ʵ��
    public static final String LSTPRJALLPAIDORGAMT="LstPrjAllPaidOrgAmt"; //��ͬ�ڹ��̿������ۼ�ʵ��ԭ��
    public static final String PRJALLREQAMT="prjAllReqAmt"; //��ͬ�ڹ��̿� ���������ۼ����루���ң�
    public static final String PRJALLREQORGAMT="prjAllReqOrgAmt"; //��ͬ�ڹ��̿� ���������ۼ����루ԭ�ң�
    public static final String PROJECTPRICEINCONTRACT="projectPriceInContract"; // �������루���ң���ͬ�ڹ��̿�
    public static final String PROJECTPRICEINCONTRACTORI="projectPriceInContractOri"; // �������루ԭ�ң���ͬ�ڹ��̿�

    /**
     * ���ӹ��̿�
     */
    public static final String LSTADDPRJALLREQAMT="lstAddPrjAllReqAmt"; //�����ۼ�����
    public static final String LSTADDPRJALLPAIDAMT="LstAddPrjAllPaidAmt"; //���ӹ��̿������ۼ�ʵ��
    public static final String ADDPROJECTAMT="addProjectAmt"; //���ӹ��̿��
    public static final String ADDPROJECTAMTORI="addProjectOriAmt"; //���ӹ��̿�ԭ��

    /**
     * Ԥ����
     */
	public static final String LSTADVANCEALLPAID = "lstAdvanceAllPaid"; //Ԥ������������ۼ�ʵ��
	public static final String LSTADVANCEALLREQ = "lstAdvanceAllReq"; //Ԥ����-���������ۼ�����
	public static final String ADVANCE = "advance"; //Ԥ����-ԭ��
	public static final String LOCALADVANCE = "locAdvance"; //Ԥ����-����
	public static final String ADVANCEALLREQ = "advanceAllReq"; //Ԥ����-���������ۼ�����
	public static final String ADVANCEALLPAID = "advanceAllPaid"; //Ԥ����-���������ۼ�ʵ��
	
    /** ���ڼƻ�����*/
    public static final String CURPLANNEDPAYMENT="curPlannedPayment";
    /** ����Ƿ����*/
    public static final String CURBACKPAY="curBackPay";
    /** �ƻ�����*/
    public static final String PAYMENTPLAN="paymentPlan";
    /** ��������%*/
    public static final String CURREQPERCENT="curReqPercent";
    /** �ۼ�����%*/
    public static final String ALLREQPERCENT="allReqPercent";
    /** ��ǰ��Ŀ*/
    public static final String CURPROJECT="curProject";


    

}
