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
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		��ͬ��������
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
		
		ContractBillInfo mainInfo = this.transmitCon(htable, ctx);//�õ���ͷ����
		if(mainInfo == null){
			FDCTransmissionHelper.isThrow(getResource(ctx, "htbmzxtzbcz"));
		}
		
		//��������
		ContractPayItemInfo payItemInfo = this.transmitPayItem(htable, ctx, mainInfo);
		seq = mainInfo.getPayItems().size()+1;
		payItemInfo.setSeq(seq);
		payItemInfo.setContractbill(mainInfo);
		mainInfo.getPayItems().add(payItemInfo);
		
		//��Լ��֤�𼰷�������
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
	
	//�ύ
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
			//��Լ��֤�𼰷�������
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
	
	//��ͬ��
	public ContractBillInfo transmitCon(Hashtable htable, Context ctx)
	throws TaskExternalException {
		
		ContractBillInfo info = null;//��ͬ
		//ȡֵ
		String FCodingNumber = FDCTransmissionHelper.getFieldValue(htable,"FCodingNumber");//*��ͬ����
        //��ʽ�ж�  �Ƿ�Ϸ�   �Ƿ�Ϊ��  �Ƿ񳬹�����
		FDCTransmissionHelper.valueFormat(getResource(ctx,"htbm"), FCodingNumber, "String",true, 80);//��ͬ����
		//�ж����������ݿ��е����
		try {//��ͬʵ��
			info = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("number", FCodingNumber)).get(0);
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
		//����ֵ
		return info;
	}
	
	//��������  ��¼
	public ContractPayItemInfo transmitPayItem(Hashtable htable, Context ctx, ContractBillInfo cbillinfo)
	throws TaskExternalException {

		ContractPayItemInfo info = new ContractPayItemInfo();//��������2

		//ȡֵ
		String FPayItems_payItemDate = FDCTransmissionHelper.getFieldValue(htable,"FPayItems_payItemDate");//��������
		String paymentType_number = FDCTransmissionHelper.getFieldValue(htable,"FPayItems$paymentType_number");//*�������ͱ���
		String FPayItems_payCondition = FDCTransmissionHelper.getFieldValue(htable,"FPayItems_payCondition");//*��������
		
		String FPayScale = FDCTransmissionHelper.getFieldValue(htable,"FPayScale");//*�������
		FPayScale = FPayScale.replace('%', ' ').trim();
		
		String FPayItems_amount = FDCTransmissionHelper.getFieldValue(htable,"FPayItems_amount");//*������(ԭ��)
		String FPayItems_desc = FDCTransmissionHelper.getFieldValue(htable,"FPayItems_desc");//�������ע
	
        //��ʽ�ж�  �Ƿ�Ϸ�   �Ƿ�Ϊ��  �Ƿ񳬹�����
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fukuanshiqingriq"), FPayItems_payItemDate, "Date", false, -1);//������������
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fukuanleixingbianma"), paymentType_number, "String", true, 80);//�������ͱ���
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fukuantiaojian"), FPayItems_payCondition, "String", true, 200);//��������
		FDCTransmissionHelper.bdValueFormat(getResource(ctx,"fukuanbili"), FPayScale, true, 17, 2);//�������
		FDCTransmissionHelper.bdValueFormat(getResource(ctx,"fukuanjineyuanbi"), FPayItems_amount, true, 17, 2);//������(ԭ��)
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fukuanshixiangbeizhu"), FPayItems_desc, "String", false, 200);//�������ע
		
		//�ж����������ݿ��е����
		PaymentTypeInfo ptinfo = null;//�������ͱ���
		BigDecimal ba1 = null,ba11=null;//���� ���   ����
		
		try {
			BigDecimal ba = cbillinfo.getOriginalAmount();  //ԭ�ҽ��
			
			//�������ͱ���
			ptinfo = PaymentTypeFactory.getLocalInstance(ctx).getPaymentTypeCollection(this.getView("number", paymentType_number)).get(0);
			//�Ҳ��� �������ͱ���  ��ʾ
			if(ptinfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillJJTKImport_FpaymentType_number")+paymentType_number+getResource(ctx,"ContractBillImport_fCur"));
			}
			
			//�������5 FPayScale
			double b = FDCTransmissionHelper.strToDouble(FPayScale);
		    if (b < 0 || b > 100) {
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillJJTKImport_FPayScale"));
			}
		    //������(ԭ��)��Ϊ�յ�ʱ���Զ���� �������       ������ / ��ͬԭ�ҽ��
			if (FPayItems_amount != null && !FPayItems_amount.trim().equals("")) {
				if(ba.compareTo(new BigDecimal(0))==0){//ԭ�ҽ��Ϊ0   
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractOriginalAmountiszreo"));
				}else{
					ba1 = FDCTransmissionHelper.strToBigDecimal(FPayItems_amount);//������
					ba11 = ba1.divide(ba, 2,BigDecimal.ROUND_HALF_UP);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
		
		
		//����ֵ
		info.setPayItemDate(FDCTransmissionHelper.strToDate(FPayItems_payItemDate));//������������2
		info.setPaymentType(ptinfo);//�������ͱ���3 paymentType_number
		info.setPayCondition(FPayItems_payCondition);//��������4  FPayItems_payCondition
		info.setDesc(FPayItems_desc);//�������ע", FPayItems_desc
		info.setAmount(ba1);//������(ԭ��) FPayItems_amount
		info.setProp(ba11);

		return info;
	}
	
	//��Լ��֤�𼰷������ַ�¼
	public ContractBailEntryInfo transmitBail(Hashtable htable, Context ctx , ContractBillInfo cbillinfo)
	throws TaskExternalException {
		
		//ContractBailInfo info = new ContractBailInfo();//��Լ��֤�𼰷�������2
		ContractBailEntryInfo entryinfo = new ContractBailEntryInfo();//��Լ��֤�𼰷������ַ�¼3
		
		//ȡֵ
		String FBail$entry_bailDate = FDCTransmissionHelper.getFieldValue(htable,"FBail$entry_bailDate");//*��Լ��֤�𼰷�������
		String entry_bailConditon = FDCTransmissionHelper.getFieldValue(htable,"FBail$entry_bailConditon");//��������
		
		String entry_prop = FDCTransmissionHelper.getFieldValue(htable,"FBail$entry_prop");//*��������
		entry_prop = entry_prop.replaceAll("%", "");
		
		String entry_amount = FDCTransmissionHelper.getFieldValue(htable,"FBail$entry_amount");//*�������ԭ��
		String entry_desc = FDCTransmissionHelper.getFieldValue(htable,"FBail$entry_desc");//������ע

        //��ʽ�ж�  �Ƿ�Ϸ�   �Ƿ�Ϊ��  �Ƿ񳬹�����
		FDCTransmissionHelper.valueFormat(getResource(ctx,"luyuebaozhengjinjiriqi"), FBail$entry_bailDate, "Date", true, -1);//��Լ��֤�𼰷�������
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fabhuabtriaojian"), entry_bailConditon, "String", false, 200);//��������
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fanhuanbili"), entry_prop, "Double", true, -1);//��������
		FDCTransmissionHelper.bdValueFormat(getResource(ctx,"fanhuanjineyuanbi"), entry_amount, true, 17, 2);//�������ԭ��
		FDCTransmissionHelper.valueFormat(getResource(ctx,"fanhuanbeizhu"), entry_desc, "String", false, 200);//������ע
		
		//�ж����������ݿ��е����
		BigDecimal ba2 = null,ba22=null;//���� ���   ����
		
		BigDecimal ba = cbillinfo.getOriginalAmount();  //ԭ�ҽ��
		//�������� entry_prop
	    double b = FDCTransmissionHelper.strToDouble(entry_prop);
	    if (b < 0 || b > 100) {
			FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillJJTKImport_entry_prop"));
		}
	    //������Ϊ�յ�ʱ���Զ������������                       ������� / ��ͬԭ�ҽ��
        if (entry_amount != null && !entry_amount.trim().equals("")) {
        	if(ba.compareTo(new BigDecimal(0))==0){//ԭ�ҽ��Ϊ0   
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractOriginalAmountiszreo"));
			}else{
				ba2 = FDCTransmissionHelper.strToBigDecimal(entry_amount);//�������
				ba22 = ba2.divide(ba, 2,BigDecimal.ROUND_HALF_UP);
			}
		}
		 
		//����ֵ
        entryinfo.setBailDate(FDCTransmissionHelper.strToDate(FBail$entry_bailDate));//��Լ��֤�𼰷�������  FBail$entry_bailDate
        entryinfo.setBailConditon(entry_bailConditon);//�������� entry_bailConditon
        entryinfo.setProp(ba22);//�������� entry_prop
        entryinfo.setAmount(ba2);//�������ԭ�� entry_amount
        entryinfo.setDesc(entry_desc);//������ע entry_desc
        //entryinfo.setParent(info);

		return entryinfo;
	}
	
	//������ͼ
	private EntityViewInfo getView(String sqlcolnum,Object item){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	//������ͼ2
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
	 * �õ���Դ�ļ�
	 * @author ֣��Ԫ
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
