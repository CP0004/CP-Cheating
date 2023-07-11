//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_OFFSETS_H
#define COSMIC_OFFSETS_H

#include <stdint.h>

namespace Offsets
{
    uintptr_t GNames = 0x874e8ac;
    uintptr_t GWorld = 0x8a7cbf0;
    uintptr_t PersistentLevel = 0x20;
    uintptr_t ULevelToAActors = 0x70;
    uintptr_t DrawShootLineTime = 0xf4;
    //--Class Actor
    uintptr_t RootComponent = 0x150;
    uintptr_t Children = 0x144;
    uintptr_t Role = 0x100;
    //--Class PlayerController
    uintptr_t PlayerCameraManager = 0x370;
    //--Class UAEPlayerController
    constexpr auto PlayerKeyController = 0x65c;
    //--Class PlayerCameraManager
    uintptr_t CameraCache = 0x370;
    //--Class Character
    uintptr_t Mesh = 0x34c;
    //--Class StaticMeshComponent
    uintptr_t MinLOD = 0x634; // RuntimeMeshComponent or SkeletalMeshComponent* SkeletalMesh or MinLOD
    //--Class SceneComponent
    uintptr_t RelativeLocation = 0x118;
    uintptr_t ComponentToWorld = 0x140;
    //--Class UAECharacter
    uintptr_t PlayerName = 0x680;
    uintptr_t Nation = 0x68c;
    uintptr_t PlayerKeyCharacter = 0x698;
    uintptr_t TeamID = 0x6b4;
    uintptr_t bIsAI = 0x734;
    //--Class STExtraCharacter
    uintptr_t Health = 0x9d0;
    uintptr_t bDead = 0x9e8;
    //--Class STExtraWeapon
    uintptr_t WeaponEntityComp = 0x624;
    //--Class STExtraShootWeapon
    uintptr_t CurBulletNumInClip = 0xb58;
    uintptr_t ShootWeaponEntityComp = 0xc70;
    //--Class WeaponEntity
    uintptr_t WeaponId = 0x100;
    //--Class STExtraBaseCharacter
    uintptr_t bIsWeaponFiring = 0xfd4;
    uintptr_t WeaponManagerComponent = 0x1930;
    uintptr_t ThirdPersonCameraComponent = 0x1350;
    //--Class CameraComponent
    uintptr_t FieldOfView = 0x260;
    //--Class WeaponManagerComponent
    uintptr_t CurrentWeaponReplicated = 0x3fc;
    //--Class ShootWeaponEntity
    uintptr_t AccessoriesVRecoilFactor = 0x8c8;
    uintptr_t AccessoriesHRecoilFactor = 0x8cc;
    uintptr_t GameDeviationFactor = 0x930;
    uintptr_t SRecoilInfo = 0x864;
    uintptr_t AutoAimingConfig = 0x758;
    //--Class AutoAimingConfig
    uintptr_t OuterRange = 0x0;
    uintptr_t InnerRange = 0x4c;
    uintptr_t ScopeRange = 0x98;
    //--Class SRecoilInfo
    uintptr_t RecoilModifierStand = 0x48;
    uintptr_t RecoilModifierCrouch = 0x4c;
    uintptr_t RecoilModifierProne = 0x50;
    //--Class UAEPlayerState
    uintptr_t Kills = 0x4e4;

}

#endif // COSMIC_OFFSETS_H
