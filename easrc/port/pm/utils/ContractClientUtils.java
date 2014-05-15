/*
 * @(#)ContractClientUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.port.pm.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleEntryCollection;
import com.kingdee.eas.base.codingrule.CodingRuleEntryInfo;
import com.kingdee.eas.base.codingrule.CodingRuleInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fi.ap.PayRequestBillFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.port.pm.contract.ContractBillCollection;
import com.kingdee.eas.port.pm.contract.ContractBillEntryFactory;
import com.kingdee.eas.port.pm.contract.ContractBillFactory;
import com.kingdee.eas.port.pm.contract.ContractBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * 
 * 描述:合同客户端工具类
 * 
 * @author liupd date:2006-7-25
 *         <p>
 * @version EAS5.1.3
 */
public class ContractClientUtils {

	/** 资源文件路径 */
	public static final String RESOURCE = "com.kingdee.eas.fdc.contract.client.ContractResource";

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
	//预估金额
	public final static String MAIN_CONTRACT_ESTIMATEAMOUNT_ROW = "es";
	//预估金额ID
	public final static String MAIN_CONTRACT_ESTIMATEID_ROW = "esId";
	
	private static final Logger logger = CoreUIObject.getLogger(ContractClientUtils.class);

	/**
	 * 
	 * 描述：获取资源（从资源文件com.kingdee.eas.fdc.contract.client.ContractResource）
	 * 
	 * @param resName
	 *            资源项名称
	 * @return
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	public static String getRes(String resName) {
		return EASResource
				.getString(
						"com.kingdee.eas.fdc.contract.client.ContractResource",
						resName);
	}

	public static Set listToSet(List idList) {
		Set idSet = new HashSet();

		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			idSet.add(id);

		}
		return idSet;
	}

	public static List getSelectedIdValues(KDTable table, String keyFieldName) {

		List ids = new ArrayList();

		int[] selectedRows = KDTableUtil.getSelectedRows(table);

		for (int i = 0; i < selectedRows.length; i++) {
			if(table.getCell(selectedRows[i], keyFieldName)==null){
				return null;
			}
			String id = (String) table.getCell(selectedRows[i], keyFieldName)
					.getValue();
			ids.add(id);
		}

		return ids;
	}

//	public static ContractBillInfo genContractBillInfoById(String contractBillId) {
//	    ContractBillInfo contractBillInfo = new ContractBillInfo();
//	    contractBillInfo.setId(BOSUuid.read(contractBillId));
//		return contractBillInfo;
//	}
//
//	public static Map getContractMap(Set idSet) {
//		
//		Map contractMap = new HashMap();
//		
//		if(idSet == null || idSet.size() == 0) {
//			return contractMap;
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
//		view.setFilter(filter);
//		view.getSelector().add("id");
//		view.getSelector().add("number");
//		view.getSelector().add("name");
//		try {
//			ContractWithoutTextCollection contractWithoutTextCollection = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection(view);
//			
//			for (Iterator iter = contractWithoutTextCollection.iterator(); iter.hasNext();) {
//				ContractWithoutTextInfo element = (ContractWithoutTextInfo) iter.next();
//				contractMap.put(element.getId().toString(), element);
//			}		
//		
//			view.getSelector().add("hasSettled");
//			ContractBillCollection contractBillCollection = ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
//			for (Iterator iter = contractBillCollection.iterator(); iter.hasNext();) {
//				ContractBillInfo element = (ContractBillInfo) iter.next();
//				contractMap.put(element.getId().toString(), element);
//			}
//
//			
//		} catch (BOSException e) {
//			ExceptionHandler.handle(e);
//		}
//		return contractMap;
//	}
	/**
	 * 启用参数"是否必须与计划任务进行关联"后，合同提交时，校验该合同下是否存在“合同与任务关联单” by cassiel 2010-08-09
	 */
	public static void checkConRelatedTaskSubmit(ContractBillInfo contract) throws BOSException, SQLException{
//		ContractBillInfo contract = this.editData;
		if(contract==null || contract.getId()==null){
			FDCMsgBox.showInfo("请先保存合同!");
			SysUtil.abort();
		}
		//未经过保存便直接提交，此时ID为空
		if(contract.getId()==null&&contract.getState()!=null&&!contract.getState().equals(FDCBillStateEnum.SAVED)){
			return;
		}else if(contract.getId()==null&&contract.getState()!=null&&contract.getState().equals(FDCBillStateEnum.SAVED)){
			FDCMsgBox.showInfo("该合同没有和任务进行关联,不能提交。");
			SysUtil.abort();
		}
		String contractBillId = contract.getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid,count(fid) jishu from T_SCH_ContractAndTaskRel where FContractID=? group by fid ");
		builder.addParam(contractBillId);
		IRowSet rowSet = builder.executeQuery();
		if(rowSet.size()==0){
			FDCMsgBox.showInfo("该合同没有和任务进行关联,不能提交。");
			SysUtil.abort();
		}
		Set ids = new HashSet();
		while(rowSet.next()){
			int count = rowSet.getInt("jishu");
			if(count >0){
				String id = rowSet.getString("fid");
				ids.add(id);
			}
		}
		boolean flag = false;
		if(ids.size()>0){
			builder.clear();
			builder.appendParam("select count(fid) jishu from T_SCH_ContractAndTaskRelEntry where FParentID  ",ids.toArray());
			IRowSet _rowSet = builder.executeQuery();
			while(_rowSet.next()){
				int _count = _rowSet.getInt("jishu");
				if(_count == 0){
					flag = true;
					break;
				}
			}
		}
		if(flag){
			FDCMsgBox.showInfo("该合同没有和任务进行关联,不能提交。");
			SysUtil.abort();
		}
		
	}
	/**
	 * 已关闭的付款申请单对应付款单的金额+未关闭的付款申请单的申请金额
	 */
	public static BigDecimal getReqAmt(String contractId){
		BigDecimal payAmt=FDCHelper.ZERO;
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select sum(t.famount) as famount  from  \n ");
		builder.appendSql("( \n ");
		builder.appendSql("(select famount as famount from T_Con_PayRequestBill where FContractId=? and FHasClosed=0) \n ");
		builder.appendSql("union all\n ");
		builder.appendSql("(select pay.famount as famount from T_CAS_PaymentBill pay \n  ");
		builder.appendSql("inner join T_Con_PayRequestbill req on pay.FFdcPayReqID=req.FID \n ");
		builder.appendSql("and pay.FContractBillId=req.FContractId \n ");
		builder.appendSql("and req.FContractId=? and req.FHasClosed=1) \n ");
		builder.appendSql(") t \n ");
		if(contractId!=null){
			builder.addParam(contractId);
			builder.addParam(contractId);
		}else{
			builder.addParam("");
			builder.addParam("");
		}
		try {
			IRowSet rowSet=builder.executeQuery();
			try {
				if(rowSet.next()){
					payAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return payAmt;
	}
	
	/**
	 * 取合同实付款=已关闭的付款申请单对应付款单的合同内工程款+未关闭的付款申请单的合同内工程款 <p>
	 * by Cassiel_peng   2009-12-02 	<p>
	 * 
	 */
	public static BigDecimal getPayAmt(String contractId){
		BigDecimal payAmt=FDCHelper.ZERO;
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select sum(flocalAmount) famount from T_CAS_PaymentBill where FContractBillId = ?  and fbillstatus =15 ");
		
//		builder.appendSql("select sum(t.famount) as famount  from  \n ");
//		builder.appendSql("( \n ");
//		builder.appendSql("(select FProjectPriceInContract as famount from T_Con_PayRequestBill where FContractId=? and FHasClosed=0) \n ");
//		builder.appendSql("union all\n ");
//		builder.appendSql("(select pay.FProjectPriceInContract as famount from T_CAS_PaymentBill pay \n  ");
//		builder.appendSql("inner join T_Con_PayRequestbill req on pay.FFdcPayReqID=req.FID \n ");
//		builder.appendSql("and pay.FContractBillId=req.FContractId \n ");
//		builder.appendSql("and req.FContractId=? and req.FHasClosed=1) \n ");
//		builder.appendSql(") t \n ");//sql server 加别名
		if(contractId!=null){
			builder.addParam(contractId);
//			builder.addParam(contractId);
		}else{
			builder.addParam("");
//			builder.addParam("");
		}
		try {
			IRowSet rowSet=builder.executeQuery();
			try {
				if(rowSet.next()){
					//NP:toBigDecimal
					payAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return payAmt;
	}
	
	/**
	 * 批量取合同实付款=已关闭的付款申请单对应付款单的合同内工程款+未关闭的付款申请单的合同内工程款 <p>
	 * by Cassiel_peng   2009-12-02 	<p>
	 * 
	 */
	public static Map getPatchPayAmt(Set contractIdSet){
		Map payAmtMap=new HashMap();
		if(contractIdSet==null||contractIdSet.size()==0){
			return payAmtMap;
		}
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select ConID, sum(合同实付款) as 合同实付款  from \n ");
		builder.appendSql("( \n ");
		builder.appendSql("(select FContractId as ConID, sum(FProjectPriceInContract) as 合同实付款 from T_Con_PayRequestBill \n ");
		builder.appendParam("where FContractId   ",contractIdSet);
		builder.appendSql("and FHasClosed=0 \n ");
		builder.appendSql("group by FContractId \n ");
		builder.appendSql("union all\n ");
		builder.appendSql("(select  FContractId as ConID, sum(pay.FProjectPriceInContract) as 合同实付款 from T_CAS_PaymentBill pay \n ");
		builder.appendSql("inner join T_Con_PayRequestbill req on pay.FFdcPayReqID=req.FID \n ");
		builder.appendSql("and pay.FContractBillId=req.FContractId \n ");
		builder.appendParam("and req.FContractId  ",contractIdSet);
		builder.appendSql("and req.FHasClosed=1 \n "); 
		builder.appendSql("group by req.FContractId) \n "); 
		builder.appendSql(") \n ");
		builder.appendSql("group by ConID \n ");
		IRowSet rowSet;
		try {
			rowSet = builder.executeQuery();
			try {
				while(rowSet.next()){
					String contractId=rowSet.getString("ConID");
					if(contractId==null){
						continue;
					}
					BigDecimal payAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("合同实付款"),2);
					payAmtMap.put(contractId, payAmt);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return payAmtMap;
	}
	/**
	 * 参数"合同实际付款情况依据付款单而定"已作废 by cassiel_peng 2009-12-05
	 */
	/*private static boolean isBaseOnPaymentBill(){
		boolean retValue=false;
		try {
			retValue=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_BASEONPAYMENTBILL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retValue;
	}*/
//	/**
//	 * 合同结算价不能小于实际付款数之和 by  cassiel_peng  2009-12-02  
//	 */
//	public static boolean checkSettlePriceBiggerThanPayAmt(ContractSettlementBillInfo settBill,String contractId){
//		boolean returnVal=true;
//		BigDecimal settPrice =FDCHelper.toBigDecimal(settBill.getTotalSettlePrice(),2);
////		 此处需要区分当前要操作(保存 提交 审核)的结算单是否为第一笔结算单,
////		 因为如果是第一笔结算单的话在没有保存之前settBill.getTotalSettlePrice()为0.
//		if(settBill.getContractBill()==null){
//			settPrice=FDCHelper.toBigDecimal(settBill.getCurSettlePrice(),2);
//		}
//		//此处的contractId不能直接通过settBill.getContractBill().getId()获取,因为结算单有可能既是第一张结算单并且还是最终结算单，
//		//更变态的是这张结算单还没有保存
//		BigDecimal totalPay = FDCHelper.toBigDecimal(getPayAmt(contractId));//实付款
//		if(settPrice!=null && settPrice.compareTo(totalPay)==-1){
//			returnVal=false;
//		}
//		return returnVal;
//	}
	/**
	 * 最终结算单保存、提交、审批时 </p>
	 * 	①、如果启用了此参数，比较合同结算价与合同实际付款数大小时，合同实际付款数取已审批的付款单合计金额。</p>
	 *  ②、未启用参数，保持系统原有逻辑，合同实际付款数取付款申请单的合计金额		</p>
	 * 非最终结算单，对合同结算价与合同实际付款数的大小不进行任何校验  </p>
	 * @param isFinalSett  是否为最终结算单  by Cassiel_peng 2009-08-12
	 *  
	 * 说明：此方法已作废，因为目前系统合同实付款=已关闭的付款申请单对应付款单的合同内工程款+未关闭的付款申请单的合同内工程款  <p>
	 * 见 BigDecimal com.kingdee.eas.fdc.contract.client.ContractClientUtils.getPayAmt(String contractId) by cassiel_peng 209-12-02
	 *    BigDecimal com.kingdee.eas.fdc.contract.client.ContractClientUtils.checkSettlePriceBiggerThanPayAmt(ContractSettlementBillInfo settBill,String contractId)
	 */
	/*public static boolean checkForSaveSubmitAudit(ContractSettlementBillInfo settBill) throws BOSException{
		boolean retValue=true;
		*//**
		 * 此处需要区分当前要操作(保存 提交 审核)的结算单是否为第一笔结算单,
		 * 因为如果是第一笔结算单的话在没有保存之前settBill.getTotalSettlePrice()为0.  
		 *//*
		BigDecimal settPrice =FDCHelper.toBigDecimal(settBill.getTotalSettlePrice(),2);
		if(settBill.getContractBill()==null){
			settPrice=FDCHelper.toBigDecimal(settBill.getCurSettlePrice(),2);
		}
		BigDecimal totalPay = FDCHelper.ZERO;
		if(!isBaseOnPaymentBill()){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId",settBill.getContractBill().getId().toString()));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("projectPriceInContract");
			IPayRequestBill payReq = PayRequestBillFactory.getRemoteInstance();
			PayRequestBillCollection coll = payReq.getPayRequestBillCollection(view);
			PayRequestBillInfo info = null;
			for(Iterator it = coll.iterator();it.hasNext();){
				info = (PayRequestBillInfo) it.next();
				totalPay = totalPay.add(FDCHelper.toBigDecimal(info.getProjectPriceInContract(),2));
			}
			if(settPrice!=null && settPrice.compareTo(totalPay)==-1){
				retValue=false;
			}
		}else{
			EntityViewInfo _view=new EntityViewInfo();
			_view.getSelector().add("id");
			_view.getSelector().add("projectPriceInContract");
			_view.getSelector().add("billStatus");
			
			
			FilterInfo _filter=new FilterInfo();
			_filter.getFilterItems().add(new FilterItemInfo("contractBillId",settBill.getContractBill().getId().toString()));
			Set statusSet=new HashSet();
			statusSet.add(BillStatusEnum.AUDITED);
			statusSet.add(BillStatusEnum.RECED);
			statusSet.add(BillStatusEnum.APPROVED);
			statusSet.add(BillStatusEnum.PAYED);
			_filter.getFilterItems().add(new FilterItemInfo("billStatus",statusSet,CompareType.INCLUDE));
			
			*//**
			 * 应周鹏要求更改：实际付款额为所有为非保存状态的付款单。2009-11-12 兰远军
			 *//*
//			_filter.getFilterItems().add(new FilterItemInfo("billStatus",new Integer(
//					com.kingdee.eas.fi.cas.BillStatusEnum.AUDITED_VALUE),CompareType.GREATER_EQUALS));
			_filter.getFilterItems().add(new FilterItemInfo("billStatus",new Integer(com.kingdee.eas.fi.cas.BillStatusEnum.SAVE_VALUE),CompareType.GREATER));
					
			_view.setFilter(_filter);
			
			IPaymentBill payment=PaymentBillFactory.getRemoteInstance();
			PaymentBillCollection _coll=payment.getPaymentBillCollection(_view);
			PaymentBillInfo _info = null;
			for(Iterator it = _coll.iterator();it.hasNext();){
				_info = (PaymentBillInfo) it.next();
				totalPay = totalPay.add(FDCHelper.toBigDecimal(_info.getProjectPriceInContract(),2));
			}
			if(settPrice!=null && settPrice.compareTo(totalPay)==-1){
				retValue=false;
			}
		}
		return retValue;
	}*/
	/**
	 * 审批状态的单据可以上传附件
	 * 
	 */
	private static boolean canUploadForAudited(){
		String companyID = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		boolean retValue=false;
		try {
			 retValue=FDCUtils.getDefaultFDCParamByKey(null,companyID, FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return retValue;
	}
	/**
	 * 应奥园要求：可以对“已审批”的变更指令单进行反审批  by Cassiel_peng
	 * @param obj,对象状态
	 * @param canUploadForAudited 是否可上传
	 */
	public static boolean canUploadAttaForAudited(Object obj, boolean canUploadForAudited){
		boolean isEdit=false;
		if(canUploadForAudited){//如果审批状态的单据可以上传附件
        	if(obj!=null&& 
        			(obj.toString().equals(FDCBillStateEnum.SAVED.toString())
        			||obj.toString().equals(FDCBillStateEnum.SUBMITTED.toString())
        			||obj.toString().equals(FDCBillStateEnum.AUDITTING.toString())
        			||obj.toString().equals(FDCBillStateEnum.AUDITTED.toString())
        			||obj.toString().equals(BillStatusEnum.SAVE.toString())
        			||obj.toString().equals(BillStatusEnum.SUBMIT.toString())
        			||obj.toString().equals(BillStatusEnum.AUDITING.toString())
        			||obj.toString().equals(BillStatusEnum.AUDITED.toString()))){
        		isEdit=true;
        	}else{
        		isEdit=false;
        	}
        }else{
        	if(obj!=null&&
        			(obj.toString().equals(FDCBillStateEnum.SAVED.toString())
        			||obj.toString().equals(FDCBillStateEnum.SUBMITTED.toString())
        			||obj.toString().equals(FDCBillStateEnum.AUDITTING.toString())
        			||obj.toString().equals(BillStatusEnum.SAVE.toString())
        			||obj.toString().equals(BillStatusEnum.SUBMIT.toString())
        			||obj.toString().equals(BillStatusEnum.AUDITING.toString()))){
        		isEdit=true;
        	}else{
        		isEdit=false;
        	}
    	}
		return isEdit;
	}
	/**
	 * 亿达：校验结算单的累计结算金额是否大于或等于0，如果小于0不允许提交或者审批  <p>
	 * 考虑到两种特殊情况：													  <p>	
	 * 1、单据编辑界面没有保存单据便提交(此时数据库里还没有保存结算造价)的情况,需要传递编辑界面单据对象的累计结算原币过来<p>
	 * 2、保存了多张单据然后修改其中的一张单据(该单据不是最后结算单)的情况,需要传递编辑界面单据对象的结算总价原币过来
	 * 因为需要比较此次修改的结算造价原币和之前保存在库里的数据，所以直接传递一个对象内码Id即可 <p>
	 * by Cassiel_peng  2008-9-3
	 */
//	public static void checkTotalSettlePriceSmallerThanZero(Map paramMap) throws BOSException, EASBizException, UuidException {
//		
//		Object contractBillId= paramMap.get("contractBillId");//合同ID
//		Object updateAfterSavedToSubmitBillId=paramMap.get("updateAfterSavedToSubmitBillId");//
//		Object unSavedOriAmt= paramMap.get("txtSettlePrice");//未经保存直接提交的单据的"结算造价原币"(从单据编辑界面取值)
//		Object state= paramMap.get("billState");//单据状态
//		
//		//取出该结算单对应合同的所有结算单(已经保存到数据库里的)累计结算金额(原币)
//		BigDecimal totalOriAmt=FDCHelper.ZERO;
//		EntityViewInfo view=new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractBillId.toString()));
//		view.setFilter(filter);
//		view.getSelector().add("curOriginalAmount");
//		ContractSettlementBillCollection conSettleBillCollection=ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection(view);
//		if(conSettleBillCollection!=null){
//			for(Iterator iter=conSettleBillCollection.iterator();iter.hasNext();){
//				ContractSettlementBillInfo conSettleBillInfo=(ContractSettlementBillInfo)iter.next();
//				totalOriAmt=FDCHelper.toBigDecimal(FDCHelper.add(FDCHelper.toBigDecimal(totalOriAmt,2), FDCHelper.toBigDecimal(conSettleBillInfo.getCurOriginalAmount(),2)),2);
//			}
//		}
//		//如果状态为空，说明该单据是没有经过保存便直接提交，那么需要取出该结算单对应合同的所有结算单的累计结算金额并且加上本张单据在编辑界面填写的结算造价原币金额
//		if(state==null){
//			if(unSavedOriAmt!=null){
//				totalOriAmt=FDCHelper.toBigDecimal(FDCHelper.add(totalOriAmt,FDCHelper.toBigDecimal(unSavedOriAmt,2)),2);
//			}
//		}
//		//如果状态为"保存"，那么必须考虑以下这种情况：该结算单之前和之后都有结算单，并且该结算单尚未提交，
//		//那么当我们来修改单据并提交该单据的时候取数应该按如下处理:用从数据库里查询出来的累计结算金额-该结算单之前已经保存在库中的金额+该结算单此次修改并试图保存的金额
//		if(state!=null&&((state.equals(FDCBillStateEnum.SAVED))||(state.equals(FDCBillStateEnum.SUBMITTED)))){
//			if(updateAfterSavedToSubmitBillId!=null){
//				SelectorItemCollection selector=new SelectorItemCollection();
//				selector.add("curOriginalAmount");
//				ContractSettlementBillInfo conSettleBill=ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(new ObjectUuidPK(BOSUuid.read(updateAfterSavedToSubmitBillId.toString())));
//				totalOriAmt=FDCHelper.add(FDCHelper.toBigDecimal(FDCHelper.subtract(totalOriAmt, FDCHelper.toBigDecimal(conSettleBill.getCurOriginalAmount(),2)),2),FDCHelper.toBigDecimal(unSavedOriAmt,2));
//			}
//		}
//		if(totalOriAmt.compareTo(FDCHelper.ZERO)==-1){
//			MsgBox.showWarning("累计结算金额不能小于0");
//			SysUtil.abort();
//		}
//	}
//	 public   static   char[]   bytesToChars(byte[]   bytes)   {   
//		  String   s=new   String(bytes);   
//		  char   chars[]=s.toCharArray();   
//		  return   chars;   
//	 }
	 
	/**
	 * 选中的合同是否已经上传正文件
	 * 
	 * @param contractId
	 * @author owen_wen 2010-11-19
	 * @return
	 */
//	public static boolean isUploadFile4Contract(String contractBillId) {
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("contract", contractBillId));
//		// 如果启用了笔记留痕功能，contract字段没有值，而Parent字段有值，所以加上下句
//		filter.getFilterItems().add(new FilterItemInfo("Parent", contractBillId));
//		filter.setMaskString("#0 or #1");
//		boolean returnVal = false;
//		try {
//			returnVal = ContractContentFactory.getRemoteInstance().exists(filter);
//		} catch (EASBizException e) {
//			logger.info(e.getMessage());
//			e.printStackTrace();
//		} catch (BOSException e) {
//			logger.info(e.getMessage());
//			e.printStackTrace();
//		}
//
//		return returnVal;
//	}
	 
	/**
	 * 查看合同正文 
	 * 性能优化    by Cassiel  2009-11-20
	 */
//	public static void viewContent(final CoreUIObject ui, final String contractId) throws BOSException, IOException, FileNotFoundException {
//		//进度条，增强界面友好性
//		LongTimeDialog dialog = UITools.getDialog(ui);
//		if (dialog == null)
//			return;
//		dialog.setLongTimeTask(new ILongTimeTask() {
//			public Object exec() throws Exception {
//				//合同正文取数使用如下的方式,保证只会取出一条 
//				EntityViewInfo view=new EntityViewInfo();
//				FilterInfo filter=new FilterInfo();
//				view.setFilter(filter);
//				String sql="select top 1 fid from T_Con_contractContent where fcontractid='"+contractId+"' order by fcreateTime desc";
//				filter.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
//				view.getSelector().add("id");
//				view.getSelector().add("fileType");
//				view.getSelector().add("contentFile");
//				final ContractContentCollection col = ContractContentFactory.getRemoteInstance().getContractContentCollection(view);
//				if(col.size()==0){
//					MsgBox.showWarning(ui, getRes("noContent"));
//					return null;
//				}
//			    
//			    ContractContentInfo contentInfo=col.get(0);
//				String type = contentInfo.getFileType();
//				File file = File.createTempFile("temp", "." + type);
//				String fullname = file.getPath();
//				FileOutputStream fos = new FileOutputStream(file);
//				fos.write(contentInfo.getContentFile());
//				fos.close();
//				
//				 File tempbat = File.createTempFile("tempbat", ".bat");
//			     FileWriter fw = new FileWriter(tempbat);
//			     fw.write("start " + fullname);
//			     fw.close();
//			     String tempbatFullname = tempbat.getPath();
//			     Process process = Runtime.getRuntime().exec(tempbatFullname);
////			     while(process.)
//			     process.toString();
//			    return null;
//			}
//
//				public void afterExec(Object result) throws Exception {
//					
//				}});
//				if (dialog != null)
//					dialog.show();
//	}
	
	/**
	 * 如果合同编码规则中定义了合同类型,则检验合同类型节点是否被选中,是否最明细节点
	 * @param ui 当前UI
	 * @param node 合同类型树节点
	 * @throws Exception
	 */
	public static void checkCodingRuleForContract(CoreUIObject ui, DefaultKingdeeTreeNode node) throws Exception {
		String orgId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		ContractBillInfo contractBillInfo = new ContractBillInfo();
		
		if(!CodingRuleManagerFactory.getRemoteInstance().isExist(contractBillInfo, orgId)) return;
		
		CodingRuleInfo  codingRule = CodingRuleManagerFactory.getRemoteInstance().getCodingRule(contractBillInfo, orgId);
		
		CodingRuleEntryCollection codingRuleEntrys = codingRule.getCodingRuleEntrys();
		
		for (Iterator iter = codingRuleEntrys.iterator(); iter.hasNext();) {
			CodingRuleEntryInfo element = (CodingRuleEntryInfo) iter.next();
			if(element.getValueAttribute() != null && element.getValueAttribute().startsWith("contractType.")) {
				if(node == null || !node.isLeaf()) {
					//请选择合同类型明细节点,因为编码规则中定义了合同类型
					MsgBox.showWarning(ui, getRes("selectContrTypeForCodingRule"));
					SysUtil.abort();
				}
			}
			
		}
	}

	/**
	 * 检查合同被其他单据的引用
	 * @param ui 当前UI对象
	 * @param id 单据Id
	 *
	 * @throws BOSException
	 * @throws EASBizException
	 */
//	public static void checkContractBillRef(CoreUIObject ui, String id) throws BOSException, EASBizException {
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", id));
//	
//		/*
//		 * 不校验变更单，拆分单，因为合同暂存和提交状态可以变更和拆分
//		 */
////		boolean existsChangeBill = ContractChangeBillFactory.getRemoteInstance().exists(filter);
//		boolean existsSettleBill = ContractSettlementBillFactory.getRemoteInstance().exists(filter);
//		FilterInfo filter2 = new FilterInfo();
//		filter2.getFilterItems().add(new FilterItemInfo("contractId", id));
//		boolean existsDeductBill = DeductBillEntryFactory.getRemoteInstance().exists(filter2);
//		boolean existsPayReqBill = PayRequestBillFactory.getRemoteInstance().exists(filter2);
////		boolean existsSplitBill = ContractCostSplitFactory.getRemoteInstance().exists(filter);
////		boolean existsNoCostSplitBill = ConNoCostSplitFactory.getRemoteInstance().exists(filter);
//		boolean existsContractRevise = ContractBillReviseFactory.getRemoteInstance().exists(filter);
//		if (existsSettleBill || existsDeductBill || existsPayReqBill || existsContractRevise) {
//			MsgBox.showWarning(ui, getRes("exist_ref"));
//			SysUtil.abort();
//		}
//	}
	/**
	 * 检查合同被其他单据的引用
	 * @param ui 当前UI对象
	 * @param id 单据Id
	 *
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void checkContractBillRefForRemove(CoreUIObject ui, String id) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", id));
	
		/*
		 * 不校验变更单，拆分单，因为合同暂存和提交状态可以变更和拆分
		 */
//		boolean existsChangeBill = ContractChangeBillFactory.getRemoteInstance().exists(filter);
//		boolean existsSplitBill = ContractCostSplitFactory.getRemoteInstance().exists(filter);
//		boolean existsNoCostSplitBill = ConNoCostSplitFactory.getRemoteInstance().exists(filter);
//		if (existsChangeBill || existsSplitBill || existsNoCostSplitBill) {
//			MsgBox.showWarning(ui, getRes("exist_ref"));
//			SysUtil.abort();
//		}
		
		
		
		/*
		 * 检查被补充、三方合同引用
		 */
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("content", id));
		filter.getFilterItems().add(new FilterItemInfo("rowKey", "nu"));
		
		if(ContractBillEntryFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showWarning(ui, getRes("contractRefByOtherCon"));
			SysUtil.abort();
		}
		
	}
//	public static void checkSplitedForAmtChange(CoreUIObject ui, String id) throws Exception {
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", id));
//		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.CANCEL_VALUE, CompareType.NOTEQUALS));
//		boolean existsSplitBill = ContractCostSplitFactory.getRemoteInstance().exists(filter);
//		boolean existsNoCostSplitBill = ConNoCostSplitFactory.getRemoteInstance().exists(filter);
//		if(existsSplitBill || existsNoCostSplitBill) {
//			//提示信息需要区分是简单模式一体化还是复杂模式一体化,因为简单模式后续处理的方式，都不是作废而是直接删除,(作废的有作废单据)简单模式没有作废的概念
//			String companyId=SysContext.getSysContext().getCurrentFIUnit().getId().toString();
//			boolean isSimpleFinacial=FDCUtils.IsSimpleFinacial(null,companyId);
//			boolean isFinacial=FDCUtils.IsFinacial(null, companyId);
//			if(isSimpleFinacial){//简单模式
//				MsgBox.showWarning("如果该合同已经拆分，修改金额会导致合同金额与拆分不一致，修改金额后，将更新合同拆分金额，如果已经有付款拆分/结算拆分，将直接删除付款拆分/结算拆分");
//			}else if(isFinacial)//复杂模式
//			MsgBox.showWarning(ui, getRes("spltedWarningForAmtChg"));
//		}
//		
//	}

	/**
	 *  选择不计成本的金额为否,金额录在分录上,显示的时候要从分录上取
	 * @param rowSet
	 * @throws SQLException
	 * @throws BOSException
	 * @throws SQLException 
	 */
//	public static void updateAmtByAmtWithoutCost(IRowSet rowSet) throws SQLException, BOSException {
//		BigDecimal amount = rowSet.getBigDecimal("amount");
//		if (amount == null) {
//			String id = rowSet.getString("id");
//			String sql = "select FContent from T_CON_ContractBillEntry where fparentid = ? and FRowkey = ?";
//			FDCSQLBuilder builder = new FDCSQLBuilder(sql);
//			builder.addParams(new Object[] { id, AMOUNT_WITHOUT_COST_ROW });
//			ResultSet resultSet = builder.executeQuery();
//			if (resultSet.next()) {
//				String cont = resultSet.getString("FContent");
//				if (cont != null && cont.length() > 0) {
//					BigDecimal amt = new BigDecimal(cont);
//					rowSet.updateBigDecimal("amount", amt);
//					BigDecimal exRate = rowSet.getBigDecimal("exRate");
//					BigDecimal lamt = null;
//					if(exRate != null && exRate.signum() > 0) {
//						 lamt = amt.multiply(exRate);
//					}
//					else {
//						lamt = amt;
//					}
//					rowSet.updateBigDecimal("localAmount", lamt);
//					
//				}
//	
//			}
//	
//		}
//	}
	
//	public static String getUpdateAmtByAmtWithoutCost(IRowSet rowSet) throws BOSException, SQLException {
//		String id = rowSet.getString("id");
//		//		处理无文本合同 by sxhong
//		if(id!=null&&PayReqUtils.isConWithoutTxt(id)){
//			return null;
//		}
//		BigDecimal amount = rowSet.getBigDecimal("amount");
//		if (amount == null) {
//			rowSet.updateBigDecimal("amount", FDCHelper.ZERO);
//			try {
//				rowSet.updateBigDecimal("originalAmount", FDCHelper.ZERO);
//			} catch (SQLException e) {
//				//e.printStackTrace();
//			}
//			try {
//				rowSet.updateBigDecimal("localAmt", FDCHelper.ZERO);
//			} catch (SQLException e) {
//				//e.printStackTrace();
//			}
//			return rowSet.getString("id");			
//		}
//		
//		return null;
//	}
//	
//	public static void updateAmtByAmtWithoutCost(IRowSet rowSet,Map amountMap) throws SQLException, BOSException {
//		//BigDecimal amount = rowSet.getBigDecimal("amount");
//		//if (amount == null) {
//			String id = rowSet.getString("id");
////			处理无文本合同 by sxhong
//			if(id!=null&&PayReqUtils.isConWithoutTxt(id)){
//				return;
//			}
//			if(amountMap.containsKey(id)){
//				//选择不计成本的金额为否,金额录在分录上,显示的时候要从分录上取
//				BigDecimal cont = (BigDecimal)amountMap.get("FContent");
//				if (cont != null) {
//					BigDecimal amt = cont;
//					rowSet.updateBigDecimal("amount", amt);
//					BigDecimal exRate = rowSet.getBigDecimal("exRate");
//					BigDecimal lamt = null;
//					if(exRate != null && exRate.signum() > 0) {
//						 lamt = amt.multiply(exRate);
//					}
//					else {
//						lamt = amt;
//					}
//					rowSet.updateBigDecimal("originalAmount", lamt);					
//				}
//			}
//		//}
//	}
//	
//	
//	/**
//	 * 取不计成本的金额（当合同性质为补充/三方合同，且是否单独计算=否的时候使用）
//	 * @param contractBillId
//	 * @return BigDecimal 取不到返回0
//	 * @throws BOSException
//	 * @throws SQLException
//	 */
//	public static BigDecimal getAmtWithoutCost(String contractBillId) throws BOSException, SQLException {
//		//处理无文本合同 by sxhong
//		if(contractBillId!=null&&PayReqUtils.isConWithoutTxt(contractBillId)){
//			return FDCConstants.B0;
//		}
//		String sql = "select FContent from T_CON_ContractBillEntry where fparentid = ? and FRowkey = ?";
//		FDCSQLBuilder builder = new FDCSQLBuilder(sql);
//		builder.addParams(new Object[] { contractBillId, AMOUNT_WITHOUT_COST_ROW });
//		ResultSet resultSet = builder.executeQuery();
//		
//		BigDecimal rtnValue = FDCConstants.B0;
//		
//		if(resultSet.next()) {
//			String cont = resultSet.getString("FContent");
//			if (cont != null && cont.length() > 0) {
//				
//				rtnValue =  new BigDecimal(cont);
//			}
//		}
//			
//		return rtnValue;
//	}
//	
	/**
	 * 取合同最新造价（如果已结算，则取结算价；否则取合同金额+变更金额+奖励-索赔-扣款(已被申请单关联的)）
	 * @param contractBillId
	 * @return BigDecimal 取不到返回0
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getLastAmt(String contractId) throws Exception {
		return FDCUtils.getContractLastPrice(null,contractId,true);
//		BigDecimal lastAmount = FDCConstants.B0;
//		SelectorItemCollection selector=new SelectorItemCollection();
//		ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(
//				new ObjectUuidPK(BOSUuid.read(contractId)),selector);
//		
//		if (contract.isHasSettled()) {
//			lastAmount = contract.getSettleAmt();
//			
//		} else {
//			BigDecimal conAmount= contract.getAmount();
//			if(conAmount==null){
//				conAmount=FDCHelper.ZERO;
//			}
//			//变更
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
//			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
//			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.VISA_VALUE));
//			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.ANNOUNCE_VALUE));
//			filter.setMaskString("#0 and (#1 or #2 or #3)");
//			view.setFilter(filter);
//			view.getSelector().add("amount");
//			view.getSelector().add("balanceAmount");
//			view.getSelector().add("hasSettled");
//			IObjectCollection collection = ContractChangeBillFactory
//					.getRemoteInstance().getContractChangeBillCollection(view);
//			ContractChangeBillInfo billInfo;
//
//			BigDecimal changeAmount = FDCHelper.ZERO;
//			for (Iterator iter = collection.iterator(); iter.hasNext();) {
//				billInfo = (ContractChangeBillInfo) iter.next();
//				if (billInfo.getAmount() != null) {
//					if(billInfo.isHasSettled()){
//						changeAmount = changeAmount.add(billInfo.getBalanceAmount());
//					}else{
//						changeAmount = changeAmount.add(billInfo.getAmount());
//					}
//				}
//			}
//
//			
//			//奖励
//			BigDecimal guerdonAmt=FDCHelper.ZERO;
//			BigDecimal compenseAmt=FDCHelper.ZERO;
//			BigDecimal deductAmt=FDCHelper.ZERO;
//			FDCSQLBuilder builder=new FDCSQLBuilder();
//			builder.appendSql("select sum(famount) as amount from T_CON_GuerdonBill where  fcontractid =? AND fstate = ? AND fisGuerdoned = 1");
//			builder.addParam(contractId);
//			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
//			IRowSet rowSet = builder.executeQuery();
//			if(rowSet.size()==1){
//				rowSet.next();
//				guerdonAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
//			}
//			
//			//违约
//			builder.clear();
//			builder.appendSql("select sum(famount) as amount from T_CON_CompensationBill where  fcontractid =? AND fstate = ? AND fisCompensated = 1");
//			builder.addParam(contractId);
//			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
//			rowSet = builder.executeQuery();
//			if(rowSet.size()==1){
//				rowSet.next();
//				compenseAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
//			}
//			
//			//扣款
//			builder.clear();
//			builder.appendSql("select sum(famount) as amount from T_CON_DeductOfPayReqBill " +
//					"where fpayRequestBillId in (select fid from T_CON_PayRequestBill where fcontractid=?)");
//			builder.addParam(contractId);
//			rowSet = builder.executeQuery();
//			if(rowSet.size()==1){
//				rowSet.next();
//				deductAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
//			}
//			
//			/*
//			 * 万科最新要求,未结算的合同最新造价=合同金额+变更金额+奖励-索赔,扣款(已被申请单关联的) 
//			 * by sxhong 2007/09/28
//			 */
//			lastAmount = conAmount.add(FDCHelper.toBigDecimal(changeAmount));
//			lastAmount = lastAmount.add(guerdonAmt).subtract(compenseAmt).subtract(deductAmt);
//		}
//		return lastAmount;
	}
	public static boolean isContractTypePre(String id) throws Exception{
		boolean b = false;
		String cuId = null;
		ContractTypeInfo contractTypeInfo = ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectStringPK(id));
		if(contractTypeInfo.getLevel() == 1) {
			cuId = contractTypeInfo.getCU().getId().toString();
		}
		else {
			String longNumber = contractTypeInfo.getLongNumber();
			String string = longNumber.substring(0, longNumber.indexOf("!"));
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("longNumber", string));
			filter.getFilterItems().add(new FilterItemInfo("level", new Integer(1)));
			view.setFilter(filter);
			view.getSelector().add("CU.id");
			
			ContractTypeCollection contractTypeCollection = ContractTypeFactory.getRemoteInstance().getContractTypeCollection(view);
			if(contractTypeCollection != null && contractTypeCollection.size() > 0) {
				ContractTypeInfo info = contractTypeCollection.get(0);
				cuId = info.getCU().getId().toString();
			}
		}
		
		
		if(OrgConstants.SYS_CU_ID.equals(cuId)) {
			b = true;
		}
		else {
			b = false;
		}
		return b;
	}
	
	//检查合同拆分状态
//	public static boolean getContractSplitState(String contractId){
//		boolean hasAllSplit = false;
//
//		if(BOSUuid.read(contractId).getType().equals(new ContractBillInfo().getBOSType())){
//			SelectorItemCollection selector = new SelectorItemCollection();
//			selector.add("splitState");
//			selector.add("state");
//			selector.add("amount");
//
//			try {
//				final ContractBillInfo contractBillInfo = 
//					ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractId), selector);
//				//合同还没有终止,而且完全拆分
//				if (contractBillInfo.getSplitState() != null 
//						&& contractBillInfo.getSplitState() == CostSplitStateEnum.ALLSPLIT 
//						&& contractBillInfo.getState() != FDCBillStateEnum.INVALID) {
//					hasAllSplit = true;
//				}else{
//					//
//					if (contractBillInfo.getAmount() == null
//							|| contractBillInfo.getAmount().compareTo(
//									FDCHelper.ZERO) == 0) {
//						FilterInfo f = new FilterInfo();
//						f.getFilterItems().add(
//								new FilterItemInfo(
//								"contractChange.contractBill.id",
//								contractId));
//						f.getFilterItems().add(
//								new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));	
//						hasAllSplit = ConChangeSplitFactory
//								.getRemoteInstance().exists(f);
//					}
//				}
//			} catch (EASBizException e) {
//				// TODO 自动生成 catch 块
//				e.printStackTrace();
//			} catch (BOSException e) {
//				// TODO 自动生成 catch 块
//				e.printStackTrace();
//			}
//		}else if(BOSUuid.read(contractId).getType().equals(new ContractWithoutTextInfo().getBOSType())){
//			hasAllSplit = true;
//		}
//		return hasAllSplit;
//	}
	
	/**
	 * 批量-取不计成本的金额（当合同性质为补充/三方合同，且是否单独计算=否的时候使用）
	 * @param String[]
	 * @return BigDecimal 取不到返回0
	 * @throws BOSException
	 * @throws SQLException
	 */
//	public static Map getAmtWithoutCost_Batch(String[] contractIdList) throws BOSException, SQLException {
//		FDCSQLBuilder builder = new FDCSQLBuilder();
//		builder.appendSql("select fparentid,FContent from T_CON_ContractBillEntry where");
//		builder.appendParam("fparentid",contractIdList);
//		builder.appendSql(" and ");
//		builder.appendParam("FRowkey",AMOUNT_WITHOUT_COST_ROW);
//
//		IRowSet rs = builder.executeQuery();
//		Map awCostMap = new HashMap(rs.size());
//		BigDecimal bdZero = FDCConstants.B0;
//		String contractBillId = null;
//		while (rs.next()) {
//			contractBillId = rs.getString("fparentid");
//			//处理无文本合同 by sxhong
//			if(contractBillId != null && PayReqUtils.isConWithoutTxt(contractBillId)){
//				awCostMap.put(contractBillId,bdZero);
//				continue;
//			}
//			String cont = rs.getString("FContent");
//			if (cont != null && cont.length() > 0) {
//				awCostMap.put(contractBillId,new BigDecimal(cont));
//			}
//		}
//			
//		return awCostMap;
//	}
	
	/**
	 * 批量-取合同最新造价（如果已结算，则取结算价；否则取合同金额+变更金额+奖励-索赔-扣款(已被申请单关联的)）
	 * @param String[]
	 * @return Map
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Map getLastAmt_Batch(String[] contractIdList) throws Exception {
		BigDecimal bdZero = FDCConstants.B0;
		String noSettleContractIdList =  null;
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FID,FHasSettled,FSettleAmt,FAmount from T_CON_ContractBill where ");
		builder.appendParam("fid",contractIdList);
		
		IRowSet rs = builder.executeQuery();
		Map lastAmtMap = new HashMap(rs.size());
		while (rs.next()) {
			String contractId = rs.getString("FID");
			BigDecimal contractAmount = FDCConstants.B0;
			if(rs.getBoolean("FHasSettled")){
				contractAmount = rs.getBigDecimal("FSettleAmt");
			}else{
				if(noSettleContractIdList == null){
					noSettleContractIdList = contractId;
				}else{					
					noSettleContractIdList = noSettleContractIdList + "," + contractId;
				}
				contractAmount = rs.getBigDecimal("FAmount");
			}	
			lastAmtMap.put(contractId, contractAmount == null ? bdZero : contractAmount);
		}
		/*
		 * 万科最新要求,未结算的合同最新造价=合同金额+变更金额+奖励-索赔,扣款(已被申请单关联的) 
		 * by sxhong 2007/09/28
		 */		
		if (noSettleContractIdList != null) {
			String[] noSettleContractIdArray = FDCHelper.stringToStrArray(noSettleContractIdList);
			//变更
			builder.clear();
			builder.appendSql("select FContractBillID,sum(fchangeAmount) as changeAmount from ( ");
			builder.appendSql("select FContractBillID,FBalanceAmount as fchangeAmount from T_CON_ContractChangeBill ");
			builder.appendSql("where FHasSettled=1 and ");
			builder.appendParam("FContractBillID",noSettleContractIdArray);
			builder.appendSql(" and (");
			builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" ) union all ");
			builder.appendSql("select FContractBillID,FAmount as fchangeAmount from T_CON_ContractChangeBill ");
			builder.appendSql("where FHasSettled=0 and ");
			builder.appendParam("FContractBillID",noSettleContractIdArray);
			builder.appendSql(" and (");
			builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" )) change group by FContractBillID");
				
			rs = builder.executeQuery();
			while (rs.next()) {
				String contractId = rs.getString("FContractBillID");
				BigDecimal changeAmount = rs.getBigDecimal("changeAmount");	
				if(lastAmtMap.containsKey(contractId) && changeAmount != null){
					lastAmtMap.put(contractId,((BigDecimal)lastAmtMap.get(contractId)).add(changeAmount));
				}				
			}
			
			//奖励	
			builder.clear();
			builder.appendSql("select FContractID,sum(famount) as amount from T_CON_GuerdonBill where ");
			builder.appendParam("FContractID",noSettleContractIdArray);
			builder.appendSql(" and ");
			builder.appendParam("fstate",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" AND fisGuerdoned = 1 group by fcontractid ");
			rs = builder.executeQuery();
			while (rs.next()) {
				String contractId = rs.getString("FContractID");
				BigDecimal guerdonAmt = rs.getBigDecimal("amount");	
				if(lastAmtMap.containsKey(contractId) && guerdonAmt != null){
					lastAmtMap.put(contractId,((BigDecimal)lastAmtMap.get(contractId)).add(guerdonAmt));
				}				
			}
			
			//违约
			builder.clear();
			builder.appendSql("select FContractID,sum(famount) as amount from T_CON_CompensationBill where ");
			builder.appendParam("FContractID",noSettleContractIdArray);
			builder.appendSql(" and ");
			builder.appendParam("fstate",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" AND fisCompensated = 1 group by fcontractid ");
			rs = builder.executeQuery();
			while (rs.next()) {
				String contractId = rs.getString("FContractID");
				BigDecimal compenseAmt = rs.getBigDecimal("amount");	
				if(lastAmtMap.containsKey(contractId) && compenseAmt != null){
					lastAmtMap.put(contractId,((BigDecimal)lastAmtMap.get(contractId)).subtract(compenseAmt));
				}				
			}
			
			//扣款
			builder.clear();
			builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount from T_CON_DeductOfPayReqBill doprb ");
			builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
			builder.appendSql("where ");
			builder.appendParam("prb.FContractID",noSettleContractIdArray);
			builder.appendSql(" group by prb.FContractID ");
			rs = builder.executeQuery();
			while (rs.next()) {
				String contractId = rs.getString("FContractID");
				BigDecimal deductAmt = rs.getBigDecimal("amount");	
				if(lastAmtMap.containsKey(contractId) && deductAmt != null){
					lastAmtMap.put(contractId,((BigDecimal)lastAmtMap.get(contractId)).subtract(deductAmt));
				}				
			}
		}
		return lastAmtMap;
	}
	
	public static List getSelectedUnAuditedId(KDTable table, String keyFieldName, boolean isCancel) {

		List ids = new ArrayList();

		int[] selectedRows = KDTableUtil.getSelectedRows(table);

		for (int i = 0; i < selectedRows.length; i++) {
			if(table.getCell(selectedRows[i], keyFieldName)==null){
				return null;
			}
			if(table.getCell(selectedRows[i], "state")!=null){
				if(FDCBillStateEnum.AUDITTED.toString().equals(table.getCell(selectedRows[i], "state").getValue().toString())
					&& !isCancel)
					continue;
			}else if(table.getCell(selectedRows[i], "changeState")!=null){
				if(FDCBillStateEnum.AUDITTED.toString().equals(table.getCell(selectedRows[i], "changeState").getValue().toString())
					&&!isCancel)
					continue;
			}
			String id = (String) table.getCell(selectedRows[i], keyFieldName).getValue();
			ids.add(id);
		}

		return ids;
	}
}
