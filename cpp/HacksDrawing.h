#ifndef COSMIC_HACKSDRAWING_H
#define COSMIC_HACKSDRAWING_H

#include <cmath>
#include <chrono>

Request request;
Response response;
char text[100];
int botCount, playerCount, knockCount;
int screenWidth, screenHeight;
Color colorPlayers, colorInfos, colorDists, colorWeapons, clrHealth;


float offsetInfoOfHead = 47.5;
int offsetHealthOfHead = 40;
int offsetDistOfHead = 5;
int offsetWeaponOfHead = 10;
int offsetMarkOfScreen = 10;
int offsetRangingOfVehicle = 15;

int radiusMark360 = 25;
int radiusGrenade = 20;
int sizeText = 20;

int hCounter = 50;

Color colorAinPlayer = Color(1, 256, 6);
Color colorPlayer = Color(245, 2, 0);
Color colorKnock = Color(90, 212, 76);
Color colorBot = Color(255, 255, 255);
Color colorInfo = Color(255, 255, 255);
Color colorDist = Color(210, 152, 44);
Color colorWeapon = Color(210, 152, 44);
Color colorHealthVehicle = Color(0, 110, 255);
Color colorFuelVehicle = Color(255, 157, 0);


//-- Set Line to Skeleton
void Skeleton(CanvasEngine draw, Color color, Vector3 vec1, Vector3 vec2) {
    draw.DrawLine(color, widthLine, Vector2(vec1.X, vec1.Y), Vector2(vec2.X, vec2.Y));
}

