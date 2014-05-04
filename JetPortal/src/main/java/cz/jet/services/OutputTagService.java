package cz.jet.services;

import java.util.Scanner;
import org.springframework.stereotype.Service;

@Service
public class OutputTagService {

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
	 * Add HTML tag to string
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
			line = line.replace(info, "<td><span class='info'>" + info + "</span></td><td>");
			line = "<tr class='info'>" + line + "</td></tr>";
		} else if (line.contains(error)) {
			line = line.replace(error, "<td><span class='error'>" + error + "</span></td><td>");
			line = "<tr class='error'>" + line + "</td></tr>";
		} else if (line.contains(warning)) {
			line = line.replace(warning, "<td><span class='warning'>" + warning + "</span></td><td>");
			line = "<tr class='warning'>" + line + "</td></tr>";
		}

		//line
		String strLine = "----*";//Str.matches
		if (line.matches(strLine)) {
			line = line.replaceAll(strLine, "<hr />");
		}
		return line;
	}
}
