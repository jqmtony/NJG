package com.kingdee.eas.fdc.finance.client;

import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.util.FDCNoteDataProvider;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class DeductBillPrintProvider extends FDCNoteDataProvider {
	
	public DeductBillPrintProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}
	public void addMetaDataPK(String ds, IMetaDataPK qpk) {
		// TODO Auto-generated method stub
		super.addMetaDataPK(ds, qpk);
	}
	public IRowSet getMainBillRowSet(R1PrintDataSource ds) throws Exception {
		return super.getMainBillRowSet(ds);
	}
	//币别需要做特殊处理
	 private final BOSObjectType type=(new ContractBillInfo()).getBOSType();
	 
	 private String handleCurrency(String contractId){
	    if(contractId==null){
	    	return null;
	    }
	    try{
		    if(BOSUuid.read(contractId).getType().equals(type)){
		    	ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select currenty.id,currency.name where id='"+contractId+"'");
		    	return contractBillInfo.getCurrency().getName();
		    }else{
		    	PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo("select currenty.id,currency.name where contractId='"+contractId+"'");
		    	return payRequestBillInfo.getCurrency().getName();
		    }
	    }catch (Exception e) {
	    	return null;
		}
	  }
	 //得到合同编码
	 private String getContractNumber(String contractId) {
		 ContractBillInfo info = null;
		 String number="";
			try {
				info =ContractBillFactory
					.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				number=info.getNumber();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return number;
	 }
	 //得到合同名称
	 private String getContractName(String contractId) {
		 ContractBillInfo info = null;
		 String name="";
			try {
				info =ContractBillFactory
					.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				name=info.getName();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return name;
	}
	public IRowSet getOtherSubRowSet(R1PrintDataSource ds) throws Exception {
		IRowSet iRowSet = getMainBillRowSet(ds);
		try {
			iRowSet.beforeFirst();
			while(iRowSet.next()){
				iRowSet.updateObject("currency", handleCurrency(iRowSet.getString("entrys.contractId")));
				iRowSet.updateObject("entrys.contractNumber", getContractNumber(iRowSet.getString("entrys.contractId")));
				iRowSet.updateObject("entrys.contractName", getContractName(iRowSet.getString("entrys.contractId")));
			}
			iRowSet.beforeFirst();
		} catch (Exception e) {
		}
		return iRowSet;
	}
}
