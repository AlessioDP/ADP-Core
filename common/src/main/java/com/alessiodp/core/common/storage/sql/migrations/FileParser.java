package com.alessiodp.core.common.storage.sql.migrations;

import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {
	private Reader fileReader;
	
	@Getter private String scriptName;
	@Getter private int version;
	@Getter private String description;
	@Getter private LinkedList<String> queries;
	
	public FileParser(@NonNull InputStream inputStream, @NonNull String fileName) {
		fileReader = new InputStreamReader(inputStream);
		scriptName = fileName;
		queries = new LinkedList<>();
	}
	
	/**
	 * Parse the file
	 *
	 * @throws IOException if anything goes wrong with the parsing
	 */
	public void parse() throws IOException {
		parseName(scriptName);
		String rs = readStatement();
		while (rs != null) {
			queries.add(rs);
			rs = readStatement();
		}
	}
	
	/**
	 * Read the next statement from the file reader
	 *
	 * @return the next statement
	 * @throws IOException if anything goes wrong with the reading
	 */
	public String readStatement() throws IOException {
		StringBuilder sb = new StringBuilder();
		int c = fileReader.read();
		
		while (true) {
			switch(c) {
				// End of file
				case -1:
					if (sb.length() == 0)
						return null;
				// End of statement
				case ';':
					return sb.toString();
				// Spacing characters
				case ' ':
				case '\t':
				case '\r':
				case '\n':
					if (sb.length() == 0 || sb.charAt(sb.length() - 1) == ' ')
						break;
					sb.append(' ');
					break;
				// Comments
				case '-':
					c = fileReader.read();
					if (c == '-') {
						// Read until new line
						while (c != -1 && c != '\r' && c != '\n') {
							c = fileReader.read();
						}
					} else {
						sb.append('-');
					}
					break;
				default:
					sb.append((char) c);
					break;
			}
			
			c = fileReader.read();
		}
	}
	
	/**
	 * Parse the file name
	 *
	 * @param name the name to parse
	 */
	public void parseName(String name) {
		Matcher matcher = Pattern.compile("^([0-9]+)__(.*)\\.sql$").matcher(name);
		if (matcher.matches()) {
			version = Integer.parseInt(matcher.group(1));
			description = matcher.group(2);
		} else {
			throw new RuntimeException("Invalid migration file '" + name + "'");
		}
	}
}
