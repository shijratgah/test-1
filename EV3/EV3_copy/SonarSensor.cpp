/**
 * SonarSensor.cpp
 * �����g�Z���T�N���X
 */

#include "ev3api.h"
#include "SonarSensor.h"

/**
 * �R���X�g���N�^
 */
SonarSensor::SonarSensor()
{
}

/*
 * ������
 */
void SonarSensor::init(){
	/* �Z���T�[���̓|�[�g�̐ݒ� */
	ev3_sensor_config(sonar_sensor, ULTRASONIC_SENSOR);
}

/*
* ��Q���Ƃ̋������擾����
*/
int SonarSensor::getDetection() {
	return ev3_ultrasonic_sensor_get_distance(sonar_sensor);
}

