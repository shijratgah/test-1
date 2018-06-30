/**
 * RunGate.cpp
 * ルックアップゲート攻略クラス
 */

#include "RunGate.h"
#include "RunMain.h"
#include "ev3api.h"

/**
 * コンストラクタ
 */
RunGate::RunGate()
{
}

/**
*処理開始
*/
void RunGate::run() {
	move(50);
}

