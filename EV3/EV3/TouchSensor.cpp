/**
 * TouchSensor.cpp
 * �^�b�`�Z���T�N���X
 */

#include "ev3api.h"
#include "TouchSensor.h"

/**
 * �R���X�g���N�^
 */
TouchSensor::TouchSensor()
{
}

/*
 * ������
 */
void TouchSensor::init(){
	/* �Z���T�[���̓|�[�g�̐ݒ� */
	ev3_sensor_config(touch_sensor, TOUCH_SENSOR);
}

