package com.kingdee.eas.fdc.photomanager;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IPhotoNumber extends IDataBase
{
    public PhotoNumberInfo getPhotoNumberInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PhotoNumberInfo getPhotoNumberInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PhotoNumberInfo getPhotoNumberInfo(String oql) throws BOSException, EASBizException;
    public PhotoNumberCollection getPhotoNumberCollection() throws BOSException;
    public PhotoNumberCollection getPhotoNumberCollection(EntityViewInfo view) throws BOSException;
    public PhotoNumberCollection getPhotoNumberCollection(String oql) throws BOSException;
}