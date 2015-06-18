package com.kingdee.eas.custom.richbase.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.*;
import com.kingdee.bos.json.JSONObject;

import java.lang.String;
import java.text.SimpleDateFormat;

import com.kingdee.eas.basedata.master.cssp.CustomerCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.custom.richbase.CustomerSyncLogFactory;
import com.kingdee.eas.custom.richbase.CustomerSyncLogInfo;
import com.kingdee.eas.custom.richbase.ICustomerSyncLog;
import com.kingdee.eas.custom.richtimer.client.ReserveServiceLocator;
import com.kingdee.eas.custom.richtimer.client.ReserveServicePortType;
import com.kingdee.eas.util.SysUtil;

public class CustomerSyncLogControllerBean extends AbstractCustomerSyncLogControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.richbase.app.CustomerSyncLogControllerBean");
    
    @Override
    protected String _syncCustomer(Context ctx) throws BOSException {
    	String backMsg = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String oql = "select fid from t_bd_customer where to_char(flastupdatetime,'yyyy-mm-dd')='"+sdf.format(SysUtil.getAppServerTime(ctx))+"'";
			CustomerCollection ccolls = CustomerFactory.getLocalInstance(ctx).getCustomerCollection("where id in("+oql+") and usedStatus=1");
	    	if(ccolls.size() > 0) {
	    		ReserveServicePortType rspt = new ReserveServiceLocator().getreserveServiceHttpSoap11Endpoint();
	        	JSONObject params = null;
	        	CustomerInfo cinfo = null;
	        	String result = null;
	        	int corrects = 0;
	        	ICustomerSyncLog ics = CustomerSyncLogFactory.getLocalInstance(ctx);
	        	CustomerSyncLogInfo logInfo = null;
	    		for(int i=ccolls.size()-1; i>=0; i--) {
	    			params = new JSONObject();
	    			cinfo = ccolls.get(i);
	    			//单位代码
	    			params.put("dwdm",cinfo.getNumber());
	    			//单位名称
	    			params.put("dwmc",cinfo.getName());
	    			//医疗机构
	    			params.put("yljg",cinfo.getCU().getName());
	    			//单位地址
	    			params.put("dwdz",cinfo.getAddress());
	    			//params.put("lxr",);
	    			params.put("bz",cinfo.getDescription());
	    			//params.put("lxdh","110");
	    			//params.put("dqmc","长宁区");
	    			//params.put("sqry",SysContext.getSysContext().getCurrentUserInfo().getName());
	    			params.put("version",cinfo.getVersion());
	    			result = rspt.saveCompanyInfo(params.toString());
	    			params = new JSONObject(result);
	    			boolean flag = params.optBoolean("result");
	    			if(flag) {
	    				corrects++;
	    			}
	    			logInfo = new CustomerSyncLogInfo();
	    			logInfo.setCustomerNum(cinfo.getNumber());
	    			logInfo.setCustomerName(cinfo.getName());
	    			logInfo.setSyncDate(SysUtil.getAppServerTime(ctx));
	    			logInfo.setIsSuccess(flag);
	    			logInfo.setTipsInfo(params.optString("info"));
	    			ics.addnew(logInfo);
	    		}
	    		backMsg="同步"+ccolls.size()+"条数据，其中成功数量："+corrects;
	    	}else {
	    		backMsg="没有符合条件的客户，不进行同步操作！";
	    	}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    	
    	return backMsg;
    }
}