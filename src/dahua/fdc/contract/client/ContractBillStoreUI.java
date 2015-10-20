/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.IContractCodingType;
import com.kingdee.eas.fdc.basedata.IContractType;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.IContractBill;

/**
 * ����:��ͬ�鵵
 * 
 * @author jackwang date:2007-3-7
 * @version EAS5.3
 */
public class ContractBillStoreUI extends AbstractContractBillStoreUI {
	private static final Logger logger = CoreUIObject.getLogger(ContractBillStoreUI.class);

	private String id = null;

	private String oldNumber = null;

	private String contractTypeId = null;

	private String projectId = null;

	private ContractBillInfo cbi = null;

	/**
	 * output class constructor
	 */
	public ContractBillStoreUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		boolean isUpdateConNo = Boolean.valueOf(
				getUIContext().get("isUpdateConNo").toString()).booleanValue();
		if (this.getUIContext().get("id") != null) {
			id = this.getUIContext().get("id").toString();
			SelectorItemCollection selector = new SelectorItemCollection();//ȡ������������
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("contractType.*"));
			selector.add(new SelectorItemInfo("curProject.*"));
			selector.add(new SelectorItemInfo("codeType.*"));
			selector.add(new SelectorItemInfo("orgUnit.*"));
			selector.add(new SelectorItemInfo("archivedNumber"));
			selector.add(new SelectorItemInfo("contractPropert"));
			this.cbi = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(id),selector);
			// �����ǰ����鵵����ȡ����ǰ�鵵��� ���û���������鵵���
			if(cbi.getArchivedNumber()==null){
				this.txtNewNumber.setEnabled(true);
				setNewNumber(cbi);				
			}else{
				if(isUpdateConNo){
					this.txtNewNumber.setEnabled(true);
				}else{
					this.txtNewNumber.setEnabled(false);
				}
				this.txtNewNumber.setText(cbi.getArchivedNumber());
			}
		}
		if (this.getUIContext().get("number") != null) {
			oldNumber = this.getUIContext().get("number").toString();
			this.txtOldNumber.setText(oldNumber);
		}
	}

	private void setNewNumber(ContractBillInfo cbi) throws BOSException, CodingRuleException, EASBizException {
//		���ñ�������
		IContractType ict = ContractTypeFactory.getRemoteInstance();
		IContractCodingType icct = ContractCodingTypeFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		ContractTypeInfo cti = cbi.getContractType();
		String contractId = cti.getId().toString();
		if(cbi.getContractType()!=null){
			cti = ict.getContractTypeInfo("select * where id = '" + contractId + "'");			
			if(cti.getLevel()!=1){
				//ȡһ����ͬ���
				String number = cti.getLongNumber();
				number = number.substring(0,number.indexOf("!"));
				cti = ict.getContractTypeInfo("select id where longNumber = '" + number + "'");
			}			
		}		
		//��ͬ����
		filter.getFilterItems().add(new FilterItemInfo("contractType.id", cti.getId().toString()));
		//��ͬ����(������"���к�ͬ"),���к�ֻͬ��Ϊ����������״̬����
		filter.getFilterItems().add(new FilterItemInfo("secondType", cbi.getContractPropert().getValue()));//�����ĳ����ͬ����
		//����״̬,�鵵״̬
		filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.STORE_VALUE));
		evi.setFilter(filter);
		ContractCodingTypeCollection cctc = icct.getContractCodingTypeCollection(evi);	
		
		//�����Ƿ�����ĳ����ͬ����(ֱ�Ӻ�ͬ,������ͬ,�����ͬ)����,������"���к�ͬ"�ĺ�ͬ��������	
		if(cctc.size()==0){
			//û�ж�����ȷ��ͬ����ָ��ĺ�ͬ��������,�Ǿ��ٲ����Ƿ��ж���"���к�ͬ"�ĺ�ͬ��������	
			evi = new EntityViewInfo();
			filter = new FilterInfo();
			//��ͬ����
			filter.getFilterItems().add(new FilterItemInfo("contractType.id", cti.getId().toString()));
			//��ͬ����(������"���к�ͬ"),���к�ֻͬ��Ϊ����������״̬����
			filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));//"���к�ͬ"
			//����״̬,��ʱ��ͬ���ڹ鵵״̬
			filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.STORE_VALUE));
			evi.setFilter(filter);
			cctc = icct.getContractCodingTypeCollection(evi);
			if(cctc.size()!=0){
				cbi.setCodeType(cctc.get(0));
			}

			if(cctc.size()==0){
				//û�ж�����ȷ��ͬ����ָ��ĺ�ͬ��������,�Ǿ��ٲ����Ƿ��ж���"���к�ͬ"�ĺ�ͬ��������	
				evi = new EntityViewInfo();
				filter = new FilterInfo();
				//��ͬ����
				filter.getFilterItems().add(new FilterItemInfo("contractType.id", null));
				//��ͬ����(������"���к�ͬ"),���к�ֻͬ��Ϊ����������״̬����
				filter.getFilterItems().add(new FilterItemInfo("secondType",cbi.getContractPropert().getValue()));//"���к�ͬ"
				//����״̬,��ʱ��ͬ���ڹ鵵״̬
				filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.STORE_VALUE));
				evi.setFilter(filter);
				cctc = icct.getContractCodingTypeCollection(evi);
				if(cctc.size()!=0){
					cbi.setCodeType(cctc.get(0));
				}
			}
			
			if(cctc.size()==0){
				//û�ж�����ȷ��ͬ����ָ��ĺ�ͬ��������,�Ǿ��ٲ����Ƿ��ж���"���к�ͬ"�ĺ�ͬ��������	
				evi = new EntityViewInfo();
				filter = new FilterInfo();
				//��ͬ����
				filter.getFilterItems().add(new FilterItemInfo("contractType.id", null));
				//��ͬ����(������"���к�ͬ"),���к�ֻͬ��Ϊ����������״̬����
				filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));//"���к�ͬ"
				//����״̬,��ʱ��ͬ���ڹ鵵״̬
				filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.STORE_VALUE));
				evi.setFilter(filter);
				cctc = icct.getContractCodingTypeCollection(evi);
				if(cctc.size()!=0){
					cbi.setCodeType(cctc.get(0));
				}
			}
		}else{
			cbi.setCodeType(cctc.get(0));
		}
		
		
		if(cctc.size()!=0){//����Ҫ��,��Ȼ�������˱���ָ��			
			//��ȡ����
			String orgId = cbi.getOrgUnit().getId().toString();//��ǰӦ����֯��ԪΪ�ɱ�����
			String bindingProperty = "codeType.number";
			if (orgId == null || orgId.trim().length() == 0) {
				// ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
				orgId = OrgConstants.DEF_CU_ID;
			}
			boolean flag = false;
			String number = "";	
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			/* �Ƿ����ñ������ */
				if (iCodingRuleManager.isExist(cbi, orgId, bindingProperty)) {
					flag = true;						
					if (iCodingRuleManager.isUseIntermitNumber(cbi, orgId, bindingProperty)) {		// ��������в�����Ϻ�					
						// ��������������ˡ��Ϻ�֧�֡����ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
						number = iCodingRuleManager.readNumber(cbi, orgId, bindingProperty, "");	//read
						this.txtNewNumber.setEnabled(false);
					} else	if (iCodingRuleManager.isAddView(cbi, orgId, bindingProperty)){			// ��������������ˡ�������ʾ��
						number = iCodingRuleManager.getNumber(cbi, orgId, bindingProperty, "");	   //get
						if(iCodingRuleManager.isModifiable(cbi, orgId, bindingProperty)){//���޸�
							this.txtNewNumber.setEnabled(true);
						}else{
							this.txtNewNumber.setEnabled(false);
						}
					} else {//�������ʲô��ûѡ,���ﲻ������,��̨ȡ��
						this.txtNewNumber.setEnabled(false);
					}	
			}
			if (flag) {			
				this.txtNewNumber.setText(number);
			}
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}


	/**
	 * output actionDo_actionPerformed
	 */
	public void actionDo_actionPerformed(ActionEvent e) throws Exception {
		// �����Ƿ�Ϊ��
		if ((this.txtNewNumber.getText() == null 
				|| this.txtNewNumber.getText().trim().equals(""))
				&&(this.txtNewNumber.isEnabled())){
			//�������δ���öϺ������,������Ϊ��
				throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);			
		} else {
			IContractBill icb = ContractBillFactory.getRemoteInstance();
			boolean flag = false;
			flag = icb.contractBillStore(this.cbi, this.txtNewNumber.getText());
			if (flag) {				
				FDCClientUtils.showOprtOK(this);
				this.getUIWindow().close();
			}
		}

	}

	/**
	 * output actionRe_actionPerformed
	 */
	public void actionRe_actionPerformed(ActionEvent e) throws Exception {
		//        super.actionRe_actionPerformed(e);
		this.getUIWindow().close();
	}
}