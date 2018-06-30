package kenshu;

import kenshu2.Util;

public class GamePlay {

	//Boardクラスの参照
	private Board board;

	//Stoneクラスの参照
	private Stone stone;

	//どちらのターンか判別する。黒が2、白が1
	public int turn = 2;

	//入力された値を保持する変数
	private int place;

	//ビット演算に使用する変数
	public final int bit = 3;

	//盤面のx座標
	private int x;

	//盤面のy座標
	private int y;


	/**
	 * オセロゲームを開始から
	 * 終了までの一連の処理をするメソッド
	 */
	public void play(){

		do{
			//Boardクラスのインスタンスを作成
			board = new Board();

			//Stoneクラスのインスタンスを作成
			stone = new Stone(2, 2);

			//盤面の初期化
			board.init();

			//盤面の中身をコンソールに出力
			board.display();

			//オセロが終了するまで繰り返す
			while(game_end() != 0){

				//黒のターンの処理
				if(turn == 2){
					//パスでない場合
					if(pass() == 1){
						System.out.println("●のターン");

						//コンソールから数字を入力
						place = Util.input();

						//入力した値の10の位をyに代入
						y = place / 10;

						//入力した値を1の位をxに代入
					    x = place % 10;

					  //入力した値が配列の値を超えているともう一度入力してもらう
						if((y < 0 || y >= 8) || (x < 0 || x >= 8)){
							System.out.println("0から7の間で入力してください");
							continue;
						}

					    //置けなければ最初に戻る
					    if(put_check(y, x) == 0){

					    	System.out.println("そこには置けません");
					    	continue;

					    }

					    //裏返しの処理を実行
					    check(y, x);

					    //配列の中身をコンソールに表示
					    board.display();

					    //ターンの入れ替え
					    turn = 1;

					//パスの際の処理
					}else{
						System.out.println("黒はパスです");

						//ターンの入れ替え
						turn = 1;

						break;
					}
				//白のターンの処理
				}else if(turn == 1){
					//パスでない場合
					if(pass() == 1){
						System.out.println("○のターン");

						//コンソールから数字を入力
						place = Util.input();

						//入力した値の10の位をyに代入
						y = place / 10;

						//入力した値を1の位をxに代入
					    x = place % 10;

						//入力した値が配列の値を超えているともう一度入力してもらう
						if((y < 0 || y >= 8) || (x < 0 || x >= 8)){
							System.out.println("00から77の間で入力してください");
							continue;
						}


					    //置けなければ最初に戻る
					    if(put_check(y, x) == 0){

					    	System.out.println("そこには置けません");
					    	continue;

					    }

					    //裏返しの処理を実行
					    check(y, x);

					    //配列の中身をコンソールに表示
					    board.display();

					    //ターンの入れ替え
					    turn = 2;

					//パスの際の処理
					}else{
						System.out.println("白はパスです");

						//ターンの入れ替え
						turn = 2;

						break;
					}
				}

			}
			//勝敗を表示
			judge();

		//オセロゲームを続けるか選択
		}while(Util.retry() != 0);






	}


