#ifndef COSMIC_HACKSDRAWING_H
#define COSMIC_HACKSDRAWING_H

#include <cmath>
#include <chrono>

Request request;
Response response;
char text[100];
int botCount, playerCount, knockCount;
int screenWidth, screenHeight;
Color colorPlayer, colorText, colorData, clrHealth;

unsigned int iMemClock, iCurClock, iLoops;
char aFPS[12];

//-- Set Line to Skeleton
void Skeleton(CanvasEngine draw, Color color, Vector3 vec1, Vector3 vec2) {
    draw.DrawLine(color, widthLine, Vector2(vec1.X, vec1.Y), Vector2(vec2.X, vec2.Y));
}

void MainDraw(CanvasEngine draw) {

    if (iMemClock > (iCurClock = clock()))
        iLoops++;
    else {
        snprintf(aFPS, sizeof(aFPS), "%d", iLoops);
        iMemClock = iCurClock + CLOCKS_PER_SEC;
        iLoops = 0;
    }

    botCount = 0;
    playerCount = 0;
    knockCount = 0;
    request.ScreenWidth = draw.getWidth();
    request.ScreenHeight = draw.getHeight();
    request.memory.isAimbot = isBullet;
    request.memory.isBotAimbot = isBot;
    request.memory.isKnockAimbot = isKnock;
    request.memory.FovAimbot = fovAimbot;
    request.memory.TargetAimbot = targetMode;
    request.memory.isLess = isLess;
    request.memory.isCrosshair = isCrossHire;
    request.memory.isIPadView = rangeIPadView;
    screenHeight = draw.getHeight();
    screenWidth = draw.getWidth();
    send((void *) &request, sizeof(request));
    receive((void *) &response);

    sprintf(text, "Fps Device: %d", m_fps.get());
    draw.DrawInfo(Color(69, 110, 138, 255), text, Vector2(150, 120), 35);
    sprintf(text, "Fps Drawing: %s", aFPS);
    draw.DrawInfo(Color(60, 255, 0, 255), text, Vector2(150, 170), 35);
    sprintf(text, "Fps Sock: %s", response.FpsSock);
    draw.DrawInfo(Color(255, 68, 0, 255), text, Vector2(150, 220), 35);
    sprintf(text, "Count Kill: %d", response.Players->countKill);
    draw.DrawInfo(Color(255, 0, 0, 255), text, Vector2(150, 270), 35);

    //--===== Check Player Lobby =====
    if (!response.InLobby) {
        if (response.Success) {
            int count = response.PlayerCount;
            if (count > 0) {
                for (int i = 0; i < count; i++) {

                    PlayerData player = response.Players[i];
                    float x = player.HeadLocation.X;
                    float y = player.HeadLocation.Y;
                    float yh = player.HeadLocation.Y;
                    float yr = player.RootLocation.Y;
                    float magic_number = (player.Distance);
                    float mx = (screenWidth / 4) / magic_number;
                    float my = (screenWidth / 1.38) / magic_number;
                    float top = yh - my + (screenWidth / 1.7) / magic_number;
                    float bottom = yr + my - (screenWidth / 1.7) / magic_number;
                    float boxHeight = abs(player.HeadLocation.Y - player.RootLocation.Y);
                    float boxWidth = boxHeight * 0.6f;
                    //Vector2 vStart = Vector2(x - (boxWidth / 2), top);
                    //  Vector2 vEnd = Vector2(x + (boxWidth / 2), bottom);
                    Vector2 vStart = Vector2(player.Bone.rShoulder.X - (boxWidth / 2), top);
                    Vector2 vEnd = Vector2(player.Bone.lAnkle.X + (boxWidth / 2), bottom);
                    int Dist = 1000 / (5 + response.Players[i].Distance);
                    int W = screenWidth / 2;
                    int H = screenHeight / 2;
                    int Ppw = response.Players[i].Bone.pelvis.X;
                    int Pph = response.Players[i].Bone.pelvis.Y;
                    int Pcw = response.Players[i].Bone.chest.X;
                    int Pch = response.Players[i].Bone.chest.Y;
                    bool insidePelvisW = Ppw > W - Dist && Ppw < W + Dist;
                    bool insidePelvisH = Pph > H - Dist && Pph < H + Dist;
                    bool insideCheastW = Pcw > W - Dist && Pcw < W + Dist;
                    bool insideCheastH = Pch > H - Dist && Pch < H + Dist;

                    if (response.Players[i].isBot && player.Health <= 0) {
                        colorPlayer = Color(255, 255, 255, 255);
                        colorText = Color(255, 255, 255, 255);
                        colorData = Color(255, 255, 255, 255);
                    }

                    if (response.Players[i].isBot && player.Health > 0) {
                        botCount++;
                        colorPlayer = Color(255, 255, 255, 255);
                        colorText = Color(255, 255, 255, 255);
                        colorData = Color(255, 255, 255, 255);
                    }

                    if (!response.Players[i].isBot && player.Health > 0) {
                        playerCount++;
                        colorPlayer = Color(232, 0, 12, 255);
                        colorText = Color(255, 255, 255, 255);
                        colorData = Color(230, 176, 0, 255);
                    }

                    if (!response.Players[i].isBot && player.Health <= 0) {
                        knockCount++;
                        colorPlayer = Color(0, 255, 0, 255);
                        colorText = Color(0, 255, 0, 255);
                        colorData = Color(0, 255, 0, 255);
                    }

                    if (insidePelvisW || insideCheastW) {
                        if (insidePelvisH || insideCheastH) {
                            colorPlayer = Color(255, 255, 0, 255);
                        }
                    }

                    if (player.HeadLocation.Z >= 0) {

                        //==================== SKELETON ====================//
                        if (isSkeleton) {
                            Skeleton(draw, colorPlayer, player.Bone.neck, player.Bone.chest);
                            Skeleton(draw, colorPlayer, player.Bone.chest, player.Bone.pelvis);
                            Skeleton(draw, colorPlayer, player.Bone.chest, player.Bone.lShoulder);
                            Skeleton(draw, colorPlayer, player.Bone.chest, player.Bone.rShoulder);
                            Skeleton(draw, colorPlayer, player.Bone.lShoulder, player.Bone.lElbow);
                            Skeleton(draw, colorPlayer, player.Bone.rShoulder, player.Bone.rElbow);
                            Skeleton(draw, colorPlayer, player.Bone.lElbow, player.Bone.lWrist);
                            Skeleton(draw, colorPlayer, player.Bone.rElbow, player.Bone.rWrist);
                            Skeleton(draw, colorPlayer, player.Bone.pelvis, player.Bone.lThigh);
                            Skeleton(draw, colorPlayer, player.Bone.pelvis, player.Bone.rThigh);
                            Skeleton(draw, colorPlayer, player.Bone.lThigh, player.Bone.lKnee);
                            Skeleton(draw, colorPlayer, player.Bone.rThigh, player.Bone.rKnee);
                            Skeleton(draw, colorPlayer, player.Bone.lKnee, player.Bone.lAnkle);
                            Skeleton(draw, colorPlayer, player.Bone.rKnee, player.Bone.rAnkle);
                            draw.DrawCircle(colorPlayer, Vector2(x, y),screenHeight / 12.5f / magic_number, widthLine);
                        }

                        //==================== LINE ====================//
                        if (isLine) {
                            draw.DrawLine(colorPlayer, widthLine,
                                          Vector2(draw.getWidth() / 2, 180),
                                          Vector2(x, top));
                        }

                        //==================== HEALTH ====================//
                        if (isHealth) {
                            if (player.Health <= 0) {
                                draw.DrawText(colorText, "KNOCK",
                                              Vector2(x, top - screenHeight / 225), 12);
                            } else {
                                float distanceDifference = vEnd.Y - vStart.Y;
                                float yHealth = distanceDifference * (player.Health / 100);
                                sprintf(text, "%d - %s - %s", player.TeamID,
                                        player.PlayerNationByte,
                                        player.PlayerNameByte);
                                float nameLength = size(text) * 1.4;
                                float healthLength = screenWidth / 55;
                                if (healthLength < mx) {
                                    healthLength = mx;
                                }
                                float wHealth = 5.5;
                                float hHealth = 5.5;

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

                                if (player.Distance <= 40) {
                                    draw.DrawFilledRect(clrHealth,
                                                        Vector2(vStart.X, vEnd.Y),
                                                        Vector2(vStart.X - 10, vEnd.Y - yHealth));
                                    draw.DrawRect(Color(0, 0, 0), screenHeight / 660,
                                                  Vector2(vStart.X, vStart.Y),
                                                  Vector2(vStart.X - 10, vEnd.Y));
                                } else {
                                    draw.DrawFilledRect(clrHealth,
                                                        Vector2(x - (healthLength +
                                                                     (nameLength +
                                                                      (sizeInfo * wHealth))),
                                                                top -
                                                                ((screenHeight /
                                                                  (20 -
                                                                   (sizeInfo /
                                                                    hHealth))) +
                                                                 (upInfo))),
                                                        Vector2(x - (healthLength +
                                                                     (nameLength +
                                                                      (sizeInfo * wHealth))) +
                                                                (2 * (healthLength +
                                                                      (nameLength +
                                                                       (sizeInfo * wHealth)))) *
                                                                response.Players[i].Health / 100,
                                                                top -
                                                                ((screenHeight /
                                                                  (35 -
                                                                   (sizeInfo /
                                                                    hHealth))) +
                                                                 (upInfo))));

                                    draw.DrawRect(Color(0, 0, 0), screenHeight / 660,
                                                  Vector2(x - (healthLength +
                                                               (nameLength + (sizeInfo * wHealth))),
                                                          top -
                                                          ((screenHeight /
                                                            (20 -
                                                             (sizeInfo /
                                                              hHealth))) +
                                                           (upInfo))),
                                                  Vector2(x + (healthLength +
                                                               (nameLength + (sizeInfo * wHealth))),
                                                          top -
                                                          ((screenHeight /
                                                            (35 -
                                                             (sizeInfo /
                                                              hHealth))) +
                                                           (upInfo))));

                                }

                            }


                        }

                        if (player.Health > 0) {
                            //==================== INFO ====================//
                            if (isInfoPlayer) {
                                draw.InfoPLayer(colorText, response.Players[i].TeamID,
                                                response.Players[i].PlayerNameByte,
                                                response.Players[i].PlayerNationByte,
                                                Vector2(x, top - ((screenHeight /
                                                                   (30 - (sizeInfo / 7.5))) +
                                                                  (upInfo))),
                                                (16 + sizeInfo));
                            }

                            if (player.Distance <= 450) {
                                //==================== BOX ====================//
                                if (isBox) {
                                    draw.DrawRect(colorPlayer, widthLine, vStart, vEnd);
                                }

                                //==================== DIST ====================//
                                if (isDist) {
                                    sprintf(text, "%0.0fM", player.Distance);
                                    draw.DrawText(colorData, text,
                                                  Vector2(x, top + -screenHeight / 90), 18);
                                }

                                //==================== WEAPON ====================//
                                if (isWeapon && player.Weapon.isWeapon) {
                                    draw.DrawWeapon(colorText,
                                                    player.Weapon.id,
                                                    player.Weapon.ammo,
                                                    Vector2(x, bottom + 25), 18);
                                }
                            }
                        }
                    }

                    //==================== ALERT 360 ====================//
                    if (player.HeadLocation.Z < 0) {
                        if (isMark) {


                        }
                    }


                    if (player.HeadLocation.Z <= 0) {
                        if (!isMark)
                            continue;
                        if (x > screenWidth - screenWidth / 12)
                            x = screenWidth - screenWidth / 120;
                        else if (x < screenWidth / 120)
                            x = screenWidth / 12;
                        if (y < screenHeight / 1) { // top
                            draw.DrawFilledCircle(colorPlayer,
                                                  Vector2(x,
                                                          screenHeight -
                                                          (offsetAfter360 + radiusAfter360)),
                                                  offsetAfter360 + radiusAfter360);
                        } else {
                            draw.DrawFilledCircle(colorPlayer,
                                                  Vector2(x, 0 + (offsetAfter360 + radiusAfter360)),
                                                  offsetAfter360 + radiusAfter360);
                        }
                    } else if (x < -screenWidth / 10 || x > screenWidth + screenWidth / 10) {
                        if (!isMark)
                            continue;
                        if (y > screenHeight - screenHeight / 12)
                            y = screenHeight - screenHeight / 120;
                        else if (y < screenHeight / 120)
                            y = screenHeight / 12;
                        if (x > screenWidth / 2) {
                            draw.DrawFilledCircle(colorPlayer,
                                                  Vector2(screenWidth -
                                                          (offsetAfter360 + radiusAfter360),
                                                          y),
                                                  offsetAfter360 + radiusAfter360);
                        } else {
                            draw.DrawFilledCircle(colorPlayer,
                                                  Vector2(0 + (offsetAfter360 + radiusAfter360), y),
                                                  offsetAfter360 + radiusAfter360);
                        }
                    } else if (y < -screenHeight / 10 || y > screenHeight + screenHeight / 10) {
                        if (!isMark)
                            continue;
                        if (x > screenWidth - screenWidth / 12)
                            x = screenWidth - screenWidth / 120;
                        else if (x < screenWidth / 120)
                            x = screenWidth / 12;
                        if (y > screenHeight / 2.5) {
                            draw.DrawFilledCircle(colorPlayer,
                                                  Vector2(x,
                                                          screenHeight -
                                                          (offsetAfter360 + radiusAfter360)),
                                                  offsetAfter360 + radiusAfter360);
                        } else {
                            draw.DrawFilledCircle(colorPlayer,
                                                  Vector2(x, 0 + (offsetAfter360 + radiusAfter360)),
                                                  offsetAfter360 + radiusAfter360);
                        }
                    }

                }

                //==================== COUNT PLAYER ====================//
                if (botCount + playerCount > 0) {
                    sprintf(text, "%d", botCount);
                    draw.DrawText(Color(255, 255, 255), "BOT",
                                  Vector2(screenWidth / 2 - 60, 100), 31);

                    draw.DrawText(Color(255, 255, 255), text,
                                  Vector2(screenWidth / 2 - 60, 150), 35);

                    sprintf(text, "%d", playerCount);
                    draw.DrawText(Color(232, 0, 12), "PLAYER",
                                  Vector2(screenWidth / 2 + 60, 100), 31);

                    draw.DrawText(Color(232, 0, 12), text,
                                  Vector2(screenWidth / 2 + 60, 150), 35);

                    draw.DrawText(Color(255, 255, 255), "﹀",
                                  Vector2(screenWidth / 2, 185), 45);
                    draw.DrawText(Color(255, 255, 255), "____    ____",
                                  Vector2(screenWidth / 2, 160), 45);
                }
                if (knockCount > 0) {
                    sprintf(text, "KNOCK : %d", knockCount);
                    draw.DrawText(Color(0, 255, 0), text,
                                  Vector2(screenWidth / 2, 350), 30);
                }

            }

            //==================== GRENADE ====================//
            for (int i = 0; i < response.GrenadeCount; i++) {
                if (!isGrenade)
                    continue;
                draw.DrawText(Color(255, 0, 0), "Warning Grenade !!!",
                              Vector2(screenWidth / 2, 300), 30);
                if (response.Grenade[i].Location.Z >= 0) {
                    if (response.Grenade[i].type == 1) {
                        draw.DrawFilledCircle(Color(255, 0, 0, 255),
                                              Vector2(response.Grenade[i].Location.X,
                                                      response.Grenade[i].Location.Y),
                                              20);
                        sprintf(text, "%0.0f", response.Grenade[i].Distance);
                        draw.DrawText(Color(255, 255, 255, 150), text,
                                      Vector2(response.Grenade[i].Location.X,
                                              response.Grenade[i].Location.Y + 5),
                                      16);
                    } else if (response.Grenade[i].type == 2) {
                        draw.DrawFilledCircle(Color(255, 140, 0, 255),
                                              Vector2(response.Grenade[i].Location.X,
                                                      response.Grenade[i].Location.Y),
                                              20);
                        sprintf(text, "%0.0f", response.Grenade[i].Distance);
                        draw.DrawText(Color(255, 255, 255, 150), text,
                                      Vector2(response.Grenade[i].Location.X,
                                              response.Grenade[i].Location.Y + 5),
                                      16);
                    } else if (response.Grenade[i].type == 3) {
                        draw.DrawFilledCircle(Color(0, 255, 200, 255),
                                              Vector2(response.Grenade[i].Location.X,
                                                      response.Grenade[i].Location.Y),
                                              20);
                        sprintf(text, "%0.0f", response.Grenade[i].Distance);
                        draw.DrawText(Color(255, 255, 255, 150), text,
                                      Vector2(response.Grenade[i].Location.X,
                                              response.Grenade[i].Location.Y + 5),
                                      16);
                    } else if (response.Grenade[i].type == 4) {
                        draw.DrawFilledCircle(Color(0, 209, 7, 255),
                                              Vector2(response.Grenade[i].Location.X,
                                                      response.Grenade[i].Location.Y),
                                              20);
                        sprintf(text, "%0.0f", response.Grenade[i].Distance);
                        draw.DrawText(Color(255, 255, 255, 150), text,
                                      Vector2(response.Grenade[i].Location.X,
                                              response.Grenade[i].Location.Y + 5),
                                      16);
                    }
                }
            }

            //==================== VEHICLE ====================//
            int vehicleCount = response.VehicleCount;
            if (vehicleCount > 0) {
                for (int i = 0; i < vehicleCount; i++) {
                    if (response.Vehicles[i].Location.Z != 1.0f) {
                        if (response.Vehicles[i].Distance > 10)
                            draw.DrawVehicles(response.Vehicles[i].VehicleName,
                                              response.Vehicles[i].Distance,
                                              Vector2(response.Vehicles[i].Location.X,
                                                      response.Vehicles[i].Location.Y),
                                              normalSize + sizeVehicle, 0, 0, true);
                    }
                }
            }

            // ==================== ITEM ====================//
            int itemsCount = response.ItemsCount;
            if (itemsCount > 0) {
                for (int i = 0; i < itemsCount; i++) {
                    if (response.Items[i].Location.Z != 1.0f) {
                        if (strstr(response.Items[i].ItemName, "PlayerDeadInventoryBox_C")) {
                            draw.DrawItems(response.Items[i].ItemName, response.Items[i].Distance,
                                           Vector2(response.Items[i].Location.X,
                                                   response.Items[i].Location.Y),
                                           normalSize + sizeItem);
                        } else {
                            if ((int) response.Items[i].Distance > 3 &&
                                (int) response.Items[i].Distance < 50)
                                draw.DrawItems(response.Items[i].ItemName,
                                               response.Items[i].Distance,
                                               Vector2(response.Items[i].Location.X,
                                                       response.Items[i].Location.Y),
                                               normalSize + sizeItem);
                        }
                    }
                }
            }

            if (request.memory.isBotAimbot)
                draw.DrawCircle(Color(255, 255, 255, 255),
                                Vector2(screenWidth / 2, screenHeight / 2),
                                request.memory.FovAimbot, 2.5);
        }
        //--
    } else {
        draw.DrawText(Color(255, 255, 255), "In Lobby", Vector2(screenWidth / 2, 100), 30);
    }
    m_fps.Update();


    draw.DrawText(Color(255, 255, 255), "In Lobby", Vector2(screenWidth / 2, 100), 30);
}

#endif //COSMIC_HACKSDRAWING_H
