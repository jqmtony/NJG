/*
 * @(#)IProgressService.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Image;

/**
 * 进度监视器的执行接口。
 * 
 * @author allenhe  date:2007-1-19 <p>
 * @version EAS5.1.3
 */
public interface IFDCRunnableWithProgress {
	public void run(IFDCProgressMonitor monitor);
}
