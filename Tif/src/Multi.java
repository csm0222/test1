import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

 
public class Multi {
	public static final String EDMS_IMG_DOWN_ROOT = "C:" + File.separator + "img" + File.separator; 
	public static void main(String[] args) throws Exception {
		// �Ķ���� ���޺��� ?userNumber=000011&elementIds=1.tif@@2.tif@@
		String userNumber = "000011";// ������� ���丮�� ����� ���� �Է¹޴´�.
		String elementIds = "1.tif@@2.tif";
		String fileNames[] = elementIds.split("@@"); // elementIds�� ��ȯ�Ѵ�.
		String userDirectory = makeUserDirectory(userNumber);

		MakeUserTempDir_001(userDirectory);// ������� ��Ʈ ���丮 �Ʒ� �ӽ� ���丮�� �����.
		
		String[] downFileNames = EdmsDown_002(userDirectory, fileNames);// TODO : edms ���� �ٿ�ε� & ���� 

		MultiTiffFileMake_003();// TODO : �ٿ���� ���ϵ� ����(tiff ������ ���� ���������� �����Ѵ�.)
		
		Thread.sleep(5000);
		
		EdmsUp_004();// TODO : multi tiff edms ���
		// ����
		DeleteUserTempDir_005(new File(userDirectory));
	}

	/**
	 * ����� �ӽ� ���丮 ����
	 * EDMS_IMG_DOWN_ROOT ��Ʈ���丮 + ��� + "_" + �ú��ʹи���
	 * @param userNumber
	 * @return
	 */
	private static String makeUserDirectory(String userNumber) {
		String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME).replaceAll("[-:.]", "");
		String userDirectory;
		if(userNumber == null || userNumber.isEmpty()) {
			userDirectory = "0000_" + now + File.separator;
		} else {
			userDirectory = userNumber + "_" + now+ File.separator;
		}
		return EDMS_IMG_DOWN_ROOT + userDirectory;
	}
	/**
	 * ������� ����� ���丮 ��θ� �Է¹޾� ����
	 * @param userDirectory
	 */
	public static void MakeUserTempDir_001(String userDirectory) {
		File rootDir = new File(userDirectory);
		if(!rootDir.exists()) {
			rootDir.mkdirs();
			System.out.println("directory make : " + userDirectory.toString());
		} else {
			System.out.println("directory exists ");
		}
	}

	/**
	 * elementId�� edms�� ���� ������ �ٿ�ε� �޴´�.
	 * @param userDirectory
	 * @param fileNames
	 */
	public static String[] EdmsDown_002(String userDirectory, String fileNames[]) {
		String[] downFileNames = new String[fileNames.length];
		
		for(int i = 0, max = fileNames.length; i < max; i++) {
			try {
				Path path = Paths.get("C:" + File.separator + "img" + File.separator + fileNames[i]); // TODO : edms���ϴٿ�ε�� ġȯ�ϱ�
				Files.write(Paths.get(userDirectory + fileNames[i]), Files.readAllBytes(path), StandardOpenOption.CREATE);
				downFileNames[i] = userDirectory + fileNames[i];
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return downFileNames;
	}
	/**
	 * �ٿ���� �̹������� Multi tiff ���Ϸ� �����Ѵ�.
	 * @return
	 */
	public static boolean MultiTiffFileMake_003(){
		
		return true;
	}
	
	/**
	 * edms ���� ���ε�
	 */
	private static void EdmsUp_004() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * tiff�� �ٿ�޾� �����ϴ� �ӽ� ���丮�� �����Ѵ�.
	 * @param path
	 * @return
	 */
	public static boolean DeleteUserTempDir_005(File path) {
		if (!path.exists()) {
			return false;
		}
		File[] files = path.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				DeleteUserTempDir_005(file);
			} else {
				file.delete();
			}
		}
		System.out.println("directory delete : " + path.toString());
		return path.delete();
	}

}