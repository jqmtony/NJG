package com.kingdee.eas.fi.ar;

import java.io.IOException;
import java.util.Set;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.framework.agent.IObjectCollectionAgent;
import com.kingdee.bos.framework.agent.ObjectCollectionAgentImpl;
import com.kingdee.bos.framework.agent.SerializableObjectCollectionAgent;

public class OtherBillCollectionAgent extends OtherBillCollection implements IObjectCollectionAgent {
	private final IObjectCollectionAgent agent;
	
    public OtherBillCollectionAgent() {
        super();
        this.agent = new ObjectCollectionAgentImpl();
    }
    
    public void addOV(IObjectPK pk) {
        this.agent.addOV(pk);
    }

    public void deleteOV(IObjectPK pk) {
        this.agent.deleteOV(pk);
    }

    public Set getDeletedOVSet() {
        return this.agent.getDeletedOVSet();
    }

    public IObjectCollection getTargetOV() {
    	return this;
    }

    public boolean isDeletedOV(IObjectPK pk) {
        return this.agent.isDeletedOV(pk);
    }
    
    public void marshal(com.kingdee.util.marshal.Marshaller marshaller) throws IOException {
		new com.kingdee.bos.framework.agent.ObjectCollectionAgentMarshalDelegate(this).marshal(marshaller);		
	}

	public void unmarshal(com.kingdee.util.marshal.Unmarshaller unmarshaller) throws IOException, ClassNotFoundException {
		new com.kingdee.bos.framework.agent.ObjectCollectionAgentMarshalDelegate(this).unmarshal(unmarshaller);
	}


}