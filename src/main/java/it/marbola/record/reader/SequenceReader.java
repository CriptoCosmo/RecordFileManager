package it.marbola.record.reader;

public class SequenceReader {

	private int cursor ;
	private String text;

	public SequenceReader(String text){
		this.text = text;
		cursor = 0;
	}

	public void jump(int count){
		cursor += count;
	}

	public String next(int lengthOfField){
		return next(lengthOfField,false);
	}

	public String next(int lengthOfField,boolean trim){

		String substring = null;

		try{
			substring = text.substring(cursor, cursor+lengthOfField);
		}catch (IndexOutOfBoundsException e){
			return null;
		}

		jump(lengthOfField);

		return trim ? substring.trim() : substring;
	}
}
