package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InsuranceFactory
{
    private InsuranceFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IInsurance getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInsurance)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9128AED8") ,com.kingdee.eas.port.equipment.base.IInsurance.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IInsurance getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInsurance)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9128AED8") ,com.kingdee.eas.port.equipment.base.IInsurance.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IInsurance getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInsurance)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9128AED8"));
    }
    public static com.kingdee.eas.port.equipment.base.IInsurance getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInsurance)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9128AED8"));
    }
}