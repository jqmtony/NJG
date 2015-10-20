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
 * 合同基本资料服务端辅助类
 * @author liupd
 *
 */
public class ContractBaseDataHelper {

	/**
	 * 将合同/无文本合同同步到合同基本资料
	 * @param ctx
	 * @param isNoText 是否无文本合同
	 * @param contractId 合同/无文本合同ID
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
		
		selectors.add("contractType.id");	//增加合同类型以便工作流中可选。。ken_liu for R131126-0233
		
		ContractTypeInfo conType = null;
		CtrlUnitInfo cu = null;
		//增加状态字段
		selectors.add("state");
		String number = null;
		String name = null;
		BOSUuid fiUnitID = null;
		FDCBillStateEnum state = null;
		/**
		 * 
		 * 由于合同编码是以工程项目隔离，而合同基础资料作为核算项目又是以公司隔离，这样在合同基础资料checkNumberDup
		 * 时候就有可能产生重复编码，故编码以“合同编码_工程项目编码”格式，避免重复。
		 *  
		 */
		if(isNoText) { //无文本合同
			ContractWithoutTextInfo contractWithoutTextInfo = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(contractId), selectors);
			number = contractWithoutTextInfo.getNumber()+"_"+contractWithoutTextInfo.getOrgUnit().getNumber();
			//R110808-0353预算项目从基础资料导入合同，长编码带点则不能导入，提示父节点不存在。 

			name = contractWithoutTextInfo.getName();
			fiUnitID = contractWithoutTextInfo.getCurProject().getFullOrgUnit().getId();
			
			cu = contractWithoutTextInfo.getCurProject().getCU();
			state = contractWithoutTextInfo.getState();
			conType = contractWithoutTextInfo.getContractType();
		}else { //合同
			ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId), selectors);
			number = contractBillInfo.getNumber()+"_"+contractBillInfo.getOrgUnit().getNumber();
			name = contractBillInfo.getName();
			fiUnitID = contractBillInfo.getCurProject().getFullOrgUnit().getId();
			
			cu = contractBillInfo.getCurProject().getCU();
			state = contractBillInfo.getState();
			conType = contractBillInfo.getContractType();
		}

		// R140220-0246:"战略管理_预算管理_预算项目"中，"-"、"."、"!"是保留字符 by skyiter_wang 2014-02-20
		if (FdcStringUtil.isNotBlank(number)) {
			number = number.replace('_', '-');
			number = number.replace('.', '-');
			number = number.replace('!', '-');
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
		boolean exist = ContractBaseDataFactory.getLocalInstance(ctx).exists(filter);
		
		
		if(exist) { //已存在该合同ID的记录，比较编码和名称，如有一个不同，则更新
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
					
					//用为核算项目的基础资料,编码或者名称修改后,需要调用此方法更新横表
				    AssistUtil.updateAssist(ctx,info.getId().toString(),((ContractBaseDataInfo)info).getBOSType());
				}
			}
			else {
				throw new FDCException(FDCException.TOOMANYRECORDEXCEPTION);
			}
			
		}
		else { //不存在记录，新增
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
