/**
 *RunMain.h
 */
#ifndef RUNMAIN_H_
#define RUNMAIN_H_

#include "ev3api.h"
	
class RunMain
{
public:
	RunMain();
	void init();
	void run();
	int getTurn();
	void move(int);
	void stop();

	int runmode = 0;

	typedef enum {
		NORMAL_RUNMODE = 0, //通常走行（ライントレース）
		SEESAW_RUNMODE = 1, //シーソー
		GATE_RUNMODE = 2,   //ルックアップゲート
		GARAGE_RUNMODE = 3, //車庫入れ
	} run_mode_t;
	
	int forward = 0;      /* 前後進命令 */
	int turn = 0;         /* 旋回命令 */
	int pwm_L, pwm_R; /* 左右モータPWM出力 */
	int gyro, volt;
	int32_t motor_ang_l, motor_ang_r;
	float target;

private:

};

#endif
