//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_STRUCTS_H
#define COSMIC_STRUCTS_H

#include "Quaternion.hpp"
#include "Vector2.hpp"
#include "Vector3.hpp"

struct FMatrix
{
    float M[4][4];
};

struct D3DMatrix
{
    float _11, _12, _13, _14;
    float _21, _22, _23, _24;
    float _31, _32, _33, _34;
    float _41, _42, _43, _44;
};

struct FTransform
{
    Quaternion Rotation;
    Vector3 Translation;
    char pad[0x4];
    Vector3 Scale3D;
    D3DMatrix ToMatrixWithScale()
    {
        D3DMatrix m;
        m._41 = Translation.X;
        m._42 = Translation.Y;
        m._43 = Translation.Z;

        float x2 = Rotation.X + Rotation.X;
        float y2 = Rotation.Y + Rotation.Y;
        float z2 = Rotation.Z + Rotation.Z;

        float xx2 = Rotation.X * x2;
        float yy2 = Rotation.Y * y2;
        float zz2 = Rotation.Z * z2;
        m._11 = (1.0f - (yy2 + zz2)) * Scale3D.X;
        m._22 = (1.0f - (xx2 + zz2)) * Scale3D.Y;
        m._33 = (1.0f - (xx2 + yy2)) * Scale3D.Z;

        float yz2 = Rotation.Y * z2;
        float wx2 = Rotation.W * x2;
        m._32 = (yz2 - wx2) * Scale3D.Z;
        m._23 = (yz2 + wx2) * Scale3D.Y;

        float xy2 = Rotation.X * y2;
        float wz2 = Rotation.W * z2;
        m._21 = (xy2 - wz2) * Scale3D.Y;
        m._12 = (xy2 + wz2) * Scale3D.X;

        float xz2 = Rotation.X * z2;
        float wy2 = Rotation.W * y2;
        m._31 = (xz2 + wy2) * Scale3D.Z;
        m._13 = (xz2 - wy2) * Scale3D.X;

        m._14 = 0.0f;
        m._24 = 0.0f;
        m._34 = 0.0f;
        m._44 = 1.0f;

        return m;
    }
};

struct FRotator
{
    float Pitch;
    float Yaw;
    float Roll;
};

struct MinimalViewInfo
{
    Vector3 Location;
    Vector3 LocationLocalSpace;
    FRotator Rotation;
    float FOV;
};

struct CameraCacheEntry
{
    float TimeStamp;
    char chunks[0xC];
    MinimalViewInfo POV;
};

Vector3 MatrixToVector(FMatrix matrix)
{
    return {matrix.M[3][0], matrix.M[3][1], matrix.M[3][2]};
}

FMatrix MatrixMultiplication(FMatrix m1, FMatrix m2)
{
    FMatrix matrix = FMatrix();
    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            for (int k = 0; k < 4; k++)
            {
                matrix.M[i][j] += m1.M[i][k] * m2.M[k][j];
            }
        }
    }
    return matrix;
}

