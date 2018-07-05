//�w�b�_�t�@�C���͐錾������t�@�C��
//�ڎ��̂悤�Ȃ��̂ł��B

/**
 *Motor.h
 */
#ifndef MOTOR_H_
#define MOTOR_H_

#include "ev3api.h"
	
class Motor
{
public:
	//�p�u���b�N
	//���Ɏg����ϐ��i�p�u���b�N�ϐ��j�A�֐��i�p�u���b�N�֐��j�������܂��B
	//�܂�RunGarage.cpp�ŁAmotor.getAngle(motor.left_motor)�݂����Ɏg���܂��B
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
	//�v���C�x�[�g
	//����(Motor�N���X��)�ł����g��Ȃ��ϐ��i�v���C�x�[�g�ϐ��j�A�֐��i�v���C�x�[�g�֐��j�������܂��B
	//�܂�RunGarage.cpp�ŁAmotor.movedistance�Ƃ͏����Ȃ��ł��B
	int movedistance; //�ړ�����

};

#endif
