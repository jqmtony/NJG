package com.kingdee.eas.fdc.contract;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

public class ChangeAuditUtil {
	private static final Logger logger = CoreUIObject.getLogger(AbstractPayRequestBillEditUI.class);	
	//���ⱻʵ����
	private ChangeAuditUtil(){}
	/**
	 * Description:
	 * 		�󶨵�Ԫ��Ҫ�洢��ֵ,��ֵ���ڵĵ�Ԫ��Ϊ��������Ӧ�ĵ�Ԫ����ұ�һ����
	 *
	 * @author sxhong  		Date 2006-9-4
	 * @param table
	 * @param rowIndex
	 * @param colIndex		
	 * @param name			Ҫ��ʾ������
	 * @param key 		ֵ,�����ö���������,��auditor.name
	 * @param bindCellMap
	 */
	public static void bindCell(KDTable table,int rowIndex,int colIndex,String name,String key,HashMap bindCellMap){
		ICell cell=table.getCell(rowIndex, colIndex);
		cell.setValue(name);
		/*
		 * ֵ���
		 */
		cell=table.getCell(rowIndex, colIndex+1);
//		cell.getStyleAttributes().setBackground(Color.LIGHT_GRAY);
		//�󶨶�Ӧ��ϵ
		bindCellMap.put(key, cell);
	}
	
	/**
	 * Description:
	 *		�󶨶��󵽵�Ԫ��
	 * @author sxhong  		Date 2006-9-4
	 * @param cell
	 * @param valueName
	 * @param valueProperty
	 * @param bindCellMap
	 */
	public static void bindCell(ICell cell,String key,HashMap bindCellMap){
		//�󶨶�Ӧ��ϵ
		bindCellMap.put(key, cell);
	}
	
	
	/**
	 * �󶨵�Ԫ��ָ�����󣬲�Ϊ��Ԫ����һ����ʾ��ֵ��Editor��KeyΪ���������õ�Ԫ��
	 * ǰ����һ��������Ϣname
	 * @author sxhong  		Date 2006-10-26
	 * @param table
	 * @param rowIndex
	 * @param colIndex
	 * @param name
	 * @param key
	 * @param bindCellMap
	 * @param numberEditor
	 */
	public static void bindCell(KDTable table,int rowIndex,int colIndex,String name,String key,HashMap bindCellMap,boolean numberEditor){
		ICell cell=table.getCell(rowIndex, colIndex);
		cell.setValue(name);
		/*
		 * ֵ���
		 */
		cell=table.getCell(rowIndex, colIndex+1);
		bindCell(cell, key, bindCellMap, numberEditor);
	}
	
	/**
	 * �󶨵�Ԫ��ָ�����󣬲�Ϊ��Ԫ����һ����ʾ��ֵ��Editor��KeyΪ������
	 * @author sxhong  		Date 2006-10-26
	 * @param cell
	 * @param key
	 * @param bindCellMap
	 * @param numberEditor
	 */
	public static void bindCell(ICell cell,String key,HashMap bindCellMap,boolean numberEditor){
		if(numberEditor){
			cell.setEditor(getCellNumberEdit());
		}
		//�󶨶�Ӧ��ϵ
		bindCellMap.put(key, cell);
	}
	
