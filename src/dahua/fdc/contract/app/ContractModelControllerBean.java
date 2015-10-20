package com.kingdee.eas.fdc.contract.app;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.EntryDescFactory;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractModelFactory;
import com.kingdee.eas.fdc.contract.ContractModelInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractModelControllerBean extends AbstractContractModelControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractModelControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	super._audit(ctx, billId);
    	updateAfterAudit(ctx,billId);
    }
    
    //更新最新版本
    private void updateAfterAudit(Context ctx, BOSUuid billId) throws BOSException,
	EASBizException {
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("contractType");
    	ContractModelInfo info = (ContractModelInfo)getValue(ctx, new ObjectUuidPK(billId), sic);
		
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("update t_con_contractModel set fislastver =0 where fcontracttypeid = ? ");
    	builder.addParam(info.getContractType().getId().toString());
    	builder.execute();
    	builder.clear();
    	
    	info.setId(billId);
    	info.setIsLastVer(true);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("isLastVer");
		_updatePartial(ctx, info, selector);
		
		
    }
    
    public void delete(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
    	super.delete(ctx, pk);
    	deleteContent(ctx, pk);
    }
    
    //删除范本
    private void deleteContent(Context ctx, IObjectPK pk) throws BOSException,
	EASBizException {
    	
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("parent",pk.toString()));
    	ContractContentFactory.getLocalInstance(ctx).delete(filter);
    	
    	filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("head",pk.toString()));
    	EntryDescFactory.getLocalInstance(ctx).delete(filter);
    	
    	
    }
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	/**
		 * 目前实现，如果该版本(非最新版本)存在修订版本则不能反审批,以后可根据具体需求做调整.
		 */
		ContractModelInfo info = 
			ContractModelFactory.getLocalInstance(ctx).getContractModelInfo(new ObjectUuidPK(billId));
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select max(fvernumber) as maxvernumber,min(fvernumber) as minvernumber from t_con_ContractModel where fcontracttypeid=? ");
		builder.addParam(info.getContractType().getId().toString());
		IRowSet row=builder.executeQuery();
		float minVerNumber = 0;
		float maxVerNumber = 0;
		try{
			if(row.next()){
				minVerNumber = row.getFloat("minvernumber");
				maxVerNumber = row.getFloat("maxvernumber");
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		if(Float.compare(info.getVerNumber(),maxVerNumber) < 0 && Float.compare(info.getVerNumber(),minVerNumber) >=0){
			throw new ContractException(ContractException.EXISTMODELVERSION);
		}
		
		builder.clear();
		builder.appendSql("update T_Con_ContractModel set fislastver=? where fid=?");
		builder.addParam(Boolean.FALSE);
		builder.addParam(billId.toString());
		builder.execute();
		super._unAudit(ctx, billId);
		
		//如果最新版被反审批而且存在上一个审批版的话，就把上一版标记为最新版
		String lastId = null; 
		builder.clear();
		builder.appendSql("select top 1 fid from t_con_ContractModel where fstate='4AUDITTED' and fcontracttypeid=? ");
		builder.appendSql("order by fvernumber desc ");
		builder.addParam(info.getContractType().getId().toString());
		IRowSet rowSet1=builder.executeQuery();
		if(rowSet1 != null && rowSet1.size()>0){
			try{
				if(rowSet1.next()){
					lastId = rowSet1.getString("fid");
				}
			}catch(SQLException e){
				throw new BOSException(e);
			}
			
			builder.clear();
			builder.appendSql("update T_con_ContractModel set fislastver=? where fid=?");
			builder.addParam(Boolean.TRUE);
			builder.addParam(lastId);
			builder.execute();
		}
    }
    
}