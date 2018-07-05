/**
 * RunGate.cpp
 * ���b�N�A�b�v�Q�[�g�U���N���X
 */

#include "RunGate.h"
#include "RunMain.h"
#include "ev3api.h"

#include "SonarSensor.h"
SonarSensor sonarsensor;

#include "Motor.h"
extern Motor motor;

/**
 * �R���X�g���N�^
 */
RunGate::RunGate()
{
	flg = 0;
}

/**
*�����J�n
*/
void RunGate::run() {
	move(10);
	// 20cm�ŏ�Q�������m
	/*
	//��Q���Ƃ̋������擾
	if(sonarsensor.getDetection() <= 20){

		fputs("sonor\r\n",bt);
	}
	*/
	//while(1){
		/*
		//50cm�̎��̃��[�^�̉�]�����擾
		motor.setMovedistance(50);
		if(getAveAngle() >= motor.getMovedistance()){
			motor.stop();//50cm�i�񂾂�~�܂�
		}
		*/
		if(ObstacleDetection(20)){
			move(60);
			fputs("1",bt);
		}else{
			move(-60);
			fputs("0",bt);
		}
	//}
}

/**
*�����̃^�C�����[�^�̊p�ʒu�̕���
*/
int RunGate::getAveAngle(){
	return (motor.getAngle(motor.left_motor) + motor.getAngle(motor.right_motor)) / 2;
}

/**
*��Q�����m����
*/
int RunGate::ObstacleDetection(int _distance){
	if(sonarsensor.getDetection() <= _distance){
		return 1;
	}
	return 0;
}
