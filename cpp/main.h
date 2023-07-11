#ifndef COSMIC_MAIN_H
#define COSMIC_MAIN_H

#include "Log.h"
#include "Socket.h"
#include "Structs.h"

bool
//-- Drawing For Player
isLine = true,
        isSkeleton = true,
        isInfoPlayer = false,
        isWeapon = false,
        isMark = false,
        isHealth = false,
        isGrenade = false,
        isBox = false,
        isDist = false,
//-- Memory
isLess = false,
        isCrossHire = false,
        isAimBot = false,
        isBullet = false,
        isBot = false,
        isKnock = false,
//Other
isVIP = false,
        isHideItem = true,
        isFixTouch = false;

float
//-- Controls
widthLine = 0.5f,
        radiusAfter360 = 10.0f,
        sizeItem = 16.0f,
        sizeVehicle = 16.0f,
        sizeInfo = 0.0f,
        upInfo = 0.0f,
//-- Memory
rangeIPadView = 0.0f,
        distAimbot = 100.0f,
        fovAimbot = 100.0f,
        targetMode = 1.0f,
//-- Other
normalSize = 7.0f,
        offsetAfter360 = 12.0f;

const char *path = "ZGF0YS91c2VyLzAvY29tLmNwLmpvL3NoYXJlZF9wcmVmcw=="; //-- data/user/0/com.cp.jo/shared_prefs
const char *password = "MTIzNDU2Nzg="; //-- 12345678
const char *email = "bW9iaWxlLm1vaGFtYWQwNzg5QGdtYWlsLmNvbQ=="; //-- mobile.mohamad0789@gmail.com
const char *_Null = "bnVsbA=="; //-- null

enum class DataControl {
    //-- Drawing For Player
    LINE,
    SKELETON,
    INFO_PLAYER,
    WEAPON,
    MARK,
    HEALTH,
    GRENADE,
    BOX,
    DIST, //-- MEMORY
    LESS,
    CROSS,
    AIMBOT_SDK,
    IPAD_VIEW,
    IS_AIMBOT,
    IS_BOT_AIMBOT,
    IS_KNOCK_AIMBOT,
    FOV_AIMBOT,
    DIST_AIMBOT,
    PRIORITY_AIMBOT,
    TARGET_AIMBOT,
    OFFSET_SCOPE,
    OFFSET_NO_SCOPE, //-- CONTROLS
    WIDTH_LINE,
    RADIUS360,
    SIZE_ITEM,
    SIZE_VEHICLE,
    SIZE_INFO,
    UP_INFO, //-- RIGHTS
    EMAIL,
    PASSWORD,
    PATH,
    _NULL, //-- Other
    HIDE_ITEMS
};

//-- returns the size of a character array using a pointer to the first element of the character array
int size(char *ptr) {
    //variable used to access the subsequent array elements.
    int offset = 0;
    //variable that counts the number of elements in your array
    int count = 0;

    //While loop that tests whether the end of the array has been reached
    while (*(ptr + offset) != '\0') {
        //increment the count variable
        ++count;
        //advance to the next element of the array
        ++offset;
    }
    //return the size of the array
    return count;
}

//-- Get FPS Screen [[
uint64_t GetTickCount() {
    using namespace std::chrono;
    return duration_cast<milliseconds>(steady_clock::now().time_since_epoch()).count();
}

class Interval {
private:
    int initial_;

public:
    inline Interval() : initial_(GetTickCount()) {}

    virtual ~Interval() {}

    inline unsigned int value() const {
        return GetTickCount() - initial_;
    }
};

class FPS {
protected:
    int32_t m_fps;
    int32_t m_fpscount;
    Interval m_fpsinterval;

public:
    FPS() : m_fps(0), m_fpscount(0) {}

    void Update() {
        m_fpscount++;
        if (m_fpsinterval.value() > 1000) {
            m_fps = m_fpscount;
            m_fpscount = 0;
            m_fpsinterval = Interval();
        }
    }

    int get() const {
        return m_fps;
    }
};

FPS m_fps;

//]]
#endif //COSMIC_MAIN_H
