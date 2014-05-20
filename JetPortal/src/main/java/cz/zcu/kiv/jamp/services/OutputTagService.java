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
			line = line.replace(info, "<span class='info'>" + info + "</span>");
			//line = "<tr class='info'><td>" + line + "</td></tr>";
		} else if (line.contains(error)) {
			line = line.replace(error, "<span class='error'>" + error + "</span>");
			//line = "<tr class='danger'><td>" + line + "</td></tr>";
		} else if (line.contains(warning)) {
			line = line.replace(warning, "<span class='warning'>" + warning + "</span>");
			//line = "<tr class='warning'><td>" + line + "</td></tr>";
		} else {
			//line = "<tr><td>" + line + "</td></tr>";
		}

		//line
		String strLine = "----*";//Str.matches
		if (line.matches(strLine)) {
			line = line.replaceAll(strLine, "<hr />");
		}
		return line;
	}
}