D3DMatrix MatrixMultiplication(D3DMatrix pM1, D3DMatrix pM2)
{
    D3DMatrix pOut;
    pOut._11 = pM1._11 * pM2._11 + pM1._12 * pM2._21 + pM1._13 * pM2._31 + pM1._14 * pM2._41;
    pOut._12 = pM1._11 * pM2._12 + pM1._12 * pM2._22 + pM1._13 * pM2._32 + pM1._14 * pM2._42;
    pOut._13 = pM1._11 * pM2._13 + pM1._12 * pM2._23 + pM1._13 * pM2._33 + pM1._14 * pM2._43;
    pOut._14 = pM1._11 * pM2._14 + pM1._12 * pM2._24 + pM1._13 * pM2._34 + pM1._14 * pM2._44;
    pOut._21 = pM1._21 * pM2._11 + pM1._22 * pM2._21 + pM1._23 * pM2._31 + pM1._24 * pM2._41;
    pOut._22 = pM1._21 * pM2._12 + pM1._22 * pM2._22 + pM1._23 * pM2._32 + pM1._24 * pM2._42;
    pOut._23 = pM1._21 * pM2._13 + pM1._22 * pM2._23 + pM1._23 * pM2._33 + pM1._24 * pM2._43;
    pOut._24 = pM1._21 * pM2._14 + pM1._22 * pM2._24 + pM1._23 * pM2._34 + pM1._24 * pM2._44;
    pOut._31 = pM1._31 * pM2._11 + pM1._32 * pM2._21 + pM1._33 * pM2._31 + pM1._34 * pM2._41;
    pOut._32 = pM1._31 * pM2._12 + pM1._32 * pM2._22 + pM1._33 * pM2._32 + pM1._34 * pM2._42;
    pOut._33 = pM1._31 * pM2._13 + pM1._32 * pM2._23 + pM1._33 * pM2._33 + pM1._34 * pM2._43;
    pOut._34 = pM1._31 * pM2._14 + pM1._32 * pM2._24 + pM1._33 * pM2._34 + pM1._34 * pM2._44;
    pOut._41 = pM1._41 * pM2._11 + pM1._42 * pM2._21 + pM1._43 * pM2._31 + pM1._44 * pM2._41;
    pOut._42 = pM1._41 * pM2._12 + pM1._42 * pM2._22 + pM1._43 * pM2._32 + pM1._44 * pM2._42;
    pOut._43 = pM1._41 * pM2._13 + pM1._42 * pM2._23 + pM1._43 * pM2._33 + pM1._44 * pM2._43;
    pOut._44 = pM1._41 * pM2._14 + pM1._42 * pM2._24 + pM1._43 * pM2._34 + pM1._44 * pM2._44;

    return pOut;
}

FMatrix TransformToMatrix(FTransform transform)
{
    FMatrix matrix;

    matrix.M[3][0] = transform.Translation.X;
    matrix.M[3][1] = transform.Translation.Y;
    matrix.M[3][2] = transform.Translation.Z;

    float x2 = transform.Rotation.X + transform.Rotation.X;
    float y2 = transform.Rotation.Y + transform.Rotation.Y;
    float z2 = transform.Rotation.Z + transform.Rotation.Z;

    float xx2 = transform.Rotation.X * x2;
    float yy2 = transform.Rotation.Y * y2;
    float zz2 = transform.Rotation.Z * z2;

    matrix.M[0][0] = (1.0f - (yy2 + zz2)) * transform.Scale3D.X;
    matrix.M[1][1] = (1.0f - (xx2 + zz2)) * transform.Scale3D.Y;
    matrix.M[2][2] = (1.0f - (xx2 + yy2)) * transform.Scale3D.Z;

    float yz2 = transform.Rotation.Y * z2;
    float wx2 = transform.Rotation.W * x2;
    matrix.M[2][1] = (yz2 - wx2) * transform.Scale3D.Z;
    matrix.M[1][2] = (yz2 + wx2) * transform.Scale3D.Y;

    float xy2 = transform.Rotation.X * y2;
    float wz2 = transform.Rotation.W * z2;
    matrix.M[1][0] = (xy2 - wz2) * transform.Scale3D.Y;
    matrix.M[0][1] = (xy2 + wz2) * transform.Scale3D.X;

    float xz2 = transform.Rotation.X * z2;
    float wy2 = transform.Rotation.W * y2;
    matrix.M[2][0] = (xz2 + wy2) * transform.Scale3D.Z;
    matrix.M[0][2] = (xz2 - wy2) * transform.Scale3D.X;

    matrix.M[0][3] = 0;
    matrix.M[1][3] = 0;
    matrix.M[2][3] = 0;
    matrix.M[3][3] = 1;

    return matrix;
}

