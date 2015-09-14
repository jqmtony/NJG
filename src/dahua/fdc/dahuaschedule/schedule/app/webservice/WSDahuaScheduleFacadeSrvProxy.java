package com.kingdee.eas.fdc.dahuaschedule.schedule.app.webservice;

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

public class WSDahuaScheduleFacadeSrvProxy { 

    public String[] createTempData( String xml ) throws WSInvokeException {
        try {
            return getController().createTempData(
            xml);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    private com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade getController() {
        try {
        if (WSConfig.getRomoteLocate()!=null&&WSConfig.getRomoteLocate().equals("false")){
            Message message =MessageContext.getCurrentContext().getRequestMessage();
            SOAPEnvelope soap =message.getSOAPEnvelope();
            SOAPHeaderElement headerElement=soap.getHeaderByName(WSConfig.loginQName,WSConfig.loginSessionId);
            String SessionId=headerElement.getValue();
            return ( com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade )BOSObjectFactory.createBOSObject( SessionId , "com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleFacade") ; 
        } else {
            return ( com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade )BOSObjectFactory.createRemoteBOSObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleFacade" , com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade.class ) ; 
        }
        }
        catch( Throwable e ) {
            return ( com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade )ORMEngine.createRemoteObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleFacade" , com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade.class ) ; 
        }
    }

    private BeanConvertHelper getBeanConvertor() {
        return new BeanConvertHelper(); 
    }

}