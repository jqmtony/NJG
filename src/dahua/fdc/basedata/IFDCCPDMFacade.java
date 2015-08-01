package com.kingdee.eas.fdc.basedata;

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

public interface IFDCCPDMFacade extends IBizCtrl
{
    public ArchiveResultInfo upLoadFileToCP(ArchiveDocumentParamsInfo params, String content, String[] fileNames, List fileStream) throws BOSException, EASBizException;
    public List downLoadFileFromCP(String bizID, String docID) throws BOSException, EASBizException;
    public void deleteCPFile(String bizID, String docID) throws BOSException, EASBizException;
    public void addFileToCP(String bizID, String fileName, byte[] fileStream) throws BOSException, EASBizException;
}