void MainDraw(CanvasEngine draw) {


    request.ScreenWidth = screenWidth = draw.getWidth();
    request.ScreenHeight = screenHeight = draw.getHeight();
    request.memory.isLess = isLess;
    request.memory.isCrosshair = isCrossHire;
    request.memory.rangeIPadView = rangeIPadView;
    request.memory.isAimbot = isAimBot;
    request.memory.isBullet = isBullet;
    send((void *) &request, sizeof(request));
    receive((void *) &response);

    sprintf(text, "FPS: %d", m_fps.get());
    draw.DrawInfo(Color(69, 110, 138, 255), text, Vector2(0 + 100, screenHeight - 100), 25);

    botCount = 0;
    playerCount = 0;
    knockCount = 0;

    //--===== Check Player Lobby =====
    if (!response.InLobby) {
        if (response.Success) {
            int count = response.PlayerCount;
            if (count > 0) {
                for (int i = 4; i < count; i++) {

                    PlayerData player = response.Players[i];

                    float magic_number = player.Distance;
                    float xh = player.HeadLocation.X;
                    float yh = player.HeadLocation.Y;
                    float xr = player.RootLocation.X;
                    float yr = player.RootLocation.Y;
                    float mx = (screenWidth / 4.f) / magic_number;
                    float my = (screenWidth / 1.38f) / magic_number;
                    float top = yh - my + (screenWidth / 1.7f) / magic_number;
                    float bottom = yr + my - (screenWidth / 1.7f) / magic_number;

                    int Dist = 1000 / (5 + response.Players[i].Distance);
                    int W = screenWidth / 2;
                    int H = screenHeight / 2;
                    int Ppw = response.Players[i].Bones.pelvis.X;
                    int Pph = response.Players[i].Bones.pelvis.Y;
                    int Pcw = response.Players[i].Bones.chest.X;
                    int Pch = response.Players[i].Bones.chest.Y;
                    bool insidePelvisW = Ppw > W - Dist && Ppw < W + Dist;
                    bool insidePelvisH = Pph > H - Dist && Pph < H + Dist;
                    bool insideCheastW = Pcw > W - Dist && Pcw < W + Dist;
                    bool insideCheastH = Pch > H - Dist && Pch < H + Dist;


                    if (response.Players[i].isBot && player.Health <= 0) {
                        knockCount++;
                        colorPlayers = colorKnock;
                        colorInfos = colorKnock;
                        colorDists = colorKnock;
                        colorWeapons = colorKnock;
                    }

                    if (response.Players[i].isBot && player.Health > 0) {
                        botCount++;
                        colorPlayers = colorBot;
                        colorInfos = colorBot;
                        colorDists = colorBot;
                        colorWeapons = colorBot;
                    }

                    if (!response.Players[i].isBot && player.Health > 0) {
                        playerCount++;
                        colorPlayers = colorPlayer;
                        colorInfos = colorInfo;
                        colorDists = colorDist;
                        colorWeapons = colorWeapon;
                    }

                    if (!response.Players[i].isBot && player.Health <= 0) {
                        knockCount++;
                        colorPlayers = colorKnock;
                        colorInfos = colorKnock;
                        colorDists = colorKnock;
                        colorWeapons = colorKnock;
                    }

                    if (insidePelvisW || insideCheastW)
                        if (insidePelvisH || insideCheastH)
                            if (player.Health > 0)
                                colorPlayers = colorAinPlayer;

                    if (player.HeadLocation.Z != 0) {

                        //==================== SKELETON ====================//
                        if (isSkeleton) {
                            Skeleton(draw, colorPlayers, player.Bones.necks, player.Bones.chest);
                            Skeleton(draw, colorPlayers, player.Bones.chest, player.Bones.pelvis);
                            Skeleton(draw, colorPlayers, player.Bones.chest, player.Bones.lShoulder);
                            Skeleton(draw, colorPlayers, player.Bones.chest, player.Bones.rShoulder);
                            Skeleton(draw, colorPlayers, player.Bones.lShoulder, player.Bones.lElbow);
                            Skeleton(draw, colorPlayers, player.Bones.rShoulder, player.Bones.rElbow);
                            Skeleton(draw, colorPlayers, player.Bones.lElbow, player.Bones.lWrist);
                            Skeleton(draw, colorPlayers, player.Bones.rElbow, player.Bones.rWrist);
                            Skeleton(draw, colorPlayers, player.Bones.pelvis, player.Bones.lThigh);
                            Skeleton(draw, colorPlayers, player.Bones.pelvis, player.Bones.rThigh);
                            Skeleton(draw, colorPlayers, player.Bones.lThigh, player.Bones.lKnee);
                            Skeleton(draw, colorPlayers, player.Bones.rThigh, player.Bones.rKnee);
                            Skeleton(draw, colorPlayers, player.Bones.lKnee, player.Bones.lAnkle);
                            Skeleton(draw, colorPlayers, player.Bones.rKnee, player.Bones.rAnkle);
                            draw.DrawFilledCircle(colorPlayers, Vector2(xh, yh),
                                                  screenHeight / 20 / magic_number);
                        }

                        //==================== LINE ====================//
                        if (isLine) {
                            draw.DrawLine(colorPlayers, widthLine,
                                          Vector2(screenWidth / 2, 200 - 50),
                                          Vector2(xh, top));
                        }

                        //==================== HEALTH ====================//
                        if (isHealth) {
                            if (player.Health > 0) {

                                sprintf(text, "%d   %s", player.TeamID, player.PlayerNameByte);
                                float nameLength = size(text) * 2.0;
                                float health = player.Health;

                                float distanceDifference = (xh + nameLength) - (xh - nameLength);
                                float rangHealth = distanceDifference * (health / 100);


                                if (player.Health < 5)
                                    clrHealth = Color(51, 0, 0, 120);
                                else if (player.Health < 10)
                                    clrHealth = Color(143, 0, 0, 120);
                                else if (player.Health < 15)
                                    clrHealth = Color(191, 13, 0, 120);
                                else if (player.Health < 20)
                                    clrHealth = Color(191, 22, 0, 120);
                                else if (player.Health < 25)
                                    clrHealth = Color(191, 32, 0, 120);
                                else if (player.Health < 30)
                                    clrHealth = Color(191, 45, 0, 120);
                                else if (player.Health < 35)
                                    clrHealth = Color(191, 57, 0, 120);
                                else if (player.Health < 40)
                                    clrHealth = Color(191, 76, 0, 120);
                                else if (player.Health < 45)
                                    clrHealth = Color(191, 102, 0, 120);
                                else if (player.Health < 50)
                                    clrHealth = Color(191, 121, 0, 120);
                                else if (player.Health < 55)
                                    clrHealth = Color(191, 134, 0, 120);
                                else if (player.Health < 60)
                                    clrHealth = Color(191, 156, 0, 120);
                                else if (player.Health < 65)
                                    clrHealth = Color(181, 191, 0, 120);
                                else if (player.Health < 70)
                                    clrHealth = Color(156, 191, 0, 120);
                                else if (player.Health < 75)
                                    clrHealth = Color(124, 191, 0, 120);
                                else if (player.Health < 80)
                                    clrHealth = Color(102, 191, 0, 120);
                                else
                                    clrHealth = Color(0, 191, 6, 120);


                                draw.DrawFilledRect(clrHealth,
                                                    Vector2(xh - nameLength,
                                                            (top - offsetHealthOfHead) - 30),
                                                    Vector2((xh - nameLength) + rangHealth,
                                                            (top - offsetHealthOfHead)));

                                draw.DrawRect(Color(0, 0, 0), screenHeight / 660,
                                              Vector2(xh - nameLength,
                                                      (top - offsetHealthOfHead) - 30),
                                              Vector2(xh + nameLength,
                                                      (top - offsetHealthOfHead)));
                            }


                        }

                        //==================== INFO ====================//
                        if (isInfoPlayer) {
                            draw.InfoPLayer(colorInfos, response.Players[i].TeamID,
                                            response.Players[i].PlayerNameByte, "",
                                            Vector2(xh, top - offsetInfoOfHead),
                                            sizeText);
                        }

                        //==================== DIST ====================//
                        if (isDist) {
                            sprintf(text, "%0.0fm", player.Distance);
                            draw.DrawText(colorDists, text,
                                          Vector2(xh, top - offsetDistOfHead), sizeText);
                        }

                        //==================== WEAPON ====================//
                        if (isWeapon && player.Weapon.isWeapon) {
                            if (player.Health > 0)
                                draw.DrawWeapon(colorWeapons,
                                                player.Weapon.id,
                                                player.Weapon.ammo,
                                                Vector2(xr, bottom + offsetWeaponOfHead),
                                                sizeText);
                        }
                    }

                    //==================== ALERT 360 ====================//
                    if (isMark) {
                        colorPlayers.a = 120;
                        if (player.HeadLocation.Z <= 0) {
                            if (xh > screenWidth - screenWidth / 12)
                                xh = screenWidth - screenWidth / 120;
                            else if (xh < screenWidth / 120)
                                xh = screenWidth / 12;
                            if (yh < screenHeight / 1) {
                                draw.DrawFilledCircle(colorPlayers, Vector2(xh, screenHeight -
                                                                                (radiusMark360 /
                                                                                 2)),
                                                      radiusMark360);
                            } else {
                                draw.DrawFilledCircle(colorPlayers,
                                                      Vector2(xh, 0 + (radiusMark360 / 2)),
                                                      radiusMark360);
                            }
                        } else if (xh < -screenWidth / 10 || xh > screenWidth + screenWidth / 10) {
                            if (yh > screenHeight - screenHeight / 12)
                                yh = screenHeight - screenHeight / 120;
                            else if (yh < screenHeight / 120)
                                yh = screenHeight / 12;
                            if (xh > screenWidth / 2) {
                                draw.DrawFilledCircle(colorPlayers,
                                                      Vector2(screenWidth - (radiusMark360 / 2),
                                                              yh), radiusMark360);
                            } else {
                                draw.DrawFilledCircle(colorPlayers,
                                                      Vector2(0 + (radiusMark360 / 2), yh),
                                                      radiusMark360);
                            }
                        } else if (yh < -screenHeight / 10 ||
                                   yh > screenHeight + screenHeight / 10) {
                            if (xh > screenWidth - screenWidth / 12)
                                xh = screenWidth - screenWidth / 120;
                            else if (xh < screenWidth / 120)
                                xh = screenWidth / 12;
                            if (yh > screenHeight / 2.5) {
                                draw.DrawFilledCircle(colorPlayers, Vector2(xh, screenHeight -
                                                                                (radiusMark360 /
                                                                                 2)),
                                                      radiusMark360);
                            } else {
                                draw.DrawFilledCircle(colorPlayers,
                                                      Vector2(xh, 0 + (radiusMark360 / 2)),
                                                      radiusMark360);
                            }
                        }
                    }
                }

                //==================== COUNT PLAYER ====================//
                if (botCount + playerCount > 0) {

                    draw.DrawLine(Color(245, 2, 0), 3.5,
                                  Vector2(screenWidth / 2 + 100, 173 - hCounter),
                                  Vector2(screenWidth / 2 + 30, 173 - hCounter));
                    draw.DrawLine(Color(245, 2, 0), 3.5,
                                  Vector2(screenWidth / 2 + 30, 173 - hCounter),
                                  Vector2(screenWidth / 2, 200 - hCounter));

                    draw.DrawLine(Color(255, 255, 255), 3.5,
                                  Vector2(screenWidth / 2 - 100, 173 - hCounter),
                                  Vector2(screenWidth / 2 - 30, 173 - hCounter));
                    draw.DrawLine(Color(255, 255, 255), 3.5,
                                  Vector2(screenWidth / 2 - 30, 173 - hCounter),
                                  Vector2(screenWidth / 2, 200 - hCounter));

                    sprintf(text, "%d", botCount);
                    draw.DrawText(Color(255, 255, 255), text,
                                  Vector2(screenWidth / 2 - 75, 161 - hCounter), 35);

                    sprintf(text, "%d", playerCount);
                    draw.DrawText(Color(245, 2, 0), text,
                                  Vector2(screenWidth / 2 + 75, 161 - hCounter), 35);

                }

                if (knockCount > 0) {
                    sprintf(text, "KNOCK : %d", knockCount);
                    draw.DrawText(Color(39, 130, 39), text,
                                  Vector2(screenWidth / 2, 350), 30);
                }

            }

            //==================== GRENADE ====================//
            if (isGrenade) {
                for (int i = 0; i < response.GrenadeCount; i++) {
                    draw.DrawText(Color(255, 0, 0), "Warning Grenade !!!",
                                  Vector2(screenWidth / 2, 300), 30);
                    if (response.Grenade[i].Location.Z >= 0) {
                        Color circleColor;
                        switch (response.Grenade[i].type) {
                            case 1:
                                circleColor = Color(255, 0, 0, 255);
                                break;
                            case 2:
                                circleColor = Color(255, 140, 0, 255);
                                break;
                            case 3:
                                circleColor = Color(0, 255, 200, 255);
                                break;
                            case 4:
                                circleColor = Color(0, 209, 7, 255);
                                break;
                            default:
                                circleColor = Color(255, 255, 255, 255);
                                break;
                        }
                        draw.DrawFilledCircle(circleColor, Vector2(response.Grenade[i].Location.X,
                                                                   response.Grenade[i].Location.Y),
                                              radiusGrenade);
                        sprintf(text, "%0.0f", response.Grenade[i].Distance);
                        draw.DrawText(Color(255, 255, 255, 150), text,
                                      Vector2(response.Grenade[i].Location.X,
                                              response.Grenade[i].Location.Y + 7), sizeText / 1.25);
                    }
                }
            }


            //==================== VEHICLE ====================//
            int vehicleCount = response.VehicleCount;
            if (vehicleCount > 0) {
                for (int i = 0; i < vehicleCount; i++) {
                    if (response.Vehicles[i].Location.Z != 1.0f) {
                        if (response.Vehicles[i].Distance > 10) {

                            draw.DrawVehicles(response.Vehicles[i].VehicleName,
                                              response.Vehicles[i].Distance,
                                              Vector2(response.Vehicles[i].Location.X,
                                                      response.Vehicles[i].Location.Y),
                                              sizeText / 1.25,
                                              0, 0, true);
                        }
                    }
                }
            }

            // ==================== ITEM ====================//
            int itemsCount = response.ItemsCount;
            if (itemsCount > 0) {
                for (int i = 0; i < itemsCount; i++) {
                    if (response.Items[i].Location.Z != 1.0f) {
                        if (strstr(response.Items[i].ItemNames, "PlayerDeadInventoryBox_C")) {
                            draw.DrawItems(response.Items[i].ItemNames, response.Items[i].Distance,
                                           Vector2(response.Items[i].Location.X,
                                                   response.Items[i].Location.Y),
                                           normalSize + sizeItem);
                        } else {
                            if ((int) response.Items[i].Distance > 3 &&
                                (int) response.Items[i].Distance < 50)
                                draw.DrawItems(response.Items[i].ItemNames,
                                               response.Items[i].Distance,
                                               Vector2(response.Items[i].Location.X,
                                                       response.Items[i].Location.Y),
                                               sizeText / 1.25);
                        }
                    }
                }
            }

            if (request.memory.isAimbot)
                draw.DrawCircle(Color(255, 255, 255, 255),
                                Vector2(screenWidth / 3, screenHeight / 2.4), 100/2, 2.5);
        }
        //--
    } else {
        draw.DrawText(Color(255, 255, 255), "In Lobby", Vector2(screenWidth / 2, 100), 30);
    }

    m_fps.Update();
}

#endif //COSMIC_HACKSDRAWING_H
