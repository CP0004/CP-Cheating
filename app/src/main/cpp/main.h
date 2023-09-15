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

const char *path = "";
const char *password = "";
const char *email = "";
const char *_Null = ""; //-- null

enum class DataControl {
    //-- Drawing For Player
    LINE, SKELETON, INFO_PLAYER, WEAPON, MARK, HEALTH, GRENADE, BOX1, DIST, //-- MEMORY
    LESS, CROSS, AIMBOT_SDK, IPAD_VIEW, IS_AIMBOT, IS_BULLET, IS_BOT_AIMBOT, IS_KNOCK_AIMBOT, FOV_AIMBOT, DIST_AIMBOT, PRIORITY_AIMBOT, TARGET_AIMBOT, OFFSET_SCOPE, OFFSET_NO_SCOPE, //-- CONTROLS
    WIDTH_LINE, RADIUS360, SIZE_ITEM, SIZE_VEHICLE, SIZE_INFO, UP_INFO, //-- RIGHTS
    EMAIL, PASSWORD, PATH, _NULL, //-- Other
    HIDE_ITEMS, //--Items
    //item
    DROP, BOX, COINS, FLARE_GUN, //Ar
    AKM, M16A4, SCAR_L, M416, GROZA, AUG, QBZ, M762, MK47, _636C, FAMAS, //Sr
    KAR98K, M24, AWM, WIN94, MOSIN, //Dmr
    SKS, VSS, MINI14, MK14, SLR, QUB, MK12, //Smg
    UZI, UMP45, VECTOR, BIZON, THOMPSON, MP5K, P90, //Shotgun
    S686, S1897, S12K, DBS, M1014, //Lmg
    M249, DP_28, M163, //scope
    x8, x6, x4, x3, x2, RED_POINT, //Melee
    PAN, MACHETE, CROWBAR, SICKLE, //Other
    CROSSBOW, //Helmet
    HELMET_LV1, HELMET_LV2, HELMET_LV3, //armor
    ARMOR_LV1, ARMOR_LV2, ARMOR_LV3, //Backpack
    BACKPACK_LV1, BACKPACK_LV2, BACKPACK_LV3, //MadKit
    ENERGY, ADRENALINE, PAINKILLER, BANDAGES, FIRST, MADKIT, FUEL, //Muzzle
    COMPENSATOR, VERTICAL, EQ_AR, EQ_SP, EQ_SMG, E_AR, E_SP, E_SMG, //Vehicle
    UAZ, BRDM, BUS, UTV, MONSTER, PICKUP, RONY, DACIA, COUPE, MIRADO, TUKSHAI, SIDECAR, SNOWMOBILE, SNOWBIKE, MOTORCYCIE, SCOOTER, BUGGY, GILDER, PG117, AQUAPAIL, BICYCLE
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
