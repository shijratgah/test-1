package kenshu;

public class Stone {

	//黒の石の数
	private int black;

	//白の石の数
	private int white;

	/**
	 * コンストラクタで初期化
	 * @param black 黒の石の数
	 * @param white 白の石の数
	 */
	public Stone(int black, int white){
		this.black = black;
		this.white = white;
	}

	/**
	 * ゲッターメソッド
	 * @return 黒の石の数
	 */
	public int getBlack(){
		return black;
	}

	/**
	 * ゲッターメソッド
	 * @return 白の石の数
	 */
	public int getWhite(){
		return white;
	}

	/**
	 * セッターメソッド
	 * @param black
	 */
	public void setBlack(int black){
		this.black = black;
	}

	/**
	 * セッターメソッド
	 * @param white
	 */
	public void setWhite(int white){
		this.white = white;
	}

}