FMatrix RotatorToMatrix(FRotator rotation)
{
    float radPitch = rotation.Pitch * ((float)3.14159265358979323846 / 180.0f);
    float radYaw = rotation.Yaw * ((float)3.14159265358979323846 / 180.0f);
    float radRoll = rotation.Roll * ((float)3.14159265358979323846 / 180.0f);

    float SP = sinf(radPitch);
    float CP = cosf(radPitch);
    float SY = sinf(radYaw);
    float CY = cosf(radYaw);
    float SR = sinf(radRoll);
    float CR = cosf(radRoll);

    FMatrix matrix;

    matrix.M[0][0] = (CP * CY);
    matrix.M[0][1] = (CP * SY);
    matrix.M[0][2] = (SP);
    matrix.M[0][3] = 0;

    matrix.M[1][0] = (SR * SP * CY - CR * SY);
    matrix.M[1][1] = (SR * SP * SY + CR * CY);
    matrix.M[1][2] = (-SR * CP);
    matrix.M[1][3] = 0;

    matrix.M[2][0] = (-(CR * SP * CY + SR * SY));
    matrix.M[2][1] = (CY * SR - CR * SP * SY);
    matrix.M[2][2] = (CR * CP);
    matrix.M[2][3] = 0;

    matrix.M[3][0] = 0;
    matrix.M[3][1] = 0;
    matrix.M[3][2] = 0;
    matrix.M[3][3] = 1;

    return matrix;
}

Vector3 TransformToLocation(FTransform c2w, FTransform transform)
{
    return MatrixToVector(MatrixMultiplication(TransformToMatrix(transform), TransformToMatrix(c2w)));
}

FRotator CalcAngle(Vector3 LocalHeadPosition, Vector3 AimPosition)
{
    Vector3 rotation = LocalHeadPosition - AimPosition;
    float hyp = sqrt(rotation.X * rotation.X + rotation.Y * rotation.Y);

    FRotator newAngle = FRotator();

    newAngle.Pitch = (-atan(rotation.Z / hyp) * (180.0f / (float)3.14159265358979323846));
    newAngle.Yaw = (atan(rotation.Y / rotation.X) * (180.0f / (float)3.14159265358979323846));
    newAngle.Roll = 0.0f;

    if (rotation.X >= 0.0f)
        newAngle.Yaw += 180.0f;

    return newAngle;
}

void ClampAngles(FRotator &angles)
{
    if (angles.Pitch > 180)
        angles.Pitch -= 360;
    if (angles.Pitch < -180)
        angles.Pitch += 360;

    if (angles.Pitch < -75.f)
        angles.Pitch = -75.f;
    else if (angles.Pitch > 75.f)
        angles.Pitch = 75.f;

    while (angles.Yaw < -180.0f)
        angles.Yaw += 360.0f;
    while (angles.Yaw > 180.0f)
        angles.Yaw -= 360.0f;
}

void ClampAngles(float *angles)
{
    if (angles[0] > 180)
        angles[0] -= 360;
    if (angles[0] < -180)
        angles[0] += 360;

    if (angles[0] < -75.f)
        angles[0] = -75.f;
    else if (angles[0] > 75.f)
        angles[0] = 75.f;

    while (angles[1] < -180.0f)
        angles[1] += 360.0f;
    while (angles[1] > 180.0f)
        angles[1] -= 360.0f;
}

#define PI 3.141592653589793238
Vector3 CalcMousePos(Vector3 headPos, Vector3 uMyobejctPos)
{
    Vector3 AimPos;
    float x = headPos.X - uMyobejctPos.X;
    float y = headPos.Y - uMyobejctPos.Y;
    float z = headPos.Z - uMyobejctPos.Z;
    AimPos.X = atan2(y, x) * 180.f / M_PI;
    AimPos.Y = atan2(z, sqrt(x * x + y * y)) * 180.f / PI;
    return AimPos;
}

