/*
 * @(#)IProgressService.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Image;

/**
 * ���ȼ�������ִ�нӿڡ�
 * 
 * @author allenhe  date:2007-1-19 <p>
 * @version EAS5.1.3
 */
public interface IFDCRunnableWithProgress {
	public void run(IFDCProgressMonitor monitor);
}
