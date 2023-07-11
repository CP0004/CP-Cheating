#include "main.h"
#include <jni.h>
#include "CanvasEngine.h"
#include "HacksDrawing.h"

int oldKill = 0;
int newKill = 0;
bool isOpen = false;
extern "C"
JNIEXPORT void JNICALL
Java_com_cp_jo_draw_Overlay_DrawOn(JNIEnv *env, jclass clazz,
                                   jobject r_canvas_draw,
                                   jobject canvas) {
    CanvasEngine canvasEngine = CanvasEngine(env, r_canvas_draw, canvas);
    if (canvasEngine.isValid()) {
        if (!isFixTouch) {
            system("su -c settings put global block_untrusted_touches 0");
            isFixTouch = true;
        }
        MainDraw(canvasEngine);
    }
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_cp_jo_tool_Tools_rights(JNIEnv *env, jclass thiz, jint index) {
    switch ((int) index) {
        case static_cast<int>(DataControl::PATH):
            return env->NewStringUTF(path);
        case static_cast<int>(DataControl::EMAIL):
            return env->NewStringUTF(email);
        case static_cast<int>(DataControl::PASSWORD):
            return env->NewStringUTF(password);
        case static_cast<int>(DataControl::_NULL):
            return env->NewStringUTF(_Null);
    }
    return env->NewStringUTF(_Null);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cp_jo_tool_Tools_linkValue(JNIEnv *env, jclass clazz, jint index, jfloat value,
                                    jboolean i_check) {
    switch ((int) index) {
        //-- Drawing For Player
        case static_cast<int>(DataControl::LINE):
            isLine = i_check;
            break;
        case static_cast<int>(DataControl::SKELETON):
            isSkeleton = i_check;
            break;
        case static_cast<int>(DataControl::INFO_PLAYER):
            isInfoPlayer = i_check;
            break;
        case static_cast<int>(DataControl::WEAPON):
            isWeapon = i_check;
            break;
        case static_cast<int>(DataControl::MARK):
            isMark = i_check;
            break;
        case static_cast<int>(DataControl::HEALTH):
            isHealth = i_check;
            break;
        case static_cast<int>(DataControl::GRENADE):
            isGrenade = i_check;
            break;
        case static_cast<int>(DataControl::BOX):
            isBox = i_check;
            break;
        case static_cast<int>(DataControl::DIST):
            isDist = i_check;
            break;
            //-- Memory
        case static_cast<int>(DataControl::LESS):
            isLess = i_check;
            break;
        case static_cast<int>(DataControl::CROSS):
            isCrossHire = i_check;
            break;
        case static_cast<int>(DataControl::AIMBOT_SDK):
            isAimBot = i_check;
            break;
        case static_cast<int>(DataControl::IS_AIMBOT):
            isBullet = i_check;
            break;
        case static_cast<int>(DataControl::IS_BOT_AIMBOT):
            isBot = i_check;
            break;
        case static_cast<int>(DataControl::IS_KNOCK_AIMBOT):
            isKnock = i_check;
            break;
        case static_cast<int>(DataControl::IPAD_VIEW):
            rangeIPadView = value + 90;
            break;
        case static_cast<int>(DataControl::DIST_AIMBOT):
            distAimbot = value;
            break;
        case static_cast<int>(DataControl::FOV_AIMBOT):
            fovAimbot = value;
            break;
        case static_cast<int>(DataControl::TARGET_AIMBOT):
            targetMode = value;
            break;
            //-- Controls
        case static_cast<int>(DataControl::WIDTH_LINE):
            widthLine = value;
            break;
        case static_cast<int>(DataControl::RADIUS360):
            radiusAfter360 = value;
            break;
        case static_cast<int>(DataControl::SIZE_ITEM):
            sizeItem = value;
            break;
        case static_cast<int>(DataControl::SIZE_VEHICLE):
            sizeVehicle = value;
            break;
        case static_cast<int>(DataControl::SIZE_INFO):
            sizeInfo = value;
            break;
        case static_cast<int>(DataControl::UP_INFO):
            upInfo = value;
            break;
            //-- Other
        case static_cast<int>(DataControl::HIDE_ITEMS):
            isHideItem = i_check;
            break;
        default:
            break;
    }
}

extern "C" JNIEXPORT jboolean JNICALL
Java_com_cp_jo_draw_Overlay_getReady(JNIEnv *, jobject thiz) {
    if (!Create()) {
        LOGE("[NDK] Socket can't create");
    }
    if (!Bind()) {
        LOGE("[NDK] Socket can't bind");
    }
    if (!Listen()) {
        LOGE("[NDK] Socket can't listen");
    }
    if (Accept()) {
        LOGE("[NDK] Socket accepted");
    }

    return false;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cp_jo_draw_Overlay_Close(JNIEnv *env, jobject clazz) {
    Close();
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_cp_jo_tool_Tools_securityBool(JNIEnv *env, jclass clazz, jfloat key) {
    key = key + 0.010f;
    if (key == 1.020f) {
        return true;
    } else {
        return false;
    }
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_cp_jo_tool_Tools_checkPremium(JNIEnv *env, jclass clazz) {
    return isVIP;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_cp_jo_tool_Tools_setPremium(JNIEnv *env, jclass clazz, jboolean premium) {
    isVIP = premium;
}