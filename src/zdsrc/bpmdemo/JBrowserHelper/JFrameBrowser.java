package com.kingdee.eas.bpmdemo.JBrowserHelper;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;

import com.kingdee.bos.ctrl.swing.KDFrame;
import com.kingdee.bos.ui.face.CoreUIObject;



public class JFrameBrowser extends KDFrame{
	
	
//	使用样例
//	JFrameBrowser jf = new JFrameBrowser();
//	jf.setJBrowserSize(500, 800);
//	jf.setJBrwserOpenUrl("http://www.baidu.com");
//	
//	jf.setTitle("提交BMP");
//	
//	jf.OpenJBrowser();

	private static final long serialVersionUID = 104878919756793793L;
	public int Bh = 0;
	public int Bw = 0;
	
	public String openUrl = "";
	public String tit = "";
	
	
	/**
	 * 设置JBrowser宽度和高度
	 * @param h
	 * @param w
	 */
	public void setJBrowserSize(int h,int w)
	{
		this.Bh = h;
		this.Bw = w;
	}
	
	/**
	 * 设置需要打开至JBrowser的url
	 */
	public void setJBrwserOpenUrl(String url)
	{
		this.openUrl = url;
	}
	
	/**
	 * 打开浏览器
	 */
	public void OpenJBrowser(CoreUIObject ui)
	{
		if (!NativeInterface.isOpen()) 
			NativeInterface.open();
		
		this.setVisible(true);
		
		this.setSize(new Dimension(Bw,Bh));
		
		this.setLocationRelativeTo(null);
		this.getContentPane().add(new JpanleBrowser(openUrl), BorderLayout.CENTER);
      
		this.setUndecorated(false);
		
		this.setLocationRelativeTo(ui);
		
		if (NativeInterface.isOpen()) 
        	NativeInterface.runEventPump();
	}
	
	
	public JPanel getPanel()
	{
		return new JpanleBrowser(openUrl);
	}
	
}