	/**
	 * �õ�һ��-1.0E17����1.0E17��BigDecimal CellEditor
	 * @author sxhong  		Date 2006-10-26
	 * @return
	 */
	public static KDTDefaultCellEditor getCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
        kdc.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	/**
	 * �ӵ�Ԫ��ȡֵ��ֵ���� ֧�ֹ�������
	 * @author sxhong  		Date 2006-10-26
	 * @param info
	 * @param map
	 * @return
	 */
	public static boolean getValueFromCell(IObjectValue info,HashMap map){
		if(info==null||map==null) return false;
		Set keys = map.keySet();
		String key=null;
		Iterator iter=keys.iterator();
		while(iter.hasNext()){
			key=(String)iter.next();
			Object cell=map.get(key);
			if(cell instanceof ICell){
				/*	
				 * ��IObjectValue�ķ�����������ֵΪnull��key�ʲ���ͨ��key���ж�
				 *  info�Ƿ��и�����
				 */
				int index = key.indexOf('.');
				if(index>0) {
					//ObjectValue
					String infoKey=key.substring(0,index);
					String propKey=key.substring(index+1);
					Object subinfo=info.get(infoKey);
					if (subinfo instanceof IObjectValue)
					{
						((IObjectValue) subinfo).put(propKey, ((ICell)cell).getValue());
						
					}
				
				}else{
					//��ObjectValue
					info.put(key,((ICell)cell).getValue());
				}
			}
		}
		
		return true;
	}
	/**
	 * ��ֵ���������ֵ���õ�Cell��չʾ��֧��ֵ����Ĺ������ԡ�
	 * @author sxhong  		Date 2006-10-26
	 * @param info
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static boolean setValueToCell(IObjectValue info,HashMap map) throws Exception{
		if(info==null||map==null) return false;
		Set keys = map.keySet();
		String key=null;
		Iterator iter=keys.iterator();
		while(iter.hasNext()){
			key=(String)iter.next();			
			Object cell=map.get(key);
			int index = key.indexOf('.');
			Object value;
			if(index>0) {
				//objectValue
				String infoKey=key.substring(0,index);
				String propKey=key.substring(index+1);					
				Object subinfo=info.get(infoKey);
				if (subinfo instanceof IObjectValue)
				{
					value=((IObjectValue) subinfo).get(propKey);
					((ICell)cell).setValue(value);
				}
			}else{
				//��ObjectValue
				value=info.get(key);
				((ICell)cell).setValue(value);
			}
		}
		
		return true;
	}
	
	/**
	 * ��������ȡ��Դ������Դ�ļ�com.kingdee.eas.fdc.contract.client.ContractResource��
	 * @author sxhong  		Date 2006-9-14
	 * @param resName	��Դ������
	 * @return String ��ȡ����Դ
	 */
	public static String getRes(String resName) {
		return EASResource
				.getString(
						"com.kingdee.eas.fdc.contract.client.ChangeAuditResource",
						resName);
	}
	 /**
	  * ����ͬδ����ʱ(�����ս�������ս���δ����)���滮���=�滮���-��ǩԼ���+��������������=���ƽ��-ǩԼ��
	  * ����ͬ�ѽ���ʱ(���ս���������)���滮���=�滮���-������������=���ƽ��-������
     * @return
     * @throws BOSException 
     * @throws EASBizException 
     * @throws SQLException 
     */
   public static void synContractProgAmt(BigDecimal oldChangeAmount,ContractChangeBillInfo model,boolean flag) throws EASBizException, BOSException, SQLException{
	flag = true;
	   //�������֮��ı�����
	BigDecimal newChangeAmount = model.getBalanceAmount();
	SelectorItemCollection sictc = new SelectorItemCollection();
   	sictc.add("*");
   	sictc.add("amount");
   	ContractChangeBillInfo contractChangeBillInfo = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(model.getId()), sictc);
    //�������֮ǰ�ı�����
   	BigDecimal oldChangeAmt = oldChangeAmount;
	if(oldChangeAmount == null){
		oldChangeAmt = contractChangeBillInfo.getAmount();
	}
   	BOSUuid contractBillId = model.getContractBill().getId();
   	SelectorItemCollection sic = new SelectorItemCollection();
   	sic.add("*");
   	sic.add("programmingContract.*");
   	IContractBill serviceCon = null;
   	IProgrammingContract service = null;
   	ContractBillInfo contractBillInfo = null;
		//	FDCSQLBuilder builder = null;
	serviceCon = ContractBillFactory.getRemoteInstance();
	service = ProgrammingContractFactory.getRemoteInstance();
		//	builder = new FDCSQLBuilder();
   	contractBillInfo = serviceCon.getContractBillInfo(new ObjectUuidPK(contractBillId.toString()), sic);
   	ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
   	if(pcInfo == null) return;
 // �滮���
	BigDecimal balanceAmt = pcInfo.getBalance();
	// �������
	BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
	//�����Ԥ�㱾�ҽ��
	BigDecimal changeAmount = model.getAmount();
	//��ܺ�ԼǩԼ���
	BigDecimal changeAmountProg = pcInfo.getChangeAmount();
	//���
	BigDecimal otherChangeAmount = FDCHelper.ZERO;
	if(flag){
		pcInfo.setBalance(FDCHelper.subtract(FDCHelper.add(balanceAmt, oldChangeAmt), newChangeAmount));
		pcInfo.setControlBalance(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, oldChangeAmt), newChangeAmount));
		pcInfo.setChangeAmount(FDCHelper.add(FDCHelper.subtract(changeAmountProg, oldChangeAmt), newChangeAmount));
		otherChangeAmount = FDCHelper.subtract(FDCHelper.add(FDCHelper.subtract(changeAmountProg, oldChangeAmt), newChangeAmount), changeAmountProg);
	}
   	SelectorItemCollection sict = new SelectorItemCollection();
   	sict.add("balance");
   	sict.add("controlBalance");
	sict.add("signUpAmount");
	sict.add("changeAmount");
	sict.add("settleAmount");
	sict.add("srcId");
   	service.updatePartial(pcInfo, sict);
	//���������ĺ�Լ�滮�汾���
	String progId = pcInfo.getId().toString();
	while (progId != null) {
		String nextVersionProgId = getNextVersionProg(progId);
		if (nextVersionProgId != null) {
			pcInfo = service
					.getProgrammingContractInfo(
							new ObjectUuidPK(nextVersionProgId), sict);
			pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(),
					otherChangeAmount));
			pcInfo.setControlBalance(FDCHelper.subtract(pcInfo.getControlBalance(),
					otherChangeAmount));
			pcInfo.setChangeAmount(FDCHelper.add(pcInfo.getChangeAmount(),
					otherChangeAmount));
			service.updatePartial(pcInfo, sict);
			progId = pcInfo.getId().toString();
		} else {
			progId = null;
		}
	}
   }
	private static String getNextVersionProg(String nextProgId) throws BOSException, SQLException{
		String tempId = null;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		IRowSet rowSet = null;
		builder.appendSql(" select fid from t_con_programmingContract where  ");
		builder.appendParam("FSrcId", nextProgId);
		rowSet = builder.executeQuery();
		while(rowSet.next()){
			tempId = rowSet.getString("fid");
		}
		return tempId ;
	}
	/**
	 * У���ͬ�����ս��㵥�Ƿ��ڹ�������
	 * @param conID
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static boolean checkHasSettlementBill(String conID) throws EASBizException, BOSException{
		boolean runningWorkflow =false;
		boolean exists = ContractSettlementBillFactory.getRemoteInstance().exists("where isFinalSettle= 1 and contractBill.id='"+conID+"'");
		if(exists){
			ContractSettlementBillCollection billCollection = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection("select id where isFinalSettle= 1 and contractBill.id='"+conID+"'");
			runningWorkflow = FDCUtils.isRunningWorkflow(billCollection.get(0).getId().toString());
		}
		
		return runningWorkflow;
	}
}
