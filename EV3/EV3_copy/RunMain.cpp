/**
 * RunMain.cpp
 * ���s�N���X
 */

#include "RunMain.h"
#include "ev3api.h"
#include "BalancerCpp.h"
Balancer balancer;
#include "PID.h"
PID *pid;
#include "Motor.h"
Motor motor;
#include "GyroSensor.h"
GyroSensor gyrosensor;
#include "ColorSensor.h"
ColorSensor colorsensor;

/**
 * �R���X�g���N�^
 */
RunMain::RunMain()
{
	target = (LIGHT_WHITE + LIGHT_BLACK)/2;
	pid = new PID();
}

/**
 *������
 */
void RunMain::init(){
	balancer.init(GYRO_OFFSET);
}

/**
*�����J�n
*/
void RunMain::run() {
	move(30);
}

/*
 *��]�ʂ��擾����
 */
int RunMain::getTurn(){
	int diff = target - colorsensor.getReflect();
    return pid->calcControllValue(diff);
}

/*
 *�O�i
 */
void RunMain::move(int _forward){
	 //�Z�b�g����l�̎擾
	 motor_ang_l = motor.getAngle(motor.left_motor);
     motor_ang_r = motor.getAngle(motor.right_motor);
     gyro = gyrosensor.getRate();
     volt = ev3_battery_voltage_mV();
	 turn = getTurn();
	 forward = _forward;
	
	 //�o�����T�[�ɒl�̃Z�b�g�B��]�ʂ̎擾
	 balancer.setCommand(forward, turn);
     balancer.update(gyro, motor_ang_r, motor_ang_l, volt);
	 pwm_L = balancer.getPwmRight();
     pwm_R = balancer.getPwmLeft();
	
	 //���E�̃��[�^�ɃZ�b�g
	 /* EV3�ł̓��[�^�[��~���̃u���[�L�ݒ肪���O�ɂł��Ȃ����� */
     /* �o��0���ɁA���̓s�x�ݒ肷�� */
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
 *��~
 */
void RunMain::stop(){
	forward = 0;
	turn = 0;
	motor.stop();
}

