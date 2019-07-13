package nao.debugger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Debugger {
	private Debugger() {}
	
	private static boolean enable = false;
	private static PrintStream oldPrintStream;
	
	/**
	 * 
	 * @param enable: if enable Error is writting to a File and to the old PrintStream
	 */
	public synchronized static void redirectErrorOutputs(boolean enable) {
		if(enable) {
			oldPrintStream = System.err;
			
			StringBuilder pathBuilder = new StringBuilder();
			
			{
				String currPath = getJarPath();
				if(currPath != null)
					pathBuilder.append(currPath);
			}
			
			if(pathBuilder.length() > 0)
				pathBuilder.append("/errlogs");
			
			final String path = pathBuilder.toString();
			System.out.println("Error Log Files in " + ((path == null || path.isEmpty()) ? "<No File available>" : path));
			
			PrintStream errorPrintStream = new PrintStream(new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					oldPrintStream.write(b);
					
					//I hope you have not lot's of erros. This function can be slow down the errorPrintStream
					if(path != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						File file = new File(path + "/log" + sdf.format(new Date()) + ".txt");
						file.getParentFile().mkdirs();
						
						FileWriter fileWriter = new FileWriter(file, true);
						fileWriter.append((char) b);
						fileWriter.close();
					}
				}
			});
			
			System.setErr(errorPrintStream);
		} else
			System.setErr(oldPrintStream);
	}
	
	private static String getJarPath(){
		final CodeSource source = Debugger.class.getProtectionDomain().getCodeSource();
		if (source != null) {
			try {
				String quelle = source.getLocation().toURI().getPath();
				
				int idx = quelle.lastIndexOf('/', quelle.length());
				quelle = quelle.substring(0, idx);
				
				return quelle;
			} catch (URISyntaxException e) {}
		}
		return null;
	}
	
	public static void setEnable(boolean value) {
		enable = value;
		redirectErrorOutputs(enable);
	}
	
	public static boolean isEnable() {
		return enable;
	}
}
