package compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class Projfx {
	
	enum OS_NAME{
		WINDOW,
		LINUX,
	}
	
	private static OS_NAME os;
	
	static Process[] processList = new Process[10];
	static int pid = 0;
	static String sep;
	
	public static String folderName;
	
	public static boolean isWin(String name) {
		return name.indexOf("win") >= 0;
	}
	
	public static boolean isLin(String name) {
		
		return name.indexOf("nux") >= 0;
	}
	
	public static void getOS(){
		
		if(isWin(System.getProperty("os.name").toLowerCase())) {
			os = OS_NAME.WINDOW;
		}else if(isLin(System.getProperty("os.name").toLowerCase())) {
			os = OS_NAME.LINUX;
		}
	}
	
	
	public static String getAllJars(String path){
		//String str = Paths.get(System.getProperty("user.dir")).toString();
		String str = Paths.get(path).toString();
		//System.out.println(str);
		File file = new File(str);
		
		
		StringBuilder sb = new StringBuilder();
	 
		File[] arr = file.listFiles();
		for(File f: arr){
			if(f.getName().endsWith(".jar")){
				sb.append(f.getAbsolutePath() + sep);
			}
		}
	 
		String s = sb.toString();
		s = s.substring(0, s.length()-1);
	 
		//System.out.println(s);
		return s;
	}
	
	public static void main(String[] args) throws InterruptedException {
		Projfx.getOS();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
            	System.out.println("Killing all Processus");
                Projfx.destroyAllProcess();	
                
            }
        });
		//System.getProperties().list(System.out);
        String projectPath = Paths.get(System.getProperty("user.dir"),"sketchBooks").toString();//"C:\\dev\\pjfx";//
        String projectName = "PImageTest";//"Test";
        if(args.length > 0) {
        	projectName = args[0];
        }
        if(args.length > 1) {
        	projectPath = args[1];
        }
        File f = new File(Paths.get(projectPath, projectName).toString()); 
        if(! f.exists()) {
        	System.err.println("ErrorSketch not Found : " + f.toString());
        	System.exit(-1);
        }
        
       
        
		String file = Paths.get("sketch", "template", "__UserDefault.java").toString();
		String bin = Paths.get(System.getProperty("user.dir"), "bin").toString();
		String engine = Paths.get("engine", "Core.java").toString();
//		String file = Paths.get("src","sketch", "template", "__UserDefault.java").toString();
//	    TODO: Change to a dynamic or platform independant path
		//String modulePath = "C:\\dev\\libs\\java\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib";
		String platformModulePath;
		String commonModulPath = Paths.get(System.getProperty("user.dir"), "libs","com").toString();
		switch(os) {
		case WINDOW:
			System.out.println("Window "+ System.getProperty("user.dir"));
			platformModulePath = Paths.get(System.getProperty("user.dir"), "libs","window", "javafx", "lib").toString();
			sep = ";";
			break;
		default:
		case LINUX:
			System.out.println("Linux");
			platformModulePath = Paths.get(System.getProperty("user.dir"), "libs", "linux", "javafx", "lib").toString();
			sep = ":";
			break;
		}
		String classPath = "-classpath \"" + bin + sep + getAllJars(platformModulePath) + sep + getAllJars(commonModulPath)+"\"";
		//String platformModulePath = "%PATH_TO_FX%";
		/*Paths.get("..","ProcessFX-0.5_lib","javafx.base.jar").toString() + sep +
				Paths.get("..","ProcessFX-0.5_lib","javafx.controls.jar").toString() + sep +
				Paths.get("..","ProcessFX-0.5_lib","javafx.fxml.jar").toString() + sep +
				Paths.get("..","ProcessFX-0.5_lib","javafx.graphics.jar").toString() + sep +
				Paths.get("..","ProcessFX-0.5_lib","javafx.media.jar").toString() + sep +
				Paths.get("..","ProcessFX-0.5_lib","javafx.swing.jar").toString() + sep +
				Paths.get("..","ProcessFX-0.5_lib","javafx.web.jar").toString() + sep +
				Paths.get("..","ProcessFX-0.5_lib","javafx-swt.jar").toString()
				;*/
		System.out.println(classPath);
		
		String buildPath = Paths.get(System.getProperty("user.dir")).toString();
		
		String srcPath = Paths.get(System.getProperty("user.dir"), "src").toString();
//		String srcPath = Paths.get(System.getProperty("user.dir")).toString();
		
		String preCompileLine = "java -cp \"" + bin + "\" precompiler.PreCompiler";
