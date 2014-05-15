package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QualityDefectTrackFactory
{
    private QualityDefectTrackFactory()
    {
    }
    public static com.kingdee.eas.port.pm.qa.IQualityDefectTrack getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IQualityDefectTrack)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9463DD7D") ,com.kingdee.eas.port.pm.qa.IQualityDefectTrack.class);
    }
    
    public static com.kingdee.eas.port.pm.qa.IQualityDefectTrack getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IQualityDefectTrack)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9463DD7D") ,com.kingdee.eas.port.pm.qa.IQualityDefectTrack.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.qa.IQualityDefectTrack getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IQualityDefectTrack)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9463DD7D"));
    }
    public static com.kingdee.eas.port.pm.qa.IQualityDefectTrack getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IQualityDefectTrack)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9463DD7D"));
    }
}