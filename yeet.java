package cs141.robward;

/**
 * @author Akhil Lal
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class MorseConverter implements Converter {
	
	private HashMap<String, String> letterToMorse = new HashMap<String, String>();	
	
	private HashMap<String, String> morseToLetter = new HashMap<String, String>();
	
	public MorseConverter(String encode) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(encode))) {

			String line;
			while ((line = br.readLine()) != null) {

				String items[] = line.split(" ");
				letterToMorse.put(items[0], items[1]);
				morseToLetter.put(items[1], items[0]);
			}
			letterToMorse.put(" ", " ");
			morseToLetter.put(" ", "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printKeyValuePairs() {
		
		for(String name: letterToMorse.keySet())
		{
			String key = name.toString();
			String value = letterToMorse.get(name).toString();
			System.out.println(key + " " + value);
		}
	}

	@Override
	public String encodeSentence(String textToEncode) {
		
		String letters[] = textToEncode.split("");
		StringBuilder encodedWord = new StringBuilder();
		char letter;
		for(int i = 0; i < letters.length; i++)
		{
			letter = letters[i].charAt(0);
			if((int)letter < 96 && letter != 32 && letter != 58)
			{
				int dec = (int)letter + 32;
				char let = (char)dec;
				letters[i] = Character.toString(let);
			}
			String finalLetter = letterToMorse.get(letters[i]);
			encodedWord.append(finalLetter);
			encodedWord.append(" ");
		}
		return encodedWord.toString();
	}

	@Override
	public String encodeWord(String textToEncode) {
		
		String letters[] = textToEncode.split("");
		StringBuilder encodedWord = new StringBuilder();
		char letter;
		for(int i = 0; i < letters.length; i++)
		{
			letter = letters[i].charAt(0);
			if((int)letter < 96)
			{
				int dec = (int)letter + 32;
				char let = (char)dec;
				letters[i] = Character.toString(let);
			}
			String finalLetter = letterToMorse.get(letters[i]);
			encodedWord.append(finalLetter);
			encodedWord.append(" ");
		}
		return encodedWord.toString();
	}

	@Override
	public String decodeSentence(String textToDecode) {
		
		String words[] = textToDecode.split("  ");
		StringBuilder encodedSentence = new StringBuilder();
		for(int i = 0; i < words.length; i++)
		{
			encodedSentence.append(words[i]);
			encodedSentence.append(" ");
		}
		String eS = encodedSentence.toString();
		String letters[] = eS.split(" ");
		StringBuilder encodedWords = new StringBuilder();
		for(int i = 0; i < letters.length; i++)
		{
			String morseLetter = morseToLetter.get(letters[i]);
			if(morseLetter != null)
			{
				encodedWords.append(morseLetter);
			}
			else encodedWords.append(" ");
			
		}
		return encodedWords.toString();
	}

	@Override
	public String decodeWord(String wordToDecode) {
		
		String letters[] = wordToDecode.split(" ");
		StringBuilder encodedWord = new StringBuilder();
		for(int i = 0; i < letters.length; i++)
		{
			String morseLetter = morseToLetter.get(letters[i]);
			encodedWord.append(morseLetter);
		}
		return encodedWord.toString();
	}

	@Override
	public boolean encodeFileToFile(String plainTextFileName, String encodedTextFileName) {
		
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(plainTextFileName))) {
			
			String line;
			while ((line = br.readLine()) != null) {

				sb.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	
		StringBuilder encodedWord = new StringBuilder();
		String letters[] = sb.toString().split("");
		char letter;
		for(int i = 0; i < letters.length; i++)
		{
			letter = letters[i].charAt(0);
			if((int)letter < 96 && letter != 32 && letter != 58 && letter != 46 && letter != 39)
			{
				int dec = (int)letter + 32;
				char let = (char)dec;
				letters[i] = Character.toString(let);
			}
			String finalLetter = letterToMorse.get(letters[i]);
			encodedWord.append(finalLetter);
			encodedWord.append(" ");
		}
		try {
			
			FileWriter file = new FileWriter(encodedTextFileName);
			file.write(encodedWord.toString());
			file.close();
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public boolean decodeFileToFile(String encodedTextFileName, String plainTextFileName) {
		
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(encodedTextFileName))) {
			
			String line;
			while ((line = br.readLine()) != null) {

				sb.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String words[] = sb.toString().split("  ");
		StringBuilder encodedSentence = new StringBuilder();
		for(int i = 0; i < words.length; i++)
		{
			encodedSentence.append(words[i]);
			encodedSentence.append(" ");
		}
		String eS = encodedSentence.toString();
		String letters[] = eS.split(" ");
		StringBuilder encodedWords = new StringBuilder();
		for(int i = 0; i < letters.length; i++)
		{
			String morseLetter = morseToLetter.get(letters[i]);
			if(morseLetter != null)
			{
				encodedWords.append(morseLetter);
			}
			else encodedWords.append(" ");
		}
		try {
			
			FileWriter file = new FileWriter(plainTextFileName);
			file.write(encodedWords.toString());
			file.close();
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();

		}
		return false;
	}

}
