package sketch.template;

import java.nio.file.Paths;

import util.FileManager;

public class SketchTemplate {/*
	String pakageName = "package sketch.template";
	String[] importation = { "import constant.CURSOR", "import constant.SETTINGS", "import util.PVector",
			"import util.color", "import engine.Sketch", "import java.util.ArrayList",
			"import javafx.scene.image.Image" };
	String className = "__UserDefault";
	String sketchFileName;
	String classScope = "public class";
	String classExtension = "extends Sketch";

	String fileContent = "";
	String directory = "";
	static final String srcDir = Paths.get(System.getProperty("user.dir")).getParent().toString();
	static final String buildDir = FileManager.path(srcDir, "src", "sketch", "template");
	static final String fileExtension = ".pjfx";
	static String projectName = null;
	static String dirPath = FileManager.path(srcDir, "sketchBooks");
	static String fullPath;

	static String[] flags = {"--project-path", "--project-name"};
	
	public static void main(String[] args) {
		if(args != null) {
			System.out.println(args[0]);
			for(int i = 0; i < args.length; ++i) {
				switch(args[i]){
					case "--project-path":
						dirPath = args[++i];
						break;
					case "--project-name":
						projectName = args[++i];
						break;
					default:
						System.out.println("argument " + args[i] + " ignored");
						break;
				}
			}
			 
		}
		if(projectName == null)
			projectName = "project1";
		fullPath = FileManager.path(dirPath, projectName);
		
		//System.out.println(dirPath + "\n" + buildDir + "\n" + srcDir + "\n" + projectName +"\n" + fullPath + "\n");
		new SketchTemplate(FileManager.listFile(fullPath, "pjfx"), fullPath);
	}

	public SketchTemplate() {
		sketchFileName = "Sketch" + (int) (Math.random() * 1000000);
		buildHeader();
		closeFile();
		saveFile();
		//System.out.println(fileContent);
	}

	public SketchTemplate(String[] sketchNames, String dir) {

		buildHeader();
		getSaveDirectory(dir);
		//System.out.println(sketchNames);
		for (String sketchName : sketchNames) {
			System.out.println(sketchName);
			sketchFileName = sketchName;
			if (!getFileContent(sketchFileName)) {
				System.err.println("Error: File " + sketchFileName + " not Found");
				System.exit(-1);
			}
		}
		closeFile();
		saveFile();
	}

	public SketchTemplate(String sketchName) {
		sketchFileName = sketchName;
		buildHeader();
		if (!getFileContent(sketchFileName + fileExtension)) {
			System.err.println("Error: File " + sketchFileName + fileExtension + " not Found");
			System.exit(-1);
		}
		closeFile();
		saveFile();
	}

	public SketchTemplate(String sketchName, String dir) {
		sketchFileName = sketchName;
		buildHeader();
		getSaveDirectory(dir);
		if (!getFileContent(sketchFileName + fileExtension)) {
			System.err.println("Error: File " + sketchFileName + fileExtension + " not Found in " + directory);
			// System.out.println("End2");
			System.exit(-1);
		}
		closeFile();
		saveFile();
	}

	private void getSaveDirectory(String dir) {
		// TODO Auto-generated method stub
		directory = FileManager.path(dir);
		//System.out.println(directory);
	}

	private boolean getFileContent(String fileName) {
		System.out.println(FileManager.path(directory, fileName));
		String t = FileManager.getTextFileData(fileName, directory);
		if (t == null)
			return false;
		fileContent += t;
		return true;
	}

	private void closeFile() {
		fileContent += "}";

	}

	public void buildHeader() {
		fileContent += pakageName + ";\n";
		for (String importLine : importation) {
			fileContent += importLine + ";\n";
		}
		fileContent += classScope + " " + className + " " + classExtension + " {\n";
	}

	public void saveFile() {
		String srcFileName = className + ".java";
		// String fullPath = FileManager.path(srcDir, buildDir)
		System.out.println("output >> " + FileManager.path(buildDir,"src", srcFileName) + "\n" + fileContent);
		FileManager.newFile(srcFileName, buildDir, fileContent);
	}*/
}