enum BoneID : int
{
    Root = 0,
    pelvis = 1,
    spine_01 = 2,
    spine_02 = 3,
    spine_03 = 4,
    neck_01 = 5,
    Head = 6,
    face_root = 7,
    eyebrows_pos_root = 8,
    eyebrows_root = 9,
    eyebrows_r = 10,
    eyebrows_l = 11,
    eyebrow_l = 12,
    eyebrow_r = 13,
    forehead_root = 14,
    forehead = 15,
    jaw_pos_root = 16,
    jaw_root = 17,
    jaw = 18,
    mouth_down_pos_root = 19,
    mouth_down_root = 20,
    lip_bm_01 = 21,
    lip_bm_02 = 22,
    lip_br = 23,
    lip_bl = 24,
    jaw_01 = 25,
    jaw_02 = 26,
    cheek_pos_root = 27,
    cheek_root = 28,
    cheek_r = 29,
    cheek_l = 30,
    nose_side_root = 31,
    nose_side_r_01 = 32,
    nose_side_r_02 = 33,
    nose_side_l_01 = 34,
    nose_side_l_02 = 35,
    eye_pos_r_root = 36,
    eye_r_root = 37,
    eye_rot_r_root = 38,
    eye_lid_u_r = 39,
    eye_r = 40,
    eye_lid_b_r = 41,
    eye_pos_l_root = 42,
    eye_l_root = 43,
    eye_rot_l_root = 44,
    eye_lid_u_l = 45,
    eye_l = 46,
    eye_lid_b_l = 47,
    nose_pos_root = 48,
    nose = 49,
    mouth_up_pos_root = 50,
    mouth_up_root = 51,
    lip_ul = 52,
    lip_um_01 = 53,
    lip_um_02 = 54,
    lip_ur = 55,
    lip_l = 56,
    lip_r = 57,
    hair_root = 58,
    hair_b_01 = 59,
    hair_b_02 = 60,
    hair_l_01 = 61,
    hair_l_02 = 62,
    hair_r_01 = 63,
    hair_r_02 = 64,
    hair_f_02 = 65,
    hair_f_01 = 66,
    hair_b_pt_01 = 67,
    hair_b_pt_02 = 68,
    hair_b_pt_03 = 69,
    hair_b_pt_04 = 70,
    hair_b_pt_05 = 71,
    camera_fpp = 72,
    GunReferencePoint = 73,
    GunRef = 74,
    breast_l = 75,
    breast_r = 76,
    clavicle_l = 77,
    upperarm_l = 78,
    lowerarm_l = 79,
    hand_l = 80,
    thumb_01_l = 81,
    thumb_02_l = 82,
    thumb_03_l = 83,
    thumb_04_l_MBONLY = 84,
    index_01_l = 85,
    index_02_l = 86,
    index_03_l = 87,
    index_04_l_MBONLY = 88,
    middle_01_l = 89,
    middle_02_l = 90,
    middle_03_l = 91,
    middle_04_l_MBONLY = 92,
    ring_01_l = 93,
    ring_02_l = 94,
    ring_03_l = 95,
    ring_04_l_MBONLY = 96,
    pinky_01_l = 97,
    pinky_02_l = 98,
    pinky_03_l = 99,
    pinky_04_l_MBONLY = 100,
    item_l = 101,
    lowerarm_twist_01_l = 102,
    upperarm_twist_01_l = 103,
    clavicle_r = 104,
    upperarm_r = 105,
    lowerarm_r = 106,
    hand_r = 107,
    thumb_01_r = 108,
    thumb_02_r = 109,
    thumb_03_r = 110,
    thumb_04_r_MBONLY = 111,
    index_01_r = 112,
    index_02_r = 113,
    index_03_r = 114,
    index_04_r_MBONLY = 115,
    middle_01_r = 116,
    middle_02_r = 117,
    middle_03_r = 118,
    middle_04_r_MBONLY = 119,
    ring_01_r = 120,
    ring_02_r = 121,
    ring_03_r = 122,
    ring_04_r_MBONLY = 123,
    pinky_01_r = 124,
    pinky_02_r = 125,
    pinky_03_r = 126,
    pinky_04_r_MBONLY = 127,
    item_r = 128,
    lowerarm_twist_01_r = 129,
    upperarm_twist_01_r = 130,
    BackPack = 131,
    backpack_01 = 132,
    backpack_02 = 133,
    Slot_Primary = 134,
    Slot_Secondary = 135,
    Slot_Melee = 136,
    slot_throwable = 137,
    coat_l_01 = 138,
    coat_l_02 = 139,
    coat_l_03 = 140,
    coat_l_04 = 141,
    coat_fl_01 = 142,
    coat_fl_02 = 143,
    coat_fl_03 = 144,
    coat_fl_04 = 145,
    coat_b_01 = 146,
    coat_b_02 = 147,
    coat_b_03 = 148,
    coat_b_04 = 149,
    coat_r_01 = 150,
    coat_r_02 = 151,
    coat_r_03 = 152,
    coat_r_04 = 153,
    coat_fr_01 = 154,
    coat_fr_02 = 155,
    coat_fr_03 = 156,
    coat_fr_04 = 157,
    thigh_l = 158,
    calf_l = 159,
    foot_l = 160,
    ball_l = 161,
    calf_twist_01_l = 162,
    thigh_twist_01_l = 163,
    thigh_r = 164,
    calf_r = 165,
    foot_r = 166,
    ball_r = 167,
    calf_twist_01_r = 168,
    thigh_twist_01_r = 169,
    Slot_SideArm = 170,
    skirt_l_01 = 171,
    skirt_l_02 = 172,
    skirt_l_03 = 173,
    skirt_f_01 = 174,
    skirt_f_02 = 175,
    skirt_f_03 = 176,
    skirt_b_01 = 177,
    skirt_b_02 = 178,
    skirt_b_03 = 179,
    skirt_r_01 = 180,
    skirt_r_02 = 181,
    skirt_r_03 = 182,
    ik_hand_root = 183,
    ik_hand_gun = 184,
    ik_hand_r = 185,
    ik_hand_l = 186,
    ik_aim_root = 187,
    ik_aim_l = 188,
    ik_aim_r = 189,
    ik_foot_root = 190,
    ik_foot_l = 191,
    ik_foot_r = 192,
    camera_tpp = 193,
    ik_target_root = 194,
    ik_target_l = 195,
    ik_target_r = 196,
    VB_spine_03_spine_03 = 197,
    VB_upperarm_r_lowerarm_r = 198
};

