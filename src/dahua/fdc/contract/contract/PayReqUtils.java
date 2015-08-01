package com.kingdee.eas.fdc.contract;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDFrame;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.TypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.client.PayRequestBillContants;
import com.kingdee.eas.fdc.finance.FDCBudgetConstants;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ����ʵ��ֵ������cell����󶨵Ĺ�����
 * @author sxhong    ����02:21:11
 *
 */
public class PayReqUtils
{
	private static final Logger logger = CoreUIObject.getLogger(PayReqUtils.class);
	
	//���ⱻʵ����
	private PayReqUtils(){}
	
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
					if(infoKey.equals("contractBill")) {
						//������ӵĺ�ͬ�ֶ�
						continue;
					}
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
		if(info == null || map == null) return false;
		Set keys = map.keySet();
		String key=null;
		Iterator iter=keys.iterator();
		while(iter.hasNext()){
			key=(String)iter.next();
			//����֮ǰ������ԭ�е�ԭ�ң�����Ҫ����ʾԭ�ң����Բ������¹���
//			if(key.equalsIgnoreCase(PayRequestBillContants.PRJALLREQAMT)||
//				key.equalsIgnoreCase(PayRequestBillContants.ADDPRJALLREQAMT)||
//				key.equalsIgnoreCase(PayRequestBillContants.PAYPARTAMATLALLREQAMT)||
//				key.equalsIgnoreCase(PayRequestBillContants.CURPAID)||
//				key.equalsIgnoreCase("allGuerdonAmt")||	
//				key.equalsIgnoreCase("allCompensationAmt")){
//				continue;
//			}
			
			Object cell=map.get(key);
			int index = key.indexOf('.');
			Object value;
			if(index>0) {
				//objectValue
				String infoKey=key.substring(0,index);
				String propKey=key.substring(index+1);					
				Object subinfo=info.get(infoKey);
				String contractId = ((PayRequestBillInfo)info).getContractId();
				if(contractId!=null&&!isConWithoutTxt(contractId)&&infoKey.equals("contractBill")){
					//����Ӻ�ͬ�еõ���ֻ�ڵ�Ԫ������ʾ���ֶ�
					subinfo=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				}
				if (subinfo instanceof IObjectValue){
					value=((IObjectValue) subinfo).get(propKey);
					if(subinfo instanceof CurProjectInfo){
						String orgName=((CurProjectInfo)subinfo).getFullOrgUnit().getName();
						if(propKey.equals("displayName")&&value!=null){
							value=value.toString().replace('_', '\\');
						}
						if(orgName!=null){
							value=orgName+"\\"+value;
						}
					}
					((ICell)cell).setValue(value);
				}
			}else {
				//��ObjectValue
				value=info.get(key);
				if(key.equalsIgnoreCase(PayRequestBillContants.CHANGEAMT)){
					BigDecimal amount = (BigDecimal)value;
					if(amount!= null&& amount.compareTo(FDCHelper.ZERO) == 0){
						continue ;
					}
				}
				
				if (null == value) {
					((ICell)cell).setValue(null);
				}else {
					((ICell)cell).setValue(value);
				}
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
						"com.kingdee.eas.fdc.contract.client.PayRequestBillResource",
						resName);
	}
	
	public static void verifyRequire(CoreUIObject ui) {
		FDCClientVerifyHelper.verifyRequire(ui);
	}

