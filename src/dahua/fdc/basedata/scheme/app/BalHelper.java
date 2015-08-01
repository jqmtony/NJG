/**
 * 
 */
package com.kingdee.eas.fdc.basedata.scheme.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @author jinxp
 *
 */
public class BalHelper {

    //�豸�ɱ��鼯��
    public static final String EquCostAggregate = "F3328B4C";
    //�豸ʹ�ü�¼��
    public static final String EQUUSERECORD = "AA5990B6";
    //�����豸ʹ�ý��㵥
    public static final String EQUUSINGSETTLE = "535400C1";
    
    //�����豸�������
    public static final String EQURENTSETTLE = "5F50EDD2";
	//ʹ����������豸���
    public static final String EQUUSECONTRUCTUNIT = "00BBCEC3";
	//���ֹ���
    public static final String EQUOPERATORWAGE = "1D8D9C5A";   
	//�豸ά�޽��㵥
    public static final String MAINTAINFEE = "A0C8FA48";  
	//ȼ��������ñ�
    public static final String FitFuelMatReqBill = "D3FBF186";  
	//��ѽ����
    public static final String ELECSETTLEBILL = "33A48E45";  
	//�˷ѽ��㵥
    public static final String EQUTRANSFERFEE = "C337A96D";  
	//�������ø��
    public static final String EQUOTHERFEEPAYBIL = "8CE6466B";  
	//��������:�������ʹ���豸�ۿ	
    public static final String EQUCONTRUCTUNITUSE = "DCCADB2F"; 
    
    //��е�豸ֱ�ӹ��̷ѳɱ���̯
    public static final String EquDirectCostApportion = "DA1BCD7C";
    
