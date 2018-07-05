/**
 * RunMain.cpp
 * ���s�N���X
 */

#include "RunMain.h"
#include "ev3api.h"
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
 *�������擾����
 */
int RunMain::getForward(){
	return forward;
}

/*
 *�O�i
 */
void RunMain::move(int _forward){
	forward = _forward;
}

/*
 *��~
 */
void RunMain::stop(){
	forward = 0;
	turn = 0;
	motor.stop();
}

