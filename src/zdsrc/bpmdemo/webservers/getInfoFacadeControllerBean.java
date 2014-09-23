package com.kingdee.eas.bpmdemo.webservers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.selectors.ContractFacade;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;

public class getInfoFacadeControllerBean extends AbstractgetInfoFacadeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.bpmdemo.webservers.getInfoFacadeControllerBean"); 
    protected String[] _SubmitResult(Context ctx, String strBSID,
			String strBOID, boolean success, int procInstID, String procURL,
			String strMessage)
			throws BOSException {
    	
    	String[] str = new String[3];
    	IObjectValue billInfo = null;
    	try {
			billInfo = DynamicObjectFactory.getLocalInstance(ctx).getValue(new ObjectUuidPK(strBOID).getObjectType(),new ObjectUuidPK(strBOID));
    	}catch (Exception e) {
			str[2] = "根据单据ID获取对象数据失败,请检查单据ID是否存在，并查看服务器log日志";
			e.printStackTrace();
		}finally{
			if(billInfo instanceof ContractBillInfo){
				str = new ContractFacade().SubmitResult(ctx, strBSID, billInfo, success, procInstID, procURL, strMessage);
			}
			return str;
		}
		
	}
	protected String[] _ApproveClose(Context ctx, String strBSID, String strBOID,
			int procInstID, String processInstanceResult, String strComment,
			Date dtTime) throws BOSException {
		
		String[] str = new String[3];
    	IObjectValue billInfo = null;
    	try {
			billInfo = DynamicObjectFactory.getLocalInstance(ctx).getValue(new ObjectUuidPK(strBOID).getObjectType(),new ObjectUuidPK(strBOID));
    	}catch (Exception e) {
			str[2] = "根据单据ID获取对象数据失败,请检查单据ID是否存在，并查看服务器log日志";
			e.printStackTrace();
		}finally{
			if(billInfo instanceof ContractBillInfo){
				str = new ContractFacade().ApproveClose(ctx, strBSID, billInfo, procInstID, processInstanceResult, strComment, dtTime);
			}
			return str;
		}
	}

	protected String[] _GetbillInfo(Context ctx, String strBSID, String strBOID) throws BOSException {
		
		String[] str = new String[3];
    	IObjectValue billInfo = null;
    	try {
			billInfo = DynamicObjectFactory.getLocalInstance(ctx).getValue(new ObjectUuidPK(strBOID).getObjectType(),new ObjectUuidPK(strBOID));
    	}catch (Exception e) {
			str[2] = "根据单据ID获取对象数据失败,请检查单据ID是否存在，并查看服务器log日志";
			e.printStackTrace();
		}finally{
			if(billInfo instanceof ContractBillInfo){
				str = new ContractFacade().GetbillInfo(ctx, strBSID, billInfo);
			}
			return str;
		}
	}
	
	protected String[] _GetrRelatedBillInfo(Context ctx, String strBTID,
			String strBOID, String strRelatedCode) throws BOSException {
		return super._GetrRelatedBillInfo(ctx, strBTID, strBOID, strRelatedCode);
	}
	

}