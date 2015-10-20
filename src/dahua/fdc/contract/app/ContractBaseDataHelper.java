package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.auxacct.app.AssistUtil;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCException;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;

/**
 * ��ͬ�������Ϸ���˸�����
 * @author liupd
 *
 */
public class ContractBaseDataHelper {

	/**
	 * ����ͬ/���ı���ͬͬ������ͬ��������
	 * @param ctx
	 * @param isNoText �Ƿ����ı���ͬ
	 * @param contractId ��ͬ/���ı���ͬID
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void synToContractBaseData(Context ctx, boolean isNoText, String contractId) throws BOSException, EASBizException {
		
		if(contractId == null) return;
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("orgUnit.number");
		selectors.add("curProject.CU.id");
		selectors.add("curProject.fullOrgUnit.id");
		
		selectors.add("contractType.id");	//���Ӻ�ͬ�����Ա㹤�����п�ѡ����ken_liu for R131126-0233
		
		ContractTypeInfo conType = null;
		CtrlUnitInfo cu = null;
		//����״̬�ֶ�
		selectors.add("state");
		String number = null;
		String name = null;
		BOSUuid fiUnitID = null;
		FDCBillStateEnum state = null;
		/**
		 * 
		 * ���ں�ͬ�������Թ�����Ŀ���룬����ͬ����������Ϊ������Ŀ�����Թ�˾���룬�����ں�ͬ��������checkNumberDup
		 * ʱ����п��ܲ����ظ����룬�ʱ����ԡ���ͬ����_������Ŀ���롱��ʽ�������ظ���
		 *  
		 */
		if(isNoText) { //���ı���ͬ
			ContractWithoutTextInfo contractWithoutTextInfo = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(contractId), selectors);
			number = contractWithoutTextInfo.getNumber()+"_"+contractWithoutTextInfo.getOrgUnit().getNumber();
			//R110808-0353Ԥ����Ŀ�ӻ������ϵ����ͬ��������������ܵ��룬��ʾ���ڵ㲻���ڡ� 

			name = contractWithoutTextInfo.getName();
			fiUnitID = contractWithoutTextInfo.getCurProject().getFullOrgUnit().getId();
			
			cu = contractWithoutTextInfo.getCurProject().getCU();
			state = contractWithoutTextInfo.getState();
			conType = contractWithoutTextInfo.getContractType();
		}else { //��ͬ
			ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId), selectors);
			number = contractBillInfo.getNumber()+"_"+contractBillInfo.getOrgUnit().getNumber();
			name = contractBillInfo.getName();
			fiUnitID = contractBillInfo.getCurProject().getFullOrgUnit().getId();
			
			cu = contractBillInfo.getCurProject().getCU();
			state = contractBillInfo.getState();
			conType = contractBillInfo.getContractType();
		}

		// R140220-0246:"ս�Թ���_Ԥ�����_Ԥ����Ŀ"�У�"-"��"."��"!"�Ǳ����ַ� by skyiter_wang 2014-02-20
		if (FdcStringUtil.isNotBlank(number)) {
			number = number.replace('_', '-');
			number = number.replace('.', '-');
			number = number.replace('!', '-');
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
		boolean exist = ContractBaseDataFactory.getLocalInstance(ctx).exists(filter);
		
		
		if(exist) { //�Ѵ��ڸú�ͬID�ļ�¼���Ƚϱ�������ƣ�����һ����ͬ�������
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("state");
			view.setFilter(filter);
			ContractBaseDataCollection contractBaseDataCollection = ContractBaseDataFactory.getLocalInstance(ctx).getContractBaseDataCollection(view);
			if(contractBaseDataCollection.size() == 1) {
				ContractBaseDataInfo info = contractBaseDataCollection.get(0);
				String number2 = info.getNumber();
				String name2 = info.getName();
				FDCBillStateEnum state2 = info.getState();
				if(number != null && number2 != null && 
						(!name.equals(name2) || !number.equals(number2) || !state.equals(state2))) {
					info.setNumber(number);
					info.setName(name);
					info.setState(state);
					ContractBaseDataFactory.getLocalInstance(ctx).updatePartial(info, view.getSelector());
					
					//��Ϊ������Ŀ�Ļ�������,������������޸ĺ�,��Ҫ���ô˷������º��
				    AssistUtil.updateAssist(ctx,info.getId().toString(),((ContractBaseDataInfo)info).getBOSType());
				}
			}
			else {
				throw new FDCException(FDCException.TOOMANYRECORDEXCEPTION);
			}
			
		}
		else { //�����ڼ�¼������
			ContractBaseDataInfo cbdInfo = new ContractBaseDataInfo();
			cbdInfo.setContractId(contractId);
			cbdInfo.setIsNoText(isNoText);
			cbdInfo.setNumber(number);
			cbdInfo.setName(name);
			cbdInfo.setState(state);
			CompanyOrgUnitInfo fiUnitInfo = new CompanyOrgUnitInfo();
			fiUnitInfo.setId(fiUnitID);
			cbdInfo.setCompany(fiUnitInfo);
			cbdInfo.setCU(cu);
			cbdInfo.setContractType(conType);
			ContractBaseDataFactory.getLocalInstance(ctx).addnew(cbdInfo);
		}
		
	}

}
