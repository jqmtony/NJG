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
 * 用于实现值对象与cell对象绑定的工具类
 * @author sxhong    下午02:21:11
 *
 */
public class PayReqUtils
{
	private static final Logger logger = CoreUIObject.getLogger(PayReqUtils.class);
	
	//避免被实例化
	private PayReqUtils(){}
	
	/**
	 * Description:
	 * 		绑定单元格及要存储的值,其值所在的单元格为名称所对应的单元格的右边一个格
	 *
	 * @author sxhong  		Date 2006-9-4
	 * @param table
	 * @param rowIndex
	 * @param colIndex		
	 * @param name			要显示的名称
	 * @param key 		值,可以用对象及其属性,如auditor.name
	 * @param bindCellMap
	 */
	public static void bindCell(KDTable table,int rowIndex,int colIndex,String name,String key,HashMap bindCellMap){
		ICell cell=table.getCell(rowIndex, colIndex);
		cell.setValue(name);
		/*
		 * 值表格
		 */
		cell=table.getCell(rowIndex, colIndex+1);
//		cell.getStyleAttributes().setBackground(Color.LIGHT_GRAY);
		//绑定对应关系
		bindCellMap.put(key, cell);
	}
	
	/**
	 * Description:
	 *		绑定对象到单元格
	 * @author sxhong  		Date 2006-9-4
	 * @param cell
	 * @param valueName
	 * @param valueProperty
	 * @param bindCellMap
	 */
	public static void bindCell(ICell cell,String key,HashMap bindCellMap){
		//绑定对应关系
		bindCellMap.put(key, cell);
	}
	
	
	/**
	 * 绑定单元格到指定对象，并为单元设置一个表示数值的Editor，Key为对象名，该单元格
	 * 前面有一个描述信息name
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
		 * 值表格
		 */
		cell=table.getCell(rowIndex, colIndex+1);
		bindCell(cell, key, bindCellMap, numberEditor);
	}
	
	/**
	 * 绑定单元格到指定对象，并为单元设置一个表示数值的Editor，Key为对象名
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
		//绑定对应关系
		bindCellMap.put(key, cell);
	}
	
	/**
	 * 从单元格取值到值对象 支持关联属性
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
				 * 因IObjectValue的方法内屏蔽了值为null的key故不能通过key来判断
				 *  info是否有该属性
				 */
				int index = key.indexOf('.');
				if(index>0) {
					//ObjectValue
					String infoKey=key.substring(0,index);
					String propKey=key.substring(index+1);
					if(infoKey.equals("contractBill")) {
						//处理添加的合同字段
						continue;
					}
					Object subinfo=info.get(infoKey);
					if (subinfo instanceof IObjectValue)
					{
						((IObjectValue) subinfo).put(propKey, ((ICell)cell).getValue());
						
					}
				
				}else{
					//非ObjectValue
					info.put(key,((ICell)cell).getValue());
				}
			}
		}
		
		return true;
	}
	/**
	 * 将值对象的属性值设置到Cell中展示，支持值对象的关联属性。
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
			//这里之前屏蔽了原有的原币，现在要求显示原币，所以不做以下过滤
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
					//处理从合同中得到且只在单元格内显示的字段
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
				//非ObjectValue
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
	 * 描述：获取资源（从资源文件com.kingdee.eas.fdc.contract.client.ContractResource）
	 * @author sxhong  		Date 2006-9-14
	 * @param resName	资源项名称
	 * @return String 获取的资源
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
	 * 得到一个-1.0E17－－1.0E17的BigDecimal CellEditor
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
	
	//无文本合同bosType
	private static final BOSObjectType  withoutTextContract = BOSObjectType.create("3D9A5388");
	/**
	 * 判断是不是合同
	 * @author sxhong  		Date 2006-9-23
	 * @param id
	 * @return
	 */
	public static boolean  isContractBill(String id){
		return id==null? false:!BOSUuid.read(id).getType().equals(withoutTextContract);
	}
	
	/**
	 * 判断是不是无文本合同
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
	     String billID = bill.getId().toString(); //获取ID
	     FilterInfo myFilter=new FilterInfo();
	     myFilter.getFilterItems().add(new FilterItemInfo("fdcPayReqID",billID));
	     
	     uiContext.put("MyFilter", myFilter); //注意，这里的billID是44位长的BOSUuid，类型是String

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
	
	//根据供应商取一个银行--付款申请单
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
	
	//根据供应商取一个银行--付款单
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
	
	//根据供应商取一个银行--无文本合同 andy_liu 2012-6-15
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
			String error= ContractClientUtils.getRes("budgetNotEnoughOrNo");//预算余额不足或没有预算
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
				error=ContractClientUtils.getRes("conPayPlanNotEnoughOrNo");//没有合同付款计划或者合同付款计划余额不足
				errorDetailMap.put("head", new String[] {
						ContractClientUtils.getRes("conPayPlanBalance"),
						ContractClientUtils.getRes("reqAmtThis") });// 合同付款计划余额,本次申请金额
				errorDetailMap.put("format", new String[]{"amount","amount"});
				errorDetailMap.put("data", new Object[][]{{FDCHelper.toBigDecimal(retMap.get("balanceAmt"),2),FDCHelper.toBigDecimal(retMap.get("reqAmt"),2)}});
			}else if(fdcBudgetParam.isAcctPlanCtrl()){
				Map map=(Map)retMap.get(FDCBudgetConstants.RETKEY_NOPASSDETAIL);
				if(map != null){
//					StringBuffer sb=new StringBuffer();
					errorDetailMap.put("head", new String[] {
							ContractClientUtils.getRes("costAccount"),
							ContractClientUtils.getRes("balance"),
							ContractClientUtils.getRes("reqAmt") });// 成本科目,余额,申请额
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
				//当预算系统控制，且严格控制时
				SysUtil.abort();
			}else
			if(isStrictCtrl && !fdcBudgetParam.isBgSysCtrl()){
				SysUtil.abort();
			}else{
				int result = MsgBox.showConfirm2New(ui, ContractClientUtils.getRes("continue"));//是否继续
				if (result == MsgBox.NO) {
					SysUtil.abort();
				}
			}
		}
	}
	/**
	 * 【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】
	 *  在合同最终结算后，付款申请单提交、审批时，如果合同实付款(不含本次) 加 本次付款申请单合同内工程款本期发生大于合同结算价  by cassiel_peng 2009-12-02
     *  @see boolean com.kingdee.eas.fdc.finance.client.PaymentBillClientUtils.checkProjectPriceInContract(Set paymentSet) throws EASBizException, BOSException
     *  uiAmt 本次本币金额 
	 */
	public static boolean checkProjectPriceInContract(Set payReqSet,BigDecimal uiAmt) throws EASBizException, BOSException{
		if(uiAmt==null){
			uiAmt=FDCHelper.ZERO;
		}
		boolean retValue=true;
		PayRequestBillInfo payReqBill=null;
		BigDecimal settlePrice=FDCHelper.ZERO;//合同结算金额
		BigDecimal thisTimePayAmt=FDCHelper.ZERO;//本次付款申请单合同内工程款本期发生
		BigDecimal prjPriceInCon=FDCHelper.ZERO;//合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计
		if(payReqSet!=null&&payReqSet.size()>0){
			payReqBill=(PayRequestBillInfo)payReqSet.iterator().next();//此处不考虑有多少付款申请单,无论有几条付款单，对应的合同结算金额都是唯一的
			String contractId=payReqBill.getContractId();
			boolean isContractWithTxt=FDCUtils.isContractBill(null, contractId);
			//如果是无文本合同就不应该有下续逻辑控制
			if(isContractWithTxt){
				ContractBillInfo contract=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				if(!contract.isHasSettled()){
					return retValue;
				}
				settlePrice=FDCHelper.toBigDecimal(contract.getSettleAmt(),2);//合同结算金额
				
				prjPriceInCon=FDCHelper.toBigDecimal(ContractClientUtils.getPayAmt(contractId), 2);//合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计
				
				/**
				 * 如果数据保存过然后修改数据后又再次提交,而此时界面上填的值跟数据库里存的值不是一样的。那么取出来的prjPriceInCon便会不准确
				 * 因为修改过后的值还没有更新到数据库中去。所以现在做如下特殊处理：先将数据库里的值减去再把在界面上修改后的新值加上
				 */
				if(payReqSet.size()==1){//单个请款单提交或者是审批
//					thisTimePayAmt=FDCHelper.toBigDecimal(payReqBill.getProjectPriceInContract(),2);如此取数是不准确的
					if(payReqBill.getId()==null){//尚未保存的单据不需要作如此特殊处理
						
					}else{
						SelectorItemCollection selector=new SelectorItemCollection();
						selector.add("projectPriceInContract");
						PayRequestBillInfo  _tempPayRequest=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(payReqBill.getId()),selector);
						thisTimePayAmt=FDCHelper.toBigDecimal(_tempPayRequest.getProjectPriceInContract(),2);
						prjPriceInCon=FDCHelper.toBigDecimal(FDCHelper.add(FDCHelper.subtract(prjPriceInCon, thisTimePayAmt), uiAmt),2);
					}
				}else{//此处一定是多张请款单批量审批
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
	
	// 取第进度款及第一笔结算款时的已完工
	public static BigDecimal getConSettleCompletePrjAmt(PayRequestBillInfo payReqInfo) throws Exception {

		// 已完工工程量(已完工)
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
		// 不包括本次,本次在上面单独加取当前单据最新数据,以避免新增或修改时数据错误
		if (payReqInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", payReqInfo.getId().toString(), CompareType.NOTEQUALS));
		}
		view.setFilter(filter);
		PayRequestBillCollection payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

		if (payReqColl != null) {
			for (int i = 0; i < payReqColl.size(); i++) {
				PayRequestBillInfo info = payReqColl.get(i);
				// 已审批
				if (info.getState() == FDCBillStateEnum.AUDITTED) {
					completePrjAmt = completePrjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
				}
			}
		}
		completePrjAmt = FDCHelper.toBigDecimal(completePrjAmt, 2);
		return completePrjAmt;
	}
}
