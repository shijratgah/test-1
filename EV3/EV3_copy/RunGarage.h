/**
 *RunGarage.h
 */
#ifndef RUNGARAGE_H_
#define RUNGARAGE_H_

#include "ev3api.h"
#include "RunMain.h"
#include "Motor.h"
//include
//���̂悤�ɋL�ڂ��邱�Ƃ�Motor.h���t�@�C���̒��g��ǂݍ��݂܂��B

#define GOAL  50 //��~�ʒu
//define
//�������萔�⎮�Œu�������܂��B
//GOAL�Ƃ��������񂪏o�Ă�����50�ɒu���������Ă��Ƃł��B
	
class RunGarage : public RunMain
{
public:
	RunGarage();
	void run();

private:
	Motor motor;
	//�\�[�X���ɂ�Motor *motor;�̂悤�ɏ����Ă���ꏊ������Ǝv���܂��B
	//�@ Motor motor;�@Motor�N���X���̂��̂��i�[����ϐ��ł��B�i�Ⴆ��Ȃ炱�������Ɓj
	//�A Motor *motor;�@Motor�N���X�̃A�h���X���i�[����ϐ��ł��B�i�Ⴆ��Ȃ炱�������Z���j
	//�@,�A�ǂ���̏����������Ă��A�@�Ȃ� motor.init();�A�Ȃ� motor->init();�̂悤�Ȋ����N���X�̒��g���g���܂��B
	//�ǂ�����g�������������̂��́A�������̈�Ƃ�����b�ɂȂ�̂ł��������ő��v���Ǝv���܂��B
	
	int movedistance; //�ړ�����
	int angle_now; //���݂̃��[�^�[�p
	int angle_past; //�ЂƂO�̃��[�^�[�p
	int angle_diff; //���������[�^�[�p
};

#endif
