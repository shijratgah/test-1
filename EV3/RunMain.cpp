/**
 * RunMain.cpp
 * 走行クラス
 */

#include "RunMain.h"
#include "ev3api.h"
#include "BalancerCpp.h"
Balancer balancer;
#include "PID.h"
PID pid;
#include "Motor.h"
Motor motor;
#include "GyroSensor.h"
GyroSensor gyrosensor;
#include "ColorSensor.h"
ColorSensor colorsensor;

#define GYRO_OFFSET  0       /* ジャイロセンサオフセット値(角速度0[deg/sec]時) */
#define LIGHT_WHITE  55         /* 白色の光センサ値 */
#define LIGHT_BLACK  0          /* 黒色の光センサ値 */

/**
 * コンストラクタ
 */
RunMain::RunMain()
	: forward(0),
	turn(0),
	runmode(0)
{
	target = (LIGHT_WHITE + LIGHT_BLACK)/2;
}

/**
 *初期化
 */
void RunMain::init(){
	balancer.init(GYRO_OFFSET);
}

/**
*処理開始
*/
void RunMain::run() {
}

/*
 *回転量を取得する
 */
int RunMain::getTurn(){
	int diff = target - colorsensor.getReflect();
    return pid.calcControllValue(diff);
}

/*
 *前進
 */
void RunMain::move(int _forward){
	 //セットする値の取得
	 motor_ang_l = motor.getAngle(motor.left_motor);
     motor_ang_r = motor.getAngle(motor.right_motor);
     gyro = gyrosensor.getRate();
     volt = ev3_battery_voltage_mV();
	 turn = getTurn();
	 forward = _forward;
	
	 //バランサーに値のセット。回転量の取得
	 balancer.setCommand(forward, turn);
     balancer.update(gyro, motor_ang_r, motor_ang_l, volt);
	 pwm_L = balancer.getPwmRight();
     pwm_R = balancer.getPwmLeft();
	
	 //左右のモータにセット
	 /* EV3ではモーター停止時のブレーキ設定が事前にできないため */
     /* 出力0時に、その都度設定する */
     if (pwm_L == 0)
       {
         ev3_motor_stop(motor.left_motor, true);
       }
     else
       {
         ev3_motor_set_power(motor.left_motor, (int)pwm_L);
       }

     if (pwm_R == 0)
       {
         ev3_motor_stop(motor.right_motor, true);
       }
     else
       {
         ev3_motor_set_power(motor.right_motor, (int)pwm_R);
       }
}

/*
 *停止
 */
void RunMain::stop(){
	forward = 0;
	turn = 0;
	motor.stop();
}

