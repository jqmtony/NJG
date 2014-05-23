package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InsuranceCompanyFactory
{
    private InsuranceCompanyFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IInsuranceCompany getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInsuranceCompany)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A0354285") ,com.kingdee.eas.port.equipment.base.IInsuranceCompany.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IInsuranceCompany getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInsuranceCompany)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A0354285") ,com.kingdee.eas.port.equipment.base.IInsuranceCompany.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IInsuranceCompany getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInsuranceCompany)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A0354285"));
    }
    public static com.kingdee.eas.port.equipment.base.IInsuranceCompany getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInsuranceCompany)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A0354285"));
    }
}