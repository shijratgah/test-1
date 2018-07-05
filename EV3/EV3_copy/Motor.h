//ヘッダファイルは宣言をするファイル
//目次のようなものです。

/**
 *Motor.h
 */
#ifndef MOTOR_H_
#define MOTOR_H_

#include "ev3api.h"
	
class Motor
{
public:
	//パブリック
	//公に使える変数（パブリック変数）、関数（パブリック関数）を書きます。
	//つまりRunGarage.cppで、motor.getAngle(motor.left_motor)みたいに使えます。
	Motor();
	void init();
	void reset(motor_port_t);
	void stop();
	int32_t getAngle(motor_port_t);
	void setMovedistance();
	int getMovedistance();

	static const motor_port_t
		left_motor = EV3_PORT_C,
		right_motor = EV3_PORT_B,
		tail_motor = EV3_PORT_A;

private:
	//プライベート
	//自分(Motorクラス内)でしか使わない変数（プライベート変数）、関数（プライベート関数）を書きます。
	//つまりRunGarage.cppで、motor.movedistanceとは書けないです。
	int movedistance; //移動距離

};

#endif
