package lib.romsanbryan.utils.files.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;

public class CsvPojo {
	private static int counter = 0;


	/**
	 * 
	 * @param filereader FileReader with file's url
	 * @param start Character start file, default #
	 * @param separator Separator for columns, default ,
	 * @throws Exception
	 */
	public static void csvToVirtualPojo(FileReader filereader, String start, String separator) throws Exception {
		String[] fieldNames = null;
		Class<?> rowObjectClass = null;
		
		try (BufferedReader stream = new BufferedReader(filereader)) {
			while (true) {
				String line = stream.readLine();
				if (line == null) {
					break;
				}
				if (line.isEmpty() || line.startsWith("#")) {
					continue;
				}
				if (rowObjectClass == null) {
					fieldNames = line.split(",");
					rowObjectClass = buildCSVClass(fieldNames);
				} else {
					String[] values = line.split(",");
					Object rowObject = rowObjectClass.newInstance();
					for (int i = 0; i < fieldNames.length; i++) {
						Field f = rowObjectClass.getDeclaredField(fieldNames[i]);
						f.setAccessible(true);
						f.set(rowObject, values[i]);
					}
					System.out.println(reflectToString(rowObject));
				}
			}
		}
	}


	private static Class<?> buildCSVClass(String[] fieldNames) throws CannotCompileException, NotFoundException {
		ClassPool pool = ClassPool.getDefault();
		CtClass result = pool.makeClass("CSV_CLASS$" + (counter++));
		ClassFile classFile = result.getClassFile();
		ConstPool constPool = classFile.getConstPool();
		classFile.setSuperclass(Object.class.getName());
		for (String fieldName : fieldNames) {
			CtField field = new CtField(ClassPool.getDefault().get(String.class.getName()), fieldName, result);
			result.addField(field);
		}
		classFile.setVersionToJava5();
		return result.toClass();
	}

	private static String reflectToString(Object value) throws IllegalAccessException {
		StringBuilder result = new StringBuilder();
//    StringBuilder result = new StringBuilder(value.getClass().getName());
//    result.append("@").append(System.identityHashCode(value)).append(" {");
		for (Field f : value.getClass().getDeclaredFields()) {
			f.setAccessible(true);
//        result.append("\n\t").append(f.getName()).append(" = ").append(f.get(value)).append(", ");
			result.append(f.getName()).append(" = ").append(f.get(value)).append(", ");
		}
		result.delete(result.length() - 2, result.length());
		return result.toString();
	}
}
