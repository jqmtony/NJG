package com.kingdee.eas.fi.ar.app;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.richbase.BizStateEnum;
import com.kingdee.eas.custom.richinf.IRichExamed;
import com.kingdee.eas.custom.richinf.IRichInvoiceRequest;
import com.kingdee.eas.custom.richinf.RichExamedFactory;
import com.kingdee.eas.custom.richinf.RichExamedInfo;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestEntryCollection;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestEntryInfo;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestFactory;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo;
import com.kingdee.eas.fi.ar.OtherBillInfo;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class OtherBillControllerBeanEx extends com.kingdee.eas.fi.ar.app.OtherBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fi.ar.app.OtherBillControllerBeanEx");
    
    
    @Override
    public void audit(Context ctx, IObjectPK billId) throws BOSException,
    		EASBizException {
    	super.audit(ctx, billId);
    	OtherBillInfo obinfo = getOtherBillInfo(ctx,billId);
    	String sbid = null;
    	IFMIsqlFacade iff = FMIsqlFacadeFactory.getLocalInstance(ctx);
    	IRowSet rs = iff.executeQuery("select FSrcObjectID from T_BOT_Relation where FDestObjectID=?",new Object[]{billId.toString()});
    	RichInvoiceRequestInfo rrinfo = new RichInvoiceRequestInfo();
    	IRichInvoiceRequest ireq = RichInvoiceRequestFactory.getLocalInstance(ctx);
    	IRichExamed ire = RichExamedFactory.getLocalInstance(ctx);
    	ObjectUuidPK reqPk = null;
    	try {
			while(rs.next()){
				sbid = rs.getString("FSrcObjectID");
				if(rrinfo.getBOSType().equals(BOSUuid.read(sbid).getType())){
					reqPk = new ObjectUuidPK(sbid);
					rrinfo = ireq.getRichInvoiceRequestInfo(reqPk);
					RichInvoiceRequestEntryCollection recoll = rrinfo.getEntrys();
					if(recoll.size() > 0) {
						RichInvoiceRequestEntryInfo reinfo = null;
						BigDecimal yhx = null;
						BigDecimal benci = null;
						RichExamedInfo examinfo = null;
						IObjectPK examPk = null;
						for(int i=recoll.size()-1; i>=0; i--) {
							reinfo = recoll.get(i);
							benci = reinfo.getBencisq();
							examPk = new ObjectUuidPK(reinfo.getDjd().getId());
							examinfo = ire.getRichExamedInfo(examPk);
							if(rrinfo.isDjkp()){
								yhx = examinfo.getNbyhxAmount();
								if(yhx == null) {
									examinfo.setNbyhxAmount(benci);
								}else{
									examinfo.setNbyhxAmount(benci.add(yhx));
								}
							}else{
								yhx = examinfo.getYhxAmount();
								if(yhx == null) {
									examinfo.setYhxAmount(benci);
								}else{
									examinfo.setYhxAmount(benci.add(yhx));
								}
							}
							
							ire.update(examPk,examinfo);
						}
						// update otherbill
						obinfo.setYhxAmount(obinfo.getTotalAmount());
						update(ctx,billId,obinfo);
					}
					//update request
					rrinfo.setBizState(BizStateEnum.ykp);
					ireq.update(reqPk,rrinfo);
				}
			}
		} catch (UuidException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void unpassAudit(Context ctx, IObjectPK pk, CoreBillBaseInfo info)
    		throws BOSException, EASBizException {
    	super.unpassAudit(ctx, pk, info);
    	unauditAfter(ctx, pk);
    	
    }
    
    @Override
    public void unAudit(Context ctx, IObjectPK[] pks,
    		CoreBillBaseCollection arg2) throws BOSException, EASBizException {
    	super.unAudit(ctx, pks, arg2);
//    	afterUnaudit(ctx, pks);
    }

	private void unauditAfter(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		OtherBillInfo obinfo = getOtherBillInfo(ctx,pk);
    	RichInvoiceRequestInfo rrinfo = new RichInvoiceRequestInfo();
    	String sbid = null;
    	IRichInvoiceRequest irir = RichInvoiceRequestFactory.getLocalInstance(ctx);
    	IRichExamed ire = RichExamedFactory.getLocalInstance(ctx);
    	IFMIsqlFacade iff = FMIsqlFacadeFactory.getLocalInstance(ctx);
    	IRowSet rs = iff.executeQuery("select FSrcObjectID from T_BOT_Relation where FDestObjectID=?",new Object[]{obinfo.getId().toString()});;
    	ObjectUuidPK reqpk = null;
    	try {
			while(rs.next()){
				sbid = rs.getString("FSrcObjectID");
				if(rrinfo.getBOSType().equals(BOSUuid.read(sbid).getType())) {
					reqpk = new ObjectUuidPK(sbid);
					rrinfo=irir.getRichInvoiceRequestInfo(reqpk);
					RichInvoiceRequestEntryCollection recoll = rrinfo.getEntrys();
					if(recoll.size() > 0) {
						RichInvoiceRequestEntryInfo reinfo = null;
						RichExamedInfo examinfo = null;
						IObjectPK examPk = null;
						for(int i=recoll.size()-1; i>=0; i--) {
							reinfo = recoll.get(i);
							examPk = new ObjectUuidPK(reinfo.getDjd().getId());
							examinfo = ire.getRichExamedInfo(examPk);
							if(rrinfo.isDjkp()){
								examinfo.setNbyhxAmount(examinfo.getNbyhxAmount().subtract(reinfo.getBencisq()));
							}else{
								examinfo.setYhxAmount(examinfo.getYhxAmount().subtract(reinfo.getBencisq()));
							}
							ire.update(examPk,examinfo);
						}
						// update otherbill
						obinfo.setYhxAmount(BigDecimal.ZERO);
						update(ctx,pk,obinfo);
					}
					rrinfo.setBizState(BizStateEnum.wkp);
					irir.update(reqpk,rrinfo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    @Override
    protected void unPassAudit(Context ctx, IObjectPK pk, IObjectValue info)
    		throws EASBizException, BOSException {
    	super.unPassAudit(ctx, pk, info);
    	unauditAfter(ctx, pk);
    }
    
    @Override
    public void unpassAudit(Context ctx, IObjectPK[] pks,
    		CoreBillBaseCollection coreBillCollection) throws BOSException,EASBizException {
    	super.unpassAudit(ctx, pks, coreBillCollection);
//    	afterUnaudit(ctx, pks);
    }

	private void afterUnaudit(Context ctx, IObjectPK[] pks)
			throws BOSException, EASBizException {
		OtherBillInfo obinfo = null;
    	RichInvoiceRequestInfo rrinfo = new RichInvoiceRequestInfo();
    	String sbid = null;
    	IRichInvoiceRequest irir = RichInvoiceRequestFactory.getLocalInstance(ctx);
    	IRichExamed ire = RichExamedFactory.getLocalInstance(ctx);
    	IFMIsqlFacade iff = FMIsqlFacadeFactory.getLocalInstance(ctx);
    	IRowSet rs = null;
    	ObjectUuidPK reqpk = null;
    	for(int j=pks.length-1; j>=0; j--) {
    		obinfo = getOtherBillInfo(ctx,pks[j]);
    		rs = iff.executeQuery("select FSrcObjectID from T_BOT_Relation where FDestObjectID=?",new Object[]{obinfo.getId().toString()});
    		try {
				while(rs.next()) {
					sbid = rs.getString("FSrcObjectID");
					if(rrinfo.getBOSType().equals(BOSUuid.read(sbid).getType())) {
						reqpk = new ObjectUuidPK(sbid);
						rrinfo=irir.getRichInvoiceRequestInfo(reqpk);
						RichInvoiceRequestEntryCollection recoll = rrinfo.getEntrys();
						if(recoll.size() > 0) {
							RichInvoiceRequestEntryInfo reinfo = null;
							RichExamedInfo examinfo = null;
							IObjectPK examPk = null;
							for(int i=recoll.size()-1; i>=0; i--) {
								reinfo = recoll.get(i);
								examPk = new ObjectUuidPK(reinfo.getDjd().getId());
								examinfo = ire.getRichExamedInfo(examPk);
								if(rrinfo.isDjkp()){
									examinfo.setNbyhxAmount(examinfo.getNbyhxAmount().subtract(reinfo.getBencisq()));
								}else{
									examinfo.setYhxAmount(examinfo.getYhxAmount().subtract(reinfo.getBencisq()));
								}
								ire.update(examPk,examinfo);
							}
							// update otherbill
							obinfo.setYhxAmount(BigDecimal.ZERO);
							update(ctx,pks[j],obinfo);
						}
						rrinfo.setBizState(BizStateEnum.wkp);
						irir.update(reqpk,rrinfo);
					}
				}
			} catch (UuidException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
	}
   
    
    
    
    
    
    
    
    
    
}				
