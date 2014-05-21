package cz.zcu.kiv.jamp.services;

import java.util.Scanner;
import org.springframework.stereotype.Service;

/**
 * Can be used for output string modify - adding HTML tags
 *
 * @author Petr Kukrál a Jan Kotalík
 */
@Service
public class OutputTagService {

	/**
	 * Modify entire content
	 *
	 * @param content input string to modify
	 * @return modifed string
	 */
	public String addTagsToContent(String content) {
		Scanner scanner = new Scanner(content);
		String result = "";
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			result = result + addTagsToLine(line);
			// process the line
		}
		scanner.close();
		return result;
	}

	/**
	 * Add HTML tags to single line
	 *
	 * @param line Line of result
	 * @return Line of result with html tags
	 */
	public String addTagsToLine(String line) {
		//types
		String info = "[INFO]";
		String error = "[ERROR]";
		String warning = "[WARNING]";

		if (line.contains(info)) {
			line = line.replace(info, "<span class='label label-info'>" + info + "</span>");
		} else if (line.contains(error)) {
			line = line.replace(error, "<span class='label label-danger'>" + error + "</span>");
		} else if (line.contains(warning)) {
			line = line.replace(warning, "<span class='label label-warning'>" + warning + "</span>");
		}

		line = line + "<br/>";

		return line;
	}
}
