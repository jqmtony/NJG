package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NodeMeasureFactory
{
    private NodeMeasureFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.INodeMeasure getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.INodeMeasure)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("62B483E1") ,com.kingdee.eas.fdc.contract.INodeMeasure.class);
    }
    
    public static com.kingdee.eas.fdc.contract.INodeMeasure getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.INodeMeasure)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("62B483E1") ,com.kingdee.eas.fdc.contract.INodeMeasure.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.INodeMeasure getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.INodeMeasure)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("62B483E1"));
    }
    public static com.kingdee.eas.fdc.contract.INodeMeasure getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.INodeMeasure)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("62B483E1"));
    }
}