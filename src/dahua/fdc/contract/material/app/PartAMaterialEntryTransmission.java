package com.kingdee.eas.fdc.material.app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialPurchasingCollection;
import com.kingdee.eas.basedata.master.material.MaterialPurchasingFactory;
import com.kingdee.eas.basedata.master.material.UsedStatusEnum;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.PartAMaterialEntryCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryFactory;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.fdc.material.PartAMaterialInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;

/**
 * ������ϸ��¼���� ����ת���ӿ�
 * @author owen_wen 2010-12-16 
 */

public class PartAMaterialEntryTransmission extends AbstractDataTransmission {

	private static final Logger logger = CoreUIObject.getLogger(PartAMaterialEntryTransmission.class);
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return PartAMaterialEntryFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			logger.debug(e.getMessage());
			throw new TaskExternalException(e.getMessage(), e);
		}		
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		checkMaterialIsExsit(hsData, ctx);		
		checkQuantity(hsData);		
		checkContractIsExist(hsData, ctx); 
		checkContractIsAudited(hsData.get("FMainContractBill_number").toString(), ctx);
		checkArriveDate(hsData);
		return null;
	}

	/**
	 * ���������1)�Ƿ�Ϊ�գ�2)�Ƿ��ʽ����ȷ������ת����double
	 * @param hsData
	 * @throws TaskExternalException
	 * @author owen_wen 2010-12-24
	 */
	private void checkQuantity(Hashtable hsData) throws TaskExternalException {
		if (hsData.get("FQuantity").toString() != null && 
				hsData.get("FQuantity").toString().length() == 0)
			throw new TaskExternalException("��������Ϊ�գ�");
		else{
			try {
				Double.parseDouble(hsData.get("FQuantity").toString());
			}catch (NumberFormatException e) {
				throw new TaskExternalException("������ʽ����ȷ��");
			}
		}
		
	}
	
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		PartAMaterialEntryInfo info = (PartAMaterialEntryInfo)coreBaseInfo;
		info.setParent((PartAMaterialInfo)getContextParameter().get("parentBill"));
		
		// ���� �ɹ����ۣ�ԭ�ң�������������Ϊ0ʱ����Ҫ�����ϵĲɹ�������ȡ����
		if(FDCNumberHelper.toBigDecimal(info.getOriginalPrice()).compareTo(FDCHelper.ZERO) == 0){
			try {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("Material", info.getMaterial().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("OrgUnit", 
						com.kingdee.eas.util.app.ContextUtil.getCurrentCtrlUnit(ctx).getId().toString()));
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				MaterialPurchasingCollection infoColl =MaterialPurchasingFactory.getLocalInstance(ctx).getMaterialPurchasingCollection(view);
				if (infoColl.size() > 0){
					// ȡ��ǰ��¼��֯����Ӧ�Ĳο���				
					info.setOriginalPrice(FDCNumberHelper.toBigDecimal(infoColl.get(0).getPrice())); 
				}
			} catch (BOSException e) {
				logger.debug(e.getMessage(), e.getCause());
				e.printStackTrace();
			}
		}
		
		// �����������Ϊ�գ��ɹ�������Ҳû���ԣ��ֶ���ֵΪ0
		if (info.getOriginalPrice() == null) 
			info.setOriginalPrice(FDCHelper.ZERO);
		
		// �ɹ����ۣ����ң�= �ɹ����ۣ�ԭ�ң�* ����
		info.setPrice(info.getOriginalPrice().multiply(((PartAMaterialInfo)getContextParameter().get("parentBill")).getContractBill().getExRate()));
		
		// ���  =  �ɹ����ۣ����ң��� ����
		info.setAmount(info.getPrice().multiply(info.getQuantity()));
		
		if (info.getDescription() != null
				&& info.getDescription().length() > 80) // ֻȡǰ80���ַ�
			info.setDescription(info.getDescription().substring(0, 80));
				
		//����Ǹ�������		
		if (this.isSltImportUpdate()) { 	
			FilterInfo filter = new FilterInfo();
			if (info.getMainContractBill() != null){ // �п���Excel��û�к�ͬ����ߺ�ͬ������
				filter.getFilterItems().add(new FilterItemInfo("MainContractBill", info.getMainContractBill().getId().toString()));
			}else {
				filter.getFilterItems().add(new FilterItemInfo("MainContractBill", null));
			}
			filter.getFilterItems().add(new FilterItemInfo("Material", info.getMaterial().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("Parent", info.getParent().getId().toString()));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			
			PartAMaterialEntryCollection infoColl;
			try {
				infoColl = PartAMaterialEntryFactory.getLocalInstance(ctx).getPartAMaterialEntryCollection(view);
				if (infoColl.size() > 0){
					info.setId(infoColl.get(0).getId()); // info����ID������super.submit(info, ctx)�оͻḲ�Ǹ�����
				}
			} catch (BOSException e) {
				logger.debug(e.getMessage(), e.getCause());
				e.printStackTrace();
			}
		}
		
		super.submit(info, ctx);
	}
	
	/**
	 * ����ͬ�Ƿ�����<p>
	 * �����������ú�ͬΪδ����״̬ʱ����״̬Ϊ���桢�ύ��������ʱ��������ʾ��Ϣ�� 
	 * ����ͬ������ʱ�������������ܻ��Զ���У�飬��ʾ��ͬ�����ڡ�
	 * �����������ú�ͬϵͳ�д���ʱ��������������ʾ��Ӧ�ĺ�ͬ����
	 * @author owen_wen 2010-12-16
	 * @throws TaskExternalException 
	 * @throws BOSException 
	 */
	private void checkContractIsAudited(String contractNumber, Context ctx) throws TaskExternalException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", contractNumber));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		try {
			ContractBillCollection contractColl = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
			if (contractColl.size() > 0){
				ContractBillInfo contractInfo = contractColl.get(0);
				if (!FDCBillStateEnum.AUDITTED_VALUE.equals(contractInfo.getState().getValue())){
					throw new TaskExternalException("��ͬδ������");
				}
			}
		} catch (BOSException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * ����ͬ�Ƿ���ڣ�
	 * @throws TaskExternalException 
	 */
	private void checkContractIsExist(Hashtable hsData, Context ctx) throws TaskExternalException{
		try {
			String contractNumber = hsData.get("FMainContractBill_number").toString();
			String contractName = hsData.get("FMainContractBill_name").toString();
			
			if ((StringUtils.isEmpty(contractNumber) && !StringUtils.isEmpty(contractName))){
				throw new TaskExternalException("���ú�ͬ������ֵʱ�����ú�ͬ���벻��Ϊ�ա�");
			}
			if (!StringUtils.isEmpty(contractNumber) && StringUtils.isEmpty(contractName)){
				throw new TaskExternalException("���ú�ͬ������ֵʱ�����ú�ͬ���Ʋ���Ϊ�ա�");
			}
			
			// ֻ�е����ú�ͬ��������ƶ���Ϊ��ʱ�����ж��Ƿ����
			if (!StringUtils.isEmpty(contractNumber) && !StringUtils.isEmpty(contractName)){ 
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("number", contractNumber));
				filter.getFilterItems().add(new FilterItemInfo("name", contractName));
				if (!ContractBillFactory.getLocalInstance(ctx).exists(filter)) {
					throw new TaskExternalException("ϵͳ�в����ڴ˺�ͬ�����ú�ͬ����Ϊ��" + contractNumber 
							+ "���ú�ͬ����Ϊ��" + contractName);
				}
			}
			
		} catch (EASBizException e) {
			logger.debug(e.getMessage(), e.getCause());
			e.printStackTrace();
		} catch (BOSException e) {
			logger.debug(e.getMessage(), e.getCause());
			e.printStackTrace();
		}
	}
		
	/**
	 * У�飺
	 * 1��	Ϊ��ʱ����ʾ�����ϱ��벻��Ϊ�գ�<p>
	 * 2��	���ϱ�����ϵͳ���Ҳ���ƥ��ģ���ʾ����������ϵͳ�в����ڣ� <p>
	 * 3��	����δ��׼������ʱ����ʾ��δ��׼�����ϲ��ܵ��룡 <p>

	 * @param materialNumber ���ϱ���
	 * @param ctx ������
	 */
	private void checkMaterialIsExsit(Hashtable hsData, Context ctx) throws TaskExternalException{
		StringBuffer msg  = new StringBuffer("");
		String materialNumber = hsData.get("FMaterial_number").toString();
		if (StringUtils.isEmpty(materialNumber)){
			msg.append("���ϱ��벻��Ϊ�գ�");
			throw new TaskExternalException(msg.toString());
		}
		
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", materialNumber));
			msg.append("���ϱ��룽" + materialNumber);
			if (!StringUtils.isEmpty(hsData.get("FMaterial_name_l2").toString())){
				filter.getFilterItems().add(new FilterItemInfo("name", hsData.get("FMaterial_name_l2").toString()));
				msg.append("���������ƣ�" + hsData.get("FMaterial_name_l2").toString());
			}
			
			if (!StringUtils.isEmpty(hsData.get("FMaterial_model").toString())) {
				filter.getFilterItems().add(new FilterItemInfo("model", hsData.get("FMaterial_model").toString()));
				msg.append("�����Ϲ���ͺţ�" + hsData.get("FMaterial_model").toString());
			}
			
			if (!StringUtils.isEmpty(hsData.get("FMaterial$baseUnit_name_l2").toString())){
				filter.getFilterItems().add(new FilterItemInfo("baseUnit.name", hsData.get("FMaterial$baseUnit_name_l2").toString()));
				msg.append("�����ϵ�λ��" + hsData.get("FMaterial$baseUnit_name_l2").toString());
			}
			
			if (!MaterialFactory.getLocalInstance(ctx).exists(filter)){
				msg.append("����������ϵͳ�в����ڣ�");
				throw new TaskExternalException(msg.toString());
			}
	
			filter.getFilterItems().clear();
			filter.getFilterItems().add(new FilterItemInfo("number", materialNumber));
			filter.getFilterItems().add(new FilterItemInfo("status", new Integer(UsedStatusEnum.APPROVED_VALUE), CompareType.NOTEQUALS));
			if (MaterialFactory.getLocalInstance(ctx).exists(filter)){
				msg.append("δ��׼�����ϲ��ܵ��룡");
				throw new TaskExternalException(msg.toString());
			}
		} catch (EASBizException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (BOSException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}		
	}
	
	/**
	 * У�鵽�����ڣ�����û�¼��ĸ�ʽ����ȷ�����������쳣��ʾ��Ϣ
	 * @author owen_wen
	 */
	private void checkArriveDate(Hashtable hsData) throws TaskExternalException{
		String arriveDate = hsData.get("FArriveDate").toString();
		if(StringUtils.isEmpty(arriveDate))
		{
			return;
		}
		
		DateFormat df = null;
		if(arriveDate.trim().length() <= "yyyy-MM-dd".length()){ // ���� "yyyy-MM-d"
			df = new SimpleDateFormat("yyyy-MM-dd");
		}else if(arriveDate.trim().length() <= "yyyy-MM-dd  HH:mm".length()){ //���� yyyy-MM-d HH:mm���
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
		}else if(arriveDate.trim().length() <= "yyyy-MM-dd  HH:mm:ss".length()){
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		}else{
			throw new TaskExternalException("�������ڸ�ʽ����ȷ��ȷ���������ڵĵ�Ԫ���ʽΪ�ı��ͣ�����ʹ�ø�ʽ�����ڣ�2010-12-05 ");
		}
		
		try {
			Date d = df.parse(arriveDate);
			((DataToken)hsData.get("FArriveDate")).data = df.format(d);
		} catch (ParseException e) {
			logger.debug(e.getMessage(), e.getCause());
			e.printStackTrace();
			throw new TaskExternalException("�������ڸ�ʽ����ȷ��ȷ���������ڵĵ�Ԫ���ʽΪ�ı��ͣ�����ʹ�ø�ʽ�����ڣ�2010-12-05 ");
		}
			
	}
}
