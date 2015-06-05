package com.kingdee.eas.custom.richinf.app;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.richinf.IRichExamed;
import com.kingdee.eas.custom.richinf.RichCompayWriteOffInfo;
import com.kingdee.eas.custom.richinf.RichExamedCollection;
import com.kingdee.eas.custom.richinf.RichExamedFactory;
import com.kingdee.eas.custom.richinf.RichExamedInfo;
import com.kingdee.eas.fi.ar.IOtherBill;
import com.kingdee.eas.fi.ar.OtherBillCollection;
import com.kingdee.eas.fi.ar.OtherBillFactory;
import com.kingdee.eas.fi.ar.OtherBillInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class RichCompayWriteOffControllerBean extends AbstractRichCompayWriteOffControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.richinf.app.RichCompayWriteOffControllerBean");

	
    @Override
    protected boolean _aboutHxAndFanHx(Context ctx, IObjectCollection fpColl,
    		IObjectCollection richExamColl, int hxType, IObjectValue ov)
    		throws BOSException {
    	//hxType：-1--反核销    0--自动核销   1--手工核销
    	if(-1 == hxType){
    		doFanHX(ctx,fpColl,richExamColl,ov);
    	}else {
    		doHandHX(ctx,fpColl,richExamColl,ov);
    	}
    	
    	return true;
    }
    
    private void doHandHX(Context ctx, IObjectCollection fpColl,IObjectCollection richExamColl, IObjectValue ov) throws BOSException{
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("yhxAmount");
    	//初始化本地接口
    	IOtherBill  iob = OtherBillFactory.getLocalInstance(ctx);
    	IRichExamed ire = RichExamedFactory.getLocalInstance(ctx);
    	
    	OtherBillCollection collFP = (OtherBillCollection)fpColl;
    	RichExamedCollection collRE = (RichExamedCollection)richExamColl;
    	String richCompayid = ((RichCompayWriteOffInfo)ov).getId().toString();
		String customerId = null;
		BigDecimal whxamount = null;
		BigDecimal fpAmount = null;
		BigDecimal yhxAmount = null;
		OtherBillInfo obInfo = null;
		String dj_cust = null;
		BigDecimal whxDJ = null;
		BigDecimal djAmount = null;
		BigDecimal yhxDJ = null;
		RichExamedInfo rhInfo = null;
		BigDecimal zero = BigDecimal.ZERO;
		BigDecimal dj_benci = zero;
		BigDecimal fp_benci = zero;
		IRowSet rs = null;
		String djid = null;
		String fpid = null;
		for(int i=collFP.size()-1; i>=0; i--){
			obInfo = collFP.get(i);
			customerId = obInfo.getAsstActID();
			fpAmount = obInfo.getTotalAmount();
			yhxAmount = obInfo.getBigDecimal("yhxAmount");
			if(yhxAmount == null){
				whxamount = fpAmount;
				yhxAmount = zero;
			}else{
				whxamount = fpAmount.subtract(yhxAmount);
			}
			fpid = obInfo.getId().toString();
			for(int j=collRE.size()-1; j>=0; j--){
				rhInfo = collRE.get(j);
				dj_cust = rhInfo.getKpUnit().getId().toString();
				if(dj_cust.equals(customerId)){
					djAmount = new BigDecimal(rhInfo.getAmount());
					yhxDJ = rhInfo.getYhxAmount();
					djid = rhInfo.getId().toString();
					if(yhxDJ == null){
    					yhxDJ = zero;
    					whxDJ = djAmount;
    				}else{
    					whxDJ = djAmount.subtract(yhxDJ);
    				}
					
					//发票的未核销金额大于到检单的，到检单全部核销，发票核销金额=之前核销+此次核销
    				if(whxamount.compareTo(whxDJ) > 0){
    					//反写分录发票和分录到检单的已核销金额
    					rs = DbUtil.executeQuery(ctx,"select cfbencihx from CT_RIC_RichCWODE where CFDjNoID=? and FParentID=?",new Object[]{djid,richCompayid});
    					try {
							if(rs.next() && rs.getBigDecimal("cfbencihx")!=null){
								dj_benci = rs.getBigDecimal("cfbencihx");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
    					Object[] ps = new Object[]{djAmount,whxDJ.add(dj_benci),djid,richCompayid};
    					DbUtil.execute(ctx,"update CT_RIC_RichCWODE set CFDj_yhx=?,cfbencihx=? where CFDjNoID=? and FParentID=?",ps);
    					rs = DbUtil.executeQuery(ctx,"select cfbencihx from CT_RIC_RichCWOFE where CFFpNoID=? and FParentID=?",new Object[]{fpid,richCompayid});
    					try {
							if(rs.next() && rs.getBigDecimal("cfbencihx")!=null){
								fp_benci = rs.getBigDecimal("cfbencihx");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
    					ps[0] = yhxAmount.add(whxDJ);
    					ps[1] = whxDJ.add(fp_benci);
    					ps[2] = fpid;
    					DbUtil.execute(ctx,"update CT_RIC_RichCWOFE set CFYhxAmount=?,cfbencihx=? where CFFpNoID=? and FParentID=?",ps);
    					
    					//反写发票和到检单的已核销金额
    					rhInfo.setYhxAmount(djAmount);
    					obInfo.setBigDecimal("yhxAmount",yhxAmount.add(whxDJ));
    					try {
    						ire.updatePartial(rhInfo,sic);
							iob.updatePartial(obInfo,sic);
						} catch (EASBizException e) {
							e.printStackTrace();
						}
						fp_benci = zero;
						dj_benci = zero;
						collRE.remove(rhInfo);
    				}else if(whxamount.compareTo(whxDJ) == 0){
    					//发票的未核销金额等于到检单，全部核销，并逃出这层循环
    					//反写分录发票和分录到检单的已核销金额
    					rs = DbUtil.executeQuery(ctx,"select cfbencihx from CT_RIC_RichCWODE where CFDjNoID=? and FParentID=?",new Object[]{djid,richCompayid});
    					try {
							if(rs.next() && rs.getBigDecimal("cfbencihx")!=null){
								dj_benci = rs.getBigDecimal("cfbencihx");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
    					Object[] ps = new Object[]{djAmount,whxamount.add(dj_benci),djid,richCompayid};
    					DbUtil.execute(ctx,"update CT_RIC_RichCWODE set CFDj_yhx=?,cfbencihx=? where CFDjNoID=? and FParentID=?",ps);
    					rs = DbUtil.executeQuery(ctx,"select cfbencihx from CT_RIC_RichCWOFE where CFFpNoID=? and FParentID=?",new Object[]{fpid,richCompayid});
    					try {
							if(rs.next() && rs.getBigDecimal("cfbencihx")!=null){
								fp_benci = rs.getBigDecimal("cfbencihx");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
    					ps[0] = fpAmount;
    					ps[1] = whxamount.add(fp_benci);
    					ps[2] = fpid;
    					DbUtil.execute(ctx,"update CT_RIC_RichCWOFE set CFYhxAmount=?,cfbencihx=? where CFFpNoID=? and FParentID=?",ps);
    					
    					//反写发票和到检单的已核销金额
    					rhInfo.setYhxAmount(djAmount);
    					obInfo.setBigDecimal("yhxAmount",fpAmount);
    					try {
    						ire.updatePartial(rhInfo,sic);
							iob.updatePartial(obInfo,sic);
						} catch (EASBizException e) {
							e.printStackTrace();
						}
						fp_benci = zero;
						dj_benci = zero;
						collRE.remove(rhInfo);
    					break;
    				}else{
    					//发票的未核销金额小于到检单，发票全部核销，到检单=之前核销+此次，并逃出这层循环
    					//反写分录发票和分录到检单的已核销金额
    					rs = DbUtil.executeQuery(ctx,"select cfbencihx from CT_RIC_RichCWODE where CFDjNoID=? and FParentID=?",new Object[]{djid,richCompayid});
    					try {
							if(rs.next() && rs.getBigDecimal("cfbencihx")!=null){
								dj_benci = rs.getBigDecimal("cfbencihx");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
    					Object[] ps = new Object[]{fpAmount.add(yhxDJ),whxamount.add(dj_benci),djid,richCompayid};
    					DbUtil.execute(ctx,"update CT_RIC_RichCWODE set CFDj_yhx=?,cfbencihx=? where CFDjNoID=? and FParentID=?",ps);
    					rs = DbUtil.executeQuery(ctx,"select cfbencihx from CT_RIC_RichCWOFE where CFFpNoID=? and FParentID=?",new Object[]{fpid,richCompayid});
    					try {
							if(rs.next() && rs.getBigDecimal("cfbencihx")!=null){
								fp_benci = rs.getBigDecimal("cfbencihx");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
    					ps[0] = fpAmount;
    					ps[1] = whxamount.add(fp_benci);
    					ps[2] = fpid;
    					DbUtil.execute(ctx,"update CT_RIC_RichCWOFE set CFYhxAmount=?,cfbencihx=? where CFFpNoID=? and FParentID=?",ps);
    					
    					//反写发票单据和到检单单据的已核销金额
    					rhInfo.setYhxAmount(fpAmount.add(yhxDJ));
    					obInfo.setBigDecimal("yhxAmount",fpAmount);
    					try {
							ire.updatePartial(rhInfo,sic);
							iob.updatePartial(obInfo,sic);
						} catch (EASBizException e) {
							e.printStackTrace();
						}
						fp_benci = zero;
						dj_benci = zero;
    					break;
    				}
				}
			}
		}
    }
    private void doAutoHX(Context ctx, IObjectCollection fpColl,IObjectCollection richExamColl, IObjectValue ov) throws BOSException{
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("yhxAmount");
    	//初始化本地接口
    	IOtherBill  iob = OtherBillFactory.getLocalInstance(ctx);
    	IRichExamed ire = RichExamedFactory.getLocalInstance(ctx);
    	
    	OtherBillCollection collFP = (OtherBillCollection)fpColl;
    	RichExamedCollection collRE = (RichExamedCollection)richExamColl;
    	RichCompayWriteOffInfo richCompay = (RichCompayWriteOffInfo)ov;
		String customerId = null;
		BigDecimal whxamount = null;
		BigDecimal fpAmount = null;
		BigDecimal yhxAmount = null;
		OtherBillInfo obInfo = null;
		String dj_cust = null;
		BigDecimal whxDJ = null;
		BigDecimal djAmount = null;
		BigDecimal yhxDJ = null;
		RichExamedInfo rhInfo = null;
		for(int i=collFP.size()-1; i>=0; i--){
			obInfo = collFP.get(i);
			customerId = obInfo.getAsstActID();
			fpAmount = obInfo.getTotalAmount();
			yhxAmount = obInfo.getBigDecimal("yhxAmount");
			if(yhxAmount == null){
				whxamount = fpAmount;
				yhxAmount = BigDecimal.ZERO;
			}else{
				whxamount = fpAmount.subtract(yhxAmount);
			}
			for(int j=collRE.size()-1; j>=0; j--){
				rhInfo = collRE.get(j);
				dj_cust = rhInfo.getKpUnit().getId().toString();
				if(dj_cust.equals(customerId)){
					djAmount = new BigDecimal(rhInfo.getAmount());
					yhxDJ = rhInfo.getYhxAmount();
					if(yhxDJ == null){
    					yhxDJ = BigDecimal.ZERO;
    					whxDJ = djAmount;
    				}else{
    					whxDJ = djAmount.subtract(yhxDJ);
    				}
					
					//发票的未核销金额大于到检单的，到检单全部核销，发票核销金额=之前核销+此次核销
    				if(whxamount.compareTo(whxDJ) > 0){
    					//反写分录发票和分录到检单的已核销金额
    					Object[] ps = new Object[]{djAmount,rhInfo.getId().toString(),richCompay.getId().toString()};
    					DbUtil.execute(ctx,"update CT_RIC_RichCWODE set CFDj_yhx=? where CFDjNoID=? and FParentID=?",ps);
    					ps[0] = yhxAmount.add(whxDJ);
    					ps[1] = obInfo.getId().toString();
    					DbUtil.execute(ctx,"update CT_RIC_RichCWOFE set CFYhxAmount=? where CFFpNoID=? and FParentID=?",ps);
    					
    					//反写发票和到检单的已核销金额
    					rhInfo.setYhxAmount(djAmount);
    					obInfo.setBigDecimal("yhxAmount",yhxAmount.add(whxDJ));
    					try {
    						ire.updatePartial(rhInfo,sic);
							iob.updatePartial(obInfo,sic);
						} catch (EASBizException e) {
							e.printStackTrace();
						}
    				}else if(whxamount.compareTo(whxDJ) == 0){
    					//发票的未核销金额等于到检单，全部核销，并逃出这层循环
    					//反写分录发票和分录到检单的已核销金额
    					Object[] ps = new Object[]{djAmount,rhInfo.getId().toString(),richCompay.getId().toString()};
    					DbUtil.execute(ctx,"update CT_RIC_RichCWODE set CFDj_yhx=? where CFDjNoID=? and FParentID=?",ps);
    					ps[0] = fpAmount;
    					ps[1] = obInfo.getId().toString();
    					DbUtil.execute(ctx,"update CT_RIC_RichCWOFE set CFYhxAmount=? where CFFpNoID=? and FParentID=?",ps);
    					
    					//反写发票和到检单的已核销金额
    					rhInfo.setYhxAmount(djAmount);
    					obInfo.setBigDecimal("yhxAmount",fpAmount);
    					try {
    						ire.updatePartial(rhInfo,sic);
							iob.updatePartial(obInfo,sic);
						} catch (EASBizException e) {
							e.printStackTrace();
						}
    					break;
    				}else{
    					//发票的未核销金额小于到检单，发票全部核销，到检单=之前核销+此次，并逃出这层循环
    					//反写分录发票和分录到检单的已核销金额
    					Object[] ps = new Object[]{fpAmount.add(yhxDJ),rhInfo.getId().toString(),richCompay.getId().toString()};
    					DbUtil.execute(ctx,"update CT_RIC_RichCWODE set CFDj_yhx=? where CFDjNoID=? and FParentID=?",ps);
    					ps[0] = fpAmount;
    					ps[1] = obInfo.getId().toString();
    					DbUtil.execute(ctx,"update CT_RIC_RichCWOFE set CFYhxAmount=? where CFFpNoID=? and FParentID=?",ps);
    					
    					//反写发票单据和到检单单据的已核销金额
    					rhInfo.setYhxAmount(fpAmount.add(yhxDJ));
    					obInfo.setBigDecimal("yhxAmount",fpAmount);
    					try {
							ire.updatePartial(rhInfo,sic);
							iob.updatePartial(obInfo,sic);
						} catch (EASBizException e) {
							e.printStackTrace();
						}
    					break;
    				}
				}
			}
		}
    }
    private void doFanHX(Context ctx, IObjectCollection fpColl,IObjectCollection richExamColl, IObjectValue ov) throws BOSException{
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("yhxAmount");
    	//初始化本地接口
    	IOtherBill  iob = OtherBillFactory.getLocalInstance(ctx);
    	IRichExamed ire = RichExamedFactory.getLocalInstance(ctx);
    	
    	OtherBillCollection collFP = (OtherBillCollection)fpColl;
    	RichExamedCollection collRE = (RichExamedCollection)richExamColl;
    	String richCompay = ((RichCompayWriteOffInfo)ov).getId().toString();
		String customerId = null;
		String fpid = null;
		String djid = null;
		BigDecimal yhxAmount = null;
		OtherBillInfo obInfo = null;
		String dj_cust = null;
		BigDecimal zero = BigDecimal.ZERO;
		BigDecimal dj_benci = null;
		BigDecimal fp_benci = null;
		IRowSet rs = null;
		BigDecimal yhxDJ = null;
		RichExamedInfo rhInfo = null;
		for(int i=collFP.size()-1; i>=0; i--){
			obInfo = collFP.get(i);
			customerId = obInfo.getAsstActID();
			yhxAmount = obInfo.getBigDecimal("yhxAmount");
			fpid = obInfo.getId().toString();
			rs = DbUtil.executeQuery(ctx,"select cfbencihx from CT_RIC_RichCWOFE where CFFpNoID=? and FParentID=?",new Object[]{fpid,richCompay});
			try {
				if(rs.next()){
					fp_benci = rs.getBigDecimal("cfbencihx");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
//			if(fp_benci.compareTo(zero) == 0){
//				continue;
//			}
			for(int j=collRE.size()-1; j>=0; j--){
				rhInfo = collRE.get(j);
				dj_cust = rhInfo.getKpUnit().getId().toString();
				if(dj_cust.equals(customerId)){
					yhxDJ = rhInfo.getYhxAmount();
					djid = rhInfo.getId().toString();
					rs = DbUtil.executeQuery(ctx,"select cfbencihx from CT_RIC_RichCWODE where CFDjNoID=? and FParentID=?",new Object[]{djid,richCompay});
					try {
						if(rs.next()){
							dj_benci = rs.getBigDecimal("cfbencihx");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(dj_benci.compareTo(zero) == 0){
						collRE.remove(rhInfo);
						continue;
					}
					if(fp_benci.compareTo(dj_benci) >= 0){
						//反写分录发票和分录到检单的已核销金额
    					Object[] ps = new Object[]{yhxDJ.subtract(dj_benci),zero,djid,richCompay};
    					DbUtil.execute(ctx,"update CT_RIC_RichCWODE set CFDj_yhx=?,cfbencihx=? where CFDjNoID=? and FParentID=?",ps);
    					yhxAmount = yhxAmount.subtract(dj_benci);
    					fp_benci = fp_benci.subtract(dj_benci);
    					ps[0] = yhxAmount;
    					ps[1] = fp_benci;
    					ps[2] = fpid;
    					DbUtil.execute(ctx,"update CT_RIC_RichCWOFE set CFYhxAmount=?,cfbencihx=? where CFFpNoID=? and FParentID=?",ps);
    					//反写发票和到检单的已核销金额
    					rhInfo.setYhxAmount(yhxDJ.subtract(dj_benci));
    					obInfo.setBigDecimal("yhxAmount",yhxAmount);
    					try {
    						ire.updatePartial(rhInfo,sic);
							iob.updatePartial(obInfo,sic);
						} catch (EASBizException e) {
							e.printStackTrace();
						}
						
					}else{
						//反写分录发票和分录到检单的已核销金额
    					Object[] ps = new Object[]{yhxDJ.subtract(fp_benci),dj_benci.subtract(fp_benci),djid,richCompay};
    					DbUtil.execute(ctx,"update CT_RIC_RichCWODE set CFDj_yhx=?,cfbencihx=? where CFDjNoID=? and FParentID=?",ps);
    					ps[0] = yhxAmount.subtract(fp_benci);
    					ps[1] = zero;
    					ps[2] = fpid;
    					DbUtil.execute(ctx,"update CT_RIC_RichCWOFE set CFYhxAmount=?,cfbencihx=? where CFFpNoID=? and FParentID=?",ps);
    					//反写发票和到检单的已核销金额
    					rhInfo.setYhxAmount(yhxDJ.subtract(fp_benci));
    					obInfo.setBigDecimal("yhxAmount",yhxAmount.subtract(fp_benci));
    					try {
    						ire.updatePartial(rhInfo,sic);
							iob.updatePartial(obInfo,sic);
						} catch (EASBizException e) {
							e.printStackTrace();
						}
						break;
					}
					
				}
			}
		}
    }
    
    
}