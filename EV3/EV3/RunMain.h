/**
 *RunMain.h
 */
#ifndef RUNMAIN_H_
#define RUNMAIN_H_

#include "ev3api.h"

//#define GYRO_OFFSET  0       /* �W���C���Z���T�I�t�Z�b�g�l(�p���x0[deg/sec]��) */
#define LIGHT_WHITE  55         /* ���F�̌��Z���T�l */
#define LIGHT_BLACK  0          /* ���F�̌��Z���T�l */

extern FILE *bt;
	
class RunMain
{
public:
	RunMain();
	void init();
	virtual void run();
	int getTurn();
	int getForward();
	void move(int);
	void stop();
	
	int forward = 0;      /* �O��i���� */
	int turn = 0;         /* ���񖽗� */
	int pwm_L, pwm_R; /* ���E���[�^PWM�o�� */
	int gyro, volt;
	int32_t motor_ang_l, motor_ang_r;
	float target;

private:

};

#endif
