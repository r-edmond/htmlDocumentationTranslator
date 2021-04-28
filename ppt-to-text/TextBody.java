public class TextBody {
	private static String text;
	private int slideNum;
	
	public TextBody() {
	}
	
	public TextBody(String text1, int slide) {
		this.text = text1;
		this.slideNum = slide;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	public void setSlideNum(int i) {
		this.slideNum = i;
	}
	
	public String getText() {
		return text;
	}
	public int getSlideNum() {
		return slideNum;
	}
	
	public String toString() {
		return ("==================\nSlide " + slideNum + ":\n\n" + text + "\n\n==================");
	}
}