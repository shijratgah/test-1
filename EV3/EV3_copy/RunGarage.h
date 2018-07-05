/**
 *RunGarage.h
 */
#ifndef RUNGARAGE_H_
#define RUNGARAGE_H_

#include "ev3api.h"
#include "RunMain.h"
#include "Motor.h"
//include
//このように記載することでMotor.hをファイルの中身を読み込みます。

#define GOAL  50 //停止位置
//define
//文字列を定数や式で置き換えます。
//GOALという文字列が出てきたら50に置換するよってことです。
	
class RunGarage : public RunMain
{
public:
	RunGarage();
	void run();

private:
	Motor motor;
	//ソース内にはMotor *motor;のように書いている場所もあると思います。
	//① Motor motor;　Motorクラスそのものを格納する変数です。（例えるならこっちが家）
	//② Motor *motor;　Motorクラスのアドレスを格納する変数です。（例えるならこっちが住所）
	//①,②どちらの書き方をしても、①なら motor.init();②なら motor->init();のような感じクラスの中身を使えます。
	//どちらを使う方が正しいのかは、メモリ領域とか難しい話になるのでおいおいで大丈夫だと思います。
	
	int movedistance; //移動距離
	int angle_now; //現在のモーター角
	int angle_past; //ひとつ前のモーター角
	int angle_diff; //動いたモーター角
};

#endif
