/**
 * RunGate.cpp
 * ルックアップゲート攻略クラス
 */

#include "RunGate.h"
#include "RunMain.h"
#include "ev3api.h"

#include "SonarSensor.h"
SonarSensor sonarsensor;

#include "Motor.h"
extern Motor motor;

/**
 * コンストラクタ
 */
RunGate::RunGate()
{
}

/**
*処理開始
*/
void RunGate::run() {
	//fputs("gate\r\n",bt);
	move(10);
	// 20cmで障害物を検知
	/*
	if(sonarsensor.getDetection() <= 20){

		fputs("sonor\r\n",bt);
	}
	*/
	//50cmの時のモータの回転数を取得
	motor.setMovedistance(50);
	if((motor.getAngle(motor.left_motor)+motor.getAngle(motor.right_motor)) / 2 >= motor.getMovedistance()){
		fprintf(bt,"getdis:%d\r\n",motor.getMovedistance());
		motor.stop();
	}
}

