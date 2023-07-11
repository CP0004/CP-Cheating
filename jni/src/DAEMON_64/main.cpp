#include "main.h"
#include "Structs.h"
#include "Utilities.h"
#include "socket.h"

using namespace std;

char name[100];
uintptr_t gname_buff[30];
char loaded[0x4000], loadedpn[20];
int rangKill = 0;

void UpdateValue(Response &response, Request &request);

int main(int argc, char *argv[])
{
    if (!Create())
        exit(1);

    if (!Connect())
        exit(1);

    if (isApkRunning("com.tencent.ig") == 1)
        target_pid = find_pid("com.tencent.ig");
    else if (isApkRunning("com.vng.pubgmobile") == 1)
        target_pid = find_pid("com.vng.pubgmobile");
    else if (isApkRunning("com.pubg.krmobile") == 1)
        target_pid = find_pid("com.pubg.krmobile");
    else if (isApkRunning("com.rekoo.pubgm") == 1)
        target_pid = find_pid("com.rekoo.pubgm");
    else if (isApkRunning("com.pubg.imobile") == 1)
        target_pid = find_pid("com.pubg.imobile");
    else
        exit(1);

    if (target_pid < 0)
        exit(1);

    libbase = get_module_base("libUE4.so");
    if (libbase < 0)
        exit(1);

    unsigned int iMemClock, iCurClock, iLoops;
    char aFPS[12];

    Request request{};
    Response response{};

    while ((receive((void *)&request) > 0))
    {

        if (iMemClock > (iCurClock = clock()))
            iLoops++;
        else
        {
            snprintf(aFPS, sizeof(aFPS), "%d", iLoops);
            strcpy(response.FpsSock, aFPS);
            iMemClock = iCurClock + CLOCKS_PER_SEC;
            iLoops = 0;
        }

        Width = request.ScreenWidth;
        Height = request.ScreenHeight;

        response.Success = false;
        response.InLobby = false;
        response.PlayerCount = 0;
        response.ItemsCount = 0;
        response.VehicleCount = 0;
        response.GrenadeCount = 0;
        UpdateValue(response, request);
        send((void *)&response, sizeof(response));
    }
    Close();
    return 0;
}

