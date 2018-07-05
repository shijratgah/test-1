/**
 * GyroSensor.cpp
 * �W���C���N���X
 */

#include "ev3api.h"
#include "GyroSensor.h"

/**
 * �R���X�g���N�^
 */
GyroSensor::GyroSensor()
{
}

/*
 * ������
 */
void GyroSensor::init(){
	/* �Z���T�[���̓|�[�g�̐ݒ� */
	ev3_sensor_config(gyro_sensor, GYRO_SENSOR);
}

/*
* �p�ʒu���擾����
*/
int GyroSensor::getRate() {
	return ev3_gyro_sensor_get_rate(gyro_sensor);
}

/*
* ���Z�b�g
*/
void GyroSensor::reset() {
	ev3_gyro_sensor_reset(gyro_sensor);
}