	public static void showOprtOK(Component comp) {
		FDCClientUtils.showOprtOK(comp);
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
	
	//���ı���ͬbosType
	private static final BOSObjectType  withoutTextContract = BOSObjectType.create("3D9A5388");
	/**
	 * �ж��ǲ��Ǻ�ͬ
	 * @author sxhong  		Date 2006-9-23
	 * @param id
	 * @return
	 */
	public static boolean  isContractBill(String id){
		return id==null? false:!BOSUuid.read(id).getType().equals(withoutTextContract);
	}
	
	/**
	 * �ж��ǲ������ı���ͬ
	 * @author sxhong  		Date 2006-9-23
	 * @param id
	 * @return
	 */
	public static boolean  isConWithoutTxt(String id){
		return id==null?false:BOSUuid.read(id).getType().equals(withoutTextContract);
	}
	
	public static void traceDownFDCPaymentBill(CoreBillBaseInfo bill,IUIObject owner) throws Exception
	  {
	    Map uiContext = new HashMap();
	    uiContext.put(UIContext.OWNER, owner);
	     String billID = bill.getId().toString(); //��ȡID
	     FilterInfo myFilter=new FilterInfo();
	     myFilter.getFilterItems().add(new FilterItemInfo("fdcPayReqID",billID));
	     
	     uiContext.put("MyFilter", myFilter); //ע�⣬�����billID��44λ����BOSUuid��������String

//	     IUIFactory uiFactory=UIFactory.createUIFactory(UIFactoryName.MODEL) ;
	     IUIFactory uiFactory=UIFactory.createUIFactory(UIFactoryName.NEWWIN) ;
	     IUIWindow window = uiFactory.create("com.kingdee.eas.fdc.finance.client.PaymentFullListUI",
	             uiContext, null);
	     if(window instanceof UIModelDialog)
	     {
	       Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
//	       ((JPanel)((UIModelDialog)window).getContentPane()).setSize(screenDim);
	       int width=700;
	       int height=450;
	       Dimension maxSize =  new Dimension(700,450);
	       if(screenDim.width > maxSize.getWidth() || screenDim.height > maxSize.getHeight()){
	    	   screenDim = maxSize;
	       }
	       JPanel panel = ((JPanel)((UIModelDialog)window).getContentPane());
	       ListUI fullUI = (ListUI)window.getUIObject();
//	       fullUI.setSize(new Dimension(690,300));
	       fullUI.getMainTable().setBounds(new Rectangle(10, 10, width - 25,height - 50));
	       fullUI.getMainTable().getColumn("id").getStyleAttributes().setHided(true);
	       KDTabbedPane tabPane=new KDTabbedPane();
	       tabPane.addTab(getRes("paymentBill"),fullUI);
	       panel.add(tabPane);
	       panel.setPreferredSize(screenDim);
	     }else{
//		       Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
//		       ((JPanel)((UIModelDialog)window).getContentPane()).setSize(screenDim);
//		       int width=700;
//		       int height=450;
//		       Dimension maxSize =  new Dimension(700,450);
//		       if(screenDim.width > maxSize.getWidth() || screenDim.height > maxSize.getHeight()){
//		    	   screenDim = maxSize;
//		       }
		       JPanel panel = ((JPanel)((KDFrame)window).getContentPane());
		       ListUI fullUI = (ListUI)window.getUIObject();
//		       fullUI.setSize(new Dimension(690,300));
//		       fullUI.getMainTable().setPreferredSize(maxSize);
//		       fullUI.getMainTable().setBounds(new Rectangle(10, 10, screenDim.width - 25,screenDim.height - 50));
		       fullUI.getMainTable().getColumn("id").getStyleAttributes().setHided(true);
		       KDTabbedPane tabPane=new KDTabbedPane();
		       JPanel panel2=new KDPanel();
		       panel2.setLayout(new BorderLayout());
		       panel2.add(fullUI,BorderLayout.CENTER);
		       tabPane.addTab(getRes("paymentBill"),panel2);
//		       panel.add(new KDScrollPane(tabPane));
//		       tabPane.setPreferredSize(maxSize);
		       panel.setLayout(new BorderLayout());
		       
		       panel.add(tabPane,BorderLayout.CENTER);
//		       panel.setPreferredSize(maxSize);
		       
	    	 
	     }
	     

	     window.show();
	  }
	
	//���ݹ�Ӧ��ȡһ������--�������뵥
	public static void fillBank(PayRequestBillInfo objectValue,String supperid,String cuId) throws BOSException, SQLException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select b.fbank,b.fbankaccount from T_BD_SupplierCompanyInfo a ");
		builder.appendSql("inner join T_BD_SupplierCompanyBank b on a.fid=b.fsuppliercompanyinfoid ");
		builder.appendSql("where a.fcontrolunitid= ");
		builder.appendParam(cuId);
		builder.appendSql(" and a.fsupplierid=");
		builder.appendParam(supperid);
		builder.appendSql(" order by b.fseq");
		
		IRowSet rowSet = builder.executeQuery();
		if (rowSet!=null && rowSet.next()) {
			String bank = rowSet.getString("fbank");
			String bankaccount = rowSet.getString("fbankaccount");
			
			objectValue.setRecBank(bank);
			objectValue.setRecAccount(bankaccount);
		}else{
			objectValue.setRecBank(null);
			objectValue.setRecAccount(null);
		}
	}
	
