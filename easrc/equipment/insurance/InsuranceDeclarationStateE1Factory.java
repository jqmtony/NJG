package com.kingdee.eas.port.equipment.insurance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InsuranceDeclarationStateE1Factory
{
    private InsuranceDeclarationStateE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationStateE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationStateE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("047FBBF4") ,com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationStateE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationStateE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationStateE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("047FBBF4") ,com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationStateE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationStateE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationStateE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("047FBBF4"));
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationStateE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationStateE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("047FBBF4"));
    }
}