/**
 * RunGarage.cpp
 * �Ԍɓ���U���N���X
 */

#include "RunGarage.h"
#include "RunMain.h"
#include "ev3api.h"

/**
 * �R���X�g���N�^
 */
RunGarage::RunGarage()
{
}

/**
*�����J�n
*/
void RunGarage::run() {
	move(50);
	
	while(1){
	switch(sts){
	
		case1: //������
		tail_control(TAIL_ANGLE_DRIVE); /* �o�����X���s�p�p�x�ɐ��� */
		sts = 2;
		break;
		
		case2: //��~�ʒu�܂ő���
		if ((int(ev3_motor_get_counts(left_motor))+int(ev3_motor_get_counts(right_motor)))/2 >= 470) {
		ter_tsk(BLN_TASK);
			ev3_motor_set_power(left_motor, forward);
			ev3_motor_set_power(right_motor, forward);
			ev3_motor_rotate(tail_motor, 70, PWM_ABS_MAX, true);
			for(int i = 1; i >= 10; i++){
				fputs("for\r\n", bt);
				ev3_motor_rotate(tail_motor, i, PWM_ABS_MAX, true);
				tslp_tsk(2000);
			}
			break;
		}
		
		ev3_motor_stop(left_motor, true);
		ev3_motor_stop(right_motor, true);
		
		break;
		
		case3:
		ter_tsk(BT_TASK);
		ter_tsk(BLN_TASK);
		fclose(bt);

		ext_tsk();
		break;
	}
}

