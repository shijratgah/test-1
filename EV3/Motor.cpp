/**
 * Motor.cpp
 * モータクラス
 */

#include "ev3api.h"
#include "Motor.h"

/**
 * コンストラクタ
 */
Motor::Motor()
{
}

/*
 * 初期化
 */
void Motor::init(){
	/* モーター出力ポートの設定 */
	ev3_motor_config(left_motor, LARGE_MOTOR);
	ev3_motor_config(right_motor, LARGE_MOTOR);
	ev3_motor_config(tail_motor, LARGE_MOTOR);
	ev3_motor_reset_counts(tail_motor);
}

/*
* モータリセット
*/
void Motor::reset(motor_port_t motor) {
	ev3_motor_reset_counts(motor);
}

/*
* モータを停止する
*/
void Motor::stop() {
	ev3_motor_stop(left_motor, false);
	ev3_motor_stop(right_motor, false);
}

/*
* モータの角位置を取得する
*/
int32_t Motor::getAngle(motor_port_t motor) {
	return ev3_motor_get_counts(motor);
}

/*
* 移動距離
*/
void Motor::setMovedistance() {
	//移動距離の計算
}
int Motor::getMovedistance() {
	return movedistance;
}
