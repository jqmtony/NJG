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
 * 描述:合同归档
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
			SelectorItemCollection selector = new SelectorItemCollection();//取编码所需属性
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("contractType.*"));
			selector.add(new SelectorItemInfo("curProject.*"));
			selector.add(new SelectorItemInfo("codeType.*"));
			selector.add(new SelectorItemInfo("orgUnit.*"));
			selector.add(new SelectorItemInfo("archivedNumber"));
			selector.add(new SelectorItemInfo("contractPropert"));
			this.cbi = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(id),selector);
			// 如果以前曾今归档过则取出以前归档编号 如果没有则新增归档编号
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
//		设置编码类型
		IContractType ict = ContractTypeFactory.getRemoteInstance();
		IContractCodingType icct = ContractCodingTypeFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		ContractTypeInfo cti = cbi.getContractType();
		String contractId = cti.getId().toString();
		if(cbi.getContractType()!=null){
			cti = ict.getContractTypeInfo("select * where id = '" + contractId + "'");			
			if(cti.getLevel()!=1){
				//取一级合同类别
				String number = cti.getLongNumber();
				number = number.substring(0,number.indexOf("!"));
				cti = ict.getContractTypeInfo("select id where longNumber = '" + number + "'");
			}			
		}		
		//合同类型
		filter.getFilterItems().add(new FilterItemInfo("contractType.id", cti.getId().toString()));
		//合同性质(不包括"所有合同"),所有合同只是为了新增单据状态而设
		filter.getFilterItems().add(new FilterItemInfo("secondType", cbi.getContractPropert().getValue()));//具体的某个合同性质
		//单据状态,归档状态
		filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.STORE_VALUE));
		evi.setFilter(filter);
		ContractCodingTypeCollection cctc = icct.getContractCodingTypeCollection(evi);	
		
		//查找是否定义了某个合同性质(直接合同,三方合同,补充合同)过滤,优先于"所有合同"的合同编码类型	
		if(cctc.size()==0){
			//没有定义明确合同性质指向的合同编码类型,那就再查找是否有定义"所有合同"的合同编码类型	
			evi = new EntityViewInfo();
			filter = new FilterInfo();
			//合同类型
			filter.getFilterItems().add(new FilterItemInfo("contractType.id", cti.getId().toString()));
			//合同性质(不包括"所有合同"),所有合同只是为了新增单据状态而设
			filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));//"所有合同"
			//单据状态,此时合同属于归档状态
			filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.STORE_VALUE));
			evi.setFilter(filter);
			cctc = icct.getContractCodingTypeCollection(evi);
			if(cctc.size()!=0){
				cbi.setCodeType(cctc.get(0));
			}

			if(cctc.size()==0){
				//没有定义明确合同性质指向的合同编码类型,那就再查找是否有定义"所有合同"的合同编码类型	
				evi = new EntityViewInfo();
				filter = new FilterInfo();
				//合同类型
				filter.getFilterItems().add(new FilterItemInfo("contractType.id", null));
				//合同性质(不包括"所有合同"),所有合同只是为了新增单据状态而设
				filter.getFilterItems().add(new FilterItemInfo("secondType",cbi.getContractPropert().getValue()));//"所有合同"
				//单据状态,此时合同属于归档状态
				filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.STORE_VALUE));
				evi.setFilter(filter);
				cctc = icct.getContractCodingTypeCollection(evi);
				if(cctc.size()!=0){
					cbi.setCodeType(cctc.get(0));
				}
			}
			
			if(cctc.size()==0){
				//没有定义明确合同性质指向的合同编码类型,那就再查找是否有定义"所有合同"的合同编码类型	
				evi = new EntityViewInfo();
				filter = new FilterInfo();
				//合同类型
				filter.getFilterItems().add(new FilterItemInfo("contractType.id", null));
				//合同性质(不包括"所有合同"),所有合同只是为了新增单据状态而设
				filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));//"所有合同"
				//单据状态,此时合同属于归档状态
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
		
		
		if(cctc.size()!=0){//必须要有,不然编码规则端报空指针			
			//获取编码
			String orgId = cbi.getOrgUnit().getId().toString();//当前应用组织单元为成本中心
			String bindingProperty = "codeType.number";
			if (orgId == null || orgId.trim().length() == 0) {
				// 当前用户所属组织不存在时，缺省实现是用集团的
				orgId = OrgConstants.DEF_CU_ID;
			}
			boolean flag = false;
			String number = "";	
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			/* 是否启用编码规则 */
				if (iCodingRuleManager.isExist(cbi, orgId, bindingProperty)) {
					flag = true;						
					if (iCodingRuleManager.isUseIntermitNumber(cbi, orgId, bindingProperty)) {		// 编码规则中不允许断号					
						// 编码规则中启用了“断号支持”功能，此时只是读取当前最新编码，真正的抢号在保存时
						number = iCodingRuleManager.readNumber(cbi, orgId, bindingProperty, "");	//read
						this.txtNewNumber.setEnabled(false);
					} else	if (iCodingRuleManager.isAddView(cbi, orgId, bindingProperty)){			// 编码规则中启用了“新增显示”
						number = iCodingRuleManager.getNumber(cbi, orgId, bindingProperty, "");	   //get
						if(iCodingRuleManager.isModifiable(cbi, orgId, bindingProperty)){//可修改
							this.txtNewNumber.setEnabled(true);
						}else{
							this.txtNewNumber.setEnabled(false);
						}
					} else {//编码规则什么都没选,这里不给编码,后台取号
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
		// 编码是否为空
		if ((this.txtNewNumber.getText() == null 
				|| this.txtNewNumber.getText().trim().equals(""))
				&&(this.txtNewNumber.isEnabled())){
			//编码规则未启用断号情况下,不允许为空
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