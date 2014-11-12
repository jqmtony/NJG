package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class ContractWithoutTextControllerBeanEx extends com.kingdee.eas.fdc.contract.app.ContractWithoutTextControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractWithoutTextControllerBeanEx");
    
    protected IObjectPK _save(Context ctx, IObjectValue model)throws BOSException, EASBizException{
    	ContractWithoutTextInfo con = (ContractWithoutTextInfo)model;
    	PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
    	IObjectPK pk = super._save(ctx, con);
    	for(int i=0;i<prbi.getBgEntry().size();i++){
			prbi.getBgEntry().get(i).setId(null);
			prbi.getBgEntry().get(i).setHead(con.getPayRequestBill());
			PayRequestBillBgEntryFactory.getLocalInstance(ctx).addnew(prbi.getBgEntry().get(i));
		}
    	SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("costedCompany");
    	sic.add("costedDept");
    	con.getPayRequestBill().setCostedCompany(prbi.getCostedCompany());
    	con.getPayRequestBill().setCostedDept(prbi.getCostedDept());
    	PayRequestBillFactory.getLocalInstance(ctx).updatePartial(con.getPayRequestBill(), sic);
    	return pk;
    }
    protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException{
    	ContractWithoutTextInfo con = (ContractWithoutTextInfo)model;
    	PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
    	IObjectPK pk = super._submit(ctx, con);
    	for(int i=0;i<prbi.getBgEntry().size();i++){
			prbi.getBgEntry().get(i).setId(null);
			prbi.getBgEntry().get(i).setHead(con.getPayRequestBill());
			PayRequestBillBgEntryFactory.getLocalInstance(ctx).addnew(prbi.getBgEntry().get(i));
		}
    	SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("costedCompany");
    	sic.add("costedDept");
    	con.getPayRequestBill().setCostedCompany(prbi.getCostedCompany());
    	con.getPayRequestBill().setCostedDept(prbi.getCostedDept());
    	PayRequestBillFactory.getLocalInstance(ctx).updatePartial(con.getPayRequestBill(), sic);
    	return pk;
    }
}				
