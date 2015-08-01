package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.cp.dm.archive.ArchiveDocumentParamsInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.cp.dm.archive.ArchiveResultInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCCPDMFacadeController extends BizController
{
    public ArchiveResultInfo upLoadFileToCP(Context ctx, ArchiveDocumentParamsInfo params, String content, String[] fileNames, List fileStream) throws BOSException, EASBizException, RemoteException;
    public List downLoadFileFromCP(Context ctx, String bizID, String docID) throws BOSException, EASBizException, RemoteException;
    public void deleteCPFile(Context ctx, String bizID, String docID) throws BOSException, EASBizException, RemoteException;
    public void addFileToCP(Context ctx, String bizID, String fileName, byte[] fileStream) throws BOSException, EASBizException, RemoteException;
}