/*
 * @(#)ContractClientUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����:��ͬ�ͻ��˹�����
 * 
 * @author liupd date:2006-7-25
 *         <p>
 * @version EAS5.1.3
 */
public class ContractClientUtils {

	/** ��Դ�ļ�·�� */
	public static final String RESOURCE = "com.kingdee.eas.fdc.contract.client.ContractResource";

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
	//Ԥ�����
	public final static String MAIN_CONTRACT_ESTIMATEAMOUNT_ROW = "es";
	//Ԥ�����ID
	public final static String MAIN_CONTRACT_ESTIMATEID_ROW = "esId";
	
	private static final Logger logger = CoreUIObject.getLogger(ContractClientUtils.class);

	/**
	 * 
	 * ��������ȡ��Դ������Դ�ļ�com.kingdee.eas.fdc.contract.client.ContractResource��
	 * 
	 * @param resName
	 *            ��Դ������
	 * @return
	 * @author:liupd ����ʱ�䣺2006-7-25
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
	 * ���ò���"�Ƿ������ƻ�������й���"�󣬺�ͬ�ύʱ��У��ú�ͬ���Ƿ���ڡ���ͬ������������� by cassiel 2010-08-09
	 */
	public static void checkConRelatedTaskSubmit(ContractBillInfo contract) throws BOSException, SQLException{
//		ContractBillInfo contract = this.editData;
		if(contract==null || contract.getId()==null){
			FDCMsgBox.showInfo("���ȱ����ͬ!");
			SysUtil.abort();
		}
		//δ���������ֱ���ύ����ʱIDΪ��
		if(contract.getId()==null&&contract.getState()!=null&&!contract.getState().equals(FDCBillStateEnum.SAVED)){
			return;
		}else if(contract.getId()==null&&contract.getState()!=null&&contract.getState().equals(FDCBillStateEnum.SAVED)){
			FDCMsgBox.showInfo("�ú�ͬû�к�������й���,�����ύ��");
			SysUtil.abort();
		}
		String contractBillId = contract.getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid,count(fid) jishu from T_SCH_ContractAndTaskRel where FContractID=? group by fid ");
		builder.addParam(contractBillId);
		IRowSet rowSet = builder.executeQuery();
		if(rowSet.size()==0){
			FDCMsgBox.showInfo("�ú�ͬû�к�������й���,�����ύ��");
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
			FDCMsgBox.showInfo("�ú�ͬû�к�������й���,�����ύ��");
			SysUtil.abort();
		}
		
	}
	/**
	 * �ѹرյĸ������뵥��Ӧ����Ľ��+δ�رյĸ������뵥��������
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
	 * ȡ��ͬʵ����=�ѹرյĸ������뵥��Ӧ����ĺ�ͬ�ڹ��̿�+δ�رյĸ������뵥�ĺ�ͬ�ڹ��̿� <p>
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
//		builder.appendSql(") t \n ");//sql server �ӱ���
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
	 * ����ȡ��ͬʵ����=�ѹرյĸ������뵥��Ӧ����ĺ�ͬ�ڹ��̿�+δ�رյĸ������뵥�ĺ�ͬ�ڹ��̿� <p>
	 * by Cassiel_peng   2009-12-02 	<p>
	 * 
	 */
	public static Map getPatchPayAmt(Set contractIdSet){
		Map payAmtMap=new HashMap();
		if(contractIdSet==null||contractIdSet.size()==0){
			return payAmtMap;
		}
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select ConID, sum(��ͬʵ����) as ��ͬʵ����  from \n ");
		builder.appendSql("( \n ");
		builder.appendSql("(select FContractId as ConID, sum(FProjectPriceInContract) as ��ͬʵ���� from T_Con_PayRequestBill \n ");
		builder.appendParam("where FContractId   ",contractIdSet);
		builder.appendSql("and FHasClosed=0 \n ");
		builder.appendSql("group by FContractId \n ");
		builder.appendSql("union all\n ");
		builder.appendSql("(select  FContractId as ConID, sum(pay.FProjectPriceInContract) as ��ͬʵ���� from T_CAS_PaymentBill pay \n ");
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
					BigDecimal payAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("��ͬʵ����"),2);
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
	 * ����"��ͬʵ�ʸ���������ݸ������"������ by cassiel_peng 2009-12-05
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
//	 * ��ͬ����۲���С��ʵ�ʸ�����֮�� by  cassiel_peng  2009-12-02  
//	 */
//	public static boolean checkSettlePriceBiggerThanPayAmt(ContractSettlementBillInfo settBill,String contractId){
//		boolean returnVal=true;
//		BigDecimal settPrice =FDCHelper.toBigDecimal(settBill.getTotalSettlePrice(),2);
////		 �˴���Ҫ���ֵ�ǰҪ����(���� �ύ ���)�Ľ��㵥�Ƿ�Ϊ��һ�ʽ��㵥,
////		 ��Ϊ����ǵ�һ�ʽ��㵥�Ļ���û�б���֮ǰsettBill.getTotalSettlePrice()Ϊ0.
//		if(settBill.getContractBill()==null){
//			settPrice=FDCHelper.toBigDecimal(settBill.getCurSettlePrice(),2);
//		}
//		//�˴���contractId����ֱ��ͨ��settBill.getContractBill().getId()��ȡ,��Ϊ���㵥�п��ܼ��ǵ�һ�Ž��㵥���һ������ս��㵥��
//		//����̬�������Ž��㵥��û�б���
//		BigDecimal totalPay = FDCHelper.toBigDecimal(getPayAmt(contractId));//ʵ����
//		if(settPrice!=null && settPrice.compareTo(totalPay)==-1){
//			returnVal=false;
//		}
//		return returnVal;
//	}
	/**
	 * ���ս��㵥���桢�ύ������ʱ </p>
	 * 	�١���������˴˲������ȽϺ�ͬ��������ͬʵ�ʸ�������Сʱ����ͬʵ�ʸ�����ȡ�������ĸ���ϼƽ�</p>
	 *  �ڡ�δ���ò���������ϵͳԭ���߼�����ͬʵ�ʸ�����ȡ�������뵥�ĺϼƽ��		</p>
	 * �����ս��㵥���Ժ�ͬ��������ͬʵ�ʸ������Ĵ�С�������κ�У��  </p>
	 * @param isFinalSett  �Ƿ�Ϊ���ս��㵥  by Cassiel_peng 2009-08-12
	 *  
	 * ˵�����˷��������ϣ���ΪĿǰϵͳ��ͬʵ����=�ѹرյĸ������뵥��Ӧ����ĺ�ͬ�ڹ��̿�+δ�رյĸ������뵥�ĺ�ͬ�ڹ��̿�  <p>
	 * �� BigDecimal com.kingdee.eas.fdc.contract.client.ContractClientUtils.getPayAmt(String contractId) by cassiel_peng 209-12-02
	 *    BigDecimal com.kingdee.eas.fdc.contract.client.ContractClientUtils.checkSettlePriceBiggerThanPayAmt(ContractSettlementBillInfo settBill,String contractId)
	 */
	/*public static boolean checkForSaveSubmitAudit(ContractSettlementBillInfo settBill) throws BOSException{
		boolean retValue=true;
		*//**
		 * �˴���Ҫ���ֵ�ǰҪ����(���� �ύ ���)�Ľ��㵥�Ƿ�Ϊ��һ�ʽ��㵥,
		 * ��Ϊ����ǵ�һ�ʽ��㵥�Ļ���û�б���֮ǰsettBill.getTotalSettlePrice()Ϊ0.  
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
			 * Ӧ����Ҫ����ģ�ʵ�ʸ����Ϊ����Ϊ�Ǳ���״̬�ĸ����2009-11-12 ��Զ��
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
	 * ����״̬�ĵ��ݿ����ϴ�����
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
	 * Ӧ��԰Ҫ�󣺿��Զԡ����������ı��ָ����з�����  by Cassiel_peng
	 * @param obj,����״̬
	 * @param canUploadForAudited �Ƿ���ϴ�
	 */
	public static boolean canUploadAttaForAudited(Object obj, boolean canUploadForAudited){
		boolean isEdit=false;
		if(canUploadForAudited){//�������״̬�ĵ��ݿ����ϴ�����
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
	 * �ڴУ����㵥���ۼƽ������Ƿ���ڻ����0�����С��0�������ύ��������  <p>
	 * ���ǵ��������������													  <p>	
	 * 1�����ݱ༭����û�б��浥�ݱ��ύ(��ʱ���ݿ��ﻹû�б���������)�����,��Ҫ���ݱ༭���浥�ݶ�����ۼƽ���ԭ�ҹ���<p>
	 * 2�������˶��ŵ���Ȼ���޸����е�һ�ŵ���(�õ��ݲ��������㵥)�����,��Ҫ���ݱ༭���浥�ݶ���Ľ����ܼ�ԭ�ҹ���
	 * ��Ϊ��Ҫ�Ƚϴ˴��޸ĵĽ������ԭ�Һ�֮ǰ�����ڿ�������ݣ�����ֱ�Ӵ���һ����������Id���� <p>
	 * by Cassiel_peng  2008-9-3
	 */
//	public static void checkTotalSettlePriceSmallerThanZero(Map paramMap) throws BOSException, EASBizException, UuidException {
//		
//		Object contractBillId= paramMap.get("contractBillId");//��ͬID
//		Object updateAfterSavedToSubmitBillId=paramMap.get("updateAfterSavedToSubmitBillId");//
//		Object unSavedOriAmt= paramMap.get("txtSettlePrice");//δ������ֱ���ύ�ĵ��ݵ�"�������ԭ��"(�ӵ��ݱ༭����ȡֵ)
//		Object state= paramMap.get("billState");//����״̬
//		
//		//ȡ���ý��㵥��Ӧ��ͬ�����н��㵥(�Ѿ����浽���ݿ����)�ۼƽ�����(ԭ��)
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
//		//���״̬Ϊ�գ�˵���õ�����û�о��������ֱ���ύ����ô��Ҫȡ���ý��㵥��Ӧ��ͬ�����н��㵥���ۼƽ�����Ҽ��ϱ��ŵ����ڱ༭������д�Ľ������ԭ�ҽ��
//		if(state==null){
//			if(unSavedOriAmt!=null){
//				totalOriAmt=FDCHelper.toBigDecimal(FDCHelper.add(totalOriAmt,FDCHelper.toBigDecimal(unSavedOriAmt,2)),2);
//			}
//		}
//		//���״̬Ϊ"����"����ô���뿼����������������ý��㵥֮ǰ��֮���н��㵥�����Ҹý��㵥��δ�ύ��
//		//��ô���������޸ĵ��ݲ��ύ�õ��ݵ�ʱ��ȡ��Ӧ�ð����´���:�ô����ݿ����ѯ�������ۼƽ�����-�ý��㵥֮ǰ�Ѿ������ڿ��еĽ��+�ý��㵥�˴��޸Ĳ���ͼ����Ľ��
//		if(state!=null&&((state.equals(FDCBillStateEnum.SAVED))||(state.equals(FDCBillStateEnum.SUBMITTED)))){
//			if(updateAfterSavedToSubmitBillId!=null){
//				SelectorItemCollection selector=new SelectorItemCollection();
//				selector.add("curOriginalAmount");
//				ContractSettlementBillInfo conSettleBill=ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(new ObjectUuidPK(BOSUuid.read(updateAfterSavedToSubmitBillId.toString())));
//				totalOriAmt=FDCHelper.add(FDCHelper.toBigDecimal(FDCHelper.subtract(totalOriAmt, FDCHelper.toBigDecimal(conSettleBill.getCurOriginalAmount(),2)),2),FDCHelper.toBigDecimal(unSavedOriAmt,2));
//			}
//		}
//		if(totalOriAmt.compareTo(FDCHelper.ZERO)==-1){
//			MsgBox.showWarning("�ۼƽ������С��0");
//			SysUtil.abort();
//		}
//	}
//	 public   static   char[]   bytesToChars(byte[]   bytes)   {   
//		  String   s=new   String(bytes);   
//		  char   chars[]=s.toCharArray();   
//		  return   chars;   
//	 }
	 
	/**
	 * ѡ�еĺ�ͬ�Ƿ��Ѿ��ϴ����ļ�
	 * 
	 * @param contractId
	 * @author owen_wen 2010-11-19
	 * @return
	 */
//	public static boolean isUploadFile4Contract(String contractBillId) {
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("contract", contractBillId));
//		// ��������˱ʼ����۹��ܣ�contract�ֶ�û��ֵ����Parent�ֶ���ֵ�����Լ����¾�
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
	 * �鿴��ͬ���� 
	 * �����Ż�    by Cassiel  2009-11-20
	 */
//	public static void viewContent(final CoreUIObject ui, final String contractId) throws BOSException, IOException, FileNotFoundException {
//		//����������ǿ�����Ѻ���
//		LongTimeDialog dialog = UITools.getDialog(ui);
//		if (dialog == null)
//			return;
//		dialog.setLongTimeTask(new ILongTimeTask() {
//			public Object exec() throws Exception {
//				//��ͬ����ȡ��ʹ�����µķ�ʽ,��ֻ֤��ȡ��һ�� 
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
	 * �����ͬ��������ж����˺�ͬ����,������ͬ���ͽڵ��Ƿ�ѡ��,�Ƿ�����ϸ�ڵ�
	 * @param ui ��ǰUI
	 * @param node ��ͬ�������ڵ�
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
					//��ѡ���ͬ������ϸ�ڵ�,��Ϊ��������ж����˺�ͬ����
					MsgBox.showWarning(ui, getRes("selectContrTypeForCodingRule"));
					SysUtil.abort();
				}
			}
			
		}
	}

	/**
	 * ����ͬ���������ݵ�����
	 * @param ui ��ǰUI����
	 * @param id ����Id
	 *
	 * @throws BOSException
	 * @throws EASBizException
	 */
