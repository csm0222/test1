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
		// 파라미터 전달변후 ?userNumber=000011&elementIds=1.tif@@2.tif@@
		String userNumber = "000011";// 행번으로 디렉토리를 만들기 위해 입력받는다.
		String elementIds = "1.tif@@2.tif";
		String fileNames[] = elementIds.split("@@"); // elementIds로 변환한다.
		String userDirectory = makeUserDirectory(userNumber);

		MakeUserTempDir_001(userDirectory);// 행번으로 루트 디렉토리 아래 임시 디렉토리를 만든다.
		
		String[] downFileNames = EdmsDown_002(userDirectory, fileNames);// TODO : edms 파일 다운로드 & 저장 

		MultiTiffFileMake_003();// TODO : 다운받은 파일들 머지(tiff 파일을 분해 순차적으로 조립한다.)
		
		Thread.sleep(5000);
		
		EdmsUp_004();// TODO : multi tiff edms 등록
		// 삭제
		DeleteUserTempDir_005(new File(userDirectory));
	}

	/**
	 * 사용자 임시 디렉토리 생성
	 * EDMS_IMG_DOWN_ROOT 루트디렉토리 + 행번 + "_" + 시분초밀리초
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
	 * 행번까지 적용된 디렉토리 경로를 입력받아 저장
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
	 * elementId로 edms로 부터 파일을 다운로드 받는다.
	 * @param userDirectory
	 * @param fileNames
	 */
	public static String[] EdmsDown_002(String userDirectory, String fileNames[]) {
		String[] downFileNames = new String[fileNames.length];
		
		for(int i = 0, max = fileNames.length; i < max; i++) {
			try {
				Path path = Paths.get("C:" + File.separator + "img" + File.separator + fileNames[i]); // TODO : edms파일다운로드로 치환하기
				Files.write(Paths.get(userDirectory + fileNames[i]), Files.readAllBytes(path), StandardOpenOption.CREATE);
				downFileNames[i] = userDirectory + fileNames[i];
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return downFileNames;
	}
	/**
	 * 다운받은 이미지들을 Multi tiff 파일로 병합한다.
	 * @return
	 */
	public static boolean MultiTiffFileMake_003(){
		
		return true;
	}
	
	/**
	 * edms 파일 업로드
	 */
	private static void EdmsUp_004() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * tiff를 다운받아 머지하는 임시 디렉토리를 삭제한다.
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