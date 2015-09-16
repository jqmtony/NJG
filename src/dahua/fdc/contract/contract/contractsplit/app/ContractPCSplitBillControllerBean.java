package com.kingdee.eas.fdc.contract.contractsplit.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillEntryCollection;
import com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillEntryFactory;
import com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillInfo;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ContractPCSplitBillControllerBean extends AbstractContractPCSplitBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.contractsplit.app.ContractPCSplitBillControllerBean");

    protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("entry.programmingContract.*");
		sel.add("entry.*");
		sel.add("contractBill.id");
		sel.add("contractBill.contractPropert");
		sel.add("contractChangeBill.id");
		sel.add("contractChangeBill.contractBill.id");
		sel.add("contractChangeSettleBill.id");
		sel.add("contractChangeSettleBill.contractBill.id");
		sel.add("contractSettleBill.id");
		ContractPCSplitBillInfo info=this.getContractPCSplitBillInfo(ctx, pk,sel);
		if(info.getContractBill()!=null){
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractChangeBill.contractBill.id",info.getContractBill().getId().toString()));
			if(this.exists(ctx, filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","存在合同变更指令合约规划拆分，不能进行删除操作！"));
			}
//			filter=new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("contractChangeSettleBill.contractBill.id",info.getContractBill().getId().toString()));
//			if(this.exists(ctx, filter)){
//				throw new EASBizException(new NumericExceptionSubItem("100","存在合同变更确认合约规划拆分，不能进行删除操作！"));
//			}
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractSettleBill.contractBill.id",info.getContractBill().getId().toString()));
			if(this.exists(ctx, filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","存在合同结算合约规划拆分，不能进行删除操作！"));
			}
			if(!ContractPropertyEnum.SUPPLY.equals(info.getContractBill().getContractPropert())){
				try {
					Set supplyContractId=getMainContractId(info.getContractBill().getId().toString(),ctx);
					if(supplyContractId.size()>0){
						filter=new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("contractBill.id",supplyContractId,CompareType.INCLUDE));
						if(this.exists(ctx, filter)){
							throw new EASBizException(new NumericExceptionSubItem("100","存在补充合同合约规划拆分，不能进行删除操作！"));
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		super._delete(ctx, pk);
		updatePC(ctx,info,false);
	}
	protected Set getMainContractId(String billId,Context ctx) throws BOSException, SQLException{
		Set supplyContractId=new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select con.fid as id from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
		builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
		builder.appendSql(" where  entry.FRowkey='am' and con.fstate='4AUDITTED' and ");
		builder.appendParam(" parent.fid", billId);
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			supplyContractId.add(rowSet.getString("id"));
		}
		return supplyContractId;
	}
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		ContractPCSplitBillInfo info=(ContractPCSplitBillInfo) model;
		
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("entry.programmingContract.*");
		sel.add("entry.*");
		sel.add("contractBill.id");
		sel.add("contractBill.contractPropert");
		sel.add("contractChangeBill.id");
		sel.add("contractChangeBill.contractBill.id");
		sel.add("contractChangeSettleBill.id");
		sel.add("contractChangeSettleBill.contractBill.id");
		sel.add("contractSettleBill.id");
		if(info.getId()!=null){
			ContractPCSplitBillInfo oldInfo=this.getContractPCSplitBillInfo(ctx, new ObjectUuidPK(info.getId()),sel);
			updatePC(ctx,oldInfo,false);
		}
		super._save(ctx, pk, model);
		
		info=this.getContractPCSplitBillInfo(ctx, new ObjectUuidPK(info.getId()),sel);
		updatePC(ctx,info,true);
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		IObjectPK pk=null;
		ContractPCSplitBillInfo info=(ContractPCSplitBillInfo) model;
		
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("entry.programmingContract.*");
		sel.add("entry.*");
		sel.add("contractBill.id");
		sel.add("contractBill.contractPropert");
		sel.add("contractChangeBill.id");
		sel.add("contractChangeBill.contractBill.id");
		sel.add("contractChangeSettleBill.id");
		sel.add("contractChangeSettleBill.contractBill.id");
		sel.add("contractSettleBill.id");
		if(info.getId()!=null){
			ContractPCSplitBillInfo oldInfo=this.getContractPCSplitBillInfo(ctx, new ObjectUuidPK(info.getId()),sel);
			updatePC(ctx,oldInfo,false);
		}
		pk=super._save(ctx, model);
		
		info=this.getContractPCSplitBillInfo(ctx, new ObjectUuidPK(info.getId()),sel);
		updatePC(ctx,info,true);
		
		return pk;
	}
    protected void updatePC(Context ctx, ContractPCSplitBillInfo info,boolean isCiting) throws BOSException, EASBizException{
    	IProgrammingContract ipc=ProgrammingContractFactory.getLocalInstance(ctx);
    	SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("budgetAmount");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("signUpAmount");
		sict.add("isCiting");
		sict.add("estimateAmount");
		sict.add("billId");
    	for(int i=0;i<info.getEntry().size();i++){
    		ProgrammingContractInfo pc=info.getEntry().get(i).getProgrammingContract();
    		BigDecimal signUpAmount=FDCHelper.ZERO;
    		BigDecimal changeAmount=FDCHelper.ZERO;
    		BigDecimal settleAmount=FDCHelper.ZERO;
    		
    		SelectorItemCollection sel = new SelectorItemCollection();
    		sel.add("amount");
    		sel.add("head.contractChangeBill.id");
    		sel.add("head.contractChangeSettleBill.conChangeBill.id");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pc.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			view.setFilter(filter);
			view.setSelector(sel);
			ContractWithoutTextCollection col=ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(view);
			BigDecimal wtAmount=FDCHelper.ZERO;
			for(int j=0;j<col.size();j++){
				wtAmount =FDCHelper.add(wtAmount,col.get(j).getAmount()) ;
			}
			signUpAmount=FDCHelper.add(signUpAmount,wtAmount) ;
			
			view = new EntityViewInfo();
			filter = new FilterInfo();
			if(!isCiting){
				filter.getFilterItems().add(new FilterItemInfo("head.id",info.getId().toString(),CompareType.NOTEQUALS));
			}
			filter.getFilterItems().add(new FilterItemInfo("head.contractBill.id",null,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pc.getId().toString()));
			view.setFilter(filter);
			view.setSelector(sel);
			ContractPCSplitBillEntryCollection signUpCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
			for(int j=0;j<signUpCol.size();j++){
				signUpAmount =FDCHelper.add(signUpAmount,signUpCol.get(j).getAmount()) ;
			}
			
			Set isAddcsID=new HashSet();
			view = new EntityViewInfo();
			filter = new FilterInfo();
			if(!isCiting){
				filter.getFilterItems().add(new FilterItemInfo("head.id",info.getId().toString(),CompareType.NOTEQUALS));
			}
			filter.getFilterItems().add(new FilterItemInfo("head.contractChangeBill.id",null,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pc.getId().toString()));
			view.setFilter(filter);
			view.setSelector(sel);
			ContractPCSplitBillEntryCollection changeCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
			for(int j=0;j<changeCol.size();j++){
				BigDecimal csAmount=FDCHelper.ZERO;
				EntityViewInfo csview = new EntityViewInfo();
				FilterInfo csfilter = new FilterInfo();
				if(!isCiting){
					csfilter.getFilterItems().add(new FilterItemInfo("head.id",info.getId().toString(),CompareType.NOTEQUALS));
				}
				csfilter.getFilterItems().add(new FilterItemInfo("head.contractChangeSettleBill.conChangeBill.id",changeCol.get(j).getHead().getContractChangeBill().getId().toString()));
				csfilter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pc.getId().toString()));
				csview.setFilter(csfilter);
				csview.setSelector(sel);
				ContractPCSplitBillEntryCollection csCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(csview);
				for(int k=0;k<csCol.size();k++){
					isAddcsID.add(csCol.get(k).getId().toString());
					csAmount =FDCHelper.add(csAmount,csCol.get(k).getAmount()) ;
				}
				if(csAmount.compareTo(FDCHelper.ZERO)>0){
					changeAmount =FDCHelper.add(changeAmount,csAmount) ;
				}else{
					changeAmount =FDCHelper.add(changeAmount,changeCol.get(j).getAmount()) ;
				}
			}
			
//			view = new EntityViewInfo();
//			filter = new FilterInfo();
//			if(!isCiting){
//				filter.getFilterItems().add(new FilterItemInfo("head.id",info.getId().toString(),CompareType.NOTEQUALS));
//			}
//			if(isAddcsID.size()>0){
//				filter.getFilterItems().add(new FilterItemInfo("id",isAddcsID,CompareType.NOTINCLUDE));
//			}
//			filter.getFilterItems().add(new FilterItemInfo("head.contractChangeSettleBill.id",null,CompareType.NOTEQUALS));
//			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pc.getId().toString()));
//			view.setFilter(filter);
//			view.setSelector(sel);
//			ContractPCSplitBillEntryCollection csCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
//			for(int j=0;j<csCol.size();j++){
//				FilterInfo cbfilter = new FilterInfo();
//				if(!isCiting){
//					cbfilter.getFilterItems().add(new FilterItemInfo("head.id",info.getId().toString(),CompareType.NOTEQUALS));
//				}
//				cbfilter.getFilterItems().add(new FilterItemInfo("head.contractChangeBill.id",csCol.get(j).getHead().getContractChangeSettleBill().getConChangeBill().getId().toString()));
//				cbfilter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pc.getId().toString()));
//				if(!ContractPCSplitBillEntryFactory.getLocalInstance(ctx).exists(cbfilter)){
//					changeAmount =FDCHelper.add(changeAmount,csCol.get(j).getAmount()) ;
//				}
//			}
			
			view = new EntityViewInfo();
			filter = new FilterInfo();
			if(!isCiting){
				filter.getFilterItems().add(new FilterItemInfo("head.id",info.getId().toString(),CompareType.NOTEQUALS));
			}
			filter.getFilterItems().add(new FilterItemInfo("head.contractSettleBill.id",null,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pc.getId().toString()));
			view.setFilter(filter);
			view.setSelector(sel);
			ContractPCSplitBillEntryCollection settleCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
			for(int j=0;j<settleCol.size();j++){
				settleAmount =FDCHelper.add(settleAmount,settleCol.get(j).getAmount()) ;
			}
			
			pc.setSignUpAmount(signUpAmount);
			pc.setChangeAmount(changeAmount);
			pc.setSettleAmount(settleAmount);
			if(settleAmount.compareTo(FDCHelper.ZERO)>0){
				pc.setBalance(FDCHelper.subtract(pc.getAmount(), FDCHelper.add(wtAmount, settleAmount)));
			}else{
				pc.setBalance(FDCHelper.subtract(pc.getAmount(), FDCHelper.add(signUpAmount, changeAmount)));
			}
    		if(isCiting){
    			if(info.getContractBill()!=null){
    				if(!ContractPropertyEnum.SUPPLY.equals(info.getContractBill().getContractPropert())){
//    					pc.setBillId(info.getContractBill().getId().toString());
    					pc.setIsCiting(true);
    				}else{
//    					ContractEstimateChangeBillFactory.getLocalInstance(ctx).sub(pc, ContractEstimateChangeTypeEnum.SUPPLY, signUpAmount, true, null);
//    					pc.setEstimateAmount(FDCHelper.ZERO);
    				}
//    				ContractEstimateChangeBillFactory.getLocalInstance(ctx).sub(pc, ContractEstimateChangeTypeEnum.CHANGE, changeAmount, true, null);
//    				if(settleCol.size()>0){
//    					pc.setEstimateAmount(FDCHelper.ZERO);
//    				}
    			}
    			if(pc.getBalance().compareTo(FDCHelper.ZERO)<0){
    				throw new EASBizException(new NumericExceptionSubItem("100","\n"+pc.getLongNumber()+pc.getName()+" 拆分金额超过规划余额！"));
    			}
//    			if(signUpAmount.compareTo(FDCHelper.ZERO)>0){
//    				pc.setBudgetAmount(FDCHelper.ZERO);
//    			}
    		}else{
    			if(info.getContractBill()!=null){
    				if(!ContractPropertyEnum.SUPPLY.equals(info.getContractBill().getContractPropert())){
//    					pc.setBillId(null);
    					pc.setIsCiting(false);
    				}
    			}
//    			if(pc.getBalance().compareTo(pc.getAmount())==0){
//    				pc.setBudgetAmount(pc.getAmount());
//    			}
    		}
    		ipc.updatePartial(pc, sict);
    	}
    }

}