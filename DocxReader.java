package reader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DocxReader {

	 static List<String> skillsList = new ArrayList<>();
	 static List<String> emailList = new ArrayList<>();
	 static List<String> phoneNumbers = new ArrayList<>();
	public static void readDocxFile(String fileName) {
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			XWPFDocument document = new XWPFDocument(fis);
			List<XWPFParagraph> paragraphs = document.getParagraphs();
			for (int i = 0; i < paragraphs.size(); i++) {
				String bodyData =(paragraphs.get(i).getParagraphText());
			//	System.out.println(bodyData);
				findEmail(bodyData);
				findPhoneNumber(bodyData);	
				findApplicantSkills(bodyData);
			}
			System.out.println(skillsList);
			System.out.println(emailList);
			System.out.println(phoneNumbers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
private static void findEmail(String details) {
		
		Pattern pattern = Pattern.compile(RegEx.EMAIL.toString(), Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(details);
		while (matcher.find()) {
			emailList.add(matcher.group());
		}
	}

	/**
	 * Find phone numbers in the resume
	 * 
	 * @param line to search for
	 * @return phone numbers found from resume
	 */
	private static void findPhoneNumber(String line) {
	
		Pattern pattern = Pattern.compile(RegEx.PHONE.toString(), Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			phoneNumbers.add(matcher.group());
		}
	}

	private static String findEducations(String line) {
		ParserHelper parser = new ParserHelper();
		// just like findWorkExperiences
		int indexOfEducation = parser.getIndexOfThisSection(RegEx.EDUCATION, line);
		if (indexOfEducation != -1) {
			int nextSectionIndex = 0;
			List<Integer> listOfSectionIndexes = parser.getAllSectionIndexes(line);
			String educationsText = line.replaceFirst(RegEx.EDUCATION.toString(), "");
			for (int index = 0; index < listOfSectionIndexes.size(); index++) {
				if (listOfSectionIndexes.get(index) == indexOfEducation) {
					// if education is the last section, then there is no
					// nextSectionIndex
					if (index == listOfSectionIndexes.size() - 1) {
						return educationsText.substring(indexOfEducation);
					} else {
						// index + 1: where index is the index of education section heading, +1
						// the index of the next section heading
						nextSectionIndex = listOfSectionIndexes.get(index + 1);
						break;
					}
				}
			}
			return educationsText.substring(indexOfEducation, nextSectionIndex);
		}
		return "";
	}

	private static String findWorkExperiences(String line) {
		ParserHelper parser = new ParserHelper();
		/*
		 * Algorithm: copy texts starting from experience section index to the following
		 * section index experience index is LESS THAN the following section index,
		 * therefore
		 * 
		 * Example: section indexes [24, 355, 534, 669] index of experience section =
		 * 355 therefore, the following section index would be 534 we can get the texts
		 * that encompasses experience section by substring => (indexOfExperience,
		 * beginIndexOfFollowingSection)
		 * 
		 */
		int indexOfExperience = parser.getIndexOfThisSection(RegEx.EXPERIENCE, line);
		if (indexOfExperience != -1) {
			int nextSectionIndex = 0; // index that follows experience section
			String experiencesText = line.replaceFirst(RegEx.EXPERIENCE.toString(), "");
			for (int index = 0; index < parser.getAllSectionIndexes(line).size(); index++) {
				if (parser.getAllSectionIndexes(line).get(index) == indexOfExperience) {
					// experience section is not always in the middle
					// rarely they may appear as the last section
					if (index == parser.getAllSectionIndexes(line).size() - 1) {
						return experiencesText.substring(indexOfExperience);
					} else {
						nextSectionIndex = parser.getAllSectionIndexes(line).get(index + 1);
						break;
					}
				}
			}
			return experiencesText.substring(indexOfExperience, nextSectionIndex);
		}
		return "";
	}
	private static void findApplicantSkills(String skillsFromResume) {
		Pattern pattern = Pattern.compile(RegEx.SKILLS.toString(),Pattern.DOTALL |Pattern.MULTILINE );
		Matcher matcher = pattern.matcher(skillsFromResume);
		while (matcher.find()) {
			
			skillsList.add(matcher.group());
			//System.out.println(skillsList);
		}
	}
	public static void main(String[] args) {
		readDocxFile("/home/agile/Downloads/Resume.docx");
	}
}