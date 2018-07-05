/**
 * Motor.cpp
 * ���[�^�N���X
 */
#include "ev3api.h"
#include "Motor.h"


#define TAIL_ANGLE_DRIVE      3 /* �o�����X���s���̊p�x[�x] */
#define P_GAIN             2.5F /* ���S��~�p���[�^������W�� */
#define PWM_ABS_MAX          60 /* ���S��~�p���[�^����PWM��΍ő�l */

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

//*****************************************************************************
// �֐��� : tail_control
// ���� : angle (���[�^�ڕW�p�x[�x])
// �Ԃ�l : ����
// �T�v : ���s�̊��S��~�p���[�^�̊p�x����
//*****************************************************************************
void Motor::tail_control(){
	float pwm = (float)(TAIL_ANGLE_DRIVE - getAngle(tail_motor))*P_GAIN; /* ��ᐧ�� */
	/* PWM�o�͖O�a���� */
	if (pwm > PWM_ABS_MAX){
		pwm = PWM_ABS_MAX;
	}
	else if (pwm < -PWM_ABS_MAX){
		pwm = -PWM_ABS_MAX;
	}

	if (pwm == 0){
		ev3_motor_stop(tail_motor, true);
	}
	else{
		ev3_motor_set_power(tail_motor, (signed char)pwm);
	}
}

/*
* �K�����グ��
*/
void Motor::tail_up(int up) {
	ev3_motor_rotate(tail_motor, up, PWM_ABS_MAX, true);
}

/*
* �K����������
*/
void Motor::tail_down(int down) {
	ev3_motor_rotate(tail_motor, down, PWM_ABS_MAX, true);
}

/*
* �K�����Œ�
*/
void Motor::tail_save() {
	ev3_motor_rotate(tail_motor, 0, PWM_ABS_MAX, true);
}

/*
* �ړ�����
*/
void Motor::setMovedistance(int _movedistance) {
	//�ړ������̌v�Z
	movedistance = int((360 / (9.8 * 3.141592)) * _movedistance);
}

int Motor::getMovedistance() {
	return movedistance;
}
