package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QualityDefectTrackE1Factory
{
    private QualityDefectTrackE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.qa.IQualityDefectTrackE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IQualityDefectTrackE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0AE27AC9") ,com.kingdee.eas.port.pm.qa.IQualityDefectTrackE1.class);
    }
    
    public static com.kingdee.eas.port.pm.qa.IQualityDefectTrackE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IQualityDefectTrackE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0AE27AC9") ,com.kingdee.eas.port.pm.qa.IQualityDefectTrackE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.qa.IQualityDefectTrackE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IQualityDefectTrackE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0AE27AC9"));
    }
    public static com.kingdee.eas.port.pm.qa.IQualityDefectTrackE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IQualityDefectTrackE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0AE27AC9"));
    }
}