void UpdateValue(Response &response, Request &request)
{
    uintptr_t uWorld = getPtr(getPtr(getPtr(getRealOffset(Offsets::GWorld)) + 0x58) + 0x78);
    uintptr_t uLevel = getPtr(uWorld + Offsets::PersistentLevel);
    uintptr_t PlayerController = getPtr(getPtr(getPtr(uWorld + 0x38) + 0x78) + 0x30);
    uintptr_t PlayerCameraManager = getPtr(PlayerController + Offsets::PlayerCameraManager);

    uintptr_t LocalPlayer;
    uintptr_t gname = getPtr(getPtr(libbase + Offsets::GNames) + 0x110);
    MinimalViewInfo POV = MinimalViewInfo();
    CameraCacheEntry CameraCache = CameraCacheEntry();

    if (PlayerCameraManager)
    {
        CameraCache = Read<CameraCacheEntry>(PlayerCameraManager + Offsets::CameraCache);
        POV = CameraCache.POV;
    }

    uintptr_t entityPtr = getActorsArray(uLevel, Offsets::ULevelToAActors, 0x448);
    uintptr_t ULevelToAActors = getPtr(entityPtr);
    int ULevelToAActorsCount = Read<int>(entityPtr + SIZE);

    pvm(gname, &gname_buff, sizeof(gname_buff));
    if (ULevelToAActorsCount < 0)
    {
        ULevelToAActorsCount = 0;
    }
    else if (ULevelToAActorsCount > 1024)
    {
        ULevelToAActorsCount = 1024;
    }

    memset(loaded, 0, 1000);
    for (int i = 0; i < ULevelToAActorsCount; ++i)
    {

        LOGD("%d", ULevelToAActorsCount);

        uintptr_t pBase = getPtr(ULevelToAActors + i * SIZE);

        LOGD("%lx", pBase);

        if (!isValid64(pBase))
            continue;

        LOGD("1");

        if (Read<int>(pBase + SIZE) != 8)
            continue;
        LOGD("2");

        int ids = Read<int>(pBase + 8 + 2 * SIZE);
        int page = ids / 0x4000;
        int index = ids % 0x4000;

        if (page < 1 || page > 30)
            continue;
        LOGD("3");

        if (gname_buff[page] == 0)
            gname_buff[page] = getPtr(gname + page * SIZE);
        strcpy(name, getText(getPtr(gname_buff[page] + index * SIZE)));

        if (strlen(name) < 5)
            continue;

        LOGD("4");

        LOGD("%s", name);

        if (strstr(name, "BP_PlayerLobbyPawn_C"))
        {
            response.InLobby = true;
            rangKill = 0;
            response.Players->countKill = 0;
            continue;
        }
        else if (strstr(name, "BP_PlayerPawn") || strstr(name, "BP_PlayerState") || strstr(name, "BP_PlayerCharacter"))
        {

            sprintf(loadedpn, "%lx,", pBase);

            if (strstr(loaded, loadedpn))
                continue;

            strcat(loaded, loadedpn);

            int PlayerDeath = Read<int>(pBase + Offsets::bDead);
            int TeamID = Read<int>(pBase + Offsets::TeamID);
            int kill = Read<int>(pBase + Offsets::Kills);
            if (kill > rangKill)
            {
                rangKill = kill;
                response.Players->countKill = kill;
            }
            if (PlayerDeath == 1)
                continue;

            if (PlayerController)
            {
                int LocalPlayerKey = Read<int>(
                    PlayerController + Offsets::PlayerKeyController);
                if (pBase)
                {
                    int PlayerKey = Read<int>(pBase + Offsets::PlayerKeyCharacter);
                    if (PlayerKey == LocalPlayerKey)
                    {
                        LocalPlayer = pBase;
                    }
                }
            }
            else
            {
                LocalPlayer = 0;
            }

            if (LocalPlayer)
            {

                ShootWeaponBase shootWeaponBase(pBase);

                shootWeaponBase.RangeIPadView(request.memory.isIPadView);
                if (shootWeaponBase.isValid())
                {

                    if (request.memory.isLess)
                    {
                        shootWeaponBase.setLessRecoil();
                    }

                    if (request.memory.isCrosshair)
                    {
                        shootWeaponBase.setSmallCrosshair();
                    }

                    if (request.memory.isAimbotSDK)
                    {
                        shootWeaponBase.Speedfire();
                    }
                }

                int LocalKey = Read<int>(LocalPlayer + Offsets::PlayerKeyController);
                int PlayerKey = Read<int>(pBase + Offsets::PlayerKeyCharacter);
                if (PlayerKey == LocalKey)
                {
                    continue;
                }

                int myTeamID = Read<int>(LocalPlayer + Offsets::TeamID);
                if (TeamID == myTeamID)
                {
                    continue;
                }
            }

            Vector3 myRootPos;
            if (LocalPlayer)
            {
                myRootPos = GetBoneLocation(LocalPlayer, BoneID::Root);
            }
            else
            {
                myRootPos = POV.Location;
            }

            uintptr_t RootComponent = getPtr(pBase + Offsets::RootComponent);
            Vector3 RelativePosition = Read<Vector3>(RootComponent + Offsets::RelativeLocation);
            float Distance = (Vector3::Distance(RelativePosition, myRootPos) / 100.0f);

            int getIsbBot = Read<int>(pBase + Offsets::bIsAI);
            float Health = Read<float>(pBase + Offsets::Health);
            bool isBot = (getIsbBot != 0);
            Vector3 HeadPos = WorldToScreen(GetBoneLocation(pBase, BoneID::Head), POV, Width, Height);
            Vector3 RootPos = WorldToScreen(GetBoneLocation(pBase, BoneID::Root), POV, Width, Height);
            Vector3 neckPos = WorldToScreen(GetBoneLocation(pBase, BoneID::neck_01), POV, Width, Height);
            Vector3 chestPos = WorldToScreen(GetBoneLocation(pBase, BoneID::spine_03), POV, Width, Height);
            Vector3 pelvisPos = WorldToScreen(GetBoneLocation(pBase, BoneID::pelvis), POV, Width, Height);
            Vector3 lShoulderPos = WorldToScreen(GetBoneLocation(pBase, BoneID::eyebrow_l), POV, Width, Height);
            Vector3 rShoulderPos = WorldToScreen(GetBoneLocation(pBase, BoneID::nose_side_r_02), POV, Width, Height);
            Vector3 lElbowPos = WorldToScreen(GetBoneLocation(pBase, BoneID::eyebrow_r), POV, Width, Height);
            Vector3 rElbowPos = WorldToScreen(GetBoneLocation(pBase, BoneID::nose_side_l_01), POV, Width, Height);
            Vector3 lWristPos = WorldToScreen(GetBoneLocation(pBase, BoneID::hair_r_02), POV, Width, Height);
            Vector3 rWristPos = WorldToScreen(GetBoneLocation(pBase, BoneID::hair_r_01), POV, Width, Height);
            Vector3 lThighPos = WorldToScreen(GetBoneLocation(pBase, BoneID::lip_um_01), POV, Width, Height);
            Vector3 rThighPos = WorldToScreen(GetBoneLocation(pBase, BoneID::lip_r), POV, Width, Height);
            Vector3 lKneePos = WorldToScreen(GetBoneLocation(pBase, BoneID::lip_um_02), POV, Width, Height);
            Vector3 rKneePos = WorldToScreen(GetBoneLocation(pBase, BoneID::hair_root), POV, Width, Height);
            Vector3 lAnklePos = WorldToScreen(GetBoneLocation(pBase, BoneID::lip_ur), POV, Width, Height);
            Vector3 rAnklePos = WorldToScreen(GetBoneLocation(pBase, BoneID::hair_b_01), POV, Width, Height);

            PlayerBone playerBone{neckPos, chestPos, pelvisPos, lShoulderPos, rShoulderPos, lElbowPos, rElbowPos, lWristPos, rWristPos, lThighPos, rThighPos, lKneePos, rKneePos, lAnklePos, rAnklePos};
            PlayerData *data = &response.Players[response.PlayerCount];
            strcpy(data->PlayerNameByte, "66:111:116:");
            strcpy(data->PlayerNationByte, "66:111:116:");

            if (data->HeadLocation.Z >= 0 && data->HeadLocation.X < Width && data->HeadLocation.X > 0)
            {
                strcpy(data->PlayerNameByte, getNameByte(getPtr(pBase + Offsets::PlayerName)));
                strcpy(data->PlayerNationByte, getNameByte(getPtr(pBase + Offsets::Nation)));

                if (strlen(data->PlayerNameByte) < 4)
                    continue;

                if (strlen(data->PlayerNationByte) < 4)
                    continue;
            }

            data->TeamID = TeamID;
            data->Health = Health;
            data->Distance = Distance;
            data->isBot = isBot;
            data->Weapon = getPlayerWeapon(pBase);
            data->HeadLocation = HeadPos;
            data->RootLocation = RootPos;
            data->Bone = playerBone;
            if (Distance > 360.0f)
                continue;
            ++response.PlayerCount;
            if (response.PlayerCount == maxplayerCount)
                continue;
        }
        else if (strstr(name, "BP_Grenade_Shoulei_C") || strstr(name, "BP_Grenade_Burn_C") || strstr(name, "BP_Grenade_Stun_C") || strstr(name, "BP_Grenade_Smoke_C"))
        {
            uintptr_t RootComponent = getPtr(pBase + Offsets::RootComponent);
            Vector3 RelativePosition = Read<Vector3>(RootComponent + Offsets::RelativeLocation);

            Vector3 myPos;
            if (LocalPlayer)
                myPos = TransformToLocation(GetComponentToWorld(LocalPlayer), GetBoneTransform(LocalPlayer, 0));
            else
                myPos = POV.Location;

            float Distance = (Vector3::Distance(RelativePosition, myPos) / 100.0f);
            Vector3 screenPos = WorldToScreen(RelativePosition, POV, Width, Height);

            GrenadeData *grenadeData = &response.Grenade[response.GrenadeCount];
            if (strstr(name, "Shoulei"))
                grenadeData->type = 1;
            else if (strstr(name, "Burn"))
                grenadeData->type = 2;
            else if (strstr(name, "Stun"))
                grenadeData->type = 3;
            else if (strstr(name, "Smoke"))
                grenadeData->type = 4;
            grenadeData->Location = screenPos;
            grenadeData->Distance = Distance;

            if (Distance > 100.0f)
                continue;
            response.GrenadeCount++;
            if (response.GrenadeCount == maxgrenadeCount)
                continue;
        }
        else if (strstr(name, "VH") || (strstr(name, "PickUp_") && !strstr(name, "BP")) || strstr(name, "Rony") || strstr(name, "Mirado") || strstr(name, "LadaNiva") || strstr(name, "AquaRail"))
        {
            uintptr_t RootComponent = getPtr(pBase + Offsets::RootComponent);
            Vector3 RelativePosition = Read<Vector3>(RootComponent + Offsets::RelativeLocation);

            Vector3 myPos;
            if (LocalPlayer)
                myPos = TransformToLocation(GetComponentToWorld(LocalPlayer), GetBoneTransform(LocalPlayer, 0));
            else
                myPos = POV.Location;

            float Distance = (Vector3::Distance(RelativePosition, myPos) / 100.0f);
            Vector3 screenPos = WorldToScreen(RelativePosition, POV, Width, Height);

            if (screenPos.Z > 0)
            {
                VehicleData *vehicleData = &response.Vehicles[response.VehicleCount];
                strcpy(vehicleData->VehicleName, name);
                vehicleData->Location = screenPos;
                vehicleData->Distance = Distance;
                if (response.VehicleCount >= maxvehicleCount)
                {
                    continue;
                }
                ++response.VehicleCount;
            }
        }
        else if (strstr(name, "Pickup_") || strstr(name, "BP_Ammo") || strstr(name, "BP_QK") || strstr(name, "Wrapper") || strstr(name, "BP_AirDropPlane_C") || strstr(name, "PlayerDeadInventoryBox_C") || strstr(name, "BP_AirDropBox_C"))
        {
            uintptr_t RootComponent = getPtr(pBase + Offsets::RootComponent);
            Vector3 RelativePosition = Read<Vector3>(RootComponent + Offsets::RelativeLocation);

            Vector3 myPos;
            if (LocalPlayer)
                myPos = TransformToLocation(GetComponentToWorld(LocalPlayer), GetBoneTransform(LocalPlayer, 0));
            else
                myPos = POV.Location;

            float Distance = (Vector3::Distance(RelativePosition, myPos) / 100.0f);
            Vector3 screenPos = WorldToScreen(RelativePosition, POV, Width, Height);

            if (screenPos.Z > 0)
            {
                ItemData *itemData = &response.Items[response.ItemsCount];
                strcpy(itemData->ItemName, name);
                itemData->Location = screenPos;
                itemData->Distance = Distance;
                if (response.ItemsCount >= maxitemsCount)
                    continue;
                ++response.ItemsCount;
            }
        }
    }

    if (response.PlayerCount + response.ItemsCount + response.VehicleCount + response.GrenadeCount)
        response.Success = true;
}
