package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBailEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBailInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;
/**						
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		合同经济条款
 * 
 */
public class ContractBillJJTKImport extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.contract.ContractResource";
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		
		ICoreBase factory = null;
		try {
			factory = ContractBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable htable, Context ctx)
			throws TaskExternalException {
		int seq = 0;
		
		ContractBillInfo mainInfo = this.transmitCon(htable, ctx);//拿到单头对象
		if(mainInfo == null){
			FDCTransmissionHelper.isThrow(getResource(ctx, "htbmzxtzbcz"));
		}
		
		//付款事项
		ContractPayItemInfo payItemInfo = this.transmitPayItem(htable, ctx, mainInfo);
		seq = mainInfo.getPayItems().size()+1;
		payItemInfo.setSeq(seq);
		payItemInfo.setContractbill(mainInfo);
		mainInfo.getPayItems().add(payItemInfo);
		
		//履约保证金及返还部分
		ContractBailEntryInfo bailEntryInfo = this.transmitBail(htable, ctx, mainInfo);
		ContractBailInfo bailInfo = null;
		if(mainInfo.getBail()==null){
			seq = 1;
			bailInfo = new ContractBailInfo();
		}else{
			seq = mainInfo.getBail().getEntry().size()+1;
			bailInfo = mainInfo.getBail();
		}
		bailEntryInfo.setSeq(seq);
		bailEntryInfo.setParent(bailInfo);
		mainInfo.setBail(bailInfo);
		mainInfo.getBail().getEntry().add(bailEntryInfo);
		
		return mainInfo;
	}
	
	//提交
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof ContractBillInfo == false) {
			return;
		}	
		try {
			ContractBillInfo billBase = (ContractBillInfo) coreBaseInfo;
			ContractBillInfo info = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("number", billBase.getNumber())).get(0);
			String id = "";
			if(info != null){
				id = info.getId().toString();
			}
			//履约保证金及返还部分
			ContractBailEntryInfo conBailEntryInfo= billBase.getBail().getEntry().get(0);
			
			if (StringUtil.isEmptyString(id)) {
				ContractBailEntryFactory.getLocalInstance(ctx).addnew(conBailEntryInfo);
				getController(ctx).addnew(coreBaseInfo);
				
			} else {
				coreBaseInfo.setId(BOSUuid.read(id));
				ContractBailEntryFactory.getLocalInstance(ctx).addnew(conBailEntryInfo);
				getController(ctx).update(new ObjectUuidPK(id), coreBaseInfo);
			}

		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}

	}
	
	//合同单
	public ContractBillInfo transmitCon(Hashtable htable, Context ctx)
	throws TaskExternalException {
		
		ContractBillInfo info = null;//合同
		//取值
		String FCodingNumber = FDCTransmissionHelper.getFieldValue(htable,"FCodingNumber");//*合同编码
        //格式判断  是否合法   是否为空  是否超过长度
		FDCTransmissionHelper.valueFormat(getResource(ctx,"htbm"), FCodingNumber, "String",true, 80);//合同编码
		//判断数据在数据空中的情况
		try {//合同实体
			info = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("number", FCodingNumber)).get(0);
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
		//设置值
		return info;
	}
	
	//付款事项  分录
	public ContractPayItemInfo transmitPayItem(Hashtable htable, Context ctx, ContractBillInfo cbillinfo)
	throws TaskExternalException {

		ContractPayItemInfo info = new ContractPayItemInfo();//付款事相2

		//取值
		String FPayItems_payItemDate = FDCTransmissionHelper.getFieldValue(htable,"FPayItems_payItemDate");//付款事相
		String paymentType_number = FDCTransmissionHelper.getFieldValue(htable,"FPayItems$paymentType_number");//*付款类型编码
		String FPayItems_payCondition = FDCTransmissionHelper.getFieldValue(htable,"FPayItems_payCondition");//*付款条件
		
		String FPayScale = FDCTransmissionHelper.getFieldValue(htable,"FPayScale");//*付款比例
		FPayScale = FPayScale.replace('%', ' ').trim();
		
		String FPayItems_amount = FDCTransmissionHelper.getFieldValue(htable,"FPayItems_amount");//*付款金额(原币)
		String FPayItems_desc = FDCTransmissionHelper.getFieldValue(htable,"FPayItems_desc");//付款事项备注
	
        //格式判断  是否合法   是否为空  是否超过长度
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fukuanshiqingriq"), FPayItems_payItemDate, "Date", false, -1);//付款事项日期
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fukuanleixingbianma"), paymentType_number, "String", true, 80);//付款类型编码
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fukuantiaojian"), FPayItems_payCondition, "String", true, 200);//付款条件
		FDCTransmissionHelper.bdValueFormat(getResource(ctx,"fukuanbili"), FPayScale, true, 17, 2);//付款比例
		FDCTransmissionHelper.bdValueFormat(getResource(ctx,"fukuanjineyuanbi"), FPayItems_amount, true, 17, 2);//付款金额(原币)
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fukuanshixiangbeizhu"), FPayItems_desc, "String", false, 200);//付款事项备注
		
		//判断数据在数据空中的情况
		PaymentTypeInfo ptinfo = null;//付款类型编码
		BigDecimal ba1 = null,ba11=null;//付款 金额   比率
		
		try {
			BigDecimal ba = cbillinfo.getOriginalAmount();  //原币金额
			
			//付款类型编码
			ptinfo = PaymentTypeFactory.getLocalInstance(ctx).getPaymentTypeCollection(this.getView("number", paymentType_number)).get(0);
			//找不到 付款类型编码  提示
			if(ptinfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillJJTKImport_FpaymentType_number")+paymentType_number+getResource(ctx,"ContractBillImport_fCur"));
			}
			
			//付款比例5 FPayScale
			double b = FDCTransmissionHelper.strToDouble(FPayScale);
		    if (b < 0 || b > 100) {
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillJJTKImport_FPayScale"));
			}
		    //付款金额(原币)不为空的时候，自动算出 付款比例       付款金额 / 合同原币金额
			if (FPayItems_amount != null && !FPayItems_amount.trim().equals("")) {
				if(ba.compareTo(new BigDecimal(0))==0){//原币金额为0   
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractOriginalAmountiszreo"));
				}else{
					ba1 = FDCTransmissionHelper.strToBigDecimal(FPayItems_amount);//付款金额
					ba11 = ba1.divide(ba, 2,BigDecimal.ROUND_HALF_UP);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
		
		
		//设置值
		info.setPayItemDate(FDCTransmissionHelper.strToDate(FPayItems_payItemDate));//付款事项日期2
		info.setPaymentType(ptinfo);//付款类型编码3 paymentType_number
		info.setPayCondition(FPayItems_payCondition);//付款条件4  FPayItems_payCondition
		info.setDesc(FPayItems_desc);//付款事项备注", FPayItems_desc
		info.setAmount(ba1);//付款金额(原币) FPayItems_amount
		info.setProp(ba11);

		return info;
	}
	
	//履约保证金及返还部分分录
	public ContractBailEntryInfo transmitBail(Hashtable htable, Context ctx , ContractBillInfo cbillinfo)
	throws TaskExternalException {
		
		//ContractBailInfo info = new ContractBailInfo();//履约保证金及返还部分2
		ContractBailEntryInfo entryinfo = new ContractBailEntryInfo();//履约保证金及返还部分分录3
		
		//取值
		String FBail$entry_bailDate = FDCTransmissionHelper.getFieldValue(htable,"FBail$entry_bailDate");//*履约保证金及返还日期
		String entry_bailConditon = FDCTransmissionHelper.getFieldValue(htable,"FBail$entry_bailConditon");//返还条件
		
		String entry_prop = FDCTransmissionHelper.getFieldValue(htable,"FBail$entry_prop");//*返还比例
		entry_prop = entry_prop.replaceAll("%", "");
		
		String entry_amount = FDCTransmissionHelper.getFieldValue(htable,"FBail$entry_amount");//*返还金额原币
		String entry_desc = FDCTransmissionHelper.getFieldValue(htable,"FBail$entry_desc");//返还备注

        //格式判断  是否合法   是否为空  是否超过长度
		FDCTransmissionHelper.valueFormat(getResource(ctx,"luyuebaozhengjinjiriqi"), FBail$entry_bailDate, "Date", true, -1);//履约保证金及返还日期
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fabhuabtriaojian"), entry_bailConditon, "String", false, 200);//返还条件
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fanhuanbili"), entry_prop, "Double", true, -1);//返还比例
		FDCTransmissionHelper.bdValueFormat(getResource(ctx,"fanhuanjineyuanbi"), entry_amount, true, 17, 2);//返还金额原币
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fanhuanbeizhu"), entry_desc, "String", false, 200);//返还备注
		
		//判断数据在数据空中的情况
		BigDecimal ba2 = null,ba22=null;//返还 金额   比率
		
		BigDecimal ba = cbillinfo.getOriginalAmount();  //原币金额
		//返还比例 entry_prop
	    double b = FDCTransmissionHelper.strToDouble(entry_prop);
	    if (b < 0 || b > 100) {
			FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillJJTKImport_entry_prop"));
		}
	    //返还金额不为空的时候，自动算出返还比例                       返还金额 / 合同原币金额
        if (entry_amount != null && !entry_amount.trim().equals("")) {
        	if(ba.compareTo(new BigDecimal(0))==0){//原币金额为0   
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractOriginalAmountiszreo"));
			}else{
				ba2 = FDCTransmissionHelper.strToBigDecimal(entry_amount);//返还金额
				ba22 = ba2.divide(ba, 2,BigDecimal.ROUND_HALF_UP);
			}
		}
		 
		//设置值
        entryinfo.setBailDate(FDCTransmissionHelper.strToDate(FBail$entry_bailDate));//履约保证金及返还日期  FBail$entry_bailDate
        entryinfo.setBailConditon(entry_bailConditon);//返还条件 entry_bailConditon
        entryinfo.setProp(ba22);//返还比例 entry_prop
        entryinfo.setAmount(ba2);//返还金额原币 entry_amount
        entryinfo.setDesc(entry_desc);//返还备注 entry_desc
        //entryinfo.setParent(info);

		return entryinfo;
	}
	
	//返回视图
	private EntityViewInfo getView(String sqlcolnum,Object item){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	//返回视图2
	private EntityViewInfo getViewList(Object[] str){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		int len = str.length;
		if(len%2!=0){
			return view;
		}
		for(int i=0; i<str.length; i+=2){
			filter.getFilterItems().add(new FilterItemInfo(str[i].toString(),str[i+1],CompareType.EQUALS));
		}
	    
        view.setFilter(filter);
		return view;
	}
	
	
	/**
	 * 得到资源文件
	 * @author 郑杰元
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