    //��е�豸ֱ�ӹ��̷ѳɱ���̯������
    public static final String EquDirectCostAdjust = "B65F216B";
    //�豸������㵥
    public static final String EQURENTACCCOUNTING = "1A8C839C";
  //�豸�۾ɽ��㵥
    public static final String EQUDEPRECIATIONSETTLE = "48F57326";
    
    
	//��ȡ����֯���ڼ�ĵ���
	public static IRowSet getBillCollection(Context ctx, String projectOrgId, String projectId, String curPeriodId,String billType) throws BOSException{
		String sql = null;
		//"Select Fid from "+table+" where FProjectOrgId=? and FProjectId=? and FPeriodID=? and FBillSate='03' ";
		
		IRowSet rs = null;
		Object[] params = new Object[]{projectOrgId,projectId,curPeriodId};
//		Object[] params = new Object[]{projectOrgId,curPeriodId};
		
		if(BalHelper.EQUUSERECORD.equals(billType.toString())){
			//�豸ʹ�õļ�¼������ʱ��
//			sql = "Select Fid from T_FDC_EquUseRecord where FProjectOrgId=?  and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquUseRecord where FProjectOrgId=?  and FProjectId =? and FPeriodID=? and FBillSate='03' ";
		}	
		
		else if(BalHelper.EQUUSINGSETTLE.equals(billType.toString())){
	
			//�����豸ʹ�ý����:������Ŀ��FProjectUnitID
//			sql = "Select Fid from T_FDC_EquUsingSettle where FProjectUnitID=?  and FPeriodID=? and FBillSate='03' ";
//			sql = "Select Fid from T_FDC_EquUsingSettle where FProjectUnitID=?  and FProjectId =? and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquUsingSettle where FProjectId =? and FPeriodID=? and FBillSate='03' ";
			params = new Object[]{projectId,curPeriodId};
		
		}	
		else if(BalHelper.EQURENTSETTLE.equals(billType.toString())){
			//�����豸�������:��Ŀ��FProjectUnitID
			//�����豸���Ҫ�������ӷ�
//			sql = "Select Fid from T_FDC_EquRentSettle where FProjectOrgId=?   and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquRentSettle where FProjectOrgId=?   and FProjectId =? and FPeriodID=? and FBillSate='03' ";
			
		}	
		else if(BalHelper.EQUUSECONTRUCTUNIT.equals(billType.toString())){
			//ʹ����������豸���:��Ŀ��FProjectUnitID
//			sql = "Select Fid from T_FDC_EquUseContructUnit where FProjectOrgId=?  and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquUseContructUnit where FProjectOrgId=?  and FProjectId =? and FPeriodID=? and FBillSate='03' ";
			
		} 
		else if(BalHelper.EQURENTACCCOUNTING.equals(billType.toString())){
			//�豸������㵥
//			sql = "Select Fid from T_FDC_EquRentAccounting where FProjectOrgId=?  and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquRentAccounting where FProjectOrgId=?  and FProjectId =? and FPeriodID=? and FBillSate='03' ";
			
		}
		else if(BalHelper.EQUOPERATORWAGE.equals(billType.toString())){
			//���ֹ��ʵ�:��Ŀ��FProjectUnitID
//			sql = "Select Fid from T_FDC_EquOperatorWage where FProjectOrgId=?  and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquOperatorWage where FProjectOrgId=?  and FProjectId =? and FPeriodID=? and FBillSate='03' ";
			
		}
		else if(BalHelper.MAINTAINFEE.equals(billType.toString())){
			
			
			//�豸ά�޽��㵥:����������ά�޽��㵥������Ŀ���е����豸��ά�޷Ѻϼ�ͳ��
			sql = "Select distinct Fid from T_FDC_EquMaintainSettlement where " +
			"	((FPayUnitID=? and FProjectID=?)" +
			"	or  fid in (select fparentid From T_FDC_EquMSEF where FPayUnitID=? and FProjectId=?) " +
			"	)  and FPeriodID=? and FBillSate='03' ";
			params = new Object[]{projectOrgId,projectId,projectOrgId, projectId,curPeriodId};
		}
		else if(BalHelper.FitFuelMatReqBill.equals(billType.toString())){
		
			sql = "Select distinct bill.Fid,transType.FStockType  from T_FDC_MaterialInvBill bill "	+
					"inner join T_FDC_TransactionType transType on transType.FId=bill.FTransTypeID 		\r\n	" +
					"	inner join T_FDC_BillType billtype on billtype.fid=transType.FbilltypeId			\r\n" +
					"where  bill.fid IN (select FParentID from T_FDC_MaterialInvBillEntry where FCostBearOrgID=? and FCostBearProjectid=? )" +
					"  and bill.FPeriodID=? and bill.FBillSate='03' ";			
			
		}
		else if(BalHelper.ELECSETTLEBILL.equals(billType.toString())){
			//��ѽ����:��Ŀ��FProjectUnitID
//			sql = "Select Fid from T_FDC_EquElecSettleBill where FProjectOrgId=? and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquElecSettleBill where FProjectOrgId=? and FProjectId=? and FPeriodID=? and FBillSate='03' ";
						
		}
		else if(BalHelper.EQUTRANSFERFEE.equals(billType.toString())){
			
			//�˷ѽ��㵥:��Ŀ��FProjectUnitID
			sql = "Select distinct Fid from T_FDC_EquTransferFee " +
//					"	where Fid in (select FParentID from T_FDC_EquTransferFeeEntry where FPayOrgID=?)" +
					"	where Fid in (select FParentID from T_FDC_EquTransferFeeEntry where FPayOrgID=? and FProjectId=?)" +
					"	  and FPeriodID=? and FBillSate='03' ";
			
		}
		else if(BalHelper.EQUOTHERFEEPAYBIL.equals(billType.toString())){
			
			//�������ø��:��Ŀ��FProjectUnitID
			sql = "Select distinct Fid from T_FDC_EquOtherFeePayBill " +
//					" where Fid in (select FParentID from T_FDC_EquOtherFeePayEntry where FPayFeeOrgID=? )" +
					" where Fid in (select FParentID from T_FDC_EquOtherFeePayEntry where FPayFeeOrgID=? and FProjectId=?)" +
					" and FPeriodID=? and FBillSate='03' ";
			
		}
		else if(BalHelper.EQUCONTRUCTUNITUSE.equals(billType.toString())){
			//��������:�������ʹ���豸�ۿ	 ��Ŀ��FProjectUnitID
//			sql = "Select Fid from T_FDC_EquContructUnitUse where FProjectOrgId=?  and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquContructUnitUse where FProjectOrgId=?  and FProjectId=? and FPeriodID=? and FBillSate='03' ";
			
		}
		
		else if(BalHelper.EQUDEPRECIATIONSETTLE.equals(billType.toString())){
			//�豸�۾ɽ��㵥
			sql = "Select Fid from t_Ec_Equdepreciationsettle where  FPayOrgID=? and fprojectid = ? and FPeriodID=? and FBillSate='03' ";
			params = new Object[]{projectOrgId,projectId,curPeriodId};
		}
	
		return DbUtil.executeQuery(ctx, sql, params);
	}
}
