/**
 * RunGarage.cpp
 * 車庫入れ攻略クラス
 */

#include "RunGarage.h"
#include "RunMain.h"
#include "ev3api.h"
#include "Motor.h"

/**
 * コンストラクタ
 * コンストラクタとはインスタンスを作成したタイミングで実行されるメソッドのこと
 * つまり、app.cppの runmain = new RunGarage; ところでクラスが作られこの関数が呼ばれます。 
 */
RunGarage::RunGarage()
{
	//変数の初期化をします。
	//初期化は大切で忘れていると、movedistance+5とした際に
	//　+5=???となりエラーになります。
	//初期化していれば、0+5=5になります。
	movedistance=0; //移動距離
	angle_now=0; //現在のモーター角
	angle_past=0; //ひとつ前のモーター角
	angle_diff=0; //動いたモーター角
}

/**
*処理開始
*/
void RunGarage::run() {
	//尻尾はたたんでない状態です。
	/*if(movedistance < GOAL){
		//停止位置に到達していない
		//バランス、ライントレースは考えないので、モータの回転量を決め打ち
		ev3_motor_set_power(motor.left_motor,30);
		ev3_motor_set_power(motor.right_motor,30);
		
		angle_now = motor.getAngle(motor.left_motor); //現在のモーター角を取得
		angle_diff = angle_now - angle_past; //動いたモーター角を求める
		angle_past = angle_now; //ひとつ前のモーター角に現在のモーター角をセット
		
		movedistance = movedistance + ((10*3.14)*(angle_diff/360));//距離の計算 距離＝さっきまでの距離+(直径×円周率×中心角)
	}else{
		//停止位置に到達
		//停止
		ev3_motor_set_power(motor.left_motor,0);
		ev3_motor_set_power(motor.right_motor,0);
	}*/
	
	if ((int(ev3_motor_get_counts(left_motor))+int(ev3_motor_get_counts(right_motor)))/2 >= 470) {
			ter_tsk(BLN_TASK);
			ev3_motor_set_power(left_motor, forward);
			ev3_motor_set_power(right_motor, forward);
			ev3_motor_rotate(tail_motor, 70, PWM_ABS_MAX, true);
			for(int i = 1; i >= 10; i++){
				fputs("for\r\n", bt);
				ev3_motor_rotate(tail_motor, i, PWM_ABS_MAX, true);
				tslp_tsk(2000);
			}
			break;
		}
		
		if (bt_cmd == 0) break;	
		
		tail_control(TAIL_ANGLE_DRIVE); /* バランス走行用角度に制御 */

		turn = 0;
	}
	
	ev3_motor_stop(left_motor, true);
	ev3_motor_stop(right_motor, true);
	
	ter_tsk(BT_TASK);
	ter_tsk(BLN_TASK);
	fclose(bt);

	ext_tsk();
}

