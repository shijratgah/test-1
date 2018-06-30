/**
 ******************************************************************************
 ** ファイル名 : app.c
 **
 ** 概要 : 2輪倒立振子ライントレースロボットのTOPPERS/HRP2用Cサンプルプログラム
 **
 ** 注記 : sample_c4 (sample_c3にBluetooth通信リモートスタート機能を追加)
 ******************************************************************************
 **/

#include "ev3api.h"
#include "app.h"
#include "balancer.h"

#if defined(BUILD_MODULE)
#include "module_cfg.h"
#else
#include "kernel_cfg.h"
#endif

#define DEBUG

#ifdef DEBUG
#define _debug(x) (x)
#else
#define _debug(x)
#endif

static int      bt_cmd = 0;     /* Bluetoothコマンド 1:リモートスタート */
static FILE     *bt = NULL;     /* Bluetoothファイルハンドル */

/* 下記のマクロは個体/環境に合わせて変更する必要があります */
/* sample_c2マクロ */
#define SONAR_ALERT_DISTANCE 30 /* 超音波センサによる障害物検知距離[cm] */
/* sample_c3マクロ */
#define TAIL_ANGLE_STAND_UP  87 /* 完全停止時の角度[度] */
#define TAIL_ANGLE_DRIVE      3 /* バランス走行時の角度[度] */
#define P_GAIN             2.5F /* 完全停止用モータ制御比例係数 */
#define PWM_ABS_MAX          60 /* 完全停止用モータ制御PWM絶対最大値 */
/* sample_c4マクロ */
//#define DEVICE_NAME     "ET0"  /* Bluetooth名 SD:\ev3rt\etc\rc.conf.ini で設定 */
//#define PASS_KEY        "1234" /* パスキー    SD:\ev3rt\etc\rc.conf.ini で設定 */
#define CMD_START         '1'    /* リモートスタートコマンド */

/* LCDフォントサイズ */
#define CALIB_FONT (EV3_FONT_SMALL)
#define CALIB_FONT_WIDTH (6/*TODO: magic number*/)
#define CALIB_FONT_HEIGHT (8/*TODO: magic number*/)

/* 関数プロトタイプ宣言 */
static int sonar_alert(void);
static void tail_control(signed int angle);

#include "RunMain.h"
#include "RunNormal.h"
#include "RunGarage.h"
#include "RunGate.h"
#include "RunSeesaw.h"
RunMain* runmain;
#include "Motor.h"
Motor* motor;
#include "GyroSensor.h"
GyroSensor* gyrosensor;
#include "ColorSensor.h"
ColorSensor* colorsensor;
#include "TouchSensor.h"
TouchSensor* touchsensor;
#include "SonarSensor.h"
SonarSensor* sonarsensor;