	//���ݹ�Ӧ��ȡһ������--���
	public static void fillBank(PaymentBillInfo objectValue,String cuId,String supperid) throws BOSException, SQLException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select b.fbank,b.fbankaccount from T_BD_SupplierCompanyInfo a ");
		builder.appendSql("inner join T_BD_SupplierCompanyBank b on a.fid=b.fsuppliercompanyinfoid ");
		builder.appendSql("where a.fcontrolunitid= ");
		builder.appendParam(cuId);
		builder.appendSql(" and a.fsupplierid=");
		builder.appendParam(supperid);
		builder.appendSql(" order by b.fseq");
		
		IRowSet rowSet = builder.executeQuery();
		if (rowSet!=null && rowSet.next()) {
			String bank = rowSet.getString("fbank");
			String bankaccount = rowSet.getString("fbankaccount");
			
			objectValue.setPayeeBank(bank);
			objectValue.setPayeeAccountBank(bankaccount);
		}else{
			objectValue.setPayeeBank(null);
			objectValue.setPayeeAccountBank(null);
		}
	}
	
	//���ݹ�Ӧ��ȡһ������--���ı���ͬ andy_liu 2012-6-15
	public static void fillBank(ContractWithoutTextInfo objectValue, String supperid, String comOrgID) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select b.fbank,b.fbankaccount from T_BD_SupplierCompanyInfo a ");
		builder.appendSql("inner join T_BD_SupplierCompanyBank b on a.fid=b.fsuppliercompanyinfoid ");
		
		builder.appendSql(" LEFT OUTER JOIN T_ORG_Company AS c ");
		builder.appendSql(" ON a.FComOrgID = c.FID ");
		
		builder.appendSql("where a.FComOrgID= ");
		builder.appendParam(comOrgID);
		builder.appendSql(" and a.fsupplierid=");
		builder.appendParam(supperid);
		builder.appendSql(" order by b.fseq");

		IRowSet rowSet = builder.executeQuery();
		if (rowSet != null && rowSet.next()) {
			String bank = rowSet.getString("fbank");
			String bankaccount = rowSet.getString("fbankaccount");

			objectValue.setBank(bank);
			objectValue.setBankAcct(bankaccount);
		} else {
			objectValue.setBank(null);
			objectValue.setBankAcct(null);
		}
	}
	
	public static void handleBudgetCtrl(CoreUI ui,Map retMap, FDCBudgetParam fdcBudgetParam){
		if(retMap==null||retMap.size()==0){
			return;
		}
		Boolean isPass=(Boolean)retMap.get(FDCBudgetConstants.RETKEY_PASS);
		if(isPass==null||!isPass.booleanValue()){
			String error= ContractClientUtils.getRes("budgetNotEnoughOrNo");//Ԥ�������û��Ԥ��
			Map errorDetailMap=new HashMap();
			String errorDetail="";
//			int msgType=fdcBudgetParam.isStrictCtrl()?0:1;
			int msgType= 1;
			Boolean value = (Boolean)retMap.get("isStrictCtrl");
			boolean isStrictCtrl = false;
			if(value != null && value.booleanValue()){
				isStrictCtrl = true;
				msgType = 0;
			}
			if(fdcBudgetParam.isContractPlanCtrl()){
				error=ContractClientUtils.getRes("conPayPlanNotEnoughOrNo");//û�к�ͬ����ƻ����ߺ�ͬ����ƻ�����
				errorDetailMap.put("head", new String[] {
						ContractClientUtils.getRes("conPayPlanBalance"),
						ContractClientUtils.getRes("reqAmtThis") });// ��ͬ����ƻ����,����������
				errorDetailMap.put("format", new String[]{"amount","amount"});
				errorDetailMap.put("data", new Object[][]{{FDCHelper.toBigDecimal(retMap.get("balanceAmt"),2),FDCHelper.toBigDecimal(retMap.get("reqAmt"),2)}});
			}else if(fdcBudgetParam.isAcctPlanCtrl()){
				Map map=(Map)retMap.get(FDCBudgetConstants.RETKEY_NOPASSDETAIL);
				if(map != null){
//					StringBuffer sb=new StringBuffer();
					errorDetailMap.put("head", new String[] {
							ContractClientUtils.getRes("costAccount"),
							ContractClientUtils.getRes("balance"),
							ContractClientUtils.getRes("reqAmt") });// �ɱ���Ŀ,���,�����
					errorDetailMap.put("format", new String[] { "", "amount",
							"amount" });
					Object[][] data =new Object[map.size()][3]; 
					errorDetailMap.put("data", data);
					int i=0;
					for(Iterator iter=map.keySet().iterator();iter.hasNext();i++){
						Map subMap=(Map)map.get(iter.next());
						
						String str = (String)subMap.get(FDCBudgetConstants.RETKEY_ACCT);
						data[i][0]=str;
						
						BigDecimal tmpAmt = (BigDecimal)subMap.get(FDCBudgetConstants.RETKEY_BALANCEAMT);
						data[i][1]=FDCHelper.toBigDecimal(tmpAmt, 2);
						tmpAmt=(BigDecimal)subMap.get(FDCBudgetConstants.RETKEY_REQAMT);
						data[i][2]=FDCHelper.toBigDecimal(tmpAmt, 2);
					}
				}
			}else if(fdcBudgetParam.isBgSysCtrl()){
				errorDetail=(String)retMap.get(FDCBudgetConstants.RETKEY_NOPASSDETAIL);
			}else{
				
			}
			
			if(errorDetailMap.size()>0){
				FDCMsgBox.showTableDetailAndOK(ui, error, errorDetailMap, msgType);
			}else{
				FDCMsgBox.showDetailAndOK(ui, error, errorDetail, msgType);
			}
			if(isStrictCtrl&&fdcBudgetParam.isBgSysCtrl()){
				//��Ԥ��ϵͳ���ƣ����ϸ����ʱ
				SysUtil.abort();
			}else
			if(isStrictCtrl && !fdcBudgetParam.isBgSysCtrl()){
				SysUtil.abort();
			}else{
				int result = MsgBox.showConfirm2New(ui, ContractClientUtils.getRes("continue"));//�Ƿ����
				if (result == MsgBox.NO) {
					SysUtil.abort();
				}
			}
		}
	}
	/**
	 * ����ͬʵ���� =�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼơ�
	 *  �ں�ͬ���ս���󣬸������뵥�ύ������ʱ�������ͬʵ����(��������) �� ���θ������뵥��ͬ�ڹ��̿�ڷ������ں�ͬ�����  by cassiel_peng 2009-12-02
     *  @see boolean com.kingdee.eas.fdc.finance.client.PaymentBillClientUtils.checkProjectPriceInContract(Set paymentSet) throws EASBizException, BOSException
     *  uiAmt ���α��ҽ�� 
	 */
	public static boolean checkProjectPriceInContract(Set payReqSet,BigDecimal uiAmt) throws EASBizException, BOSException{
		if(uiAmt==null){
			uiAmt=FDCHelper.ZERO;
		}
		boolean retValue=true;
		PayRequestBillInfo payReqBill=null;
		BigDecimal settlePrice=FDCHelper.ZERO;//��ͬ������
		BigDecimal thisTimePayAmt=FDCHelper.ZERO;//���θ������뵥��ͬ�ڹ��̿�ڷ���
		BigDecimal prjPriceInCon=FDCHelper.ZERO;//��ͬʵ����=�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼ�
		if(payReqSet!=null&&payReqSet.size()>0){
			payReqBill=(PayRequestBillInfo)payReqSet.iterator().next();//�˴��������ж��ٸ������뵥,�����м����������Ӧ�ĺ�ͬ�������Ψһ��
			String contractId=payReqBill.getContractId();
			boolean isContractWithTxt=FDCUtils.isContractBill(null, contractId);
			//��������ı���ͬ�Ͳ�Ӧ���������߼�����
			if(isContractWithTxt){
				ContractBillInfo contract=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				if(!contract.isHasSettled()){
					return retValue;
				}
				settlePrice=FDCHelper.toBigDecimal(contract.getSettleAmt(),2);//��ͬ������
				
				prjPriceInCon=FDCHelper.toBigDecimal(ContractClientUtils.getPayAmt(contractId), 2);//��ͬʵ����=�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼ�
				
				/**
				 * ������ݱ����Ȼ���޸����ݺ����ٴ��ύ,����ʱ���������ֵ�����ݿ�����ֵ����һ���ġ���ôȡ������prjPriceInCon��᲻׼ȷ
				 * ��Ϊ�޸Ĺ����ֵ��û�и��µ����ݿ���ȥ�������������������⴦���Ƚ����ݿ����ֵ��ȥ�ٰ��ڽ������޸ĺ����ֵ����
				 */
				if(payReqSet.size()==1){//�������ύ����������
//					thisTimePayAmt=FDCHelper.toBigDecimal(payReqBill.getProjectPriceInContract(),2);���ȡ���ǲ�׼ȷ��
					if(payReqBill.getId()==null){//��δ����ĵ��ݲ���Ҫ��������⴦��
						
					}else{
						SelectorItemCollection selector=new SelectorItemCollection();
						selector.add("projectPriceInContract");
						PayRequestBillInfo  _tempPayRequest=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(payReqBill.getId()),selector);
						thisTimePayAmt=FDCHelper.toBigDecimal(_tempPayRequest.getProjectPriceInContract(),2);
						prjPriceInCon=FDCHelper.toBigDecimal(FDCHelper.add(FDCHelper.subtract(prjPriceInCon, thisTimePayAmt), uiAmt),2);
					}
				}else{//�˴�һ���Ƕ�������������
				}
				if(settlePrice!=null && settlePrice.compareTo(prjPriceInCon)==-1){
					retValue=false;
				}
			}
		}
		return retValue;
	}
	public static BigDecimal getConCompletePrjAmt(String contractId) throws BOSException{
		BigDecimal completePrjAmt = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("completePrjAmt");
		view.getSelector().add("state");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
		filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", TypeInfo.progressID));
		view.setFilter(filter);
		PayRequestBillCollection payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

		if (payReqColl != null) {
			for (int i = 0; i < payReqColl.size(); i++) {
				PayRequestBillInfo info = payReqColl.get(i);
				if (info.getState() == FDCBillStateEnum.AUDITTED) {
					completePrjAmt = completePrjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
				}
			}
		}
		completePrjAmt = FDCHelper.toBigDecimal(completePrjAmt, 2);
		return completePrjAmt;
	}
	
	// ȡ�ڽ��ȿ��һ�ʽ����ʱ�����깤
	public static BigDecimal getConSettleCompletePrjAmt(PayRequestBillInfo payReqInfo) throws Exception {

		// ���깤������(���깤)
		BigDecimal completePrjAmt = FDCHelper.toBigDecimal(payReqInfo.getCompletePrjAmt());

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("projectPriceInContract");
		view.getSelector().add("completePrjAmt");
		view.getSelector().add("state");
		view.getSelector().add("entrys.projectPriceInContract");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId", payReqInfo.getContractId()));
		// filter.getFilterItems().add(new
		// FilterItemInfo("paymentType.payType.id", TypeInfo.progressID));
		// filter.getFilterItems().add(new FilterItemInfo("createTime",
		// payReqInfo.getCreateTime(), CompareType.LESS_EQUALS));
		// ����������,���������浥����ȡ��ǰ������������,�Ա����������޸�ʱ���ݴ���
		if (payReqInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", payReqInfo.getId().toString(), CompareType.NOTEQUALS));
		}
		view.setFilter(filter);
		PayRequestBillCollection payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

		if (payReqColl != null) {
			for (int i = 0; i < payReqColl.size(); i++) {
				PayRequestBillInfo info = payReqColl.get(i);
				// ������
				if (info.getState() == FDCBillStateEnum.AUDITTED) {
					completePrjAmt = completePrjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
				}
			}
		}
		completePrjAmt = FDCHelper.toBigDecimal(completePrjAmt, 2);
		return completePrjAmt;
	}
}
