package com.kingdee.eas.bpmdemo.webservers.webservice;

import org.apache.axis.Message;

import org.apache.axis.MessageContext;

import org.apache.axis.message.SOAPEnvelope;

import org.apache.axis.message.SOAPHeaderElement;

import com.kingdee.bos.webservice.WSConfig;

import com.kingdee.bos.webservice.WSInvokeException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ObjectMultiPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.orm.core.ORMEngine;
import com.kingdee.bos.webservice.BeanConvertHelper;
import com.kingdee.bos.webservice.BOSTypeConvertor;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.webservice.WSConfig;
import com.kingdee.bos.webservice.MetaDataHelper;
import com.kingdee.bos.BOSObjectFactory;

public class WSgetInfoFacadeSrvProxy { 

    public void GetProgressReport( String Domxml ) throws WSInvokeException {
        try {
            getController().GetProgressReport(
            Domxml
            );
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String[] ApproveClose( String strBSID , String strBOID , int iProcInstID , String eProcessInstanceResult , String strComment , String dtTime ) throws WSInvokeException {
        try {
            return getController().ApproveClose(
            strBSID,
            strBOID,
            iProcInstID,
            eProcessInstanceResult,
            strComment,
            BOSTypeConvertor.string2Date(dtTime));
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String[] GetrRelatedBillInfo( String strBTID , String strBOID , String strRelatedCode ) throws WSInvokeException {
        try {
            return getController().GetrRelatedBillInfo(
            strBTID,
            strBOID,
            strRelatedCode);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String[] GetcurProject() throws WSInvokeException {
        try {
            return getController().GetcurProject();
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String[] SubmitResult( String strBSID , String strBOID , boolean bSuccess , int iProcInstID , String procURL , String strMessage ) throws WSInvokeException {
        try {
            return getController().SubmitResult(
            strBSID,
            strBOID,
            bSuccess,
            iProcInstID,
            procURL,
            strMessage);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public void GetTaskEvalation( String Domxml ) throws WSInvokeException {
        try {
            getController().GetTaskEvalation(
            Domxml
            );
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String[] Getpoint( String pointID ) throws WSInvokeException {
        try {
            return getController().Getpoint(
            pointID);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String[] ApproveBack( String strBTID , String strBOID , String strXML ) throws WSInvokeException {
        try {
            return getController().ApproveBack(
            strBTID,
            strBOID,
            strXML);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String[] GetbillInfo( String strBSID , String strBOID ) throws WSInvokeException {
        try {
            return getController().GetbillInfo(
            strBSID,
            strBOID);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String[] GetDemo() throws WSInvokeException {
        try {
            return getController().GetDemo();
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    private com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade getController() {
        try {
        if (WSConfig.getRomoteLocate()!=null&&WSConfig.getRomoteLocate().equals("false")){
            Message message =MessageContext.getCurrentContext().getRequestMessage();
            SOAPEnvelope soap =message.getSOAPEnvelope();
            SOAPHeaderElement headerElement=soap.getHeaderByName(WSConfig.loginQName,WSConfig.loginSessionId);
            String SessionId=headerElement.getValue();
            return ( com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade )BOSObjectFactory.createBOSObject( SessionId , "com.kingdee.eas.bpmdemo.webservers.getInfoFacade") ; 
        } else {
            return ( com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade )BOSObjectFactory.createRemoteBOSObject( WSConfig.getSrvURL() , "com.kingdee.eas.bpmdemo.webservers.getInfoFacade" , com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade.class ) ; 
        }
        }
        catch( Throwable e ) {
            return ( com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade )ORMEngine.createRemoteObject( WSConfig.getSrvURL() , "com.kingdee.eas.bpmdemo.webservers.getInfoFacade" , com.kingdee.eas.bpmdemo.webservers.IgetInfoFacade.class ) ; 
        }
    }

    private BeanConvertHelper getBeanConvertor() {
        return new BeanConvertHelper(); 
    }

}