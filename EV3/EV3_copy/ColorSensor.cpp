/**
 * ColorSensor.cpp
 * �J���[�Z���T�N���X
 */

#include "ev3api.h"
#include "ColorSensor.h"

/**
 * �R���X�g���N�^
 */
ColorSensor::ColorSensor()
{
}

/*
 * ������
 */
void ColorSensor::init(){
	/* �Z���T�[���̓|�[�g�̐ݒ� */
	ev3_sensor_config(color_sensor, COLOR_SENSOR);
	ev3_color_sensor_get_reflect(color_sensor); /* ���˗����[�h */
}

/*
* ���ˌ��̋������擾����
*/
int ColorSensor::getReflect() {
	return ev3_color_sensor_get_reflect(color_sensor);
}

