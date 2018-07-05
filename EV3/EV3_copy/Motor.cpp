//�\�[�X�t�@�C���̓N���X�̒��g�������t�@�C���ł��B

/**
 * Motor.cpp
 * ���[�^�N���X
 */

#include "ev3api.h"
#include "Motor.h"

/**
 * �R���X�g���N�^
 */
Motor::Motor()
{
}

/*
 * ������
 */
void Motor::init(){
	/* ���[�^�[�o�̓|�[�g�̐ݒ� */
	ev3_motor_config(left_motor, LARGE_MOTOR);
	ev3_motor_config(right_motor, LARGE_MOTOR);
	ev3_motor_config(tail_motor, LARGE_MOTOR);
	ev3_motor_reset_counts(tail_motor);
}

/*
* ���[�^���Z�b�g
*/
void Motor::reset(motor_port_t motor) {
	ev3_motor_reset_counts(motor);
}

/*
* ���[�^���~����
*/
void Motor::stop() {
	ev3_motor_stop(left_motor, false);
	ev3_motor_stop(right_motor, false);
}

/*
* ���[�^�̊p�ʒu���擾����
*/
int32_t Motor::getAngle(motor_port_t motor) {
	return ev3_motor_get_counts(motor);
}

/*
* �ړ�����
*/
void Motor::setMovedistance() {
	//�ړ������̌v�Z
	movedistance = int((360 / (9.8 * 3.141592)) * 50);//50�͌��ߑł� ���� movedistance = 585 �R���p�C������
}
int Motor::getMovedistance() {
	return movedistance;
}
