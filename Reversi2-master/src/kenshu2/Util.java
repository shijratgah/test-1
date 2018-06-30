package kenshu2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Util {

	/**
	 * 石を置く場所を入力するメソッド
	 * @return 入力された値
	 */
	public static int input(){
		int x;
		System.out.print("石を置く場所を指定してください");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        try{
                String buf = br.readLine();
                x = Integer.parseInt(buf);
        }catch(Exception e){
                x = 0;
        }
        return x;
	}


	/**
	 * オセロを続けるか、やめるか選ぶメソッド
	 * @return コンソールから入力された値
	 */
	public static int retry(){
		int x;
		System.out.print("ゲームを続けますか？はい=1、いいえ=0");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        try{
                String buf = br.readLine();
                x = Integer.parseInt(buf);
        }catch(Exception e){
                x = 0;
        }
        return x;
	}

}
