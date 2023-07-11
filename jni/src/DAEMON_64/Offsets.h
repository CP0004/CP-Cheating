//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_OFFSETS_H
#define COSMIC_OFFSETS_H

#include <stdint.h>

namespace Offsets
{
    uintptr_t GNames = 0xba342e0;
    uintptr_t GWorld = 0xbea1400;
    uintptr_t PersistentLevel = 0x30;
    uintptr_t ULevelToAActors = 0xA0;
    uintptr_t DrawShootLineTime = 0x134;
    uintptr_t ComponentToWorld = 0x1A0;
    //--Class Actor
    uintptr_t RootComponent = 0x1a8;
    uintptr_t Children = 0x198;
    uintptr_t Role = 0x148;
    //--Class PlayerController
    uintptr_t PlayerCameraManager = 0x488;
    //--Class UAEPlayerController
    constexpr auto PlayerKeyController = 0x840;
    //--Class PlayerCameraManager
    uintptr_t CameraCache = 0x460;
    //--Class Character
    uintptr_t Mesh = 0x450;
    //--Class StaticMeshComponent
    uintptr_t MinLOD = 0x7b0; // RuntimeMeshComponent or SkeletalMeshComponent* SkeletalMesh or MinLOD
    //--Class SceneComponent
    uintptr_t RelativeLocation = 0x17c;
    //--Class UAECharacter
    uintptr_t PlayerName = 0x8b0;
    uintptr_t Nation = 0x8c0;
    uintptr_t PlayerKeyCharacter = 0x8d0;
    uintptr_t TeamID = 0x8f8;
    uintptr_t bIsAI = 0x990;
    //--Class STExtraCharacter
    uintptr_t Health = 0xd08;
    uintptr_t bDead = 0xd24;
    //--Class STExtraWeapon
    uintptr_t WeaponEntityComp = 0x7a8;
    //--Class STExtraShootWeapon
    uintptr_t CurBulletNumInClip = 0xe68;
    uintptr_t ShootWeaponEntityComp = 0xfb8;
    //--Class WeaponEntity
    uintptr_t WeaponId = 0x170;
    //--Class STExtraBaseCharacter
    uintptr_t bIsWeaponFiring = 0x1538;
    uintptr_t WeaponManagerComponent = 0x2058;
    uintptr_t ThirdPersonCameraComponent = 0x1950;
    //--Class CameraComponent
    uintptr_t FieldOfView = 0x2cc;
    //--Class WeaponManagerComponent
    uintptr_t CurrentWeaponReplicated = 0x4e8;
    //--Class ShootWeaponEntity
    uintptr_t AccessoriesVRecoilFactor = 0xab8;
    uintptr_t AccessoriesHRecoilFactor = 0xabc;
    uintptr_t GameDeviationFactor = 0xb30;
    uintptr_t SRecoilInfo = 0xa48;
    uintptr_t AutoAimingConfig = 0x920;
    //--Class AutoAimingConfig
    uintptr_t OuterRange = 0x0;
    uintptr_t InnerRange = 0x4c;
    uintptr_t ScopeRange = 0x98;
    //--Class SRecoilInfo
    uintptr_t RecoilModifierStand = 0x50;
    uintptr_t RecoilModifierCrouch = 0x54;
    uintptr_t RecoilModifierProne = 0x58;
    //--Class UAEPlayerState
    uintptr_t Kills = 0x64c;
}

#endif // COSMIC_OFFSETS_H
