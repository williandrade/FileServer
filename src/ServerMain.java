import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import me.williandrade.service.DirectoryWatchService;
import me.williandrade.service.SimpleDirectoryWatchService;

public class ServerMain {

	public static void main(String[] args) throws Exception {

		String path = "/Users/william/Documents/projects/networkClass/dir";

		DirectoryWatchService watchService = new SimpleDirectoryWatchService();
		watchService.register(new DirectoryWatchService.OnFileChangeListener() {
			@Override
			public void onFileCreate(String filePath) {
				process(filePath, path);
			}

			@Override
			public void onFileModify(String filePath) {
				process(filePath, path);
			}

			@Override
			public void onFileDelete(String filePath) {
			}
		}, path);

		watchService.start();
	}

	private static void process(String filePath, String path) {
		if (filePath.endsWith(".toCalc")) {
			try {
				String content = getContent(path, filePath);
				String[] parts = content.split(":::");

				String num1 = parts[0];
				String operator = parts[1];
				String num2 = parts[2];

				String result = null;

				switch (operator) {
				case "x":
					Float multiplyVal = Float.parseFloat(num1) * Float.parseFloat(num2);
					result = multiplyVal.toString();
					break;
				case "+":
					Float plusVal = Float.parseFloat(num1) + Float.parseFloat(num2);
					result = plusVal.toString();
					break;
				case "-":
					Float subtractVal = Float.parseFloat(num1) - Float.parseFloat(num2);
					result = subtractVal.toString();
					break;
				case "/":
					Float divaidVal = Float.parseFloat(num1) / Float.parseFloat(num2);
					result = divaidVal.toString();
					break;
				default:
					break;
				}

				BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/" + filePath + ".finish"));
				bw.write(result);
				bw.close();

				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String getContent(String path, String fileName) throws Exception {
		byte[] encoded = Files.readAllBytes(Paths.get(path + "/" + fileName));
		String content = new String(encoded, "UTF-8");
		return content;
	}

}
