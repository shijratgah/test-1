package kenshu;

public class Board {

	//盤面の値を所持した二次元配列
	private int[][] banmen = new int[8][8];

	/**
	 * コンストラクタ
	 * 配列を初期化する
	 */
	protected void init(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				//白の石を指定の座標に置く
				if((i == 3 && j == 3) || (i == 4 && j == 4)){
					banmen [i][j] = 1;
				//黒の石を指定の座標に置く
				}else if((i == 3 && j == 4) || (i == 4 && j == 3)){
					banmen [i][j] = 2;
				//それ以外は0を代入
				}else{
					banmen[i][j] = 0;
				}

			}
		}
	}

	/**
	 * ゲッターメソッド
	 * @return　int型二次元配列
	 */
	public int[][] getBanmen(){
		return banmen;
	}

	/**
	 * セッターメソッド
	 * @param banmen
	 */
	public void setBanmen(int[][] banmen){
		this.banmen = banmen;
	}

	/**
	 * 配列の情報をコンソールに出力するメソッド
	 */
	public void display(){

		System.out.print("   ");

		//x軸の番号を出力
		for(int i = 0; i < 8; i++){
			System.out.print(i + "  ");
		}
		//改行
		System.out.println();


		for(int i = 0; i < 8; i++){
			//y軸の番号を出力
			System.out.print((i) * 10 + " ");
			//いちばん最初に空白を出力
			if(i == 0){
				System.out.print(" ");
			}
			for(int j = 0; j < 8; j++){
				//要素の中身が1なら○を出力
				if(banmen[i][j] == 1){
					System.out.print("○ ");
				//要素の中身が2なら●を出力
				}else if(banmen[i][j] == 2){
					System.out.print("● ");
				//それ以外は＊を出力
				}else{
					System.out.print("＊ ");
				}
			}
			System.out.println();
		}
	}



}
