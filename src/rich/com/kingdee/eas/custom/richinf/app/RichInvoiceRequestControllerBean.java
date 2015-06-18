package com.kingdee.eas.custom.richinf.app;

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
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.eas.custom.richinf.BillState;
import com.kingdee.eas.custom.richinf.IRichExamed;
import com.kingdee.eas.custom.richinf.RichExamedFactory;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestEntryCollection;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class RichInvoiceRequestControllerBean extends AbstractRichInvoiceRequestControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.richinf.app.RichInvoiceRequestControllerBean");
    
    
    @Override
    protected void _audit(Context ctx, IObjectValue model) throws BOSException {
    	RichInvoiceRequestInfo rrinfo = (RichInvoiceRequestInfo)model;
    	try {
    		rrinfo.setAuditDate(SysUtil.getAppServerTime(ctx));
    		rrinfo.setAuditor((UserInfo)ctx.get(SysContextConstant.USERINFO));
    		rrinfo.setBillState(BillState.AUDIT);
			_update(ctx,new ObjectUuidPK(rrinfo.getId()),model);
			
			//将开票机构反写回到检单的开票机构。
			if(rrinfo.getKpCompany() != null) {
				String companyID = rrinfo.getKpCompany().getId().toString();
				String reid = null;
				IRichExamed  ire = RichExamedFactory.getLocalInstance(ctx);
				for(int i=rrinfo.getEntrys().size()-1; i>=0; i--){
					reid = rrinfo.getEntrys().get(i).getDjd().getId().toString();
					if(!companyID.equals(ire.getRichExamedInfo(new ObjectUuidPK(reid)).getKpCompany().getId().toString())){
						DbUtil.execute(ctx,"update CT_RIC_RichExamed set CFKpCompanyID=? where FID=?",new Object[]{companyID,reid});
					}
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    protected void _unAudit(Context ctx, IObjectValue model) throws BOSException {
    	//
    	RichInvoiceRequestInfo rrinfo = (RichInvoiceRequestInfo)model;
    	rrinfo.setBillState(BillState.SUBMIT);
    	try {
			_update(ctx,new ObjectUuidPK(rrinfo.getId()),model);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
   
    @Override
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	RichInvoiceRequestInfo info = (RichInvoiceRequestInfo)model;
    	
    	//累计到检金额  累计已申请开票金额   累计已开票金额
    	//取得在此之前的到检金额，然后加上本次的
    	StringBuffer sb = new StringBuffer();
    	String ldNumber = info.getLdNumber();
    	sb.append("select DISTINCT rire.CFDjdID djdid,rire.CFYsAmount jsamount from ");
    	sb.append("CT_RIC_RichInvoiceRequestEntry rire left join CT_RIC_RichInvoiceRequest requ ");
    	sb.append("on rire.fparentid=requ.fid where requ.cfBillState<>'SAVE' and requ.CFLdNumber='");
    	sb.append(ldNumber+"'");
    	IRowSet rs = DbUtil.executeQuery(ctx,sb.toString());
    	BigDecimal djTotal = BigDecimal.ZERO;
    	Set<String> befores = new HashSet<String>();
    	if(rs != null && rs.size() > 0) {
    		try {
				while(rs.next()){
					befores.add(rs.getString("djdid"));
					djTotal = djTotal.add(rs.getBigDecimal("jsamount"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	RichInvoiceRequestEntryCollection reColl = info.getEntrys();
    	for(int i=reColl.size()-1; i>=0; i--) {
    		if(!befores.contains(reColl.get(i).getDjd().getId().toString()))
    			djTotal = djTotal.add(reColl.get(i).getYsAmount());
    	}
    	info.setDjAmount(djTotal);
    	//取得在此之前的申请开票金额，然后加上本次的
    	String sql="select CFAmount from CT_RIC_RichInvoiceRequest where cfBillState<>'SAVE' and CFLdNumber='"+ldNumber+"'";
    	rs = DbUtil.executeQuery(ctx,sql);
    	BigDecimal requestTotal = BigDecimal.ZERO;
    	if(rs != null && rs.size() > 0) {
    		try {
				while(rs.next()){
					requestTotal = requestTotal.add(rs.getBigDecimal("CFAmount"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	if(info.getBillState().equals(BillState.SAVE)) {
    		info.setBillState(BillState.SUBMIT);
    		info.setReqSumAmount(requestTotal.add(info.getAmount()));
    	}else{
    		RichInvoiceRequestInfo oldValue = (RichInvoiceRequestInfo)_getValue(ctx,new ObjectUuidPK(info.getId()));
    		if(oldValue.getAmount().compareTo(info.getAmount()) > 0)
    			info.setReqSumAmount(requestTotal.add(oldValue.getAmount().subtract(info.getAmount())));
    		else
    			info.setReqSumAmount(requestTotal.add(info.getAmount().subtract(oldValue.getAmount())));
    	}
    	
    	//取得在此之前的已开票金额
    	sql = "select FAmount from T_AR_OtherBill where FBillStatus=3 and CFLdNo='"+ldNumber+"'";
    	rs = DbUtil.executeQuery(ctx,sql);
    	if(rs != null && rs.size() > 0) {
    		BigDecimal fpTotal = BigDecimal.ZERO;
    		try {
				while(rs.next()){
					fpTotal = fpTotal.add(rs.getBigDecimal("FAmount"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			info.setInvoicedAmount(fpTotal);
    	}
    	return super._submit(ctx, model);
    }
    
}