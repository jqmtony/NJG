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
import com.kingdee.eas.bpm.common.UpdateUtil;
import com.kingdee.eas.bpm.common.ViewXmlUtil;
import com.kingdee.eas.bpm.selectors.ChangeAuditFacade;
import com.kingdee.eas.bpm.selectors.ChangeOfSettlementFacade;
import com.kingdee.eas.bpm.selectors.CompensationFacade;
import com.kingdee.eas.bpm.selectors.ContractFacade;
import com.kingdee.eas.bpm.selectors.ContractReviseFacade;
import com.kingdee.eas.bpm.selectors.ContractWithoutTextFacade;
import com.kingdee.eas.bpm.selectors.DeductBillFacade;
import com.kingdee.eas.bpm.selectors.JLFacade;
import com.kingdee.eas.bpm.selectors.PayRequestFacade;
import com.kingdee.eas.bpm.selectors.SettleMentFacade;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillReviseInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.fdc.finance.DeductBillInfo;

public class getInfoFacadeControllerBean extends AbstractgetInfoFacadeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.bpmdemo.webservers.getInfoFacadeControllerBean"); 
    public String[] _SubmitResult(Context ctx, String strBSID,
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
			if(billInfo instanceof ChangeAuditBillInfo)
			{
				str = new ChangeAuditFacade().SubmitResult(ctx, strBSID, billInfo, success, procInstID, procURL, strMessage);
			}
			if(billInfo instanceof ContractSettlementBillInfo)
			{
				str = new SettleMentFacade().SubmitResult(ctx, strBSID, billInfo, success, procInstID, procURL, strMessage);
			}
			if(billInfo instanceof PayRequestBillInfo)
			{
				str = new PayRequestFacade().SubmitResult(ctx, strBSID, billInfo, success, procInstID, procURL, strMessage);
			}
			if(billInfo instanceof ContractWithoutTextInfo)
			{
				str = new ContractWithoutTextFacade().SubmitResult(ctx, strBSID, billInfo, success, procInstID, procURL, strMessage);
			}
			if(billInfo instanceof ContractBillReviseInfo)
			{
				str = new ContractReviseFacade().SubmitResult(ctx, strBSID, billInfo, success, procInstID, procURL, strMessage);
			}                
			if(billInfo instanceof ContractChangeBillInfo)
			{
				str = new ChangeOfSettlementFacade().SubmitResult(ctx, strBSID, billInfo, success, procInstID, procURL, strMessage);
			}
			if(billInfo instanceof GuerdonBillInfo)
			{
				str=new JLFacade().SubmitResult(ctx, strBSID, billInfo, success, procInstID, procURL, strMessage);
			}
			if(billInfo instanceof CompensationBillInfo)
			{
				str=new CompensationFacade().SubmitResult(ctx, strBSID, billInfo, success, procInstID, procURL, strMessage);
			}
			if(billInfo instanceof DeductBillInfo)
			{
				str=new DeductBillFacade().SubmitResult(ctx, strBSID, billInfo, success, procInstID, procURL, strMessage);
			}
			return str;
		}
		
	}
    public String[] _ApproveClose(Context ctx, String strBSID, String strBOID,
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
			if(billInfo instanceof ChangeAuditBillInfo)
			{
				str = new ChangeAuditFacade().ApproveClose(ctx, strBSID, billInfo, procInstID, processInstanceResult, strComment, dtTime);
			}
			if(billInfo instanceof ContractSettlementBillInfo)
			{
				str = new SettleMentFacade().ApproveClose(ctx, strBSID, billInfo, procInstID, processInstanceResult, strComment, dtTime);
			}
			
			if(billInfo instanceof PayRequestBillInfo){
				str = new PayRequestFacade().ApproveClose(ctx, strBSID, billInfo, procInstID, processInstanceResult, strComment, dtTime);
			}
			if(billInfo instanceof ContractWithoutTextInfo){
				str = new ContractWithoutTextFacade().ApproveClose(ctx, strBSID, billInfo, procInstID, processInstanceResult, strComment, dtTime);
			}
			
			if(billInfo instanceof ContractBillReviseInfo)
			{
				str = new ContractReviseFacade().ApproveClose(ctx, strBSID, billInfo, procInstID, processInstanceResult, strComment, dtTime);
			}
			if(billInfo instanceof ContractChangeBillInfo)
			{
				str = new ChangeOfSettlementFacade().ApproveClose(ctx, strBSID, billInfo, procInstID, processInstanceResult, strComment, dtTime);
			}
			if(billInfo instanceof GuerdonBillInfo)
			{
				str=new JLFacade().ApproveClose(ctx, strBSID, billInfo, procInstID, processInstanceResult, strComment, dtTime);
			}
			if(billInfo instanceof CompensationBillInfo)
			{
				str=new CompensationFacade().ApproveClose(ctx, strBSID, billInfo, procInstID, processInstanceResult, strComment, dtTime);
			}
			if(billInfo instanceof DeductBillInfo)
			{
				str=new DeductBillFacade().ApproveClose(ctx, strBSID, billInfo, procInstID, processInstanceResult, strComment, dtTime);
			}

			return str;
		}
	}

	public String[] _GetbillInfo(Context ctx, String strBSID, String strBOID) throws BOSException {
		
		String[] str = new String[3];
    	IObjectValue billInfo = null;
    	try {
			billInfo = DynamicObjectFactory.getLocalInstance(ctx).getValue(new ObjectUuidPK(strBOID).getObjectType(),new ObjectUuidPK(strBOID));
			if(billInfo instanceof ContractBillInfo){
				str = new ContractFacade().GetbillInfo(ctx, strBSID, billInfo);	
			}
			if(billInfo instanceof ChangeAuditBillInfo)
			{
				str = new ChangeAuditFacade().GetbillInfo(ctx, strBSID, billInfo);
			}
			if(billInfo instanceof ContractSettlementBillInfo)
			{
				str = new SettleMentFacade().GetbillInfo(ctx, strBSID, billInfo);
			}
			if(billInfo instanceof PayRequestBillInfo)
			{
				str = new PayRequestFacade().GetbillInfo(ctx, strBSID, billInfo);
			}
			if(billInfo instanceof ContractWithoutTextInfo)
			{
				str = new ContractWithoutTextFacade().GetbillInfo(ctx, strBSID, billInfo);
			}
			if(billInfo instanceof ContractBillReviseInfo)
			{
				str = new ContractReviseFacade().GetbillInfo(ctx, strBSID, billInfo);
			}
			if(billInfo instanceof ContractChangeBillInfo)
			{
				str = new ChangeOfSettlementFacade().GetbillInfo(ctx, strBSID, billInfo);
			}
			if(billInfo instanceof GuerdonBillInfo)
			{
				//str=new JLFacade().GetbillInfo(ctx, strBSID, billInfo);
				str=ViewXmlUtil.getViewXmlHTString(ctx, "007",((GuerdonBillInfo) billInfo).getId().toString());
			}
			if(billInfo instanceof CompensationBillInfo)
			{
				//str=new CompensationFacade().GetbillInfo(ctx, strBSID, billInfo);
				str=ViewXmlUtil.getViewXmlHTString(ctx, "005",((CompensationBillInfo) billInfo).getId().toString());
			}
			if(billInfo instanceof DeductBillInfo)
			{
				//str=new DeductBillFacade().GetbillInfo(ctx, strBSID, billInfo);
				str=ViewXmlUtil.getViewXmlHTString(ctx, "006",((DeductBillInfo) billInfo).getId().toString());
			}
			
    	}catch (Exception e) {
			str[2] = "根据单据ID获取对象数据失败,请检查单据ID是否存在，并查看服务器log日志";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS结果:"+str[0]);
				log.setDescription("错误信息"+str[2]);
				log.setBeizhu("调用接口方法：_GetbillInfo");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}
	}
	
	public String[] _GetrRelatedBillInfo(Context ctx, String strBTID,String strBOID, String strRelatedCode) throws BOSException {
		String[] str = new String[3];
    	IObjectValue billInfo = null;
    	try {
			billInfo = DynamicObjectFactory.getLocalInstance(ctx).getValue(new ObjectUuidPK(strBOID).getObjectType(),new ObjectUuidPK(strBOID));
    	}catch (Exception e) {
			str[2] = "根据单据ID获取对象数据失败,请检查单据ID是否存在，并查看服务器log日志";
			e.printStackTrace();
		}finally{
			if(billInfo instanceof ContractBillInfo){
				 str =  ViewXmlUtil.getViewXmlString(ctx, strRelatedCode, ((ContractBillInfo) billInfo).getId().toString());
			}
			else if(billInfo instanceof ContractSettlementBillInfo)
			{
				 ContractSettlementBillInfo  SettlementBillInfo = ((ContractSettlementBillInfo) billInfo);
				 str =  ViewXmlUtil.getViewXmlString(ctx, strRelatedCode, SettlementBillInfo.getContractBill().getId().toString());
			}
			else{
				 str =  ViewXmlUtil.getViewXmlString(ctx, strRelatedCode, strBOID);	
			}
			return str;
		}
	}
	public String[] _ApproveBack(Context ctx, String strBTID,String strBOID, String strXML) throws BOSException {
		String[] str = new String[3];
		str = UpdateUtil.updateValue(ctx, strBTID, strBOID, strXML);
        return str;		
	}
	
	
}