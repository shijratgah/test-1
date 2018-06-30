/**
 * PID.h
 */
#ifndef PID_H_
#define PID_H_
	
class PID
{
public:
	static int calcControllValue(int);

private:
	PID();
	
	static int math_limit(int,int,int);
	static int diff;
	static int m_d_now;
	static int m_d_pre;
	static int m_i_list[13];
	static int m_num;
};

#endif
