/**
 * PID.cpp
 * PID������s���N���X
 */

#include "PID.h"

/**
 * �R���X�g���N�^
 */
PID::PID()
{
}

int PID::calcControllValue(int diff) {
	float d_control = 0;
	float p_control = 0;
	float i_control = 0;
	int pid_total = 0;
	int total_i = 0;

	//P����v�Z
	p_control = diff * KP;

	//D����v�Z
	m_d_pre = m_d_now;
	m_d_now = diff;
	d_control = (m_d_pre - m_d_now) * KD;

	//I����v�Z
	if(m_num>=13) {
		m_num = 0;
	}
	m_i_list[m_num] = diff;
	for(int i=0; i<13; i++) {
		total_i += m_i_list[i];
	}
	m_num ++;
	i_control = total_i * KI;

	//PID�Z�o�l
	pid_total = static_cast<int>(p_control + i_control + d_control);
	pid_total = math_limit(pid_total,-100,100);
	return pid_total;
}

int PID::math_limit(int pid,int min,int max){
	int result;
	result = pid;
	if (max < pid) {
		result = max;
	} else if (pid < min) {
		result = min;
	}
	return result;
}

