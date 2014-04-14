package cn.kane.jvm.chap8.classLoader;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;



public final class ClassLoaderFactory {

	public static ClassLoader createClassLoader(File unpacked[],File packed[], ClassLoader parent, boolean delegate) throws Exception {
		System.out.println("Creating new class loader");
        // Construct the "class path" for this class loader
        ArrayList<String> list = new ArrayList<String>();
        // Add unpacked directories
        if (unpacked != null) {
            for (int i = 0; i < unpacked.length; i++)  {
                File file = unpacked[i];
                if (!file.isDirectory() || !file.exists() || !file.canRead())
                    continue;
                System.out.println("  Including directory " + file.getAbsolutePath());
                URL url = new URL("file", null,file.getCanonicalPath() + File.separator);
                list.add(url.toString());
            }
        }
        // Add packed directory JAR files
        if (packed != null) {
            for (int i = 0; i < packed.length; i++) {
                File directory = packed[i];
                if (!directory.isDirectory() || !directory.exists() || !directory.canRead())
                    continue;
                String filenames[] = directory.list();
                for (int j = 0; j < filenames.length; j++) {
                    String filename = filenames[j].toLowerCase();
                    if (!filename.endsWith(".jar"))
                        continue;
                    File file = new File(directory, filenames[j]);
                    System.out.println("  Including jar file " + file.getAbsolutePath());
                    URL url = new URL("file", null, file.getCanonicalPath());
                    list.add(url.toString());
                }
            }
        }
        // Construct the class loader itself
        String array[] = (String[]) list.toArray(new String[list.size()]);
        MyClassLoader classLoader = null;
        if (parent == null)
            classLoader = new MyClassLoader(array);
        else
            classLoader = new MyClassLoader(array, parent);
        classLoader.setDelegate(delegate);
        return (classLoader);
    }


}
