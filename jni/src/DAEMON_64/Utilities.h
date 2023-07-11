#ifndef COSMIC_UTILITIES_H
#define COSMIC_UTILITIES_H

#include <string>
using namespace std;

float healthbuff[2];

struct Actors
{
    uint64_t Enc_1, Enc_2;
    uint64_t Enc_3, Enc_4;
};

struct Chunk
{
    uint32_t val_1, val_2, val_3, val_4;
    uint32_t val_5, val_6, val_7, val_8;
};

uint64_t getActorsArray(uint64_t uLevel, int Actors_Offset, int EncryptedActors_Offset)
{
    if (uLevel < 0x10000000)
        return 0;

    if (Read<uint64_t>(uLevel + Actors_Offset) > 0)
        return uLevel + Actors_Offset;

    if (Read<uint64_t>(uLevel + EncryptedActors_Offset) > 0)
        return uLevel + EncryptedActors_Offset;

    auto AActors = Read<Actors>(uLevel + EncryptedActors_Offset + 0x10);

    if (AActors.Enc_1 > 0)
    {
        auto Enc = Read<Chunk>(AActors.Enc_1 + 0x80);
        return (((Read<uint8_t>(AActors.Enc_1 + Enc.val_1) | (Read<uint8_t>(AActors.Enc_1 + Enc.val_2) << 8)) | (Read<uint8_t>(AActors.Enc_1 + Enc.val_3) << 0x10)) & 0xFFFFFF | ((uint64_t)Read<uint8_t>(AActors.Enc_1 + Enc.val_4) << 0x18) | ((uint64_t)Read<uint8_t>(AActors.Enc_1 + Enc.val_5) << 0x20)) & 0xFFFF00FFFFFFFFFF | ((uint64_t)Read<uint8_t>(AActors.Enc_1 + Enc.val_6) << 0x28) | ((uint64_t)Read<uint8_t>(AActors.Enc_1 + Enc.val_7) << 0x30) | ((uint64_t)Read<uint8_t>(AActors.Enc_1 + Enc.val_8) << 0x38);
    }
    else if (AActors.Enc_2 > 0)
    {
        auto Lost_Actors = Read<uint64_t>(AActors.Enc_2);
        if (Lost_Actors > 0)
        {
            return (uint16_t)(Lost_Actors - 0x400) & 0xFF00 | (uint8_t)(Lost_Actors - 0x04) | (Lost_Actors + 0xFC0000) & 0xFF0000 | (Lost_Actors - 0x4000000) & 0xFF000000 | (Lost_Actors + 0xFC00000000) & 0xFF00000000 | (Lost_Actors + 0xFC0000000000) & 0xFF0000000000 | (Lost_Actors + 0xFC000000000000) & 0xFF000000000000 | (Lost_Actors - 0x400000000000000) & 0xFF00000000000000;
        }
    }
    else if (AActors.Enc_3 > 0)
    {
        auto Lost_Actors = Read<uint64_t>(AActors.Enc_3);
        if (Lost_Actors > 0)
        {
            return (Lost_Actors >> 0x38) | (Lost_Actors << (64 - 0x38));
        }
    }
    else if (AActors.Enc_4 > 0)
    {
        auto Lost_Actors = Read<uint64_t>(AActors.Enc_4);
        if (Lost_Actors > 0)
        {
            return Lost_Actors ^ 0xCDCD00;
        }
    }
    return 0;
}

PlayerWeapon getPlayerWeapon(uintptr_t address)
{
    PlayerWeapon playerWeapon;
    uintptr_t base[3];
    VMRead((void *)getPtr(address + Offsets::Children), base, sizeof(base));
    if (isValid32(base[0]) && Read<int>(base[0] + Offsets::DrawShootLineTime) == 2)
    {
        playerWeapon.isWeapon = true;
        playerWeapon.id = Read<int>(getPtr(base[0] + Offsets::WeaponEntityComp) + Offsets::WeaponId);
        playerWeapon.ammo = Read<int>(base[0] + Offsets::CurBulletNumInClip);
    }
    else if (isValid32(base[1]) && Read<int>(base[1] + Offsets::DrawShootLineTime) == 2)
    {
        playerWeapon.isWeapon = true;
        playerWeapon.id = Read<int>(getPtr(base[1] + Offsets::WeaponEntityComp) + Offsets::WeaponId);
        playerWeapon.ammo = Read<int>(base[1] + Offsets::CurBulletNumInClip);
    }
    else if (isValid32(base[2]) && Read<int>(base[2] + Offsets::DrawShootLineTime) == 2)
    {
        playerWeapon.isWeapon = true;
        playerWeapon.id = Read<int>(getPtr(base[2] + Offsets::WeaponEntityComp) + Offsets::WeaponId);
        playerWeapon.ammo = Read<int>(base[2] + Offsets::CurBulletNumInClip);
    }
    else
    {
        playerWeapon.isWeapon = false;
    }
    return playerWeapon;
}
#endif // COSMIC_UTILITIES_H
