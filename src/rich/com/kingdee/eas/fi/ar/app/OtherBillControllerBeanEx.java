package com.kingdee.eas.fi.ar.app;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.richinf.IRichExamed;
import com.kingdee.eas.custom.richinf.IRichInvoiceRequest;
import com.kingdee.eas.custom.richinf.RichExamedFactory;
import com.kingdee.eas.custom.richinf.RichExamedInfo;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestEntryCollection;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestEntryInfo;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestFactory;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo;
import com.kingdee.eas.fi.ar.OtherBillInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;

public class OtherBillControllerBeanEx extends com.kingdee.eas.fi.ar.app.OtherBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fi.ar.app.OtherBillControllerBeanEx");
    
    @Override
    public void audit(Context ctx, IObjectPK billId) throws BOSException,
    		EASBizException {
    	super.audit(ctx, billId);
    	OtherBillInfo obinfo = getOtherBillInfo(ctx,billId);
    	String sbid = obinfo.getSourceBillId();
    	if(sbid != null){
    		RichInvoiceRequestInfo rrinfo = new RichInvoiceRequestInfo();
    		if(rrinfo.getBOSType().equals(BOSUuid.read(sbid).getType())){
    			rrinfo=RichInvoiceRequestFactory.getLocalInstance(ctx).getRichInvoiceRequestInfo(new ObjectUuidPK(sbid));
    			RichInvoiceRequestEntryCollection recoll = rrinfo.getEntrys();
    			if(recoll.size() > 0) {
    				RichInvoiceRequestEntryInfo reinfo = null;
    				BigDecimal yhx = null;
    				BigDecimal benci = null;
    				RichExamedInfo examinfo = null;
    				IRichExamed ire = RichExamedFactory.getLocalInstance(ctx);
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
    				obinfo.setBigDecimal("yhxAmount",obinfo.getTotalAmount());
    				update(ctx,billId,obinfo);
    			}
    		}
    	}
    }
    
    @Override
    public void unpassAudit(Context ctx, IObjectPK[] pks,
    		CoreBillBaseCollection coreBillCollection) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super.unpassAudit(ctx, pks, coreBillCollection);
    	OtherBillInfo obinfo = null;
    	RichInvoiceRequestInfo rrinfo = new RichInvoiceRequestInfo();
    	String sbid = null;
    	IRichInvoiceRequest irir = RichInvoiceRequestFactory.getLocalInstance(ctx);
    	IRichExamed ire = RichExamedFactory.getLocalInstance(ctx);
    	for(int j=pks.length-1; j>=0; j--) {
    		obinfo = getOtherBillInfo(ctx,pks[j]);
    		sbid = obinfo.getSourceBillId();
    		if(sbid != null && rrinfo.getBOSType().equals(BOSUuid.read(sbid).getType())) {
    			rrinfo=irir.getRichInvoiceRequestInfo(new ObjectUuidPK(sbid));
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
    				obinfo.setBigDecimal("yhxAmount",BigDecimal.ZERO);
    				update(ctx,pks[j],obinfo);
    			}
    		}
    	}
    }
   
    
    
    
    
    
    
    
    
    
    
}				
