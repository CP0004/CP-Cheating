//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_OFFSETS_H
#define COSMIC_OFFSETS_H

#include <stdint.h>

namespace Offsets
{
    uintptr_t GNames = 0x00;
    uintptr_t GWorld = 0x00;
    uintptr_t PersistentLevel = 0x00;
    uintptr_t ULevelToAActors = 0x00;
    uintptr_t DrawShootLineTime = 0x00;
   
    uintptr_t RootComponent = 0x00;
    uintptr_t Children = 0x00;
    uintptr_t Role = 0x00;
 
    uintptr_t PlayerCameraManager = 0x00;
   
    constexpr auto PlayerKeyController = 0x00;
  
    uintptr_t CameraCache = 0x00;
  
    uintptr_t Mesh = 0x00;
  
    uintptr_t MinLOD = 0x00; // RuntimeMeshComponent or SkeletalMeshComponent* SkeletalMesh or MinLOD
 
    uintptr_t RelativeLocation = 0x00;
    uintptr_t ComponentToWorld = 0x00;
    
    uintptr_t PlayerName = 0x00;
    uintptr_t Nation = 0x00;
    uintptr_t PlayerKeyCharacter = 0x00;
    uintptr_t TeamID = 0x00;
    uintptr_t bIsAI = 0x00;
    
    uintptr_t Health = 0x00;
    uintptr_t bDead = 0x00;
  
    uintptr_t WeaponEntityComp = 0x00;
   
    uintptr_t CurBulletNumInClip = 0x00;
    uintptr_t ShootWeaponEntityComp = 0x00;
 
    uintptr_t WeaponId = 0x00;
    
    uintptr_t bIsWeaponFiring = 0x00;
    uintptr_t WeaponManagerComponent = 0x00;
    uintptr_t ThirdPersonCameraComponent = 0x00;
  
    uintptr_t FieldOfView = 0x00;
   
    uintptr_t CurrentWeaponReplicated = 0x00;
  
    uintptr_t AccessoriesVRecoilFactor = 0x00;
    uintptr_t AccessoriesHRecoilFactor = 0x00;
    uintptr_t GameDeviationFactor = 0x00;
    uintptr_t SRecoilInfo = 0x00;
    uintptr_t AutoAimingConfig = 0x00;
 
    uintptr_t OuterRange = 0x00;
    uintptr_t InnerRange = 0x00;
    uintptr_t ScopeRange = 0x00;
   
    uintptr_t RecoilModifierStand = 0x00;
    uintptr_t RecoilModifierCrouch = 0x00;
    uintptr_t RecoilModifierProne = 0x00;
   
    uintptr_t Kills = 0x00;

}

#endif // COSMIC_OFFSETS_H
