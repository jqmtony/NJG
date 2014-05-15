package com.kingdee.eas.port.equipment.insurance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InsuranceCoverageFactory
{
    private InsuranceCoverageFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceCoverage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceCoverage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("46F6E919") ,com.kingdee.eas.port.equipment.insurance.IInsuranceCoverage.class);
    }
    
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceCoverage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceCoverage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("46F6E919") ,com.kingdee.eas.port.equipment.insurance.IInsuranceCoverage.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceCoverage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceCoverage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("46F6E919"));
    }
    public static com.kingdee.eas.port.equipment.insurance.IInsuranceCoverage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.insurance.IInsuranceCoverage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("46F6E919"));
    }
}