Vector3 WorldToScreen(Vector3 worldLocation, MinimalViewInfo camViewInfo, int screenWidth, int screenHeight)
{
    FMatrix tempMatrix = RotatorToMatrix(camViewInfo.Rotation);

    Vector3 vAxisX(tempMatrix.M[0][0], tempMatrix.M[0][1], tempMatrix.M[0][2]);
    Vector3 vAxisY(tempMatrix.M[1][0], tempMatrix.M[1][1], tempMatrix.M[1][2]);
    Vector3 vAxisZ(tempMatrix.M[2][0], tempMatrix.M[2][1], tempMatrix.M[2][2]);

    Vector3 vDelta = worldLocation - camViewInfo.Location;

    Vector3 vTransformed(Vector3::Dot(vDelta, vAxisY), Vector3::Dot(vDelta, vAxisZ), Vector3::Dot(vDelta, vAxisX));

    float fov = camViewInfo.FOV;
    float screenCenterX = (screenWidth / 2.0f);
    float screenCenterY = (screenHeight / 2.0f);

    float X = (screenCenterX + vTransformed.X * (screenCenterX / tanf(fov * ((float)3.14159265358979323846 / 360.0f))) / vTransformed.Z);
    float Y = (screenCenterY - vTransformed.Y * (screenCenterX / tanf(fov * ((float)3.14159265358979323846 / 360.0f))) / vTransformed.Z);
    float Z = vTransformed.Z;

    return {X, Y, Z};
}

FTransform GetBoneTransform(uintptr_t entity, int idx)
{
    uintptr_t Mesh = getPtr(entity + Offsets::Mesh);
    if (Mesh)
    {
        uintptr_t Bones = getPtr(Mesh + Offsets::MinLOD);
        if (Bones)
        {
            return Read<FTransform>(Bones + (idx * 48));
        }
    }
    return {};
}

