/**
 * RunSeesaw.cpp
 * �V�[�\�U���N���X
 */

#include "RunSeesaw.h"
#include "RunMain.h"
#include "ev3api.h"

/**
 * �R���X�g���N�^
 */
RunSeesaw::RunSeesaw()
{
}

/**
*�����J�n
*/
void RunSeesaw::run() {
	move(50);
	fprintf(bt, "%s\r\n", "seesaw");
}