//	public static void checkContractBillRef(CoreUIObject ui, String id) throws BOSException, EASBizException {
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", id));
//	
//		/*
//		 * ��У����������ֵ�����Ϊ��ͬ�ݴ���ύ״̬���Ա���Ͳ��
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
	 * ����ͬ���������ݵ�����
	 * @param ui ��ǰUI����
	 * @param id ����Id
	 *
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void checkContractBillRefForRemove(CoreUIObject ui, String id) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", id));
	
		/*
		 * ��У����������ֵ�����Ϊ��ͬ�ݴ���ύ״̬���Ա���Ͳ��
		 */
//		boolean existsChangeBill = ContractChangeBillFactory.getRemoteInstance().exists(filter);
//		boolean existsSplitBill = ContractCostSplitFactory.getRemoteInstance().exists(filter);
//		boolean existsNoCostSplitBill = ConNoCostSplitFactory.getRemoteInstance().exists(filter);
//		if (existsChangeBill || existsSplitBill || existsNoCostSplitBill) {
//			MsgBox.showWarning(ui, getRes("exist_ref"));
//			SysUtil.abort();
//		}
		
		
		
		/*
		 * ��鱻���䡢������ͬ����
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
//			//��ʾ��Ϣ��Ҫ�����Ǽ�ģʽһ�廯���Ǹ���ģʽһ�廯,��Ϊ��ģʽ��������ķ�ʽ�����������϶���ֱ��ɾ��,(���ϵ������ϵ���)��ģʽû�����ϵĸ���
//			String companyId=SysContext.getSysContext().getCurrentFIUnit().getId().toString();
//			boolean isSimpleFinacial=FDCUtils.IsSimpleFinacial(null,companyId);
//			boolean isFinacial=FDCUtils.IsFinacial(null, companyId);
//			if(isSimpleFinacial){//��ģʽ
//				MsgBox.showWarning("����ú�ͬ�Ѿ���֣��޸Ľ��ᵼ�º�ͬ������ֲ�һ�£��޸Ľ��󣬽����º�ͬ��ֽ�����Ѿ��и�����/�����֣���ֱ��ɾ��������/������");
//			}else if(isFinacial)//����ģʽ
//			MsgBox.showWarning(ui, getRes("spltedWarningForAmtChg"));
//		}
//		
//	}

	/**
	 *  ѡ�񲻼Ƴɱ��Ľ��Ϊ��,���¼�ڷ�¼��,��ʾ��ʱ��Ҫ�ӷ�¼��ȡ
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
//		//		�������ı���ͬ by sxhong
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
////			�������ı���ͬ by sxhong
//			if(id!=null&&PayReqUtils.isConWithoutTxt(id)){
//				return;
//			}
//			if(amountMap.containsKey(id)){
//				//ѡ�񲻼Ƴɱ��Ľ��Ϊ��,���¼�ڷ�¼��,��ʾ��ʱ��Ҫ�ӷ�¼��ȡ
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
//	 * ȡ���Ƴɱ��Ľ�����ͬ����Ϊ����/������ͬ�����Ƿ񵥶�����=���ʱ��ʹ�ã�
//	 * @param contractBillId
//	 * @return BigDecimal ȡ��������0
//	 * @throws BOSException
//	 * @throws SQLException
//	 */
//	public static BigDecimal getAmtWithoutCost(String contractBillId) throws BOSException, SQLException {
//		//�������ı���ͬ by sxhong
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
	 * ȡ��ͬ������ۣ�����ѽ��㣬��ȡ����ۣ�����ȡ��ͬ���+������+����-����-�ۿ�(�ѱ����뵥������)��
	 * @param contractBillId
	 * @return BigDecimal ȡ��������0
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
//			//���
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
//			//����
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
//			//ΥԼ
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
//			//�ۿ�
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
//			 * �������Ҫ��,δ����ĺ�ͬ�������=��ͬ���+������+����-����,�ۿ�(�ѱ����뵥������) 
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
	
	//����ͬ���״̬
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
//				//��ͬ��û����ֹ,������ȫ���
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
//				// TODO �Զ����� catch ��
//				e.printStackTrace();
//			} catch (BOSException e) {
//				// TODO �Զ����� catch ��
//				e.printStackTrace();
//			}
//		}else if(BOSUuid.read(contractId).getType().equals(new ContractWithoutTextInfo().getBOSType())){
//			hasAllSplit = true;
//		}
//		return hasAllSplit;
//	}
	
	/**
	 * ����-ȡ���Ƴɱ��Ľ�����ͬ����Ϊ����/������ͬ�����Ƿ񵥶�����=���ʱ��ʹ�ã�
	 * @param String[]
	 * @return BigDecimal ȡ��������0
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
//			//�������ı���ͬ by sxhong
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
	 * ����-ȡ��ͬ������ۣ�����ѽ��㣬��ȡ����ۣ�����ȡ��ͬ���+������+����-����-�ۿ�(�ѱ����뵥������)��
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
		 * �������Ҫ��,δ����ĺ�ͬ�������=��ͬ���+������+����-����,�ۿ�(�ѱ����뵥������) 
		 * by sxhong 2007/09/28
		 */		
		if (noSettleContractIdList != null) {
			String[] noSettleContractIdArray = FDCHelper.stringToStrArray(noSettleContractIdList);
			//���
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
			
			//����	
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
			
			//ΥԼ
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
			
			//�ۿ�
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
