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

    //设备成本归集单
    public static final String EquCostAggregate = "F3328B4C";
    //设备使用记录单
    public static final String EQUUSERECORD = "AA5990B6";
    //自有设备使用结算单
    public static final String EQUUSINGSETTLE = "535400C1";
    
    //外租设备租金结算表
    public static final String EQURENTSETTLE = "5F50EDD2";
	//使用劳务队伍设备付款单
    public static final String EQUUSECONTRUCTUNIT = "00BBCEC3";
	//机手工资
    public static final String EQUOPERATORWAGE = "1D8D9C5A";   
	//设备维修结算单
    public static final String MAINTAINFEE = "A0C8FA48";  
	//燃油配件领用表
    public static final String FitFuelMatReqBill = "D3FBF186";  
	//电费结算表
    public static final String ELECSETTLEBILL = "33A48E45";  
	//运费结算单
    public static final String EQUTRANSFERFEE = "C337A96D";  
	//其他费用付款单
    public static final String EQUOTHERFEEPAYBIL = "8CE6466B";  
	//其它收入:劳务队伍使用设备扣款单	
    public static final String EQUCONTRUCTUNITUSE = "DCCADB2F"; 
    
    //机械设备直接工程费成本分摊
    public static final String EquDirectCostApportion = "DA1BCD7C";
    
    //机械设备直接工程费成本分摊调整表
    public static final String EquDirectCostAdjust = "B65F216B";
    //设备出租结算单
    public static final String EQURENTACCCOUNTING = "1A8C839C";
  //设备折旧结算单
    public static final String EQUDEPRECIATIONSETTLE = "48F57326";
    
    
	//获取本组织本期间的单据
	public static IRowSet getBillCollection(Context ctx, String projectOrgId, String projectId, String curPeriodId,String billType) throws BOSException{
		String sql = null;
		//"Select Fid from "+table+" where FProjectOrgId=? and FProjectId=? and FPeriodID=? and FBillSate='03' ";
		
		IRowSet rs = null;
		Object[] params = new Object[]{projectOrgId,projectId,curPeriodId};
//		Object[] params = new Object[]{projectOrgId,curPeriodId};
		
		if(BalHelper.EQUUSERECORD.equals(billType.toString())){
			//设备使用的记录：运行时间
//			sql = "Select Fid from T_FDC_EquUseRecord where FProjectOrgId=?  and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquUseRecord where FProjectOrgId=?  and FProjectId =? and FPeriodID=? and FBillSate='03' ";
		}	
		
		else if(BalHelper.EQUUSINGSETTLE.equals(billType.toString())){
	
			//自有设备使用结算表:结算项目部FProjectUnitID
//			sql = "Select Fid from T_FDC_EquUsingSettle where FProjectUnitID=?  and FPeriodID=? and FBillSate='03' ";
//			sql = "Select Fid from T_FDC_EquUsingSettle where FProjectUnitID=?  and FProjectId =? and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquUsingSettle where FProjectId =? and FPeriodID=? and FBillSate='03' ";
			params = new Object[]{projectId,curPeriodId};
		
		}	
		else if(BalHelper.EQURENTSETTLE.equals(billType.toString())){
			//外租设备租金结算表:项目部FProjectUnitID
			//外租设备租金要加上运杂费
//			sql = "Select Fid from T_FDC_EquRentSettle where FProjectOrgId=?   and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquRentSettle where FProjectOrgId=?   and FProjectId =? and FPeriodID=? and FBillSate='03' ";
			
		}	
		else if(BalHelper.EQUUSECONTRUCTUNIT.equals(billType.toString())){
			//使用劳务队伍设备付款单:项目部FProjectUnitID
//			sql = "Select Fid from T_FDC_EquUseContructUnit where FProjectOrgId=?  and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquUseContructUnit where FProjectOrgId=?  and FProjectId =? and FPeriodID=? and FBillSate='03' ";
			
		} 
		else if(BalHelper.EQURENTACCCOUNTING.equals(billType.toString())){
			//设备出租结算单
//			sql = "Select Fid from T_FDC_EquRentAccounting where FProjectOrgId=?  and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquRentAccounting where FProjectOrgId=?  and FProjectId =? and FPeriodID=? and FBillSate='03' ";
			
		}
		else if(BalHelper.EQUOPERATORWAGE.equals(billType.toString())){
			//机手工资单:项目部FProjectUnitID
//			sql = "Select Fid from T_FDC_EquOperatorWage where FProjectOrgId=?  and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquOperatorWage where FProjectOrgId=?  and FProjectId =? and FPeriodID=? and FBillSate='03' ";
			
		}
		else if(BalHelper.MAINTAINFEE.equals(billType.toString())){
			
			
			//设备维修结算单:符合条件的维修结算单中由项目部承担的设备的维修费合计统计
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
			//电费结算表:项目部FProjectUnitID
//			sql = "Select Fid from T_FDC_EquElecSettleBill where FProjectOrgId=? and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquElecSettleBill where FProjectOrgId=? and FProjectId=? and FPeriodID=? and FBillSate='03' ";
						
		}
		else if(BalHelper.EQUTRANSFERFEE.equals(billType.toString())){
			
			//运费结算单:项目部FProjectUnitID
			sql = "Select distinct Fid from T_FDC_EquTransferFee " +
//					"	where Fid in (select FParentID from T_FDC_EquTransferFeeEntry where FPayOrgID=?)" +
					"	where Fid in (select FParentID from T_FDC_EquTransferFeeEntry where FPayOrgID=? and FProjectId=?)" +
					"	  and FPeriodID=? and FBillSate='03' ";
			
		}
		else if(BalHelper.EQUOTHERFEEPAYBIL.equals(billType.toString())){
			
			//其他费用付款单:项目部FProjectUnitID
			sql = "Select distinct Fid from T_FDC_EquOtherFeePayBill " +
//					" where Fid in (select FParentID from T_FDC_EquOtherFeePayEntry where FPayFeeOrgID=? )" +
					" where Fid in (select FParentID from T_FDC_EquOtherFeePayEntry where FPayFeeOrgID=? and FProjectId=?)" +
					" and FPeriodID=? and FBillSate='03' ";
			
		}
		else if(BalHelper.EQUCONTRUCTUNITUSE.equals(billType.toString())){
			//其它收入:劳务队伍使用设备扣款单	 项目部FProjectUnitID
//			sql = "Select Fid from T_FDC_EquContructUnitUse where FProjectOrgId=?  and FPeriodID=? and FBillSate='03' ";
			sql = "Select Fid from T_FDC_EquContructUnitUse where FProjectOrgId=?  and FProjectId=? and FPeriodID=? and FBillSate='03' ";
			
		}
		
		else if(BalHelper.EQUDEPRECIATIONSETTLE.equals(billType.toString())){
			//设备折旧结算单
			sql = "Select Fid from t_Ec_Equdepreciationsettle where  FPayOrgID=? and fprojectid = ? and FPeriodID=? and FBillSate='03' ";
			params = new Object[]{projectOrgId,projectId,curPeriodId};
		}
	
		return DbUtil.executeQuery(ctx, sql, params);
	}
}