//		String preCompileLine = "java precompiler.PreCompiler";
		String preCompileArgs = "--project-path \"" + projectPath + "\" --project-name " + projectName;
		String compile = "javac "+ classPath +" --module-path "+platformModulePath+ " -d " + bin + " --add-modules javafx.controls " + file;
		String runLine = "java -XX:+UseParallelGC -Xms1536m -Xmx1536m -XX:NewRatio=2 "+classPath +" --module-path \""+platformModulePath+"\" --add-modules javafx.controls -Djavafx.animation.fullspeed=true -Dfile.encoding=Cp1252 engine.Core " + projectPath + " " +projectName;
		
		/*String clear_linux = "CMD.exe /C dir";
		String clear_window = "CMD.exe /C dir";
		 */
		
		System.out.println(preCompileArgs);
		try {
			
			/*if(runProcess("pwd", "CMD.exe /C dir", srcPath) != 0) {
				destroyAllProcess();
				return;
			}*/
			
			if(runProcess("Pre-compiler", new String[] {preCompileLine, preCompileArgs}, srcPath) != 0) {
				destroyAllProcess();
				return;
			}
			if(runProcess("Compiler", compile, srcPath) != 0) {
				destroyAllProcess();
				return;
			}/*
			switch(os) {
			case WINDOW:
				if(runProcess("CMD.exe /C cls", srcPath) != 0) {
					destroyAllProcess();
					return;
				}
				break;
			default:		
			case LINUX:
				//TODO: find the actual linux comand to clear console;
				if(runProcess("clear", srcPath) != 0) {
					destroyAllProcess();
					return;
				}
				break;
			}*/
			//System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nRunning Sketch");
			if(runProcess("Running", runLine, srcPath, false) != 0) {
				destroyAllProcess();
				return;
			}

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private static void destroyAllProcess() {
		for(int i = 0; i  < pid; ++i) {
			processList[i].destroy();
		}
	}
	
	private static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
            new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + line);
        }
    }
	
	private static void printErrors(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(	
            new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.err.println(cmd + " " + line);
        }
    } 
	
	public static int runProcess(String command) throws Exception {
		Process pro = Runtime.getRuntime().exec(command);
		processList[pid++] = pro;
        printLines(command + " stdout:", pro.getInputStream());
        printErrors(command + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)
        	System.err.println(command + " exited with " + pro.exitValue());
        else
        	System.out.println(command + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
	
	public static int runProcess(String[] command) throws Exception {
		Process pro = Runtime.getRuntime().exec(command);
		processList[pid++] = pro;
        printLines(command + " stdout:", pro.getInputStream());
        printErrors(command + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)
        	System.err.println(command + " exited with " + pro.exitValue());
        else
        	System.out.println(command + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
	
	public static int runProcess(String command, String dir) throws Exception {
		Process pro = Runtime.getRuntime().exec(command, null, new File(dir));
		processList[pid++] = pro;
        printLines(command + " stdout:", pro.getInputStream());
        printErrors(command + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)
        	System.err.println(command + " exited with " + pro.exitValue());
        else
        	System.out.println(command + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
	
	public static int runProcess(String name, String command, String dir) throws Exception {
		//System.out.println("Command" + name + " " +  command + " " + dir);
		Process pro = Runtime.getRuntime().exec(command, null, new File(dir));
		processList[pid++] = pro;
        printLines(name + " stdout:", pro.getInputStream());
        printErrors(name + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)
        	System.err.println(name + " exited with " + pro.exitValue());
        else
        	System.out.println(name + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
	
	
	public static int runProcess(String name, String[] command, String dir) throws Exception {
		String finalCommand = "";
		for(int i = 0; i < command.length; ++i) {
			finalCommand += command[i] + " ";
		}	
		
		Process pro = Runtime.getRuntime().exec(finalCommand, null, new File(dir));
		processList[pid++] = pro;
        printLines(name + " stdout:", pro.getInputStream());
        printErrors(name + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)
        	System.err.println(name + " exited with " + pro.exitValue());
        else
        	System.out.println(name + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
	
	public static int runProcess(String[] command, String dir) throws Exception {
		String finalCommand = "";
		for(int i = 0; i < command.length; ++i) {
			finalCommand += command[i] + " ";
		}
		
		Process pro = Runtime.getRuntime().exec(finalCommand, null, new File(dir));
		processList[pid++] = pro;
        printLines(finalCommand + " stdout:", pro.getInputStream());
        printErrors(finalCommand + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)	
        	System.err.println(finalCommand + " exited with " + pro.exitValue());
        else
        	System.out.println(finalCommand + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
	
	public static int runProcess(String name, String command, String dir, boolean TitleOutput) throws Exception {
		//System.out.println("Command" + name + " " +  command + " " + dir);
		Process pro = Runtime.getRuntime().exec(command, null, new File(dir));
		processList[pid++] = pro;
		if(TitleOutput) {
			printLines(name + " stdout:", pro.getInputStream());
			printErrors(name + " stderr", pro.getErrorStream());
		}else {
			printLines("", pro.getInputStream());
			printErrors("", pro.getErrorStream());
		}
        pro.waitFor();
        if(pro.exitValue() != 0)
        	System.err.println(name + " exited with " + pro.exitValue());
        else
        	System.out.println(name + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
}
