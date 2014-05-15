package com.kingdee.eas.port.equipment.insurance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InsuranceDeclarationStateFactory
{
    private InsuranceDeclarationStateFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationState getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationState)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("78256D68") ,com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationState.class);
    }
    
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationState getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationState)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("78256D68") ,com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationState.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationState getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationState)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("78256D68"));
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationState getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceDeclarationState)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("78256D68"));
    }
}