/* メインタスク */
void main_task(intptr_t unused)
{
  signed char forward;      /* 前後進命令 */
  signed char turn;         /* 旋回命令 */

  /* LCD画面表示 */
  ev3_lcd_fill_rect(0, 0, EV3_LCD_WIDTH, EV3_LCD_HEIGHT, EV3_LCD_WHITE);
  ev3_lcd_draw_string("EV3way-ET sample_cpp4", 0, CALIB_FONT_HEIGHT*1);

  /* 各センサー類の初期化 */
  motor->init();
  gyrosensor->init();
  colorsensor->init();
  touchsensor->init();
  sonarsensor->init();

  /* Open Bluetooth file */
  bt = ev3_serial_open_file(EV3_SERIAL_BT);
  assert(bt != NULL);

  /* Bluetooth通信タスクの起動 */
  act_tsk(BT_TASK);

  ev3_led_set_color(LED_ORANGE); /* 初期化完了通知 */
  ev3_motor_rotate(motor->tail_motor, TAIL_ANGLE_STAND_UP, PWM_ABS_MAX, true);
  //ev3_lcd_draw_string("尻尾処理前", 0, CALIB_FONT_HEIGHT*1);
  /* スタート待機 */
  while(1)
    {
      ev3_lcd_draw_string("          ", 0, CALIB_FONT_HEIGHT*1);
      ev3_lcd_draw_string("ready", 0, CALIB_FONT_HEIGHT*1);
      //tail_control(TAIL_ANGLE_STAND_UP); /* 完全停止用角度に制御 */

      if (bt_cmd == 1)
        {
          break; /* リモートスタート */
        }

      tslp_tsk(10); /* 10msecウェイト */
    }

  /* 走行モーターエンコーダーリセット */
  motor->reset(motor->left_motor);
  motor->reset(motor->right_motor);

  /* ジャイロセンサーリセット */
  gyrosensor->reset();
  runmain->init();

  ev3_led_set_color(LED_GREEN); /* スタート通知 */

  /**
   * Main loop for the self-balance control algorithm
   */
  while(1)
    {

      if (ev3_button_is_pressed(BACK_BUTTON)) break;

      tail_control(TAIL_ANGLE_DRIVE); /* バランス走行用角度に制御 */

	  //走行処理
	  switch (runmain->runmode) {
	  case runmain->NORMAL_RUNMODE:
		  runmain = new RunNormal;
			  break;
	  case runmain->SEESAW_RUNMODE:
		  runmain = new RunSeesaw;
			  break;
	  case runmain->GATE_RUNMODE:
		  runmain = new RunGate;
			  break;
	  case runmain->GARAGE_RUNMODE:
		  runmain = new RunGarage;
			  break;
	  default:
		  runmain = new RunMain;
			  break;
	  }
	  runmain->run();

      tslp_tsk(4); /* 4msec周期起動 */
    }
  runmain->stop()

  ter_tsk(BT_TASK);
  fclose(bt);

  ext_tsk();
}

//*****************************************************************************
// 関数名 : sonar_alert
// 引数 : 無し
// 返り値 : 1(障害物あり)/0(障害物無し)
// 概要 : 超音波センサによる障害物検知
//*****************************************************************************
static int sonar_alert(void)
{
  static unsigned int counter = 0;
  static int alert = 0;

  signed int distance;

  if (++counter == 40/4) /* 約40msec周期毎に障害物検知  */
    {
      /*
       * 超音波センサによる距離測定周期は、超音波の減衰特性に依存します。
       * NXTの場合は、40msec周期程度が経験上の最短測定周期です。
       * EV3の場合は、要確認
       */
      distance = sonarsensor->getDetection();
      if ((distance <= SONAR_ALERT_DISTANCE) && (distance >= 0))
        {
          alert = 1; /* 障害物を検知 */
        }
      else
        {
          alert = 0; /* 障害物無し */
        }
      counter = 0;
    }

  return alert;
}

//*****************************************************************************
// 関数名 : tail_control
// 引数 : angle (モータ目標角度[度])
// 返り値 : 無し
// 概要 : 走行体完全停止用モータの角度制御
//*****************************************************************************
static void tail_control(signed int angle)
{
  float pwm = (float)(angle - motor->getAngle(motor->tail_motor))*P_GAIN; /* 比例制御 */
  /* PWM出力飽和処理 */
  if (pwm > PWM_ABS_MAX)
    {
      pwm = PWM_ABS_MAX;
    }
  else if (pwm < -PWM_ABS_MAX)
    {
      pwm = -PWM_ABS_MAX;
    }

  if (pwm == 0)
    {
      ev3_motor_stop(motor->tail_motor, true);
    }
  else
    {
      ev3_motor_set_power(motor->tail_motor, (signed char)pwm);
    }
}

//*****************************************************************************
// 関数名 : bt_task
// 引数 : unused
// 返り値 : なし
// 概要 : Bluetooth通信によるリモートスタート。 Tera Termなどのターミナルソフトから、
//       ASCIIコードで1を送信すると、リモートスタートする。
//*****************************************************************************
void bt_task(intptr_t unused)
{
  while(1)
    {
      uint8_t c = fgetc(bt); /* 受信 */
      switch(c)
        {
        case '1':
          bt_cmd = 1;
          break;
        default:
          break;
        }
      fputc(c, bt); /* エコーバック */
    }
}