FTransform GetComponentToWorld(uintptr_t entity)
{
    uintptr_t Mesh = getPtr(entity + Offsets::Mesh);
    if (Mesh)
    {
        return Read<FTransform>(Mesh + Offsets::ComponentToWorld);
    }
    return {};
}

Vector3 GetBoneLocation(uintptr_t Player, int boneIdx)
{
    if (Player)
    {
        return TransformToLocation(GetComponentToWorld(Player), GetBoneTransform(Player, boneIdx));
    }
    return {};
}

Vector3 GetHeadLocation(uintptr_t entity)
{
    return GetBoneLocation(entity, BoneID::Head);
}

#define maxplayerCount 100
#define maxvehicleCount 50
#define maxitemsCount 400
#define maxgrenadeCount 10
#define maxlootBoxCount 25

struct PlayerBone
{
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

struct Memory
{
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

struct PlayerWeapon
{
    bool isWeapon = false;
    int id;
    int ammo;
};

struct VehicleData
{
    char VehicleName[50];
    float Distance;
    Vector3 Location;
};

struct ItemData
{
    char ItemName[50];
    float Distance;
    Vector3 Location;
};

struct GrenadeData
{
    int type;
    float Distance;
    Vector3 Location;
};

struct PlayerData
{
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

struct Request
{
    Memory memory;
    int ScreenWidth;
    int ScreenHeight;
    bool Vip;
};

struct Response
{
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

// SDK 32 /////////////////////////////////////////////////////////////////////
struct ShootWeaponBase
{
    uintptr_t FromBase;
    uintptr_t Base;
    uintptr_t ShootWeaponEntity;
    uintptr_t baseCameraView;
    int bIsWeaponFiring;

    ShootWeaponBase(uintptr_t pBase)
    {
        FromBase = getPtr(pBase + Offsets::WeaponManagerComponent);
        Base = getPtr(FromBase + Offsets::CurrentWeaponReplicated);
        ShootWeaponEntity = getPtr(Base + Offsets::ShootWeaponEntityComp);
        bIsWeaponFiring = getPtr(pBase + Offsets::bIsWeaponFiring);
        baseCameraView = getPtr(pBase + Offsets::ThirdPersonCameraComponent);
    }

    void Speedfire()
    {
        Write(ShootWeaponEntity + Offsets::AutoAimingConfig + 0x0, "1090519040", TYPE_DWORD);
        Write(ShootWeaponEntity + Offsets::AutoAimingConfig + 0x4c, "1090519040", TYPE_DWORD);
        Write(ShootWeaponEntity + Offsets::AutoAimingConfig + 0x4, "1082130432", TYPE_DWORD);
        Write(ShootWeaponEntity + Offsets::AutoAimingConfig + 0x50, "1082130432", TYPE_DWORD);
    }

    void setLessRecoil()
    {
        Write(ShootWeaponEntity + Offsets::SRecoilInfo + Offsets::RecoilModifierStand, "0.1", TYPE_FLOAT);
        Write(ShootWeaponEntity + Offsets::SRecoilInfo + Offsets::RecoilModifierCrouch, "0.1", TYPE_FLOAT);
        Write(ShootWeaponEntity + Offsets::SRecoilInfo + Offsets::RecoilModifierProne, "0.1", TYPE_FLOAT);
    }

    void setSmallCrosshair()
    {
        Write(ShootWeaponEntity + Offsets::GameDeviationFactor, "0.1", TYPE_FLOAT);
    }

    void RangeIPadView(int range)
    {
        if (range > 90)
            WriteFloat(baseCameraView + Offsets::FieldOfView, (float)range);
    }

    bool isFiring()
    {
        return (bIsWeaponFiring != 0);
    }
    bool isValid()
    {
        return (Base != 0);
    }
};

#endif // COSMIC_STRUCTS_H
