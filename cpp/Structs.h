#ifndef COSMIC_STRUCTS_H
#define COSMIC_STRUCTS_H

#define maxplayerCount 100
#define maxvehicleCount 50
#define maxitemsCount 400
#define maxgrenadeCount 10
#define maxlootBoxCount 25

using namespace std;

class Color {
public:
    float r;
    float g;
    float b;
    float a;

    Color() {
        this->r = 0;
        this->g = 0;
        this->b = 0;
        this->a = 0;
    }

    Color(float r, float g, float b, float a) {
        this->r = r;
        this->g = g;
        this->b = b;
        this->a = a;
    }

    Color(float r, float g, float b) {
        this->r = r;
        this->g = g;
        this->b = b;
        this->a = 255;
    }
};


class Vector2 {
public:
    float X;
    float Y;

    Vector2() {
        this->X = 0;
        this->Y = 0;
    }

    Vector2(float X, float Y) {
        this->X = X;
        this->Y = Y;
    }

};

class Vector3 {
public:
    float X;
    float Y;
    float Z;

    Vector3() {
        this->X = 0;
        this->Y = 0;
        this->Z = 0;
    }

    Vector3(float X, float Y, float Z) {
        this->X = X;
        this->Y = Y;
        this->Z = Z;
    }
};

struct PlayerBone {
    Vector3 neck;
    Vector3 chest;
    Vector3 pelvis;
    Vector3 lShoulder;
    Vector3 rShoulder;
    Vector3 lElbow;
    Vector3 rElbow;
    Vector3 lWrist;
    Vector3 rWrist;
    Vector3 lThigh;
    Vector3 rThigh;
    Vector3 lKnee;
    Vector3 rKnee;
    Vector3 lAnkle;
    Vector3 rAnkle;

};

struct Memory {
    bool isLess;
    bool isCrosshair;
    bool isAimbotSDK;
    int isIPadView;
    bool isAimbot;
    int DistAimbot;
    int FovAimbot;
    int PriorityAimbot;
    int TargetAimbot;
    bool isBotAimbot;
    bool isKnockAimbot;
    int OffsetScope;
    int OffsetNoScope;
};

struct PlayerWeapon {
    bool isWeapon = false;
    int id;
    int ammo;
};

struct VehicleData {
    char VehicleName[50];
    float Distance;
    Vector3 Location;
};

struct ItemData {
    char ItemName[50];
    float Distance;
    Vector3 Location;
};

struct GrenadeData {
    int type;
    float Distance;
    Vector3 Location;
};

struct PlayerData {
    char PlayerNameByte[100];
    char PlayerNationByte[100];
    int TeamID;
    float Health;
    float Distance;
    bool isBot;
    bool isInAim;
    int countKill;
    Vector3 HeadLocation;
    Vector3 RootLocation;
    PlayerWeapon Weapon;
    PlayerBone Bone;
};

struct Request {
    Memory memory;
    int ScreenWidth;
    int ScreenHeight;
    bool Vip = true;
};

struct Response {
    bool Success;
    bool InLobby;
    char FpsSock[12];
    int PlayerCount;
    int VehicleCount;
    int ItemsCount;
    int GrenadeCount;
    PlayerData Players[maxplayerCount];
    VehicleData Vehicles[maxvehicleCount];
    ItemData Items[maxitemsCount];
    GrenadeData Grenade[maxgrenadeCount];
};
#endif //COSMIC_STRUCTS_H