	/**
	 * ゲームが終了かどうか調べるメソッド
	 * @return 終了なら0、終了でなければ1を返す。
	 */
	public int game_end(){

		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){

				//配列の中身に0があるか調べる
				if(board.getBanmen()[i][j] == 0){
					return 1;
				}
			}
		}

		//黒と白の石、どちらか0でないか調べる
		if(stone.getBlack() != 0 || stone.getWhite() != 0){
			return 1;
		}
		return 0;
	}

	/**
	 * 勝敗を表示するメソッド
	 */
	public void judge(){
		int black = 0 , white = 0;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				//黒の石を数える
				if(board.getBanmen()[i][j] == 2){
					black++;
				//白の石を数える
				}else if(board.getBanmen()[i][j] == 1){
					white++;
				}
			}
		}
		//白が多かった際
		if(black < white){
			System.out.println(white + "枚で白の勝ち");
		//枚数が同じの際
		}else if(black == white){
			System.out.println("引き分け");
		//黒が多かった際
		}else{
			System.out.println(black + "枚で黒の勝ち");
		}

	}

	/**
	 * 挟める石があるか調べるメソッド
	 * @return 挟む石が無ければ0を返す。挟む石があれば1を返す。
	 */
	public int pass(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				//配列[0][0]の時、右、下、右斜め下にしか検索しない
				if(board.getBanmen()[i][j] == 0 && i == 0 && j == 0){
					//右方向に相手の石を見つけた際
					if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
						//右方向に進んでいく
						for(int a = j + 1; a < 8; a++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}

					}

					//下方向に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
						//下方向に進んでいく
						for(int a = i + 1; a < 8; a++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][j] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}

					}

					//右斜め下方向に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
						int b = j + 1;
						//右下方向に進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
					}

				//配列[0][7]の時、左、下、左斜め下にしか検索しない
				}else if(board.getBanmen()[i][j] == 0 && i == 0 && j == 7){

					//左方向に相手の石を見つけた際
					if(board.getBanmen()[i][j-1] == (turn ^ bit)){
						//左方向に進んでいく
						for(int a = j - 1; a >= 0; a--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}

				    }
					//下方向に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
						//下方向へ進んでいく
				    	for(int a = i + 1; a < 8; a++){
				    		//石が置いてない場所を見つけた際
				    		if(board.getBanmen()[a][j] == 0){
				    			break;
				    		//自分の石を見つけた際
				    		}else if(board.getBanmen()[a][j] == 1){
								return 1;
							}
						}

				    }
					//左斜め下方向に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
				    	int b = j - 1;
				    	//左下へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
				    }

				//配列[7][0]の時、上、右、右斜め上にしか検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 7 && j == 0){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}

			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}

			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    //配列[7][7]の時、左、上、左斜め上にしか検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 7 && j == 7){

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}
			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}
			    	}
			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    //配列[0][j]の時、上側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 0){

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}
			    	//左斜め下方向への検索
			    	if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}
			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}
			    	}

			    	//右斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    //配列[i][0]の時、左側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && j == 0){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				return 1;
			    			}
			    		}
			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    	//右斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}
			    	}

			    //配列[i][7]の時、右側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && j == 7){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				return 1;
			    			}
			    		}
			    	}

			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    	//左斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}
			    	}

			    //配列[7][j]の時、下側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 7){

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a > 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				return 1;
			    			}
			    		}
			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    //他の座標は周りをすべて調べる
			    }else if(board.getBanmen()[i][j] == 0){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				return 1;
			    			}
			    		}
			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    	//右斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}
			    	}

			    	//左斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    }
			}
		}
		return 0;
	}


	/**
	 * 石を置けるかどうか調べるメソッド
	 * @param i　コンソールから入力された十の位の数字
	 * @param j　コンソールから入力された一の位の数字
	 */
	public void check(int i, int j){
				//配列[0][0]の時、右、下、右斜め下にしか検索しない
				if(board.getBanmen()[i][j] == 0 && i == 0 && j == 0){
					//右方向に相手の石を見つけた際に
					if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
						//右方向へ進んでいく
						for(int a = j + 1; a < 8; a++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_E(i, j);
							}
						}

					}

					//下方向に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
						//下方向へ進んでいく
						for(int a = i + 1; a < 8; a++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][j] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][j] == turn){
								//裏返す処理
								flip_S(i, j);
							}
						}

					}

					//右斜め下に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
						int b = j + 1;
						//右下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_SE(i, j);
							}
						}
					}


				//配列[0][7]の時、左、下、左斜め下にしか検索しない
				}else if(board.getBanmen()[i][j] == 0 && i == 0 && j == 7){

					//左方向に相手の石を見つけた際
					if(board.getBanmen()[i][j-1] == (turn ^ bit)){
						//左方向へ進んでいく
						for(int a = j - 1; a >= 0; a--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_W(i, j);
							}
						}

				    }
					//下方向に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
						//下方向へ進んでいく
				    	for(int a = i + 1; a < 8; a++){
				    		//石が置いてない場所を見つけた際
				    		if(board.getBanmen()[a][j] == 0){
				    			break;
				    		//自分の石を見つけた際
				    		}else if(board.getBanmen()[a][j] == turn){
				    			//裏返す処理
								flip_S(i, j);
							}
						}

				    }
					//左斜め下に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
				    	int b = j - 1;
				    	//左下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_SW(i, j);
							}
						}
				    }

				//配列[7][0]の時、上、右、右斜め上にしか検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 7 && j == 0){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向に進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				//裏返す処理
								flip_N(i, j);
							}
						}

			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_E(i, j);
							}
						}

			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_NE(i, j);
							}
						}
			    	}

			    //配列[7][7]の時、左、上、左斜め上にしか検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 7 && j == 7){

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_W(i, j);
							}
						}
			    	}
			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				//裏返す処理
								flip_N(i, j);
							}
						}
			    	}
			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_NW(i, j);
							}
						}
			    	}

			    //配列[0][j]の時、上側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 0){

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向に進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_W(i, j);
							}
						}
			    	}
			    	//左斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_SW(i, j);
							}
						}
			    	}
			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				//裏返す処理
								flip_S(i, j);
							}
						}
			    	}

			    	//右斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_SE(i, j);
							}
						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_E(i, j);
							}
						}
			    	}

			    //配列[i][0]の時、左側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && j == 0){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				//裏返す処理
			    				flip_N(i, j);
			    			}
			    		}
			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_NE(i, j);
							}
						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_E(i, j);
							}
						}
			    	}

			    	//右斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_SE(i, j);
							}
						}
			    	}

			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				//裏返す処理
								flip_S(i, j);
							}
						}
			    	}

			    //配列[i][7]の時、右側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && j == 7){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				//裏返す処理
			    				flip_N(i, j);
			    			}
			    		}
			    	}

			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_NW(i, j);
							}
						}
			    	}

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_W(i, j);
							}
						}
			    	}

			    	//左斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_SW(i, j);
							}
						}
			    	}

			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				//裏返す処理
								flip_S(i, j);
							}
						}
			    	}

			    //配列[7][j]の時、下側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 7){

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_W(i, j);
							}
						}
			    	}

			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_NW(i, j);
							}
						}
			    	}

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				//裏返す処理
			    				flip_N(i, j);
			    			}
			    		}
			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_NE(i, j);
							}
						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_E(i, j);
							}
						}
			    	}

			    //他の座標は周りをすべて調べる
			    }else if(board.getBanmen()[i][j] == 0){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				//裏返す処理
			    				flip_N(i, j);
			    			}
			    		}
			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_NE(i, j);
							}

						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_E(i, j);
							}
						}
			    	}

			    	//右斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_SE(i, j);
							}
						}
			    	}

			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				//裏返す処理
								flip_S(i, j);
							}
						}
			    	}

			    	//左斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_SW(i, j);
							}
						}
			    	}

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								//裏返す処理
								flip_W(i, j);
							}
						}
			    	}

			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								//裏返す処理
								flip_NW(i, j);
							}
						}
			    	}

			    }



	}


	//上方向を裏返す処理
	private void flip_N(int i, int j){
		//盤面のコピー
		int[][] banmen_copy;

		//上方向に相手の石を見つけた際
		if(board.getBanmen()[i-1][j] == (turn ^ bit)){
			//上方向へ進んでいく
			for(int a = i - 1; a >= 0; a--){
				//石が置いてない場所を見つけた際
				if(board.getBanmen()[a][j] == 0){
					break;
				//自分の石を見つけた際
				}else if(board.getBanmen()[a][j] == turn){
					//盤面のコピーを作成
					banmen_copy = board.getBanmen();
					//戻りながら自分の石に変えていく
					for(int b = a; b <= i; b++){
						banmen_copy[b][j] = turn;
					}
					//コピーの内容をセットする
					board.setBanmen(banmen_copy);
					break;
				}
			}
		}


	}
	//下方向を裏返す処理
	private void flip_S(int i, int j){

		//盤面のコピー
		int[][] banmen_copy;

		//下方向に相手の石を見つけた際
		if(board.getBanmen()[i+1][j] == (turn ^ bit)){
			//下方向へ進んでいく
			for(int a = i + 1; a < 8; a++){
				//石が置いてない場所を見つけた際
				if(board.getBanmen()[a][j] == 0){
					break;
				//自分の石を見つけた際
				}else if(board.getBanmen()[a][j] == turn){
					//盤面のコピーを作成
					banmen_copy = board.getBanmen();
					//戻りながら自分の石に変えていく
					for(int b = a; b >= i; b--){
						banmen_copy[b][j] = turn;
					}
					//コピーの内容をセットする
					board.setBanmen(banmen_copy);
					break;
				}
			}
		}


}

	//右方向を裏返す処理
	private void flip_E(int i, int j){

		//盤面のコピー
		int[][] banmen_copy;

	//右方向に相手の石を見つけた際
		if(board.getBanmen()[i][j+1] == (turn ^ bit)){
			//右方向へ進んでいく
			for(int a = j + 1; a < 8; a++){
				//石が置いてない場所を見つけた際
				if(board.getBanmen()[i][a] == 0){
					break;
				//自分の石を見つけた際
				}else if(board.getBanmen()[i][a] == turn){
					//盤面のコピーを作成
					banmen_copy = board.getBanmen();
					//戻りながら自分の石に変えていく
					for(int b = a; b >= j ; b--){
					banmen_copy[i][b] = turn;
					}
					//コピーの内容をセット
					board.setBanmen(banmen_copy);
					break;
				}
			}
		}

	}
	//左方向を裏返す処理
	private void flip_W(int i, int j){

		//盤面のコピー
		int[][] banmen_copy;

		//左方向に相手の石を見つけた際
		if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			//左方向へ進んでいく
			for(int a = j - 1; a >= 0; a--){
				//石が置いてない場所を見つけた際
				if(board.getBanmen()[i][a] == 0){
					break;
				//自分の石を見つけた際
				}else if(board.getBanmen()[i][a] == turn){
					//盤面のコピーを作成
					banmen_copy = board.getBanmen();
					//戻りながら自分の石に変えていく
					for(int b = a; b <= j ; b++){
						banmen_copy[i][b] = turn;
					}
					//コピーの内容をセット
					board.setBanmen(banmen_copy);
					break;
				}
			}
		}

	}
	//右上方向を裏返す処理
	private void flip_NE(int i, int j){

		//盤面のコピー
		int[][] banmen_copy;

		//右斜め上方向に相手の石を見つけた際
		if(board.getBanmen()[i-1][j+1] == (turn ^ bit)){
			int a; int b = j + 1;
			//右上方向へ進んでいく
			for(a = i - 1; (a >= 0) && (b < 8); a--, b++){
				//石が置いてない場所を見つけた際
				if(board.getBanmen()[a][b] == 0){
					break;
				//自分の石を見つけた際
				}else if(board.getBanmen()[a][b] == turn){
					//盤面のコピーを作成
					banmen_copy = board.getBanmen();
					//戻りながら自分の石に変えていく
					for(int c = a; c <= i; c++){
						banmen_copy[c][b--] = turn;
					}
					//コピーの内容をセット
					board.setBanmen(banmen_copy);
					break;
				}

			}
		}

	}
	//右下方向を裏返す処理
	private void flip_SE(int i, int j){

		//盤面のコピー
		int[][] banmen_copy;

		//右斜め下方向に相手の石を見つけた際
		if(board.getBanmen()[i+1][j+1] == (turn ^ bit)){
			int a; int b = j + 1;
			//右下方向へ進んでいく
			for(a = i + 1; (a < 8) && (b < 8); a++, b++){
				//石が置いてない場所を見つけた際
				if(board.getBanmen()[a][b] == 0){
					break;
				//自分の石を見つけた際
				}else if(board.getBanmen()[a][b] == turn){
					//盤面のコピーを作成
					banmen_copy = board.getBanmen();
					//戻りながら自分の石に変えていく
					for(int c = a; c >= i; c--){
						banmen_copy[c][b--] = turn;
					}
					//コピーの内容をセット
					board.setBanmen(banmen_copy);
					break;
				}
			}
		}

	}
	//左下方向を裏返す処理
	private void flip_SW(int i, int j){

		//盤面のコピー
		int[][] banmen_copy;

		//左斜め下方向に相手の石を見つけた際
		if(board.getBanmen()[i+1][j-1] == (turn ^ bit)){
			int a; int b = j - 1;
			//左下方向へ進んでいく
			for(a = i + 1; (a < 8) && (b >= 0); a++, b--){
				//石が置いてない場所を見つけた際
				if(board.getBanmen()[a][b] == 0){
					break;
				//自分の石を見つけた際
				}else if(board.getBanmen()[a][b] == turn){
					//盤面のコピーを作成
					banmen_copy = board.getBanmen();
					//戻りながら自分の石に変えていく
					for(int c = a; c >= i; c--){
						banmen_copy[c][b++] = turn;
					}
					//コピーの内容をセット
					board.setBanmen(banmen_copy);
					break;
				}
			}
		}

	}
	//左上方向を裏返す処理
	private void flip_NW(int i, int j){

		//盤面のコピー
		int[][] banmen_copy;

		//左斜め上方向に相手の石を見つけた際
		if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			int a; int b = j - 1;
			//左上方向へ進んでいく
			for(a = i - 1; (a >= 0) && (b >= 0); a--, b--){
				//石が置いてない場所を見つけた際
				if(board.getBanmen()[a][b] == 0){
					break;
				//自分の石を見つけた際
				}else if(board.getBanmen()[a][b] == turn){
					//盤面のコピーを作成
					banmen_copy = board.getBanmen();
					//戻りながら自分の石に変えていく
					for(int c = a; c <= i; c++){
						banmen_copy[c][b++] = turn;
					}
					//コピーの内容をセット
					board.setBanmen(banmen_copy);
					break;
				}
			}
		}

	}


	/**
	 * 挟める石があるか調べるメソッド
	 * @return 挟む石が無ければ0を返す。挟む石があれば1を返す。
	 */
	public int put_check(int i, int j){
				//配列[0][0]の時、右、下、右斜め下にしか検索しない
				if(board.getBanmen()[i][j] == 0 && i == 0 && j == 0){
					//右方向に相手の石を見つけた際
					if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
						//右方向に進んでいく
						for(int a = j + 1; a < 8; a++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}

					}

					//下方向に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
						//下方向に進んでいく
						for(int a = i + 1; a < 8; a++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][j] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}

					}

					//右斜め下方向に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
						int b = j + 1;
						//右下方向に進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
					}

				//配列[0][7]の時、左、下、左斜め下にしか検索しない
				}else if(board.getBanmen()[i][j] == 0 && i == 0 && j == 7){

					//左方向に相手の石を見つけた際
					if(board.getBanmen()[i][j-1] == (turn ^ bit)){
						//左方向に進んでいく
						for(int a = j - 1; a >= 0; a--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}

				    }
					//下方向に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
						//下方向へ進んでいく
				    	for(int a = i + 1; a < 8; a++){
				    		//石が置いてない場所を見つけた際
				    		if(board.getBanmen()[a][j] == 0){
				    			break;
				    		//自分の石を見つけた際
				    		}else if(board.getBanmen()[a][j] == 1){
								return 1;
							}
						}

				    }
					//左斜め下方向に相手の石を見つけた際
					if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
				    	int b = j - 1;
				    	//左下へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
				    }

				//配列[7][0]の時、上、右、右斜め上にしか検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 7 && j == 0){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}

			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}

			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    //配列[7][7]の時、左、上、左斜め上にしか検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 7 && j == 7){

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}
			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}
			    	}
			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    //配列[0][j]の時、上側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 0){

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}
			    	//左斜め下方向への検索
			    	if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}
			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}
			    	}

			    	//右斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    //配列[i][0]の時、左側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && j == 0){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				return 1;
			    			}
			    		}
			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    	//右斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}
			    	}

			    //配列[i][7]の時、右側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && j == 7){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				return 1;
			    			}
			    		}
			    	}

			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    	//左斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}
			    	}

			    //配列[7][j]の時、下側を検索しない
			    }else if(board.getBanmen()[i][j] == 0 && i == 7){

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a > 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				return 1;
			    			}
			    		}
			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    //他の座標は周りをすべて調べる
			    }else if(board.getBanmen()[i][j] == 0){

			    	//上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j] == (turn ^ bit)){
			    		//上方向へ進んでいく
			    		for(int a = i - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
			    				return 1;
			    			}
			    		}
			    	}

			    	//右斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i - 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b < 8); a--, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//右方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j + 1] == (turn ^ bit)){
			    		//右方向へ進んでいく
			    		for(int a = j + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    	//右斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j + 1] == (turn ^ bit)){
			    		int b = j + 1;
			    		//右下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b < 8); a++, b++){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j] == (turn ^ bit)){
			    		//下方向へ進んでいく
			    		for(int a = i + 1; a < 8; a++){
			    			//石が置いてない場所を見つけた際
			    			if(board.getBanmen()[a][j] == 0){
			    				break;
			    			//自分の石を見つけた際
			    			}else if(board.getBanmen()[a][j] == turn){
								return 1;
							}
						}
			    	}

			    	//左斜め下方向に相手の石を見つけた際
			    	if(board.getBanmen()[i + 1][j - 1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左下方向へ進んでいく
						for(int a = i + 1; (a < 8) && (b >= 0); a++, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    	//左方向に相手の石を見つけた際
			    	if(board.getBanmen()[i][j-1] == (turn ^ bit)){
			    		//左方向へ進んでいく
			    		for(int a = j - 1; a >= 0; a--){
			    			//石が置いてない場所を見つけた際
							if(board.getBanmen()[i][a] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[i][a] == turn){
								return 1;
							}
						}
			    	}

			    	//左斜め上方向に相手の石を見つけた際
			    	if(board.getBanmen()[i-1][j-1] == (turn ^ bit)){
			    		int b = j - 1;
			    		//左上方向へ進んでいく
						for(int a = i - 1; (a >= 0) && (b >= 0); a--, b--){
							//石が置いてない場所を見つけた際
							if(board.getBanmen()[a][b] == 0){
								break;
							//自分の石を見つけた際
							}else if(board.getBanmen()[a][b] == turn){
								return 1;
							}
						}
			    	}

			    }
		return 0;
	}


}
